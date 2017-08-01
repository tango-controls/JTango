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
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevStateHelper;
import org.omg.CORBA.Any;

 
public class StateCmd extends Command
{

//+-------------------------------------------------------------------------
//
// method : 		State 
// 
// description : 	constructor for Command class State
//
//--------------------------------------------------------------------------
 
	public StateCmd(String name,int in,int out,String desc)
	{
		super(name,in,out);
		set_out_type_desc(desc);
	}

//+-------------------------------------------------------------------------
//
// method : 		execute 
// 
// description : 	return device state
//
//--------------------------------------------------------------------------

 
	public Any execute(DeviceImpl device,Any in_any) throws DevFailed
	{

		Util.out4.println("State::execute(): arrived");
	
//
// return state in a CORBA_Any
//

		Any out_any = Util.instance().get_orb().create_any();
	
		DevState sta = device.dev_state();
		DevStateHelper.insert(out_any,sta);
	
		Util.out4.println("Leaving State::execute()");
		return out_any;

	}	

}
