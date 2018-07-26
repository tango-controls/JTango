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
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.DeviceState;
import org.tango.server.annotation.Status;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * Manage the status of the device
 *
 * @see Status
 * @author ABEILLE
 *
 */
public final class StatusImpl {
    private final Logger logger = LoggerFactory.getLogger(StatusImpl.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(StatusImpl.class);
    private final Method getStatusMethod;
    private final Method setStatusMethod;
    private String status = "";
    private final Object businessObject;
    private final Map<String, String> attributeAlarm = new HashMap<String, String>();

    /**
     * Ctr
     *
     * @param businessObject
     * @param getStatusMethod
     * @param setStatusMethod
     */
    public StatusImpl(final Object businessObject, final Method getStatusMethod, final Method setStatusMethod) {
        this.getStatusMethod = getStatusMethod;
        this.setStatusMethod = setStatusMethod;
        this.businessObject = businessObject;
        status = "";
    }

    /**
     * Get the status of the device
     *
     * @return the status
     * @throws DevFailed
     */
    public String updateStatus(final DeviceState state) throws DevFailed {
        xlogger.entry();
        if (getStatusMethod != null) {
            try {
                status = (String) getStatusMethod.invoke(businessObject);
            } catch (final IllegalArgumentException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final IllegalAccessException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final InvocationTargetException e) {
                if (e.getCause() instanceof DevFailed) {
                    throw (DevFailed) e.getCause();
                } else {
                    throw DevFailedUtils.newDevFailed("INVOCATION_ERROR", ExceptionUtils.getStackTrace(e.getCause())
                            + " InvocationTargetException");
                }
            }
        } else {
            if (status.isEmpty()) {
                status = "The device is in " + state + " state.";
            }
        }
        StringBuilder statusAlarm = new StringBuilder();
        for (final String string : attributeAlarm.values()) {
            statusAlarm = statusAlarm.append(string);
        }

        return status + statusAlarm;
    }

    /**
     * Change status of the device
     *
     * @param status
     * @throws DevFailed
     */
    public synchronized void statusMachine(final String status, final DeviceState state) throws DevFailed {
        if (status != null) {
            logger.debug("Changing status to: {}", status);
            this.status = status;
            if (setStatusMethod != null) {
                try {
                    setStatusMethod.invoke(businessObject, status);
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

    public void addAttributeAlarm(final String attributeName, final boolean isTooHigh) throws DevFailed {
        String alarmStatus;
        if (isTooHigh) {
            alarmStatus = "\nAlarm : Value too high for " + attributeName;
        } else {
            alarmStatus = "\nAlarm : Value too low for " + attributeName;
        }
        attributeAlarm.put(attributeName, alarmStatus);
        // statusMachine(status + alarmStatus, DeviceState.ALARM);
    }

    public void addDeltaAttributeAlarm(final String attributeName) throws DevFailed {
        final String alarmStatus = "\nAlarm : RDS (R-W delta) for " + attributeName;
        attributeAlarm.put(attributeName, alarmStatus);
        // statusMachine(status + alarmStatus, DeviceState.ALARM);
    }

    public void removeAttributeAlarm(final String attributeName) {
        attributeAlarm.remove(attributeName);
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append("getStatusMethod", getStatusMethod);
        builder.append("setStatusMethod", setStatusMethod);
        builder.append("device class", businessObject.getClass());
        return builder.toString();
    }

    public String getStatus() {
        return status;
    }
}
