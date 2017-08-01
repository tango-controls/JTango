package org.tango.client.database;

import fr.esrf.Tango.DevFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.client.database.cache.DatabaseCache;
import org.tango.client.database.cache.ICachableDatabase;
import org.tango.client.database.cache.NoCacheDatabase;
import org.tango.utils.DevFailedUtils;

import java.util.Map;

/**
 * Client of the tango database device.
 * 
 * @author ABEILLE
 * 
 */
public final class Database implements ITangoDB {
    private final Logger logger = LoggerFactory.getLogger(Database.class);
    private final fr.esrf.TangoApi.Database database;
    private ICachableDatabase cache;

    /**
     * Ctr
     * 
     * @param host
     *            host name of the tango db
     * @param port
     *            port number of the tango db
     * @throws DevFailed
     */
    Database(final String host, final String port) throws DevFailed {
        database = new fr.esrf.TangoApi.Database(host, port);
        final NoCacheDatabase noCache = new NoCacheDatabase(host, port);
        try {
            final DatabaseCache tmp = new DatabaseCache(database, noCache);
            if (tmp.isCacheAvailable()) {
                cache = tmp;
            } else {
                logger.warn("database cache version {} not supported, please install it to have better performance",
                        tmp.getVersion());
                cache = noCache;
            }
        } catch (final DevFailed e) {
            logger.error("error loading db cache, {} ", DevFailedUtils.toString(e));
            cache = noCache;
        } catch (final Exception e) {
            // TODO: should be removed, indicate a bug in cache loading
            logger.error("error loading db cache", e);
            cache = noCache;
        }
    }

    /**
     * Export a tango device into the tango db (execute DbExportDevice on DB device)
     * 
     * @param info
     *            export info {@link DeviceExportInfo}
     * @throws DevFailed
     */
    @Override
    public void exportDevice(final DeviceExportInfo info) throws DevFailed {
        cache.exportDevice(info);
    }

    /**
     * Get the list of instance for an executable.(execute DbGetInstanceNameList on DB device)
     * 
     * @param dsExecName
     *            The executable name
     * @return the list of instance
     * @throws DevFailed
     */
    @Override
    public String[] getInstanceNameList(final String dsExecName) throws DevFailed {
        return cache.getInstanceNameList(dsExecName);
    }

    /**
     * Import a tango device from the tango db (execute DbImportDevice on DB device)
     * 
     * @param toBeImported
     *            the device to import
     * @return {@link DeviceImportInfo}
     * @throws DevFailed
     */
    @Override
    public DeviceImportInfo importDevice(final String toBeImported) throws DevFailed {
        return cache.importDevice(toBeImported);
    }

    /**
     * Export a server into the tango db (execute DbUnExportServer on DB device)
     * 
     * @param serverName
     *            The server name
     * @throws DevFailed
     */
    @Override
    public void unexportServer(final String serverName) throws DevFailed {
        cache.unexportServer(serverName);
    }

    /**
     * Get the list of device for a server and a class (execute DbGetDeviceList on DB device)
     * 
     * @param serverName
     *            The server name
     * @param className
     *            The class name
     * @return
     * @throws DevFailed
     */
    @Override
    public String[] getDeviceList(final String serverName, final String className) throws DevFailed {
        return cache.getDeviceList(serverName, className);
    }

    /**
     * Get some properties value for a device. (execute DbGetDevicePropertyList on DB device)
     * 
     * @param name
     *            The device name
     * @param propertyNames
     *            The list of properties to retrieve. Empty if all
     * @return
     * @throws DevFailed
     */
    @Override
    public Map<String, String[]> getDeviceProperties(final String name, final String... propertyNames) throws DevFailed {
        return cache.getDeviceProperties(name, propertyNames);
    }

