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
// Revision 1.6  2007/08/07 06:54:13  pascal_verdier
// Management without database added.
//
// Revision 1.5  2006/08/31 08:45:32  pascal_verdier
// Reconnection management changed to reconnect after network blank.
//
// Revision 1.4  2006/06/08 08:04:40  pascal_verdier
// Bug on events if DS change host fixed.
//
// Revision 1.3  2005/12/02 09:54:04  pascal_verdier
// java import have been optimized.
//
// Revision 1.2  2004/03/19 10:24:34  ounsy
// Modification of the overall Java event client Api for synchronization with tango C++ Release 4
//
// Revision 1.1  2004/03/08 11:43:23  pascal_verdier
// *** empty log message ***
//
//
// Copyleftt 2003 by Synchrotron Soleil, France
//-======================================================================
/*
 * EventChannelStruct.java
 *
 * Created on May 21, 2003, 3:22 PM
 */

package fr.esrf.TangoApi.events;

import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoApi.Database;
import org.omg.CosNotifyChannelAdmin.EventChannel;
import org.omg.CosNotifyChannelAdmin.StructuredProxyPushSupplier;

/**
 *
 * @author  ounsy
 */
public class EventChannelStruct {
    
    public EventChannel		eventChannel;
    public StructuredProxyPushSupplier	structuredProxyPushSupplier;
    public DeviceProxy		adm_device_proxy;
    public long		last_subscribed;
    public long		last_heartbeat;
    public boolean	heartbeat_skipped;
    public int		heartbeat_filter_id;
	public String	host;
	public boolean	notifd_failed = false;
	public boolean	use_db = true;
	public Database	dbase = null;
    
    /** Creates a new instance of EventChannelStruct */
    public EventChannelStruct() 
    {
    }
    
}
