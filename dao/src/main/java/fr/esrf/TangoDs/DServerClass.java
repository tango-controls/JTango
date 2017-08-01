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

import java.util.Collections;

import static fr.esrf.TangoDs.TangoConst.*;

/**
 * Class for all data common to all devices of the DServer class.
 * This class is implemented using
 * the singleton design pattern. Therefore a device server process can have only
 * one instance of this class and its constructor is not public.
 *
 * @author $Author: pascal_verdier $
 * @version $Revision: 25297 $
 */
@Deprecated
public class DServerClass extends DeviceClass
{
	private static DServerClass 	_instance = null;
	
	
//+----------------------------------------------------------------------------
//
// method : 		instance()
// 
// description : 	static method to retrieve the DServerClass object once 
//			been initialised
//
//-----------------------------------------------------------------------------

    //TODO singleton is anti-pattern
    DServerClass(String name) throws DevFailed {
        super(name);

//
// Add class command(s) to the command_list
//

        command_factory();

//
// Sort commands
//

        MyComp comp = new MyComp();
        Collections.sort(command_list, comp);

//
// Create device name from device server name
//

        StringBuffer dev_name = new StringBuffer(Tango_DSDeviceDomain);
        dev_name.append('/');
        dev_name.append(Util.instance().get_ds_exec_name());
        dev_name.append('/');
        dev_name.append(Util.instance().get_ds_inst_name());

        String[] dev_list = new String[1];
        dev_list[0] = new String(dev_name);

//
// Create the device server device
//

        device_factory(dev_list);
    }

//+----------------------------------------------------------------------------
//
// method : 		Init()
// 
// description : 	static method to create/retrieve the Util object.
//			This method is the only one which enables a user to
//			create the object
//
// in :			- argv : The command line argument
//			- class_name : The device server class name
//
//-----------------------------------------------------------------------------

/**
 * Get the singleton object reference.
 *
 * This method returns a reference to the object of the DServerClass class.
 * If the class has not been initialised with it's init method, this method
 * print a message and abort the device server process
 *
 * @return The DServerClass object reference
 */

	public static DServerClass instance()
	{
		if (_instance == null)
		{
			System.err.println("DServerClass is not initialised !!!");
			System.err.println("Exiting");
			System.exit(-1);
		}
		return _instance;
	}

//+----------------------------------------------------------------------------
//
// method : 		DServerClass()
// 
// description : 	constructor for the DServerClass class
//			The constructor add specific class commands to the
//			command list, create a device of the DServer class
//			retrieve all classes which must be created within the
//			server and finally, creates these classes
//
// argument : in : 	- s : The class name
//
//-----------------------------------------------------------------------------

/**
 * Create and get the singleton object reference.
 *
 * This method returns a reference to the object of the DServerClass class.
 * If the class singleton object has not been created, it will be
 * instanciated
 *
 * @return The DServerClass object reference
 * @exception DevFailed If it is not possible to construct the object. It is a
 * propagation of the xecption thrown by the <i>device_factory</i> method.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
	public static DServerClass init() throws DevFailed
	{
		if (_instance == null)
		{
			_instance = new DServerClass("DServer");
		}
		return _instance;
	}
	
	
//+----------------------------------------------------------------------------
//
// method : 		command_factory
// 
// description : 	Create the command object(s) and store them in the 
//			command list
//
//-----------------------------------------------------------------------------

/**
 * Create DServerClass commands.
 *
 * Create one instance of all classes representing command available for
 * device of the DServer class.
 * These commands are QueryDevice, QueryClass, Kill, SetTraceLevel,
 * GetTraceLevel, SetTraceOutput and GetTraceOutput
 *
 */
 
