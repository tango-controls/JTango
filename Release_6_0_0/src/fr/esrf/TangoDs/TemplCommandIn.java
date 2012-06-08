//+============================================================================
//
// file :               TemplCommandIn.java
//
// description :        Java source code for the TemplCommandIn class. This class
//			is a class derived from the Command class. It is used
//			to implement command with the template command method.
//			This class is only for commands without output
//			parameters.
//
// project :            TANGO
//
// author(s) :          E.Taurel
//
// $Revision$
//
// $Log$
// Revision 3.9  2007/05/29 08:07:43  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.8  2005/12/02 09:55:46  pascal_verdier
// java import have been optimized.
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
// Revision 2.0  2003/01/09 16:02:58  taurel
// - Update release number before using SourceForge
//
// Revision 1.1.1.1  2003/01/09 15:54:39  taurel
// Imported sources into CVS before using SourceForge
//
// Revision 1.6  2001/10/10 08:11:25  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:38  taurel
// Many changes due to new release
//
// Revision 1.2  2001/05/04 12:03:22  taurel
// Fix bug in the Util.get_device_by_name() method
//
// Revision 1.1.1.1  2001/04/04 08:23:53  taurel
// Imported sources
//
// Revision 1.3  2000/04/13 08:23:02  taurel
// Added attribute support
//
// Revision 1.2  2000/02/04 09:10:00  taurel
// Just update revision number
//
// Revision 1.1.1.1  2000/02/04 09:08:24  taurel
// Imported sources
//
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DispLevel;
import org.omg.CORBA.Any;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * This class is a class representing a command in the template command model
 * with input parameter but without output parameter
 *
 * @author	$Author$
 * @version	$Revision$
 */
 
