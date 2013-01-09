package fr.esrf.Tango;

/**
 * Generated from IDL exception "DevFailed".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at Dec 11, 2012 4:18:48 PM
 */

public class DevFailed
	extends org.omg.CORBA.UserException
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevFailed()
	{
		super(fr.esrf.Tango.DevFailedHelper.id());
	}

	public fr.esrf.Tango.DevError[] errors;
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
