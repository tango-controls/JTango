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
// Revision 1.8  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.7  2008/12/03 15:39:50  pascal_verdier
// javadoc warnings removed.
//
// Revision 1.6  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:58  ounsy
// updated change from api/java
//
// Revision 1.1  2007/04/27 09:06:13  pascal_verdier
// *** empty log message ***
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
 * @version $Revision$
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
