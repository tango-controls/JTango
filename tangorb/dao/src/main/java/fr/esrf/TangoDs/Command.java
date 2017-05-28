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
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;


/**
 * This class is a class representing a command in the TANGO device server
 * pattern. it is an abstract class. It is the root class for all command
 * related classes for command implemented with the inheritance model or
 * with the template command model
 *
 * @author $Author: pascal_verdier $
 * @version $Revision: 25297 $
 */
 
public abstract class Command implements TangoConst
{
/**
 * The command name
 */
	protected String			name;
/**
 * The command input parameter type
 */
	protected int				in_type;
/**
 * The command output parameter type
 */
	protected int				out_type;
/**
 * The command input parameter description
 */
	protected String			in_type_desc;
/**
 * The command output parameter type
 */
	protected String			out_type_desc;
/**
 * The TANGO device class name on which the command is defined (only for
 * command implemented using the template command model)
 */
	protected String			device_class_name;
/**
 * Template command flag
 */
	protected boolean			template_cmd = false;
/**
 *	The command polling period.
 */
	protected int				poll_period = 0;
/**
 *	The command display level.
 */
 	protected DispLevel		disp_level = DispLevel.OPERATOR;

//+-------------------------------------------------------------------------
//
// method : 		Command 
// 
// description : 	constructors for abstract class Command
//
//--------------------------------------------------------------------------

/**
 * Constructs a newly allocated Command object for a command from its
 * name and its input and output parameter types.
 * The input and output parameter description are set to the default String
 * "Uninitialised".
 * The command display type is set to OPERATOR_CMD.
 *
 * @param 	s	The command name
 * @param	in	The command input parameter type
 * @param	out 	The command output parameter type
 *
 */

	public Command(String s, int in,int out)
	{
		name = s;
		in_type = in;
		out_type = out;
		in_type_desc  = "";
		out_type_desc = "";
	}

/**
 * Constructs a newly allocated Command object for a command from its
 * name, its input and output parameter types plus parameters description
 * The command display type is set to OPERATOR_CMD.
 *
 * @param 	s	The command name
 * @param	in	The command input parameter type
 * @param	out 	The command output parameter type
 * @param	in_desc	The input parameter description
 * @param	out_desc The output parameter description
 *
 */
 	
	public Command(String s, int in,int out,String in_desc,String out_desc)
	{
		name = s;
		in_type = in;
		out_type = out;
		in_type_desc = in_desc;
		out_type_desc = out_desc;
	}	

/**
 * Constructs a newly allocated Command object for a command from its
 * name and its input and output parameter types.
 * The input and output parameter description are set to the default String
 * "Uninitialised".
 *
 * @param 	s	The command name
 * @param	in	The command input parameter type
 * @param	out 	The command output parameter type
 * @param	level	The command display type
 *
 */

	public Command(String s, int in,int out,DispLevel level)
	{
		name = s;
		in_type = in;
		out_type = out;
		disp_level = level;
	}

/**
 * Constructs a newly allocated Command object for a command from its
 * name, its input and output parameter types plus parameters description
 *
 * @param 	s	The command name
 * @param	in	The command input parameter type
 * @param	out 	The command output parameter type
 * @param	in_desc	The input parameter description
 * @param	out_desc The output parameter description
 * @param	level	The command display type
 *
 */
 	
