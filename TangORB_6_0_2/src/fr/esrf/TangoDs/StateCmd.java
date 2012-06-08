//+============================================================================
//
// file :               State.java
//
// description :        Java source code for the command State.
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
// Revision 1.1.1.1  2003/01/09 15:54:40  taurel
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
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevStateHelper;
import org.omg.CORBA.Any;

 
public class StateCmd extends Command
{

//+-------------------------------------------------------------------------
//
// method : 		State 
// 
// description : 	constructor for Command class State
//
//--------------------------------------------------------------------------
 
	public StateCmd(String name,int in,int out,String desc)
	{
		super(name,in,out);
		set_out_type_desc(desc);
	}

//+-------------------------------------------------------------------------
//
// method : 		execute 
// 
// description : 	return device state
//
//--------------------------------------------------------------------------

 
	public Any execute(DeviceImpl device,Any in_any) throws DevFailed
	{

		Util.out4.println("State::execute(): arrived");
	
//
// return state in a CORBA_Any
//

		Any out_any = Util.instance().get_orb().create_any();
	
		DevState sta = device.dev_state();
		DevStateHelper.insert(out_any,sta);
	
		Util.out4.println("Leaving State::execute()");
		return out_any;

	}	

}
