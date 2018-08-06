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

import fr.esrf.Tango.DevFailed;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;
import net.sf.ehcache.management.ManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.server.ServerManager;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.command.CommandImpl;
import org.tango.server.device.AroundInvokeImpl;
import org.tango.server.device.DeviceLocker;
import org.tango.server.properties.PropertiesUtils;
import org.tango.server.servant.DeviceImpl;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Manage cache for attributes/commands of a Tango device
 *
 * @author ABEILLE
 *
 */
public final class TangoCacheManager {

    private static final String POLLING_THREADS_POOL_CONF = "polling_threads_pool_conf";

    private static final Logger LOGGER = LoggerFactory.getLogger(TangoCacheManager.class);

    private static final int POOL_SIZE = 1;

    private static volatile ScheduledExecutorService POLLING_POOL = new ScheduledThreadPoolExecutor(POOL_SIZE,
            new TangoCacheThreadFactory());
    /**
     * Maintains the ordered list of polled device of the server
     */
    private static List<String> polledDevices = new LinkedList<String>();
    private volatile static CacheManager MANAGER;
    private static int poolSize = POOL_SIZE;
    private static Map<String, TangoCacheManager> cacheList = new HashMap<String, TangoCacheManager>();
    private final Map<AttributeImpl, AttributeCache> attributeCacheMap = new HashMap<AttributeImpl, AttributeCache>();
    private final Map<CommandImpl, CommandCache> commandCacheMap = new HashMap<CommandImpl, CommandCache>();
    /**
     * pollingPeriod==0 means that the polling is triggered externally
     */
    private final Map<AttributeImpl, AttributeCache> extTrigAttributeCacheMap = new HashMap<AttributeImpl, AttributeCache>();
    private final Map<CommandImpl, CommandCache> extTrigCommandCacheMap = new HashMap<CommandImpl, CommandCache>();
    private final DeviceLocker deviceLock;

    private final String deviceName;

    private final AroundInvokeImpl aroundInvoke;
    private StateStatusCache stateCache;
    private StateStatusCache statusCache;

    public TangoCacheManager(final String deviceName, final DeviceLocker deviceLock, final AroundInvokeImpl aroundInvoke) {
        this.deviceLock = deviceLock;
        this.deviceName = deviceName;
        this.aroundInvoke = aroundInvoke;
        cacheList.put(deviceName, this);
    }

    private static void startCache() {
        final Configuration config = new Configuration();
        config.setUpdateCheck(false);
        final CacheConfiguration defaultCacheConfiguration = new CacheConfiguration();
        defaultCacheConfiguration.setDiskPersistent(false);
        defaultCacheConfiguration.setOverflowToDisk(false);
        defaultCacheConfiguration.setMaxElementsInMemory(1);
        config.setDefaultCacheConfiguration(defaultCacheConfiguration);
        MANAGER = CacheManager.create(config);
        final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ManagementService.registerMBeans(MANAGER, mBeanServer, true, true, true, true, true);
        setPollSize(POOL_SIZE);
    }

    public static void shutdown() {
        if (MANAGER != null) {
            MANAGER.shutdown();
            MANAGER = null;
        }
        if (POLLING_POOL != null) {
            POLLING_POOL.shutdownNow();
            POLLING_POOL = null;
        }
    }

    /**
     * Retrieve the ordered list of polled devices
     *
     * @throws DevFailed
     */
    public static void initPoolConf() throws DevFailed {
        polledDevices.clear();
        final Map<String, String[]> prop = PropertiesUtils.getDeviceProperties(ServerManager.getInstance()
                .getAdminDeviceName());
        if (prop.containsKey(POLLING_THREADS_POOL_CONF)) {
            final String[] pollingThreadsPoolConf = prop.get(POLLING_THREADS_POOL_CONF);
            for (int i = 0; i < pollingThreadsPoolConf.length; i++) {
                if (cacheList.containsKey(pollingThreadsPoolConf[i]) && !pollingThreadsPoolConf[i].isEmpty()
                        && !polledDevices.contains(pollingThreadsPoolConf[i])) {
                    polledDevices.add(pollingThreadsPoolConf[i]);
                }
            }
        }
    }

    public static void setPollSize(final int poolSize) {
        if (poolSize > 0) {
            TangoCacheManager.poolSize = poolSize;
            for (final TangoCacheManager cache : cacheList.values()) {
                cache.stop();
            }
            if (POLLING_POOL != null) {
                POLLING_POOL.shutdownNow();
            }
            POLLING_POOL = new ScheduledThreadPoolExecutor(poolSize, new TangoCacheThreadFactory());
            for (final TangoCacheManager cache : cacheList.values()) {
                cache.start();
            }
            LOGGER.debug("polling pool size is {}", poolSize);
        }
    }

