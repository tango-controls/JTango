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
// Revision 1.4  2008/10/10 11:33:37  pascal_verdier
// Headers changed for LGPL conformity.
//
//
//-======================================================================

package fr.esrf.TangoApi.Group;

//- Import Java stuffs
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.Database;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoDs.Except;

/**
 * TANGO group abstraction main class
 */

public class Group extends GroupElement implements java.io.Serializable {

    // -- PRIVATE INNER CLASS: GroupElementFactory -----------------------------
    // -------------------------------------------------------------------------
    private final class GroupElementFactory {
	// ======================================================================
	/**
	 * Instanciate the TangoElements which name matches the pattern p
	 * 
	 * @param device
	 *            name pattern (wild card).
	 * @return a vector of GroupElement.
	 */
	// ======================================================================
	public Vector instanciate(final String p) throws DevFailed {
	    // - a vector to store GroupElement
	    String[] dnl = null;
	    // - is <p> a device name or a device name pattern ?
	    if (p.indexOf('*') == -1) {
		// - <p> is a pure device name
		dnl = new String[1];
		dnl[0] = p;
	    } else {
		// - ask the db the list of device matching pattern p
		final Database db = new Database();
		dnl = db.get_device_exported(p);
	    }
	    if (dnl.length == 0) {
		return null;
	    }
	    final Vector ge = new Vector();
	    for (final String element : dnl) {
		ge.add(new GroupDeviceElement(element));
	    }
	    return ge;
	}
    }

    // -- PUBLIC INTERFACE -----------------------------------------------------
    // -------------------------------------------------------------------------

    /** Creates a new instance of Group */
    public Group(final String name) {
	super(name);
	asynch_req_id = 0;
	elements = new Vector();
	factory = new GroupElementFactory();
	arp = new TreeMap();
    }

    /** Dump group */
    public void dump() {
	synchronized (this) {
	    dump_i(0);
	}
    }

    /** Ping the group returns true if all device alive, false otherwise */
    public boolean ping(final boolean fwd) {
	synchronized (this) {
	    return ping_i(fwd);
	}
    }

    /** Returns the group's device list */
    public String[] get_device_list(final boolean fwd) {
	return get_device_name_list(fwd);
    }

    /** Returns the first group named <n> in the hierarchy */
    public Group get_group(final String n) {
	synchronized (this) {
	    return get_group_i(n, true);
	}
    }

    /** Returns the device named <n> in the hierarchy */
    public DeviceProxy get_device(final String n) {
	synchronized (this) {
	    return get_device_i(n);
	}
    }

    /** Returns the <i>th device in the hierarchy */
    public DeviceProxy get_device(final int i) {
	synchronized (this) {
	    return get_device_i(i);
	}
    }

    /** Returns the group's size */
    @Override
    public int get_size(final boolean fwd) {
	synchronized (this) {
	    return get_size_i(fwd);
	}
    }

    /** Adds a group to the group */
    public void add(final Group g) {
	if (g == null) {
	    return;
	}
	synchronized (this) {
	    add_i(g);
	}
    }

    /**
     * Adds devices matching pattern
     * <p>
     * to the group
     * 
     * @throws DevFailed
     */
    public void add(final String p) throws DevFailed {
	synchronized (this) {

	    final Vector v = factory.instanciate(p);
	    final Iterator it = v.iterator();
	    while (it.hasNext()) {
		add_i((GroupElement) it.next());
	    }

	}
    }

    /**
     * Adds devices matching patterns <pl> to the group
     * 
     * @throws DevFailed
     */
    public void add(final String[] pl) throws DevFailed {
	synchronized (this) {
	    for (final String element : pl) {
		if (element != null) {
		    final Vector v = factory.instanciate(element);
		    final Iterator it = v.iterator();
		    while (it.hasNext()) {
			add_i((GroupElement) it.next());
		    }
		}
	    }
	}
    }

    /**
     * Remove elements matching pattern
     * <p>
     * from the group
     */
    public void remove(final String p, final boolean fwd) {
	synchronized (this) {
	    remove_i(p, fwd);
	}
    }

    /** Remove elements matching patterns <pl> from the group */
    public void remove(final String[] pl, final boolean fwd) {
	synchronized (this) {
	    for (final String element : pl) {
		remove_i(element, fwd);
	    }
	}
    }

