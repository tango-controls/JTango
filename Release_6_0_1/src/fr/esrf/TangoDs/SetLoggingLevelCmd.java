//+============================================================================
//
// file :               SetLoggingLevelCmd.java
//
// description :        Java source code for the command SetLoggingLevel.
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
import fr.esrf.Tango.DevVarLongStringArray;
import org.omg.CORBA.Any;

public class SetLoggingLevelCmd extends Command implements TangoConst
{
 /**
  * SetLoggingLevelCmd ctor
  */
	public SetLoggingLevelCmd(String name, int in, int out, String in_desc)
	{
		super(name, in, out);
    set_in_type_desc(in_desc);
	}

 /**
  * Executes the SetLoggingLevelCmd TANGO command
  */
	public Any execute(DeviceImpl device, Any in_any) throws DevFailed
	{
		Util.out4.println("SetLoggingLevelCmd::execute(): arrived");
    
    DevVarLongStringArray dvlsa = null;
		try {
      dvlsa = extract_DevVarLongStringArray(in_any);
		}
		catch (DevFailed df) {
			Util.out3.println("SetLoggingLevelCmd::execute() --> Wrong argument type");
			Except.re_throw_exception(df,
                                "API_IncompatibleCmdArgumentType",
                                "Imcompatible command argument type, expected type is : DevVarLongStringArray",
                                "SetLoggingLevelCmd.execute");
		}
    
    Logging.instance().set_logging_level(dvlsa);
    
    return Util.return_empty_any("SetLoggingLevel");
	}

}
