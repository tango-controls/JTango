package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "AttributeValueList"
 *	@author JacORB IDL compiler 
 */

public final class AttributeValueListHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, fr.esrf.Tango.AttributeValue[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static fr.esrf.Tango.AttributeValue[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.AttributeValueListHelper.id(), "AttributeValueList",org.omg.CORBA.ORB.init().create_sequence_tc(0, fr.esrf.Tango.AttributeValueHelper.type()));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/AttributeValueList:1.0";
	}
	public static fr.esrf.Tango.AttributeValue[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		fr.esrf.Tango.AttributeValue[] _result;
		int _l_result19 = _in.read_long();
		_result = new fr.esrf.Tango.AttributeValue[_l_result19];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=fr.esrf.Tango.AttributeValueHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, fr.esrf.Tango.AttributeValue[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			fr.esrf.Tango.AttributeValueHelper.write(_out,_s[i]);
		}

	}
}
