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
import org.omg.CORBA.Any;

public class AddLoggingTargetCmd extends Command implements TangoConst
{
 /**
  * AddLoggingTargetCmd ctor
  */
	public AddLoggingTargetCmd(String name, int in, int out, String in_desc)
	{
		super(name, in, out);
    set_in_type_desc(in_desc);
	}

 /**
  * Executes the AddLoggingTargetCmd TANGO command
  */
	public Any execute(DeviceImpl device, Any in_any) throws DevFailed
	{
		Util.out4.println("AddLoggingTargetCmd::execute(): arrived");
    
    String[] dvsa = null;
		try {
      dvsa = extract_DevVarStringArray(in_any);
		}
		catch (DevFailed df) {
			Util.out3.println("AddLoggingTargetCmd::execute() --> Wrong argument type");
			Except.re_throw_exception(df,
                                "API_IncompatibleCmdArgumentType",
                                "Imcompatible command argument type, expected type is : DevVarStringArray",
                                "AddLoggingTargetCmd.execute");
		}
    
    Logging.instance().add_logging_target(dvsa);
    
    return Util.return_empty_any("AddLoggingTarget");
	}

}
