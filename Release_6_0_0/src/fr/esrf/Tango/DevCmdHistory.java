package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevCmdHistory"
 *	@author JacORB IDL compiler 
 */

public final class DevCmdHistory
	implements org.omg.CORBA.portable.IDLEntity
{
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
