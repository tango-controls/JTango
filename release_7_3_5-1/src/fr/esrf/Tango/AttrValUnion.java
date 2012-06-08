package fr.esrf.Tango;

/**
 *	Generated from IDL definition of union "AttrValUnion"
 *	@author JacORB IDL compiler 
 */

public final class AttrValUnion
	implements org.omg.CORBA.portable.IDLEntity
{
	private fr.esrf.Tango.AttributeDataType discriminator;
	private boolean[] bool_att_value;
	private short[] short_att_value;
	private int[] long_att_value;
	private long[] long64_att_value;
	private float[] float_att_value;
	private double[] double_att_value;
	private byte[] uchar_att_value;
	private short[] ushort_att_value;
	private int[] ulong_att_value;
	private long[] ulong64_att_value;
	private java.lang.String[] string_att_value;
	private fr.esrf.Tango.DevState[] state_att_value;
	private fr.esrf.Tango.DevState dev_state_att;
	private fr.esrf.Tango.DevEncoded[] encoded_att_value;
	private boolean union_no_data;

	public AttrValUnion ()
	{
	}

	public fr.esrf.Tango.AttributeDataType discriminator ()
	{
		return discriminator;
	}

	public boolean[] bool_att_value ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.ATT_BOOL)
			throw new org.omg.CORBA.BAD_OPERATION();
		return bool_att_value;
	}

	public void bool_att_value (boolean[] _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.ATT_BOOL;
		bool_att_value = _x;
	}

	public short[] short_att_value ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.ATT_SHORT)
			throw new org.omg.CORBA.BAD_OPERATION();
		return short_att_value;
	}

	public void short_att_value (short[] _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.ATT_SHORT;
		short_att_value = _x;
	}

	public int[] long_att_value ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.ATT_LONG)
			throw new org.omg.CORBA.BAD_OPERATION();
		return long_att_value;
	}

	public void long_att_value (int[] _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.ATT_LONG;
		long_att_value = _x;
	}

	public long[] long64_att_value ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.ATT_LONG64)
			throw new org.omg.CORBA.BAD_OPERATION();
		return long64_att_value;
	}

	public void long64_att_value (long[] _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.ATT_LONG64;
		long64_att_value = _x;
	}

	public float[] float_att_value ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.ATT_FLOAT)
			throw new org.omg.CORBA.BAD_OPERATION();
		return float_att_value;
	}

	public void float_att_value (float[] _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.ATT_FLOAT;
		float_att_value = _x;
	}

	public double[] double_att_value ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.ATT_DOUBLE)
			throw new org.omg.CORBA.BAD_OPERATION();
		return double_att_value;
	}

	public void double_att_value (double[] _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.ATT_DOUBLE;
		double_att_value = _x;
	}

	public byte[] uchar_att_value ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.ATT_UCHAR)
			throw new org.omg.CORBA.BAD_OPERATION();
		return uchar_att_value;
	}

	public void uchar_att_value (byte[] _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.ATT_UCHAR;
		uchar_att_value = _x;
	}

	public short[] ushort_att_value ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.ATT_USHORT)
			throw new org.omg.CORBA.BAD_OPERATION();
		return ushort_att_value;
	}

	public void ushort_att_value (short[] _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.ATT_USHORT;
		ushort_att_value = _x;
	}

	public int[] ulong_att_value ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.ATT_ULONG)
			throw new org.omg.CORBA.BAD_OPERATION();
		return ulong_att_value;
	}

	public void ulong_att_value (int[] _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.ATT_ULONG;
		ulong_att_value = _x;
	}

	public long[] ulong64_att_value ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.ATT_ULONG64)
			throw new org.omg.CORBA.BAD_OPERATION();
		return ulong64_att_value;
	}

	public void ulong64_att_value (long[] _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.ATT_ULONG64;
		ulong64_att_value = _x;
	}

	public java.lang.String[] string_att_value ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.ATT_STRING)
			throw new org.omg.CORBA.BAD_OPERATION();
		return string_att_value;
	}

	public void string_att_value (java.lang.String[] _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.ATT_STRING;
		string_att_value = _x;
	}

	public fr.esrf.Tango.DevState[] state_att_value ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.ATT_STATE)
			throw new org.omg.CORBA.BAD_OPERATION();
		return state_att_value;
	}

	public void state_att_value (fr.esrf.Tango.DevState[] _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.ATT_STATE;
		state_att_value = _x;
	}

	public fr.esrf.Tango.DevState dev_state_att ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.DEVICE_STATE)
			throw new org.omg.CORBA.BAD_OPERATION();
		return dev_state_att;
	}

	public void dev_state_att (fr.esrf.Tango.DevState _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.DEVICE_STATE;
		dev_state_att = _x;
	}

	public fr.esrf.Tango.DevEncoded[] encoded_att_value ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.ATT_ENCODED)
			throw new org.omg.CORBA.BAD_OPERATION();
		return encoded_att_value;
	}

	public void encoded_att_value (fr.esrf.Tango.DevEncoded[] _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.ATT_ENCODED;
		encoded_att_value = _x;
	}

	public boolean union_no_data ()
	{
		if (discriminator != fr.esrf.Tango.AttributeDataType.NO_DATA)
			throw new org.omg.CORBA.BAD_OPERATION();
		return union_no_data;
	}

	public void union_no_data (boolean _x)
	{
		discriminator = fr.esrf.Tango.AttributeDataType.NO_DATA;
		union_no_data = _x;
	}

}
