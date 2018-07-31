/**
 * Copyright (C) :     2012
 * <p>
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
 * <p>
 * This file is part of Tango.
 * <p>
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.properties;

import fr.esrf.Tango.DevFailed;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.utils.DevFailedUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class PropertiesUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);
    /**
     * Map to request device properties once
     */
    private static Map<String, Map<String, String[]>> devicePropertiesCache = new HashMap<String, Map<String, String[]>>();
    /**
     * Map to request class properties once
     */
    private static Map<String, Map<String, String[]>> classPropertiesCache = new HashMap<String, Map<String, String[]>>();

    /**
     * Map to request device pipe properties once
     */
    private static Map<String, Map<String, String[]>> devicePipePropertiesCache = new HashMap<String, Map<String, String[]>>();
    /**
     * Map to request class pie properties once
     */
    private static Map<String, Map<String, String[]>> classPipePropertiesCache = new HashMap<String, Map<String, String[]>>();

    private PropertiesUtils() {

    }

    public static void clearDeviceCache(final String deviceName) {
        devicePropertiesCache.remove(deviceName);
        for (final Entry<String, Map<String, String[]>> entry : devicePipePropertiesCache.entrySet()) {
            if (entry.getKey().startsWith(deviceName)) {
                devicePipePropertiesCache.remove(entry.getKey());
            }
        }

    }

    public static void clearClassCache(final String className) {
        classPropertiesCache.remove(className);
        for (final Entry<String, Map<String, String[]>> entry : devicePipePropertiesCache.entrySet()) {
            if (entry.getKey().startsWith(className)) {
                classPipePropertiesCache.remove(className);
            }
        }
    }

    public static void clearCache() {
        devicePropertiesCache.clear();
        classPropertiesCache.clear();
        devicePipePropertiesCache.clear();
        classPipePropertiesCache.clear();
    }

    public static Map<String, String[]> getDevicePipeProperties(final String deviceName, final String pipeName)
            throws DevFailed {
        final Map<String, String[]> properties;
        final String fullPipeName = deviceName + pipeName;
        if (devicePipePropertiesCache.containsKey(fullPipeName)) {
            properties = devicePipePropertiesCache.get(fullPipeName);
        } else {
            properties = DatabaseFactory.getDatabase().getDevicePipeProperties(deviceName, pipeName);
            devicePipePropertiesCache.put(fullPipeName, properties);
        }
        return properties;
    }

    public static Map<String, String[]> getDeviceProperties(final String deviceName) throws DevFailed {
        final Map<String, String[]> properties;
        if (devicePropertiesCache.containsKey(deviceName)) {
            properties = devicePropertiesCache.get(deviceName);
        } else {
            properties = DatabaseFactory.getDatabase().getDeviceProperties(deviceName);
            devicePropertiesCache.put(deviceName, properties);
        }
        return properties;
    }

    static Map<String, String[]> getClassProperties(final String className) throws DevFailed {
        final Map<String, String[]> properties;
        if (classPropertiesCache.containsKey(className)) {
            properties = classPropertiesCache.get(className);
        } else {
            properties = DatabaseFactory.getDatabase().getClassProperties(className);
            classPropertiesCache.put(className, properties);
        }
        return properties;
    }

    static Map<String, String[]> getClassPipeProperties(final String className, final String pipeName) throws DevFailed {
        final Map<String, String[]> properties;
        final String fullPipeName = className + pipeName;
        if (classPipePropertiesCache.containsKey(fullPipeName)) {
            properties = classPipePropertiesCache.get(fullPipeName);
        } else {
            properties = DatabaseFactory.getDatabase().getClassProperties(className, pipeName);
            classPipePropertiesCache.put(fullPipeName, properties);
        }
        return properties;
    }

    /**
     * Ignore case on property name
     *
     * @param prop
     * @param propertyName
     * @return The property value
     */
    static String[] getProp(final Map<String, String[]> prop, final String propertyName) {
        String[] result = null;
        if (prop != null) {
            for (final Entry<String, String[]> entry : prop.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(propertyName)) {
                    result = entry.getValue();
                }
            }
        }
        return result;
    }

    public static String[] getDeviceProperty(final String deviceName, final String className, final String propertyName)
            throws DevFailed {
        String[] property = new String[0];
        final Map<String, String[]> prop = getDeviceProperties(deviceName);
        final String[] temp = getProp(prop, propertyName);
        if (temp != null) {
            // get value
            property = temp;
            LOGGER.debug("{} device property is {}", propertyName, Arrays.toString(property));
        } else {
            // get property from class property
            final Map<String, String[]> propClass = getClassProperties(className);
            final String[] tempClass = getProp(propClass, propertyName);
            if (tempClass != null) {
                // get value
                property = tempClass;
                LOGGER.debug("{} class property is {}", propertyName, Arrays.toString(property));
            } else {
                LOGGER.debug("{} property value not found in tango db", propertyName);
            }
        }
        return property;
    }

    public static String[] getDevicePipeProperty(final String deviceName, final String className,
                                                 final String pipeName, final String propertyName) throws DevFailed {
        String[] property = new String[0];
        final Map<String, String[]> prop = getDevicePipeProperties(deviceName, pipeName);
        final String[] temp = getProp(prop, propertyName);
        if (temp != null) {
            // get value
            property = temp;
            LOGGER.debug("{} device pipe property is {}", propertyName, Arrays.toString(property));
        } else {
            // get property from class property
            final Map<String, String[]> propClass = getClassPipeProperties(className, pipeName);
            if (propClass.get(propertyName) != null) {
                // get value
                property = propClass.get(propertyName);
                LOGGER.debug("{} class pipe property is {}", propertyName, Arrays.toString(property));
            } else {
                LOGGER.debug("{} pipe property {} value not found in tango db", pipeName, propertyName);
            }
        }
        return property;
    }

    static void injectProperty(final String propertyName, final Method propMethod, final String[] property,
                               final Object businessObject, final String[] defaultValue) throws DevFailed {
        // if (property.length > 0) {
        final Transmorph transmorph = new Transmorph(new DefaultConverters());
        final Class<?> paramType = propMethod.getParameterTypes()[0];
        if (paramType.isArray()) {
            injectArray(propertyName, propMethod, property, businessObject, transmorph, paramType, defaultValue);
        } else {
            injectValue(propertyName, propMethod, property, businessObject, transmorph, paramType, defaultValue);
        }
        // }
    }

    private static void injectValue(final String propertyName, final Method propMethod, final String[] property,
                                    final Object businessObject, final Transmorph transmorph, final Class<?> paramType,
                                    final String[] defaultValue) throws DevFailed {

        Object propConverted = null;
        final String valueToInject;
        String trimmedDefaultValue = null;
        if (defaultValue.length == 0) {
            trimmedDefaultValue = "";
        } else {
            trimmedDefaultValue = defaultValue[0].trim();
        }
        if (property.length == 0 || property[0].isEmpty()) {
            valueToInject = trimmedDefaultValue;
        } else {
            valueToInject = property[0].trim();
        }
        if (!valueToInject.isEmpty()) {
            LOGGER.debug("{} inject: {}", propertyName, valueToInject);
            if (Boolean.class.isAssignableFrom(paramType) || boolean.class.isAssignableFrom(paramType)) {
                if (valueToInject.equalsIgnoreCase("false") || valueToInject.equals("0")) {
                    propConverted = false;
                } else {
                    propConverted = true;
                }
            } else {
                try {
                    propConverted = transmorph.convert(valueToInject, paramType);
                } catch (final ConverterException e) {
                    final String errorMsg = "could not set property " + propertyName + ", error converting "
                            + valueToInject + " to " + paramType.getCanonicalName();
                    // ignore error for default value
                    if (property.length == 0 || property[0].isEmpty()) {
                        LOGGER.debug("{} is empty", propertyName);
                    } else {
                        throw DevFailedUtils.newDevFailed("PROPERTY_ERROR", errorMsg);
                    }
                }
            }

            try {
                if (propConverted != null) {
                    propMethod.invoke(businessObject, propConverted);
                }
            } catch (final IllegalArgumentException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final IllegalAccessException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final InvocationTargetException e) {
                throw DevFailedUtils.newDevFailed(e);
            }
        }
    }

    /**
     * Set pipe device properties in db
     *
     * @param deviceName
     * @param pipeName
     * @param properties
     * @throws DevFailed
     */
    public static void setDevicePipePropertiesInDB(final String deviceName, final String pipeName,
                                                   final Map<String, String[]> properties) throws DevFailed {
        LOGGER.debug("update pipe {} device properties {} in DB ", pipeName, properties.keySet());
        DatabaseFactory.getDatabase().setDevicePipeProperties(deviceName, pipeName, properties);
    }

    private static void injectArray(final String propertyName, final Method propMethod, final String[] property,
                                    final Object businessObject, final Transmorph transmorph, final Class<?> paramType,
                                    final String[] defaultValue) throws DevFailed {
        Object propConverted = null;
        final String[] trimProp;
        if (property.length == 0 || property.length == 1 && property[0].isEmpty()) {
            // inject default values
            trimProp = new String[defaultValue.length];
            for (int i = 0; i < defaultValue.length; i++) {
                trimProp[i] = defaultValue[i].trim();
            }
        } else {
            trimProp = new String[property.length];
            for (int i = 0; i < property.length; i++) {
                trimProp[i] = property[i].trim();
            }
        }
        LOGGER.debug("{} inject: {}", propertyName, Arrays.toString(trimProp));
        try {
            propConverted = transmorph.convert(trimProp, paramType);
        } catch (final ConverterException e) {
            final String errorMsg = "could not set property " + propertyName + ", error converting "
                    + Arrays.toString(trimProp) + " to " + paramType.getCanonicalName();
            // ignore error for default value
            if (property.length == 1 && property[0].isEmpty()) {
                LOGGER.error(errorMsg);
            } else {
                throw DevFailedUtils.newDevFailed("PROPERTY_ERROR", errorMsg);
            }
        }

        try {
            if (propConverted != null) {
                propMethod.invoke(businessObject, propConverted);
            }
        } catch (final IllegalArgumentException e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final IllegalAccessException e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final InvocationTargetException e) {
            if (e.getCause() instanceof DevFailed) {
                throw (DevFailed) e.getCause();
            } else {
                throw DevFailedUtils.newDevFailed(e.getCause());
            }
        }
    }
}
