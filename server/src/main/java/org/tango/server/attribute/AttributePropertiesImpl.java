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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.tango.server.ExceptionMessages;
import org.tango.server.properties.AttributePropertiesManager;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.ArchiveEventProp;
import fr.esrf.Tango.ChangeEventProp;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.EventProperties;
import fr.esrf.Tango.PeriodicEventProp;

/**
 * User class to create attribute properties.
 * 
 * @author ABEILLE
 * 
 */
public final class AttributePropertiesImpl {

    public static final String PERIOD_1000 = "1000";

    public static final String FORMAT_6_2F = "%6.2f";
    // default values
    public static final String NOT_SPECIFIED = "Not specified";
    public static final String NO_UNIT = "No unit";
    public static final String NO_STD_UNIT = "No standard unit";
    public static final String NO_DIPLAY_UNIT = "No display unit";
    public static final String NO_DESCRIPTION = "No description";
    public static final String NONE = "None";
    public static final String NAN = "NAN";

    // attribute properties names in tango db
    private static final String LABEL = "label";
    private static final String FORMAT = "format";
    private static final String UNIT = "unit";
    private static final String DISPLAY_UNIT = "display_unit";
    private static final String STANDARD_UNIT = "standard_unit";
    private static final String MIN_VAL = "min_value";
    private static final String MAX_VAL = "max_value";
    private static final String MIN_ALARM = "min_alarm";
    private static final String MAX_ALARM = "max_alarm";
    private static final String MIN_WARNING = "min_warning";
    private static final String MAX_WARNING = "max_warning";
    private static final String DELTA_T = "delta_t";
    private static final String DELTA_VAL = "delta_val";
    private static final String DESC = "description";
    private static final String EVENT_CHANGE_ABS = "abs_change";
    private static final String EVENT_CHANGE_REL = "rel_change";
    private static final String EVENT_PERIOD = "event_period";
    public static final String EVENT_ARCHIVE_PERIOD = "archive_period";
    private static final String EVENT_ARCHIVE_REL = "archive_rel_change";
    private static final String EVENT_ARCHIVE_ABS = "archive_abs_change";
    private static final String ENUM_LABELS = "enum_labels";
    private static final String ROOT_ATTRIBUTE = "__root_att";
    private String label = "";
    private String description = NO_DESCRIPTION;
    private String unit = NO_UNIT;
    private String standardUnit = NO_STD_UNIT;
    private String displayUnit = NO_DIPLAY_UNIT;
    private String format = FORMAT_6_2F;
    private String minValue = NOT_SPECIFIED;
    private double minValueDouble = -Double.MAX_VALUE;
    private String maxValue = NOT_SPECIFIED;
    private double maxValueDouble = Double.MAX_VALUE;
    private String minAlarm = NOT_SPECIFIED;
    private double minAlarmDouble = -Double.MAX_VALUE;
    private String maxAlarm = NOT_SPECIFIED;
    private double maxAlarmDouble = Double.MAX_VALUE;
    private String writableAttrName = NONE;
    private String minWarning = NOT_SPECIFIED;
    private double minWarningDouble = -Double.MAX_VALUE;
    private String maxWarning = NOT_SPECIFIED;
    private double maxWarningDouble = Double.MAX_VALUE;
    private String deltaT = NOT_SPECIFIED;
    private long deltaTLong = 0;
    private String deltaVal = NOT_SPECIFIED;
    private String[] alarmExtensions = new String[0];
    private final EventProperties eventProp;
    private String[] extensions = new String[0];
    private String[] sysExtensions = new String[0];
    private double deltaValDouble = 0;
    private String[] enumLabels = new String[] { NOT_SPECIFIED };
    private String rootAttribute = NOT_SPECIFIED;

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

    }

    public EventProperties createEmptyEventProperties() {
        final ChangeEventProp change = new ChangeEventProp();
        change.abs_change = NOT_SPECIFIED;
        change.extensions = new String[0];
        change.rel_change = NOT_SPECIFIED;
        final PeriodicEventProp per = new PeriodicEventProp();
        per.extensions = new String[0];
        per.period = PERIOD_1000;
        final ArchiveEventProp arch = new ArchiveEventProp();
        arch.abs_change = NOT_SPECIFIED;
        arch.extensions = new String[0];
        arch.period = NOT_SPECIFIED;
        arch.rel_change = NOT_SPECIFIED;
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
        if (description.isEmpty() || description.equalsIgnoreCase(NOT_SPECIFIED) || description.equalsIgnoreCase(NONE)) {
            this.description = NO_DESCRIPTION;
        } else {
            this.description = description;
        }
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(final String unit) {
        if (unit.isEmpty() || unit.equalsIgnoreCase(NOT_SPECIFIED) || unit.equalsIgnoreCase(NONE)) {
            this.unit = NO_UNIT;
        } else {
            this.unit = unit;
        }
    }

    public String getStandardUnit() {
        return standardUnit;
    }

    public void setStandardUnit(final String standardUnit) {
        if (standardUnit.isEmpty() || standardUnit.equalsIgnoreCase(NOT_SPECIFIED)
                || standardUnit.equalsIgnoreCase(NONE)) {
            this.standardUnit = NO_STD_UNIT;
        } else {
            this.standardUnit = standardUnit;
        }
    }

    public String getDisplayUnit() {
        return displayUnit;
    }

    public void setDisplayUnit(final String displayUnit) {
        if (displayUnit.isEmpty() || displayUnit.equalsIgnoreCase(NOT_SPECIFIED) || displayUnit.equalsIgnoreCase(NONE)) {
            this.displayUnit = NO_DIPLAY_UNIT;
        } else {
            this.displayUnit = displayUnit;
        }
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(final String format) {
        if (format.isEmpty() || format.equalsIgnoreCase(NOT_SPECIFIED) || format.equalsIgnoreCase(NONE)) {
            this.format = FORMAT_6_2F;
        } else {
            this.format = format;
        }
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(final String minValue) {
        if (minValue.isEmpty() || minValue.equalsIgnoreCase(NAN) || minValue.equalsIgnoreCase(NONE)) {
            this.minValue = NOT_SPECIFIED;
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
        if (maxValue.isEmpty() || maxValue.equalsIgnoreCase(NAN) || maxValue.equalsIgnoreCase(NONE)) {
            this.maxValue = NOT_SPECIFIED;
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
        if (minAlarm.isEmpty() || minAlarm.equalsIgnoreCase(NAN) || minAlarm.equalsIgnoreCase(NONE)) {
            this.minAlarm = NOT_SPECIFIED;
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
        if (maxAlarm.isEmpty() || maxAlarm.equalsIgnoreCase(NAN) || maxAlarm.equalsIgnoreCase(NONE)) {
            this.maxAlarm = NOT_SPECIFIED;
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
        if (field.equalsIgnoreCase(NONE) || field.equalsIgnoreCase(NAN)) {
            result = NOT_SPECIFIED;
        }
        return result;
    }

    private void setDefaultPeriod(final String field) {
        if (field.equalsIgnoreCase(NOT_SPECIFIED) || field.equalsIgnoreCase(NONE) || field.equalsIgnoreCase(NAN)) {
            eventProp.per_event.period = PERIOD_1000;
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

    public String getMinWarning() {
        return minWarning;
    }

    public String getMaxWarning() {
        return maxWarning;
    }

    public String getDeltaT() {
        return deltaT;
    }

    public String getDeltaVal() {
        return deltaVal;
    }

    public void setWritableAttrName(final String writableAttrName) {
        this.writableAttrName = writableAttrName;
    }

    public void setMinWarning(final String minWarning) {
        if (minWarning.equalsIgnoreCase(NAN) || minWarning.equalsIgnoreCase(NONE)) {
            this.minWarning = NOT_SPECIFIED;
            minWarningDouble = -Double.MAX_VALUE;
        } else {
            this.minWarning = minWarning;
            try {
                minWarningDouble = Double.parseDouble(minWarning);
            } catch (final NumberFormatException e) {
            }
        }
    }

    public void setMaxWarning(final String maxWarning) {
        if (maxWarning.equalsIgnoreCase(NAN) || maxWarning.equalsIgnoreCase(NONE)) {
            this.maxWarning = NOT_SPECIFIED;
            maxWarningDouble = Double.MAX_VALUE;
        } else {
            this.maxWarning = maxWarning;
            try {
                maxWarningDouble = Double.parseDouble(maxWarning);
            } catch (final NumberFormatException e) {
            }
        }
    }

    public void setDeltaT(final String deltaT) {
        if (deltaT.equalsIgnoreCase(NAN) || deltaT.equalsIgnoreCase(NONE)) {
            this.deltaT = NOT_SPECIFIED;
            deltaTLong = 0;
        } else {
            this.deltaT = deltaT;
            try {
                deltaTLong = Long.parseLong(deltaT);
            } catch (final NumberFormatException e) {
            }
        }
    }

    public void setDeltaVal(final String deltaVal) {
        if (deltaVal.equalsIgnoreCase(NAN) || deltaVal.equalsIgnoreCase(NONE)) {
            this.deltaVal = NOT_SPECIFIED;
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
        if (enumLabels.length == 1) {
            if (enumLabels[0].isEmpty() || enumLabels[0].equalsIgnoreCase(NAN) || enumLabels[0].equalsIgnoreCase(NONE)) {
                enumLabels[0] = NOT_SPECIFIED;
            }
        }

        if (enumLabels != null && enumLabels.length > 0) {
            // find duplicate values
            final List<String> inputList = Arrays.asList(enumLabels);
            final Set<String> inputSet = new HashSet<String>(inputList);
            if (inputSet.size() < inputList.size()) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_OPT_PROP, "duplicate enum values not allowed");
            }
            this.enumLabels = Arrays.copyOf(enumLabels, enumLabels.length);
            // set min max values
            if (enumLabels.length >= 1 && !enumLabels[0].equals(NOT_SPECIFIED)) {
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
        reflectionToStringBuilder.setExcludeFieldNames(new String[] { "eventProp", "alarmExtensions", "extensions",
                "sysExtensions", "minValueDouble", "maxValueDouble", "minAlarmDouble", "maxAlarmDouble",
                "minWarningDouble", "maxWarningDouble", "writableAttrName", "deltaTLong", "deltaValDouble" });
        return reflectionToStringBuilder.toString();
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
    public boolean equals(final Object obj) {
        final String reflectionToStringBuilder = new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .toString();
        final String toCompare = new ReflectionToStringBuilder(obj, ToStringStyle.SHORT_PREFIX_STYLE).toString();
        boolean isEqual = reflectionToStringBuilder.equalsIgnoreCase(toCompare);
        if (isEqual) {
            isEqual = compareEventProps(this.getEventProp(), ((AttributePropertiesImpl) obj).getEventProp());

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
        this.rootAttribute = rootAttribute;
        isFwdAttribute = true;
    }

    void persist(final String deviceName, final String attributeName) throws DevFailed {
        final Map<String, String[]> properties = new HashMap<String, String[]>();
        properties.put(LABEL, new String[] { getLabel() });
        if (!isFwdAttribute) {
            properties.put(FORMAT, new String[] { getFormat() });
            properties.put(UNIT, new String[] { getUnit() });
            properties.put(DISPLAY_UNIT, new String[] { getDisplayUnit() });
            properties.put(STANDARD_UNIT, new String[] { getStandardUnit() });
            properties.put(MIN_VAL, new String[] { getMinValue() });
            properties.put(MAX_VAL, new String[] { getMaxValue() });
            properties.put(MIN_ALARM, new String[] { getMinAlarm() });
            properties.put(MAX_ALARM, new String[] { getMaxAlarm() });
            properties.put(MIN_WARNING, new String[] { getMinWarning() });
            properties.put(MAX_WARNING, new String[] { getMaxWarning() });
            properties.put(DELTA_T, new String[] { getDeltaT() });
            properties.put(DELTA_VAL, new String[] { getDeltaVal() });
            properties.put(DESC, new String[] { getDescription() });
            properties.put(ENUM_LABELS, getEnumLabels());

            final EventProperties eventProp = getEventProp();
            properties.put(EVENT_ARCHIVE_ABS, new String[] { eventProp.arch_event.abs_change });
            properties.put(EVENT_ARCHIVE_PERIOD, new String[] { eventProp.arch_event.period });
            properties.put(EVENT_ARCHIVE_REL, new String[] { eventProp.arch_event.rel_change });
            properties.put(EVENT_CHANGE_ABS, new String[] { eventProp.ch_event.abs_change });
            properties.put(EVENT_PERIOD, new String[] { eventProp.per_event.period });
            properties.put(EVENT_CHANGE_REL, new String[] { eventProp.ch_event.rel_change });
        }
        final AttributePropertiesManager attributePropertiesManager = new AttributePropertiesManager(deviceName);
        attributePropertiesManager.setAttributePropertiesInDB(attributeName, properties);
    }

    void load(final String deviceName, final String attributeName) throws DevFailed {
        final AttributePropertiesManager attributePropertiesManager = new AttributePropertiesManager(deviceName);
        final Map<String, String[]> propValues = attributePropertiesManager.getAttributePropertiesFromDB(attributeName);
        // use a second map for attribute props that have one value
        final Map<String, String> propValuesSingle = new HashMap<String, String>(propValues.size());
        for (final Entry<String, String[]> entry : propValues.entrySet()) {
            final String[] value = entry.getValue();
            if (value.length == 1) {
                propValuesSingle.put(entry.getKey(), value[0]);
            } else if (value.length == 0) {
                propValuesSingle.put(entry.getKey(), "");
            }
        }
//        if (propValues.containsKey(ROOT_ATTRIBUTE)) {
//            setRootAttribute(propValuesSingle.get(ROOT_ATTRIBUTE));
//        }
        if (propValues.containsKey(LABEL)) {
            setLabel(propValuesSingle.get(LABEL));
        }
        if (!isFwdAttribute) {
            if (propValues.containsKey(FORMAT)) {
                setFormat(propValuesSingle.get(FORMAT));
            }
            if (propValues.containsKey(UNIT)) {
                setUnit(propValuesSingle.get(UNIT));
            }
            if (propValues.containsKey(DISPLAY_UNIT)) {
                setDisplayUnit(propValuesSingle.get(DISPLAY_UNIT));
            }
            if (propValues.containsKey(STANDARD_UNIT)) {
                setStandardUnit(propValuesSingle.get(STANDARD_UNIT));
            }
            setMinMax(propValuesSingle);
            if (propValues.containsKey(DESC)) {
                setDescription(propValuesSingle.get(DESC));
            }
            setEventProperties(propValuesSingle);
            if (propValues.containsKey(ENUM_LABELS)) {
                setEnumLabels(propValues.get(ENUM_LABELS));
            }
        }
    }

    String loadAttributeRootName(final String deviceName, final String attributeName) throws DevFailed {
        final AttributePropertiesManager attributePropertiesManager = new AttributePropertiesManager(deviceName);
        setRootAttribute(attributePropertiesManager.getAttributePropertyFromDB(attributeName, ROOT_ATTRIBUTE));
        return getRootAttribute();
    }

    void persistAttributeRootName(final String deviceName, final String attributeName) throws DevFailed {
        new AttributePropertiesManager(deviceName).setAttributePropertyInDB(attributeName, ROOT_ATTRIBUTE,
                getRootAttribute());
    }

    private void setMinMax(final Map<String, String> propValues) {
        if (propValues.containsKey(MIN_VAL)) {
            setMinValue(propValues.get(MIN_VAL));
        }
        if (propValues.containsKey(MAX_VAL)) {
            setMaxValue(propValues.get(MAX_VAL));
        }
        if (propValues.containsKey(MIN_ALARM)) {
            setMinAlarm(propValues.get(MIN_ALARM));
        }
        if (propValues.containsKey(MAX_ALARM)) {
            setMaxAlarm(propValues.get(MAX_ALARM));
        }
        if (propValues.containsKey(MIN_WARNING)) {
            setMinWarning(propValues.get(MIN_WARNING));
        }
        if (propValues.containsKey(MAX_WARNING)) {
            setMaxWarning(propValues.get(MAX_WARNING));
        }
        if (propValues.containsKey(DELTA_T)) {
            setDeltaT(propValues.get(DELTA_T));
        }
        if (propValues.containsKey(DELTA_VAL)) {
            setDeltaVal(propValues.get(DELTA_VAL));
        }
    }

    private void setEventProperties(final Map<String, String> propValues) {

        if (propValues.containsKey(EVENT_ARCHIVE_ABS)) {
            setArchivingEventAbsChange(propValues.get(EVENT_ARCHIVE_ABS));
        }
        if (propValues.containsKey(EVENT_ARCHIVE_PERIOD)) {
            setArchivingEventPeriod(propValues.get(EVENT_ARCHIVE_PERIOD));
        }
        if (propValues.containsKey(EVENT_ARCHIVE_REL)) {
            setArchivingEventRelChange(propValues.get(EVENT_ARCHIVE_REL));
        }
        if (propValues.containsKey(EVENT_CHANGE_ABS)) {
            setEventAbsChange(propValues.get(EVENT_CHANGE_ABS));
        }
        if (propValues.containsKey(EVENT_PERIOD)) {
            setEventPeriod(propValues.get(EVENT_PERIOD));
        }
        if (propValues.containsKey(EVENT_CHANGE_REL)) {
            setEventRelChange(propValues.get(EVENT_CHANGE_REL));
        }
    }

    void clear(final String deviceName, final String attributeName) throws DevFailed {
        final AttributePropertiesManager attributePropertiesManager = new AttributePropertiesManager(deviceName);
        attributePropertiesManager.removeAttributeProperties(attributeName);
    }
}
