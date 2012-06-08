package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "PeriodicEventProp"
 *	@author JacORB IDL compiler 
 */

public final class PeriodicEventProp
	implements org.omg.CORBA.portable.IDLEntity
{
	public PeriodicEventProp(){}
	public java.lang.String period = "";
	public java.lang.String[] extensions;
	public PeriodicEventProp(java.lang.String period, java.lang.String[] extensions)
	{
		this.period = period;
		this.extensions = extensions;
	}
}
