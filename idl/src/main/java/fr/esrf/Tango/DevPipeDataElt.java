package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevPipeDataElt".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevPipeDataElt
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevPipeDataElt(){}
	public java.lang.String name = "";
	public fr.esrf.Tango.AttrValUnion value;
	public fr.esrf.Tango.DevPipeDataElt[] inner_blob;
	public java.lang.String inner_blob_name = "";
	public DevPipeDataElt(java.lang.String name, fr.esrf.Tango.AttrValUnion value, fr.esrf.Tango.DevPipeDataElt[] inner_blob, java.lang.String inner_blob_name)
	{
		this.name = name;
		this.value = value;
		this.inner_blob = inner_blob;
		this.inner_blob_name = inner_blob_name;
	}
}
