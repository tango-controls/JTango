//+============================================================================
//
// file :               KillCmd.java
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
// Revision 1.1  2007/08/23 08:33:24  ounsy
// updated change from api/java
//
// Revision 3.6  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.5  2004/03/12 14:07:56  pascal_verdier
// Use JacORB-2.1
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
// Revision 1.3  2000/04/13 08:23:00  taurel
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

 
public class KillCmd extends Command implements TangoConst
{

//+-------------------------------------------------------------------------
//
// method : 		KillCmd 
// 
// description : 	constructor for Command class Kill
//
//--------------------------------------------------------------------------
 
	public KillCmd(String name,int in,int out)
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
		Util.out4.println("KillCmd::execute(): arrived");
//	
// call DServer method which implements this command
//
		((DServer)(device)).kill();
//
// return to the caller
//
		return Util.return_empty_any("Kill");
	}

}
