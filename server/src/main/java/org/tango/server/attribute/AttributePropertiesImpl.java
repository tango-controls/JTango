/**
 * Copyright (C) :     2012
 * <p>
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
 * <p>
 * This file is part of Tango.
 * <p>
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.attribute;

import fr.esrf.Tango.ArchiveEventProp;
import fr.esrf.Tango.ChangeEventProp;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.EventProperties;
import fr.esrf.Tango.PeriodicEventProp;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.tango.DeviceState;
import org.tango.server.Constants;
import org.tango.server.ExceptionMessages;
import org.tango.server.properties.AttributePropertiesManager;
import org.tango.utils.CaseInsensitiveMap;
import org.tango.utils.DevFailedUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * User class to create attribute properties.
 *
 * @author ABEILLE
 */
public final class AttributePropertiesImpl {

    private final EventProperties eventProp;
    private String label = "";
    private String description = Constants.NO_DESCRIPTION;
    private String unit = Constants.NO_UNIT;
    private String standardUnit = Constants.NO_STD_UNIT;
    private String displayUnit = Constants.NO_DIPLAY_UNIT;
    private String format = Constants.NOT_SPECIFIED;
    private String minValue = Constants.NOT_SPECIFIED;
    private double minValueDouble = -Double.MAX_VALUE;
    private String maxValue = Constants.NOT_SPECIFIED;
    private double maxValueDouble = Double.MAX_VALUE;
    private String minAlarm = Constants.NOT_SPECIFIED;
    private double minAlarmDouble = -Double.MAX_VALUE;
    private String maxAlarm = Constants.NOT_SPECIFIED;
    private double maxAlarmDouble = Double.MAX_VALUE;
    private String writableAttrName = Constants.NONE;
    private String minWarning = Constants.NOT_SPECIFIED;
    private double minWarningDouble = -Double.MAX_VALUE;
    private String maxWarning = Constants.NOT_SPECIFIED;
    private double maxWarningDouble = Double.MAX_VALUE;
    private String deltaT = Constants.NOT_SPECIFIED;
    private long deltaTLong = 0;
    private String deltaVal = Constants.NOT_SPECIFIED;
    private String[] alarmExtensions = new String[0];
    private String[] extensions = new String[0];
    private String[] sysExtensions = new String[0];
    private double deltaValDouble = 0;
    private String[] enumLabels = new String[]{Constants.NOT_SPECIFIED};
    private String rootAttribute = Constants.NOT_SPECIFIED;

    private boolean isEnumMutable = true;

    private boolean isFwdAttribute = false;

    public AttributePropertiesImpl() {
        isEnumMutable = true;
        eventProp = createEmptyEventProperties();
    }

    public AttributePropertiesImpl(final AttributePropertiesImpl props) {
        label = props.label;
        description = props.description;
        unit = props.unit;
        standardUnit = props.standardUnit;
        displayUnit = props.displayUnit;
        format = props.format;
        minValue = props.minValue;
        minValueDouble = props.minValueDouble;
        maxValue = props.maxValue;
        maxValueDouble = props.maxValueDouble;
        minAlarm = props.minAlarm;
        minAlarmDouble = props.minAlarmDouble;
        maxAlarm = props.maxAlarm;
        maxAlarmDouble = props.maxAlarmDouble;
        minWarning = props.minWarning;
        minWarningDouble = props.minWarningDouble;
        maxWarning = props.maxWarning;
        maxWarningDouble = props.maxWarningDouble;
        deltaT = props.deltaT;
        deltaTLong = props.deltaTLong;
        deltaVal = props.deltaVal;
        deltaValDouble = props.deltaValDouble;
        alarmExtensions = Arrays.copyOf(props.alarmExtensions, props.alarmExtensions.length);
        // TODO deep copy
        eventProp = props.eventProp;
        extensions = Arrays.copyOf(props.extensions, props.extensions.length);
        sysExtensions = Arrays.copyOf(props.sysExtensions, props.sysExtensions.length);
        enumLabels = Arrays.copyOf(props.enumLabels, props.enumLabels.length);
        isEnumMutable = props.isEnumMutable;
        isFwdAttribute = props.isFwdAttribute;
        rootAttribute = props.rootAttribute;
        writableAttrName = props.writableAttrName;
    }

