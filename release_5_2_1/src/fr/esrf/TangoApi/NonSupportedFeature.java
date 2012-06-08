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
// Revision 3.2  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.1  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
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
 *	Exception thrown in case feature not supported.<Br>
 *	Can be instancied by <i>Except.throw_non_supported_exception</i> method.
 *
 *	@see fr.esrf.TangoDs.Except
 *
 * @author  verdier
 * @version $Revision$
 */

public class NonSupportedFeature extends DevFailed implements ApiDefs
{
	//===================================================================
	/**
	 *	Exception constructor.<Br>
	 *	Can be instancied by <i>Except.throw_non_supported_exception</i> method.
	 *
	 *	@see fr.esrf.TangoDs.Except
	 */
	//===================================================================
	public NonSupportedFeature(fr.esrf.Tango.DevError[] errors)
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
		return"fr.esrf.TangoApi.NonSupportedFeature";
	}

	//===================================================================
	/**
	 *	Return full exception.
	 */
	//===================================================================
	public String getStack()
	{
		StringBuffer	sb =
			new StringBuffer("fr.esrf.TangoApi.NonSupportedFeature:\n");
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
