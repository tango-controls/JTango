package org.tango.client.database.cache;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.Connection;
import fr.esrf.TangoApi.DeviceData;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.utils.DevFailedUtils;

import java.util.*;

public final class ServerCache {

    private static final String NOT_FOUND = "Not found";
    private final Logger logger = LoggerFactory.getLogger(ServerCache.class);
    private final Connection database;
    private final List<String> adminInfos = new LinkedList<String>();

    private String adminDeviceName;
    private String adminClassName;
    private final Map<String, ClassCache> classCaches = new HashMap<String, ClassCache>();

    private class Server {
        private final Map<String, String[]> devicesPerServer = new HashMap<String, String[]>();

        public void addClass(final String className, final String[] deviceNames) {
            devicesPerServer.put(className, deviceNames);
        }

        public String[] getDevices(final String className) {
            return devicesPerServer.get(className);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    private final Map<String, Server> servers = new HashMap<String, ServerCache.Server>();
    private final Map<String, DeviceCache> deviceCaches = new HashMap<String, DeviceCache>();

    public ServerCache(final Connection database) throws DevFailed {
        this.database = database;

    }

    public void fillCache(final String serverName, final String hostName) throws DevFailed {
        if (!servers.containsKey(serverName)) {
            // set a big timeout because DbGetDataForServerCache can be very big
            this.database.set_timeout_millis(13000);
            final DeviceData in = new DeviceData();
            in.insert(new String[] { serverName, hostName });

            logger.debug("filling cache of server {} with host name {}", serverName, hostName);
            final String[] out = database.command_inout("DbGetDataForServerCache", in).extractStringArray();
            if (out.length == 2) {
                throw DevFailedUtils.newDevFailed("cache for " + serverName + " not found");
            }
            int i = 0;
            // 1 - Admin device import paramters (2 or 8 elts - 2 when admin
            // device not defined in db (second one being
            // "Not found"))
            // admin device name - 0
            adminDeviceName = out[i++];
            adminInfos.add(adminDeviceName);
            ClassCache adminCache = null;
            if (!out[i].equalsIgnoreCase(NOT_FOUND)) {
                // IOR - 1
                adminInfos.add(out[i++]);
                // tango version - 2
                adminInfos.add(out[i++]);
                // server name - 3
                adminInfos.add(out[i++]);
                // host name - 4
                adminInfos.add(out[i++]);
                // ? - 5
                adminInfos.add(out[i++]);
                // PID - 6
                adminInfos.add(out[i++]);
                // admin class name - 7
                adminClassName = out[i++];
                adminInfos.add(adminClassName);
                adminCache = new ClassCache(adminClassName);
                classCaches.put(adminClassName, adminCache);
            }
            logger.debug("filling cache, adminInfos = {} ", adminInfos);
            // 2 - Event channel factory import event parameters (2 or 6 elts -
            // 2 when event channel factory not found
            // in DB
            // (second = "Not found")) - TODO when event will be supported
            // notifd/factory/...
            i++;
            if (out[i].equalsIgnoreCase(NOT_FOUND)) {
                i = i + 1;
            } else {
                i = i + 5;
            }
            // 3 - DS event channel import event parameters (2 or 6 elts - 2
            // when event channel factory not found in DB
            // (second
            // = "Not found"))
            i++;
            if (out[i].equalsIgnoreCase(NOT_FOUND)) {
                i = i + 1;
            } else {
                i = i + 5;
            }
            // 4 - DServer class properties (at least 2 elts)
            if (out[i].equalsIgnoreCase(adminClassName)) {
                i++;
                final int nbProperties = Integer.parseInt(out[i++]);
                for (int j = 0; j < nbProperties; j++) {
                    final String propertyName = out[i++];
                    final int propertySize = Integer.parseInt(out[i++]);
                    final String[] propertyValue = new String[propertySize];
                    for (int k = 0; k < propertyValue.length; k++) {
                        propertyValue[k] = out[i++];
                    }
                    adminCache.addProperty(propertyName, propertyValue);
                }
            }
            logger.debug("admin class properties cache is {}", adminCache);
            // 5 - Default class properties (at least 2 elts)
            if (out[i].equalsIgnoreCase("Default")) {
                i++;
                final int nbProperties = Integer.parseInt(out[i++]);
                for (int j = 0; j < nbProperties; j++) {
                    // final String propertyName = out[i++];
                    i++;
                    final int propertySize = Integer.parseInt(out[i++]);
                    final String[] propertyValue = new String[propertySize];
                    for (int k = 0; k < propertyValue.length; k++) {
                        propertyValue[k] = out[i++];
                    }
                    // TODO
                }
            }
            // 6 - admin device properties (at least 2 elts)
            final DeviceCache adminDeviceCache = new DeviceCache(adminDeviceName);
            deviceCaches.put(adminDeviceName, adminDeviceCache);
            if (out[i].equalsIgnoreCase(adminDeviceName)) {
                i++;
                final int nbProperties = Integer.parseInt(out[i++]);
                for (int j = 0; j < nbProperties; j++) {
                    final String propertyName = out[i++];
                    final int propertySize = Integer.parseInt(out[i++]);
                    final String[] propertyValue = new String[propertySize];
                    for (int k = 0; k < propertyValue.length; k++) {
                        propertyValue[k] = out[i++];
                    }
                    adminDeviceCache.addProperty(propertyName, propertyValue);
                    adminCache.addDeviceCache(adminDeviceCache);
                }
            }
            logger.debug("admin class properties cache is {}", adminDeviceCache);
            // /FOR/ Each Tango class in the DS (2 elts to define Class number)
            // 7 - <Class> class properties (at least 2 elts)
            // 8 - <Class> class attribute properties (at least 2 elts)
            // 9 - <Class> device list (at least 2 elts)
            // /FOR/ Each device (at least 2 elts)
            // 10 - <Dev> device properties (at least 2 elts)
            // 11 - <Dev> device attribute properties (at least 2 elts)
            // /END FOR/
            // /END FOR/

            if (out[i].equalsIgnoreCase(serverName)) {
                final Server s = new Server();
                i++;
                final int classNr = Integer.parseInt(out[i++]);
                final String[] classeNames = new String[classNr];
                // iterate on all class of the server
                for (int j = 0; j < classNr; j++) {
                    final String className = out[i++];
                    final ClassCache classCache = new ClassCache(className);
                    classeNames[j] = className;
                    // 7 - <Class> class properties (at least 2 elts)
                    final int classPropertiesNr = Integer.parseInt(out[i++]);
                    for (int k = 0; k < classPropertiesNr; k++) {
                        final String propertyName = out[i++];
                        final int propertySize = Integer.parseInt(out[i++]);
                        final String[] propertyValue = new String[propertySize];
                        for (int l = 0; l < propertyValue.length; l++) {
                            propertyValue[l] = out[i++];
                        }
                        classCache.addProperty(propertyName, propertyValue);
                    }
                    logger.debug("{} class cache is {}", className, classCache);
                    // 8 - <Class> class attribute properties (at least 2 elts)

                    i++;
                    final int classAttrPropertiesNr = Integer.parseInt(out[i++]);
                    for (int k = 0; k < classAttrPropertiesNr; k++) {
                        // final String propertyName = out[i++];
                        i++;
                        final int propertySize = Integer.parseInt(out[i++]);
                        final String[] propertyValue = new String[propertySize];
                        for (int l = 0; l < propertyValue.length; l++) {
                            propertyValue[l] = out[i++];
                        }

                        // TODO
                    }

                    // 9 - <Class> device list (at least 2 elts)
                    i++;
                    final int deviceNr = Integer.parseInt(out[i++]);
                    i = i + deviceNr;
                    final String[] deviceNames = new String[deviceNr];
                    for (int k = 0; k < deviceNr; k++) {
                        final String deviceName = out[i++];

                        final DeviceCache deviceCache = new DeviceCache(deviceName);
                        deviceCaches.put(deviceName, deviceCache);
                        deviceNames[k] = deviceName;
                        // 10 - <Dev> device properties (at least 2 elts)
                        final int devicePropertiesNr = Integer.parseInt(out[i++]);
                        for (int l = 0; l < devicePropertiesNr; l++) {
                            final String propertyName = out[i++];
                            final int propertySize = Integer.parseInt(out[i++]);
                            final String[] propertyValue = new String[propertySize];
                            for (int m = 0; m < propertyValue.length; m++) {
                                propertyValue[m] = out[i++];
                            }
                            deviceCache.addProperty(propertyName, propertyValue);
                        }
                        logger.debug("{} device cache is {}", deviceName, deviceCache);
                        classCache.addDeviceCache(deviceCache);
                        // 11 - <Dev> device attribute properties (at least 2
                        // elts)
                        i++;
                        final int attrPropertiesNr = Integer.parseInt(out[i++]);
                        for (int l = 0; l < attrPropertiesNr; l++) {
                            final String attributeName = out[i++];
                            final AttributeCache attributeCache = new AttributeCache(attributeName);
                            final int propertyNr = Integer.parseInt(out[i++]);
                            for (int m = 0; m < propertyNr; m++) {
                                final String propertyName = out[i++];
                                final int propertySize = Integer.parseInt(out[i++]);
                                final String[] propertyValue = new String[propertySize];
                                for (int n = 0; n < propertyValue.length; n++) {
                                    propertyValue[n] = out[i++];
                                }
                                attributeCache.addProperty(propertyName, propertyValue);
                            }
                            logger.debug("{} attribute cache is {}", attributeName, attributeCache);
                            deviceCache.addAttributeCache(attributeCache);

                        }
                    }
                    classCaches.put(className, classCache);

                    s.addClass(className, deviceNames);
                }
                servers.put(serverName, s);
                logger.debug("servers {}", servers);
            }

            // TODO 12 - CtrlSystem free object properties (at least 2 elts)
            // TODO 13 - TAC device import parameters (2 or 8 elts - 2 when
            // admin device not defined in db (second one
            // being "Not found"))
            // reset default timeout
            // TODO pipes
            this.database.set_timeout_millis(3000);
        }
    }

    public DeviceCache getDeviceCache(final String name) {
        return deviceCaches.get(name);
    }

    public ClassCache getClassCache(final String name) {
        return classCaches.get(name);
    }

    /**
     * Get devices of a server
     *
     * @return
     */
    public String[] getDeviceList(final String serverName, final String className) {
        String[] result = new String[0];
        final Server server = servers.get(serverName);
        if (server != null) {
            final String[] devices = server.getDevices(className);
            if (devices != null) {
                result = Arrays.copyOf(devices, devices.length);
            }
        }
        return result;
    }
}
