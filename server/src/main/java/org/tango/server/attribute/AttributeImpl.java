/**
 * Copyright (C) : 2012
 * 
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
 * 
 * This file is part of Tango.
 * 
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango. If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.attribute;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.attribute.AttributeTangoType;
import org.tango.server.DeviceBehaviorObject;
import org.tango.server.ExceptionMessages;
import org.tango.server.IPollable;
import org.tango.server.PollingUtils;
import org.tango.server.idl.TangoIDLAttributeUtil;
import org.tango.server.properties.AttributePropertiesManager;
import org.tango.server.servant.Constants;
import org.tango.utils.ArrayUtils;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DispLevel;
import fr.esrf.Tango.EventProperties;

/**
 * Tango attribute
 * 
 * @author ABEILLE
 */
public final class AttributeImpl extends DeviceBehaviorObject implements Comparable<AttributeImpl>, IPollable {

    private final Logger logger = LoggerFactory.getLogger(AttributeImpl.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(AttributeImpl.class);

    private final String name;
    private final AttributeConfiguration config;
    // private AttributeValue_4 corbaValue;
    private AttributeValue readValue;
    private AttributeValue writeValue = null;
    private final AttributeHistory history;
    private final AttributePropertiesManager attributePropertiesManager;

    private final IAttributeBehavior behavior;
    private DevFailed lastError;
    private boolean isAlarmToHigh;
    private boolean isOutOfLimits;

    private long writtenTimestamp = 0;
    private boolean isDeltaAlarm;

    private volatile double executionDuration;
    private volatile double lastUpdateTime;
    private volatile double deltaTime;

    public AttributeImpl(final IAttributeBehavior behavior, final AttributePropertiesManager attributePropertiesManager)
            throws DevFailed {
        super();
        config = behavior.getConfiguration();
        name = config.getName();
        this.behavior = behavior;
        this.attributePropertiesManager = attributePropertiesManager;
        history = new AttributeHistory(config.getName(), config.getWritable().equals(AttrWriteType.READ_WRITE),
                getTangoType());
        isAlarmToHigh = false;
    }

    private Object getMemorizedValue() throws DevFailed {
        final String value = attributePropertiesManager
                .getAttributePropertyFromDB(getName(), Constants.MEMORIZED_VALUE);
        Object obj = null;
        if (value != null && !value.isEmpty() && config.getFormat().equals(AttrDataFormat.SCALAR)) {
            final Transmorph transmorph = new Transmorph(new DefaultConverters());
            try {
                obj = transmorph.convert(value, config.getType());
            } catch (final ConverterException e) {
                DevFailedUtils.throwDevFailed(e);
            }
        }
        return obj;
    }

    public void applyMemorizedValue() throws DevFailed {
        if (isMemorized() && !config.getWritable().equals(AttrWriteType.READ)) {
            xlogger.entry(config.getName());

            final Object value = getMemorizedValue();
            if (value != null) {
                final AttributeValue attrValue = new AttributeValue(value, AttrQuality.ATTR_VALID);
                synchronized (this) {
                    if (config.isMemorizedAtInit()) {
                        setValue(attrValue);
                    } else {
                        writeValue = attrValue;
                    }
                }
            }
            // TODO manage performance issues for arrays
            // else {
            // final int dimX = 0;
            // final int dimY = 0;
            // if (getFormat().equals(AttrDataFormat.IMAGE)) {
            // final String[] dims = attributePropertiesManager.getAttributePropertyFromDB(getName(),
            // Constants.MEMORIZED_VALUE_DIM);
            // if (dims != null && dims.length == 2) {
            // dimX = Integer.valueOf(dims[0]);
            // dimY = Integer.valueOf(dims[1]);
            // }
            // }
            // obj = Array.newInstance(type.getAttrClass(), value.length);
            // for (int i = 0; i < value.length; i++) {
            // Array.set(obj, i, MiscellaneousUtils.toObject(value[i], type));
            // }
            // }
            // logger.info("{} is memorized with value {} ", config.getName(),
            // Arrays.toString(value));

            xlogger.exit();
        }
    }

    private String[] getValueAsString() throws DevFailed {
        String[] result;
        if (config.getFormat().equals(AttrDataFormat.SCALAR)) {
            result = new String[1];
            result[0] = writeValue.getValue().toString();
        } else {
            final Object obj = writeValue.getValue();
            final int length = Array.getLength(obj);
            result = new String[length];
            for (int i = 0; i < result.length; i++) {
                result[i] = Array.get(obj, i).toString();
            }
        }
        return result;
    }

    /**
     * read attribute on device
     * 
     * @throws DevFailed
     */
    public void updateValue() throws DevFailed {
        xlogger.entry(getName());
        readValue = new AttributeValue();
        try {
            // invoke on device
            AttributeValue returnedValue;
            try {
                returnedValue = (AttributeValue) behavior.getValue().clone();
            } catch (final CloneNotSupportedException e) {
                throw DevFailedUtils.newDevFailed(e);
            }

            // get as array if necessary (for image)
            if (returnedValue.getValue() != null) {
                checkUpdateErrors(returnedValue);
                readValue.setValue(ArrayUtils.from2DArrayToArray(returnedValue.getValue()));
                // force dim for image
                readValue.setXDim(returnedValue.getXDim());
                readValue.setYDim(returnedValue.getYDim());
                // force conversion to check types
                TangoIDLAttributeUtil.toAttributeValue4(this, readValue, null);
                if (config.getWritable().equals(AttrWriteType.READ_WRITE) && behavior instanceof ISetValueUpdater) {
                    // write value is managed by the user
                    try {
                        final AttributeValue val = (AttributeValue) ((ISetValueUpdater) behavior).getSetValue().clone();
                        writeValue = new AttributeValue();
                        writeValue.setValue(ArrayUtils.from2DArrayToArray(val.getValue()));
                        // force dim for image
                        writeValue.setXDim(val.getXDim());
                        writeValue.setYDim(val.getYDim());
                    } catch (final CloneNotSupportedException e) {
                        throw DevFailedUtils.newDevFailed(e);
                    }
                }
            } else if (config.getWritable().equals(AttrWriteType.WRITE)) {
                if (writeValue == null) {
                    readValue.setValue(AttributeTangoType.getDefaultValue(config.getType()));
                } else {
                    readValue = writeValue;
                }
            } else {
                DevFailedUtils.throwDevFailed(ExceptionMessages.ATTR_VALUE_NOT_SET, "read value has not been updated");
            }
            updateQuality(returnedValue);
            updateDefaultWritePart();
        } catch (final DevFailed e) {
            readValue.setQuality(AttrQuality.ATTR_INVALID);
            readValue.setXDim(0);
            readValue.setYDim(0);
            readValue.setTime(System.currentTimeMillis());
            lastError = e;
            throw e;
        }
        xlogger.exit(getName());
    }

    private void checkUpdateErrors(final AttributeValue returnedValue) throws DevFailed {
        if (config.getFormat().equals(AttrDataFormat.SCALAR) && returnedValue.getXDim() != 1
                && returnedValue.getYDim() != 0) {
            DevFailedUtils.throwDevFailed(ExceptionMessages.ATTR_OPT_PROP,
                    "Data size for attribute " + config.getName() + " exceeds given limit");
        }
        if (!ArrayUtils.checkDimensions(returnedValue.getValue(), returnedValue.getXDim(), returnedValue.getYDim())) {
            DevFailedUtils.throwDevFailed(ExceptionMessages.ATTR_OPT_PROP,
                    "Data size for attribute " + config.getName() + " exceeds given limit");
        }
    }

    @Override
    public String getLastDevFailed() {
        return PollingUtils.toString(lastError);
    }

    private void updateDefaultWritePart() throws DevFailed {
        if (writeValue == null && !config.getWritable().equals(AttrWriteType.READ)) {
            logger.debug("setting default value to write part");
            try {
                writeValue = (AttributeValue) readValue.clone();
            } catch (final CloneNotSupportedException e) {
                DevFailedUtils.throwDevFailed(e);
            }
            writeValue.setValue(AttributeTangoType.getDefaultValue(readValue.getValue().getClass()));
        }
    }

    private void updateQuality(final AttributeValue returnedValue) {
        isOutOfLimits = false;
        isDeltaAlarm = false;
        final AttrQuality currentQuality = returnedValue.getQuality();
        readValue.setQuality(currentQuality);
        final AttributePropertiesImpl props = config.getAttributeProperties();
        final boolean isAlarmNotConfigured = props.getMaxAlarm().equals(AttributePropertiesImpl.NOT_SPECIFIED)
                && props.getMinAlarm().equals(AttributePropertiesImpl.NOT_SPECIFIED)
                && props.getMaxWarning().equals(AttributePropertiesImpl.NOT_SPECIFIED)
                && props.getMinWarning().equals(AttributePropertiesImpl.NOT_SPECIFIED)
                && props.getDeltaT().equals(AttributePropertiesImpl.NOT_SPECIFIED);
        if (currentQuality.equals(AttrQuality.ATTR_VALID) && !config.getWritable().equals(AttrWriteType.WRITE)
                && isNumber() && !isAlarmNotConfigured) {
            final double maxAlarm = props.getMaxAlarmDouble();
            final double minAlarm = props.getMinAlarmDouble();
            final double maxWarning = props.getMaxWarningDouble();
            final double minWarning = props.getMinWarningDouble();
            final long deltaT = props.getDeltaTLong();
            if (config.getFormat().equals(AttrDataFormat.SCALAR)) {
                checkScalarQuality(returnedValue, maxAlarm, minAlarm, maxWarning, minWarning, deltaT);
            } else {
                checkSpectrumQuality(returnedValue, maxAlarm, minAlarm, maxWarning, minWarning, deltaT);
            }
        }
    }

    private void checkSpectrumQuality(final AttributeValue returnedValue, final double maxAlarm, final double minAlarm,
            final double maxWarning, final double minWarning, final long deltaTime) {
        final long currentDeltaTime = returnedValue.getTime() - writtenTimestamp;
        final String[] readArray = ArrayUtils.toStringArray(readValue.getValue());
        String[] writeArray = null;
        if (writtenTimestamp != 0 && deltaTime != 0) {
            writeArray = ArrayUtils.toStringArray(writeValue.getValue());
        }
        if (writeArray != null && readArray.length != writeArray.length && currentDeltaTime > deltaTime) {
            // if size is different for read and write, may be an Delta alarm
            readValue.setQuality(AttrQuality.ATTR_ALARM);
        } else {
            int i = 0;
            for (final String element : readArray) {
                final double readVal = Double.parseDouble(element);
                if (writeArray != null) {
                    // check difference between set value and actual value
                    final double deltaVal = Math.abs(config.getAttributeProperties().getDeltaValDouble());
                    final double valWrite = Double.parseDouble(writeArray[i++]);
                    final double diff = Math.abs(readVal - valWrite);
                    if (currentDeltaTime > deltaTime && diff > deltaVal) {
                        readValue.setQuality(AttrQuality.ATTR_ALARM);
                        isDeltaAlarm = true;
                        logger.debug("{} is delta alarm", getName());
                        break;
                    }
                }
                // alarm
                if (readVal >= maxAlarm || readVal <= minAlarm) {
                    isOutOfLimits = true;
                    if (readVal >= maxAlarm) {
                        isAlarmToHigh = true;
                        logger.debug("{} is too high alarm {}", getName(), maxAlarm);
                    } else {
                        isAlarmToHigh = false;
                        logger.debug("{} is too low alarm {}", getName(), minAlarm);
                    }
                    readValue.setQuality(AttrQuality.ATTR_ALARM);
                    break;
                } else if (readVal >= maxWarning || readVal <= minWarning) {
                    // warning
                    isOutOfLimits = true;
                    if (readVal >= maxWarning) {
                        isAlarmToHigh = true;
                        logger.debug("{} is too high warning {}", getName(), maxWarning);
                    } else {
                        isAlarmToHigh = false;
                        logger.debug("{} is too low warning {}", getName(), minWarning);
                    }
                    readValue.setQuality(AttrQuality.ATTR_WARNING);
                    break;
                }
            }
        }
    }

    private void checkScalarQuality(final AttributeValue returnedValue, final double maxAlarm, final double minAlarm,
            final double maxWarning, final double minWarning, final long deltaTime) {
        final double valRead = Double.parseDouble(readValue.getValue().toString());
        checkScalarDeltaAlarm(returnedValue, deltaTime, valRead);
        if (!readValue.getQuality().equals(AttrQuality.ATTR_ALARM)) {
            if (valRead >= maxAlarm || valRead <= minAlarm) {
                // check min and max alarm props
                isOutOfLimits = true;
                if (valRead >= maxAlarm) {
                    isAlarmToHigh = true;
                    logger.debug("{} is too high alarm {}", getName(), maxAlarm);
                } else {
                    isAlarmToHigh = false;
                    logger.debug("{} is too low alarm {}", getName(), minAlarm);
                }
                readValue.setQuality(AttrQuality.ATTR_ALARM);
            } else if (valRead >= maxWarning || valRead <= minWarning) {
                // check min and max warning props
                isOutOfLimits = true;
                if (valRead >= maxWarning) {
                    isAlarmToHigh = true;
                    logger.debug("{} is too high warning {}", getName(), maxWarning);
                } else {
                    isAlarmToHigh = false;
                    logger.debug("{} is too low warning {} ", getName(), minWarning);
                }
                readValue.setQuality(AttrQuality.ATTR_WARNING);
            }
        }
    }

    private void checkScalarDeltaAlarm(final AttributeValue returnedValue, final long deltaTime, final double valRead) {
        if (writtenTimestamp != 0 && deltaTime != 0) {
            // check difference between set value and actual value

            final double deltaVal = Math.abs(config.getAttributeProperties().getDeltaValDouble());
            final long currentDeltaTime = returnedValue.getTime() - writtenTimestamp;
            final double valWrite = Double.parseDouble(writeValue.getValue().toString());
            final double diff = Math.abs(valRead - valWrite);
            if (currentDeltaTime > deltaTime && diff > deltaVal) {
                readValue.setQuality(AttrQuality.ATTR_ALARM);
                isDeltaAlarm = true;
                logger.debug("{} is delta alarm", getName());
            }
        }
    }

    public boolean isAlarmToHigh() {
        return isAlarmToHigh;
    }

    /**
     * write attribute
     * 
     * @param value
     * @throws DevFailed
     */
    public void setValue(final AttributeValue value) throws DevFailed {
        if (!config.getWritable().equals(AttrWriteType.READ)) {
            checkSetErrors(value);
            // copy value for safety and transform it to 2D array if necessary
            try {
                writeValue = (AttributeValue) value.clone();
            } catch (final CloneNotSupportedException e) {
                DevFailedUtils.throwDevFailed(e);
            }

            checkMinMaxValue();
            writtenTimestamp = writeValue.getTime();
            final AttributeValue value2D = new AttributeValue();
            int dimY = writeValue.getYDim();
            if (config.getFormat().equals(AttrDataFormat.IMAGE) && dimY == 0) {
                // force at least 1 to obtain a real 2D array with [][]
                dimY = 1;
            }
            value2D.setValue(ArrayUtils.fromArrayTo2DArray(writeValue.getValue(), writeValue.getXDim(), dimY));
            value2D.setQuality(value.getQuality());
            // value2D.setTime(value.getTime());
            behavior.setValue(value2D);

            if (isMemorized() && getFormat().equals(AttrDataFormat.SCALAR)) {
                // TODO: refactoring to manage performance issues for spectrum and
                // images
                attributePropertiesManager.setAttributePropertyInDB(getName(), Constants.MEMORIZED_VALUE,
                        getValueAsString()[0]);
                // if (getFormat().equals(AttrDataFormat.IMAGE)) {
                // deviceImpl.setAttributePropertyInDB(att.getName(),
                // Constants.MEMORIZED_VALUE_DIM,
                // Integer.toString(value4.w_dim.dim_x),
                // Integer.toString(value4.w_dim.dim_y));
                // }
            }
        } else {
            DevFailedUtils.throwDevFailed(ExceptionMessages.ATTR_NOT_WRITABLE, config.getName() + " is not writable");
        }
    }

    private void checkMinMaxValue() throws DevFailed {
        if (isNumber()) {
            final double max = config.getAttributeProperties().getMaxValueDouble();
            final double min = config.getAttributeProperties().getMinValueDouble();
            if (getFormat().equals(AttrDataFormat.SCALAR)) {
                final double val = Double.parseDouble(writeValue.getValue().toString());
                if (val > max || val < min) {
                    DevFailedUtils.throwDevFailed(ExceptionMessages.WATTR_OUTSIDE_LIMIT,
                            "value is outside allowed range: " + min + "<" + max);
                }
            } else {
                final String[] array = ArrayUtils.toStringArray(writeValue.getValue());
                for (final String element : array) {
                    final double val = Double.parseDouble(element);
                    if (val > max || val < min) {
                        DevFailedUtils.throwDevFailed(ExceptionMessages.WATTR_OUTSIDE_LIMIT,
                                "value is outside allowed range: " + min + "<" + max);
                    }
                }
            }
        }
    }

    private void checkSetErrors(final AttributeValue value) throws DevFailed {
        if (!ArrayUtils.checkDimensions(value.getValue(), value.getXDim(), value.getYDim())) {
            DevFailedUtils.throwDevFailed(ExceptionMessages.ATTR_INCORRECT_DATA_NUMBER,
                    "data size does not correspond to dimensions");
        }
        if (getFormat().equals(AttrDataFormat.SPECTRUM) && value.getXDim() > getMaxX()) {
            DevFailedUtils
                    .throwDevFailed(ExceptionMessages.WATTR_OUTSIDE_LIMIT, "value has a max size of " + getMaxX());
        } else if (getFormat().equals(AttrDataFormat.IMAGE)
                && (value.getXDim() > getMaxX() || value.getYDim() > getMaxY())) {
            DevFailedUtils.throwDevFailed(ExceptionMessages.WATTR_OUTSIDE_LIMIT, "value has a max size of " + getMaxX()
                    + "*" + getMaxY());
        }
    }

    /**
     * Set the attribute properties.
     * 
     * @param properties
     *            The attribute properties
     * @throws DevFailed
     */
    public void setProperties(final AttributePropertiesImpl properties) throws DevFailed {
        if (isMemorized() && getMemorizedValue() != null) {
            final double memoValue = Double.parseDouble(getMemorizedValue().toString());
            if (properties.getMaxValueDouble() < memoValue || properties.getMinValueDouble() > memoValue) {
                DevFailedUtils.throwDevFailed("min or max value not possible for current memorized value");
            }
        }
        config.setAttributeProperties(properties);
        setAttributePropertiesInDb(properties);
    }

    @Override
    public String getName() {
        return name;
    }

    public AttrDataFormat getFormat() {
        return config.getFormat();
    }

    public AttrWriteType getWritable() {
        return config.getWritable();
    }

    public boolean isMemorized() {
        return config.isMemorized();
    }

    public AttributePropertiesImpl getProperties() {
        return config.getAttributeProperties();
    }

    public DispLevel getDispLevel() {
        return config.getDispLevel();
    }

    public int getMaxX() {
        return config.getMaxX();
    }

    public int getMaxY() {
        return config.getMaxY();
    }

    @Override
    public String toString() {
        final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(this,
                ToStringStyle.MULTI_LINE_STYLE);
        reflectionToStringBuilder.setExcludeFieldNames(new String[] { "readValue", "writeValue", "history", "type" });
        return reflectionToStringBuilder.toString();
    }

    public AttributeValue getWriteValue() {
        return writeValue;
    }

    public AttributeValue getReadValue() {
        return readValue;
    }

    public IAttributeBehavior getBehavior() {
        return behavior;
    }

    public void addToHistory() {
        history.addToHistory(readValue, writeValue, new DevError[0]);
    }

    public void addErrorToHistory(final DevFailed e) throws DevFailed {
        history.addToHistory(readValue, writeValue, e.errors);
    }

    public AttributeHistory getHistory() {
        return history;
    }

    @Override
    public int getPollingPeriod() {
        return config.getPollingPeriod();
    }

    @Override
    public boolean isPolled() {
        return config.isPolled();
    }

    public boolean isCheckArchivingEvent() {
        return config.isCheckArchivingEvent();
    }

    public boolean isCheckChangeEvent() {
        return config.isCheckChangeEvent();
    }

    public boolean isPushArchiveEvent() {
        return config.isPushArchiveEvent();
    }

    public boolean isPushChangeEvent() {
        return config.isPushChangeEvent();
    }

    public boolean isPushDataReady() {
        return config.isPushDataReady();
    }

    public int getTangoType() {
        return config.getTangoType();
    }

    @Override
    public void configurePolling(final int pollingPeriod) throws DevFailed {
        // PollingUtils.configurePolling(pollingPeriod, config, attributePropertiesManager);
        history.clear();
        config.setPolled(true);
        config.setPollingPeriod(Math.abs(pollingPeriod));
    }

    @Override
    public void resetPolling() throws DevFailed {
        config.setPolled(false);
        config.setPollingPeriod(0);
        // PollingUtils.resetPolling(config, attributePropertiesManager);
    }

    // public void updatePollingConfigFromDB() throws DevFailed {
    // PollingUtils.updatePollingConfigFromDB(config, attributePropertiesManager);
    // }

    public void configureAttributePropFromDb() throws DevFailed {
        final AttributePropertiesImpl props = config.getAttributeProperties();
        final Map<String, String> propValues = attributePropertiesManager.getAttributePropertiesFromDB(getName());
        if (propValues.containsKey(AttributePropertiesImpl.LABEL)) {
            props.setLabel(propValues.get(AttributePropertiesImpl.LABEL));
        }
        if (propValues.containsKey(AttributePropertiesImpl.FORMAT)) {
            props.setFormat(propValues.get(AttributePropertiesImpl.FORMAT));
        }
        if (propValues.containsKey(AttributePropertiesImpl.UNIT)) {
            props.setUnit(propValues.get(AttributePropertiesImpl.UNIT));
        }
        if (propValues.containsKey(AttributePropertiesImpl.DISPLAY_UNIT)) {
            props.setDisplayUnit(propValues.get(AttributePropertiesImpl.DISPLAY_UNIT));
        }
        if (propValues.containsKey(AttributePropertiesImpl.STANDARD_UNIT)) {
            props.setStandardUnit(propValues.get(AttributePropertiesImpl.STANDARD_UNIT));
        }
        setMinMax(props, propValues);
        if (propValues.containsKey(AttributePropertiesImpl.DESC)) {
            props.setDescription(propValues.get(AttributePropertiesImpl.DESC));
        }
        setEventProperties(props, propValues);
    }

    private void setMinMax(final AttributePropertiesImpl props, final Map<String, String> propValues) {
        if (propValues.containsKey(AttributePropertiesImpl.MIN_VAL)) {
            props.setMinValue(propValues.get(AttributePropertiesImpl.MIN_VAL));
        }
        if (propValues.containsKey(AttributePropertiesImpl.MAX_VAL)) {
            props.setMaxValue(propValues.get(AttributePropertiesImpl.MAX_VAL));
        }
        if (propValues.containsKey(AttributePropertiesImpl.MIN_ALARM)) {
            props.setMinAlarm(propValues.get(AttributePropertiesImpl.MIN_ALARM));
        }
        if (propValues.containsKey(AttributePropertiesImpl.MAX_ALARM)) {
            props.setMaxAlarm(propValues.get(AttributePropertiesImpl.MAX_ALARM));
        }
        if (propValues.containsKey(AttributePropertiesImpl.MIN_WARNING)) {
            props.setMinWarning(propValues.get(AttributePropertiesImpl.MIN_WARNING));
        }
        if (propValues.containsKey(AttributePropertiesImpl.MAX_WARNING)) {
            props.setMaxWarning(propValues.get(AttributePropertiesImpl.MAX_WARNING));
        }
        if (propValues.containsKey(AttributePropertiesImpl.DELTA_T)) {
            props.setDeltaT(propValues.get(AttributePropertiesImpl.DELTA_T));
        }
        if (propValues.containsKey(AttributePropertiesImpl.DELTA_VAL)) {
            props.setDeltaVal(propValues.get(AttributePropertiesImpl.DELTA_VAL));
        }
    }

    private void setEventProperties(final AttributePropertiesImpl props, final Map<String, String> propValues) {

        if (propValues.containsKey(AttributePropertiesImpl.EVENT_ARCHIVE_ABS)) {
            props.setArchivingEventAbsChange(propValues.get(AttributePropertiesImpl.EVENT_ARCHIVE_ABS));
        }
        if (propValues.containsKey(AttributePropertiesImpl.EVENT_ARCHIVE_PERIOD)) {
            props.setArchivingEventPeriod(propValues.get(AttributePropertiesImpl.EVENT_ARCHIVE_PERIOD));
        }
        if (propValues.containsKey(AttributePropertiesImpl.EVENT_ARCHIVE_REL)) {
            props.setArchivingEventRelChange(propValues.get(AttributePropertiesImpl.EVENT_ARCHIVE_REL));
        }
        if (propValues.containsKey(AttributePropertiesImpl.EVENT_CHANGE_ABS)) {
            props.setEventAbsChange(propValues.get(AttributePropertiesImpl.EVENT_CHANGE_ABS));
        }
        if (propValues.containsKey(AttributePropertiesImpl.EVENT_PERIOD)) {
            props.setEventPeriod(propValues.get(AttributePropertiesImpl.EVENT_PERIOD));
        }
        if (propValues.containsKey(AttributePropertiesImpl.EVENT_CHANGE_REL)) {
            props.setEventRelChange(propValues.get(AttributePropertiesImpl.EVENT_CHANGE_REL));
        }
    }

    private void setAttributePropertiesInDb(final AttributePropertiesImpl props) throws DevFailed {
        final Map<String, String> properties = new HashMap<String, String>();
        properties.put(AttributePropertiesImpl.LABEL, props.getLabel());
        properties.put(AttributePropertiesImpl.FORMAT, props.getFormat());
        properties.put(AttributePropertiesImpl.UNIT, props.getUnit());
        properties.put(AttributePropertiesImpl.DISPLAY_UNIT, props.getDisplayUnit());
        properties.put(AttributePropertiesImpl.STANDARD_UNIT, props.getStandardUnit());
        properties.put(AttributePropertiesImpl.MIN_VAL, props.getMinValue());
        properties.put(AttributePropertiesImpl.MAX_VAL, props.getMaxValue());
        properties.put(AttributePropertiesImpl.MIN_ALARM, props.getMinAlarm());
        properties.put(AttributePropertiesImpl.MAX_ALARM, props.getMaxAlarm());
        properties.put(AttributePropertiesImpl.MIN_WARNING, props.getMinWarning());
        properties.put(AttributePropertiesImpl.MAX_WARNING, props.getMaxWarning());
        properties.put(AttributePropertiesImpl.DELTA_T, props.getDeltaT());
        properties.put(AttributePropertiesImpl.DELTA_VAL, props.getDeltaVal());
        properties.put(AttributePropertiesImpl.DESC, props.getDescription());
        final EventProperties eventProp = props.getEventProp();

        properties.put(AttributePropertiesImpl.EVENT_ARCHIVE_ABS, eventProp.arch_event.abs_change);
        properties.put(AttributePropertiesImpl.EVENT_ARCHIVE_PERIOD, eventProp.arch_event.period);
        properties.put(AttributePropertiesImpl.EVENT_ARCHIVE_REL, eventProp.arch_event.rel_change);
        properties.put(AttributePropertiesImpl.EVENT_CHANGE_ABS, eventProp.ch_event.abs_change);
        properties.put(AttributePropertiesImpl.EVENT_PERIOD, eventProp.per_event.period);
        properties.put(AttributePropertiesImpl.EVENT_CHANGE_REL, eventProp.ch_event.rel_change);

        attributePropertiesManager.setAttributePropertiesInDB(getName(), properties);

    }

    public void removeProperties() throws DevFailed {
        attributePropertiesManager.removeAttributeProperties(getName());
    }

    public boolean isNumber() {
        boolean result = Number.class.isAssignableFrom(config.getScalarType());
        result = result || double.class.isAssignableFrom(config.getScalarType());
        result = result || long.class.isAssignableFrom(config.getScalarType());
        result = result || int.class.isAssignableFrom(config.getScalarType());
        result = result || float.class.isAssignableFrom(config.getScalarType());
        result = result || byte.class.isAssignableFrom(config.getScalarType());
        result = result || short.class.isAssignableFrom(config.getScalarType());
        return result;
    }

    public boolean isDevEncoded() {
        return DevEncoded.class.isAssignableFrom(config.getScalarType());
    }

    public boolean isBoolean() {
        return boolean.class.isAssignableFrom(config.getScalarType());
    }

    public boolean isString() {
        return String.class.isAssignableFrom(config.getScalarType());
    }

    public boolean isState() {
        return DevState.class.isAssignableFrom(config.getScalarType());
    }

    public boolean isScalar() {
        return config.getFormat().equals(AttrDataFormat.SCALAR);
    }

    public boolean isOutOfLimits() {
        return isOutOfLimits;
    }

    @Override
    public int compareTo(final AttributeImpl o) {
        return name.compareTo(o.name);
    }

    public boolean isDeltaAlarm() {
        return isDeltaAlarm;
    }

    @Override
    public int getPollRingDepth() {
        return history.getMaxSize();
    }

    @Override
    public void setPollRingDepth(final int pollRingDepth) {
        history.setMaxSize(pollRingDepth);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AttributeImpl other = (AttributeImpl) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public double getExecutionDuration() {
        return executionDuration;
    }

    @Override
    public double getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public double getDeltaTime() {
        return deltaTime;
    }

    @Override
    public void setPollingStats(final double executionDuration, final double lastUpdateTime, final double deltaTime) {
        this.executionDuration = executionDuration;
        this.lastUpdateTime = lastUpdateTime;
        this.deltaTime = deltaTime;
    }
}
