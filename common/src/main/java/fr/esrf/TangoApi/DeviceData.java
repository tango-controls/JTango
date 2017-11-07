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
// $Revision: 28928 $
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
import fr.esrf.Tango.factory.TangoFactory;
import fr.esrf.TangoDs.TangoConst;

/**
 * Class Description: This class manage data object for Tango device access.
 * 
 * <Br>
 * <Br>
 * <Br>
 * <b> Usage example: </b> <Br>
 * <ul>
 * <i> String status; <Br>
 * DeviceProxy dev = ApiUtil.getDeviceProxy("sys/steppermotor/1"); <Br>
 * try { <Br>
 * <ul>
 * DeviceData data = dev.command_inout("DevStatus"); <Br>
 * status = data.extractString(); <Br>
 * </ul> } <Br>
 * catch (DevFailed e) { <Br>
 * <ul>
 * status = "Unknown status"; <Br>
 * Except.print_exception(e); <Br>
 * </ul> } <Br>
 * </ul>
 * </i>
 * 
 * @author verdier
 * @version $Revision: 28928 $
 */

public class DeviceData implements TangoConst {
	private IDeviceDataDAO devicedataDAO = null;

	Any any;

	//===========================================================
	/**
	 * Constructor for the TgApi Data Object.
	 * 
	 * @throws DevFailed
	 *             if TgApi class not instancied.
	 */
	//===========================================================
	public DeviceData() throws DevFailed {
		devicedataDAO = TangoFactory.getSingleton().getDeviceDataDAO();
		devicedataDAO.init(this);
	}

	//===========================================================
	/**
	 * Constructor for the TgApi Data Object.
	 * 
	 * @param orb
	 *            orb connection id.
	 * @throws DevFailed
	 *             if TgApi class not instancied.
	 */
	//===========================================================
	public DeviceData(ORB orb) throws DevFailed {
		devicedataDAO = TangoFactory.getSingleton().getDeviceDataDAO();
		devicedataDAO.init(this, orb);
	}

	//===========================================================
	/**
	 * Constructor for the TgApi Data Object.
	 * 
	 * @param any
	 *            CORBA Any reference to be used in DeviceData.
	 * @throws DevFailed
	 *             if TgApi class not instancied.
	 */
	//===========================================================
	public DeviceData(Any any) throws DevFailed {
		devicedataDAO = TangoFactory.getSingleton().getDeviceDataDAO();
		devicedataDAO.init(this, any);
	}

	// ********** Insert Methods for basic types *********************

	//===========================================
	/**
	 * Insert method for argin is void.
	 */
	//===========================================
	public void insert() {
		devicedataDAO.insert(this);
	}

	//===========================================
	/**
	 * Insert method for argin is Any (CORBA).
	 */
	//===========================================
	public void insert(Any any) {
		devicedataDAO.insert(this, any);
	}

