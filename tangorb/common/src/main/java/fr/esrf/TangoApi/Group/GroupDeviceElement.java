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
// $Revision: 25296 $
//
//-======================================================================


package fr.esrf.TangoApi.Group;

//- Import Tango stuffs

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;
import fr.esrf.TangoApi.AsynReplyNotArrived;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;

import java.util.HashMap;
import java.util.Map;

/**
 * TANGO group device element - private class for package Group
 */

class GroupDeviceElement extends GroupElement implements java.io.Serializable {

    /**
     * Asynch Request Repository
     */
    private final Map<Integer, AsynchRequest> arp;
    /**
     * The underlying DeviceProxy
     */
    private DeviceProxy proxy;

    /** Creates a new instance of GroupDeviceElement */
    public GroupDeviceElement(final String name) {
	super(name);
	try {
	    proxy = new DeviceProxy(name);
	} catch (final DevFailed df) {
	    proxy = null;
	}
	arp = new HashMap<Integer, GroupDeviceElement.AsynchRequest>();
    }

    /** Dump element */
    @Override
    void dump_i(final int indent_level) {
	for (int i = 0; i < indent_level; i++) {
	    System.out.print("  ");
	}
	System.out.println("`-> Device: " + get_name());
    }

    /** Returns the underlying DeviceProxy */
    @Override
    DeviceProxy get_device_i(final String n) {
	return name_equals(n) ? proxy : null;
    }

    /** Returns the underlying DeviceProxy - access limited to package Group */
    @Override
    DeviceProxy get_device_i(int i) {
	return --i == 0 ? proxy : null;
    }

    /** Ping the underlying device, return true if alive, false otherwise */
    @Override
    boolean ping_i(final boolean fwd) {
	if (proxy == null) {
	    return false;
	}
	try {
	    proxy.ping();
	} catch (final DevFailed df) {
	    return false;
	}
	return true;
    }

    /** set individual time out - access limited to package Group */
    @Override
    void set_timeout_millis(final int timeout, final boolean fwd) throws DevFailed {
	try {
	    proxy.set_timeout_millis(timeout);
	} catch (final DevFailed df) {
	    throw df;
	} catch (final Exception e) {
	    final DevError[] errors = new DevError[1];
	    errors[0] = new DevError();
	    errors[0].severity = ErrSeverity.ERR;
	    errors[0].reason = "unknown exception caught";
	    errors[0].desc = "unknown error";
	    errors[0].origin = "GroupDeviceElemnt.set_indidual_timeout_millis";
	    final DevFailed ex = new DevFailed(errors);
	    throw ex;
	}
    }

    /** command_inout_i - access limited to package Group */
    @Override
    int command_inout_asynch_i(final String c, final boolean fgt, final boolean fwd, final int rid) throws DevFailed {
	try {
	    final int actual_rid = proxy.command_inout_asynch(c, fgt);
	    if (fgt == false) {
		arp.put(new Integer(rid), new AsynchRequest(actual_rid, c));
	    }
	} catch (final DevFailed df) {
	    if (fgt == false) {
		arp.put(new Integer(rid), new AsynchRequest(-1, c, df));
	    }
	} catch (final Exception e) {
	    if (fgt == false) {
		final DevError[] errors = new DevError[1];
		errors[0] = new DevError();
		errors[0].severity = ErrSeverity.ERR;
		errors[0].reason = "unknown exception caught";
		errors[0].desc = "unknown error";
		errors[0].origin = "GroupDeviceElemnt.command_inout";
		final DevFailed ex = new DevFailed(errors);
		arp.put(new Integer(rid), new AsynchRequest(-1, c, ex));
	    }
	}
	return rid;
    }

