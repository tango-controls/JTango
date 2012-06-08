//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  DefaultLogger.
//						(Claisse Laurent) - 5 juil. 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.2  2006/07/18 15:17:34  ounsy
// added a file-changing mechanism to change log files when the day changes
//
// Revision 1.1  2006/06/16 09:24:03  ounsy
// moved from the TdbArchiving project
//
// Revision 1.3  2006/06/15 15:15:30  ounsy
// padded the dates with "0" if they're too short
//
// Revision 1.2  2006/06/13 13:30:04  ounsy
// minor changes
//
// Revision 1.1  2006/06/08 08:34:49  ounsy
// creation
//
// Revision 1.2  2005/11/29 18:28:12  chinkumo
// no message
//
// Revision 1.1.2.2  2005/09/14 15:41:32  chinkumo
// Second commit !
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================
package fr.soleil.TangoArchiving.ArchivingTools.Diary;

import java.io.IOException;
import java.io.PrintWriter;

import fr.soleil.TangoArchiving.ArchivingTools.Tools.DateHeure;

public class DefaultLogger extends AbstractLogger
{
    private DateHeure timeOfLastLog;
    private String archiver;
    private String path;
    
    public DefaultLogger ( String _archiver , String _path )
    {
        super();

        this.archiver = _archiver;
        this.path = _path;
        super.setTraceLevel( ILogger.LEVEL_DEBUG );

        try
        {
            super.initDiaryWriter( _path , _archiver );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        
        DateHeure now = new DateHeure ();
        this.setTimeOfLastLog ( now );
    }

    /* (non-Javadoc)
     * @see bensikin.logs.AbstractLogger#getDecoratedLog(java.lang.String, int)
     */
    protected String getDecoratedLog ( String msg , int level )
    {
        switch ( level )
        {
            case ILogger.LEVEL_CRITIC:
                msg = "CRITIC: " + msg;
                break;

            case ILogger.LEVEL_ERROR:
                msg = "ERROR: " + msg;
                break;

            case ILogger.LEVEL_WARNING:
                msg = "WARNING: " + msg;
                break;

            case ILogger.LEVEL_INFO:
                msg = "INFO: " + msg;
                break;

            case ILogger.LEVEL_DEBUG:
                msg = "DEBUG: " + msg;
                break;

            default :

                break;
        }


        return msg;
    }

    /* (non-Javadoc)
     * @see bensikin.logs.AbstractLogger#addToDiary(int, java.lang.Object)
     */
    protected void addToDiary ( int level , Object o ) throws Exception
    {
        try
        {
            if ( o instanceof String )
            {
                String msg = ( String ) o;
                msg = this.getDecoratedDiaryEntry( level , msg );
                write( this.writer , msg , true );
            }
            else if ( o instanceof Exception )
            {
                Exception e = ( Exception ) o;
                e.printStackTrace();

                write ( this.writer , "    " + e.toString() , true );
                StackTraceElement[] stack = e.getStackTrace();
                for ( int i = 0 ; i < stack.length ; i++ )
                {
                    write ( this.writer , "        at " + stack[ i ].toString() , true );
                }
            }
            else
            {
                //System.out.println ( "DefaultLogger/addToDiary/other/"+o.getClass().toString()+"/" );
            }
        }
        catch ( Exception e )
        {
            //we catch the exception and only trace it so that we don't get a failure message on an action
            //if the action result logs fail.
            e.printStackTrace();
        }
    }


    /**
     * @param msg
     * @return 6 juil. 2005
     */
    private String getDecoratedDiaryEntry ( int level , String msg )
    {
        String criticity = ( String ) levels.get( new Integer( level ) );
        
        java.sql.Timestamp now = new java.sql.Timestamp( System.currentTimeMillis() );
        String time = now.toString();
        if ( time.length () == 21 )
        {
            time += "00";
        }
        if ( time.length () == 22 )
        {
            time += "0";
        }
        
        StringBuffer buff = new StringBuffer (); 
        
        buff.append ( time );
        buff.append ( " " );
        buff.append ( criticity );
        buff.append ( " --> " );
        buff.append ( msg );

        return buff.toString ();
    }

    /* (non-Javadoc)
     * @see bensikin.logs.AbstractLogger#log(java.lang.String)
     */
    protected void log ( String msg )
    {
        System.out.println ( msg );
    }

    /* (non-Javadoc)
     * @see bensikin.logs.ILogger#close()
     */
    public void close ()
    {
        if ( this.writer != null )
        {
            this.writer.close();
        }
    }
    
    /**
     * @param pw
     * @param s
     * @param hasNewLine
     * @throws Exception 8 juil. 2005
     */
    public static void write ( PrintWriter pw , String s , boolean hasNewLine ) throws Exception
    {
        if ( hasNewLine )
        {
            pw.println( s );
        }
        else
        {
            pw.print( s );
        }

        pw.flush();
    }

    protected void changeDiaryFileIfNecessary() throws IOException 
    {
        DateHeure timeOfLastLog = this.getTimeOfLastLog ();
        DateHeure now = new DateHeure ();
        
        if ( this.needsNewFile ( timeOfLastLog , now ) )
        {
            this.close ();
            super.initDiaryWriter( this.path , this.archiver );
        }
        
        this.setTimeOfLastLog ( now );
    }

    private boolean needsNewFile(DateHeure timeOfLastLog2, DateHeure now) 
    {
        String separator = "|";
        
        /*String timeOfLastLogToTheMinute = timeOfLastLog2.getJour() + separator + timeOfLastLog2.getHeure () + separator +  timeOfLastLog2.getMinute ();
        String nowToTheMinute = now.getJour() + separator + now.getHeure () + separator +  now.getMinute ();
        boolean areTheSameMinute =  nowToTheMinute.equals( timeOfLastLogToTheMinute );
        
        System.out.println ( "DefaultLogger/needsNewFile/now/"+now+"/timeOfLastLog/"+timeOfLastLog+"/timeOfLastLogToTheMinute/"+timeOfLastLogToTheMinute+"/nowToTheMinute/"+nowToTheMinute+"/areTheSameMinute/"+areTheSameMinute );
        
        return ! areTheSameMinute;*/
        
        String timeOfLastLogToTheDay = timeOfLastLog2.getAnnee() + separator + timeOfLastLog2.getMois() +separator + timeOfLastLog2.getJour();
        String nowToTheDay = now.getAnnee() + separator + now.getMois() +separator + now.getJour();
        boolean areTheSameDay =  nowToTheDay.equals( timeOfLastLogToTheDay );
        
        //System.out.println ( "DefaultLogger/needsNewFile/now/"+now+"/timeOfLastLog/"+timeOfLastLog+"/timeOfLastLogToTheDay/"+timeOfLastLogToTheDay+"/nowToTheDay/"+nowToTheDay+"/areTheSameDay/"+areTheSameDay );
        
        return ! areTheSameDay;
    }

    /**
     * @return Returns the timeOfLastLog.
     */
    public DateHeure getTimeOfLastLog() {
        return this.timeOfLastLog;
    }

    /**
     * @param timeOfLastLog The timeOfLastLog to set.
     */
    public void setTimeOfLastLog(DateHeure timeOfLastLog) {
        this.timeOfLastLog = timeOfLastLog;
    }

}
