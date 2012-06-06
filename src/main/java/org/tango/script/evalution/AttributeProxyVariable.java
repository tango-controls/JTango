/*
 * Created on 18 Jan 2007
 * with Eclipse
 */
package org.tango.script.evalution;

import java.lang.ref.SoftReference;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.tango.clientapi.TangoAttribute;

/**
 * Implementation of IContextVariable which gets its value from AttributeProxy.
 * 
 * Use SoftReference
 * 
 * @author HARDION
 * @version 1.0
 */
public final class AttributeProxyVariable implements IContextVariable {

    private final String name;

    private final String attributeName;

    private SoftReference<TangoAttribute> softProxy = null;

    /**
     * Build a variable with an AttributeProxy
     * 
     * @param name
     *            name of the variable
     * @param attributeName
     *            name of the attribute proxy
     * @throws ContextException
     */
    public AttributeProxyVariable(final String name, final String attributeName) throws DevFailed {
	super();
	this.name = name;
	this.attributeName = attributeName;
	softProxy = new SoftReference<TangoAttribute>(new TangoAttribute(attributeName));
    }

    public String getName() {
	return name;
    }

    public Object getValue() throws DevFailed {
	TangoAttribute proxy = softProxy.get();
	// Check if Soft reference still contains proxy reference
	if (proxy == null) {
	    proxy = new TangoAttribute(attributeName);
	    softProxy = new SoftReference<TangoAttribute>(proxy);

	}
	// read SCALAR value from device
	final Object result;
	if (proxy.getDataType() == TangoConst.Tango_DEV_STATE) {
	    // convert tango state to string
	    result = proxy.read(String.class);
	} else {
	    result = proxy.read();
	}

	return result;
    }

    /**
     * @return the attributeName
     */
    public String getAttributeName() {
	return attributeName;
    }

    @Override
    public String toString() {
	final ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
	sb.append("name", name);
	sb.append("attributeName", attributeName);
	return sb.toString();
    }
}
