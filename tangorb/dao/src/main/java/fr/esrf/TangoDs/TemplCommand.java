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

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DispLevel;
import org.omg.CORBA.Any;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * This class is a class representing a command in the template command model
 * without input or output parameter
 *
 * @author $Author: pascal_verdier $
 * @version $Revision: 25297 $
 */
 
public class TemplCommand extends Command implements TangoConst
{
/**
 * The execute method object reference
 */
	protected Method		exe_method;
/**
 * The command allowed method object reference
 */
	protected Method		state_method;

/**
 * The execute method name
 */	
	protected String		exe_method_name;
/**
 * The command allowed method name
 */
	protected String		state_method_name;

//+-------------------------------------------------------------------------
//
// method : 		TemplCommand 
// 
// description : 	constructors for class TemplCommand
//
//--------------------------------------------------------------------------

/**
 * Constructs a newly allocated TemplCommand object for a command with a
 * name, an execution method and a command allowed method.
 * This constructor set the command input and output type to Tango_DEV_VOID
 * The input and output parameter description are set to the default String
 * "Uninitialised".
 * The command display type is set to OPERATOR_CMD.
 *
 * @param 	name	The command name
 * @param	exe_method	The command execution method name
 * @param	state_method 	The command allowed method name
 *
 */			
	public TemplCommand(String name,String exe_method,String state_method)
	{
		super(name,Tango_DEV_VOID,Tango_DEV_VOID);
		
		exe_method_name = exe_method;
		state_method_name = state_method;
		template_cmd = true;		
	}

/**
 * Constructs a newly allocated TemplCommand object for a command with a
 * name and an execution method.
 * This constructor set the command input and output type to Tango_DEV_VOID.
 * The input and output parameter description are set to the default String
 * "Uninitialised".
 * The command display type is set to OPERATOR_CMD.
 *
 * @param 	name	The command name
 * @param	exe_method	The command execution method name
 *
 */
	public TemplCommand(String name,String exe_method)
	{
		super(name,Tango_DEV_VOID,Tango_DEV_VOID);

		exe_method_name = exe_method;
		state_method_name = null;				
		template_cmd = true;		
	}

/**
 * Constructs a newly allocated TemplCommand object for a command with a
 * name, an execution method, a command allowed method and a description for the
 * input and output command parameters.
 * This constructor set the command input and output type to Tango_DEV_VOID
 * The command display type is set to OPERATOR_CMD.
 *
 * @param 	name	The command name
 * @param	exe_method	The command execution method name
 * @param	state_method 	The command allowed method name
 * @param	in_desc	The command input parameter description
 * @param	out_desc	The command output parameter description
 *
 */			
	public TemplCommand(String name,String exe_method,String state_method,
			    String in_desc,String out_desc)
	{
		super(name,Tango_DEV_VOID,Tango_DEV_VOID,in_desc,out_desc);
				
		exe_method_name = exe_method;
		state_method_name = state_method;		
		template_cmd = true;		
	}

/**
 * Constructs a newly allocated TemplCommand object for a command with a
 * name, an execution method and a description for the
 * input and output command parameters
 * The command display type is set to OPERATOR_CMD.
 *
 * @param 	name	The command name
 * @param	exe_method	The command execution method name
 * @param	in_desc	The command input parameter description
 * @param	out_desc	The command output parameter description
 *
 */
 
