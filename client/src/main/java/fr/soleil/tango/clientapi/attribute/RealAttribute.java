package fr.soleil.tango.clientapi.attribute;

import java.lang.reflect.Array;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.utils.DevFailedUtils;
import org.tango.utils.TangoUtil;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeInfo;
import fr.esrf.TangoApi.AttributeProxy;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.tango.clientapi.InsertExtractUtils;
import fr.soleil.tango.clientapi.Properties;
import fr.soleil.tango.clientapi.factory.ProxyFactory;
import fr.soleil.tango.errorstrategy.RetriableTask;
import fr.soleil.tango.errorstrategy.Task;

public final class RealAttribute implements ITangoAttribute {

    private static final String TANGO_WRONG_DATA_ERROR = "TANGO_WRONG_DATA_ERROR";
    private final Logger logger = LoggerFactory.getLogger(RealAttribute.class);
    private AttributeProxy attributeProxy;

    private DeviceAttribute deviceAttribute = null;
    private int dataType;
    private AttrDataFormat dataFormat;
    private AttrWriteType writeType;
    private final String attributeName;
    private final int delay;
    private final int retries;

    public RealAttribute(final String attributeName) throws DevFailed {
        this.attributeName = attributeName;
        delay = Properties.getDelay();
        retries = Properties.getRetries();
        final Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws DevFailed {
                attributeProxy = ProxyFactory.getInstance().createAttributeProxy(attributeName);
                deviceAttribute = attributeProxy.read();
                final AttributeInfo info = attributeProxy.get_info();
                dataType = info.data_type;
                dataFormat = info.data_format;
                writeType = info.writable;
                return null;
            }
        };

