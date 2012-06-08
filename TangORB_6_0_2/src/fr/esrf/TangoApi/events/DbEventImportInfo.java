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
 * DbEventImportInfo.java
 *
 * Created on May 23, 2003, 11:52 AM
 */

package fr.esrf.TangoApi.events;
 

import fr.esrf.Tango.DevVarLongStringArray;

/**
 *
 * @author  pascal_verdier
 */
public class DbEventImportInfo implements java.io.Serializable {

	/**
	 *	ior connection as String.
	 */
	public String	channel_ior = null;    
	/**
	 *	host where notifd running
	 */
	public String	host = null;    
	/**
	 *	true if device is exported.
	 */
	public boolean	channel_exported;
        
      /** Creates a new instance of DbEventImportInfo */
    public DbEventImportInfo() 
    {
    }
	//===============================================
	/**
	 *	Complete constructor.
	 */
	//===============================================
	public DbEventImportInfo(DevVarLongStringArray info)
	{
		channel_ior      = new String(info.svalue[1]);
		channel_exported = (info.lvalue[0]==1);
		host             = info.svalue[3];
	}
    
}
