package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "NamedDevError"
 *	@author JacORB IDL compiler 
 */

public final class NamedDevError
	implements org.omg.CORBA.portable.IDLEntity
{
	public NamedDevError(){}
	public java.lang.String name = "";
	public int index_in_call;
	public fr.esrf.Tango.DevError[] err_list;
	public NamedDevError(java.lang.String name, int index_in_call, fr.esrf.Tango.DevError[] err_list)
	{
		this.name = name;
		this.index_in_call = index_in_call;
		this.err_list = err_list;
	}
}
