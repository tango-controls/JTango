//+=============================================================================
//
// file :               GroupElement.java
//
// description :        Java source for TANGO Group abstraction.
//
// project :            TANGO
//
// author(s) :          N.Leclercq - SOLEIL
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-=============================================================================

package fr.esrf.TangoApi.Group;

//- Import Tango stuffs
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;


/**
 * TANGO group abstraction base class (abstract) - private class for package Group
 */

abstract class GroupElement implements java.io.Serializable {
    
    /** Creates a new instance of GroupElement */
    GroupElement(String _name) {
        name = _name;
        parent = null;
    }
    
    /** Returns the group element name */
    public String get_name() {
        return name;
    }
    
    /** Returns the group element fully qualified name */
    public String get_fully_qualified_name() {
        if (parent != null) {
            return parent.get_fully_qualified_name() + "." + name;
        }
        return name;
    }
    
    /** Returns the group element size - default impl - returns 1 */
    public int get_size(boolean fwd) {
        return 1;
    }
    
    /** Returns parent element - access limited to package Group */
    GroupElement get_parent() {
        return parent;
    };
    
    /** Change parent element to <_parent> then return previous parent - access limited to package Group */
    GroupElement set_parent(GroupElement _parent) {
        GroupElement previous_parent = parent;
        parent = _parent;
        return previous_parent;
    };
    
    /** Returns the device list  - default impl - returns name - access limited to package Group */
    String[] get_device_name_list(boolean fwd) {
        String[] dl = new String[1];
        dl[0] = name;
        return dl;
    }
    
    /** Returns the underlying DeviceProxy - access limited to package Group */
    abstract DeviceProxy get_device_i(String name);
    
    /** Returns the ith device in the hierarchy - access limited to package Group */
    abstract DeviceProxy get_device_i(int i);
    
    /** Dump element */
    abstract void dump_i(int indent_level);
    
    /** Ping element */
    abstract boolean ping_i(boolean fwd);
    
    /** command_inout wrappers - access limited to package Group */
    abstract void set_timeout_millis(int timeout, boolean fwd) throws DevFailed;
    abstract int command_inout_asynch_i (String c, boolean fgt, boolean fwd, int rid) throws DevFailed; 
    abstract int command_inout_asynch_i (String c, DeviceData dd, boolean fgt, boolean fwd, int rid) throws DevFailed; 
    abstract GroupCmdReplyList command_inout_reply_i (int rid, int tmo, boolean fwd) throws DevFailed; 

    /** read_attribute wrappers - access limited to package Group */
    abstract int read_attribute_asynch_i (String a, boolean fwd, int rid) throws DevFailed; 
    abstract GroupAttrReplyList read_attribute_reply_i (int rid, int tmo, boolean fwd) throws DevFailed; 

    /** read_attribute wrappers - access limited to package Group */
    abstract int write_attribute_asynch_i (DeviceAttribute da, boolean fwd, int rid) throws DevFailed; 
    abstract GroupReplyList write_attribute_reply_i (int rid, int tmo, boolean fwd) throws DevFailed; 

    /** Find element named <n> in the hierarchy - default impl - access limited to package Group */
    GroupElement find(String n, boolean fwd) {
        return name_equals(n)  ? this : null;
    } 
    
    /** Returns true if <n> equals name or fully_qualified_name, false otherwise */  
    protected boolean name_equals(String n) {
        return n.equalsIgnoreCase(name) 
		|| n.equalsIgnoreCase(get_fully_qualified_name());
    }
    
    /** Returns true if name matches pattern */
    protected boolean name_matches(String pattern) {
        pattern = pattern.toLowerCase().replaceAll("[*]{1}",".*?");
        return name.toLowerCase().matches(pattern) 
		|| get_fully_qualified_name().toLowerCase().matches(pattern);
    }
    
    //** The group element name */ 
    private String name;
    
    //** Parent element */
    private GroupElement parent;
}
