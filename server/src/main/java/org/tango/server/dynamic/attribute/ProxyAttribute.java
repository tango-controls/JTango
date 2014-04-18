/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tango.server.dynamic.attribute;

import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.IAttributeBehavior;
import org.tango.utils.DevFailedUtils;

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
public final class ProxyAttribute implements IAttributeBehavior {

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
            // Read value on proxy
            if (proxy.getDeviceAttribute().getType() == TangoConst.Tango_DEV_USHORT) {
                // Fix: client api returns a int while server must provide a short
                readValue.setValue(proxy.read(short.class));
            } else {
                readValue.setValue(proxy.read());
            }
            readValue.setQuality(proxy.getDeviceAttribute().getQuality());
        }
        return readValue;
    }

    @Override
    public void setValue(final AttributeValue value) throws DevFailed {
        if (isReadyOnly) {
            DevFailedUtils.newDevFailed("SECURITY_ERROR", "it not allowed to write this attribute");
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
}
