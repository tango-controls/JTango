package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "LockerLanguage"
 *	@author JacORB IDL compiler 
 */

public final class LockerLanguage
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _CPP = 0;
	public static final LockerLanguage CPP = new LockerLanguage(_CPP);
	public static final int _JAVA = 1;
	public static final LockerLanguage JAVA = new LockerLanguage(_JAVA);
	public int value()
	{
		return value;
	}
	public static LockerLanguage from_int(int value)
	{
		switch (value) {
			case _CPP: return CPP;
			case _JAVA: return JAVA;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected LockerLanguage(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
