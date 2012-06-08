//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:	Exception for Asynchronous call.
//
// $Author$
//
// $Version$
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//							 All Rights Reversed
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
			sb.append("Desc     -> " + errors[i].desc   + "\n");
			sb.append("Reason   -> " + errors[i].reason + "\n");
			sb.append("Origin   -> " + errors[i].origin + "\n");

			if (i<errors.length-1)
				sb.append("-------------------------------------------------------------\n");
		}
		return sb.toString();
	}
}
//-----------------------------------------------------------------------------
/* end of $Source$ */
