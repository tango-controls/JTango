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
// Revision 1.5  2008/10/10 11:35:27  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.4  2007/08/23 08:32:59  ounsy
// updated change from api/java
//
// Revision 3.2  2005/12/02 09:55:46  pascal_verdier
// java import have been optimized.
//
// Revision 3.1  2004/09/17 08:02:33  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
//
//-======================================================================

package fr.esrf.TangoDs;
 

import fr.esrf.Tango.DevError;

/** 
 *	Class Description:
 *
 * @author  verdier
 * @version  $Revision$
 */


public class NamedDevFailed
{
	public String		name;
	public long		idx_in_call;
	public DevError[]	err_stack;
	//======================================================================
	NamedDevFailed(DevError[] err, String name, long idx)
	{
		err_stack = err;
		this.name = name;
		idx_in_call = idx;
	}	
}