	public TemplCommand(String name,String exe_method,
			    String in_desc,String out_desc)
	{
		super(name,Tango_DEV_VOID,Tango_DEV_VOID,in_desc,out_desc);
		
		exe_method_name = exe_method;
		state_method_name = null;				
		template_cmd = true;		
	}

/**
 * Constructs a newly allocated TemplCommand object for a command with a
 * name only.
 * This constructor is used only by sub-classes and is not intended to be
 * used to construct a "real" TemplCommand object
 * This constructor set the command input and output type to Tango_DEV_VOID.
 * The input and output parameter description are set to the default String
 * "Uninitialised".
 * The command display type is set to OPERATOR_CMD.
 *
 * @param 	name	The command name
 *
 */	
	public TemplCommand(String name)
	{
		super(name,Tango_DEV_VOID,Tango_DEV_VOID);
	}

/**
 * Constructs a newly allocated TemplCommand object for a command with a
 * name and input plus output parameter description.
 * This constructor is used only by sub-classes and is not intended to be
 * used to construct a "real" TemplCommand object. The last parameter is only
 * needed to differentiate this constructor from the one with the command name,
 * the execution method and the state method which is also an object constructor
 * from three String.
 * This constructor set the command input and output type to Tango_DEV_VOID
 * The command display type is set to OPERATOR_CMD.
 *
 * @param 	name	The command name
 * @param	in_desc	The command input parameter description
 * @param	out_desc	The command output parameter description
 * @param	dummy	Not used parameter
 *
 */	
	public TemplCommand(String name,String in_desc,String out_desc,double dummy)
	{
		super(name,Tango_DEV_VOID,Tango_DEV_VOID,in_desc,out_desc);
	}

/**
 * Constructs a newly allocated TemplCommand object for a command with a
 * name, an execution method and a command allowed method.
 * This constructor set the command input and output type to Tango_DEV_VOID
 * The input and output parameter description are set to the default String
 * "Uninitialised".
 *
 * @param 	name	The command name
 * @param	exe_method	The command execution method name
 * @param	state_method 	The command allowed method name
 * @param	disp	The command display type
 *
 */			
	public TemplCommand(String name,String exe_method,String state_method,DispLevel disp)
	{
		super(name,Tango_DEV_VOID,Tango_DEV_VOID,disp);
		
		exe_method_name = exe_method;
		state_method_name = state_method;
		template_cmd = true;		
	}

/**
 * Constructs a newly allocated TemplCommand object for a command with a
 * name and an execution method.
 * This constructor set the command input and output type to Tango_DEV_VOID.
 * The input and output parameter description are set to the default String
 * "Uninitialised".
 *
 * @param 	name	The command name
 * @param	exe_method	The command execution method name
 * @param	disp	The command display type
 *
 */
	public TemplCommand(String name,String exe_method,DispLevel disp)
	{
		super(name,Tango_DEV_VOID,Tango_DEV_VOID,disp);

		exe_method_name = exe_method;
		state_method_name = null;				
		template_cmd = true;		
	}

/**
 * Constructs a newly allocated TemplCommand object for a command with a
 * name, an execution method, a command allowed method and a description for the
 * input and output command parameters.
 * This constructor set the command input and output type to Tango_DEV_VOID
 *
 * @param 	name	The command name
 * @param	exe_method	The command execution method name
 * @param	state_method 	The command allowed method name
 * @param	in_desc	The command input parameter description
 * @param	out_desc	The command output parameter description
 * @param	disp	The command display type
 *
 */			
	public TemplCommand(String name,String exe_method,String state_method,
			    String in_desc,String out_desc,DispLevel disp)
	{
		super(name,Tango_DEV_VOID,Tango_DEV_VOID,in_desc,out_desc,disp);
				
		exe_method_name = exe_method;
		state_method_name = state_method;		
		template_cmd = true;		
	}

/**
 * Constructs a newly allocated TemplCommand object for a command with a
 * name, an execution method and a description for the
 * input and output command parameters
 *
 * @param 	name	The command name
 * @param	exe_method	The command execution method name
 * @param	in_desc	The command input parameter description
 * @param	out_desc	The command output parameter description
 * @param	disp	The command display type
 *
 */
 
