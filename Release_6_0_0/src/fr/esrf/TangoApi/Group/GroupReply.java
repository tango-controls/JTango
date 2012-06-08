//+=============================================================================
//
// file :               GroupReply.java
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
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.CommunicationTimeout;

/**
 * TANGO group reply base class
 */
public class GroupReply implements java.io.Serializable{
    
    /** Enable/disable exceptions - returns previous mode */
    static boolean enable_exception(boolean ex_mode) {
        boolean tmp = exception_enabled;
        exception_enabled = ex_mode;
        return tmp;
    }
    
    /** Creates a new instance of GroupReply - defauly ctor */
    public GroupReply() {
        dev_name = "unknown";
        obj_name = "unknown";
        has_failed = false;
        exception = null;
    }
    
    /** Creates a new instance of GroupReply */
    public GroupReply(String _dev_name, String _obj_name) {
        dev_name = _dev_name;
        obj_name = _obj_name;
        has_failed = false;
        exception = null;
    }
    
    /** Creates a new instance of GroupReply */
    public GroupReply(String _dev_name, String _obj_name, DevFailed _ex) {
        dev_name = _dev_name;
        obj_name = _obj_name;
        exception = _ex;
        has_failed = true;
        has_timeout = (_ex instanceof CommunicationTimeout);
    }
    
    /** Returns error flag */
    public boolean has_failed() {
        return has_failed;
    }
    /** Returns timeout flag */
    public boolean has_timeout() {
        return has_timeout;
    }
    
    /** Returns associated device name */
    public String dev_name() {
        return dev_name;
    }
    
    /** Returns associated obj. name (i.e command or attribute name)*/
    public String obj_name() {
        return obj_name;
    }
    
    /** Returns the error stack - returns null if has_failed is set to false */
    public DevError[] get_err_stack() {
        return (has_failed && exception != null) ? exception.errors : null;
    }
    
    /** Enable/disable exceptions */
    protected static boolean exception_enabled = true;
    
    /** Device name */
    protected String dev_name;
    
    /** Obj. name (attribute or command */
    protected String obj_name;
    
    /** Error flag */
    protected boolean has_failed;
    
    /** Exception */
    protected DevFailed exception;
    
    /** TimeOut */
    protected boolean has_timeout;
}
