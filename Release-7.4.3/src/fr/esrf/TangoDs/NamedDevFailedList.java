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
// Revision 1.6  2009/01/16 12:52:13  pascal_verdier
// IntelliJIdea warnings removed.
//
// Revision 1.5  2008/10/10 11:35:27  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.4  2007/08/23 08:32:59  ounsy
// updated change from api/java
//
// Revision 3.4  2005/12/02 09:55:46  pascal_verdier
// java import have been optimized.
//
// Revision 3.3  2004/11/12 10:27:45  pascal_verdier
// *** empty log message ***
//
// Revision 3.2  2004/11/05 12:08:50  pascal_verdier
// Use now JacORB_2_2_1.
//
// Revision 3.1  2004/09/17 08:02:33  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
//
//-======================================================================

package fr.esrf.TangoDs;
 

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;
import fr.esrf.Tango.MultiDevFailed;

import java.util.Vector;

/** 
 *	Class Description:	This class extends fr.esrf.Tango.DevFailed.
 *	WARNING :	DO NOT FORGET to remove final on class
 *                   fr.esrf.Tango.DevFailed.
 *
 * @author  verdier
 * @version  $Revision$
 */


@SuppressWarnings({"CheckedExceptionClass"})
public class NamedDevFailedList extends DevFailed
{
	private final Vector	err_list;	//	NamedDevFailed
	//==========================================================================
	//==========================================================================
	public NamedDevFailedList(MultiDevFailed corba_ex, String dev_name, String op_name, String reason)
	{
		err_list = new Vector();
		int	nb_obj_failed = corba_ex.errors.length;
	
		for (int i=0 ; i<nb_obj_failed ; i++)
		{
			NamedDevFailed	tmp_err =
				new NamedDevFailed(corba_ex.errors[i].err_list,
				       				corba_ex.errors[i].name,
				       				corba_ex.errors[i].index_in_call);
			err_list.add(tmp_err);
		}
		//	Build an exception summary (as string) in the DevFailed part
		//	of this exception.
		String	desc =
			"Failed to execute " + op_name + " on device " + dev_name +
			", object(s) ";
		for (int i=0 ; i<nb_obj_failed ; i++)
		{
			desc += corba_ex.errors[i].name;
			if (i != nb_obj_failed - 1)
				desc += ", ";
		}

		errors = new DevError[1];
		errors[0] = new DevError();
		errors[0].severity = ErrSeverity.ERR;
		errors[0].reason = reason;
		errors[0].origin = op_name;
		errors[0].desc   = desc;
		System.out.println("NamedDevFailedList\n" + desc);
	}
	//==========================================================================
	//==========================================================================
	public int get_faulty_attr_nb()
	{
		return err_list.size();
	}
	//==========================================================================
	//==========================================================================
	public NamedDevFailed elementAt(int i)
	{
		return (NamedDevFailed)err_list.elementAt(i);
	}
	//==========================================================================
	//==========================================================================
	public boolean call_failed()
	{
		return ((err_list.size()==0) && (errors.length!=0));
	}
}
