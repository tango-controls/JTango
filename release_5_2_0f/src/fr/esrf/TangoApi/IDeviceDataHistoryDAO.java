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
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.Tango.TimeVal;

public interface IDeviceDataHistoryDAO {

	/**
	 *	History from command history.
	 */
	public static final int COMMAND = 0;

	/**
	 *	History from attribute history.
	 */
	public static final int ATTRIBUTE = 1;

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
	
	//===========================================
	/**
	 *	Return attribute time value.
	 */
	public TimeVal getTimeVal(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Return attribute time value in seconds since EPOCH.
	 */
	public long getTimeValSec(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	return time in milliseconds since EPOCH
	 *	to build a Date class.
	 */
	public long getTime(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	return AttrQuality if from attribute.
	 */
	public AttrQuality getAttrQuality(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	Return attribute dim_x if from attribute.
	 */
	public int getDimX(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	Return attribute dim_y if from attribute.
	 */
	public int getDimY(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a CORBA Any.
	 */
	public Any extractAny(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a boolean.
	 */
	public boolean extractBoolean(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for an unsigned char.
	 *	@return	the extracted value.
	 */
	public short extractUChar(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a short.
	 */
	public short extractShort(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for an unsigned short.
	 */
	public short extractUShort(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a long.
	 */
	public int extractLong(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for an unsigned long.
	 */
	public int extractULong(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a float.
	 */
	public float extractFloat(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a double.
	 */
	public double extractDouble(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a String.
	 */
	public String extractString(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a DevState.
	 */
	public DevState extractDevState(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a byte Array.
	 */
	public boolean[] extractBooleanArray(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a byte Array.
	 */
	public byte[] extractByteArray(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for an unsigned char Array.
	 *
	 *	@return	the extracted value.
	 */
	public short[] extractUCharArray(DeviceDataHistory deviceDataHistory) throws DevFailed;

	//===========================================
	/**
	 *	extract method for a short Array.
	 */
	public short[] extractShortArray(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for an unsigned short Array.
	 */
	public short[] extractUShortArray(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a long Array.
	 */
	public int[] extractLongArray(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for an unsigned long Array.
	 */
	public int[] extractULongArray(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a float Array.
	 */
	public float[] extractFloatArray(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a double Array.
	 */
	public double[] extractDoubleArray(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a String Array.
	 */
	public String[] extractStringArray(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a DevVarLongStringArray.
	 */
	public DevVarLongStringArray extractLongStringArray(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	extract method for a DevVarDoubleStringArray.
	 */
	public DevVarDoubleStringArray extractDoubleStringArray(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Returns the attribute errors list
	 */
	public DevError[] getErrStack(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Returns the attribute type
	 */
	public TypeCode type(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Return attribute name.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	public String getName(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Return number of data read.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	public int getNbRead(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Return number of data written.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	public int getNbWritten(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Return attribute written dim_x.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	public int getWrittenDimX(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Return attribute written dim_y.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	public int getWrittenDimY(DeviceDataHistory deviceDataHistory);

	//===========================================
	/**
	 *	Returns attribute Tango type.
	 */
	public int getType(DeviceDataHistory deviceDataHistory) throws DevFailed;

}