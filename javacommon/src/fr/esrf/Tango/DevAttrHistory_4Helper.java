package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "DevAttrHistory_4"
 *	@author JacORB IDL compiler 
 */

public final class DevAttrHistory_4Helper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevAttrHistory_4Helper.id(),"DevAttrHistory_4",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("dates", fr.esrf.Tango.TimeValListHelper.type(), null),new org.omg.CORBA.StructMember("value", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(11)), null),new org.omg.CORBA.StructMember("quals", fr.esrf.Tango.AttrQualityListHelper.type(), null),new org.omg.CORBA.StructMember("quals_array", fr.esrf.Tango.EltInArrayListHelper.type(), null),new org.omg.CORBA.StructMember("r_dims", fr.esrf.Tango.AttributeDimListHelper.type(), null),new org.omg.CORBA.StructMember("r_dims_array", fr.esrf.Tango.EltInArrayListHelper.type(), null),new org.omg.CORBA.StructMember("w_dims", fr.esrf.Tango.AttributeDimListHelper.type(), null),new org.omg.CORBA.StructMember("w_dims_array", fr.esrf.Tango.EltInArrayListHelper.type(), null),new org.omg.CORBA.StructMember("errors", fr.esrf.Tango.DevErrorListListHelper.type(), null),new org.omg.CORBA.StructMember("errors_array", fr.esrf.Tango.EltInArrayListHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevAttrHistory_4 s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevAttrHistory_4 extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/DevAttrHistory_4:1.0";
	}
	public static fr.esrf.Tango.DevAttrHistory_4 read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevAttrHistory_4 result = new fr.esrf.Tango.DevAttrHistory_4();
		result.name=in.read_string();
		result.dates = fr.esrf.Tango.TimeValListHelper.read(in);
		result.value=in.read_any();
		result.quals = fr.esrf.Tango.AttrQualityListHelper.read(in);
		result.quals_array = fr.esrf.Tango.EltInArrayListHelper.read(in);
		result.r_dims = fr.esrf.Tango.AttributeDimListHelper.read(in);
		result.r_dims_array = fr.esrf.Tango.EltInArrayListHelper.read(in);
		result.w_dims = fr.esrf.Tango.AttributeDimListHelper.read(in);
		result.w_dims_array = fr.esrf.Tango.EltInArrayListHelper.read(in);
		result.errors = fr.esrf.Tango.DevErrorListListHelper.read(in);
		result.errors_array = fr.esrf.Tango.EltInArrayListHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevAttrHistory_4 s)
	{
		out.write_string(s.name);
		fr.esrf.Tango.TimeValListHelper.write(out,s.dates);
		out.write_any(s.value);
		fr.esrf.Tango.AttrQualityListHelper.write(out,s.quals);
		fr.esrf.Tango.EltInArrayListHelper.write(out,s.quals_array);
		fr.esrf.Tango.AttributeDimListHelper.write(out,s.r_dims);
		fr.esrf.Tango.EltInArrayListHelper.write(out,s.r_dims_array);
		fr.esrf.Tango.AttributeDimListHelper.write(out,s.w_dims);
		fr.esrf.Tango.EltInArrayListHelper.write(out,s.w_dims_array);
		fr.esrf.Tango.DevErrorListListHelper.write(out,s.errors);
		fr.esrf.Tango.EltInArrayListHelper.write(out,s.errors_array);
	}
}
