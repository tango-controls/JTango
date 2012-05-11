package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "ZmqCallInfo"
 *	@author JacORB IDL compiler 
 */

public final class ZmqCallInfo
	implements org.omg.CORBA.portable.IDLEntity
{
	public ZmqCallInfo(){}
	public int version;
	public java.lang.String method_name = "";
	public byte[] oid;
	public boolean call_is_except;
	public ZmqCallInfo(int version, java.lang.String method_name, byte[] oid, boolean call_is_except)
	{
		this.version = version;
		this.method_name = method_name;
		this.oid = oid;
		this.call_is_except = call_is_except;
	}
}
