package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevAttrHistory"
 *	@author JacORB IDL compiler 
 */

public final class DevAttrHistory
	implements org.omg.CORBA.portable.IDLEntity
{
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
