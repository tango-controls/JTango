package fr.soleil.tango.clientapi.factory;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeProxy;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoApi.DeviceProxyFactory;
import fr.esrf.TangoApi.Group.AttributeGroup;
import fr.esrf.TangoApi.Group.Group;
import org.tango.utils.TangoUtil;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ProxyFactory {
    private static final float LOAD = .75F;
    private static final int DEFAULT_TMOUT = 3000;
    // private static final Logger logger =
    // Logger.getLogger(ProxyFactory.class);
    private static final int MAX_ENTRIES = 100;
    private static final ProxyFactory instance = new ProxyFactory();
    private final Map<String, DeviceProxy> devicesMap;
    private final Map<String, AttributeProxy> attributesMap;
    private final Map<String, Group> groupMap;
    private final Map<String, AttributeGroup> attributeGroupMap;
    private int timeout = DEFAULT_TMOUT;

    private ProxyFactory() {
        // create cache map with a fix capacity. least-recently accessed entries
        // will be removed
        devicesMap = new LRUMap<String, DeviceProxy>(MAX_ENTRIES + 1, LOAD, true);
        attributesMap = new LRUMap<String, AttributeProxy>(MAX_ENTRIES + 1, .75F, true);
        attributeGroupMap = new LRUMap<String, AttributeGroup>(MAX_ENTRIES + 1, .75F, true);
        groupMap = new LRUMap<String, Group>(MAX_ENTRIES + 1, .75F, true);
    }

    /**
     *
     * @return instance
     */
    public static ProxyFactory getInstance() {
        return instance;
    }

    /**
     *
     * @param deviceName
     *            the device name
     * @return DeviceProxy
     * @throws DevFailed
     */
    public DeviceProxy createDeviceProxy(final String deviceName) throws DevFailed {
        DeviceProxy dev;
        final String fullDeviceName = TangoUtil.getfullNameForDevice(deviceName);
        synchronized (devicesMap) {
            if (!devicesMap.containsKey(fullDeviceName)) {
                dev = DeviceProxyFactory.get(fullDeviceName);
                try {
                    dev.set_timeout_millis(timeout);
                } catch (final DevFailed e) {
                }
                devicesMap.put(fullDeviceName, dev);
            } else {
                dev = devicesMap.get(fullDeviceName);
            }
        }
        return dev;
    }

    /**
     *
     * @param attrName
     * @return
     * @throws DevFailed
     */
    public AttributeProxy createAttributeProxy(final String attrName) throws DevFailed {
        AttributeProxy attr;
        final String fullAttrName = TangoUtil.getfullAttributeNameForAttribute(attrName);
        synchronized (attributesMap) {
            if (!attributesMap.containsKey(fullAttrName)) {
                attr = new AttributeProxy(fullAttrName);
                attr.set_timeout_millis(timeout);
                attributesMap.put(fullAttrName, attr);
            } else {
                attr = attributesMap.get(fullAttrName);
            }
        }
        return attr;
    }

    /**
     *
     * @param groupName
     * @param deviceNames
     * @return
     * @throws DevFailed
     */
    public Group createGroup(final String groupName, final String... deviceNames) throws DevFailed {
        Group grp;
        final String key = Arrays.toString(deviceNames);
        synchronized (groupMap) {
            if (!groupMap.containsKey(key)) {
                grp = new Group(groupName);
                grp.set_timeout_millis(timeout, true);
                grp.add(deviceNames);
                groupMap.put(key, grp);
            } else {
                grp = groupMap.get(key);
            }
        }
        return grp;
    }

    public AttributeGroup createAttributeGroup(final boolean throwExceptions, final String... attributeNames)
            throws DevFailed {
        AttributeGroup grp;
        final String key = Arrays.toString(attributeNames);
        synchronized (attributeGroupMap) {
            if (!attributeGroupMap.containsKey(key)) {
                grp = new AttributeGroup(throwExceptions, attributeNames);
                grp.setTimeout(timeout);
                attributeGroupMap.put(key, grp);
            } else {
                grp = attributeGroupMap.get(key);
            }
        }
        return grp;
    }

    /**
     *
     * @param timeout
     * @throws DevFailed
     */
    public void setTimout(final int timeout) throws DevFailed {
        this.timeout = timeout;
        synchronized (devicesMap) {
            for (final DeviceProxy dev : devicesMap.values()) {
                dev.set_timeout_millis(timeout);
            }
        }
        synchronized (attributesMap) {
            for (final AttributeProxy attr : attributesMap.values()) {
                attr.getDeviceProxy().set_timeout_millis(timeout);
                attr.set_timeout_millis(timeout);
            }
        }
        synchronized (groupMap) {
            for (final Group group : groupMap.values()) {
                group.set_timeout_millis(timeout, true);
            }
        }
        synchronized (attributeGroupMap) {
            for (final AttributeGroup group : attributeGroupMap.values()) {
                group.setTimeout(timeout);
            }
        }
    }

    public int getTimeout() {
        return timeout;
    }

    public void clearAllMap() {
        devicesMap.clear();
        attributesMap.clear();
        attributeGroupMap.clear();
        groupMap.clear();
    }

    private static class LRUMap<K, V> extends LinkedHashMap<K, V> {

        private static final long serialVersionUID = -6898381007090501228L;

        public LRUMap(final int initialCapacity, final float loadFactor, final boolean accessOrder) {
            super(initialCapacity, loadFactor, accessOrder);
        }

        @Override
        public boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
            return size() > MAX_ENTRIES;
        }
    }

}
