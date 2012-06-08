package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevInfo"
 *	@author JacORB IDL compiler 
 */

public final class DevInfo
	implements org.omg.CORBA.portable.IDLEntity
{
	public DevInfo(){}
	public java.lang.String dev_class = "";
	public java.lang.String server_id = "";
	public java.lang.String server_host = "";
	public int server_version;
	public java.lang.String doc_url = "";
	public DevInfo(java.lang.String dev_class, java.lang.String server_id, java.lang.String server_host, int server_version, java.lang.String doc_url)
	{
		this.dev_class = dev_class;
		this.server_id = server_id;
		this.server_host = server_host;
		this.server_version = server_version;
		this.doc_url = doc_url;
	}
}
