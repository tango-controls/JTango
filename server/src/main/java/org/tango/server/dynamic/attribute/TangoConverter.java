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

import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.idl.TangoIDLAttributeUtil;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeInfo;
import fr.esrf.TangoApi.AttributeInfoEx;

/**
 * 
 * @author hardion
 */
public final class TangoConverter {

    private TangoConverter() {
    }

    public static AttributeConfiguration toAttributeConfiguration(final AttributeInfo ai) throws DevFailed {
        final AttributeConfiguration result = new AttributeConfiguration();
        result.setName(ai.name);
        result.setTangoType(ai.data_type, ai.data_format);
        result.setMemorized(false);
        result.setDispLevel(ai.level);
        result.setWritable(ai.writable);
        final AttributePropertiesImpl properties = new AttributePropertiesImpl();
        properties.setLabel(ai.label);
        properties.setFormat(ai.format);
        properties.setDescription(ai.description);
        properties.setDisplayUnit(ai.display_unit);
        properties.setStandardUnit(ai.standard_unit);
        properties.setUnit(ai.unit);
        properties.setMaxAlarm(ai.max_alarm);
        properties.setMaxValue(ai.max_value);
        properties.setMinAlarm(ai.min_alarm);
        properties.setMinValue(ai.min_value);

        result.setAttributeProperties(properties);
        return result;
    }

    public static AttributeConfiguration toAttributeConfigurationEx(final AttributeInfoEx ai) throws DevFailed {
        final AttributeConfiguration result = toAttributeConfiguration(ai);
        final AttributePropertiesImpl properties = result.getAttributeProperties();
        properties.setEnumLabels(ai.enum_label);
        properties.setRootAttribute(ai.root_attr_name);
        properties.setEventAbsChange(ai.events.ch_event.abs_change);
        properties.setEventRelChange(ai.events.ch_event.rel_change);
        properties.setEventPeriod(ai.events.per_event.period);
        properties.setArchivingEventAbsChange(ai.events.arch_event.abs_change);
        properties.setArchivingEventRelChange(ai.events.arch_event.rel_change);
        result.setAttributeProperties(properties);
        return result;
    }

    public static AttributeInfoEx toAttributeConfigurationEx(final AttributeConfiguration config) throws DevFailed {
        return new AttributeInfoEx(TangoIDLAttributeUtil.toAttributeConfig5(config));
    }
}
