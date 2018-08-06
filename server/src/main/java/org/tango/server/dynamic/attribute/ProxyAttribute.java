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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tango.server.dynamic.attribute;

import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.IAttributeBehavior;
import org.tango.server.attribute.ISetValueUpdater;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.tango.clientapi.TangoAttribute;

/**
 * Dynamic attribute that connects to another tango attribute. For performance issues, this attribute may be read from a
 * MultiAttributeProxy
 *
 * @see MultiAttributeProxy
 * @author hardion
 */
public final class ProxyAttribute implements IAttributeBehavior, ISetValueUpdater {

    // Configuration of attribute from the targeted attribute proxy
    private final StateMachineBehavior smb = new StateMachineBehavior();
    private final AttributeConfiguration ac;
    private AttributeValue readValue = new AttributeValue();
    // Proxy Part
    private final TangoAttribute proxy;
    private final boolean isReadyOnly;
    private boolean autoUpdate = true;

    /**
     *
     * @param attributeName
     *            The name of this attribute
     * @param attributeProxyName
     *            The full name of the proxy attribute
     * @param isReadyOnly
     *            Force this attribute to be read only even if proxy is read & write
     * @throws DevFailed
     */
    public ProxyAttribute(final String attributeName, final String attributeProxyName, final boolean isReadyOnly)
            throws DevFailed {
        this.isReadyOnly = isReadyOnly;
        proxy = new TangoAttribute(attributeProxyName);
        ac = TangoConverter.toAttributeConfiguration(proxy.getAttributeProxy().get_info());
        final int type = ac.getTangoType();
        final AttrDataFormat format = ac.getFormat();
        // Fix: client api returns a different type that the server must provide
        if (type == TangoConst.Tango_DEV_UCHAR) {
            ac.setTangoType(TangoConst.Tango_DEV_SHORT, format);
        } else if (type == TangoConst.Tango_DEV_USHORT) {
            ac.setTangoType(TangoConst.Tango_DEV_LONG, format);
        } else if (type == TangoConst.Tango_DEV_ULONG) {
            ac.setTangoType(TangoConst.Tango_DEV_LONG64, format);
        }
        ac.getAttributeProperties().setDescription("Proxied from " + attributeProxyName);
        ac.setName(attributeName);
        if (isReadyOnly) {
            ac.setWritable(AttrWriteType.READ);
        }
    }

    /**
     * see {@link #ProxyAttribute(String, String, boolean)}
     *
     * @param autoUpdate
     *            getValue will not read value from proxy. The value will be feeded with
     *            {@link #setReadValue(AttributeValue)}
     * @throws DevFailed
     */
    public ProxyAttribute(final String attributeName, final String attributeProxyName, final boolean isReadyOnly,
            final boolean autoUpdate) throws DevFailed {
        this(attributeName, attributeProxyName, isReadyOnly);
        this.autoUpdate = autoUpdate;
    }

    @Override
    public AttributeValue getValue() throws DevFailed {
        if (autoUpdate) {
            readValue.setValue(proxy.read());
            readValue.setQuality(proxy.getDeviceAttribute().getQuality());
            readValue.setTime(proxy.getTimestamp());
        }
        return readValue;
    }

    @Override
    public void setValue(final AttributeValue value) throws DevFailed {
        if (isReadyOnly) {
            throw DevFailedUtils.newDevFailed("SECURITY_ERROR", "it not allowed to write this attribute");
        }
        proxy.write(value.getValue());
    }

    @Override
    public AttributeConfiguration getConfiguration() {
        return ac;
    }

    @Override
    public StateMachineBehavior getStateMachine() {
        return smb;
    }

    /**
     * Update the read value
     *
     * @param readValue
     */
    public void setReadValue(final AttributeValue readValue) {
        this.readValue = readValue;
    }

    @Override
    public AttributeValue getSetValue() throws DevFailed {
        final AttributeValue value = new AttributeValue();
        proxy.update();
        value.setValue(proxy.extractWritten());
        return value;
    }
}
