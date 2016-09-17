package fr.esrf.Tango;

/**
 * Generated from IDL alias "PipeConfigList".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class PipeConfigListHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, fr.esrf.Tango.PipeConfig[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static fr.esrf.Tango.PipeConfig[] extract (final org.omg.CORBA.Any any)
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
			synchronized(PipeConfigListHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.PipeConfigListHelper.id(), "PipeConfigList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.PipeConfigHelper.id(),"PipeConfig",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("description", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("label", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("level", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.DispLevelHelper.id(),"DispLevel",new String[]{"OPERATOR","EXPERT","DL_UNKNOWN"}), null),new org.omg.CORBA.StructMember("writable", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.PipeWriteTypeHelper.id(),"PipeWriteType",new String[]{"PIPE_READ","PIPE_READ_WRITE","PIPE_WT_UNKNOWN"}), null),new org.omg.CORBA.StructMember("extensions", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))), null)})));
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/PipeConfigList:1.0";
	}
	public static fr.esrf.Tango.PipeConfig[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		fr.esrf.Tango.PipeConfig[] _result;
		int _l_result25 = _in.read_long();
		try
		{
			 int x = _in.available();
			 if ( x > 0 && _l_result25 > x )
				{
					throw new org.omg.CORBA.MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result25);
				}
		}
		catch (java.io.IOException e)
		{
		}
		_result = new fr.esrf.Tango.PipeConfig[_l_result25];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=fr.esrf.Tango.PipeConfigHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, fr.esrf.Tango.PipeConfig[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			fr.esrf.Tango.PipeConfigHelper.write(_out,_s[i]);
		}

	}
}
