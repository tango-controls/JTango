// +======================================================================
//   $Source$
//
//   Project:   ezTangORB
//
//   Description:  java source code for the simplified TangORB API.
//
//   $Author: Igor Khokhriakov <igor.khokhriakov@hzg.de> $
//
//   Copyright (C) :      2014
//                        Helmholtz-Zentrum Geesthacht
//                        Max-Planck-Strasse, 1, Geesthacht 21502
//                        GERMANY
//                        http://hzg.de
//
//   This file is part of Tango.
//
//   Tango is free software: you can redistribute it and/or modify
//   it under the terms of the GNU Lesser General Public License as published by
//   the Free Software Foundation, either version 3 of the License, or
//   (at your option) any later version.
//
//   Tango is distributed in the hope that it will be useful,
//   but WITHOUT ANY WARRANTY; without even the implied warranty of
//   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//   GNU Lesser General Public License for more details.
//
//   You should have received a copy of the GNU Lesser General Public License
//   along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
//  $Revision: 25721 $
//
// -======================================================================

package org.tango.client.ez.proxy;

import com.google.common.base.Objects;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.*;
import fr.esrf.TangoApi.events.TangoEventsAdapter;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.client.ez.attribute.Quality;
import org.tango.client.ez.data.TangoDataWrapper;
import org.tango.client.ez.data.TangoDeviceAttributeWrapper;
import org.tango.client.ez.data.format.TangoDataFormat;
import org.tango.client.ez.data.type.*;

import javax.annotation.concurrent.ThreadSafe;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This class is a main entry point of the proxy framework.
 * <p/>
 * This class encapsulates {@link DeviceProxy} and a number of routines which should be performed by every client
 * of the Tango Java API. These routines are: type conversion, data extraction, exception handling etc.
 * <p/>
 * Thread-safety is guaranteed only for methods from TangoProxy interface
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 07.06.12
 */
@ThreadSafe
public final class DeviceProxyWrapper implements TangoProxy {
    public static final String API_ATTR_NOT_FOUND = "API_AttrNotFound";
    public static final String API_COMMAND_NOT_FOUND = "API_CommandNotFound";
    private static final Logger logger = LoggerFactory.getLogger(DeviceProxyWrapper.class);
    private final DeviceProxy proxy;
    private final TangoEventsAdapter eventsAdapter;
    private final ConcurrentMap<String, TangoEventDispatcher<?>> dispatchers = new ConcurrentHashMap<String, TangoEventDispatcher<?>>();
    private final Object subscriptionGuard = new Object();
    private final Set<String> subscriptionSet = new HashSet<String>();
    private final ConcurrentMap<String, TangoAttributeInfoWrapper> attributeInfo = new ConcurrentHashMap<String, TangoAttributeInfoWrapper>();
    private final ConcurrentMap<String, TangoCommandInfoWrapper> commandInfo = new ConcurrentHashMap<String, TangoCommandInfoWrapper>();
    private final Object commandInfoQueryGuard = new Object();
    private final Object attributeInfoQueryGuard = new Object();

    /**
     * @param name path to tango server
     * @throws TangoProxyException
     */
    protected DeviceProxyWrapper(String name) throws TangoProxyException {
        logger.trace("DeviceProxyWrapper({})", name);
        try {
            this.proxy = new DeviceProxy(name);
            this.eventsAdapter = new TangoEventsAdapter(this.proxy);
        } catch (DevFailed devFailed) {
            logger.error("Failed to construct DeviceProxyWrapper for device {}", name);
            throw new TangoProxyException(name, devFailed);
        }
    }

    @Override
    public String getName() {
        return this.proxy.name();
    }


    /**
     * Reads attribute specified by name and returns value of appropriate type (if defined in TangoDataFormat and TangoDataTypes)
     *
     * @param attrName name
     * @param <T>      type of value
     * @return value
     * @throws TangoProxyException
     */
    @Override
    public <T> T readAttribute(String attrName) throws TangoProxyException, NoSuchAttributeException {
        logger.trace("DeviceProxyWrapper#readAttribute {}/{}", getName(), attrName);
        try {
            DeviceAttribute deviceAttribute = this.proxy.read_attribute(attrName);
            return readAttributeValue(attrName, deviceAttribute);
        } catch (DevFailed e) {
            logger.error("DeviceProxyWrapper#readAttribute has failed. {}/{}", getName(), attrName);
            throw new TangoProxyException(getName(), e);
        } catch (ValueExtractionException e) {
            logger.error("DeviceProxyWrapper#readAttribute has failed. {}/{}", getName(), attrName);
            throw new TangoProxyException(getName(), e);
        }
    }