	public void command_factory()
	{
		command_list.addElement(new DevRestartCmd(
					"DevRestart",
					Tango_DEV_STRING,
					Tango_DEV_VOID,
					"Device name"));
		command_list.addElement(new RestartServerCmd(
					"RestartServer",
					Tango_DEV_VOID,
					Tango_DEV_VOID));
		command_list.addElement(new QueryClassCmd(
					"QueryClass",
					Tango_DEV_VOID,
					Tango_DEVVAR_STRINGARRAY,
					"Device server class(es) list"));
		command_list.addElement(new QueryDeviceCmd(
					"QueryDevice",
					Tango_DEV_VOID,
					Tango_DEVVAR_STRINGARRAY,
					"Device server device(s) list"));
		command_list.addElement(new KillCmd(
					"Kill",
					Tango_DEV_VOID,
					Tango_DEV_VOID));
		command_list.addElement(new AddLoggingTargetCmd(
					"AddLoggingTarget",
					Tango_DEVVAR_STRINGARRAY,
					Tango_DEV_VOID,
					"Str[i]=Device-name - Str[i+1]=target_type::target_name"));
		command_list.addElement(new RemoveLoggingTargetCmd(
					"RemoveLoggingTarget",
					Tango_DEVVAR_STRINGARRAY,
					Tango_DEV_VOID,
					"Str[i]=Device-name - Str[i+1]=target_type::target_name"));
		command_list.addElement(new GetLoggingTargetCmd("GetLoggingTarget",
					Tango_DEV_STRING,
					Tango_DEVVAR_STRINGARRAY,
					"Device name",
					"Logging target list"));
		command_list.addElement(new SetLoggingLevelCmd(
					"SetLoggingLevel",
					Tango_DEVVAR_LONGSTRINGARRAY,
                    Tango_DEV_VOID,
                    "Lg[i]=Logging level. Str[i]=Device name."));
		command_list.addElement(new GetLoggingLevelCmd(
					"GetLoggingLevel",
					Tango_DEVVAR_STRINGARRAY,
                    Tango_DEVVAR_LONGSTRINGARRAY,
                    "Device list",
                    "Lg[i]=Logging level. Str[i]=Device name."));
		command_list.addElement(new StopLoggingCmd(
					"StopLogging",
					Tango_DEV_VOID,
					Tango_DEV_VOID));
		command_list.addElement(new StartLoggingCmd(
					"StartLogging",
					Tango_DEV_VOID,
					Tango_DEV_VOID));

		// Now, commands related to polling
		command_list.addElement(new PolledDeviceCmd(
					"PolledDevice",
					Tango_DEV_VOID,
					Tango_DEVVAR_STRINGARRAY,
					"Polled device name list"));
		command_list.addElement(new DevPollStatusCmd(
					"DevPollStatus",
					Tango_DEV_STRING,
					Tango_DEVVAR_STRINGARRAY,
					"Device name",
					"Device polling status"));

		String	msg = "Lg[0]=Upd period. Str[0]=Device name. Str[1]=Object type. Str[2]=Object name";
		command_list.addElement(new AddObjPollingCmd(
					"AddObjPolling",
					Tango_DEVVAR_LONGSTRINGARRAY,
					Tango_DEV_VOID,
					msg));
						    
		command_list.addElement(new UpdObjPollingPeriodCmd(
					"UpdObjPollingPeriod",
					Tango_DEVVAR_LONGSTRINGARRAY,
					Tango_DEV_VOID,
					msg));

		msg = "Lg[0]=Upd period. Str[0]=Device name. Str[1]=Object type. Str[2]=Object name";
		command_list.addElement(new RemObjPollingCmd(
					"RemObjPolling",
					Tango_DEVVAR_STRINGARRAY,
					Tango_DEV_VOID,
					msg));
						    
		command_list.addElement(new StopPollingCmd(
					"StopPolling",
					Tango_DEV_VOID,
					Tango_DEV_VOID));
						  
		command_list.addElement(new StartPollingCmd(
					"StartPolling",
					Tango_DEV_VOID,
					Tango_DEV_VOID));
}


//+----------------------------------------------------------------------------
//
// method : 		device_factory
// 
// description : 	Create the device object(s) and store them in the 
//			device list
//
// in :			String[] devlist : The device name list
//
//-----------------------------------------------------------------------------

/**
 * Create and export device of the DServer class.
 *
 * @param	devlist	The device(s) to be created name list
 * @exception DevFailed If the device creation or export failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 *
 */
 
	public void device_factory(String[] devlist) throws DevFailed
	{
	
		Util.out4.println("DServerClass::device_factory() arrived");
		for (int i = 0;i < devlist.length;i++)
		{
			Util.out4.println("Device name : " + devlist[i]);
						
//
// Create device and add it into the device list
//

			device_list.addElement(new DServer(this,
						  	   devlist[i],
						  	   "A device server device !!",
						  	   DevState.ON,
						  	   "The device is ON"));

//
// Export device to the outside world
//

			Util.out4.println("Util._UseDb = " + Util._UseDb);
			if (Util._UseDb == true)
				export_device(((DeviceImpl)(device_list.elementAt(i))));
			else
				export_device(((DeviceImpl)(device_list.elementAt(i))),devlist[i]);
		}
	}
	
}