    /**
     * Set values of device properties. (execute DbPutDeviceProperty on DB device)
     * 
     * @param deviceName
     *            The device name
     * @param properties
     *            The properties names and their values
     * @throws DevFailed
     */
    @Override
    public void setDeviceProperties(final String deviceName, final Map<String, String[]> properties) throws DevFailed {
        cache.setDeviceProperties(deviceName, properties);
    }

    /**
     * Get a class properties. (execute DbGetClassPropertyList on DB device)
     * 
     * @param name
     *            The class name
     * @param propertyNames
     *            The properties names
     * @return
     * @throws DevFailed
     */
    @Override
    public Map<String, String[]> getClassProperties(final String name, final String... propertyNames) throws DevFailed {
        return cache.getClassProperties(name, propertyNames);
    }

    /**
     * Set a tango class properties. (execute DbPutClassProperty on DB device)
     * 
     * @param name
     *            The class name
     * @param properties
     *            The properties names and values.
     * @throws DevFailed
     */
    @Override
    public void setClassProperties(final String name, final Map<String, String[]> properties) throws DevFailed {
        cache.setClassProperties(name, properties);
    }

    /**
     * Get an attribute properties. (execute DbGetDeviceAttributeProperty2 on DB device)
     * 
     * @param deviceName
     *            The device name
     * @param attributeName
     *            The attribute name
     * @return
     * @throws DevFailed
     */
    @Override
    public Map<String, String[]> getAttributeProperties(final String deviceName, final String attributeName)
            throws DevFailed {
        return cache.getAttributeProperties(deviceName, attributeName);

    }

    /**
     * Set some attribute properties. (execute DbPutDeviceAttributeProperty2 on DB device)
     * 
     * @param deviceName
     *            The device name
     * @param attributeName
     *            The attribute name
     * @param properties
     *            The properties names and values.
     * @throws DevFailed
     */
    @Override
    public void setAttributeProperties(final String deviceName, final String attributeName,
            final Map<String, String[]> properties) throws DevFailed {
        cache.setAttributeProperties(deviceName, attributeName, properties);
    }

    @Override
    public void deleteDeviceProperty(final String deviceName, final String propertyName) throws DevFailed {
        cache.deleteDeviceProperty(deviceName, propertyName);
    }

    @Override
    public void loadCache(final String serverName, final String hostName) throws DevFailed {
        cache.loadCache(serverName, hostName);
    }

    @Override
    public String getAccessDeviceName() throws DevFailed {
        return cache.getAccessDeviceName();
    }

    @Override
    public void clearCache() {
        cache.clearCache();
    }

    @Override
    public void deleteAttributeProperties(final String deviceName, final String... attributeNames) throws DevFailed {
        cache.deleteAttributeProperties(deviceName, attributeNames);
    }

    @Override
    public String[] getPossibleTangoHosts() throws DevFailed {
        return cache.getPossibleTangoHosts();
    }

    @Override
    public String getFreeProperty(final String name, final String propertyName) throws DevFailed {
        return cache.getFreeProperty(name, propertyName);
    }

    @Override
    public Map<String, String[]> getDevicePipeProperties(final String deviceName, final String pipeName)
            throws DevFailed {
        return cache.getDevicePipeProperties(deviceName, pipeName);
    }

    @Override
    public void setDevicePipeProperties(final String deviceName, final String pipeName,
            final Map<String, String[]> properties) throws DevFailed {
        cache.setDevicePipeProperties(deviceName, pipeName, properties);
    }

    @Override
    public Map<String, String[]> getClassPipeProperties(final String className, final String pipeName) throws DevFailed {
        return cache.getClassPipeProperties(className, pipeName);
    }

    @Override
    public void setClassPipeProperties(final String className, final String pipeName,
            final Map<String, String[]> properties) throws DevFailed {
        cache.setClassPipeProperties(className, pipeName, properties);
    }

    @Override
    public void deleteDevicePipeProperties(final String deviceName, final String... pipeNames) throws DevFailed {
        cache.deleteDevicePipeProperties(deviceName, pipeNames);

    }

}
