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
//
// Copyright 2008 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttributeValue;
import fr.esrf.Tango.AttributeValue_3;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.TimeVal;

public interface IDeviceAttributeDAO {

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param attrval	AttributeValue_3 IDL object.
	 */
	public void init(AttributeValue_3 attrval);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param attrval	AttributeValue IDL object.
	 */
	public void init(AttributeValue attrval);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name	Attribute name.
	 */
	public void init(String name);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name	Attribute name.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	public void init(String name, int dim_x, int dim_y);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name		Attribute name.
	 *	@param value	Attribute value.
	 */
	public void init(String name, boolean value);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name		Attribute name.
	 *	@param value	Attribute value.
	 */
	public void init(String name, DevState value);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name		Attribute name.
	 *	@param value	Attribute value.
	 */
	public void init(String name, boolean[] value, int dim_x, int dim_y);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name		Attribute name.
	 *	@param value	Attribute value.
	 */
	public void init(String name, byte value);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name		Attribute name.
	 *	@param value	Attribute value.
	 */
	public void init(String name, byte[] value, int dim_x, int dim_y);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name		Attribute name.
	 *	@param value	Attribute value.
	 */
	public void init(String name, short value);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name		Attribute name.
	 *	@param values	Attribute values.
	 *	@param dim_x	array dimention in X	
	 *	@param dim_y	array dimention in Y	
	 */
	public void init(String name, short[] values, int dim_x, int dim_y);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name		Attribute name.
	 *	@param value	Attribute value.
	 */
	public void init(String name, int value);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name	Attribute name.
	 *	@param values		Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	public void init(String name, int[] values, int dim_x, int dim_y);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name		Attribute name.
	 *	@param value	Attribute value.
	 */
	public void init(String name, long value);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name	Attribute name.
	 *	@param values		Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	public void init(String name, long[] values, int dim_x, int dim_y);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name		Attribute name.
	 *	@param value	Attribute value.
	 */
	public void init(String name, float value);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name	Attribute name.
	 *	@param values		Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	public void init(String name, float[] values, int dim_x, int dim_y);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name		Attribute name.
	 *	@param value	Attribute value.
	 */
	public void init(String name, double value);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name	Attribute name.
	 *	@param values		Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	public void init(String name, double[] values, int dim_x, int dim_y);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name	Attribute name.
	 *	@param value	Attribute value.
	 */
	public void init(String name, String value);

	//===========================================
	/**
	 *	DeviceAttribute class constructor.
	 *
	 *	@param name	Attribute name.
	 *	@param values	Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	public void init(String name, String[] values, int dim_x, int dim_y);

	//===========================================
	//===========================================
	boolean hasFailed();

	//===========================================
	/**
	 *	Returns the attribute errors list
	 */
	DevError[] getErrStack();

	//===========================================
	/**
	 *	Set the AttributeValue internal object with input one.
	 *
	 *	@param val	AttributeValue_3 input object
	 */
	void setAttributeValue(AttributeValue_3 attrval);

