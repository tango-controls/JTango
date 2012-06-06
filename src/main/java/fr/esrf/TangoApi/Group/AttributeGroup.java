package fr.esrf.TangoApi.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.tango.utils.TangoUtil;

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.tango.clientapi.factory.ProxyFactory;

/**
 * Tentative to manage group of Attributes.
 * 
 * @author ABEILLE
 * 
 */
public final class AttributeGroup {
    private String[] userAttributesNames;
    /**
     * Used to write
     */
    private DeviceProxy[] devices;

    /**
     * Used to read. Group all attributes per device
     */
    private final Map<String, List<String>> attributesMap = new HashMap<String, List<String>>();
    private final Map<String, DeviceProxy> devicesMap = new HashMap<String, DeviceProxy>();

    private final Map<String, Integer> readAnswersIDs = new HashMap<String, Integer>();
    private final Map<String, Integer> writeAnswersIDs = new HashMap<String, Integer>();

    private List<DeviceAttribute> readReply = new ArrayList<DeviceAttribute>();
    private int timeout;
    private final boolean throwExceptions;

    public AttributeGroup(final String... attributes) throws DevFailed {
	throwExceptions = true;
	add(attributes);
    }

    public AttributeGroup(final boolean throwExceptions, final String... attributes) throws DevFailed {
	this.throwExceptions = throwExceptions;
	add(attributes);
    }

    public DeviceProxy getDevice(final String attributeName) throws DevFailed {
	return devicesMap.get(TangoUtil.getfullDeviceNameForAttribute(attributeName));
    }

    /**
     * Add a list of devices in the group or add a list of patterns
     * 
     * @param attributes
     *            The attribute list
     * @throws DevFailed
     */
    private synchronized void add(final String... attributes) throws DevFailed {
	userAttributesNames = new String[attributes.length];
	devices = new DeviceProxy[attributes.length];
	int i = 0;
	for (final String attributeName : attributes) {
	    final String deviceName = TangoUtil.getfullDeviceNameForAttribute(attributeName);
	    final String fullAttribute = TangoUtil.getfullAttributeNameForAttribute(attributeName);
	    userAttributesNames[i] = fullAttribute;
	    final DeviceProxy device = ProxyFactory.getInstance().createDeviceProxy(deviceName);
	    devices[i++] = device;
	    if (!devicesMap.containsKey(deviceName)) {
		devicesMap.put(deviceName, device);
	    }
	    final String attribute = fullAttribute.split("/")[3];
	    if (!attributesMap.containsKey(deviceName)) {
		final List<String> attributesNames = new ArrayList<String>();
		attributesNames.add(attribute);
		attributesMap.put(deviceName, attributesNames);
	    } else {
		attributesMap.get(deviceName).add(attribute);
	    }
	}
    }

    public synchronized void readAsync() throws DevFailed {
	if (!readAnswersIDs.isEmpty()) {
	    // remove requests to avoid memory leak
	    getReadReplies();
	}
	DevError[] allErrors = new DevError[0];
	for (final String deviceName : devicesMap.keySet()) {
	    final List<String> attributeNames = attributesMap.get(deviceName);
	    final DeviceProxy devElement = devicesMap.get(deviceName);
	    try {
		final int rid = devElement.read_attribute_asynch(attributeNames.toArray(new String[attributeNames
			.size()]));
		readAnswersIDs.put(deviceName, rid);
	    } catch (final DevFailed e) {
		final DevError[] errors = e.errors;
		for (final DevError error : errors) {
		    allErrors = (DevError[]) ArrayUtils.add(allErrors, error);
		}
	    }
	}
	if (allErrors.length > 0 && throwExceptions) {
	    throw new DevFailed(allErrors);
	}
    }

