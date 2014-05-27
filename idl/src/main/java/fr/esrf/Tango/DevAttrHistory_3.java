package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevAttrHistory_3".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
