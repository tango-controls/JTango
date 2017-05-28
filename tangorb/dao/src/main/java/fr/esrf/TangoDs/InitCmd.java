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

public class InitCmd extends Command implements TangoConst {

    // +-------------------------------------------------------------------------
    //
    // method : InitCmd
    // 
    // description : constructor for Command class Kill
    //
    // --------------------------------------------------------------------------

    public InitCmd(final String name, final int in, final int out) {
	super(name, in, out);
    }

    // +-------------------------------------------------------------------------
    //
    // method : execute
    // 
    // description : Kill the device server
    //
    // --------------------------------------------------------------------------

    @Override
    public Any execute(final DeviceImpl device, final Any in_any) throws DevFailed {
	Util.out4.println("InitCmd::execute(): arrived");

	// Re-initialize the device
	try {
	    device.delete_device();
	    device.init_device();
	} catch (final DevFailed e) {
	    final Util tg = Util.instance();

	    Except.re_throw_exception(e, "API_InitThrowsException", "Init command failed!!\n"
		    + "HINT: RESTART device with the Restart command"
		    + " of the device server adm. device\n"
		    + "Device server adm. device name = dserver/" + tg.get_ds_name(),
		    "InitCmd.execute()");
	}

	// return to the caller
	return Util.return_empty_any("Init");
    }

}
