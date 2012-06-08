//+============================================================================
//
// file :               RestartServerCmd.java
//
// description :        Java source code for the command RestartServer.
//
// project :            TANGO
//
// author(s) :          E.Taurel
//
// $Revision$
//
// $Log$
// Revision 3.6  2005/12/02 09:55:46  pascal_verdier
// java import have been optimized.
//
// Revision 3.5  2004/03/12 14:07:57  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.0  2003/01/09 16:02:58  taurel
// - Update release number before using SourceForge
//
// Revision 1.1.1.1  2003/01/09 15:54:39  taurel
// Imported sources into CVS before using SourceForge
//
// Revision 1.6  2001/10/10 08:11:24  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:37  taurel
// Many changes due to new release
//
// Revision 1.2  2001/05/04 12:03:21  taurel
// Fix bug in the Util.get_device_by_name() method
//
// Revision 1.1.1.1  2001/04/04 08:23:54  taurel
// Imported sources
//
// Revision 1.3  2000/04/13 08:23:00  taurel
// Added attribute support
//
// Revision 1.2  2000/02/04 09:09:59  taurel
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

import fr.esrf.Tango.DevFailed;
import org.omg.CORBA.Any;
 
public class RestartServerCmd extends Command
{

//+-------------------------------------------------------------------------
//
// method : 		RestartServerCmd 
// 
// description : 	constructor for Command class Status
//
//--------------------------------------------------------------------------
 
	public RestartServerCmd(String name,int in,int out)
	{
		super(name,in,out);
	}

//+-------------------------------------------------------------------------
//
// method : 		execute 
// 
// description : 	restart the server. This means :
//			- destroys all the registered device classes
//			This is done by re-calling the init_method of the
//			DServer object
//
//--------------------------------------------------------------------------

	public Any execute(DeviceImpl device,Any in_any) throws DevFailed
	{

		Util.out4.println("RestartServer.execute(): arrived ");

		((DServer)(device)).restart_server();

		Any ret = Util.return_empty_any(new String("RestartServer"));
		return ret;
	}

}
