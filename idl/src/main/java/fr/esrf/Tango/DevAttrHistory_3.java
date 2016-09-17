package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevAttrHistory_3".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevAttrHistory_3
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevAttrHistory_3(){}
	public boolean attr_failed;
	public fr.esrf.Tango.AttributeValue_3 value;
	public DevAttrHistory_3(boolean attr_failed, fr.esrf.Tango.AttributeValue_3 value)
	{
		this.attr_failed = attr_failed;
		this.value = value;
	}
}
