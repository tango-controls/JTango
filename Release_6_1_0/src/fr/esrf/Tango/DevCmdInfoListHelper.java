package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevCmdInfoList"
 *	@author JacORB IDL compiler 
 */

public final class DevCmdInfoListHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, fr.esrf.Tango.DevCmdInfo[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static fr.esrf.Tango.DevCmdInfo[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevCmdInfoListHelper.id(), "DevCmdInfoList",org.omg.CORBA.ORB.init().create_sequence_tc(0, fr.esrf.Tango.DevCmdInfoHelper.type()));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/DevCmdInfoList:1.0";
	}
	public static fr.esrf.Tango.DevCmdInfo[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		fr.esrf.Tango.DevCmdInfo[] _result;
		int _l_result12 = _in.read_long();
		_result = new fr.esrf.Tango.DevCmdInfo[_l_result12];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=fr.esrf.Tango.DevCmdInfoHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, fr.esrf.Tango.DevCmdInfo[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			fr.esrf.Tango.DevCmdInfoHelper.write(_out,_s[i]);
		}

	}
}
