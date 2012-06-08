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
// Revision 1.5  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.4  2008/07/30 11:29:51  pascal_verdier
// Header added
//
//-======================================================================

package fr.esrf.TangoApi;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarLongStringArray;

public interface IDeviceDataDAO {

	// ===========================================================
	/**
	 * Constructor for the TgApi Data Object.
	 * 
	 * @throws DevFailed
	 *             if TgApi class not instancied.
	 */
	// ===========================================================	
	public void init(DeviceData deviceData) throws DevFailed;

	// ===========================================================
	/**
	 * Constructor for the TgApi Data Object.
	 * 
	 * @param orb
	 *            orb connection id.
	 * @throws DevFailed
	 *             if TgApi class not instancied.
	 */
	// ===========================================================
	public void init(DeviceData deviceData, ORB orb) throws DevFailed;

	// ===========================================================
	/**
	 * Constructor for the TgApi Data Object.
	 * 
	 * @param any
	 *            CORBA Any reference to be used in DeviceData.
	 * @throws DevFailed
	 *             if TgApi class not instancied.
	 */
	// ===========================================================
	public void init(DeviceData deviceData, Any any) throws DevFailed;
	
	
	//===========================================
	/**
	 *	Insert method for argin is void.
	 */
	void insert(DeviceData deviceData);

	//===========================================
	/**
	 *	Insert method for argin is Any (CORBA).
	 */
	void insert(DeviceData deviceData, Any any);

	//===========================================
	/**
	 *	Insert method for argin is boolean.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, boolean argin);

	//===========================================
	/**
	 *	Insert method for argin is short.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, short argin);

	//===========================================
	/**
	 *	Insert method for argin is long.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, long argin);

	//===========================================
	/**
	 *	Insert method for argin is int.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, int argin);

	//===========================================
	/**
	 *	Insert method for argin is float.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, float argin);

	//===========================================
	/**
	 *	Insert method for argin is double.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, double argin);

	//===========================================
	/**
	 *	Insert method for argin is String.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, String argin);

	//===========================================
	/**
	 *	Insert method for argin is DevState.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, DevState argin);

	//===========================================
	/**
	 *	Insert method for argin is DevVarCharArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, byte[] argin);

	//===========================================
	/**
	 *	Insert method for argin is DevVarShortArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, short[] argin);

	//===========================================
	/**
	 *	Insert method for argin is DevVarLongArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, int[] argin);

	//===========================================
	/**
	 *	Insert method for argin is DevVarLongArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, long[] argin);

	//===========================================
	/**
	 *	Insert method for argin is DevVarFloatArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, float[] argin);

	//===========================================
	/**
	 *	Insert method for argin is DevVarDoubleArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, double[] argin);

	//===========================================
	/**
	 *	Insert method for argin is DevVarStringArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, String[] argin);

	//===========================================
	/**
	 *	Insert method for argin is DevVarLongStringArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, DevVarLongStringArray argin);

	//===========================================
	/**
	 *	Insert method for argin is DevVarDoubleStringArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert(DeviceData deviceData, DevVarDoubleStringArray argin);

	//===========================================
	/**
	 *	Insert method for argin is unsigned long 64.array
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert_u64(DeviceData deviceData, long[] argin);

	//===========================================
	/**
	 *	Insert method for argin is unsigned long 64.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert_u64(DeviceData deviceData, long argin);

	//===========================================
	/**
	 *	Insert method for argin int as unsigned char.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert_uc(DeviceData deviceData, byte argin);

	//===========================================
	/**
	 *	Insert method for argin int as unsigned char.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert_uc(DeviceData deviceData, short argin);

	//===========================================
	/**
	 *	Insert method for argin int as unsigned short.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert_us(DeviceData deviceData, int argin);

	//===========================================
	/**
	 *	Insert method for argin is unsigned short.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert_us(DeviceData deviceData, short argin);

	//===========================================
	/**
	 *	Insert method for argin is unsigned short.
	 *
	 *	@param argin	argin value for next command.
	 *	@deprecated use insert_us(short/int argin)
	 */
	void insert_u(DeviceData deviceData, short argin);

	//===========================================
	/**
	 *	Insert method for argin long ass unsigned int.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert_ul(DeviceData deviceData, long argin);

	//===========================================
	/**
	 *	Insert method for argin is unsigned int.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert_ul(DeviceData deviceData, int argin);

	//===========================================
	/**
	 *	Insert method for argin is unsigned int.
	 *
	 *	@param argin	argin value for next command.
	 *	@deprecated use insert_ul(int/long argin)
	 */
	void insert_u(DeviceData deviceData, int argin);

	//===========================================
	/**
	 *	Insert method for argin int as unsigned short array.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert_us(DeviceData deviceData, int[] argin);

	//===========================================
	/**
	 *	Insert method for argin is unsigned short array.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert_us(DeviceData deviceData, short[] argin);

	//===========================================
	/**
	 *	Insert method for argin is unsigned short array.
	 *
	 *	@param argin	argin value for next command.
	 *	@deprecated use insert_us(short[]/int[] argin)
	 */
	void insert_u(DeviceData deviceData, short[] argin);

	//===========================================
	/**
	 *	Insert method for argin long array as unsigned int array.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert_ul(DeviceData deviceData, long[] argin);

	//===========================================
	/**
	 *	Insert method for argin is unsigned int array.
	 *
	 *	@param argin	argin value for next command.
	 */
	void insert_ul(DeviceData deviceData, int[] argin);

	//===========================================
	/**
	 *	Insert method for argin is unsigned int array.
	 *
	 *	@param argin	argin value for next command.
	 *	@deprecated use insert_ul(int[]/long[] argin)
	 */
	void insert_u(DeviceData deviceData, int[] argin);

	//===========================================
	/**
	 *	extract method for a CORBA Any.
	 */
	Any extractAny(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a boolean.
	 */
	boolean extractBoolean(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a short.
	 */
	short extractShort(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for an unsigned char.
	 */
	short extractUChar(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for an unsigned short.
	 */
	int extractUShort(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a long.
	 */
	int extractLong(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a long.
	 */
	long extractLong64(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for an unsigned long.
	 */
	long extractULong(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for an unsigned long.
	 */
	long extractULong64(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a float.
	 */
	float extractFloat(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a double.
	 */
	double extractDouble(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a String.
	 */
	String extractString(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a DevState.
	 */
	DevState extractDevState(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a byte Array.
	 */
	byte[] extractByteArray(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a short Array.
	 */
	short[] extractShortArray(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for an unsigned short Array.
	 *	@return extract value as int array
	 */
	int[] extractUShortArray(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a long Array.
	 */
	int[] extractLongArray(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a long64 Array.
	 */
	long[] extractLong64Array(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for an unsigned long Array.
	 *	@return extract value as long array
	 */
	long[] extractULongArray(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a long64 Array.
	 */
	long[] extractULong64Array(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a float Array.
	 */
	float[] extractFloatArray(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a double Array.
	 */
	double[] extractDoubleArray(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a String Array.
	 */
	String[] extractStringArray(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a DevVarLongStringArray.
	 */
	DevVarLongStringArray extractLongStringArray(DeviceData deviceData);

	//===========================================
	/**
	 *	extract method for a DevVarDoubleStringArray.
	 */
	DevVarDoubleStringArray extractDoubleStringArray(DeviceData deviceData);

	TypeCode type(DeviceData deviceData);

	//===========================================
	//===========================================
	int getType(DeviceData deviceData) throws DevFailed;

}
