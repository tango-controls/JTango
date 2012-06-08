package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "AttributeValue_4"
 *	@author JacORB IDL compiler 
 */

public final class AttributeValue_4
	implements org.omg.CORBA.portable.IDLEntity
{
	public AttributeValue_4(){}
	public fr.esrf.Tango.AttrValUnion value;
	public fr.esrf.Tango.AttrQuality quality;
	public fr.esrf.Tango.AttrDataFormat data_format;
	public fr.esrf.Tango.TimeVal time;
	public java.lang.String name = "";
	public fr.esrf.Tango.AttributeDim r_dim;
	public fr.esrf.Tango.AttributeDim w_dim;
	public fr.esrf.Tango.DevError[] err_list;
	public AttributeValue_4(fr.esrf.Tango.AttrValUnion value, fr.esrf.Tango.AttrQuality quality, fr.esrf.Tango.AttrDataFormat data_format, fr.esrf.Tango.TimeVal time, java.lang.String name, fr.esrf.Tango.AttributeDim r_dim, fr.esrf.Tango.AttributeDim w_dim, fr.esrf.Tango.DevError[] err_list)
	{
		this.value = value;
		this.quality = quality;
		this.data_format = data_format;
		this.time = time;
		this.name = name;
		this.r_dim = r_dim;
		this.w_dim = w_dim;
		this.err_list = err_list;
	}
}
