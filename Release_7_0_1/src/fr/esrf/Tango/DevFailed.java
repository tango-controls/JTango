package fr.esrf.Tango;

/**
 *	Generated from IDL definition of exception "DevFailed"
 *	@author JacORB IDL compiler 
 */

public class DevFailed
	extends org.omg.CORBA.UserException
{
	public DevFailed()
	{
		super(fr.esrf.Tango.DevFailedHelper.id());
	}

	public fr.esrf.Tango.DevError[] errors;
	public DevFailed(java.lang.String _reason,fr.esrf.Tango.DevError[] errors)
	{
		super(fr.esrf.Tango.DevFailedHelper.id()+ " " + _reason);
		this.errors = errors;
	}
	public DevFailed(fr.esrf.Tango.DevError[] errors)
	{
		super(fr.esrf.Tango.DevFailedHelper.id());
		this.errors = errors;
	}
}
