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
package org.tango.server.attribute;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.DeviceState;
import org.tango.server.ExceptionMessages;
import org.tango.server.StateMachineBehavior;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;

/**
 * Behavior a Tango attribute using java reflection
 *
 * @author ABEILLE
 *
 */
public final class ReflectAttributeBehavior implements IAttributeBehavior {

    private final Logger logger = LoggerFactory.getLogger(ReflectAttributeBehavior.class);

    private final AttributeConfiguration config;
    private final Method getter;
    private final Method setter;
    private final Object businessObject;

    /**
     * Ctr
     *
     * @param config
     *            The attribute configuration
     * @param businessObject
     *            The object that contains the attribute
     * @param getter
     *            The method to read the attribute
     * @param setter
     *            The method to write the attribute
     */
    public ReflectAttributeBehavior(final AttributeConfiguration config, final Object businessObject,
            final Method getter, final Method setter) {
        this.businessObject = businessObject;
        this.getter = getter;
        this.setter = setter;
        this.config = config;
    }

    @Override
    public AttributeValue getValue() throws DevFailed {
        final AttributeValue result;
        Object value = null;
        if (getter != null) {
            try {
                logger.debug("read attribute {} from method '{}'", config.getName(), getter);
                value = getter.invoke(businessObject);
            } catch (final IllegalArgumentException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final IllegalAccessException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final InvocationTargetException e) {
                throwDevFailed(e);
            }
        }
        result = buildAttributeValue(value);
        return result;
    }

    private void throwDevFailed(final InvocationTargetException e) throws DevFailed {
        if (e.getCause() instanceof DevFailed) {
            throw (DevFailed) e.getCause();
        } else {
            throw DevFailedUtils.newDevFailed(e.getCause());
        }
    }

    private AttributeValue buildAttributeValue(final Object value) throws DevFailed {
        AttributeValue result = null;
        if (value == null) {
            result = new AttributeValue(null);
        } else if (value instanceof AttributeValue) {
            result = (AttributeValue) value;
        } else if (value instanceof DeviceState) {
            final DeviceState state = (DeviceState) value;
            result = new AttributeValue(state.getDevState());
        } else if (value instanceof DeviceState[]) {
            final DeviceState[] in = (DeviceState[]) value;
            final DevState[] devStates = new DevState[in.length];
            for (int i = 0; i < in.length; i++) {
                devStates[i] = in[i].getDevState();
            }
            result = new AttributeValue(devStates);
        } else if (value instanceof Enum) {
            final Enum<?> enumValue = (Enum<?>) value;
            result = new AttributeValue((short) enumValue.ordinal());
        } else {
            result = new AttributeValue(value);
        }
        return result;
    }

    @Override
    public void setValue(final AttributeValue value) throws DevFailed {

        if (setter != null && setter.getParameterTypes().length == 1) {
            final Class<?> paramSetter = getParamSetter();
            try {
                if (paramSetter.equals(DeviceState.class)) {
                    setter.invoke(businessObject, DeviceState.getDeviceState((DevState) value.getValue()));
                } else if (Enum.class.isAssignableFrom(paramSetter)) {
                    final short enumValue = (Short) value.getValue();
                    setter.invoke(businessObject, paramSetter.getEnumConstants()[enumValue]);
                } else if (paramSetter.equals(DeviceState[].class)) {
                    final DevState[] states = (DevState[]) value.getValue();
                    final DeviceState[] devStates = new DeviceState[states.length];
                    for (int i = 0; i < devStates.length; i++) {
                        devStates[i] = DeviceState.getDeviceState(states[i]);
                    }
                    setter.invoke(businessObject, (Object) devStates);
                } else {
                    final Class<?> input = getInputClass(value);
                    checkParamTypes(value, paramSetter, input);
                    setter.invoke(businessObject, value.getValue());
                }
            } catch (final IllegalArgumentException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final IllegalAccessException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final InvocationTargetException e) {
                throwDevFailed(e);
            }
        }

    }

    private void checkParamTypes(final AttributeValue value, final Class<?> paramSetter, final Class<?> input)
            throws DevFailed {
        if (!paramSetter.isAssignableFrom(input)) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_OPT_PROP,
                    "type mismatch, expected was " + setter.getParameterTypes()[0].getCanonicalName() + ", input is "
                            + value.getValue().getClass().getCanonicalName());
        }
    }

    private Class<?> getInputClass(final AttributeValue value) {
        Class<?> input = value.getValue().getClass();
        if (Number.class.isAssignableFrom(input) || Boolean.class.isAssignableFrom(input)) {
            input = ClassUtils.wrapperToPrimitive(value.getValue().getClass());
        }
        return input;
    }

    private Class<?> getParamSetter() {
        Class<?> paramSetter = setter.getParameterTypes()[0];
        if (Number.class.isAssignableFrom(paramSetter) || Boolean.class.isAssignableFrom(paramSetter)) {
            paramSetter = ClassUtils.wrapperToPrimitive(setter.getParameterTypes()[0]);
        }
        return paramSetter;
    }

    @Override
    public AttributeConfiguration getConfiguration() {
        return config;
    }

    @Override
    public StateMachineBehavior getStateMachine() {
        return null;
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append("getter", getter);
        builder.append("setter", setter);
        return builder.toString();
    }

}
