//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.1  2007/08/23 09:41:21  ounsy
// Add default impl for tangorb
//
// Revision 3.11  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.10  2005/11/29 05:33:29  pascal_verdier
// extractUChar() and extractUCharArray() methods added.
//
// Revision 3.9  2005/06/13 09:05:18  pascal_verdier
// Minor bugs fixed.
//
// Revision 3.8  2004/12/16 10:16:44  pascal_verdier
// Missing TANGO 5 features added.
//
// Revision 3.7  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.6  2004/05/14 14:21:33  pascal_verdier
// Add timeout at runtime.
// Some little bugs fixed.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.0  2003/04/29 08:03:28  pascal_verdier
// Asynchronous calls added.
// Logging related methods.
// little bugs fixed.
//
// Revision 2.0  2003/01/09 14:00:37  verdier
// jacORB is now the ORB used.
//
// Revision 1.8  2002/06/26 09:02:17  verdier
// tested with atkpanel on a TACO device
//
// Revision 1.7  2002/04/09 12:21:51  verdier
// IDL 2 implemented.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.omg.CORBA.Any;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;


/**
 *	Class Description:
 *	This class manage data object for Tango device history Data access.
 *	<Br><Br>
 *	<Br><b> Usage example: </b> <Br>
 *	<ul><i>
 *		DeviceDataHistory[]	histo = dev.command_history("ReadCurrent", 10); <Br>
 *		for (int i=0 ; i < histo.length ; i++) <Br>
 *		{	<Br><ul>
 *			Date		d = new Date(histo[i].getTime()); <Br>
 *			double[]	values = histo[i].extractDoubleArray(); <Br></ul>
 *		} <Br>
 *	</ul></i>
 *
 * @author  verdier
 * @version  $Revision$
 */

public class DeviceDataHistoryDAODefaultImpl implements IDeviceDataHistoryDAO
{
	private	Any					any;
	private DevCmdHistory		cmd_histo;
	private AttributeValue_3	attrval;
	private TimeVal				tval;
	
	public DeviceDataHistoryDAODefaultImpl()
	{
	}		
	
	//===========================================
	/**
	 *	Constructor from a DevCmdHistory.
	 */
	//===========================================
	public void init(DeviceDataHistory deviceDataHistory, String cmdname, DevCmdHistory cmd_hist) throws DevFailed
	{
		any       = cmd_hist.value;
		deviceDataHistory.source    = COMMAND;
		cmd_histo = cmd_hist;
		deviceDataHistory.name   = cmdname;
		tval   = cmd_histo.time;
		deviceDataHistory.failed = cmd_histo.cmd_failed;
		deviceDataHistory.errors = cmd_histo.errors;
	}
	//===========================================
	/**
	 *	Constructor from an AttributeValue.
	 */
	//===========================================
	public void init(DeviceDataHistory deviceDataHistory, DevAttrHistory att_histo) throws DevFailed
	{
		any     = att_histo.value.value;
		deviceDataHistory.source  = ATTRIBUTE;
		attrval = new AttributeValue_3(any, 
					att_histo.value.quality,
					att_histo.value.time,
					att_histo.value.name,
					new AttributeDim(att_histo.value.dim_x, att_histo.value.dim_y),
					new AttributeDim(0, 0),
					att_histo.errors);
		deviceDataHistory.name    = att_histo.value.name;
		tval    = att_histo.value.time;
		deviceDataHistory.failed  = att_histo.attr_failed;
		deviceDataHistory.errors  = att_histo.errors;
	}

	//===========================================
	/**
	 *	Constructor from an AttributeValue for Device_3Impl.
	 */
	//===========================================
	public void init(DeviceDataHistory deviceDataHistory, DevAttrHistory_3 att_histo) throws DevFailed
	{
		any     = att_histo.value.value;
		deviceDataHistory.source  = ATTRIBUTE;
		attrval = att_histo.value;
		deviceDataHistory.name    = att_histo.value.name;
		tval    = att_histo.value.time;
		deviceDataHistory.failed  = att_histo.attr_failed;
		deviceDataHistory.errors  = att_histo.value.err_list;
	}
		
