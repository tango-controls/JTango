//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: pascal_verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision: 25297 $
//
//-======================================================================


package fr.esrf.TangoDs;

import fr.esrf.Tango.*;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.DbAttribute;

import java.util.Date;
import java.util.Vector;

/**
 * This class represents a Tango attribute.
 *
 * @author $Author: pascal_verdier $
 * @version $Revision: 25297 $
 */
 
public class Attribute implements TangoConst
{

/**
 * The attribute name
 */
	protected	String			name;
/**
 * The attribute writable flag
 */
	protected	AttrWriteType		writable;
/**
 * The attribute data type.
 *
 * Only four types are suported. They are Tango_DevShort, Tango_DevLong,
 * Tango_DevDouble and Tango_DevString
 */
	protected	int			data_type;
/**
 * The attribute data format.
 *
 * Three data formats are supported. They are SCALAR, SPECTRUM and IMAGE
 */
	protected	AttrDataFormat		data_format;
/**
 * The attribute maximum x dimension.
 *
 * It is needed for SPECTRUM or IMAGE data format
 */
	protected	int			max_x;
/**
 * The attribute maximum y dimension.
 *
 * It is necessary only for IMAGE data format
 */
	protected	int			max_y;

/**
 * The attribute label
 */	
	protected	String			label;
/**
 * The attribute description
 */
	protected	String			description;
/**
 * The attribute unit
 */
	protected	String			unit;
/**
 * The attribute standard unit
 */
	protected	String			standard_unit;
/**
 * The attribute display unit
 */
	protected	String			display_unit;
/**
 * The attribute format.
 *
 * This string specifies how an attribute value must be printed
 */
	protected	String			format;
/**
 * The name of the associated writable attribute
 */
	protected	String			writable_attr_name;
/**
 * The attribute minimum alarm level
 */
	protected	String			min_alarm_str;
/**
 * The attribute maximun alarm level
 */
	protected	String			max_alarm_str;
/**
 * The attribute minimum value
 */
	protected	String			min_value_str;
/**
 * The attribute maximum value
 */
	protected	String			max_value_str;

/**
 * The attribute minimum alarm in binary format
 */	
	protected 	TangoAttrCheckVal	min_alarm;
/**
 * The attribute miaaximum alarm in binary format
 */
	protected 	TangoAttrCheckVal	max_alarm;
/**
 * The attribute minimum value in binary format
 */
	protected 	TangoAttrCheckVal	min_value;
/**
 * The attribute maximum value in binary format
 */
	protected 	TangoAttrCheckVal	max_value;

/**
 * Flag set to true if a minimum alarm is defined
 */	
	protected	boolean			check_min_alarm;
/**
 * Flag set to true if a maximum alarm is defined
 */
	protected	boolean			check_max_alarm;
/**
 * Flag set to true if a minimum value is defined
 */
	protected	boolean			check_min_value;
/**
 * Flag set to true if a maximum alarm is defined
 */
	protected	boolean			check_max_value;
/**
 * Flag set to true if the read value is below the alarm level
 */
	protected	boolean			min_alarm_on;
/**
 * Flag set to true if the read value is above the alarm level
 */
	protected	boolean			max_alarm_on;
	
/**
 * Flag set to true if the date must be set
 */	
	protected	boolean			date;
/**
 * The attribute data size
 */	
	protected	int			data_size;

/**
 * The attribute quality factor
 */
	protected	AttrQuality		quality;
/**
 * The date when attribute was read
 */	
	protected	TimeVal			when;
/**
 * The attribute value
 */	
	protected	TangoAttrValue		value;
/**
 * A flag set to true if the attribute value has been updated
 */	
	protected	boolean			value_flag;
	
/**
 * A flag set to true if the attribute is actve (beeing read)
 */	
	protected	boolean			active = false;
    /**
     * Index in the main attribute vector of the associated writable attribute (if any)
     */
    protected int assoc_ind;
    protected DispLevel disp_level = DispLevel.OPERATOR;
    protected int poll_period;
    int dim_x;
    int dim_y;
    private		DevEncoded		tmp_enc[]  = new DevEncoded[2];
	private		DevState		tmp_st[]   = new DevState[2];
	private		short			tmp_sh[]   = new short[2];
	private		int				tmp_lo[]   = new int[2];
	private		long			tmp_lo64[] = new long[2];
	private		float			tmp_fl[]   = new float[2];
	private		double			tmp_db[]   = new double[2];
	private		String			tmp_str[]  = new String[2];
	private		boolean			tmp_bool[] = new boolean[2];
	private		int				name_size;
	private		String			name_upper;

	
//+-------------------------------------------------------------------------
//
// method : 		Attribute 
// 
// description : 	constructor for the Attribute class
//
//--------------------------------------------------------------------------
/**
 * Create a new Attribute object.
 *
 * @param prop_list The attribute properties list. Each property is an object
 * of the AttrProperty class
 * @param tmp_attr The temporary attribute object
 * @param dev_name The device name
 * @exception DevFailed If the command sent to the database failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Attribute(Vector prop_list,Attr tmp_attr,String dev_name) throws DevFailed
	{
		check_min_alarm = false;
		check_max_alarm = false;
		check_min_value = false;
		check_max_value = false;
		date = true;
		
		min_alarm = new TangoAttrCheckVal();
		max_alarm = new TangoAttrCheckVal();
		min_value = new TangoAttrCheckVal();
		max_value = new TangoAttrCheckVal();
		
		value = new TangoAttrValue();
		when = new TimeVal();
		quality = AttrQuality.ATTR_VALID;

//
// Init the attribute name
//

		name = tmp_attr.get_name();
		name_size = name.length();
		name_upper = name.toUpperCase();

//
// Init the remaining attribute main characteristic
//

		data_type   = tmp_attr.get_type();
		writable    = tmp_attr.get_writable();
		data_format = tmp_attr.get_format();
		disp_level  = tmp_attr.get_disp_level();
		poll_period = tmp_attr.get_polling_period();
		writable_attr_name = tmp_attr.get_assoc();
		switch(data_format.value())
		{
		case AttrDataFormat._SPECTRUM:
			max_x = ((SpectrumAttr)(tmp_attr)).get_max_x();
			max_y = 0;
			dim_y = 0;
			break;
				
		case AttrDataFormat._IMAGE:
			max_x = ((ImageAttr)(tmp_attr)).get_max_x();
			max_y = ((ImageAttr)(tmp_attr)).get_max_y();
			break;
				
		default :
			max_x = 1;
			max_y = 0;
			dim_x = 1;
			dim_y = 0;
		}
			
//
// Initialise optional properties
//

		init_opt_prop(prop_list,dev_name);
	}

//+-------------------------------------------------------------------------
//
// method : 		find
// 
// description : 	Find a AttrProperty object in a vector of Attrproperty
//			objects with a specified name.
//
// in :			prop_list : The property vector
//			dev_name : The property name
//
// This method returns the position in the vetcor of the wanted property
//
//--------------------------------------------------------------------------

	private int find(Vector prop_list,String prop_name) throws DevFailed
	{
		int i;
		int nb_prop = prop_list.size();
		
		for (i = 0;i < nb_prop;i++)
		{
			if (((AttrProperty)(prop_list.elementAt(i))).get_name().equals(prop_name) == true)
				break;
		}
		
		if (i == nb_prop)
		{
			StringBuffer o = new StringBuffer("Property ");
			o.append(prop_name);
			o.append(" not found");
			
			Except.throw_exception("API_AttrWrongDefined",o.toString(),
					       "Attribute.find");
		}
		
		return i;
	}
	
//+-------------------------------------------------------------------------
//
// method : 		init_opt_prop
// 
// description : 	Init the optional properties
//
// in :			prop_list : The property vector
//			dev_name : The device name (usefull for error)
//
//--------------------------------------------------------------------------

	private void init_opt_prop(Vector prop_list,String dev_name) throws DevFailed
	{
	
//
// Init the label property
//

		label = get_attr_value(prop_list,"label");
	
//
// Init the description property
//

		description = get_attr_value(prop_list,"description");
	
//
// Init the unit property
//

		unit = get_attr_value(prop_list,"unit");
	
//
// Init the standard unit property
//

		standard_unit = get_attr_value(prop_list,"standard_unit");
	
//
// Init the display unit property
//
	
		display_unit = get_attr_value(prop_list,"display_unit");
	
//
// Init the format property
//

		format = get_attr_value(prop_list,"format");
		
//
// Init the min alarm property
//

		min_alarm_str = get_attr_value(prop_list,"min_alarm");
		if ((min_alarm_str.equals(Tango_AlrmValueNotSpec) == false) &&
		    (data_type != Tango_DEV_STRING) && (data_type != Tango_DEV_BOOLEAN))
		{
			try
			{
				switch (data_type)
				{
				case Tango_DEV_SHORT:
					min_alarm.sh = Short.parseShort(min_alarm_str);
					break;
			
				case Tango_DEV_LONG:
					min_alarm.lg = Integer.parseInt(min_alarm_str);
					break;
			
				case Tango_DEV_DOUBLE:
					min_alarm.db = Double.valueOf(min_alarm_str);
					break;
				}
			}
			catch (NumberFormatException ex)
			{
				throw_err_format("min_alarm",dev_name);
			}
			check_min_alarm = true;
		}
	
//
// Init the max alarm property
//

		max_alarm_str = get_attr_value(prop_list,"max_alarm");
		if ((max_alarm_str.equals(Tango_AlrmValueNotSpec) == false) && 
		    (data_type != Tango_DEV_STRING) && (data_type != Tango_DEV_BOOLEAN))
		{
			try
			{
				switch (data_type)
				{
				case Tango_DEV_SHORT:
					max_alarm.sh = Short.parseShort(max_alarm_str);
					break;
			
				case Tango_DEV_LONG:
					max_alarm.lg = Integer.parseInt(max_alarm_str);
					break;
			
				case Tango_DEV_DOUBLE:
					max_alarm.db = Double.valueOf(max_alarm_str);
					break;
				}
			}
			catch (NumberFormatException ex)
			{
				throw_err_format("max_alarm",dev_name);
			}
			check_max_alarm = true;
		}
		
//
// Init the min value property
//

		min_value_str = get_attr_value(prop_list,"min_value");
		if ((min_value_str.equals(Tango_AlrmValueNotSpec) == false) && 
		    (data_type != Tango_DEV_STRING) && (data_type != Tango_DEV_BOOLEAN))
		{
			try
			{
				switch (data_type)
				{
				case Tango_DEV_SHORT:
					min_value.sh = Short.parseShort(min_value_str);
					break;
			
				case Tango_DEV_LONG:
					min_value.lg = Integer.parseInt(min_value_str);
					break;
			
				case Tango_DEV_DOUBLE:
					min_value.db = Double.valueOf(min_value_str);
					break;
				}
			}
			catch (NumberFormatException ex)
			{
				throw_err_format("min_value",dev_name);
			}
			check_min_value = true;
		}
		
//
// Init the max value property
//

		max_value_str = get_attr_value(prop_list,"max_value");
		if ((max_value_str.equals(Tango_AlrmValueNotSpec) == false) && 
		    (data_type != Tango_DEV_STRING) && (data_type != Tango_DEV_BOOLEAN))
		{
			try
			{
				switch (data_type)
				{
				case Tango_DEV_SHORT:
					max_value.sh = Short.parseShort(max_value_str);
					break;
			
				case Tango_DEV_LONG:
					max_value.lg = Integer.parseInt(max_value_str);
					break;
			
				case Tango_DEV_DOUBLE:
					max_value.db = Double.valueOf(max_value_str);
					break;
				}
			}
			catch (NumberFormatException ex)
			{
				throw_err_format("max_value",dev_name);
			}			
			check_max_value = true;
		}
	}

	
//+-------------------------------------------------------------------------
//
// method : 		get_attr_value
// 
// description : 	Retrieve a property value as a string from the vector
//			of properties
//
// in :			prop_list : The property vector
//			prop_name : the property name
//
//--------------------------------------------------------------------------


	private String get_attr_value(Vector prop_list,String prop_name) throws DevFailed
	{
		int pos = 0;
//
// Get the property value
//

		try
		{
			pos = find(prop_list,prop_name);
		}
		catch (DevFailed ex)
		{
			StringBuffer o = new StringBuffer("Property ");
			o.append(prop_name);
			o.append(" is missing for attribute ");
			o.append(name);
			
			Except.throw_exception("API_AttrOptProp",o.toString(),
					       "Attribute.get_attr_value");
		}
		
		return ((AttrProperty)(prop_list.elementAt(pos))).get_value()[0];
	}

//+-------------------------------------------------------------------------
//
// method : 		throw_err_format
// 
// description : 	Throw a Tango DevFailed exception when an error format
//			is detected in the string which should be converted
//			to a number
//
// in :			prop_name : The property name
//			dev_name : The device name
//
//--------------------------------------------------------------------------

	private void throw_err_format(String prop_name,String dev_name) throws DevFailed
	{
		StringBuffer o = new StringBuffer("Device ");
		o.append(dev_name);
		o.append("-> Attribute");
		o.append(name);	
		o.append("\nThe property ");
		o.append(prop_name);
		o.append(" is defined in a unsupported format");
				
		Except.throw_exception("API_AttrOptProp",o.toString(),
				       "Attribute.throw_err_format");
	}

//+-------------------------------------------------------------------------
//
// method : 		set_data_size
// 
// description : 	Compute the attribute amount of data
//
//--------------------------------------------------------------------------

	private void set_data_size()
	{
		switch(data_format.value())
		{
		case AttrDataFormat._SCALAR :
			data_size = 1;
			break;
		
		case AttrDataFormat._SPECTRUM :
			data_size = dim_x;
			break;
		
		case AttrDataFormat._IMAGE :
			data_size = dim_x * dim_y;
			break;
		}
	}

//+-------------------------------------------------------------------------
//
// method : 		is_writ_associated
// 
// description : 	Check if the attribute has an associated writable
//			attribute
//
// This method returns a boolean set to true if the atribute has an associatied
// writable attribute
//
//--------------------------------------------------------------------------

/**
 * Check if the attribute has an associated writable attribute.
 *
 * This method returns a boolean set to true if the attribute has a writable
 * attribute associated to it.
 *
 * @return A boolean set to true if there is an associated writable attribute
 */
 
