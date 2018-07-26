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
import org.tango.server.annotation.DeviceManagement;
import org.tango.server.device.DeviceManager;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

final class DeviceManagerBuilder {
    private final Logger logger = LoggerFactory.getLogger(DeviceManagerBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(DeviceManagerBuilder.class);
    /**
     * keep record of DeviceManagers for device inheritance
     */
    private static final Map<String, DeviceManager> DEV_MNGERS = new HashMap<String, DeviceManager>();

    public static void clear() {
        DEV_MNGERS.clear();
    }

    /**
     * create a {@link DeviceManager} {@link DeviceManagement}
     * 
     * @param field
     * @param device
     * @param businessObject
     * @throws DevFailed
     */
    public void build(final Field field, final DeviceImpl device, final Object businessObject) throws DevFailed {
        xlogger.entry();
        final String name = field.getName();
        logger.debug("Has a DeviceManagerManagement : {}", name);
        BuilderUtils.checkStatic(field);

        final String setterName = BuilderUtils.SET + name.substring(0, 1).toUpperCase(Locale.ENGLISH)
                + name.substring(1);
        try {
            final String deviceName = device.getName().toLowerCase(Locale.ENGLISH);

            final Method setter = businessObject.getClass().getMethod(setterName, DeviceManager.class);
            if (DEV_MNGERS.containsKey(deviceName)) {
                setter.invoke(businessObject, DEV_MNGERS.get(deviceName));
            } else {
                final DeviceManager mnger = new DeviceManager(device);
                setter.invoke(businessObject, mnger);
                DEV_MNGERS.put(deviceName, mnger);
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
