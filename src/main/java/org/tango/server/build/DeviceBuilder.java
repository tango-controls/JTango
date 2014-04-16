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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.tango.server.annotation.Schedule;
import org.tango.server.annotation.State;
import org.tango.server.annotation.Status;
import org.tango.server.annotation.TransactionType;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * Build a {@link Device}
 * 
 * @author ABEILLE
 * 
 */
public final class DeviceBuilder {

    private static final String MUST_BE_UNIQUE = " must be unique";
    // private static Logger logger = LoggerFactory.getLogger(DeviceBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(DeviceBuilder.class);
    private static final DeviceBuilder INSTANCE = new DeviceBuilder();

    private DeviceBuilder() {
    }

    public DeviceImpl createDevice(final Class<?> clazz, final String className, final String name) throws DevFailed {
        MDC.put("deviceName", name);
        xlogger.entry();
        DeviceImpl device = null;
        checkIsTangoDevice(clazz, name);

        try {
            final Device annotation = clazz.getAnnotation(Device.class);
            TransactionType txType = ServerManager.getInstance().getTransactionType();
            if (txType == null) {
                txType = annotation.transactionType();
            }
            final String deviceType = annotation.deviceType();
            // create the device
            final Object businessObject = clazz.newInstance();
            device = new DeviceImpl(name, className, txType, businessObject, deviceType);

            // create the user device methods
            final Map<Class<?>, List<Method>> methodsBO = getAnnotatedMethods(clazz);
            createBusinessObjectAttrCmd(methodsBO, device, businessObject);
            createBusinessObjectInitDelete(methodsBO, device, businessObject);
            createBusinessObjectAroundInvoke(methodsBO, device, businessObject);
            // create the user device fields
            final Map<Class<?>, List<Field>> fields = getAnnotatedFields(clazz);
            createBusinessObjectFields(fields, device, businessObject);
            createBusinessObjectProps(fields, clazz, device, businessObject);
            createBusinessObjectState(fields, clazz, device, businessObject);

            // create default attributes and commands
            createDeviceImplMethods(device, businessObject);
            createDeviceImplFields(device, businessObject);
        } catch (final InstantiationException e) {
            DevFailedUtils.throwDevFailed(e);
        } catch (final IllegalAccessException e) {
            DevFailedUtils.throwDevFailed(e);
        }
        device.initDevice();
        xlogger.exit();
        return device;
    }

    private Map<Class<?>, List<Method>> getAnnotatedMethods(final Class<?> clazz) {
        final Map<Class<?>, List<Method>> methods = new HashMap<Class<?>, List<Method>>();
        for (final Method method : clazz.getDeclaredMethods()) {
            final Annotation[] annotations = method.getAnnotations();
            for (final Annotation annotation : annotations) {
                if (methods.containsKey(annotation.annotationType())) {
                    methods.get(annotation.annotationType()).add(method);
                } else {
                    final List<Method> list = new ArrayList<Method>();
                    list.add(method);
                    methods.put(annotation.annotationType(), list);
                }
            }
        }
        return methods;
    }

    private void createDeviceImplMethods(final DeviceImpl device, final Object businessObject) throws DevFailed {
        final Map<Class<?>, List<Method>> deviceImplMethods = getAnnotatedMethods(device.getClass());

        final List<Method> cmds = deviceImplMethods.get(Command.class);
        if (cmds != null) {
            final CommandBuilder cmd = new CommandBuilder();
            for (final Method method : cmds) {
                cmd.build(device, businessObject, method, true);
            }
        }
        final List<Method> attrs = deviceImplMethods.get(Attribute.class);
        if (attrs != null) {
            final AttributeMethodBuilder attr = new AttributeMethodBuilder();
            for (final Method method : attrs) {
                attr.build(device, businessObject, method, true);
            }
        }
    }

