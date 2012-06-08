//=============================================================================	
//
// file :		Attr.java
//
// description :	Java source file for the Attr class. 
//			This class is created by the device server programmer
//			to define a no dimension attribute. Afterwards, the
//			Tango core software use it to create the real 
//			object representing the attribute (attribute class)
//
// project :		TANGO
//
// $Author: :		E.Taurel
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
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
// $Revision$
//
// $Log$
// Revision 1.4  2009/01/16 12:37:13  pascal_verdier
// IntelliJIdea warnings removed.
//
// Revision 1.3  2008/10/10 11:30:46  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.2  2008/09/12 11:27:51  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.1  2007/08/23 08:33:25  ounsy
// updated change from api/java
//
// Revision 3.10  2007/05/29 08:07:43  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.9  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.8  2005/06/13 09:08:42  pascal_verdier
// Attribute historic buffer can be filled by trigger.
//
// Revision 3.7  2004/06/29 04:05:09  pascal_verdier
// Comments used by javadoc added.
//
// Revision 3.6  2004/05/14 13:47:56  pascal_verdier
// Compatibility with Tango-2.2.0 cpp
// (polling commands and attibites).
//
// Revision 3.5  2004/03/12 14:07:56  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.0  2003/01/09 16:02:57  taurel
// - Update release number before using SourceForge
//
// Revision 1.1.1.1  2003/01/09 15:54:39  taurel
// Imported sources into CVS before using SourceForge
//
// Revision 1.6  2001/10/10 08:11:22  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:35  taurel
// Many changes due to new release
//
// Revision 1.2  2001/05/04 12:03:20  taurel
// Fix bug in the Util.get_device_by_name() method
//
// Revision 1.1.1.1  2001/04/04 08:23:54  taurel
// Imported sources
//
// Revision 1.1.1.1  2001/02/27 08:46:20  taurel
// Imported sources
//
//
// copyleft :		European Synchrotron Radiation Facility
//			BP 220, Grenoble 38043
//			FRANCE
//
//=============================================================================

package fr.esrf.TangoDs;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DispLevel;

import java.util.Vector;

/**
 * User class to create a no dimension attribute object.
 *
 * Information from this class and information fetched out from the Tango
 * database allows the Tango core software to create the Attribute object
 * for the attribute created by the user.
 *
 * @author	$Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision$
//
// $Log$
// Revision 1.4  2009/01/16 12:37:13  pascal_verdier
// IntelliJIdea warnings removed.
//
// Revision 1.3  2008/10/10 11:30:46  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.2  2008/09/12 11:27:51  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.1  2007/08/23 08:33:25  ounsy
// updated change from api/java
//
// Revision 3.10  2007/05/29 08:07:43  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.9  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.8  2005/06/13 09:08:42  pascal_verdier
// Attribute historic buffer can be filled by trigger.
//
// Revision 3.7  2004/06/29 04:05:09  pascal_verdier
// Comments used by javadoc added.
//
// Revision 3.6  2004/05/14 13:47:56  pascal_verdier
// Compatibility with Tango-2.2.0 cpp
// (polling commands and attibites).
//
// Revision 3.5  2004/03/12 14:07:56  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.0  2003/01/09 16:02:57  taurel
// - Update release number before using SourceForge
//
// Revision 1.1.1.1  2003/01/09 15:54:39  taurel
// Imported sources into CVS before using SourceForge
//
// Revision 1.6  2001/10/10 08:11:22  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:35  taurel
// Many changes due to new release
//
// Revision 1.2  2001/05/04 12:03:20  taurel
// Fix bug in the Util.get_device_by_name() method
//
// Revision 1.1.1.1  2001/04/04 08:23:54  taurel
// Imported sources
//
// Revision 1.1.1.1  2001/02/27 08:46:20  taurel
// Imported sources
//
//
// copyleft :		European Synchrotron Radiation Facility
//			BP 220, Grenoble 38043
//			FRANCE
//
//=============================================================================

package fr.esrf.TangoDs;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DispLevel;

import java.util.Vector;

/**
 * User class to create a no dimension attribute object.
 *
 * Information from this class and information fetched out from the Tango
 * database allows the Tango core software to create the Attribute object
 * for the attribute created by the user.
 *
 * @author	$Author$
 * @version	$Revision$
 */
 
