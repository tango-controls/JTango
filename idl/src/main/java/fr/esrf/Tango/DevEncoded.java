package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevEncoded".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevEncoded
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevEncoded(){}
	public java.lang.String encoded_format = "";
	public byte[] encoded_data;
	public DevEncoded(java.lang.String encoded_format, byte[] encoded_data)
	{
		this.encoded_format = encoded_format;
		this.encoded_data = encoded_data;
	}
}
