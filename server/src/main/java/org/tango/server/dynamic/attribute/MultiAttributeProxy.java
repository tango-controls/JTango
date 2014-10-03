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
package org.tango.server.dynamic.attribute;

import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.IAttributeBehavior;
import org.tango.server.dynamic.DynamicManager;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.tango.clientapi.InsertExtractUtils;

/**
 * Use read_attributes on a DeviceProxy and update associated ProxyAttribute
 * 
 * @see DeviceProxy
 * @see ProxyAttribute
 * @author ABEILLE
 * 
 */
public final class MultiAttributeProxy {

    private final DeviceProxy device;
    private final DynamicManager dynamicManager;

    public MultiAttributeProxy(final DeviceProxy device, final DynamicManager dynamicManager) {
        this.device = device;
        this.dynamicManager = dynamicManager;
    }

    public AttributeValue[] readAttributes(final String... attnames) throws DevFailed {
        final AttributeValue[] result = new AttributeValue[attnames.length];
        final DeviceAttribute[] da = device.read_attribute(attnames);

        int i = 0;
        for (final DeviceAttribute deviceAttribute : da) {
            final AttrDataFormat format = device.get_attribute_info(attnames[i]).data_format;
            final Object value = InsertExtractUtils.extractRead(deviceAttribute, format);
            final IAttributeBehavior attribute = dynamicManager.getAttribute(deviceAttribute.getName());
            final AttributeValue attrVal = new AttributeValue(value, deviceAttribute.getQuality());
            attrVal.setTime(deviceAttribute.getTime());
            if (attribute instanceof ProxyAttribute) {
                ((ProxyAttribute) attribute).setReadValue(attrVal);
            }
            result[i++] = attrVal;
        }
        return result;
    }
}
