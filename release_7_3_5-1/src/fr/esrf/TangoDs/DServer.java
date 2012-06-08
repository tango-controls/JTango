//+============================================================================
//
// file :               DServer.java
//
// description :        java source code for the DServer class. 
//			This class derived from the DeviceImpl class.
//			It represents the CORBA servant object which will be
//			accessed from the network. Each tango device server
//			will have one and only one object of this class.
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
// Revision 1.9  2010/08/11 12:04:24  abeilleg
// call delete device in kill command
//
// Revision 1.8  2010/08/03 13:11:56  abeilleg
// call delete_device in restart_server
//
// Revision 1.7  2009/03/25 13:33:28  pascal_verdier
// ...
//
// Revision 1.6  2009/01/16 12:37:13  pascal_verdier
// IntelliJIdea warnings removed.
//
// Revision 1.5  2008/12/03 15:43:51  pascal_verdier
// javadoc warnings removed.
//
// Revision 1.4  2008/10/10 11:30:46  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.3  2008/09/12 11:27:51  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.2  2007/09/12 11:39:18  pascal_verdier
// bug in InvocationTargetException case in class_factory() method fixed.
//
// Revision 1.1  2007/08/23 08:33:25  ounsy
// updated change from api/java
//
// Revision 3.8  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.7  2005/06/13 09:08:42  pascal_verdier
// Attribute historic buffer can be filled by trigger.
//
// Revision 3.6  2004/05/14 13:47:58  pascal_verdier
// Compatibility with Tango-2.2.0 cpp
// (polling commands and attibites).
//
// Revision 3.5  2004/03/12 14:07:57  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.1  2003/05/19 14:54:13  nleclercq
// Added TANGO Logging support (12 new files)
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
// Revision 1.2  2001/05/04 12:03:20  taurel
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.DbDatum;
import fr.esrf.TangoApi.DeviceData;

@SuppressWarnings( { "NestedTryStatement", "ErrorNotRethrown" })
public class DServer extends DeviceImpl implements TangoConst {
    protected String process_name;
    protected String instance_name;
    protected StringBuffer full_name;

    protected Vector class_list = new Vector();;

    // +----------------------------------------------------------------------------
    //
    // method : DServer()
    // 
    // description : constructor for DServer object
    //
    // in : - cp : The class object
    // - n : The device name
    // - d : The device description
    // - s : The device state
    // - st : The device status
    //
    // -----------------------------------------------------------------------------

