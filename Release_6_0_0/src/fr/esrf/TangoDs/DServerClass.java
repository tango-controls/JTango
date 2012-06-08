//+============================================================================
//
// file :               DServerClass.java
//
// description :        java source code for the DServerClass class. 
//			This class derived from the DeviceClass and is
//			a singleton. It implements all the commands accessible
//			by the dserver obbject
//
// project :            TANGO
//
// author(s) :          E.Taurel
//
// $Revision$
//
// $Log$
// Revision 3.7  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/05/14 13:47:56  pascal_verdier
// Compatibility with Tango-2.2.0 cpp
// (polling commands and attibites).
//
// Revision 3.5  2004/03/12 14:07:56  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.2  2003/05/20 09:10:40  nleclercq
// Removed deprecated logging commands
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
// Revision 1.3  2000/04/13 08:22:59  taurel
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
import fr.esrf.Tango.DevState;

import java.util.Collections;

/**
 * Class for all data common to all devices of the DServer class.
 * This class is implemented using
 * the singleton design pattern. Therefore a device server process can have only
 * one instance of this class and its constructor is not public.
 *
 * @author	$Author$
 * @version	$Revision$
 */
 
public class DServerClass extends DeviceClass implements TangoConst
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
			_instance = new DServerClass(new String("DServer"));
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

	DServerClass(String name) throws DevFailed
	{
		super(name);
			
//
// Add class command(s) to the command_list
//

		command_factory();
		
//
// Sort commands
//

		MyComp comp = new MyComp();
		Collections.sort(command_list,comp);
		
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
		command_list.addElement(new DevRestartCmd(new String("DevRestart"),
						 	     Tango_DEV_STRING,
						 	     Tango_DEV_VOID,
							     new String("Device name")));
		command_list.addElement(new RestartServerCmd(new String("RestartServer"),
						 	     Tango_DEV_VOID,
						 	     Tango_DEV_VOID));
		command_list.addElement(new QueryClassCmd(new String("QueryClass"),
						 	     Tango_DEV_VOID,
						 	     Tango_DEVVAR_STRINGARRAY,
							     new String("Device server class(es) list")));
		command_list.addElement(new QueryDeviceCmd(new String("QueryDevice"),
						  	      Tango_DEV_VOID,
						  	      Tango_DEVVAR_STRINGARRAY,
							      new String("Device server device(s) list")));
		command_list.addElement(new KillCmd(new String("Kill"),
					   	       Tango_DEV_VOID,
					   	       Tango_DEV_VOID));
		command_list.addElement(new AddLoggingTargetCmd(new String("AddLoggingTarget"),
						     		 Tango_DEVVAR_STRINGARRAY,
                     Tango_DEV_VOID,
                     new String("Str[i]=Device-name - Str[i+1]=target_type::target_name")));
		command_list.addElement(new RemoveLoggingTargetCmd(new String("RemoveLoggingTarget"),
						     		 Tango_DEVVAR_STRINGARRAY,
                     Tango_DEV_VOID,
                     new String("Str[i]=Device-name - Str[i+1]=target_type::target_name"))); 
		command_list.addElement(new GetLoggingTargetCmd(new String("GetLoggingTarget"),
						     		 Tango_DEV_STRING,
                     Tango_DEVVAR_STRINGARRAY,
                     new String("Device name"),
                     new String("Logging target list"))); 
		command_list.addElement(new SetLoggingLevelCmd(new String("SetLoggingLevel"),
						     		 Tango_DEVVAR_LONGSTRINGARRAY,
                     Tango_DEV_VOID,
                     new String("Lg[i]=Logging level. Str[i]=Device name."))); 
		command_list.addElement(new GetLoggingLevelCmd(new String("GetLoggingLevel"),
						     		 Tango_DEVVAR_STRINGARRAY,
                     Tango_DEVVAR_LONGSTRINGARRAY,
                     new String("Device list"),
                     new String("Lg[i]=Logging level. Str[i]=Device name."))); 
		command_list.addElement(new StopLoggingCmd(new String("StopLogging"),
						Tango_DEV_VOID,
						Tango_DEV_VOID)); 
		command_list.addElement(new StartLoggingCmd(new String("StartLogging"),
						Tango_DEV_VOID,
						Tango_DEV_VOID));

		// Now, commands related to polling
		command_list.addElement(new PolledDeviceCmd("PolledDevice",
						Tango_DEV_VOID,
						Tango_DEVVAR_STRINGARRAY,
						"Polled device name list"));
		command_list.addElement(new DevPollStatusCmd("DevPollStatus",
						Tango_DEV_STRING,
						Tango_DEVVAR_STRINGARRAY,
						"Device name",
						"Device polling status"));

		String	msg = "Lg[0]=Upd period. Str[0]=Device name. Str[1]=Object type. Str[2]=Object name";
		command_list.addElement(new AddObjPollingCmd("AddObjPolling",
						Tango_DEVVAR_LONGSTRINGARRAY,
						Tango_DEV_VOID,
						msg));
						    
		command_list.addElement(new UpdObjPollingPeriodCmd("UpdObjPollingPeriod",
						Tango_DEVVAR_LONGSTRINGARRAY,
						Tango_DEV_VOID,
						msg));


		msg = "Lg[0]=Upd period. Str[0]=Device name. Str[1]=Object type. Str[2]=Object name";
		command_list.addElement(new RemObjPollingCmd("RemObjPolling",
						Tango_DEVVAR_STRINGARRAY,
						Tango_DEV_VOID,
						msg));
						    
		command_list.addElement(new StopPollingCmd("StopPolling",
						Tango_DEV_VOID,
						Tango_DEV_VOID));
						  
		command_list.addElement(new StartPollingCmd("StartPolling",
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
						  	   new String("A device server device !!"),
						  	   DevState.ON,
						  	   new String("The device is ON")));							 

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
