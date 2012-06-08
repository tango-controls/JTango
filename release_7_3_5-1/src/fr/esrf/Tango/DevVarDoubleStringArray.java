package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevVarDoubleStringArray"
 *	@author JacORB IDL compiler 
 */

public final class DevVarDoubleStringArray
	implements org.omg.CORBA.portable.IDLEntity
{
	public DevVarDoubleStringArray(){}
	public double[] dvalue;
	public java.lang.String[] svalue;
	public DevVarDoubleStringArray(double[] dvalue, java.lang.String[] svalue)
	{
		this.dvalue = dvalue;
		this.svalue = svalue;
	}
}
