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
