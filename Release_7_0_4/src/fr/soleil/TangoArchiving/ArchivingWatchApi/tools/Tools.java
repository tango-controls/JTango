/*	Synchrotron Soleil 
 *  
 *   File          :  Tools.java
 *  
 *   Project       :  archiving_watcher
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  30 nov. 2005 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: Tools.java,v 
 *
 */
 /*
 * Created on 30 nov. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.tools;

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.DBTools;
import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warnable;
import fr.soleil.TangoArchiving.ArchivingWatchApi.lifecycle.LifeCycleManager;
import fr.soleil.TangoArchiving.ArchivingWatchApi.lifecycle.LifeCycleManagerFactory;

/**
 * Contains varied logging and exception handling methods. 
 * @author CLAISSE 
 */
public class Tools 
{
    /**
     * Portable carriage return/line feeed
     */
    public static final String CRLF = System.getProperty( "line.separator" );
    /**
     * Separates parts of Tango attributes complete names
     */
    public static final String TANGO_SEPARATOR = "/";
    
    /**
     * Converts a String to a boolean.
     * @param in The String to convert
     * @return The boolean representation
     */
    public static boolean stringToBoolean ( String in )
    {
        try
        {
            Boolean b = Boolean.valueOf( in );
            return b.booleanValue();
        }
        catch ( Exception e )
        {
            return false;
        }
    }
    
    /**
     * Logs a message using:
     * <UL> 
     *  <LI> If the archiving watcher runs in standalone mode, usual System.out logs
     *  <LI> If the archiving watcher runs in device mode, the device's tango logging  
     * </UL>
     * @param msg The message to log
     * @param level The criticity level
     */
    public static void trace ( String msg , int level )
    {
        Tools.dummyTrace ( msg , level );
        Tools.logIntoDiary ( level , msg );
        
        if ( DBTools.getWarnable () != null )
        {
            DBTools.trace ( msg , level );
            return;
        }
        
        LifeCycleManager lifeCycleManager = LifeCycleManagerFactory.getCurrentImpl ();
        if ( lifeCycleManager == null )
        {
            Tools.dummyTrace ( msg , level );
            return;
        }
        Warnable warnable = lifeCycleManager.getWatcherToWarn ();
        if ( warnable == null )
        {
            Tools.dummyTrace ( msg , level );
            return;
        }
        
        try 
        {
            warnable.trace ( msg , level );
        } 
        catch (DevFailed e) 
        {
            e.printStackTrace();
            Throwable cause = e.getCause ();
            if ( cause != null )
            {
                cause.printStackTrace ();
            }
            Tools.dummyTrace ( msg , level );
        }
        
    }
    
    private static void logIntoDiary( int level , Object msg ) 
    {
        LifeCycleManager lcManager = LifeCycleManagerFactory.getCurrentImpl ();
        if ( lcManager == null )
        {
            return;
        }
        Warnable warnable = lcManager.getWatcherToWarn ();
        if ( warnable != null )
        {
            warnable.logIntoDiary ( level , msg );
        }
    }

    /**
     * Logs a message and an exception using:
     * <UL> 
     *  <LI> If the archiving watcher runs in standalone mode, usual System.out logs
     *  <LI> If the archiving watcher runs in device mode, the device's tango logging  
     * </UL>
     * @param msg The message to log
     * @param t The exception to log
     * @param level The criticity level
     */
    public static void trace ( String msg , Throwable t , int level )
    {
        Tools.dummyTrace ( msg , t , level );
        Tools.logIntoDiary ( msg , t , level );
        
        if ( DBTools.getWarnable () != null )
        {
            DBTools.trace ( msg , t , level );
            return;
        }
        
        LifeCycleManager lifeCycleManager = LifeCycleManagerFactory.getCurrentImpl ();
        if ( lifeCycleManager == null )
        {
            Tools.dummyTrace ( msg , t , level );
            return;
        }
        
        Warnable warnable = lifeCycleManager.getWatcherToWarn ();
        if ( warnable == null )
        {
            Tools.dummyTrace ( msg , t , level );
        }
        else
        {
            try 
            {
                warnable.trace ( msg , t , level );
            } 
            catch (DevFailed e) 
            {
                e.printStackTrace();
                Throwable cause = e.getCause ();
                if ( cause != null )
                {
                    cause.printStackTrace ();
                }
                Tools.dummyTrace ( msg , level );
            }
        }
    }

