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
// Revision 1.9  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.8  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.7  2008/07/30 11:27:42  pascal_verdier
// insert/extract UChar added
//
// Revision 1.6  2008/01/10 15:40:23  ounsy
// Resolving a multi connection problem when accessing to a device
//
// Revision 1.5  2007/08/23 09:42:20  ounsy
// update for synchonize source code with tango/api/java
//
// Revision 1.3  2007/07/02 12:05:38  ounsy
// Minor modification of common classes :
// - add getter and setter
//
// Revision 3.11  2007/05/29 08:11:15  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.10  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.9  2005/09/14 07:33:33  pascal_verdier
// Bug fixed in getData() method.
//
// Revision 3.8  2005/08/10 08:11:29  pascal_verdier
// Default value modified in getType() method.
//
// Revision 3.7  2005/06/22 13:28:25  pascal_verdier
// getType() method added.
//
// Revision 3.6  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:23  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.1  2003/09/08 11:03:25  pascal_verdier
// *** empty log message ***
//
// Revision 3.0  2003/04/29 08:03:29  pascal_verdier
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
// Revision 1.6  2002/01/09 12:18:15  verdier
// TACO signals can be read as TANGO attribute.
//
// Revision 1.5  2001/12/10 14:19:42  verdier
// TACO JNI Interface added.
// URL syntax used for connection.
// Connection on device without database added.
//
// Revision 1.4  2001/07/04 14:06:05  verdier
// Attribute management added.
//
// Revision 1.3  2001/04/02 08:32:05  verdier
// TangoApi package has users...
//
// Revision 1.1  2001/02/02 13:03:46  verdier
// Initial revision
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
 * @version $Revision$
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

