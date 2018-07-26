package fr.soleil.tango.clientapi;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeProxy;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.tango.clientapi.attribute.ITangoAttribute;
import fr.soleil.tango.clientapi.attribute.MockAttribute;
import fr.soleil.tango.clientapi.attribute.RealAttribute;

/**
 * Manage access to a tango attribute.
 *
 * @author ABEILLE
 */
public final class TangoAttribute {
    private static final String ATTRIBUTE_MUST_BE_SPECTRUM_OR_IMAGE = "attribute must be spectrum or image";
    private static final String LOG_EXTRACTING = "extracting {}";
    private static final String LOG_INSERTING = "inserting {}";
    private final Logger logger = LoggerFactory.getLogger(TangoAttribute.class);
    private static final String TANGO_WRONG_DATA_ERROR = "TANGO_WRONG_DATA_ERROR";
    private static final String THIS_ATTRIBUTE_MUST_BE_A_JAVA_LANG_NUMBER = "this attribute must be a java.lang.Number";

    private final ITangoAttribute attributeImpl;

    /**
     * Build a <b>mock</b> connection to a tango attribute.
     *
     * @param name
     *            the attribute name
     * @param mockValue
     *            The default mock value
     * @throws DevFailed
     */
    public TangoAttribute(final String name, final Object mockValue) throws DevFailed {
        attributeImpl = new MockAttribute(name, mockValue);
    }

    /**
     * Build a <b>mock</b> connection to a tango attribute.
     *
     * @param attribute
     *            The mock attribute behavior. Default behavior may be changed
     *            by using a mock library like http://mockito.org
     * @throws DevFailed
     */
    public TangoAttribute(final ITangoAttribute attribute) throws DevFailed {
        attributeImpl = attribute;
    }

    /**
     * Build a connection to a tango attribute.
     *
     * @param name
     *            the attribute name The full name of the attribute
     */
    public TangoAttribute(final String name) throws DevFailed {
        attributeImpl = new RealAttribute(name);
    }

    public void write() throws DevFailed {
        attributeImpl.write();
    }

    public void write(final Object value) throws DevFailed {
        insert(value);
        this.write();
    }

    public <T> void writeImage(final int dimX, final int dimY, final Object values) throws DevFailed {
        insertImage(dimX, dimY, values);
        this.write();
    }

    /**
     * Get the value on the attribute
     *
     * @throws DevFailed
     */
    public void update() throws DevFailed {
        attributeImpl.update();
    }

    /**
     * Read the tango attribute with SCALAR format and convert it
     *
     * @param <T>
     * @param type
     *            The requested output type
     * @return
     * @throws DevFailed
     */
    public <T> T read(final Class<T> type) throws DevFailed {
        update();
        return extract(type);
    }

    /**
     * @see #extract()
     * @return
     * @throws DevFailed
     */
    public Object read() throws DevFailed {
        update();
        return extract();
    }

    /**
     * Read the tango attribute with SCALAR format, but does not convert it.
     * Does not works for String and Boolean
     *
     * @return
     * @throws DevFailed
     */
    public Number readAsNumber() throws DevFailed {
        update();
        return extractNumber();
    }

    /**
     * Read attribute and return result as array.
     *
     * @param type
     *            The requested output type, is the component type (double,
     *            Double...).
     * @return
     * @throws DevFailed
     */
    public <T> Object readArray(final Class<T> type) throws DevFailed {
        update();
        return extractArray(type);
    }

    public <T> Object readWrittenArray(final Class<T> type) throws DevFailed {
        update();
        return extractWrittenArray(type);
    }

    /**
     * Read written value of attribute
     *
     * @param <T>
     * @param type
     *            The requested output type
     * @return
     * @throws DevFailed
     */
    public <T> T readWritten(final Class<T> type) throws DevFailed {
        update();
        return extractWritten(type);
    }

    /**
     * Read the written part of attribute without any conversion
     *
     * @return
     * @throws DevFailed
     */
    public Object readWritten() throws DevFailed {
        update();
        return extractWritten();
    }

    public Number readWrittenAsNumber() throws DevFailed {
        update();
        return extractNumberWritten();
    }

    /**
     * Read attribute with format SPECTRUM or IMAGE
     *
     * @param <T>
     * @param type
     * @return
     * @throws DevFailed
     */
    public <T> T[] readSpecOrImage(final Class<T> type) throws DevFailed {
        update();
        return extractSpecOrImage(type);
    }

