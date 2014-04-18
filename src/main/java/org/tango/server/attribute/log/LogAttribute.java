package org.tango.server.attribute.log;

import org.slf4j.Logger;
import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.IAttributeBehavior;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.TangoConst;

public class LogAttribute implements IAttributeBehavior {

    private final AttributeAppender attributeAppender;

    public LogAttribute(final int depth, final Logger... loggers) {
	attributeAppender = new AttributeAppender(depth);
	for (final Logger logger : loggers) {
	    if (logger instanceof ch.qos.logback.classic.Logger) {
		final ch.qos.logback.classic.Logger loggerBack = (ch.qos.logback.classic.Logger) logger;
		loggerBack.addAppender(attributeAppender);
		attributeAppender.start();
	    }
	}

    }

    @Override
    public AttributeConfiguration getConfiguration() throws DevFailed {
	final AttributeConfiguration config = new AttributeConfiguration();
	config.setName("log");
	config.setTangoType(TangoConst.Tango_DEV_STRING, AttrDataFormat.IMAGE);
	config.setWritable(AttrWriteType.READ);
	return config;
    }

    @Override
    public AttributeValue getValue() throws DevFailed {
	return new AttributeValue(attributeAppender.getLog());
    }

    @Override
    public void setValue(final AttributeValue value) throws DevFailed {

    }

    @Override
    public StateMachineBehavior getStateMachine() throws DevFailed {
	// TODO Auto-generated method stub
	return null;
    }

}
