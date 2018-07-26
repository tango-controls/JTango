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
import java.lang.reflect.Method;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.ServerManager;
import org.tango.server.annotation.AroundInvoke;
import org.tango.server.annotation.Attribute;
import org.tango.server.annotation.ClassProperty;
import org.tango.server.annotation.Command;
import org.tango.server.annotation.Delete;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.DeviceManagement;
import org.tango.server.annotation.DeviceProperties;
import org.tango.server.annotation.DeviceProperty;
import org.tango.server.annotation.DynamicManagement;
import org.tango.server.annotation.Init;
import org.tango.server.annotation.Pipe;
import org.tango.server.annotation.Schedule;
import org.tango.server.annotation.State;
import org.tango.server.annotation.Status;
import org.tango.server.annotation.TransactionType;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;
import org.tango.utils.ReflectionScanner;

import fr.esrf.Tango.DevFailed;

/**
 * Build a {@link Device}
 * 
 * @author ABEILLE
 * 
 */
public final class DeviceBuilder {

    private static final String MDC_DEVICE_NAME_KEY = "deviceName";
    private static final String MUST_BE_UNIQUE = " must be unique";
    private final Logger logger = LoggerFactory.getLogger(DeviceBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(DeviceBuilder.class);
    private final Class<?> clazz;
    private final String className;
    private final String name;
    private DeviceImpl device;
    private Object businessObject;

    DeviceBuilder(final Class<?> clazz, final String className, final String name) {
        this.clazz = clazz;
        this.className = className;
        this.name = name;

    }

    public DeviceImpl createDevice() throws DevFailed {
        MDC.put(MDC_DEVICE_NAME_KEY, name);
        xlogger.entry();

        checkIsTangoDevice(clazz, name);
        DeviceManagerBuilder.clear();
        DynamicManagerBuilder.clear();

        try {
            final Device annotation = clazz.getAnnotation(Device.class);
            TransactionType txType = ServerManager.getInstance().getTransactionType();
            if (txType == null) {
                txType = annotation.transactionType();
            }
            final String deviceType = annotation.deviceType();
            // create the device
            businessObject = clazz.newInstance();
            device = new DeviceImpl(name, className, txType, businessObject, deviceType);
            addSuperDevices();

            // create default attributes and commands
            final ReflectionScanner deviceImplScanner = new ReflectionScanner(device.getClass());

            createBusinessObjectAttrField(deviceImplScanner, true);
            createBusinessObjectAttrCmd(deviceImplScanner, true);

            final ReflectionScanner boScanner = new ReflectionScanner(clazz);

            createBusinessObjectFields(boScanner);
            createBusinessObjectAttrField(boScanner, false);
            createBusinessObjectAttrCmd(boScanner, false);
            createBusinessObjectInitDelete(boScanner);
            createBusinessObjectAroundInvoke(boScanner);
            createBusinessObjectDeviceProperties(boScanner);
            createBusinessObjectProps(boScanner);
            createBusinessObjectPipes(boScanner);
            createBusinessObjectState(boScanner);

        } catch (final InstantiationException e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final IllegalAccessException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        device.initDevice();
        xlogger.exit();
        return device;
    }

    private void addSuperDevices() throws DevFailed {
        Class<?> superDeviceClass = clazz.getSuperclass();
        while (superDeviceClass != null && superDeviceClass.getAnnotation(Device.class) != null) {
            logger.debug("adding super class to device {}", superDeviceClass.getCanonicalName());
            final ReflectionScanner superClassScanner = new ReflectionScanner(superDeviceClass);
            createBusinessObjectAttrCmd(superClassScanner, false);
            createBusinessObjectAttrField(superClassScanner, false);
            createBusinessObjectProps(superClassScanner);
            createBusinessObjectFields(superClassScanner);

            createBusinessObjectInitDelete(superClassScanner);
            createBusinessObjectPipes(superClassScanner);
            createBusinessObjectState(superClassScanner);
            superDeviceClass = superDeviceClass.getSuperclass();
        }
    }

    private void createBusinessObjectAttrCmd(final ReflectionScanner scanner, final boolean isOnDeviceImpl)
            throws DevFailed {

        // Command
        final Set<Method> cmds = scanner.getMethodsAnnotatedWith(Command.class);
        if (cmds != null) {
            final CommandBuilder cmd = new CommandBuilder();
            for (final Method method : cmds) {
                cmd.build(device, businessObject, method, isOnDeviceImpl);
            }
        }
        // Attribute
        final Set<Method> attrs = scanner.getMethodsAnnotatedWith(Attribute.class);
        if (attrs != null) {
            final AttributeMethodBuilder attr = new AttributeMethodBuilder();
            for (final Method method : attrs) {
                attr.build(device, businessObject, method, isOnDeviceImpl);
            }
        }
    }

    private void createBusinessObjectInitDelete(final ReflectionScanner scanner) throws DevFailed {
        // Init
        final Set<Method> initM = scanner.getMethodsAnnotatedWith(Init.class);
        if (initM != null && initM.size() > 1) {
            throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, Init.class + MUST_BE_UNIQUE);
        }
        if (initM != null && initM.size() == 1) {
            new InitBuilder().build(initM.iterator().next(), device, businessObject);
        }
        // Delete
        final Set<Method> deleteM = scanner.getMethodsAnnotatedWith(Delete.class);
        if (deleteM != null && deleteM.size() > 1) {
            throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, Delete.class + MUST_BE_UNIQUE);
        }

        if (deleteM != null && deleteM.size() == 1) {
            new DeleteBuilder().build(deleteM.iterator().next(), device);
        }
        // Schedule
        final Set<Method> scheduleM = scanner.getMethodsAnnotatedWith(Schedule.class);
        if (scheduleM != null && scheduleM.size() == 1) {
            new DeviceSchedulerBuilder().build(scheduleM, device);
        }
    }