	//===========================================
	/**
	 *	Set the AttributeValue internal object with input one.
	 *
	 *	@param val	AttributeValue input object
	 */
	void setAttributeValue(AttributeValue attrval);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert(DevState argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 *	@param dim_x array dimention in X	
	 */
	void insert(DevState[] argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	void insert(DevState[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert(boolean argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 *	@param dim_x array dimention in X	
	 */
	void insert(boolean[] argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	void insert(boolean[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute values as unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_uc(byte argin);

	//===========================================
	/**
	 *	Insert method for attribute values as unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_uc(byte[] argin);

	//===========================================
	/**
	 *	Insert method for attribute values as unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_uc(short argin);

	//===========================================
	/**
	 *	Insert method for attribute values as unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_uc(short[] argin);

	//===========================================
	/**
	 *	Insert method for attribute values as unsigned.
	 *
	 *	@param argin	Attribute values.
	 *	@param dim_x	nb data.in x direction
	 *	@param dim_y	nb data.in y direction
	 */
	void insert_uc(short[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute values as unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_uc(byte[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert(short argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert(short[] argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	void insert(short[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute values as unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_us(short argin);

	//===========================================
	/**
	 *	Insert method for attribute values as unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_us(int argin);

	//===========================================
	/**
	 *	Insert method for attribute valuesas unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_us(short[] argin);

	//===========================================
	/**
	 *	Insert method for attribute valuesas unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_us(int[] argin);

	//===========================================
	/**
	 *	Insert method for attribute valuesas unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_us(short[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute valuesas unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_us(int[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert(int argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert(int[] argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	void insert(int[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert(long argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert(long[] argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	void insert(long[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute values as unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_ul(int argin);

	//===========================================
	/**
	 *	Insert method for attribute values as unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_ul(long argin);

	//===========================================
	/**
	 *	Insert method for attribute valuesas unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_ul(int[] argin);

	//===========================================
	/**
	 *	Insert method for attribute valuesas unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_ul(long[] argin);

	//===========================================
	/**
	 *	Insert method for attribute valuesas unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_ul(int[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute valuesas unsigned.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_ul(long[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_u64(long argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert_u64(long[] argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	void insert_u64(long[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert(float argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert(float[] argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	void insert(float[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert(double argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert(double[] argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	void insert(double[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	void insert(String argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 *	@param dim_x array dimention in X	
	 */
	void insert(String[] argin);

	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 *	@param dim_x array dimention in X	
	 *	@param dim_y array dimention in Y	
	 */
	void insert(String[] argin, int dim_x, int dim_y);

	//===========================================
	/**
	 *	extract method for an DevState Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	DevState[] extractDevStateArray() throws DevFailed;

	//===========================================
	/**
	 *	extract method for an DevState.
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	DevState extractDevState() throws DevFailed;

	//===========================================
	/**
	 *	extract method for an boolean.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	boolean extractBoolean() throws DevFailed;

	//===========================================
	/**
	 *	extract method for an boolean Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	boolean[] extractBooleanArray() throws DevFailed;

	//===========================================
	/**
	 *	extract method for an unsigned char.
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	short extractUChar() throws DevFailed;

	//===========================================
	/**
	 *	extract method for an unsigned char Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	short[] extractUCharArray() throws DevFailed;

	//===========================================
	/**
	 *	extract method for an unsigned char Array as a char array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	byte[] extractCharArray() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a short.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	short extractShort() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a short Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	short[] extractShortArray() throws DevFailed;

	//===========================================
	/**
	 *	extract method for an unsigned short.
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	int extractUShort() throws DevFailed;

	//===========================================
	/**
	 *	extract method for an unsigned short Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	int[] extractUShortArray() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a long.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	int extractLong() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a long Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	int[] extractLongArray() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a unsigned long.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	long extractULong() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a unsigned long.array
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	long[] extractULongArray() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a long.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	long extractLong64() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a long Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	long[] extractLong64Array() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a long.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	long extractULong64() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a long Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	long[] extractULong64Array() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a float.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	float extractFloat() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a float Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	float[] extractFloatArray() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a double.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	double extractDouble() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a double Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	double[] extractDoubleArray() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a DevState (state attribute).
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	DevState extractState() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a String.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	String extractString() throws DevFailed;

	//===========================================
	/**
	 *	extract method for a double Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	String[] extractStringArray() throws DevFailed;

	//===========================================
	/**
	 *	Return attribute quality
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	AttrQuality getQuality() throws DevFailed;

	//===========================================
	/**
	 *	Return attribute time value.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	TimeVal getTimeVal() throws DevFailed;

	//===========================================
	/**
	 *	Return attribute time value in seconds since EPOCH.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	long getTimeValSec() throws DevFailed;

	//===========================================
	/**
	 *	Return attribute time value in seconds since EPOCH.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	long getTimeValMillisSec() throws DevFailed;

	//===========================================
	/**
	 *	Return attribute name.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	String getName() throws DevFailed;

	//===========================================
	/**
	 *	Return number of data read.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	int getNbRead() throws DevFailed;

	//===========================================
	/**
	 *	Return number of data written.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	int getNbWritten() throws DevFailed;

	//===========================================
	/**
	 *	Return attribute dim_x.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	int getDimX() throws DevFailed;

	//===========================================
	/**
	 *	Return attribute dim_y.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	int getDimY() throws DevFailed;

	//===========================================
	/**
	 *	Return attribute written dim_x.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	int getWrittenDimX() throws DevFailed;

	//===========================================
	/**
	 *	Return attribute written dim_y.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	int getWrittenDimY() throws DevFailed;

	//===========================================
	/**
	 *	Return AttributeValue IDL object.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	AttributeValue getAttributeValueObject_2();

	//===========================================
	/**
	 *	Return AttributeValue IDL object.
	 */
	AttributeValue_3 getAttributeValueObject_3();

	//===========================================
	/**
	 *	return time in milliseconds since 1/1/70
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	long getTime() throws DevFailed;

	//===========================================
	//===========================================
	int getType() throws DevFailed;

}
