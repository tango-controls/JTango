//+============================================================================
//
// file :               WAttribute.java
//
// description :        Java source code for the WAttribute class.
//			This class is used to manage writable attribute.
//			This class inherits from the Attribute class
//
// project :            TANGO
//
// author(s) :          E.Taurel
//
// $Revision$
//
// $Log$
// Revision 1.1  2007/08/23 08:33:25  ounsy
// updated change from api/java
//
// Revision 3.8  2007/05/29 08:07:43  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.7  2006/09/18 11:10:49  pascal_verdier
// Write boolean attribute bug fixed.
//
// Revision 3.6  2005/12/02 09:55:46  pascal_verdier
// java import have been optimized.
//
// Revision 3.5  2004/03/12 14:07:57  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.0  2003/01/09 16:02:58  taurel
// - Update release number before using SourceForge
//
// Revision 1.1.1.1  2003/01/09 15:54:40  taurel
// Imported sources into CVS before using SourceForge
//
// Revision 1.6  2001/10/10 08:11:26  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:39  taurel
// Many changes due to new release
//
// Revision 1.2  2001/05/04 12:03:22  taurel
// Fix bug in the Util.get_device_by_name() method
//
// Revision 1.1.1.1  2001/04/04 08:23:54  taurel
// Imported sources
//
// Revision 1.3  2000/04/13 08:23:02  taurel
// Added attribute support
//
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;

import fr.esrf.Tango.*;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;

import java.util.Vector;

/**
 * This class represents a writable attribute. It inherits from the Attribute
 * class and only add what is specific to writable attribute.
 *
 * @author	$Author$
 * @version	$Revision$
 */
  
public class WAttribute extends Attribute implements TangoConst
{
	private	DevState		state_val;
	private DevState		old_state_val;
	
	private	boolean			bool_val;
	private boolean			old_bool_val;
	
	private	short			short_val;
	private short			old_short_val;
	
	private	int				long_val;
	private	int				old_long_val;
	
	private	long			long64_val;
	private	long			old_long64_val;
	
	private	double			double_val;
	private double			old_double_val;
	
