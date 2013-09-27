package fr.esrf.TangoApi.Group;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.soleil.tango.clientapi.InsertExtractUtils;
import fr.soleil.tango.clientapi.TangoGroupAttribute;

public class AttributeGroupTaskReader implements Runnable {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static final Logger LOGGER = LoggerFactory.getLogger(AttributeGroupTaskReader.class);

    private boolean readWriteValue = false;
    private boolean readQuality = false;
    private boolean readAttributeInfo = false;
    private final IAttributeGroupTaskListener attributeGroupListener;
    private TangoGroupAttribute attributeGroup = null;

    public AttributeGroupTaskReader(final IAttributeGroupTaskListener listener, TangoGroupAttribute attributeGroup,
            boolean readWriteValue, boolean readQuality, boolean readAttributeInfo) {
        attributeGroupListener = listener;
        this.readWriteValue = readWriteValue;
        this.readQuality = readQuality;
        this.readAttributeInfo = readAttributeInfo;
        setAttributeGroup(attributeGroup);
    }

    public AttributeGroupTaskReader(final IAttributeGroupTaskListener listener, boolean readWriteValue,
            boolean readQuality, boolean readAttributeInfo) {
        this(listener, null, readWriteValue, readQuality, readAttributeInfo);
    }

    public AttributeGroupTaskReader(final IAttributeGroupTaskListener listener) {
        this(listener, false, false, false);
    }

    public void setAttributeGroup(TangoGroupAttribute attributeGroup) {
        this.attributeGroup = attributeGroup;
    }

    @Override
    public void run() {
        valueReader();
    }

    public void valueReader() {
        if ((attributeGroup != null) && (attributeGroupListener != null)) {
            try {
                DeviceAttribute[] resultGroup = null;
                AttributeInfoEx[] attributeInfoExList = null;
                String[] attributeNames = attributeGroup.getGroup().getAttributeNames();

                // read attributes
                try {
                    resultGroup = attributeGroup.read();
                    if (readAttributeInfo) {
                        attributeInfoExList = attributeGroup.getConfig();
                    }
                    attributeGroupListener.updateDeviceAttribute(resultGroup);
                } catch (final DevFailed devFailed) {
                    LOGGER.error("error extract group", devFailed);
                    LOGGER.error(DevFailedUtils.toString(devFailed));
                    if (attributeGroupListener != null) {
                        attributeGroupListener.catchException(devFailed);
                    }
                    return;
                }
                // extract results
                boolean tmpHasFailed = false;
                int i = 0;
                String attrName = null;
                AttributeInfoEx attributeInfo = null;

                for (final DeviceAttribute deviceAttribute : resultGroup) {
                    attrName = attributeNames[i++];
                    attributeInfo = null;
                    if (attributeInfoExList != null) {
                        attributeInfo = attributeInfoExList[i];
                    }
                    try {
                        final double tmpReadValue = InsertExtractUtils.extractRead(deviceAttribute,
                                AttrDataFormat.SCALAR, double.class);
                        attributeGroupListener.updateReadValue(attrName, tmpReadValue);
                        if (attributeInfo != null) {
                            attributeGroupListener.updateAttributeInfoEx(attrName, attributeInfo);
                        }
                        if(readQuality) {
                            attributeGroupListener.updateQuality(attrName, deviceAttribute.getQuality());
                        }
                        if (readWriteValue && (attributeInfo.writable != AttrWriteType.READ)) {
                            final double tmpWriteValue = InsertExtractUtils.extractWrite(deviceAttribute,
                                    AttrDataFormat.SCALAR, attributeInfo.writable, double.class);
                            attributeGroupListener.updateWriteValue(attrName, tmpWriteValue);
                        }

                    } catch (final DevFailed devFailed) {
                        LOGGER.error("error extract group", devFailed);
                        LOGGER.error(DevFailedUtils.toString(devFailed));
                        tmpHasFailed = true;
                        attributeGroupListener.updateQuality(attrName, AttrQuality.ATTR_INVALID);
                        attributeGroupListener.updateErrorMessage(attrName, DATE_FORMAT.format(new Date()) + " : "
                                + DevFailedUtils.toString(devFailed));
                    }
                }
                if (tmpHasFailed) {
                    attributeGroupListener.catchException(new Exception(DATE_FORMAT.format(new Date())
                            + " : One of the attribute hasFailed"));
                } else {
                    attributeGroupListener.readingLoopFinished();
                }
            } catch (final Exception e) {
                attributeGroupListener.catchException(e);
            }
        } else {
            if (attributeGroupListener != null) {
                attributeGroupListener.catchException(new Exception(DATE_FORMAT.format(new Date())
                        + " : Error no group attribute is defined"));
            }
        }
    }
}