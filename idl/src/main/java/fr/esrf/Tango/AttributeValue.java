package fr.esrf.Tango;

/**
 * Generated from IDL struct "AttributeValue".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
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
