//+============================================================================
//
// file :               BlackBoxElt.java
//
// description :        java source code for the BlackBoxElt class. This class 
//			is used to implement the 
//			tango device server black box. There is one
//			black box for each Tango device. This black box
//			keeps info. on all the activities on a device.
//			A client is able to retrieve these data via a Device
//			attribute. The BlackBoxElt class encapsulates all the
//			data stored for one request to the device
//
// project :            TANGO
//
// author(s) :          E.Taurel
//
// $Revision$
//
// $Log$
// Revision 3.6  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.5  2004/03/12 14:07:56  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.0  2003/01/09 16:02:57  taurel
// - Update release number before using SourceForge
//
// Revision 1.1.1.1  2003/01/09 15:54:39  taurel
// Imported sources into CVS before using SourceForge
//
// Revision 1.6  2001/10/10 08:11:23  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:36  taurel
// Many changes due to new release
//
// Revision 1.2  2001/05/04 12:03:20  taurel
// Fix bug in the Util.get_device_by_name() method
//
// Revision 1.1.1.1  2001/04/04 08:23:52  taurel
// Imported sources
//
// Revision 1.3  2000/04/13 08:22:59  taurel
// Added attribute support
//
// Revision 1.2  2000/02/04 09:09:57  taurel
// Just update revision number
//
// Revision 1.1.1.1  2000/02/04 09:08:23  taurel
// Imported sources
//
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;

import java.util.Date;

class BlackBoxElt implements TangoConst
{
	public int			req_type;
	public int			attr_type;
	public int			op_type;
	public String			host;
	public String			cmd_name;
	public Date			when;
	public byte[]			host_ip;
	
	public BlackBoxElt()
	{
		req_type = Req_Unknown;
		attr_type = Attr_Unknown;
		op_type = Op_Unknown;
		
		host = new String("Unknown");
	}
}
