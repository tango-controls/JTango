package org.tango.client.database.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public final class DeviceCache {

    private final String name;
    private final Map<String, String[]> propertiesCache = new HashMap<String, String[]>();
    private final Map<String, AttributeCache> attributeCaches = new HashMap<String, AttributeCache>();

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
	return new HashMap<String, String[]>(propertiesCache);
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