    public synchronized DeviceAttribute[] getReadReplies() throws DevFailed {
	if (!readAnswersIDs.isEmpty()) {
	    DevError[] allErrors = new DevError[0];
	    final Map<String, DeviceAttribute> replies = new HashMap<String, DeviceAttribute>();
	    try {
		readReply = new ArrayList<DeviceAttribute>();
		for (final String deviceName : devicesMap.keySet()) {
		    final List<String> attributeNames = attributesMap.get(deviceName);
		    final DeviceProxy devElement = devicesMap.get(deviceName);
		    try {
			final Integer id = readAnswersIDs.get(deviceName);
			if (id != null) {// can be null if read_attribute_asynch has failed (i.e. a device is down)
			    final DeviceAttribute[] subReply = devElement.read_attribute_reply(id, timeout);
			    // memorize the replies per attribute name
			    int i = 0;
			    for (final String attribute : attributeNames) {
				replies.put(deviceName + "/" + attribute, subReply[i++]);
			    }
			}
		    } catch (final DevFailed e) {
			final DevError[] errors = e.errors;
			for (final DevError error : errors) {
			    allErrors = (DevError[]) ArrayUtils.add(allErrors, error);
			}
		    }
		}
	    } finally {
		readAnswersIDs.clear();
	    }

	    if (allErrors.length > 0 && throwExceptions) {
		throw new DevFailed(allErrors);
	    } else {
		// send back all replies in user order
		for (final String userAttributesName : userAttributesNames) {
		    readReply.add(replies.get(userAttributesName));
		}
	    }
	}

	return readReply.toArray(new DeviceAttribute[readReply.size()]);
    }

    public synchronized DeviceAttribute[] read() throws DevFailed {
	readAsync();
	return getReadReplies();
    }

    private void writeAttributesAsync(final DeviceAttribute... values) throws DevFailed {
	if (!writeAnswersIDs.isEmpty()) {
	    writeAnswersIDs.clear();
	}

	// sort DeviceAttribute array per device
	final Map<String, List<DeviceAttribute>> inputs = new HashMap<String, List<DeviceAttribute>>();
	int i = 0;
	for (final DeviceAttribute value : values) {
	    final String deviceName = devices[i++].get_name();
	    List<DeviceAttribute> list = inputs.get(deviceName);
	    if (list == null) {
		list = new ArrayList<DeviceAttribute>();
	    }
	    list.add(value);
	    inputs.put(deviceName, list);
	}
	for (final String deviceName : devicesMap.keySet()) {

	    final DeviceProxy devElement = devicesMap.get(deviceName);
	    final List<DeviceAttribute> list = inputs.get(deviceName);
	    final int answersIDs = devElement.write_attribute_asynch(list.toArray(new DeviceAttribute[list.size()]),
		    false);
	    writeAnswersIDs.put(deviceName, answersIDs);
	}
    }

    public synchronized void getWriteReplies() throws DevFailed {

	if (!writeAnswersIDs.isEmpty()) {
	    final DevError[] allErrors = new DevError[0];
	    try {
		for (final String deviceName : devicesMap.keySet()) {
		    final DeviceProxy devElement = devicesMap.get(deviceName);
		    try {
			devElement.write_attribute_reply(writeAnswersIDs.get(deviceName), timeout);
		    } catch (final DevFailed e) {
			final DevError[] errors = e.errors;
			for (final DevError error : errors) {
			    ArrayUtils.add(allErrors, error);
			}
		    }
		}
	    } finally {
		writeAnswersIDs.clear();
	    }
	    if (allErrors.length > 0) {
		throw new DevFailed(allErrors);
	    }
	}

    }

    /**
     * write a list of values on attributes
     * 
     * @param value
     * @return
     * @throws DevFailed
     */
    public synchronized void writeAsync(final DeviceAttribute... value) throws DevFailed {
	writeAttributesAsync(value);
    }

    /**
     * write a list of values on attributes
     * 
     * @param value
     * @return
     * @throws DevFailed
     */
    public synchronized void writeAsync(final DeviceAttribute value) throws DevFailed {
	writeAttributesAsync(value);
    }

    /**
     * write a list of values on attributes
     * 
     * @param value
     * @return
     * @throws DevFailed
     */
    public synchronized void write(final DeviceAttribute... value) throws DevFailed {
	writeAsync(value);
	getWriteReplies();
    }

    /**
     * Write a value on several attributes.
     * 
     * @param value
     * @return
     * @throws DevFailed
     */

    public synchronized void write(final DeviceAttribute value) throws DevFailed {
	writeAsync(value);
	getWriteReplies();
    }

    public DeviceProxy getDeviceProxy(final String attributeName) {
	return devicesMap.get(attributeName);
    }

    public synchronized String[] getAttributeNames() {
	return Arrays.copyOf(userAttributesNames, userAttributesNames.length);
    }

    public synchronized void setTimeout(final int timeout) {
	this.timeout = timeout;
    }
}
