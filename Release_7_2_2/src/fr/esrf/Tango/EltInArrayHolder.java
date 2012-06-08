package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "EltInArray"
 *	@author JacORB IDL compiler 
 */

public final class EltInArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.EltInArray value;

	public EltInArrayHolder ()
	{
	}
	public EltInArrayHolder(final fr.esrf.Tango.EltInArray initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.EltInArrayHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.EltInArrayHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.EltInArrayHelper.write(_out, value);
	}
}
