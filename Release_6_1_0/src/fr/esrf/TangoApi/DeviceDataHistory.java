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
// Revision 1.5  2007/08/23 09:42:22  ounsy
// update for synchonize source code with tango/api/java
//
// Revision 1.3  2007/07/02 12:05:38  ounsy
// Minor modification of common classes :
// - add getter and setter
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

import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttributeValue_3;
import fr.esrf.Tango.DevAttrHistory;
import fr.esrf.Tango.DevAttrHistory_3;
import fr.esrf.Tango.DevCmdHistory;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.Tango.TimeVal;
import fr.esrf.Tango.factory.TangoFactory;

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
	 * return AttrQuality if from attribute.
	 */
	// ===========================================
	public AttrQuality getAttrQuality() throws DevFailed {
		return devicedatahistoryDAO.getAttrQuality(this);

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
	public Any extractAny() {
		return devicedatahistoryDAO.extractAny(this);

	}

	// ===========================================
	/**
	 * extract method for a boolean.
	 */
	// ===========================================
	public boolean extractBoolean() {
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
	public short extractShort() {
		return devicedatahistoryDAO.extractShort(this);

	}

	// ===========================================
	/**
	 * extract method for an unsigned short.
	 */
	// ===========================================
	public short extractUShort() {
		return devicedatahistoryDAO.extractUShort(this);

	}

	// ===========================================
	/**
	 * extract method for a long.
	 */
	// ===========================================
	public int extractLong() {
		return devicedatahistoryDAO.extractLong(this);

	}

	// ===========================================
	/**
	 * extract method for an unsigned long.
	 */
	// ===========================================
	public int extractULong() {
		return devicedatahistoryDAO.extractULong(this);

	}

	// ===========================================
	/**
	 * extract method for a float.
	 */
	// ===========================================
	public float extractFloat() {
		return devicedatahistoryDAO.extractFloat(this);

	}

	// ===========================================
	/**
	 * extract method for a double.
	 */
	// ===========================================
	public double extractDouble() {
		return devicedatahistoryDAO.extractDouble(this);

	}

	// ===========================================
	/**
	 * extract method for a String.
	 */
	// ===========================================
	public String extractString() {
		return devicedatahistoryDAO.extractString(this);

	}

	// ===========================================
	/**
	 * extract method for a DevState.
	 */
	// ===========================================
	public DevState extractDevState() {
		return devicedatahistoryDAO.extractDevState(this);

	}

	// ********** Extract Methods for sequence types *********************

	// ===========================================
	/**
	 * extract method for a byte Array.
	 */
	// ===========================================
	public boolean[] extractBooleanArray() {
		return devicedatahistoryDAO.extractBooleanArray(this);

	}

	// ===========================================
	/**
	 * extract method for a byte Array.
	 */
	// ===========================================
	public byte[] extractByteArray() {
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
	public short[] extractShortArray() {
		return devicedatahistoryDAO.extractShortArray(this);

	}

	// ===========================================
	/**
	 * extract method for an unsigned short Array.
	 */
	// ===========================================
	public short[] extractUShortArray() {
		return devicedatahistoryDAO.extractUShortArray(this);

	}

	// ===========================================
	/**
	 * extract method for a long Array.
	 */
	// ===========================================
	public int[] extractLongArray() {
		return devicedatahistoryDAO.extractLongArray(this);

	}

	// ===========================================
	/**
	 * extract method for an unsigned long Array.
	 */
	// ===========================================
	public int[] extractULongArray() {
		return devicedatahistoryDAO.extractULongArray(this);

	}

	// ===========================================
	/**
	 * extract method for a float Array.
	 */
	// ===========================================
	public float[] extractFloatArray() {
		return devicedatahistoryDAO.extractFloatArray(this);

	}

	// ===========================================
	/**
	 * extract method for a double Array.
	 */
	// ===========================================
	public double[] extractDoubleArray() {
		return devicedatahistoryDAO.extractDoubleArray(this);

	}

	// ===========================================
	/**
	 * extract method for a String Array.
	 */
	// ===========================================
	public String[] extractStringArray() {
		return devicedatahistoryDAO.extractStringArray(this);

	}

	// ===========================================
	/**
	 * extract method for a DevVarLongStringArray.
	 */
	// ===========================================
	public DevVarLongStringArray extractLongStringArray() {
		return devicedatahistoryDAO.extractLongStringArray(this);

	}

	// ===========================================
	/**
	 * extract method for a DevVarDoubleStringArray.
	 */
	// ===========================================
	public DevVarDoubleStringArray extractDoubleStringArray() {
		return devicedatahistoryDAO.extractDoubleStringArray(this);

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
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public String getName() {
		return devicedatahistoryDAO.getName(this);

	}

	// ===========================================
	/**
	 * Return number of data read.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public int getNbRead() {
		return devicedatahistoryDAO.getNbRead(this);

	}

	// ===========================================
	/**
	 * Return number of data written.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public int getNbWritten() {
		return devicedatahistoryDAO.getNbWritten(this);

	}

	// ===========================================
	/**
	 * Return attribute written dim_x.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public int getWrittenDimX() {
		return devicedatahistoryDAO.getWrittenDimX(this);

	}

	// ===========================================
	/**
	 * Return attribute written dim_y.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
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

	public void setDevicedatahistoryDAO(IDeviceDataHistoryDAO databaseDAO) {
		this.devicedatahistoryDAO = devicedatahistoryDAO;
	}	
}

