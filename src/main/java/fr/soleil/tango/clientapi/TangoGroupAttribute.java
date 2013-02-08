package fr.soleil.tango.clientapi;

import java.util.Arrays;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.Group.AttributeGroup;
import fr.soleil.tango.clientapi.factory.ProxyFactory;

public final class TangoGroupAttribute {

    private final AttributeGroup group;
    private final String[] attributeNamesGroup;
    private final DeviceAttribute[] deviceAttributes;

    public TangoGroupAttribute(final String... attributeNames) throws DevFailed {
	attributeNamesGroup = attributeNames;
	group = ProxyFactory.getInstance().createAttributeGroup(true, attributeNames);
	deviceAttributes = group.read();
    }

    /**
     * @param groupName
     * @param throwExceptions
     * @param attributeNames
     * @throws DevFailed
     */
    public TangoGroupAttribute(final boolean throwExceptions, final String... attributeNames) throws DevFailed {
	attributeNamesGroup = attributeNames;
	group = ProxyFactory.getInstance().createAttributeGroup(throwExceptions, attributeNames);
	deviceAttributes = group.read();
    }

    /**
     * Write a value on several attribute
     * 
     * @param value
     *            Can be an array
     * @throws DevFailed
     */
    public void write(final Object value) throws DevFailed {
	for (final DeviceAttribute deviceAttribute : deviceAttributes) {
	    InsertExtractUtils.insert(deviceAttribute, value);
	}
	group.write(deviceAttributes);
    }

    public void writeAysn(final Object value) throws DevFailed {
	for (final DeviceAttribute deviceAttribute : deviceAttributes) {
	    InsertExtractUtils.insert(deviceAttribute, value);
	}
	group.writeAsync(deviceAttributes);
    }

    /**
     * Write a value on several attribute
     * 
     * @param value
     *            Can be an array
     * @throws DevFailed
     */
    public void write(final Object... value) throws DevFailed {
	writeAysn(value);
	getWriteAsyncReplies();
    }

    public void writeAysn(final Object... value) throws DevFailed {
	if (value.length != deviceAttributes.length) {
	    DevFailedUtils.throwDevFailed("WRITE_ERROR", deviceAttributes.length + " values must be provided");
	}
	int i = 0;
	for (final DeviceAttribute deviceAttribute : deviceAttributes) {
	    InsertExtractUtils.insert(deviceAttribute, value[i++]);
	}
	group.writeAsync(deviceAttributes);
    }

    public void getWriteAsyncReplies() throws DevFailed {
	group.getWriteReplies();
    }

    public DeviceAttribute[] read() throws DevFailed {
	return group.read();
    }

    /**
     * Read attributes and extract their values
     * 
     * @return an array of objects. Same size as the number of attributes. Contains READ and WRITE parts
     * @throws DevFailed
     */
    public Object[] readExtract() throws DevFailed {
	final DeviceAttribute[] da = read();
	final Object[] results = extract(da);
	return results;
    }

    public Object[] extract(final DeviceAttribute[] da) throws DevFailed {
	final Object[] results = new Object[da.length];
	for (int i = 0; i < results.length; i++) {
	    results[i] = InsertExtractUtils.extract(da[i]);
	}
	return results;
    }

    /**
     * Launch read asynchronously. Get replies with {@link TangoGroupAttribute#getAsyncReplies()}
     * 
     * @throws DevFailed
     */
    public void readAync() throws DevFailed {
	group.readAsync();
    }

    /**
     * Get replies of last call to {@link TangoGroupAttribute#readAync()}
     * 
     * @return
     * @throws DevFailed
     */
    public DeviceAttribute[] getReadAsyncReplies() throws DevFailed {
	return group.getReadReplies();
    }

    /**
     * @return The attributes configuration @see {@link AttributeInfoEx}
     * @throws DevFailed
     */
    public AttributeInfoEx[] getConfig() throws DevFailed {
	return group.getConfig();
    }

    @Override
    public String toString() {
	final ToStringBuilder str = new ToStringBuilder(this);
	str.append("names", Arrays.toString(attributeNamesGroup));
	return str.toString();
    }

    public AttributeGroup getGroup() {
	return group;
    }
}