    /** Remove all elements from the group */
    public void remove_all() {
	synchronized (this) {
	    elements.removeAllElements();
	}
    }

    /** Returns true if the group contains an element named <n>, false otherwise */
    public boolean contains(final String n, final boolean fwd) {
	synchronized (this) {
	    return find_i(n, fwd) != null ? true : false;
	}
    }

    /** set_timeout_millis */
    @Override
    public void set_timeout_millis(final int timeout, final boolean fwd)
	    throws DevFailed {
	synchronized (this) {
	    final Iterator it = elements.iterator();
	    while (it.hasNext()) {
		final GroupElement e = (GroupElement) it.next();
		if (e instanceof GroupDeviceElement || fwd) {
		    e.set_timeout_millis(timeout, fwd);
		}
	    }
	}
    }

    /** command_inout */
    public GroupCmdReplyList command_inout(final String c, final boolean fwd)
	    throws DevFailed {
	synchronized (this) {
	    final int rid = command_inout_asynch_i(c, false, fwd, next_req_id());
	    return command_inout_reply_i(rid, 0, fwd);
	}
    }

    /** command_inout */
    public GroupCmdReplyList command_inout(final String c, final DeviceData dd,
	    final boolean fwd) throws DevFailed {
	synchronized (this) {
	    final int rid = command_inout_asynch_i(c, dd, false, fwd,
		    next_req_id());
	    return command_inout_reply_i(rid, 0, fwd);
	}
    }

    /** get_command_argument_list */
    public DeviceData[] get_command_specific_argument_list(final boolean fwd)
	    throws DevFailed {
	final int al_size = get_size(fwd);
	final DeviceData[] al = new DeviceData[al_size];
	for (int i = 0; i < al_size; i++) {
	    al[i] = new DeviceData();
	}
	return al;
    }

    /** command_inout */
    public GroupCmdReplyList command_inout(final String c,
	    final DeviceData[] dd, final boolean fwd) throws DevFailed {
	synchronized (this) {
	    final int rid = command_inout_asynch_i(c, dd, false, fwd,
		    next_req_id());
	    return command_inout_reply_i(rid, 0, fwd);
	}
    }

    /** command_inout_asynch */
    public int command_inout_asynch(final String c, final boolean fgt,
	    final boolean fwd) throws DevFailed {
	final int rid = command_inout_asynch_i(c, fgt, fwd, next_req_id());
	if (fgt == false) {
	    arp.put(new Integer(rid), new Boolean(fwd));
	}
	return rid;
    }

    /** command_inout_asynch */
    public int command_inout_asynch(final String c, final DeviceData dd,
	    final boolean fgt, final boolean fwd) throws DevFailed {
	final int rid = command_inout_asynch_i(c, dd, fgt, fwd, next_req_id());
	if (fgt == false) {
	    arp.put(new Integer(rid), new Boolean(fwd));
	}
	return rid;
    }

    /** command_inout_asynch */
    public int command_inout_asynch(final String c, final DeviceData[] dd,
	    final boolean fgt, final boolean fwd) throws DevFailed {
	final int rid = command_inout_asynch_i(c, dd, fgt, fwd, next_req_id());
	if (fgt == false) {
	    arp.put(new Integer(rid), new Boolean(fwd));
	}
	return rid;
    }

    /** command_inout_reply */
    public GroupCmdReplyList command_inout_reply(final int rid, final int tmo)
	    throws DevFailed {
	final Integer rid_obj = new Integer(rid);
	final Boolean fwd = (Boolean) arp.get(rid_obj);
	if (fwd == null) {
	    Except.throw_exception("API_BadAsynPollId",
		    "Invalid asynch. request identifier specified",
		    "Group.command_inout_reply");
	}
	arp.remove(rid_obj);
	return command_inout_reply_i(rid, tmo, fwd.booleanValue());
    }

    /** read_attribute */
    public GroupAttrReplyList read_attribute(final String a, final boolean fwd)
	    throws DevFailed {
	synchronized (this) {
	    final int rid = read_attribute_asynch_i(a, fwd, next_req_id());
	    return read_attribute_reply_i(rid, 0, fwd);
	}
    }

