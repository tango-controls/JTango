package org.tango.client.database.cache;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.DbDatum;
import fr.esrf.TangoApi.DeviceData;
import org.apache.commons.lang3.ArrayUtils;
import org.tango.TangoHostManager;
import org.tango.client.database.DeviceExportInfo;
import org.tango.client.database.DeviceImportInfo;
import org.tango.utils.CaseInsensitiveMap;

import java.util.*;

public final class NoCacheDatabase implements ICachableDatabase {

    private final fr.esrf.TangoApi.Database database;

    private enum PropertyType {
        Class, Device;
    }

    /**
     * Ctr
     *
     * @param host
     *            host name of the tango db
     * @param port
     *            port number of the tango db
     * @throws DevFailed
     */
    public NoCacheDatabase(final String host, final String port) throws DevFailed {
        database = new fr.esrf.TangoApi.Database(host, port);
        // force check access control
        database.isCommandAllowed("database", "DbImportDevice");
    }

    /**
     * Export a tango device into the tango db (execute DbExportDevice on DB
     * device)
     *
     * @param info
     *            export info {@link DeviceExportInfo}
     * @throws DevFailed
     */
    @Override
    public void exportDevice(final DeviceExportInfo info) throws DevFailed {
        final String[] array = info.toStringArray();
        final DeviceData argin = new DeviceData();
        argin.insert(array);
        database.command_inout("DbExportDevice", argin);
    }

    /**
     * Get the list of instance for an executable.(execute DbGetInstanceNameList
     * on DB device)
     *
     * @param dsExecName
     *            The executable name
     * @return the list of instance
     * @throws DevFailed
     */
    @Override
    public String[] getInstanceNameList(final String dsExecName) throws DevFailed {
        final DeviceData argin = new DeviceData();
        argin.insert(dsExecName);
        final DeviceData argout = database.command_inout("DbGetInstanceNameList", argin);
        return argout.extractStringArray();
    }

    /**
     * Import a tango device from the tango db (execute DbImportDevice on DB
     * device)
     *
     * @param toBeImported
     *            the device to import
     * @return {@link DeviceImportInfo}
     * @throws DevFailed
     */
    @Override
    public DeviceImportInfo importDevice(final String toBeImported) throws DevFailed {
        final DeviceData argin = new DeviceData();
        argin.insert(toBeImported);
        final DeviceData argout = database.command_inout("DbImportDevice", argin);
        final DevVarLongStringArray info = argout.extractLongStringArray();
        return new DeviceImportInfo(info);
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
        final DeviceData argin = new DeviceData();
        argin.insert(serverName);
        database.command_inout("DbUnExportServer", argin);
    }

    /**
     * Get the list of device for a server and a class (execute DbGetDeviceList
     * on DB device)
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
        final DeviceData send = new DeviceData();
        send.insert(new String[] { serverName, className });
        final DeviceData received = database.command_inout("DbGetDeviceList", send);
        return received.extractStringArray();
    }

    /**
     * Get some properties value for a device. (execute DbGetDevicePropertyList
     * on DB device)
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
        Map<String, String[]> map;
        if (propertyNames.length == 0) {
            map = getProperties(PropertyType.Device, name);
        } else {
            map = getProperty(PropertyType.Device, name, propertyNames);
        }
        return map;
    }

    /**
     * Set values of device properties. (execute DbPutDeviceProperty on DB
     * device)
     *
     * @param deviceName
     *            The device name
     * @param properties
     *            The properties names and their values
     * @throws DevFailed
     */
    @Override
    public void setDeviceProperties(final String deviceName, final Map<String, String[]> properties) throws DevFailed {
        final List<String> args = getArray(properties, deviceName);
        final DeviceData argin = new DeviceData();
        argin.insert(args.toArray(new String[args.size()]));
        database.command_inout("DbPutDeviceProperty", argin);
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
        Map<String, String[]> map;
        if (propertyNames.length == 0) {
            map = getProperties(PropertyType.Class, name);
        } else {
            map = getProperty(PropertyType.Class, name, propertyNames);
        }
        return map;
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
        final List<String> args = getArray(properties, name);
        final DeviceData argin = new DeviceData();
        argin.insert(args.toArray(new String[args.size()]));
        database.command_inout("DbPutClassProperty", argin);
    }

