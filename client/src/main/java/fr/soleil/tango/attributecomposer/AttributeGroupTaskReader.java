package fr.soleil.tango.attributecomposer;

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
    private final Logger logger = LoggerFactory.getLogger(AttributeGroupTaskReader.class);

    private boolean readWriteValue = false;
    private boolean readQuality = false;
    private boolean readAttributeInfo = false;
    private final IAttributeGroupTaskListener attributeGroupListener;
    private TangoGroupAttribute attributeGroup = null;

    public AttributeGroupTaskReader(final IAttributeGroupTaskListener listener,
            final TangoGroupAttribute attributeGroup, final boolean readWriteValue, final boolean readQuality,
            final boolean readAttributeInfo) {
        attributeGroupListener = listener;
        this.readWriteValue = readWriteValue;
        this.readQuality = readQuality;
        this.readAttributeInfo = readAttributeInfo;
        setAttributeGroup(attributeGroup);
    }

    public AttributeGroupTaskReader(final IAttributeGroupTaskListener listener, final boolean readWriteValue,
            final boolean readQuality, final boolean readAttributeInfo) {
        this(listener, null, readWriteValue, readQuality, readAttributeInfo);
    }

    public AttributeGroupTaskReader(final IAttributeGroupTaskListener listener) {
        this(listener, false, false, false);
    }

    public void setAttributeGroup(final TangoGroupAttribute attributeGroup) {
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
                final String[] attributeNames = attributeGroup.getGroup().getAttributeNames();

                // read attributes
                try {
                    resultGroup = attributeGroup.read();
                    attributeGroupListener.updateDeviceAttribute(resultGroup);
                } catch (final DevFailed devFailed) {
                    logger.error("error extract group", devFailed);
                    logger.error(DevFailedUtils.toString(devFailed));
                    if (attributeGroupListener != null) {
                        attributeGroupListener.catchDevFailed(devFailed);
                    }
                    return;
                }

                if ((readAttributeInfo || readWriteValue) && (attributeGroup != null)) {
                    try {
                        attributeInfoExList = attributeGroup.getConfig();
                    } catch (final DevFailed devFailed) {
                        logger.error("error read attribute info", devFailed);
                        logger.error(DevFailedUtils.toString(devFailed));
                        if (attributeGroupListener != null) {
                            attributeGroupListener.catchDevFailed(devFailed);
                        }
                    }
                }

                // extract results
                boolean tmpHasFailed = false;
                int i = 0;
                String attrName = null;
                for (final DeviceAttribute deviceAttribute : resultGroup) {
                    attrName = attributeNames[i];
                    AttributeInfoEx attributeInfo = null;
                    if ((attributeInfoExList != null) && (i >= 0) && (i < attributeInfoExList.length)) {
                        attributeInfo = attributeInfoExList[i];
                    }
                    try {
                        if (deviceAttribute != null) {
                            final AttrDataFormat format = deviceAttribute.getDataFormat();
                            final Object tmpReadValue = InsertExtractUtils.extractRead(deviceAttribute, format);

                            attributeGroupListener.updateReadValue(attrName, tmpReadValue);

                            if (readAttributeInfo) {
                                if (attributeInfo != null) {
                                    attributeGroupListener.updateAttributeInfoEx(attrName, attributeInfo);
                                } else {
                                    attributeGroupListener.updateAttributeInfoErrorMessage(attrName,
                                            DATE_FORMAT.format(new Date())
                                            + " : Can't read attribute info of" + attrName);
                                }
                            }

                            if (readQuality) {
                                attributeGroupListener.updateQuality(attrName, deviceAttribute.getQuality());
                            }

                            if (readWriteValue) {
                                try {
                                    if ((attributeInfo != null) && (attributeInfo.writable != AttrWriteType.READ)) {
                                        final Object tmpWriteValue = InsertExtractUtils.extractWrite(deviceAttribute,
                                                attributeInfo.writable, format);
                                        attributeGroupListener.updateWriteValue(attrName, tmpWriteValue);
                                    }
                                } catch (final DevFailed e) {
                                    logger.error("error extract write value", e);
                                    logger.error(DevFailedUtils.toString(e));
                                    attributeGroupListener.updateWriteValueErrorMessage(attrName,
                                            DATE_FORMAT.format(new Date()) + " : " + DevFailedUtils.toString(e));
                                }
                            }
                        } else {
                            logger.error("error extract group, device attribute for {} is null", attrName);
                            attributeGroupListener.updateQuality(attrName, AttrQuality.ATTR_INVALID);
                            attributeGroupListener.updateErrorMessage(attrName, DATE_FORMAT.format(new Date()) + " : "
                                    + "cannot read " + attrName);
                            tmpHasFailed = true;
                        }

                    } catch (final DevFailed devFailed) {
                        logger.error("error extract group", devFailed);
                        logger.error(DevFailedUtils.toString(devFailed));
                        tmpHasFailed = true;
                        attributeGroupListener.updateQuality(attrName, AttrQuality.ATTR_INVALID);
                        attributeGroupListener.updateErrorMessage(attrName, DATE_FORMAT.format(new Date()) + " : "
                                + DevFailedUtils.toString(devFailed));
                    }
                    i++;
                }
                if (tmpHasFailed) {
                    attributeGroupListener.catchException(new Exception(DATE_FORMAT.format(new Date())
                            + " : at least, one attribute reading has failed"));
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