	private	String			str_val;
	private	String			old_str_val;
	
	
//+-------------------------------------------------------------------------
//
// method : 		WAttribute::WAttribute
// 
// description : 	constructor for the WAttribute class from the 
//			attribute property vector, its type and the device
//			name
//
// argument : in : 	- prop_list : The attribute property list
//			- type : The attrubute data type
//			- dev_name : The device name
//
//--------------------------------------------------------------------------

/**
 * Create a new WAttribute object.
 *
 * @param prop_list The attribute properties list. Each property is an object
 * of the AttrProperty class
 * @param tmp_attr The temporary attribute object
 * @param dev_name The device name
 * @exception DevFailed If the creation of the Attribute object failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public WAttribute(Vector prop_list,Attr tmp_attr,String dev_name) throws DevFailed
	{
		super(prop_list,tmp_attr,dev_name);
		
		bool_val = old_bool_val = false;
		short_val = old_short_val = 0;
		long_val = old_long_val = 0;
		double_val = old_double_val = 0.0;
		str_val = "Not initialised";
	}

//+-------------------------------------------------------------------------
//
// method : 		set_value
// 
// description : 	This method is used when a Writable attribute is
//			set to set the value in the Attribute class. This
//			is necessary for the read_attribute CORBA operation
//			which takes its data from this internal Attribute
//			class data.
//			It is used in the read_attributes code in the
//			device class
//
//--------------------------------------------------------------------------

	void set_value() throws DevFailed
	{
		switch(data_type)
		{
		case Tango_DEV_BOOLEAN:
			super.set_value(bool_val);
			break;
		
		case Tango_DEV_SHORT:
			super.set_value(short_val);
			break;
		
		case Tango_DEV_LONG:
			super.set_value(long_val);
			break;
				
		case Tango_DEV_LONG64:
			super.set_value(long64_val);
			break;
				
		case Tango_DEV_DOUBLE:
			super.set_value(double_val);
			break;
		
		case Tango_DEV_STRING:
			super.set_value(str_val);
			break;
		}
	}
	
//+-------------------------------------------------------------------------
//
// method : 		rollback
// 
// description : 	Reset the internal data to its value before the
//			set_write_value method was applied (Useful in case of
//			error in the set_write_value method)
//
//--------------------------------------------------------------------------

	void rollback()
	{
		switch (data_type)
		{
		case Tango_DEV_BOOLEAN :
			bool_val = old_bool_val;
			break;
		
		case Tango_DEV_SHORT :
			short_val = old_short_val;
			break;
		
		case Tango_DEV_LONG :
			long_val = old_long_val;
			break;
		
		case Tango_DEV_LONG64 :
			long64_val = old_long64_val;
			break;
		
		case Tango_DEV_DOUBLE :
			double_val = old_double_val;
			break;
		
		case Tango_DEV_STRING :
			str_val = old_str_val;
			break;
		}
	}

//+-------------------------------------------------------------------------
//
// method : 		set_write_value
// 
// description : 	Set the value inside the Wattribute object from
//			the Any object received from the network. Some special
//			treatement for string due to memory allocation.
//
// in :			any : Reference to the CORBA Any object
//
//--------------------------------------------------------------------------

	void set_write_value(Any any) throws DevFailed
	{
		switch (data_type)
		{
		case Tango_DEV_STATE :
			// Check data type inside the any
			DevState[] st = null;
			try
			{
				st = DevVarStateArrayHelper.extract(any);
			}
			catch (BAD_OPERATION ex)
			{
				Except.throw_exception("API_IncompatibleAttrDataType",
						       "Incompatible attribute type, expected type is : Tango_DevBoolean",
						       "WAttribute.set_write_value()");
			}
		
			//	No min max value check for boolean !
			old_state_val = state_val;		
			state_val = st[0];
			break;
		
		case Tango_DEV_BOOLEAN :
			// Check data type inside the any
			boolean[] bl_ptr = null;
			try
			{
				bl_ptr = DevVarBooleanArrayHelper.extract(any);
			}
			catch (BAD_OPERATION ex)
			{
				Except.throw_exception("API_IncompatibleAttrDataType",
						       "Incompatible attribute type, expected type is : Tango_DevBoolean",
						       "WAttribute.set_write_value()");
			}
		
			//	No min max value check for boolean !
			old_bool_val = bool_val;		
			bool_val = bl_ptr[0];
			break;
		
		case Tango_DEV_SHORT :
			// Check data type inside the any
			short[] sh_ptr = null;
			try
			{
				sh_ptr = DevVarShortArrayHelper.extract(any);
			}
			catch (BAD_OPERATION ex)
			{
				Except.throw_exception("API_IncompatibleAttrDataType",
						       "Incompatible attribute type, expected type is : Tango_DevShort",
						       "WAttribute.set_write_value()");
			}
		
			// Check the incoming value
			if (check_min_value == true)
			{
				if (sh_ptr[0] < min_value.sh)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is below or equal the minimum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",
							       o.toString(),
							       "WAttribute.set_write_value()");
				}
			}
			if (check_max_value == true)
			{
				if (sh_ptr[0] > max_value.sh)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is above or equal the maximum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",
							       o.toString(),
							       "WAttribute.set_write_value()");
				}
			}

			old_short_val = short_val;		
			short_val = sh_ptr[0];
			break;
		

		case Tango_DEV_USHORT :
			// Check data type inside the any
			short[] ush_ptr = null;
			try
			{
				ush_ptr = DevVarUShortArrayHelper.extract(any);
			}
			catch (BAD_OPERATION ex)
			{
				Except.throw_exception("API_IncompatibleAttrDataType",
						       "Incompatible attribute type, expected type is : Tango_DevUShort",
						       "WAttribute.set_write_value()");
			}
		
			// Check the incoming value
			int	ush_val =  (0xFFFF & ush_ptr[0]);
			if (check_min_value == true)
			{
				if (ush_val < min_value.sh)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is below or equal the minimum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",
							       o.toString(),
							       "WAttribute.set_write_value()");
				}
			}
			if (check_max_value == true)
			{
				if (ush_val > max_value.sh)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is above or equal the maximum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",
							       o.toString(),
							       "WAttribute.set_write_value()");
				}
			}

			old_long_val = long_val;				
			long_val = ush_val;
			break;
		

		case Tango_DEV_LONG :
			// Check data type inside the any
			int[] lg_ptr = null;
			try
			{
				lg_ptr = DevVarLongArrayHelper.extract(any);
			}
			catch(BAD_OPERATION ex)
			{
				Except.throw_exception("API_IncompatibleAttrDataType",
						       "Incompatible attribute type, expected type is : Tango_DevLong",
						       "WAttribute.set_write_value()");
			}
			// Check the incoming value
			if (check_min_value == true)
			{
				if (lg_ptr[0] < min_value.lg)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is below or equal the minimum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",o.toString(),
							       "WAttribute.set_write_value()");
				}
			}
			if (check_max_value == true)
			{
				if (lg_ptr[0] > max_value.lg)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is above or equal the maximum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",o.toString(),
							       "WAttribute.set_write_value()");
				}
			}

			old_long_val = long_val;				
			long_val = lg_ptr[0];
			break;


		case Tango_DEV_ULONG :
			// Check data type inside the any
			int[] ulg_ptr = null;
			try
			{
				ulg_ptr = DevVarULongArrayHelper.extract(any);
			}
			catch(BAD_OPERATION ex)
			{
				Except.throw_exception("API_IncompatibleAttrDataType",
						       "Incompatible attribute type, expected type is : Tango_DevULong",
						       "WAttribute.set_write_value()");
			}
			// Check the incoming value
			long	mask = (long) 0x7fffffff;
			mask += ((long)1 << 31);
			long	ulg_val =  (mask & (long)ulg_ptr[0]);
			if (check_min_value == true)
			{
				if (ulg_val < min_value.lg)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is below or equal the minimum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",o.toString(),
							       "WAttribute.set_write_value()");
				}
			}
			if (check_max_value == true)
			{
				if (ulg_val > max_value.lg)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is above or equal the maximum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",o.toString(),
							       "WAttribute.set_write_value()");
				}
			}

			old_long64_val = long64_val;				
			long64_val = ulg_val;
			break;


		case Tango_DEV_LONG64 :
			// Check data type inside the any
			long[] lg64_ptr = null;
			try
			{
				lg64_ptr = DevVarLong64ArrayHelper.extract(any);
			}
			catch(BAD_OPERATION ex)
			{
				Except.throw_exception("API_IncompatibleAttrDataType",
						       "Incompatible attribute type, expected type is : Tango_DevLong",
						       "WAttribute.set_write_value()");
			}
			// Check the incoming value
			if (check_min_value == true)
			{
				if (lg64_ptr[0] < min_value.lg)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is below or equal the minimum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",o.toString(),
							       "WAttribute.set_write_value()");
				}
			}
			if (check_max_value == true)
			{
				if (lg64_ptr[0] > max_value.lg)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is above or equal the maximum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",o.toString(),
							       "WAttribute.set_write_value()");
				}
			}

			old_long64_val = long64_val;				
			long64_val = lg64_ptr[0];
			break;


		case Tango_DEV_ULONG64 :
			// Check data type inside the any
			long[] ulg64_ptr = null;
			try
			{
				ulg64_ptr = DevVarLong64ArrayHelper.extract(any);
			}
			catch(BAD_OPERATION ex)
			{
				Except.throw_exception("API_IncompatibleAttrDataType",
						       "Incompatible attribute type, expected type is : Tango_DevLong",
						       "WAttribute.set_write_value()");
			}
			// Check the incoming value
			if (check_min_value == true)
			{
				if (ulg64_ptr[0] < min_value.lg)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is below or equal the minimum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",o.toString(),
							       "WAttribute.set_write_value()");
				}
			}
			if (check_max_value == true)
			{
				if (ulg64_ptr[0] > max_value.lg)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is above or equal the maximum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",o.toString(),
							       "WAttribute.set_write_value()");
				}
			}

			old_long64_val = long64_val;				
			long64_val = ulg64_ptr[0];
			break;

		
		case Tango_DEV_DOUBLE :
			// Check data type inside the any
			double[] db_ptr = null;
			try
			{
				db_ptr = DevVarDoubleArrayHelper.extract(any);
			}
			catch(BAD_OPERATION ex)
			{
				Except.throw_exception("API_IncompatibleAttrDataType",
						       "Incompatible attribute type, expected type is : Tango_DevDouble",
						       "WAttribute.set_write_value()");
			}
		
			// Check the incoming value
			if (check_min_value == true)
			{
				if (db_ptr[0] < min_value.db)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is below or equal the minimum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",o.toString(),
							       "WAttribute.set_write_value()");
				}
			}
			if (check_max_value == true)
			{
				if (db_ptr[0] > max_value.db)
				{
					StringBuffer o = new StringBuffer("Set value for attribute ");;	
					o.append(name);
					o.append(" is above or equal the maximum authorized");
					Except.throw_exception("API_WAttrOutsideLimit",o.toString(),
							       "WAttribute.set_write_value()");
				}
			}		

			old_double_val = double_val;
			double_val = db_ptr[0];
			break;
		

		case Tango_DEV_STRING :
			String[] str_ptr = null;
			try
			{
				str_ptr = DevVarStringArrayHelper.extract(any);
			}
			catch(BAD_OPERATION ex)
			{
				Except.throw_exception("API_IncompatibleAttrDataType",
						       "Incompatible attribute type, expected type is : Tango_DevString",
						       "WAttribute.set_write_value()");
			}

			old_str_val = str_val;
			str_val = str_ptr[0];
			break;
		}
	}


//+-------------------------------------------------------------------------
//
// Methods to retrieve/set some data members from outside the class and all
// its inherited classes
//
//--------------------------------------------------------------------------
//===================================================================
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevState
 *
 * @return The new value sent by the caller
 */
	public DevState getStateWriteValue()
	{
		return state_val;
	}
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevState
 *
 * @return The new value sent by the caller
 * @deprecated
 */
	public DevState get_state_write_value()
	{
		return getStateWriteValue();
	}

//===================================================================
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevBoolean
 *
 * @return The new value sent by the caller
 */
	public boolean getBooleanWriteValue()
	{
		return bool_val;
	}
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevBoolean
 *
 * @return The new value sent by the caller
 * @deprecated
 */
	public boolean get_bool_write_value()
	{
		return getBooleanWriteValue();
	}

//===================================================================
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevShort.
 *
 * @return The new value sent by the caller
 */
	public short getShortWriteValue()
	{
		return short_val;
	}
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevShort.
 *
 * @return The new value sent by the caller
 * @deprecated
 */
	public short get_sh_write_value()
	{
		return getShortWriteValue();
	}
//===================================================================
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevLong.
 *
 * @return The new value sent by the caller
 */
	public int getUShortWriteValue()
	{
		return long_val;
	}

/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevUShort
 *
 * @return The new value sent by the caller
 * @deprecated
 */
 
