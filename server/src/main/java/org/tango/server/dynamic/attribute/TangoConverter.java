/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tango.server.dynamic.attribute;

import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributePropertiesImpl;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeInfo;

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
}
