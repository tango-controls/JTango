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
package org.tango.server.servant;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttributeValue_3;
import fr.esrf.Tango.AttributeValue_4;
import fr.esrf.Tango.AttributeValue_5;
import fr.esrf.Tango.ClntIdent;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevSource;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.MultiDevFailed;
import fr.esrf.Tango.NamedDevError;
import net.sf.ehcache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.DeviceState;
import org.tango.server.ExceptionMessages;
import org.tango.server.InvocationContext;
import org.tango.server.InvocationContext.CallType;
import org.tango.server.InvocationContext.ContextType;
import org.tango.server.ServerManager;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.ForwardedAttribute;
import org.tango.server.cache.PollingManager;
import org.tango.server.device.AroundInvokeImpl;
import org.tango.server.device.DeviceLocker;
import org.tango.server.device.StateImpl;
import org.tango.server.idl.CleverAnyAttribute;
import org.tango.server.idl.CleverAttrValUnion;
import org.tango.server.idl.TangoIDLAttributeUtil;
import org.tango.utils.DevFailedUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class AttributeGetterSetter {

    public static final String DOES_NOT_EXIST = " does not exist";
    private static final String ATTRIBUTE = "Attribute ";
    private static final XLogger XLOGGER = XLoggerFactory.getXLogger(AttributeGetterSetter.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(AttributeGetterSetter.class);

    private AttributeGetterSetter() {

    }

    public static AttributeImpl getAttribute(final String name, final List<AttributeImpl> attributeList)
            throws DevFailed {
        AttributeImpl result = null;
        for (final AttributeImpl attribute : attributeList) {
            if (attribute.getName().equalsIgnoreCase(name)) {
                result = attribute;
                break;
            }
        }
        if (result == null) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_FOUND, name + DOES_NOT_EXIST);
        }
        return result;
    }

    static void setAttributeValue4(final AttributeValue_4[] values, final List<AttributeImpl> attributeList,
                                   final StateImpl stateImpl, final AroundInvokeImpl aroundInvoke, final ClntIdent clientID)
            throws MultiDevFailed {
        XLOGGER.entry();
        final List<NamedDevError> errors = new ArrayList<NamedDevError>();
        int i = 0;
        for (final AttributeValue_4 value4 : values) {
            final String name = value4.name;
            try {
                final AttributeImpl att = getAttribute(name, attributeList);
                // Call the always executed method
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_WRITE_ATTRIBUTE, CallType.UNKNOWN,
                        clientID, name));

                // Check if the attribute is allowed
                final DevState s = stateImpl.updateState();
                if (!att.isAllowed(DeviceState.getDeviceState(s))) {
                    throw DevFailedUtils.newDevFailed(ExceptionMessages.COMMAND_NOT_ALLOWED, ATTRIBUTE + att.getName()
                            + " not allowed when the device is in " + DeviceState.toString(s));
                }
                final Object obj = CleverAttrValUnion.get(value4.value, att.getFormat());
                final AttributeValue attrValue = new AttributeValue(obj, value4.quality, value4.w_dim.dim_x,
                        value4.w_dim.dim_y);
                att.lock();
                try {
                    att.setValue(attrValue);
                } finally {
                    att.unlock();
                }
                // state machine
                stateImpl.stateMachine(att.getEndState());
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_WRITE_ATTRIBUTE, CallType.UNKNOWN,
                        clientID, name));
            } catch (final DevFailed e) {
                errors.add(new NamedDevError(name, i, e.errors));
            }
            i++;
        }
        if (!errors.isEmpty()) {
            throw new MultiDevFailed(errors.toArray(new NamedDevError[0]));
        }
        XLOGGER.exit();
    }

    static void setAttributeValue(final fr.esrf.Tango.AttributeValue[] values, final List<AttributeImpl> attributeList,
                                  final StateImpl stateImpl, final AroundInvokeImpl aroundInvoke, final ClntIdent clientID) throws DevFailed {
        XLOGGER.entry();

        for (final fr.esrf.Tango.AttributeValue value3 : values) {
            final String name = value3.name;
            final AttributeImpl att = getAttribute(name, attributeList);
            if (!att.getFormat().equals(AttrDataFormat.SCALAR)) {
                throw DevFailedUtils.newDevFailed("write only supported for SCALAR attributes");
            }
            // Call the always executed method
            aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_WRITE_ATTRIBUTE, CallType.UNKNOWN,
                    clientID, name));

            // Check if the attribute is allowed
            final DevState s = stateImpl.updateState();
            if (!att.isAllowed(DeviceState.getDeviceState(s))) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.COMMAND_NOT_ALLOWED, ATTRIBUTE + att.getName()
                        + " not allowed when the device is in " + DeviceState.toString(s));
            }
            final Object obj = CleverAnyAttribute.get(value3.value, att.getTangoType(), att.getFormat());
            final AttributeValue attrValue = new AttributeValue(obj, value3.quality);
            att.lock();
            try {
                att.setValue(attrValue);
            } finally {
                att.unlock();
            }
            // state machine
            stateImpl.stateMachine(att.getEndState());
            aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_WRITE_ATTRIBUTE, CallType.UNKNOWN,
                    clientID, name));
        }
        XLOGGER.exit();
    }

    static AttributeValue_5[] getAttributesValues5(final String deviceName, final String[] names,
                                                   final PollingManager cacheManager, final List<AttributeImpl> attributeList,
                                                   final AroundInvokeImpl aroundInvoke, final DevSource source, final DeviceLocker locker,
                                                   final ClntIdent clientID) throws DevFailed {
        // final Profiler profiler = new Profiler("get value time");
        final boolean fromCache = isFromCache(source);
        final CallType callType = CallType.getFromDevSource(source);
        final AttributeValue_5[] back = new AttributeValue_5[names.length];
        // profiler.start(Arrays.toString(names));
        // sort attributes with cache
        final Map<Integer, AttributeImpl> cacheAttributes = new HashMap<Integer, AttributeImpl>();
        final Map<Integer, AttributeImpl> notCacheAttributes = new HashMap<Integer, AttributeImpl>();
        for (int i = 0; i < names.length; i++) {
            AttributeImpl att = null;
            try {
                att = getAttribute(names[i], attributeList);
                if (source.equals(DevSource.DEV) && att.isPolled() && att.getPollingPeriod() == 0) {
                    // attribute is polled, so throw exception except
                    back[i] = TangoIDLAttributeUtil.toAttributeValue5Error(
                            names[i],
                            AttrDataFormat.FMT_UNKNOWN,
                            0,
                            DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_ALLOWED, ATTRIBUTE + names[i]
                                    + " value is available only by CACHE"));
                } else if (!deviceName.equalsIgnoreCase(ServerManager.getInstance().getAdminDeviceName())
                        && source.equals(DevSource.CACHE) && !att.isPolled()) {
                    // attribute is not polled, so throw exception except for admin device
                    back[i] = TangoIDLAttributeUtil.toAttributeValue5Error(
                            names[i],
                            AttrDataFormat.FMT_UNKNOWN,
                            0,
                            DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_POLLED, ATTRIBUTE + names[i]
                                    + " not polled"));
                } else if (att.isPolled() && fromCache) {
                    cacheAttributes.put(i, att);
                } else {
                    notCacheAttributes.put(i, att);
                }
            } catch (final DevFailed e) {
                back[i] = TangoIDLAttributeUtil.toAttributeValue5Error(names[i], AttrDataFormat.FMT_UNKNOWN, 0, e);
            }
        }

        // get value from cache
        for (final Entry<Integer, AttributeImpl> attribute : cacheAttributes.entrySet()) {
            final AttributeImpl att = attribute.getValue();
            final int i = attribute.getKey();
            try {
                LOGGER.debug("read from CACHE {}", att.getName());
                // aroundInvoke
                // .aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTE, callType, att.getName()));
                // profiler.start("get cache");
                final AttributeValue readValue = cacheManager.getAttributeCacheElement(att);
                // profiler.start("to idl 5");
                if (readValue == null) {
                    back[i] = TangoIDLAttributeUtil.toAttributeValue5Error(names[i], att.getFormat(),
                            att.getTangoType(),
                            DevFailedUtils.newDevFailed("CACHE_ERROR", names[i] + " not available from cache"));
                } else {
                    back[i] = TangoIDLAttributeUtil.toAttributeValue5(att, readValue, att.getWriteValue());
                }
                // profiler.stop().print();
            } catch (final CacheException e) {
                if (e.getCause() instanceof DevFailed) {
                    back[i] = TangoIDLAttributeUtil.toAttributeValue5Error(names[i], AttrDataFormat.FMT_UNKNOWN, 0,
                            (DevFailed) e.getCause());
                }
            }
            // aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTE, callType,
            // att.getName()));
        }
        // get attributes values
        if (!notCacheAttributes.isEmpty()) {
            final Object lock = locker.getAttributeLock();
            // lock if necessary
            synchronized (lock != null ? lock : new Object()) {
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTES, callType, clientID,
                        names));
                for (final Entry<Integer, AttributeImpl> attribute : notCacheAttributes.entrySet()) {
                    final AttributeImpl att = attribute.getValue();
                    final int i = attribute.getKey();
                    LOGGER.debug("read from DEVICE {} ", att.getName());
                    aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTE, callType, clientID,
                            att.getName()));
                    try {
                        if (att.getBehavior() instanceof ForwardedAttribute) {
                            // special case for fwd attribute where we retrieve directly a AttributeValue_5
                            final ForwardedAttribute fwdAttr = (ForwardedAttribute) att.getBehavior();
                            back[i] = fwdAttr.getValue5();
                        } else {
                            att.lock();
                            try {
                                att.updateValue();
                                back[i] = TangoIDLAttributeUtil.toAttributeValue5(att, att.getReadValue(),
                                        att.getWriteValue());
                            } finally {
                                att.unlock();
                            }
                        }
                    } catch (final DevFailed e) {
                        back[i] = TangoIDLAttributeUtil.toAttributeValue5Error(names[i], att.getFormat(),
                                att.getTangoType(), e);
                    }
                    aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTE, callType,
                            clientID, att.getName()));
                } // for
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTES, callType, clientID,
                        names));
            } // synchronized
        }

        return back;
    }

    private static boolean isFromCache(final DevSource source) {
        boolean fromCache = false;
        if (source.equals(DevSource.CACHE) || source.equals(DevSource.CACHE_DEV)) {
            fromCache = true;
        }
        return fromCache;
    }

    static AttributeValue_4[] getAttributesValues4(final String deviceName, final String[] names,
                                                   final PollingManager cacheManager, final List<AttributeImpl> attributeList,
                                                   final AroundInvokeImpl aroundInvoke, final DevSource source, final DeviceLocker locker,
                                                   final ClntIdent clientID) throws DevFailed {
        final boolean fromCache = isFromCache(source);
        final CallType callType = CallType.getFromDevSource(source);
        final AttributeValue_4[] back = new AttributeValue_4[names.length];
        // sort attributes with cache
        final Map<Integer, AttributeImpl> cacheAttributes = new HashMap<Integer, AttributeImpl>();
        final Map<Integer, AttributeImpl> notCacheAttributes = new HashMap<Integer, AttributeImpl>();
        for (int i = 0; i < names.length; i++) {
            AttributeImpl att = null;
            try {
                att = getAttribute(names[i], attributeList);
                if (source.equals(DevSource.DEV) && att.isPolled() && att.getPollingPeriod() == 0) {
                    // attribute is polled, so throw exception except
                    back[i] = TangoIDLAttributeUtil.toAttributeValue4Error(
                            names[i],
                            AttrDataFormat.FMT_UNKNOWN,
                            DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_ALLOWED, ATTRIBUTE + names[i]
                                    + " value is available only by CACHE"));
                } else if (!deviceName.equalsIgnoreCase(ServerManager.getInstance().getAdminDeviceName())
                        && source.equals(DevSource.CACHE) && !att.isPolled()) {
                    // attribute is not polled, so throw exception e'xcept for admin device
                    back[i] = TangoIDLAttributeUtil.toAttributeValue4Error(
                            names[i],
                            AttrDataFormat.FMT_UNKNOWN,
                            DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_POLLED, ATTRIBUTE + names[i]
                                    + " not polled"));
                } else if (att.isPolled() && fromCache) {
                    cacheAttributes.put(i, att);
                } else {
                    notCacheAttributes.put(i, att);
                }
            } catch (final DevFailed e) {
                back[i] = TangoIDLAttributeUtil.toAttributeValue4Error(names[i], AttrDataFormat.FMT_UNKNOWN, e);
            }
        }

        // get value from cache
        for (final Entry<Integer, AttributeImpl> attribute : cacheAttributes.entrySet()) {
            final AttributeImpl att = attribute.getValue();
            final int i = attribute.getKey();
            try {
                LOGGER.debug("read from CACHE {}", att.getName());
                final AttributeValue readValue = cacheManager.getAttributeCacheElement(att);
                if (readValue == null) {
                    back[i] = TangoIDLAttributeUtil.toAttributeValue4Error(names[i], att.getFormat(),
                            DevFailedUtils.newDevFailed("CACHE_ERROR", names[i] + " not available from cache"));
                } else {
                    back[i] = TangoIDLAttributeUtil.toAttributeValue4(att, readValue, att.getWriteValue());
                }
            } catch (final CacheException e) {
                if (e.getCause() instanceof DevFailed) {
                    back[i] = TangoIDLAttributeUtil.toAttributeValue4Error(names[i], AttrDataFormat.FMT_UNKNOWN,
                            (DevFailed) e.getCause());
                }
            }
        }
        // get attributes values
        if (!notCacheAttributes.isEmpty()) {
            final Object lock = locker.getAttributeLock();
            // lock if necessary
            synchronized (lock != null ? lock : new Object()) {
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTES, callType, clientID,
                        names));
                for (final Entry<Integer, AttributeImpl> attribute : notCacheAttributes.entrySet()) {
                    final AttributeImpl att = attribute.getValue();
                    final int i = attribute.getKey();
                    LOGGER.debug("read from DEVICE {} ", att.getName());
                    aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTE, callType, clientID,
                            att.getName()));
                    att.lock();
                    try {
                        att.updateValue();
                        back[i] = TangoIDLAttributeUtil.toAttributeValue4(att, att.getReadValue(),
                                att.getWriteValue());
                    } catch (final DevFailed e) {
                        back[i] = TangoIDLAttributeUtil.toAttributeValue4Error(names[i], att.getFormat(), e);
                    } finally {
                        att.unlock();
                    }
                    aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTE, callType,
                            clientID, att.getName()));
                } // for
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTES, callType, clientID,
                        names));
            } // synchronized
        }

        return back;
    }

    static AttributeValue_3[] getAttributesValues3(final String deviceName, final String[] names,
                                                   final PollingManager cacheManager, final List<AttributeImpl> attributeList,
                                                   final AroundInvokeImpl aroundInvoke, final DevSource source, final DeviceLocker locker,
                                                   final ClntIdent clientID) throws DevFailed {
        final boolean fromCache = isFromCache(source);
        final CallType callType = CallType.getFromDevSource(source);
        final AttributeValue_3[] back = new AttributeValue_3[names.length];
        // sort attributes with cache
        final Map<Integer, AttributeImpl> cacheAttributes = new HashMap<Integer, AttributeImpl>();
        final Map<Integer, AttributeImpl> notCacheAttributes = new HashMap<Integer, AttributeImpl>();
        for (int i = 0; i < names.length; i++) {
            AttributeImpl att = null;
            try {
                att = getAttribute(names[i], attributeList);
                if (source.equals(DevSource.DEV) && att.isPolled() && att.getPollingPeriod() == 0) {
                    // attribute is polled, so throw exception except
                    back[i] = TangoIDLAttributeUtil.toAttributeValue3Error(
                            names[i],
                            DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_ALLOWED, ATTRIBUTE + names[i]
                                    + " value is available only by CACHE"));
                } else if (!deviceName.equalsIgnoreCase(ServerManager.getInstance().getAdminDeviceName())
                        && source.equals(DevSource.CACHE) && !att.isPolled()) {
                    // attribute is not polled, so throw exception e'xcept for admin device
                    back[i] = TangoIDLAttributeUtil.toAttributeValue3Error(
                            names[i],
                            DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_POLLED, ATTRIBUTE + names[i]
                                    + " not polled"));
                } else if (att.isPolled() && fromCache) {
                    cacheAttributes.put(i, att);
                } else {
                    notCacheAttributes.put(i, att);
                }
            } catch (final DevFailed e) {
                back[i] = TangoIDLAttributeUtil.toAttributeValue3Error(names[i], e);
            }
        }

        // get value from cache
        for (final Entry<Integer, AttributeImpl> attribute : cacheAttributes.entrySet()) {
            final AttributeImpl att = attribute.getValue();
            final int i = attribute.getKey();
            try {
                LOGGER.debug("read from CACHE {}", att.getName());
                final AttributeValue readValue = cacheManager.getAttributeCacheElement(att);
                if (readValue == null) {
                    back[i] = TangoIDLAttributeUtil.toAttributeValue3Error(names[i],
                            DevFailedUtils.newDevFailed("CACHE_ERROR", names[i] + " not available from cache"));
                } else {
                    back[i] = TangoIDLAttributeUtil.toAttributeValue3(att, readValue, att.getWriteValue());
                }
            } catch (final CacheException e) {
                if (e.getCause() instanceof DevFailed) {
                    back[i] = TangoIDLAttributeUtil.toAttributeValue3Error(names[i], (DevFailed) e.getCause());
                }
            }
        }
        // get attributes values
        if (!notCacheAttributes.isEmpty()) {
            final Object lock = locker.getAttributeLock();
            // lock if necessary
            synchronized (lock != null ? lock : new Object()) {
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTES, callType, clientID,
                        names));
                for (final Entry<Integer, AttributeImpl> attribute : notCacheAttributes.entrySet()) {
                    final AttributeImpl att = attribute.getValue();
                    final int i = attribute.getKey();
                    LOGGER.debug("read from DEVICE {} ", att.getName());
                    aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTE, callType, clientID,
                            att.getName()));
                    att.lock();
                    try {
                        att.updateValue();
                        back[i] = TangoIDLAttributeUtil.toAttributeValue3(att, att.getReadValue(),
                                att.getWriteValue());
                    } catch (final DevFailed e) {
                        back[i] = TangoIDLAttributeUtil.toAttributeValue3Error(names[i], e);
                    } finally {
                        att.unlock();
                    }
                    aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTE, callType,
                            clientID, att.getName()));
                } // for
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTES, callType, clientID,
                        names));
            } // synchronized
        }
        return back;
    }

    static fr.esrf.Tango.AttributeValue[] getAttributesValues(final String deviceName, final String[] names,
                                                              final PollingManager cacheManager, final List<AttributeImpl> attributeList,
                                                              final AroundInvokeImpl aroundInvoke, final DevSource source, final DeviceLocker locker,
                                                              final ClntIdent clientID) throws DevFailed {
        final boolean fromCache = isFromCache(source);
        final CallType callType = CallType.getFromDevSource(source);

        final fr.esrf.Tango.AttributeValue[] back = new fr.esrf.Tango.AttributeValue[names.length];
        final Map<Integer, AttributeImpl> cacheAttributes = new HashMap<Integer, AttributeImpl>();
        final Map<Integer, AttributeImpl> notCacheAttributes = new HashMap<Integer, AttributeImpl>();
        for (int i = 0; i < names.length; i++) {
            final AttributeImpl att = getAttribute(names[i], attributeList);
            if (source.equals(DevSource.DEV) && att.isPolled() && att.getPollingPeriod() == 0) {
                // attribute is polled, so throw exception except
                throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_ALLOWED, ATTRIBUTE + names[i]
                        + " value is available only by CACHE");
            } else if (!deviceName.equalsIgnoreCase(ServerManager.getInstance().getAdminDeviceName())
                    && source.equals(DevSource.CACHE) && !att.isPolled()) {
                // attribute is not polled, so throw exception e'xcept for admin device
                throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_POLLED, ATTRIBUTE + names[i]
                        + " not polled");
            } else if (att.isPolled() && fromCache) {
                cacheAttributes.put(i, att);
            } else {
                notCacheAttributes.put(i, att);
            }

        }

        // get value from cache
        for (final Entry<Integer, AttributeImpl> attribute : cacheAttributes.entrySet()) {
            final AttributeImpl att = attribute.getValue();
            final int i = attribute.getKey();
            try {
                LOGGER.debug("read from CACHE {}", att.getName());
                final AttributeValue readValue = cacheManager.getAttributeCacheElement(att);
                if (readValue == null) {
                    throw DevFailedUtils.newDevFailed("CACHE_ERROR", names[i] + " not available from cache");
                } else {
                    back[i] = TangoIDLAttributeUtil.toAttributeValue(att, readValue);
                }
            } catch (final CacheException e) {
                if (e.getCause() instanceof DevFailed) {
                    throw (DevFailed) e.getCause();
                }
            }
        }
        // get attributes values
        if (!notCacheAttributes.isEmpty()) {
            final Object lock = locker.getAttributeLock();
            // lock if necessary
            synchronized (lock != null ? lock : new Object()) {
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTES, callType, clientID,
                        names));
                for (final Entry<Integer, AttributeImpl> attribute : notCacheAttributes.entrySet()) {
                    final AttributeImpl att = attribute.getValue();
                    final int i = attribute.getKey();
                    LOGGER.debug("read from DEVICE {} ", att.getName());
                    aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTE, callType, clientID,
                            att.getName()));
                    att.lock();
                    try {
                        att.updateValue();
                        back[i] = TangoIDLAttributeUtil.toAttributeValue(att, att.getReadValue());
                    } finally {
                        att.unlock();
                    }
                    aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTE, callType,
                            clientID, att.getName()));
                } // for
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTES, callType, clientID,
                        names));
            } // synchronized
        }
        return back;
    }
}