	public TemplCommand(String name,String exe_method,
			    String in_desc,String out_desc,DispLevel disp)
	{
		super(name,Tango_DEV_VOID,Tango_DEV_VOID,in_desc,out_desc,disp);
		
		exe_method_name = exe_method;
		state_method_name = null;				
		template_cmd = true;		
	}

/**
 * Constructs a newly allocated TemplCommand object for a command with a
 * name only.
 * This constructor is used only by sub-classes and is not intended to be
 * used to construct a "real" TemplCommand object
 * This constructor set the command input and output type to Tango_DEV_VOID.
 * The input and output parameter description are set to the default String
 * "Uninitialised".
 *
 * @param 	name	The command name
 * @param	disp	The command display type
 *
 */	
	public TemplCommand(String name,DispLevel disp)
	{
		super(name,Tango_DEV_VOID,Tango_DEV_VOID,disp);
	}

/**
 * Constructs a newly allocated TemplCommand object for a command with a
 * name and input plus output parameter description.
 * This constructor is used only by sub-classes and is not intended to be
 * used to construct a "real" TemplCommand object. The last parameter is only
 * needed to differentiate this constructor from the one with the command name,
 * the execution method and the state method which is also an object constructor
 * from three String.
 * This constructor set the command input and output type to Tango_DEV_VOID
 *
 * @param 	name	The command name
 * @param	in_desc	The command input parameter description
 * @param	out_desc	The command output parameter description
 * @param	dummy	Not used parameter
 * @param	disp	The command display type
 *
 */	
	public TemplCommand(String name,String in_desc,String out_desc,DispLevel disp,double dummy)
	{
		super(name,Tango_DEV_VOID,Tango_DEV_VOID,in_desc,out_desc,disp);
	}
	
//+----------------------------------------------------------------------------
//
// method : 		analyse_methods
// 
// description : 	Analyse the method given at object creation time if
//			they fullfill the needs for command execution method.
//			It is not possible to do this in the constructor
//			because the class name is not known at object 
//			creation name
//
//-----------------------------------------------------------------------------

/**
 * Analyse the method given at construction time.
 *
 * This method check if the method(s) given at construction time fulfill the
 * required specification. It always analyse the execution method and eventually 
 * the command allowed method.
 *
 * @exception DevFailed If one of the method does not fulfill the requirements.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public void analyse_methods() throws DevFailed
	{
	
//
// Analyse the execution method given by the user
//

		this.exe_method = analyse_method_exe(device_class_name,exe_method_name);		

//
// Analyse the state method if one is given by the user
//

		if (state_method_name != null)
			this.state_method = analyse_method_state(device_class_name,state_method_name);

	}
	
//+----------------------------------------------------------------------------
//
// method : 		analyse_method_exe
// 
// description : 	Try to find the method to be executed when the command
//			arrived (in the device class) and check it input and
//			output parameter
//
// in :			String cl_name : The device class name
//			String exe_method : The method name
//
//-----------------------------------------------------------------------------

 		
	private Method analyse_method_exe(String cl_name,String exe_method) throws DevFailed
	{
		Method meth = null;
	
		try
		{
		
//
// Get the class object for the device class
//

			StringBuffer str = new StringBuffer(cl_name);
			str.append(".");
			str.append(cl_name);
					
			Class cl = Class.forName(str.toString());

//
// Get the device object method list
//
			
			Method[] meth_list = cl.getDeclaredMethods();
			if (meth_list.length == 0)
			{
				StringBuffer mess = new StringBuffer("Command ");
				mess.append(name);
				mess.append(": Can't find method ");
				mess.append(exe_method);

				Except.throw_exception("API_MethodNotFound",
						     mess.toString(),
				  	  	     "TemplCommand.analyse_method_exe()");
			}

//
// Find the execute method in method list
//

			meth = find_method(meth_list,exe_method);

//
// Check if it is public
//

			if (Modifier.isPublic(meth.getModifiers()) != true)
			{
				StringBuffer mess = new StringBuffer("Command ");
				mess.append(name);
				mess.append(": The method ");
				mess.append(exe_method);
				mess.append(" is not public");

				Except.throw_exception("API_MethodArgument",
						     mess.toString(),
							"TemplCommand.analyse_method_exe()");
			}
			
//
// Check its argument
//
					
			Class[] param_type = meth.getParameterTypes();
			if (param_type.length != 0)
			{
				StringBuffer mess = new StringBuffer("Command ");
				mess.append(name);
				mess.append(": Argument(s) defined for method ");
				mess.append(exe_method);

				Except.throw_exception("API_MethodArgument",
						     mess.toString(),
				  	  	     "TemplCommand.analyse_method_exe()");
			}
			
//
// Check method return type
//

			Class ret_type = meth.getReturnType();
			if (ret_type.equals(Void.TYPE) != true)
			{
				StringBuffer mess = new StringBuffer("Command ");
				mess.append(name);
				mess.append(": Return type defined for method ");
				mess.append(exe_method);

				Except.throw_exception("API_MethodArgument",
						     mess.toString(),
				  	  	     "TemplCommand.analyse_method_exe()");
			}
											
		}
		catch (ClassNotFoundException ex)
		{
			StringBuffer mess = new StringBuffer("Command ");
			mess.append(name);
			mess.append(": Can't find class ");
			mess.append(cl_name);

			Except.throw_exception("API_ClassNotFound",
					     mess.toString(),
				  	     "TemplCommand.analyse_method_exe()");
		}
		catch (SecurityException ex)
		{
			StringBuffer mess = new StringBuffer("Command ");
			mess.append(name);
			mess.append(": Security violation when trying to retrieve method list for class ");
			mess.append(cl_name);
			
			Except.throw_exception("API_JavaRuntimeSecurityException",
					     mess.toString(),
				  	     "TemplCommand.analyse_method_exe()");
		}
		
//
// Return the Method reference
//

	return meth;
	
	}

//+----------------------------------------------------------------------------
//
// method : 		analyse_method_state
// 
// description : 	Try to find the state methods to be 
//			executed when the command
//			arrived (in the device class).Also check its input and
//			output parameters
//
// in :			String cl_name : The device class name
//			String state_method : The state method name
//
//-----------------------------------------------------------------------------

/**
 * Analyse if a command allowed method fullfill TANGO requirement.
 *
 * A command allowed method must be public, it must have only one argument
 * of type org.ong.CORBA.Any and return a boolean
 *
 * @param cl_name The class name where the method is defined
 * @param state_method The command allowed method name
 * @return A Method object
 * @exception DevFailed If the method is not found or if the method does not
 * fullfill the requirements described above
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	protected Method analyse_method_state(String cl_name,String state_method) throws DevFailed
	{
		Method s_meth = null;
	
		try
		{
		
//
// Get the class object for the device class
//

			StringBuffer str = new StringBuffer(cl_name);
			str.append(".");
			str.append(cl_name);
					
			Class cl = Class.forName(str.toString());

//
// Get the device object method list
//
			
			Method[] meth_list = cl.getDeclaredMethods();
			if (meth_list.length == 0)
			{
				StringBuffer mess = new StringBuffer("Command ");
				mess.append(name);
				mess.append(": Can't find method ");
				mess.append(state_method);

				Except.throw_exception("API_MethodNotFound",
						     mess.toString(),
				  	  	     "TemplCommand.analyse_method_state()");
			}

//
// Find the state method in method list
//

			s_meth = find_method(meth_list,state_method);

//
// Check if it is public
//

			if (Modifier.isPublic(s_meth.getModifiers()) != true)
			{
				StringBuffer mess = new StringBuffer("Command ");
				mess.append(name);
				mess.append(": The method ");
				mess.append(state_method);
				mess.append(" is not public");

				Except.throw_exception("API_MethodArgument",
						     mess.toString(),
				  	  	     "TemplCommand.analyse_method_state()");
			}
			
//
// Check its argument
//
					
			Class[] s_param_type = s_meth.getParameterTypes();
			if (s_param_type.length != 1)
			{
				StringBuffer mess = new StringBuffer("Command ");
				mess.append(name);
				mess.append(": Wrong number of argument defined for method ");
				mess.append(state_method);

				Except.throw_exception("API_MethodArgument",
						     mess.toString(),
				  	  	     "TemplCommand.analyse_method_state()");
			}
			Class any_class = Class.forName("org.omg.CORBA.Any");
			if (s_param_type[0].equals(any_class) != true)
			{
				StringBuffer mess = new StringBuffer("Command ");
				mess.append(name);
				mess.append(": Incorrect argument type for method ");
				mess.append(state_method);

				Except.throw_exception("API_MethodArgument",
						     mess.toString(),
				  	  	     "TemplCommand.analyse_method_state()");
			}
						
//
// Check method return type
//

			Class s_ret_type = s_meth.getReturnType();
			if (s_ret_type.equals(Boolean.TYPE) != true)
			{
				StringBuffer mess = new StringBuffer("Command ");
				mess.append(name);
				mess.append(": Bad return type for method ");
				mess.append(state_method);
				mess.append(". Should be boolean");

				Except.throw_exception("API_MethodArgument",
						     mess.toString(),
				  	  	     "TemplCommand.analyse_method_state()");
			}
														
		}
		catch (ClassNotFoundException ex)
		{
			StringBuffer mess = new StringBuffer("Command ");
			mess.append(name);
			mess.append(": Can't find class ");
			mess.append(cl_name);

			Except.throw_exception("API_ClassNotFound",
					     mess.toString(),
				  	     "TemplCommand.analyse_method_state()");
		}
		catch (SecurityException ex)
		{
			StringBuffer mess = new StringBuffer("Command ");
			mess.append(name);
			mess.append(": Security violation when trying to retrieve method list for class ");
			mess.append(cl_name);
			
			Except.throw_exception("API_JavaRuntimeSecurityException",
					     mess.toString(),
				  	     "TemplCommand.analyse_method_state()");
		}
		
//
// Return the method reference
//

		return s_meth;
	}
	
//+----------------------------------------------------------------------------
//
// method : 		find_method
// 
// description : 	Try to find a reference too a Method object for a 
//			caller deefined method name
//
// in :			Method[] meth_list : The method object reference array
//			String meth_name : The name of the method to find
//
//-----------------------------------------------------------------------------

/**
 * Retrieve a Method object from a Method list from its name.
 *
 * @param meth_list The Method object list
 * @return The wanted method
 * @exception DevFailed If the method is not known or if two methods are found
 * with the same name
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
	
	protected Method find_method(Method[] meth_list,String meth_name) throws DevFailed
	{
		int i;
		Method meth_found = null;
		
		for (i = 0;i < meth_list.length;i++)
		{
			if (meth_name.equals(meth_list[i].getName()))
			{
				for (int j = i + 1;j < meth_list.length;j++)
				{
					if (meth_name.equals(meth_list[j].getName()))
					{
						StringBuffer mess = new StringBuffer("Method overloading is not supported for command (Method name = ");
						mess.append(meth_name);
						mess.append(")");
						Except.throw_exception("API_OverloadingNotSupported",
								     mess.toString(),
				  	  	  		     "TemplCommand.find_method()");
					}
						
				}					
				meth_found = meth_list[i];		
				break;
			}
		}
		if (i == meth_list.length)
		{
			StringBuffer mess = new StringBuffer("Command ");
			mess.append(name);
			mess.append(": Can't find method ");
			mess.append(meth_name);

			Except.throw_exception("API_MethodNotFound",
					     mess.toString(),
				  	     "TemplCommand.find_method()");
		}
		
		return meth_found;
	}

//+-------------------------------------------------------------------------
//
// method : 		get_tango_type
// 
// description : 	Return to the caller one int which is the Tango
//			type related to the java type coded in the Class
//			object
//
// input : - type_cl : The class object reference for the command argument
//
// This method returns an interger set to the Tango type code or throws an
// exception if the type id not supported
//
//--------------------------------------------------------------------------

/**
 * Get the TANGO type for a command argument.
 *
 * This type is retrieved from the method executing the command argument
 * Class object reference
 *
 * @param type_cl The argument Class object
 * @return The TANGO type
 * @exception DevFailed If the argument is not a TANGO supported type
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	protected int get_tango_type(Class type_cl) throws DevFailed
	{
		int type = 0;
		
//
// For arrays
//

		if (type_cl.isArray() == true)
		{
			String type_name = type_cl.getComponentType().getName();
			if (type_name.equals("byte"))
				type = Tango_DEVVAR_CHARARRAY;
			else if (type_name.equals("short"))
				type = Tango_DEVVAR_SHORTARRAY;
			else if (type_name.equals("int"))
				type = Tango_DEVVAR_LONGARRAY;
			else if (type_name.equals("float"))
				type = Tango_DEVVAR_FLOATARRAY;
			else if (type_name.equals("double"))
				type = Tango_DEVVAR_DOUBLEARRAY;
			else if (type_name.equals("java.lang.String"))
				type = Tango_DEVVAR_STRINGARRAY;
			else
			{
				StringBuffer mess = new StringBuffer("Argument array of ");
				mess.append(type_name);
				mess.append(" not supported");

				Except.throw_exception("API_MethodArgument",
						     mess.toString(),
				  	  	     "TemplCommandIn.get_tango_type()");
			}
		}
		
//
// For all the other types
//

		else
		{
			String type_name = type_cl.getName();
			if (type_name.equals("boolean"))
				type = Tango_DEV_BOOLEAN;
			else if (type_name.equals("short"))
				type = Tango_DEV_SHORT;
			else if (type_name.equals("int"))
				type = Tango_DEV_LONG;
			else if (type_name.equals("long"))
				type = Tango_DEV_LONG64;
			else if (type_name.equals("float"))
				type = Tango_DEV_FLOAT;
			else if (type_name.equals("double"))
				type = Tango_DEV_DOUBLE;
			else if (type_name.equals("java.lang.String"))
				type = Tango_DEV_STRING;
			else if (type_name.equals("Tango.DevVarLongStringArray"))
				type = Tango_DEVVAR_LONGSTRINGARRAY;
			else if (type_name.equals("Tango.DevVarDoubleStringArray"))
				type = Tango_DEVVAR_DOUBLESTRINGARRAY;
			else if (type_name .equals("Tango.State"))
				type = Tango_DEV_STATE;
			else
			{
				StringBuffer mess = new StringBuffer("Argument ");
				mess.append(type_name);
				mess.append(" not supported");

				Except.throw_exception("API_MethodArgument",
						     mess.toString(),
				  	  	     "TemplCommandIn.get_tango_type()");
			}
							
		}
		return type;
	}
		
//+-------------------------------------------------------------------------
//
// method : 		is_allowed
// 
// description : 	Check if the command is allowed. If the refrence to 
//			the method object "state_method" is null, the 
//			default mode id used (command always executed). 
//			Otherwise, the method is invoked
//
// input : - dev : reference to the device on which the command must be
//		   executed
//	   - data_in : Incoming command data
//
// This method returns a boolean set to true if the command is allowed
//
//--------------------------------------------------------------------------

/**
 * Invoke the command allowed method given at object creation time.
 *
 * This method is automtically called by the TANGO core classes when the
 * associated command is requested by a client to check if the command is allowed
 * in the actual device state. If the user give a command allowed method
 * at object creation time, this method will be invoked.
 *
 * @param dev The device on which the command must be executed
 * @param data_in The incoming data still packed in a CORBA Any object. For
 * command created with this TemplCommand class, this Any object does not
 * contain data
 * @return A boolean set to true is the command is allowed. Otherwise, the
 * return value is false. This return value is always set to true if the user
 * does not supply a method to be excuted. If a method has been supplied, the 
 * return value is the value returned by the user supplied mehod.
 */
 