    /** read_attribute_asynch */
    public int read_attribute_asynch(final String a, final boolean fwd)
	    throws DevFailed {
	final int rid = read_attribute_asynch_i(a, fwd, next_req_id());
	arp.put(new Integer(rid), new Boolean(fwd));
	return rid;
    }

    /** read_attribute_reply */
    public GroupAttrReplyList read_attribute_reply(final int rid, final int tmo)
	    throws DevFailed {
	final Integer rid_obj = new Integer(rid);
	final Boolean fwd = (Boolean) arp.get(rid_obj);
	if (fwd == null) {
	    Except.throw_exception("API_BadAsynPollId",
		    "Invalid asynch. request identifier specified",
		    "Group.read_inout_reply");
	}
	arp.remove(rid_obj);
	return read_attribute_reply_i(rid, tmo, fwd.booleanValue());
    }

    /** write_attribute */
    public GroupReplyList write_attribute(final DeviceAttribute da,
	    final boolean fwd) throws DevFailed {
	synchronized (this) {
	    final int rid = write_attribute_asynch_i(da, fwd, next_req_id());
	    return write_attribute_reply_i(rid, 0, fwd);
	}
    }

    /** get_command_argument_list */
    public DeviceAttribute[] get_attribute_specific_value_list(final boolean fwd) {
	synchronized (this) {
	    int i = 0;
	    final int av_size = get_size_i(fwd);
	    final DeviceAttribute[] av = new DeviceAttribute[av_size];
	    Iterator it;
	    if (fwd) {
		final Vector h = get_hierarchy();
		it = h.iterator();
		while (it.hasNext()) {
		    av[i++] = new DeviceAttribute(((GroupDeviceElement) it
			    .next()).get_name());
		}
	    } else {
		it = elements.iterator();
		while (it.hasNext()) {
		    final GroupElement e = (GroupElement) it.next();
		    if (e instanceof GroupDeviceElement) {
			av[i++] = new DeviceAttribute(e.get_name());
		    }
		}
	    }
	    return av;
	}
    }

    /** write_attribute */
    public GroupReplyList write_attribute(final DeviceAttribute[] da,
	    final boolean fwd) throws DevFailed {
	synchronized (this) {
	    final int rid = write_attribute_asynch_i(da, fwd, next_req_id());
	    return write_attribute_reply_i(rid, 0, fwd);
	}
    }

    /** write_attribute_asynch */
    public int write_attribute_asynch(final DeviceAttribute da,
	    final boolean fwd) throws DevFailed {
	final int rid = write_attribute_asynch_i(da, fwd, next_req_id());
	arp.put(new Integer(rid), new Boolean(fwd));
	return rid;
    }

    /** write_attribute_asynch */
    public int write_attribute_asynch(final DeviceAttribute[] da,
	    final boolean fwd) throws DevFailed {
	final int rid = write_attribute_asynch_i(da, fwd, next_req_id());
	arp.put(new Integer(rid), new Boolean(fwd));
	return rid;
    }

    /** write_attribute_reply */
    public GroupReplyList write_attribute_reply(final int rid, final int tmo)
	    throws DevFailed {
	final Integer rid_obj = new Integer(rid);
	final Boolean fwd = (Boolean) arp.get(rid_obj);
	if (fwd == null) {
	    Except.throw_exception("API_BadAsynPollId",
		    "Invalid asynch. request identifier specified",
		    "Group.write_attribute_reply");
	}
	arp.remove(rid_obj);
	return write_attribute_reply_i(rid, tmo, fwd.booleanValue());
    }

    // -- PRIVATE INTERFACE ----------------------------------------------------
    // -------------------------------------------------------------------------

    /** Returns the group's device list - access limited to package Group */
    @Override
    String[] get_device_name_list(final boolean fwd) {
	synchronized (this) {
	    int i = 0;
	    final String[] dl = new String[get_size_i(fwd)];
	    final Iterator it = elements.iterator();
	    while (it.hasNext()) {
		final GroupElement e = (GroupElement) it.next();
		if (e instanceof GroupDeviceElement || fwd) {
		    final String[] sub_dl = e.get_device_name_list(fwd);
		    for (int j = 0; j < sub_dl.length; j++, i++) {
			dl[i] = sub_dl[j];
		    }
		}
	    }
	    return dl;
	}
    }