    private void createBusinessObjectAroundInvoke(final ReflectionScanner scanner) throws DevFailed {
        // AroundInvoke
        final Set<Method> invokeM = scanner.getMethodsAnnotatedWith(AroundInvoke.class);
        if (invokeM != null && invokeM.size() > 1) {
            throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, AroundInvoke.class + MUST_BE_UNIQUE);
        }
        if (invokeM != null && invokeM.size() == 1) {
            new AroundInvokeBuilder().build(invokeM.iterator().next(), device, businessObject);
        }
    }

    private void createBusinessObjectAttrField(final ReflectionScanner scanner, final boolean isOnDeviceImpl)
            throws DevFailed {
        // Attribute
        final Set<Field> attributeF = scanner.getFieldsAnnotatedWith(Attribute.class);
        if (attributeF != null) {
            final AttributeFieldBuilder attr = new AttributeFieldBuilder();
            for (final Field field : attributeF) {
                attr.build(device, businessObject, field, isOnDeviceImpl);
            }
        }

    }

    private void createBusinessObjectPipes(final ReflectionScanner scanner) throws DevFailed {
        // Pipe
        final Set<Field> fields = scanner.getFieldsAnnotatedWith(Pipe.class);
        if (fields != null) {
            final PipeBuilder pipe = new PipeBuilder();
            for (final Field field : fields) {
                pipe.build(device, businessObject, field);
            }
        }

    }

    private void createBusinessObjectFields(final ReflectionScanner scanner) throws DevFailed {
        // DynamicManagement
        final Set<Field> dynF = scanner.getFieldsAnnotatedWith(DynamicManagement.class);
        if (dynF != null) {
            if (dynF.size() > 1) {
                throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, DynamicManagement.class
                        + MUST_BE_UNIQUE);
            }
            if (dynF.size() == 1) {
                new DynamicManagerBuilder().build(dynF.iterator().next(), device, businessObject);
            }
        }

        // DeviceManagement
        final Set<Field> deviceF = scanner.getFieldsAnnotatedWith(DeviceManagement.class);
        if (deviceF != null) {
            if (deviceF.size() > 1) {
                throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, DeviceManagement.class
                        + MUST_BE_UNIQUE);
            }
            if (deviceF.size() == 1) {
                new DeviceManagerBuilder().build(deviceF.iterator().next(), device, businessObject);
            }
        }
    }

    private void createBusinessObjectState(final ReflectionScanner scanner) throws DevFailed {

        // State
        final Set<Field> stateF = scanner.getFieldsAnnotatedWith(State.class);
        if (stateF != null) {
            final StateBuilder stateB = new StateBuilder();
            if (stateF.size() > 1) {
                throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, State.class + MUST_BE_UNIQUE);
            }
            if (stateF.size() == 1) {
                stateB.build(clazz, stateF.iterator().next(), device, businessObject);
            }
        }
        // Status
        final Set<Field> statusF = scanner.getFieldsAnnotatedWith(Status.class);
        if (statusF != null) {
            final StatusBuilder statusB = new StatusBuilder();
            if (statusF.size() > 1) {
                throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, Status.class + MUST_BE_UNIQUE);
            }
            if (statusF.size() == 1) {
                statusB.build(clazz, statusF.iterator().next(), device, businessObject);
            }
        }

    }

    private void createBusinessObjectDeviceProperties(final ReflectionScanner scanner) throws DevFailed {

        // DeviceProperties
        final Set<Field> devicePropsF = scanner.getFieldsAnnotatedWith(DeviceProperties.class);
        if (devicePropsF != null) {
            final DevicePropertiesBuilder devicePropsB = new DevicePropertiesBuilder();
            if (devicePropsF.size() > 1) {
                throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, DeviceProperties.class
                        + MUST_BE_UNIQUE);
            }
            if (devicePropsF.size() == 1) {
                devicePropsB.build(clazz, devicePropsF.iterator().next(), device, businessObject);
            }
        }
    }

    private void createBusinessObjectProps(final ReflectionScanner scanner) throws DevFailed {

        // DeviceProperties
        final Set<Field> devicePropsF = scanner.getFieldsAnnotatedWith(DeviceProperties.class);
        if (devicePropsF != null) {
            final DevicePropertiesBuilder devicePropsB = new DevicePropertiesBuilder();
            if (devicePropsF.size() > 1) {
                throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, DeviceProperties.class
                        + MUST_BE_UNIQUE);
            }
            if (devicePropsF.size() == 1) {
                devicePropsB.build(clazz, devicePropsF.iterator().next(), device, businessObject);
            }
        }
        // DeviceProperty
        final Set<Field> devicePropF = scanner.getFieldsAnnotatedWith(DeviceProperty.class);
        if (devicePropF != null) {
            final DevicePropertyBuilder devicePropB = new DevicePropertyBuilder();
            for (final Field field : devicePropF) {
                devicePropB.build(clazz, field, device, businessObject);
            }
        }
        // ClassProperty
        final Set<Field> classPropF = scanner.getFieldsAnnotatedWith(ClassProperty.class);
        if (classPropF != null) {
            final ClassPropertyBuilder classPropB = new ClassPropertyBuilder();
            for (final Field field : classPropF) {
                classPropB.build(clazz, field, device, businessObject);
            }
        }
    }

    private void checkIsTangoDevice(final Class<?> clazz, final String name) throws DevFailed {
        if (clazz == null) {
            throw DevFailedUtils.newDevFailed("INIT_FAILED", "create device " + name + " - is not a tango device");
        }
        if (clazz.getAnnotation(Device.class) == null) {
            throw DevFailedUtils.newDevFailed("INIT_FAILED", "create device " + name + " of class " + clazz.getName()
                    + " - is not a tango device");
        }
    }
}
