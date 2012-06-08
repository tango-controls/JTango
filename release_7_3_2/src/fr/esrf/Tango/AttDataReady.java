package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "AttDataReady"
 *	@author JacORB IDL compiler 
 */

public final class AttDataReady
	implements org.omg.CORBA.portable.IDLEntity
{
	public AttDataReady(){}
	public java.lang.String name = "";
	public int data_type;
	public int ctr;
	public AttDataReady(java.lang.String name, int data_type, int ctr)
	{
		this.name = name;
		this.data_type = data_type;
		this.ctr = ctr;
	}
}
