//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description: This class is a singleton class i.e only one object of
//				this class can be created.
//				It contains all properties and methods
//				which the DServer requires only once e.g. the commands.
//
// $Author$
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
// Copyleft 2000 by European Synchrotron Radiation Facility, Grenoble, France
//-======================================================================


package fr.esrf.TangoDs;

/**
 * This class is a singleton class i.e only one object of
 * this class can be created.
 * It contains all properties and methods
 * which the DServer requires only once e.g. the commands.
 *
 * @author	$Author$
 * @version	$Revision$
 */

import fr.esrf.Tango.DevFailed;
import org.omg.CORBA.Any;

public class DevPollStatusCmd extends Command
{
	//===============================================================
	/**
	 *	Constructor for Command class DevPollStatus
	 */
	//===============================================================
	DevPollStatusCmd(String name, int in, int out, String in_desc, String out_desc)
	{
		super(name,in,out);
		set_in_type_desc(in_desc);
		set_out_type_desc(out_desc);
	}
	//===============================================================
	/**
	 *	Trigger the execution of the method really implemented
	 *	the command in the DServer class
	 */
	//===============================================================
	public Any execute(DeviceImpl device, Any in_any) throws DevFailed
	{

		Util.out4.println("DevPollStatusCmd.execute(): arrived ");

		// Call the device method and return to caller
		String argin = extract_DevString(in_any);
		return insert(((DServer)(device)).dev_poll_status(argin));
	}
}
