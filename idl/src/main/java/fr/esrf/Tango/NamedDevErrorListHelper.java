package fr.esrf.Tango;

/**
 * Generated from IDL alias "NamedDevErrorList".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class NamedDevErrorListHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, fr.esrf.Tango.NamedDevError[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static fr.esrf.Tango.NamedDevError[] extract (final org.omg.CORBA.Any any)
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
			synchronized(NamedDevErrorListHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.NamedDevErrorListHelper.id(), "NamedDevErrorList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.NamedDevErrorHelper.id(),"NamedDevError",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("index_in_call", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("err_list", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevErrorListHelper.id(), "DevErrorList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevErrorHelper.id(),"DevError",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("reason", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("severity", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.ErrSeverityHelper.id(),"ErrSeverity",new String[]{"WARN","ERR","PANIC"}), null),new org.omg.CORBA.StructMember("desc", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("origin", org.omg.CORBA.ORB.init().create_string_tc(0), null)}))), null)})));
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/NamedDevErrorList:1.0";
	}
	public static fr.esrf.Tango.NamedDevError[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		fr.esrf.Tango.NamedDevError[] _result;
		int _l_result16 = _in.read_long();
		try
		{
			 int x = _in.available();
			 if ( x > 0 && _l_result16 > x )
				{
					throw new org.omg.CORBA.MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result16);
				}
		}
		catch (java.io.IOException e)
		{
		}
		_result = new fr.esrf.Tango.NamedDevError[_l_result16];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=fr.esrf.Tango.NamedDevErrorHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, fr.esrf.Tango.NamedDevError[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			fr.esrf.Tango.NamedDevErrorHelper.write(_out,_s[i]);
		}

	}
}
