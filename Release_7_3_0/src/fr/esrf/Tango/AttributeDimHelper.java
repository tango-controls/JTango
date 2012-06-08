package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "AttributeDim"
 *	@author JacORB IDL compiler 
 */

public final class AttributeDimHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.AttributeDimHelper.id(),"AttributeDim",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("dim_x", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("dim_y", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.AttributeDim s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.AttributeDim extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/AttributeDim:1.0";
	}
	public static fr.esrf.Tango.AttributeDim read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.AttributeDim result = new fr.esrf.Tango.AttributeDim();
		result.dim_x=in.read_long();
		result.dim_y=in.read_long();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.AttributeDim s)
	{
		out.write_long(s.dim_x);
		out.write_long(s.dim_y);
	}
}
