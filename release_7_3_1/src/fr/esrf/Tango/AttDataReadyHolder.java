package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "AttDataReady"
 *	@author JacORB IDL compiler 
 */

public final class AttDataReadyHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttDataReady value;

	public AttDataReadyHolder ()
	{
	}
	public AttDataReadyHolder(final fr.esrf.Tango.AttDataReady initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.AttDataReadyHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.AttDataReadyHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.AttDataReadyHelper.write(_out, value);
	}
}
