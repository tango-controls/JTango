//+============================================================================
//
// file :               DeviceImpl.java
//
// description :        Java source code for the DeviceImpl class. This class
//			is the root class for all derived Device classes.
//			It is an abstract classes. The DeviceImpl class is the
//			CORBA servant which is "exported" onto the network and
//			accessed by the client.
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
// Revision 1.11  2009/03/31 07:50:49  pascal_verdier
// Pb with always executed_hook() fixed.
//
// Revision 1.10  2009/03/27 08:13:04  pascal_verdier
// Exception different than DevFailed catched in command_inout and write_attribute
//
// Revision 1.9  2009/03/25 13:34:08  pascal_verdier
// Synchronization modified.
//
// Revision 1.8  2009/01/16 12:37:13  pascal_verdier
// IntelliJIdea warnings removed.
//
// Revision 1.7  2008/12/03 15:43:51  pascal_verdier
// javadoc warnings removed.
//
// Revision 1.6  2008/10/10 11:30:46  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2008/09/19 09:03:25  pascal_verdier
// Pb in connections counter fixed.
//
// Revision 1.4  2008/09/19 07:08:13  pascal_verdier
// Serialization BY_DEV, BY_CLASS, NO_SYNC (by Attribute).
// Check poa.pool_thread_max value.
// Database.(get/put)_device_attribute_property() replace command_inout("Db(Get/Put)DeviceAttributeProperty") for new db command.
//
// Revision 1.3  2008/09/12 11:27:51  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.2  2008/03/13 08:58:29  pascal_verdier
// Commands and Attributes access have been serialized.
//
// Revision 1.1  2007/08/23 08:33:25  ounsy
// updated change from api/java
//
// Revision 3.13  2007/05/29 08:07:43  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.12  2006/09/15 14:49:38  pascal_verdier
// set_value for boolean bug fixed.
//
// Revision 3.11  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.10  2005/06/13 09:08:42  pascal_verdier
// Attribute historic buffer can be filled by trigger.
//
// Revision 3.9  2005/03/08 09:37:40  pascal_verdier
// Case bug fixed in get_device_by_name().
//
// Revision 3.8  2004/06/21 12:25:25  pascal_verdier
// Command_query cmd bug fixed.
//
// Revision 3.7  2004/06/04 08:48:10  ounsy
// Added KeepAliveThread stopping after Eventconsumerthread shutdown
//
// Revision 3.6  2004/05/14 13:47:57  pascal_verdier
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
// Revision 1.6  2001/10/10 08:11:24  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:37  taurel
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
// Revision 1.2  2000/02/04 09:09:59  taurel
// Just update revision number
//
// Revision 1.1.1.1  2000/02/04 09:08:24  taurel
// Imported sources
//
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;

import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.SystemException;
import org.omg.PortableServer.POA;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.AttributeConfig;
import fr.esrf.Tango.AttributeConfig_2;
import fr.esrf.Tango.AttributeValue;
import fr.esrf.Tango.DevAttrHistory;
import fr.esrf.Tango.DevCmdHistory;
import fr.esrf.Tango.DevCmdInfo;
import fr.esrf.Tango.DevCmdInfo_2;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevInfo;
import fr.esrf.Tango.DevSource;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevVarBooleanArrayHelper;
import fr.esrf.Tango.DevVarDoubleArrayHelper;
import fr.esrf.Tango.DevVarLong64ArrayHelper;
import fr.esrf.Tango.DevVarLongArrayHelper;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.Tango.DevVarShortArrayHelper;
import fr.esrf.Tango.DevVarStateArrayHelper;
import fr.esrf.Tango.DevVarStringArrayHelper;
import fr.esrf.Tango.DevVarULong64ArrayHelper;
import fr.esrf.Tango.DevVarULongArrayHelper;
import fr.esrf.Tango.DevVarUShortArrayHelper;
import fr.esrf.Tango.Device_2POA;
import fr.esrf.TangoApi.DbAttribute;
import fr.esrf.TangoApi.DbDatum;
import fr.esrf.TangoApi.DbDevice;
import fr.esrf.TangoApi.DeviceProxy;

/**
 * Base class for all TANGO device
 */

public abstract class DeviceImpl extends Device_2POA implements TangoConst {

    /**
     * The device black box
     */
    protected BlackBox blackbox;
    /**
     * The device black box depth
     */
    protected int blackbox_depth;
    /**
     * The device name
     */
    protected String device_name;
    /**
     * The administration device name
     */
    protected String adm_device_name;
    /**
     * The device description
     */
    protected String desc;
    /**
     * The device status
     */
    protected String device_status;
    /**
     * The device state
     */
    protected DevState device_state;
    /**
     * The device access
     */
    protected byte[] access;
    /**
     * The device version
     */
    protected int version;
    /**
     * Reference to the device-class object associated with the device
     */
    protected final DeviceClass device_class;
    /**
     * Reference to the multi attribute object
     */
    protected MultiAttribute dev_attr;
    /**
     * Reference to the associated DbDevice object
     */
    protected DbDevice db_dev;

    private byte[] obj_id;
    private boolean exported = false;

    /**
     * Device's logger
     */
    private Logger logger = null;

    /**
     * Device's logging level backup (for stop/start_logging)
     */
    private Level last_level = Level.OFF;

    /**
     * Device's rolling file threshold
     */
    private long rft = LOGGING_DEF_RFT;

    /**
     * DeviceImpl polling extention.
     */
    private final DeviceImplExt ext;

    // +-------------------------------------------------------------------------
    //
    // method : DeviceImpl
    // 
    // description : constructor for the DeviceImpl class from the class object,
    // the device name, the description field,
    // the state and the status.
    //
    // argument : in : - cl : The class object
    // - d_name : The device name
    // - de : The device description (default to "A TANGO device")
    // - st : The device state (default to UNKNOWN)
    // - sta : The device status (default to "Not initialised")
    //
    // --------------------------------------------------------------------------

    /**
     * Constructs a newly allocated DeviceImpl object from all its creation
     * parameters.
     * 
     * The device is constructed from its name, its description, an original
     * state and status
     * 
     * @param cl
     *            Reference to the device class object
     * @param d_name
     *            The device name
     * @param de
     *            The device description field
     * @param st
     *            The device creation state
     * @param sta
     *            The device creation status
     * 
     * @exception DevFailed
     *                Re-throw of the exception thrown during the creation of
     *                the multi-attribute object. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */

    public DeviceImpl(final DeviceClass cl, final String d_name, final String de,
	    final DevState st, final String sta) throws DevFailed {
	device_class = cl;
	device_name = d_name;
	desc = de;
	device_state = st;
	device_status = sta;

	version = Tango_DevVersion;
	blackbox_depth = 0;

	ext = new DeviceImplExt();

	if (Util._UseDb) {
	    db_dev = new DbDevice(device_name);
	}
	get_dev_system_resource();

	black_box_create();

	dev_attr = new MultiAttribute(device_name, device_class);

	final StringBuffer tmp_adm_name = new StringBuffer("dserver/");
	tmp_adm_name.append(Util.instance().get_ds_name());
	adm_device_name = tmp_adm_name.toString();

	init_logger();
    }

    /**
     * Constructs a newly allocated DeviceImpl object from its name.
     * 
     * The device description field is set to <i>A Tango device</i>. The device
     * state is set to unknown and the device status is set to <i>Not
     * Initialised</i>
     * 
     * @param cl
     *            Reference to the device class object
     * @param d_name
     *            The device name
     * 
     * @exception DevFailed
     *                Re-throw of the exception thrown during the creation of
     *                the multi-attribute object. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */

    public DeviceImpl(final DeviceClass cl, final String d_name) throws DevFailed {
	device_class = cl;
	device_name = d_name;

	desc = "A Tango device";
	device_state = DevState.UNKNOWN;
	device_status = "Not Initialised";

	version = Tango_DevVersion;
	blackbox_depth = 0;

	ext = new DeviceImplExt();

	if (Util._UseDb) {
	    db_dev = new DbDevice(device_name);
	}
	get_dev_system_resource();

	black_box_create();

	dev_attr = new MultiAttribute(device_name, device_class);

	final StringBuffer tmp_adm_name = new StringBuffer("dserver/");
	tmp_adm_name.append(Util.instance().get_ds_name());
	adm_device_name = tmp_adm_name.toString();
	init_logger();
    }

    /**
     * Constructs a newly allocated DeviceImpl object from its name and its
     * description.
     * 
     * The device state is set to unknown and the device status is set to <i>Not
     * Initialised</i>
     * 
     * @param cl
     *            Reference to the device class object
     * @param d_name
     *            The device name
     * @param desc
     *            The device description
     * 
     * @exception DevFailed
     *                Re-throw of the exception thrown during the creation of
     *                the multi-attribute object. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */

    public DeviceImpl(final DeviceClass cl, final String d_name, final String desc)
	    throws DevFailed {
	device_class = cl;
	device_name = d_name;

	this.desc = desc;
	device_state = DevState.UNKNOWN;
	device_status = "Not Initialised";

	version = Tango_DevVersion;
	blackbox_depth = 0;

	ext = new DeviceImplExt();

	if (Util._UseDb) {
	    db_dev = new DbDevice(device_name);
	}
	get_dev_system_resource();

	black_box_create();

	dev_attr = new MultiAttribute(device_name, device_class);

	final StringBuffer tmp_adm_name = new StringBuffer("dserver/");
	tmp_adm_name.append(Util.instance().get_ds_name());
	adm_device_name = tmp_adm_name.toString();
	init_logger();
    }

    // +-------------------------------------------------------------------------
    //
    // method : black_box_create
    // 
    // description : Private method to create the device black box.
    // The black box depth is a resource with a default
    // value if the resource is not defined
    //
    // --------------------------------------------------------------------------

    private void black_box_create() {

	//
	// If the black box depth objbect attribute is null, create one with the
	// default depth
	//

	if (blackbox_depth == 0) {
	    blackbox = new BlackBox();
	} else {
	    blackbox = new BlackBox(blackbox_depth);
	}
    }

    // +----------------------------------------------------------------------------
    //
    // method : get_dev_system_resource()
    // 
    // description : Method to retrieve some basic device resources
    // The resources to be retrived are :
    // - The black box depth
    // - The device description
    //
    // -----------------------------------------------------------------------------

