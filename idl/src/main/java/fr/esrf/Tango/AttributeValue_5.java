package fr.esrf.Tango;

/**
 * Generated from IDL struct "AttributeValue_5".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class AttributeValue_5
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public AttributeValue_5(){}
	public fr.esrf.Tango.AttrValUnion value;
	public fr.esrf.Tango.AttrQuality quality;
	public fr.esrf.Tango.AttrDataFormat data_format;
	public int data_type;
	public fr.esrf.Tango.TimeVal time;
	public java.lang.String name = "";
	public fr.esrf.Tango.AttributeDim r_dim;
	public fr.esrf.Tango.AttributeDim w_dim;
	public fr.esrf.Tango.DevError[] err_list;
	public AttributeValue_5(fr.esrf.Tango.AttrValUnion value, fr.esrf.Tango.AttrQuality quality, fr.esrf.Tango.AttrDataFormat data_format, int data_type, fr.esrf.Tango.TimeVal time, java.lang.String name, fr.esrf.Tango.AttributeDim r_dim, fr.esrf.Tango.AttributeDim w_dim, fr.esrf.Tango.DevError[] err_list)
	{
		this.value = value;
		this.quality = quality;
		this.data_format = data_format;
		this.data_type = data_type;
		this.time = time;
		this.name = name;
		this.r_dim = r_dim;
		this.w_dim = w_dim;
		this.err_list = err_list;
	}
}
