package org.tango.client.database;

import fr.esrf.Tango.DevFailed;
import org.apache.commons.lang3.ArrayUtils;
import org.tango.utils.DevFailedUtils;
import org.tango.utils.TangoUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Manage tango db properties within a file
 *
 * @author ABEILLE
 *
 */
public final class FileTangoDB implements ITangoDB {

    private static final String ERROR_PARSING_FILE_PROP = "error parsing file prop";
    private final Map<String, Map<String, String[]>> deviceProperties = new HashMap<String, Map<String, String[]>>();
    private final Map<String, Map<String, String[]>> classProperties = new HashMap<String, Map<String, String[]>>();
    private final Map<String, Map<String, String[]>> attributeProperties = new HashMap<String, Map<String, String[]>>();
    private final String[] devices;
    private final String className;

    FileTangoDB(final String[] devices, final String className) {
        this.devices = Arrays.copyOf(devices, devices.length);
        this.className = className;
    }

    FileTangoDB(final File propertiesFiles, final String[] devices, final String className) throws DevFailed {
        this.devices = Arrays.copyOf(devices, devices.length);
        this.className = className;
        loadFileProperties(propertiesFiles);
    }

    @Override
    public void exportDevice(final DeviceExportInfo info) throws DevFailed {

    }

    @Override
    public String[] getInstanceNameList(final String dsExecName) throws DevFailed {
        return new String[0];
    }

    @Override
    public DeviceImportInfo importDevice(final String toBeImported) throws DevFailed {
        return new DeviceImportInfo("", false, "", "", 0);
    }

    @Override
    public void unexportServer(final String serverName) throws DevFailed {
    }

    @Override
    public String[] getDeviceList(final String serverName, final String className) throws DevFailed {
        String[] result = new String[0];
        if (this.className.equalsIgnoreCase(className)) {
            result = Arrays.copyOf(devices, devices.length);
        }
        return result;
    }

    @Override
    public Map<String, String[]> getDeviceProperties(final String name, final String... propertyNames) throws DevFailed {
        final Map<String, String[]> prop = deviceProperties.get(name);
        final Map<String, String[]> result;
        if (prop == null) {
            result = new HashMap<String, String[]>();
        } else if (propertyNames.length == 0) {
            result = prop;
        } else {
            result = new HashMap<String, String[]>();
            for (final String propertyName : propertyNames) {
                final String[] props = prop.get(propertyName);
                if (props == null) {
                    result.put(propertyName, new String[0]);
                } else {
                    result.put(propertyName, props);
                }
            }
        }
        return result;
    }

    @Override
    public void setDeviceProperties(final String deviceName, final Map<String, String[]> properties) throws DevFailed {
        if (deviceProperties.containsKey(deviceName)) {
            deviceProperties.get(deviceName).putAll(properties);
        } else {
            deviceProperties.put(deviceName, properties);
        }
    }

    @Override
    public Map<String, String[]> getClassProperties(final String name, final String... propertyNames) throws DevFailed {
        final Map<String, String[]> prop = classProperties.get(name);
        Map<String, String[]> result = new HashMap<String, String[]>();
        if (prop == null) {
            result = new HashMap<String, String[]>();
        } else if (propertyNames.length == 0) {
            result = prop;

        } else {
            for (final String propertyName : propertyNames) {
                final String[] props = prop.get(propertyName);
                if (props == null) {
                    result.put(propertyName, new String[0]);
                } else {
                    result.put(propertyName, props);
                }
            }
        }
        return result;
    }

    @Override
    public void setClassProperties(final String name, final Map<String, String[]> properties) throws DevFailed {
        if (classProperties.containsKey(name)) {
            classProperties.get(name).putAll(properties);
        } else {
            classProperties.put(name, properties);
        }
    }

    @Override
    public Map<String, String[]> getAttributeProperties(final String deviceName, final String attributeName)
            throws DevFailed {
        Map<String, String[]> map = attributeProperties.get(deviceName + "/" + attributeName);
        if (map == null) {
            map = new HashMap<String, String[]>();
        }
        return map;
    }

    @Override
    public void setAttributeProperties(final String deviceName, final String attributeName,
            final Map<String, String[]> properties) throws DevFailed {
        final Map<String, String[]> map = attributeProperties.get(deviceName + "/" + attributeName);
        if (map == null) {
            attributeProperties.put(deviceName + "/" + attributeName, properties);
        } else {
            map.putAll(properties);
        }
    }

    private void loadFileProperties(final File propertiesFiles) throws DevFailed {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(propertiesFiles));
            System.out.println("loading file " + propertiesFiles);
            String previousLine = "";
            String propertyName = "";
            String className = "";
            String deviceName = "";
            while (in.ready()) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                line = line.trim();

