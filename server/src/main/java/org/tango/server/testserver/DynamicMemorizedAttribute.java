package org.tango.server.testserver;

import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.IAttributeBehavior;

public class DynamicMemorizedAttribute implements IAttributeBehavior {

    private final AttributeConfiguration configAttr = new AttributeConfiguration();
    private AttributeValue value = new AttributeValue();

    public DynamicMemorizedAttribute(String name) throws DevFailed {
        configAttr.setName(name);
        configAttr.setType(String.class);
        configAttr.setWritable(AttrWriteType.READ_WRITE);
        configAttr.setMemorized(true);
        value.setValue("Init");
    }

    @Override
    public AttributeConfiguration getConfiguration() throws DevFailed {
        return configAttr;
    }

    @Override
    public AttributeValue getValue() throws DevFailed {
        return value;
    }

    @Override
    public void setValue(AttributeValue value) throws DevFailed {
        this.value = value;
    }

    @Override
    public StateMachineBehavior getStateMachine() throws DevFailed {
        return null;
    }
}
