//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author$
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
// $Revision$
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.Tango.factory.TangoFactory;
import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;

/**
 * Class Description: This class manage data object for Tango device history
 * Data access. <Br>
 * <Br>
 * <Br>
 * <b> Usage example: </b> <Br>
 * <ul>
 * <i> DeviceDataHistory[] histo = dev.command_history("ReadCurrent", 10); <Br>
 * for (int i=0 ; i < histo.length ; i++) <Br> { <Br>
 * <ul>
 * Date d = new Date(histo[i].getTime()); <Br>
 * double[] values = histo[i].extractDoubleArray(); <Br>
 * </ul> } <Br>
 * </ul>
 * </i>
 * 
 * @author verdier
 * @version $Revision$
 */

public class DeviceDataHistory {
	private IDeviceDataHistoryDAO devicedatahistoryDAO = null;

	/**
	 *	Data source DeviceDataHistory.COMMAND or DeviceDataHistory.ATTRIBUTE
	 */
	public int				source;
	/**
	 *	Command/Attribute name.
	 */
	public String			name;
	/**
	 *	true if command/attribute failed.
	 */
	public boolean			failed;
	/**
	 *	Error list if any in reading Command/Attribute.
	 */
	public DevError[]		errors;
	
	// ===========================================
	/**
	 * Constructor from a DevCmdHistory.
	 */
	// ===========================================
	public DeviceDataHistory(String cmdname, DevCmdHistory cmd_hist) throws DevFailed {
		devicedatahistoryDAO = TangoFactory.getSingleton().getDeviceDataHistoryDAO();
		devicedatahistoryDAO.init(this, cmdname, cmd_hist);

	}

	// ===========================================
	/**
	 * Constructor from an AttributeValue.
	 */
	// ===========================================
	public DeviceDataHistory(DevAttrHistory att_histo) throws DevFailed {
		devicedatahistoryDAO = TangoFactory.getSingleton().getDeviceDataHistoryDAO();
		devicedatahistoryDAO.init(this, att_histo);

	}

	// ===========================================
	/**
	 * Constructor from an AttributeValue for Device_3Impl.
	 */
	// ===========================================
	public DeviceDataHistory(DevAttrHistory_3 att_histo) throws DevFailed {
		devicedatahistoryDAO = TangoFactory.getSingleton().getDeviceDataHistoryDAO();
		devicedatahistoryDAO.init(this, att_histo);

	}
	// ===========================================
	/**
	 * Constructor from an AttributeValue for Device_4Impl.
	 */
	// ===========================================
	public DeviceDataHistory(String name, int source, TimeVal time) throws DevFailed {
		devicedatahistoryDAO = TangoFactory.getSingleton().getDeviceDataHistoryDAO();
		devicedatahistoryDAO.init(this, name, source, time);

	}

	// ===========================================
	/**
	 * Constructor from an AttributeValue for Device_4Impl.
	 */
	// ===========================================
	public DeviceDataHistory(String name, int source, long t) throws DevFailed {
		devicedatahistoryDAO = TangoFactory.getSingleton().getDeviceDataHistoryDAO();
		devicedatahistoryDAO.init(this, name, source, t);

	}
	// ===========================================
	/**
	 * Set attribute time value.
	 */
	// ===========================================
	public void  setTimeVal(TimeVal tval) {
		devicedatahistoryDAO.setTimeVal(this, tval);
	}
	// ===========================================
	/**
	 * Return attribute time value.
	 */
	// ===========================================
	public TimeVal getTimeVal() {
		return devicedatahistoryDAO.getTimeVal(this);

	}

	// ===========================================
	/**
	 * Return attribute time value in seconds since EPOCH.
	 */
	// ===========================================
	public long getTimeValSec() {
		return devicedatahistoryDAO.getTimeValSec(this);

	}

	// ===========================================
	/**
	 * return time in milliseconds since EPOCH to build a Date class.
	 */
	// ===========================================
	public long getTime() {
		return devicedatahistoryDAO.getTime(this);

	}

