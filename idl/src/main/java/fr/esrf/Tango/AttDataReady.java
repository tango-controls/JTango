package fr.esrf.Tango;

/**
 * Generated from IDL struct "AttDataReady".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