    /** command_inout_i - access limited to package Group */
    @Override
    int command_inout_asynch_i(final String c, final DeviceData dd, final boolean fgt, final boolean fwd, final int rid)
	    throws DevFailed {
	try {
	    final int actual_rid = proxy.command_inout_asynch(c, dd, fgt);
	    if (fgt == false) {
		arp.put(new Integer(rid), new AsynchRequest(actual_rid, c));
	    }
	} catch (final DevFailed df) {
	    if (fgt == false) {
		arp.put(new Integer(rid), new AsynchRequest(-1, c, df));
	    }
	} catch (final Exception e) {
	    if (fgt == false) {
		final DevError[] errors = new DevError[1];
		errors[0] = new DevError();
		errors[0].severity = ErrSeverity.ERR;
		errors[0].reason = "unknown exception caught";
		errors[0].desc = "unknown error";
		errors[0].origin = "GroupDeviceElemnt.command_inout";
		final DevFailed ex = new DevFailed(errors);
		arp.put(new Integer(rid), new AsynchRequest(-1, c, ex));
	    }
	}
	return rid;
    }

    /** command_inout_reply_i - access limited to package Group */
    @Override
    GroupCmdReplyList command_inout_reply_i(final int rid, final int tmo, final boolean fwd) throws DevFailed {
	final Integer rid_obj = new Integer(rid);
	final GroupCmdReplyList rl = new GroupCmdReplyList();
	final AsynchRequest ar = arp.get(rid_obj);
	if (ar == null) {
	    final DevError[] errors = new DevError[1];
	    errors[0] = new DevError();
	    errors[0].severity = ErrSeverity.ERR;
	    errors[0].reason = "API_BadAsynPollId";
	    errors[0].desc = "Invalid asynch. request identifier specified";
	    errors[0].origin = "GroupDeviceElement.command_inout_reply";
	    final DevFailed e = new DevFailed(errors);
	    rl.add(new GroupCmdReply(get_name(), "unknown", e));
	    return rl;
	}
	if (ar.req_id == -1) {
	    for (final String element : ar.obj_name) {
		rl.add(new GroupCmdReply(get_name(), element, ar.exception));
	    }
	    arp.remove(rid_obj);
	    return rl;
	}
	try {
	    final DeviceData dd = proxy.command_inout_reply(ar.req_id, tmo);
	    for (final String element : ar.obj_name) {
		rl.add(new GroupCmdReply(get_name(), element, dd));
	    }
	} catch (final AsynReplyNotArrived na) {
	    final DevError[] errors = new DevError[1];
	    errors[0] = new DevError();
	    errors[0].severity = ErrSeverity.ERR;
	    errors[0].reason = "API_AsynReplyNotArrived";
	    errors[0].desc = "No reply for asynch request";
	    errors[0].origin = "GroupDeviceElement.command_inout_reply";
	    final DevFailed e = new DevFailed(errors);
	    for (final String element : ar.obj_name) {
		rl.add(new GroupCmdReply(get_name(), element, e));
	    }

	} catch (final DevFailed df) {
	    for (final String element : ar.obj_name) {
		rl.add(new GroupCmdReply(get_name(), element, df));
	    }
	} catch (final Exception ex) {
	    final DevError[] errors = new DevError[1];
	    errors[0] = new DevError();
	    errors[0].severity = ErrSeverity.ERR;
	    errors[0].reason = "unknown exception caught";
	    errors[0].desc = "unknown error";
	    errors[0].origin = "GroupDeviceElemnt.command_inout";
	    final DevFailed e = new DevFailed(errors);
	    for (final String element : ar.obj_name) {
		rl.add(new GroupCmdReply(get_name(), element, e));
	    }
	}
	arp.remove(rid_obj);
	return rl;
    }

    /** read_attribute_asynch_i - access limited to package Group */
    @Override
    int read_attribute_asynch_i(final String[] a, final boolean fwd, final int rid) throws DevFailed {
	try {
	    final int actual_rid = proxy.read_attribute_asynch(a);
	    arp.put(new Integer(rid), new AsynchRequest(actual_rid, a));
	} catch (final DevFailed df) {
	    arp.put(new Integer(rid), new AsynchRequest(-1, a, df));
	} catch (final Exception e) {
	    final DevError[] errors = new DevError[1];
	    errors[0] = new DevError();
	    errors[0].severity = ErrSeverity.ERR;
	    errors[0].reason = "unknown exception caught";
	    errors[0].desc = "unknown error";
	    errors[0].origin = "GroupDeviceElemnt.read_attribute";
	    final DevFailed ex = new DevFailed(errors);
	    arp.put(new Integer(rid), new AsynchRequest(-1, a, ex));
	}
	return rid;
    }

