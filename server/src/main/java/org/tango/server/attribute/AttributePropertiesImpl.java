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

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import fr.esrf.Tango.ArchiveEventProp;
import fr.esrf.Tango.ChangeEventProp;
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

    private static final String FORMAT_6_2F = "%6.2f";
    // default values
    public static final String NOT_SPECIFIED = "Not specified";
    public static final String NO_UNIT = "No unit";
    public static final String NO_STD_UNIT = "No standard unit";
    public static final String NO_DIPLAY_UNIT = "No display unit";
    public static final String NO_DESCRIPTION = "No description";
    public static final String NONE = "None";
    public static final String NAN = "NAN";

    // attribute names in tango db
    public static final String LABEL = "label";
    public static final String FORMAT = "format";
    public static final String UNIT = "unit";
    public static final String DISPLAY_UNIT = "display_unit";
    public static final String STANDARD_UNIT = "standard_unit";
    public static final String MIN_VAL = "min_value";
    public static final String MAX_VAL = "max_value";
    public static final String MIN_ALARM = "min_alarm";
    public static final String MAX_ALARM = "max_alarm";
    public static final String MIN_WARNING = "min_warning";
    public static final String MAX_WARNING = "max_warning";
    public static final String DELTA_T = "delta_t";
    public static final String DELTA_VAL = "delta_val";
    public static final String DESC = "description";
    public static final String EVENT_CHANGE_ABS = "abs_change";
    public static final String EVENT_CHANGE_REL = "rel_change";
    public static final String EVENT_PERIOD = "event_period";
    public static final String EVENT_ARCHIVE_PERIOD = "archive_period";
    public static final String EVENT_ARCHIVE_REL = "archive_rel_change";
    public static final String EVENT_ARCHIVE_ABS = "archive_abs_change";

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

    public AttributePropertiesImpl() {
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
        maxAlarm = props.maxAlarm;
        minWarning = props.minWarning;
        maxWarning = props.maxWarning;
        deltaT = props.deltaT;
        deltaVal = props.deltaVal;
        alarmExtensions = Arrays.copyOf(props.alarmExtensions, props.alarmExtensions.length);
        // TODO deep copy
        eventProp = props.eventProp;
        extensions = Arrays.copyOf(props.extensions, props.extensions.length);
        sysExtensions = Arrays.copyOf(props.sysExtensions, props.sysExtensions.length);

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
        if (description.isEmpty() || description.equalsIgnoreCase(AttributePropertiesImpl.NOT_SPECIFIED)
                || description.equalsIgnoreCase(AttributePropertiesImpl.NONE)) {
            this.description = AttributePropertiesImpl.NO_DESCRIPTION;
        } else {
            this.description = description;
        }
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(final String unit) {
        if (unit.isEmpty() || unit.equalsIgnoreCase(AttributePropertiesImpl.NOT_SPECIFIED)
                || unit.equalsIgnoreCase(AttributePropertiesImpl.NONE)) {
            this.unit = AttributePropertiesImpl.NO_UNIT;
        } else {
            this.unit = unit;
        }
    }

    public String getStandardUnit() {
        return standardUnit;
    }

    public void setStandardUnit(final String standardUnit) {
        if (standardUnit.isEmpty() || standardUnit.equalsIgnoreCase(AttributePropertiesImpl.NOT_SPECIFIED)
                || standardUnit.equalsIgnoreCase(AttributePropertiesImpl.NONE)) {
            this.standardUnit = AttributePropertiesImpl.NO_STD_UNIT;
        } else {
            this.standardUnit = standardUnit;
        }
    }

    public String getDisplayUnit() {
        return displayUnit;
    }

    public void setDisplayUnit(final String displayUnit) {
        if (displayUnit.isEmpty() || displayUnit.equalsIgnoreCase(AttributePropertiesImpl.NOT_SPECIFIED)
                || displayUnit.equalsIgnoreCase(AttributePropertiesImpl.NONE)) {
            this.displayUnit = AttributePropertiesImpl.NO_DIPLAY_UNIT;
        } else {
            this.displayUnit = displayUnit;
        }
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(final String format) {
        if (format.isEmpty() || format.equalsIgnoreCase(AttributePropertiesImpl.NOT_SPECIFIED)
                || format.equalsIgnoreCase(AttributePropertiesImpl.NONE)) {
            this.format = AttributePropertiesImpl.FORMAT_6_2F;
        } else {
            this.format = format;
        }
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(final String minValue) {
        if (minValue.isEmpty() || minValue.equalsIgnoreCase(AttributePropertiesImpl.NAN)
                || minValue.equalsIgnoreCase(AttributePropertiesImpl.NONE)) {
            this.minValue = AttributePropertiesImpl.NOT_SPECIFIED;
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
        if (maxValue.isEmpty() || maxValue.equalsIgnoreCase(AttributePropertiesImpl.NAN)
                || maxValue.equalsIgnoreCase(AttributePropertiesImpl.NONE)) {
            this.maxValue = AttributePropertiesImpl.NOT_SPECIFIED;
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
        if (minAlarm.isEmpty() || minAlarm.equalsIgnoreCase(AttributePropertiesImpl.NAN)
                || minAlarm.equalsIgnoreCase(AttributePropertiesImpl.NONE)) {
            this.minAlarm = AttributePropertiesImpl.NOT_SPECIFIED;
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
        if (maxAlarm.isEmpty() || maxAlarm.equalsIgnoreCase(AttributePropertiesImpl.NAN)
                || maxAlarm.equalsIgnoreCase(AttributePropertiesImpl.NONE)) {
            this.maxAlarm = AttributePropertiesImpl.NOT_SPECIFIED;
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
        if (field.equalsIgnoreCase(AttributePropertiesImpl.NONE) || field.equalsIgnoreCase(AttributePropertiesImpl.NAN)) {
            result = NOT_SPECIFIED;
        }
        return result;
    }

    private void setDefaultPeriod(final String field) {
        if (field.equalsIgnoreCase(AttributePropertiesImpl.NOT_SPECIFIED)
                || field.equalsIgnoreCase(AttributePropertiesImpl.NONE)
                || field.equalsIgnoreCase(AttributePropertiesImpl.NAN)) {
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
        if (minWarning.equalsIgnoreCase(AttributePropertiesImpl.NAN)
                || minWarning.equalsIgnoreCase(AttributePropertiesImpl.NONE)) {
            this.minWarning = AttributePropertiesImpl.NOT_SPECIFIED;
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
        if (maxWarning.equalsIgnoreCase(AttributePropertiesImpl.NAN)
                || maxWarning.equalsIgnoreCase(AttributePropertiesImpl.NONE)) {
            this.maxWarning = AttributePropertiesImpl.NOT_SPECIFIED;
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
        if (deltaT.equalsIgnoreCase(AttributePropertiesImpl.NAN)
                || deltaT.equalsIgnoreCase(AttributePropertiesImpl.NONE)) {
            this.deltaT = AttributePropertiesImpl.NOT_SPECIFIED;
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
        if (deltaVal.equalsIgnoreCase(AttributePropertiesImpl.NAN)
                || deltaVal.equalsIgnoreCase(AttributePropertiesImpl.NONE)) {
            this.deltaVal = AttributePropertiesImpl.NOT_SPECIFIED;
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

}
