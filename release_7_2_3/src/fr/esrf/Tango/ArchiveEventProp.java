package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "ArchiveEventProp"
 *	@author JacORB IDL compiler 
 */

public final class ArchiveEventProp
	implements org.omg.CORBA.portable.IDLEntity
{
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
