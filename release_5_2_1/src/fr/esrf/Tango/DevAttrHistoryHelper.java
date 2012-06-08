package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "DevAttrHistory"
 *	@author JacORB IDL compiler 
 */

public final class DevAttrHistoryHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevAttrHistoryHelper.id(),"DevAttrHistory",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("attr_failed", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8)), null),new org.omg.CORBA.StructMember("value", fr.esrf.Tango.AttributeValueHelper.type(), null),new org.omg.CORBA.StructMember("errors", fr.esrf.Tango.DevErrorListHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevAttrHistory s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevAttrHistory extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/DevAttrHistory:1.0";
	}
	public static fr.esrf.Tango.DevAttrHistory read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevAttrHistory result = new fr.esrf.Tango.DevAttrHistory();
		result.attr_failed=in.read_boolean();
		result.value=fr.esrf.Tango.AttributeValueHelper.read(in);
		result.errors = fr.esrf.Tango.DevErrorListHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevAttrHistory s)
	{
		out.write_boolean(s.attr_failed);
		fr.esrf.Tango.AttributeValueHelper.write(out,s.value);
		fr.esrf.Tango.DevErrorListHelper.write(out,s.errors);
	}
}
