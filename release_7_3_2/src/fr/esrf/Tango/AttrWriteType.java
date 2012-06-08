package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "AttrWriteType"
 *	@author JacORB IDL compiler 
 */

public final class AttrWriteType
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _READ = 0;
	public static final AttrWriteType READ = new AttrWriteType(_READ);
	public static final int _READ_WITH_WRITE = 1;
	public static final AttrWriteType READ_WITH_WRITE = new AttrWriteType(_READ_WITH_WRITE);
	public static final int _WRITE = 2;
	public static final AttrWriteType WRITE = new AttrWriteType(_WRITE);
	public static final int _READ_WRITE = 3;
	public static final AttrWriteType READ_WRITE = new AttrWriteType(_READ_WRITE);
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
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected AttrWriteType(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
