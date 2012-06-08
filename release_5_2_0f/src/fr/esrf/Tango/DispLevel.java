package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "DispLevel"
 *	@author JacORB IDL compiler 
 */

public final class DispLevel
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _OPERATOR = 0;
	public static final DispLevel OPERATOR = new DispLevel(_OPERATOR);
	public static final int _EXPERT = 1;
	public static final DispLevel EXPERT = new DispLevel(_EXPERT);
	public int value()
	{
		return value;
	}
	public static DispLevel from_int(int value)
	{
		switch (value) {
			case _OPERATOR: return OPERATOR;
			case _EXPERT: return EXPERT;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected DispLevel(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