	// ===========================================
	/**
	 * Set AttrQuality if from attribute.
	 */
	// ===========================================
	public void setAttrQuality(AttrQuality q) throws DevFailed {
		devicedatahistoryDAO.setAttrQuality(this, q);
	}
	// ===========================================
	/**
	 * return AttrQuality if from attribute.
	 */
	// ===========================================
	public AttrQuality getAttrQuality() throws DevFailed {
		return devicedatahistoryDAO.getAttrQuality(this);

	}

	// ===========================================
	/**
	 * Set attribute dim_x if from attribute.
	 */
	// ===========================================
	public void setDimX(int dim) throws DevFailed {
		devicedatahistoryDAO.setDimX(this, dim);
	}
	// ===========================================
	/**
	 * Set attribute dim_y if from attribute.
	 */
	// ===========================================
	public void setDimY(int dim) throws DevFailed {
		devicedatahistoryDAO.setDimY(this, dim);
	}
	// ===========================================
	/**
	 * Return attribute dim_x if from attribute.
	 */
	// ===========================================
	public int getDimX() throws DevFailed {
		return devicedatahistoryDAO.getDimX(this);

	}

	// ===========================================
	/**
	 * Return attribute dim_y if from attribute.
	 */
	// ===========================================
	public int getDimY() throws DevFailed {
		return devicedatahistoryDAO.getDimY(this);

	}

	// ********** Extract Methods for basic types *********************

	// ===========================================
	/**
	 * extract method for a CORBA Any.
	 */
	// ===========================================
	public Any extractAny()  throws DevFailed{
		return devicedatahistoryDAO.extractAny(this);

	}

	// ===========================================
	/**
	 * extract method for a boolean.
	 */
	// ===========================================
	public boolean extractBoolean()  throws DevFailed{
		return devicedatahistoryDAO.extractBoolean(this);

	}

	// ===========================================
	/**
	 * extract method for an unsigned char.
	 * 
	 * @return the extracted value.
	 */
	// ===========================================
	public short extractUChar() throws DevFailed {
		return devicedatahistoryDAO.extractUChar(this);

	}

	// ===========================================
	/**
	 * extract method for a short.
	 */
	// ===========================================
	public short extractShort()  throws DevFailed{
		return devicedatahistoryDAO.extractShort(this);

	}

	// ===========================================
	/**
	 * extract method for an unsigned short.
	 */
	// ===========================================
	public short extractUShort()  throws DevFailed{
		return devicedatahistoryDAO.extractUShort(this);

	}

	// ===========================================
	/**
	 * extract method for a long.
	 */
	// ===========================================
	public int extractLong()  throws DevFailed{
		return devicedatahistoryDAO.extractLong(this);

	}

	// ===========================================
	/**
	 * extract method for an unsigned long.
	 */
	// ===========================================
	public int extractULong()  throws DevFailed{
		return devicedatahistoryDAO.extractULong(this);

	}

	// ===========================================
	/**
	 * extract method for a long64.
	 */
	// ===========================================
	public long extractLong64()  throws DevFailed{
		return devicedatahistoryDAO.extractLong64(this);

	}

	// ===========================================
	/**
	 * extract method for an unsigned long64.
	 */
	// ===========================================
	public long extractULong64()  throws DevFailed{
		return devicedatahistoryDAO.extractULong64(this);

	}
	// ===========================================
	/**
	 * extract method for a float.
	 */
	// ===========================================
	public float extractFloat()  throws DevFailed{
		return devicedatahistoryDAO.extractFloat(this);

	}

	// ===========================================
	/**
	 * extract method for a double.
	 */
	// ===========================================
	public double extractDouble()  throws DevFailed{
		return devicedatahistoryDAO.extractDouble(this);

	}

	// ===========================================
	/**
	 * extract method for a String.
	 */
	// ===========================================
	public String extractString()  throws DevFailed{
		return devicedatahistoryDAO.extractString(this);

	}

	// ===========================================
	/**
	 * extract method for a DevState.
	 */
	// ===========================================
	public DevState extractDevState() throws DevFailed {
		return devicedatahistoryDAO.extractDevState(this);

	}
	
	// ===========================================
	/**
	 * extract method for a DevEncoded.
	 */
	// ===========================================
	public DevEncoded extractDevEncoded() throws DevFailed {
		return devicedatahistoryDAO.extractDevEncoded(this);

	}

	// ********** Extract Methods for sequence types *********************

