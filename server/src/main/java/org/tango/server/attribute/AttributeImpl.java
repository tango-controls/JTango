/**
 * Copyright (C) :     2012
 *
 * 	Synchrotron Soleil
 * 	L'Orme des merisiers
 * 	Saint Aubin
 * 	BP48
 * 	91192 GIF-SUR-YVETTE CEDEX
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.attribute;

import fr.esrf.Tango.*;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.attribute.AttributeTangoType;
import org.tango.server.*;
import org.tango.server.cache.PollingUtils;
import org.tango.server.events.EventManager;
import org.tango.server.idl.TangoIDLAttributeUtil;
import org.tango.server.properties.AttributePropertiesManager;
import org.tango.utils.ArrayUtils;
import org.tango.utils.DevFailedUtils;

import java.lang.reflect.Array;

/**
 * Tango attribute
 *
 * @author ABEILLE
 */
public class AttributeImpl extends DeviceBehaviorObject
        implements Comparable<AttributeImpl>, IPollable, IReadableWritable<AttributeValue> {

    private final Logger logger = LoggerFactory.getLogger(AttributeImpl.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(AttributeImpl.class);

    private final String name;
    private final AttributeConfiguration config;
    private final AttributeHistory history;
    private final AttributePropertiesManager attributePropertiesManager;
    private final IAttributeBehavior behavior;
    private final boolean isFwdAttribute;
    private final String deviceName;
    private AttributeValue readValue;
    private AttributeValue writeValue = null;
    private DevFailed lastError;
    private boolean isAlarmToHigh;
    private boolean isOutOfLimits;
    private long writtenTimestamp = 0;
    private boolean isDeltaAlarm;
    private volatile double executionDuration;
    private volatile double lastUpdateTime;
    private volatile double deltaTime;

    public AttributeImpl(final IAttributeBehavior behavior, final String deviceName) throws DevFailed {
        super();
        name = behavior.getConfiguration().getName();
        this.deviceName = deviceName;
        this.attributePropertiesManager = new AttributePropertiesManager(deviceName);
        isFwdAttribute = behavior instanceof ForwardedAttribute;
        config = behavior.getConfiguration();
        this.behavior = behavior;
        history = new AttributeHistory(config.getName(), config.getWritable().equals(AttrWriteType.READ_WRITE),
                config.getTangoType(), config.getFormat());
        isAlarmToHigh = false;
    }

    private Object getMemorizedValue() throws DevFailed {
        final String value = attributePropertiesManager.getAttributePropertyFromDB(getName(),
                Constants.MEMORIZED_VALUE);
        Object obj = null;
        if (value != null && !value.isEmpty() && config.getFormat().equals(AttrDataFormat.SCALAR)) {
            final Transmorph transmorph = new Transmorph(new DefaultConverters());
            try {
                obj = transmorph.convert(value, config.getType());
            } catch (final ConverterException e) {
                throw DevFailedUtils.newDevFailed(e);
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
    @Override
    public void updateValue() throws DevFailed {
        xlogger.entry(getName());
        // invoke on device
        // final Profiler profilerPeriod = new Profiler("read attribute " + name);
        // profilerPeriod.start("invoke");
        if (!config.getWritable().equals(AttrWriteType.READ) && behavior instanceof ISetValueUpdater) {
            // write value is managed by the user
            try {
                final AttributeValue setValue = ((ISetValueUpdater) behavior).getSetValue();
                if (setValue != null) {
                    writeValue = (AttributeValue) ((ISetValueUpdater) behavior).getSetValue().clone();
                    // get as array if necessary (for image)
                    writeValue.setValueWithoutDim(ArrayUtils.from2DArrayToArray(writeValue.getValue()));
                } else {
                    writeValue = null;
                }
            } catch (final CloneNotSupportedException e) {
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        if (config.getWritable().equals(AttrWriteType.WRITE)) {
            if (writeValue == null) {
                readValue = new AttributeValue();
                readValue.setValue(AttributeTangoType.getDefaultValue(config.getType()));
            } else {
                readValue = writeValue;
            }
        } else {
            // attribute with a read part
            final AttributeValue returnedValue = behavior.getValue();
            this.updateValue(returnedValue);
        }

        // profilerPeriod.stop().print();
        xlogger.exit(getName());
    }

    /**
     * set the read value
     *
     * @throws DevFailed
     */
    @Override
    public void updateValue(final AttributeValue inValue) throws DevFailed {
        xlogger.entry(getName());
        // final Profiler profilerPeriod = new Profiler("read attribute " + name);
        // profilerPeriod.start("in");
        if (inValue == null) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_VALUE_NOT_SET,
                    name + " read value has not been updated");
        }

        try {
            // copy value
            readValue = (AttributeValue) inValue.clone();
        } catch (final CloneNotSupportedException e) {
            throw DevFailedUtils.newDevFailed(e);
        }

        // update quality if necessary
        if (readValue.getValue() != null && !readValue.getQuality().equals(AttrQuality.ATTR_INVALID)) {
            updateQuality(readValue);
        }
        // profilerPeriod.start("clone");

        // profilerPeriod.stop().print();
        try {
            if (readValue.getValue() != null) {
                // profilerPeriod.start("checkUpdateErrors");
                checkUpdateErrors(readValue);
                // profilerPeriod.start("from2DArrayToArray");
                // get as array if necessary (for image)
                readValue.setValueWithoutDim(ArrayUtils.from2DArrayToArray(readValue.getValue()));
                // force conversion to check types
                // profilerPeriod.start("toAttributeValue5");
                TangoIDLAttributeUtil.toAttributeValue5(this, readValue, null);
            } else {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_VALUE_NOT_SET,
                        name + " read value has not been updated");
            }
            // profilerPeriod.start("updateDefaultWritePart");
            updateDefaultWritePart();
            // profilerPeriod.stop().print();

        } catch (final DevFailed e) {
            // readValue.setQuality(AttrQuality.ATTR_INVALID);
            readValue.setXDim(0);
            readValue.setYDim(0);
            lastError = e;
            throw e;
        }
        xlogger.exit(getName());
    }

    private void checkUpdateErrors(final AttributeValue returnedValue) throws DevFailed {
        if (config.getFormat().equals(AttrDataFormat.SCALAR) && returnedValue.getXDim() != 1
                && returnedValue.getYDim() != 0) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_OPT_PROP,
                    "Data size for attribute " + name + " exceeds given limit");
        }
        if (!ArrayUtils.checkDimensions(returnedValue.getValue(), returnedValue.getXDim(), returnedValue.getYDim())) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_OPT_PROP,
                    "Data size for attribute " + name + " exceeds given limit");
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
                throw DevFailedUtils.newDevFailed(e);
            }
            writeValue.setValue(AttributeTangoType.getDefaultValue(readValue.getValue().getClass()));
        }
    }

    private void updateQuality(final AttributeValue returnedValue) {
        isOutOfLimits = false;
        isDeltaAlarm = false;
        final AttributePropertiesImpl props = config.getAttributeProperties();
        final boolean isAlarmNotConfigured = props.getMaxAlarm().equals(Constants.NOT_SPECIFIED)
                && props.getMinAlarm().equals(Constants.NOT_SPECIFIED)
                && props.getMaxWarning().equals(Constants.NOT_SPECIFIED)
                && props.getMinWarning().equals(Constants.NOT_SPECIFIED)
                && props.getDeltaT().equals(Constants.NOT_SPECIFIED);
        if (!config.getWritable().equals(AttrWriteType.WRITE) && isNumber() && !isAlarmNotConfigured) {
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
        final String[] readArray = ArrayUtils.toStringArray(returnedValue.getValue());
        String[] writeArray = null;
        if (writtenTimestamp != 0 && deltaTime != 0) {
            writeArray = ArrayUtils.toStringArray(writeValue.getValue());
        }
        if (writeArray != null && readArray.length != writeArray.length && currentDeltaTime > deltaTime) {
            // if size is different for read and write, may be an Delta alarm
            returnedValue.setQuality(AttrQuality.ATTR_ALARM);
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
                        returnedValue.setQuality(AttrQuality.ATTR_ALARM);
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
                    returnedValue.setQuality(AttrQuality.ATTR_ALARM);
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
                    returnedValue.setQuality(AttrQuality.ATTR_WARNING);
                    break;
                }
            }
        }
    }

    private void checkScalarQuality(final AttributeValue returnedValue, final double maxAlarm, final double minAlarm,
            final double maxWarning, final double minWarning, final long deltaTime) {
        final double valRead = Double.parseDouble(returnedValue.getValue().toString());
        checkScalarDeltaAlarm(returnedValue, deltaTime, valRead);
        if (!returnedValue.getQuality().equals(AttrQuality.ATTR_ALARM)) {
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
                returnedValue.setQuality(AttrQuality.ATTR_ALARM);
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
                returnedValue.setQuality(AttrQuality.ATTR_WARNING);
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
                returnedValue.setQuality(AttrQuality.ATTR_ALARM);
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
    @Override
    public void setValue(final AttributeValue value) throws DevFailed {
        if (!config.getWritable().equals(AttrWriteType.READ)) {
            // final Profiler profilerPeriod = new Profiler("write attribute " + name);
            // profilerPeriod.start("check");
            checkSetErrors(value);
            // profilerPeriod.start("clone");
            // copy value for safety and transform it to 2D array if necessary
            try {
                writeValue = (AttributeValue) value.clone();
            } catch (final CloneNotSupportedException e) {
                throw DevFailedUtils.newDevFailed(e);
            }
            // profilerPeriod.start("after clone");
            checkMinMaxValue();
            writtenTimestamp = writeValue.getTime();
            int dimY = writeValue.getYDim();
            if (config.getFormat().equals(AttrDataFormat.IMAGE) && dimY == 0) {
                // force at least 1 to obtain a real 2D array with [][]
                dimY = 1;
            }
            // profilerPeriod.start("convert image");
            value.setValue(ArrayUtils.fromArrayTo2DArray(writeValue.getValue(), writeValue.getXDim(), dimY),
                    writtenTimestamp);
            behavior.setValue(value);
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
            // profilerPeriod.stop().print();
        } else {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_WRITABLE, name + " is not writable");
        }
    }

    private void checkMinMaxValue() throws DevFailed {
        if (isNumber()) {
            final double max = config.getAttributeProperties().getMaxValueDouble();
            final double min = config.getAttributeProperties().getMinValueDouble();
            if (getFormat().equals(AttrDataFormat.SCALAR)) {
                final double val = Double.parseDouble(writeValue.getValue().toString());
                if (val > max || val < min) {
                    throw DevFailedUtils.newDevFailed(ExceptionMessages.WATTR_OUTSIDE_LIMIT,
                            "value is outside allowed range: " + min + "<" + max);
                }
            }
            // XXX removed for performance issue
            // else {
            //
            // final String[] array = ArrayUtils.toStringArray(writeValue.getValue());
            // for (final String element : array) {
            // final double val = Double.parseDouble(element);
            // if (val > max || val < min) {
            // throw DevFailedUtils.newDevFailed(ExceptionMessages.WATTR_OUTSIDE_LIMIT,
            // "value is outside allowed range: " + min + "<" + max);
            // }
            // }
            // }
        }
    }

    private void checkSetErrors(final AttributeValue value) throws DevFailed {
        if (!ArrayUtils.checkDimensions(value.getValue(), value.getXDim(), value.getYDim())) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_INCORRECT_DATA_NUMBER,
                    name + "data size does not correspond to dimensions");
        }
        if (getFormat().equals(AttrDataFormat.SPECTRUM) && value.getXDim() > getMaxX()) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.WATTR_OUTSIDE_LIMIT,
                    "value has a max size of " + getMaxX());
        } else if (getFormat().equals(AttrDataFormat.IMAGE)
                && (value.getXDim() > getMaxX() || value.getYDim() > getMaxY())) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.WATTR_OUTSIDE_LIMIT,
                    name + " value has a max size of " + getMaxX() + "*" + getMaxY());
        }
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

    public boolean isMemorizedAtInit() {
        return config.isMemorizedAtInit();
    }

    public AttributePropertiesImpl getProperties() throws DevFailed {
        if (isFwdAttribute) {
            // retrieve remote attribute properties
            final ForwardedAttribute fwdAttr = (ForwardedAttribute) behavior;
            config.setAttributeProperties(fwdAttr.getProperties());
        }
        return config.getAttributeProperties();
    }

    /**
     * Set the attribute properties.
     *
     * @param properties The attribute properties
     * @throws DevFailed
     */
    public void setProperties(final AttributePropertiesImpl properties) throws DevFailed {
        if (isMemorized() && getMemorizedValue() != null) {
            final double memoValue = Double.parseDouble(getMemorizedValue().toString());
            if (properties.getMaxValueDouble() < memoValue || properties.getMinValueDouble() > memoValue) {
                throw DevFailedUtils.newDevFailed("min or max value not possible for current memorized value");
            }
        }
        config.setAttributeProperties(properties);
        if (isFwdAttribute) {
            // set config on forwarded attribute
            final ForwardedAttribute fwdAttr = (ForwardedAttribute) behavior;
            properties.setRootAttribute(fwdAttr.getRootName());
            fwdAttr.setAttributeConfiguration(config);
        }
        config.persist(deviceName);
        EventManager.getInstance().pushAttributeConfigEvent(deviceName, name);
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

    @Override
    public AttributeValue getWriteValue() {
        return writeValue;
    }

    @Override
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
    }

    public void configureAttributePropFromDb() throws DevFailed {
        config.load(deviceName);
        if (isFwdAttribute) {
            ((ForwardedAttribute) behavior).setLabel(config.getAttributeProperties().getLabel());
        }
    }

    public void removeProperties() throws DevFailed {
        config.clear(deviceName);
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

    public boolean isFwdAttribute() {
        return isFwdAttribute;
    }
}
