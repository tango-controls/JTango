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
// Revision 1.5  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:57  ounsy
// updated change from api/java
//
// Revision 1.3  2005/12/02 09:54:04  pascal_verdier
// java import have been optimized.
//
// Revision 1.2  2004/03/19 10:24:35  ounsy
// Modification of the overall Java event client Api for synchronization with tango C++ Release 4
//
// Revision 1.1  2004/03/08 11:43:23  pascal_verdier
// *** empty log message ***
//
//
// Copyleftt 2003 by Synchrotron Soleil, France
//-======================================================================
/*
 * TangoChangeEvent.java
 *
 * Created on September 22, 2003, 3:43 PM
 */

package fr.esrf.TangoApi.events;


import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;

import java.util.EventObject;

/**
 *
 * @author  pascal_verdier
 */
public class TangoChangeEvent extends EventObject implements java.io.Serializable {
    
    /** Creates a new instance of AttrAbsoluteChangeEvent */
    public TangoChangeEvent(TangoChange source, EventData event_data)
	{
		super(source);
		this.attr_value = event_data.attr_value;
		this.errors = event_data.errors;
	}
    
	public DeviceAttribute getValue() throws DevFailed
	{  
		if (attr_value == null)
		   throw new DevFailed(errors);
		return attr_value;
	}
        
	private DeviceAttribute attr_value;
	private DevError[] errors;
    
}