	public Command(String s, int in,int out,String in_desc,String out_desc,DispLevel level)
	{
		name = s;
		in_type = in;
		out_type = out;
		in_type_desc = in_desc;
		out_type_desc = out_desc;
		disp_level = level;
	}	
			
//
// One abstract method which should be implemented in each sub-class
//

/**
 * Execute the command.
 *
 * This method is automtically called by the TANGO core classes when the
 * associated command is requested by a client. This method is abstract and must be redefined
 * in each sub-class
 *
 * @param dev The device on which the command must be executed
 * @param data_in The incoming data still packed in a CORBA Any object.
 * @return The CORBA Any object returned to the client.
 * @exception DevFailed If the execution method failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public abstract Any execute(DeviceImpl dev,Any data_in) throws DevFailed;
	
//
// The default is_allowed method
//

/**
 * Check if the command is allowed in the actual device state.
 *
 * This method is automtically called by the TANGO core classes when the
 * associated command is requested by a client to check if the command is allowed
 * in the actual device state. This method is the default is_allowed method which
 * always allows the command to be executed. It is possible to re-define it 
 * if this default behaviour does not fullfill the device needs.
 *
 * @param dev The device on which the command must be executed
 * @param data_in The incoming data still packed in a CORBA Any object. This
 * data is passed to this method in case it is necessary to take the command
 * allowed decision
 * @return A boolean set to true is the command is allowed. Otherwise, the
 * return value is false.
 */
 
	public boolean is_allowed(DeviceImpl dev, Any data_in)
	{
		return true;
	}
		
//
// Miscellaneous obvious methods
//

/**
 * Return the command name.
 *
 * @return The command name
 */	
	public String get_name()
	{
		return name;
	}

/**
 * Return the input parameter type.
 *
 * @return The input parameter type
 */
 	
	public int get_in_type()
	{
		return in_type;
	}

/**
 * Return the output parameter type.
 *
 * @return The output parameter type
 */
 	
	public int get_out_type()
	{
		return out_type;
	}

/**
 * Return the input parameter description.
 *
 * @return The input parameter description
 */
 	
	public String get_in_type_desc()
	{
		return in_type_desc;
	}

    /**
     * Set the input parameter description field.
     *
     * @param type The input parameter description
     */

    public void set_in_type_desc(String type) {
        in_type_desc = type;
    }

/**
 * Return the output parameter description.
 *
 * @return The output parameter description
 */

	public String get_out_type_desc()
	{
		return out_type_desc;
    }

    /**
     * Set the output parameter description field.
     *
     * @param type The output parameter description
     */

    public void set_out_type_desc(String type) {
        out_type_desc = type;
    }

/**
 * Return the device class name on which command must be executed.
 *
 * This method is necessary only for command created using the template
 * command method
 * @return The TANGO device class name
 */

	public String get_device_class_name()
	{
		return device_class_name;
	}

/**
 * Set the TANGO device class name field.
 *
 * This method is necessary only for command created using the template
 * command method
 * @param name The TANGO device class name
 */

public void set_device_class_name(String name)
	{
        device_class_name = name;
    }

    /**
     * Determines if the command is implemented using the template command method.
     *
     * If the Command object is implemented using the template
     * command model, returns true, otherwise returns false
     */