	// ===========================================
	/**
	 * extract method for a byte Array.
	 */
	// ===========================================
	public boolean[] extractBooleanArray()  throws DevFailed{
		return devicedatahistoryDAO.extractBooleanArray(this);

	}

	// ===========================================
	/**
	 * extract method for a byte Array.
	 */
	// ===========================================
	public byte[] extractByteArray()  throws DevFailed{
		return devicedatahistoryDAO.extractByteArray(this);

	}

	// ===========================================
	/**
	 * extract method for an unsigned char Array.
	 * 
	 * @return the extracted value.
	 */
	// ===========================================
	public short[] extractUCharArray() throws DevFailed {
		return devicedatahistoryDAO.extractUCharArray(this);

	}

	// ===========================================
	/**
	 * extract method for a short Array.
	 */
	// ===========================================
	public short[] extractShortArray()  throws DevFailed{
		return devicedatahistoryDAO.extractShortArray(this);

	}

	// ===========================================
	/**
	 * extract method for an unsigned short Array.
	 */
	// ===========================================
	public short[] extractUShortArray() throws DevFailed {
		return devicedatahistoryDAO.extractUShortArray(this);

	}

	// ===========================================
	/**
	 * extract method for a long Array.
	 */
	// ===========================================
	public int[] extractLongArray()  throws DevFailed{
		return devicedatahistoryDAO.extractLongArray(this);

	}

	// ===========================================
	/**
	 * extract method for an unsigned long Array.
	 */
	// ===========================================
	public int[] extractULongArray()  throws DevFailed{
		return devicedatahistoryDAO.extractULongArray(this);

	}

	// ===========================================
	/**
	 * extract method for a long64 Array.
	 */
	// ===========================================
	public long[] extractLong64Array()  throws DevFailed{
		return devicedatahistoryDAO.extractLong64Array(this);

	}

	// ===========================================
	/**
	 * extract method for an unsigned long64 Array.
	 */
	// ===========================================
	public long[] extractULong64Array()  throws DevFailed{
		return devicedatahistoryDAO.extractULong64Array(this);

	}

	// ===========================================
	/**
	 * extract method for a float Array.
	 */
	// ===========================================
	public float[] extractFloatArray() throws DevFailed {
		return devicedatahistoryDAO.extractFloatArray(this);

	}

	// ===========================================
	/**
	 * extract method for a double Array.
	 */
	// ===========================================
	public double[] extractDoubleArray()  throws DevFailed{
		return devicedatahistoryDAO.extractDoubleArray(this);

	}

	// ===========================================
	/**
	 * extract method for a String Array.
	 */
	// ===========================================
	public String[] extractStringArray()  throws DevFailed{
		return devicedatahistoryDAO.extractStringArray(this);

	}

	// ===========================================
	/**
	 * extract method for a DevState Array.
	 */
	// ===========================================
	public DevState[] extractDevStateArray() throws DevFailed {
		return devicedatahistoryDAO.extractDevStateArray(this);

	}

	// ===========================================
	/**
	 * extract method for a DevEncoded Array.
	 */
	// ===========================================
	public DevEncoded[] extractDevEncodedArray() throws DevFailed {
		return devicedatahistoryDAO.extractDevEncodedArray(this);

	}

	// ===========================================
	/**
	 * extract method for a DevVarLongStringArray.
	 */
	// ===========================================
	public DevVarLongStringArray extractLongStringArray() throws DevFailed {
		return devicedatahistoryDAO.extractLongStringArray(this);

	}

	// ===========================================
	/**
	 * extract method for a DevVarDoubleStringArray.
	 */
	// ===========================================
	public DevVarDoubleStringArray extractDoubleStringArray()  throws DevFailed{
		return devicedatahistoryDAO.extractDoubleStringArray(this);
	}

	//===========================================
	/**
	 *	Returns true if attribute failed
	 */
	//===========================================
	public boolean hasFailed() {
		return devicedatahistoryDAO.hasFailed(this);
	}
	// ===========================================
	/**
	 * Set the attribute errors list
	 */
	// ===========================================
	public void setErrStack(DevError[] err) {
		devicedatahistoryDAO.setErrStack(this, err);
	}
	// ===========================================
	/**
	 * Returns the attribute errors list
	 */
	// ===========================================
	public DevError[] getErrStack() {
		return devicedatahistoryDAO.getErrStack(this);
	}

