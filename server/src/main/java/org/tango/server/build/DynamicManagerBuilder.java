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
package org.tango.server.build;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.annotation.DynamicManagement;
import org.tango.server.dynamic.DynamicManager;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * Build a {@link DynamicManagement}
 * 
 * @author ABEILLE
 * 
 */
final class DynamicManagerBuilder {

    private final Logger logger = LoggerFactory.getLogger(DynamicManagerBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(DynamicManagerBuilder.class);

    /**
     * keep record of DynamicManager for device inheritance
     */
    private static final Map<String, DynamicManager> DYN_MNGRS = new HashMap<String, DynamicManager>();

    public static void clear() {
        DYN_MNGRS.clear();
    }

    /**
     * create a {@link DynamicManager} {@link DynamicManagement}
     * 
     * @param field
     * @param device
     * @param businessObject
     * @throws DevFailed
     */
    public void build(final Field field, final DeviceImpl device, final Object businessObject) throws DevFailed {
        xlogger.entry();
        final String name = field.getName();
        logger.debug("Has a DynamicAttributeManagement : {}", name);
        BuilderUtils.checkStatic(field);

        final String setterName = BuilderUtils.SET + name.substring(0, 1).toUpperCase(Locale.ENGLISH)
                + name.substring(1);
        try {
            final String deviceName = device.getName().toLowerCase(Locale.ENGLISH);
            final Method setter = businessObject.getClass().getMethod(setterName, DynamicManager.class);
            if (DYN_MNGRS.containsKey(deviceName)) {
                setter.invoke(businessObject, DYN_MNGRS.get(deviceName));
            } else {
                final DynamicManager mnger = new DynamicManager(device);
                setter.invoke(businessObject, mnger);
                DYN_MNGRS.put(deviceName, mnger);
            }
        } catch (final IllegalArgumentException e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final IllegalAccessException e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final InvocationTargetException e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final NoSuchMethodException e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final SecurityException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        xlogger.exit();
    }
}
