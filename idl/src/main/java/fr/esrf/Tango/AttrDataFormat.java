package fr.esrf.Tango;
/**
 * Generated from IDL enum "AttrDataFormat".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class AttrDataFormat
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _SCALAR = 0;
	public static final AttrDataFormat SCALAR = new AttrDataFormat(_SCALAR);
	public static final int _SPECTRUM = 1;
	public static final AttrDataFormat SPECTRUM = new AttrDataFormat(_SPECTRUM);
	public static final int _IMAGE = 2;
	public static final AttrDataFormat IMAGE = new AttrDataFormat(_IMAGE);
	public static final int _FMT_UNKNOWN = 3;
	public static final AttrDataFormat FMT_UNKNOWN = new AttrDataFormat(_FMT_UNKNOWN);
	public int value()
	{
		return value;
	}
	public static AttrDataFormat from_int(int value)
	{
		switch (value) {
			case _SCALAR: return SCALAR;
			case _SPECTRUM: return SPECTRUM;
			case _IMAGE: return IMAGE;
			case _FMT_UNKNOWN: return FMT_UNKNOWN;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _SCALAR: return "SCALAR";
			case _SPECTRUM: return "SPECTRUM";
			case _IMAGE: return "IMAGE";
			case _FMT_UNKNOWN: return "FMT_UNKNOWN";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected AttrDataFormat(int i)
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
