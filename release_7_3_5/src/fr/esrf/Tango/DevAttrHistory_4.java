package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevAttrHistory_4"
 *	@author JacORB IDL compiler 
 */

public final class DevAttrHistory_4
	implements org.omg.CORBA.portable.IDLEntity
{
	public DevAttrHistory_4(){}
	public java.lang.String name = "";
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
	public DevAttrHistory_4(java.lang.String name, fr.esrf.Tango.TimeVal[] dates, org.omg.CORBA.Any value, fr.esrf.Tango.AttrQuality[] quals, fr.esrf.Tango.EltInArray[] quals_array, fr.esrf.Tango.AttributeDim[] r_dims, fr.esrf.Tango.EltInArray[] r_dims_array, fr.esrf.Tango.AttributeDim[] w_dims, fr.esrf.Tango.EltInArray[] w_dims_array, fr.esrf.Tango.DevError[][] errors, fr.esrf.Tango.EltInArray[] errors_array)
	{
		this.name = name;
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