    private void get_dev_system_resource() throws DevFailed {

	//
	// Try to retrieve resources for device black box depth and device
	// description
	//

	if (Util._UseDb) {
	    try {
		final String[] prop_names = { "blackbox_depth", "description", "poll_ring_depth",
			"polled_cmd", "polled_attr", "non_auto_polled_cmd", "non_auto_polled_attr",
			"poll_old_factor" };

		// Call db server
		final DbDatum[] res_value = db_dev.get_property(prop_names);

		int i = 0;
		if (res_value[i].is_empty() == false) {
		    blackbox_depth = res_value[i].extractLong();
		}
		i++;
		if (res_value[i].is_empty() == false) {
		    desc = res_value[i].extractString();
		}
		i++;

		if (res_value[i].is_empty() == false) {
		    set_poll_ring_depth(res_value[i].extractLong());
		}
		i++;
		if (res_value[i].is_empty() == false) {
		    set_polled_cmd(res_value[i].extractStringArray());
		}
		i++;
		if (res_value[i].is_empty() == false) {
		    set_polled_attr(res_value[i].extractStringArray());
		}
		i++;
		if (res_value[i].is_empty() == false) {
		    set_non_auto_polled_cmd(res_value[i].extractStringArray());
		}
		i++;
		if (res_value[i].is_empty() == false) {
		    set_non_auto_polled_attr(res_value[i].extractStringArray());
		}
		i++;
		if (res_value[i].is_empty() == false) {
		    set_poll_old_factor(res_value[i].extractLong());
		} else {
		    set_poll_old_factor(Tango_DEFAULT_POLL_OLD_FACTOR);
		}
	    } catch (final DevFailed e) {
		final StringBuffer o = new StringBuffer(
			"Database error while trying to retrieve device properties for device ");
		o.append(device_name);

		Except.throw_exception("API_DatabaseAccess", o.toString(),
			"DeviceImpl.get_dev_system_resource");
	    } catch (final BAD_OPERATION ex) {
		final StringBuffer o = new StringBuffer(
			"Database error while trying to retrieve device properties for device ");
		o.append(device_name);

		Except.throw_exception("API_DatabaseAccess", o.toString(),
			"DeviceImpl.get_dev_system_resource");
	    }
	}
    }

    // +-------------------------------------------------------------------------
    //
    // method : command_inout
    // 
    // description : Method called for each command_inout operation executed
    // from any client
    // The call to this method is in a try bloc for the
    // DevFailed exception (generated by the idl
    // compiler). Therefore, it
    // is not necessary to propagate by re-throw the exceptions
    // thrown by the underlying functions/methods.
    //
    // --------------------------------------------------------------------------

    /**
     * Execute a command.
     * 
     * It's the master method executed when a "command_inout" CORBA operation is
     * requested by a client. It updates the device black-box, call the TANGO
     * command handler and returned the output Any
     * 
     * @param in_cmd
     *            The command name
     * @param in_any
     *            The command input data packed in a CORBA Any
     * @return The command output data packed in a CORBA Any object
     * @exception DevFailed
     *                Re-throw of the exception thrown by the command_handler
     *                method. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */

    public Any command_inout(final String in_cmd, final Any in_any) throws DevFailed {

	Util.out4.println("DeviceImpl.command_inout(): command received : " + in_cmd);

	//
	// Record operation request in black box
	//

	blackbox.insert_cmd(in_cmd, 1);

	//	
	// Execute command
	//
	Any out_any = null;
	try {
	    // If nb connection is closed to the maximum value,
	    // throw an exception to do not block devices.
	    Util.increaseAccessConter();
	    if (Util.getAccessConter() > Util.getPoaThreadPoolMax() - 2) {
		Util.decreaseAccessConter();
		Except.throw_exception("API_MemoryAllocation", Util.instance().get_ds_real_name()
			+ ": No thread available to connect device",
			"DeviceImpl.write_attributes()");
	    }
	    switch (Util.get_serial_model()) {
	    case BY_CLASS:
		synchronized (device_class) {
		    out_any = device_class.command_handler(this, in_cmd, in_any);
		}
		break;

	    case BY_DEVICE:
		synchronized (this) {
		    out_any = device_class.command_handler(this, in_cmd, in_any);
		}
		break;

	    default: // NO_SYNC
		out_any = device_class.command_handler(this, in_cmd, in_any);
	    }
	} catch (final DevFailed exc) {
	    Util.decreaseAccessConter();
	    throw exc;
	} catch (final Exception exc) {
	    Util.decreaseAccessConter();
	    Except.throw_exception("API_ExceptionCatched", exc.toString(),
		    "DeviceImpl.command_inout");
	}
	Util.decreaseAccessConter();

	//
	// Return value to the caller
	//

	Util.out4.println("DeviceImpl.command_inout(): leaving method for command " + in_cmd);
	return out_any;
    }

    // +-------------------------------------------------------------------------
    //
    // method : Device_2Impl.command_inout_2
    // 
    // description : Method called for each command_inout operation executed
    // from any client on a Tango device version 2.
    //
    // --------------------------------------------------------------------------
    public Any command_inout_2(final String in_cmd, final Any in_data, final DevSource source)
	    throws DevFailed, SystemException {
	Util.out4.println("Device_2Impl.command_inout_2 arrived, source = " + source
		+ ", command = " + in_cmd);

	PollObj polled_cmd = null;
	boolean polling_failed = false;

	// If the source parameter specifies device, call the command_inout
	// method
	// implemented by the DeviceImpl class
	if (source == DevSource.DEV) {
	    return command_inout(in_cmd, in_data);
	}

	try {
	    // Record operation request in black box
	    blackbox.insert_cmd(in_cmd, 2);

	    final String cmd_str = in_cmd.toLowerCase();

	    // Check that device supports this command
	    check_command_exists(cmd_str);
	    // Check that the command is polled
	    boolean found = false;
	    final Vector poll_list = get_poll_obj_list(); // PollObj
	    for (int i = 0; i < poll_list.size(); i++) {
		final PollObj poll_obj = (PollObj) poll_list.elementAt(i);
		if (poll_obj.get_type() == Tango_POLL_CMD
			&& poll_obj.get_name().equals(in_cmd.toLowerCase())) {
		    polled_cmd = poll_obj;
		    found = true;
		}
	    }
	    // If the command is not polled but its polling update period is
	    // defined, and the command is not in the device list of command
	    // which should not be polled, start to poll it
	    if (found == false) {
		final Command cmd = get_command(cmd_str);
		final int poll_period = cmd.get_polling_period();
		if (poll_period == 0) {
		    Except.throw_exception("API_CmdNotPolled", "Command " + in_cmd + " not polled",
			    "Device_2Impl.command_inout");
		}

		found = false;
		final Vector napc = get_non_auto_polled_cmd();
		for (int i = 0; i < napc.size(); i++) {
		    final String s = (String) napc.elementAt(i);
		    if (s.equals(cmd_str)) {
			found = true;
		    }
		}
		if (found == true) {
		    Except.throw_exception("API_CmdNotPolled", "Command " + in_cmd + " not polled",
			    "Device_2Impl.command_inout");
		}

		else {
		    final Util tg = Util.instance();
		    final DServer adm_dev = tg.get_dserver_device();

		    final DevVarLongStringArray send = new DevVarLongStringArray();
		    send.lvalue = new int[1];
		    send.svalue = new String[3];

		    send.lvalue[0] = poll_period;
		    send.svalue[0] = device_name;
		    send.svalue[1] = "command";
		    send.svalue[2] = in_cmd;

		    adm_dev.add_obj_polling(send, false);

		    // Wait for first polling. Release monitor during this
		    // sleep in order to give it to the polling thread
		    /*
		     * ext.only_one.rel_monitor(); try { Thread.sleep(500); }
		     * catch (InterruptedException e){}
		     * ext.only_one.get_monitor();
		     */
		    // Update the polled-cmd pointer to the new polled object
		    for (int i = 0; i < poll_list.size(); i++) {
			final PollObj poll_obj = (PollObj) poll_list.elementAt(i);
			if (poll_obj.get_type() == Tango_POLL_CMD
				&& poll_obj.get_name().equals(in_cmd)) {
			    polled_cmd = poll_obj;
			    break;
			}
		    }
		}
	    }

	    if (polled_cmd == null) {
		Except.throw_exception("API_CommandNotPolled", "Command not found.",
			"Device_2Impl.command_inout");
	    }
	    // Check that some data is available in cache
	    assert polled_cmd != null;
	    if (polled_cmd.is_ring_empty()) {
		Except.throw_exception("API_NoDataYet", "No data available in cache for command "
			+ in_cmd, "Device_2Impl.command_inout");
	    }
	    // Check that data are still refreshed by the polling thread
	    final long ctm = System.currentTimeMillis();
	    int tv_sec = (int) (ctm / 1000);
	    final int tv_usec = (int) (ctm - 1000 * tv_sec) * 1000;
	    tv_sec = tv_sec - Tango_DELTA_T;
	    final double last = polled_cmd.get_last_insert_date();
	    final double now_d = tv_sec + (double) tv_usec / 1000000;
	    final double diff_d = now_d - last;
	    if (diff_d > polled_cmd.get_authorized_delta()) {
		Except.throw_exception("API_NotUpdatedAnyMore", "Data in cache for command "
			+ in_cmd + " not updated any more", "Device_2Impl.command_inout");
	    }
	} catch (final DevFailed e) {
	    if (source == DevSource.CACHE) {
		throw e;
	    }
	    polling_failed = true;
	}
	// Get cmd result (or exception)
	if (source == DevSource.CACHE) {
	    Util.out4.println("Device_2Impl: Returning data from polling buffer");
	    assert polled_cmd != null;
	    return polled_cmd.get_last_cmd_result();
	} else {
	    if (polling_failed == false) {
		Util.out4.println("Device_2Impl: Returning data from polling buffer");
		return polled_cmd.get_last_cmd_result();
	    } else {
		return command_inout(in_cmd, in_data);
	    }
	}

    }

    // +-------------------------------------------------------------------------
    //
    // method : Device_2Impl.command_inout_history_2
    // 
    // description : CORBA operation to read command result history from
    // the polling buffer.
    //
    // argument: in : - command : command name
    // - n : history depth (in record number)
    //
    // This method returns a pointer to a DevCmdHistoryList with one
    // DevCmdHistory structure for each command record
    //
    // --------------------------------------------------------------------------
    public DevCmdHistory[] command_inout_history_2(final String command, int n) throws DevFailed,
	    SystemException {
	Util.out4.println("Device_2Impl.command_inout_history_2 arrived");

	final String cmd_str = command.toLowerCase();

	// Record operation request in black box
	blackbox.insert_op(Op_Command_inout_history_2);

	// Check that device supports this command
	check_command_exists(cmd_str);

	// Check that the command is polled
	PollObj polled_cmd = null;
	final Vector poll_list = get_poll_obj_list();
	for (int i = 0; i < poll_list.size(); i++) {
	    final PollObj poll_obj = (PollObj) poll_list.elementAt(i);
	    if (poll_obj.get_type() == Tango_POLL_CMD && poll_obj.get_name().equals(cmd_str)) {
		polled_cmd = poll_obj;
	    }
	}
	if (polled_cmd == null) // NOT found
	{
	    Except.throw_exception("API_CmdNotPolled", "Command " + cmd_str + " not polled",
		    "Device_2Impl.command_inout_history_2");
	}

	// Check that some data is available in cache

	assert polled_cmd != null;
	if (polled_cmd.is_ring_empty()) {
	    Except.throw_exception("API_NoDataYet", "No data available in cache for command "
		    + cmd_str, "Device_2Impl.command_inout_history_2");
	}
	// Set the number of returned records
	final int in_buf = polled_cmd.get_elt_nb_in_buffer();
	if (n > in_buf) {
	    n = in_buf;
	}

	// return command result history
	return polled_cmd.get_cmd_history(n);
    }

    // +-------------------------------------------------------------------------
    //
    // method : name
    // 
    // description : Method called when a client request the name attribute
    // This method is called for a IDL attribute which can
    // not throw exception to client
    //
    // --------------------------------------------------------------------------

