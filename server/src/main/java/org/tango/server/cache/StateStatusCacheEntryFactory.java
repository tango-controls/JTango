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

import net.sf.ehcache.constructs.blocking.CacheEntryFactory;

import org.tango.server.InvocationContext;
import org.tango.server.InvocationContext.CallType;
import org.tango.server.InvocationContext.ContextType;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.command.CommandImpl;
import org.tango.server.device.AroundInvokeImpl;
import org.tango.server.device.DeviceLocker;
import org.tango.server.events.EventManager;

import fr.esrf.Tango.DevFailed;

public final class StateStatusCacheEntryFactory implements CacheEntryFactory {
    private static final double NANO_TO_MILLI = 1000000.0;
    private final CommandImpl command;
    private final AttributeImpl attribute;
    private final DeviceLocker deviceLock;
    private long lastUpdateTime;
    private final String deviceName;
    private final AroundInvokeImpl aroundInvoke;

    public StateStatusCacheEntryFactory(final CommandImpl command, final AttributeImpl attribute,
            final DeviceLocker deviceLock, final String deviceName, final AroundInvokeImpl aroundInvoke) {
        this.deviceLock = deviceLock;
        this.command = command;
        this.attribute = attribute;
        this.deviceName = deviceName;
        this.aroundInvoke = aroundInvoke;
    }

    @Override
    public Object createEntry(final Object key) throws DevFailed {

        Object result = null;
        final Object lock = deviceLock.getAttributeLock();
        synchronized (lock != null ? lock : new Object()) {
            aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTE, CallType.POLLING, null,
                    attribute.getName()));
            try {
                synchronized (attribute) {
                    final long time1 = System.nanoTime();
                    attribute.updateValue();
                    final long now = System.nanoTime();
                    final long nowMilli = System.currentTimeMillis();
                    final long deltaTime = now - lastUpdateTime;
                    lastUpdateTime = now;
                    final long executionDuration = lastUpdateTime - time1;
                    attribute.setPollingStats(executionDuration / NANO_TO_MILLI, nowMilli, deltaTime / NANO_TO_MILLI);
                    command.setPollingStats(executionDuration / NANO_TO_MILLI, nowMilli, deltaTime / NANO_TO_MILLI);
                    attribute.addToHistory();
                    result = attribute.getReadValue();
                }
                command.addToHistory(((AttributeValue) result).getValue());
                EventManager.getInstance().pushAttributeValueEvent(deviceName, attribute.getName());
            } catch (final DevFailed e) {
                command.addErrorToHistory(e);
                attribute.addErrorToHistory(e);

                EventManager.getInstance().pushAttributeErrorEvent(deviceName, attribute.getName(), e);
                throw e;
            } finally {
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTE, CallType.POLLING,
                        null, attribute.getName()));
            }
        }// synchronized
        return result;
    }

}
