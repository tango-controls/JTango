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
package org.tango.server.pipe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.ReflectAttributeBehavior;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

public final class ReflectPipeBehavior implements IPipeBehavior {

    private final Logger logger = LoggerFactory.getLogger(ReflectAttributeBehavior.class);

    private final PipeConfiguration config;
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
    public ReflectPipeBehavior(final PipeConfiguration config, final Object businessObject, final Method getter,
            final Method setter) {
        this.businessObject = businessObject;
        this.getter = getter;
        this.setter = setter;
        this.config = config;
    }

    @Override
    public PipeValue getValue() throws DevFailed {
        PipeValue result = null;
        if (getter != null) {
            try {
                logger.debug("read pipe {} from method '{}'", config.getName(), getter);
                result = (PipeValue) getter.invoke(businessObject);
            } catch (final IllegalArgumentException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final IllegalAccessException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final InvocationTargetException e) {
                throwDevFailed(e);
            }
        }
        return result;
    }

    @Override
    public void setValue(final PipeValue value) throws DevFailed {
        if (setter != null) {
            try {
                setter.invoke(businessObject, value);
            } catch (final IllegalArgumentException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final IllegalAccessException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final InvocationTargetException e) {
                throwDevFailed(e);
            }
        }
    }

    @Override
    public StateMachineBehavior getStateMachine() throws DevFailed {
        // TODO Auto-generated method stub
        return null;
    }

    private void throwDevFailed(final InvocationTargetException e) throws DevFailed {
        if (e.getCause() instanceof DevFailed) {
            throw (DevFailed) e.getCause();
        } else {
            throw DevFailedUtils.newDevFailed(e.getCause());
        }
    }

    @Override
    public PipeConfiguration getConfiguration() {
        return config;
    }

}