	public int get_ush_write_value()
	{
		return getUShortWriteValue();
	}

//===================================================================
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevLong.
 *
 * @return The new value sent by the caller
 */
	public int getLongWriteValue()
	{
		return long_val;
	}

/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevLong.
 *
 * @return The new value sent by the caller
 * @deprecated
 */
	public int get_lg_write_value()
	{
		return getLongWriteValue();
	}

//===================================================================
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevULong.
 *
 * @return The new value sent by the caller
 */
 
	public long getULongWriteValue()
	{
		return long64_val;
	}
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevULong.
 *
 * @return The new value sent by the caller
 * @deprecated
 */
	public long get_ulg_write_value()
	{
		return getULongWriteValue();
	}
//===================================================================
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevLong64.
 *
 * @return The new value sent by the caller
 */
 
	public long getLong64WriteValue()
	{
		return long64_val;
	}
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevLong64.
 *
 * @return The new value sent by the caller
 * @deprecated
 */
 
	public long get_lg64_write_value()
	{
		return getLong64WriteValue();
	}

//===================================================================
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevULong64.
 *
 * @return The new value sent by the caller
 */
 
	public long getULong64WriteValue()
	{
		return long64_val;
	}
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevULong64.
 *
 * @return The new value sent by the caller
 * @deprecated
 */
 
