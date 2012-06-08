//+============================================================================
//
// file :               GetTraceOutputCmd.java
//
// description :        Java source code for the command GetTraceLevel.
//			This command one command of the DServerClass class
//			One object of this class (DServerClass) is automatically
//			instanciated in each Tango device server process
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
// Revision 2.1  2003/05/19 14:54:13  nleclercq
// Added TANGO Logging support (12 new files)
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
// Revision 1.2  2001/05/04 12:03:21  taurel
// Fix bug in the Util.get_device_by_name() method
//
// Revision 1.1.1.1  2001/04/04 08:23:53  taurel
// Imported sources
//
// Revision 1.3  2000/04/13 08:22:59  taurel
// Added attribute support
//
// Revision 1.2  2000/02/04 09:09:58  taurel
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

 
public class GetTraceOutputCmd extends Command implements TangoConst
{

//+-------------------------------------------------------------------------
//
// method : 		GetTraceLevelCmd 
// 
// description : 	constructor for Command class GetTraceOutput
//
//--------------------------------------------------------------------------
 
	public GetTraceOutputCmd(String name,int in,int out,String desc)
	{
		super(name,in,out);
		set_out_type_desc(desc);
	}

//+-------------------------------------------------------------------------
//
// method : 		execute 
// 
// description : 	Retrieve the trace level variable
//
//--------------------------------------------------------------------------
 
	public Any execute(DeviceImpl device,Any in_any) throws DevFailed
	{
		Util.out4.println("GetTraceOutputCmd.execute(): arrived");

    String desc = "GetTraceOutput is no more supported, please use GetLoggingTarget";
    Except.throw_exception("API_DeprecatedCommand", desc, "GetTraceOutputCmd::execute");

    //- make compiler happy
		return Util.return_empty_any("GetTraceOutput");
  }
  
}
