package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "EltInArrayList"
 *	@author JacORB IDL compiler 
 */

public final class EltInArrayListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.EltInArray[] value;

	public EltInArrayListHolder ()
	{
	}
	public EltInArrayListHolder (final fr.esrf.Tango.EltInArray[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return EltInArrayListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EltInArrayListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		EltInArrayListHelper.write (out,value);
	}
}
