package fr.esrf.Tango;
/**
 * Generated from IDL enum "LockerLanguage".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class LockerLanguage
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
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
	public String toString()
	{
		switch (value) {
			case _CPP: return "CPP";
			case _JAVA: return "JAVA";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected LockerLanguage(int i)
	{
		value = i;
	}
	/**
	 * Designate replacement object when deserialized from stream. See
	 * http://www.omg.org/docs/ptc/02-01-03.htm#Issue4271
	 *
	 * @throws java.io.ObjectStreamException
	 */
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
