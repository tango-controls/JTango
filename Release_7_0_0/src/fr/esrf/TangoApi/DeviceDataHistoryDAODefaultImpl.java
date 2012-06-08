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
// Revision 1.7  2009/01/16 12:42:58  pascal_verdier
// IntelliJIdea warnings removed.
// Bug in State attribute and Quality extraction fixed.
//
// Revision 1.6  2008/12/03 15:44:26  pascal_verdier
// javadoc warnings removed.
//
// Revision 1.5  2008/10/10 11:28:31  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.4  2008/09/24 09:21:38  pascal_verdier
// Attribute history for DevEncoded type added.
//
// Revision 1.3  2008/09/12 11:32:14  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.2  2008/01/10 15:39:42  ounsy
// Resolving a multi connection problem when accessing to a device
//
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
		any                      = cmd_hist.value;
		deviceDataHistory.source = TangoConst.COMMAND;
		deviceDataHistory.name   = cmdname;
		tval                     = cmd_hist.time;
		deviceDataHistory.failed = cmd_hist.cmd_failed;
		deviceDataHistory.errors = cmd_hist.errors;
	}
	//===========================================
	/**
	 *	Constructor from an AttributeValue.
	 */
	//===========================================
	public void init(DeviceDataHistory deviceDataHistory, DevAttrHistory att_histo) throws DevFailed
	{
		any                       = att_histo.value.value;
		deviceDataHistory.source  = TangoConst.ATTRIBUTE;
		attrval = new AttributeValue_3(any, 
					att_histo.value.quality,
					att_histo.value.time,
					att_histo.value.name,
					new AttributeDim(att_histo.value.dim_x, att_histo.value.dim_y),
					new AttributeDim(0, 0),
					att_histo.errors);
		deviceDataHistory.name    = att_histo.value.name;
		tval                      = att_histo.value.time;
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
		any                      = att_histo.value.value;
		deviceDataHistory.source = TangoConst.ATTRIBUTE;
		attrval                  = att_histo.value;
		deviceDataHistory.name   = att_histo.value.name;
		tval                     = att_histo.value.time;
		deviceDataHistory.failed = att_histo.attr_failed;
		deviceDataHistory.errors = att_histo.value.err_list;
	}
	//===========================================
	/**
	 *	Constructor from an AttributeValue for Device_4Impl.
	 */
	//===========================================
	public void init(DeviceDataHistory deviceDataHistory, String name, int source, TimeVal time) throws DevFailed
	{
		any                      = null;
		deviceDataHistory.source = source;
		attrval = new AttributeValue_3(null, null, time, name, null, null, null);
		deviceDataHistory.name   = name;
		tval                     = time;
		deviceDataHistory.failed = false;
		deviceDataHistory.errors = null;
	}
	//===========================================
	/**
	 *	Constructor from an AttributeValue for Device_4Impl.
	 */
	//===========================================
	public void init(DeviceDataHistory deviceDataHistory, String name, int source, long t) throws DevFailed
	{
		any                      = null;
		deviceDataHistory.source = source;
		int	sec  = (int) (t/1000);
		int usec = (int) ((t-1000*sec)*1000);
		TimeVal	time = new TimeVal(sec, usec, 0);
		attrval = new AttributeValue_3(null, null, time, name, null, null, null);
		deviceDataHistory.name   = name;
		tval                     = time;
		deviceDataHistory.failed = false;
		deviceDataHistory.errors = null;
	}
		
	//===========================================
	/**
	 *	Set attribute time value.
	 */
	//===========================================
	public void setTimeVal(DeviceDataHistory deviceDataHistory, TimeVal tval)
	{
		this.tval = tval;
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
	 *	Set AttrQuality if from attribute.
	 */
	//===========================================
	public void setAttrQuality(DeviceDataHistory deviceDataHistory, AttrQuality q) throws DevFailed
	{
		attrval.quality = q;
	}
	//===========================================
	/**
	 *	return AttrQuality if from attribute.
	 */
	//===========================================
	public AttrQuality getAttrQuality(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		if (deviceDataHistory.source==TangoConst.COMMAND)
			Except.throw_non_supported_exception("TangoApi_NOT_AVAILABLE",
									"Method not avalaible for command",
									"DeviceDataHistory.getAttrQuality()");
		return attrval.quality;
	}
	//===========================================
	/**
	 *	Ret Seturn attribute dim_x if from attribute.
	 */
	//===========================================
	public void setDimX(DeviceDataHistory deviceDataHistory, int dim) throws DevFailed
	{
		if (attrval.r_dim==null)
			attrval.r_dim = new AttributeDim();
		attrval.r_dim.dim_x = dim;
		attrval.r_dim.dim_y = 1;
	}
	//===========================================
	/**
	 *	Ret Seturn attribute dim_y if from attribute.
	 */
	//===========================================
	public void setDimY(DeviceDataHistory deviceDataHistory, int dim) throws DevFailed
	{
		if (attrval.r_dim==null)
			attrval.r_dim = new AttributeDim();
		attrval.r_dim.dim_y = ((dim==0)? 1:dim);
	}
	//===========================================
	/**
	 *	Return attribute dim_x if from attribute.
	 */
	//===========================================
	public int getDimX(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		if (deviceDataHistory.source==TangoConst.COMMAND &&
			attrval.r_dim==null)
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
		if (deviceDataHistory.source==TangoConst.COMMAND &&
			attrval.r_dim==null)
			Except.throw_non_supported_exception("TangoApi_NOT_AVAILABLE",
									"Method not avalaible for command",
									"DeviceDataHistory.getDimY()");
		return attrval.r_dim.dim_y;
	}
	//===========================================
	/**
	 *	Returns true is attribute failed
	 */
	//===========================================
	public boolean hasFailed(DeviceDataHistory deviceDataHistory)
	{
		return deviceDataHistory.failed;
	}
	//===========================================
	/**
	 *	Set the attribute errors list
	 */
	//===========================================
	public void setErrStack(DeviceDataHistory deviceDataHistory, DevError[] err)
	{
		deviceDataHistory.failed = true;
		attrval.err_list = err;
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
	 *	Return attribute name.
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
	 */
	//===========================================
	public int getNbRead(DeviceDataHistory deviceDataHistory)
	{
		return attrval.r_dim.dim_x * DIM_MINI(attrval.r_dim.dim_y);
	}
	//===========================================
	/**
	 *	Return number of data written.
	 */
	//===========================================
	public int getNbWritten(DeviceDataHistory deviceDataHistory)
	{
		return attrval.w_dim.dim_x * DIM_MINI(attrval.w_dim.dim_y);
	}
	//===========================================
	/**
	 *	Set attribute written dim_x.
	 */
	//===========================================
	public void setWrittenDimX(DeviceDataHistory deviceDataHistory, int dim)
	{
		if (attrval.w_dim==null)
			attrval.w_dim = new AttributeDim();
		attrval.w_dim.dim_x = dim;
		attrval.w_dim.dim_y = 1;
	}
	//===========================================
	/**
	 *	Set attribute written dim_y.
	 */
	//===========================================
	public void setWrittenDimY(DeviceDataHistory deviceDataHistory, int dim)
	{
		if (attrval.w_dim==null)
			attrval.w_dim = new AttributeDim();
		attrval.w_dim.dim_y = ((dim==0)? 1:dim);
	}
	//===========================================
	/**
	 *	Return attribute written dim_x.
	 */
	//===========================================
	public int getWrittenDimX(DeviceDataHistory deviceDataHistory)
	{
		return attrval.w_dim.dim_x;
	}
	//===========================================
	/**
	 *	Return attribute written dim_y.
	 */
	//===========================================
	public int getWrittenDimY(DeviceDataHistory deviceDataHistory)
	{
		return attrval.w_dim.dim_y;
	}
	//===========================================
	//===========================================








	//===========================================
	//===========================================





	//===========================================
	/**
	 *	Throws exception if err_list not null.
	 *
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	//===========================================
	private void manageExceptions(String method_name) throws DevFailed
	{
		if (attrval.err_list!=null)
			if (attrval.err_list.length>0)
				throw new WrongData(attrval.err_list);
		if (attrval.quality==AttrQuality.ATTR_INVALID)
			Except.throw_wrong_data_exception("AttrQuality_ATTR_INVALID",
							"Attribute quality factor is INVALID",
							"DeviceAttributeHistory." + method_name + "()");
	}






	//**********	Extract Methods for basic types	*********************


	//===========================================
	/**
	 *	extract method for a CORBA Any.
	 */
	//===========================================
	public Any extractAny(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		return any;
	}
	//===========================================
	/**
	 *	extract method for a boolean.
	 */
	//===========================================
	public boolean extractBoolean(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractBoolean()");
		if (bool_data!=null)	//	Result is not in any
			return bool_data[0];
		else
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
		manageExceptions("extractUChar()");
		if (short_data!=null)	//	Result is not in any
			return short_data[0];
		else
		if (deviceDataHistory.source==TangoConst.ATTRIBUTE)
			return extractUCharArray(deviceDataHistory)[0];
		else
			return extractUChar(deviceDataHistory);
	}
	//===========================================
	/**
	 *	extract method for a short.
	 */
	//===========================================
	public short extractShort(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractShort()");
		if (short_data!=null)	//	Result is not in any
			return short_data[0];
		else
		if (deviceDataHistory.source==TangoConst.ATTRIBUTE)
			return extractShortArray(deviceDataHistory)[0];
		else
			return DevShortHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for an unsigned short.
	 */
	//===========================================
	public short extractUShort(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		return DevUShortHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a long.
	 */
	//===========================================
	public int extractLong(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractLong()");
		if (int_data!=null)	//	Result is not in any
			return int_data[0];
		else
		if (deviceDataHistory.source==TangoConst.ATTRIBUTE)
			return extractLongArray(deviceDataHistory)[0];
		else
			return DevLongHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for an unsigned long.
	 */
	//===========================================
	public int extractULong(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractULong()");
		if (int_data!=null)	//	Result is not in any
			return int_data[0];
		else
		if (deviceDataHistory.source==TangoConst.ATTRIBUTE)
			return extractULongArray(deviceDataHistory)[0];
		else
			return DevULongHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a long.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	//===========================================
	public long extractLong64(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractLong64");
		if (long_data!=null)	//	Result is not in any
			return long_data[0];
		else
			return extractLong64Array(deviceDataHistory)[0];
	}
	//===========================================
	/**
	 *	extract method for a long.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	//===========================================
	public long extractULong64(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractULong64");
		if (long_data!=null)	//	Result is not in any
			return long_data[0];
		else
			return extractULong64Array(deviceDataHistory)[0];
	}

	//===========================================
	/**
	 *	extract method for a float.
	 */
	//===========================================
	public float extractFloat(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractFloat()");
		if (float_data!=null)	//	Result is not in any
			return float_data[0];
		else
		if (deviceDataHistory.source==TangoConst.ATTRIBUTE)
			return extractFloatArray(deviceDataHistory)[0];
		else
			return DevFloatHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a double.
	 */
	//===========================================
	public double extractDouble(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractDouble()");
		if (double_data!=null)	//	Result is not in any
			return double_data[0];
		else
		if (deviceDataHistory.source==TangoConst.ATTRIBUTE)
			return extractDoubleArray(deviceDataHistory)[0];
		else
			return DevDoubleHelper.extract(any);
	}

	//===========================================
	/**
	 *	extract method for a String.
	 */
	//===========================================
	public String extractString(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractString()");
		if (deviceDataHistory.source==TangoConst.ATTRIBUTE)
			return extractStringArray(deviceDataHistory)[0];
		else
			return DevStringHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for an DevState.
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	//===========================================
	public DevState extractDevState(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractDevState");

		if (state_data!=null)	//	Result is not in any
			return state_data[0];
		else
		if (isArray())
			return deviceDataHistory.extractDevStateArray()[0];
		else
			return DevStateHelper.extract(attrval.value);
	}
	//===========================================
	/**
	 *	extract method for an DevEncoded
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	//===========================================
	public DevEncoded extractDevEncoded(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractDevEncoded");

		if (enc_data!=null)	//	Result is not in any
			return enc_data[0];
		else
		if (deviceDataHistory.source==TangoConst.ATTRIBUTE)
			return extractDevEncodedArray(deviceDataHistory)[0];
		else
			return DevEncodedHelper.extract(any);
	}


	//**********	Extract Methods for sequence types	*********************


	//===========================================
	/**
	 *	extract method for a byte Array.
	 */
	//===========================================
	public boolean[] extractBooleanArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractBooleanArray()");
		if (bool_data!=null)	//	Result is not in any
			return bool_data;
		else
			return DevVarBooleanArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a byte Array.
	 */
	//===========================================
	public byte[] extractByteArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractByteArray()");
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
		manageExceptions("extractUCharArray()");
		if (short_data!=null)	//	Result is not in any
			return short_data;
		else
		{
			byte[]	argout = DevVarCharArrayHelper.extract(any);
			short[]	val = new short[argout.length];
			short	mask = 0xFF;
			for (int i=0 ; i<argout.length ; i++)
				val[i] = (short)(mask & argout[i]);
			return val;
		}
	}
	//===========================================
	/**
	 *	extract method for a short Array.
	 */
	//===========================================
	public short[] extractShortArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractShortArray()");
		if (short_data!=null)	//	Result is not in any
			return short_data;
		else
			return DevVarShortArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for an unsigned short Array.
	 */
	//===========================================
	public short[] extractUShortArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractUShortArray()");
		return DevVarUShortArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a long Array.
	 */
	//===========================================
	public int[] extractLongArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractLongArray()");
		if (int_data!=null)	//	Result is not in any
			return int_data;
		else
			return DevVarLongArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for an unsigned long Array.
	 */
	//===========================================
	public int[] extractULongArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractULongArray()");
		if (int_data!=null)	//	Result is not in any
			return int_data;
		else
			return DevVarULongArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a long Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	//===========================================
	public long[] extractLong64Array(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractLong64Array()");
		if (long_data!=null)	//	Result is not in any
			return long_data;
		else
			return DevVarLong64ArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a long Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	//===========================================
	public long[] extractULong64Array(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractULong64Array()");
		if (long_data!=null)	//	Result is not in any
			return long_data;
		else
			return DevVarULong64ArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a float Array.
	 */
	//===========================================
	public float[] extractFloatArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractFloatArray()");
		if (float_data!=null)	//	Result is not in any
			return float_data;
		else
			return DevVarFloatArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a double Array.
	 */
	//===========================================
	public double[] extractDoubleArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractDoubleArray()");
		if (double_data!=null)	//	Result is not in any
			return double_data;
		else
			return DevVarDoubleArrayHelper.extract(any);
	}

	//===========================================
	/**
	 *	extract method for a String Array.
	 */
	//===========================================
	public String[] extractStringArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractStringArray()");

		if (string_data!=null)	//	Result is not in any
			return string_data;
		else
			return DevVarStringArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for an DevState Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	//===========================================
	public DevState[] extractDevStateArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractDevStateArray()");
		if (state_data!=null)	//	Result is not in any
			return state_data;
		else
		try{
			if (isArray())
				return DevVarStateArrayHelper.extract(any);
			else
				 // It is used for state attribute
				return new DevState[] { DevStateHelper.extract(any)};
		}
		catch(org.omg.CORBA.BAD_PARAM e)
		{
			//	If state never been initialized !!!!!
			Except.throw_wrong_data_exception(e.toString(),
						"Exception catched : " + e.toString() +"\n" +
						"Maybe the attribute value has not been initialized",
						"DeviceAttribute.extractDevStateArray()");
		}
		return new DevState[0];//	never used
	}

	//===========================================
	/**
	 *	extract method for a DevEncoded Array.
	 */
	//===========================================
	public DevEncoded[] extractDevEncodedArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		manageExceptions("extractDevEncodedArray()");
		if (enc_data!=null)	//	Result is not in any
			return enc_data;
		else
			return DevVarEncodedArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a DevVarLongStringArray.
	 */
	//===========================================
	public DevVarLongStringArray extractLongStringArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		if (long_string_data!=null)	//	Result is not in any
			return long_string_data;
		else
			return DevVarLongStringArrayHelper.extract(any);
	}
	//===========================================
	/**
	 *	extract method for a DevVarDoubleStringArray.
	 */
	//===========================================
	public DevVarDoubleStringArray extractDoubleStringArray(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		if (double_string_data!=null)	//	Result is not in any
			return double_string_data;
		else
			return DevVarDoubleStringArrayHelper.extract(any);
	}
	//===========================================
	//===========================================







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
	 *	return tru if any is an array
	 */
	//===========================================
	private static boolean isArray(org.omg.CORBA.Any local_any)
	{ 
		boolean retval = true; 
		    
		try 
		{ 
			TypeCode        tc = local_any.type(); 
			TypeCode        tc_alias = tc.content_type(); 
			//TypeCode        tc_seq   =
					tc_alias.content_type();
		} 
		catch(org.omg.CORBA.TypeCodePackage.BadKind e) 
		{ 
			//System.out.println(e); 
			retval = false; 
		} 
		return retval; 
	} 
	//===========================================
	//===========================================
	private boolean isArray() 
	{
		return isArray(any);
	} 
	//===========================================
	/**
	 *	Returns attribute Tango type.
	 */
	//===========================================
	private int	setType = -1;
	public int getType(DeviceDataHistory deviceDataHistory) throws DevFailed
	{
		if (setType!=-1)
			return setType;	//	if initialized, has been inserted from array


		int	type = -1;
		try {
			TypeCode	tc = attrval.value.type();

			//	Check if sequence is empty
			if (tc.kind().value() == TCKind._tk_null)
				return TangoConst.Tango_DEV_VOID;

			//	Special case for state
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
			case TCKind._tk_enum: 
				type = TangoConst.Tango_DEV_STATE; 
				break; 
			case TCKind._tk_struct:
				type = TangoConst.Tango_DEV_ENCODED; 
				break; 
			}
		}
		catch(org.omg.CORBA.TypeCodePackage.BadKind e)
		{
			Except.throw_wrong_data_exception("Api_TypeCodePackage.BadKind",
						"Bad or unknown type ",
						"DeviceDataHistory.getType()");
		}
		return type;
	}


	//==========================================================================
	/*
	 *	Insert methods used since Device_4Impl
	 */
	//==========================================================================

	//==========================================================================
	private boolean[]		bool_data   = null;
	private short[]			short_data  = null;
	private int[]			int_data    = null;
	private long[]			long_data   = null;
	private float[]			float_data  = null;
	private double[]		double_data = null;
	private String[]		string_data = null;
	private DevState[]		state_data  = null;
	private DevEncoded[]	enc_data    = null;
	private DevVarLongStringArray	long_string_data = null;
	private DevVarDoubleStringArray	double_string_data = null;
	//==========================================================================
	//==========================================================================
	public int getDataLength(DeviceDataHistory ddh) throws DevFailed
	{
		// Get the data length for this record
		int r_dim_x = ddh.getDimX();
		int r_dim_y = ddh.getDimY();

		int data_length;
		if (r_dim_y == 0)
			data_length  = r_dim_x;
		else
			data_length  = (r_dim_x * r_dim_y);

		if (ddh.source==TangoConst.ATTRIBUTE)
		{
			int w_dim_x = ddh.getWrittenDimX();
			int w_dim_y = ddh.getWrittenDimY();
			if (w_dim_y == 0)
				data_length += w_dim_x;
			else
				data_length += (w_dim_x * w_dim_y);
		}
		return data_length;
	}
	//==========================================================================
	/**
	 *	Insert values between base and (base-length) if not failed
	 *		and return base for next insertion.
	 *
	 *	@param	ddh DeviceDataHistory object.
	 *	@param	values values to be inserted
	 *	@param	base	base index to start to get values
	 *	@return base index for next insertion.
	 */
	//==========================================================================
	public int insert(DeviceDataHistory ddh, boolean[] values, int base) throws DevFailed
	{
		if (ddh.failed || 
			(ddh.source==TangoConst.ATTRIBUTE &&
			ddh.getAttrQuality()==AttrQuality.ATTR_INVALID))
			return base;

		//	Build an array to be inserted
		int	data_length = ddh.getDataLength();
		bool_data = new boolean[data_length];
		for (int i=0 ; i<data_length ; i++)
			bool_data[i] = values[(base - data_length) + i];

		setType = TangoConst.Tango_DEV_BOOLEAN;
		//	Does not exist !
		//if (ddh.source==TangoConst.COMMAND && data_length>1)
		//	setType = TangoConst.Tango_DEVVAR_BOOLEANARRAY;
		return (base-data_length);
	}
	//==========================================================================
	/**
	 *	Insert values between base and (base-length) if not failed
	 *		and return base for next insertion.
	 *
	 *	@param	ddh DeviceDataHistory object.
	 *	@param	values values to be inserted
	 *	@param	base	base index to start to get values
	 *	@return base index for next insertion.
	 */
	//==========================================================================
	public int insert(DeviceDataHistory ddh, short[] values, int base) throws DevFailed
	{
		if (ddh.failed || 
			(ddh.source==TangoConst.ATTRIBUTE &&
			ddh.getAttrQuality()==AttrQuality.ATTR_INVALID))
			return base;

		//	Build an array to be inserted
		int	data_length = ddh.getDataLength();
		short_data = new short[data_length];
		for (int i=0 ; i<data_length ; i++)
			short_data[i] = values[(base - data_length) + i];

		setType = TangoConst.Tango_DEV_SHORT;
		if (ddh.source==TangoConst.COMMAND && data_length>1)
			setType = TangoConst.Tango_DEVVAR_SHORTARRAY;
		return (base-data_length);
	}
	//==========================================================================
	/**
	 *	Insert values between base and (base-length) if not failed
	 *		and return base for next insertion.
	 *
	 *	@param	ddh DeviceDataHistory object.
	 *	@param	values values to be inserted
	 *	@param	base	base index to start to get values
	 *	@return base index for next insertion.
	 */
	//==========================================================================
	public int insert(DeviceDataHistory ddh, int[] values, int base) throws DevFailed
	{
		if (ddh.failed || 
			(ddh.source==TangoConst.ATTRIBUTE &&
			ddh.getAttrQuality()==AttrQuality.ATTR_INVALID))
			return base;

		//	Build an array to be inserted
		int	data_length = ddh.getDataLength();
		int_data = new int[data_length];
		for (int i=0 ; i<data_length ; i++)
			int_data[i] = values[(base - data_length) + i];

		setType = TangoConst.Tango_DEV_LONG;
		if (ddh.source==TangoConst.COMMAND && data_length>1)
			setType = TangoConst.Tango_DEVVAR_LONGARRAY;
		return (base-data_length);
	}

	//==========================================================================
	/**
	 *	Insert values between base and (base-length) if not failed
	 *		and return base for next insertion.
	 *
	 *	@param	ddh DeviceDataHistory object.
	 *	@param	values values to be inserted
	 *	@param	base	base index to start to get values
	 *	@return base index for next insertion.
	 */
	//==========================================================================
	public int insert(DeviceDataHistory ddh, long[] values, int base) throws DevFailed
	{
		if (ddh.failed || 
			(ddh.source==TangoConst.ATTRIBUTE &&
			ddh.getAttrQuality()==AttrQuality.ATTR_INVALID))
			return base;

		//	Build an array to be inserted
		int	data_length = ddh.getDataLength();
		long_data = new long[data_length];
		for (int i=0 ; i<data_length ; i++)
			long_data[i] = values[(base - data_length) + i];

		setType = TangoConst.Tango_DEV_LONG64;
		if (ddh.source==TangoConst.COMMAND && data_length>1)
			setType = TangoConst.Tango_DEVVAR_LONG64ARRAY;
		return (base-data_length);
	}

	//==========================================================================
	/**
	 *	Insert values between base and (base-length) if not failed
	 *		and return base for next insertion.
	 *
	 *	@param	ddh DeviceDataHistory object.
	 *	@param	values values to be inserted
	 *	@param	base	base index to start to get values
	 *	@return base index for next insertion.
	 */
	//==========================================================================
	public int insert(DeviceDataHistory ddh, float[] values, int base) throws DevFailed
	{
		if (ddh.failed || 
			(ddh.source==TangoConst.ATTRIBUTE &&
			ddh.getAttrQuality()==AttrQuality.ATTR_INVALID))
			return base;

		//	Build an array to be inserted
		int	data_length = ddh.getDataLength();
		float_data = new float[data_length];
		for (int i=0 ; i<data_length ; i++)
			float_data[i] = values[(base - data_length) + i];

		setType = TangoConst.Tango_DEV_FLOAT;
		if (ddh.source==TangoConst.COMMAND && data_length>1)
			setType = TangoConst.Tango_DEVVAR_FLOATARRAY;
		return (base-data_length);
	}

	//==========================================================================
	/**
	 *	Insert values between base and (base-length) if not failed
	 *		and return base for next insertion.
	 *
	 *	@param	ddh DeviceDataHistory object.
	 *	@param	values values to be inserted
	 *	@param	base	base index to start to get values
	 *	@return base index for next insertion.
	 */
	//==========================================================================
	public int insert(DeviceDataHistory ddh, double[] values, int base) throws DevFailed
	{
		if (ddh.failed || 
			(ddh.source==TangoConst.ATTRIBUTE &&
			ddh.getAttrQuality()==AttrQuality.ATTR_INVALID))
			return base;

		//	Build an array to be inserted
		int	data_length = ddh.getDataLength();
		double_data = new double[data_length];
		for (int i=0 ; i<data_length ; i++)
			double_data[i] = values[(base - data_length) + i];

		setType = TangoConst.Tango_DEV_DOUBLE;
		if (ddh.source==TangoConst.COMMAND && data_length>1)
			setType = TangoConst.Tango_DEVVAR_DOUBLEARRAY;
		return (base-data_length);
	}
	//==========================================================================
	/**
	 *	Insert values between base and (base-length) if not failed
	 *		and return base for next insertion.
	 *
	 *	@param	ddh DeviceDataHistory object.
	 *	@param	values values to be inserted
	 *	@param	base	base index to start to get values
	 *	@return base index for next insertion.
	 */
	//==========================================================================
	public int insert(DeviceDataHistory ddh, String[] values, int base) throws DevFailed
	{
		if (ddh.failed || 
			(ddh.source==TangoConst.ATTRIBUTE &&
			ddh.getAttrQuality()==AttrQuality.ATTR_INVALID))
			return base;

		//	Build an array to be inserted
		int	data_length = ddh.getDataLength();
		string_data = new String[data_length];
		for (int i=0 ; i<data_length ; i++)
			string_data[i] = values[(base - data_length) + i];

		setType = TangoConst.Tango_DEV_STRING;
		if (ddh.source==TangoConst.COMMAND && data_length>1)
			setType = TangoConst.Tango_DEVVAR_STRINGARRAY;
		return (base-data_length);
	}

	//==========================================================================
	/**
	 *	Insert values between base and (base-length) if not failed
	 *		and return base for next insertion.
	 *
	 *	@param	ddh DeviceDataHistory object.
	 *	@param	values values to be inserted
	 *	@param	base	base index to start to get values
	 *	@return base index for next insertion.
	 */
	//==========================================================================
	public int insert(DeviceDataHistory ddh, DevState[] values, int base) throws DevFailed
	{
		if (ddh.failed || 
			(ddh.source==TangoConst.ATTRIBUTE &&
			ddh.getAttrQuality()==AttrQuality.ATTR_INVALID))
			return base;

		//	Build an array to be inserted
		int	data_length = ddh.getDataLength();
		state_data = new DevState[data_length];
		for (int i=0 ; i<data_length ; i++)
			state_data[i] = values[(base - data_length) + i];

		setType = TangoConst.Tango_DEV_STATE;
		return (base-data_length);
	}
	//==========================================================================
	/**
	 *	Insert values between base and (base-length) if not failed
	 *		and return base for next insertion.
	 *
	 *	@param	ddh DeviceDataHistory object.
	 *	@param	values values to be inserted
	 *	@param	base	base index to start to get values
	 *	@return base index for next insertion.
	 */
	//==========================================================================
	public int insert(DeviceDataHistory ddh, DevEncoded[] values, int base) throws DevFailed
	{
		if (ddh.failed || 
			(ddh.source==TangoConst.ATTRIBUTE &&
			ddh.getAttrQuality()==AttrQuality.ATTR_INVALID))
			return base;

		//	Build an array to be inserted
		int	data_length = ddh.getDataLength();
		enc_data = new DevEncoded[data_length];
		for (int i=0 ; i<data_length ; i++)
			enc_data[i] = values[(base - data_length) + i];

		setType = TangoConst.Tango_DEV_ENCODED;
		return (base-data_length);
	}

	//==========================================================================
	/**
	 *	Insert values between base and (base-length) if not failed
	 *		and return base for next insertion.
	 *
	 *	@param	ddh DeviceDataHistory object.
	 *	@param	lsa values to be inserted
	 *	@param	bases	strin base index and numeric base insex to start to get values
	 *	@return base index for next insertion.
	 */
	//==========================================================================
	public int[] insert(DeviceDataHistory ddh, DevVarLongStringArray lsa, int[] bases) throws DevFailed
	{
		if (ddh.failed || 
			(ddh.source==TangoConst.ATTRIBUTE &&
			ddh.getAttrQuality()==AttrQuality.ATTR_INVALID))
			return bases;

		int	s_base = bases[0];
		int	l_base = bases[1];
		int	s_size = ddh.getDimX();
		int	l_size = ddh.getDimY();
		long_string_data = new DevVarLongStringArray();
		long_string_data.svalue = new String[s_size];
		long_string_data.lvalue = new int[l_size];

		//	Fill arrays
		for (int i=0 ; i<s_size ; i++)
			long_string_data.svalue[i] = lsa.svalue[(s_base - s_size) + i];
		for (int i=0 ; i<l_size ; i++)
			long_string_data.lvalue[i] = lsa.lvalue[(l_base - l_size) + i];

		setType = TangoConst.Tango_DEVVAR_LONGSTRINGARRAY;

		//	Return 2 bases for next insert
		bases[0] = s_base - s_size;
		bases[1] = l_base - l_size;
		return bases;
	}
	//==========================================================================
	/**
	 *	Insert values between base and (base-length) if not failed
	 *		and return base for next insertion.
	 *
	 *	@param	ddh DeviceDataHistory object.
	 *	@param	dsa values to be inserted
	 *	@param	bases	strin base index and numeric base insex to start to get values
	 *	@return base index for next insertion.
	 */
	//==========================================================================
	public int[] insert(DeviceDataHistory ddh, DevVarDoubleStringArray dsa, int[] bases) throws DevFailed
	{
		if (ddh.failed || 
			(ddh.source==TangoConst.ATTRIBUTE &&
			ddh.getAttrQuality()==AttrQuality.ATTR_INVALID))
			return bases;

		int	s_base = bases[0];
		int	d_base = bases[1];
		int	s_size = ddh.getDimX();
		int	d_size = ddh.getDimY();
		double_string_data = new DevVarDoubleStringArray();
		double_string_data.svalue = new String[s_size];
		double_string_data.dvalue = new double[d_size];

		//	Fill arrays
		for (int i=0 ; i<s_size ; i++)
			double_string_data.svalue[i] = dsa.svalue[(s_base - s_size) + i];
		for (int i=0 ; i<d_size ; i++)
			double_string_data.dvalue[i] = dsa.dvalue[(d_base - d_size) + i];

		setType = TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY;

		//	Return 2 bases for next insert
		bases[0] = s_base - s_size;
		bases[1] = d_base - d_size;
		return bases;
	}


	//==========================================================================
	/**
	 *	Insert values
	 *
	 *	@param	values values to be inserted
	 */
	//==========================================================================
	public void insert(DeviceDataHistory ddh, double[] values) throws DevFailed
	{
		if (ddh.failed || 
			(ddh.source==TangoConst.ATTRIBUTE &&
			ddh.getAttrQuality()==AttrQuality.ATTR_INVALID))
			return;

		ddh.setDimX(values.length);
		ddh.setDimY(1);
		ddh.setWrittenDimX(0);
		ddh.setWrittenDimY(0);
		double_data = values;
		setType = TangoConst.Tango_DEV_DOUBLE;
		if (ddh.source==TangoConst.COMMAND && values.length>1)
			setType = TangoConst.Tango_DEVVAR_DOUBLEARRAY;
	}

	//==========================================================================
	/**
	 *	Insert values
	 *
	 *	@param	values values to be inserted
	 */
	//==========================================================================
	public void insert(DeviceDataHistory ddh, String[] values) throws DevFailed
	{
		if (ddh.failed || 
			(ddh.source==TangoConst.ATTRIBUTE &&
			ddh.getAttrQuality()==AttrQuality.ATTR_INVALID))
			return;

		ddh.setDimX(values.length);
		ddh.setDimY(1);
		ddh.setWrittenDimX(0);
		ddh.setWrittenDimY(0);
		string_data = values;

		setType = TangoConst.Tango_DEV_STRING;
		if (ddh.source==TangoConst.COMMAND && values.length>1)
			setType = TangoConst.Tango_DEVVAR_STRINGARRAY;
	}
}