    /**
     * Get an attribute properties. (execute DbGetDeviceAttributeProperty2 on DB
     * device)
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
        Map<String, String[]> map;
        final DeviceData argin = new DeviceData();
        final List<String> args = new ArrayList<String>();
        args.add(deviceName);
        args.add(attributeName);
        // value is an array
        argin.insert(args.toArray(new String[args.size()]));
        final DeviceData argout = database.command_inout("DbGetDeviceAttributeProperty2", argin);
        final String[] result = argout.extractStringArray();
        map = extractArgout(result, 4);
        return map;

    }

    /**
     * Set some attribute properties. (execute DbPutDeviceAttributeProperty2 on
     * DB device)
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
        final DeviceData argin = new DeviceData();
        final List<String> args = new ArrayList<String>();
        args.add(deviceName);
        args.add(Integer.toString(1)); // attribute number
        args.add(attributeName);
        args.add(Integer.toString(properties.size())); // property number
        for (final Map.Entry<String, String[]> entry : properties.entrySet()) {
            args.add(entry.getKey());
            final String[] propValues = entry.getValue();
            args.add(Integer.toString(propValues.length));
            for (final String propValue : propValues) {
                args.add(propValue);
            }
        }
        argin.insert(args.toArray(new String[args.size()]));
        database.command_inout("DbPutDeviceAttributeProperty2", argin);
    }

    private Map<String, String[]> getProperties(final PropertyType type, final String devOrClass) throws DevFailed {
        Map<String, String[]> map;
        final DeviceData argin = new DeviceData();
        if (type.equals(PropertyType.Class)) {
            argin.insert(devOrClass);
        } else {
            argin.insert(new String[] { devOrClass, "*" });
        }
        final DeviceData argout = database.command_inout("DbGet" + type + "PropertyList", argin);
        final List<String> properties = new ArrayList<String>(Arrays.asList(argout.extractStringArray()));

        // remove defaut properties and device name that is returned!
        properties.remove("logging_level");
        properties.remove("logging_target");
        properties.remove("poll_old_factor");
        properties.remove("poll_ring_depth");
        properties.remove(devOrClass);
        if (properties.size() > 0) {
            map = getProperty(type, devOrClass, properties.toArray(new String[properties.size()]));
        } else {
            map = new HashMap<String, String[]>();
        }
        return map;
    }

    private Map<String, String[]> getProperty(final PropertyType type, final String devOrClass,
            final String... propNames) throws DevFailed {
        // Format input parameters as string array
        final String[] array = ArrayUtils.add(propNames, 0, devOrClass);

        // Read Database
        final DeviceData argin = new DeviceData();
        argin.insert(array);
        final DeviceData argout = database.command_inout("DbGet" + type + "Property", argin);
        final String[] result = argout.extractStringArray();
        // {deviceName,nbProps,propName1,propSize1,values1,...propNameN,propSizeN,valuesN}
        // System.out.println("result " + Arrays.toString(result));
        // System.out.println(result.length);
        return extractArgout(result, 2);
    }

    /**
     * Return a map of properties with name as key and value as entry
     *
     * @param result
     *            Array {propName, propSize, values...,propNameN, propSizeN,
     *            valuesN}
     * @param startingPoint
     * @return
     */
    private Map<String, String[]> extractArgout(final String[] result, final int startingPoint) {
        final Map<String, String[]> map = new CaseInsensitiveMap<String[]>();
        if (result.length > 4) {
            // System.out.println(result[0]);// device name
            // System.out.println(Integer.valueOf(result[1]));// prop size
            // System.out.println(result[2]);// prop name
            int i = startingPoint;
            int nextSize = Integer.valueOf(result[i + 1]);

            while (i < result.length) {
                // System.out.println("i " + i);
                if (nextSize > 0) {
                    final String name = result[i].toLowerCase(Locale.ENGLISH);
                    final String[] propValues = Arrays.copyOfRange(result, i + 2, nextSize + i + 2);
                    map.put(name, propValues);
//                    System.out.println("name " + name);
//                    System.out.println("prop1 " + Arrays.toString(propValues));
                }
                final int idxNextPropSize;
                if (nextSize == 0) {
                    // System.out.println("zero");
                    idxNextPropSize = i + 4;
                    i = i + 3;
                } else {
                    idxNextPropSize = nextSize + i + 3;
                    i = nextSize + i + 2;
                }
                // System.out.println("idxNextPropSize " + idxNextPropSize);

                if (idxNextPropSize >= result.length) {
                    break;
                }

                // System.out.println("idxNextPropSize value " +
                // result[idxNextPropSize]);
                nextSize = Integer.valueOf(result[idxNextPropSize]);
                // System.out.println("nextSize " + nextSize);
            }
        }
        return map;
    }

    @Override
    public void deleteDeviceProperty(final String deviceName, final String propertyName) throws DevFailed {
        final DeviceData argin = new DeviceData();
        argin.insert(new String[] { deviceName, propertyName });
        database.command_inout("DbDeleteDeviceProperty", argin);
    }

