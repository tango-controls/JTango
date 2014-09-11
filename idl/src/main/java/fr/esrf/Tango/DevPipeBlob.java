package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevPipeBlob".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevPipeBlob
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevPipeBlob(){}
	public java.lang.String name = "";
	public fr.esrf.Tango.DevPipeDataElt[] blob_data;
	public DevPipeBlob(java.lang.String name, fr.esrf.Tango.DevPipeDataElt[] blob_data)
	{
		this.name = name;
		this.blob_data = blob_data;
	}
}
