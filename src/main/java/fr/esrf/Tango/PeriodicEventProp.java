package fr.esrf.Tango;

/**
 * Generated from IDL struct "PeriodicEventProp".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class PeriodicEventProp
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public PeriodicEventProp(){}
	public java.lang.String period = "";
	public java.lang.String[] extensions;
	public PeriodicEventProp(java.lang.String period, java.lang.String[] extensions)
	{
		this.period = period;
		this.extensions = extensions;
	}
}
