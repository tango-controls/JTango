package fr.soleil.tango.attributecomposer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.soleil.tango.clientapi.InsertExtractUtils;
import fr.soleil.tango.clientapi.TangoGroupAttribute;

public class AttributeGroupReader implements Runnable {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private final Logger logger = LoggerFactory.getLogger(AttributeGroupReader.class);

    private boolean readWriteValue = false;
    private boolean readQuality = false;
    private boolean readAttributeInfo = false;
    private final IAttributeGroupTaskListener attributeGroupListener;
    private final TangoGroupAttribute attributeGroup;

    public AttributeGroupReader(final IAttributeGroupTaskListener listener, final TangoGroupAttribute attributeGroup,
            final boolean readWriteValue, final boolean readQuality, final boolean readAttributeInfo) {
        attributeGroupListener = listener;
        this.readWriteValue = readWriteValue;
        this.readQuality = readQuality;
        this.readAttributeInfo = readAttributeInfo;
        this.attributeGroup = attributeGroup;
    }

    public AttributeGroupReader(final IAttributeGroupTaskListener listener, final TangoGroupAttribute attributeGroup) {
        this(listener, attributeGroup, false, false, false);
    }

    @Override
    public void run() {
        valueReader();
    }

    public void valueReader() {

        try {
            DeviceAttribute[] resultGroup = null;
            AttributeInfoEx[] attributeInfoExList = null;

            final String[] attributeNames = attributeGroup.getGroup().getAttributeNames();

            try {
                resultGroup = attributeGroup.read();
                attributeGroupListener.updateDeviceAttribute(resultGroup);
            } catch (final DevFailed devFailed) {
                DevFailedUtils.logDevFailed(devFailed, logger);
                logger.error("error extract group", devFailed);
                logger.error(DevFailedUtils.toString(devFailed));
                attributeGroupListener.catchDevFailed(devFailed);
                return;
            }
            boolean hasFailed = false;
            // group is configure to not throw exceptions, so retrieve them
            if (!attributeGroup.isThrowExceptions() && attributeGroup.hasFailed()) {
                hasFailed = true;
                final Map<String, DevError[]> errors = attributeGroup.getErrors();
                for (final Entry<String, DevError[]> entry : errors.entrySet()) {
                    final String attributeName = entry.getKey();
                    final DevFailed e = new DevFailed(entry.getValue());
                    logger.error("error extract group", e);
                    logger.error(DevFailedUtils.toString(e));
                    attributeGroupListener.updateQuality(attributeName, AttrQuality.ATTR_INVALID);
                    attributeGroupListener.updateErrorMessage(attributeName, DATE_FORMAT.format(new Date()) + " : "
                            + DevFailedUtils.toString(e));
                }
            }

            // get attributes' config
            if (readAttributeInfo || readWriteValue) {
                try {
                    attributeInfoExList = attributeGroup.getConfig();
                } catch (final DevFailed devFailed) {
                    DevFailedUtils.logDevFailed(devFailed, logger);
                    logger.error("error read attribute info", devFailed);
                    logger.error(DevFailedUtils.toString(devFailed));
                    attributeGroupListener.catchDevFailed(devFailed);
                }
                // group is configure to not throw exceptions, so retrieve them
                if (!attributeGroup.isThrowExceptions() && attributeGroup.hasFailed()) {
                    final Map<String, DevError[]> errors = attributeGroup.getErrors();
                    for (final Entry<String, DevError[]> entry : errors.entrySet()) {
                        final String attributeName = entry.getKey();
                        final DevFailed e = new DevFailed(entry.getValue());
                        logger.error("error read attribute info", e);
                        logger.error(DevFailedUtils.toString(e));
//                        attributeGroupListener.updateErrorMessage(attributeName, DATE_FORMAT.format(new Date()) + " : "
//                                + DevFailedUtils.toString(e));
                        attributeGroupListener.updateAttributeInfoErrorMessage(attributeName,
                                DATE_FORMAT.format(new Date()) + " : " + DevFailedUtils.toString(e));
                    }
                }
            }

            // extract results
            int i = 0;
            String attrName = null;
            for (final DeviceAttribute deviceAttribute : resultGroup) {
                attrName = attributeNames[i];
                AttributeInfoEx attributeInfo = null;
                if (attributeInfoExList != null && i >= 0 && i < attributeInfoExList.length) {
                    attributeInfo = attributeInfoExList[i];
                }
                if (deviceAttribute != null) {
                    try {
                        final AttrDataFormat format = deviceAttribute.getDataFormat();

                        final Object tmpReadValue = InsertExtractUtils.extractRead(deviceAttribute, format);
                        attributeGroupListener.updateReadValue(attrName, tmpReadValue);

                        if (readAttributeInfo) {
                            if (attributeInfo != null) {
                                attributeGroupListener.updateAttributeInfoEx(attrName, attributeInfo);
                            } else {
                                attributeGroupListener.updateAttributeInfoErrorMessage(attrName,
                                        DATE_FORMAT.format(new Date()) + " : Can't read attribute info of" + attrName);
                            }
                        }

                        if (readQuality) {
                            attributeGroupListener.updateQuality(attrName, deviceAttribute.getQuality());
                        }

                        if (readWriteValue) {
                            try {
                                if (attributeInfo != null && attributeInfo.writable != AttrWriteType.READ) {
                                    final Object tmpWriteValue = InsertExtractUtils.extractWrite(deviceAttribute,
                                            attributeInfo.writable, format);
                                    attributeGroupListener.updateWriteValue(attrName, tmpWriteValue);
                                }
                            } catch (final DevFailed e) {
                                DevFailedUtils.logDevFailed(e, logger);
                                logger.error("error extract write value", e);
                                logger.error(DevFailedUtils.toString(e));
                                attributeGroupListener.updateWriteValueErrorMessage(attrName,
                                        DATE_FORMAT.format(new Date()) + " : " + DevFailedUtils.toString(e));
                            }
                        }

                    } catch (final DevFailed devFailed) {
                        DevFailedUtils.logDevFailed(devFailed, logger);
                        logger.error("error extract group", devFailed);
                        logger.error(DevFailedUtils.toString(devFailed));
                        hasFailed = true;
                        attributeGroupListener.updateQuality(attrName, AttrQuality.ATTR_INVALID);
                        attributeGroupListener.updateErrorMessage(attrName, DATE_FORMAT.format(new Date()) + " : "
                                + DevFailedUtils.toString(devFailed));
                    }

                }
                i++;
            }
            if (hasFailed) {
                attributeGroupListener.catchException(new Exception(DATE_FORMAT.format(new Date())
                        + " : at least, one attribute reading has failed"));
            } else {
                attributeGroupListener.readingLoopFinished();
            }
        } catch (final Exception e) {
            logger.error("error ", e);
            attributeGroupListener.catchException(e);
        }

    }

    boolean isReadWriteValue() {
        return readWriteValue;
    }

    boolean isReadQuality() {
        return readQuality;
    }

    boolean isReadAttributeInfo() {
        return readAttributeInfo;
    }

    IAttributeGroupTaskListener getAttributeGroupListener() {
        return attributeGroupListener;
    }
}