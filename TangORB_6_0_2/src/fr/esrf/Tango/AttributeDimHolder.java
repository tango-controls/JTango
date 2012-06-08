package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "AttributeDim"
 *	@author JacORB IDL compiler 
 */

public final class AttributeDimHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeDim value;

	public AttributeDimHolder ()
	{
	}
	public AttributeDimHolder(final fr.esrf.Tango.AttributeDim initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.AttributeDimHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.AttributeDimHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.AttributeDimHelper.write(_out, value);
	}
}