    /**
     * Same as {@link DeviceProxyWrapper#readAttribute(String)} but returns a pair of value and time in milliseconds.
     *
     * @param attrName name
     * @param <T>      type of value
     * @return pair of value and time
     * @throws TangoProxyException
     */
    @Override
    public <T> Map.Entry<T, Long> readAttributeValueAndTime(String attrName) throws TangoProxyException, NoSuchAttributeException {
        logger.trace("DeviceProxyWrapper#readAttributeValueAndTime {}/{}", getName(), attrName);
        try {
            DeviceAttribute deviceAttribute = this.proxy.read_attribute(attrName);
            T result = readAttributeValue(attrName, deviceAttribute);

            long time = deviceAttribute.getTimeValMillisSec();
            return new AbstractMap.SimpleImmutableEntry<T, Long>(result, time);
        } catch (DevFailed e) {
            logger.error("DeviceProxyWrapper#readAttributeValueAndTime has failed. {}/{}", getName(), attrName);
            throw new TangoProxyException(getName(), e);
        } catch (ValueExtractionException e) {
            logger.error("DeviceProxyWrapper#readAttributeValueAndTime has failed. {}/{}", getName(), attrName);
            throw new TangoProxyException(getName(), e);
        }
    }

    private <T> T readAttributeValue(String attrName, DeviceAttribute deviceAttribute) throws TangoProxyException, DevFailed, ValueExtractionException, NoSuchAttributeException {
        if (deviceAttribute.hasFailed()) {
            throw new DevFailed(deviceAttribute.getErrStack());
        }
        TangoDataWrapper dataWrapper = TangoDataWrapper.create(deviceAttribute);
        TangoAttributeInfoWrapper attributeInfo = getAttributeInfo(attrName);
        TangoDataFormat<T> dataFormat = TangoDataFormat.createForAttrDataFormat(attributeInfo.toAttributeInfo().data_format);
        return dataFormat.extract(dataWrapper);
    }

    /**
     * @param attrName
     * @param <T>
     * @return a triplet(val,time,quality)
     * @throws TangoProxyException
     */
    @Override
    public <T> Triplet<T, Long, Quality> readAttributeValueTimeQuality(String attrName) throws TangoProxyException, NoSuchAttributeException {
        logger.trace("DeviceProxyWrapper#readAttributeValueTimeQuality {}/{}", getName(), attrName);
        try {
            DeviceAttribute deviceAttribute = this.proxy.read_attribute(attrName);
            T result = readAttributeValue(attrName, deviceAttribute);

            long time = deviceAttribute.getTimeValMillisSec();
            Quality quality = Quality.fromAttrQuality(deviceAttribute.getQuality());

            return new Triplet<T, Long, Quality>(result, time, quality);
        } catch (DevFailed e) {
            logger.error("DeviceProxyWrapper#readAttributeValueTimeQuality has failed. {}/{}", getName(), attrName);
            throw new TangoProxyException(getName(), e);
        } catch (ValueExtractionException e) {
            logger.error("DeviceProxyWrapper#readAttributeValueTimeQuality has failed. {}/{}", getName(), attrName);
            throw new TangoProxyException(getName(), e);
        }
    }

    /**
     * Writes a new value of type T to an attribute specified by name.
     *
     * @param attrName name
     * @param value    new value
     * @param <T>      type of value
     * @throws TangoProxyException
     */
    @Override
    public <T> void writeAttribute(String attrName, T value) throws TangoProxyException, NoSuchAttributeException {
        logger.trace("DeviceProxyWrapper#writeAttribute {}/{}={}", getName(), attrName, value);
        DeviceAttribute deviceAttribute = new DeviceAttribute(attrName);
        TangoDataWrapper dataWrapper = TangoDataWrapper.create(deviceAttribute);

        try {
            TangoAttributeInfoWrapper attributeInfo = getAttributeInfo(attrName);
            int devDataType = attributeInfo.toAttributeInfo().data_type;
            TangoDataFormat<T> dataFormat = TangoDataFormat.createForAttrDataFormat(attributeInfo.toAttributeInfo().data_format);
            dataFormat.insert(dataWrapper, value, devDataType);
            this.proxy.write_attribute(deviceAttribute);
        } catch (DevFailed e) {
            logger.error("DeviceProxyWrapper#writeAttribute has failed. {}/{}={}", getName(), attrName, value);
            throw new TangoProxyException(getName(), e);
        } catch (ValueInsertionException e) {
            logger.error("DeviceProxyWrapper#writeAttribute has failed. {}/{}={}", getName(), attrName, value);
            throw new TangoProxyException(getName(), e);
        }
    }

    @Override
    public <V> V executeCommand(String cmd) throws TangoProxyException, NoSuchCommandException {
        return executeCommand(cmd, null);
    }

