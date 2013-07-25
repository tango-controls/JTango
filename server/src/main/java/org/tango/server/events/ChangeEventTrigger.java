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
package org.tango.server.events;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.utils.ArrayUtils;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;

/**
 * manage trigger for {@link EventType#CHANGE_EVENT}
 * 
 * @author ABEILLE
 * 
 */
public class ChangeEventTrigger implements IEventTrigger {

    private final Logger logger = LoggerFactory.getLogger(PeriodicEventTrigger.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(PeriodicEventTrigger.class);
    private final AttributeImpl attribute;
    private AttributeValue previousValue;
    private AttributeValue value;
    private double absolute;
    private boolean checkAbsolute;
    private double relative;
    private boolean checkRelative;

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

    private DevFailed error;
    private DevFailed previousError;

    @Override
    public boolean isSendEvent() throws DevFailed {
        xlogger.entry();
        previousValue = value;
        value = attribute.getReadValue();

        boolean hasChanged = false;

        if (previousError != null && error == null) {
            // there was an error before
            hasChanged = true;
        } else if (previousError == null && error != null) {
            // an error has just occured
            hasChanged = true;
        } else if (previousError != null && error != null) {
            if (!DevFailedUtils.toString(previousError).equals(DevFailedUtils.toString(error))) {
                // the error msg has change
                hasChanged = true;
            }
        } else if (attribute.isScalar()) {
            if (attribute.isNumber()) {
                hasChanged = hasScalarNumberChanged();
            } else if (attribute.isState()) {
                hasChanged = hasStateChanged();
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

        // TODO DevEncoded?
        logger.debug("CHANGE event must send: {}", hasChanged);
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
            hasChanged = delta <= absolute || delta >= absolute;
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
                delta = (val - previousVal) * 100.0 / previousVal;
            }
            hasChanged = delta <= relative || delta >= relative;
        }
        return hasChanged;
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
                    hasChanged = delta <= absolute || delta >= absolute;
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
                    hasChanged = delta <= relative || delta >= relative;
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
            if (!Arrays.toString(val).equals(Arrays.toString(previousVal))) {
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

}
