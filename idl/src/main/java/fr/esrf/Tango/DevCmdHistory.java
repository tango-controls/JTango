package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevCmdHistory".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevCmdHistory
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevCmdHistory(){}
	public fr.esrf.Tango.TimeVal time;
	public boolean cmd_failed;
	public org.omg.CORBA.Any value;
	public fr.esrf.Tango.DevError[] errors;
	public DevCmdHistory(fr.esrf.Tango.TimeVal time, boolean cmd_failed, org.omg.CORBA.Any value, fr.esrf.Tango.DevError[] errors)
	{
		this.time = time;
		this.cmd_failed = cmd_failed;
		this.value = value;
		this.errors = errors;
	}
}
