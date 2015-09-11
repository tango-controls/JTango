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
package org.tango.server.cache;

import org.tango.server.Constants;
import org.tango.server.PolledObjectConfig;
import org.tango.server.properties.AttributePropertiesManager;

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;

/**
 * Utils for polling
 *
 * @author ABEILLE
 *
 */
public final class PollingUtils {

    private PollingUtils() {

    }

    /**
     * Configure polling
     *
     * @param pollingPeriod
     *            the polling period
     * @param config
     *            the polling config
     * @param attributePropertiesManager
     *            the attribute properties manager
     * @throws DevFailed
     */
    public static void configurePolling(final int pollingPeriod, final PolledObjectConfig config,
            final AttributePropertiesManager attributePropertiesManager) throws DevFailed {
        final int unsignedPolling = Math.abs(pollingPeriod);
        config.setPolled(true);
        config.setPollingPeriod(unsignedPolling);
        attributePropertiesManager.setAttributePropertyInDB(config.getName(), Constants.IS_POLLED, "true");
        attributePropertiesManager.setAttributePropertyInDB(config.getName(), Constants.POLLING_PERIOD,
                Integer.toString(unsignedPolling));
    }

    /**
     * Reset polling
     *
     * @param config
     *            the polling config
     * @param attributePropertiesManager
     *            the attribute properties manager
     * @throws DevFailed
     */
    public static void resetPolling(final PolledObjectConfig config,
            final AttributePropertiesManager attributePropertiesManager) throws DevFailed {
        config.setPolled(false);
        config.setPollingPeriod(0);
        attributePropertiesManager.setAttributePropertyInDB(config.getName(), Constants.IS_POLLED, "false");
        attributePropertiesManager.setAttributePropertyInDB(config.getName(), Constants.POLLING_PERIOD, "0");
    }

    /**
     * Update polling config in tango db
     *
     * @param config
     * @param attributePropertiesManager
     * @throws DevFailed
     */
    public static void updatePollingConfigFromDB(final PolledObjectConfig config,
            final AttributePropertiesManager attributePropertiesManager) throws DevFailed {
        final String isPolledProp = attributePropertiesManager.getAttributePropertyFromDB(config.getName(),
                Constants.IS_POLLED);
        if (!isPolledProp.isEmpty()) {
            config.setPolled(Boolean.valueOf(isPolledProp));
            final String periodProp = attributePropertiesManager.getAttributePropertyFromDB(config.getName(),
                    Constants.POLLING_PERIOD);
            if (!periodProp.isEmpty()) {
                config.setPollingPeriod(Integer.valueOf(periodProp));
            }
        }
    }

    public static String toString(final DevFailed e) {
        final StringBuilder buffer = new StringBuilder();
        if (e != null) {
            if (e.errors != null) {
                for (final DevError error : e.errors) {
                    buffer.append("\tReason = ").append(error.reason).append("\n");
                    buffer.append("\tDesc = ").append(error.desc).append("\n");
                    buffer.append("\tOrigin = ").append(error.origin).append("\n");
                }
            } else {
                buffer.append("EMPTY DevFailed");
            }
        }
        return buffer.toString();
    }

}