	//===========================================
	/**
	 *	Return attribute time value.
	 */
	//===========================================
	public TimeVal getTimeVal(DeviceDataHistory deviceDataHistory)
	{
		return tval;
	}
	//===========================================
	/**
	 *	Return attribute time value in seconds since EPOCH.
	 */
	//===========================================
	public long getTimeValSec(DeviceDataHistory deviceDataHistory)
	{
		return (long)tval.tv_sec;
	}
	//===========================================
	/**
	 *	return time in milliseconds since EPOCH
	 *	to build a Date class.
	 */
	//===========================================
	public long getTime(DeviceDataHistory deviceDataHistory)
	{
		return  (long)tval.tv_sec * 1000 + tval.tv_usec/1000;
	}
	//===========================================
	/**
	 *	return AttrQuality if from attribute.
	 */
	//===========================================
	public AttrQuality getAttrQuality(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		if (deviceDataHistory.source==COMMAND)
			Except.throw_non_supported_exception("TangoApi_NOT_AVAILABLE",
									"Method not avalaible for command",
									"DeviceDataHistory.getAttrQuality()");
		return attrval.quality;
	}
	//===========================================
	/**
	 *	Return attribute dim_x if from attribute.
	 */
	//===========================================
	public int getDimX(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		if (deviceDataHistory.source==COMMAND)
			Except.throw_non_supported_exception("TangoApi_NOT_AVAILABLE",
									"Method not avalaible for command",
									"DeviceDataHistory.getDimX()");
		return attrval.r_dim.dim_x;
	}
	//===========================================
	/**
	 *	Return attribute dim_y if from attribute.
	 */
	//===========================================
	public int getDimY(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		if (deviceDataHistory.source==COMMAND)
			Except.throw_non_supported_exception("TangoApi_NOT_AVAILABLE",
									"Method not avalaible for command",
									"DeviceDataHistory.getDimY()");
		return attrval.r_dim.dim_y;
	}



	//**********	Extract Methods for basic types	*********************


