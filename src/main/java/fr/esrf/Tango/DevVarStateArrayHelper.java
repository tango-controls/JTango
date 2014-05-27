package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarStateArray".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevVarStateArrayHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, fr.esrf.Tango.DevState[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static fr.esrf.Tango.DevState[] extract (final org.omg.CORBA.Any any)
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
			synchronized(DevVarStateArrayHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStateArrayHelper.id(), "DevVarStateArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.DevStateHelper.id(),"DevState",new String[]{"ON","OFF","CLOSE","OPEN","INSERT","EXTRACT","MOVING","STANDBY","FAULT","INIT","RUNNING","ALARM","DISABLE","UNKNOWN"})));
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/DevVarStateArray:1.0";
	}
	public static fr.esrf.Tango.DevState[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		fr.esrf.Tango.DevState[] _result;
		int _l_result12 = _in.read_long();
		try
		{
			 int x = _in.available();
			 if ( x > 0 && _l_result12 > x )
				{
					throw new org.omg.CORBA.MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result12);
				}
		}
		catch (java.io.IOException e)
		{
		}
		_result = new fr.esrf.Tango.DevState[_l_result12];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=fr.esrf.Tango.DevStateHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, fr.esrf.Tango.DevState[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			fr.esrf.Tango.DevStateHelper.write(_out,_s[i]);
		}

	}
}
