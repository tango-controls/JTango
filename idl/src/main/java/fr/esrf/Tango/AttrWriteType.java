package fr.esrf.Tango;
/**
 * Generated from IDL enum "AttrWriteType".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class AttrWriteType
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _READ = 0;
	public static final AttrWriteType READ = new AttrWriteType(_READ);
	public static final int _READ_WITH_WRITE = 1;
	public static final AttrWriteType READ_WITH_WRITE = new AttrWriteType(_READ_WITH_WRITE);
	public static final int _WRITE = 2;
	public static final AttrWriteType WRITE = new AttrWriteType(_WRITE);
	public static final int _READ_WRITE = 3;
	public static final AttrWriteType READ_WRITE = new AttrWriteType(_READ_WRITE);
	public static final int _WT_UNKNOWN = 4;
	public static final AttrWriteType WT_UNKNOWN = new AttrWriteType(_WT_UNKNOWN);
	public int value()
	{
		return value;
	}
	public static AttrWriteType from_int(int value)
	{
		switch (value) {
			case _READ: return READ;
			case _READ_WITH_WRITE: return READ_WITH_WRITE;
			case _WRITE: return WRITE;
			case _READ_WRITE: return READ_WRITE;
			case _WT_UNKNOWN: return WT_UNKNOWN;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _READ: return "READ";
			case _READ_WITH_WRITE: return "READ_WITH_WRITE";
			case _WRITE: return "WRITE";
			case _READ_WRITE: return "READ_WRITE";
			case _WT_UNKNOWN: return "WT_UNKNOWN";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected AttrWriteType(int i)
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
