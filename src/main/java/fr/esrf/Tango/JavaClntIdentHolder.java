package fr.esrf.Tango;

/**
 * Generated from IDL struct "JavaClntIdent".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class JavaClntIdentHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.JavaClntIdent value;

	public JavaClntIdentHolder ()
	{
	}
	public JavaClntIdentHolder(final fr.esrf.Tango.JavaClntIdent initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.JavaClntIdentHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.JavaClntIdentHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.JavaClntIdentHelper.write(_out, value);
	}
}