	public boolean is_writ_associated()
	{
		return (! writable_attr_name.equals(Tango_AssocWritNotSpec));
	}

//+-------------------------------------------------------------------------
//
// method : 		is_alarmed
// 
// description : 	Check if the attribute has an an alarm value set
//
// This method returns a boolean set to true if the atribute has an alarm
// value defined
//
//--------------------------------------------------------------------------

/**
 * Check if the attribute has an alarm value defined.
 *
 * This method returns a boolean set to true if the attribute has an alarm
 * value defined.
 *
 * @return A boolean set to true if there is an alarm defined.
 */
 
	public boolean is_alarmed()
	{
		return (check_min_alarm || check_max_alarm);
	}


//+-------------------------------------------------------------------------
//
// method : 		get_properties
// 
// description : 	Init the AttributeConfig with all the attribute
//			properties value
//
//--------------------------------------------------------------------------
/**
 * Get attribute properties.
 *
 * This method initialise the fields of a AttributeConfig object with the 
 * attribute properties value
 *
 * @param conf A AttributeConfig object.
 */
	public void get_properties(AttributeConfig conf)
	{

//
// Copy mandatory properties
//

		conf.writable = writable;
		conf.data_format = data_format;
		conf.max_dim_x = max_x;
		conf.max_dim_y = max_y;
		conf.data_type = data_type;
		conf.name = name;

//
// Copy optional properties
//

		conf.label = label;
		conf.description = description;
		conf.unit = unit;
		conf.standard_unit = standard_unit;
		conf.display_unit = display_unit;
		conf.format = format;
		conf.writable_attr_name = writable_attr_name;
		conf.min_alarm = min_alarm_str;
		conf.max_alarm = max_alarm_str;
		conf.min_value = min_value_str;
		conf.max_value = max_value_str;
	
//
// No extension nowdays
//

		conf.extensions = new String[0];
	}
//======================================================================
//======================================================================
	AttributeConfig_2 get_properties_2()
	{
		AttributeConfig_2	conf = new AttributeConfig_2();
		// Copy mandatory properties
		conf.writable = writable;
		conf.data_format = data_format;
		conf.max_dim_x = max_x;
		conf.max_dim_y = max_y;
		conf.data_type = data_type;
		conf.name = name;

		// Copy optional properties
		conf.label = label;
		conf.description = description;
		conf.unit = unit;
		conf.standard_unit = standard_unit;
		conf.display_unit = display_unit;
		conf.format = format;
		conf.writable_attr_name = writable_attr_name;
		conf.min_alarm = min_alarm_str;
		conf.max_alarm = max_alarm_str;
		conf.min_value = min_value_str;
		conf.max_value = max_value_str;

		conf.level = disp_level;
		
		// No extension nowdays
		conf.extensions = new String[0];
		return conf;
	}

//+-------------------------------------------------------------------------
//
// method : 		set_properties
// 
// description : 	Init the AttributeConfig with all the attribute
//			properties value
//
//--------------------------------------------------------------------------

 
	void set_properties(AttributeConfig conf,String dev_name) throws DevFailed
	{

//
// Copy only a sub-set of the new properties
//

		description = conf.description;
		label = conf.label;
		unit = conf.unit;
		standard_unit = conf.standard_unit;
		display_unit = conf.display_unit;
		format = conf.format;

//
// For the last four properties, convert their value to the right type
//

		min_alarm_str = conf.min_alarm;
		if ((min_alarm_str.equals(Tango_AlrmValueNotSpec) == false) &&
		    (data_type != Tango_DEV_STRING) && (data_type != Tango_DEV_BOOLEAN))
		{
			try
			{
				switch (data_type)
				{
				case Tango_DEV_SHORT:
					min_alarm.sh = Short.parseShort(min_alarm_str);
					break;
			
				case Tango_DEV_LONG:
					min_alarm.lg = Integer.parseInt(min_alarm_str);
					break;
			
				case Tango_DEV_DOUBLE:
					min_alarm.db = Double.valueOf(min_alarm_str);
					break;
				}
			}
			catch (NumberFormatException ex)
			{
				throw_err_format("min_alarm",dev_name);
			}
			check_min_alarm = true;
		}
		else
			check_min_value = false;
	
//
// Init the max alarm property
//

		max_alarm_str = conf.max_alarm;
		if ((max_alarm_str.equals(Tango_AlrmValueNotSpec) == false) && 
		    (data_type != Tango_DEV_STRING) && (data_type != Tango_DEV_BOOLEAN))
		{
			try
			{
				switch (data_type)
				{
				case Tango_DEV_SHORT:
					max_alarm.sh = Short.parseShort(max_alarm_str);
					break;
			
				case Tango_DEV_LONG:
					max_alarm.lg = Integer.parseInt(max_alarm_str);
					break;
			
				case Tango_DEV_DOUBLE:
					max_alarm.db = Double.valueOf(max_alarm_str);
					break;
				}
			}
			catch (NumberFormatException ex)
			{
				throw_err_format("max_alarm",dev_name);
			}
			check_max_alarm = true;
		}
		else
			check_max_alarm = false;
		
//
// Init the min value property
//

		min_value_str = conf.min_value;
		if ((min_value_str.equals(Tango_AlrmValueNotSpec) == false) && 
		    (data_type != Tango_DEV_STRING) && (data_type != Tango_DEV_BOOLEAN))
		{
			try
			{
				switch (data_type)
				{
				case Tango_DEV_SHORT:
					min_value.sh = Short.parseShort(min_value_str);
					break;
			
				case Tango_DEV_LONG:
					min_value.lg = Integer.parseInt(min_value_str);
					break;
			
				case Tango_DEV_DOUBLE:
					min_value.db = Double.valueOf(min_value_str);
					break;
				}
			}
			catch (NumberFormatException ex)
			{
				throw_err_format("min_value",dev_name);
			}
			check_min_value = true;
		}
		else
			check_min_value = false;
		
//
// Init the max value property
//

		max_value_str = conf.max_value;
		if ((max_value_str.equals(Tango_AlrmValueNotSpec) == false) && 
		    (data_type != Tango_DEV_STRING) && (data_type != Tango_DEV_BOOLEAN))
		{
			try
			{
				switch (data_type)
				{
				case Tango_DEV_SHORT:
					max_value.sh = Short.parseShort(max_value_str);
					break;
			
				case Tango_DEV_LONG:
					max_value.lg = Integer.parseInt(max_value_str);
					break;
			
				case Tango_DEV_DOUBLE:
					max_value.db = Double.valueOf(max_value_str);
					break;
				}
			}
			catch (NumberFormatException ex)
			{
				throw_err_format("max_value",dev_name);
			}			
			check_max_value = true;
		}
		else
			check_max_value = false;
	}

//+-------------------------------------------------------------------------
//
// method : 		upd_database
// 
// description : 	Update the tango database with the new attribute
//			values
//
//--------------------------------------------------------------------------
	void upd_database(AttributeConfig conf,String dev_name) throws DevFailed
	{
		Util.out4.println("Entering upd_database method for attribute " + name);
		
		DbAttribute	db_attr = new DbAttribute(name);

		// Check if each attribute property is not the default value. Only properties
		// not having the default value will be sent to the database
		if (conf.description.equals(Tango_DescNotSpec) != true)
			db_attr.add("description", new String[] {conf.description});
	
		if (conf.label.equals(Tango_LabelNotSpec) != true)
			db_attr.add("label", new String[] {conf.label});
	
		if (conf.unit.equals(Tango_UnitNotSpec) != true)
			db_attr.add("unit", new String[] {conf.unit});
	
		if (conf.standard_unit.equals(Tango_StdUnitNotSpec) != true)
			db_attr.add("standard_unit", new String[] {conf.standard_unit});

		if (conf.display_unit.equals(Tango_DispUnitNotSpec) != true)
			db_attr.add("display_unit", new String[] {conf.display_unit});

		if (conf.format.equals(Tango_FormatNotSpec) != true)
			db_attr.add("format", new String[] {conf.format});

		if (conf.min_value.equals(Tango_AlrmValueNotSpec) != true)
			db_attr.add("min_value", new String[] {conf.min_value});

		if (conf.max_value.equals(Tango_AlrmValueNotSpec) != true)
			db_attr.add("max_value", new String[] {conf.max_value});

		if (conf.min_alarm.equals(Tango_AlrmValueNotSpec) != true)
			db_attr.add("min_alarm", new String[] {conf.min_alarm});
	
		if (conf.max_alarm.equals(Tango_AlrmValueNotSpec) != true)
			db_attr.add("max_alarm", new String[] {conf.max_alarm});

		// Update db only if needed
		if (db_attr.size()>0)
		{		
			Util.out4.println(db_attr.size() + " properties to update in db");
			ApiUtil.get_db_obj().put_device_attribute_property(dev_name, db_attr);
		}
						
		Util.out4.println("Leaving upd_database method");
	}

//+-------------------------------------------------------------------------
//
// method : 		add_write_value
// 
// description : 	These methods add the associated writable attribute
//			value to the read attribute buffer and create a
//			sequence from the attribute internal buffer
//
// in :			val : The associated write attribute value
//
//--------------------------------------------------------------------------
	