    private void createBusinessObjectAttrCmd(final Map<Class<?>, List<Method>> methodsBO, final DeviceImpl device,
            final Object businessObject) throws DevFailed {
        // Command
        final List<Method> cmds = methodsBO.get(Command.class);
        if (cmds != null) {
            final CommandBuilder cmd = new CommandBuilder();
            for (final Method method : cmds) {
                cmd.build(device, businessObject, method, false);
            }
        }
        // Attribute
        final List<Method> attrs = methodsBO.get(Attribute.class);
        if (attrs != null) {
            final AttributeMethodBuilder attr = new AttributeMethodBuilder();
            for (final Method method : attrs) {
                attr.build(device, businessObject, method, false);
            }
        }
    }

    private void createBusinessObjectInitDelete(final Map<Class<?>, List<Method>> methodsBO, final DeviceImpl device,
            final Object businessObject) throws DevFailed {
        // Init
        final InitBuilder init = new InitBuilder();
        final List<Method> initM = methodsBO.get(Init.class);
        if (initM != null && initM.size() > 1) {
            DevFailedUtils.throwDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, Init.class + MUST_BE_UNIQUE);
        }
        if (initM != null && initM.size() == 1) {
            init.build(initM.get(0), device, businessObject);
        }
        // Delete
        final DeleteBuilder delete = new DeleteBuilder();
        final List<Method> deleteM = methodsBO.get(Delete.class);
        if (deleteM != null && deleteM.size() > 1) {
            DevFailedUtils.throwDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, Delete.class + MUST_BE_UNIQUE);
        }
        if (deleteM != null && deleteM.size() == 1) {
            delete.build(deleteM.get(0), device);
        }

        final List<Method> scheduleM = methodsBO.get(Schedule.class);
        if (scheduleM != null && scheduleM.size() == 1) {
            new DeviceSchedulerBuilder().build(scheduleM, device);
        }
    }

    private void createBusinessObjectAroundInvoke(final Map<Class<?>, List<Method>> methodsBO, final DeviceImpl device,
            final Object businessObject) throws DevFailed {
        // AroundInvoke
        final AroundInvokeBuilder invoke = new AroundInvokeBuilder();
        final List<Method> invokeM = methodsBO.get(AroundInvoke.class);
        if (invokeM != null && invokeM.size() > 1) {
            DevFailedUtils.throwDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, AroundInvoke.class + MUST_BE_UNIQUE);
        }
        if (invokeM != null && invokeM.size() == 1) {
            invoke.build(invokeM.get(0), device, businessObject);
        }
    }

    private Map<Class<?>, List<Field>> getAnnotatedFields(final Class<?> clazz) {
        final Map<Class<?>, List<Field>> fields = new HashMap<Class<?>, List<Field>>();
        for (final Field field : clazz.getDeclaredFields()) {
            final Annotation[] annotations = field.getAnnotations();
            for (final Annotation annotation : annotations) {
                if (fields.containsKey(annotation.annotationType())) {
                    fields.get(annotation.annotationType()).add(field);
                } else {
                    final List<Field> list = new ArrayList<Field>();
                    list.add(field);
                    fields.put(annotation.annotationType(), list);
                }
            }
        }
        return fields;
    }

    private void createDeviceImplFields(final DeviceImpl device, final Object businessObject) throws DevFailed {
        final Map<Class<?>, List<Field>> fields = getAnnotatedFields(device.getClass());
        final AttributeFieldBuilder attr = new AttributeFieldBuilder();
        final List<Field> attributeF = fields.get(Attribute.class);
        if (attributeF != null) {
            // default attributes declared on DeviceImpl4
            for (final Field field : attributeF) {
                attr.build(device, businessObject, field, true);
            }
        }
    }

    private void createBusinessObjectFields(final Map<Class<?>, List<Field>> fields, final DeviceImpl device,
            final Object businessObject) throws DevFailed {
        // Attribute
        final List<Field> attributeF = fields.get(Attribute.class);
        if (attributeF != null) {
            final AttributeFieldBuilder attr = new AttributeFieldBuilder();
            for (final Field field : attributeF) {
                attr.build(device, businessObject, field, false);
            }
        }
        // DynamicManagement
        final List<Field> dynF = fields.get(DynamicManagement.class);
        if (dynF != null) {
            final DynamicManagerBuilder dynB = new DynamicManagerBuilder();
            if (dynF.size() > 1) {
                DevFailedUtils.throwDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, DynamicManagement.class
                        + MUST_BE_UNIQUE);
            }
            if (dynF.size() == 1) {
                dynB.build(dynF.get(0), device, businessObject);
            }
        }

        // DeviceManagement
        final List<Field> deviceF = fields.get(DeviceManagement.class);
        if (deviceF != null) {
            final DeviceManagerBuilder dynB = new DeviceManagerBuilder();
            if (deviceF.size() > 1) {
                DevFailedUtils.throwDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, DynamicManagement.class
                        + MUST_BE_UNIQUE);
            }
            if (deviceF.size() == 1) {
                dynB.build(deviceF.get(0), device, businessObject);
            }
        }
    }

    private void createBusinessObjectState(final Map<Class<?>, List<Field>> fields, final Class<?> clazz,
            final DeviceImpl device, final Object businessObject) throws DevFailed {

        // State
        final List<Field> stateF = fields.get(State.class);
        if (stateF != null) {
            final StateBuilder stateB = new StateBuilder();
            if (stateF.size() > 1) {
                DevFailedUtils.throwDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, State.class + MUST_BE_UNIQUE);
            }
            if (stateF.size() == 1) {
                stateB.build(clazz, stateF.get(0), device, businessObject);
            }
        }
        // Status
        final List<Field> statusF = fields.get(Status.class);
        if (statusF != null) {
            final StatusBuilder statusB = new StatusBuilder();
            if (statusF.size() > 1) {
                DevFailedUtils.throwDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, Status.class + MUST_BE_UNIQUE);
            }
            if (statusF.size() == 1) {
                statusB.build(clazz, statusF.get(0), device, businessObject);
            }
        }

    }

    private void createBusinessObjectProps(final Map<Class<?>, List<Field>> fields, final Class<?> clazz,
            final DeviceImpl device, final Object businessObject) throws DevFailed {

        // DeviceProperties
        final List<Field> devicePropsF = fields.get(DeviceProperties.class);
        if (devicePropsF != null) {
            final DevicePropertiesBuilder devicePropsB = new DevicePropertiesBuilder();
            if (devicePropsF.size() > 1) {
                DevFailedUtils.throwDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, DeviceProperties.class
                        + MUST_BE_UNIQUE);
            }
            if (devicePropsF.size() == 1) {
                devicePropsB.build(clazz, devicePropsF.get(0), device, businessObject);
            }
        }
        // DeviceProperty
        final List<Field> devicePropF = fields.get(DeviceProperty.class);
        if (devicePropF != null) {
            final DevicePropertyBuilder devicePropB = new DevicePropertyBuilder();
            for (final Field field : devicePropF) {
                devicePropB.build(clazz, field, device, businessObject);
            }
        }
        // ClassProperty
        final List<Field> classPropF = fields.get(ClassProperty.class);
        if (classPropF != null) {
            final ClassPropertyBuilder classPropB = new ClassPropertyBuilder();
            for (final Field field : classPropF) {
                classPropB.build(clazz, field, device, businessObject);
            }
        }
    }

    public static DeviceBuilder getInstance() {
        return INSTANCE;
    }

    private void checkIsTangoDevice(final Class<?> clazz, final String name) throws DevFailed {
        if (clazz == null) {
            DevFailedUtils.throwDevFailed("INIT_FAILED", "create device " + name + " - is not a tango device");
        }
        if (clazz.getAnnotation(Device.class) == null) {
            DevFailedUtils.throwDevFailed("INIT_FAILED", "create device " + name + " of class " + clazz.getName()
                    + " - is not a tango device");
        }
    }
}
