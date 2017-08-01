package org.tango.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("serial")
public class CaseInsensitiveMap<V> extends HashMap<String, V> {

    public CaseInsensitiveMap() {
        super();
    }

    public CaseInsensitiveMap(final Map<String, V> map) {
        super(map);
    }

    public CaseInsensitiveMap(final int size) {
        super(size);
    }

    @Override
    public V put(final String key, final V value) {
        return super.put(key.toLowerCase(Locale.ENGLISH), value);
    }

    @Override
    public V get(final Object key) {

        return super.get(((String) key).toLowerCase());
    }

    @Override
    public void putAll(final Map<? extends String, ? extends V> m) {
        for (final Map.Entry<? extends String, ? extends V> entry : m.entrySet()) {
            super.put(entry.getKey().toLowerCase(Locale.ENGLISH), entry.getValue());
        }
    }

    @Override
    public V remove(final Object key) {
        return super.remove(((String) key).toLowerCase());
    }

}
