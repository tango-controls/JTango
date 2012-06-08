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
// $Author: :          E.Taurel
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision$
//
// $Log$
// Revision 1.3  2009/01/16 12:37:13  pascal_verdier
// IntelliJIdea warnings removed.
//
// Revision 1.2  2008/10/10 11:30:46  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.1  2007/08/23 08:33:25  ounsy
// updated change from api/java
//
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
		return Util.return_empty_any("Init");
	}

}
