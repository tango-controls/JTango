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
package org.tango.server.idl;

import java.lang.reflect.Array;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.orb.ORBManager;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttrValUnion;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.AttributeAlarm;
import fr.esrf.Tango.AttributeConfig;
import fr.esrf.Tango.AttributeConfig_2;
import fr.esrf.Tango.AttributeConfig_3;
import fr.esrf.Tango.AttributeConfig_5;
import fr.esrf.Tango.AttributeDim;
import fr.esrf.Tango.AttributeValue_3;
import fr.esrf.Tango.AttributeValue_4;
import fr.esrf.Tango.AttributeValue_5;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;

/**
 * Util to manage insertion or extraction in Tango IDL classes for attributes.
 * 
 * @author ABEILLE
 * 
 */
public final class TangoIDLAttributeUtil {

    private static final XLogger XLOGGER = XLoggerFactory.getXLogger(TangoIDLAttributeUtil.class);

    // private static final Logger LOGGER = LoggerFactory.getLogger(TangoIDLAttributeUtil.class);

    // private static final String TYPE_ERROR = "TYPE_ERROR";

    private TangoIDLAttributeUtil() {

    }

    public static fr.esrf.Tango.AttributeValue toAttributeValue(final AttributeImpl attributeImpl,
            final AttributeValue value) throws DevFailed {
        XLOGGER.entry();
        final fr.esrf.Tango.AttributeValue value2 = new fr.esrf.Tango.AttributeValue();
        try {
            value2.name = attributeImpl.getName();
            value2.value = CleverAnyAttribute.set(attributeImpl.getTangoType(), value.getValue());
            value2.quality = value.getQuality();
            value2.time = TangoIDLUtil.getTime(value.getTime());
            value2.dim_x = value.getXDim();
            value2.dim_y = value.getYDim();
        } catch (final IllegalArgumentException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        XLOGGER.exit();
        return value2;
    }

    public static AttributeValue_3 toAttributeValue3(final AttributeImpl attributeImpl, final AttributeValue read,
            final AttributeValue write) throws DevFailed {
        XLOGGER.entry();
        final AttributeValue_3 value3 = new AttributeValue_3();
        try {
            value3.name = attributeImpl.getName();
            value3.w_dim = new AttributeDim();
            final Object readValue = read.getValue();
            // if attribute has a write value, return it with read value
            if (write != null && write.getValue() != null) {
                final Object writeValue = write.getValue();
                final Object insert = readWriteInArray(readValue, writeValue);
                value3.value = CleverAnyAttribute.set(attributeImpl.getTangoType(), insert);
                value3.w_dim.dim_x = write.getXDim();
                value3.w_dim.dim_y = write.getYDim();
            } else {
                value3.value = CleverAnyAttribute.set(attributeImpl.getTangoType(), read.getValue());
            }
            value3.quality = read.getQuality();
            value3.time = TangoIDLUtil.getTime(read.getTime());
            value3.r_dim = new AttributeDim();
            value3.r_dim.dim_x = read.getXDim();
            value3.r_dim.dim_y = read.getYDim();
            value3.err_list = new DevError[0];
        } catch (final IllegalArgumentException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        XLOGGER.exit();
        return value3;
    }

    public static AttributeValue_3 toAttributeValue3Error(final String name, final DevFailed e) {
        final AttributeValue_3 value3 = new AttributeValue_3();
        value3.name = name;
        try {
            value3.value = ORBManager.createAny();
        } catch (final DevFailed e1) {
            // ignore
        }
        value3.quality = AttrQuality.ATTR_INVALID;
        value3.time = TangoIDLUtil.getTime(System.currentTimeMillis());
        value3.r_dim = new AttributeDim();
        value3.w_dim = new AttributeDim();
        value3.err_list = e.errors;
        return value3;
    }

    public static AttributeValue_5 toAttributeValue5(final AttributeImpl attributeImpl, final AttributeValue read,
            final AttributeValue write) throws DevFailed {
        XLOGGER.entry();
        final AttributeValue_5 value = new AttributeValue_5();
        try {
            final Object readValue = read.getValue();
            value.name = attributeImpl.getName();
            value.data_format = attributeImpl.getFormat();
            value.data_type = attributeImpl.getTangoType();
            value.w_dim = new AttributeDim();
            // if attribute has a write value, return it with read value
            if (attributeImpl.getWritable().equals(AttrWriteType.READ_WRITE) && write != null
                    && write.getValue() != null) {
                final Object writeValue = write.getValue();
                final Object insert = readWriteInArray(readValue, writeValue);
                value.value = CleverAttrValUnion.set(attributeImpl.getTangoType(), insert);
                value.w_dim.dim_x = write.getXDim();
                value.w_dim.dim_y = write.getYDim();
            } else {
                value.value = CleverAttrValUnion.set(attributeImpl.getTangoType(), readValue);
            }
            value.quality = read.getQuality();
            value.time = TangoIDLUtil.getTime(read.getTime());
            value.r_dim = new AttributeDim();
            value.r_dim.dim_x = read.getXDim();
            value.r_dim.dim_y = read.getYDim();
            // LOGGER.debug("get attr dim {} {}", value4.r_dim.dim_x, value4.r_dim.dim_y);
            value.err_list = new DevError[0];
        } catch (final IllegalArgumentException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        XLOGGER.exit();
        return value;
    }

    public static AttributeValue_4 toAttributeValue4(final AttributeImpl attributeImpl, final AttributeValue read,
            final AttributeValue write) throws DevFailed {
        XLOGGER.entry();
        final AttributeValue_4 value4 = new AttributeValue_4();
        try {

            final Object readValue = read.getValue();

            // logger.debug("value {}", readValue);
            value4.name = attributeImpl.getName();
            value4.data_format = attributeImpl.getFormat();
            value4.w_dim = new AttributeDim();
            // if attribute has a write value, return it with read value
            if (attributeImpl.getWritable().equals(AttrWriteType.READ_WRITE) && write != null
                    && write.getValue() != null) {
                final Object writeValue = write.getValue();
                final Object insert = readWriteInArray(readValue, writeValue);
                value4.value = CleverAttrValUnion.set(attributeImpl.getTangoType(), insert);
                value4.w_dim.dim_x = write.getXDim();
                value4.w_dim.dim_y = write.getYDim();
            } else {
                value4.value = CleverAttrValUnion.set(attributeImpl.getTangoType(), readValue);
            }
            value4.quality = read.getQuality();
            value4.time = TangoIDLUtil.getTime(read.getTime());
            value4.r_dim = new AttributeDim();
            value4.r_dim.dim_x = read.getXDim();
            value4.r_dim.dim_y = read.getYDim();
            // LOGGER.debug("get attr dim {} {}", value4.r_dim.dim_x, value4.r_dim.dim_y);
            value4.err_list = new DevError[0];
        } catch (final IllegalArgumentException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        XLOGGER.exit();
        return value4;
    }

    private static Object readWriteInArray(final Object readValue, final Object writeValue) {
        Object insert = null;
        if (readValue.getClass().isArray()) {// spectrum & image
            final int readLength = Array.getLength(readValue);
            final int writeLength = Array.getLength(writeValue);
            insert = Array.newInstance(readValue.getClass().getComponentType(), readLength + writeLength);
            System.arraycopy(readValue, 0, insert, 0, readLength);
            System.arraycopy(writeValue, 0, insert, readLength, writeLength);
        } else { // scalar attribute
            insert = Array.newInstance(readValue.getClass(), 2);
            Array.set(insert, 0, readValue);
            Array.set(insert, 1, writeValue);
        }
        return insert;
    }

    public static AttributeValue_4 toAttributeValue4Error(final String name, final AttrDataFormat format,
            final DevFailed e) {
        final AttributeValue_4 value4 = new AttributeValue_4();
        value4.name = name;
        value4.data_format = format;
        value4.value = new AttrValUnion();
        value4.value.union_no_data(true);
        value4.quality = AttrQuality.ATTR_INVALID;
        value4.time = TangoIDLUtil.getTime(System.currentTimeMillis());
        value4.r_dim = new AttributeDim();
        value4.w_dim = new AttributeDim();
        value4.err_list = e.errors;
        return value4;
    }

    public static AttributeValue_5 toAttributeValue5Error(final String name, final AttrDataFormat format,
            final int dataType, final DevFailed e) {
        final AttributeValue_5 value = new AttributeValue_5();
        value.name = name;
        value.data_format = format;
        value.data_type = dataType;
        value.value = new AttrValUnion();
        value.value.union_no_data(true);
        value.quality = AttrQuality.ATTR_INVALID;
        value.time = TangoIDLUtil.getTime(System.currentTimeMillis());
        value.r_dim = new AttributeDim();
        value.w_dim = new AttributeDim();
        value.err_list = e.errors;
        return value;
    }

    public static AttributeConfig toAttributeConfig(final AttributeImpl attribute) throws DevFailed {
        final AttributePropertiesImpl props = attribute.getProperties();
        return new AttributeConfig(attribute.getName(), attribute.getWritable(), attribute.getFormat(),
                attribute.getTangoType(), attribute.getMaxX(), attribute.getMaxY(), props.getDescription(), attribute
                        .getProperties().getLabel(), props.getUnit(), props.getStandardUnit(), attribute
                        .getProperties().getDisplayUnit(), props.getFormat(), props.getMinValue(), attribute
                        .getProperties().getMaxValue(), props.getMinAlarm(), props.getMaxAlarm(),
                props.getWritableAttrName(), props.getExtensions());

    }

    public static AttributeConfig_2 toAttributeConfig2(final AttributeImpl attribute) throws DevFailed {
        final AttributePropertiesImpl props = attribute.getProperties();
        return new AttributeConfig_2(attribute.getName(), attribute.getWritable(), attribute.getFormat(),
                attribute.getTangoType(), attribute.getMaxX(), attribute.getMaxY(), props.getDescription(), attribute
                        .getProperties().getLabel(), props.getUnit(), props.getStandardUnit(), attribute
                        .getProperties().getDisplayUnit(), props.getFormat(), props.getMinValue(), attribute
                        .getProperties().getMaxValue(), props.getMinAlarm(), props.getMaxAlarm(),
                props.getWritableAttrName(), attribute.getDispLevel(), props.getExtensions());
    }

    public static AttributeConfig_5 toAttributeConfig5(final AttributeImpl attribute) throws DevFailed {

        final AttributePropertiesImpl props = attribute.getProperties();
        final AttributeAlarm alarm = new AttributeAlarm();
        alarm.delta_t = props.getDeltaT();
        alarm.delta_val = props.getDeltaVal();
        alarm.max_alarm = props.getMaxAlarm();
        alarm.max_warning = props.getMaxWarning();
        alarm.min_alarm = props.getMinAlarm();
        alarm.min_warning = props.getMinWarning();
        alarm.extensions = props.getAlarmExtensions();
        return new AttributeConfig_5(attribute.getName(), attribute.getWritable(), attribute.getFormat(),
                attribute.getTangoType(), attribute.isMemorized(), attribute.isMemorizedAtInit(), attribute.getMaxX(),
                attribute.getMaxY(), props.getDescription(), attribute.getProperties().getLabel(), props.getUnit(),
                props.getStandardUnit(), attribute.getProperties().getDisplayUnit(), props.getFormat(),
                props.getMinValue(), attribute.getProperties().getMaxValue(), props.getWritableAttrName(),
                attribute.getDispLevel(), props.getRootAttribute(), props.getEnumLabels(), alarm, props.getEventProp(),
                props.getExtensions(), props.getSysExtensions());
    }

    public static AttributeConfig_5 toAttributeConfig5(final AttributeConfiguration config) throws DevFailed {

        final AttributePropertiesImpl props = config.getAttributeProperties();
        final AttributeAlarm alarm = new AttributeAlarm();
        alarm.delta_t = props.getDeltaT();
        alarm.delta_val = props.getDeltaVal();
        alarm.max_alarm = props.getMaxAlarm();
        alarm.max_warning = props.getMaxWarning();
        alarm.min_alarm = props.getMinAlarm();
        alarm.min_warning = props.getMinWarning();
        alarm.extensions = props.getAlarmExtensions();
        return new AttributeConfig_5(config.getName(), config.getWritable(), config.getFormat(), config.getTangoType(),
                config.isMemorized(), config.isMemorizedAtInit(), config.getMaxX(), config.getMaxY(),
                props.getDescription(), config.getAttributeProperties().getLabel(), props.getUnit(),
                props.getStandardUnit(), config.getAttributeProperties().getDisplayUnit(), props.getFormat(),
                props.getMinValue(), config.getAttributeProperties().getMaxValue(), props.getWritableAttrName(),
                config.getDispLevel(), props.getRootAttribute(), props.getEnumLabels(), alarm, props.getEventProp(),
                props.getExtensions(), props.getSysExtensions());
    }

    public static AttributeConfig_3 toAttributeConfig3(final AttributeImpl attribute) throws DevFailed {

        final AttributePropertiesImpl props = attribute.getProperties();
        final AttributeAlarm alarm = new AttributeAlarm();
        alarm.delta_t = props.getDeltaT();
        alarm.delta_val = props.getDeltaVal();
        alarm.max_alarm = props.getMaxAlarm();
        alarm.max_warning = props.getMaxWarning();
        alarm.min_alarm = props.getMinAlarm();
        alarm.min_warning = props.getMinWarning();
        alarm.extensions = props.getAlarmExtensions();
        return new AttributeConfig_3(attribute.getName(), attribute.getWritable(), attribute.getFormat(),
                attribute.getTangoType(), attribute.getMaxX(), attribute.getMaxY(), props.getDescription(), attribute
                        .getProperties().getLabel(), props.getUnit(), props.getStandardUnit(), attribute
                        .getProperties().getDisplayUnit(), props.getFormat(), props.getMinValue(), attribute
                        .getProperties().getMaxValue(), props.getWritableAttrName(), attribute.getDispLevel(), alarm,
                props.getEventProp(), props.getExtensions(), props.getSysExtensions());
    }

    public static AttributePropertiesImpl toAttributeProperties(final AttributeConfig config) {
        final AttributePropertiesImpl props = new AttributePropertiesImpl();
        props.setDescription(config.description);
        props.setDisplayUnit(config.display_unit);
        props.setExtensions(config.extensions);
        props.setFormat(config.format);
        props.setLabel(config.label);
        props.setMaxAlarm(config.max_alarm);
        props.setMaxValue(config.max_value);
        props.setMinAlarm(config.min_alarm);
        props.setMinValue(config.min_value);
        props.setStandardUnit(config.standard_unit);
        props.setUnit(config.unit);
        return props;
    }

    public static AttributePropertiesImpl toAttributeProperties(final AttributeConfig_2 config2) {
        final AttributePropertiesImpl props = new AttributePropertiesImpl();
        props.setDescription(config2.description);
        props.setDisplayUnit(config2.display_unit);
        props.setExtensions(config2.extensions);
        props.setFormat(config2.format);
        props.setLabel(config2.label);
        props.setMaxAlarm(config2.max_alarm);
        props.setMaxValue(config2.max_value);
        props.setMinAlarm(config2.min_alarm);
        props.setMinValue(config2.min_value);
        props.setStandardUnit(config2.standard_unit);
        props.setUnit(config2.unit);
        props.setWritableAttrName(config2.writable_attr_name);
        return props;
    }

    public static AttributePropertiesImpl toAttributeProperties(final AttributeConfig_3 config) {
        final AttributePropertiesImpl props = new AttributePropertiesImpl();
        props.setAlarmExtensions(config.att_alarm.extensions);
        props.setDeltaT(config.att_alarm.delta_t);
        props.setDeltaVal(config.att_alarm.delta_val);
        props.setDescription(config.description);
        props.setDisplayUnit(config.display_unit);
        props.setEventProp(config.event_prop);
        props.setExtensions(config.extensions);
        props.setFormat(config.format);
        props.setLabel(config.label);
        props.setMaxAlarm(config.att_alarm.max_alarm);
        props.setMaxValue(config.max_value);
        props.setMaxWarning(config.att_alarm.max_warning);
        props.setMinAlarm(config.att_alarm.min_alarm);
        props.setMinValue(config.min_value);
        props.setMinWarning(config.att_alarm.min_warning);
        props.setStandardUnit(config.standard_unit);
        props.setSysExtensions(config.sys_extensions);
        props.setUnit(config.unit);
        props.setWritableAttrName(config.writable_attr_name);
        return props;
    }

    public static AttributePropertiesImpl toAttributeProperties(final AttributeConfig_5 config) throws DevFailed {
        final AttributePropertiesImpl props = new AttributePropertiesImpl();
        props.setAlarmExtensions(config.att_alarm.extensions);
        props.setDeltaT(config.att_alarm.delta_t);
        props.setDeltaVal(config.att_alarm.delta_val);
        props.setDescription(config.description);
        props.setDisplayUnit(config.display_unit);
        props.setEventProp(config.event_prop);
        props.setExtensions(config.extensions);
        props.setFormat(config.format);
        props.setLabel(config.label);
        props.setMaxAlarm(config.att_alarm.max_alarm);
        props.setMaxValue(config.max_value);
        props.setMaxWarning(config.att_alarm.max_warning);
        props.setMinAlarm(config.att_alarm.min_alarm);
        props.setMinValue(config.min_value);
        props.setMinWarning(config.att_alarm.min_warning);
        props.setStandardUnit(config.standard_unit);
        props.setSysExtensions(config.sys_extensions);
        props.setUnit(config.unit);
        props.setWritableAttrName(config.writable_attr_name);
        props.setEnumLabels(config.enum_labels);
        // TODO memorized
        // props.setRootAttribute(config.root_attr_name);
        return props;
    }
}
