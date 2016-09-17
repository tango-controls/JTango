package fr.esrf.Tango;
/**
 * Generated from IDL enum "ErrSeverity".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class ErrSeverity
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _WARN = 0;
	public static final ErrSeverity WARN = new ErrSeverity(_WARN);
	public static final int _ERR = 1;
	public static final ErrSeverity ERR = new ErrSeverity(_ERR);
	public static final int _PANIC = 2;
	public static final ErrSeverity PANIC = new ErrSeverity(_PANIC);
	public int value()
	{
		return value;
	}
	public static ErrSeverity from_int(int value)
	{
		switch (value) {
			case _WARN: return WARN;
			case _ERR: return ERR;
			case _PANIC: return PANIC;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _WARN: return "WARN";
			case _ERR: return "ERR";
			case _PANIC: return "PANIC";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected ErrSeverity(int i)
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
