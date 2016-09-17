package fr.esrf.TangoApi.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.utils.DevFailedUtils;
import org.tango.utils.TangoUtil;

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoDs.NamedDevFailed;
import fr.esrf.TangoDs.NamedDevFailedList;
import fr.soleil.tango.clientapi.factory.ProxyFactory;

/**
 * Tentative to manage group of Attributes.
 *
 * @author ABEILLE
 */
public final class AttributeGroup {

    private final Logger logger = LoggerFactory.getLogger(AttributeGroup.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(AttributeGroup.class);

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

    private final Map<String, DevError[]> errorsMap = new HashMap<String, DevError[]>();

    public AttributeGroup(final String... attributes) throws DevFailed {
        throwExceptions = true;
        add(attributes);
    }

    public boolean isThrowExceptions() {
        return throwExceptions;
    }

    /**
     * No more used. The exception is now set
     *
     * @param throwExceptions
     * @param attributes
     * @throws DevFailed
     */
    public AttributeGroup(final boolean throwExceptions, final String... attributes) throws DevFailed {
        this.throwExceptions = throwExceptions;
        add(attributes);
    }

    public DeviceProxy getDevice(final String attributeName) throws DevFailed {
        return devicesMap.get(TangoUtil.getfullDeviceNameForAttribute(attributeName).toLowerCase(Locale.ENGLISH));
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
            final String deviceName = TangoUtil.getfullDeviceNameForAttribute(attributeName)
                    .toLowerCase(Locale.ENGLISH);
            final String fullAttribute = TangoUtil.getfullAttributeNameForAttribute(attributeName).toLowerCase(
                    Locale.ENGLISH);
            userAttributesNames[i] = fullAttribute;
            try {
                final DeviceProxy device = ProxyFactory.getInstance().createDeviceProxy(deviceName);
                devices[i++] = device;
                if (!devicesMap.containsKey(deviceName)) {
                    devicesMap.put(deviceName, device);
                }
            } catch (final DevFailed e) {
                if (throwExceptions) {
                    throw e;
                } else {
                    devices[i++] = null;
                    if (!devicesMap.containsKey(deviceName)) {
                        devicesMap.put(deviceName, null);
                    }
                }
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

    public synchronized void readAsync() {
        xlogger.entry(devicesMap.keySet());
        if (!readAnswersIDs.isEmpty()) {
            // remove requests to avoid memory leak
            getReadReplies();
        }
        errorsMap.clear();
        for (final String deviceName : devicesMap.keySet()) {
            final List<String> attributeNames = attributesMap.get(deviceName);
            final DeviceProxy devElement = devicesMap.get(deviceName);
            if (devElement != null) {
                try {
                    final int rid = devElement.read_attribute_asynch(attributeNames.toArray(new String[attributeNames
                                                                                                       .size()]));
                    readAnswersIDs.put(deviceName, rid);
                } catch (final DevFailed e) {
                    logger.error("error", e);
                    logger.error(DevFailedUtils.toString(e));

                    for (final String attribute : attributeNames) {
                        errorsMap.put(deviceName + "/" + attribute, e.errors);
                    }
                }
            }
        }
        xlogger.exit();
    }

    public synchronized DeviceAttribute[] getReadReplies() {
        xlogger.entry(devicesMap.keySet());
        if (!readAnswersIDs.isEmpty()) {

            final Map<String, DeviceAttribute> replies = new HashMap<String, DeviceAttribute>();
            try {
                readReply = new ArrayList<DeviceAttribute>();
                for (final String deviceName : devicesMap.keySet()) {

                    final List<String> attributeNames = attributesMap.get(deviceName);
                    // logger.debug("getReadReplies on device {} and attributes {}", deviceName, attributeNames);
                    final DeviceProxy devElement = devicesMap.get(deviceName);
                    if (devElement != null) {
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
                            logger.error("error", e);
                            logger.error(DevFailedUtils.toString(e));

                            for (final String attribute : attributeNames) {
                                errorsMap.put(deviceName + "/" + attribute, e.errors);
                            }
                        }
                    }
                }
            } finally {
                readAnswersIDs.clear();
            }

            // send back all replies in user order
            for (final String userAttributesName : userAttributesNames) {
                readReply.add(replies.get(userAttributesName));
            }
        }
        xlogger.exit();
        return readReply.toArray(new DeviceAttribute[readReply.size()]);
    }

    public synchronized DeviceAttribute[] read() throws DevFailed {
        xlogger.entry();
        readAsync();
        final DeviceAttribute[] replies = getReadReplies();
        if (throwExceptions && !errorsMap.isEmpty()) {
            DevError[] allErrors = null;
            for (final DevError[] errors : errorsMap.values()) {
                for (final DevError error : errors) {
                    allErrors = ArrayUtils.add(allErrors, error);
                }
            }
            throw new DevFailed(allErrors);
        }
        xlogger.exit();
        return replies;
    }

    private void writeAttributesAsync(final DeviceAttribute... values) throws DevFailed {
        errorsMap.clear();
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
            if (value != null) {
                list.add(value);
            }
            inputs.put(deviceName, list);
        }
        for (final String deviceName : devicesMap.keySet()) {
            final DeviceProxy devElement = devicesMap.get(deviceName);
            final List<DeviceAttribute> list = inputs.get(deviceName);
            if (devElement != null) {
                try {
                    final int answersID = devElement.write_attribute_asynch(
                            list.toArray(new DeviceAttribute[list.size()]), false);
                    writeAnswersIDs.put(deviceName, answersID);
                } catch (final DevFailed e) {
                    logger.error("error", e);
                    logger.error(DevFailedUtils.toString(e));
                    final DevError[] errors = e.errors;
                    final List<String> attributeNames = attributesMap.get(deviceName);
                    for (final String attribute : attributeNames) {
                        errorsMap.put(deviceName + "/" + attribute, errors);
                    }
                }
            }
        }
    }

    public synchronized void getWriteReplies() throws DevFailed {

        if (!writeAnswersIDs.isEmpty()) {
            try {
                for (final String deviceName : devicesMap.keySet()) {
                    final DeviceProxy devElement = devicesMap.get(deviceName);
                    if (devElement != null && writeAnswersIDs.get(deviceName) != null) {
                        try {
                            devElement.write_attribute_reply(writeAnswersIDs.get(deviceName), timeout);
                        } catch (final DevFailed e) {
                            logger.error("error", e);
                            logger.error(DevFailedUtils.toString(e));

                            if (e instanceof NamedDevFailedList) {
                                final NamedDevFailedList list = (NamedDevFailedList) e;
                                for (int i = 0; i < list.get_faulty_attr_nb(); i++) {
                                    final NamedDevFailed named = list.elementAt(i);
                                    errorsMap.put(deviceName + "/" + named.name, named.err_stack);
                                }
                            } else {
                                final DevError[] errors = e.errors;
                                final List<String> attributeNames = attributesMap.get(deviceName);
                                for (final String attribute : attributeNames) {
                                    errorsMap.put(deviceName + "/" + attribute, errors);
                                }
                            }
                        }
                    }
                }
            } finally {
                writeAnswersIDs.clear();
            }
            if (!errorsMap.isEmpty()) {
                DevError[] allErrors = null;
                for (final DevError[] errors : errorsMap.values()) {
                    for (final DevError error : errors) {
                        allErrors = ArrayUtils.add(allErrors, error);
                    }
                }
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
    public synchronized void write(final DeviceAttribute... value) throws DevFailed {
        writeAsync(value);
        getWriteReplies();
    }

    public synchronized AttributeInfoEx[] getConfig() throws DevFailed {
        errorsMap.clear();
        final AttributeInfoEx[] result = new AttributeInfoEx[userAttributesNames.length];
        DevError[] allErrors = new DevError[0];
        final Map<String, AttributeInfoEx> replies = new HashMap<String, AttributeInfoEx>();
        for (final String deviceName : devicesMap.keySet()) {
            final List<String> attributeNames = attributesMap.get(deviceName);
            final DeviceProxy devElement = devicesMap.get(deviceName);
            if (devElement != null) {
                try {
                    final AttributeInfoEx[] subReply = devElement.get_attribute_info_ex(attributeNames
                            .toArray(new String[attributeNames.size()]));
                    int i = 0;
                    // order reply per attribute
                    for (final String attribute : attributeNames) {
                        replies.put(deviceName + "/" + attribute, subReply[i++]);
                    }
                } catch (final DevFailed e) {
                    logger.error("error", e);
                    logger.error(DevFailedUtils.toString(e));
                    final DevError[] errors = e.errors;
                    for (final DevError error : errors) {
                        allErrors = ArrayUtils.add(allErrors, error);
                    }
                    for (final String attribute : attributeNames) {
                        errorsMap.put(deviceName + "/" + attribute, errors);
                    }
                }
            }
        }

        if (allErrors.length > 0 && throwExceptions) {
            throw new DevFailed(allErrors);
        } else {
            // send back all replies in user order
            int i = 0;
            for (final String userAttributesName : userAttributesNames) {
                result[i++] = replies.get(userAttributesName);
            }
        }
        return result;
    }

    public DeviceProxy getDeviceProxy(final String attributeName) {
        return devicesMap.get(attributeName.toLowerCase(Locale.ENGLISH));
    }

    public synchronized String[] getAttributeNames() {
        return Arrays.copyOf(userAttributesNames, userAttributesNames.length);
    }

    public synchronized void setTimeout(final int timeout) {
        this.timeout = timeout;
    }

    /**
     * Get last occured error
     *
     * @return a map with the attribute name as a key and its error as value
     */
    public synchronized Map<String, DevError[]> getErrors() {
        return new HashMap<String, DevError[]>(errorsMap);
    }

    public synchronized boolean hasFailed() {
        return !errorsMap.isEmpty();
    }
}
