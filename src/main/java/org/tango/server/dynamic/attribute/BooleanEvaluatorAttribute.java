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
