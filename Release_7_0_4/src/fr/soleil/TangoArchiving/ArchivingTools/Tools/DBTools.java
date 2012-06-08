package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import java.sql.Timestamp;

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingTools.Diary.ILogger;

/**
 * 
 * @author CLAISSE 
 */
public class DBTools 
{
    public static final String CRLF = System.getProperty( "line.separator" );
    public static final String TANGO_SEPARATOR = "/";
    private static Warnable warnable;
    
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
    
    public static synchronized void trace ( String msg , int level )
    {
        Warnable warnable = getWarnable ();
        if ( warnable == null )
        {
            DBTools.dummyTrace ( msg , level );
            return;
        }
        
        warnable.trace ( msg , level );
    }
    
    public static synchronized void trace ( String msg , Throwable t , int level )
    {
        Warnable warnable = getWarnable ();
        if ( warnable == null )
        {
            DBTools.dummyTrace ( msg , t , level );
        }
        else
        {
            warnable.trace ( msg , t , level );
        }
    }

    /**
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
     * @param msg
     * @param level
     */
    private static void dummyTrace(String msg, int level) 
    {
        System.out.println ( msg );
    }

    /**
     * @param exception
     * @return
     * @throws DevFailed
     */
    public static void throwDevFailed(Throwable exception) throws DevFailed 
    {
        DevFailed devFailed = getAsDevFailed ( exception );
        throw devFailed;
    }
    
    /**
     * @param exception
     * @return
     * @throws DevFailed
     */
    public static DevFailed getAsDevFailed(Throwable exception) throws DevFailed 
    {
        DevFailed devFailed = new DevFailed ();
        devFailed.initCause ( exception );
        devFailed.setStackTrace ( exception.getStackTrace () );
        return devFailed;
    }
    
    /**
     * @param t
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
            message.append ( DBTools.CRLF );
            
            for ( int i = 0 ; i < steList.length ; i ++ )
            {
                StackTraceElement ste = steList [ i ];
                message.append ( ste.toString () );
                message.append ( DBTools.CRLF );
            }
        }
        
        return message.toString ();
    }
    
    public static String getCompleteMessage (DevFailed t) 
    {
        StringBuffer message = new StringBuffer ();
        
        StackTraceElement [] steList = t.getStackTrace ();
        message.append ( DBTools.getCompleteMessage ( steList ) );
        
        if ( t.getCause() != null )
        {
            StackTraceElement [] causeList = t.getCause ().getStackTrace ();
            message.append ( DBTools.CRLF );
            message.append ( "CAUSE---------------START" );
            message.append ( DBTools.getCompleteMessage ( causeList ) ); 
            message.append ( "CAUSE---------------END" );
        }
        
        DevError [] errors = t.errors;
        if ( errors != null && errors.length !=0 )
        {
            message.append ( DBTools.CRLF );
            message.append ( "ERRORS---------------START" );
            message.append ( DBTools.CRLF );
            for (  int i = 0 ; i < errors.length ; i ++ )
            {
                DevError error = errors [ i ];
                message.append ( "desc/"+error.desc+"/origin/"+error.origin+"/reason/"+error.reason );
                message.append ( DBTools.CRLF );
            }
            message.append ( "ERRORS---------------END" );
        }
        
        return message.toString ();
    }

    /**
     * @return Returns the warnable.
     */
    public static Warnable getWarnable() {
        return warnable;
    }

    /**
     * @param warnable The warnable to set.
     */
    public static void setWarnable(Warnable warnable) {
        DBTools.warnable = warnable;
    }

    public static boolean areTimestampsEqual ( long newTime , long oldTime, ILogger logger ) 
    {
        try
        {
            if ( newTime == oldTime )
            {
                return true;
            }
            
            Timestamp oldTimeT = new Timestamp ( oldTime );
            Timestamp newTimeT = new Timestamp ( newTime );
            if ( newTimeT.compareTo ( oldTimeT ) == 0 )
            {
                return true;
            }
            
            String oldTimeS = oldTimeT.toString ();
            String newTimeS = newTimeT.toString ();
            
            //System.out.println ( "CLA/areTimestampsEqual/BEFORE/oldTimeS/"+oldTimeS+"/newTimeS/"+newTimeS+"/" );
            
            int length = 23;
            if ( oldTimeS.length () > length )
            {
                oldTimeS = oldTimeS.substring ( 0 , length );
                
            }
            if ( newTimeS.length () > length  )
            {
                newTimeS = newTimeS.substring ( 0 , length );
            }
            
            boolean ret = oldTimeS.equals ( newTimeS );
            
            //System.out.println ( "CLA/areTimestampsEqual/AFTER/oldTimeS/"+oldTimeS+"/newTimeS/"+newTimeS+"/ret/"+ret+"/" );
            return ret;
        }
        catch ( Exception e )
        {
            e.printStackTrace ();
            
            String message = "DBTools/areTimestampsEqual/Exception!";
            logger.trace ( ILogger.LEVEL_WARNING , message );
            logger.trace ( ILogger.LEVEL_WARNING , e );
            
            return false;
        }
    }
    

    
}
