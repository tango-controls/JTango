//+============================================================================
//
// file :               StartLoggingCmd.java
//
// description :        Java source code for the command GetLoggingLevel.
//			This is a DServerClass TANGO command. An instance of this class is 
//      automatically attached to the DServer instance at startup.
//
// project :            TANGO
//
// author(s) :          N.leclercq
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;

import fr.esrf.Tango.DevFailed;
import org.omg.CORBA.Any;

public class StartLoggingCmd extends Command implements TangoConst
{
 /**
  * StartLoggingCmd ctor
  */
	public StartLoggingCmd(String name, int in, int out)
	{
		super(name, in, out);
	}

 /**
  * Executes the StartLoggingCmd TANGO command
  */
	public Any execute(DeviceImpl device, Any in_any) throws DevFailed
	{
		Util.out4.println("StartLoggingCmd::execute(): arrived");
    
    Logging.instance().start_logging();

		Util.out4.println("Leaving StartLoggingCmd.execute()");	
    
    return Util.return_empty_any("StartLogging");
	}

}
