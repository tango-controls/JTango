package fr.esrf.Tango;

/**
 * Generated from IDL struct "ArchiveEventProp".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class ArchiveEventProp
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public ArchiveEventProp(){}
	public java.lang.String rel_change = "";
	public java.lang.String abs_change = "";
	public java.lang.String period = "";
	public java.lang.String[] extensions;
	public ArchiveEventProp(java.lang.String rel_change, java.lang.String abs_change, java.lang.String period, java.lang.String[] extensions)
	{
		this.rel_change = rel_change;
		this.abs_change = abs_change;
		this.period = period;
		this.extensions = extensions;
	}
}
