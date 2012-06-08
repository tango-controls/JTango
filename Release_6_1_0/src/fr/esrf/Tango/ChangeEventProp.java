package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "ChangeEventProp"
 *	@author JacORB IDL compiler 
 */

public final class ChangeEventProp
	implements org.omg.CORBA.portable.IDLEntity
{
	public ChangeEventProp(){}
	public java.lang.String rel_change = "";
	public java.lang.String abs_change = "";
	public java.lang.String[] extensions;
	public ChangeEventProp(java.lang.String rel_change, java.lang.String abs_change, java.lang.String[] extensions)
	{
		this.rel_change = rel_change;
		this.abs_change = abs_change;
		this.extensions = extensions;
	}
}