        final RetriableTask<Void> retriable = new RetriableTask<Void>(retries, delay);
        retriable.execute(task);
    }

    @Override
    public Object extract() throws DevFailed {
        return InsertExtractUtils.extractRead(deviceAttribute, dataFormat);
    }

    @Override
    public <T> T extract(final Class<T> type) throws DevFailed {
        return InsertExtractUtils.extractRead(deviceAttribute, dataFormat, type);
    }

    @Override
    public void insert(final Object value) throws DevFailed {
        InsertExtractUtils.insert(deviceAttribute, value);
    }

    @Override
    public void write() throws DevFailed {
        final Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws DevFailed {
                logger.debug("writing on {}", attributeName);
                attributeProxy.write(deviceAttribute);
                logger.debug("writing {} DONE", attributeName);
                return null;
            }
        };
        final RetriableTask<Void> retriable = new RetriableTask<Void>(retries, delay);
        retriable.execute(task);
    }

    @Override
    public void update() throws DevFailed {
        final Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws DevFailed {
                deviceAttribute = attributeProxy.read();
                final AttributeInfo info = attributeProxy.get_info();
                dataType = info.data_type;
                dataFormat = info.data_format;
                writeType = info.writable;
                return null;
            }
        };
        final RetriableTask<Void> retriable = new RetriableTask<Void>(retries, delay);
        retriable.execute(task);
    }

    @Override
    public void insertImage(final int dimX, final int dimY, final Object values) throws DevFailed {
        if (!dataFormat.equals(AttrDataFormat.IMAGE)) {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, "this attribute is spectrum");
        }
        InsertExtractUtils.insert(deviceAttribute, values, dimX, dimY);
    }

    @Override
    public Object extractArray() throws DevFailed {
        return InsertExtractUtils.extractReadArray(deviceAttribute, dataFormat);
    }

    @Override
    public Object extractWrittenArray() throws DevFailed {
        return InsertExtractUtils.extractWriteArray(deviceAttribute, writeType, dataFormat);
    }

    @Override
    public <T> Object extractArray(final Class<T> type) throws DevFailed {
        final Class<?> typeConv = Array.newInstance(type, 0).getClass();
        return InsertExtractUtils.extractReadArray(deviceAttribute, dataFormat, typeConv);
    }

    @Override
    public <T> Object extractWrittenArray(final Class<T> type) throws DevFailed {
        final Class<?> typeConv = Array.newInstance(type, 0).getClass();
        return InsertExtractUtils.extractWriteArray(deviceAttribute, writeType, dataFormat, typeConv);
    }

    @Override
    public Object extractWritten() throws DevFailed {
        return InsertExtractUtils.extractWrite(deviceAttribute, writeType, dataFormat);
    }

    @Override
    public <T> T extractWritten(final Class<T> type) throws DevFailed {
        return InsertExtractUtils.extractWrite(deviceAttribute, dataFormat, writeType, type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] extractSpecOrImage(final Class<T> type) throws DevFailed {
        if (dataFormat.equals(AttrDataFormat.SCALAR)) {
            throw DevFailedUtils.newDevFailed("FORMAT_ERROR", "attribute must be spectrum or image");
        }
        final Class<?> typeConv = Array.newInstance(type, 0).getClass();
        return (T[]) InsertExtractUtils.extractReadArray(deviceAttribute, dataFormat, typeConv);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] extractWrittenSpecOrImage(final Class<T> type) throws DevFailed {
        if (dataFormat.equals(AttrDataFormat.SCALAR)) {
            throw DevFailedUtils.newDevFailed("FORMAT_ERROR", "attribute must be spectrum or image");
        }
        final Class<?> typeConv = Array.newInstance(type, 0).getClass();
        return (T[]) InsertExtractUtils.extractWriteArray(deviceAttribute, writeType, dataFormat, typeConv);
    }

    @Override
    public String extractToString(final String separator, final String endSeparator) throws DevFailed {
        String str = "";
        if (dataFormat.equals(AttrDataFormat.SCALAR)) {
            str = InsertExtractUtils.extractRead(deviceAttribute, dataFormat, String.class);
        } else if (dataFormat.equals(AttrDataFormat.SPECTRUM)) {
            final String[] s = InsertExtractUtils.extractRead(deviceAttribute, dataFormat, String[].class);
            final StringBuffer buff = new StringBuffer();
            for (int i = 0; i < s.length; i++) {
                buff.append(s[i]);
                if (i != s.length - 1) {
                    buff.append(separator);
                }
            }
            str = buff.toString();
        } else if (dataFormat.equals(AttrDataFormat.IMAGE)) {
            final int dimx = deviceAttribute.getDimX();
            final int dimy = deviceAttribute.getDimY();
            final String[] s = InsertExtractUtils.extractRead(deviceAttribute, dataFormat, String[].class);
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
        boolean isNumber = true;
        if (dataType == TangoConst.Tango_DEV_BOOLEAN || dataType == TangoConst.Tango_DEV_STRING) {
            isNumber = false;
        }
        return isNumber;
    }

    @Override
    public boolean isBoolean() {
        boolean isBoolean = false;
        if (dataType == TangoConst.Tango_DEV_BOOLEAN) {
            isBoolean = true;
        }
        return isBoolean;
    }

    @Override
    public boolean isString() {
        boolean isString = false;
        if (dataType == TangoConst.Tango_DEV_STRING) {
            isString = true;
        }
        return isString;
    }

    @Override
    public boolean isWritable() {
        boolean isWritable = false;
        if (!writeType.equals(AttrWriteType.READ)) {
            isWritable = true;
        }
        return isWritable;
    }

    @Override
    public boolean isScalar() {
        return dataFormat.equals(AttrDataFormat.SCALAR);
    }

    @Override
    public boolean isSpectrum() {
        return dataFormat.equals(AttrDataFormat.SPECTRUM);
    }

    @Override
    public boolean isImage() {
        return dataFormat.equals(AttrDataFormat.IMAGE);
    }

    @Override
    public AttributeProxy getAttributeProxy() {
        return attributeProxy;
    }

    @Override
    public DeviceAttribute getDeviceAttribute() {
        return deviceAttribute;
    }

    @Override
    public int getDimX() throws DevFailed {
        return deviceAttribute.getDimX();
    }

    @Override
    public int getDimY() throws DevFailed {
        return deviceAttribute.getDimY();
    }

    @Override
    public int getWrittenDimX() throws DevFailed {
        return deviceAttribute.getWrittenDimX();
    }

    @Override
    public int getWrittenDimY() throws DevFailed {
        return deviceAttribute.getWrittenDimY();
    }

    @Override
    public int getDataType() {
        return dataType;
    }

    @Override
    public AttrWriteType getWriteType() {
        return writeType;
    }

    @Override
    public String getDeviceName() throws DevFailed {
        return TangoUtil.getfullDeviceNameForAttribute(attributeName);
    }

    @Override
    public long getTimestamp() throws DevFailed {
        return deviceAttribute.getTime();
    }

    @Override
    public AttrDataFormat getDataFormat() {
        return dataFormat;
    }

    @Override
    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this);
        str.append("name", getAttributeName());
        final String typeString = TangoConst.Tango_CmdArgTypeName[getDataType()];
        final String formatString = TangoConst.Tango_AttrDataFormatName[getDataFormat().value()];
        str.append("type", typeString);
        str.append("format", formatString);
        str.append("writeType", getWriteType().value());
        return str.toString();
    }

    @Override
    public AttrQuality getQuality() throws DevFailed {
        return deviceAttribute.getQuality();
    }

    @Override
    public void setTimeout(final int timeout) throws DevFailed {
        attributeProxy.set_timeout_millis(timeout);
    }

}
