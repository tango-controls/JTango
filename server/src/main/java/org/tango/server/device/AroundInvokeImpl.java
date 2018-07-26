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
package org.tango.server.device;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.InvocationContext;
import org.tango.server.annotation.AroundInvoke;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * {@link AroundInvoke} implementation
 *
 * @author ABEILLE
 *
 */
public final class AroundInvokeImpl {

    // private final static Logger logger =
    // LoggerFactory.getLogger(AroundInvokeImpl.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(AroundInvokeImpl.class);

    private final Object businessObject;
    private final Method aroundInvokeMethod;

    /**
     * Ctr
     *
     * @param businessObject
     * @param aroundInvokeMethod
     */
    public AroundInvokeImpl(final Object businessObject, final Method aroundInvokeMethod) {
        this.businessObject = businessObject;
        this.aroundInvokeMethod = aroundInvokeMethod;
    }

    /**
     * Call aroundInvoke implementation
     *
     * @param ctx
     *            invocation context
     * @throws DevFailed
     */
    public void aroundInvoke(final InvocationContext ctx) throws DevFailed {
        xlogger.entry();
        if (aroundInvokeMethod != null) {
            try {
                aroundInvokeMethod.invoke(businessObject, ctx);
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
        }
        xlogger.exit();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append("method", aroundInvokeMethod);
        builder.append("device class", businessObject.getClass());
        return builder.toString();
    }

}