	void add_write_value(DevEncoded val)
	{
		tmp_enc[1] = val;
		value.enc_value = tmp_enc;	
	}

	void add_write_value(DevState val)
	{
		tmp_st[1] = val;
		value.state_seq = tmp_st;	
	}

	void add_write_value(boolean val)
	{
		tmp_bool[1] = val;
		value.bool_seq = tmp_bool;	
	}

	void add_write_value(short val)
	{
		tmp_sh[1] = val;
		value.sh_seq = tmp_sh;	
	}

	void add_write_value(int val)
	{
		tmp_lo[1] = val;
		value.lg_seq = tmp_lo;	
	}

	void add_write_value(long val)
	{
		tmp_lo64[1] = val;
		value.lg64_seq = tmp_lo64;	
	}

	void add_write_value(float val)
	{
		tmp_fl[1] = val;
		value.fl_seq = tmp_fl;	
	}

	void add_write_value(double val)
	{
		tmp_db[1] = val;
		value.db_seq = tmp_db;	
	}

	void add_write_value(String val)
	{
		tmp_str[1] = val;
		value.str_seq = tmp_str;	
	}


//+-------------------------------------------------------------------------
//
// method : 		set_value
// 
// description : 	Set the attribute read value and quality. This method
//			automatically set the date when it has been called
//			This method is overloaded several times for all the
//			supported attribute data type
//
// in :			p_data : The attribute read value
//			qua : The attribute read value quality
//
//--------------------------------------------------------------------------


/**
 * Set internal attribute value (for Tango_DevState attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data.
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @param y The attribuute y dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public void set_value(DevState[] p_data,int x,int y) throws DevFailed
	{

		// Throw exception if type is not correct
		if (data_type != Tango_DEV_STATE)
			Except.throw_exception("API_AttrOptProp",
							"Invalid data type for attribute " + name,
					       "Attribute.set_value");

		// Check that data size is less than the given max
		if ((x > max_x) || (y > max_y))
			Except.throw_exception("API_AttrOptProp",
				               "Data size for attribute " + name +" exceeds given limit",
				      	       "Attribute.set_value");

		// Compute data size and set default quality to valid
		dim_x = x;
		dim_y = y;
		set_data_size();
		quality = AttrQuality.ATTR_VALID;

		// If the data is wanted from the State command, store it in a sequence.
		// If the attribute  has an associated writable attribute, store data in a
		// temporary buffer (the write value must be added before the data is sent
		// back to the caller)
		if (date == false)
		{
			if (p_data.length == data_size)
				value.state_seq = p_data;
			else
			{
				value.state_seq = new DevState[data_size];
				System.arraycopy(p_data, 0, value.state_seq, 0, data_size);
			}
		}
		else
		{
			if ((is_writ_associated() == true))
				tmp_st[0] = p_data[0];
			else
			{
				if (p_data.length == data_size)
					value.state_seq = p_data;
				else
				{
					value.state_seq = new DevState[data_size];
					System.arraycopy(p_data, 0, value.state_seq, 0, data_size);
				}
			}
		}
		value_flag = true;
	
		// Store quality factor and reset alarm flags
		min_alarm_on = false;
		max_alarm_on = false;

		// Get time
		set_time();
	}


/**
 * Set internal attribute value (for Tango_DevEncoded attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data.
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @param y The attribuute y dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public void set_value(DevEncoded[] p_data,int x,int y) throws DevFailed
	{

		// Throw exception if type is not correct
		if (data_type != Tango_DEV_ENCODED)
			Except.throw_exception("API_AttrOptProp",
							"Invalid data type for attribute " + name,
					       "Attribute.set_value");

		// Check that data size is less than the given max
		if ((x > max_x) || (y > max_y))
			Except.throw_exception("API_AttrOptProp",
				               "Data size for attribute " + name +" exceeds given limit",
				      	       "Attribute.set_value");

		// Compute data size and set default quality to valid
		dim_x = x;
		dim_y = y;
		set_data_size();
		quality = AttrQuality.ATTR_VALID;

		// If the data is wanted from the State command, store it in a sequence.
		// If the attribute  has an associated writable attribute, store data in a
		// temporary buffer (the write value must be added before the data is sent
		// back to the caller)
		if (date == false)
		{
			if (p_data.length == data_size)
				value.enc_value = p_data;
			else
			{
				value.enc_value = new DevEncoded[data_size];
				System.arraycopy(p_data, 0, value.enc_value, 0, data_size);
			}
		}
		else
		{
			if ((is_writ_associated() == true))
				tmp_enc[0] = p_data[0];
			else
			{
				if (p_data.length == data_size)
					value.enc_value = p_data;
				else
				{
					value.enc_value = new DevEncoded[data_size];
					System.arraycopy(p_data, 0, value.enc_value, 0, data_size);
				}
			}
		}
		value_flag = true;
	
		// Store quality factor and reset alarm flags
		min_alarm_on = false;
		max_alarm_on = false;

		// Get time
		set_time();
	}


/**
 * Set internal attribute value (for Tango_DevBoolean attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data.
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @param y The attribuute y dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public void set_value(boolean[] p_data,int x,int y) throws DevFailed
	{

		// Throw exception if type is not correct
		if (data_type != Tango_DEV_BOOLEAN)
			Except.throw_exception("API_AttrOptProp",
							"Invalid data type for attribute " + name,
					       "Attribute.set_value");

		// Check that data size is less than the given max
		if ((x > max_x) || (y > max_y))
			Except.throw_exception("API_AttrOptProp",
				               "Data size for attribute " + name +" exceeds given limit",
				      	       "Attribute.set_value");

		// Compute data size and set default quality to valid
		dim_x = x;
		dim_y = y;
		set_data_size();
		quality = AttrQuality.ATTR_VALID;

		// If the data is wanted from the State command, store it in a sequence.
		// If the attribute  has an associated writable attribute, store data in a
		// temporary buffer (the write value must be added before the data is sent
		// back to the caller)
		if (date == false)
		{
			if (p_data.length == data_size)
				value.bool_seq = p_data;
			else
			{
				value.bool_seq = new boolean[data_size];
				System.arraycopy(p_data, 0, value.bool_seq, 0, data_size);
			}
		}
		else
		{
			if ((is_writ_associated() == true))
				tmp_bool[0] = p_data[0];
			else
			{
				if (p_data.length == data_size)
					value.bool_seq = p_data;
				else
				{
					value.bool_seq = new boolean[data_size];
					System.arraycopy(p_data, 0, value.bool_seq, 0, data_size);
				}
			}
		}
		value_flag = true;
	
		// Store quality factor and reset alarm flags
		min_alarm_on = false;
		max_alarm_on = false;

		// Get time
		set_time();
	}

/**
 * Set internal attribute value (for Tango_DevShort attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data.
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @param y The attribuute y dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
	public void set_value(short[] p_data,int x,int y) throws DevFailed
	{
//
// Throw exception if type is not correct
//
		if (data_type != Tango_DEV_SHORT &&
			data_type != Tango_DEV_USHORT)
		{
			StringBuffer o = new StringBuffer("Invalid data type for attribute ");
			o.append(name);
			Except.throw_exception("API_AttrOptProp",o.toString(),
					       "Attribute.set_value");
		}

//
// Check that data size is less than the given max
//

		if ((x > max_x) || (y > max_y))
		{		
			StringBuffer o = new StringBuffer("Data size for attribute ");
			o.append(name);
			o.append(" exceeds given limit");
			Except.throw_exception("API_AttrOptProp",
				               o.toString(),
				      	       "Attribute.set_value");
		}

//
// Compute data size and set default quality to valid
//

		dim_x = x;
		dim_y = y;
		set_data_size();
		quality = AttrQuality.ATTR_VALID;
		
//
// If the data is wanted from the State command, store it in a sequence.
// If the attribute  has an associated writable attribute, store data in a
// temporary buffer (the write value must be added before the data is sent
// back to the caller)
//

		if (date == false)
		{
			if (p_data.length == data_size)
				value.sh_seq = p_data;
			else
			{
				value.sh_seq = new short[data_size];
				System.arraycopy(p_data, 0, value.sh_seq, 0, data_size);
			}
		}
		else
		{
			if ((is_writ_associated() == true))
				tmp_sh[0] = p_data[0];
			else
			{
				if (p_data.length == data_size)
					value.sh_seq = p_data;
				else
				{
					value.sh_seq = new short[data_size];
					System.arraycopy(p_data, 0, value.sh_seq, 0, data_size);
				}
			}
		}
		value_flag = true;
	
//
// Reset alarm flags
//

		min_alarm_on = false;
		max_alarm_on = false;

//
// Get time
//

		set_time();
	}

/**
 * Set internal attribute value (for Tango_DevLong attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data.
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @param y The attribuute y dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public void set_value(int[] p_data,int x,int y) throws DevFailed
	{

//
// Throw exception if type is not correct
//

		if (data_type != Tango_DEV_LONG &&
			data_type != Tango_DEV_ULONG)
		{
			StringBuffer o = new StringBuffer("Invalid data type for attribute ");
			o.append(name);
			Except.throw_exception("API_AttrOptProp",o.toString(),
					       "Attribute.set_value");
		}

//
// Check that data size is less than the given max
//

		if ((x > max_x) || (y > max_y))
		{		
			StringBuffer o = new StringBuffer("Data size for attribute ");
			o.append(name);
			o.append(" exceeds given limit ");
			if (x > max_x)
				o.append("(").append(x).append(" > ").append(max_x).append(")");
			if (y > max_y)
				o.append("(").append(y).append(" > ").append(max_y).append(")");
			Except.throw_exception("API_AttrOptProp",
				               o.toString(),
				      	       "Attribute.set_value");
		}

//
// Compute data size and set default quality to valid
//

		dim_x = x;
		dim_y = y;
		set_data_size();
		quality = AttrQuality.ATTR_VALID;
		
//
// If the data is wanted from the State command, store it in a sequence.
// If the attribute  has an associated writable attribute, store data in a
// temporary buffer (the write value must be added before the data is sent
// back to the caller)
//
		if (date == false)
		{
			if (p_data.length == data_size)
				value.lg_seq = p_data;
			else
			{
				value.lg_seq = new int[data_size];
				System.arraycopy(p_data, 0, value.lg_seq, 0, data_size);
			}
		}
		else
		{
			if ((is_writ_associated() == true))
				tmp_lo[0] = p_data[0];
			else
			{
				if (p_data.length == data_size)
					value.lg_seq = p_data;
				else
				{
					value.lg_seq = new int[data_size];
					System.arraycopy(p_data, 0, value.lg_seq, 0, data_size);
				}
			}
		}
		value_flag = true;
	
//
// Reset alarm flags
//

		min_alarm_on = false;
		max_alarm_on = false;

//
// Get time
//

		set_time();
	}
/**
 * Set internal attribute value (for Tango_DevLong64 attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data.
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @param y The attribuute y dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public void set_value(long[] p_data,int x,int y) throws DevFailed
	{

//
// Throw exception if type is not correct
//

		if (data_type != Tango_DEV_LONG64)
		{
			StringBuffer o = new StringBuffer("Invalid data type for attribute ");
			o.append(name);
			Except.throw_exception("API_AttrOptProp",o.toString(),
					       "Attribute.set_value");
		}

//
// Check that data size is less than the given max
//

		if ((x > max_x) || (y > max_y))
		{		
			StringBuffer o = new StringBuffer("Data size for attribute ");
			o.append(name);
			o.append(" exceeds given limit");
			Except.throw_exception("API_AttrOptProp",
				               o.toString(),
				      	       "Attribute.set_value");
		}

//
// Compute data size and set default quality to valid
//

		dim_x = x;
		dim_y = y;
		set_data_size();
		quality = AttrQuality.ATTR_VALID;
		
//
// If the data is wanted from the State command, store it in a sequence.
// If the attribute  has an associated writable attribute, store data in a
// temporary buffer (the write value must be added before the data is sent
// back to the caller)
//
		if (date == false)
		{
			if (p_data.length == data_size)
				value.lg64_seq = p_data;
			else
			{
				value.lg64_seq = new long[data_size];
				System.arraycopy(p_data, 0, value.lg64_seq, 0, data_size);
			}
		}
		else
		{
			if ((is_writ_associated() == true))
				tmp_lo64[0] = p_data[0];
			else
			{
				if (p_data.length == data_size)
					value.lg64_seq = p_data;
				else
				{
					value.lg64_seq = new long[data_size];
					System.arraycopy(p_data, 0, value.lg64_seq, 0, data_size);
				}
			}
		}
		value_flag = true;
	
//
// Reset alarm flags
//

		min_alarm_on = false;
		max_alarm_on = false;

//
// Get time
//

		set_time();
	}
/**
 * Set internal attribute value (for Tango_DevFloat attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data.
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @param y The attribuute y dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public void set_value(float[] p_data,int x,int y) throws DevFailed
	{

//
// Throw exception if type is not correct
//

		if (data_type != Tango_DEV_FLOAT)
		{
			StringBuffer o = new StringBuffer("Invalid data type for attribute ");
			o.append(name);
			Except.throw_exception("API_AttrOptProp",o.toString(),
					       "Attribute.set_value");
		}

//
// Check that data size is less than the given max
//

		if ((x > max_x) || (y > max_y))
		{		
			StringBuffer o = new StringBuffer("Data size for attribute ");
			o.append(name);
			o.append(" exceeds given limit");
			Except.throw_exception("API_AttrOptProp",
				               o.toString(),
				      	       "Attribute.set_value");
		}

//
// Compute data size and set default quality to valid
//

		dim_x = x;
		dim_y = y;
		set_data_size();
		quality = AttrQuality.ATTR_VALID;
		
//
// If the data is wanted from the State command, store it in a sequence.
// If the attribute  has an associated writable attribute, store data in a
// temporary buffer (the write value must be added before the data is sent
// back to the caller)
//

		if (date == false)
		{
			if (p_data.length == data_size)
				value.fl_seq = p_data;
			else
			{
				value.fl_seq = new float[data_size];
				System.arraycopy(p_data, 0, value.fl_seq, 0, data_size);
			}
		}
		else
		{
			if ((is_writ_associated() == true))
				tmp_fl[0] = p_data[0];
			else
			{
				if (p_data.length == data_size)
					value.fl_seq = p_data;
				else
				{
					value.fl_seq = new float[data_size];
					System.arraycopy(p_data, 0, value.fl_seq, 0, data_size);
				}
			}
		}
		value_flag = true;
	
//
// Store quality factor and reset alarm flags
//

		min_alarm_on = false;
		max_alarm_on = false;

//
// Get time
//

		set_time();
	}

/**
 * Set internal attribute value (for Tango_DevDouble attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data.
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @param y The attribuute y dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public void set_value(double[] p_data,int x,int y) throws DevFailed
	{
//
// Throw exception if type is not correct
//

		if (data_type != Tango_DEV_DOUBLE)
		{
			StringBuffer o = new StringBuffer("Invalid data type for attribute ");
			o.append(name);
			Except.throw_exception("API_AttrOptProp",o.toString(),
					       "Attribute.set_value");
		}

//
// Check that data size is less than the given max
//

		if ((x > max_x) || (y > max_y))
		{		
			StringBuffer o = new StringBuffer("Data size for attribute ");
			o.append(name);
			o.append(" exceeds given limit");
			Except.throw_exception("API_AttrOptProp",
				               o.toString(),
				      	       "Attribute.set_value");
		}

//
// Compute data size and set default quality to valid
//

		dim_x = x;
		dim_y = y;
		set_data_size();
		quality = AttrQuality.ATTR_VALID;
		
//
// If the data is wanted from the State command, store it in a sequence.
// If the attribute  has an associated writable attribute, store data in a
// temporary buffer (the write value must be added before the data is sent
// back to the caller)
//

		if (date == false)
		{
			if (p_data.length == data_size)
				value.db_seq = p_data;
			else
			{
				value.db_seq = new double[data_size];
				System.arraycopy(p_data, 0, value.db_seq, 0, data_size);
			}
		}
		else
		{
			if ((is_writ_associated() == true))
				tmp_db[0] = p_data[0];
			else
			{
				if (p_data.length == data_size)
					value.db_seq = p_data;
				else
				{
					value.db_seq = new double[data_size];
					System.arraycopy(p_data, 0, value.db_seq, 0, data_size);
				}
			}
		}
		value_flag = true;
	
//
// Store quality factor and reset alarm flags
//

		min_alarm_on = false;
		max_alarm_on = false;

//
// Get time
//

		set_time();
	}

/**
 * Set internal attribute value (for Tango_DevString attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data.
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @param y The attribuute y dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public void set_value(String[] p_data,int x,int y) throws DevFailed
	{

//
// Throw exception if type is not correct.
//

		if (data_type != Tango_DEV_STRING)
		{
			StringBuffer o = new StringBuffer("Invalid data type for attribute ");
			o.append(name);
			Except.throw_exception("API_AttrOptProp",o.toString(),
					       "Attribute.set_value");
		}

//
// Check that data size is less than the given max
//

		if ((x > max_x) || (y > max_y))
		{		
			StringBuffer o = new StringBuffer("Data size for attribute ");
			o.append(name);
			o.append(" exceeds given limit");
			Except.throw_exception("API_AttrOptProp",
				               o.toString(),
				      	       "Attribute.set_value");
		}

//
// Compute data size and set default quality to valid
//

		dim_x = x;
		dim_y = y;
		set_data_size();
		quality = AttrQuality.ATTR_VALID;
		
//
// If the data is wanted from the State command, store it in a sequence.
// If the attribute  has an associated writable attribute, store data in a
// temporary buffer (the write value must be added before the data is sent
// back to the caller)
//

		if (date == false)
		{
			if (p_data.length == data_size)
				value.str_seq = p_data;
			else
			{
				value.str_seq = new String[data_size];
				System.arraycopy(p_data, 0, value.str_seq, 0, data_size);
			}
		}
		else
		{
			if ((is_writ_associated() == true))
				tmp_str[0] = p_data[0];
			else
			{
				if (p_data.length == data_size)
					value.str_seq = p_data;
				else
				{
					value.str_seq = new String[data_size];
					System.arraycopy(p_data, 0, value.str_seq, 0, data_size);
				}
			}
		}
		value_flag = true;
	
//
// Store quality factor
//

		quality = AttrQuality.ATTR_VALID;

//
// Get time
//

		set_time();
	}

/**
 * Set internal attribute value (for DevState attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for no attribute dimension (x=1, y=0)
 *
 * @param p_data The attribute read value
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public void set_value(DevState p_data) throws DevFailed
	{
		DevState[] tmp = new DevState[1];
		tmp[0] = p_data;
		set_value(tmp,1,0);
	}

/**
 * Set internal attribute value (for DevEncoded attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for no attribute dimension (x=1, y=0)
 *
 * @param p_data The attribute read value
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public void set_value(DevEncoded p_data) throws DevFailed
	{
		DevEncoded[] tmp = new DevEncoded[1];
		tmp[0] = p_data;
		set_value(tmp,1,0);
	}
/**
 * Set internal attribute value (for boolean attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for no attribute dimension (x=1, y=0)
 *
 * @param p_data The attribute read value
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public void set_value(boolean p_data) throws DevFailed
	{
		boolean[] tmp = new boolean[1];
		tmp[0] = p_data;
		set_value(tmp,1,0);
	}

/**
 * Set internal attribute value (for short attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for no attribute dimension (x=1, y=0)
 *
 * @param p_data The attribute read value
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public void set_value(short p_data) throws DevFailed
	{
		short[] tmp = new short[1];
		tmp[0] = p_data;
		set_value(tmp,1,0);
	}

/**
 * Set internal attribute value (for int attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for no attribute dimension (x=1, y=0)
 *
 * @param p_data The attribute read value
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public void set_value(int p_data) throws DevFailed
	{
		int[] tmp = new int[1];
		tmp[0] = p_data;
		set_value(tmp,1,0);
	}

/**
 * Set internal attribute value (for int attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for no attribute dimension (x=1, y=0)
 *
 * @param p_data The attribute read value
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public void set_value(long p_data) throws DevFailed
	{
		long[] tmp = new long[1];
		tmp[0] = p_data;
		set_value(tmp,1,0);
	}

/**
 * Set internal attribute value (for double attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for no attribute dimension (x=1, y=0)
 *
 * @param p_data The attribute read value
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */		
	public void set_value(double p_data) throws DevFailed
	{
		double[] tmp = new double[1];
		tmp[0] = p_data;
		set_value(tmp,1,0);
	}

/**
 * Set internal attribute value (for String attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for no attribute dimension (x=1, y=0)
 *
 * @param p_data The attribute read value
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public void set_value(String p_data) throws DevFailed
	{
		String[] tmp = new String[1];
		tmp[0] = p_data;
		set_value(tmp,1,0);
	}
	
/**
 * Set internal attribute value (for DevState attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for a one dimension attribute dimension (y=0)
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
	public void set_value(DevState[] p_data,int x) throws DevFailed
	{
		set_value(p_data,x,0);
	}

/**
 * Set internal attribute value (for boolean attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for a one dimension attribute dimension (y=0)
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
	public void set_value(boolean[] p_data,int x) throws DevFailed
	{
		set_value(p_data,x,0);
	}

/**
 * Set internal attribute value (for short attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for a one dimension attribute dimension (y=0)
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
	public void set_value(short[] p_data,int x) throws DevFailed
	{
		set_value(p_data,x,0);
	}

/**
 * Set internal attribute value (for int attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for a one dimension attribute dimension (y=0)
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public void set_value(int[] p_data,int x) throws DevFailed
	{
		set_value(p_data,x,0);
	}

/**
 * Set internal attribute value (for int attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for a one dimension attribute dimension (y=0)
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public void set_value(long[] p_data,int x) throws DevFailed
	{
		set_value(p_data,x,0);
	}

/**
 * Set internal attribute value (for double attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for a one dimension attribute dimension (y=0)
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */		
	public void set_value(double[] p_data,int x) throws DevFailed
	{
		set_value(p_data,x,0);
	}

/**
 * Set internal attribute value (for String attribute data type).
 *
 * This method stores the attribute read value inside the object. This data will be
 * returned to the caller. This method also stores the date when it is called
 * and initilise the attribute quality to VALID data. This method must be used
 * for a one dimension attribute dimension (y=0)
 *
 * @param p_data The attribute read value
 * @param x The attribute x dimension
 * @exception DevFailed If the attribute data type is not coherent.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public void set_value(String[] p_data,int x) throws DevFailed
	{
		set_value(p_data,x,0);
	}
		
//+-------------------------------------------------------------------------
//
// method : 		set_time
// 
// description : 	Set the date if the date flag is true
//
//--------------------------------------------------------------------------

	private void set_time()
	{
		if (date == true)
		{		
			Date d = new Date();
			
			long nb_millisec = d.getTime();
	
			when.tv_sec = (int)(nb_millisec / 1000);
			when.tv_usec = (int)((nb_millisec - (when.tv_sec * 1000)) * 1000);
			when.tv_nsec = 0;
		}
	}

//+-------------------------------------------------------------------------
//
// method :		check_alarm
//
// description :	Check if the attribute is in alarm
//
// This method returns a boolean set to true if the atribute is in alarm. In
// this case, it also set the attribute quality factor to ALARM
//
//--------------------------------------------------------------------------
/**
 * Check if the attribute read value is below/above the larm level.
 *
 * @return A boolean set to true if the attribute is in alarm condition.
 * @exception DevFailed If no alarm level is defined.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
	public boolean check_alarm() throws DevFailed
	{

//
// Throw exception if no alarm is defined for this attribute
//

		if (is_alarmed() == false)
		{
			StringBuffer o = new StringBuffer("No alarm defined for attribute ");		
			o.append(name);
			Except.throw_exception("API_AttrNoAlarm",o.toString(),
					       "Attribute.check_alarm");
		}
	
//
// Check the min alarm if defined
//

		int i;
		if (check_min_alarm == true)
		{
			switch (data_type)
			{
			case Tango_DEV_SHORT:
				for (i = 0;i < data_size;i++)
				{
					if (value.sh_seq[i] <= min_alarm.sh)
					{
						quality = AttrQuality.ATTR_ALARM;
						min_alarm_on = true;
						return true;
					}
				}
				break;
			
			case Tango_DEV_LONG:
				for (i = 0;i < data_size;i++)
				{
					if (value.lg_seq[i] <= min_alarm.lg)
					{
						quality = AttrQuality.ATTR_ALARM;
						min_alarm_on = true;
						return true;
					}
				}
				break;
			
			case Tango_DEV_LONG64:
				for (i = 0;i < data_size;i++)
				{
					if (value.lg64_seq[i] <= min_alarm.lg64)
					{
						quality = AttrQuality.ATTR_ALARM;
						min_alarm_on = true;
						return true;
					}
				}
				break;
			
			case Tango_DEV_DOUBLE:
				for (i = 0;i < data_size;i++)
				{
					if (value.db_seq[i] <= min_alarm.db)
					{
						quality = AttrQuality.ATTR_ALARM;
						min_alarm_on = true;
						return true;
					}
				}
				break;
			}
			min_alarm_on = false;
		}
	
//
// Check the max alarm if defined
//

		if (check_max_alarm == true)
		{
			switch (data_type)
			{
			case Tango_DEV_SHORT:
				for (i = 0;i < data_size;i++)
				{
					if (value.sh_seq[i] >= max_alarm.sh)
					{
						quality = AttrQuality.ATTR_ALARM;
						max_alarm_on = true;
						return true;
					}
				}
				break;
			
			case Tango_DEV_LONG:
				for (i = 0;i < data_size;i++)
				{
					if (value.lg_seq[i] >= max_alarm.lg)
					{
						quality = AttrQuality.ATTR_ALARM;
						max_alarm_on = true;
						return true;
					}
				}
				break;
			
			case Tango_DEV_LONG64:
				for (i = 0;i < data_size;i++)
				{
					if (value.lg64_seq[i] >= max_alarm.lg64)
					{
						quality = AttrQuality.ATTR_ALARM;
						max_alarm_on = true;
						return true;
					}
				}
				break;
			
			case Tango_DEV_DOUBLE:
				for (i = 0;i < data_size;i++)
				{
					if (value.db_seq[i] >= max_alarm.db)
					{
						quality = AttrQuality.ATTR_ALARM;
						max_alarm_on = true;
						return true;
					}
				}
				break;
			}
			max_alarm_on = false;
		}
	
		return false;
	}
			
//+-------------------------------------------------------------------------
//
// method : 		AttrProperty.toString
// 
// description : 	Redefinition of the Object.toString method to
//			print attribute name and value in one go
//
//--------------------------------------------------------------------------
/**
 * Returns a string representation of this attribute.
 *
 * This method returns a string with all attribute properties name and value\\
 *
 * @return A string representation of the attribute.
 */
	public String toString()
	{
		// Get attribute properties
		AttributeConfig_2 conf = get_properties_2();

//
// Print all these properties
//

		StringBuffer o = new StringBuffer();	
		o.append("Attribute name = ");
		o.append(conf.name);
		o.append("\nAttribute data_type = ");
		switch (conf.data_type)
		{
		case Tango_DEV_BOOLEAN :
			o.append("Tango_DevBoolean");
			break;
		
		case Tango_DEV_SHORT :
			o.append("Tango_DevShort");
			break;
		
		case Tango_DEV_LONG :
			o.append("Tango_DevLong");
			break;
		
		case Tango_DEV_LONG64 :
			o.append("Tango_DevLong64");
			break;
		
		case Tango_DEV_DOUBLE :
			o.append("Tango_DevDouble");
			break;
		
		case Tango_DEV_STRING :
			o.append("Tango_DevString");
			break;
		}
	
		o.append("\nAttribute data_format = ");
		switch (conf.data_format.value())
		{
		case AttrDataFormat._SCALAR :
			o.append("scalar");
			break;
		
		case AttrDataFormat._SPECTRUM :
			o.append("spectrum, max_x = ");
			o.append(conf.max_dim_x);
			break;
		
		case AttrDataFormat._IMAGE :
			o.append("image, max_dim_x = ");
			o.append(conf.max_dim_x);
			o.append(", max_dim_y = ");
			o.append(conf.max_dim_y);
			break;
		}

		if (conf.writable == AttrWriteType.READ)
			o.append("\nAttribute is not writable");
		else
			o.append("\nAttribute is writable");
		o.append("\nAttribute label = ");
		o.append(conf.label);
		o.append("\nAttribute description = ");
		o.append(conf.description);
		o.append("\nAttribute unit = ");
		o.append(conf.unit);
		o.append(", standard unit = ");
		o.append(conf.standard_unit);
		o.append(", display unit = ");
		o.append(conf.display_unit);
		o.append("\nAttribute format = ");
		o.append(conf.format);
		o.append("\nAttribute min alarm = ");
		o.append(conf.min_alarm);
		o.append("\nAttribute max alarm = ");
		o.append(conf.max_alarm);
		o.append("\nAttribute min value = ");
		o.append(conf.min_value);
		o.append("\nAttribute max value = ");
		o.append(conf.max_value);	
		o.append("\nAttribute poll_period = ");
		o.append(poll_period);	
		o.append("\nAttribute writable_attr_name = ");
		o.append(conf.writable_attr_name);
		if (conf.level == DispLevel.OPERATOR)
			o.append("\nDisplay Level = OPERATOR");
		else
			o.append("\nDisplay Level = EXPERT");
	
		o.append("\n");
		return o.toString();
	}

