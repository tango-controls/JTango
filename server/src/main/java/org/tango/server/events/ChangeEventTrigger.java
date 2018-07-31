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
package org.tango.server.events;

import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.EventProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.Constants;
import org.tango.server.ExceptionMessages;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.utils.ArrayUtils;
import org.tango.utils.DevFailedUtils;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * manage trigger for {@link EventType#CHANGE_EVENT}
 *
 * @author ABEILLE
 *
 */
public class ChangeEventTrigger implements IEventTrigger {

    private final Logger logger = LoggerFactory.getLogger(ChangeEventTrigger.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(ChangeEventTrigger.class);
    private final AttributeImpl attribute;
    private final QualityEventTrigger qualityTrigger;
    private AttributeValue previousValue;
    private AttributeValue value;
    private double absolute;
    private boolean checkAbsolute;
    private double relative;
    private boolean checkRelative;
    private DevFailed error;
    private DevFailed previousError;
    private boolean previousInitialized = false;

    /**
     * Ctr
     *
     * @param attribute The attribute that send event
     * @param absolute The absolute change delta
     * @param relative The relative change delta
     */
    public ChangeEventTrigger(final AttributeImpl attribute, final String absolute, final String relative) {
        this.attribute = attribute;
        value = attribute.getReadValue();
        qualityTrigger = new QualityEventTrigger(attribute);
        setCriteria(absolute, relative);
    }

    /**
     * Check if event criteria are set for specified events
     *
     * @param attribute the specified attribute
     * @throws DevFailed if no event criteria is set for specified attribute.
     */
    static void checkEventCriteria(final AttributeImpl attribute) throws DevFailed {
        // Check if value is not numerical (always true for State and String)
        if (attribute.isState() || attribute.isString() || attribute.isBoolean()) {
            return;
        }
        // Else check criteria
        final EventProperties props = attribute.getProperties().getEventProp();
        if (props.ch_event.abs_change.equals(Constants.NOT_SPECIFIED)
                && props.ch_event.rel_change.equals(Constants.NOT_SPECIFIED)) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.EVENT_CRITERIA_NOT_SET,
                    "Event properties (abs_change or rel_change) for attribute " + attribute.getName()
                            + " are not set");
        }
    }

    public void setCriteria(final String absolute, final String relative) {
        try {
            this.absolute = Double.parseDouble(absolute);
            checkAbsolute = true;
        } catch (final NumberFormatException e) {
            checkAbsolute = false;
        }
        try {
            this.relative = Double.parseDouble(relative);
            checkRelative = true;
        } catch (final NumberFormatException e) {
            checkRelative = false;
        }
    }

    @Override
    public boolean isSendEvent() throws DevFailed {
        xlogger.entry();
        boolean hasChanged = qualityTrigger.isSendEvent();
        if (!hasChanged) {
            value = attribute.getReadValue();
            // Check if first call
            if (!previousInitialized) {
                previousError = error;
                previousValue = value;
                previousInitialized = true;
                hasChanged = true;
            } else {
                if (previousError != null && error == null) {
                    // there was an error before
                    hasChanged = true;
                } else if (previousError == null && error != null) {
                    // an error has just occured
                    hasChanged = true;
                } else if (previousError != null && error != null) {
                    if (!DevFailedUtils.toString(previousError).equals(DevFailedUtils.toString(error))) {
                        // the error msg has changed
                        hasChanged = true;
                    }
                } else if (value.getValue() == null && previousValue.getValue() == null) {
                    hasChanged = false;
                } else if (value.getValue() == null && previousValue.getValue() != null) {
                    hasChanged = true;
                } else if (value.getValue() != null && previousValue.getValue() == null) {
                    hasChanged = true;
                } else if (attribute.isScalar()) {
                    if (attribute.isNumber()) {
                        hasChanged = hasScalarNumberChanged();
                    } else if (attribute.isState()) {
                        hasChanged = hasStateChanged();
                    } else if (attribute.isDevEncoded()) {
                        hasChanged = hasDevEncodedChanged();
                    } else {
                        // string or boolean
                        hasChanged = hasScalarStringChanged();
                    }
                } else {
                    if (attribute.isNumber()) {
                        hasChanged = hasArrayNumberChanged();
                    } else if (attribute.isState()) {
                        hasChanged = hasStateArrayChanged();
                    } else {
                        // string or boolean
                        hasChanged = hasArrayStringChanged();
                    }
                }
                if (hasChanged) {
                    previousValue = value;
                }
            }
        }
        logger.debug("CHANGE event for {} must send: {}", attribute.getName(), hasChanged);
        xlogger.exit();
        return hasChanged;
    }

    @Override
    public void setError(final DevFailed error) {
        previousError = this.error;
        this.error = error;
    }

    private boolean hasScalarNumberChanged() {
        boolean hasChanged = false;
        final double val = Double.parseDouble(value.getValue().toString());
        final double previousVal = Double.parseDouble(previousValue.getValue().toString());
        // absolute change
        if (checkAbsolute) {
            final double delta = val - previousVal;
            hasChanged = Math.abs(delta) >= absolute;
        }
        // relative change
        if (!hasChanged && checkRelative) {
            final double delta;
            if (previousVal == 0) {
                if (val == 0) {
                    delta = 0;
                } else {
                    delta = 100;
                }
            } else {
                delta = (val - previousVal) / previousVal * 100.0;
            }
            hasChanged = Math.abs(delta) >= relative;
        }
        return hasChanged;
    }

    private boolean hasDevEncodedChanged() {
        final DevEncoded val = (DevEncoded) value.getValue();
        final DevEncoded previousVal = (DevEncoded) previousValue.getValue();
        return !Arrays.equals(val.encoded_data, previousVal.encoded_data);
    }

    private boolean hasScalarStringChanged() {
        final String val = value.getValue().toString();
        final String previousVal = previousValue.getValue().toString();
        return !val.equals(previousVal);
    }

    private boolean hasArrayNumberChanged() {
        boolean hasChanged = false;
        if (Array.getLength(value.getValue()) != Array.getLength(previousValue.getValue())) {
            hasChanged = true;
        } else {
            final String[] val = ArrayUtils.toStringArray(value.getValue());
            final String[] previousVal = ArrayUtils.toStringArray(previousValue.getValue());
            for (int i = 0; i < previousVal.length; i++) {
                final double valD = Double.parseDouble(val[i]);
                final double previousValD = Double.parseDouble(previousVal[i]);
                // absolute change
                if (checkAbsolute) {
                    final double delta = valD - previousValD;
                    hasChanged = Math.abs(delta) >= absolute;
                }
                // relative change
                if (!hasChanged && checkRelative) {
                    final double delta;
                    if (previousValD == 0) {
                        if (valD == 0) {
                            delta = 0;
                        } else {
                            delta = 100;
                        }
                    } else {
                        delta = valD - previousValD * 100.0 / previousValD;
                    }
                    hasChanged = Math.abs(delta) >= relative;
                }
                if (hasChanged) {
                    break;
                }
            }
        }
        return hasChanged;
    }

    private boolean hasArrayStringChanged() {
        boolean hasChanged = false;

        if (Array.getLength(value.getValue()) != Array.getLength(previousValue.getValue())) {
            hasChanged = true;
        } else {
            final String[] val;
            final String[] previousVal;
            if (value.getValue() instanceof String[]) {
                val = (String[]) value.getValue();
                previousVal = (String[]) previousValue.getValue();
            } else {
                val = ArrayUtils.toStringArray(value.getValue());
                previousVal = ArrayUtils.toStringArray(previousValue.getValue());
            }
            if (!Arrays.equals(val, previousVal)) {
                hasChanged = true;
            }
        }
        return hasChanged;
    }

    private boolean hasStateChanged() {
        final DevState state = (DevState) value.getValue();
        final DevState previousState = (DevState) previousValue.getValue();
        return state != previousState;
    }

    private boolean hasStateArrayChanged() {
        boolean hasChanged = false;
        final DevState[] state = (DevState[]) value.getValue();
        final DevState[] previousState = (DevState[]) previousValue.getValue();
        if (state.length != previousState.length) {
            hasChanged = true;
        } else {
            for (int i = 0; i < previousState.length; i++) {
                if (!state[i].equals(previousState[i])) {
                    hasChanged = true;
                    break;
                }
            }
        }
        return hasChanged;
    }

    @Override
    public void updateProperties() throws DevFailed {
        final EventProperties props = attribute.getProperties().getEventProp();
        setCriteria(props.ch_event.abs_change, props.ch_event.rel_change);
    }

    @Override
    public boolean doCheck() {
        return attribute.isPushChangeEvent() ? attribute.isCheckChangeEvent() : true;
    }
}
