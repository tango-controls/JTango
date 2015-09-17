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
package org.tango.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;

/**
 * Manage logging to another device
 *
 * @author ABEILLE
 *
 */
public final class DeviceAppender extends AppenderBase<ILoggingEvent> implements ITangoAppender {

    private static final int ARGIN_SIZE = 6;
    private static Logger logger = LoggerFactory.getLogger(DeviceAppender.class);
    private final DeviceProxy loggerDevice;
    private final String loggingDeviceName;
    private Level level;
    private final String deviceName;

    public DeviceAppender(final String deviceTargetName, final String deviceName) throws DevFailed {
        loggerDevice = new DeviceProxy(deviceTargetName);
        this.loggingDeviceName = deviceTargetName;
        this.deviceName = deviceName;
        level = Level.DEBUG;
    }

    @Override
    protected void append(final ILoggingEvent eventObject) {
        if (eventObject.getMDCPropertyMap().get(DeviceImpl.MDC_KEY).equalsIgnoreCase(deviceName)
                && eventObject.getLevel().isGreaterOrEqual(level)) {
            try {
                final String[] dvsa = new String[ARGIN_SIZE];
                int i = 0;
                dvsa[i++] = String.valueOf(eventObject.getTimeStamp());
                dvsa[i++] = eventObject.getLevel().toString();
                dvsa[i++] = eventObject.getLoggerName();
                dvsa[i++] = eventObject.getFormattedMessage();
                dvsa[i++] = "";
                dvsa[i] = eventObject.getThreadName();
                final DeviceData dd = new DeviceData();
                dd.insert(dvsa);
                loggerDevice.command_inout_asynch("Log", dd, true);
            } catch (final DevFailed e) {
                logger.error("failed to send log to {} : {}", loggerDevice.get_name(), DevFailedUtils.toString(e));
            }
        }
    }

    public String getLoggingDeviceName() {
        return loggingDeviceName;
    }

    @Override
    public void setLevel(final int level) {
        this.level = LoggingLevel.getLevelFromInt(level);
    }

    @Override
    public String getDeviceName() {
        return deviceName;
    }
}
