//+=============================================================================
//
// file :               GroupAttrReply.java
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

//- Import TANGO stuffs
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;


/**
 * TANGO group reply for attribute
 */
public class GroupAttrReply extends GroupReply implements java.io.Serializable {
    
    /** Creates a new instance of GroupAttrReply */
    public GroupAttrReply() {
        super();
    }
    
    /** Creates a new instance of GroupAttrReply */
    public GroupAttrReply(String _dev_name, String _obj_name, DeviceAttribute _data) {
        super(_dev_name, _obj_name);
        data = _data;
    }
    
    /** Creates a new instance of GroupAttrReply */
    public GroupAttrReply(String _dev_name, String _obj_name, DevFailed _ex) {
        super(_dev_name, _obj_name, _ex);
        data = null;
    }
    
    /** Returns the associated data - returns null if has_failed set to true */
    public DeviceAttribute get_data() throws DevFailed {
        if (exception_enabled && has_failed) {
          throw exception;
        }
        return data;
    }
    
    /** The command reply data */
    private DeviceAttribute data;
}