    /**
     * Get device name.
     * 
     * It's the master method executed when the device name is requested via a
     * CORBA attribute. It updates the device black-box and return the device
     * name
     * 
     * @return The device name
     */

    public String name() {
	Util.out4.println("DeviceImpl.name() arrived");

	//
	// Record attribute request in black box
	//

	blackbox.insert_attr(Attr_Name);

	//
	// Return data to caller
	//

	Util.out4.println("Leaving DeviceImpl.name()");
	return device_name;
    }

    // +-------------------------------------------------------------------------
    //
    // method : adm_name
    // 
    // description : Method called when a client request the adm_name attribute
    // This method is called for a IDL attribute which can
    // not throw exception to client
    //
    // --------------------------------------------------------------------------

    /**
     * Get administration device name.
     * 
     * It's the master method executed when the administration device name is
     * requested via a CORBA attribute. It updates the device black-box and
     * return the administration device name
     * 
     * @return The administration device name
     */

    public String adm_name() {
	Util.out4.println("DeviceImpl.adm_name() arrived");

	//
	// Record attribute request in black box
	//

	blackbox.insert_attr(Attr_AdmName);

	//
	// Return data to caller
	//

	Util.out4.println("Leaving DeviceImpl.adm_name()");
	return adm_device_name;
    }

    // +-------------------------------------------------------------------------
    //
    // method : description
    // 
    // description : Method called when a client request the description
    // attribute
    // This method is called for a IDL attribute which can
    // not throw exception to client
    //
    // --------------------------------------------------------------------------

    /**
     * Get device description.
     * 
     * It's the master method executed when the device description is requested
     * via a CORBA attribute. It updates the device black-box and return the
     * device description field
     * 
     * @return The device description
     */

    public String description() {
	Util.out4.println("DeviceImpl.description() arrived");

	//
	// Record attribute request in black box
	//

	blackbox.insert_attr(Attr_Description);

	//
	// Return data to caller
	//

	Util.out4.println("Leaving DeviceImpl.description()");
	return desc;
    }

    // +-------------------------------------------------------------------------
    //
    // method : state
    // 
    // description : Method called when a client request the state
    // attribute
    //
    // --------------------------------------------------------------------------

    /**
     * Get device state.
     * 
     * It's the master method executed when the device state is requested via a
     * CORBA attribute. It updates the device black-box and return the device
     * state. This method calls the <i>dev_state</i> device method but catch all
     * the execption and does not re-throw them because exception can't be
     * thrown to a client for CORBA attribute
     * 
     * @return The device state
     */

    public DevState state() {
	Util.out4.println("DeviceImpl.state() (attribute) arrived");

	//
	// Record attribute request in black box
	//

	blackbox.insert_attr(Attr_State);

	//
	// Return data to caller. If the dev_state throw an exception, catch it
	// and do
	// not re-throw it because we are in a CORBA attribute implementation
	//

	DevState tmp = null;
	try {
	    tmp = dev_state();
	} catch (final DevFailed ex) {
	}

	Util.out4.println("Leaving DeviceImpl.state() (attribute)");
	return tmp;
    }

    // +-------------------------------------------------------------------------
    //
    // method : status
    // 
    // description : Method called when a client request the description
    // status
    // This method is called for a IDL attribute which can
    // not throw exception to client
    //
    // --------------------------------------------------------------------------

    /**
     * Get device status.
     * 
     * It's the master method executed when the device status is requested via a
     * CORBA attribute. It updates the device black-box and return the device
     * state. This method calls the <i>dev_status</i> device method but catch
     * all the execption and does not re-throw them because exception can't be
     * thrown to a client for CORBA attribute
     * 
     * @return The device status
     */

    public String status() {
	Util.out4.println("DeviceImpl.status() (attibute) arrived");

	//
	// Record attribute request in black box
	//

	blackbox.insert_attr(Attr_Status);

	//
	// Return data to caller. If the dev_status method throw exception,
	// catch it
	// and forget it because we are in a CORBA attribute implementation
	//

	String tmp = null;
	try {
	    tmp = dev_status();
	} catch (final DevFailed ex) {
	}

	Util.out4.println("Leaving DeviceImpl.status() (attribute)");
	return tmp;
    }

    // +-------------------------------------------------------------------------
    //
    // method : black_box
    // 
    // description : CORBA operation to read n element(s) of the black-box.
    // This method returns black box element as strings
    //
    // argument: in : - n : Number of elt to read
    //
    // --------------------------------------------------------------------------

    /**
     * Get device black box.
     * 
     * It's the master method executed when the device black box is requested.
     * It reads the device black box, update it and return black-box data to the
     * client
     * 
     * @param n
     *            The number of actions description which must be returned to
     *            the client. The number of returned element is limited to the
     *            number of elements stored in the black-box or to the complete
     *            black-box depth if it is full.
     * @return The device black box with one String for each action requested on
     *         the device
     * @exception DevFailed
     *                If it is not possible to read the device black box. Click
     *                <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed">here
     *                </a> to read <b>DevFailed</b> exception specification
     */

    public String[] black_box(final int n) throws DevFailed {

	Util.out4.println("DeviceImpl.black_box() arrived");

	final String[] ret = blackbox.read(n);

	//
	// Record operation request in black box
	//

	blackbox.insert_op(Op_BlackBox);

	Util.out4.println("Leaving DeviceImpl.black_box()");
	return ret;
    }

    // +-------------------------------------------------------------------------
    //
    // method : command_list_query
    // 
    // description : CORBA operation to read the device command list.
    // This method returns command info in a sequence of
    // DevCmdInfo
    //
    //
    // --------------------------------------------------------------------------

    /**
     * Get device command list.
     * 
     * Invoked when the client request the command_list_query CORBA operation.
     * It updates the device black box and returns an array of DevCmdInfo object
     * with one object for each command.
     * 
     * @return The device command list. One DevCmdInfo is initialised for each
     *         device command
     */

    public DevCmdInfo[] command_list_query() {

	Util.out4.println("DeviceImpl.command_list_query() arrived");

	//
	// Retrive number of command and allocate memory to send back info
	//

	final int nb_cmd = device_class.get_command_list().size();
	Util.out4.println(nb_cmd + " command(s) for device");
	final DevCmdInfo[] back = new DevCmdInfo[nb_cmd];
	for (int loop = 0; loop < nb_cmd; loop++) {
	    back[loop] = new DevCmdInfo();
	}

	//
	// Populate the sequence
	//

	for (int i = 0; i < nb_cmd; i++) {
	    back[i].cmd_name = ((Command) device_class.get_command_list().elementAt(i)).get_name();
	    back[i].cmd_tag = ((Command) device_class.get_command_list().elementAt(i)).get_tag();
	    back[i].in_type = ((Command) device_class.get_command_list().elementAt(i))
		    .get_in_type();
	    back[i].out_type = ((Command) device_class.get_command_list().elementAt(i))
		    .get_out_type();
	    String tmp_desc = ((Command) device_class.get_command_list().elementAt(i))
		    .get_in_type_desc();
	    if (tmp_desc == null) {
		back[i].in_type_desc = Tango_DescNotSet;
	    } else {
		back[i].in_type_desc = tmp_desc;
	    }
	    tmp_desc = ((Command) device_class.get_command_list().elementAt(i)).get_out_type_desc();
	    if (tmp_desc == null) {
		back[i].out_type_desc = Tango_DescNotSet;
	    } else {
		back[i].out_type_desc = tmp_desc;
	    }
	}

	//
	// Record operation request in black box
	//

	blackbox.insert_op(Op_Command_list);

	//
	// Return to caller
	//

	Util.out4.println("Leaving DeviceImpl.command_list_query()");
	return back;
    }

    // ===========================================================================
    /**
     * Get device command list.
     * 
     * Invoked when the client request the command_list_query CORBA operation.
     * It updates the device black box and returns an array of DevCmdInfo object
     * with one object for each command.
     * 
     * @return The device command list. One DevCmdInfo is initialised for each
     *         device command
     */
    // ===========================================================================
    public DevCmdInfo_2[] command_list_query_2() {
	Util.out4.println("Device_2Impl.command_list_query_2 arrived");

	// Retrieve number of command and allocate memory to send back info
	final Vector cmd_list = device_class.get_command_list();
	Util.out4.println(cmd_list.size() + " command(s) for device");
	final DevCmdInfo_2[] back = new DevCmdInfo_2[cmd_list.size()];

	for (int i = 0; i < cmd_list.size(); i++) {
	    final Command cmd = (Command) cmd_list.elementAt(i);
	    final DevCmdInfo_2 tmp = new DevCmdInfo_2();
	    tmp.cmd_name = cmd.get_name();
	    tmp.cmd_tag = cmd.get_tag();
	    tmp.level = cmd.get_disp_level();
	    tmp.in_type = cmd.get_in_type();
	    tmp.out_type = cmd.get_out_type();
	    final String str_in = cmd.get_in_type_desc();
	    if (str_in != null) {
		tmp.in_type_desc = str_in;
	    } else {
		tmp.in_type_desc = Tango_DescNotSet;
	    }
	    final String str_out = cmd.get_out_type_desc();
	    if (str_out != null) {
		tmp.out_type_desc = str_out;
	    } else {
		tmp.out_type_desc = Tango_DescNotSet;
	    }
	    back[i] = tmp;
	}
	// Record operation request in black box
	blackbox.insert_op(Op_Command_list_2);

	Util.out4.println("Device_2Impl.command_list_query_2 exiting");
	return back;

    }

    // ===========================================================================
    /**
     * Get device command list.
     * 
     * Invoked when the client request the command_list_query CORBA operation.
     * It updates the device black box and returns an array of DevCmdInfo object
     * with one object for specified command.
     * 
     * @param command
     *            The specified command name.
     * @return The device command list. One DevCmdInfo is initialised for each
     *         device command
     */
    // ===========================================================================
    public DevCmdInfo_2 command_query_2(final String command) throws DevFailed, SystemException {
	Util.out4.println("Device_2Impl.command_query_2(" + command + ") arrived");

	// Retrieve number of command and allocate memory to send back info
	final int nb_cmd = device_class.get_command_list().size();
	Util.out4.println(nb_cmd + " command(s) for device");
	DevCmdInfo_2 back = null;
	for (int i = 0; i < nb_cmd; i++) {
	    final Command cmd = (Command) device_class.get_command_list().elementAt(i);
	    if (cmd.get_name().toLowerCase().equals(command.toLowerCase())) {
		final DevCmdInfo_2 tmp = new DevCmdInfo_2();
		tmp.cmd_name = cmd.get_name();
		tmp.cmd_tag = cmd.get_tag();
		tmp.level = cmd.get_disp_level();
		tmp.in_type = cmd.get_in_type();
		tmp.out_type = cmd.get_out_type();
		final String str_in = cmd.get_in_type_desc();
		if (str_in.length() != 0) {
		    tmp.in_type_desc = str_in;
		} else {
		    tmp.in_type_desc = Tango_DescNotSet;
		}
		final String str_out = cmd.get_out_type_desc();
		if (str_out.length() != 0) {
		    tmp.out_type_desc = str_out;
		} else {
		    tmp.out_type_desc = Tango_DescNotSet;
		}
		back = tmp;
	    }
	}

	// throw an exception to client if command not found
	if (back == null) {
	    Except.throw_exception("API_CommandNotFound", "Command " + command + " not found",
		    "Device_2Impl.command_query_2");
	}

	// Record operation request in black box
	blackbox.insert_op(Op_Command_list_2);

	Util.out4.println("Device_2Impl.command_list_query_2 exiting");
	return back;

    }

