//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// $Revision$
//
// $Log$
//
// Copyleftt 2008 by Synchrotron Soleil, France
//-======================================================================
/*
 * TangoAttConfigEvent.java
 *
 * Created on April 10, 2008 By Pascal Verdier
 */

package fr.esrf.TangoApi.events;


import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeInfoEx;

import java.util.EventObject;

/**
 *
 * @author  ounsy
 */
public class TangoAttConfigEvent  extends EventObject implements java.io.Serializable {
    
    /** Creates a new instance of TangoOnAttConfigEvent */
    public TangoAttConfigEvent(TangoAttConfig source, EventData event_data)
	{
		super(source);
		this.attr_config = event_data.attr_config;
		this.errors = event_data.errors;
	}
    
	public AttributeInfoEx getValue() throws DevFailed
	{  
		if (attr_config == null)
		   throw new DevFailed(errors);
		return attr_config;
	}
        
	private AttributeInfoEx attr_config;
	private DevError[] errors;
    
}