	void set_value() throws DevFailed
	{
	}
					
//+-------------------------------------------------------------------------
//
// Methods to retrieve/set some data members from outside the class and all
// its inherited classes
//
//--------------------------------------------------------------------------

/**
 * Get the attribute write type
 *
 * @return The attribute write type
 */
 	public AttrWriteType get_writable()
	{
		return writable;
	}

/**
 * Get attribute name
 *
 * @return The attribute name
 */	
	public String get_name()
	{
		return name;
	}

/**
 * Get name of the associated writable attribute
 *
 * @return The associated writable attribute name
 */	
	public String get_assoc_name()
	{
		return writable_attr_name;
	}

/**
 * Get attribute data type
 *
 * @return The attribute data type
 */	
	public int get_data_type()
	{
		return data_type;
	}

/**
 * Check if the attribute is in minimum alarm condition .
 *
 * @return A boolean set to true if the attribute is in alarm condition (read
 * value below the min. alarm).
 */	
	public boolean is_min_alarm()
	{
		return min_alarm_on;
	}

/**
 * Check if the attribute is in maximum alarm condition .
 *
 * @return A boolean set to true if the attribute is in alarm condition (read
 * value above the max. alarm).
 */	
	public boolean is_max_alarm()
	{
		return max_alarm_on;
	}

/**
 * Get attribute data format
 *
 * @return The attribute data format
 */	
	public AttrDataFormat get_data_format()
	{
		return data_format;
	}

/**
 * Get index of the associated writable attribute
 *
 * @return The index in the main attribute vector of the associated writable
 * attribute
 */	
	public int get_assoc_ind()
	{
		return assoc_ind;
	}

/**
 * Set index of the associated writable attribute
 *
 * @param val The new index in the main attribute vector of the associated writable
 * attribute
 */	
	public void set_assoc_ind(int val)
	{
		assoc_ind = val;
	}

/**
 * Get attribute data format
 *
 * @return The attribute data format
 */
	public int get_data_size()
	{
		return data_size;
	}

/**
 * Get attribute data size in x dimension
 *
 * @return The attribute data size in x dimension. Set to 1 for scalar attribute
 */
	int get_x()
	{
		return dim_x;
	}

	
/**
 * Get attribute data size in y dimension
 *
 * @return The attribute data size in y dimension. Set to 0 for scalar and
 * spectrum attribute
 */
	int get_y()
	{
		return dim_y;
	}
	
/**
 * Get attribute data quality
 *
 * @return The attribute data quality
 */
	public AttrQuality get_quality()
	{
		return quality;
	}

/**
 * Set attribute data quality
 *
 * @param qua	The new attribute data quality
 */	
	public void set_quality(AttrQuality qua)
	{
		quality = qua;
	}

/**
 * Get attribute date
 *
 * @return The attribute date
 */	
	public TimeVal get_when()
	{
		return when;
	}
	public TimeVal get_date()
	{
		return when;
	}
	public void set_date(TimeVal date)
	{
		when = date;
	}
	
