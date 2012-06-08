package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "AttributeConfig_2"
 *	@author JacORB IDL compiler 
 */

public final class AttributeConfig_2
	implements org.omg.CORBA.portable.IDLEntity
{
	public AttributeConfig_2(){}
	public java.lang.String name = "";
	public fr.esrf.Tango.AttrWriteType writable;
	public fr.esrf.Tango.AttrDataFormat data_format;
	public int data_type;
	public int max_dim_x;
	public int max_dim_y;
	public java.lang.String description = "";
	public java.lang.String label = "";
	public java.lang.String unit = "";
	public java.lang.String standard_unit = "";
	public java.lang.String display_unit = "";
	public java.lang.String format = "";
	public java.lang.String min_value = "";
	public java.lang.String max_value = "";
	public java.lang.String min_alarm = "";
	public java.lang.String max_alarm = "";
	public java.lang.String writable_attr_name = "";
	public fr.esrf.Tango.DispLevel level;
	public java.lang.String[] extensions;
	public AttributeConfig_2(java.lang.String name, fr.esrf.Tango.AttrWriteType writable, fr.esrf.Tango.AttrDataFormat data_format, int data_type, int max_dim_x, int max_dim_y, java.lang.String description, java.lang.String label, java.lang.String unit, java.lang.String standard_unit, java.lang.String display_unit, java.lang.String format, java.lang.String min_value, java.lang.String max_value, java.lang.String min_alarm, java.lang.String max_alarm, java.lang.String writable_attr_name, fr.esrf.Tango.DispLevel level, java.lang.String[] extensions)
	{
		this.name = name;
		this.writable = writable;
		this.data_format = data_format;
		this.data_type = data_type;
		this.max_dim_x = max_dim_x;
		this.max_dim_y = max_dim_y;
		this.description = description;
		this.label = label;
		this.unit = unit;
		this.standard_unit = standard_unit;
		this.display_unit = display_unit;
		this.format = format;
		this.min_value = min_value;
		this.max_value = max_value;
		this.min_alarm = min_alarm;
		this.max_alarm = max_alarm;
		this.writable_attr_name = writable_attr_name;
		this.level = level;
		this.extensions = extensions;
	}
}