    public static int getPoolSize() {
        return poolSize;
    }

    public static List<String> getPolledDevices() {
        return polledDevices;
    }

    /**
     * Add the current device in polled list and persist it as device property of admin device. This property is not
     * used. Just here to have the same behavior as C++ Tango API.
     *
     * @throws DevFailed
     */
    private void updatePoolConf() throws DevFailed {
        if (!polledDevices.contains(deviceName)) {
            polledDevices.add(deviceName);
            final Map<String, String[]> properties = new HashMap<String, String[]>();
            properties.put(POLLING_THREADS_POOL_CONF, polledDevices.toArray(new String[0]));
            DatabaseFactory.getDatabase().setDeviceProperties(ServerManager.getInstance().getAdminDeviceName(),
                    properties);
        }
    }

    public synchronized void startStateStatusPolling(final CommandImpl command, final AttributeImpl attribute) {
        if (MANAGER == null) {
            startCache();
        }
        if (command.getName().equalsIgnoreCase(DeviceImpl.STATE_NAME)) {
            if (stateCache != null) {
                stateCache.stopRefresh();
            }
            stateCache = new StateStatusCache(MANAGER, command, attribute, deviceName, deviceLock, aroundInvoke);
            if (command.getPollingPeriod() != 0) {
                stateCache.startRefresh(POLLING_POOL);
            }
        } else if (command.getName().equalsIgnoreCase(DeviceImpl.STATUS_NAME)) {
            if (statusCache != null) {
                statusCache.stopRefresh();
            }
            statusCache = new StateStatusCache(MANAGER, command, attribute, deviceName, deviceLock, aroundInvoke);
            if (command.getPollingPeriod() != 0) {
                statusCache.startRefresh(POLLING_POOL);
            }
        }
    }

    /**
     * Start command polling
     *
     * @param command
     *            The command to poll
     * @throws DevFailed
     */
    public synchronized void startCommandPolling(final CommandImpl command) throws DevFailed {
        addCommandPolling(command);
        LOGGER.debug("starting command {} for polling on device {}", command.getName(), deviceName);
        if (command.getPollingPeriod() != 0) {
            commandCacheMap.get(command).startRefresh(POLLING_POOL);
        }
    }

    /**
     * Add command as polled
     *
     * @param command
     * @throws DevFailed
     */
    private void addCommandPolling(final CommandImpl command) throws DevFailed {
        if (MANAGER == null) {
            startCache();
        }
        removeCommandPolling(command);
        final CommandCache cache = new CommandCache(MANAGER, command, deviceName, deviceLock, aroundInvoke);
        if (command.getPollingPeriod() == 0) {
            extTrigCommandCacheMap.put(command, cache);
        } else {
            commandCacheMap.put(command, cache);
        }
        updatePoolConf();
    }

    /**
     * Start attribute polling
     *
     * @param attr
     *            The attribute to poll
     * @throws DevFailed
     */
    public synchronized void startAttributePolling(final AttributeImpl attr) throws DevFailed {
        addAttributePolling(attr);
        LOGGER.debug("starting attribute {} for polling on device {}", attr.getName(), deviceName);
        if (attr.getPollingPeriod() != 0) {
            attributeCacheMap.get(attr).startRefresh(POLLING_POOL);
        }
    }

    /**
     * Add attribute as polled
     *
     * @param attr
     * @throws DevFailed
     */
    private void addAttributePolling(final AttributeImpl attr) throws DevFailed {
        if (MANAGER == null) {
            startCache();
        }
        removeAttributePolling(attr);
        final AttributeCache cache = new AttributeCache(MANAGER, attr, deviceName, deviceLock, aroundInvoke);
        if (attr.getPollingPeriod() == 0) {
            extTrigAttributeCacheMap.put(attr, cache);
        } else {
            attributeCacheMap.put(attr, cache);
        }
        updatePoolConf();
    }

    /**
     * Remove polling of an attribute
     *
     * @param attr
     * @throws DevFailed
     */
    public synchronized void removeAttributePolling(final AttributeImpl attr) throws DevFailed {
        if (attributeCacheMap.containsKey(attr)) {
            final AttributeCache cache = attributeCacheMap.get(attr);
            cache.stopRefresh();
            attributeCacheMap.remove(attr);
        } else if (extTrigAttributeCacheMap.containsKey(attr)) {
            extTrigAttributeCacheMap.remove(attr);
        } else if (attr.getName().equalsIgnoreCase(DeviceImpl.STATE_NAME) && stateCache != null) {
            stateCache.stopRefresh();
            stateCache = null;
        } else if (attr.getName().equalsIgnoreCase(DeviceImpl.STATUS_NAME) && statusCache != null) {
            statusCache.stopRefresh();
            statusCache = null;
        }
    }