    /** read_attribute_asynch_i - access limited to package Group */
    @Override
    int read_attribute_asynch_i(final String a, final boolean fwd, final int rid) throws DevFailed {
	try {
	    final int actual_rid = proxy.read_attribute_asynch(a);
	    arp.put(new Integer(rid), new AsynchRequest(actual_rid, a));
	} catch (final DevFailed df) {
	    arp.put(new Integer(rid), new AsynchRequest(-1, a, df));
	} catch (final Exception e) {
	    final DevError[] errors = new DevError[1];
	    errors[0] = new DevError();
	    errors[0].severity = ErrSeverity.ERR;
	    errors[0].reason = "unknown exception caught";
	    errors[0].desc = "unknown error";
	    errors[0].origin = "GroupDeviceElemnt.read_attribute";
	    final DevFailed ex = new DevFailed(errors);
	    arp.put(new Integer(rid), new AsynchRequest(-1, a, ex));
	}
	return rid;
    }

    /** read_attribute_reply_i - access limited to package Group */
    @Override
    GroupAttrReplyList read_attribute_reply_i(final int rid, final int tmo, final boolean fwd) throws DevFailed {
	final Integer rid_obj = new Integer(rid);
	final GroupAttrReplyList rl = new GroupAttrReplyList();
	final AsynchRequest ar = arp.get(rid_obj);
	if (ar == null) {
	    final DevError[] errors = new DevError[1];
	    errors[0] = new DevError();
	    errors[0].severity = ErrSeverity.ERR;
	    errors[0].reason = "API_BadAsynPollId";
	    errors[0].desc = "Invalid asynch. request identifier specified";
	    errors[0].origin = "GroupDeviceElement.read_attribute_reply";
	    final DevFailed e = new DevFailed(errors);
	    rl.add(new GroupAttrReply(get_name(), "unknown", e));
	    return rl;
	}
	if (ar.req_id == -1) {
	    for (final String element : ar.obj_name) {
		rl.add(new GroupAttrReply(get_name(), element, ar.exception));
	    }
	    arp.remove(rid_obj);
	    return rl;
	}
	try {

	    final DeviceAttribute[] das = proxy.read_attribute_reply(ar.req_id, tmo);
	    int i = 0;
	    for (final String element : ar.obj_name) {
		rl.add(new GroupAttrReply(get_name(), element, das[i++]));
	    }
	} catch (final AsynReplyNotArrived na) {
	    final DevError[] errors = new DevError[1];
	    errors[0] = new DevError();
	    errors[0].severity = ErrSeverity.ERR;
	    errors[0].reason = "API_AsynReplyNotArrived";
	    errors[0].desc = "No reply for asynch request";
	    errors[0].origin = "GroupDeviceElement.read_attribute_reply";
	    final DevFailed e = new DevFailed(errors);
	    for (final String element : ar.obj_name) {
		rl.add(new GroupAttrReply(get_name(), element, e));
	    }
	} catch (final DevFailed df) {
	    for (final String element : ar.obj_name) {
		rl.add(new GroupAttrReply(get_name(), element, df));
	    }
	} catch (final Exception ex) {
	    final DevError[] errors = new DevError[1];
	    errors[0] = new DevError();
	    errors[0].severity = ErrSeverity.ERR;
	    errors[0].reason = "unknown exception caught";
	    errors[0].desc = "unknown error";
	    errors[0].origin = "GroupDeviceElemnt.read_attribute_reply";
	    final DevFailed e = new DevFailed(errors);
	    for (final String element : ar.obj_name) {
		rl.add(new GroupAttrReply(get_name(), element, e));
	    }
	}
	arp.remove(rid_obj);
	return rl;
    }

