package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "AttributeValue"
 *	@author JacORB IDL compiler 
 */

public final class AttributeValue
	implements org.omg.CORBA.portable.IDLEntity
{
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
