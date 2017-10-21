package org.tango.client.database.cache;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeProxy;
import fr.esrf.TangoApi.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.client.database.DeviceExportInfo;
import org.tango.client.database.DeviceImportInfo;
import org.tango.utils.CaseInsensitiveMap;
import org.tango.utils.DevFailedUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 *
 * @author ABEILLE
 *
 */
public final class DatabaseCache implements ICachableDatabase {
    private static final String RELEASE_1_X = "release 1\\.[7-9]";
    private final Logger logger = LoggerFactory.getLogger(ServerCache.class);
    private ServerCache serverCache;

    private boolean isCacheAvailable;
    private final NoCacheDatabase dbDevice;
    private final String version;
    private final Connection database;

    public DatabaseCache(final Connection database, final NoCacheDatabase dbDevice) throws DevFailed {
        this.dbDevice = dbDevice;
        // check version of stored procedure
        final AttributeProxy attr = new AttributeProxy(database.get_device().name() + "/StoredProcedureRelease");
        version = attr.read().extractString();
        logger.debug("current database cache version {}", version);
        if (Pattern.matches(RELEASE_1_X, version)) {
            isCacheAvailable = true;
        } else {
            isCacheAvailable = false;
        }
        this.database = database;

    }

    @Override
    public void loadCache(final String serverName, final String hostName) throws DevFailed {
        if (isCacheAvailable) {
            try {
                serverCache = new ServerCache(database);
                serverCache.fillCache(serverName, hostName);
            } catch (final DevFailed e) {
                serverCache = null;
                logger.error("Failed to load cache for server {} on host {}", serverName, hostName);
                DevFailedUtils.logDevFailed(e, logger);
            } catch (final Throwable e) {
                serverCache = null;
                logger.error("Failed to load cache, do not use it", e);
            }
        }
    }

    @Override
    public void clearCache() {
        serverCache = null;
    }

    @Override
    public void exportDevice(final DeviceExportInfo info) throws DevFailed {
        dbDevice.exportDevice(info);

    }

    @Override
    public String[] getInstanceNameList(final String dsExecName) throws DevFailed {
        return dbDevice.getInstanceNameList(dsExecName);
    }

    @Override
    public DeviceImportInfo importDevice(final String toBeImported) throws DevFailed {
        return dbDevice.importDevice(toBeImported);
    }

    @Override
    public void unexportServer(final String serverName) throws DevFailed {
        dbDevice.unexportServer(serverName);
    }

    @Override
    public String[] getDeviceList(final String serverName, final String className) throws DevFailed {
        return dbDevice.getDeviceList(serverName, className);
    }