    /**
     * Executes command on tango server. Command is specified by name.
     * Encapsulates conversion {@link DeviceData}<->actual type (T,V).
     *
     * @param cmd   name
     * @param value input
     * @param <T>   type of input
     * @param <V>   type of output
     * @return result
     * @throws TangoProxyException
     */
    @Override
    public <T, V> V executeCommand(String cmd, T value) throws TangoProxyException, NoSuchCommandException {
        logger.trace("DeviceProxyWrapper#executeCommand {}/{}({})", getName(), cmd, value);
        try {
            DeviceData argin = new DeviceData();
            TangoDataWrapper arginWrapper = TangoDeviceAttributeWrapper.create(argin);
            TangoCommandInfoWrapper cmdInfo = getCommandInfo(cmd);
            TangoDataType<T> typeIn = TangoDataTypes.forTangoDevDataType(cmdInfo.toCommandInfo().in_type);
            typeIn.insert(arginWrapper, value);

            DeviceData argout = this.proxy.command_inout(cmd, argin);
            TangoDataWrapper argoutWrapper = TangoDataWrapper.create(argout);

            TangoDataType<V> typeOut = TangoDataTypes.forTangoDevDataType(cmdInfo.toCommandInfo().out_type);
            return typeOut.extract(argoutWrapper);
        } catch (DevFailed e) {
            logger.error("DeviceProxyWrapper#executeCommand has failed. {}/{}({})", getName(), cmd, value);
            throw new TangoProxyException(getName(), e);
        } catch (ValueExtractionException e) {
            throw new TangoProxyException(getName(), e);
        } catch (UnknownTangoDataType unknownTangoDataType) {
            throw new AssertionError(unknownTangoDataType);
        } catch (ValueInsertionException e) {
            throw new TangoProxyException(getName(), e);
        }
    }

    @Override
    public void subscribeToEvent(String attrName, TangoEvent event) throws TangoProxyException {
        logger.trace("DeviceProxyWrapper#subscribeToEvent {}/{}.{}", getName(), attrName, event);
        //TODO filters
        String[] filters = new String[0];
        String eventKey = getEventKey(attrName, event);

        TangoEventDispatcher<?> dispatcher = dispatchers.get(eventKey);

        if (dispatcher != null) return;

        dispatcher = new TangoEventDispatcher<Object>();
        TangoEventDispatcher<?> oldDispatcher = dispatchers.putIfAbsent(eventKey, dispatcher);
        if (oldDispatcher != null) dispatcher = oldDispatcher;//this may create unused dispatcher instance

        try {
            synchronized (subscriptionGuard) {
                if (subscriptionSet.contains(eventKey)) return;
                switch (event) {
                    case CHANGE:
                        eventsAdapter.addTangoChangeListener(dispatcher, attrName, filters, true);
                        break;
                    case PERIODIC:
                        eventsAdapter.addTangoPeriodicListener(dispatcher, attrName, filters, true);
                        break;
                    case ARCHIVE:
                        eventsAdapter.addTangoArchiveListener(dispatcher, attrName, filters, true);
                        break;
                    case USER:
                        eventsAdapter.addTangoArchiveListener(dispatcher, attrName, filters, true);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown TangoEvent:" + event);
                }
                subscriptionSet.add(eventKey);
            }
        } catch (DevFailed devFailed) {
            logger.error("DeviceProxyWrapper#subscribeToEvent has failed. {}/{}.{}", getName(), attrName, event);
            throw new TangoProxyException(getName(), devFailed);
        }
    }

    private String getEventKey(String attrName, TangoEvent event) {
        return this.proxy.name() + "/" + attrName + "." + event.name().toLowerCase();
    }

    @SuppressWarnings("unchecked")
    public <T> void addEventListener(String attrName, TangoEvent event, TangoEventListener<T> listener) throws TangoProxyException {
        logger.trace("DeviceProxyWrapper#addEventListener {}/{}.{}", getName(), attrName, event);
        String eventKey = getEventKey(attrName, event);
        if (!dispatchers.containsKey(eventKey))
            subscribeToEvent(attrName, event);
        TangoEventDispatcher<T> dispatcher = (TangoEventDispatcher<T>) dispatchers.get(eventKey);//T is irrelevant at runtime
        dispatcher.addListener(listener);
    }

