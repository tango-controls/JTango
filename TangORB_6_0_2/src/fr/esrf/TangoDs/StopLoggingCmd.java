//+============================================================================
//
// file :               StopLoggingCmd.java
//
// description :        Java source code for the command GetLoggingLevel.
//			This is a DServerClass TANGO command. An instance of this class is 
//      automatically attached to the DServer instance at startup.
//
// project :            TANGO
//
// author(s) :          P.Verdier
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;

import fr.esrf.Tango.DevFailed;
import org.omg.CORBA.Any;

public class StopLoggingCmd extends Command implements TangoConst
{
 /**
  * StopLoggingCmd ctor
  */
	public StopLoggingCmd(String name, int in, int out)
	{
		super(name, in, out);
	}

 /**
  * Executes the StopLoggingCmd TANGO command
  */
	public Any execute(DeviceImpl device, Any in_any) throws DevFailed
	{
		Util.out4.println("StopLoggingCmd::execute(): arrived");
    
    Logging.instance().stop_logging();

		Util.out4.println("Leaving StopLoggingCmd.execute()");	
    
    return Util.return_empty_any("StopLogging");
	}

}
