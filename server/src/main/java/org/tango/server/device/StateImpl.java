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
package org.tango.server.device;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.DeviceState;
import org.tango.server.annotation.State;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;

/**
 * Manage the state of the device
 *
 * @see State
 * @author ABEILLE
 *
 */
public final class StateImpl {
    private final Logger logger = LoggerFactory.getLogger(StateImpl.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(StateImpl.class);
    private final Method getStateMethod;
    private final Method setStateMethod;
    private final Object businessObject;
    private DevState state = DevState.UNKNOWN;
    private final Set<String> attributeAlarm = new HashSet<String>();

    /**
     * Ctr
     *
     * @param businessObject
     * @param getStateMethod
     * @param setStateMethod
     */
    public StateImpl(final Object businessObject, final Method getStateMethod, final Method setStateMethod) {
        this.businessObject = businessObject;
        this.getStateMethod = getStateMethod;
        this.setStateMethod = setStateMethod;
    }

    /**
     * If the state is not defined by user, will be a default state
     *
     * @return true if a default state
     */
    public boolean isDefaultState() {
        return getStateMethod == null;
    }

    /**
     * get the state from the device
     *
     * @return the state
     * @throws DevFailed
     */
    public DevState updateState() throws DevFailed {
        xlogger.entry();
        Object getState;
        if (getStateMethod != null) {
            try {
                getState = getStateMethod.invoke(businessObject);
                if (getState != null) {
                    if (getState instanceof DeviceState) {
                        state = ((DeviceState) getState).getDevState();
                    } else {
                        state = (DevState) getState;
                    }
                }
            } catch (final IllegalArgumentException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final IllegalAccessException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final InvocationTargetException e) {
                if (e.getCause() instanceof DevFailed) {
                    throw (DevFailed) e.getCause();
                } else {
                    throw DevFailedUtils.newDevFailed(e.getCause());
                }
            }
        }
        //	if (!attributeAlarm.isEmpty()) {
        //	    state = DevState.ALARM;
        //	}
        xlogger.exit();
        return state;
    }

    public void addAttributeAlarm(final String attributeName) {
        attributeAlarm.add(attributeName);
        state = DevState.ALARM;
    }

    public void removeAttributeAlarm(final String attributeName) {
        attributeAlarm.remove(attributeName);
    }

    /**
     * change state of the device
     *
     * @param state
     * @throws DevFailed
     */
    public synchronized void stateMachine(final DeviceState state) throws DevFailed {
        if (state != null) {
            this.state = state.getDevState();
            if (setStateMethod != null) {
                logger.debug("Changing state to {}", state);
                try {
                    if (setStateMethod.getParameterTypes()[0].equals(DeviceState.class)) {
                        setStateMethod.invoke(businessObject, state);
                    } else {
                        setStateMethod.invoke(businessObject, this.state);
                    }
                } catch (final IllegalArgumentException e) {
                    throw DevFailedUtils.newDevFailed(e);
                } catch (final IllegalAccessException e) {
                    throw DevFailedUtils.newDevFailed(e);
                } catch (final InvocationTargetException e) {
                    if (e.getCause() instanceof DevFailed) {
                        throw (DevFailed) e.getCause();
                    } else {
                        throw DevFailedUtils.newDevFailed(e.getCause());
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append("getStateMethod", getStateMethod);
        builder.append("setStateMethod", setStateMethod);
        builder.append("device class", businessObject.getClass());
        return builder.toString();
    }

    public DevState getState() {
        return state;
    }

}
