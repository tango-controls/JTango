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
 * EventData.java
 *
 * Created on May 21, 2003, 2:44 PM
 */

package fr.esrf.TangoApi.events;


import fr.esrf.Tango.DevError;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceProxy;

/**
 *
 * @author  ounsy
 */
public class EventData implements java.io.Serializable {
    
    /** Creates a new instance of EventData */
    public EventData(
                     DeviceProxy device,
                     String name,
                     String event,
                     DeviceAttribute attr_value,
                     DevError[] errors
                    ) 
    {
        this.device = device;
        this.name = name;
        this.event = event;
        this.attr_value = attr_value;
        this.errors = errors;
        err = (errors == null ) ? false : true;
    }

    public DeviceProxy device;
    public String name;
    public String event;
    public DeviceAttribute attr_value;
    public DevError[] errors;
    public boolean err;
}
