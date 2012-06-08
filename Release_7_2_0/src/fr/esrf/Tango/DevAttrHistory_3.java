package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevAttrHistory_3"
 *	@author JacORB IDL compiler 
 */

public final class DevAttrHistory_3
	implements org.omg.CORBA.portable.IDLEntity
{
	public DevAttrHistory_3(){}
	public boolean attr_failed;
	public fr.esrf.Tango.AttributeValue_3 value;
	public DevAttrHistory_3(boolean attr_failed, fr.esrf.Tango.AttributeValue_3 value)
	{
		this.attr_failed = attr_failed;
		this.value = value;
	}
}