public class Attr implements TangoConst
{
	 String 	    	name;
	 AttrDataFormat        	format;
	 AttrWriteType         	writable;
	 int		       	type;
	 String 	       	assoc_name;
	 DispLevel			disp_level = DispLevel.OPERATOR;
	 Vector 	       	class_properties = new Vector();
	 Vector			user_default_properties = new Vector();
	protected	int			poll_period;
		
/**
 * Constructs a newly allocated Attr object.
 * The attribute display type is set to OPERATOR_ATTR.
 *
 * @param 	name	The attribute name
 * @param	data_type	The attribute data type
 * @param	w_type	The attribute type (read, write, read with write ...)
 * @param	assoc	Name of the associated writable attribute. This is used
 * only the read with write attribute
 *
 */
	public Attr(String name,int data_type,AttrWriteType w_type,String assoc) throws DevFailed
	{
		this.name = name;
		type = data_type;
		writable = w_type;
		assoc_name = assoc;
		
		format = AttrDataFormat.SCALAR;
		check_type();
	
		if ((writable.value() == AttrWriteType._WRITE) && 
		    ! assoc_name.equals(Tango_AssocWritNotSpec))
		{
			Util.out3.println("Attr.Attr throwing exception");
		
			StringBuffer o = new StringBuffer("Attribute : ");
			o.append(name);
			o.append(": "); 
			o.append(" Associated attribute is not supported");
			Except.throw_exception("API_AttrWrongDefined",
				      	       o.toString(),"Attr.Attr");
		}
	
		if ((writable.value() == AttrWriteType._READ_WITH_WRITE) &&
		    assoc_name.equals(Tango_AssocWritNotSpec))
		{
			Util.out3.println("Attr.Attr throwing exception");
		
			StringBuffer o = new StringBuffer("Attribute : ");
			o.append(name);
			o.append(": "); 
			o.append(" Associated attribute not defined");
			Except.throw_exception("API_AttrWrongDefined",
				      	       o.toString(),"Attr.Attr");
		}
	
		if (writable.value() == AttrWriteType._READ_WRITE)
			assoc_name = name;
	}

		
/**
 * Constructs a newly allocated Attr object.
 * The attribute display type is set to OPERATOR_ATTR.
 *
 * The type of this attribute is set to READ.
 * @param 	name	The attribute name
 * @param	data_type	The attribute data type
 */
 
	public Attr(String name,int data_type) throws DevFailed
	{
		this.name = name;
		type = data_type;
		writable = AttrWriteType.READ;
		assoc_name = Tango_AssocWritNotSpec;
		
		format = AttrDataFormat.SCALAR;
		check_type();
	}

/**
 * Constructs a newly allocated Attr object.
 * The attribute display type is set to OPERATOR_ATTR.
 *
 * @param 	name	The attribute name
 * @param	data_type	The attribute data type
 * @param	w_type	The attribute type (read, write, ...)
 */
 
