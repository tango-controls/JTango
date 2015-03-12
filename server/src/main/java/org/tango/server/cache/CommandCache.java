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

import java.util.Locale;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.command.CommandImpl;
import org.tango.server.device.AroundInvokeImpl;
import org.tango.server.device.DeviceLocker;

public final class CommandCache {
    private final Logger logger = LoggerFactory.getLogger(CommandCache.class);

    private ScheduledFuture<?> result;
    private final SelfPopulatingCache cache;
    private final CommandImpl command;

    public CommandCache(final CacheManager manager, final CommandImpl command, final String deviceName,
            final DeviceLocker deviceLock, final AroundInvokeImpl aroundInvoke) {
        this.command = command;
        final String cacheName = "cmdTangoPollingCache." + deviceName + "/" + command.getName();
        Cache defaultCache = manager.getCache(cacheName);
        if (defaultCache == null) {
            manager.addCache(cacheName);
            defaultCache = manager.getCache(cacheName);
            // defaultCache.setStatisticsEnabled(true);
        }
        defaultCache.flush();
        cache = new SelfPopulatingCache(defaultCache, new CommandCacheEntryFactory(command, deviceLock, aroundInvoke));
        cache.getCacheConfiguration().setTimeToLiveSeconds(60);

    }

    public void startRefresh(final ScheduledExecutorService pollingPool) {
        logger.debug("start command refresh cache of {} at a rate of {} ms", command.getName(),
                command.getPollingPeriod());
        final CacheRefresher refresher = new CacheRefresher(cache, command.getName().toLowerCase(Locale.ENGLISH));
        result = pollingPool.scheduleAtFixedRate(refresher, 0L, command.getPollingPeriod(), TimeUnit.MILLISECONDS);
    }

    public void stopRefresh() {
        if (result != null) {
            logger.debug("stop refresh cache of {}", command.getName());
            result.cancel(true);
        }
    }

    public SelfPopulatingCache getCache() {
        return cache;
    }
}
