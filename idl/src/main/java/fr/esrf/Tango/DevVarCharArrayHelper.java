package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarCharArray".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevVarCharArrayHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, byte[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static byte[] extract (final org.omg.CORBA.Any any)
	{
		if ( any.type().kind() == org.omg.CORBA.TCKind.tk_null)
		{
			throw new org.omg.CORBA.BAD_OPERATION ("Can't extract from Any with null type.");
		}
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevVarCharArrayHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarCharArrayHelper.id(), "DevVarCharArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(10))));
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/DevVarCharArray:1.0";
	}
	public static byte[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		byte[] _result;
		int _l_result6 = _in.read_long();
		try
		{
			 int x = _in.available();
			 if ( x > 0 && _l_result6 > x )
				{
					throw new org.omg.CORBA.MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result6);
				}
		}
		catch (java.io.IOException e)
		{
		}
		_result = new byte[_l_result6];
		_in.read_octet_array(_result,0,_l_result6);
		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, byte[] _s)
	{
		
		_out.write_long(_s.length);
		_out.write_octet_array(_s,0,_s.length);
	}
}
