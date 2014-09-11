package fr.esrf.Tango;

/**
 * Generated from IDL struct "NamedDevError".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class NamedDevError
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
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
