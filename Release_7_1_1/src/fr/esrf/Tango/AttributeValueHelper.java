package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "AttributeValue"
 *	@author JacORB IDL compiler 
 */

public final class AttributeValueHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.AttributeValueHelper.id(),"AttributeValue",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("value", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(11)), null),new org.omg.CORBA.StructMember("quality", fr.esrf.Tango.AttrQualityHelper.type(), null),new org.omg.CORBA.StructMember("time", fr.esrf.Tango.TimeValHelper.type(), null),new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("dim_x", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("dim_y", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.AttributeValue s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.AttributeValue extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/AttributeValue:1.0";
	}
	public static fr.esrf.Tango.AttributeValue read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.AttributeValue result = new fr.esrf.Tango.AttributeValue();
		result.value=in.read_any();
		result.quality=fr.esrf.Tango.AttrQualityHelper.read(in);
		result.time=fr.esrf.Tango.TimeValHelper.read(in);
		result.name=in.read_string();
		result.dim_x=in.read_long();
		result.dim_y=in.read_long();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.AttributeValue s)
	{
		out.write_any(s.value);
		fr.esrf.Tango.AttrQualityHelper.write(out,s.quality);
		fr.esrf.Tango.TimeValHelper.write(out,s.time);
		out.write_string(s.name);
		out.write_long(s.dim_x);
		out.write_long(s.dim_y);
	}
}
