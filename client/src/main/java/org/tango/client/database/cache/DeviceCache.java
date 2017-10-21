package org.tango.client.database.cache;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.tango.utils.CaseInsensitiveMap;

import java.util.Map;

public final class DeviceCache {

    private final String name;
    private final Map<String, String[]> propertiesCache = new CaseInsensitiveMap<String[]>();
    private final Map<String, AttributeCache> attributeCaches = new CaseInsensitiveMap<AttributeCache>();

    DeviceCache(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addProperty(final String propertyName, final String[] propertyValue) {
        propertiesCache.put(propertyName, propertyValue);
    }

    public void removeProperty(final String propertyName) {
        propertiesCache.remove(propertyName);
    }

    public void addProperties(final Map<String, String[]> properties) {
        propertiesCache.putAll(properties);
    }

    public Map<String, String[]> getPropertiesCache() {
        return new CaseInsensitiveMap<String[]>(propertiesCache);
    }

    public void addAttributeCache(final AttributeCache cache) {
        attributeCaches.put(cache.getName(), cache);
    }

    public AttributeCache getAttributeCache(final String name) {
             return attributeCaches.get(name);
    }

    /**
     * @return a string
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