	//===========================================
	/**
	 *	extract method for a CORBA Any.
	 */
	//===========================================
	public Any extractAny(DeviceDataHistory deviceDataHistory)
	{
		return any;
	}
	//===========================================
	/**
	 *	extract method for a boolean.
	 */
	//===========================================
	public boolean extractBoolean(DeviceDataHistory deviceDataHistory)
	{
		return DevBooleanHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for an unsigned char.
	 *	@return	the extracted value.
	 */
	//===========================================
	public short extractUChar(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		short[]	array = extractUCharArray(deviceDataHistory);
		return array[0];
	}
	//===========================================
	/**
	 *	extract method for a short.
	 */
	//===========================================
	public short extractShort(DeviceDataHistory deviceDataHistory)
	{
		if (deviceDataHistory.source==ATTRIBUTE)
		{
			short[]	array = extractShortArray(deviceDataHistory);
			return array[0];
		}
		else
			return DevShortHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for an unsigned short.
	 */
	//===========================================
	public short extractUShort(DeviceDataHistory deviceDataHistory)
	{
		return DevUShortHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a long.
	 */
	//===========================================
	public int extractLong(DeviceDataHistory deviceDataHistory)
	{
		if (deviceDataHistory.source==ATTRIBUTE)
		{
			int[]	array = extractLongArray(deviceDataHistory);
			return array[0];
		}
		else
			return DevLongHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for an unsigned long.
	 */
	//===========================================
	public int extractULong(DeviceDataHistory deviceDataHistory)
	{
		return DevULongHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a float.
	 */
	//===========================================
	public float extractFloat(DeviceDataHistory deviceDataHistory)
	{
		return DevFloatHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a double.
	 */
	//===========================================
	public double extractDouble(DeviceDataHistory deviceDataHistory)
	{
		if (deviceDataHistory.source==ATTRIBUTE)
		{
			double[]	array = extractDoubleArray(deviceDataHistory);
			return array[0];
		}
		else
			return DevDoubleHelper.extract(any);
	}

	//===========================================
	/**
	 *	extract method for a String.
	 */
	//===========================================
	public String extractString(DeviceDataHistory deviceDataHistory)
	{
		if (deviceDataHistory.source==ATTRIBUTE)
		{
			String[]	array = extractStringArray(deviceDataHistory);
			return array[0];
		}
		else
			return DevStringHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a DevState.
	 */
	//===========================================
	public DevState extractDevState(DeviceDataHistory deviceDataHistory)
	{
		return DevStateHelper.extract(any);
	}



	//**********	Extract Methods for sequence types	*********************


	//===========================================
	/**
	 *	extract method for a byte Array.
	 */
	//===========================================
	public boolean[] extractBooleanArray(DeviceDataHistory deviceDataHistory)
	{
		return DevVarBooleanArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a byte Array.
	 */
	//===========================================
	public byte[] extractByteArray(DeviceDataHistory deviceDataHistory)
	{
		return DevVarCharArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for an unsigned char Array.
	 *
	 *	@return	the extracted value.
	 */
	//===========================================
	public short[] extractUCharArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		byte[]	argout = DevVarCharArrayHelper.extract(attrval.value);
		short[]	val = new short[argout.length];
		short	mask = 0xFF;
		for (int i=0 ; i<argout.length ; i++)
			val[i] = (short)(mask & argout[i]);
		return val;
	}
	//===========================================
	/**
	 *	extract method for a short Array.
	 */
	//===========================================
	public short[] extractShortArray(DeviceDataHistory deviceDataHistory)
	{
		return DevVarShortArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for an unsigned short Array.
	 */
	//===========================================
	public short[] extractUShortArray(DeviceDataHistory deviceDataHistory)
	{
		return DevVarUShortArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a long Array.
	 */
	//===========================================
	public int[] extractLongArray(DeviceDataHistory deviceDataHistory)
	{
		return DevVarLongArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for an unsigned long Array.
	 */
	//===========================================
	public int[] extractULongArray(DeviceDataHistory deviceDataHistory)
	{
		return DevVarULongArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a float Array.
	 */
	//===========================================
	public float[] extractFloatArray(DeviceDataHistory deviceDataHistory)
	{
		return DevVarFloatArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a double Array.
	 */
	//===========================================
	public double[] extractDoubleArray(DeviceDataHistory deviceDataHistory)
	{
		return DevVarDoubleArrayHelper.extract(any);
	}

	//===========================================
	/**
	 *	extract method for a String Array.
	 */
	//===========================================
	public String[] extractStringArray(DeviceDataHistory deviceDataHistory)
	{
		if (any==null)	System.out.println("any = null !!");
		return DevVarStringArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a DevVarLongStringArray.
	 */
	//===========================================
	public DevVarLongStringArray extractLongStringArray(DeviceDataHistory deviceDataHistory)
	{
		return DevVarLongStringArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a DevVarDoubleStringArray.
	 */
	//===========================================
	public DevVarDoubleStringArray extractDoubleStringArray(DeviceDataHistory deviceDataHistory)
	{
		return DevVarDoubleStringArrayHelper.extract(any);
	}

	//===========================================
	/**
	 *	Returns the attribute errors list
	 */
	//===========================================
	public DevError[] getErrStack(DeviceDataHistory deviceDataHistory)
	{
		return attrval.err_list;
	}
	//===========================================
	/**
	 *	Returns the attribute type
	 */
	//===========================================
	public TypeCode type(DeviceDataHistory deviceDataHistory)
	{
		return any.type();
	}
	//===========================================
	/**
	 *	Return attribute name.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	//===========================================
	public String getName(DeviceDataHistory deviceDataHistory)
	{
		return attrval.name;
	}
	//===========================================
	//===========================================
	private int DIM_MINI(int x)
	{
		return (x==0) ? 1 : x;
	}
	//===========================================
	/**
	 *	Return number of data read.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	//===========================================
	public int getNbRead(DeviceDataHistory deviceDataHistory)
	{
		return attrval.r_dim.dim_x * DIM_MINI(attrval.r_dim.dim_y);
	}
	//===========================================
	/**
	 *	Return number of data written.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	//===========================================
	public int getNbWritten(DeviceDataHistory deviceDataHistory)
	{
		return attrval.w_dim.dim_x * DIM_MINI(attrval.w_dim.dim_y);
	}
	//===========================================
	/**
	 *	Return attribute written dim_x.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	//===========================================
	public int getWrittenDimX(DeviceDataHistory deviceDataHistory)
	{
		return attrval.w_dim.dim_x;
	}
	//===========================================
	/**
	 *	Return attribute written dim_y.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	//===========================================
	public int getWrittenDimY(DeviceDataHistory deviceDataHistory)
	{
		return attrval.w_dim.dim_y;
	}
	//===========================================
	/**
	 *	Returns attribute Tango type.
	 */
	//===========================================
	public int getType(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		int	type = -1;
		try {
			TypeCode	tc = attrval.value.type();
			//	Special case for test
			if (tc.kind().value()==TCKind._tk_enum)
				return TangoConst.Tango_DEV_STATE;
			
			TypeCode	tc_alias = tc.content_type();
			TypeCode	tc_seq   = tc_alias.content_type();
			TCKind		kind = tc_seq.kind();
			switch(kind.value())
			{
			case TCKind._tk_void:
				type = TangoConst.Tango_DEV_VOID;
				break;
			case TCKind._tk_boolean:
				type = TangoConst.Tango_DEV_BOOLEAN;
				break;
			case TCKind._tk_char:
				type = TangoConst.Tango_DEV_CHAR;
				break;
			case TCKind._tk_octet:
				type = TangoConst.Tango_DEV_UCHAR;
				break;
			case TCKind._tk_short:
				type = TangoConst.Tango_DEV_SHORT;
				break;
			case TCKind._tk_ushort:
				type = TangoConst.Tango_DEV_USHORT;
				break;
			case TCKind._tk_long:
				type = TangoConst.Tango_DEV_LONG;
				break;
			case TCKind._tk_ulong:
				type = TangoConst.Tango_DEV_ULONG;
				break;
			case TCKind._tk_float:
				type = TangoConst.Tango_DEV_FLOAT;
				break;
			case TCKind._tk_double:
				type = TangoConst.Tango_DEV_DOUBLE;
				break;
			case TCKind._tk_string:
				type = TangoConst.Tango_DEV_STRING;
				break;
			}
		}
		catch(org.omg.CORBA.TypeCodePackage.BadKind e)
		{
			Except.throw_exception("Api_TypeCodePackage.BadKind",
						"Bad or unknown type ",
						"DeviceDataHistory.getType()");
		}
		return type;
	}
}
