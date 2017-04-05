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


package fr.esrf.TangoApi;

/**
 *	<b>Class Description:</b><Br>
 *	Exception thrown in case of coommunication failed on timeout.<Br>
 *	Can be instancied by <i>Except.throw_communication_timeout</i> method.
 *
 *	@see fr.esrf.TangoDs.Except
 *	@see fr.esrf.TangoApi.CommunicationFailed
 *
 *
 * @author  verdier
 * @version $Revision: 25296 $
 */

@SuppressWarnings({"CheckedExceptionClass"})
public class CommunicationTimeout extends CommunicationFailed implements ApiDefs, java.io.Serializable
{
	//===================================================================
	/**
	 *	Exception constructor.<Br>
	 *	Can be instancied by <i>Except.throw_communication_timeout</i> method.
	 *
	 *	@see fr.esrf.TangoDs.Except
	 */
	//===================================================================
	public CommunicationTimeout(fr.esrf.Tango.DevError[] errors)
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
		return"fr.esrf.TangoApi.CommunicationTimeout";
	}
}
