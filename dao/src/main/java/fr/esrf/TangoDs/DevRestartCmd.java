//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: pascal_verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,
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
// $Revision: 25297 $
//
//-======================================================================


package fr.esrf.TangoDs;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevStringHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
 
public class DevRestartCmd extends Command
{

//+-------------------------------------------------------------------------
//
// method : 		DevRestartCmd 
// 
// description : 	constructor for Command class Status
//
//--------------------------------------------------------------------------
 
	public DevRestartCmd(String name,int in,int out,String desc)
	{
		super(name,in,out);
		set_in_type_desc(desc);
	}

//+-------------------------------------------------------------------------
//
// method : 		execute 
// 
// description : 	Restart the device. This means :
//			- destroy it from teh device list within its class
//			  object
//			- re-create the device by calling the device_factory
//			  method
//
//--------------------------------------------------------------------------

	public Any execute(DeviceImpl device,Any in_any) throws DevFailed
	{

		Util.out4.println("DevRestart.execute(): arrived ");

//
// Extract the input device name.
//

		String in_dev = null;
		try
		{
			//in_dev = in_any.extract_string();
			in_dev = DevStringHelper.extract(in_any);
		}
		catch(BAD_OPERATION ex)
		{
			Util.out3.println("DevRestartCmd.execute() --> Wrong argument type");
			Except.throw_exception("API_IncompatibleCmdArgumentType",
					       "Imcompatible command argument type, expected type is : string",
					       "DevRestartCmd.execute");
		}
		Util.out4.println("Received device name = " + in_dev);

//
// Call the DServer object method which will do the work
//

		((DServer)(device)).restart(in_dev);
		
//	
// Leave command
//

		return Util.return_empty_any("DevRestart");
	}

}
