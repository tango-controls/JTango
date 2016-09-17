package fr.esrf.Tango;

/**
 * Generated from IDL struct "ZmqCallInfo".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class ZmqCallInfo
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public ZmqCallInfo(){}
	public int version;
	public int ctr;
	public java.lang.String method_name = "";
	public byte[] oid;
	public boolean call_is_except;
	public ZmqCallInfo(int version, int ctr, java.lang.String method_name, byte[] oid, boolean call_is_except)
	{
		this.version = version;
		this.ctr = ctr;
		this.method_name = method_name;
		this.oid = oid;
		this.call_is_except = call_is_except;
	}
}
