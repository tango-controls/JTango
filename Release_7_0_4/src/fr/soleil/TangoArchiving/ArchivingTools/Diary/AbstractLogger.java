//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  AbstractLogger.
//						(Claisse Laurent) - 5 juil. 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.3  2006/11/20 09:31:54  ounsy
// the diary path is no longer added "/diary"
//
// Revision 1.2  2006/07/18 15:17:34  ounsy
// added a file-changing mechanism to change log files when the day changes
//
// Revision 1.1  2006/06/16 09:24:03  ounsy
// moved from the TdbArchiving project
//
// Revision 1.2  2006/06/13 13:30:04  ounsy
// minor changes
//
// Revision 1.1  2006/06/08 08:34:49  ounsy
// creation
//
// Revision 1.3  2006/05/19 15:05:29  ounsy
// minor changes
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.StringTokenizer;

public abstract class AbstractLogger implements ILogger
{
    protected int traceLevel;
    protected PrintWriter writer;
    protected Hashtable levels;
    protected Hashtable reverseLevels;

    /* (non-Javadoc)
     * @see bensikin.logs.ILogger#getTraceLevel()
     */
    public int getTraceLevel ()
    {
        return traceLevel;
    }

    /* (non-Javadoc)
     * @see bensikin.logs.ILogger#setTraceLevel(int)
     */
    public void setTraceLevel ( int level )
    {
        //System.out.println ( "AbstractLogger/setTraceLevel/level/"+level+"/" );
        this.traceLevel = level;
    }
    
    /* (non-Javadoc)
     * @see bensikin.logs.ILogger#setTraceLevel(java.lang.String)
     */
    public void setTraceLevel ( java.lang.String _level )
    {
        //System.out.println ( "AbstractLogger/setTraceLevel/level/"+level+"/" );
        int level = ILogger.LEVEL_DEBUG;
        
        try
        {
            Integer _levelI = (Integer) reverseLevels.get ( _level ); 
            level = _levelI.intValue ();
        }
        catch ( Exception e )
        {
            e.printStackTrace ();
        }
        
        this.traceLevel = level;
    }

    /* (non-Javadoc)
     * @see bensikin.logs.ILogger#initDiaryWriter(java.lang.String)
     */
    public void initDiaryWriter ( String path , String archiver ) throws IOException
    {
        String refactoredArchiverName = this.refactorArchiverName ( archiver );
        
        levels = new Hashtable( 5 );
        levels.put( new Integer( LEVEL_CRITIC ) , CRITIC );
        levels.put( new Integer( LEVEL_ERROR ) , ERROR );
        levels.put( new Integer( LEVEL_WARNING ) , WARNING );
        levels.put( new Integer( LEVEL_INFO ) , INFO );
        levels.put( new Integer( LEVEL_DEBUG ) , DEBUG );

        reverseLevels = new Hashtable( 5 );
        reverseLevels.put( CRITIC , new Integer( LEVEL_CRITIC ) );
        reverseLevels.put( ERROR , new Integer( LEVEL_ERROR ) );
        reverseLevels.put( WARNING , new Integer( LEVEL_WARNING ) );
        reverseLevels.put( INFO , new Integer( LEVEL_INFO ) );
        reverseLevels.put( DEBUG , new Integer( LEVEL_DEBUG ) );

        //String absp = path + "/diary";
        String absp = path;
        System.out.println ( "absp|"+absp+"|" );
        File f = new File( absp );
        if ( !f.canWrite() )
        {
            System.out.println ( "! canWrite" );
            //boolean b = 
            f.mkdir();
            //System.out.println ( "b/"+b+"/" );
        }
        else
        {
            System.out.println ( "canWrite" );
        }

        java.sql.Date today = new java.sql.Date( System.currentTimeMillis() );
        String date = today.toString();
        
        absp += "/diary_" + refactoredArchiverName + "_" + date; 
        
        /*DateHeure dh = new DateHeure ();
        String time = dh.getHeure()+"_"+dh.getMinute();
        absp += "_" + time;*/
        
        absp += ".log";

        //writer = new PrintWriter ( new FileWriter ( path , true ) );
        writer = new PrintWriter( new FileWriter( absp , true ) );
    }


    private String refactorArchiverName ( String archiver ) 
    {
        StringTokenizer st = new StringTokenizer ( archiver , "/" );
        String ret = "";
        while ( st.hasMoreTokens () )
        {
            ret = st.nextToken (); 
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see bensikin.logs.ILogger#getTraceLevel(java.lang.String)
     */
    public int getTraceLevel ( String level_s )
    {
        Integer lev = ( Integer ) reverseLevels.get( level_s );
        if ( lev == null )
        {
            return LEVEL_DEBUG;
        }

        try
        {
            int ret = lev.intValue();
            return ret;
        }
        catch ( NumberFormatException nfe )
        {
            nfe.printStackTrace();
            return LEVEL_DEBUG;
        }
    }

    /* (non-Javadoc)
     * @see bensikin.logs.ILogger#trace(int, java.lang.Object)
     */
    public void trace ( int level , Object o )
    {
        //System.out.println ( "AbstractLogger/trace/level/"+level+"/this.traceLevel/"+this.traceLevel+"/" );
        
        if ( level <= this.traceLevel )
        {
            try
            {
                this.changeDiaryFileIfNecessary ();
                this.addToDiary( level , o );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }

            if ( o instanceof String )
            {
                String msg = ( String ) o;
                msg = this.getDecoratedLog( msg , level );
                this.log( msg );
            }
        }
    }

    protected abstract void changeDiaryFileIfNecessary () throws IOException;
    
    /**
     * @param msg
     * @param level
     * @return 8 juil. 2005
     */
    protected abstract String getDecoratedLog ( String msg , int level );

    /**
     * @param level
     * @param o
     * @throws Exception 8 juil. 2005
     */
    protected abstract void addToDiary ( int level , Object o ) throws Exception;

    /**
     * @param msg 8 juil. 2005
     */
    protected abstract void log ( String msg );

    /* (non-Javadoc)
     * @see bensikin.logs.ILogger#close()
     */
    public abstract void close ();

}
