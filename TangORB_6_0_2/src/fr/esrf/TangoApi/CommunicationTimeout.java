//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the java api client exception .
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:58  ounsy
// updated change from api/java
//
// Revision 1.1  2007/04/27 09:06:13  pascal_verdier
// *** empty log message ***
//
//
// Copyright 2004 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;
 
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;


/** 
 *	<b>Class Description:</b><Br>
 *	Exception thrown in case of coommunication failed on timeout.<Br>
 *	Can be instancied by <i>Except.throw_communication_timeout</i> method.
 *
 *	@see fr.esrf.TangoDs.Except, fr.esrf.TangoApi.CommunicationFailed
 *
 *
 * @author  verdier
 * @version $Revision$
 */

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
