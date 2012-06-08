package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "AttributeValueList_3"
 *	@author JacORB IDL compiler 
 */

public final class AttributeValueList_3Helper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, fr.esrf.Tango.AttributeValue_3[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static fr.esrf.Tango.AttributeValue_3[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.AttributeValueList_3Helper.id(), "AttributeValueList_3",org.omg.CORBA.ORB.init().create_sequence_tc(0, fr.esrf.Tango.AttributeValue_3Helper.type()));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/AttributeValueList_3:1.0";
	}
	public static fr.esrf.Tango.AttributeValue_3[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		fr.esrf.Tango.AttributeValue_3[] _result;
		int _l_result20 = _in.read_long();
		_result = new fr.esrf.Tango.AttributeValue_3[_l_result20];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=fr.esrf.Tango.AttributeValue_3Helper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, fr.esrf.Tango.AttributeValue_3[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			fr.esrf.Tango.AttributeValue_3Helper.write(_out,_s[i]);
		}

	}
}