    // +-------------------------------------------------------------------------
    //
    // method : command_query
    // 
    // description : CORBA operation to read a device command info.
    // This method returns command info for a specific
    // command.
    //
    //
    // --------------------------------------------------------------------------

    /**
     * Get command info.
     * 
     * Invoked when the client request the command_query CORBA operation. It
     * updates the device black box and returns a DevCmdInfo object for the
     * command with name passed to the method as parameter.
     * 
     * @param command
     *            The command name
     * @return A DevCmdInfo initialised for the wanted command
     * @exception DevFailed
     *                Thrown if the command does not exist. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */

    public DevCmdInfo command_query(final String command) throws DevFailed {

	Util.out4.println("DeviceImpl.command_query() arrived");

	final DevCmdInfo back = new DevCmdInfo();

	//
	// Try to retrieve the command in the command list
	//
	final String cmd_name = command.toLowerCase();
	int i;
	final int nb_cmd = device_class.get_command_list().size();
	for (i = 0; i < nb_cmd; i++) {
	    final Command cmd = (Command) device_class.get_command_list().elementAt(i);
	    if (cmd.get_name().toLowerCase().equals(cmd_name) == true) {
		back.cmd_name = command;
		back.cmd_tag = cmd.get_tag();
		back.in_type = cmd.get_in_type();
		back.out_type = cmd.get_out_type();
		String tmp_desc = cmd.get_in_type_desc();
		if (tmp_desc == null) {
		    back.in_type_desc = Tango_DescNotSet;
		} else {
		    back.in_type_desc = tmp_desc;
		}
		tmp_desc = cmd.get_out_type_desc();
		if (tmp_desc == null) {
		    back.out_type_desc = Tango_DescNotSet;
		} else {
		    back.out_type_desc = tmp_desc;
		}
		break;
	    }
	}

	if (i == nb_cmd) {
	    Util.out3.println("DeviceImpl.command_query(): operation " + command + " not found");

	    //		
	    // throw an exception to client
	    //
	    Except.throw_exception("API_CommandNotFound", "Command " + command + " not found",
		    "DeviceImpl.command_query()");
	}

	//
	// Record operation request in black box
	//

	blackbox.insert_op(Op_Command);

	//
	// Return to caller
	//

	Util.out4.println("Leaving DeviceImpl.command_query()");
	return back;
    }

    // +-------------------------------------------------------------------------
    //
    // method : info
    // 
    // description : CORBA operation to get device info
    //
    // --------------------------------------------------------------------------

    /**
     * Get device info.
     * 
     * Invoked when the client request the info CORBA operation. It updates the
     * black box and returns a DevInfo object with miscellaneous device info
     * 
     * @return A DevInfo object
     */

    public DevInfo info() {

	Util.out4.println("DeviceImpl.info() arrived");

	final DevInfo back = new DevInfo();

	//
	// Retrieve server host
	//

	final Util tg = Util.instance();
	back.server_host = tg.get_host_name();

	//
	// Fill-in remaining structure fields
	//

	back.dev_class = device_class.get_name();
	back.server_id = tg.get_ds_real_name();
	back.server_version = Tango_DevVersion;
	back.doc_url = device_class.get_doc_url();

	//
	// Record operation request in black box
	//

	blackbox.insert_op(Op_Info);

	//
	// Return to caller
	//

	Util.out4.println("Leaving DeviceImpl.info()");
	return back;
    }

    // +-------------------------------------------------------------------------
    //
    // method : ping
    // 
    // description : CORBA operation to ping if a device to see it is alive
    //
    // --------------------------------------------------------------------------

    /**
     * Ping the device to check if it is still alive.
     * 
     * Invoked when the client request the ping CORBA operation. It updates the
     * device black box and simply returns
     * 
     */

    public void ping() {

	Util.out4.println("DeviceImpl.ping() arrived");

	//
	// Record operation request in black box
	//

	blackbox.insert_op(Op_Ping);

	//
	// Return to caller
	//

	Util.out4.println("Leaving DeviceImpl.ping()");
    }

    // +-------------------------------------------------------------------------
    //
    // method : get_attribute_config
    // 
    // description : CORBA operation to get attribute configuration.
    //
    // argument: in : - names: name of attribute(s)
    //
    // This method returns a reference to a AttributeConfig array with one
    // AttributeConfig
    // object for each attribute
    //
    // --------------------------------------------------------------------------
    /**
     * Get attribute(s) configuration.
     * 
     * Invoked when the client request the get_attribute_config CORBA operation.
     * It retrieves attribute(s) configuration and initialise a AttributeConfig
     * object for each of the wanted attribute.
     * 
     * @param names
     *            The attribute name list
     * @return An array of AttributeConfig object. One AttributeConfig object is
     *         initialised for each wanted attribute
     * @exception DevFailed
     *                Thrown if one of the attribute does not exist. Click <a
     *                href
     *                ="../../tango_basic/idl_html/Tango.html#DevFailed">here
     *                </a> to read <b>DevFailed</b> exception specification
     */
    public AttributeConfig[] get_attribute_config(final String[] names) throws DevFailed {
	Util.out4.println("DeviceImpl.get_attribute_config arrived");

	int nb_attr = names.length;
	boolean all_attr = false;

	//
	// Record operation request in black box
	//

	blackbox.insert_op(Op_Get_Attr_Config);

	//
	// Return exception if the device does not have any attribute
	//

	final int nb_dev_attr = dev_attr.get_attr_nb();
	if (nb_dev_attr == 0) {
	    Except.throw_exception("API_AttrNotFound", "The device does not have any attribute",
		    "DeviceImpl.get_attribute_config");
	}

	//
	// Check if the caller want to get config for all attribute
	//

	if (nb_attr == 1 && names[0].equals(Tango_AllAttr)) {
	    nb_attr = nb_dev_attr;
	    all_attr = true;
	}

	//
	// Allocate memory for the AttributeConfig objects
	//

	AttributeConfig[] back = null;
	// noinspection ErrorNotRethrown
	try {
	    back = new AttributeConfig[nb_attr];
	    for (int i = 0; i < nb_attr; i++) {
		back[i] = new AttributeConfig();
	    }
	} catch (final OutOfMemoryError ex) {
	    Util.out3.println("Bad allocation while in DeviceImpl.get_attribute_config");
	    Except.throw_exception("API_MemoryAllocation", "Can't allocate memory in server",
		    "DeviceImpl.get_attribute_config");
	}

	//
	// Fill in these objects
	//

	for (int i = 0; i < nb_attr; i++) {
	    if (all_attr == true) {
		final Attribute attr = dev_attr.get_attr_by_ind(i);
		attr.get_properties(back[i]);
	    } else {
		final Attribute attr = dev_attr.get_attr_by_name(names[i]);
		attr.get_properties(back[i]);
	    }
	}

	//
	// Return to caller
	//

	Util.out4.println("Leaving DeviceImpl.get_attribute_config");

	return back;
    }

    // +-------------------------------------------------------------------------
    //
    // method : Device_2Impl.get_attribute_config_2
    // 
    // description : CORBA operation to get attribute configuration.
    //
    // argument: in : - names: name of attribute(s)
    //
    // This method returns a pointer to a AttributeConfigList_2 with one
    // AttributeConfig_2 structure for each atribute
    //
    //
    // WARNING !!!!!!!!!!!!!!!!!!
    //
    // This is the release 2 of this CORBA operation which
    // returns one more parameter than in release 1
    // The code has been duplicated in order to keep it clean
    // (avoid many "if" on version number in a common method)
    //
    // --------------------------------------------------------------------------
    public AttributeConfig_2[] get_attribute_config_2(final String[] names) throws DevFailed,
	    SystemException {
	Util.out4.println("Device_2Impl.get_attribute_config_2 arrived");

	// Allocate memory for the AttributeConfig structures
	int nb_attr = names.length;
	boolean all_attr = false;

	// Record operation request in black box
	blackbox.insert_op(Op_Get_Attr_Config_2);

	// Get attribute number
	final int nb_dev_attr = dev_attr.get_attr_nb();

	// Check if the caller want to get config for all attribute
	final String in_name = names[0];
	if (nb_attr == 1 && in_name.equals(Tango_AllAttr)) {
	    nb_attr = nb_dev_attr;
	    all_attr = true;
	}

	final AttributeConfig_2[] back = new AttributeConfig_2[nb_attr];
	// Fill in these structures
	for (int i = 0; i < nb_attr; i++) {
	    if (all_attr == true) {
		final Attribute attr = dev_attr.get_attr_by_ind(i);
		back[i] = attr.get_properties_2();
	    } else {
		final Attribute attr = dev_attr.get_attr_by_name(names[i]);
		back[i] = attr.get_properties_2();
	    }
	}

	Util.out4.println("Leaving Device_2Impl.get_attribute_config_2");

	return back;
    }

    // +-------------------------------------------------------------------------
    //
    // method : set_attribute_config
    // 
    // description : CORBA operation to set attribute configuration locally
    // and in the Tango database
    //
    // argument: in : - new_conf: The new attribute(s) configuration. One
    // AttributeConfig object is needed for each
    // attribute to update
    //
    // --------------------------------------------------------------------------
    /**
     * Set attribute(s) configuration.
     * 
     * Invoked when the client request the set_attribute_config CORBA operation.
     * This method update the attribute configuration and also update the TANGO
     * database with the new value. This method allows to update only the
     * optional attribute properties.
     * 
     * @param new_conf
     *            The attribute(s) new configuration list. There is one
     *            AttributeConfig object for each attribute with configuration
     *            to be updated
     * @exception DevFailed
     *                Thrown if one of the attribute does not exist. Click <a
     *                href
     *                ="../../tango_basic/idl_html/Tango.html#DevFailed">here
     *                </a> to read <b>DevFailed</b> exception specification
     */
    public void set_attribute_config(final AttributeConfig new_conf[]) throws DevFailed {
	Util.out4.println("DeviceImpl.set_attribute_config arrived");

	//
	// Record operation request in black box
	//

	blackbox.insert_op(Op_Set_Attr_Config);

	//
	// Return exception if the device does not have any attribute
	//

	final int nb_dev_attr = dev_attr.get_attr_nb();
	if (nb_dev_attr == 0) {
	    Except.throw_exception("API_AttrNotFound", "The device does not have any attribute",
		    "DeviceImpl.set_attribute_config");
	}

	//
	// Update attribute config first in database, then locally
	//

	final int nb_attr = new_conf.length;
	int i = 0;

	try {
	    for (i = 0; i < nb_attr; i++) {
		final Attribute attr = dev_attr.get_attr_by_name(new_conf[i].name);
		if (Util._UseDb == true) {
		    attr.upd_database(new_conf[i], device_name);
		}
		attr.set_properties(new_conf[i], device_name);
	    }

	} catch (final DevFailed e) {

	    //
	    // Re build the list of "alarmable" attribute
	    //

	    dev_attr.get_alarm_list().removeAllElements();
	    for (int j = 0; j < nb_dev_attr; j++) {
		if (dev_attr.get_attr_by_ind(j).is_alarmed() == true) {
		    dev_attr.get_alarm_list().addElement(j);
		}
	    }

	    //
	    // Change the exception reason flag
	    //

	    final StringBuffer o = new StringBuffer(e.errors[0].reason);

	    if (i != 0) {
		o.append("\nAll previous attribute(s) have been successfully updated");
	    }
	    if (i != nb_attr - 1) {
		o.append("\nAll remaining attribute(s) have not been updated");
	    }

	    e.errors[0].reason = o.toString();
	    throw e;
	}

	//
	// Re build the list of "alarmable" attribute
	//

	dev_attr.get_alarm_list().removeAllElements();
	for (i = 0; i < nb_dev_attr; i++) {
	    if (dev_attr.get_attr_by_ind(i).is_alarmed()) {
		dev_attr.get_alarm_list().addElement(i);
	    }
	}

	//
	// Return to caller
	//

	Util.out4.println("Leaving DeviceImpl.set_attribute_config");
    }

