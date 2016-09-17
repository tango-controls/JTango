package fr.soleil.tango.clientapi.attribute;

import java.lang.reflect.Array;

import org.tango.attribute.AttributeTangoType;
import org.tango.utils.ArrayUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeProxy;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.soleil.tango.clientapi.util.TypeConversionUtil;

public final class MockAttribute implements ITangoAttribute {
    private final String attributeName;
    private Object mockValue;
    private final Class<?> mockType;
    private long timeStamp;

    public MockAttribute(final String attributeName, final Object mockValue) {
        this.mockValue = mockValue;
        mockType = mockValue.getClass();
        this.attributeName = attributeName;
        timeStamp = System.currentTimeMillis();
    }

    @Override
    public Object extract() throws DevFailed {
        return mockValue;
    }

    @Override
    public Object extractArray() throws DevFailed {
        return mockValue;
    }

    @Override
    public <T> T extract(final Class<T> type) throws DevFailed {
        return TypeConversionUtil.castToType(type, mockValue);
    }

    @Override
    public void insert(final Object value) throws DevFailed {
        mockValue = TypeConversionUtil.castToType(mockType, value);
    }

    @Override
    public void write() throws DevFailed {
    }

    @Override
    public void update() throws DevFailed {
        timeStamp = System.currentTimeMillis();
    }

    @Override
    public void insertImage(final int dimX, final int dimY, final Object values) throws DevFailed {
        mockValue = TypeConversionUtil.castToType(mockType, values);
    }

    @Override
    public <T> Object extractArray(final Class<T> type) throws DevFailed {
        return TypeConversionUtil.castToArray(type, mockValue);
    }

    @Override
    public <T> Object extractWrittenArray(final Class<T> type) throws DevFailed {
        return TypeConversionUtil.castToArray(type, mockValue);
    }

    @Override
    public Object extractWritten() throws DevFailed {
        return mockValue;
    }

    @Override
    public Object extractWrittenArray() throws DevFailed {
        return mockValue;
    }

    @Override
    public <T> T extractWritten(final Class<T> type) throws DevFailed {
        return TypeConversionUtil.castToType(type, mockValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] extractSpecOrImage(final Class<T> type) throws DevFailed {
        return (T[]) TypeConversionUtil.castToArray(type, mockValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] extractWrittenSpecOrImage(final Class<T> type) throws DevFailed {
        return (T[]) TypeConversionUtil.castToArray(type, mockValue);
    }

    @Override
    public String extractToString(final String separator, final String endSeparator) throws DevFailed {
        String str = "";
        if (isScalar()) {
            str = TypeConversionUtil.castToType(String.class, mockValue);
        } else if (isSpectrum()) {
            final String[] s = TypeConversionUtil.castToType(String[].class, mockValue);
            final StringBuffer buff = new StringBuffer();
            for (int i = 0; i < s.length; i++) {
                buff.append(s[i]);
                if (i != s.length - 1) {
                    buff.append(separator);
                }
            }
            str = buff.toString();
        } else if (isImage()) {
            final int dimx = getDimX();
            final int dimy = getDimY();
            final String[] s = TypeConversionUtil.castToType(String[].class, mockValue);
            final StringBuffer buff = new StringBuffer(s.length);
            for (int i = 0; i < dimy; i++) {
                for (int j = 0; j < dimx; j++) {
                    buff.append(s[i * dimx + j]);
                    if (j != dimx - 1) {
                        buff.append(separator);
                    }
                }
                if (i != dimy - 1) {
                    buff.append(endSeparator);
                }
            }
            str = buff.toString();
        }
        return str;
    }

    @Override
    public boolean isNumber() {
        boolean isNumber = false;
        Class<? extends Object> mockClass = mockType;
        while (mockClass.isArray()) {
            mockClass = mockClass.getComponentType();
        }
        if (Number.class.isAssignableFrom(mockClass)) {
            isNumber = true;
        }
        return isNumber;
    }

    @Override
    public boolean isBoolean() {
        boolean isBoolean = false;
        Class<? extends Object> mockClass = mockType;
        while (mockClass.isArray()) {
            mockClass = mockClass.getComponentType();
        }
        if (Boolean.class.isAssignableFrom(mockClass)) {
            isBoolean = true;
        }
        return isBoolean;
    }

    @Override
    public boolean isString() {
        boolean isString = false;
        Class<? extends Object> mockClass = mockType;
        while (mockClass.isArray()) {
            mockClass = mockClass.getComponentType();
        }
        if (String.class.isAssignableFrom(mockClass)) {
            isString = true;
        }
        return isString;
    }

    @Override
    public boolean isWritable() {
        return true;
    }

    @Override
    public boolean isScalar() {
        return !mockValue.getClass().isArray();
    }

    @Override
    public boolean isSpectrum() {
        boolean isSpectrum = false;
        if (mockType.isArray() && !mockType.getComponentType().isArray()) {
            isSpectrum = true;
        }
        return isSpectrum;
    }

    @Override
    public boolean isImage() {
        boolean isImage = false;
        if (mockType.isArray() && mockType.getComponentType().isArray()) {
            isImage = true;
        }
        return isImage;
    }

    @Override
    public AttributeProxy getAttributeProxy() {
        return null;
    }

    @Override
    public DeviceAttribute getDeviceAttribute() {
        return new DeviceAttribute(attributeName);
    }

    @Override
    public int getDimX() throws DevFailed {
        int dimX = 1;
        if (isSpectrum()) {
            dimX = Array.getLength(mockValue);
        } else if (isImage()) {
            dimX = ArrayUtils.get2DArrayXDim(mockValue);
        }
        return dimX;
    }

    @Override
    public int getDimY() throws DevFailed {
        int dimY = 0;
        if (isImage()) {
            dimY = Array.getLength(mockValue);
        }
        return dimY;
    }

    @Override
    public int getWrittenDimX() throws DevFailed {
        return getDimX();
    }

    @Override
    public int getWrittenDimY() throws DevFailed {
        return getDimY();
    }

    @Override
    public int getDataType() throws DevFailed {
        return AttributeTangoType.getTypeFromClass(mockType).getTangoIDLType();
    }

    @Override
    public AttrWriteType getWriteType() {
        return AttrWriteType.READ_WRITE;
    }

    @Override
    public String getDeviceName() throws DevFailed {
        return attributeName;
    }

    @Override
    public long getTimestamp() throws DevFailed {
        return timeStamp;
    }

    @Override
    public AttrDataFormat getDataFormat() {
        AttrDataFormat format = AttrDataFormat.SCALAR;
        if (isSpectrum()) {
            format = AttrDataFormat.SPECTRUM;
        } else if (isImage()) {
            format = AttrDataFormat.IMAGE;
        }
        return format;
    }

    @Override
    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public AttrQuality getQuality() {
        return AttrQuality.ATTR_VALID;
    }

    @Override
    public void setTimeout(final int timeout) throws DevFailed {
    }

}
