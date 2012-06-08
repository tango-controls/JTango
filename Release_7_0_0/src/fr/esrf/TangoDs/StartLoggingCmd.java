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
// $Author: :          N.leclercq
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
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
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