    // +-------------------------------------------------------------------------
    //
    // method : read_attributes
    // 
    // description : CORBA operation to read attribute(s) value.
    //
    // argument: in : - names: name of attribute(s) to be read
    //
    // This method returns a pointer to a AttributeConfigList with one
    // AttributeValue
    // structure for each atribute. This structure contains the attribute value,
    // the
    // value quality and the date.
    //
    // --------------------------------------------------------------------------
    /**
     * Read attribute(s) value.
     * 
     * Invoked when the client request the read_attributes CORBA operation. This
     * method read attributes value and return them to the caller. A date and a
     * quality factor is added to each attribute.
     * 
     * @param names
     *            The attribute name list
     * @return An array of AttributeValue object. One AttributeValue object is
     *         initialised for each wanted attribute
     * @exception DevFailed
     *                Thrown if one of the attribute does not exist or if a
     *                problem occurs during attribute reading. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */
    // =========================================================================
    public AttributeValue[] read_attributes(final String[] names) throws DevFailed {
	Util.out4.println("DeviceImpl.read_attributes arrived" + names[0]);

	// Record operation request in black box
	blackbox.insert_op(Op_Read_Attr);

	// Return exception if the device does not have any attribute
	if (dev_attr.get_attr_nb() == 0) {
	    Except.throw_exception("API_AttrNotFound", "The device does not have any attribute",
		    "DeviceImpl.read_attributes");
	}

	// Build a sequence with the names of the attribute to be read.
	// This is necessary in case of the "AllAttr" shortcut is used
	// If all attributes are wanted, build this list
	final String[] real_names = checkRealNames(names);

	// If nb connection is closed to the maximum value,
	// throw an exception to do not block devices.
	Util.increaseAccessConter();
	if (Util.getAccessConter() > Util.getPoaThreadPoolMax() - 2) {
	    Util.decreaseAccessConter();
	    Except.throw_exception("API_MemoryAllocation", Util.instance().get_ds_real_name()
		    + ": No thread available to connect device", "DeviceImpl.read_attributes()");
	}

	final Vector wanted_attr = new Vector();
	final Vector wanted_w_attr = new Vector();
	AttributeValue[] back = null;
	try {
	    switch (Util.get_serial_model()) {
	    case BY_CLASS:
		synchronized (device_class) {
		    sortAttributes(real_names, wanted_attr, wanted_w_attr);
		    back = manageReadAttrinbute(real_names, wanted_attr, wanted_w_attr);
		}
		break;

	    case BY_DEVICE:
		synchronized (this) {
		    sortAttributes(real_names, wanted_attr, wanted_w_attr);
		    back = manageReadAttrinbute(real_names, wanted_attr, wanted_w_attr);
		}
		break;

	    default: // NO_SYNC
		synchronized (this) {
		    sortAttributes(real_names, wanted_attr, wanted_w_attr);
		}
		back = manageReadAttrinbute(real_names, wanted_attr, wanted_w_attr);
	    }
	} // End of try

	catch (final DevFailed exc) {
	    Util.decreaseAccessConter();
	    setNotActive(real_names);
	    throw exc;
	} catch (final Exception exc) {
	    Util.decreaseAccessConter();
	    setNotActive(real_names);
	    exc.printStackTrace();
	    Except.throw_exception("API_ExceptionCatched", exc.toString(),
		    "DeviceImpl.read_attributes");
	}

	Util.decreaseAccessConter();
	setNotActive(real_names);

	// Return to caller
	Util.out4.println("Leaving DeviceImpl.read_attributes");
	return back;
    }

    // =========================================================================
    /**
     * Check if value has been inserted and retuens the AttributeValue objects
     */
    // =========================================================================
    private AttributeValue[] manageReadAttrinbute(final String[] real_names,
	    final Vector wanted_attr, final Vector wanted_w_attr) throws DevFailed {
	// Call the always_executed_hook method before
	always_executed_hook();

	// Read the hardware for readable attribute
	if (wanted_attr.size() != 0) {
	    read_attr_hardware(wanted_attr);
	}

	// Set attr value (for readable attribute)
	for (int i = 0; i < wanted_attr.size(); i++) {
	    final int idx = ((Integer) wanted_attr.elementAt(i)).intValue();
	    read_attr(dev_attr.get_attr_by_ind(idx));
	}
	// Set attr value for writable attribute
	for (int i = 0; i < wanted_w_attr.size(); i++) {
	    final int idx = ((Integer) wanted_w_attr.elementAt(i)).intValue();
	    final AttrWriteType w_type = dev_attr.get_attr_by_ind(idx).get_writable();
	    if (w_type == AttrWriteType.READ_WITH_WRITE || w_type == AttrWriteType.WRITE) {
		dev_attr.get_attr_by_ind(idx).set_value();
	    }
	}

	// Allocate memory for the AttributeValue structures and fill it
	return buildAttributeValue(real_names);
    }

    // =========================================================================
    /**
     * Check if value has been inserted and retuens the AttributeValue objects
     */
    // =========================================================================
    private AttributeValue[] buildAttributeValue(final String[] real_names) throws DevFailed {
	// Allocate memory for the AttributeValue structures
	AttributeValue[] back = new AttributeValue[0];
	// noinspection ErrorNotRethrown
	try {
	    back = new AttributeValue[real_names.length];
	    for (int i = 0; i < real_names.length; i++) {
		back[i] = new AttributeValue();
	    }
	} catch (final OutOfMemoryError ex) {
	    Except.throw_exception("API_MemoryAllocation", "Can't allocate memory in server",
		    "DeviceImpl.read_attributes");
	}

	// Build the sequence returned to caller for readable attributes and
	// check
	// that all the wanted attributes set value has been updated
	for (int i = 0; i < real_names.length; i++) {
	    final Attribute att = dev_attr.get_attr_by_name(real_names[i]);
	    if (att.get_value_flag()) {
		final AttrWriteType w_type = att.get_writable();
		if (w_type == AttrWriteType.READ || w_type == AttrWriteType.READ_WRITE
			|| w_type == AttrWriteType.READ_WITH_WRITE) {
		    if (w_type == AttrWriteType.READ_WRITE
			    || w_type == AttrWriteType.READ_WITH_WRITE) {
			dev_attr.add_write_value(att);
		    }

		    if (att.is_alarmed() == true
			    && att.get_quality().value() != AttrQuality._ATTR_INVALID) {
			att.check_alarm();
		    }
		}
		back[i].time = att.get_when();
		back[i].quality = att.get_quality();
		back[i].name = att.get_name();
		back[i].dim_x = att.get_x();
		back[i].dim_y = att.get_y();

		final Util tg = Util.instance();
		back[i].value = tg.get_orb().create_any();

		switch (att.get_data_type()) {
		case Tango_DEV_BOOLEAN:
		    DevVarBooleanArrayHelper.insert(back[i].value, att.get_boolean_value());
		    break;

		case Tango_DEV_SHORT:
		    DevVarShortArrayHelper.insert(back[i].value, att.get_short_value());
		    break;

		case Tango_DEV_USHORT:
		    DevVarUShortArrayHelper.insert(back[i].value, att.get_short_value());
		    break;

		case Tango_DEV_LONG:
		    DevVarLongArrayHelper.insert(back[i].value, att.get_long_value());
		    break;

		case Tango_DEV_ULONG:
		    DevVarULongArrayHelper.insert(back[i].value, att.get_long_value());
		    break;

		case Tango_DEV_LONG64:
		    DevVarLong64ArrayHelper.insert(back[i].value, att.get_long64_value());
		    break;

		case Tango_DEV_ULONG64:
		    DevVarULong64ArrayHelper.insert(back[i].value, att.get_long64_value());
		    break;

		case Tango_DEV_DOUBLE:
		    DevVarDoubleArrayHelper.insert(back[i].value, att.get_double_value());
		    break;

		case Tango_DEV_STRING:
		    DevVarStringArrayHelper.insert(back[i].value, att.get_string_value());
		    break;
		case Tango_DEV_STATE:
		    DevVarStateArrayHelper.insert(back[i].value, att.get_state_value());
		    break;

		/*
		 * //======================================================== //
		 * Not possible (DevEncoded type is not transfered inside an
		 * any)
		 * //========================================================
		 * case Tango_DEV_ENCODED :
		 * DevEncodedHelper.insert(back[i].value,att.get_enc_value());
		 * break;
		 */
		default:
		    Except.throw_exception("API_DatTypeNotSupported", "Attribute " + att.get_name()
			    + " data type not supported !", "Device_2Impl.read_attributes()");
		}
	    } else {
		Except.throw_exception("API_AttrValueNotSet", "Read value for attribute "
			+ att.get_name() + " has not been updated", "DeviceImpl.read_attributes");
	    }
	} // End of for ( ; ; )
	return back;
    }

    // =========================================================================
    /**
     * Retrieve wanted attributes in the device attribute list and check (and
     * set) their active flag, before clearing their value set flag
     */
    // =========================================================================
    private void sortAttributes(final String[] names, final Vector wanted_attr,
	    final Vector wanted_w_attr) throws DevFailed {
	for (final String name : names) {
	    final Attribute att = dev_attr.get_attr_by_name(name);
	    final int att_idx = dev_attr.get_attr_ind_by_name(name);

	    if (att.get_writable() == AttrWriteType.READ_WRITE) {
		wanted_w_attr.addElement(att_idx);
		wanted_attr.addElement(att_idx);
		att.waitEndOfRead(); // Wait a while if already reading
	    } else if (att.get_writable() == AttrWriteType.WRITE) {
		wanted_w_attr.addElement(att_idx);
	    } else {
		// READ or READ_WITH_WRITE
		wanted_attr.addElement(att_idx);
		att.waitEndOfRead(); // Wait a while if already reading
	    }
	}
    }

    // =========================================================================
    /**
     * Build a sequence with the names of the attribute to be read. This is
     * necessary in case of the "AllAttr" shortcut is used If all attributes are
     * wanted, build this list
     */
    // =========================================================================
    private String[] checkRealNames(final String[] names) {
	if (names.length == 1) {
	    if (names[0].equals(Tango_AllAttr)) {
		final String[] real_names = new String[dev_attr.get_attr_nb()];
		for (int i = 0; i < dev_attr.get_attr_nb(); i++) {
		    real_names[i] = dev_attr.get_attr_by_ind(i).get_name();
		}
		return real_names;
	    } else {
		return names;
	    }
	} else {
	    return names;
	}
    }

