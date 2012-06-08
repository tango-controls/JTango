//=============================================================================
//
// file :               except.java
//
// description :        Java source code for the Except class. This class is
//			a container for exception related methods.   
//
// project :            TANGO
//
// author(s) :          E.Taurel
//
//
// Revision 1.3  2000/04/13 10:40:43  taurel
// Added attribute support
//
// Revision 1.2  2000/02/04 11:00:17  taurel
// Just update revision number
//
// Revision 1.1.1.1  2000/02/04 10:58:28  taurel
// Imported sources
//
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//=============================================================================

package fr.esrf.TangoDs;

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;
import fr.esrf.TangoApi.*;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;


/**
 * Container class for all exception related methods. Most of these methods are
 * static methods
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
 * @version	$Revision$
 */
 
public class Except implements TangoConst, java.io.Serializable
{

//=============================================================================
//
//			The Except class
//
// description :	This class is a container for all exceptions related
//			methods to be used in aa Tango device server/client.
//			Most of these methods are static.
//
//=============================================================================
	
    //================================================================
	/**
	 *	This method, like print_exception method.
	 *	It builds a string with exception argumet but returns this string.
	 */
    //================================================================
    public static String str_exception(Exception except)
    {
        String	str = "";

        if (except instanceof ConnectionFailed)
            str += ((ConnectionFailed)(except)).getStack();
        else
        if (except instanceof CommunicationFailed)
            str += ((CommunicationFailed)(except)).getStack();
        else
        if (except instanceof WrongNameSyntax)
            str += ((WrongNameSyntax)(except)).getStack();
        else
        if (except instanceof WrongData)
            str += ((WrongData)(except)).getStack();
        else
        if (except instanceof NonDbDevice)
            str += ((NonDbDevice)(except)).getStack();
        else
        if (except instanceof NonSupportedFeature)
            str += ((NonSupportedFeature)(except)).getStack();
        else
        if (except instanceof EventSystemFailed)
            str += ((EventSystemFailed)(except)).getStack();
        else
        if (except instanceof AsynReplyNotArrived)
            str += ((AsynReplyNotArrived)(except)).getStack();
        else
        if (except instanceof DevFailed)
        {
            DevFailed	df = (DevFailed)except;
            //	True DevFailed
            str += "Tango exception  " + df.toString() + "\n";
            for (int i=0 ; i<df.errors.length ; i++)
            {
                str += "Severity -> ";
                switch (df.errors[i].severity.value())
                {
                case ErrSeverity._WARN :
                    str += "WARNING \n";
                    break;

                case ErrSeverity._ERR :
                    str += "ERROR \n";
                    break;

                case ErrSeverity._PANIC :
                    str += "PANIC \n";
                    break;

                default :
                    str += "Unknown severity code";
                    break;
                }
                str += "Desc   -> " + df.errors[i].desc   + "\n";
                str += "Reason -> " + df.errors[i].reason + "\n";
                str += "Origin -> " + df.errors[i].origin + "\n";

                if (i<df.errors.length-1)
                    str += "-------------------------------------------------------------\n";
            }
        }
        else
            str = except.toString();
        return str;
    }

/**
 * Print a TANGO exception.
 *
 * Print all the details of a TANGO exception. If the exception is not a 
 * TANGO DevFailed exception, it
 * only print the stack.
 *
 * @param errors errors array
 */	
	public static void print_exception(fr.esrf.Tango.DevError[] errors)
	{
		print_exception(new DevFailed(errors));
	}
/**
 * Print a TANGO exception.
 *
 * Print all the details of a TANGO exception. If the exception is not a 
 * TANGO DevFailed exception, it
 * only print the stack.
 *
 * @param ex The exception object reference
 */	
	public static void print_exception(Exception ex)
	{
	
//
// get exception name
//
/***		
		String ex_name = ex.getClass().getName();
		int last_dot = ex_name.lastIndexOf('.');
		if (last_dot == -1)
		{
		
//
// Unknown exception
//

			System.err.println(ex.getMessage());
			ex.printStackTrace();
			return;
		}
		String ex_last_name = ex_name.substring(last_dot + 1);
		if (ex_last_name.equals("DevFailed") == true)
*****/
		if (ex instanceof DevFailed)
		{
		
//
// For a Tango DevFailed exception
//

			DevFailed ex_dev = ((fr.esrf.Tango.DevFailed)ex);
			for (int i=0 ; i<ex_dev.errors.length ; i++)
			{
				System.err.println("Tango exception");
				System.err.print("Severity -> ");
				switch (ex_dev.errors[i].severity.value())
				{
				case ErrSeverity._WARN :
					System.err.println("WARNING ");
					break;
						
				case ErrSeverity._ERR :
					System.err.println("ERROR ");
					break;
					
				case ErrSeverity._PANIC :
					System.err.println("PANIC ");
					break;
						
				default :
					System.err.println("Unknown severity code");
					break;
				}
				System.err.println("Desc -> " + ex_dev.errors[i].desc);
				
				//	Check if from a MultiDevFailed exception
				if (ex instanceof NamedDevFailedList)
				{
					NamedDevFailedList	ndfl = (NamedDevFailedList)ex;
					if (i<ndfl.get_faulty_attr_nb())
					{
						NamedDevFailed	ndf = ndfl.elementAt(i);
						for (DevError err : ndf.err_stack)
							System.out.println("Reason -> " + err.reason);
					}
				}
				else
					System.err.println("Reason -> " + ex_dev.errors[i].reason);
				System.err.println("Origin -> " + ex_dev.errors[i].origin);
			}
		}
		else
		{
			int		last_dot, before_last_dot;
			String	ex_name = ex.getClass().getName();
			if ((last_dot=ex_name.lastIndexOf('.'))<0)
			{
				// Unknown exception
				System.err.println(ex.getMessage());
				ex.printStackTrace();
				return;
			}
			String ex_last_name = ex_name.substring(last_dot + 1);
			if ((before_last_dot=ex_name.lastIndexOf('.',last_dot - 1))<0)
			{
				// Unknown exception
				System.err.println(ex.getMessage());
				ex.printStackTrace();
				return;
			}

			String ex_part_name = ex_name.substring(before_last_dot + 1,last_dot);
			if (ex_part_name.equals("CORBA") == true)
			{
				// CORBA System exception
				SystemException ex_sys = ((SystemException)ex);
				System.err.println("CORBA system exception " + ex_last_name);
				System.err.println(ex.getMessage());
				System.err.print("Completed: ");
				switch(ex_sys.completed.value())
				{
					case CompletionStatus._COMPLETED_YES :
						System.err.println("yes");
						break;

					case CompletionStatus._COMPLETED_NO :
						System.err.println("no");
						break;

					case CompletionStatus._COMPLETED_MAYBE :
						System.err.println("maybe");
						break;
				}
				System.err.println("Minor code: " + ex_sys.minor);
			}
			else
			{
				// Unknown exeption
				System.err.println(ex.getMessage());
				ex.printStackTrace();
			}

		}
	}

/**
 * Print a TANGO exception with stack.
 *
 * Print all the details of a TANGO exception. It also prints the stack when
 * the exception occurs. If the exception is not a TANGO DevFailed exception, it
 * only print the stack.
 *
 * @param ex The exception object reference
 */
 	
