package fr.esrf.Tango;

/**
 *	Generated from IDL definition of exception "MultiDevFailed"
 *	@author JacORB IDL compiler 
 */

public final class MultiDevFailed
	extends org.omg.CORBA.UserException
{
	public MultiDevFailed()
	{
		super(fr.esrf.Tango.MultiDevFailedHelper.id());
	}

	public fr.esrf.Tango.NamedDevError[] errors;
	public MultiDevFailed(java.lang.String _reason,fr.esrf.Tango.NamedDevError[] errors)
	{
		super(fr.esrf.Tango.MultiDevFailedHelper.id()+ " " + _reason);
		this.errors = errors;
	}
	public MultiDevFailed(fr.esrf.Tango.NamedDevError[] errors)
	{
		super(fr.esrf.Tango.MultiDevFailedHelper.id());
		this.errors = errors;
	}
}
