/**
 * Copyright (C) :     2012
 * <p>
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
 * <p>
 * This file is part of Tango.
 * <p>
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.monitoring;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.tango.server.Chronometer;

/**
 * TODO: Error stats
 *
 * @author abeille
 *
 */
public class TangoStats implements TangoMXBean /*, NotificationEmitter*/ {

    private static final int MAX_CHRONO = 1000;
    private static final int DURATION = 1000;
    private final Chronometer periodChrono = new Chronometer();
    private final Map<Long, Chronometer> chronoMap = new ConcurrentHashMap<Long, Chronometer>();
    // private final NotificationBroadcasterSupport broadcaster = new NotificationBroadcasterSupport();
    private String serverName = "";
    private volatile long seqNumber = 0;
    private volatile String lastRequest = "";
    private volatile long requestsPerSecond = 0;
    private volatile long minRequestsPerSecond = 0;
    private volatile long maxRequestsPerSecond = 0;
    private volatile long averageRequestsPerSecond = 0;
    private volatile long totalRequestsPerSecond = 0;
    private volatile long requestsPerSecondTemp = 0;
    private volatile long lastRequestDuration = 0;
    private volatile long minRequestDuration = Long.MAX_VALUE;
    private volatile long maxRequestDuration = Long.MIN_VALUE;
    private volatile long averageRequestDuration = 0;
    private volatile long totalRequestDuration = 0;
    private volatile String maxRequest = "";
    private volatile long errorNr = 0;

    private static final TangoStats INSTANCE = new TangoStats();

    public static TangoStats getInstance() {
        return INSTANCE;
    }

    private TangoStats() {
    }

    @Override
    public void resetStats() {
        seqNumber = 0;
        lastRequest = "";
        requestsPerSecond = 0;
        minRequestsPerSecond = 0;
        maxRequestsPerSecond = 0;
        averageRequestsPerSecond = 0;
        totalRequestsPerSecond = 0;
        requestsPerSecondTemp = 0;
        lastRequestDuration = 0;
        minRequestDuration = Long.MAX_VALUE;
        maxRequestDuration = Long.MIN_VALUE;
        maxRequest = "";
        averageRequestDuration = 0;
        totalRequestDuration = 0;
        errorNr = 0;
        chronoMap.clear();
    }

    public void setServerName(final String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String getServerName() {
        return serverName;
    }

    @Override
    public String getLastRequest() {
        return lastRequest;
    }

    @Override
    public long getRequestsPerSecond() {
        return requestsPerSecond;
    }

    private synchronized long getNextSeqNumber() {
        return seqNumber++;
    }

    public long addRequest(final String lastRequest) {
        this.lastRequest = lastRequest;
        final Chronometer chrono = new Chronometer();
        chrono.start();
        final long id = getNextSeqNumber();
        if (chronoMap.size() > MAX_CHRONO) {
            // manage memory
            resetStats();
        }
        chronoMap.put(id, chrono);
        if (periodChrono.isOver()) {
            // Create a JMX Notification
            // final Notification notification = new Notification(AttributeChangeNotification.ATTRIBUTE_CHANGE, this,
            // id,
            // Double.toString(requestsPerSecond));
            // // Send a JMX notification.
            // broadcaster.sendNotification(notification);
            requestsPerSecond = requestsPerSecondTemp;
            requestsPerSecondTemp = 0;
            periodChrono.start(DURATION);
        } else {
            this.requestsPerSecondTemp++;
        }
        return id;
    }

    // @Override
    // public void addNotificationListener(final NotificationListener listener, final NotificationFilter filter,
    // final Object handback) {
    // broadcaster.addNotificationListener(listener, filter, handback);
    // }
    //
    // @Override
    // public MBeanNotificationInfo[] getNotificationInfo() {
    // return new MBeanNotificationInfo[] { new MBeanNotificationInfo(
    // new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE },
    // javax.management.AttributeChangeNotification.class.getName(), "Attributes has been reading") };
    // }
    //
    // @Override
    // public void removeNotificationListener(final NotificationListener listener) throws ListenerNotFoundException {
    // broadcaster.removeNotificationListener(listener);
    // }
    //
    // @Override
    // public void removeNotificationListener(final NotificationListener listener, final NotificationFilter filter,
    // final Object handback) throws ListenerNotFoundException {
    // broadcaster.removeNotificationListener(listener, filter, handback);
    // }

    public long getSeqNumber() {
        return seqNumber;
    }

    public void endRequest(final long id) {
        final Chronometer chrono = chronoMap.get(id);
        if (chrono != null) {
            lastRequestDuration = chrono.getElapsedTime();
            chronoMap.remove(id);
            if (lastRequestDuration < minRequestDuration) {
                minRequestDuration = lastRequestDuration;
            } else if (lastRequestDuration > maxRequestDuration) {
                maxRequestDuration = lastRequestDuration;
                maxRequest = lastRequest;
            }
            totalRequestDuration = totalRequestDuration + lastRequestDuration;
            averageRequestDuration = totalRequestDuration / getSeqNumber();

            if (requestsPerSecond < minRequestsPerSecond) {
                minRequestsPerSecond = requestsPerSecond;
            } else if (requestsPerSecond > maxRequestsPerSecond) {
                maxRequestsPerSecond = requestsPerSecond;
            }
            totalRequestsPerSecond = totalRequestsPerSecond + requestsPerSecond;
            averageRequestsPerSecond = totalRequestsPerSecond / getSeqNumber();
        }
    }

    @Override
    public long getLastRequestDuration() {
        return lastRequestDuration;
    }

    @Override
    public String getMaxRequest() {
        return maxRequest;
    }

    @Override
    public long getMaxRequestDuration() {
        return maxRequestDuration;
    }

    @Override
    public long getMinRequestDuration() {
        return minRequestDuration;
    }

    @Override
    public long getAverageRequestDuration() {
        return averageRequestDuration;
    }

    public void addError() {
        errorNr++;
    }

    @Override
    public long getErrorNr() {
        return errorNr;
    }

    @Override
    public long getMaxRequestsPerSecond() {
        return maxRequestsPerSecond;
    }

    @Override
    public long getAverageRequestsPerSecond() {
        return averageRequestsPerSecond;
    }

    @Override
    public long getMinRequestsPerSecond() {
        return minRequestsPerSecond;
    }

}