    @Override
    public void unsubscribeFromEvent(String attrName, TangoEvent event) throws TangoProxyException {
        logger.trace("DeviceProxyWrapper#unsubscribeFromEvent {}/{}.{}", getName(), attrName, event);
        String eventKey = getEventKey(attrName, event);

        TangoEventDispatcher<?> dispatcher = dispatchers.get(eventKey);
        if (dispatcher == null) return;
        dispatchers.remove(eventKey, dispatcher);//this may accidentally remove new value if it has the same hash code

        try {
            synchronized (subscriptionGuard) {
                if (!subscriptionSet.contains(eventKey)) return;
                switch (event) {
                    case CHANGE:
                        eventsAdapter.removeTangoChangeListener(dispatcher, attrName);
                        break;
                    case PERIODIC:
                        eventsAdapter.removeTangoPeriodicListener(dispatcher, attrName);
                        break;
                    case ARCHIVE:
                        eventsAdapter.removeTangoArchiveListener(dispatcher, attrName);
                        break;
                    case USER:
                        eventsAdapter.removeTangoUserListener(dispatcher, attrName);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown TangoEvent:" + event);
                }
                subscriptionSet.remove(eventKey);
            }

        } catch (DevFailed devFailed) {
            logger.error("DeviceProxyWrapper#unsubscribeFromEvent has failed. {}/{}.{}", getName(), attrName, event);
            throw new TangoProxyException(getName(), devFailed);
        }
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("proxy", proxy.name())
                .toString();
    }

    /**
     * Returns {@link TangoAttributeInfoWrapper} for the attribute specified by name or null.
     *
     * @param attrName name
     * @return TangoAttributeInfoWrapper or null
     * @throws TangoProxyException
     */
    @Override
    public TangoAttributeInfoWrapper getAttributeInfo(String attrName) throws TangoProxyException, NoSuchAttributeException {
        logger.trace("DeviceProxyWrapper#getAttributeInfo {}/{}", getName(), attrName);
        TangoAttributeInfoWrapper attrInf = attributeInfo.get(attrName);
        if (attrInf != null) return attrInf;
        synchronized (attributeInfoQueryGuard) {
            //double check
            attrInf = attributeInfo.get(attrName);
            if (attrInf != null) return attrInf;

            try {
                AttributeInfo info = proxy.get_attribute_info(attrName);
                attrInf = new TangoAttributeInfoWrapper(info);
                attributeInfo.put(attrName, attrInf);
                return attrInf;
            } catch (DevFailed devFailed) {
                if(devFailed.errors.length > 0 && API_ATTR_NOT_FOUND.equalsIgnoreCase(devFailed.errors[0].reason))
                    throw new NoSuchAttributeException();
                else throw new TangoProxyException(getName(), devFailed);
            } catch (UnknownTangoDataType unknownTangoDataType) {
                throw new AssertionError(unknownTangoDataType);
            }
        }
    }

    /**
     * Checks if attribute specified by name is exists.
     *
     * @param attrName name
     * @return true if attribute is ok, false - otherwise
     */
    @Override
    public boolean hasAttribute(String attrName) throws TangoProxyException {
        try {
            getAttributeInfo(attrName);
            return true;
        } catch (NoSuchAttributeException e) {
            return false;
        }
    }

    /**
     * Returns {@link TangoCommandInfoWrapper} instance or null.
     *
     * @param cmdName
     * @return a TangoCommandInfoWrapper instance or null
     * @throws TangoProxyException
     */
    @Override
    public TangoCommandInfoWrapper getCommandInfo(String cmdName) throws TangoProxyException, NoSuchCommandException {
        logger.trace("DeviceProxyWrapper#getCommandInfo {}/{}", getName(), cmdName);
        TangoCommandInfoWrapper cmdInf = commandInfo.get(cmdName);
        if (cmdInf != null) return cmdInf;
        //only one thread actually queries remote tango
        synchronized (commandInfoQueryGuard) {
            //double check whether other thread might already query info
            cmdInf = commandInfo.get(cmdName);
            if (cmdInf != null) return cmdInf;

            try {
                CommandInfo info = proxy.command_query(cmdName);
                cmdInf = new TangoCommandInfoWrapper(info);
                commandInfo.put(cmdName, cmdInf);
                return cmdInf;
            } catch (DevFailed devFailed) {
                if(devFailed.errors.length > 0 && API_COMMAND_NOT_FOUND.equalsIgnoreCase(devFailed.errors[0].reason))
                    throw new NoSuchCommandException();
                else throw new TangoProxyException(getName(), devFailed);
            } catch (UnknownTangoDataType e) {
                throw new AssertionError(e);
            }
        }
    }

    @Override
    public boolean hasCommand(String name) throws TangoProxyException {
        try {
            getCommandInfo(name);
            return true;
        } catch (NoSuchCommandException e) {
            return false;
        }
    }

    @Override
    public DeviceProxy toDeviceProxy() {
        return proxy;
    }

    @Override
    public TangoEventsAdapter toTangoEventsAdapter() {
        return eventsAdapter;
    }

    @Override
    public void reset() {
        commandInfo.clear();
        attributeInfo.clear();
    }
}
