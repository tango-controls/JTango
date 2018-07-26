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
package org.tango.server.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.StateMachineBehavior;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

public final class ReflectCommandBehavior implements ICommandBehavior {

    private final XLogger xlogger = XLoggerFactory.getXLogger(ReflectCommandBehavior.class);

    private final Method executeMethod;
    private final Object businessObject;
    private final CommandConfiguration config;

    public ReflectCommandBehavior(final Method executeMethod, final Object businessObject,
            final CommandConfiguration config) {
        this.executeMethod = executeMethod;
        this.businessObject = businessObject;
        this.config = config;
    }

    @Override
    public Object execute(final Object arg) throws DevFailed {
        xlogger.entry();
        Object obj = null;
        try {
            if (!config.getInType().equals(Void.class)) {
                checkInputType(arg);
                // execute with params
                obj = executeMethod.invoke(businessObject, arg);
            } else {
                // execute without params
                obj = executeMethod.invoke(businessObject);
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

        xlogger.exit();
        return obj;
    }

    private void checkInputType(final Object arg) throws DevFailed {
        final Class<?>[] paramType = executeMethod.getParameterTypes();
        if (paramType.length > 1) {
            throw DevFailedUtils.newDevFailed("INIT_FAILED", "Command can have only one parameter");
        }
        Class<?> paramMethod = paramType[0];
        if (Number.class.isAssignableFrom(paramMethod) || Boolean.class.isAssignableFrom(paramMethod)) {
            paramMethod = ClassUtils.wrapperToPrimitive(paramMethod);
        }
        Class<?> input = arg.getClass();
        if (Number.class.isAssignableFrom(input) || Boolean.class.isAssignableFrom(input)) {
            input = ClassUtils.wrapperToPrimitive(arg.getClass());
        }
        if (!paramMethod.isAssignableFrom(input)) {
            throw DevFailedUtils.newDevFailed("TYPE_ERROR", "type mismatch, expected was " + paramMethod.getCanonicalName()
                    + ", input is " + arg.getClass().getCanonicalName());
        }
    }

    @Override
    public CommandConfiguration getConfiguration() {
        return config;
    }

    @Override
    public StateMachineBehavior getStateMachine() {
        return null;
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append("executeMethod", executeMethod);
        return builder.toString();
    }

}
