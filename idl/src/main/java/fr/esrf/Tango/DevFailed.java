package fr.esrf.Tango;

/**
 * Generated from IDL exception "DevFailed".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */
//TODO extend DevFailed hierarchy and use precise exceptions in method calls i.e. remove IDL layer
public class DevFailed
	extends org.omg.CORBA.UserException
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
    public fr.esrf.Tango.DevError[] errors;

	public DevFailed()
	{
		super(fr.esrf.Tango.DevFailedHelper.id());
	}
	public DevFailed(java.lang.String _reason,fr.esrf.Tango.DevError[] errors)
	{
		super(_reason);
		this.errors = errors;
	}
	public DevFailed(fr.esrf.Tango.DevError[] errors)
	{
		super(fr.esrf.Tango.DevFailedHelper.id());
		this.errors = errors;
	}
}