    @Override
    public void deleteAttributeProperties(final String deviceName, final String... attributeNames) throws DevFailed {
        final DeviceData argin = new DeviceData();
        if (attributeNames == null || attributeNames.length == 0) {
            // delete all properties of the device
            argin.insert(new String[] { deviceName, "*" });
        } else {
            argin.insert(ArrayUtils.add(attributeNames, 0, deviceName));
        }
        database.command_inout("DbDeleteAllDeviceAttributeProperty", argin);
    }

    @Override
    public void deleteDevicePipeProperties(final String deviceName, final String... pipeNames) throws DevFailed {
        final DeviceData argin = new DeviceData();
        if (pipeNames == null || pipeNames.length == 0) {
            // delete all properties of the device
            argin.insert(new String[] { deviceName, "*" });
        } else {
            argin.insert(ArrayUtils.add(pipeNames, 0, deviceName));
        }
        database.command_inout("DbDeleteAllDevicePipeProperty", argin);
    }

    @Override
    public void loadCache(final String serverName, final String hostName) throws DevFailed {
    }

    @Override
    public void clearCache() {
    }

    @Override
    public String getVersion() {
        return "no cache";
    }

    @Override
    public boolean isCacheAvailable() {
        return false;
    }

    @Override
    public String getAccessDeviceName() throws DevFailed {
        String access = "";
        final DbDatum prop = database.get_property("CtrlSystem", "Services");
        if (prop != null) {
            final String[] r = prop.extractStringArray();
            if (r != null) {
                for (final String string : r) {
                    // get device name
                    if (string.startsWith("AccessControl")) {
                        access = string.substring(string.indexOf(':') + 1);
                    }

                }
            }
        }
        return access;
    }

    @Override
    public String[] getPossibleTangoHosts() throws DevFailed {
        String[] tangoHosts = null;
        try {
            final DeviceData deviceData = database.command_inout("DbGetCSDbServerList");
            tangoHosts = deviceData.extractStringArray();
        } catch (final DevFailed e) {
            // this is an old database server
            final String desc = e.errors[0].desc.toLowerCase();
            if (desc.startsWith("command ") && desc.endsWith("not found")) {
                tangoHosts = new String[] { TangoHostManager.getFirstFullTangoHost() };
            } else {
                throw e;
            }
        }
        return tangoHosts;

    }

    @Override
    public String getFreeProperty(final String name, final String propertyName) throws DevFailed {
        final DeviceData argin = new DeviceData();
        argin.insert(new String[] { name, propertyName });
        final DeviceData deviceData = database.command_inout("DbGetProperty", argin);
        String result = "";
        if (deviceData.extractStringArray().length > 4) {
            result = deviceData.extractStringArray()[4];
        }
        return result;
    }

    @Override
    public Map<String, String[]> getDevicePipeProperties(final String deviceName, final String pipeName)
            throws DevFailed {
        final DeviceData argin = new DeviceData();
        argin.insert(new String[] { deviceName, pipeName });
        final DeviceData argout = database.command_inout("DbGetDevicePipeProperty", argin);
        final String[] result = argout.extractStringArray();
        return extractArgout(result, 4);
    }

    @Override
    public void setDevicePipeProperties(final String deviceName, final String pipeName,
            final Map<String, String[]> properties) throws DevFailed {
        // argin desc: Str[0] = Device name,Str[1] = Pipe number,Str[2] = Pipe name
        final List<String> args = getArray(properties, deviceName, "1", pipeName);
        final DeviceData argin = new DeviceData();
        final String[] array = args.toArray(new String[args.size()]);
        argin.insert(array);
        database.command_inout("DbPutDevicePipeProperty", argin);
    }

    @Override
    public Map<String, String[]> getClassPipeProperties(final String className, final String pipeName) throws DevFailed {
        final DeviceData argin = new DeviceData();
        argin.insert(new String[] { className, pipeName });
        final DeviceData argout = database.command_inout("DbGetClassPipeProperty", argin);
        final String[] result = argout.extractStringArray();
        return extractArgout(result, 2);
    }

    @Override
    public void setClassPipeProperties(final String className, final String pipeName,
            final Map<String, String[]> properties) throws DevFailed {
        final List<String> args = getArray(properties, className);
        final DeviceData argin = new DeviceData();
        argin.insert(args.toArray(new String[args.size()]));
        database.command_inout("DbPutClassPipeProperty", argin);

    }

    private List<String> getArray(final Map<String, String[]> properties, final String... start) {
        final List<String> args = new ArrayList<String>();
        for (int i = 0; i < start.length; i++) {
            args.add(start[i]);
        }

        args.add(Integer.toString(properties.size()));
        for (final Map.Entry<String, String[]> entry : properties.entrySet()) {
            args.add(entry.getKey());
            final String[] propValues = entry.getValue();
            args.add(Integer.toString(propValues.length));
            for (final String propValue : propValues) {
                args.add(propValue);
            }
        }
        return args;
    }
}
