package fr.esrf.Tango;
/**
 * Generated from IDL enum "DevSource".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevSource
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _DEV = 0;
	public static final DevSource DEV = new DevSource(_DEV);
	public static final int _CACHE = 1;
	public static final DevSource CACHE = new DevSource(_CACHE);
	public static final int _CACHE_DEV = 2;
	public static final DevSource CACHE_DEV = new DevSource(_CACHE_DEV);
	public int value()
	{
		return value;
	}
	public static DevSource from_int(int value)
	{
		switch (value) {
			case _DEV: return DEV;
			case _CACHE: return CACHE;
			case _CACHE_DEV: return CACHE_DEV;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _DEV: return "DEV";
			case _CACHE: return "CACHE";
			case _CACHE_DEV: return "CACHE_DEV";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected DevSource(int i)
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