    /**
     * Remove all polling
     */
    public synchronized void removeAll() {
        for (final AttributeCache cache : attributeCacheMap.values()) {
            cache.stopRefresh();
        }
        attributeCacheMap.clear();
        extTrigAttributeCacheMap.clear();
        for (final CommandCache cache : commandCacheMap.values()) {
            cache.stopRefresh();
        }
        commandCacheMap.clear();
        extTrigCommandCacheMap.clear();
        if (stateCache != null) {
            stateCache.stopRefresh();
            stateCache = null;
        }
        if (statusCache != null) {
            statusCache.stopRefresh();
            statusCache = null;
        }
        cacheList.remove(deviceName);
    }

    /**
     * Remove polling of a command
     *
     * @param command
     * @throws DevFailed
     */
    public synchronized void removeCommandPolling(final CommandImpl command) throws DevFailed {
        if (commandCacheMap.containsKey(command)) {
            final CommandCache cache = commandCacheMap.get(command);
            cache.stopRefresh();
            commandCacheMap.remove(command);
        } else if (extTrigCommandCacheMap.containsKey(command)) {
            extTrigCommandCacheMap.remove(command);
        } else if (command.getName().equalsIgnoreCase(DeviceImpl.STATE_NAME) && stateCache != null) {
            stateCache.stopRefresh();
            stateCache = null;
        } else if (command.getName().equalsIgnoreCase(DeviceImpl.STATUS_NAME) && statusCache != null) {
            statusCache.stopRefresh();
            statusCache = null;
        }
    }

    /**
     * Start all polling
     */
    public synchronized void start() {
        for (final AttributeCache cache : attributeCacheMap.values()) {
            cache.startRefresh(POLLING_POOL);
        }
        for (final CommandCache cache : commandCacheMap.values()) {
            cache.startRefresh(POLLING_POOL);
        }
        if (stateCache != null) {
            stateCache.startRefresh(POLLING_POOL);
        }
        if (statusCache != null) {
            statusCache.startRefresh(POLLING_POOL);
        }
    }

    /**
     * Stop all polling
     */
    public synchronized void stop() {
        for (final AttributeCache cache : attributeCacheMap.values()) {
            cache.stopRefresh();
        }
        for (final CommandCache cache : commandCacheMap.values()) {
            cache.stopRefresh();
        }
        if (stateCache != null) {
            stateCache.stopRefresh();
        }
        if (statusCache != null) {
            statusCache.stopRefresh();
        }
        if (POLLING_POOL != null) {
            POLLING_POOL.shutdownNow();
            POLLING_POOL = new ScheduledThreadPoolExecutor(poolSize, new TangoCacheThreadFactory());
        }
    }

    /**
     * Get cache of an attribute
     *
     * @param attr
     *            the attribute
     * @return the attribute cache
     * @throws NoCacheFoundException if cache for the attribute is not found
     */
    public synchronized SelfPopulatingCache getAttributeCache(final AttributeImpl attr) throws NoCacheFoundException {
        if (attr.getName().equalsIgnoreCase(DeviceImpl.STATE_NAME)) {
            return stateCache.getCache();
        } else if (attr.getName().equalsIgnoreCase(DeviceImpl.STATUS_NAME)) {
            return statusCache.getCache();
        } else {
            return tryGetAttributeCache(attr);
        }
    }

    private SelfPopulatingCache tryGetAttributeCache(final AttributeImpl attr) throws NoCacheFoundException {
        AttributeCache attrCache = attributeCacheMap.get(attr);
        if (attrCache == null) {
            attrCache = extTrigAttributeCacheMap.get(attr);
            if (attrCache == null)
                throw new NoCacheFoundException("No cache found for " + attr.getName());
        }
        return attrCache.getCache();
    }

    /**
     * Get cache of a command
     *
     * @param cmd
     *            The command
     * @return The command cache
     */
    public synchronized SelfPopulatingCache getCommandCache(final CommandImpl cmd) {
        SelfPopulatingCache cache = null;
        if (cmd.getName().equalsIgnoreCase(DeviceImpl.STATE_NAME)) {
            cache = stateCache.getCache();
        } else if (cmd.getName().equalsIgnoreCase(DeviceImpl.STATUS_NAME)) {
            cache = statusCache.getCache();
        } else {
            CommandCache cmdCache = commandCacheMap.get(cmd);
            if (cmdCache == null) {
                cmdCache = extTrigCommandCacheMap.get(cmd);
            }
            cache = cmdCache.getCache();
        }
        return cache;
    }

}
