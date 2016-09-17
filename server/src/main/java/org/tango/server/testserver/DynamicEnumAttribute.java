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
package org.tango.server.testserver;

import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.IAttributeBehavior;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.TangoConst;

public class DynamicEnumAttribute implements IAttributeBehavior {

    private final AttributeConfiguration configAttr = new AttributeConfiguration();
    private short value = 0;

    public DynamicEnumAttribute() throws DevFailed {
        final AttributePropertiesImpl props = new AttributePropertiesImpl();
        props.setLabel("DevEnumDynamic");
        props.setEnumLabels(new String[] { "label1", "label2" });
        configAttr.setTangoType(TangoConst.Tango_DEV_ENUM, AttrDataFormat.SCALAR);
        configAttr.setName("DevEnumDynamic");
        configAttr.setWritable(AttrWriteType.READ_WRITE);
        configAttr.setAttributeProperties(props);
    }

    @Override
    public AttributeConfiguration getConfiguration() throws DevFailed {
        return configAttr;
    }

    @Override
    public AttributeValue getValue() throws DevFailed {
        return new AttributeValue(value);
    }

    @Override
    public void setValue(final AttributeValue value) throws DevFailed {
        this.value = (Short) value.getValue();
    }

    @Override
    public StateMachineBehavior getStateMachine() throws DevFailed {
        return null;
    }

}
