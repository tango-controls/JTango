//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the AttrWrittenEvent class definition .
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
// Revision 1.6  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:58  ounsy
// updated change from api/java
//
// Revision 3.7  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.0  2003/04/29 08:03:28  pascal_verdier
// Asynchronous calls added.
// Logging related methods.
// little bugs fixed.
//
//-======================================================================

package fr.esrf.TangoApi;
 
import fr.esrf.Tango.DevError;

public class  AttrWrittenEvent implements java.io.Serializable
{
	public DeviceProxy 	device;
	public String[]		attr_names;
	public DevError[]	errors;
	public boolean		err;

	//===============================================================
	//===============================================================
	public AttrWrittenEvent(DeviceProxy dev, String[] att, DevError[] err_in)
	{
		device   = dev;
		attr_names = att;
		errors   = err_in;
		if (errors==null)
			err = false;
		else
			err = (errors.length!=0) ;
	}
}
