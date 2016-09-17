package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevVarDoubleStringArray".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevVarDoubleStringArray
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevVarDoubleStringArray(){}
	public double[] dvalue;
	public java.lang.String[] svalue;
	public DevVarDoubleStringArray(double[] dvalue, java.lang.String[] svalue)
	{
		this.dvalue = dvalue;
		this.svalue = svalue;
	}
}