    // =========================================================================
    // =========================================================================
    private void setNotActive(final String[] names) throws DevFailed {
	for (final String name : names) {
	    dev_attr.get_attr_by_name(name).setActive(false);
	}
    }

    // =========================================================================
    /**
     * Read attribute(s) value.
     * 
     * Invoked when the client request the read_attributes CORBA operation with
     * a DevSource parameter. This method read attributes value and return them
     * to the caller. A date and a quality factor is added to each attribute.
     * 
     * @param names
     *            The attribute name list
     * @param source
     *            could be DevSource.CACHE, DevSource.CACHE_DEV or
     *            DevSource.DEVICE.
     * @return An array of AttributeValue object. One AttributeValue object is
     *         initialised for each wanted attribute
     * @exception DevFailed
     *                Thrown if one of the attribute does not exist or if a
     *                problem occurs during attribute reading. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */
    // =========================================================================
    public AttributeValue[] read_attributes_2(final String[] names, final DevSource source)
	    throws DevFailed {
	Util.out4.println("DeviceImpl.read_attributes_2 arrived src=" + source.value());

	// If the source parameter specifies device, call the read_attribute
	// method implemented by the DeviceImpl class
	if (source == DevSource.DEV) {
	    return read_attributes(names);
	}

	// final boolean att_in_fault = false;
	boolean polling_failed = false;

	// Record operation request in black box
	blackbox.insert_op(Op_Read_Attr_2);

	AttributeValue[] back = null;

	try {
	    // Build a sequence with the names of the attribute to be read.
	    // This is necessary in case of the "AllAttr" shortcut is used
	    // If all attributes are wanted, build this list
	    int i, j;
	    boolean all_attr = false;
	    final Vector poll_list = get_poll_obj_list(); // PollObj
	    final int nb_poll = poll_list.size();
	    final int nb_names = names.length;
	    String[] real_names = names;
	    // Check if All Attr
	    if (nb_names == 1) {
		final String att_name = names[0];
		if (att_name.equals(Tango_AllAttr)) {
		    all_attr = true;
		    int nb_real = 0;
		    // Get the real number
		    for (i = 0; i < nb_poll; i++) {
			final PollObj poll_obj = (PollObj) poll_list.elementAt(i);
			if (poll_obj.get_type() == Tango_POLL_ATTR) {
			    nb_real++;
			}
		    }
		    // get the real polle object
		    real_names = new String[nb_real];
		    for (i = 0, j = 0; i < nb_poll && j < nb_real; i++) {
			final PollObj poll_obj = (PollObj) poll_list.elementAt(i);
			if (poll_obj.get_type() == Tango_POLL_ATTR) {
			    real_names[j++] = poll_obj.get_name();
			}
		    }
		}
	    }

	    // Check that device supports the wanted attribute
	    if (all_attr == false) {
		for (i = 0; i < real_names.length; i++) {
		    dev_attr.get_attr_ind_by_name(real_names[i]);
		}
	    }
	    // Check that all wanted attributes are polled.
	    // If some are non polled, store
	    // their name in the real_names sequence in a vector
	    final Vector non_polled = new Vector(); // String
	    if (all_attr == false) {
		for (i = 0; i < real_names.length; i++) {
		    for (j = 0; j < nb_poll; j++) {
			final PollObj poll_obj = (PollObj) poll_list.elementAt(j);
			if (poll_obj.get_name().equals(real_names[i].toLowerCase())) {
			    break;
			}
		    }
		    if (j == nb_poll) {
			non_polled.add(real_names[i]);
		    }
		}
	    }
	    // If some attributes are not polled but their polling update period
	    // is defined,
	    // and the attribute is not in the device list of attr which should
	    // not be
	    // polled, start to poll them
	    final Vector poll_period = new Vector();
	    if (non_polled.size() != 0) {
		// Check that it is possible to start polling for the non polled
		// attribute
		for (i = 0; i < non_polled.size(); i++) {
		    final String non_polled_name = (String) non_polled.elementAt(i);
		    final Attribute att = dev_attr.get_attr_by_name(non_polled_name);

		    poll_period.add(att.get_polling_period());

		    if (att.get_polling_period() == 0) {
			Except.throw_exception("API_AttrNotPolled", "Attribute " + non_polled_name
				+ " not polled", "Device_2Impl.read_attributes");
		    } else {
			boolean found = false;
			final Vector napa = get_non_auto_polled_attr();

			for (j = 0; j < napa.size(); j++) {
			    final String napa_str = ((String) napa.elementAt(j)).toLowerCase();
			    if (napa_str.equals(non_polled_name.toLowerCase())) {
				found = true;
			    }
			}

			if (found) {
			    Except.throw_exception("API_AttrNotPolled", "Attribute "
				    + non_polled_name + " not polled",
				    "Device_2Impl.read_attributes");
			}
		    }
		}
		// Start polling
		final Util tg = Util.instance();
		final DServer adm_dev = tg.get_dserver_device();

		final DevVarLongStringArray send = new DevVarLongStringArray();
		send.lvalue = new int[1];
		send.svalue = new String[3];
		send.svalue[0] = device_name;
		send.svalue[1] = "attribute";

		for (i = 0; i < non_polled.size(); i++) {
		    send.lvalue[0] = (Integer) poll_period.elementAt(i);
		    send.svalue[2] = (String) non_polled.elementAt(i);

		    adm_dev.add_obj_polling(send, false);
		}
	    }

	    // Allocate memory for the AttributeValue structures
	    final int nb_attr = real_names.length;
	    back = new AttributeValue[nb_attr];

	    // For each attribute, check that some data are available in cache
	    // and that they
	    // are not too old
	    for (i = 0; i < nb_attr; i++) {
		PollObj polled_attr = null;
		for (j = 0; j < poll_list.size(); j++) {
		    final PollObj poll_obj = (PollObj) poll_list.elementAt(j);
		    if (poll_obj.get_type() == Tango_POLL_ATTR
			    && poll_obj.get_name().toLowerCase()
				    .equals(real_names[i].toLowerCase())) {
			polled_attr = poll_obj;
			break;
		    }
		}

		// Check that some data is available in cache
		if (polled_attr.is_ring_empty() == true) {
		    Except.throw_exception("API_NoDataYet",
			    "No data available in cache for attribute " + real_names[i],
			    "Device_2Impl.read_attributes");
		}

		// Check that data are still refreshed by the polling thread
		final long ctm = System.currentTimeMillis();
		int tv_sec = (int) (ctm / 1000);
		final int tv_usec = (int) (ctm - 1000 * tv_sec) * 1000;
		tv_sec = tv_sec - Tango_DELTA_T;

		final double last = polled_attr.get_last_insert_date();
		final double now_d = tv_sec + (double) tv_usec / 1000000;
		final double diff_d = now_d - last;
		if (diff_d > polled_attr.get_authorized_delta()) {
		    Except.throw_exception("API_NotUpdatedAnyMore", "Data in cache for attribute "
			    + real_names[i] + " not updated any more",
			    "Device_2Impl.read_attributes");
		}

		// Finally, after all these checks, get value and store it in
		// the
		// sequence sent back to user
		// In order to avoid unnecessary copy, don't use the assignement
		// operator of the
		// AttributeValue structure which copy each element and
		// therefore also copy
		// the Any object. The Any assignement operator is a deep copy!
		// Create a new sequence using the attribute buffer and insert
		// it into the
		// Any. The sequence inside the source Any has been created
		// using the attribute
		// data buffer.
		back[i] = polled_attr.get_last_attr_value();
	    }
	} catch (final DevFailed e) {
	    if (source == DevSource.CACHE) {
		throw e;
	    }
	    // if (att_in_fault == true) {
	    // throw e;
	    // }
	    polling_failed = true;
	}

	// If the source parameter is CACHE, returned data,
	// else call method reading attribute from device
	if (source == DevSource.CACHE_DEV && polling_failed == true) {
	    back = read_attributes(names);
	}
	return back;
    }

    // +-------------------------------------------------------------------------
    //
    // method : Device_2Impl.read_attribute_history_2
    // 
    // description : CORBA operation to read attribute alue history from
    // the polling buffer.
    //
    // argument: in : - name : attribute name
    // - n : history depth (in record number)
    //
    // This method returns a pointer to a DevAttrHistoryList with one
    // DevAttrHistory structure for each attribute record
    //
    // --------------------------------------------------------------------------

    public DevAttrHistory[] read_attribute_history_2(final String name, int n) throws DevFailed,
	    SystemException {
	Util.out4.println("Device_2Impl.read_attribute_history_2 arrived");

	// Record operation request in black box
	blackbox.insert_op(Op_Read_Attr_history_2);

	final Vector poll_list = get_poll_obj_list();
	final int nb_poll = poll_list.size();

	// Check that the device supports this attribute.
	// This method returns an exception in case of unsupported attribute
	final Attribute att = dev_attr.get_attr_by_name(name);
	final String attr_str = name.toLowerCase();

	// Check that the wanted attribute is polled.
	PollObj polled_attr = null;
	for (int i = 0; i < nb_poll; i++) {
	    final PollObj poll_obj = (PollObj) poll_list.elementAt(i);
	    if (poll_obj.get_type() == Tango_POLL_ATTR && poll_obj.get_name().equals(attr_str)) {
		polled_attr = poll_obj;
		break;
	    }
	}

	if (polled_attr == null) {
	    Except.throw_exception("API_AttrNotPolled", "No data available in cache for attribute "
		    + attr_str, "Device_2Impl.read_attribute_history_2");
	}

	// Check that some data is available in cache
	assert polled_attr != null;
	if (polled_attr.is_ring_empty()) {
	    Except.throw_exception("API_NoDataYet", "No data available in cache for attribute "
		    + attr_str, "Device_2Impl.read_attribute_history_2");
	}

	// Set the number of returned records
	final int in_buf = polled_attr.get_elt_nb_in_buffer();
	if (n > in_buf) {
	    n = in_buf;
	}

	// return attribute value history
	return polled_attr.get_attr_history(n, att.get_data_type());
    }

