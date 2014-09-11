package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevAttrHistory_5".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevAttrHistory_5
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevAttrHistory_5(){}
	public java.lang.String name = "";
	public fr.esrf.Tango.AttrDataFormat data_format;
	public int data_type;
	public fr.esrf.Tango.TimeVal[] dates;
	public org.omg.CORBA.Any value;
	public fr.esrf.Tango.AttrQuality[] quals;
	public fr.esrf.Tango.EltInArray[] quals_array;
	public fr.esrf.Tango.AttributeDim[] r_dims;
	public fr.esrf.Tango.EltInArray[] r_dims_array;
	public fr.esrf.Tango.AttributeDim[] w_dims;
	public fr.esrf.Tango.EltInArray[] w_dims_array;
	public fr.esrf.Tango.DevError[][] errors;
	public fr.esrf.Tango.EltInArray[] errors_array;
	public DevAttrHistory_5(java.lang.String name, fr.esrf.Tango.AttrDataFormat data_format, int data_type, fr.esrf.Tango.TimeVal[] dates, org.omg.CORBA.Any value, fr.esrf.Tango.AttrQuality[] quals, fr.esrf.Tango.EltInArray[] quals_array, fr.esrf.Tango.AttributeDim[] r_dims, fr.esrf.Tango.EltInArray[] r_dims_array, fr.esrf.Tango.AttributeDim[] w_dims, fr.esrf.Tango.EltInArray[] w_dims_array, fr.esrf.Tango.DevError[][] errors, fr.esrf.Tango.EltInArray[] errors_array)
	{
		this.name = name;
		this.data_format = data_format;
		this.data_type = data_type;
		this.dates = dates;
		this.value = value;
		this.quals = quals;
		this.quals_array = quals_array;
		this.r_dims = r_dims;
		this.r_dims_array = r_dims_array;
		this.w_dims = w_dims;
		this.w_dims_array = w_dims_array;
		this.errors = errors;
		this.errors_array = errors_array;
	}
}
