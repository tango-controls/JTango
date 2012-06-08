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
// Revision 1.6  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2008/09/24 09:24:52  pascal_verdier
// Header added
//
//-======================================================================

package fr.esrf.TangoApi;

import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttributeDim;
import fr.esrf.Tango.AttributeValue_3;
import fr.esrf.Tango.DevAttrHistory;
import fr.esrf.Tango.DevAttrHistory_3;
import fr.esrf.Tango.DevCmdHistory;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.Tango.TimeVal;

public interface IDeviceDataHistoryDAO {


	// ===========================================
	/**
	 * Constructor from a DevCmdHistory.
	 */
	// ===========================================
	public void init(DeviceDataHistory deviceDataHistory, String cmdname, DevCmdHistory cmd_hist) throws DevFailed;

	// ===========================================
	/**
	 * Constructor from an AttributeValue.
	 */
	// ===========================================
	public void init(DeviceDataHistory deviceDataHistory, DevAttrHistory att_histo) throws DevFailed ;

	// ===========================================
	/**
	 * Constructor from an AttributeValue for Device_3Impl.
	 */
	// ===========================================
	public void init(DeviceDataHistory deviceDataHistory, DevAttrHistory_3 att_histo) throws DevFailed;	
	
	// ===========================================
	/**
	 * Constructor from an AttributeValue for Device_4Impl.
	 */
	// ===========================================
	public void init(DeviceDataHistory deviceDataHistory, String name, int source, TimeVal time) throws DevFailed;	
	
	// ===========================================
	/**
	 * Constructor from an AttributeValue for Device_4Impl.
	 */
	// ===========================================
	public void init(DeviceDataHistory deviceDataHistory, String name, int source, long t) throws DevFailed;	
	
	//===========================================
	/**
	 *	Set attribute time value.
	 */
	// ===========================================
	public void setTimeVal(DeviceDataHistory deviceDataHistory, TimeVal tval);

