//+===========================================================================
// $Source$
//
// Project:   Tango API
//
// Description:  Java source for conversion between Tango/TACO library
//
// $Author$
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
// Revision 1.6  2009/03/25 13:32:08  pascal_verdier
//
//============================================================================


package fr.esrf.TangoApi;


import fr.esrf.Tango.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

/**
 *	<b>Class Description:</b><Br>
 *	This class is a wrapper for TACO device.
 *	It is an interface between TANGO DeviceProxy and TACO device.
 *	It replace the fr.esrf.TacoApi.TacoDevice class using JNI library abd use true
 *	java classes found in Taco.jar (ESRF specific).
 *	
 * @author  verdier
 * @version	$Revision$
 */


public class TacoTangoDeviceDAODefaultImpl implements ITacoTangoDeviceDAO
{

	//======================================================
	/* (non-Javadoc) */
	//======================================================
	public TacoTangoDeviceDAODefaultImpl()
	{
	}
	//======================================================
	/* (non-Javadoc) */
	//======================================================
	public void init(TacoTangoDevice tacoDevice, String devname, String nethost) throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported.\n" +
				"Patch TangORB.jar if Taco is needed.",
				"TacoTangoDeviceDAODefaultImpl.TacoTangoDeviceDAODefaultImpl("+devname+")");
	}
	//======================================================
	/* (non-Javadoc) */
	//======================================================
	public DeviceData command_inout(TacoTangoDevice tacoDevice, String command, DeviceData argin) throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported",
				"TacoTangoDeviceDAODefaultImpl.command_inout()");
		return null;
	}
	//======================================================
	//======================================================
	public CommandInfo[] commandListQuery(TacoTangoDevice tacoDevice) throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported",
				"TacoTangoDeviceDAODefaultImpl.command_inout()");
		return null;
	}
	//======================================================
	//======================================================
	public CommandInfo commandQuery(TacoTangoDevice tacoDevice, String cmdname) throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported",
				"TacoTangoDeviceDAODefaultImpl.command_inout()");
		return null;
	}
	//======================================================
	/* (non-Javadoc) */
	//======================================================
	public void set_rpc_protocol(TacoTangoDevice tacoDevice, int mode)	throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported",
				"TacoTangoDeviceDAODefaultImpl.command_inout()");
	}

	//======================================================
	/* (non-Javadoc) */
	//======================================================
	public int get_rpc_protocol(TacoTangoDevice tacoDevice)	throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported",
				"TacoTangoDeviceDAODefaultImpl.command_inout()");
		return -1;
	}

	//======================================================
	/* (non-Javadoc) */
	//======================================================
	public int get_rpc_timeout(TacoTangoDevice tacoDevice)	throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported",
				"TacoTangoDeviceDAODefaultImpl.command_inout()");
		return -1;
	}
	//======================================================
	/* (non-Javadoc) */
	//======================================================
	public void set_rpc_timeout(TacoTangoDevice tacoDevice, int millis)	throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported",
				"TacoTangoDeviceDAODefaultImpl.command_inout()");
	}
	//==========================================================================
	/* (non-Javadoc) */
	//==========================================================================
	public String[] dev_inform(TacoTangoDevice tacoDevice) throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported",
				"TacoTangoDeviceDAODefaultImpl.command_inout()");
		return null;
	}
	//==========================================================================
	//==========================================================================
	public DbDatum[] get_property(TacoTangoDevice tacoDevice, String[] propnames) throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported",
				"TacoTangoDeviceDAODefaultImpl.command_inout()");
		return null;
	}

	//==========================================================================
	/* (non-Javadoc) */
	//==========================================================================
	public void set_source(TacoTangoDevice tacoDevice, DevSource src) throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported",
				"TacoTangoDeviceDAODefaultImpl.command_inout()");
	}
	//==========================================================================
	/* (non-Javadoc) */
	//==========================================================================
	public DevSource get_source(TacoTangoDevice tacoDevice)
	{
		return null;
	}
	
	//==========================================================================
	//==========================================================================







	//==========================================================================
	/*
	 *	Signal / Attribute management
	 */
	//==========================================================================

	//==========================================================================
	/* (non-Javadoc) */
	//==========================================================================
	public String[] get_attribute_list(TacoTangoDevice tacoDevice) throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported",
				"TacoTangoDeviceDAODefaultImpl.command_inout()");
		return null;
	}
	//======================================================
	/* (non-Javadoc) */
	//======================================================
	public AttributeConfig[] get_attribute_config(TacoTangoDevice tacoDevice, String[] attrnames) throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported",
				"TacoTangoDeviceDAODefaultImpl.command_inout()");
		return null;
	}
	//======================================================
	/* (non-Javadoc) */
	//======================================================
	public DeviceAttribute[] read_attribute(TacoTangoDevice tacoDevice, String[] attrnames) throws DevFailed
	{
		Except.throw_exception("Api_TacoFailed",
				"Taco protocol not supported",
				"TacoTangoDeviceDAODefaultImpl.command_inout()");
		return null;
	}



	//======================================================
	/*
	 *	Taco  <--> Tango  data convertion methods
	 */
	//======================================================

	//======================================================
	/* (non-Javadoc) */
	//======================================================
	public int tangoType(int taco_type)
	{
		return -1;
	}
	//======================================================
	/* (non-Javadoc) */
	//======================================================
	public String[] infoToTango(TacoTangoDevice tacoDevice, String taco_info)
	{

		return null;
	}
	//======================================================
	//======================================================
}
