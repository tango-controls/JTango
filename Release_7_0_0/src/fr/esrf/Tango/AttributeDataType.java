package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "AttributeDataType"
 *	@author JacORB IDL compiler 
 */

public final class AttributeDataType
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _ATT_BOOL = 0;
	public static final AttributeDataType ATT_BOOL = new AttributeDataType(_ATT_BOOL);
	public static final int _ATT_SHORT = 1;
	public static final AttributeDataType ATT_SHORT = new AttributeDataType(_ATT_SHORT);
	public static final int _ATT_LONG = 2;
	public static final AttributeDataType ATT_LONG = new AttributeDataType(_ATT_LONG);
	public static final int _ATT_LONG64 = 3;
	public static final AttributeDataType ATT_LONG64 = new AttributeDataType(_ATT_LONG64);
	public static final int _ATT_FLOAT = 4;
	public static final AttributeDataType ATT_FLOAT = new AttributeDataType(_ATT_FLOAT);
	public static final int _ATT_DOUBLE = 5;
	public static final AttributeDataType ATT_DOUBLE = new AttributeDataType(_ATT_DOUBLE);
	public static final int _ATT_UCHAR = 6;
	public static final AttributeDataType ATT_UCHAR = new AttributeDataType(_ATT_UCHAR);
	public static final int _ATT_USHORT = 7;
	public static final AttributeDataType ATT_USHORT = new AttributeDataType(_ATT_USHORT);
	public static final int _ATT_ULONG = 8;
	public static final AttributeDataType ATT_ULONG = new AttributeDataType(_ATT_ULONG);
	public static final int _ATT_ULONG64 = 9;
	public static final AttributeDataType ATT_ULONG64 = new AttributeDataType(_ATT_ULONG64);
	public static final int _ATT_STRING = 10;
	public static final AttributeDataType ATT_STRING = new AttributeDataType(_ATT_STRING);
	public static final int _ATT_STATE = 11;
	public static final AttributeDataType ATT_STATE = new AttributeDataType(_ATT_STATE);
	public static final int _DEVICE_STATE = 12;
	public static final AttributeDataType DEVICE_STATE = new AttributeDataType(_DEVICE_STATE);
	public static final int _ATT_ENCODED = 13;
	public static final AttributeDataType ATT_ENCODED = new AttributeDataType(_ATT_ENCODED);
	public static final int _NO_DATA = 14;
	public static final AttributeDataType NO_DATA = new AttributeDataType(_NO_DATA);
	public int value()
	{
		return value;
	}
	public static AttributeDataType from_int(int value)
	{
		switch (value) {
			case _ATT_BOOL: return ATT_BOOL;
			case _ATT_SHORT: return ATT_SHORT;
			case _ATT_LONG: return ATT_LONG;
			case _ATT_LONG64: return ATT_LONG64;
			case _ATT_FLOAT: return ATT_FLOAT;
			case _ATT_DOUBLE: return ATT_DOUBLE;
			case _ATT_UCHAR: return ATT_UCHAR;
			case _ATT_USHORT: return ATT_USHORT;
			case _ATT_ULONG: return ATT_ULONG;
			case _ATT_ULONG64: return ATT_ULONG64;
			case _ATT_STRING: return ATT_STRING;
			case _ATT_STATE: return ATT_STATE;
			case _DEVICE_STATE: return DEVICE_STATE;
			case _ATT_ENCODED: return ATT_ENCODED;
			case _NO_DATA: return NO_DATA;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected AttributeDataType(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
