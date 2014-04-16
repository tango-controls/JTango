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

import org.tango.server.servant.DeviceImpl;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;

/**
 * Manage tango logging in file
 * 
 * @author ABEILLE
 * 
 */
public final class FileAppender extends RollingFileAppender<ILoggingEvent> implements ITangoAppender {

    private final String deviceName;
    private Level level;

    public FileAppender(final String loggingDeviceName) {
        this.deviceName = loggingDeviceName;
    }

    @Override
    protected void subAppend(final ILoggingEvent event) {
        if (event.getMDCPropertyMap().get(DeviceImpl.MDC_KEY).equalsIgnoreCase(deviceName)
                && event.getLevel().isGreaterOrEqual(level)) {
            super.subAppend(event);
        }
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
