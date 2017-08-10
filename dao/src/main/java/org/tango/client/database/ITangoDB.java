package org.tango.client.database;

import fr.esrf.Tango.DevFailed;

import java.util.Map;

public interface ITangoDB {
    /**
     * Load a cache of a server if possible
     * 
     * @param serverName
     * @param hostName
     */
    void loadCache(String serverName, String hostName) throws DevFailed;

    /**
     * Clear all cache
     */
    void clearCache();

    /**
     * Export a tango device into the tango db (execute DbExportDevice on DB device)
     * 
     * @param info
     *            export info {@link DeviceExportInfo}
     * @throws DevFailed
     */
    void exportDevice(final DeviceExportInfo info) throws DevFailed;

    /**
     * Get the list of instance for an executable.(execute DbGetInstanceNameList on DB device)
     * 
     * @param dsExecName
     *            The executable name
     * @return the list of instance
     * @throws DevFailed
     */
    String[] getInstanceNameList(final String dsExecName) throws DevFailed;

    /**
     * Import a tango device from the tango db (execute DbImportDevice on DB device)
     * 
     * @param toBeImported
     *            the device to import
     * @return {@link DeviceImportInfo}
     * @throws DevFailed
     */
    DeviceImportInfo importDevice(final String toBeImported) throws DevFailed;

    /**
     * Export a server into the tango db (execute DbUnExportServer on DB device)
     * 
     * @param serverName
     *            The server name
     * @throws DevFailed
     */
    void unexportServer(final String serverName) throws DevFailed;

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
    String[] getDeviceList(final String serverName, final String className) throws DevFailed;

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
    Map<String, String[]> getDeviceProperties(final String name, final String... propertyNames) throws DevFailed;

    /**
     * Set values of device properties. (execute DbPutDeviceProperty on DB device)
     * 
     * @param deviceName
     *            The device name
     * @param properties
     *            The properties names and their values
     * @throws DevFailed
     */
    void setDeviceProperties(final String deviceName, final Map<String, String[]> properties) throws DevFailed;

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
    Map<String, String[]> getClassProperties(final String name, final String... propertyNames) throws DevFailed;

    /**
     * Set a tango class properties. (execute DbPutClassProperty on DB device)
     * 
     * @param name
     *            The class name
     * @param properties
     *            The properties names and values.
     * @throws DevFailed
     */
    void setClassProperties(final String name, final Map<String, String[]> properties) throws DevFailed;

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
    Map<String, String[]> getAttributeProperties(final String deviceName, final String attributeName) throws DevFailed;

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
    void setAttributeProperties(final String deviceName, final String attributeName,
            final Map<String, String[]> properties) throws DevFailed;

    /**
     * Remove a device property from tango DB
     * 
     * @param deviceName
     *            The device name
     * @param propertyName
     *            The property to remove
     * @throws DevFailed
     */
    void deleteDeviceProperty(String deviceName, String propertyName) throws DevFailed;

    /**
     * Remove attribute properties
     * 
     * @param deviceName
     *            The device name
     * @param attributeNames
     *            The attribute names (if empty, remove all)
     * @throws DevFailed
     */
    void deleteAttributeProperties(String deviceName, String... attributeNames) throws DevFailed;

    /**
     * Ask the database for all possible tango hosts
     * 
     * @return
     */
    String[] getPossibleTangoHosts() throws DevFailed;

    String getAccessDeviceName() throws DevFailed;

    String getFreeProperty(String name, String propertyName) throws DevFailed;

    /**
     * 
     */
    Map<String, String[]> getDevicePipeProperties(String deviceName, String pipeName) throws DevFailed;

    void setDevicePipeProperties(String deviceName, String pipeName, Map<String, String[]> properties) throws DevFailed;

    Map<String, String[]> getClassPipeProperties(String className, String pipeName) throws DevFailed;

    void setClassPipeProperties(String className, String pipeName, Map<String, String[]> properties) throws DevFailed;

    void deleteDevicePipeProperties(String deviceName, String... pipeNames) throws DevFailed;

    // List<String> getDevicePipeList(String deviceName, String wildcard) throws DevFailed;

    // List<String> getClassPipeList(String className, String wildcard) throws DevFailed;
    // void deleteDevicePipeProperties( String deviceName, String pipeName, List<String> propertyNames) throws DevFailed
    // void deleteClassPipeProperties(String className, String pipeName, List<String> propertyNames) throws DevFailed
    // void deleteDevicePipe( String deviceName, String pipeName) throws DevFailed
    // void deleteClassPipe( String className, String pipeName)
}
