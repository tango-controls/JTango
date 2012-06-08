//+============================================================================
//
// file :               InitCmd.java
//
// description :        Java source code for the command Kill.
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
// Revision 3.2  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.1  2004/05/14 13:47:58  pascal_verdier
// Compatibility with Tango-2.2.0 cpp
// (polling commands and attibites).
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

 
public class InitCmd extends Command implements TangoConst
{

//+-------------------------------------------------------------------------
//
// method : 		InitCmd 
// 
// description : 	constructor for Command class Kill
//
//--------------------------------------------------------------------------
 
	public InitCmd(String name,int in,int out)
	{
		super(name,in,out);
	}

//+-------------------------------------------------------------------------
//
// method : 		execute 
// 
// description : 	Kill the device server
//
//--------------------------------------------------------------------------
 
	public Any execute(DeviceImpl device,Any in_any) throws DevFailed
	{
		Util.out4.println("InitCmd::execute(): arrived");

		// Re-initialize the device
		try
		{
			device.init_device();
		}
		catch (DevFailed e)
		{
			Util	tg = Util.instance();

			Except.re_throw_exception(e,
				"API_InitThrowsException",
				"Init command failed!!\n" + 
				"HINT: RESTART device with the Restart command" +
				" of the device server adm. device\n" +
				"Device server adm. device name = dserver/" + tg.get_ds_name(),
				"InitCmd.execute()");
		}

		// return to the caller
		Any ret = Util.return_empty_any("Init");
		return ret;
	}

}
