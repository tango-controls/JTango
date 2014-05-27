package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevEncoded".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevEncoded
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevEncoded(){}
	public java.lang.String encoded_format;
	public byte[] encoded_data;
	public DevEncoded(java.lang.String encoded_format, byte[] encoded_data)
	{
		this.encoded_format = encoded_format;
		this.encoded_data = encoded_data;
	}
}