	public Attr(String name,int data_type,AttrWriteType w_type) throws DevFailed
	{
		this.name = name;
		type = data_type;
		writable = w_type;
		assoc_name = Tango_AssocWritNotSpec;
		
		format = AttrDataFormat.SCALAR;
		check_type();

		if (writable.value() == AttrWriteType._READ_WITH_WRITE)
		{
			Util.out3.println("Attr.Attr throwing exception");
		
			StringBuffer o = new StringBuffer("Attribute: ");
			o.append(name);
			o.append(": "); 
			o.append(" Associated attribute not defined");
			Except.throw_exception("API_AttrWrongDefined",
				      	       o.toString(),"Attr.Attr");
		}
	
		if (writable.value() == AttrWriteType._READ_WRITE)
			assoc_name = name;
	}

/**
 * Constructs a newly allocated Attr object.
 *
 * @param 	name	The attribute name
 * @param	data_type	The attribute data type
 * @param	w_type	The attribute type (read, write, read with write ...)
 * @param	assoc	Name of the associated writable attribute. This is used
 * only the read with write attribute
 * @param	level	The attribute display type
 *
 */
	public Attr(String name,int data_type,AttrWriteType w_type,
		    String assoc,DispLevel level) throws DevFailed
	{
		this.name = name;
		type = data_type;
		writable = w_type;
		assoc_name = assoc;
		disp_level = level;
		
		format = AttrDataFormat.SCALAR;
		check_type();
	
		if ((writable.value() == AttrWriteType._WRITE) && 
		    ! assoc_name.equals(Tango_AssocWritNotSpec))
		{
			Util.out3.println("Attr.Attr throwing exception");
		
			StringBuffer o = new StringBuffer("Attribute : ");
			o.append(name);
			o.append(": "); 
			o.append(" Associated attribute is not supported");
			Except.throw_exception("API_AttrWrongDefined",
				      	       o.toString(),"Attr.Attr");
		}
	
		if ((writable.value() == AttrWriteType._READ_WITH_WRITE) &&
		    assoc_name.equals(Tango_AssocWritNotSpec))
		{
			Util.out3.println("Attr.Attr throwing exception");
		
			StringBuffer o = new StringBuffer("Attribute : ");
			o.append(name);
			o.append(": "); 
			o.append(" Associated attribute not defined");
			Except.throw_exception("API_AttrWrongDefined",
				      	       o.toString(),"Attr.Attr");
		}
	
		if (writable.value() == AttrWriteType._READ_WRITE)
			assoc_name = name;
	}

		
/**
 * Constructs a newly allocated Attr object.
 *
 * The type of this attribute is set to READ.
 * @param 	name	The attribute name
 * @param	data_type	The attribute data type
 * @param	level	The attribute display type
 */
 
	public Attr(String name,int data_type,DispLevel level) throws DevFailed
	{
		this.name = name;
		type = data_type;
		disp_level = level;
		writable = AttrWriteType.READ;
		assoc_name = Tango_AssocWritNotSpec;
		
		format = AttrDataFormat.SCALAR;
		check_type();
	}

/**
 * Constructs a newly allocated Attr object.
 *
 * @param 	name	The attribute name
 * @param	data_type	The attribute data type
 * @param	w_type	The attribute type (read, write, ...)
 * @param	level	The attribute display type
 */
 
	public Attr(String name,int data_type,AttrWriteType w_type,
		    DispLevel level) throws DevFailed
	{
		this.name = name;
		type = data_type;
		writable = w_type;
		disp_level = level;
		assoc_name = Tango_AssocWritNotSpec;
		
		format = AttrDataFormat.SCALAR;
		check_type();

		if (writable.value() == AttrWriteType._READ_WITH_WRITE)
		{
			Util.out3.println("Attr.Attr throwing exception");
		
			StringBuffer o = new StringBuffer("Attribute: ");
			o.append(name);
			o.append(": "); 
			o.append(" Associated attribute not defined");
			Except.throw_exception("API_AttrWrongDefined",
				      	       o.toString(),"Attr.Attr");
		}
	
		if (writable.value() == AttrWriteType._READ_WRITE)
			assoc_name = name;
	}	
			
//+-------------------------------------------------------------------------
//
// method : 		Attr::check_type 
// 
// description : 	This method checks data type and throws an exception
//			in case of unsupported data type
//
//--------------------------------------------------------------------------

