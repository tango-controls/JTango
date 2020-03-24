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
package org.tango.server.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.command.CommandImpl;
import org.tango.server.device.AroundInvokeImpl;
import org.tango.server.device.DeviceLocker;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class StateStatusCache {
    private final Logger logger = LoggerFactory.getLogger(StateStatusCache.class);
    private final SelfPopulatingCache cache;
    private final CommandImpl command;
    private ScheduledFuture<?> result;

    public StateStatusCache(final CacheManager manager, final CommandImpl command, final AttributeImpl attribute,
                            final String deviceName, final DeviceLocker deviceLock, final AroundInvokeImpl aroundInvoke) {
        this.command = command;
        final String cacheName = "stateStatusTangoPollingCache." + deviceName + "/" + command.getName();
        Cache defaultCache = manager.getCache(cacheName);
        if (defaultCache == null) {
            manager.addCache(cacheName);
            defaultCache = manager.getCache(cacheName);
            // defaultCache.setStatisticsEnabled(true);
        }
        defaultCache.flush();
        cache = new SelfPopulatingCache(defaultCache, new StateStatusCacheEntryFactory(command, attribute, deviceLock,
                deviceName, aroundInvoke));
        cache.getCacheConfiguration().setTimeToLiveSeconds(60);

    }

    public void startRefresh(final ScheduledExecutorService pollingPool) {
        if (result == null) {
            logger.debug("start refresh cache of {} at period of {}", command.getName(), command.getPollingPeriod());
            final CacheRefresher refresher = new CacheRefresher(cache, command.getName());
            result = pollingPool.scheduleAtFixedRate(refresher, 0L, command.getPollingPeriod(), TimeUnit.MILLISECONDS);
        }
    }

    public void stopRefresh() {
        if (result != null) {
            logger.debug("stop refresh cache of {}", command.getName());
            final boolean isCancelled = result.cancel(true);
            if (!isCancelled) {
                logger.error("stop refresh NOT CANCELLED");
                // throw DevFailedUtils.newDevFailed("STOP_REFRESH",
                // "error stopping refresh of "
                // + attribute.getName());
            }
            result = null;
        }
    }

    public SelfPopulatingCache getCache() {
        return cache;
    }
}