                // device properties # --- deviceName properties
                if (line.startsWith("# ---")) {
                    deviceName = line.split("# ---")[1].trim().split("properties")[0].trim();
                }
                // else if (line.startsWith("#") || line.isEmpty()) {
                // ignore: commment
                // } else
                if (!deviceName.isEmpty() && line.startsWith(deviceName)) {
                    // deviceName->propertyName:propertyValue
                    final String prop = line.split("->")[1].trim().split(",\\\\")[0];
                    propertyName = prop.substring(0, prop.indexOf(':'));
                    String propertyValue = prop.substring(prop.indexOf(':') + 1).trim();
                    // remove quotes of strings
                    if (propertyValue.startsWith("\"") && propertyValue.endsWith("\"")) {
                        propertyValue = propertyValue.substring(1, propertyValue.length() - 1);
                    }
                    if (deviceProperties.containsKey(deviceName)) {
                        final Map<String, String[]> props = deviceProperties.get(deviceName);
                        if (props.containsKey(propertyName)) {
                            ArrayUtils.add(props.get(propertyName), propertyValue);
                        } else {
                            props.put(propertyName, new String[] { propertyValue });
                        }
                    } else {
                        final Map<String, String[]> props = new HashMap<String, String[]>();
                        props.put(propertyName, new String[] { propertyValue });
                        deviceProperties.put(deviceName, props);
                    }
                    previousLine = line;
                } else if (line.startsWith("CLASS")) {
                    // CLASS/className->propertyName:propertyValue
                    final String[] split = line.split("->");
                    className = split[0].split("/")[1];
                    final String prop = split[1].trim().split(",\\\\")[0];
                    propertyName = prop.substring(0, prop.indexOf(':'));
                    String propertyValue = prop.substring(prop.indexOf(':') + 1).trim();
                    // remove quotes of strings
                    if (propertyValue.startsWith("\"") && propertyValue.endsWith("\"")) {
                        propertyValue = propertyValue.substring(1, propertyValue.length() - 1);
                    }
                    if (classProperties.containsKey(className)) {
                        final Map<String, String[]> props = classProperties.get(className);
                        if (props.containsKey(propertyName)) {
                            ArrayUtils.add(props.get(propertyName), propertyValue);
                        } else {
                            props.put(propertyName, new String[] { propertyValue });
                        }
                    } else {
                        final Map<String, String[]> props = new HashMap<String, String[]>();
                        props.put(propertyName, new String[] { propertyValue });
                        classProperties.put(className, props);
                    }
                    previousLine = line;
                } else if (!line.startsWith("#") && !line.isEmpty()) {
                    if (previousLine.endsWith(",\\") && (!deviceName.isEmpty() || !className.isEmpty())) {
                        // property is an array
                        // deviceName->propertyName: propertyName:propertyValue1,\
                        // propertyValue2
                        String propertyValue = line;
                        // remove trailing ,\
                        if (propertyValue.endsWith(",\\")) {
                            propertyValue = line.substring(0, line.length() - 2);
                        }
                        // remove quotes of strings
                        if (propertyValue.startsWith("\"") && propertyValue.endsWith("\"")) {
                            propertyValue = propertyValue.substring(1, propertyValue.length() - 1);
                        }
                        if (previousLine.startsWith(deviceName)) {
                            if (deviceProperties.containsKey(deviceName)) {
                                final Map<String, String[]> props = deviceProperties.get(deviceName);
                                if (props.containsKey(propertyName)) {
                                    final Object[] array = ArrayUtils.add(props.get(propertyName), propertyValue);
                                    props.put(propertyName, (String[]) array);
                                } else {
                                    throw DevFailedUtils.newDevFailed(ERROR_PARSING_FILE_PROP);
                                }
                            } else {
                                throw DevFailedUtils.newDevFailed(ERROR_PARSING_FILE_PROP);
                            }
                        } else if (previousLine.startsWith("CLASS")) {
                            if (classProperties.containsKey(className)) {
                                final Map<String, String[]> props = classProperties.get(className);
                                if (props.containsKey(propertyName)) {
                                    final Object[] array = ArrayUtils.add(props.get(propertyName), propertyValue);
                                    props.put(propertyName, (String[]) array);
                                } else {
                                    throw DevFailedUtils.newDevFailed(ERROR_PARSING_FILE_PROP);
                                }
                            } else {
                                throw DevFailedUtils.newDevFailed(ERROR_PARSING_FILE_PROP);
                            }
                        }
                    }
                }
            }
        } catch (final IOException e) {
            throw DevFailedUtils.newDevFailed(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (final IOException e) {
                    // ignore
                }
            }
        }
    }

    @Override
    public void deleteDeviceProperty(final String deviceName, final String propertyName) throws DevFailed {
        final Map<String, String[]> props = deviceProperties.get(deviceName);
        if (props != null) {
            props.remove(propertyName);
        }
    }

    @Override
    public void loadCache(final String serverName, final String hostName) {
        // NA
    }

    @Override
    public void clearCache() {
        // NA

    }

    @Override
    public String getAccessDeviceName() throws DevFailed {
        return "";
    }

    @Override
    public void deleteAttributeProperties(final String deviceName, final String... attributeNames) throws DevFailed {
        if (attributeNames == null || attributeNames.length == 0) {
            for (final String attributeName : attributeProperties.keySet()) {
                if (TangoUtil.getfullDeviceNameForAttribute(attributeName).equalsIgnoreCase(deviceName)) {
                    attributeProperties.remove(attributeName);
                }
            }
        } else {
            for (final String attributeName : attributeNames) {
                if (attributeProperties.containsKey(deviceName + "/" + attributeName)) {
                    attributeProperties.remove(deviceName + "/" + attributeName);
                }
            }
        }
    }

    /**
     * The tango host without DB is empty
     */
    @Override
    public String[] getPossibleTangoHosts() {
        return new String[] { "nodb" };
    }

    @Override
    public String getFreeProperty(final String name, final String propertyName) {
        return "";
    }

    @Override
    public Map<String, String[]> getDevicePipeProperties(final String deviceName, final String pipeName)
            throws DevFailed {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDevicePipeProperties(final String deviceName, final String pipeName,
            final Map<String, String[]> properties) throws DevFailed {
        // TODO Auto-generated method stub

    }

    @Override
    public Map<String, String[]> getClassPipeProperties(final String className, final String pipeName) throws DevFailed {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setClassPipeProperties(final String className, final String pipeName,
            final Map<String, String[]> properties) throws DevFailed {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteDevicePipeProperties(final String deviceName, final String... pipeNames) throws DevFailed {
        // TODO Auto-generated method stub

    }

}