	void check_type() throws DevFailed
	{
		boolean unsuported = true;
		if (type == Tango_DEV_SHORT)
			unsuported = false;
		else if (type == Tango_DEV_USHORT)
			unsuported = false;
		else if (type == Tango_DEV_LONG)
			unsuported = false;
		else if (type == Tango_DEV_LONG64)
			unsuported = false;
		else if (type == Tango_DEV_ULONG)
			unsuported = false;
		else if (type == Tango_DEV_ULONG64)
			unsuported = false;
		else if (type == Tango_DEV_BOOLEAN)
			unsuported = false;
		else if (type == Tango_DEV_FLOAT)
			unsuported = false;
		else if (type == Tango_DEV_DOUBLE)
			unsuported = false;
		else if (type == Tango_DEV_STRING)
			unsuported = false;
		else if (type == Tango_DEV_STATE)
			unsuported = false;
		else if (type == Tango_DEV_ENCODED)
			unsuported = false;

		if (unsuported == true)
		{
			Util.out3.println("Attr.check_type throwing exception");
		
			StringBuffer o = new StringBuffer("Attribute : ");
			o.append(name).append(": Data type is not supported");
			Except.throw_exception("API_AttrWrongDefined",
				    o.toString(),
					"Attr.check_type");
		}
	}

//+-------------------------------------------------------------------------
//
// method : 		set_default_properties
// 
// description : 	This method set the default user properties in the
//			Attr object. At this level, each attribute property
//			is represented by one instance of the Attrproperty
//			class.
//
//--------------------------------------------------------------------------

/**
 * Set the attribute user default properties.
 *
 * @param 	prop_list	The user default attribute properties
 */
 
	public void set_default_properties(UserDefaultAttrProp prop_list)
	{
		if (prop_list.label != null)
			user_default_properties.addElement(new AttrProperty("label",prop_list.label));

		if (prop_list.description != null)
			user_default_properties.addElement(new AttrProperty("description",prop_list.description));

		if (prop_list.unit != null)
			user_default_properties.addElement(new AttrProperty("unit",prop_list.unit));

		if (prop_list.standard_unit != null)
			user_default_properties.addElement(new AttrProperty("standard_unit",prop_list.standard_unit));

		if (prop_list.display_unit != null)
			user_default_properties.addElement(new AttrProperty("display_unit",prop_list.display_unit));
		
		if (prop_list.format != null)
			user_default_properties.addElement(new AttrProperty("format",prop_list.format));

		if (prop_list.min_value != null)
			user_default_properties.addElement(new AttrProperty("min_value",prop_list.min_value));

		if (prop_list.max_value != null)
			user_default_properties.addElement(new AttrProperty("max_value",prop_list.max_value));

		if (prop_list.min_alarm != null)
			user_default_properties.addElement(new AttrProperty("min_alarm",prop_list.min_alarm));

		if (prop_list.max_alarm != null)
			user_default_properties.addElement(new AttrProperty("max_alarm",prop_list.max_alarm));
	}


	String get_name()
	{
		return name;
	}
		
	AttrDataFormat get_format()
	{
		return format;
	}
		
	AttrWriteType get_writable()
	{
		return writable;
	}
	
	int get_type()
	{
		return type;
	}
	
	String get_assoc()
	{
		return assoc_name;
	}
	
	boolean is_assoc()
	{
		return (! assoc_name.equals(Tango_AssocWritNotSpec));
	}
		
	Vector get_class_properties()
	{
		return class_properties;
	}
	
	void set_class_properties(Vector in_prop)
	{
		class_properties = in_prop;
	}

	Vector	get_user_default_properties()
	{
		return user_default_properties;
	}
	
	
	//==============================================
	//==============================================
	public DispLevel get_disp_level()
	{
		return disp_level;
	}
	//==============================================
	//==============================================
	public void set_disp_level(DispLevel level)
	{
		disp_level = level;
	}
	//==============================================
	//==============================================
	public int get_polling_period()
	{
		return poll_period;
	}
	//==============================================
	//==============================================
	public void set_polling_period(int p)
	{
		poll_period = p;
	}
}
