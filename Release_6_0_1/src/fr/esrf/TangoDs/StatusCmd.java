//+============================================================================
//
// file :               StatusCmd.java
//
// description :        Java source code for the command Status.
//			This command is automatically
//			installed for every devices
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
// Revision 1.1.1.1  2001/04/04 08:23:53  taurel
// Imported sources
//
// Revision 1.3  2000/04/13 08:23:00  taurel
// Added attribute support
//
// Revision 1.2  2000/02/04 09:09:59  taurel
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
//-============================================================================

package fr.esrf.TangoDs;

import fr.esrf.Tango.DevFailed;
import org.omg.CORBA.Any;

 
public class StatusCmd extends Command
{

//+-------------------------------------------------------------------------
//
// method : 		StatusCmd 
// 
// description : 	constructor for Command class Status
//
//--------------------------------------------------------------------------

	public StatusCmd(String name,int in,int out,String desc)
	{
		super(name,in,out);
		set_out_type_desc(desc);
	}

//+-------------------------------------------------------------------------
//
// method : 		execute 
// 
// description : 	return status as string
//
//--------------------------------------------------------------------------

	public Any execute(DeviceImpl device,Any in_any) throws DevFailed
	{

		Util.out4.println("Status::execute(): arrived");
	
//
// return status string as CORBA_Any
//

		Any out_any = Util.instance().get_orb().create_any();
	
		String s = device.dev_status();
		out_any.insert_string(s);
	
		Util.out4.println("Leaving Status::execute()");
		return out_any;

	}
}
