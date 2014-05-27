package fr.esrf.Tango;

/**
 * Generated from IDL union "ClntIdent".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class ClntIdent
	implements org.omg.CORBA.portable.IDLEntity
{
	private fr.esrf.Tango.LockerLanguage discriminator;
	private int cpp_clnt;
	private fr.esrf.Tango.JavaClntIdent java_clnt;

	public ClntIdent ()
	{
	}

	public fr.esrf.Tango.LockerLanguage discriminator ()
	{
		return discriminator;
	}

	public int cpp_clnt ()
	{
		if (discriminator != fr.esrf.Tango.LockerLanguage.CPP)
			throw new org.omg.CORBA.BAD_OPERATION();
		return cpp_clnt;
	}

	public void cpp_clnt (int _x)
	{
		discriminator = fr.esrf.Tango.LockerLanguage.CPP;
		cpp_clnt = _x;
	}

	public fr.esrf.Tango.JavaClntIdent java_clnt ()
	{
		if (discriminator != fr.esrf.Tango.LockerLanguage.JAVA)
			throw new org.omg.CORBA.BAD_OPERATION();
		return java_clnt;
	}

	public void java_clnt (fr.esrf.Tango.JavaClntIdent _x)
	{
		discriminator = fr.esrf.Tango.LockerLanguage.JAVA;
		java_clnt = _x;
	}

}
