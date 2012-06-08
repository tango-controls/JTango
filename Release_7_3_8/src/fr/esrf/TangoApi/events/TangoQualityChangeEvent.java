//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
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
// Revision 1.7  2008/10/10 11:34:14  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.6  2008/04/11 08:08:44  pascal_verdier
// *** empty log message ***
//
// Revision 1.5  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:57  ounsy
// updated change from api/java
//
// Revision 1.3  2005/12/02 09:54:04  pascal_verdier
// java import have been optimized.
//
// Revision 1.2  2004/03/19 10:24:34  ounsy
// Modification of the overall Java event client Api for synchronization with tango C++ Release 4
//
// Revision 1.1  2004/03/08 11:43:23  pascal_verdier
// *** empty log message ***
//-======================================================================
/*
 * TangoQualityChangeEvent.java
 *
 * Created on September 26, 2003, 11:55 AM
 */

package fr.esrf.TangoApi.events;


import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;

import java.util.EventObject;

/**
 *
 * @author  pascal_verdier
 */
public class TangoQualityChangeEvent  extends EventObject implements java.io.Serializable {
    
    /** Creates a new instance of TangoOnQualityChangeEvent */
    public TangoQualityChangeEvent(TangoQualityChange source, EventData event_data)
	{
		super(source);
		this.attr_value = event_data.attr_value;
		this.errors = event_data.errors;
	}
    
	public DeviceAttribute getValue() throws DevFailed
	{  
		if (attr_value == null)
		   throw new DevFailed(errors);
		return attr_value;
	}
        
	private DeviceAttribute attr_value;
	private DevError[] errors;
    
}
