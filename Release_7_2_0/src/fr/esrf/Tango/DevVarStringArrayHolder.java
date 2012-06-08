package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevVarStringArray"
 *	@author JacORB IDL compiler 
 */

public final class DevVarStringArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public java.lang.String[] value;

	public DevVarStringArrayHolder ()
	{
	}
	public DevVarStringArrayHolder (final java.lang.String[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarStringArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarStringArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarStringArrayHelper.write (out,value);
	}
}