    DServer(final DeviceClass cl, final String n, final String d, final DevState s, final String st)
	    throws DevFailed {
	super(cl, n, d, s, st);

	process_name = Util.instance().get_ds_exec_name();
	instance_name = Util.instance().get_ds_inst_name();

	full_name = new StringBuffer(process_name);
	full_name.append('/');
	full_name.append(instance_name);
	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	    public void run() {
		// call delete_device
		final int nb_class = class_list.size();
		for (int j = 0; j < nb_class; j++) {
		    final Vector<?> v = ((DeviceClass) class_list.elementAt(j)).get_device_list();
		    final int nb_dev = v.size();
		    for (int k = 0; k < nb_dev; k++) {
			final DeviceImpl dev = (DeviceImpl) v.elementAt(k);
			if (dev.get_exported_flag() == true) {
			    try {
				Util.out4.println("delete device " + dev.get_name());
				dev.delete_device();
			    } catch (final DevFailed e) {
				e.printStackTrace();
				// ignore error, kill the device anyway
			    }
			}
		    }
		}
		Util.out4.println("unregister " + Util.instance().get_ds_name());
		Util.instance().unregister_server();
	    }
	}));

	init_device();
    }

    @Override
    public void init_device() throws DevFailed {

	Util.out4.println("DServer.DSserver() create dserver " + device_name);

	//
	// Now, creates all user classes
	//

	boolean class_factory_done = false;
	int i = 0;
	try {
	    //
	    // Activate the POA manager
	    //

	    final Util tg = Util.instance();
	    final POAManager manager = tg.get_poa().the_POAManager();
	    try {
		manager.activate();
	    } catch (final org.omg.PortableServer.POAManagerPackage.AdapterInactive ex) {
		Except.throw_exception("API_CantActivatePOAManager",
			"The POA activate method throws an exception", "DServer.init_device()");
	    }

	    class_factory();
	    class_factory_done = true;

	    if (class_list.isEmpty() == false) {
		// Add the DServer object class
		// And Set the class list pointer in the Util class
		// class_list.add(this.get_device_class());
		tg.set_class_list(class_list);

		// A loop for each class
		final String ds_name = tg.get_ds_name();
		for (i = 0; i < class_list.size(); i++) {
		    // Build class commands
		    final DeviceClass cl_ref = (DeviceClass) class_list.elementAt(i);
		    cl_ref.command_factory();
		    // Sort command list
		    final MyComp comp = new MyComp();
		    Collections.sort(cl_ref.get_command_list(), comp);

		    // Build class attributes
		    cl_ref.attribute_factory(cl_ref.get_class_attr().get_attr_list());
		    cl_ref.get_class_attr().init_class_attribute(cl_ref.get_name(), 0);

		    // Set class name in command instances and analyse user
		    // methods (only for
		    // command created using the template method)
		    for (int k = 0; k < cl_ref.get_command_list().size(); k++) {
			final Command cmd = (Command) cl_ref.get_command_list().elementAt(k);
			if (cmd.is_template() == true) {
			    cmd.set_device_class_name(cl_ref.get_name());
			    cmd.analyse_methods();
			}
		    }

		    // Retrieve device(s) name list from the database. No need
		    // to implement
		    // a retry here (in case of db server restart) because the
		    // db reconnection
		    // is forced by the get_property call executed during
		    // xxxClass construction
		    // before we reach this code.

		    String[] dev_list = null;
		    if (Util._UseDb) {
			final String[] ds = new String[2];
			ds[0] = ds_name;
			ds[1] = ((DeviceClass) class_list.elementAt(i)).get_name();

			final DeviceData send = new DeviceData();
			send.insert(ds);
			final DeviceData received = tg.get_database().command_inout(
				"DbGetDeviceList", send);

			try {
			    dev_list = received.extractStringArray();

			} catch (final BAD_OPERATION ex) {
			    Util.out3
				    .println("DServer.init_device() --> Wrong argument type for DbGetDeviceList command");
			    Except
				    .throw_exception(
					    "API_IncompatibleCmdArgumentType",
					    "Imcompatible command argument type returned by the DbGetDeviceList command",
					    "DServer.init_device()");
			}

			if (dev_list.length == 0) {
			    final StringBuffer o = new StringBuffer(
				    "No device defined in database for class ");
			    o.append(((DeviceClass) class_list.elementAt(i)).get_name());

			    Except.throw_exception("API_DatabaseAccess", o.toString(),
				    "DServer.init_device()");
			}
			Util.out4.println(dev_list.length + " device(s) defined");

			// Create all device(s)
			((DeviceClass) class_list.elementAt(i)).device_factory(dev_list);
		    } else {
			Vector list = ((DeviceClass) class_list.elementAt(i)).get_nodb_name_list();
			String[] dev_list_nodb;
			if (i != class_list.size() - 1) {
			    ((DeviceClass) class_list.elementAt(i)).device_name_factory(list);
			} else {
			    if (tg.get_cmd_line_name_list().size() == 0) {
				((DeviceClass) class_list.elementAt(i)).device_name_factory(list);
			    } else {
				list = tg.get_cmd_line_name_list();
			    }
			}

			if (list.isEmpty() == true) {
			    dev_list_nodb = new String[1];
			    dev_list_nodb[0] = "NoName";
			} else {
			    dev_list_nodb = new String[list.size()];
			    for (int k = 0; k < list.size(); k++) {
				dev_list_nodb[k] = (String) list.elementAt(k);
			    }
			}

			//
			// Create all device(s)
			//

			((DeviceClass) class_list.elementAt(i)).device_factory(dev_list_nodb);
		    }
		}
	    }

	} catch (final OutOfMemoryError ex) {

	    //
	    // If the class_factory method have not been successfully executed,
	    // erase
	    // all classes already built. If the error occurs during the command
	    // or device
	    // factories, erase only the following classes
	    //

	    final StringBuffer o = new StringBuffer("Can't allocate memory in server while ");

	    if (class_factory_done == false) {
		final int class_err = class_list.size() + 1;
		o.append("creating class number ");
		o.append(class_err);
		if (class_list.isEmpty() == false) {
		    class_list.removeAllElements();
		}
	    } else {
		o.append("building command(s) or device(s) for class number ");
		o.append(i + 1);
		for (int j = i; j < class_list.size(); j++) {
		    class_list.removeElementAt(j);
		}
	    }

	    Except.throw_exception("API_MemoryAllocation", o.toString(), "DServer.init_device()");
	} catch (final DevFailed ex) {

	    //
	    // If the class_factory method have not been successfully executed,
	    // erase
	    // all classes already built. If the error occurs during the command
	    // or device
	    // factories, erase only the following classes
	    //

	    if (class_factory_done == false) {
		if (class_list.isEmpty() == false) {
		    class_list.removeAllElements();
		}
	    } else {
		for (int j = i; j < class_list.size(); j++) {
		    class_list.removeElementAt(j);
		}
	    }
	    throw ex;
	}
    }

    // +----------------------------------------------------------------------------
    //
    // method : class_factory()
    // 
    // description : Build all the DeviceClass embedded in a server
    // There is a difference in this method between the C++
    // version and the java version. In java, with its
    // "introspection" capability (Class class), it is
    // possible to build a class from its name. Using this nice
    // feature, this method simply ask the database for a list
    // of class name embedded ina device server process and
    // creates all of them.
    // In C++, it is the user responsability to write this
    // method
    //
    // -----------------------------------------------------------------------------

    private void class_factory() throws DevFailed {

	//
	// First, retrieve all classes embedded in this server
	//

	final Util tg = Util.instance();
	String[] cl_list = null;
	try {
	    if (Util._UseDb) {
		final StringBuffer str = new StringBuffer(tg.get_ds_exec_name());
		str.append('/');
		str.append(tg.get_ds_inst_name());

		final DeviceData send = new DeviceData();
		send.insert(str.toString());
		final DeviceData received = tg.get_database().command_inout(
			"DbGetDeviceServerClassList", send);

		cl_list = received.extractStringArray();
	    } else {
		final Vector v = tg.get_class_name_list();
		cl_list = new String[v.size()];
		for (int i = 0; i < v.size(); i++) {
		    cl_list[i] = (String) v.elementAt(i);
		}
	    }
	} catch (final BAD_OPERATION ex) {
	    Util.out3
		    .println("DServer.class_factory() --> Wrong argument type for DbGetDeviceServerClassList command");
	    Except
		    .throw_exception(
			    "API_IncompatibleCmdArgumentType",
			    "Imcompatible command argument type returned by the DbGetDeviceServerClassList command",
			    "DServer.class_factory()");
	} catch (final DevFailed ex) {
	    Util.out3.println("DServer.class_factory() --> db command failed");
	    Except.throw_exception("API_CantRetrieveClassList",
		    "The db command DbGetDeviceServerClassList failed", "DServer.class_factory()");
	}

	Util.out4.println(cl_list.length - 1 + " class(es) defined in server");

	//
	// Create each classes by calling its init method and store the
	// constructed
	// object in the class_list vector
	//

	int i = 0;
	try {
	    for (i = 0; i < cl_list.length; i++) {

		//
		// Forget the DServer class
		//

		if (cl_list[i].equals("DServer") == true) {
		    continue;
		}

		//
		// Build the array of object used to find the init method
		//

		final Class cl_param = Class.forName("java.lang.String");
		final Class cl_param_array[] = new Class[1];
		cl_param_array[0] = cl_param;

		//
		// If there is no "Class" at the end of the class name returned
		// by the db,
		// add it and create the Class object associated with the device
		// class
		//

		Class cl;
		final int pos = cl_list[i].indexOf("Class");
		if (pos == -1) {
		    final StringBuffer class_name = new StringBuffer(cl_list[i]);
		    final int containsDot = cl_list[i].indexOf(".");
		    if (containsDot == -1) {
			class_name.insert(0, ".");
			class_name.append("Class");
			class_name.insert(0, cl_list[i]);
		    } else {
			class_name.append("Class");
		    }

		    Util.out4.println("Searching for class : " + class_name);

		    cl = Class.forName(new String(class_name));
		} else {
		    cl = Class.forName(cl_list[i]);
		}

		//
		// Retrieve the init method (which should be static) and execute
		// it
		//

		final Method init_meth = cl.getMethod("init", cl_param_array);

		final java.lang.Object[] meth_param = new java.lang.Object[1];
		final int containsDot = cl_list[i].lastIndexOf(".");
		if (containsDot == -1) {
		    meth_param[0] = cl_list[i];
		} else {
		    meth_param[0] = cl_list[i].substring(containsDot + 1);
		}
		class_list.addElement(init_meth.invoke(null, meth_param));
	    }
	} catch (final ClassNotFoundException ex) {
	    final StringBuffer o = new StringBuffer("Can't retrieve class ");
	    o.append(cl_list[i]);
	    Util.out3.println("DServer.class_factory() --> Can't find class");
	    Except.throw_exception("API_ClassNotFound", new String(o), "DServer.class_factory()");
	} catch (final NoSuchMethodException ex) {
	    final StringBuffer o = new StringBuffer("Can't retrieve init method in class ");
	    o.append(cl_list[i]);
	    Util.out3.println("DServer.class_factory() --> Can't retrieve int method");
	    Except.throw_exception("API_InitMethodNotFound", new String(o),
		    "DServer.class_factory()");
	} catch (final SecurityException ex) {
	    final StringBuffer o = new StringBuffer("Security exception while creating class ");
	    o.append(cl_list[i]);
	    Util.out3
		    .println("DServer.class_factory() --> Security exception during class creation");
	    Except.throw_exception("API_JavaRuntimeSecurityException", new String(o),
		    "DServer.class_factory()");
	} catch (final InvocationTargetException ex) {
	    final StringBuffer o = new StringBuffer("The init method of class ");
	    o.append(cl_list[i]);
	    o.append(" throws an exception");
	    Util.out3
		    .println("DServer.class_factory() --> Init method send on exception during class creation");
	    final Throwable th = ex.getTargetException();
	    if (th instanceof fr.esrf.TangoApi.ConnectionFailed) {
		throw (fr.esrf.TangoApi.ConnectionFailed) th;
	    } else if (th instanceof DevFailed) {
		throw (DevFailed) th;
	    }

	    Except.throw_exception("API_InitThrowsException", new String(o),
		    "DServer.class_factory()");
	} catch (final IllegalAccessException ex) {
	    final StringBuffer o = new StringBuffer("The init method of class ");
	    o.append(cl_list[i]);
	    o.append(" is not public");
	    Util.out3
		    .println("DServer.class_factory() --> Init method not accessible during class creation");
	    Except.throw_exception("API_InitNotPublic", new String(o), "DServer.class_factory()");
	}
    }

    // +----------------------------------------------------------------------------
    //
    // method : query_class()
    // 
    // description : command to read all the classes used in a device server
    // process
    //
    // out : The class name list in a strings sequence
    //
    // -----------------------------------------------------------------------------

    public String[] query_class() throws DevFailed {
	Util.out4.println("In QueryClass command");

	final int nb_class = class_list.size();
	String[] ret = null;

	try {
	    ret = new String[nb_class];

	    for (int i = 0; i < nb_class; i++) {
		ret[i] = ((DeviceClass) class_list.elementAt(i)).get_name();
	    }
	} catch (final OutOfMemoryError ex) {
	    Util.out3.println("Memory Allocation error in DServer.query_class  method");
	    Except.throw_exception("API_MemoryAllocation", "Can't allocate memory in server",
		    "DServer.query_class");
	}
	return ret;
    }

    // +----------------------------------------------------------------------------
    //
    // method : query_device()
    // 
    // description : command to read all the devices implemented by a device
    // server process
    //
    // out : The device name list in a strings sequence
    //
    // -----------------------------------------------------------------------------

    public String[] query_device() throws DevFailed {
	Util.out4.println("In QueryDevice command");

	final int nb_class = class_list.size();
	final Vector tmp_name = new Vector();

	for (int i = 0; i < nb_class; i++) {
	    final int nb_dev = ((DeviceClass) class_list.elementAt(i)).get_device_list().size();
	    for (int j = 0; j < nb_dev; j++) {
		tmp_name.addElement(((DeviceImpl) ((DeviceClass) class_list.elementAt(i))
			.get_device_list().elementAt(j)).get_name());
	    }
	}

	String[] ret = null;
	try {
	    ret = new String[tmp_name.size()];

	    for (int i = 0; i < tmp_name.size(); i++) {
		ret[i] = (String) tmp_name.elementAt(i);
	    }
	} catch (final BAD_OPERATION ex) {
	    Util.out3.println("Memory Allocation error in DServer.query_device method");
	    Except.throw_exception("API_MemoryAllocation", "Can't allocate memory in server",
		    "DServer.query_device");
	}
	return ret;
    }

    // +----------------------------------------------------------------------------
    //
    // method : DServer.restart()
    // 
    // description : command to restart a device
    //
    // out : The device name to be re-started
    //
    // -----------------------------------------------------------------------------

    public void restart(final String dev_name) throws DevFailed {
	Util.out4.println("In DServer.restart(" + dev_name + ") method");
	//
	// Check if the wanted device exists in each class
	//

	final Util tg = Util.instance();
	Vector dev_list = tg.get_device_list_by_class(((DeviceClass) class_list.elementAt(0))
		.get_name());
	final int nb_class = class_list.size();
	int i, j, nb_dev;
	DeviceImpl dev_to_del = null;
	DeviceClass dev_cl = null;

	j = 0;
	for (i = 0; i < nb_class; i++) {
	    dev_list = tg.get_device_list_by_class(((DeviceClass) class_list.elementAt(i))
		    .get_name());
	    nb_dev = dev_list.size();
	    for (j = 0; j < nb_dev; j++) {
		if (((DeviceImpl) dev_list.elementAt(j)).get_name().equals(dev_name) == true) {
		    // Get device & class reference
		    dev_to_del = (DeviceImpl) dev_list.elementAt(j);
		    dev_cl = (DeviceClass) class_list.elementAt(i);
		    break;
		}
	    }
	    if (dev_to_del != null && dev_cl != null) {
		break;
	    }
	}

	//
	// Throw exception if the device is not found
	//

	if (dev_to_del == null || dev_cl == null) // Have been found
	{
	    final StringBuffer o = new StringBuffer("Device ");
	    o.append(dev_name);
	    o.append(" not found");

	    Except.throw_exception("API_DeviceNotFound", new String(o), "Dserver.restart()");
	}

	//
	// Remove ourself from device list
	//

	dev_list.removeElementAt(j);

	// Store polling conditions if any
	assert dev_to_del != null;
	final Vector p_obj = dev_to_del.get_poll_obj_list(); // PollObj
	final Vector dev_pol = new Vector(); // Pol

	for (i = 0; i < p_obj.size(); i++) {
	    dev_pol.add(p_obj.elementAt(i));
	}
	if (dev_pol.size() > 0) {
	    stop_polling();
	}

	// Delete the device (deactivate it and remove it)
	final POA r_poa = tg.get_poa();
	if (dev_to_del.get_exported_flag() == true) {
	    try {
		r_poa.deactivate_object(dev_to_del.get_obj_id());
	    } catch (final WrongPolicy ex) {
	    } catch (final ObjectNotActive ex) {
	    }
	}

	// Re-create device
	final String[] dev_name_list = new String[1];
	dev_name_list[0] = dev_name;
	assert dev_cl != null;
	dev_cl.device_factory(dev_name_list);

	// Re-start device polling (if any)
	final DevVarLongStringArray send = new DevVarLongStringArray();
	send.lvalue = new int[1];
	send.svalue = new String[3];

	for (i = 0; i < dev_pol.size(); i++) {
	    final PollObj poll_obj = (PollObj) dev_pol.elementAt(i);
	    // Send command to the polling thread
	    send.lvalue[0] = poll_obj.get_upd_i();
	    send.svalue[0] = poll_obj.get_name();
	    if (poll_obj.type == Tango_POLL_CMD) {
		send.svalue[1] = "command";
	    } else {
		send.svalue[1] = "attribute";
	    }
	    send.svalue[2] = poll_obj.name;

	    try {
		add_obj_polling(send, false);
	    } catch (final DevFailed e) {
		if (Util._tracelevel >= 4) {
		    Except.print_exception(e);
		}
	    }
	}
    }

    // +----------------------------------------------------------------------------
    //
    // method : DServer.restart_server()
    // 
    // description : command to restart a server
    //
    // -----------------------------------------------------------------------------

    public void restart_server() throws DevFailed {
	Util.out4.println("In DServer.restart_server() method");

	//	
	// Reset initial state and status
	//

	set_state(DevState.ON);
	set_status("The device is ON");

	//
	// Destroy and recreate the muli attribute object
	//

	final MultiAttribute tmp = new MultiAttribute(device_name, get_device_class());
	set_device_attr(tmp);

	//
	// Deleting the dserver device is a specific case. We must also delete
	// all
	// TDSOM embedded in this server
	//

	if (class_list.isEmpty() == false) {

	    //	
	    // Destroy already registered classes, devices and commands
	    // To destroy already created devices, we must disconnect them from
	    // the ORB
	    // otherwise their reference count will never decrease to 0 and the
	    // object will
	    // not be eligable for garbage collection.
	    //

	    final int nb_class = class_list.size();
	    final POA r_poa = Util.instance().get_poa();
	    for (int j = 0; j < nb_class; j++) {
		final Vector v = ((DeviceClass) class_list.elementAt(j)).get_device_list();
		final int nb_dev = v.size();

		for (int k = 0; k < nb_dev; k++) {
		    final DeviceImpl dev = (DeviceImpl) v.elementAt(k);
		    if (dev.get_exported_flag() == true) {
			dev.delete_device();
			try {
			    r_poa.deactivate_object(((DeviceImpl) v.elementAt(k)).get_obj_id());
			} catch (final WrongPolicy ex) {
			    ex.printStackTrace();
			} catch (final ObjectNotActive ex) {
			    ex.printStackTrace();
			}
		    }
		}
		v.removeAllElements();
		((DeviceClass) class_list.elementAt(j)).initClass();
	    }
	    class_list.removeAllElements();
	    System.out.println("DServer.restart_server - class list " + class_list);

	}

	// Restart everything
	init_device();

	// Restart polling (if any)
	Util.instance().polling_configure();
    }

    // +----------------------------------------------------------------------------
    //
    // method : kill()
    // 
    // description : command to kill the device server process. This is done
    // by starting a thread which will kill the process.
    // Starting a thread allows the client to receive
    // something from the server before it is killed
    //
    // -----------------------------------------------------------------------------

    public void kill() {
	Util.out4.println("In Kill command");
	// call delete_device
	final int nb_class = class_list.size();
	for (int j = 0; j < nb_class; j++) {
	    final Vector<?> v = ((DeviceClass) class_list.elementAt(j)).get_device_list();
	    final int nb_dev = v.size();
	    for (int k = 0; k < nb_dev; k++) {
		final DeviceImpl dev = (DeviceImpl) v.elementAt(k);
		if (dev.get_exported_flag() == true) {
		    try {
			dev.delete_device();
		    } catch (final DevFailed e) {
			e.printStackTrace();
			// ignore error, kill the device anyway
		    }
		}
	    }
	}
	//
	// Create the thread and start it
	//
	final KillThread killer = new KillThread();

	killer.start();
    }

    //
    // Miscellaneous obvious methods
    //

    public String get_process_name() {
	return process_name;
    }

    public String get_personal_name() {
	return instance_name;
    }

    public String get_instance_name() {
	return instance_name;
    }

    public String get_full_name() {
	return new String(full_name);
    }

    public Vector get_class_list() {
	return class_list;
    }

    @Override
    public Logger get_logger() {
	return Logging.core_logger();
    }

    @Override
    public void init_logger() {
	// - no-op : done @startup
    }

    // ===========================================
    //
    // Polling commands
    //
    // ===========================================

    // ===================================================================
    /**
     * Command to read all the devices actually polled by the device server.
     */
    // ===================================================================
    String[] polled_device() {
	Util.out4.println("In polled_device command");

	final int nb_class = class_list.size();
	final Vector dev_name = new Vector();
	for (int i = 0; i < nb_class; i++) {
	    final DeviceClass dc = (DeviceClass) class_list.elementAt(i);
	    final int nb_dev = dc.get_device_list().size();
	    for (int j = 0; j < nb_dev; j++) {
		// Get DS name if it is polled
		final DeviceImpl dev = dc.get_device_at(j);
		if (dev.is_polled() == true) {
		    dev_name.add(dev.get_name());
		}
	    }
	}

	// Return an empty sequence if no devices are polled
	if (dev_name.size() == 0) {
	    Util.out4.println("Return an empty sequence because no devices are polled");
	    return new String[0];
	}
	// Returned device name list to caller (sorted)
	final MyComp comp = new MyComp();
	Collections.sort(dev_name, comp);

	final int nb_dev = dev_name.size();
	final String[] ret = new String[nb_dev];
	for (int i = 0; i < nb_dev; i++) {
	    ret[i] = (String) dev_name.elementAt(i);
	}

	return ret;
    }

    // ===================================================================
    /**
     * command to read device polling status
     * 
     * @param dev_name
     *            The device name.
     * @return The device polling status as a string (multiple lines)
     */
    // ===================================================================
    synchronized String[] dev_poll_status(final String dev_name) throws DevFailed {
	Util.out4.println("In dev_poll_status command");

	// Find the device
	final DeviceImpl dev = Util.instance().get_device_by_name(dev_name);

	final Vector poll_list = dev.get_poll_obj_list();
	final int nb_poll_obj = poll_list.size();

	// Return an empty sequence if nothing is polled for this device
	if (nb_poll_obj == 0) {
	    return new String[0];
	}

	// Compute how many cmds and/or attributes are polled
	int nb_cmd = 0;
	for (int i = 0; i < nb_poll_obj; i++) {
	    final PollObj poll_obj = (PollObj) poll_list.elementAt(i);
	    if (poll_obj.get_type() == Tango_POLL_CMD) {
		nb_cmd++;
	    }
	}

	// Allocate memory for returned strings
	final String[] ret = new String[nb_poll_obj];

	// Populate returned strings
	int cmd_ind = 0;
	int attr_ind = nb_cmd;
	String returned_info;

	for (int i = 0; i < nb_poll_obj; i++) {
	    final PollObj poll_obj = (PollObj) poll_list.elementAt(i);
	    // First, the name
	    final int type = poll_obj.get_type();
	    if (type == Tango_POLL_CMD) {
		returned_info = "Polled command name = ";
	    } else {
		returned_info = "Polled attribute name = ";
	    }
	    returned_info += poll_obj.get_name();

	    // Add update period
	    returned_info += "\nPolling period (mS) = ";
	    final int tmp = poll_obj.get_upd();
	    returned_info = returned_info + tmp;

	    // Add ring buffer depth
	    returned_info += "\nPolling ring buffer depth = ";
	    final int depth = dev.get_poll_ring_depth();
	    if (depth == 0) {
		returned_info += Tango_DefaultPollRingDepth;
	    } else {
		returned_info += depth;
	    }

	    // Add a message if the data ring is empty
	    if (poll_obj.is_ring_empty() == true) {
		returned_info += "\nNo data recorded yet";
	    } else {
		// Add needed time to execute last command
		returned_info += "\nTime needed for the last ";
		if (type == Tango_POLL_CMD) {
		    returned_info += "command execution (mS) = ";
		} else {
		    returned_info += "attribute reading (mS) = ";
		}
		returned_info += poll_obj.get_needed_time_i();

		// Add not updated since... info
		returned_info += "\nData not updated since ";
		final double since = poll_obj.get_last_insert_date_i();

		final long ctm = System.currentTimeMillis();
		final int tv_sec = (int) (ctm / 1000);
		final int tv_usec = (int) (ctm - 1000 * tv_sec) * 1000;

		final double now_d = tv_sec + (double) tv_usec / 1000000;
		final double diff_t = now_d - since;
		if (diff_t < 1.0) {
		    final int nb_msec = (int) (diff_t * 1000);
		    returned_info = returned_info + nb_msec + " mS";
		} else if (diff_t < 60.0) {
		    final int nb_sec = (int) diff_t;
		    final int nb_msec = (int) ((diff_t - nb_sec) * 1000);
		    returned_info = returned_info + nb_sec + " S and ";
		    returned_info = returned_info + nb_msec + " mS";
		} else {
		    final int nb_min = (int) (diff_t / 60);
		    final int nb_sec = (int) (diff_t - 60 * nb_min);
		    final int nb_msec = (int) ((diff_t - (int) diff_t) * 1000);

		    returned_info = returned_info + nb_min + " MN";

		    if (nb_sec != 0) {
			returned_info = returned_info + " ," + nb_sec + " S";
		    }

		    if (nb_msec != 0) {
			returned_info = returned_info + " and " + nb_msec + " mS";
		    }
		}

		// Add delta_t between last record(s)
		try {
		    returned_info += "\nDelta between last records (in mS) = ";
		    final double[] delta = poll_obj.get_delta_t_i(4);
		    for (int j = 0; j < delta.length; j++) {
			final int nb_msec = (int) (delta[j] * 1000);
			returned_info = returned_info + nb_msec;
			if (j != delta.length - 1) {
			    returned_info = returned_info + ", ";
			}
		    }
		} catch (final DevFailed e) {
		}

		// Add last polling exception fields (if any)
		if (poll_obj.is_last_an_error_i() == true) {
		    if (type == Tango_POLL_CMD) {
			returned_info += "\nLast command execution FAILED :";
		    } else {
			returned_info += "\nLast attribute read FAILED :";
		    }
		    final DevFailed ex = poll_obj.get_last_except_i();
		    returned_info += "\n\tReason = " + ex.errors[0].reason;
		    returned_info += "\n\tDesc = " + ex.errors[0].desc;
		    returned_info += "\n\tOrigin = " + ex.errors[0].origin;
		}
	    }

	    // Init. string in sequence
	    if (type == Tango_POLL_CMD) {
		ret[cmd_ind] = returned_info;
		cmd_ind++;
	    } else {
		ret[attr_ind] = returned_info;
		attr_ind++;
	    }
	}
	return ret;
    }

    // ===================================================================
    /**
     * command to add one object to be polled
     * 
     * @param argin
     *            The polling parameters(device name, object type,..)
     */
    // ===================================================================
    public void add_obj_polling(final DevVarLongStringArray argin) throws DevFailed {
	add_obj_polling(argin, true);
    }

    // ===================================================================
    /**
     * command to add one object to be polled
     * 
     * @param argin
     *            The polling parameters(device name, object type,..)
     * @param with_db_upd
     *            Update db if true (false if no dbase).
     */
    // ===================================================================
    public void add_obj_polling(final DevVarLongStringArray argin, final boolean with_db_upd)
	    throws DevFailed {
	Util.out4.println("In add_obj_polling command");
	for (final String value : argin.svalue) {
	    Util.out4.println("Input string = " + value);
	}
	for (final int value : argin.lvalue) {
	    Util.out4.println("Input long = " + value);
	}

	// Check that parameters number is correct
	if (argin.svalue.length != 3 || argin.lvalue.length != 1) {
	    Except.throw_exception("API_WrongNumberOfArgs", "Incorrect number of inout arguments",
		    "DServer.add_obj_polling");
	}

	// Find the device
	final Util tg = Util.instance();
	DeviceImpl dev = null;
	try {
	    dev = tg.get_device_by_name(argin.svalue[0]);
	} catch (final DevFailed e) {
	    Except.re_throw_exception(e, "API_DeviceNotFound", "Device " + argin.svalue[0]
		    + " not found", "DServer.add_obj_polling");
	}

	// Check that the command (or the attribute) exists.
	// For command, also checks that it does not need input value.
	final String obj_type = argin.svalue[1].toLowerCase();
	final String obj_name = argin.svalue[2].toLowerCase();
	int type = Tango_POLL_CMD;
	assert dev != null;
	if (obj_type.equals(Tango_PollCommand)) {
	    dev.check_command_exists(obj_name);
	    type = Tango_POLL_CMD;
	} else if (obj_type.equals(Tango_PollAttribute)) {
	    dev.get_device_attr().get_attr_by_name(obj_name);
	    type = Tango_POLL_ATTR;
	} else {
	    Except.throw_exception("API_NotSupported",
		    "Object type " + obj_type + " not supported", "DServer.add_obj_polling");
	}

	// If it's for the Init command, refuse to poll it
	if (type == Tango_POLL_CMD) {
	    if (obj_name.equals("Init")) {
		Except.throw_exception("API_NotSupported",
			"It's not possible to poll the Init command!", "DServer.add_obj_polling");
	    }
	}

	// Check if the object is not already polled
	final Vector poll_list = dev.get_poll_obj_list();
	for (int i = 0; i < poll_list.size(); i++) {
	    final PollObj poll_obj = (PollObj) poll_list.elementAt(i);
	    if (poll_obj.get_type() == type) {
		if (poll_obj.get_name().equals(obj_name)) {
		    String s;
		    if (type == Tango_POLL_CMD) {
			s = "Command ";
		    } else {
			s = "Attribute ";
		    }
		    Except.throw_exception("API_AlreadyPolled", s + " " + obj_name
			    + " already polled", "DServer.add_obj_polling");
		}
	    }
	}
	// Check that the update period is not to small
	final int upd = argin.lvalue[0];
	if (upd < Tango_MIN_POLL_PERIOD && upd != 0) {
	    Except.throw_exception("API_NotSupported", upd
		    + " is below the min authorized period (100 mS)", "DServer.add_obj_polling");
	}

	// Create a new PollObj instance for this object
	poll_list.add(new PollObj(dev, type, obj_name, upd));

	// Send command to the polling thread but wait in case of previous cmd
	// still not executed
	Util.out4.println("Sending cmd to polling thread");
	final TangoMonitor mon = tg.get_poll_monitor();
	final PollThCmd shared_cmd = tg.get_poll_shared_cmd();

	if (shared_cmd.cmd_pending == true) {
	    mon.wait_it();
	}
	shared_cmd.cmd_pending = true;
	shared_cmd.cmd_code = Tango_POLL_ADD_OBJ;
	shared_cmd.dev = dev;
	shared_cmd.index = poll_list.size() - 1;
	mon.signal();
	Util.out4.println("Cmd sent to polling thread");

	// Wait for thread to execute command
	boolean interupted;
	while (shared_cmd.cmd_pending == true) {
	    interupted = mon.wait_it(Tango_DEFAULT_TIMEOUT);
	    if (shared_cmd.cmd_pending == true && interupted == false) {
		Util.out4.println("TIME OUT");
		poll_list.remove(poll_list.size() - 1);
		Except.throw_exception("API_CommandTimedOut", "Polling thread blocked !!!",
			"DServer.add_obj_polling");
	    }
	}
	Util.out4.println("Thread cmd normally executed");

	// Update polling parameters in database (if wanted and possible)
	// If the property is already there (it should not but...),
	// only update its polling period
	if (with_db_upd && Util._UseDb) {
	    final String upd_str = "" + upd;
	    boolean found = false;

	    final DbDatum db_info = new DbDatum("polled_cmd");
	    if (type == Tango_POLL_CMD) {
		final Vector non_auto_list = dev.get_non_auto_polled_cmd();
		for (int i = 0; i < non_auto_list.size(); i++) {
		    final String s = (String) non_auto_list.elementAt(i);
		    if (s.equals(obj_name)) {
			non_auto_list.remove(i);
			db_info.name = "non_auto_polled_cmd";
			db_info.insert(stringVect2StringArray(non_auto_list));
			found = true;
			break;
		    }
		}
		if (found == false) {
		    final Vector cmd_list = dev.get_polled_cmd();
		    int i;
		    for (i = 0; i < cmd_list.size(); i = i + 2) {
			final String s = (String) cmd_list.elementAt(i);
			if (s.equals(obj_name)) {
			    cmd_list.remove(i + 1);
			    cmd_list.insertElementAt(upd_str, i + 1);
			    break;
			}
		    }
		    if (i == cmd_list.size()) {
			cmd_list.add(obj_name);
			cmd_list.add(upd_str);
		    }
		    db_info.insert(stringVect2StringArray(cmd_list));
		}
	    } else {
		final Vector non_auto_list = dev.get_non_auto_polled_attr();
		for (int i = 0; i < non_auto_list.size(); i++) {
		    final String s = (String) non_auto_list.elementAt(i);
		    if (s.equals(obj_name)) {
			non_auto_list.remove(i);
			db_info.name = "non_auto_polled_attr";
			db_info.insert(stringVect2StringArray(non_auto_list));
			found = true;
			break;
		    }
		}
		if (found == false) {
		    db_info.name = "polled_attr";
		    final Vector attr_list = dev.get_polled_attr();
		    int i;
		    for (i = 0; i < attr_list.size(); i = i + 2) {
			final String s = (String) attr_list.elementAt(i);
			if (s.equals(obj_name)) {
			    attr_list.remove(i + 1);
			    attr_list.insertElementAt(upd_str, i + 1);
			    break;
			}
		    }
		    if (i == attr_list.size()) {
			attr_list.add(obj_name);
			attr_list.add(upd_str);
		    }
		    db_info.insert(stringVect2StringArray(attr_list));
		}
	    }

	    final DbDatum[] send_data = new DbDatum[1];
	    send_data[0] = db_info;
	    dev.get_db_device().put_property(send_data);

	    Util.out4.println("Polling properties updated");
	}

	// Mark the device as polled
	dev.is_polled(true);
    }

    // ===================================================================
    /**
     * command to upadte an already polled object update period
     * 
     * @param argin
     *            The polling parameters(device name, object type,..)
     */
    // ===================================================================
    void upd_obj_polling(final DevVarLongStringArray argin) throws DevFailed {
	upd_obj_polling(argin, true);
    }

    void upd_obj_polling(final DevVarLongStringArray argin, final boolean with_db_upd)
	    throws DevFailed {
	Util.out4.println("In upd_obj_polling command");
	for (final String value : argin.svalue) {
	    Util.out4.println("Input string = " + value);
	}
	for (final int value : argin.lvalue) {
	    Util.out4.println("Input long = " + value);
	}

	// Check that parameters number is correct
	if (argin.svalue.length != 3 || argin.lvalue.length != 1) {
	    Except.throw_exception("API_WrongNumberOfArgs", "Incorrect number of inout arguments",
		    "DServer.upd_obj_polling");
	}

	// Find the device
	final Util tg = Util.instance();
	DeviceImpl dev = null;
	try {
	    dev = tg.get_device_by_name(argin.svalue[0]);
	} catch (final DevFailed e) {
	    Except.re_throw_exception(e, "API_DeviceNotFound", "Device " + argin.svalue[0]
		    + " not found", "DServer.upd_obj_polling");
	}
	// Check that the device is polled
	assert dev != null;
	if (dev.is_polled() == false) {
	    Except.throw_exception("API_DeviceNotPolled", "Device " + argin.svalue[0]
		    + " is not polled", "DServer.upd_obj_polling_period");
	}

	// Find the wanted object in the list of device polled object
	final String obj_type = argin.svalue[1].toLowerCase();
	final String obj_name = argin.svalue[2].toLowerCase();
	int type = Tango_POLL_CMD;

	if (obj_type.equals(Tango_PollCommand)) {
	    type = Tango_POLL_CMD;
	} else if (obj_type.equals(Tango_PollAttribute)) {
	    type = Tango_POLL_ATTR;
	} else {
	    Except.throw_exception("API_NotSupported",
		    "Object type " + obj_type + " not supported", "DServer.upd_obj_polling_period");
	}

	// Update polling period
	final Vector poll_list = dev.get_poll_obj_list();
	for (int i = 0; i < poll_list.size(); i++) {
	    final PollObj poll_obj = (PollObj) poll_list.elementAt(i);
	    if (poll_obj.get_type() == type) {
		if (poll_obj.get_name().equals(obj_name)) {
		    poll_obj.update_upd(argin.lvalue[0]);
		}
	    }
	}

	// Send command to the polling thread
	final TangoMonitor mon = tg.get_poll_monitor();
	final PollThCmd shared_cmd = tg.get_poll_shared_cmd();

	if (shared_cmd.cmd_pending == true) {
	    mon.wait_it();
	}
	shared_cmd.cmd_pending = true;
	shared_cmd.cmd_code = Tango_POLL_UPD_PERIOD;
	shared_cmd.dev = dev;
	shared_cmd.name = obj_name;
	shared_cmd.type = type;
	shared_cmd.new_upd = argin.lvalue[0];

	mon.signal();

	// Update database property --> Update polling period if this object is
	// already
	// defined in the polling property. Add object name and update period if
	// the
	// object is not known in the property
	if (with_db_upd && Util._UseDb) {
	    final String upd_str = "" + argin.lvalue[0];

	    final DbDatum db_info = new DbDatum("polled_attr");
	    if (type == Tango_POLL_CMD) {
		db_info.name = "polled_cmd";
		final Vector cmd_list = dev.get_polled_cmd();
		int i;
		for (i = 0; i < cmd_list.size(); i = i + 2) {
		    final String s = (String) cmd_list.elementAt(i);
		    if (s.equals(obj_name)) {
			cmd_list.remove(i + 1);
			cmd_list.insertElementAt(upd_str, i + 1);
			break;
		    }
		}
		if (i == cmd_list.size()) {
		    cmd_list.add(obj_name);
		    cmd_list.add(upd_str);
		}
		db_info.insert(stringVect2StringArray(cmd_list));
	    } else {
		final Vector attr_list = dev.get_polled_attr();
		int i;
		for (i = 0; i < attr_list.size(); i = i + 2) {
		    final String s = (String) attr_list.elementAt(i);
		    if (s.equals(obj_name)) {
			attr_list.remove(i + 1);
			attr_list.insertElementAt(upd_str, i + 1);
			break;
		    }
		}
		if (i == attr_list.size()) {
		    attr_list.add(obj_name);
		    attr_list.add(upd_str);
		}
		db_info.insert(stringVect2StringArray(attr_list));
	    }

	    final DbDatum[] send_data = new DbDatum[1];
	    send_data[0] = db_info;
	    dev.get_db_device().put_property(send_data);

	    Util.out4.println("Polling properties updated");
	}
    }

    // ===================================================================
    /**
     * command to remove an already polled object from the device polled object
     * list
     * 
     * @param argin
     *            The polling parameters(device name, object type,..)
     */
    // ===================================================================
    public void rem_obj_polling(final String[] argin) throws DevFailed {
	rem_obj_polling(argin, Util._UseDb);
    }

    // ===================================================================
    /**
     * command to remove an already polled object from the device polled object
     * list
     * 
     * @param argin
     *            The polling parameters(device name, object type,..)
     * @param with_db_upd
     *            Update db if true (false if no dbase).
     */
    // ===================================================================
    public synchronized void rem_obj_polling(final String[] argin, final boolean with_db_upd)
	    throws DevFailed {
	Util.out4.println("In rem_obj_polling command");
	for (final String arg : argin) {
	    Util.out4.println("Input string = " + arg);
	}

	// Check that parameters number is correct
	if (argin.length != 3) {
	    Except.throw_exception("API_WrongNumberOfArgs", "Incorrect number of inout arguments",
		    "DServer.rem_obj_polling");
	}

	// Find the device
	final Util tg = Util.instance();
	DeviceImpl dev = null;
	try {
	    dev = tg.get_device_by_name(argin[0]);
	} catch (final DevFailed e) {
	    Except.re_throw_exception(e, "API_DeviceNotFound", "Device " + argin + " not found",
		    "DServer.rem_obj_polling");
	}
	// Check that the device is polled
	assert dev != null;
	if (dev.is_polled() == false) {
	    Except.throw_exception("API_DeviceNotPolled", "Device " + argin[0] + " is not polled",
		    "DServer.rem_obj_polling_period");
	}

	// Find the wanted object in the list of device polled object
	final String obj_type = argin[1].toLowerCase();
	final String obj_name = argin[2].toLowerCase();
	int type = Tango_POLL_CMD;

	if (obj_type.equals(Tango_PollCommand)) {
	    type = Tango_POLL_CMD;
	} else if (obj_type.equals(Tango_PollAttribute)) {
	    type = Tango_POLL_ATTR;
	} else {
	    Except.throw_exception("API_NotSupported",
		    "Object type " + obj_type + " not supported", "DServer.rem_obj_polling_period");
	}

	final Vector poll_list = dev.get_poll_obj_list();
	for (int i = 0; i < poll_list.size(); i++) {
	    final PollObj poll_obj = (PollObj) poll_list.elementAt(i);
	    if (poll_obj.get_type() == type) {
		if (poll_obj.get_name().equals(obj_name)) {
		    poll_list.remove(i);
		}
	    }
	}

	Util.out4.println("Sending cmd to polling thread");
	final TangoMonitor mon = tg.get_poll_monitor();
	final PollThCmd shared_cmd = tg.get_poll_shared_cmd();
	if (shared_cmd.cmd_pending == true) {
	    mon.signal();
	}
	shared_cmd.cmd_pending = true;
	shared_cmd.cmd_code = Tango_POLL_REM_OBJ;
	shared_cmd.dev = dev;
	shared_cmd.name = obj_name;
	shared_cmd.type = type;
	mon.signal();

	Util.out4.println("Cmd sent to polling thread");

	// Wait for thread to execute command
	boolean interrupted;
	while (shared_cmd.cmd_pending == true) {
	    interrupted = mon.wait_it(Tango_DEFAULT_TIMEOUT);
	    if (shared_cmd.cmd_pending == true && interrupted == false) {
		// Util.out4
		System.out.println("TIME OUT");
		Except.throw_exception("API_CommandTimedOut", "Polling thread blocked !!!",
			"DServer.rem_obj_polling");
	    }
	}
	Util.out4.println("Thread cmd normally executed");

	// Mark the device as non polled if this was the last polled object
	if (poll_list.size() == 0) {
	    dev.is_polled(false);
	}

	// Update database property. This means remove object entry in the
	// polling
	// properties if they exist or add it to the list of device not polled
	// for automatic polling defined at command/attribute level.
	// Do this if possible and wanted.
	if (with_db_upd && Util._UseDb) {
	    final DbDatum db_info = new DbDatum("polled_attr");
	    boolean update_needed = false;
	    if (type == Tango_POLL_CMD) {
		db_info.name = "polled_cmd";
		final Vector cmd_list = dev.get_polled_cmd();
		int i;
		for (i = 0; i < cmd_list.size(); i++) {
		    final String s = (String) cmd_list.elementAt(i);
		    if (s.equals(obj_name)) {
			cmd_list.remove(i);
			cmd_list.remove(i);
			db_info.insert(stringVect2StringArray(cmd_list));
			update_needed = true;
			break;
		    }
		    i++;
		}
		if (update_needed == false) {
		    final Vector non_auto_cmd = dev.get_non_auto_polled_cmd();
		    for (i = 0; i < non_auto_cmd.size(); i++) {
			final String s = (String) non_auto_cmd.elementAt(i);
			if (s.equals(obj_name)) {
			    break;
			}
		    }
		    if (i == cmd_list.size()) {
			non_auto_cmd.add(obj_name);
			db_info.name = "non_auto_polled_cmd";
			db_info.insert(stringVect2StringArray(non_auto_cmd));
			update_needed = true;
		    }
		}
	    } else {
		final Vector attr_list = dev.get_polled_attr();
		int i;
		for (i = 0; i < attr_list.size(); i++) {
		    final String s = (String) attr_list.elementAt(i);
		    if (s.equals(obj_name)) {
			attr_list.remove(i);
			attr_list.remove(i);
			db_info.insert(stringVect2StringArray(attr_list));
			update_needed = true;
			break;
		    }
		    i++;
		}
		if (update_needed == false) {
		    final Vector non_auto_attr = dev.get_non_auto_polled_attr();
		    for (i = 0; i < non_auto_attr.size(); i++) {
			final String s = (String) non_auto_attr.elementAt(i);
			if (s.equals(obj_name)) {
			    break;
			}
		    }
		    if (i == attr_list.size()) {
			non_auto_attr.add(obj_name);
			db_info.name = "non_auto_polled_cmd";
			db_info.insert(stringVect2StringArray(non_auto_attr));
			update_needed = true;
		    }
		}
	    }
	    if (update_needed == true) {
		final DbDatum[] send_data = new DbDatum[1];
		send_data[0] = db_info;
		dev.get_db_device().put_property(send_data);
		Util.out4.println("Polling properties updated");
	    }
	}
    }

    // ===================================================================
    /**
     * command to stop the polling thread
     */
    // ===================================================================
    synchronized void stop_polling() throws DevFailed {
	Util.out4.println("In stop_polling method");

	// Send command to the polling thread and wait for its execution
	final Util tg = Util.instance();
	final TangoMonitor mon = tg.get_poll_monitor();
	final PollThCmd shared_cmd = tg.get_poll_shared_cmd();
	if (shared_cmd.cmd_pending == true) {
	    mon.signal();
	}
	shared_cmd.cmd_pending = true;
	shared_cmd.cmd_code = Tango_POLL_STOP;

	mon.signal();

	boolean interupted;
	while (shared_cmd.cmd_pending == true) {
	    interupted = mon.wait_it(Tango_DEFAULT_TIMEOUT);

	    if (shared_cmd.cmd_pending == true && interupted == false) {
		Util.out4.println("TIME OUT");
		Except.throw_exception("API_CommandTimedOut", "Polling thread blocked !!!",
			"DServer.stop_polling");
	    }
	}

	// Update polling status
	tg.poll_status(false);
	set_status("The device is ON\nThe polling is OFF");
    }

    // ===================================================================
    /**
     * command to start the polling thread
     */
    // ===================================================================
    synchronized void start_polling() throws DevFailed {
	Util.out4.println("In start_polling method");

	// Send command to the polling thread and wait for its execution
	final Util tg = Util.instance();
	final TangoMonitor mon = tg.get_poll_monitor();
	final PollThCmd shared_cmd = tg.get_poll_shared_cmd();
	if (shared_cmd.cmd_pending == true) {
	    mon.signal();
	}
	shared_cmd.cmd_pending = true;
	shared_cmd.cmd_code = Tango_POLL_START;

	mon.signal();

	boolean interupted;
	while (shared_cmd.cmd_pending == true) {
	    interupted = mon.wait_it(Tango_DEFAULT_TIMEOUT);

	    if (shared_cmd.cmd_pending == true && interupted == false) {
		Util.out4.println("TIME OUT");
		Except.throw_exception("API_CommandTimedOut", "Polling thread blocked !!!",
			"DServer.start_polling");
	    }
	}

	// Update polling status
	tg.poll_status(true);
	set_status("The device is ON\nThe polling is ON");
    }

    // ===================================================================
    // ===================================================================
    private String[] stringVect2StringArray(final Vector v) {
	final String[] array = new String[v.size()];
	for (int i = 0; i < v.size(); i++) {
	    array[i] = (String) v.elementAt(i);
	}
	return array;
    }

    @Override
    public void delete_device() throws DevFailed {
	class_list.clear();
    }
}
