//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:	 Java source for TANGO Group abstraction.
//
// author(s) :          N.Leclercq - SOLEIL
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008
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

import java.util.TreeMap;

/**
 * TANGO group device element - private class for package Group
 */

class GroupDeviceElement extends GroupElement implements java.io.Serializable {
    
    /** Inner class: AsynchRequest */
    private class AsynchRequest {
        /** Ctor */
        AsynchRequest(int _req_id, String _obj_name) {
            req_id = _req_id;
            obj_name = _obj_name;
            exception = null;
        }
        /** Ctor */
        AsynchRequest(int _req_id, String _obj_name, DevFailed _exception) {
            req_id = _req_id;
            obj_name = _obj_name;
            exception = _exception;
        }
        /** Asynch request id holder */
        public int req_id;
        /** Command or attribuet name */
        public String obj_name;
        /** Exception holder */
        public DevFailed exception;
    }
    
    /** Creates a new instance of GroupDeviceElement */
    public GroupDeviceElement(String name) {
        super(name);
        try {
            proxy = new DeviceProxy(name);
        }
        catch (DevFailed df) {
            proxy = null;
        }
        arp = new TreeMap();
    }
    
    /** Dump element */
    void dump_i(int indent_level) {
        for (int i = 0; i < indent_level; i++) {
            System.out.print("  ");
        }
        System.out.println("`-> Device: " + get_name());
    }
    
    /** Returns the underlying DeviceProxy */
    DeviceProxy get_device_i(String n) {
        return name_equals(n) ? proxy : null;
    }
    
    /** Returns the underlying DeviceProxy - access limited to package Group */
    DeviceProxy get_device_i(int i) {
        return (--i == 0) ? proxy : null;
    }
    
    /** Ping the underlying device, return true if alive, false otherwise */
    boolean ping_i(boolean fwd) {
        if (proxy == null) {
            return false;
        }
        try {
            proxy.ping();
        }
        catch (DevFailed df) {
            return false;
        }
        return true;
    }
    
    /** set individual time out - access limited to package Group */
    void set_timeout_millis(int timeout, boolean fwd) throws DevFailed {
        try
        {
            proxy.set_timeout_millis(timeout);
        }
        catch (DevFailed df) {
            throw df;
        }
        catch (Exception e)
        {
            DevError[] errors = new DevError[1];
            errors[0] = new DevError();
            errors[0].severity = ErrSeverity.ERR;
            errors[0].reason = "unknown exception caught";
            errors[0].desc = "unknown error";
            errors[0].origin = "GroupDeviceElemnt.set_indidual_timeout_millis";
            DevFailed ex = new DevFailed(errors);
            throw ex;
        }
    }
    
    /** command_inout_i - access limited to package Group */
    int command_inout_asynch_i(String c, boolean fgt, boolean fwd, int rid) throws DevFailed {
        try {
            int actual_rid = proxy.command_inout_asynch(c, fgt);
            if (fgt == false) {
                arp.put(new Integer(rid), new AsynchRequest(actual_rid,c));
            }
        }
        catch (DevFailed df) {
            if (fgt == false) {
                arp.put(new Integer(rid), new AsynchRequest(-1,c,df));
            }
        }
        catch (Exception e) {
            if (fgt == false) {
                DevError[] errors = new DevError[1];
                errors[0] = new DevError();
                errors[0].severity = ErrSeverity.ERR;
                errors[0].reason = "unknown exception caught";
                errors[0].desc = "unknown error";
                errors[0].origin = "GroupDeviceElemnt.command_inout";
                DevFailed ex = new DevFailed(errors);
                arp.put(new Integer(rid), new AsynchRequest(-1,c,ex));
            }
        }
        return rid;
    }
    
    /** command_inout_i - access limited to package Group */
    int command_inout_asynch_i(String c, DeviceData dd, boolean fgt, boolean fwd, int rid) throws DevFailed {
        try {
            int actual_rid = proxy.command_inout_asynch(c, dd, fgt);
            if (fgt == false) {
                arp.put(new Integer(rid), new AsynchRequest(actual_rid, c));
            }
        }
        catch (DevFailed df) {
            if (fgt == false) {
                arp.put(new Integer(rid), new AsynchRequest(-1, c, df));
            }
        }
        catch (Exception e) {
            if (fgt == false) {
                DevError[] errors = new DevError[1];
                errors[0] = new DevError();
                errors[0].severity = ErrSeverity.ERR;
                errors[0].reason = "unknown exception caught";
                errors[0].desc = "unknown error";
                errors[0].origin = "GroupDeviceElemnt.command_inout";
                DevFailed ex = new DevFailed(errors);
                arp.put(new Integer(rid), new AsynchRequest(-1, c, ex));
            }
        }
        return rid;
    }
    
