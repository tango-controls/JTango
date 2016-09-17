package fr.esrf.Tango;

/**
 * Generated from IDL exception "MultiDevFailed".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class MultiDevFailed
	extends org.omg.CORBA.UserException
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public MultiDevFailed()
	{
		super(fr.esrf.Tango.MultiDevFailedHelper.id());
	}

	public fr.esrf.Tango.NamedDevError[] errors;
	public MultiDevFailed(java.lang.String _reason,fr.esrf.Tango.NamedDevError[] errors)
	{
		super(_reason);
		this.errors = errors;
	}
	public MultiDevFailed(fr.esrf.Tango.NamedDevError[] errors)
	{
		super(fr.esrf.Tango.MultiDevFailedHelper.id());
		this.errors = errors;
	}
}
