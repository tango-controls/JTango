package fr.esrf.Tango;
/**
 * Generated from IDL enum "PipeWriteType".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class PipeWriteType
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _PIPE_READ = 0;
	public static final PipeWriteType PIPE_READ = new PipeWriteType(_PIPE_READ);
	public static final int _PIPE_READ_WRITE = 1;
	public static final PipeWriteType PIPE_READ_WRITE = new PipeWriteType(_PIPE_READ_WRITE);
	public static final int _PIPE_WT_UNKNOWN = 2;
	public static final PipeWriteType PIPE_WT_UNKNOWN = new PipeWriteType(_PIPE_WT_UNKNOWN);
	public int value()
	{
		return value;
	}
	public static PipeWriteType from_int(int value)
	{
		switch (value) {
			case _PIPE_READ: return PIPE_READ;
			case _PIPE_READ_WRITE: return PIPE_READ_WRITE;
			case _PIPE_WT_UNKNOWN: return PIPE_WT_UNKNOWN;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _PIPE_READ: return "PIPE_READ";
			case _PIPE_READ_WRITE: return "PIPE_READ_WRITE";
			case _PIPE_WT_UNKNOWN: return "PIPE_WT_UNKNOWN";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected PipeWriteType(int i)
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
