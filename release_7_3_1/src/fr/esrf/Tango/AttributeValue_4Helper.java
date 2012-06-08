package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "AttributeValue_4"
 *	@author JacORB IDL compiler 
 */

public final class AttributeValue_4Helper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.AttributeValue_4Helper.id(),"AttributeValue_4",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("value", fr.esrf.Tango.AttrValUnionHelper.type(), null),new org.omg.CORBA.StructMember("quality", fr.esrf.Tango.AttrQualityHelper.type(), null),new org.omg.CORBA.StructMember("data_format", fr.esrf.Tango.AttrDataFormatHelper.type(), null),new org.omg.CORBA.StructMember("time", fr.esrf.Tango.TimeValHelper.type(), null),new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("r_dim", fr.esrf.Tango.AttributeDimHelper.type(), null),new org.omg.CORBA.StructMember("w_dim", fr.esrf.Tango.AttributeDimHelper.type(), null),new org.omg.CORBA.StructMember("err_list", fr.esrf.Tango.DevErrorListHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.AttributeValue_4 s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.AttributeValue_4 extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/AttributeValue_4:1.0";
	}
	public static fr.esrf.Tango.AttributeValue_4 read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.AttributeValue_4 result = new fr.esrf.Tango.AttributeValue_4();
		result.value=fr.esrf.Tango.AttrValUnionHelper.read(in);
		result.quality=fr.esrf.Tango.AttrQualityHelper.read(in);
		result.data_format=fr.esrf.Tango.AttrDataFormatHelper.read(in);
		result.time=fr.esrf.Tango.TimeValHelper.read(in);
		result.name=in.read_string();
		result.r_dim=fr.esrf.Tango.AttributeDimHelper.read(in);
		result.w_dim=fr.esrf.Tango.AttributeDimHelper.read(in);
		result.err_list = fr.esrf.Tango.DevErrorListHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.AttributeValue_4 s)
	{
		fr.esrf.Tango.AttrValUnionHelper.write(out,s.value);
		fr.esrf.Tango.AttrQualityHelper.write(out,s.quality);
		fr.esrf.Tango.AttrDataFormatHelper.write(out,s.data_format);
		fr.esrf.Tango.TimeValHelper.write(out,s.time);
		out.write_string(s.name);
		fr.esrf.Tango.AttributeDimHelper.write(out,s.r_dim);
		fr.esrf.Tango.AttributeDimHelper.write(out,s.w_dim);
		fr.esrf.Tango.DevErrorListHelper.write(out,s.err_list);
	}
}