    /** Returns the group's hierarchy */
    private Vector get_hierarchy() {
	synchronized (this) {
	    final Vector h = new Vector();
	    final Iterator it = elements.iterator();
	    while (it.hasNext()) {
		final GroupElement e = (GroupElement) it.next();
		if (e instanceof GroupDeviceElement) {
		    h.add(e);
		} else {
		    h.add(((Group) e).get_hierarchy());
		}
	    }
	    return h;
	}
    }

    /** Returns the group's size - internal impl */
    private int get_size_i(final boolean fwd) {
	int size = 0;
	final Iterator it = elements.iterator();
	while (it.hasNext()) {
	    final GroupElement e = (GroupElement) it.next();
	    if (e instanceof GroupDeviceElement || fwd) {
		size += e.get_size(true);
	    }
	}
	return size;
    }

    /** Returns the group named <n> or null if no such group - internal impl */
    private Group get_group_i(final String n, final boolean fwd) {
	final GroupElement e = find_i(n, fwd);
	return e != null && e instanceof Group ? (Group) e : null;
    }

    /** Returns the first element named <n> or null if no such element */
    @Override
    GroupElement find(final String n, final boolean fwd) {
	synchronized (this) {
	    return find_i(n, fwd);
	}
    }

    /** Returns the element named <n> or null if no such element - internal impl */
    private GroupElement find_i(final String n, final boolean fwd) {
	if (name_equals(n)) {
	    return this;
	}
	GroupElement e = null;
	Iterator it = elements.iterator();
	while (it.hasNext()) {
	    e = (GroupElement) it.next();
	    if (e.name_equals(n)) {
		return e;
	    }
	}
	if (fwd) {
	    it = elements.iterator();
	    while (it.hasNext()) {
		e = ((GroupElement) it.next()).find(n, fwd);
		if (e != null) {
		    return e;
		}
	    }
	}
	return null;
    }

    /** Adds an element to the group */
    private boolean add_i(final GroupElement e) {
	if (e == null || e == this) {
	    // -DEBUG
	    System.out.println("Group::add_i::failed to add " + e.get_name()
		    + " (null or self)");
	    return false;
	}
	final GroupElement ge = find_i(e.get_name(), e instanceof Group ? false
		: true);
	if (ge != null && ge != this) {
	    // -DEBUG
	    System.out.println("Group::add_i::failed to add " + e.get_name()
		    + " (already attached)");
	    return false;
	}
	elements.add(e);
	e.set_parent(this);
	return true;
    }

    /**
     * Remove elements matching pattern
     * <p>
     * from the group
     */
    private void remove_i(final String p, final boolean fwd) {
	if (name_equals(p)) {
	    System.out.println("Group::remove_i::failed to remove " + p
		    + " (can't remove self)");
	    return;
	}
	if (p.indexOf('*') == -1) {
	    final Iterator it = elements.iterator();
	    while (it.hasNext()) {
		final GroupElement e = (GroupElement) it.next();
		if (e.name_equals(p)) {
		    elements.remove(e);
		    break;
		}
	    }
	} else {
	    final Vector remove_list = new Vector();
	    Iterator it = elements.iterator();
	    while (it.hasNext()) {
		final GroupElement e = (GroupElement) it.next();
		if (e.name_matches(p)) {
		    remove_list.add(e);
		}
	    }
	    it = remove_list.iterator();
	    while (it.hasNext()) {
		elements.remove(it.next());
	    }
	}
	if (fwd == true) {
	    final Iterator it = elements.iterator();
	    while (it.hasNext()) {
		final GroupElement e = (GroupElement) it.next();
		if (e instanceof Group) {
		    ((Group) e).remove(p, fwd);
		}
	    }
	}
    }

    /**
     * Returns the <i>th device in the hierarchy - access limited to package
     * Group
     */
    @Override
    DeviceProxy get_device_i(int i) {
	synchronized (this) {
	    DeviceProxy dp = null;
	    final Iterator it = elements.iterator();
	    while (dp == null && it.hasNext()) {
		final GroupElement e = (GroupElement) it.next();
		dp = e.get_device_i(i--);
	    }
	    return dp;
	}
    }

    /** Returns device <n> in the hierarchy - access limited to package Group */
    @Override
    DeviceProxy get_device_i(final String n) {
	DeviceProxy dp = null;
	final Iterator it = elements.iterator();
	while (dp == null && it.hasNext()) {
	    final GroupElement e = (GroupElement) it.next();
	    dp = e.get_device_i(n);
	}
	return dp;
    }