	//===========================================
	/**
	 *	Return attribute time value.
	 */
	// ===========================================
	public TimeVal getTimeVal(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Return attribute time value in seconds since EPOCH.
	 */
	// ===========================================
	public long getTimeValSec(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	return time in milliseconds since EPOCH
	 *	to build a Date class.
	 */
	// ===========================================
	public long getTime(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Set AttrQuality if from attribute.
	 */
	// ===========================================
	public void setAttrQuality(DeviceDataHistory deviceDataHistory, AttrQuality q) throws DevFailed;

	//===========================================
	/**
	 *	return AttrQuality if from attribute.
	 */
	// ===========================================
	public AttrQuality getAttrQuality(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	Set attribute dim_x if from attribute.
	 */
	// ===========================================
	public void setDimX(DeviceDataHistory deviceDataHistory, int dim) throws DevFailed;

	//===========================================
	/**
	 *	Set attribute dim_y if from attribute.
	 */
	// ===========================================
	public void setDimY(DeviceDataHistory deviceDataHistory, int dim) throws DevFailed;

	//===========================================
	/**
	 *	Return attribute dim_x if from attribute.
	 */
	// ===========================================
	public int getDimX(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	Return attribute dim_y if from attribute.
	 */
	// ===========================================
	public int getDimY(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a CORBA Any.
	 */
	// ===========================================
	public Any extractAny(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a boolean.
	 */
	// ===========================================
	public boolean extractBoolean(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for an unsigned char.
	 *	@return	the extracted value.
	 */
	// ===========================================
	public short extractUChar(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a short.
	 */
	// ===========================================
	public short extractShort(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for an unsigned short.
	 */
	// ===========================================
	public short extractUShort(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a long.
	 */
	// ===========================================
	public int extractLong(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for an unsigned long.
	 */
	// ===========================================
	public int extractULong(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a long64.
	 */
	// ===========================================
	public long extractLong64(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for an unsigned long64.
	 */
	// ===========================================
	public long extractULong64(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a float.
	 */
	// ===========================================
	public float extractFloat(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a double.
	 */
	// ===========================================
	public double extractDouble(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a String.
	 */
	// ===========================================
	public String extractString(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a DevState.
	 */
	// ===========================================
	public DevState extractDevState(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a DevEncoded
	 */
	// ===========================================
	public DevEncoded extractDevEncoded(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a byte Array.
	 */
	// ===========================================
	public boolean[] extractBooleanArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a byte Array.
	 */
	// ===========================================
	public byte[] extractByteArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for an unsigned char Array.
	 *
	 *	@return	the extracted value.
	 */
	public short[] extractUCharArray(DeviceDataHistory deviceDataHistory) throws DevFailed;
	// ===========================================

	//===========================================
	/**
	 *	extract method for a short Array.
	 */
	// ===========================================
	public short[] extractShortArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for an unsigned short Array.
	 */
	// ===========================================
	public short[] extractUShortArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a long Array.
	 */
	// ===========================================
	public int[] extractLongArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for an unsigned long Array.
	 */
	// ===========================================
	public int[] extractULongArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a long64 Array.
	 */
	// ===========================================
	public long[] extractLong64Array(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for an unsigned long64 Array.
	 */
	// ===========================================
	public long[] extractULong64Array(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a float Array.
	 */
	// ===========================================
	public float[] extractFloatArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a double Array.
	 */
	// ===========================================
	public double[] extractDoubleArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a String Array.
	 */
	// ===========================================
	public String[] extractStringArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a DevState Array.
	 */
	// ===========================================
	public DevState[] extractDevStateArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a DevEncoded Array
	 */
	// ===========================================
	public DevEncoded[] extractDevEncodedArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a DevVarLongStringArray.
	 */
	// ===========================================
	public DevVarLongStringArray extractLongStringArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a DevVarDoubleStringArray.
	 */
	// ===========================================
	public DevVarDoubleStringArray extractDoubleStringArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	Returns true if attribute failed
	 */
	//===========================================
	public boolean hasFailed(DeviceDataHistory deviceDataHistory);
	//===========================================
	/**
	 *	Set the attribute errors list
	 */
	// ===========================================
	public void setErrStack(DeviceDataHistory deviceDataHistory, DevError[] err);
	//===========================================
	/**
	 *	Returns the attribute errors list
	 */
	// ===========================================
	public DevError[] getErrStack(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Returns the attribute type
	 */
	// ===========================================
	public TypeCode type(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Return attribute name.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	// ===========================================
	public String getName(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Return number of data read.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	// ===========================================
	public int getNbRead(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Return number of data written.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	// ===========================================
	public int getNbWritten(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Set attribute written dim_x.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	// ===========================================
	public void setWrittenDimX(DeviceDataHistory deviceDataHistory, int nb);

	//===========================================
	/**
	 *	Set attribute written dim_y.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	// ===========================================
	public void setWrittenDimY(DeviceDataHistory deviceDataHistory, int nb);

	//===========================================
	/**
	 *	Return attribute written dim_x.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	// ===========================================
	public int getWrittenDimX(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Return attribute written dim_y.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	// ===========================================
	public int getWrittenDimY(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Returns attribute Tango type.
	 */
	// ===========================================
	public int getType(DeviceDataHistory deviceDataHistory) throws DevFailed;



	//======================================================================	
	public int getDataLength(DeviceDataHistory ddh) throws DevFailed;



	//======================================================================	
	public int insert(DeviceDataHistory ddh, boolean[] values, int base) throws DevFailed;
	//======================================================================	
	public int insert(DeviceDataHistory ddh, short[] values, int base) throws DevFailed;
	//======================================================================	
	public int insert(DeviceDataHistory ddh, int[] values, int base) throws DevFailed;
	//======================================================================	
	public int insert(DeviceDataHistory ddh, long[] values, int base) throws DevFailed;
	//======================================================================	
	public int insert(DeviceDataHistory ddh, float[] values, int base) throws DevFailed;
	//======================================================================	
	public int insert(DeviceDataHistory ddh, double[] values, int base) throws DevFailed;
	//======================================================================	
	public int insert(DeviceDataHistory ddh, String[] values, int base) throws DevFailed;
	//======================================================================	
	public int insert(DeviceDataHistory ddh, DevState[] values, int base) throws DevFailed;
	//======================================================================	
	public int insert(DeviceDataHistory ddh, DevEncoded[] values, int base) throws DevFailed;
	//======================================================================	
	public int[] insert(DeviceDataHistory ddh, DevVarLongStringArray lsa, int[] bases) throws DevFailed;
	//======================================================================	
	public int[] insert(DeviceDataHistory ddh, DevVarDoubleStringArray dsa, int[] bases) throws DevFailed;

	//======================================================================	
	public void insert(DeviceDataHistory ddh, double[] values) throws DevFailed;
	//======================================================================	
	public void insert(DeviceDataHistory ddh, String[] values) throws DevFailed;
}
