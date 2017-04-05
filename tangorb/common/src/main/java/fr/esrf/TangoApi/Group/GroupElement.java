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
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;

/**
 * TANGO group abstraction base class (abstract) - private class for package
 * Group
 */

abstract class GroupElement implements java.io.Serializable {

    // ** The group element name */
    private final String name;
    // ** Parent element */
    private GroupElement parent;

    /** Creates a new instance of GroupElement */
    GroupElement(final String _name) {
	name = _name;
	parent = null;
    }

    /** Returns the group element name */
    public String get_name() {
	return name;
    }

    /**
     * Returns the group element fully qualified name
     */
    public String get_fully_qualified_name() {
	if (parent != null) {
	    return parent.get_fully_qualified_name() + "." + name;
	}
	return name;
    }

    ;

    /** Returns the group element size - default impl - returns 1 */
    public int get_size(final boolean fwd) {
        return 1;
    }

    ;

    /** Returns parent element - access limited to package Group */
    GroupElement get_parent() {
	return parent;
    }

/**
     * Change parent element to <_parent> then return previous parent - access
     * limited to package Group
     */
    GroupElement set_parent(final GroupElement _parent) {
	final GroupElement previous_parent = parent;
	parent = _parent;
	return previous_parent;
    }

    /**
     * Returns the device list - default impl - returns name - access limited to
     * package Group
     */
    String[] get_device_name_list(final boolean fwd) {
	final String[] dl = new String[1];
	dl[0] = name;
	return dl;
    }

    /** Returns the underlying DeviceProxy - access limited to package Group */
    abstract DeviceProxy get_device_i(String name);

    /**
     * Returns the ith device in the hierarchy - access limited to package Group
     */
    abstract DeviceProxy get_device_i(int i);

    /** Dump element */
    abstract void dump_i(int indent_level);

    /** Ping element */
    abstract boolean ping_i(boolean fwd);

    /** command_inout wrappers - access limited to package Group */
    abstract void set_timeout_millis(int timeout, boolean fwd) throws DevFailed;

    abstract int command_inout_asynch_i(String c, boolean fgt, boolean fwd, int rid) throws DevFailed;

    abstract int command_inout_asynch_i(String c, DeviceData dd, boolean fgt, boolean fwd, int rid) throws DevFailed;

    abstract GroupCmdReplyList command_inout_reply_i(int rid, int tmo, boolean fwd) throws DevFailed;

    /** read_attribute wrappers - access limited to package Group */
    abstract int read_attribute_asynch_i(String a, boolean fwd, int rid) throws DevFailed;

    abstract int read_attribute_asynch_i(String[] a, boolean fwd, int rid) throws DevFailed;

    abstract GroupAttrReplyList read_attribute_reply_i(int rid, int tmo, boolean fwd) throws DevFailed;

    /** read_attribute wrappers - access limited to package Group */
    abstract int write_attribute_asynch_i(DeviceAttribute da, boolean fwd, int rid) throws DevFailed;

    abstract GroupReplyList write_attribute_reply_i(int rid, int tmo, boolean fwd) throws DevFailed;

    /**
     * Find element named <n> in the hierarchy - default impl - access limited
     * to package Group
     */
    GroupElement find(final String n, final boolean fwd) {
	return name_equals(n) ? this : null;
    }

    /** Returns true if <n> equals name or fully_qualified_name, false otherwise */
    protected boolean name_equals(final String n) {
	return n.equalsIgnoreCase(name) || n.equalsIgnoreCase(get_fully_qualified_name());
    }

    /** Returns true if name matches pattern */
    protected boolean name_matches(String pattern) {
	pattern = pattern.toLowerCase().replaceAll("[*]{1}", ".*?");
	return name.toLowerCase().matches(pattern) || get_fully_qualified_name().toLowerCase().matches(pattern);
    }
}
