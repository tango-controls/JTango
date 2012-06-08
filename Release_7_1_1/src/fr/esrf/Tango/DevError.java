package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevError"
 *	@author JacORB IDL compiler 
 */

public final class DevError
	implements org.omg.CORBA.portable.IDLEntity
{
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