	//===========================================
	/**
	 * Insert method for argin is boolean.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(boolean argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is short.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(short argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is long (64 bits)
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(long argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is int.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(int argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is float.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(float argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is double.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(double argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is String.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(String argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is DevState.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(DevState argin) {
		devicedataDAO.insert(this, argin);
	}

	// ********** Insert Methods for sequence types *********************

	//===========================================
	/**
	 * Insert method for argin is DevVarCharArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(byte[] argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is DevVarShortArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(short[] argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is DevVarLongArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(int[] argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is long array (64 bits)
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(long[] argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is DevVarFloatArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(float[] argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is DevVarDoubleArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(double[] argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is DevVarStringArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(String[] argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is DevVarLongStringArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(DevVarLongStringArray argin) {
		devicedataDAO.insert(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is DevVarDoubleStringArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert(DevVarDoubleStringArray argin) {
		devicedataDAO.insert(this, argin);
	}

	// ********** Insert Methods for unsigned types *********************

	//===========================================
	/**
	 * Insert method for argin is unsigned long 64.array
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert_u64(long[] argin) {
		devicedataDAO.insert_u64(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is unsigned long 64.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert_u64(long argin) {
		devicedataDAO.insert_u64(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is UCHAR.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert_uc(byte argin) {
		devicedataDAO.insert_uc(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is UCHAR.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert_uc(short argin) {
		devicedataDAO.insert_uc(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin int as unsigned short.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert_us(int argin) {
		devicedataDAO.insert_us(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is unsigned short.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert_us(short argin) {
		devicedataDAO.insert_us(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is unsigned short.
	 * 
	 * @param argin
	 *            argin value for next command.
	 * @deprecated use insert_us(short/int argin)
	 */
	//===========================================
	public void insert_u(short argin) {
		devicedataDAO.insert_us(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin long ass unsigned int.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert_ul(long argin) {
		devicedataDAO.insert_ul(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is unsigned int.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert_ul(int argin) {
		devicedataDAO.insert_ul(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is unsigned int.
	 * 
	 * @param argin
	 *            argin value for next command.
	 * @deprecated use insert_ul(int/long argin)
	 */
	//===========================================
	public void insert_u(int argin) {
		devicedataDAO.insert_ul(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin int as unsigned short array.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert_us(int[] argin) {
		devicedataDAO.insert_us(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is unsigned short array.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert_us(short[] argin) {
		devicedataDAO.insert_us(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is unsigned short array.
	 * 
	 * @param argin
	 *            argin value for next command.
	 * @deprecated use insert_us(short[]/int[] argin)
	 */
	//===========================================
	public void insert_u(short[] argin) {
		devicedataDAO.insert_us(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin long array as unsigned int array.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert_ul(long[] argin) {
		devicedataDAO.insert_ul(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is unsigned int array.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	//===========================================
	public void insert_ul(int[] argin) {
		devicedataDAO.insert_ul(this, argin);
	}

	//===========================================
	/**
	 * Insert method for argin is unsigned int array.
	 * 
	 * @param argin
	 *            argin value for next command.
	 * @deprecated use insert_ul(int[]/long[] argin)
	 */
	//===========================================
	public void insert_u(int[] argin) {
		devicedataDAO.insert_ul(this, argin);
	}

	// ********** Extract Methods for basic types *********************

	//===========================================
	/**
	 * extract method for a CORBA Any.
	 */
	//===========================================
	public Any extractAny() {
		return devicedataDAO.extractAny(this);
	}

	//===========================================
	/**
	 * extract method for a boolean.
	 */
	//===========================================
	public boolean extractBoolean() {
		return devicedataDAO.extractBoolean(this);
	}

	//===========================================
	/**
	 * extract method for an unsigned char.
	 */
	//===========================================
	public short extractUChar() {
		return devicedataDAO.extractUChar(this);
	}

	//===========================================
	/**
	 * extract method for a short.
	 */
	//===========================================
	public short extractShort() {
		return devicedataDAO.extractShort(this);
	}

	//===========================================
	/**
	 * extract method for an unsigned short.
	 */
	//===========================================
	public int extractUShort() {
		return devicedataDAO.extractUShort(this);
	}

	//===========================================
	/**
	 * extract method for a long.
	 */
	//===========================================
	public int extractLong() {
		return devicedataDAO.extractLong(this);
	}

	//===========================================
	/**
	 * extract method for a long.
	 */
	//===========================================
	public long extractLong64() {
		return devicedataDAO.extractLong64(this);
	}

	//===========================================
	/**
	 * extract method for an unsigned long.
	 */
	//===========================================
	public long extractULong() {
		return devicedataDAO.extractULong(this);
	}

	//===========================================
	/**
	 * extract method for an unsigned long.
	 */
	//===========================================
	public long extractULong64() {
		return devicedataDAO.extractULong64(this);
	}

	//===========================================
	/**
	 * extract method for a float.
	 */
	//===========================================
	public float extractFloat() {
		return devicedataDAO.extractFloat(this);
	}

	//===========================================
	/**
	 * extract method for a double.
	 */
	//===========================================
	public double extractDouble() {
		return devicedataDAO.extractDouble(this);
	}

	//===========================================
	/**
	 * extract method for a String.
	 */
	//===========================================
	public String extractString() {
		return devicedataDAO.extractString(this);
	}

	//===========================================
	/**
	 * extract method for a DevState.
	 */
	//===========================================
	public DevState extractDevState() {
		return devicedataDAO.extractDevState(this);
	}

	// ********** Extract Methods for sequence types *********************

	//===========================================
	/**
	 * extract method for a byte Array.
	 */
	//===========================================
	public byte[] extractByteArray() {
		return devicedataDAO.extractByteArray(this);
	}

	//===========================================
	/**
	 * extract method for a byte Array as unsigned in short array.
	 */
	//===========================================
	public short[] extractUByteArray() {
        byte[] bytes = devicedataDAO.extractByteArray(this);
        short[] shorts = new short[bytes.length];
        for (int i=0 ; i<bytes.length ; i++) {
            shorts[i] = (short) (bytes[i] & 0xff);
        }
        return shorts;
	}

	//===========================================
	/**
	 * extract method for a short Array.
	 */
	//===========================================
	public short[] extractShortArray() {
		return devicedataDAO.extractShortArray(this);
	}

	//===========================================
	/**
	 * extract method for an unsigned short Array.
	 * 
	 * @return extract value as int array
	 */
	//===========================================
	public int[] extractUShortArray() {
		return devicedataDAO.extractUShortArray(this);
	}

	//===========================================
	/**
	 * extract method for a long Array.
	 */
	//===========================================
	public int[] extractLongArray() {
		return devicedataDAO.extractLongArray(this);
	}

	//===========================================
	/**
	 * extract method for a long64 Array.
	 */
	//===========================================
	public long[] extractLong64Array() {
		return devicedataDAO.extractLong64Array(this);
	}

	//===========================================
	/**
	 * extract method for an unsigned long Array.
	 * 
	 * @return extract value as long array
	 */
	//===========================================
	public long[] extractULongArray() {
		return devicedataDAO.extractULongArray(this);
	}

	//===========================================
	/**
	 * extract method for a long64 Array.
	 */
	//===========================================
	public long[] extractULong64Array() {
		return devicedataDAO.extractULong64Array(this);
	}

	//===========================================
	/**
	 * extract method for a float Array.
	 */
	//===========================================
	public float[] extractFloatArray() {
		return devicedataDAO.extractFloatArray(this);
	}

	//===========================================
	/**
	 * extract method for a double Array.
	 */
	//===========================================
	public double[] extractDoubleArray() {
		return devicedataDAO.extractDoubleArray(this);
	}

	//===========================================
	/**
	 * extract method for a String Array.
	 */
	//===========================================
	public String[] extractStringArray() {
		return devicedataDAO.extractStringArray(this);
	}

	//===========================================
	/**
	 * extract method for a boolean.
	 */
	//===========================================
	public boolean[] extractBooleanArray() {
		return devicedataDAO.extractBooleanArray(this);
	}

	//===========================================
	/**
	 * extract method for a DevVarLongStringArray.
	 */
	//===========================================
	public DevVarLongStringArray extractLongStringArray() {
		return devicedataDAO.extractLongStringArray(this);
	}

	//===========================================
	/**
	 * extract method for a DevVarDoubleStringArray.
	 */
	//===========================================
	public DevVarDoubleStringArray extractDoubleStringArray() {
		return devicedataDAO.extractDoubleStringArray(this);
	}

	public TypeCode type() {
		return devicedataDAO.type(this);
	}

	//===========================================
	//===========================================
	public int getType() throws DevFailed {
		return devicedataDAO.getType(this);
	}

	public Any getAny() {
		return any;
	}

	public void setAny(Any any) {
		this.any = any;
	}
	
	public IDeviceDataDAO getDevicedataDAO() {
		return devicedataDAO;
	}
}