    /** Dump group - access limited to package Group */
    @Override
    void dump_i(final int indent_level) {
	for (int i = 0; i < indent_level; i++) {
	    System.out.print("  ");
	}
	System.out.println("|- Group: " + get_name() + " [" + get_size_i(true)
		+ ":" + get_size_i(false) + "]");
	final Iterator it = elements.iterator();
	while (it.hasNext()) {
	    ((GroupElement) it.next()).dump_i(indent_level + 1);
	}
    }

    /** Ping the group - access limited to package Group */
    @Override
    boolean ping_i(final boolean fwd) {
	boolean all_alive = true;
	final Iterator it = elements.iterator();
	while (it.hasNext() && all_alive) {
	    final GroupElement e = (GroupElement) it.next();
	    if (e instanceof GroupDeviceElement || fwd) {
		all_alive = e.ping_i(fwd) ? all_alive : false;
	    }
	}
	return all_alive;
    }

    /** command_inout_asynch_i - access limited to package Group */
    @Override
    int command_inout_asynch_i(final String c, final boolean fgt,
	    final boolean fwd, final int rid) throws DevFailed {
	synchronized (this) {
	    final Iterator it = elements.iterator();
	    while (it.hasNext()) {
		final GroupElement e = (GroupElement) it.next();
		if (e instanceof GroupDeviceElement || fwd) {
		    e.command_inout_asynch_i(c, fgt, fwd, rid);
		}
	    }
	    return rid;
	}
    }

    /** command_inout_i - access limited to package Group */
    @Override
    int command_inout_asynch_i(final String c, final DeviceData dd,
	    final boolean fgt, final boolean fwd, final int rid)
	    throws DevFailed {
	synchronized (this) {
	    final Iterator it = elements.iterator();
	    while (it.hasNext()) {
		final GroupElement e = (GroupElement) it.next();
		if (e instanceof GroupDeviceElement || fwd) {
		    e.command_inout_asynch_i(c, dd, fgt, fwd, rid);
		}
	    }
	    return rid;
	}
    }

    /** command_inout_asynch_i - access limited to package Group */
    private int command_inout_asynch_i(final String c, final DeviceData[] dd,
	    final boolean fgt, final boolean fwd, final int rid)
	    throws DevFailed {
	synchronized (this) {
	    final int gsize = get_size_i(fwd);
	    if (gsize != dd.length) {
		final String desc = "the size of the input argument list must equal the number of device in the group"
			+ " [expected:" + gsize + " - got:" + dd.length + "]";
		Except.throw_exception("API_MethodArgument", desc,
			"Group.command_inout_asynch");
	    }
	    int i = 0;
	    Iterator it;
	    if (fwd) {
		final Vector h = get_hierarchy();
		it = h.iterator();
		while (it.hasNext()) {
		    ((GroupDeviceElement) it.next()).command_inout_asynch_i(c,
			    dd[i++], fgt, fwd, rid);
		}
	    } else {
		it = elements.iterator();
		while (it.hasNext()) {
		    final GroupElement e = (GroupElement) it.next();
		    if (e instanceof GroupDeviceElement) {
			e.command_inout_asynch_i(c, dd[i++], fgt, fwd, rid);
		    }
		}
	    }
	    return rid;
	}
    }

    /** command_inout_reply_i - access limited to package Group */
    @Override
    GroupCmdReplyList command_inout_reply_i(final int rid, final int tmo,
	    final boolean fwd) throws DevFailed {
	synchronized (this) {
	    final GroupCmdReplyList reply = new GroupCmdReplyList();
	    GroupCmdReplyList sub_reply;
	    final Iterator it = elements.iterator();
	    while (it.hasNext()) {
		final GroupElement ge = (GroupElement) it.next();
		if (ge instanceof GroupDeviceElement || fwd) {
		    sub_reply = ge.command_inout_reply_i(rid, tmo, fwd);
		    if (sub_reply.isEmpty() == false) {
			reply.addAll(sub_reply);
		    }
		    if (sub_reply.has_failed()) {
			reply.has_failed = true;
		    }
		}
	    }
	    return reply;
	}
    }