public class TemplCommandIn extends TemplCommand implements TangoConst
{

//+-------------------------------------------------------------------------
//
// method : 		TemplCommandIn 
// 
// description : 	constructors for class TemplCommandIn
//
//--------------------------------------------------------------------------

/**
 * Constructs a newly allocated TemplCommandIn object for a command with a
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

	public TemplCommandIn(String name,String exe_method,String state_method)
	{
		super(name);
				
		exe_method_name = exe_method;
		state_method_name = state_method;
		template_cmd = true;		
	}

/**
 * Constructs a newly allocated TemplCommandIn object for a command with a
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
 
	public TemplCommandIn(String name,String exe_method)
	{
		super(name);
		
		exe_method_name = exe_method;
		state_method_name = null;				
		template_cmd = true;				
	}

/**
 * Constructs a newly allocated TemplCommandIn object for a command with a
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
 			
	public TemplCommandIn(String name,String exe_method,String state_method,
			    String in_desc,String out_desc)
	{
		super(name,in_desc,out_desc,0.0);
				
		exe_method_name = exe_method;
		state_method_name = state_method;		
		template_cmd = true;		
	}

/**
 * Constructs a newly allocated TemplCommandIn object for a command with a
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
 	
	public TemplCommandIn(String name,String exe_method,
			    String in_desc,String out_desc)
	{
		super(name,in_desc,out_desc,0.0);
		
		exe_method_name = exe_method;
		state_method_name = null;				
		template_cmd = true;				
	}

/**
 * Constructs a newly allocated TemplCommandIn object for a command with a
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

	public TemplCommandIn(String name,String exe_method,String state_method,DispLevel disp)
	{
		super(name,disp);
				
		exe_method_name = exe_method;
		state_method_name = state_method;
		template_cmd = true;		
	}

/**
 * Constructs a newly allocated TemplCommandIn object for a command with a
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
 
	public TemplCommandIn(String name,String exe_method,DispLevel disp)
	{
		super(name,disp);
		
		exe_method_name = exe_method;
		state_method_name = null;				
		template_cmd = true;				
	}

/**
 * Constructs a newly allocated TemplCommandIn object for a command with a
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
 			
	public TemplCommandIn(String name,String exe_method,String state_method,
			    String in_desc,String out_desc,DispLevel disp)
	{
		super(name,in_desc,out_desc,disp,0.0);
				
		exe_method_name = exe_method;
		state_method_name = state_method;		
		template_cmd = true;		
	}

/**
 * Constructs a newly allocated TemplCommandIn object for a command with a
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
 	
	public TemplCommandIn(String name,String exe_method,
			    String in_desc,String out_desc,DispLevel disp)
	{
		super(name,in_desc,out_desc,disp,0.0);
		
		exe_method_name = exe_method;
		state_method_name = null;				
		template_cmd = true;				
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
 * the command allowed method. The analysis of the command execution method will
 * also set the command input type to the correct value depending on the
 * execution method input parameter type
 *
 * @exception DevFailed If one of the method does not fulfill the requirements
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
						     new String(mess),
				  	  	     new String("TemplCommandIn.analyse_method_exe()"));
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
						     new String(mess),
				  	  	     new String("TemplCommandIn.analyse_method_exe()"));
			}
			
//
// Check its argument
//
					
			Class[] param_type = meth.getParameterTypes();
			if (param_type.length != 1)
			{
				StringBuffer mess = new StringBuffer("Command ");
				mess.append(name);
				if (param_type.length == 0)
					mess.append(": No argument defined for method ");
				else
					mess.append(": More than one argument defined for method ");
				mess.append(exe_method);

				Except.throw_exception("API_MethodArgument",
						     new String(mess),
				  	  	     new String("TemplCommandIn.analyse_method_exe()"));
			}

//
// Analyse argument type
//

			in_type = get_tango_type(param_type[0]);
						
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
						     new String(mess),
				  	  	     new String("TemplCommandIn.analyse_method_exe()"));
			}							
		}
		catch (ClassNotFoundException ex)
		{
			StringBuffer mess = new StringBuffer("Command ");
			mess.append(name);
			mess.append(": Can't find class ");
			mess.append(cl_name);

			Except.throw_exception("API_ClassNotFound",
					     new String(mess),
				  	     new String("TemplCommandIn.analyse_method_exe()"));
		}
		catch (SecurityException ex)
		{
			StringBuffer mess = new StringBuffer("Command ");
			mess.append(name);
			mess.append(": Security violation when trying to retrieve method list for class ");
			mess.append(cl_name);
			
			Except.throw_exception("API_JavaRuntimeSecurityException",
					     new String(mess),
				  	     new String("TemplCommandIn.analyse_method_exe()"));
		}
		
//
// Return method reference for later use
//

		return meth;
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
 * @param data_in The incoming data still packed in a CORBA Any object. This
 * data is passed to this method in case it is necessary to take the command
 * allowed decision
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
				return ((Boolean)obj).booleanValue();
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
 * This method is automtically called by the TANGO core classes when the
 * associated command is requested by a client. It unpacks the data stored in
 * the CORBA Any object and invoke the user supplied command execution
 * method
 *
 * @param dev The device on which the command must be executed
 * @param in_any The incoming data still packed in a CORBA Any object.
 * @return The CORBA Any object returned to the client. For command created with
 * this TemplCommandIn class, this any object does not contain data.
 * @exception DevFailed If the execution method failed
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
 
	public Any execute(DeviceImpl dev,Any in_any) throws DevFailed
	{

		try
		{
			java.lang.Object[] meth_param = new java.lang.Object[1];
			
//
// Extract data from the any according to command parameter type
//

			switch (in_type)
			{
			case Tango_DEV_BOOLEAN:
				meth_param[0] = new Boolean(extract_DevBoolean(in_any));
				break;
				
			case Tango_DEV_SHORT:
				meth_param[0] = new Short(extract_DevShort(in_any));
				break;
				
			case Tango_DEV_LONG:
				meth_param[0] = new Integer(extract_DevLong(in_any));
				break;
				
			case Tango_DEV_LONG64:
				meth_param[0] = new Long(extract_DevLong64(in_any));
				break;
				
			case Tango_DEV_FLOAT:
				meth_param[0] = new Float(extract_DevFloat(in_any));
				break;
				
			case Tango_DEV_DOUBLE:
				meth_param[0] = new Double(extract_DevDouble(in_any));
				break;
				
			case Tango_DEV_STRING:
				meth_param[0] = extract_DevString(in_any);
				break;
				
			case Tango_DEVVAR_CHARARRAY:
				meth_param[0] = extract_DevVarCharArray(in_any);
				break;
				
			case Tango_DEVVAR_SHORTARRAY:
				meth_param[0] = extract_DevVarShortArray(in_any);
				break;
				
			case Tango_DEVVAR_LONGARRAY:
				meth_param[0] = extract_DevVarLongArray(in_any);
				break;
				
			case Tango_DEVVAR_LONG64ARRAY:
				meth_param[0] = extract_DevVarLong64Array(in_any);
				break;
				
			case Tango_DEVVAR_FLOATARRAY:
				meth_param[0] = extract_DevVarFloatArray(in_any);
				break;
				
			case Tango_DEVVAR_DOUBLEARRAY:
				meth_param[0] = extract_DevVarDoubleArray(in_any);
				break;
				
			case Tango_DEVVAR_STRINGARRAY:
				meth_param[0] = extract_DevVarStringArray(in_any);
				break;
				
			case Tango_DEVVAR_LONGSTRINGARRAY:
				meth_param[0] = extract_DevVarLongStringArray(in_any);
				break;
				
			case Tango_DEVVAR_DOUBLESTRINGARRAY:
				meth_param[0] = extract_DevVarDoubleStringArray(in_any);
				break;
				
			case Tango_DEV_STATE:
				meth_param[0] = extract_DevState(in_any);
				break;
			}				

//
// Execute the method
//
				
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
					       new String(mess),
				  	       new String("TemplCommandIn.execute()"));
		}
		catch(IllegalAccessException e)
		{
			StringBuffer mess = new StringBuffer("Argument error when trying to invoke method ");
			mess.append(exe_method);

			Except.throw_exception("API_MethodArgument",
					       new String(mess),
				  	       new String("TemplCommandIn.execute()"));
		}
			
//
// Return an empty Any
//

		return insert();
	}			
}