    /** command_inout_reply_i - access limited to package Group */
    GroupCmdReplyList command_inout_reply_i(int rid, int tmo, boolean fwd) throws DevFailed {
        Integer rid_obj = new Integer(rid);
        GroupCmdReplyList rl = new GroupCmdReplyList();
        AsynchRequest ar = (AsynchRequest)arp.get(rid_obj);
        if (ar == null) {
            DevError[] errors = new DevError[1];
            errors[0] = new DevError();
            errors[0].severity = ErrSeverity.ERR;
            errors[0].reason = "API_BadAsynPollId";
            errors[0].desc = "Invalid asynch. request identifier specified";
            errors[0].origin = "GroupDeviceElement.command_inout_reply";
            DevFailed e = new DevFailed(errors);
            rl.add(new GroupCmdReply(get_name(), "unknown", e));
            return rl;
        }
        if (ar.req_id == -1) {
            rl.add(new GroupCmdReply(get_name(), ar.obj_name, ar.exception));
            arp.remove(rid_obj);
            return rl;
        }
        try {
            DeviceData dd = proxy.command_inout_reply(ar.req_id, tmo);
            rl.add(new GroupCmdReply(get_name(), ar.obj_name, dd));
        }
        catch (AsynReplyNotArrived na) {
            DevError[] errors = new DevError[1];
            errors[0] = new DevError();
            errors[0].severity = ErrSeverity.ERR;
            errors[0].reason = "API_AsynReplyNotArrived";
            errors[0].desc = "No reply for asynch request";
            errors[0].origin = "GroupDeviceElement.command_inout_reply";
            DevFailed e = new DevFailed(errors);
            rl.add(new GroupCmdReply(get_name(), ar.obj_name, e));
        }
        catch (DevFailed df) {
            rl.add(new GroupCmdReply(get_name(), ar.obj_name, df));
        }
        catch (Exception ex) {
            DevError[] errors = new DevError[1];
            errors[0] = new DevError();
            errors[0].severity = ErrSeverity.ERR;
            errors[0].reason = "unknown exception caught";
            errors[0].desc = "unknown error";
            errors[0].origin = "GroupDeviceElemnt.command_inout";
            DevFailed e = new DevFailed(errors);
            rl.add(new GroupCmdReply(get_name(), ar.obj_name, e));
        }
        arp.remove(rid_obj);
        return rl;
    }
    
    /** read_attribute_asynch_i - access limited to package Group */
    int read_attribute_asynch_i(String a, boolean fwd, int rid) throws DevFailed {
        try {
            int actual_rid = proxy.read_attribute_asynch(a);
            arp.put(new Integer(rid), new AsynchRequest(actual_rid, a));
        }
        catch (DevFailed df) {
            arp.put(new Integer(rid), new AsynchRequest(-1, a, df));
        }
        catch (Exception e) {
            DevError[] errors = new DevError[1];
            errors[0] = new DevError();
            errors[0].severity = ErrSeverity.ERR;
            errors[0].reason = "unknown exception caught";
            errors[0].desc = "unknown error";
            errors[0].origin = "GroupDeviceElemnt.read_attribute";
            DevFailed ex = new DevFailed(errors);
            arp.put(new Integer(rid), new AsynchRequest(-1, a, ex));
        }
        return rid;
    }
    
