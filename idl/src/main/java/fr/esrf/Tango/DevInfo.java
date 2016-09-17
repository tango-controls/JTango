package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevInfo".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevInfo
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
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