    // +-------------------------------------------------------------------------
    //
    // method : write_attributes
    // 
    // description : CORBA operation to write attribute(s) value
    //
    // argument: in : - values: The new attribute(s) value to be set.
    //
    // --------------------------------------------------------------------------
    /**
     * Write attribute(s).
     * 
     * Invoked when the client request the write_attributes CORBA operation.
     * This method writes new attribute value.
     * 
     * @param values
     *            The attribute(s) new set value. There is one AttributeValue
     *            object for each attribute to be set
     * @exception DevFailed
     *                Thrown if one of the attribute does not exist. Click <a
     *                href
     *                ="../../tango_basic/idl_html/Tango.html#DevFailed">here
     *                </a> to read <b>DevFailed</b> exception specification
     */
    public void write_attributes(final AttributeValue[] values) throws DevFailed {
	Util.out4.println("DeviceImpl.write_attributes arrived");

	//
	// Record operation request in black box
	//

	blackbox.insert_op(Op_Write_Attr);

	//
	// Return exception if the device does not have any attribute
	//

	final int nb_dev_attr = dev_attr.get_attr_nb();
	if (nb_dev_attr == 0) {
	    Except.throw_exception("API_AttrNotFound", "The device does not have any attribute",
		    "DeviceImpl.write_attributes");
	}

	//
	// Retrieve index of wanted attributes in the device attribute list
	//

	final long nb_updated_attr = values.length;
	final Vector updated_attr = new Vector();

	int i;
	for (i = 0; i < nb_updated_attr; i++) {
	    updated_attr.addElement(dev_attr.get_attr_ind_by_name(values[i].name));
	}

	//
	// Check that these attributes are writable
	//

	for (i = 0; i < nb_updated_attr; i++) {
	    if (dev_attr.get_attr_by_ind((Integer) updated_attr.elementAt(i)).get_writable() == AttrWriteType.READ
		    || dev_attr.get_attr_by_ind((Integer) updated_attr.elementAt(i)).get_writable() == AttrWriteType.READ_WITH_WRITE) {
		final StringBuffer o = new StringBuffer("Attribute ");
		o.append(dev_attr.get_attr_by_ind((Integer) updated_attr.elementAt(i)).get_name());
		o.append(" is not writable");

		Except.throw_exception("API_AttrNotWritable", o.toString(),
			"DeviceImpl.write_attributes");
	    }
	}

	//
	// Set attribute internal value
	//

	for (i = 0; i < nb_updated_attr; i++) {
	    try {
		dev_attr.get_w_attr_by_ind((Integer) updated_attr.elementAt(i)).set_write_value(
			values[i].value);
	    } catch (final DevFailed ex) {
		for (int j = 0; j < i; j++) {
		    dev_attr.get_w_attr_by_ind((Integer) updated_attr.elementAt(j)).rollback();
		}
		throw ex;
	    }
	}

	//
	// Write the hardware
	//
	try {
	    // If nb connection is closed to the maximum value,
	    // throw an exception to do not block devices.
	    Util.increaseAccessConter();
	    if (Util.getAccessConter() > Util.getPoaThreadPoolMax() - 2) {
		Util.decreaseAccessConter();
		Except.throw_exception("API_MemoryAllocation", Util.instance().get_ds_real_name()
			+ ": No thread available to connect device",
			"DeviceImpl.write_attributes()");
	    }

	    // Call the always_executed_hook method before
	    always_executed_hook();

	    switch (Util.get_serial_model()) {
	    case BY_CLASS:
		synchronized (device_class) {
		    write_attr_hardware(updated_attr);
		}
		break;

	    case BY_DEVICE:
		synchronized (this) {
		    write_attr_hardware(updated_attr);
		}
		break;

	    default: // NO_SYNC
		write_attr_hardware(updated_attr);
	    }
	} catch (final DevFailed exc) {
	    Util.decreaseAccessConter();
	    throw exc;
	} catch (final Exception exc) {
	    Util.decreaseAccessConter();
	    Except.throw_exception("API_ExceptionCatched", exc.toString(),
		    "DeviceImpl.write_attributes");
	}
	Util.decreaseAccessConter();

	//
	// Return to caller.
	//

	Util.out4.println("Leaving DeviceImpl.write_attributes");
    }

    // =================================================================
    /**
     * Remove an attribute on the device attribute list.
     * 
     * @param attname
     *            Attribute's name to be removed to the list
     * @throws DevFailed .
     */
    // =================================================================
    public void remove_attribute(final String attname) throws DevFailed {
	// Remove property in database
	if (Util._UseDb) {
	    final DeviceProxy dev = new DeviceProxy(device_name);
	    final DbAttribute da = dev.get_attribute_property(attname);
	    dev.delete_attribute_property(da);

	}
	dev_attr.remove_attribute(attname);
    }

    // +-------------------------------------------------------------------------
    //
    // method : add_attribute
    // 
    // description : Add attribute to the device attribute(s) list
    //
    // argument: in : - new_attr: The new attribute to be added.
    //
    // --------------------------------------------------------------------------

    /**
     * Add a new attribute to the device attribute list.
     * 
     * Attributes are normally constructed in the
     * DeviceClass::attribute_factory() method. Nevertheless, it is still
     * possible to add a new attribute to a device with this method. Please,
     * note that if you add an attribute to a device at device creation time,
     * this attribute will be added to the device class attribute list.
     * Therefore, all devices belonging to the same class created after this
     * attribute addition will also have this attribute.
     * 
     * @param new_attr
     *            Reference to the new attribute to be added to the list
     * @exception DevFailed . Click
     *                <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed">here
     *                </a> to read <b>DevFailed</b> exception specification
     */

    public void add_attribute(final Attr new_attr) throws DevFailed {
	final Vector attr_list = device_class.get_class_attr().get_attr_list();
	final int old_attr_nb = attr_list.size();

	//
	// Check that this attribute is not already defined for this device.
	// If it is alaredy there, immediately returns.
	// Trick : If you add an attribute to a device, this attribute will be
	// inserted in the device class attribute list. Therefore, all devices
	// created after this attribute addition will also have this attribute.
	//	

	final String attr_name = new_attr.get_name();
	boolean already_there = true;

	try {
	    dev_attr.get_attr_by_name(attr_name);
	} catch (final DevFailed ex) {
	    already_there = false;
	}

	if (already_there == true) {
	    return;
	}

	//
	// Add this attribute in the MultiClassAttribute attr_list vector if it
	// does not
	// already exist
	//

	int i;
	for (i = 0; i < old_attr_nb; i++) {
	    if (((Attr) attr_list.elementAt(i)).get_name().equals(attr_name) == true) {
		break;
	    }
	}
	if (i == old_attr_nb) {

	    attr_list.addElement(new_attr);

	    //
	    // Get all the properties defined for this attribute at class level
	    //

	    device_class.get_class_attr()
		    .init_class_attribute(device_class.get_name(), old_attr_nb);
	}

	//
	// Add the attribute to the MultiAttribute object
	//

	dev_attr.add_attribute(device_name, device_class, i);
    }

    //
    // Five methods which could be redefined in each device server class
    //

    /**
     * Get device state.
     * 
     * Default method to get device state. The behaviour of this method depends
     * on the device state. If the device state is ON or ALARM, it reads the
     * attribute(s) with an alarm level defined, check if the read value is
     * above/below the alarm and eventually change the state to ALARM, return
     * the device state. For all th eother device state, ti smethod simply
     * returns the state This method can be redefined in sub-classes in case of
     * the default behaviour does not fullfill the needs
     * 
     * @return The device state
     * @exception DevFailed
     *                If it is necessary to read attribute(s) and a problem
     *                occurs during the reading. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */

    public DevState dev_state() throws DevFailed {
	if (device_state.value() == DevState._ON || device_state.value() == DevState._ALARM) {

	    //
	    // Read the hardware
	    //

	    final Vector attr_list = dev_attr.get_alarm_list();
	    final int nb_wanted_attr = attr_list.size();
	    if (nb_wanted_attr != 0) {
		read_attr_hardware(attr_list);

		//
		// Set attr value
		//

		int i;
		for (i = 0; i < nb_wanted_attr; i++) {
		    final Attribute att = dev_attr
			    .get_attr_by_ind((Integer) attr_list.elementAt(i));
		    att.wanted_date(false);
		    try {
			read_attr(att);
		    } catch (final DevFailed ex) {
			att.wanted_date(true);
			throw ex;
		    }
		    att.wanted_date(true);
		}

		//
		// Check alarm level
		//

		if (dev_attr.check_alarm() == true) {
		    device_state = DevState.ALARM;
		}

	    }
	}
	return device_state;
    }

    /**
     * Get device status.
     * 
     * Default method to get device status. It returns the contents of the
     * device device_status field. If the device state is ALARM, alarm messages
     * are added to the device status. This method can be redefined in
     * sub-classes in case of the default behaviour does not fullfill the needs
     * 
     * @return The device status
     * @exception DevFailed
     *                This method does not throw exception but a redefined
     *                method can. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */

    public String dev_status() throws DevFailed {
	if (device_state.value() == DevState._ALARM || device_state.value() == DevState._ON) {
	    final StringBuffer alarm_status = new StringBuffer(device_status);
	    dev_attr.read_alarm(alarm_status);
	    return alarm_status.toString();
	}
	return device_status;
    }

    /**
     * Read the hardware to return attribute value(s).
     * 
     * Default method to implement an action necessary on a device to read the
     * hardware involved in a a read attribute CORBA call. This method must be
     * redefined in sub-classes in order to support attribute reading
     * 
     * @param attr_list
     *            Reference to a vector with Integer object. Each element in
     *            this vector is the index in the device onject attribute vector
     *            of an attribute to be read.
     * @exception DevFailed
     *                This method does not throw exception but a redefined
     *                method can. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */
    public void read_attr_hardware(final Vector attr_list) throws DevFailed {
    }

    /**
     * Set the attribute read value.
     * 
     * Default method to set an attribute read value. This method must be
     * redefined in sub-classes when attributes are needed
     * 
     * @param attr
     *            The attribute object
     * @exception DevFailed
     *                This method does not throw exception but a redefined
     *                method can. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */
    public void read_attr(final Attribute attr) throws DevFailed {
    }

    /**
     * Write the hardware for attributes.
     * 
     * Default method to implement an action necessary on a device to write the
     * hardware involved in a a write attribute. This method must be redefined
     * in sub-classes in order to support writable attribute
     * 
     * @param attr_list
     *            Reference to a vector of Integer objects. Each element in this
     *            vector is the index in the main attribute vector of an
     *            attribute to be written.
     * @exception DevFailed
     *                This method does not throw exception but a redefined
     *                method can. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */
    public void write_attr_hardware(final Vector attr_list) throws DevFailed {
    }

    /**
     * Hook method.
     * 
     * Default method to implement an action necessary on a device before any
     * command is executed. This method can be redefined in sub-classes in case
     * of the default behaviour does not fullfill the needs
     * 
     * @exception DevFailed
     *                This method does not throw exception but a redefined
     *                method can. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */

    public void always_executed_hook() throws DevFailed {
    }

    //
    // one abstract method
    //

    /**
     * Intialise a device.
     * 
     * In the DeviceImpl class, this method is pure abstract and must be defined
     * in sub-class. Its rule is to initialise a device. This method is called
     * during device creation an dby the DevRestart command
     * 
     * @exception DevFailed
     *                This method does not throw exception but a redefined
     *                method can. Click <a
     *                href="../../tango_basic/idl_html/Tango.html#DevFailed"
     *                >here</a> to read <b>DevFailed</b> exception specification
     */

    public abstract void init_device() throws DevFailed;

    /**
     * This method is called during device Init an the DevRestart command. Some
     * clean-up should be done here for stopping thread, clearing lists...
     * 
     * @throws DevFailed
     */
    public abstract void delete_device() throws DevFailed;

    /**
     * Return device POA
     * 
     * Return a reference to the device POA. This method is necessary for the
     * CORBA object implicit activation by the _this method
     * 
     * @return Reference to the device POA
     */

    @Override
    public POA _default_POA() {
	return Util.instance().get_poa();
    }

    //
    // Methods to retrieve/set some data members from outside the class and all
    // its inherited classes
    //

