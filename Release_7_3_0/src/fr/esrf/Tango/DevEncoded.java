package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevEncoded"
 *	@author JacORB IDL compiler 
 */

public final class DevEncoded
	implements org.omg.CORBA.portable.IDLEntity
{
	public DevEncoded(){}
	public java.lang.String encoded_format;
	public byte[] encoded_data;
	public DevEncoded(java.lang.String encoded_format, byte[] encoded_data)
	{
		this.encoded_format = encoded_format;
		this.encoded_data = encoded_data;
	}
}
