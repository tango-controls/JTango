package fr.esrf.Tango;

/**
 * Generated from IDL struct "AttributeDim".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class AttributeDim
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public AttributeDim(){}
	public int dim_x;
	public int dim_y;
	public AttributeDim(int dim_x, int dim_y)
	{
		this.dim_x = dim_x;
		this.dim_y = dim_y;
	}
}