    /**
     * Get device status.
     * 
     * Return the device device_status field. This method does the same thing
     * than the default dev_status method.
     * 
     * @return Device status
     */

    public String get_status() {
	return device_status;
    }

    /**
     * Set device status.
     * 
     * @param new_status
     *            The new device status
     */

    public void set_status(final String new_status) {
	device_status = new_status;
    }

    /**
     * Get device state.
     * 
     * Return the device device_state field. This method does the same thing
     * than the default dev_state method.
     * 
     * @return Device state
     */

    public DevState get_state() {
	return device_state;
    }

    /**
     * Set device state.
     * 
     * @param new_state
     *            The new device state
     */

    public void set_state(final DevState new_state) {
	device_state = new_state;
    }

    /**
     * Get device name.
     * 
     * Return the device name (device_name field)
     * 
     * @return Device name
     */

    public String get_name() {
	return device_name;
    }

    /**
     * Get device class object reference.
     * 
     * Return the device class object reference
     * 
     * @return Device class
     */

    public DeviceClass get_device_class() {
	return device_class;
    }

    /**
     * Set device state.
     * 
     * @param new_multi_attr
     *            The new attribute
     */

    public void set_device_attr(final MultiAttribute new_multi_attr) {
	dev_attr = new_multi_attr;
    }

    /**
     * Get the reference to the associated DbDevice object
     * 
     * Return the DbDevice object reference
     * 
     * @return DbDevice
     */

    public DbDevice get_db_device() {
	return db_dev;
    }

    /**
     * Get the associated CORBA object identifier
     * 
     * @return The CORBA object identifier
     */

    public byte[] get_obj_id() {
	return obj_id;
    }

    /**
     * Set the associated CORBA object identifier
     * 
     * @param id
     *            The CORBA object identifier
     */

    public void set_obj_id(final byte[] id) {
	obj_id = id;
    }

    void set_exported_flag(final boolean exp) {
	exported = exp;
    }

    boolean get_exported_flag() {
	return exported;
    }

    /**
     * Returns the device's logger
     */
    public Logger get_logger() {
	if (logger == null) {
	    logger = Logger.getLogger(get_name().toLowerCase());
	    logger.setAdditivity(false);
	    logger.setLevel(Level.WARN);
	    last_level = Level.WARN;
	}
	return logger;
    }

    /**
     * Initialize the logging for this device
     */
    @SuppressWarnings( { "NestedTryStatement" })
    public void init_logger() {
	try {
	    Util.out4.println("Initializing logging for " + get_name());
	    // - Get Util instance
	    final Util util = Util.instance();
	    // - Get cmd line logging level then ...
	    final int trace_level = util.get_trace_level();
	    // ... convert it to log4j level
	    Level cmd_line_level;
	    // does the logging level set from cmd line?
	    boolean level_set_from_cmd_line = true;
	    if (trace_level <= 0) {
		level_set_from_cmd_line = false;
		cmd_line_level = Level.OFF;
	    } else if (trace_level <= 2) {
		cmd_line_level = Level.INFO;
	    } else {
		cmd_line_level = Level.DEBUG;
	    }
	    // - Add a console target if logging level set from cmd line
	    if (level_set_from_cmd_line) {
		// add a console target if logging level set from cmd line
		try {
		    Logging.instance().add_logging_target(get_logger(), LOGGING_CONSOLE_TARGET);
		} catch (final DevFailed df) {
		    /** ignore exception */
		}
	    }
	    if (!Util._UseDb) {
		// - Done if we are not using the database
		Util.out4.println("Not using the database. Logging Intialization complete");
		return;
	    }
	    // - Get both logging level and targets from database
	    final Logging.LoggingProperties properties = Logging.instance().get_logging_properties(
		    get_logger(), util.get_database());
	    if (properties == null) {
		Util.out4.println("Failed to obtain logging properties from database");
		Util.out4.println("Aborting logging intialization");
		get_logger().setLevel(cmd_line_level);
		return;
	    }
	    // - Set logging level
	    if (level_set_from_cmd_line == false) {
		get_logger().setLevel(properties.logging_level);
		Util.out4.println("Logging level set to " + properties.logging_level.toString());
	    } else {
		get_logger().setLevel(cmd_line_level);
	    }
	    // - Save current logging level
	    last_level = get_logger().getLevel();
	    // - Get rolling threshold for file targets
	    if (rft != properties.logging_rft) {
		rft = properties.logging_rft;
		Util.out4.println("Rolling threshold changed to " + String.valueOf(rft));
	    }
	    // - Set logging targets
	    if (properties.logging_targets != null) {
		Util.out4.println("Adding logging targets (" + properties.logging_targets.length
			+ " entries in db)");
		for (final String logging_target : properties.logging_targets) {
		    try {
			Logging.instance().add_logging_target(get_logger(), logging_target);
		    } catch (final DevFailed e) { /* ignore any exception */
		    }
		}
	    }
	    // set rolling file threshold for file targets
	    Logging.instance().set_rolling_file_threshold(get_logger(), rft);
	} catch (final Exception e) {
	    // igore any exception
	}
    }

    /**
     * Backup current logging level then set it to OFF
     */
    public void stop_logging() {
	last_level = get_logger().getLevel();
	get_logger().setLevel(Level.OFF);
    }

    /**
     * Restore logging level to its backup value
     */
    public void start_logging() {
	get_logger().setLevel(last_level);
    }

    // =======================================
    //
    // Polling part
    //
    // =======================================
    // ==========================================================
    /**
     * This method check that a comamnd is supported by the device and does not
     * need input value. The method throws an exception if the command is not
     * defined or needs an input value
     */
    // ==========================================================
    void check_command_exists(final String cmd_name) throws DevFailed {
	final Vector cmd_list = device_class.get_command_list();
	for (int i = 0; i < cmd_list.size(); i++) {
	    final Command cmd = (Command) cmd_list.elementAt(i);
	    if (cmd.get_name().toLowerCase().equals(cmd_name)) {
		if (cmd.get_in_type() != Tango_DEV_VOID) {
		    Except.throw_exception("API_IncompatibleCmdArgumentType", "Command " + cmd_name
			    + " cannot be polled because it needs input value",
			    "DeviceImpl.check_command_exists");
		}
		return;
	    }
	}

	Except.throw_exception("API_CommandNotFound", "Command " + cmd_name + " not found",
		"DeviceImpl.check_command_exists");
    }

    // ==========================================================
    // ==========================================================
    int get_poll_old_factor() {
	return ext.poll_old_factor;
    }

    // ==========================================================
    // ==========================================================
    void set_poll_old_factor(final int fact) {
	ext.poll_old_factor = fact;
    }

    // ==========================================================
    // ==========================================================
    boolean is_polled() {
	return ext.polled;
    }

    // ==========================================================
    // ==========================================================
    void is_polled(final boolean b) {
	ext.polled = b;
    }

    // ==========================================================
    // ==========================================================
    Vector get_poll_obj_list() {
	return ext.poll_obj_list;
    }

    // ==========================================================
    // ==========================================================
    int get_poll_ring_depth() {
	return ext.poll_ring_depth;
    }

    // ==========================================================
    // ==========================================================
    public void set_poll_ring_depth(final int size) {
	ext.poll_ring_depth = size;
    }

    // ==========================================================
    // ==========================================================
    MultiAttribute get_device_attr() {
	return dev_attr;
    }

    // ==========================================================
    // ==========================================================
    void set_non_auto_polled_cmd(final String[] s) {
	for (final String value : s) {
	    ext.non_auto_polled_cmd.add(value);
	}
    }

    // ==========================================================
    // ==========================================================
    Vector get_non_auto_polled_cmd() {
	return ext.non_auto_polled_cmd;
    }

    // ==========================================================
    // ==========================================================
    void set_non_auto_polled_attr(final String[] s) {
	for (final String value : s) {
	    ext.non_auto_polled_attr.add(value);
	}
    }

    // ==========================================================
    // ==========================================================
    Vector get_non_auto_polled_attr() {
	return ext.non_auto_polled_attr;
    }

    // ==========================================================
    // ==========================================================
    public void set_polled_cmd(final String[] s) {
	for (final String value : s) {
	    ext.polled_cmd.add(value);
	}
    }

    // ==========================================================
    // ==========================================================
    Vector get_polled_cmd() {
	return ext.polled_cmd;
    }

    // ==========================================================
    // ==========================================================
    public void set_polled_attr(final String[] s) {
	for (final String value : s) {
	    ext.polled_attr.add(value);
	}
    }

    // ==========================================================
    // ==========================================================
    Vector get_polled_attr() {
	return ext.polled_attr;
    }

    // ==========================================================
    // ==========================================================
    TangoMonitor get_dev_monitor() {
	return ext.only_one;
    }

    // ==========================================================
    /**
     * This method check return the polled object for input type and name.
     * 
     * @param obj_type
     *            the object type
     * @param obj_name
     *            the object name
     */
    // ==========================================================
    synchronized PollObj get_polled_obj_by_type_name(final int obj_type, final String obj_name)
	    throws DevFailed {
	// Get vector and search for type and name.
	final Vector po_list = get_poll_obj_list();
	for (int i = 0; i < po_list.size(); i++) {
	    final PollObj polled_obj = (PollObj) po_list.elementAt(i);
	    if (polled_obj.get_type_i() == obj_type) {
		if (polled_obj.get_name_i().equals(obj_name)) {
		    return polled_obj;
		}
	    }
	}

	// If not found throws exception
	Except.throw_exception("API_PollObjNotFound", obj_name
		+ " not found in list of polled object", "DeviceImpl.get_polled_obj_by_type_name");
	return null;
    }

    // ==========================================================
    // ==========================================================
    // +-------------------------------------------------------------------------
    //
    // method : DeviceImpl.get_command
    // 
    // description : This method returns a pointer to command object.
    // The method throws an exception if the
    // command is not defined
    //
    // in : cmd_name : The command name
    //
    // --------------------------------------------------------------------------
    Command get_command(final String cmd_name) throws DevFailed {
	Command ret_cmd = null;
	final Vector cmd_list = device_class.get_command_list();
	for (int i = 0; i < cmd_list.size(); i++) {
	    final Command cmd = (Command) cmd_list.elementAt(i);
	    if (cmd.get_name().toLowerCase().equals(cmd_name.toLowerCase())) {
		ret_cmd = cmd;
	    }
	}

	if (ret_cmd == null) {
	    Except.throw_exception("API_CommandNotFound", "Command " + cmd_name + " not found",
		    "DeviceImpl.get_command");
	}

	return ret_cmd;
    }

    // ==========================================================
    /**
	 *
	 */
    // ==========================================================
    class DeviceImplExt {
	boolean exported = false;
	boolean polled = false;
	int poll_ring_depth = 0;
	int poll_old_factor;

	Vector polled_cmd;
	Vector polled_attr;
	Vector non_auto_polled_cmd;
	Vector non_auto_polled_attr;
	Vector poll_obj_list;

	TangoMonitor only_one; // Polling monitor

	DeviceImplExt() {
	    polled_cmd = new Vector();
	    polled_attr = new Vector();
	    non_auto_polled_cmd = new Vector();
	    non_auto_polled_attr = new Vector();
	    poll_obj_list = new Vector();

	    only_one = new TangoMonitor();
	}
    }
}