    /**
     * Read the tango attribute with SPECTRUM and IMAGE format, but does not
     * convert it. Does not works for String and Boolean
     *
     * @return
     * @throws DevFailed
     */
    public Number[] readSpecOrImageAsNumber() throws DevFailed {
        update();
        return extractNumberSpecOrImage();
    }

    public <T> T[] readWrittenSpecOrImage(final Class<T> type) throws DevFailed {
        update();
        return extractWrittenSpecOrImage(type);
    }

    public Number[] readWrittenSpecOrImageAsNumber() throws DevFailed {
        update();
        return extractNumberWrittenSpecOrImage();
    }

    /**
     * Read attribute and convert it to a String with separators
     *
     * @param separator
     *            between each value (for SPECTRUM and IMAGE only)
     * @param endSeparator
     *            between each dimension (for IMAGE only)
     * @return The formatted string
     * @throws DevFailed
     */
    public String readAsString(final String separator, final String endSeparator) throws DevFailed {
        update();
        return extractToString(separator, endSeparator);
    }

    /**
     * Insert scalar, spectrum or image. spectrum can be arrays of Objects or
     * primitives. image can be 2D arrays of Objects or primitives
     *
     * @param value
     * @throws DevFailed
     */
    public void insert(final Object value) throws DevFailed {
        logger.debug(LOG_INSERTING, this);
        attributeImpl.insert(value);
    }

    public void insertImage(final int dimX, final int dimY, final Object values) throws DevFailed {
        logger.debug(LOG_INSERTING, this);
        attributeImpl.insertImage(dimX, dimY, values);
    }

    public <T> T extract(final Class<T> type) throws DevFailed {
        logger.debug(LOG_EXTRACTING, this);
        return attributeImpl.extract(type);
    }

    public <T> Object extractArray(final Class<T> type) throws DevFailed {
        logger.debug(LOG_EXTRACTING, this);
        return attributeImpl.extractArray(type);
    }

    public <T> Object extractWrittenArray(final Class<T> type) throws DevFailed {
        logger.debug(LOG_EXTRACTING, this);
        return attributeImpl.extractWrittenArray(type);
    }

