/**
 * Copyright (C) : 2012
 * 
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango. If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.servant;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.DeviceState;
import org.tango.server.ExceptionMessages;
import org.tango.server.InvocationContext;
import org.tango.server.InvocationContext.ContextType;
import org.tango.server.ServerManager;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.cache.TangoCacheManager;
import org.tango.server.device.AroundInvokeImpl;
import org.tango.server.device.StateImpl;
import org.tango.server.idl.CleverAnyAttribute;
import org.tango.server.idl.CleverAttrValUnion;
import org.tango.server.idl.TangoIDLAttributeUtil;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttributeValue_3;
import fr.esrf.Tango.AttributeValue_4;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevSource;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.MultiDevFailed;
import fr.esrf.Tango.NamedDevError;

public final class AttributeGetterSetter {

    private static final String ATTRIBUTE = "Attribute ";
    public static final String DOES_NOT_EXIST = " does not exist";
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
            DevFailedUtils.throwDevFailed(ExceptionMessages.ATTR_NOT_FOUND, name + DOES_NOT_EXIST);
        }
        return result;
    }

    static void setAttributeValue4(final AttributeValue_4[] values, final List<AttributeImpl> attributeList,
            final StateImpl stateImpl, final AroundInvokeImpl aroundInvoke) throws MultiDevFailed {
        XLOGGER.entry();
        final List<NamedDevError> errors = new ArrayList<NamedDevError>();
        int i = 0;
        for (final AttributeValue_4 value4 : values) {
            final String name = value4.name;
            try {
                final AttributeImpl att = getAttribute(name, attributeList);
                // Call the always executed method
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_WRITE_ATTRIBUTE, name));

                // Check if the attribute is allowed
                final DevState s = stateImpl.updateState();
                if (!att.isAllowed(DeviceState.getDeviceState(s))) {
                    DevFailedUtils.throwDevFailed(ExceptionMessages.COMMAND_NOT_ALLOWED, ATTRIBUTE + att.getName()
                            + " not allowed when the device is in " + DeviceState.toString(s));
                }
                final Object obj = CleverAttrValUnion.get(value4.value, att.getFormat());
                final AttributeValue attrValue = new AttributeValue(obj, value4.quality, value4.w_dim.dim_x,
                        value4.w_dim.dim_y);
                synchronized (att) {
                    att.setValue(attrValue);
                }
                // state machine
                stateImpl.stateMachine(att.getEndState());
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_WRITE_ATTRIBUTE, name));
            } catch (final DevFailed e) {
                errors.add(new NamedDevError(name, i, e.errors));
            }
            i++;
        }
        if (!errors.isEmpty()) {
            throw new MultiDevFailed(errors.toArray(new NamedDevError[errors.size()]));
        }
        XLOGGER.exit();
    }

    static void setAttributeValue(final fr.esrf.Tango.AttributeValue[] values, final List<AttributeImpl> attributeList,
            final StateImpl stateImpl, final AroundInvokeImpl aroundInvoke) throws DevFailed {
        XLOGGER.entry();

        for (final fr.esrf.Tango.AttributeValue value3 : values) {
            final String name = value3.name;
            final AttributeImpl att = getAttribute(name, attributeList);
            if (!att.getFormat().equals(AttrDataFormat.SCALAR)) {
                DevFailedUtils.throwDevFailed("write only supported for SCALAR attributes");
            }
            // Call the always executed method
            aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_WRITE_ATTRIBUTE, name));

            // Check if the attribute is allowed
            final DevState s = stateImpl.updateState();
            if (!att.isAllowed(DeviceState.getDeviceState(s))) {
                DevFailedUtils.throwDevFailed(ExceptionMessages.COMMAND_NOT_ALLOWED, ATTRIBUTE + att.getName()
                        + " not allowed when the device is in " + DeviceState.toString(s));
            }
            final Object obj = CleverAnyAttribute.get(value3.value, att.getTangoType(), att.getFormat());
            final AttributeValue attrValue = new AttributeValue(obj, value3.quality);
            synchronized (att) {
                att.setValue(attrValue);
            }
            // state machine
            stateImpl.stateMachine(att.getEndState());
            aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_WRITE_ATTRIBUTE, name));
        }
        XLOGGER.exit();
    }

    static AttributeValue_4[] getAttributesValues4(final String deviceName, final String[] names,
            final TangoCacheManager cacheManager, final List<AttributeImpl> attributeList,
            final AroundInvokeImpl aroundInvoke, final DevSource source) throws DevFailed {
        boolean fromCache = false;
        if (source.equals(DevSource.CACHE) || source.equals(DevSource.CACHE_DEV)) {
            fromCache = true;
        }
        aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTES, names));
        final AttributeValue_4[] back = new AttributeValue_4[names.length];
        for (int i = 0; i < names.length; i++) {
            AttributeImpl att = null;
            try {
                att = getAttribute(names[i], attributeList);
            } catch (final DevFailed e) {
                back[i] = TangoIDLAttributeUtil.toAttributeValue4Error(names[i], AttrDataFormat.FMT_UNKNOWN, e);
            }
            if (att != null) {
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTE, att.getName()));
                if (source.equals(DevSource.DEV) && att.isPolled() && att.getPollingPeriod() == 0) {
                    // command is not polled, so throw exception except for
                    // admin device
                    back[i] = TangoIDLAttributeUtil.toAttributeValue4Error(
                            names[i],
                            AttrDataFormat.FMT_UNKNOWN,
                            DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_ALLOWED, ATTRIBUTE + names[i]
                                    + " value is available only by CACHE"));
                } else if (!deviceName.equalsIgnoreCase(ServerManager.getInstance().getAdminDeviceName())
                        && source.equals(DevSource.CACHE) && !att.isPolled()) {
                    // command is not polled, so throw exception except for
                    // admin device
                    back[i] = TangoIDLAttributeUtil.toAttributeValue4Error(
                            names[i],
                            AttrDataFormat.FMT_UNKNOWN,
                            DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_POLLED, ATTRIBUTE + names[i]
                                    + " not polled"));
                } else {
                    if (att.isPolled() && fromCache) {
                        // get value from cache
                        try {
                            final Element element = cacheManager.getAttributeCache(att).get(
                                    names[i].toLowerCase(Locale.ENGLISH));
                            LOGGER.debug("read from CACHE {} - expired {}", att.getName(), element.isExpired());
                            final AttributeValue readValue = (AttributeValue) element.getValue();
                            if (readValue == null) {
                                back[i] = TangoIDLAttributeUtil.toAttributeValue4Error(
                                        names[i],
                                        att.getFormat(),
                                        DevFailedUtils.newDevFailed("CACHE_ERROR", names[i]
                                                + " not available from cache"));
                            } else {
                                back[i] = TangoIDLAttributeUtil.toAttributeValue4(att, readValue, att.getWriteValue());
                            }
                        } catch (final CacheException e) {
                            if (e.getCause() instanceof DevFailed) {
                                back[i] = TangoIDLAttributeUtil.toAttributeValue4Error(names[i],
                                        AttrDataFormat.FMT_UNKNOWN, (DevFailed) e.getCause());
                            }
                        }
                    } else {
                        LOGGER.debug("read from DEVICE {} ", att.getName());
                        try {
                            synchronized (att) {
                                att.updateValue();
                                back[i] = TangoIDLAttributeUtil.toAttributeValue4(att, att.getReadValue(),
                                        att.getWriteValue());
                            }
                        } catch (final DevFailed e) {
                            back[i] = TangoIDLAttributeUtil.toAttributeValue4Error(names[i], att.getFormat(), e);
                        }

                    }
                }
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTE, att.getName()));
            }
        }
        aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTES, names));
        return back;
    }

    static AttributeValue_3[] getAttributesValues3(final String[] names, final TangoCacheManager cacheManager,
            final List<AttributeImpl> attributeList, final AroundInvokeImpl aroundInvoke, final boolean fromCache)
            throws DevFailed {
        aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTES, names));
        final AttributeValue_3[] back = new AttributeValue_3[names.length];
        for (int i = 0; i < names.length; i++) {
            AttributeImpl att = null;
            try {
                att = getAttribute(names[i], attributeList);
            } catch (final DevFailed e) {
                back[i] = TangoIDLAttributeUtil.toAttributeValue3Error(names[i], e);
            }
            if (att != null) {
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTE, att.getName()));
                if (att.isPolled() && fromCache) {
                    // get value from cache
                    final Element element = cacheManager.getAttributeCache(att).get(
                            names[i].toLowerCase(Locale.ENGLISH));
                    final AttributeValue readValue = (AttributeValue) element.getValue();
                    back[i] = TangoIDLAttributeUtil.toAttributeValue3(att, readValue, att.getWriteValue());
                    LOGGER.debug("read from CACHE {} - expired {}", att.getName(), element.isExpired());
                } else {
                    LOGGER.debug("read from DEVICE {} ", att.getName());
                    try {
                        synchronized (att) {
                            att.updateValue();
                            back[i] = TangoIDLAttributeUtil.toAttributeValue3(att, att.getReadValue(),
                                    att.getWriteValue());
                        }
                    } catch (final DevFailed e) {
                        back[i] = TangoIDLAttributeUtil.toAttributeValue3Error(names[i], e);
                    }
                }
                aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTE, att.getName()));
            }
        }
        aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTES, names));
        return back;
    }

    static fr.esrf.Tango.AttributeValue[] getAttributesValues(final String[] names,
            final TangoCacheManager cacheManager, final List<AttributeImpl> attributeList,
            final AroundInvokeImpl aroundInvoke, final boolean fromCache) throws DevFailed {
        aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTES, names));
        final fr.esrf.Tango.AttributeValue[] back = new fr.esrf.Tango.AttributeValue[names.length];
        for (int i = 0; i < names.length; i++) {
            final AttributeImpl att = getAttribute(names[i], attributeList);
            aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTE, att.getName()));
            if (att.isPolled() && fromCache) {
                // get value from cache
                final Element element = cacheManager.getAttributeCache(att).get(names[i].toLowerCase(Locale.ENGLISH));
                final AttributeValue readValue = (AttributeValue) element.getValue();
                back[i] = TangoIDLAttributeUtil.toAttributeValue(att, readValue);
                LOGGER.debug("read from CACHE {} - expired {}", att.getName(), element.isExpired());
            } else {
                LOGGER.debug("read from DEVICE {} ", att.getName());
                synchronized (att) {
                    att.updateValue();
                    back[i] = TangoIDLAttributeUtil.toAttributeValue(att, att.getReadValue());
                }
            }
            aroundInvoke.aroundInvoke(new InvocationContext(ContextType.PRE_READ_ATTRIBUTE, att.getName()));
        }
        aroundInvoke.aroundInvoke(new InvocationContext(ContextType.POST_READ_ATTRIBUTES, names));
        return back;
    }
}
