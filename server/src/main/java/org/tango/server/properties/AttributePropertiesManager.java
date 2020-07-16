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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.server.Constants;
import org.tango.utils.CaseInsensitiveMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Manage attribute properties persistancy in tango db.
 *
 * @author ABEILLE
 */
public final class AttributePropertiesManager {

    private final XLogger xlogger = XLoggerFactory.getXLogger(AttributePropertiesManager.class);
    private final Logger logger = LoggerFactory.getLogger(AttributePropertiesManager.class);
    private final String deviceName;

    public AttributePropertiesManager(final String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * Get an attribute's properties from tango db
     *
     * @param attributeName
     * @return The properties
     * @throws DevFailed
     */
    public Map<String, String[]> getAttributePropertiesFromDB(final String attributeName) throws DevFailed {
        xlogger.entry(attributeName);
        xlogger.exit();
        return DatabaseFactory.getDatabase().getAttributeProperties(deviceName, attributeName);
    }

    /**
     * Get an attribute's properties from tango db
     *
     * @param attributeName
     * @return The properties
     * @throws DevFailed
     */
    private Map<String, String> getAttributePropertiesFromDBSingle(final String attributeName) throws DevFailed {
        xlogger.entry(attributeName);
        final Map<String, String> result = new CaseInsensitiveMap<String>();
        final Map<String, String[]> prop = DatabaseFactory.getDatabase().getAttributeProperties(deviceName,
                attributeName);
        for (final Entry<String, String[]> entry : prop.entrySet()) {
            final String name = entry.getKey();
            final String[] value = entry.getValue();
            if (value.length > 0 && !value[0].equalsIgnoreCase(Constants.NOT_SPECIFIED)) {
                result.put(name, value[0]);
            }
        }
        xlogger.exit();
        return result;
    }

    public void removeAttributeProperties(final String... attributeNames) throws DevFailed {
        DatabaseFactory.getDatabase().deleteAttributeProperties(deviceName, attributeNames);
    }

    /**
     * Get an attribute property from tango db
     *
     * @param attributeName
     * @param propertyName
     * @return The property
     * @throws DevFailed
     */
    public String getAttributePropertyFromDB(final String attributeName, final String propertyName) throws DevFailed {
        xlogger.entry(propertyName);
        String[] result = new String[]{};
        final Map<String, String[]> prop = DatabaseFactory.getDatabase().getAttributeProperties(deviceName,
                attributeName);
        if (prop.get(propertyName) != null) {
            // get value
            result = prop.get(propertyName);
            logger.debug(attributeName + " property {} is {}", propertyName, Arrays.toString(result));
        }
        xlogger.exit();
        String single = "";
        if (result.length == 1 && !result[0].isEmpty()) {
            single = result[0];
        }
        return single;
    }

    /**
     * Set attribute property in tango db
     *
     * @param attributeName
     * @param propertyName
     * @param value
     * @throws DevFailed
     */
    public void setAttributePropertyInDB(final String attributeName, final String propertyName, final String value)
            throws DevFailed {
        xlogger.entry(propertyName);

        // insert value in db only if input value is different and not a
        // default value
        // if (checkCurrentValue) {
        // final String presentValue = getAttributePropertyFromDB(attributeName, propertyName);
        // final boolean isADefaultValue = presentValue.isEmpty()
        // && (value.equalsIgnoreCase(AttributePropertiesImpl.NOT_SPECIFIED)
        // || value.equalsIgnoreCase(AttributePropertiesImpl.NO_DIPLAY_UNIT)
        // || value.equalsIgnoreCase(AttributePropertiesImpl.NO_UNIT) || value
        // .equalsIgnoreCase(AttributePropertiesImpl.NO_STD_UNIT));
        // if (!isADefaultValue && !presentValue.equals(value) && !value.isEmpty() && !value.equals("NaN")) {
        // LOGGER.debug("update in DB {}, property {}= {}", new Object[] { attributeName, propertyName, value });
        // final Map<String, String[]> propInsert = new HashMap<String, String[]>();
        // propInsert.put(propertyName, new String[] { value });
        // DatabaseFactory.getDatabase().setAttributeProperties(deviceName, attributeName, propInsert);
        // }
        // } else {
        logger.debug("update in DB {}, property {}= {}", new Object[]{attributeName, propertyName, value});
        final Map<String, String[]> propInsert = new HashMap<String, String[]>();
        propInsert.put(propertyName, new String[]{value});
        DatabaseFactory.getDatabase().setAttributeProperties(deviceName, attributeName, propInsert);
        // }
        xlogger.exit();
    }

    /**
     * Set attribute properties in tango db
     *
     * @param attributeName
     * @param properties
     * @throws DevFailed
     */
    public void setAttributePropertiesInDB(final String attributeName, final Map<String, String[]> properties)
            throws DevFailed {
        xlogger.entry(properties);

        final Map<String, String> currentValues = getAttributePropertiesFromDBSingle(attributeName);
        final Map<String, String[]> propInsert = new HashMap<String, String[]>();
        for (final Entry<String, String[]> entry : properties.entrySet()) {
            final String propertyName = entry.getKey();
            final String[] valueArray = entry.getValue();
            // insert value in db only if input value is different and not a
            // default value
            if (valueArray.length == 1) {
                final String value = valueArray[0];
                final String presentValue = currentValues.get(propertyName);
                boolean isADefaultValue = false;
                if (presentValue != null) {
                    isADefaultValue = presentValue.isEmpty()
                            && (value.equalsIgnoreCase(Constants.NOT_SPECIFIED)
                            || value.equalsIgnoreCase(Constants.NO_DIPLAY_UNIT)
                            || value.equalsIgnoreCase(Constants.NO_UNIT) || value
                            .equalsIgnoreCase(Constants.NO_STD_UNIT));
                }
                if (!isADefaultValue) {
                    if (presentValue == null) {
                        propInsert.put(propertyName, valueArray);
                    } else if (!presentValue.equals(value) && !value.isEmpty() && !value.equals("NaN")) {
                        propInsert.put(propertyName, valueArray);
                    }
                }
            } else {
                // do not check if not a single value
                propInsert.put(propertyName, valueArray);
            }
        }
        logger.debug("update attribute {} properties {} in DB ", attributeName, properties.keySet());
        DatabaseFactory.getDatabase().setAttributeProperties(deviceName, attributeName, propInsert);
        xlogger.exit();
    }

    public String getDeviceName() {
        return deviceName;
    }
}