    /** read_attribute_asynch_i - access limited to package Group */
    @Override
    int write_attribute_asynch_i(final DeviceAttribute da, final boolean fwd, final int rid) throws DevFailed {
	try {
	    final int actual_rid = proxy.write_attribute_asynch(da);
	    arp.put(new Integer(rid), new AsynchRequest(actual_rid, da.getName()));
	} catch (final DevFailed df) {
	    arp.put(new Integer(rid), new AsynchRequest(-1, da.getName(), df));
	} catch (final Exception e) {
	    final DevError[] errors = new DevError[1];
	    errors[0] = new DevError();
	    errors[0].severity = ErrSeverity.ERR;
	    errors[0].reason = "unknown exception caught";
	    errors[0].desc = "unknown error";
	    errors[0].origin = "GroupDeviceElemnt.write_attribute";
	    final DevFailed ex = new DevFailed(errors);
	    arp.put(new Integer(rid), new AsynchRequest(-1, da.getName(), ex));
	}
	return rid;
    }

    /** read_attribute_reply_i - access limited to package Group */
    @Override
    GroupReplyList write_attribute_reply_i(final int rid, final int tmo, final boolean fwd) throws DevFailed {
	final Integer rid_obj = new Integer(rid);
	final GroupReplyList rl = new GroupReplyList();
	final AsynchRequest ar = arp.get(rid_obj);
	if (ar == null) {
	    final DevError[] errors = new DevError[1];
	    errors[0] = new DevError();
	    errors[0].severity = ErrSeverity.ERR;
	    errors[0].reason = "API_BadAsynPollId";
	    errors[0].desc = "Invalid asynch. request identifier specified";
	    errors[0].origin = "GroupDeviceElement.write_attribute_reply";
	    final DevFailed e = new DevFailed(errors);
	    rl.add(new GroupReply(get_name(), "unknown", e));
	    return rl;
	}
	if (ar.req_id == -1) {
	    for (final String element : ar.obj_name) {
		rl.add(new GroupReply(get_name(), element, ar.exception));
	    }
	    arp.remove(rid_obj);
	    return rl;
	}
	try {
	    proxy.write_attribute_reply(ar.req_id, tmo);
	    for (final String element : ar.obj_name) {
		rl.add(new GroupReply(get_name(), element));
	    }
	} catch (final AsynReplyNotArrived na) {
	    final DevError[] errors = new DevError[1];
	    errors[0] = new DevError();
	    errors[0].severity = ErrSeverity.ERR;
	    errors[0].reason = "API_AsynReplyNotArrived";
	    errors[0].desc = "No reply for asynch request";
	    errors[0].origin = "GroupDeviceElement.write_attribute_reply";
	    final DevFailed e = new DevFailed(errors);
	    for (final String element : ar.obj_name) {
		rl.add(new GroupReply(get_name(), element, e));
	    }
	} catch (final DevFailed df) {
	    for (final String element : ar.obj_name) {
		rl.add(new GroupReply(get_name(), element, df));
	    }
	} catch (final Exception ex) {
	    final DevError[] errors = new DevError[1];
	    errors[0] = new DevError();
	    errors[0].severity = ErrSeverity.ERR;
	    errors[0].reason = "unknown exception caught";
	    errors[0].desc = "unknown error";
	    errors[0].origin = "GroupDeviceElemnt.write_attribute_reply";
	    final DevFailed e = new DevFailed(errors);
	    for (final String element : ar.obj_name) {
		rl.add(new GroupReply(get_name(), element, e));
	    }
	}
	arp.remove(rid_obj);
        return rl;
    }

    /**
     * Inner class: AsynchRequest
     */
    private class AsynchRequest {
        /** Asynch request id holder */
        public int req_id;
        /** Command or attribuet name */
        public String[] obj_name;
        /** Exception holder */
        public DevFailed exception;

        /**
         * Ctor
         */
        AsynchRequest(final int _req_id, final String _obj_name) {
            req_id = _req_id;
            obj_name = new String[1];
            obj_name[0] = _obj_name;
            exception = null;
        }

        AsynchRequest(final int _req_id, final String[] _obj_name) {
            req_id = _req_id;
            obj_name = _obj_name;
            exception = null;
        }

        /**
         * Ctor
         */
        AsynchRequest(final int _req_id, final String _obj_name, final DevFailed _exception) {
            req_id = _req_id;
            obj_name = new String[1];
            obj_name[0] = _obj_name;
            exception = _exception;
        }

        AsynchRequest(final int _req_id, final String[] _obj_name, final DevFailed _exception) {
            req_id = _req_id;
            obj_name = _obj_name;
            exception = _exception;
        }
    }
}
