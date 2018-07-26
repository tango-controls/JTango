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

import java.util.Locale;

import net.sf.ehcache.constructs.blocking.CacheEntryFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.InvocationContext;
import org.tango.server.InvocationContext.CallType;
import org.tango.server.InvocationContext.ContextType;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.device.AroundInvokeImpl;
import org.tango.server.device.DeviceLocker;
import org.tango.server.events.EventManager;

import fr.esrf.Tango.DevFailed;

public final class AttributeCacheEntryFactory implements CacheEntryFactory {
    private static final double NANO_TO_MILLI = 1000000.0;

    private final Logger logger = LoggerFactory.getLogger(AttributeCacheEntryFactory.class);

    private final AttributeImpl attribute;
    // private Profiler profilerPeriod = new Profiler("period");
    private final DeviceLocker deviceLock;
    private long lastUpdateTime;

    private final String deviceName;

    private final AroundInvokeImpl aroundInvoke;

    public AttributeCacheEntryFactory(final AttributeImpl attribute, final DeviceLocker deviceLock,
            final String deviceName, final AroundInvokeImpl aroundInvoke) {
        this.deviceLock = deviceLock;
        this.attribute = attribute;
        this.deviceName = deviceName;
        this.aroundInvoke = aroundInvoke;
    }

    @Override
    public Object createEntry(final Object key) throws DevFailed {

        logger.debug("Creating entry for key = {} , attribute {}/{} ",
                new Object[] { key, deviceName, attribute.getName() });

        // profilerPeriod.stop().print();
        // profilerPeriod = new Profiler("period");
        // profilerPeriod.start(attribute.getName());
        // if (element != null) {
        // logger.debug("{} hint {}", element.getHitCount());
        // System.out.println(element.getExpirationTime());
        // }
        // final Profiler profiler = new Profiler("read time");
        // profiler.start(attribute.getName());
        Object result = null;

        if (key.equals(attribute.getName().toLowerCase(Locale.ENGLISH))) {
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
                        attribute.setPollingStats(executionDuration / NANO_TO_MILLI, nowMilli, deltaTime
                                / NANO_TO_MILLI);
                        attribute.addToHistory();
                        result = attribute.getReadValue();
                        EventManager.getInstance().pushAttributeValueEvent(deviceName, attribute.getName());
                    }
                } catch (final DevFailed e) {
                    attribute.addErrorToHistory(e);
                    EventManager.getInstance().pushAttributeErrorEvent(deviceName, attribute.getName(), e);
                    throw e;
                } finally {
                    aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTE, CallType.POLLING,
                            null, attribute.getName()));
                }
            }// synchronized
        }

        // profiler.stop().print();

        return result;
    }
    // @Override
    // public void updateEntryValue(final Object key, final Object value) throws
    // Exception {
    // System.out.println("~~~~~~UPDATING entry for key = " + key);
    // final StringBuffer stringBuffer = (StringBuffer) value;
    // stringBuffer.append(stringBuffer.length());
    //
    // }
}
