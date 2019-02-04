package org.tango.client.database.cache;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.tango.utils.CaseInsensitiveMap;

import java.util.Arrays;
import java.util.Map;

public final class AttributeCache {
    private final String name;
    private final Map<String, String[]> propertiesCache = new CaseInsensitiveMap<String[]>();

    AttributeCache(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addProperty(final String propertyName, final String[] propertyValue) {
        propertiesCache.put(propertyName, propertyValue);
    }

    public Map<String, String[]> getPropertiesCache() {
        return new CaseInsensitiveMap<String[]>(propertiesCache);
    }

    public void addProperties(final Map<String, String[]> properties) {
        propertiesCache.putAll(properties);
    }

    public void removeAll() {
        propertiesCache.clear();
    }

    /**
     * @return a string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(" = [");
        for (Map.Entry<String, String[]> entry:  propertiesCache.entrySet()) {
           builder.append(entry.getKey()).append(" = ").append(Arrays.toString(entry.getValue())).append(",");
        }
        builder.append("]");
        return builder.toString();
    }
}