    @Override
    public Map<String, String[]> getDeviceProperties(final String name, final String... propertyNames) throws DevFailed {
        Map<String, String[]> result = new HashMap<String, String[]>();
        if (serverCache == null) {
            result = dbDevice.getDeviceProperties(name, propertyNames);
        } else {
            final DeviceCache deviceCache = serverCache.getDeviceCache(name);
            if (deviceCache != null) {
                final Map<String, String[]> props = deviceCache.getPropertiesCache();
                if (propertyNames.length == 0 || props.size() == propertyNames.length) {
                    result = props;
                } else {
                    for (final String propertyName : propertyNames) {
                        result.put(propertyName, props.get(propertyName));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void setDeviceProperties(final String deviceName, final Map<String, String[]> properties) throws DevFailed {
        if (serverCache != null) {
            DeviceCache deviceCache = serverCache.getDeviceCache(deviceName);
            if (deviceCache == null) {
                deviceCache = new DeviceCache(deviceName);
            }
            deviceCache.addProperties(properties);
        }
        dbDevice.setDeviceProperties(deviceName, properties);
    }

    @Override
    public Map<String, String[]> getClassProperties(final String name, final String... propertyNames) throws DevFailed {
        Map<String, String[]> result = new HashMap<String, String[]>();
        if (serverCache == null) {
            result = dbDevice.getClassProperties(name, propertyNames);
        } else {
            final ClassCache classCache = serverCache.getClassCache(name);
            if (classCache != null) {
                final Map<String, String[]> props = classCache.getPropertiesCache();
                if (propertyNames.length == 0 || props.size() == propertyNames.length) {
                    result = props;
                } else {
                    for (final String propertyName : propertyNames) {
                        result.put(propertyName, props.get(propertyName));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void setClassProperties(final String name, final Map<String, String[]> properties) throws DevFailed {
        if (serverCache != null) {
            ClassCache classCache = serverCache.getClassCache(name);
            if (classCache == null) {
                classCache = new ClassCache(name);
            }
            classCache.addProperties(properties);
        }
        dbDevice.setClassProperties(name, properties);
    }

    @Override
    public Map<String, String[]> getAttributeProperties(final String deviceName, final String attributeName)
            throws DevFailed {
        Map<String, String[]> result = new CaseInsensitiveMap<String[]>();
        if (serverCache == null) {
            result = dbDevice.getAttributeProperties(deviceName, attributeName);
        } else {
            final DeviceCache deviceCache = serverCache.getDeviceCache(deviceName);
            if (deviceCache != null) {
                final AttributeCache attributeCache = deviceCache.getAttributeCache(attributeName);
                if (attributeCache != null) {
                    final Map<String, String[]> props = attributeCache.getPropertiesCache();
                    result = props;
                }
            }
        }
        return result;
    }

    @Override
    public void setAttributeProperties(final String deviceName, final String attributeName,
            final Map<String, String[]> properties) throws DevFailed {
        if (serverCache != null) {
            DeviceCache deviceCache = serverCache.getDeviceCache(deviceName);
            if (deviceCache == null) {
                deviceCache = new DeviceCache(deviceName);
            }
            AttributeCache attributeCache = deviceCache.getAttributeCache(attributeName);
            if (attributeCache == null) {
                attributeCache = new AttributeCache(attributeName);
            }
            attributeCache.addProperties(properties);
        }
        dbDevice.setAttributeProperties(deviceName, attributeName, properties);
    }

    @Override
    public void deleteDeviceProperty(final String deviceName, final String propertyName) throws DevFailed {
        if (serverCache != null) {
            final DeviceCache deviceCache = serverCache.getDeviceCache(deviceName);
            if (deviceCache != null) {
                deviceCache.removeProperty(propertyName);
            }
        }
        dbDevice.deleteDeviceProperty(deviceName, propertyName);
    }

    @Override
    public boolean isCacheAvailable() {
        return isCacheAvailable;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getAccessDeviceName() throws DevFailed {
        return dbDevice.getAccessDeviceName();
    }

    @Override
    public void deleteAttributeProperties(final String deviceName, final String... attributeNames) throws DevFailed {
        if (serverCache != null) {
            final DeviceCache deviceCache = serverCache.getDeviceCache(deviceName);
            if (deviceCache != null) {
                for (final String attributeName : attributeNames) {
                    final AttributeCache attributeCache = deviceCache.getAttributeCache(attributeName);
                    if (attributeCache != null) {
                        attributeCache.removeAll();
                    }
                }
            }

        }
        dbDevice.deleteAttributeProperties(deviceName, attributeNames);
    }

    @Override
    public String[] getPossibleTangoHosts() throws DevFailed {
        return dbDevice.getPossibleTangoHosts();
    }

    @Override
    public String getFreeProperty(final String name, final String propertyName) throws DevFailed {
        // TODO get from cache
        return dbDevice.getFreeProperty(name, propertyName);
    }

    @Override
    public Map<String, String[]> getDevicePipeProperties(final String deviceName, final String pipeName)
            throws DevFailed {
        // TODO cache
        return dbDevice.getDevicePipeProperties(deviceName, pipeName);
    }

    @Override
    public void setDevicePipeProperties(final String deviceName, final String pipeName,
            final Map<String, String[]> properties) throws DevFailed {
        // TODO cache
        dbDevice.setDevicePipeProperties(deviceName, pipeName, properties);
    }

    @Override
    public Map<String, String[]> getClassPipeProperties(final String className, final String pipeName) throws DevFailed {
        // TODO cache
        return dbDevice.getClassPipeProperties(className, pipeName);
    }

    @Override
    public void setClassPipeProperties(final String className, final String pipeName,
            final Map<String, String[]> properties) throws DevFailed {
        // TODO cache
        dbDevice.setClassPipeProperties(className, pipeName, properties);
    }

    @Override
    public void deleteDevicePipeProperties(final String deviceName, final String... pipeNames) throws DevFailed {
        // TODO cache
        dbDevice.deleteDevicePipeProperties(deviceName, pipeNames);
    }

}
