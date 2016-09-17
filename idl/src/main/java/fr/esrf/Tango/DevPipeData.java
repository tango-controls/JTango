package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevPipeData".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevPipeData
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevPipeData(){}
	public java.lang.String name = "";
	public fr.esrf.Tango.TimeVal time;
	public fr.esrf.Tango.DevPipeBlob data_blob;
	public DevPipeData(java.lang.String name, fr.esrf.Tango.TimeVal time, fr.esrf.Tango.DevPipeBlob data_blob)
	{
		this.name = name;
		this.time = time;
		this.data_blob = data_blob;
	}
}