    public EventProperties createEmptyEventProperties() {
        final ChangeEventProp change = new ChangeEventProp();
        change.abs_change = Constants.NOT_SPECIFIED;
        change.extensions = new String[0];
        change.rel_change = Constants.NOT_SPECIFIED;
        final PeriodicEventProp per = new PeriodicEventProp();
        per.extensions = new String[0];
        per.period = Constants.PERIOD_1000;
        final ArchiveEventProp arch = new ArchiveEventProp();
        arch.abs_change = Constants.NOT_SPECIFIED;
        arch.extensions = new String[0];
        arch.period = Constants.NOT_SPECIFIED;
        arch.rel_change = Constants.NOT_SPECIFIED;
        return new EventProperties(change, per, arch);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        if (!label.isEmpty()) {
            this.label = label;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        if (description.isEmpty() || description.equalsIgnoreCase(Constants.NOT_SPECIFIED)
                || description.equalsIgnoreCase(Constants.NONE)) {
            this.description = Constants.NO_DESCRIPTION;
        } else {
            this.description = description;
        }
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(final String unit) {
        if (unit.isEmpty() || unit.equalsIgnoreCase(Constants.NOT_SPECIFIED) || unit.equalsIgnoreCase(Constants.NONE)) {
            this.unit = Constants.NO_UNIT;
        } else {
            this.unit = unit;
        }
    }

    public String getStandardUnit() {
        return standardUnit;
    }

    public void setStandardUnit(final String standardUnit) {
        if (standardUnit.isEmpty() || standardUnit.equalsIgnoreCase(Constants.NOT_SPECIFIED)
                || standardUnit.equalsIgnoreCase(Constants.NONE)) {
            this.standardUnit = Constants.NO_STD_UNIT;
        } else {
            this.standardUnit = standardUnit;
        }
    }

    public String getDisplayUnit() {
        return displayUnit;
    }

    public void setDisplayUnit(final String displayUnit) {
        if (displayUnit.isEmpty() || displayUnit.equalsIgnoreCase(Constants.NOT_SPECIFIED)
                || displayUnit.equalsIgnoreCase(Constants.NONE)) {
            this.displayUnit = Constants.NO_DIPLAY_UNIT;
        } else {
            this.displayUnit = displayUnit;
        }
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(final String format) {
        this.format = format;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(final String minValue) {
        if (minValue.isEmpty() || minValue.equalsIgnoreCase(Constants.NAN) || minValue.equalsIgnoreCase(Constants.NONE)) {
            this.minValue = Constants.NOT_SPECIFIED;
            minValueDouble = -Double.MAX_VALUE;
        } else {
            this.minValue = minValue;
            try {
                minValueDouble = Double.valueOf(minValue);
            } catch (final NumberFormatException e) {
            }
        }
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(final String maxValue) {
        if (maxValue.isEmpty() || maxValue.equalsIgnoreCase(Constants.NAN) || maxValue.equalsIgnoreCase(Constants.NONE)) {
            this.maxValue = Constants.NOT_SPECIFIED;
            maxValueDouble = Double.MAX_VALUE;
        } else {
            this.maxValue = maxValue;
            try {
                maxValueDouble = Double.valueOf(maxValue);
            } catch (final NumberFormatException e) {
            }
        }
    }

    public String getMinAlarm() {
        return minAlarm;
    }

    public void setMinAlarm(final String minAlarm) {
        if (minAlarm.isEmpty() || minAlarm.equalsIgnoreCase(Constants.NAN) || minAlarm.equalsIgnoreCase(Constants.NONE)) {
            this.minAlarm = Constants.NOT_SPECIFIED;
            minAlarmDouble = -Double.MAX_VALUE;
        } else {
            this.minAlarm = minAlarm;
            try {
                minAlarmDouble = Double.parseDouble(minAlarm);
            } catch (final NumberFormatException e) {
            }
        }
    }

    public String getMaxAlarm() {
        return maxAlarm;
    }

    public void setMaxAlarm(final String maxAlarm) {
        if (maxAlarm.isEmpty() || maxAlarm.equalsIgnoreCase(Constants.NAN) || maxAlarm.equalsIgnoreCase(Constants.NONE)) {
            this.maxAlarm = Constants.NOT_SPECIFIED;
            maxAlarmDouble = Double.MAX_VALUE;
        } else {
            this.maxAlarm = maxAlarm;
            try {
                maxAlarmDouble = Double.parseDouble(maxAlarm);
            } catch (final NumberFormatException e) {
            }
        }
    }

    public EventProperties getEventProp() {
        return eventProp;
    }

    public void setEventProp(final EventProperties eventProp) {
        setDefaultPeriod(eventProp.per_event.period);
        this.eventProp.ch_event.abs_change = getDefaultValue(eventProp.ch_event.abs_change);
        this.eventProp.ch_event.rel_change = getDefaultValue(eventProp.ch_event.rel_change);
        this.eventProp.arch_event.abs_change = getDefaultValue(eventProp.arch_event.abs_change);
        this.eventProp.arch_event.rel_change = getDefaultValue(eventProp.arch_event.rel_change);
        this.eventProp.arch_event.period = getDefaultValue(eventProp.arch_event.period);
    }

    public void setEventPeriod(final String value) {
        setDefaultPeriod(value);
    }

    public void setEventAbsChange(final String value) {
        eventProp.ch_event.abs_change = getDefaultValue(value);
    }

    public void setEventRelChange(final String value) {
        eventProp.ch_event.rel_change = getDefaultValue(value);
    }

    public void setArchivingEventAbsChange(final String value) {
        eventProp.arch_event.abs_change = getDefaultValue(value);
    }

    public void setArchivingEventRelChange(final String value) {
        eventProp.arch_event.rel_change = getDefaultValue(value);
    }

    public void setArchivingEventPeriod(final String value) {
        eventProp.arch_event.period = getDefaultValue(value);
    }

    private String getDefaultValue(final String field) {
        String result = field;
        if (field.equalsIgnoreCase(Constants.NONE) || field.equalsIgnoreCase(Constants.NAN)) {
            result = Constants.NOT_SPECIFIED;
        }
        return result;
    }

    private void setDefaultPeriod(final String field) {
        if (field.equalsIgnoreCase(Constants.NOT_SPECIFIED) || field.equalsIgnoreCase(Constants.NONE)
                || field.equalsIgnoreCase(Constants.NAN)) {
            eventProp.per_event.period = Constants.PERIOD_1000;
        } else {
            eventProp.per_event.period = field;
        }
    }

    public String[] getExtensions() {
        return Arrays.copyOf(extensions, extensions.length);
    }

    public void setExtensions(final String[] extensions) {
        this.extensions = Arrays.copyOf(extensions, extensions.length);
    }

    public String[] getSysExtensions() {
        return Arrays.copyOf(sysExtensions, sysExtensions.length);
    }

    public void setSysExtensions(final String[] sysExtensions) {
        this.sysExtensions = Arrays.copyOf(sysExtensions, sysExtensions.length);
    }

    public String getWritableAttrName() {
        return writableAttrName;
    }

    public void setWritableAttrName(final String writableAttrName) {
        this.writableAttrName = writableAttrName;
    }

    public String getMinWarning() {
        return minWarning;
    }

    public void setMinWarning(final String minWarning) {
        if (minWarning.equalsIgnoreCase(Constants.NAN) || minWarning.equalsIgnoreCase(Constants.NONE)) {
            this.minWarning = Constants.NOT_SPECIFIED;
            minWarningDouble = -Double.MAX_VALUE;
        } else {
            this.minWarning = minWarning;
            try {
                minWarningDouble = Double.parseDouble(minWarning);
            } catch (final NumberFormatException e) {
            }
        }
    }

    public String getMaxWarning() {
        return maxWarning;
    }

    public void setMaxWarning(final String maxWarning) {
        if (maxWarning.equalsIgnoreCase(Constants.NAN) || maxWarning.equalsIgnoreCase(Constants.NONE)) {
            this.maxWarning = Constants.NOT_SPECIFIED;
            maxWarningDouble = Double.MAX_VALUE;
        } else {
            this.maxWarning = maxWarning;
            try {
                maxWarningDouble = Double.parseDouble(maxWarning);
            } catch (final NumberFormatException e) {
            }
        }
    }

    public String getDeltaT() {
        return deltaT;
    }

    public void setDeltaT(final String deltaT) {
        if (deltaT.equalsIgnoreCase(Constants.NAN) || deltaT.equalsIgnoreCase(Constants.NONE)) {
            this.deltaT = Constants.NOT_SPECIFIED;
            deltaTLong = 0;
        } else {
            this.deltaT = deltaT;
            try {
                deltaTLong = Long.parseLong(deltaT);
            } catch (final NumberFormatException e) {
            }
        }
    }

    public String getDeltaVal() {
        return deltaVal;
    }

    public void setDeltaVal(final String deltaVal) {
        if (deltaVal.equalsIgnoreCase(Constants.NAN) || deltaVal.equalsIgnoreCase(Constants.NONE)) {
            this.deltaVal = Constants.NOT_SPECIFIED;
        } else {
            this.deltaVal = deltaVal;
            try {
                deltaValDouble = Double.parseDouble(deltaVal);
            } catch (final NumberFormatException e) {
            }
        }
    }

    public String[] getAlarmExtensions() {
        return Arrays.copyOf(alarmExtensions, alarmExtensions.length);
    }

    public void setAlarmExtensions(final String[] alarmExtensions) {
        this.alarmExtensions = Arrays.copyOf(alarmExtensions, alarmExtensions.length);
    }

    public double getMinValueDouble() {
        return minValueDouble;
    }

    public double getMaxValueDouble() {
        return maxValueDouble;
    }

    public double getMinAlarmDouble() {
        return minAlarmDouble;
    }

    public double getMaxAlarmDouble() {
        return maxAlarmDouble;
    }

    public double getMinWarningDouble() {
        return minWarningDouble;
    }

    public double getMaxWarningDouble() {
        return maxWarningDouble;
    }

    public String[] getEnumLabels() {
        return enumLabels;
    }

    public void setEnumLabels(final String[] enumLabels) throws DevFailed {
        if (!isEnumMutable) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.NOT_SUPPORTED_FEATURE,
                    "It's not supported to change enumeration labels number from outside the Tango device class code");
        }
        setEnumLabelsPrivate(enumLabels);
    }

    private void setEnumLabelsPrivate(final String[] enumLabels) throws DevFailed {
        if (enumLabels != null && enumLabels.length > 0) {
            if (enumLabels.length == 1) {
                if (enumLabels[0].isEmpty() || enumLabels[0].equalsIgnoreCase(Constants.NAN)
                        || enumLabels[0].equalsIgnoreCase(Constants.NONE)) {
                    enumLabels[0] = Constants.NOT_SPECIFIED;
                }
            }
            // find duplicate values
            final List<String> inputList = Arrays.asList(enumLabels);
            final Set<String> inputSet = new HashSet<String>(inputList);
            if (inputSet.size() < inputList.size()) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_OPT_PROP, "duplicate enum values not allowed");
            }
            this.enumLabels = Arrays.copyOf(enumLabels, enumLabels.length);
            // set min max values
            if (!enumLabels[0].equals(Constants.NOT_SPECIFIED)) {
                setMinValue("0");
                setMaxValue(Integer.toString(enumLabels.length - 1));
            }
        }

    }

    public void setEnumLabels(final String[] enumLabels, final boolean isMutable) throws DevFailed {
        this.isEnumMutable = isMutable;
        setEnumLabelsPrivate(enumLabels);
    }

    @Override
    public String toString() {

        final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
        reflectionToStringBuilder.setExcludeFieldNames(new String[]{"eventProp", "alarmExtensions", "extensions",
                "sysExtensions", "minValueDouble", "maxValueDouble", "minAlarmDouble", "maxAlarmDouble",
                "minWarningDouble", "maxWarningDouble", "writableAttrName", "deltaTLong", "deltaValDouble"});
        return reflectionToStringBuilder.toString();
    }

    public void setDefaultFormat(final Class<?> attributeScalarType) {
        if (String.class.isAssignableFrom(attributeScalarType)) {
            setFormat(Constants.FORMAT_S);
        } else if (double.class.isAssignableFrom(attributeScalarType)
                || float.class.isAssignableFrom(attributeScalarType)) {
            setFormat(Constants.FORMAT_6_2F);
        } else if (boolean.class.isAssignableFrom(attributeScalarType)
                || DeviceState.class.isAssignableFrom(attributeScalarType)
                || DevState.class.isAssignableFrom(attributeScalarType)) {
            setFormat(Constants.NOT_SPECIFIED);
        } else {
            // integer, long, ...
            setFormat(Constants.FORMAT_D);
        }
    }

    public long getDeltaTLong() {
        return deltaTLong;
    }

    public double getDeltaValDouble() {
        return deltaValDouble;
    }

    private boolean compareEventProps(final EventProperties p1, final EventProperties p2) {
        boolean isEqual = true;
        if (!p1.arch_event.abs_change.equals(p2.arch_event.abs_change)) {
            isEqual = false;
        } else if (!p1.arch_event.rel_change.equals(p2.arch_event.rel_change)) {
            isEqual = false;
        } else if (!p1.arch_event.period.equals(p2.arch_event.period)) {
            isEqual = false;
        } else if (!p1.ch_event.abs_change.equals(p2.ch_event.abs_change)) {
            isEqual = false;
        } else if (!p1.ch_event.rel_change.equals(p2.ch_event.rel_change)) {
            isEqual = false;
        } else if (!p1.per_event.period.equals(p2.per_event.period)) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(alarmExtensions);
        result = prime * result + (deltaT == null ? 0 : deltaT.hashCode());
        result = prime * result + (int) (deltaTLong ^ deltaTLong >>> 32);
        result = prime * result + (deltaVal == null ? 0 : deltaVal.hashCode());
        long temp;
        temp = Double.doubleToLongBits(deltaValDouble);
        result = prime * result + (int) (temp ^ temp >>> 32);
        result = prime * result + (description == null ? 0 : description.hashCode());
        result = prime * result + (displayUnit == null ? 0 : displayUnit.hashCode());
        result = prime * result + Arrays.hashCode(enumLabels);
        result = prime * result + (eventProp == null ? 0 : eventProp.hashCode());
        result = prime * result + Arrays.hashCode(extensions);
        result = prime * result + (format == null ? 0 : format.hashCode());
        result = prime * result + (isEnumMutable ? 1231 : 1237);
        result = prime * result + (isFwdAttribute ? 1231 : 1237);
        result = prime * result + (label == null ? 0 : label.hashCode());
        result = prime * result + (maxAlarm == null ? 0 : maxAlarm.hashCode());
        temp = Double.doubleToLongBits(maxAlarmDouble);
        result = prime * result + (int) (temp ^ temp >>> 32);
        result = prime * result + (maxValue == null ? 0 : maxValue.hashCode());
        temp = Double.doubleToLongBits(maxValueDouble);
        result = prime * result + (int) (temp ^ temp >>> 32);
        result = prime * result + (maxWarning == null ? 0 : maxWarning.hashCode());
        temp = Double.doubleToLongBits(maxWarningDouble);
        result = prime * result + (int) (temp ^ temp >>> 32);
        result = prime * result + (minAlarm == null ? 0 : minAlarm.hashCode());
        temp = Double.doubleToLongBits(minAlarmDouble);
        result = prime * result + (int) (temp ^ temp >>> 32);
        result = prime * result + (minValue == null ? 0 : minValue.hashCode());
        temp = Double.doubleToLongBits(minValueDouble);
        result = prime * result + (int) (temp ^ temp >>> 32);
        result = prime * result + (minWarning == null ? 0 : minWarning.hashCode());
        temp = Double.doubleToLongBits(minWarningDouble);
        result = prime * result + (int) (temp ^ temp >>> 32);
        result = prime * result + (rootAttribute == null ? 0 : rootAttribute.hashCode());
        result = prime * result + (standardUnit == null ? 0 : standardUnit.hashCode());
        result = prime * result + Arrays.hashCode(sysExtensions);
        result = prime * result + (unit == null ? 0 : unit.hashCode());
        result = prime * result + (writableAttrName == null ? 0 : writableAttrName.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        boolean isEqual = false;
        if (obj instanceof AttributePropertiesImpl) {
            final String reflectionToStringBuilder = new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .toString();
            final String toCompare = new ReflectionToStringBuilder(obj, ToStringStyle.SHORT_PREFIX_STYLE).toString();
            isEqual = reflectionToStringBuilder.equalsIgnoreCase(toCompare);
            if (isEqual) {
                isEqual = compareEventProps(this.getEventProp(), ((AttributePropertiesImpl) obj).getEventProp());

            }
        }
        return isEqual;
    }

    public boolean isEnumMutable() {
        return isEnumMutable;
    }

    public String getRootAttribute() {
        return rootAttribute;
    }

    public void setRootAttribute(final String rootAttribute) {
        if (rootAttribute != null && !rootAttribute.isEmpty() && !rootAttribute.equals(Constants.NOT_SPECIFIED)) {
            this.rootAttribute = rootAttribute;
            isFwdAttribute = true;
        }
    }

    void persist(final String deviceName, final String attributeName) throws DevFailed {
        final Map<String, String[]> properties = new HashMap<String, String[]>();
        properties.put(Constants.LABEL, new String[]{getLabel()});
        if (!isFwdAttribute) {
            properties.put(Constants.FORMAT, new String[]{getFormat()});
            properties.put(Constants.UNIT, new String[]{getUnit()});
            properties.put(Constants.DISPLAY_UNIT, new String[]{getDisplayUnit()});
            properties.put(Constants.STANDARD_UNIT, new String[]{getStandardUnit()});
            properties.put(Constants.MIN_VAL, new String[]{getMinValue()});
            properties.put(Constants.MAX_VAL, new String[]{getMaxValue()});
            properties.put(Constants.MIN_ALARM, new String[]{getMinAlarm()});
            properties.put(Constants.MAX_ALARM, new String[]{getMaxAlarm()});
            properties.put(Constants.MIN_WARNING, new String[]{getMinWarning()});
            properties.put(Constants.MAX_WARNING, new String[]{getMaxWarning()});
            properties.put(Constants.DELTA_T, new String[]{getDeltaT()});
            properties.put(Constants.DELTA_VAL, new String[]{getDeltaVal()});
            properties.put(Constants.DESC, new String[]{getDescription()});
            properties.put(Constants.ENUM_LABELS, getEnumLabels());

            final EventProperties eventProp = getEventProp();
            properties.put(Constants.EVENT_ARCHIVE_ABS, new String[]{eventProp.arch_event.abs_change});
            properties.put(Constants.EVENT_ARCHIVE_PERIOD, new String[]{eventProp.arch_event.period});
            properties.put(Constants.EVENT_ARCHIVE_REL, new String[]{eventProp.arch_event.rel_change});
            properties.put(Constants.EVENT_CHANGE_ABS, new String[]{eventProp.ch_event.abs_change});
            properties.put(Constants.EVENT_PERIOD, new String[]{eventProp.per_event.period});
            properties.put(Constants.EVENT_CHANGE_REL, new String[]{eventProp.ch_event.rel_change});
        }
        final AttributePropertiesManager attributePropertiesManager = new AttributePropertiesManager(deviceName);
        attributePropertiesManager.setAttributePropertiesInDB(attributeName, properties);
    }

    void load(final String deviceName, final String attributeName) throws DevFailed {
        final AttributePropertiesManager attributePropertiesManager = new AttributePropertiesManager(deviceName);
        final Map<String, String[]> propValues = attributePropertiesManager.getAttributePropertiesFromDB(attributeName);
        // use a second map for attribute props that have one value
        final Map<String, String> propValuesSingle = new CaseInsensitiveMap<String>(propValues.size());
        for (final Entry<String, String[]> entry : propValues.entrySet()) {
            final String[] value = entry.getValue();
            if (value.length == 1) {
                propValuesSingle.put(entry.getKey(), value[0]);
            } else if (value.length == 0) {
                propValuesSingle.put(entry.getKey(), "");
            }
        }
        // if (propValues.containsKey(ROOT_ATTRIBUTE)) {
        // setRootAttribute(propValuesSingle.get(ROOT_ATTRIBUTE));
        // }
        if (propValues.containsKey(Constants.LABEL)) {
            setLabel(propValuesSingle.get(Constants.LABEL));
        }
        if (!isFwdAttribute) {
            if (propValues.containsKey(Constants.FORMAT)) {
                setFormat(propValuesSingle.get(Constants.FORMAT));
            }
            if (propValues.containsKey(Constants.UNIT)) {
                setUnit(propValuesSingle.get(Constants.UNIT));
            }
            if (propValues.containsKey(Constants.DISPLAY_UNIT)) {
                setDisplayUnit(propValuesSingle.get(Constants.DISPLAY_UNIT));
            }
            if (propValues.containsKey(Constants.STANDARD_UNIT)) {
                setStandardUnit(propValuesSingle.get(Constants.STANDARD_UNIT));
            }
            setMinMax(propValuesSingle);
            if (propValues.containsKey(Constants.DESC)) {
                setDescription(propValuesSingle.get(Constants.DESC));
            }
            setEventProperties(propValuesSingle);
            if (propValues.containsKey(Constants.ENUM_LABELS)) {
                setEnumLabels(propValues.get(Constants.ENUM_LABELS));
            }
        }
    }

    public String loadAttributeRootName(final String deviceName, final String attributeName) throws DevFailed {
        final AttributePropertiesManager attributePropertiesManager = new AttributePropertiesManager(deviceName);
        setRootAttribute(attributePropertiesManager.getAttributePropertyFromDB(attributeName, Constants.ROOT_ATTRIBUTE));
        return getRootAttribute();
    }

    public void persistAttributeRootName(final String deviceName, final String attributeName) throws DevFailed {
        new AttributePropertiesManager(deviceName).setAttributePropertyInDB(attributeName, Constants.ROOT_ATTRIBUTE,
                getRootAttribute());
    }

    private void setMinMax(final Map<String, String> propValues) {
        if (propValues.containsKey(Constants.MIN_VAL)) {
            setMinValue(propValues.get(Constants.MIN_VAL));
        }
        if (propValues.containsKey(Constants.MAX_VAL)) {
            setMaxValue(propValues.get(Constants.MAX_VAL));
        }
        if (propValues.containsKey(Constants.MIN_ALARM)) {
            setMinAlarm(propValues.get(Constants.MIN_ALARM));
        }
        if (propValues.containsKey(Constants.MAX_ALARM)) {
            setMaxAlarm(propValues.get(Constants.MAX_ALARM));
        }
        if (propValues.containsKey(Constants.MIN_WARNING)) {
            setMinWarning(propValues.get(Constants.MIN_WARNING));
        }
        if (propValues.containsKey(Constants.MAX_WARNING)) {
            setMaxWarning(propValues.get(Constants.MAX_WARNING));
        }
        if (propValues.containsKey(Constants.DELTA_T)) {
            setDeltaT(propValues.get(Constants.DELTA_T));
        }
        if (propValues.containsKey(Constants.DELTA_VAL)) {
            setDeltaVal(propValues.get(Constants.DELTA_VAL));
        }
    }

    private void setEventProperties(final Map<String, String> propValues) {

        if (propValues.containsKey(Constants.EVENT_ARCHIVE_ABS)) {
            setArchivingEventAbsChange(propValues.get(Constants.EVENT_ARCHIVE_ABS));
        }
        if (propValues.containsKey(Constants.EVENT_ARCHIVE_PERIOD)) {
            setArchivingEventPeriod(propValues.get(Constants.EVENT_ARCHIVE_PERIOD));
        }
        if (propValues.containsKey(Constants.EVENT_ARCHIVE_REL)) {
            setArchivingEventRelChange(propValues.get(Constants.EVENT_ARCHIVE_REL));
        }
        if (propValues.containsKey(Constants.EVENT_CHANGE_ABS)) {
            setEventAbsChange(propValues.get(Constants.EVENT_CHANGE_ABS));
        }
        if (propValues.containsKey(Constants.EVENT_PERIOD)) {
            setEventPeriod(propValues.get(Constants.EVENT_PERIOD));
        }
        if (propValues.containsKey(Constants.EVENT_CHANGE_REL)) {
            setEventRelChange(propValues.get(Constants.EVENT_CHANGE_REL));
        }
    }

    void clear(final String deviceName, final String attributeName) throws DevFailed {
        final AttributePropertiesManager attributePropertiesManager = new AttributePropertiesManager(deviceName);
        attributePropertiesManager.removeAttributeProperties(attributeName);
    }
}
