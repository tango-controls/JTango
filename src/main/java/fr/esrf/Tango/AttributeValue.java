package fr.esrf.Tango;

/**
 * Generated from IDL struct "AttributeValue".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class AttributeValue
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public AttributeValue(){}
	public org.omg.CORBA.Any value;
	public fr.esrf.Tango.AttrQuality quality;
	public fr.esrf.Tango.TimeVal time;
	public java.lang.String name = "";
	public int dim_x;
	public int dim_y;
	public AttributeValue(org.omg.CORBA.Any value, fr.esrf.Tango.AttrQuality quality, fr.esrf.Tango.TimeVal time, java.lang.String name, int dim_x, int dim_y)
	{
		this.value = value;
		this.quality = quality;
		this.time = time;
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
	}
}
