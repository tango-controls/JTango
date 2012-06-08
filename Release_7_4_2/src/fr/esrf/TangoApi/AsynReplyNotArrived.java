//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:	Exception for Asynchronous call.
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
//
// $Version: $
//
// $Log$
// Revision 1.7  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.6  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
//
//-======================================================================

package fr.esrf.TangoApi;


import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;

/** 
 *	<b>Class Description:</b><Br>
 *	Exception thrown in case of asynchronous call reply did not arrived.<Br>
 *	Can be instancied by <i>Except.</i> method.
 *
 *	@see fr.esrf.TangoDs.Except
 *
 * @author  verdier
 * @version $Revision$
 */
@SuppressWarnings({"CheckedExceptionClass"})
public class AsynReplyNotArrived extends DevFailed implements java.io.Serializable
{
	//===================================================================
	/**
	 *	Exception constructor.<Br>
	 *	Can be instancied by <i>Except.throw_asyn_reply_not_arrived</i> method.
	 *
	 *	@see fr.esrf.TangoDs.Except
	 */
	//===================================================================
	public AsynReplyNotArrived(fr.esrf.Tango.DevError[] errors)
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
		return "fr.esrf.TangoApi.AsynReplyNotArrived";
	}
	//===================================================================
	/**
	 *	Return full exception.
	 */
	//===================================================================
	public String getStack()
	{
		StringBuffer	sb =
			new StringBuffer("fr.esrf.TangoApi.AsynReplyNotArrived:\n");
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
			sb.append("Desc     -> ").append(errors[i].desc).append("\n");
			sb.append("Reason   -> ").append(errors[i].reason).append("\n");
			sb.append("Origin   -> ").append(errors[i].origin).append("\n");

			if (i<errors.length-1)
				sb.append("-------------------------------------------------------------\n");
		}
		return sb.toString();
	}
}
//-----------------------------------------------------------------------------
/* end of $Source$ */
