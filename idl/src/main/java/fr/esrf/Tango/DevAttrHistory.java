package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevAttrHistory".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevAttrHistory
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevAttrHistory(){}
	public boolean attr_failed;
	public fr.esrf.Tango.AttributeValue value;
	public fr.esrf.Tango.DevError[] errors;
	public DevAttrHistory(boolean attr_failed, fr.esrf.Tango.AttributeValue value, fr.esrf.Tango.DevError[] errors)
	{
		this.attr_failed = attr_failed;
		this.value = value;
		this.errors = errors;
	}
}
