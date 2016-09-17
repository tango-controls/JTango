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

import org.tango.script.evalution.BooleanExpressionValidator;
import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.IAttributeBehavior;

import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;

public final class BooleanEvaluatorAttribute implements IAttributeBehavior {

    private final BooleanExpressionValidator context;
    private final AttributeConfiguration config = new AttributeConfiguration();

    public BooleanEvaluatorAttribute(final String name, final BooleanExpressionValidator context) throws DevFailed {
	this.context = context;
	config.setName(name);
	config.setType(boolean.class);
	config.setWritable(AttrWriteType.READ);
	final AttributePropertiesImpl props = new AttributePropertiesImpl();
	props.setLabel(name);
	props.setDescription("context validity for " + context);
	config.setAttributeProperties(props);
    }

    @Override
    public AttributeConfiguration getConfiguration() throws DevFailed {
	return config;
    }

    @Override
    public AttributeValue getValue() throws DevFailed {
	return new AttributeValue(context.isExpressionTrue());
    }

    @Override
    public void setValue(final AttributeValue value) throws DevFailed {
	// read only attribute, nothing to do
    }

    @Override
    public StateMachineBehavior getStateMachine() throws DevFailed {
	return null;
    }

}