    public boolean is_template() {
        return template_cmd;
    }

/**
 * Analyse the method given at construction time.
 *
 * This method is necessary only for command created using the template
 * command method.
 *
 * @exception DevFailed If one of the method does not fulfill the requirements.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
	
	public void analyse_methods() throws DevFailed
	{
		Util.out4.println("In Command.analyse_methods()");
	}
	
//
// All the insert methods (overloaded for each Tango data type)
//

	@SuppressWarnings({"ErrorNotRethrown"})
	private Any alloc_any() throws DevFailed
	{
		Any out_any = null;
		try
		{	
			out_any = Util.instance().get_orb().create_any();
		}
		catch (OutOfMemoryError ex)
		{
			Util.out3.println("Bad allocation while in alloc_any Command method()");
			Except.throw_exception("API_MemoryAllocation",
					     "Can't allocate memory in server",
					     "Command::alloc_any");
		}
		return out_any;
	}

/**
 * Create an  empty CORBA Any object.
 *
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 		
	public Any insert() throws DevFailed
	{
		return alloc_any();
	}

/**
 * Create a CORBA Any object and insert a boolean data in it.
 *
 * @param data The boolean data to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(boolean data) throws DevFailed
	{
		Any out_any = alloc_any();
		out_any.insert_boolean(data);	
		return out_any;
	}

/**
 * Create a CORBA Any object and insert a short data in it.
 *
 * @param data The short data to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(short data) throws DevFailed
	{
		Any out_any = alloc_any();	
		out_any.insert_short(data);	
		return out_any;
	}
	
/**
 * Create a CORBA Any object and insert a short in it (special case for the
 * DevUShort Tango type)
 *
 * @param data The int array to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert_u(short data) throws DevFailed
	{
		Any out_any = alloc_any();		
		out_any.insert_ushort(data);	
		return out_any;
	}
	
/**
 * Create a CORBA Any object and insert an int data in it.
 *
 * @param data The int data to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public Any insert(int data) throws DevFailed
	{
		Any out_any = alloc_any();		
		out_any.insert_long(data);	
		return out_any;
	}
	
/**
 * Create a CORBA Any object and insert a int in it (special case for the
 * DevULong Tango type)
 *
 * @param data The int array to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert_u(int data) throws DevFailed
	{
		Any out_any = alloc_any();		
		out_any.insert_ulong(data);	
		return out_any;
	}
	
/**
 * Create a CORBA Any object and insert an long data in it.
 *
 * @param data The int data to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public Any insert(long data) throws DevFailed
	{
		Any out_any = alloc_any();		
		out_any.insert_longlong(data);	
		return out_any;
	}
	
/**
 * Create a CORBA Any object and insert an long data in it.
 *
 * @param data The int data to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public Any insert_u(long data) throws DevFailed
	{
		Any out_any = alloc_any();		
		out_any.insert_longlong(data);	
		return out_any;
	}
	
/**
 * Create a CORBA Any object and insert a float data in it.
 *
 * @param data The float data to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(float data) throws DevFailed
	{
		Any out_any = alloc_any();	
		out_any.insert_float(data);	
		return out_any;
	}

/**
 * Create a CORBA Any object and insert a double data in it.
 *
 * @param data The double data to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(double data) throws DevFailed
	{
		Any out_any = alloc_any();	
		out_any.insert_double(data);	
		return out_any;
	}

/**
 * Create a CORBA Any object and insert a String in it.
 *
 * @param data The String to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(String data) throws DevFailed
	{
		Any out_any = alloc_any();	
		out_any.insert_string(data);	
		return out_any;
	}

/**
 * Create a CORBA Any object and insert a byte array in it.
 *
 * @param data The byte array to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(byte[] data) throws DevFailed
	{
		Any out_any = alloc_any();
		DevVarCharArrayHelper.insert(out_any,data);	
		return out_any;
	}

/**
 * Create a CORBA Any object and insert a short array in it.
 *
 * @param data The short array to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(short[] data) throws DevFailed
	{
		Any out_any = alloc_any();
		DevVarShortArrayHelper.insert(out_any,data);	
		return out_any;
	}
	
/**
 * Create a CORBA Any object and insert a int array in it (special case for the
 * DevVarUShortArray Tango type)
 *
 * @param data The int array to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert_u(short[] data) throws DevFailed
	{
		Any out_any = alloc_any();
		DevVarUShortArrayHelper.insert(out_any,data);	
		return out_any;
	}
	
/**
 * Create a CORBA Any object and insert a int array in it.
 *
 * @param data The int array to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(int[] data) throws DevFailed
	{
		Any out_any = alloc_any();
		DevVarLongArrayHelper.insert(out_any,data);	
		return out_any;
	}
	
/**
 * Create a CORBA Any object and insert a int array in it (special case for the
 * DevVarULongArray Tango type)
 *
 * @param data The int array to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert_u(int[] data) throws DevFailed
	{
		Any out_any = alloc_any();
		DevVarULongArrayHelper.insert(out_any,data);	
		return out_any;
	}

/**
 * Create a CORBA Any object and insert a long array in it.
 *
 * @param data The int array to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(long[] data) throws DevFailed
	{
		Any out_any = alloc_any();
		DevVarLong64ArrayHelper.insert(out_any,data);	
		return out_any;
	}
	

/**
 * Create a CORBA Any object and insert a long array in it.
 *
 * @param data The int array to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert_u(long[] data) throws DevFailed
	{
		Any out_any = alloc_any();
		DevVarULong64ArrayHelper.insert(out_any,data);	
		return out_any;
	}
	

/**
 * Create a CORBA Any object and insert a float array in it.
 *
 * @param data The float array to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(float[] data) throws DevFailed
	{
		Any out_any = alloc_any();
		DevVarFloatArrayHelper.insert(out_any,data);	
		return out_any;
	}

/**
 * Create a CORBA Any object and insert a double array in it.
 *
 * @param data The double array to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(double[] data) throws DevFailed
	{
		Any out_any = alloc_any();
		DevVarDoubleArrayHelper.insert(out_any,data);	
		return out_any;
	}

/**
 * Create a CORBA Any object and insert a String array in it.
 *
 * @param data The String array to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(String[] data) throws DevFailed
	{
		Any out_any = alloc_any();
		DevVarStringArrayHelper.insert(out_any,data);	
		return out_any;
	}

/**
 * Create a CORBA Any object and insert a DevVarLongStringArray type in it.
 *
 * @param data The data to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(DevVarLongStringArray data) throws DevFailed
	{
		Any out_any = alloc_any();
		DevVarLongStringArrayHelper.insert(out_any,data);	
		return out_any;
	}


/**
 * Create a CORBA Any object and insert a DevVarDoubleStringArray type in it.
 *
 * @param data The data to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(DevVarDoubleStringArray data) throws DevFailed
	{
		Any out_any = alloc_any();
		DevVarDoubleStringArrayHelper.insert(out_any,data);	
		return out_any;
	}


/**
 * Create a CORBA Any object and insert a device state in it.
 *
 * @param data The device state to be inserted into the Any object
 * @exception DevFailed If the Any object creation failed.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any insert(DevState data) throws DevFailed
	{
		Any out_any = alloc_any();
		DevStateHelper.insert(out_any,data);	
		return out_any;
	}
	
//
// All the extract methods (overloaded for each Tango data type)
//

	private void throw_bad_type(String type) throws DevFailed
	{
		StringBuffer mess = new StringBuffer("Incompatible command argument type, expected type is : Tango_");
		mess.append(type);

		Except.throw_exception("API_IncompatibleCmdArgumentType",
				     mess.toString(),
				     "Command.extract()");
	}

/**
 * Extract a boolean data from a CORBA Any object.
 *
 * @param in The CORBA Any object
 * @return The extracted boolean data
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public boolean extract_DevBoolean(Any in) throws DevFailed
	{
		boolean data = false;
		try
		{
			data = in.extract_boolean();
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevBoolean");
		}
		return data;
	}

/**
 * Extract a short data from a CORBA Any object.
 *
 * @param in The CORBA Any object
 * @return The extracted short data
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public short extract_DevShort(Any in) throws DevFailed
	{
		short data = 0;
		try
		{
			data = in.extract_short();
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevShort");
		}
		return data;
	}	

/**
 * Extract a int data from a CORBA Any object.
 *
 * Remenber that the TANGO DevLong type is mapped to the java int type
 * @param in The CORBA Any object
 * @return The extracted int data
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 		
	public int extract_DevLong(Any in) throws DevFailed
	{
		int data = 0;
		try
		{
			data = in.extract_long();
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevLong");
		}
		return data;
	}

/**
 * Extract a int data from a CORBA Any object.
 *
 * Remenber that the TANGO DevLong64 type is mapped to the java int type
 * @param in The CORBA Any object
 * @return The extracted int data
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 		
	public long extract_DevLong64(Any in) throws DevFailed
	{
		long data = 0;
		try
		{
			data = in.extract_longlong();
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevLong64");
		}
		return data;
	}

/**
 * Extract a float data from a CORBA Any object.
 *
 * @param in The CORBA Any object
 * @return The extracted float data
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 		
	public float extract_DevFloat(Any in) throws DevFailed
	{
		float data = 0;
		try
		{
			data = in.extract_float();
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevFloat");
		}
		return data;
	}

/**
 * Extract a double data from a CORBA Any object.
 *
 * @param in The CORBA Any object
 * @return The extracted double data
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public double extract_DevDouble(Any in) throws DevFailed
	{
		double data = 0;
		try
		{
			data = in.extract_double();
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevDouble");
		}
		return data;
	}

/**
 * Extract a String from a CORBA Any object.
 *
 * @param in The CORBA Any object
 * @return The extracted String
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public String extract_DevString(Any in) throws DevFailed
	{
		String data = null;
		try
		{
			//data = in.extract_string();
			data = DevStringHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevString");
		}
		return data;
	}

/**
 * Extract a DevUShort data from a CORBA Any object.
 *
 * Remenber that the TANGO DevUShort type is mapped to the java int type
 * @param in The CORBA Any object
 * @return The extracted boolean data
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public short extract_DevUShort(Any in) throws DevFailed
	{
		short data = 0;
		try
		{
			data = in.extract_ushort();
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevUShort");
		}
		return data;
	}

/**
 * Extract a DevULong data from a CORBA Any object.
 *
 * Remenber that the TANGO DevULong type is mapped to the java int type
 * @param in The CORBA Any object
 * @return The extracted boolean data
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public int extract_DevULong(Any in) throws DevFailed
	{
		int data = 0;
		try
		{
			data = in.extract_ulong();
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevULong");
		}
		return data;
	}


/**
 * Extract a DevULong data from a CORBA Any object.
 *
 * Remenber that the TANGO DevULong64 type is mapped to the java int type
 * @param in The CORBA Any object
 * @return The extracted boolean data
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public long extract_DevULong64(Any in) throws DevFailed
	{
		long data = 0;
		try
		{
			data = in.extract_ulonglong();
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevULong");
		}
		return data;
	}

/**
 * Extract a byte array from a CORBA Any object.
 *
 * @param in The CORBA Any object
 * @return The extracted byte array
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public byte[] extract_DevVarCharArray(Any in) throws DevFailed
	{
		byte[] data = null;
		try
		{
			data = DevVarCharArrayHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevVarCharArray");
		}
		return data;
	}

/**
 * Extract a short array from a CORBA Any object.
 *
 * @param in The CORBA Any object
 * @return The extracted short array
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public short[] extract_DevVarShortArray(Any in) throws DevFailed
	{
		short[] data = null;
		try
		{
			data = DevVarShortArrayHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevVarShortArray");
		}
		return data;
	}

/**
 * Extract a int array from a CORBA Any object.
 *
 * Remenber that the TANGO DevVarLongArray type is mapped to the java int array
 * type
 * @param in The CORBA Any object
 * @return The extracted int array
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public int[] extract_DevVarLongArray(Any in) throws DevFailed
	{
		int[] data = null;
		try
		{
			data = DevVarLongArrayHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevVarLongArray");
		}
		return data;
	}

/**
 * Extract a int array from a CORBA Any object.
 *
 * Remenber that the TANGO DevVarULongArray type is mapped to the java int array
 * type
 * @param in The CORBA Any object
 * @return The extracted int array
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public int[] extract_DevVarULongArray(Any in) throws DevFailed
	{
		int[] data = null;
		try
		{
			data = DevVarULongArrayHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevVarULongArray");
		}
		return data;
	}

/**
 * Extract a int array from a CORBA Any object.
 *
 * Remenber that the TANGO DevVarLong64Array type is mapped to the java int array
 * type
 * @param in The CORBA Any object
 * @return The extracted int array
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public long[] extract_DevVarLong64Array(Any in) throws DevFailed
	{
		long[] data = null;
		try
		{
			data = DevVarLong64ArrayHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevVarLong64Array");
		}
		return data;
	}

/**
 * Extract a int array from a CORBA Any object.
 *
 * Remenber that the TANGO DevVarULong64Array type is mapped to the java int array
 * type
 * @param in The CORBA Any object
 * @return The extracted int array
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public long[] extract_DevVarULong64Array(Any in) throws DevFailed
	{
		long[] data = null;
		try
		{
			data = DevVarULong64ArrayHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevVarULong64Array");
		}
		return data;
	}

/**
 * Extract a float array from a CORBA Any object.
 *
 * @param in The CORBA Any object
 * @return The extracted float array
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public float[] extract_DevVarFloatArray(Any in) throws DevFailed
	{
		float[] data = null;
		try
		{
			data = DevVarFloatArrayHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevVarFloatArray");
		}
		return data;
	}

/**
 * Extract a double array from a CORBA Any object.
 *
 * @param in The CORBA Any object
 * @return The extracted double array
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public double[] extract_DevVarDoubleArray(Any in) throws DevFailed
	{
		double[] data = null;
		try
		{
			data = DevVarDoubleArrayHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevVarDoubleArray");
		}
		return data;
	}

/**
 * Extract a DevVarUShortArray type from a CORBA Any object.
 *
 * Remenber that the TANGO DevVarUShortArray type is mapped to the java short array
 * type
 * @param in The CORBA Any object
 * @return The extracted short array
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public short[] extract_DevVarUShortArray(Any in) throws DevFailed
	{
		short[] data = null;
		try
		{
			data = DevVarUShortArrayHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevVarUShortArray");
		}
		return data;
	}

/**
 * Extract a DevVarStringArray type from a CORBA Any object.
 *
 * @param in The CORBA Any object
 * @return The extracted String array
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public String[] extract_DevVarStringArray(Any in) throws DevFailed
	{
		String[] data = null;
		try
		{
			data = DevVarStringArrayHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevVarStringArray");
		}
		return data;
	}

/**
 * Extract a DevVarLongStringArray type from a CORBA Any object.
 *
 * @param in The CORBA Any object
 * @return The extracted DevVarLongStringArray object
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public DevVarLongStringArray extract_DevVarLongStringArray(Any in) throws DevFailed
	{
		DevVarLongStringArray data = null;
		try
		{
			data = DevVarLongStringArrayHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevVarLongStringArray");
		}
		return data;
	}

/**
 * Extract a DevVarDoubleStringArray type from a CORBA Any object.
 *
 * @param in The CORBA Any object
 * @return The extracted DevVarDoubleStringArray object
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public DevVarDoubleStringArray extract_DevVarDoubleStringArray(Any in) throws DevFailed
	{
		DevVarDoubleStringArray data = null;
		try
		{
			data = DevVarDoubleStringArrayHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevVarDoubleStringArray");
		}
		return data;
	}

/**
 * Extract a DevState type from a CORBA Any object.
 *
 * @param in The CORBA Any object
 * @return The extracted DevState object
 * @exception DevFailed If the Any object does not contains a data of the
 * waited type.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 	
	public DevState extract_DevState(Any in) throws DevFailed
	{
		DevState data = null;
		try
		{
			data = DevStateHelper.extract(in);
		}
		catch (BAD_OPERATION ex)
		{
			throw_bad_type("DevState");
		}
		return data;
	}

//=============================================================================			
//=============================================================================			
	public int get_tag()
	{
		if (disp_level==DispLevel.OPERATOR)
			return Tango_OPERATOR_CMD;
		else
			return Tango_EXPERT_CMD;
	}
//=============================================================================			
//=============================================================================			
	public DispLevel get_disp_level()
	{
		return disp_level;
	}
//=============================================================================			
//=============================================================================			
	public void set_disp_level(DispLevel level )
	{
		disp_level = level;
	}
//=============================================================================			
//=============================================================================			
	public int get_polling_period()
	{
		return poll_period;
	}
//=============================================================================			
//=============================================================================			
	public void set_polling_period(int p)
	{	
		poll_period = p;
	}
}