    /** read_attribute_reply_i - access limited to package Group */
    GroupAttrReplyList read_attribute_reply_i(int rid, int tmo, boolean fwd) throws DevFailed {
        Integer rid_obj = new Integer(rid);
        GroupAttrReplyList rl = new GroupAttrReplyList();
        AsynchRequest ar = (AsynchRequest)arp.get(rid_obj);
        if (ar == null) {
            DevError[] errors = new DevError[1];
            errors[0] = new DevError();
            errors[0].severity = ErrSeverity.ERR;
            errors[0].reason = "API_BadAsynPollId";
            errors[0].desc = "Invalid asynch. request identifier specified";
            errors[0].origin = "GroupDeviceElement.read_attribute_reply";
            DevFailed e = new DevFailed(errors);
            rl.add(new GroupAttrReply(get_name(), "unknown", e));
            return rl;
        }
        if (ar.req_id == -1) {
            rl.add(new GroupAttrReply(get_name(), ar.obj_name, ar.exception));
            arp.remove(rid_obj);
            return rl;
        }
        try {
            DeviceAttribute[] das = proxy.read_attribute_reply(ar.req_id, tmo);
            rl.add(new GroupAttrReply(get_name(), ar.obj_name, das[0]));
        }
        catch (AsynReplyNotArrived na) {
            DevError[] errors = new DevError[1];
            errors[0] = new DevError();
            errors[0].severity = ErrSeverity.ERR;
            errors[0].reason = "API_AsynReplyNotArrived";
            errors[0].desc = "No reply for asynch request";
            errors[0].origin = "GroupDeviceElement.read_attribute_reply";
            DevFailed e = new DevFailed(errors);
            rl.add(new GroupAttrReply(get_name(), ar.obj_name, e));
        }
        catch (DevFailed df) {
            rl.add(new GroupAttrReply(get_name(), ar.obj_name, df));
        }
        catch (Exception ex) {
            DevError[] errors = new DevError[1];
            errors[0] = new DevError();
            errors[0].severity = ErrSeverity.ERR;
            errors[0].reason = "unknown exception caught";
            errors[0].desc = "unknown error";
            errors[0].origin = "GroupDeviceElemnt.read_attribute_reply";
            DevFailed e = new DevFailed(errors);
            rl.add(new GroupAttrReply(get_name(), ar.obj_name, e));
        }
        arp.remove(rid_obj);
        return rl; 
    }
    
    /** read_attribute_asynch_i - access limited to package Group */
    int write_attribute_asynch_i(DeviceAttribute da, boolean fwd, int rid) throws DevFailed {
        try {
            int actual_rid = proxy.write_attribute_asynch(da);
            arp.put(new Integer(rid), new AsynchRequest(actual_rid, da.getName()));
        }
        catch (DevFailed df) {
            arp.put(new Integer(rid), new AsynchRequest(-1, da.getName(), df));
        }
        catch (Exception e) {
            DevError[] errors = new DevError[1];
            errors[0] = new DevError();
            errors[0].severity = ErrSeverity.ERR;
            errors[0].reason = "unknown exception caught";
            errors[0].desc = "unknown error";
            errors[0].origin = "GroupDeviceElemnt.write_attribute";
            DevFailed ex = new DevFailed(errors);
            arp.put(new Integer(rid), new AsynchRequest(-1, da.getName(), ex));
        }
        return rid;
    }
    
    /** read_attribute_reply_i - access limited to package Group */
    GroupReplyList write_attribute_reply_i(int rid, int tmo, boolean fwd) throws DevFailed {
        Integer rid_obj = new Integer(rid);
        GroupReplyList rl = new GroupReplyList();
        AsynchRequest ar = (AsynchRequest)arp.get(rid_obj);
        if (ar == null) {
            DevError[] errors = new DevError[1];
            errors[0] = new DevError();
            errors[0].severity = ErrSeverity.ERR;
            errors[0].reason = "API_BadAsynPollId";
            errors[0].desc = "Invalid asynch. request identifier specified";
            errors[0].origin = "GroupDeviceElement.write_attribute_reply";
            DevFailed e = new DevFailed(errors);
            rl.add(new GroupReply(get_name(), "unknown", e));
            return rl;
        }
        if (ar.req_id == -1) {
            rl.add(new GroupReply(get_name(), ar.obj_name, ar.exception));
            arp.remove(rid_obj);
            return rl;
        }
        try {
            proxy.write_attribute_reply(ar.req_id, tmo);
            rl.add(new GroupReply(get_name(), ar.obj_name));
        }
        catch (AsynReplyNotArrived na) {
            DevError[] errors = new DevError[1];
            errors[0] = new DevError();
            errors[0].severity = ErrSeverity.ERR;
            errors[0].reason = "API_AsynReplyNotArrived";
            errors[0].desc = "No reply for asynch request";
            errors[0].origin = "GroupDeviceElement.write_attribute_reply";
            DevFailed e = new DevFailed(errors);
            rl.add(new GroupReply(get_name(), ar.obj_name, e));
        }
        catch (DevFailed df) {
            rl.add(new GroupReply(get_name(), ar.obj_name, df));
        }
        catch (Exception ex) {
            DevError[] errors = new DevError[1];
            errors[0] = new DevError();
            errors[0].severity = ErrSeverity.ERR;
            errors[0].reason = "unknown exception caught";
            errors[0].desc = "unknown error";
            errors[0].origin = "GroupDeviceElemnt.write_attribute_reply";
            DevFailed e = new DevFailed(errors);
            rl.add(new GroupReply(get_name(), ar.obj_name, e));
        }
        arp.remove(rid_obj);
        return rl;   
    }
    
    /** The underlying DeviceProxy */
    private DeviceProxy proxy;
    
    /** Asynch Request Repository */
    private TreeMap arp;
}