    /** read_attribute_asynch_i - access limited to package Group */
    @Override
    int read_attribute_asynch_i(final String a, final boolean fwd, final int rid)
	    throws DevFailed {
	synchronized (this) {
	    final Iterator it = elements.iterator();
	    while (it.hasNext()) {
		final GroupElement e = (GroupElement) it.next();
		if (e instanceof GroupDeviceElement || fwd) {
		    e.read_attribute_asynch_i(a, fwd, rid);
		}
	    }
	    return rid;
	}
    }

    /** read_attribute_reply_i - access limited to package Group */
    @Override
    GroupAttrReplyList read_attribute_reply_i(final int rid, final int tmo,
	    final boolean fwd) throws DevFailed {
	synchronized (this) {
	    final GroupAttrReplyList reply = new GroupAttrReplyList();
	    GroupAttrReplyList sub_reply;
	    final Iterator it = elements.iterator();
	    while (it.hasNext()) {
		final GroupElement ge = (GroupElement) it.next();
		if (ge instanceof GroupDeviceElement || fwd) {
		    sub_reply = ge.read_attribute_reply_i(rid, tmo, fwd);
		    if (sub_reply.isEmpty() == false) {
			reply.addAll(sub_reply);
		    }
		    if (sub_reply.has_failed()) {
			reply.has_failed = true;
		    }
		}
	    }
	    return reply;
	}
    }

    /** write_attribute_asynch_i - access limited to package Group */
    @Override
    int write_attribute_asynch_i(final DeviceAttribute da, final boolean fwd,
	    final int rid) throws DevFailed {
	synchronized (this) {
	    final Iterator it = elements.iterator();
	    while (it.hasNext()) {
		final GroupElement e = (GroupElement) it.next();
		if (e instanceof GroupDeviceElement || fwd) {
		    e.write_attribute_asynch_i(da, fwd, rid);
		}
	    }
	    return rid;
	}
    }

    /** write_attribute_asynch_i - access limited to package Group */
    private int write_attribute_asynch_i(final DeviceAttribute da[],
	    final boolean fwd, final int rid) throws DevFailed {
	synchronized (this) {
	    final int gsize = get_size_i(fwd);
	    if (gsize != da.length) {
		final String desc = "the size of the input argument list must equal the number of device in the group"
			+ " [expected:" + gsize + " - got:" + da.length + "]";
		Except.throw_exception("API_MethodArgument", desc,
			"Group.write_attribute_asynch");
	    }
	    int i = 0;
	    Iterator it;
	    if (fwd) {
		final Vector h = get_hierarchy();
		it = h.iterator();
		while (it.hasNext()) {
		    ((GroupDeviceElement) it.next()).write_attribute_asynch_i(
			    da[i++], fwd, rid);
		}
	    } else {
		it = elements.iterator();
		while (it.hasNext()) {
		    final GroupElement e = (GroupElement) it.next();
		    if (e instanceof GroupDeviceElement) {
			e.write_attribute_asynch_i(da[i++], fwd, rid);
		    }
		}
	    }
	    return rid;
	}
    }

    /** write_attribute_reply_i - access limited to package Group */
    @Override
    GroupReplyList write_attribute_reply_i(final int rid, final int tmo,
	    final boolean fwd) throws DevFailed {
	synchronized (this) {
	    final GroupReplyList reply = new GroupReplyList();
	    GroupReplyList sub_reply;
	    final Iterator it = elements.iterator();
	    while (it.hasNext()) {
		final GroupElement ge = (GroupElement) it.next();
		if (ge instanceof GroupDeviceElement || fwd) {
		    sub_reply = ge.write_attribute_reply_i(rid, tmo, fwd);
		    if (sub_reply.isEmpty() == false) {
			reply.addAll(sub_reply);
		    }
		    if (sub_reply.has_failed()) {
			reply.has_failed = true;
		    }
		}
	    }
	    return reply;
	}
    }

    /** Pseudo asynch. request generator */
    private int next_req_id() {
	asynch_req_id = asynch_req_id++ % java.lang.Integer.MAX_VALUE;
	return asynch_req_id;
    }

    /** Group's elements repository */
    private final Vector elements;

    /** Pseudo asynch. request id generator */
    private int asynch_req_id;

    /** Group element factory */
    private final GroupElementFactory factory;

    /** Asynch request repository */
    private final TreeMap arp;
}
