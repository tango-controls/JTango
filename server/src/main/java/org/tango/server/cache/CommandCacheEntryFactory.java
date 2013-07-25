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
package org.tango.server.cache;

import net.sf.ehcache.constructs.blocking.CacheEntryFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.command.CommandImpl;
import org.tango.server.device.DeviceLock;

import fr.esrf.Tango.DevFailed;

public final class CommandCacheEntryFactory implements CacheEntryFactory {
    private final Logger logger = LoggerFactory.getLogger(CommandCacheEntryFactory.class);
    private static final double NANO_TO_MILLI = 1000000.0;
    private final CommandImpl command;
    // private Profiler profilerPeriod = new Profiler("period");
    private final DeviceLock deviceLock;

    private long lastUpdateTime;

    public CommandCacheEntryFactory(final CommandImpl command, final DeviceLock deviceLock) {
        this.deviceLock = deviceLock;
        this.command = command;
    }

    @Override
    public Object createEntry(final Object key) throws DevFailed {
        logger.debug("Creating entry for key = {} , command {} ", key, command.getName());
        Object result = null;
        deviceLock.lockCommand();
        try {
            final long time1 = System.nanoTime();
            result = command.execute(null);
            final long now = System.nanoTime();
            final long nowMilli = System.currentTimeMillis();
            final long deltaTime = now - lastUpdateTime;
            lastUpdateTime = now;
            final long executionDuration = lastUpdateTime - time1;
            command.addToHistory(result);
            command.setPollingStats(executionDuration / NANO_TO_MILLI, nowMilli, deltaTime / NANO_TO_MILLI);
        } catch (final DevFailed e) {
            command.addErrorToHistory(e);
            throw e;
        } finally {
            deviceLock.lockCommand();
        }
        return result;
    }

}
