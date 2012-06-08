package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "AttDataReady"
 *	@author JacORB IDL compiler 
 */

public final class AttDataReadyHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.AttDataReadyHelper.id(),"AttDataReady",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("data_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("ctr", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.AttDataReady s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.AttDataReady extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/AttDataReady:1.0";
	}
	public static fr.esrf.Tango.AttDataReady read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.AttDataReady result = new fr.esrf.Tango.AttDataReady();
		result.name=in.read_string();
		result.data_type=in.read_long();
		result.ctr=in.read_long();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.AttDataReady s)
	{
		out.write_string(s.name);
		out.write_long(s.data_type);
		out.write_long(s.ctr);
	}
}