    /**
     * Extract value in a Number without conversion
     *
     * @return
     * @throws DevFailed
     */
    public Number extractNumber() throws DevFailed {
        logger.debug(LOG_EXTRACTING, this);
        final Object result = attributeImpl.extract();
        if (!Number.class.isAssignableFrom(result.getClass())) {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, THIS_ATTRIBUTE_MUST_BE_A_JAVA_LANG_NUMBER);
        }
        return (Number) result;
    }

    /**
     * Extract read part without conversion. Spectrum attributes are returned as
     * primitive array. Image attributes are returned as 2D array of primitives
     * (e.g Double[][])
     */
    public Object extract() throws DevFailed {
        logger.debug(LOG_EXTRACTING, this);
        return attributeImpl.extract();
    }

    /**
     * Extract write part without conversion. Spectrum attributes are returned
     * as primitive array. Image attributes are returned as 2D array of
     * primitives (e.g Double[][])
     */
    public Object extractWritten() throws DevFailed {
        logger.debug(LOG_EXTRACTING, this);
        return attributeImpl.extractWritten();
    }

    public <T> T extractWritten(final Class<T> type) throws DevFailed {
        logger.debug(LOG_EXTRACTING, this);
        return attributeImpl.extractWritten(type);
    }

    public Number extractNumberWritten() throws DevFailed {
        logger.debug(LOG_EXTRACTING, this);
        final Object result = attributeImpl.extractWritten();
        if (!Number.class.isAssignableFrom(result.getClass())) {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, THIS_ATTRIBUTE_MUST_BE_A_JAVA_LANG_NUMBER);
        }
        return (Number) result;
    }

    public <T> T[] extractSpecOrImage(final Class<T> type) throws DevFailed {
        logger.debug(LOG_EXTRACTING, this);
        if (attributeImpl.getDataFormat().equals(AttrDataFormat.SCALAR)) {
            throw DevFailedUtils.newDevFailed(ATTRIBUTE_MUST_BE_SPECTRUM_OR_IMAGE);
        }
        return attributeImpl.extractSpecOrImage(type);
    }

    public Number[] extractNumberSpecOrImage() throws DevFailed {
        logger.debug(LOG_EXTRACTING, this);
        if (attributeImpl.getDataFormat().equals(AttrDataFormat.SCALAR)) {
            throw DevFailedUtils.newDevFailed(ATTRIBUTE_MUST_BE_SPECTRUM_OR_IMAGE);
        }
        final Object result = attributeImpl.extractArray();
        if (!Number.class.isAssignableFrom(result.getClass().getComponentType())) {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, THIS_ATTRIBUTE_MUST_BE_A_JAVA_LANG_NUMBER);
        }
        return (Number[]) result;
    }

    public <T> T[] extractWrittenSpecOrImage(final Class<T> type) throws DevFailed {
        logger.debug(LOG_EXTRACTING, this);
        if (attributeImpl.getDataFormat().equals(AttrDataFormat.SCALAR)) {
            throw DevFailedUtils.newDevFailed(ATTRIBUTE_MUST_BE_SPECTRUM_OR_IMAGE);
        }
        return attributeImpl.extractSpecOrImage(type);
    }

    public Number[] extractNumberWrittenSpecOrImage() throws DevFailed {
        logger.debug(LOG_EXTRACTING, this);
        if (attributeImpl.getDataFormat().equals(AttrDataFormat.SCALAR)) {
            throw DevFailedUtils.newDevFailed(ATTRIBUTE_MUST_BE_SPECTRUM_OR_IMAGE);
        }
        final Object result = attributeImpl.extractWrittenArray();
        if (!Number.class.isAssignableFrom(result.getClass().getComponentType())) {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, THIS_ATTRIBUTE_MUST_BE_A_JAVA_LANG_NUMBER);
        }
        return (Number[]) result;
    }

    /**
     * Extract data and format it as followed :\n SCALAR: ex: 1 SPECTRUM: one
     * line separated by separator. \n ex: 1 3 5 IMAGE: x lines and y colums
     * separated by endSeparator.\n ex: 3 5 8 5 6 9
     *
     * @param separator
     * @param endSeparator
     * @return
     * @throws DevFailed
     */
    public String extractToString(final String separator, final String endSeparator) throws DevFailed {
        logger.debug(LOG_EXTRACTING, this);
        return attributeImpl.extractToString(separator, endSeparator);
    }

    public boolean isNumber() {
        return attributeImpl.isNumber();
    }

    public boolean isBoolean() {
        return attributeImpl.isBoolean();
    }

    public boolean isString() {
        return attributeImpl.isString();
    }

    public boolean isWritable() {
        return attributeImpl.isWritable();
    }

    public boolean isScalar() {
        return attributeImpl.isScalar();
    }

    public boolean isSpectrum() {
        return attributeImpl.isSpectrum();
    }

    public boolean isImage() {
        return attributeImpl.isImage();
    }

    public AttributeProxy getAttributeProxy() {
        return attributeImpl.getAttributeProxy();
    }

    public DeviceAttribute getDeviceAttribute() {
        return attributeImpl.getDeviceAttribute();
    }

    public int getDimX() throws DevFailed {
        return attributeImpl.getDimX();
    }

    public int getDimY() throws DevFailed {
        return attributeImpl.getDimY();
    }

    public int getWrittenDimX() throws DevFailed {
        return attributeImpl.getWrittenDimX();
    }

    public int getWrittenDimY() throws DevFailed {
        return attributeImpl.getWrittenDimY();
    }

    public int getDataType() throws DevFailed {
        return attributeImpl.getDataType();
    }

    public AttrWriteType getWriteType() {
        return attributeImpl.getWriteType();
    }

    public long getTimestamp() throws DevFailed {
        return attributeImpl.getTimestamp();
    }

    public AttrQuality getQuality() throws DevFailed {
        return attributeImpl.getQuality();
    }

    public String getDeviceName() throws DevFailed {
        return attributeImpl.getDeviceName();
    }

    public String getName() {
        return attributeImpl.getAttributeName();
    }

    @Override
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this);
        str.append("name", attributeImpl.getAttributeName());
        String typeString = "UNKOWN";
        try {
            typeString = TangoConst.Tango_CmdArgTypeName[attributeImpl.getDataType()];
        } catch (final DevFailed e) {
        }
        final String formatString = TangoConst.Tango_AttrDataFormatName[attributeImpl.getDataFormat().value()];
        str.append("type", typeString);
        str.append("format", formatString);
        str.append("writeType", attributeImpl.getWriteType().value());
        return str.toString();
    }

    public void setTimeout(final int timeout) throws DevFailed {
        attributeImpl.setTimeout(timeout);
    }
}
