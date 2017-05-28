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

//- Import TANGO stuffs
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.CommunicationTimeout;

/**
 * TANGO group reply base class
 */
public class GroupReply implements java.io.Serializable{

    /**
     * Enable/disable exceptions
     */
    protected static boolean exception_enabled = true;
    /**
     * Device name
     */
    protected String dev_name;
    /**
     * Obj. name (attribute or command
     */
    protected String obj_name;
    /**
     * Error flag
     */
    protected boolean has_failed;
    /**
     * Exception
     */
    protected DevFailed exception;
    /**
     * TimeOut
     */
    protected boolean has_timeout;
    
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

    /**
     * Enable/disable exceptions - returns previous mode
     */
    static boolean enable_exception(boolean ex_mode) {
        boolean tmp = exception_enabled;
        exception_enabled = ex_mode;
        return tmp;
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
}
