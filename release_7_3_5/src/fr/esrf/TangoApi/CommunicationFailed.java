//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the java api client exception .
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
// Revision 1.5  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.4  2007/08/23 08:32:59  ounsy
// updated change from api/java
//
// Revision 3.2  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.1  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
//-======================================================================

package fr.esrf.TangoApi;
 
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;


/** 
 *	<b>Class Description:</b><Br>
 *	Exception thrown in case of coommunication failed.<Br>
 *	Can be instancied by <i>Except.throw_communication_failed</i> method.
 *
 *	@see fr.esrf.TangoDs.Except
 *
 *
 * @author  verdier
 * @version $Revision$
 */

public class CommunicationFailed extends DevFailed implements ApiDefs
{
	//===================================================================
	/**
	 *	Exception constructor.<Br>
	 *	Can be instancied by <i>Except.throw_communication_failed</i> method.
	 *
	 *	@see fr.esrf.TangoDs.Except
	 */
	//===================================================================
	public CommunicationFailed(fr.esrf.Tango.DevError[] errors)
	{
		super(errors);
	}

	//===================================================================
	/**
	 *	Return exception name.
	 */
	//===================================================================
	public String toString()
	{
		return"fr.esrf.TangoApi.CommunicationFailed";
	}

	//===================================================================
	/**
	 *	Return full exception.
	 */
	//===================================================================
	public String getStack()
	{
		StringBuffer	sb =
			new StringBuffer("fr.esrf.TangoApi.CommunicationFailed:\n");
		for (int i=0 ; i<errors.length ; i++)
		{
			sb.append("Severity -> ");
			switch (errors[i].severity.value())
			{
			case ErrSeverity._WARN :
				sb.append("WARNING \n");
				break;

			case ErrSeverity._ERR :
				sb.append("ERROR \n");
				break;

			case ErrSeverity._PANIC :
				sb.append("PANIC \n");
				break;

			default :
				sb.append("Unknown severity code");
				break;
			}
			sb.append("Desc     -> " + errors[i].desc   + "\n");
			sb.append("Reason   -> " + errors[i].reason + "\n");
			sb.append("Origin   -> " + errors[i].origin + "\n");

			if (i<errors.length-1)
				sb.append("-------------------------------------------------------------\n");
		}
		return sb.toString();
	}
}
