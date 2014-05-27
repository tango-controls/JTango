package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevVarDoubleStringArray".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