    private static void logIntoDiary(String msg, Throwable t, int level) 
    {
        logIntoDiary ( level , msg );
        logIntoDiary ( level , t );
    }

    /**
     * Logging used in standalone mode
     * @param msg
     * @param t
     * @param level
     */
    private static void dummyTrace(String msg, Throwable t, int level) 
    {
        System.out.println ( msg );
        t.printStackTrace ();
    }

    /**
     * Logging used in standalone mode
     * @param msg
     * @param level
     */
    private static void dummyTrace(String msg, int level) 
    {
        System.out.println ( msg );
    }

    /**
     * Converts a Throwable to a DevFailed and throws it.
     * @param exception The exception to convert
     * @return The DevFailed representation
     * @throws DevFailed Always thrown by definition of the method
     */
    public static void throwDevFailed(Throwable exception) throws DevFailed 
    {
	    DevFailed devFailed = new DevFailed ();
	    devFailed.initCause ( exception );
        devFailed.setStackTrace ( exception.getStackTrace () );
	    throw devFailed;
    }
    
    /**
     * Extracts causes from a Throwable if it is an instance of DevFailed, and prints it.
     * @param t The exception to log
     */
    public static void printIfDevFailed (Throwable t) 
    {
        t.printStackTrace ();    
        
        if (t instanceof DevFailed)
		{
            if ( ( (DevFailed) t ).getCause() != null )
            {
                System.out.println ( "CAUSE---------------START" );
			    ( (DevFailed) t ).getCause().printStackTrace (); 
			    System.out.println ( "CAUSE---------------END" );
            }
            
            DevError [] errors = ( (DevFailed) t ).errors;
		    if ( errors != null && errors.length !=0 )
		    {
		        System.out.println ( "ERRORS---------------START" );
		        for (  int i = 0 ; i < errors.length ; i ++ )
		        {
		            DevError error = errors [ i ];
		            System.out.println ( "desc/"+error.desc+"/origin/"+error.origin+"/reason/"+error.reason );
		        }
		        System.out.println ( "ERRORS---------------END" );
		    }
		}    
    }
    
    private static String getCompleteMessage ( StackTraceElement [] steList )
    {
        StringBuffer message = new StringBuffer ();
        
        if ( steList != null && steList.length != 0 )
        {
            message.append ( Tools.CRLF );
            
            for ( int i = 0 ; i < steList.length ; i ++ )
            {
                StackTraceElement ste = steList [ i ];
                message.append ( ste.toString () );
                message.append ( Tools.CRLF );
            }
        }
        
        return message.toString ();
    }
    
    /**
     * Extracts causes from a DevFailed.
     * @param t The exception to log
     * @return A String representation of the content of the DevFailed 
     */
    public static String getCompleteMessage (DevFailed t) 
    {
        StringBuffer message = new StringBuffer ();
        
        StackTraceElement [] steList = t.getStackTrace ();
        message.append ( Tools.getCompleteMessage ( steList ) );
        
        if ( t.getCause() != null )
        {
            StackTraceElement [] causeList = t.getCause ().getStackTrace ();
            message.append ( Tools.CRLF );
            message.append ( "CAUSE---------------START" );
            message.append ( Tools.getCompleteMessage ( causeList ) ); 
            message.append ( "CAUSE---------------END" );
        }
        
        DevError [] errors = t.errors;
        if ( errors != null && errors.length !=0 )
        {
            message.append ( Tools.CRLF );
            message.append ( "ERRORS---------------START" );
            message.append ( Tools.CRLF );
            for (  int i = 0 ; i < errors.length ; i ++ )
            {
                DevError error = errors [ i ];
                message.append ( "desc/"+error.desc+"/origin/"+error.origin+"/reason/"+error.reason );
                message.append ( Tools.CRLF );
            }
            message.append ( "ERRORS---------------END" );
        }
        
        return message.toString ();
    }
}