	public long get_ulg64_write_value()
	{
		return getULong64WriteValue();
	}

//===================================================================
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevDouble.
 *
 * @return The new value sent by the caller
 */	
	public double getDoubleWriteValue()
	{
		return double_val;
	}

/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevDouble.
 *
 * @return The new value sent by the caller
 * @deprecated
 */	
	public double get_db_write_value()
	{
		return getDoubleWriteValue();
	}

//===================================================================
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevString.
 *
 * @return The new value sent by the caller
 */
	public String getStringWriteValue()
	{
		return str_val;
	}	
/**
 * Retrieve the new value for writable attribute when attribute data type is 
 * Tango_DevString.
 *
 * @return The new value sent by the caller
 * @deprecated
 */
	public String get_str_write_value()
	{
		return str_val;
	}	
//===================================================================
/**
 * Clone this object
 * This is a deep copy because all attributes are primary except
 * DevState.
 * But DevState reference an immutable object so we don't need to copy
 * explicitly
 * 
 * @see java.lang.Object#clone()
 */
	public Object clone() throws CloneNotSupportedException {
		// super.clone takes in charge copy of primary variables
		// Object DevState referenced by state_val is a static object
		WAttribute clone = (WAttribute)super.clone();
		clone.name = this.name;

		return clone;
	}
}
