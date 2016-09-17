package fr.esrf.Tango;

/**
 * Generated from IDL struct "PipeConfig".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class PipeConfig
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public PipeConfig(){}
	public java.lang.String name = "";
	public java.lang.String description = "";
	public java.lang.String label = "";
	public fr.esrf.Tango.DispLevel level;
	public fr.esrf.Tango.PipeWriteType writable;
	public java.lang.String[] extensions;
	public PipeConfig(java.lang.String name, java.lang.String description, java.lang.String label, fr.esrf.Tango.DispLevel level, fr.esrf.Tango.PipeWriteType writable, java.lang.String[] extensions)
	{
		this.name = name;
		this.description = description;
		this.label = label;
		this.level = level;
		this.writable = writable;
		this.extensions = extensions;
	}
}
