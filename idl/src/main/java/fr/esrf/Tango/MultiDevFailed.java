package fr.esrf.Tango;

/**
 * Generated from IDL exception "MultiDevFailed".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
