package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevVarLongStringArray"
 *	@author JacORB IDL compiler 
 */

public final class DevVarLongStringArray
	implements org.omg.CORBA.portable.IDLEntity
{
	public DevVarLongStringArray(){}
	public int[] lvalue;
	public java.lang.String[] svalue;
	public DevVarLongStringArray(int[] lvalue, java.lang.String[] svalue)
	{
		this.lvalue = lvalue;
		this.svalue = svalue;
	}
}
