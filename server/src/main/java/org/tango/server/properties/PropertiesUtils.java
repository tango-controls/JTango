/**
 * Copyright (C) :     2012
 *
 * 	Synchrotron Soleil
 * 	L'Orme des merisiers
 * 	Saint Aubin
 * 	BP48
 * 	91192 GIF-SUR-YVETTE CEDEX
 *
 * This file is part of Tango.
 *
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.properties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

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

    private PropertiesUtils() {

    }

    public static void clearDeviceCache(final String deviceName) {
	devicePropertiesCache.remove(deviceName);
    }

    public static void clearClassCache(final String className) {
	classPropertiesCache.remove(className);
    }

    public static void clearCache() {
	devicePropertiesCache.clear();
	classPropertiesCache.clear();
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

    public static Map<String, String[]> getClassProperties(final String className) throws DevFailed {
	final Map<String, String[]> properties;
	if (classPropertiesCache.containsKey(className)) {
	    properties = classPropertiesCache.get(className);
	} else {
	    properties = DatabaseFactory.getDatabase().getClassProperties(className);
	    classPropertiesCache.put(className, properties);
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
    public static String[] getProp(final Map<String, String[]> prop, final String propertyName) {
	String[] result = null;
	for (final Entry<String, String[]> entry : prop.entrySet()) {
	    if (entry.getKey().equalsIgnoreCase(propertyName)) {
		result = entry.getValue();
	    }
	}
	return result;
    }

    public static String[] getDeviceProperty(final String deviceName, final String className, final String propertyName)
	    throws DevFailed {
	String[] property = new String[] { "" };
	final Map<String, String[]> prop = getDeviceProperties(deviceName);
	final String[] temp = getProp(prop, propertyName);
	if (temp != null) {
	    // get value
	    property = temp;
	    LOGGER.debug("{} device property is {}", propertyName, Arrays.toString(property));
	} else {
	    // get property from class property
	    final Map<String, String[]> propClass = getClassProperties(className);
	    if (propClass.get(propertyName) != null) {
		// get value
		property = propClass.get(propertyName);
		LOGGER.debug("{} class property is {}", propertyName, Arrays.toString(property));
	    } else {
		LOGGER.debug("{} property value not found in tango db", propertyName);
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
	if (property.length == 0 || property[0].isEmpty()) {
	    valueToInject = defaultValue[0].trim();
	} else {
	    valueToInject = property[0].trim();
	}
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
		if (property[0].isEmpty()) {
		    LOGGER.error(errorMsg);
		} else {
		    DevFailedUtils.throwDevFailed("PROPERTY_ERROR", errorMsg);
		}
	    }
	}

	try {
	    if (propConverted != null) {
		propMethod.invoke(businessObject, propConverted);
	    }
	} catch (final IllegalArgumentException e) {
	    DevFailedUtils.throwDevFailed(e);
	} catch (final IllegalAccessException e) {
	    DevFailedUtils.throwDevFailed(e);
	} catch (final InvocationTargetException e) {
	    DevFailedUtils.throwDevFailed(e);
	}

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
		DevFailedUtils.throwDevFailed("PROPERTY_ERROR", errorMsg);
	    }
	}

	try {
	    if (propConverted != null) {
		propMethod.invoke(businessObject, propConverted);
	    }
	} catch (final IllegalArgumentException e) {
	    DevFailedUtils.throwDevFailed(e);
	} catch (final IllegalAccessException e) {
	    DevFailedUtils.throwDevFailed(e);
	} catch (final InvocationTargetException e) {
	    if (e.getCause() instanceof DevFailed) {
		throw (DevFailed) e.getCause();
	    } else {
		DevFailedUtils.throwDevFailed(e.getCause());
	    }
	}
    }
}