	void wanted_date(boolean val)
	{
		date = val;
	}

	String get_upper_name()
	{
		return name_upper;
	}
	
	int get_name_size()
	{
		return name_size;
	}
	
	boolean get_value_flag()
	{
		return value_flag;
    }

    void set_value_flag(boolean val) {
        value_flag = val;
    }

	DevEncoded[] get_enc_value()
	{
		return value.enc_value;
	}
	DevState[] get_state_value()
	{
		return value.state_seq;
	}
	boolean[] get_boolean_value()
	{
		return value.bool_seq;
	}
	short[] get_short_value()
	{
		return value.sh_seq;
	}
	
	int[] get_long_value()
	{
		return value.lg_seq;
	}
	long[] get_long64_value()
	{
		return value.lg64_seq;
	}
	float[] get_float_value()
	{
		return value.fl_seq;
	}
	double[] get_double_value()
	{
		return value.db_seq;
	}
	
	String[] get_string_value()
	{
		return value.str_seq;
	}
	
	
	/**
	 *	Return the polling period
	 */
	int get_polling_period()
	{
		return poll_period;
	}
	/**
	 *	Set the polling period
	 */
	void set_polling_period(int p)
	{
		poll_period = p;
	}

	//===============================================================
	//===============================================================
	boolean isActive()
	{
		return active;
    }

    //===============================================================
    //===============================================================
    void setActive(boolean b) {
        active = b;
    }

	//===============================================================
	//===============================================================
	synchronized void waitEndOfRead()
	{
		long	t0 = System.currentTimeMillis();
		long	dt = 0;
		while (isActive() && dt<3000)
		{
			long	t1 = System.currentTimeMillis();
			dt = t1-t0;
			//System.out.println(dt + ": "+name + " is already active !!!");
			try { Thread.sleep(10); } catch(InterruptedException e){}
		}

		setActive(true);
		set_value_flag(false);
	}
}
