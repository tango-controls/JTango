package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "DevSource"
 *	@author JacORB IDL compiler 
 */

public final class DevSource
	implements org.omg.CORBA.portable.IDLEntity
{
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
	protected DevSource(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
