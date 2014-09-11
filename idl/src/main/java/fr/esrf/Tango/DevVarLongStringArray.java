package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevVarLongStringArray".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevVarLongStringArray
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevVarLongStringArray(){}
	public int[] lvalue;
	public java.lang.String[] svalue;
	public DevVarLongStringArray(int[] lvalue, java.lang.String[] svalue)
	{
		this.lvalue = lvalue;
		this.svalue = svalue;
	}
}
