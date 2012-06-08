//+=============================================================================
//
// file :               Util.java
//
// description :        Java source for all the utilities used by Tango device
//			server and mainly for the Util class
//
// project :            TANGO
//
// author(s) :          E.Taurel
//
// $Revision$
//
// $Log$
// Revision 3.16  2007/05/29 08:07:43  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.15  2007/04/18 05:48:49  pascal_verdier
// Catch TIMEOUT execption in server_already_running() added.
//
// Revision 3.14  2005/12/02 09:55:46  pascal_verdier
// java import have been optimized.
//
// Revision 3.13  2005/08/30 08:25:58  pascal_verdier
// Redundancy database connection between 2 TANGO_HOST added.
//
// Revision 3.12  2005/08/10 09:12:26  pascal_verdier
// Initial Revision
//
// Revision 3.11  2005/06/13 09:08:42  pascal_verdier
// Attribute historic buffer can be filled by trigger.
//
// Revision 3.10  2005/06/02 14:12:44  pascal_verdier
// Case bug fixed in get_device_by_name() method.
//
// Revision 3.9  2004/11/05 12:08:50  pascal_verdier
// Use now JacORB_2_2_1.
//
// Revision 3.8  2004/10/18 08:58:49  pascal_verdier
// Bug on case dependency without database fixed.
//
// Revision 3.7  2004/06/29 04:05:09  pascal_verdier
// Comments used by javadoc added.
//
// Revision 3.6  2004/05/14 13:47:58  pascal_verdier
// Compatibility with Tango-2.2.0 cpp
// (polling commands and attibites).
//
// Revision 3.5  2004/03/12 14:07:57  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.2  2003/05/22 08:38:02  pascal_verdier
// get_device_by_name() takes DServer device into account.
//
// Revision 2.1  2003/05/19 14:54:13  nleclercq
// Added TANGO Logging support (12 new files)
//
// Revision 2.0  2003/01/09 16:02:58  taurel
// - Update release number before using SourceForge
//
// Revision 1.1.1.1  2003/01/09 15:54:39  taurel
// Imported sources into CVS before using SourceForge
//
// Revision 1.6  2001/10/10 08:11:25  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:38  taurel
// Many changes due to new release
//
// Revision 1.2  2001/05/04 12:03:22  taurel
// Fix bug in the Util.get_device_by_name() method
//
// Revision 1.1.1.1  2001/04/04 08:23:53  taurel
// Imported sources
//
// Revision 1.3  2000/04/13 08:23:01  taurel
// Added attribute support
//
// Revision 1.2  2000/02/04 09:10:00  taurel
// Just update revision number
//
// Revision 1.1.1.1  2000/02/04 09:08:24  taurel
// Imported sources
//
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-=============================================================================

package fr.esrf.TangoDs;

/**
 * This class is a used to store TANGO device server process data and to provide
 * the user with a set of utilities method. This class is implemented using
 * the singleton design pattern. Therefore a device server process can have only
 * one instance of this class and its constructor is not public.
 *
 * @author	$Author$
 * @version	$Revision$
 */
 
public class Util implements TangoConst
{

/**
 * The UtilPrint object used for level 1 printing
 */
 
	static public UtilPrint		out1;
	
/**
 * The UtilPrint object used for level 2 printing
 */
 
	static public UtilPrint		out2;
	
/**
 * The UtilPrint object used for level 3 printing
 */
 
	static public UtilPrint		out3;
	
/**
 * The UtilPrint object used for level 4 printing
 */
 	static public UtilPrint		out4;

/**
 * The UtilPrint object used for level 5 printing
 */
 
	static public UtilPrint		out5;
	
}