	public static void print_exception_stack(Exception ex)
	{
		print_exception(ex);
		ex.printStackTrace();
	}
	
/**
 * Generate and throw a TANGO DevFailed exception.
 *
 * The exception is created with a single DevError
 * object. The DevError severity field is set to ERR
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 *
 * @param reason The exception DevError object reason field
 * @param desc The exception DevError object desc field
 * @param origin The exception DevError object origin field
 * @exception DevFailed The thrown exception.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
	public static void throw_exception(String reason,String desc,
					   String origin)
					   throws DevFailed
	{
		DevError[] err = new DevError[1];
		err[0] = new DevError();
	
		err[0].desc = desc;
		err[0].severity = ErrSeverity.ERR;
		err[0].reason = reason;
		err[0].origin = origin;
	
		throw new DevFailed(err);
	}

/**
 * Generate and throw a TANGO DevFailed exception.
 *
 * The exception is created with a single DevError
 * object.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 *
 * @param reason The exception DevError object reason field
 * @param desc The exception DevError object desc field
 * @param origin The exception DevError object origin field
 * @param sever The exception DevError object severity field
 * @exception DevFailed The thrown exception.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
	public static void throw_exception(String reason,String desc,
					   String origin,ErrSeverity sever)
					   throws DevFailed
	{
		DevError[] err = new DevError[1];
		err[0] = new DevError();
	
		err[0].desc = desc;
		err[0].severity = sever;
		err[0].reason = reason;
		err[0].origin = origin;
	
		throw new DevFailed(err);
	}

/**
 * Re-throw a TANGO DevFailed exception with one more error.
 *
 * The exception is re-thrown with one more DevError
 * object. The new DevError severity field is set to ERR
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 *
 * @param ex The DevFailed exception
 * @param reason The exception DevError object reason field
 * @param desc The exception DevError object desc field
 * @param origin The exception DevError object origin field
 * @exception DevFailed The thrown exception.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
	public static void re_throw_exception(DevFailed ex,
					      String reason,String desc,
					      String origin)
					      throws DevFailed
	{
		int nb_error = ex.errors.length;
		DevError[] err = new DevError[nb_error + 1];
		System.arraycopy(ex.errors, 0, err, 0, nb_error);
		err[nb_error] = new DevError();
	
		err[nb_error].desc = desc;
		err[nb_error].severity = ErrSeverity.ERR;
		err[nb_error].reason = reason;
		err[nb_error].origin = origin;
	
		throw new DevFailed(err);
	}

/**
 * Re-throw a TANGO DevFailed exception with one more error.
 *
 * The exception is re-rhrown with one more DevError
 * object.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 *
 * @param ex The DevFailed exception
 * @param reason The exception DevError object reason field
 * @param desc The exception DevError object desc field
 * @param origin The exception DevError object origin field
 * @param sever The exception DevError object severity field
 * @exception DevFailed The thrown exception.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */
	public static void re_throw_exception(DevFailed ex,
					      String reason,String desc,
					      String origin,ErrSeverity sever)
					      throws DevFailed
	{
		int nb_error = ex.errors.length;
		DevError[] err = new DevError[nb_error + 1];
		System.arraycopy(ex.errors, 0, err, 0, nb_error);
		err[nb_error] = new DevError();
	
		err[nb_error].desc = desc;
		err[nb_error].severity = sever;
		err[nb_error].reason = reason;
		err[nb_error].origin = origin;
	
		throw new DevFailed(err);
// ex.errors = err;
// throw ex;
	}
			
/**
 * Generate and throw a TANGO DevFailed exception from a CORBA exception.
 *
 * The exception is created with a single DevError
 * object. A value ERR is defined for the DevError
 * severity field.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 *
 * @param ex A CORBA System Exception. The reason and desc fields of the
 * DevError object will be set according to the data in this exception.
 * The reason field is always set to API_CorbaSysException and the reason flag is 
 * different depending on the exact type of the CORBA system exception.
 * @param origin The exception DevError object origin field
 * @exception DevFailed The thrown exception.
 * Click <a href="../../tango_basic/idl_html/Tango.html#DevFailed">here</a> to read
 * <b>DevFailed</b> exception specification
 */	
	public static void throw_exception(SystemException ex,
					   String origin)
					   throws DevFailed
	{
		DevError[] err = new DevError[1];
		err[0] = new DevError();
	
		err[0].desc = print_CORBA_SystemException(ex);
		err[0].severity = ErrSeverity.ERR;
		err[0].reason = "API_CorbaSysException";
		err[0].origin = origin;
	
		throw new DevFailed(err);
	}


/**
 * Create a string from a CORBA excepition
 *
 * @param ex The CORBA exception
 *
 */
	public static String print_CORBA_SystemException(SystemException ex)
	{
	
//
// get exception name
//
		
		String ex_name = ex.getClass().getName();
		int last_dot = ex_name.lastIndexOf('.');
		
//
// Unknown exception
//

		if (last_dot == -1)
		{
			return "Unknown system exception !!!!!!!!";
		}
		String ex_last_name = ex_name.substring(last_dot + 1);	
		int before_last_dot = ex_name.lastIndexOf('.',last_dot - 1);
		
//
// Unknown exception
//

		if (before_last_dot == -1)
		{
			return "Unknown system exception !!!!!!!!";
		}
		else
		{
			String ex_part_name = ex_name.substring(before_last_dot + 1,last_dot);

			if (ex_part_name.equals("CORBA") == true)
			{
				
//
// CORBA System exception
//

				StringBuffer str = new StringBuffer("CORBA system exception ");
				str.append(ex_last_name);
				str.append(ex.getMessage());
				str.append("Completed: ");
				switch(ex.completed.value())
				{
				case CompletionStatus._COMPLETED_YES :
					str.append("yes");
					break;
							
				case CompletionStatus._COMPLETED_NO :
					str.append("no");
					break;
							
				case CompletionStatus._COMPLETED_MAYBE :
					str.append("maybe");
					break;
				}
				str.append("Minor code: ");
				str.append(ex.minor);
				
				return str.toString();
			}
			else
			{
				
//
// Unknown exeption
//
				return "Unknown system exception !!!!!!!!";

			}
		}
	}	




//=======================================================================
/*
 *	Client exceptions management
 */
//=======================================================================
	//===================================================================
	/**
	 *	Throw a ConnectionFailed exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception ConnectionFailed The client exception.
	 */
	//===================================================================
	static public void throw_connection_failed(String reason,String desc, String origin)
			throws ConnectionFailed
	{
		throw_connection_failed(null,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._PANIC));
	}
	//===================================================================
	/**
	 *	Re-throw a ConnectionFailed exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception ConnectionFailed The client exception.
	 */
	//===================================================================
	static public void throw_connection_failed(DevFailed df, String reason,String desc, String origin)
			throws ConnectionFailed
	{
		throw_connection_failed(df,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._PANIC));
	}
	//===================================================================
	/**
	 *	Throw a ConnectionFailed exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception ConnectionFailed The client exception.
	 */
	//===================================================================
	static public void throw_connection_failed(String reason,String desc, String origin, ErrSeverity severity)
			throws ConnectionFailed
	{
		throw_connection_failed(null,
			reason, desc, origin, severity);
	}
	//===================================================================
	/**
	 *	re-throw a DevFailed in a ConnectionFailed exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception ConnectionFailed The client exception.
	 */
	//===================================================================
	static public void throw_connection_failed(DevFailed df, String reason,String desc, String origin, ErrSeverity severity)
			throws ConnectionFailed
	{
		
		DevError[]	err;
		int			i = 0;
		
		//	if DevFailed is null -> allocate
		if (df==null)
			err =  new DevError[1];
		else
		{
			//	Else copy in new object
			err = new DevError[df.errors.length+1];
			for ( ; i<df.errors.length ; i++)
				err[i] = df.errors[i];
		}
		err[i] = new DevError(reason, severity, desc, origin);
		throw new ConnectionFailed(err);
	}
	//===================================================================
	/**
	 *	Throw a CommunicationFailed exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception CommunicationFailed The client exception.
	 */
	//===================================================================
	static public void throw_communication_failed(String reason,String desc, String origin)
			throws CommunicationFailed
	{
		throw_communication_failed(null,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
	/**
	 *	Re-throw a CommunicationFailed exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception CommunicationFailed The client exception.
	 */
	//===================================================================
	static public void throw_communication_failed(DevFailed df, String reason,String desc, String origin)
			throws CommunicationFailed
	{
		throw_communication_failed(df,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
    /**
     *  Throw a CommunicationTimeout exception.
     *
     * @param reason The exception DevError object reason field
     * @param desc The exception DevError object desc field
     * @param origin The exception DevError object origin field
     * @exception CommunicationTimeout The client exception.
     */
    //===================================================================
    static public void throw_communication_timeout(String reason,String desc, String origin)
            throws fr.esrf.TangoApi.CommunicationTimeout
    {
        throw_communication_timeout(null,
            reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
    }
	//===================================================================
	/**
	 *	Throw a CommunicationFailed exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception CommunicationFailed The client exception.
	 */
	//===================================================================
	static public void throw_communication_failed(String reason,String desc, String origin, ErrSeverity severity)
			throws CommunicationFailed
	{
		throw_communication_failed(null,
			reason, desc, origin, severity);
	}
	//===================================================================
	/**
	 *	re-throw a DevFailed in a CommunicationFailed exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception CommunicationFailed The client exception.
	 */
	//===================================================================
	static public void throw_communication_failed(DevFailed df, String reason,String desc, String origin, ErrSeverity severity)
			throws CommunicationFailed
	{
		
		DevError[]	err;
		int			i = 0;
		
		//	if DevFailed is null -> allocate
		if (df==null)
			err =  new DevError[1];
		else
		{
			//	Else copy in new object
			err = new DevError[df.errors.length+1];
			for ( ; i<df.errors.length ; i++)
				err[i] = df.errors[i];
		}
		err[i] = new DevError(reason, severity, desc, origin);
		throw new CommunicationFailed(err);
	}

	//===================================================================
    /**
     *  re-throw a DevFailed in a CommunicationTimeout exception.
     *
     * @param df A DevFailed exception to be rethrown.
     * @param reason The exception DevError object reason field
     * @param desc The exception DevError object desc field
     * @param origin The exception DevError object origin field
     * @param severity The exception DevError object severity field
     * @exception CommunicationTimeout The client exception.
     */
    //===================================================================
    static public void throw_communication_timeout(DevFailed df, String reason,String desc, String origin, ErrSeverity severity)
            throws fr.esrf.TangoApi.CommunicationTimeout
    {
        
        DevError[]  err;
        int         i = 0;
        
        //  if DevFailed is null -> allocate
        if (df==null)
            err =  new DevError[1];
        else
        {
            //  Else copy in new object
            err = new DevError[df.errors.length+1];
            for ( ; i<df.errors.length ; i++)
                err[i] = df.errors[i];
        }
        err[i] = new DevError(reason, severity, desc, origin);
        throw new fr.esrf.TangoApi.CommunicationTimeout(err);
    }
	//===================================================================
	/**
	 *	Throw a WrongNameSyntax exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception WrongNameSyntax The client exception.
	 */
	//===================================================================
	static public void throw_wrong_syntax_exception(String reason,String desc, String origin)
			throws WrongNameSyntax
	{
		throw_wrong_syntax_exception(null,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
	/**
	 *	Re-throw a WrongNameSyntax exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception WrongNameSyntax The client exception.
	 */
	//===================================================================
	static public void throw_wrong_syntax_exception(DevFailed df, String reason,String desc, String origin)
			throws WrongNameSyntax
	{
		throw_wrong_syntax_exception(df,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
	/**
	 *	Throw a WrongNameSyntax exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception WrongNameSyntax The client exception.
	 */
	//===================================================================
	static public void throw_wrong_syntax_exception(String reason,String desc, String origin, ErrSeverity severity)
			throws WrongNameSyntax
	{
		throw_wrong_syntax_exception(null,
			reason, desc, origin, severity);
	}
	//===================================================================
	/**
	 *	re-throw a DevFailed in a WrongNameSyntax exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception WrongNameSyntax The client exception.
	 */
	//===================================================================
	static public void throw_wrong_syntax_exception(DevFailed df, String reason,String desc, String origin, ErrSeverity severity)
			throws WrongNameSyntax
	{
		
		DevError[]	err;
		int			i = 0;
		
		//	if DevFailed is null -> allocate
		if (df==null)
			err =  new DevError[1];
		else
		{
			//	Else copy in new object
			err = new DevError[df.errors.length+1];
			for ( ; i<df.errors.length ; i++)
				err[i] = df.errors[i];
		}
		err[i] = new DevError(reason, severity, desc, origin);
		throw new WrongNameSyntax(err);
	}

	//===================================================================
	/**
	 *	Throw a WrongData exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception WrongData The client exception.
	 */
	//===================================================================
	static public void throw_wrong_data_exception(String reason,String desc, String origin)
			throws WrongData
	{
		throw_wrong_data_exception(null,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
	/**
	 *	Re-throw a WrongData exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception WrongData The client exception.
	 */
	//===================================================================
	static public void throw_wrong_data_exception(DevFailed df, String reason,String desc, String origin)
			throws WrongData
	{
		throw_wrong_data_exception(df,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
	/**
	 *	Throw a WrongData exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception WrongData The client exception.
	 */
	//===================================================================
	static public void throw_wrong_data_exception(String reason,String desc, String origin, ErrSeverity severity)
			throws WrongData
	{
		throw_wrong_data_exception(null,
			reason, desc, origin, severity);
	}
	//===================================================================
	/**
	 *	re-throw a DevFailed in a WrongData exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception WrongData The client exception.
	 */
	//===================================================================
	static public void throw_wrong_data_exception(DevFailed df, String reason,String desc, String origin, ErrSeverity severity)
			throws WrongData
	{
		
		DevError[]	err;
		int			i = 0;
		
		//	if DevFailed is null -> allocate
		if (df==null)
			err =  new DevError[1];
		else
		{
			//	Else copy in new object
			err = new DevError[df.errors.length+1];
			for ( ; i<df.errors.length ; i++)
				err[i] = df.errors[i];
		}
		err[i] = new DevError(reason, severity, desc, origin);
		throw new WrongData(err);
	}

	//===================================================================
	/**
	 *	Throw a NonDbDevice exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception NonDbDevice The client exception.
	 */
	//===================================================================
	static public void throw_non_db_exception(String reason,String desc, String origin)
			throws NonDbDevice
	{
		throw_non_db_exception(null,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
	/**
	 *	Re-throw a NonDbDevice exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception NonDbDevice The client exception.
	 */
	//===================================================================
	static public void throw_non_db_exception(DevFailed df, String reason,String desc, String origin)
			throws NonDbDevice
	{
		throw_non_db_exception(df,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
	/**
	 *	Throw a NonDbDevice exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception NonDbDevice The client exception.
	 */
	//===================================================================
	static public void throw_non_db_exception(String reason,String desc, String origin, ErrSeverity severity)
			throws NonDbDevice
	{
		throw_non_db_exception(null,
			reason, desc, origin, severity);
	}
	//===================================================================
	/**
	 *	re-throw a DevFailed in a NonDbDevice exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception NonDbDevice The client exception.
	 */
	//===================================================================
	static public void throw_non_db_exception(DevFailed df, String reason,String desc, String origin, ErrSeverity severity)
			throws NonDbDevice
	{
		
		DevError[]	err;
		int			i = 0;
		
		//	if DevFailed is null -> allocate
		if (df==null)
			err =  new DevError[1];
		else
		{
			//	Else copy in new object
			err = new DevError[df.errors.length+1];
			for ( ; i<df.errors.length ; i++)
				err[i] = df.errors[i];
		}
		err[i] = new DevError(reason, severity, desc, origin);
		throw new NonDbDevice(err);
	}

	//===================================================================
	/**
	 *	Throw a NonSupportedFeature exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception NonSupportedFeature The client exception.
	 */
	//===================================================================
	static public void throw_non_supported_exception(String reason,String desc, String origin)
			throws NonSupportedFeature
	{
		throw_non_supported_exception(null,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
	/**
	 *	Re-throw a NonSupportedFeature exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception NonSupportedFeature The client exception.
	 */
	//===================================================================
	static public void throw_non_supported_exception(DevFailed df, String reason,String desc, String origin)
			throws NonSupportedFeature
	{
		throw_non_supported_exception(df,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
	/**
	 *	Throw a NonSupportedFeature exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception NonSupportedFeature The client exception.
	 */
	//===================================================================
	static public void throw_non_supported_exception(String reason,String desc, String origin, ErrSeverity severity)
			throws NonSupportedFeature
	{
		throw_non_supported_exception(null,
			reason, desc, origin, severity);
	}
	//===================================================================
	/**
	 *	re-throw a DevFailed in a NonSupportedFeature exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception NonSupportedFeature The client exception.
	 */
	//===================================================================
	static public void throw_non_supported_exception(DevFailed df, String reason,String desc, String origin, ErrSeverity severity)
			throws NonSupportedFeature
	{
		
		DevError[]	err;
		int			i = 0;
		
		//	if DevFailed is null -> allocate
		if (df==null)
			err =  new DevError[1];
		else
		{
			//	Else copy in new object
			err = new DevError[df.errors.length+1];
			for ( ; i<df.errors.length ; i++)
				err[i] = df.errors[i];
		}
		err[i] = new DevError(reason, severity, desc, origin);
		throw new NonSupportedFeature(err);
	}

	//===================================================================
	/**
	 *	Throw a AsynReplyNotArrived exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception AsynReplyNotArrived The client exception.
	 */
	//===================================================================
	static public void throw_asyn_reply_not_arrived(String reason,String desc, String origin)
			throws AsynReplyNotArrived
	{
		throw_asyn_reply_not_arrived(null,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
	/**
	 *	Re-throw a AsynReplyNotArrived exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception AsynReplyNotArrived The client exception.
	 */
	//===================================================================
	static public void throw_asyn_reply_not_arrived(DevFailed df, String reason,String desc, String origin)
			throws AsynReplyNotArrived
	{
		throw_asyn_reply_not_arrived(df,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
	/**
	 *	Throw a AsynReplyNotArrived exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception AsynReplyNotArrived The client exception.
	 */
	//===================================================================
	static public void throw_asyn_reply_not_arrived(String reason,String desc, String origin, ErrSeverity severity)
			throws AsynReplyNotArrived
	{
		throw_asyn_reply_not_arrived(null,
			reason, desc, origin, severity);
	}
	//===================================================================
	/**
	 *	re-throw a DevFailed in a AsynReplyNotArrived exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception AsynReplyNotArrived The client exception.
	 */
	//===================================================================
	static public void throw_asyn_reply_not_arrived(DevFailed df, String reason,String desc, String origin, ErrSeverity severity)
			throws AsynReplyNotArrived
	{
		
		DevError[]	err;
		int			i = 0;
		
		//	if DevFailed is null -> allocate
		if (df==null)
			err =  new DevError[1];
		else
		{
			//	Else copy in new object
			err = new DevError[df.errors.length+1];
			for ( ; i<df.errors.length ; i++)
				err[i] = df.errors[i];
		}
		err[i] = new DevError(reason, severity, desc, origin);
		throw new AsynReplyNotArrived(err);
	}

	//===================================================================
	/**
	 *	Throw a EventSystemFailed exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception EventSystemFailed The client exception.
	 */
	//===================================================================
	static public void throw_event_system_failed(String reason,String desc, String origin)
			throws EventSystemFailed
	{
		throw_event_system_failed(null,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
	/**
	 *	Re-throw a EventSystemFailed exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @exception EventSystemFailed The client exception.
	 */
	//===================================================================
	static public void throw_event_system_failed(DevFailed df, String reason,String desc, String origin)
			throws EventSystemFailed
	{
		throw_event_system_failed(df,
			reason, desc, origin, ErrSeverity.from_int(ErrSeverity._ERR));
	}
	//===================================================================
	/**
	 *	Throw a EventSystemFailed exception.
	 *
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception EventSystemFailed The client exception.
	 */
	//===================================================================
	static public void throw_event_system_failed(String reason,String desc, String origin, ErrSeverity severity)
			throws EventSystemFailed
	{
		throw_event_system_failed(null,
			reason, desc, origin, severity);
	}
	//===================================================================
	/**
	 *	re-throw a DevFailed in a EventSystemFailed exception.
	 *
	 * @param df A DevFailed exception to be rethrown.
	 * @param reason The exception DevError object reason field
	 * @param desc The exception DevError object desc field
	 * @param origin The exception DevError object origin field
	 * @param severity The exception DevError object severity field
	 * @exception EventSystemFailed The client exception.
	 */
	//===================================================================
	static public void throw_event_system_failed(DevFailed df, String reason,String desc, String origin, ErrSeverity severity)
			throws EventSystemFailed
	{
		
		DevError[]	err;
		int			i = 0;
		
		//	if DevFailed is null -> allocate
		if (df==null)
			err =  new DevError[1];
		else
		{
			//	Else copy in new object
			err = new DevError[df.errors.length+1];
			for ( ; i<df.errors.length ; i++)
				err[i] = df.errors[i];
		}
		err[i] = new DevError(reason, severity, desc, origin);
		throw new EventSystemFailed(err);
	}

}