	public boolean is_allowed(DeviceImpl dev,Any data_in)
	{
		if (state_method == null)
			return true;
		else
		{

//
// If the Method reference is not null, execute the method with the invoke
// method
//

			try
			{
				java.lang.Object[] meth_param = new java.lang.Object[1];
				meth_param[0] = data_in;
				java.lang.Object obj = state_method.invoke(dev,meth_param);
				return (Boolean) obj;
			}
			catch(InvocationTargetException e)
			{
				return false;
			}
			catch(IllegalArgumentException e)
			{
				return false;
			}
			catch (IllegalAccessException e)
			{
				return false;
			}
		}
	}
	
//+-------------------------------------------------------------------------
//
// method : 		execute
// 
// description : 	Execute the method associated with the command
//			(stored in the exe_method reference)
//
// input : - dev : Reference to the device on which the command must be
//		   executed
//	   - in_any : Incoming command data
//
// This method returns a reference to an Any object with the command outing
// data.
//
//--------------------------------------------------------------------------

/**
 * Invoke the command execution method given at object creation time.
 *
 * This method is automatically called by the TANGO core classes when the
 * associated command is requested by a client.
 *
 * @param dev The device on which the command must be executed
 * @param in_any The incoming data still packed in a CORBA Any object. For
 * command created with this TemplCommand class, this Any object does not
 * contain usefull data
 * @return The CORBA Any object returned to the client. For command created with
 * this TemplCommand class, this any object does not contain data.
 * @exception DevFailed If the execution method failed
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any execute(DeviceImpl dev,Any in_any) throws DevFailed
	{

//
// Execute the command associated method
//

		try
		{
			java.lang.Object[] meth_param = new java.lang.Object[0];
			exe_method.invoke(dev,meth_param);
		}
		catch(InvocationTargetException e)
		{
			throw (DevFailed)(e.getTargetException());
		}
		catch(IllegalArgumentException e)
		{
			StringBuffer mess = new StringBuffer("Argument error when trying to invoke method ");
			mess.append(exe_method);

			Except.throw_exception("API_MethodArgument",
					       mess.toString(),
				  	       "TemplCommand.execute()");
		}
		catch(IllegalAccessException e)
		{
			StringBuffer mess = new StringBuffer("Argument error when trying to invoke method ");
			mess.append(exe_method);

			Except.throw_exception("API_MethodArgument",
					       mess.toString(),
				  	       "TemplCommand.execute()");
		}
			
//
// Return an empty Any
//

		return insert();
	}			
}
