package org.tango.client.database.cache;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.tango.utils.CaseInsensitiveMap;

import java.util.HashMap;
import java.util.Map;

public final class ClassCache {

    private final Map<String, String[]> propertiesCache = new CaseInsensitiveMap<String[]>();
    private final String name;
    private final Map<String, DeviceCache> deviceCaches = new CaseInsensitiveMap<DeviceCache>();

    ClassCache(final String name) {
        this.name = name;
    }

    public void addProperty(final String propertyName, final String[] propertyValue) {
        propertiesCache.put(propertyName, propertyValue);
    }

    public void addDeviceCache(final DeviceCache cache) {
        deviceCaches.put(cache.getName(), cache);
    }

    public Map<String, String[]> getPropertiesCache() {
        return new HashMap<String, String[]>(propertiesCache);
    }

    public DeviceCache getDeviceCache(final String deviceName) {
        return deviceCaches.get(deviceName);
    }

    public void addProperties(final Map<String, String[]> properties) {
        propertiesCache.putAll(properties);
    }

    public String getName() {
        return name;
    }

    /**
     * @return a string
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
