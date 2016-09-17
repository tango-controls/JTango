package fr.soleil.tango.clientapi.attribute;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeProxy;
import fr.esrf.TangoApi.DeviceAttribute;

public interface ITangoAttribute {

    String getAttributeName();

    Object extract() throws DevFailed;

    Object extractArray() throws DevFailed;

    <T> T extract(final Class<T> type) throws DevFailed;

    Object extractWrittenArray() throws DevFailed;

    void insert(final Object value) throws DevFailed;

    <T> Object extractArray(final Class<T> type) throws DevFailed;

    <T> Object extractWrittenArray(final Class<T> type) throws DevFailed;

    Object extractWritten() throws DevFailed;

    <T> T extractWritten(final Class<T> type) throws DevFailed;

    <T> T[] extractSpecOrImage(final Class<T> type) throws DevFailed;

    <T> T[] extractWrittenSpecOrImage(final Class<T> type) throws DevFailed;

    void write() throws DevFailed;

    /**
     * Get the value on the attribute
     *
     * @throws DevFailed
     */
    void update() throws DevFailed;

    void insertImage(final int dimX, final int dimY, final Object values) throws DevFailed;

    /**
     * Extract data and format it as followed :\n SCALAR: ex: 1 SPECTRUM: one line separated by separator. \n ex: 1 3 5
     * IMAGE: x lines and y colums separated by endSeparator.\n ex: 3 5 8 5 6 9
     *
     * @param separator
     * @param endSeparator
     * @return
     * @throws DevFailed
     */
    String extractToString(final String separator, final String endSeparator) throws DevFailed;

    boolean isNumber();

    boolean isBoolean();

    boolean isString();

    boolean isWritable();

    boolean isScalar();

    boolean isSpectrum();

    boolean isImage();

    AttributeProxy getAttributeProxy();

    DeviceAttribute getDeviceAttribute();

    int getDimX() throws DevFailed;

    int getDimY() throws DevFailed;

    int getWrittenDimX() throws DevFailed;

    int getWrittenDimY() throws DevFailed;

    int getDataType() throws DevFailed;

    AttrDataFormat getDataFormat();

    AttrWriteType getWriteType();

    String getDeviceName() throws DevFailed;

    long getTimestamp() throws DevFailed;

    AttrQuality getQuality() throws DevFailed;

    void setTimeout(final int timeout) throws DevFailed;
}
