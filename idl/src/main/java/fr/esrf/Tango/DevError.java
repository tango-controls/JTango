package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevError".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevError
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevError(){}
	public java.lang.String reason = "";
	public fr.esrf.Tango.ErrSeverity severity;
	public java.lang.String desc = "";
	public java.lang.String origin = "";
	public DevError(java.lang.String reason, fr.esrf.Tango.ErrSeverity severity, java.lang.String desc, java.lang.String origin)
	{
		this.reason = reason;
		this.severity = severity;
		this.desc = desc;
		this.origin = origin;
	}
}
