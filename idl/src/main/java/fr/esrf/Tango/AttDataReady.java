package fr.esrf.Tango;

/**
 * Generated from IDL struct "AttDataReady".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class AttDataReady
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
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
