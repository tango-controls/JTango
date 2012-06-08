//+=============================================================================
//
// file :               GroupCmdReplyList.java
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

//- Import Java stuffs
import java.util.Vector;

/**
 * TANGO group reply list for command
 */
public class GroupCmdReplyList extends Vector implements java.io.Serializable {
    
    /** Creates a new instance of GroupCmdReplyList */
    public GroupCmdReplyList() {
        has_failed = false;
    }
    
    /** Adds an element to the list */
    public boolean add(Object o) {
        if (o instanceof GroupCmdReply == false) {
            return true;
        }
        GroupCmdReply gcr = (GroupCmdReply)o;
        if (gcr.has_failed) {
            has_failed = true;
        }
        return super.add(o);
    }
    
    /** Resets error flag and clears the list */
    public void reset() {
        removeAllElements();
        has_failed = false;
    }
    
    /** Returns error flag */
    public boolean has_failed() {
        return has_failed;
    }
    
    /** Global error flag */
    boolean has_failed;
}