	// ===========================================
	/**
	 * Returns the attribute type
	 */
	// ===========================================
	public TypeCode type() {
		return devicedatahistoryDAO.type(this);
	}

	// ===========================================
	/**
	 * Return attribute name.
	 */
	// ===========================================
	public String getName() {
		return devicedatahistoryDAO.getName(this);
	}

	// ===========================================
	/**
	 * Return number of data read.
	 */
	// ===========================================
	public int getNbRead() {
		return devicedatahistoryDAO.getNbRead(this);

	}

	// ===========================================
	/**
	 * Return number of data written.
	 */
	// ===========================================
	public int getNbWritten() {
		return devicedatahistoryDAO.getNbWritten(this);

	}

	// ===========================================
	/**
	 * Set attribute written dim_x.
	 */
	// ===========================================
	public void setWrittenDimX(int nb) {
		devicedatahistoryDAO.setWrittenDimX(this, nb);
	}
	// ===========================================
	/**
	 * Set attribute written dim_y.
	 */
	// ===========================================
	public void setWrittenDimY(int nb) {
		devicedatahistoryDAO.setWrittenDimY(this, nb);
	}
	// ===========================================
	/**
	 * Return attribute written dim_x.
	 */
	// ===========================================
	public int getWrittenDimX() {
		return devicedatahistoryDAO.getWrittenDimX(this);

	}

	// ===========================================
	/**
	 * Return attribute written dim_y.
	 */
	// ===========================================
	public int getWrittenDimY() {
		return devicedatahistoryDAO.getWrittenDimY(this);

	}

	// ===========================================
	/**
	 * Returns attribute Tango type.
	 */
	// ===========================================
	public int getType() throws DevFailed {
		return devicedatahistoryDAO.getType(this);

	}
	
	
	public IDeviceDataHistoryDAO getDeviceedatahistoryDAO() {
		return devicedatahistoryDAO;
	}
	
	//==========================================================================
	public int getDataLength() throws DevFailed {
		return devicedatahistoryDAO.getDataLength(this);
	}


	//======================================================================	
	public int insert(boolean[] values, int base) throws DevFailed {
		return devicedatahistoryDAO.insert(this, values, base);
	}
	//======================================================================	
	public int insert(short[] values, int base) throws DevFailed {
		return devicedatahistoryDAO.insert(this, values, base);
	}
	//======================================================================	
	public int insert(int[] values, int base) throws DevFailed {
		return devicedatahistoryDAO.insert(this, values, base);
	}
	//======================================================================	
	public int insert(long[] values, int base) throws DevFailed {
		return devicedatahistoryDAO.insert(this, values, base);
	}
	//======================================================================	
	public int insert(float[] values, int base) throws DevFailed {
		return devicedatahistoryDAO.insert(this, values, base);
	}
	//======================================================================	
	public int insert(double[] values, int base) throws DevFailed {
		return devicedatahistoryDAO.insert(this, values, base);
	}
	//======================================================================	
	public int insert(String[] values, int base) throws DevFailed {
		return devicedatahistoryDAO.insert(this, values, base);
	}
	//======================================================================	
	public int insert(DevState[] values, int base) throws DevFailed {
		return devicedatahistoryDAO.insert(this, values, base);
	}
	//======================================================================	
	public int insert(DevEncoded[] values, int base) throws DevFailed {
		return devicedatahistoryDAO.insert(this, values, base);
	}
	//======================================================================	
	public int[] insert(DevVarLongStringArray lsa, int[] bases) throws DevFailed {
		return devicedatahistoryDAO.insert(this, lsa, bases);
	}
	//======================================================================	
	public int[] insert(DevVarDoubleStringArray dsa, int[] bases) throws DevFailed {
		return devicedatahistoryDAO.insert(this, dsa, bases);
	}

	//======================================================================	
	public void insert(double[] values) throws DevFailed {
		devicedatahistoryDAO.insert(this, values);
	}
	//======================================================================	
	public void insert(String[] values) throws DevFailed {
		devicedatahistoryDAO.insert(this, values);
	}
}

