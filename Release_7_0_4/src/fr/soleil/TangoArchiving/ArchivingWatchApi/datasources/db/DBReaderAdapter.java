package fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingApi.DataBaseApi;
import fr.soleil.TangoArchiving.ArchivingManagerApi.ArchivingManagerApi;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.DateHeure;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.DbData;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.SamplingType;
import fr.soleil.TangoArchiving.ArchivingWatchApi.ArchivingWatch;
import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warnable;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ModeData;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * An implementation that really loads data from the HDB physical database
 * @author CLAISSE 
 */
public abstract class DBReaderAdapter implements IDBReader 
{
    protected DataBaseApi database;
    protected ArchivingManagerApi manager;
    protected boolean isReady = false;
    
    protected int cpt = 0;

    public DBReaderAdapter ()
    {
        this.manager = new ArchivingManagerApi ();
    }
    
    /* (non-Javadoc)
     * @see archwatch.datasources.in.IArchivedAttributesReader#getArchivedAttributes()
     */
    public Hashtable getArchivedAttributes() throws DevFailed 
    {
        //throwDummyException(3);
        this.openConnectionIfNecessary ( false );
        
        String [] currentArchivedAtt = null;
        try
        {
            currentArchivedAtt = database.getCurrentArchivedAtt (true);
        }
        catch ( Throwable e )
        {
            Tools.trace ( "DBReaderAdapter/getArchivedAttributes/problem calling getCurrentArchivedAtt(): " , e , Warnable.LOG_LEVEL_ERROR );
            Tools.throwDevFailed ( e );
        }
        if ( currentArchivedAtt == null )
        {
            return null;    
        }

        int numberOfAttributes = currentArchivedAtt.length;        
        Hashtable ret = new Hashtable ( numberOfAttributes );
        for ( int i = 0 ; i < numberOfAttributes ; i ++ )
        {
            String completeName = currentArchivedAtt [ i ];
            ret.put ( completeName , new ModeData () ); //The ModeData will be loaded when necessary ie. at the beginning of each step
        }

        return ret;
    }

    /**
     * @param completeName
     * @return
     * @throws ArchivingException
     */
    public ModeData getModeDataForAttribute ( String completeName ) throws DevFailed  
    {
        this.openConnectionIfNecessary ( false );
        
        ModeData ret = new ModeData ();
        
        try
        {
            String archiver = this.getArchiverForAttribute ( completeName );
            ret.setArchiver ( archiver );
        }
        catch ( Throwable e )
        {
            Tools.trace ( "DBReaderAdapter/getModeDataForAttribute/problem calling getArchiverForAttribute ( "+ completeName +" ): " , e , Warnable.LOG_LEVEL_ERROR );
            Tools.throwDevFailed ( e );
        }
        
        try
        {
            Mode mode = this.getModeForAttribute ( completeName );
            ret.setMode ( mode );
            
            if ( mode.getModeP () == null )
            {
                Tools.trace ( "DBReaderAdapter/getModeDataForAttribute/mode.getModeP() == null for attribute|"+ completeName +"|" , Warnable.LOG_LEVEL_ERROR );
                ret.setIncomplete ( true );
            }
        }
        catch ( Throwable e )
        {
            Tools.trace ( "DBReaderAdapter/getModeDataForAttribute/problem calling getModeForAttribute ( "+ completeName +" ): " , e , Warnable.LOG_LEVEL_ERROR );
            Tools.throwDevFailed ( e );
        }
        
        return ret;
    }
    /*
     * //CLA 22/03/07 The DevFailed should be thrown
     * public ModeData getModeDataForAttribute ( String completeName ) throws DevFailed 
    {
        this.openConnectionIfNecessary ( false );
        
        ModeData ret = new ModeData ();
        
        try
        {
            String archiver = this.getArchiverForAttribute ( completeName );
            ret.setArchiver ( archiver );
        }
        catch ( Throwable e )
        {
            Tools.trace ( "DBReaderAdapter/getModeDataForAttribute/problem calling getArchiverForAttribute ( "+ completeName +" ): " , e , Warnable.LOG_LEVEL_ERROR );
            ret.setIncomplete ( true );
        }
        
        try
        {
            Mode mode = this.getModeForAttribute ( completeName );
            ret.setMode ( mode );
            
            if ( mode.getModeP () == null )
            {
                Tools.trace ( "DBReaderAdapter/getModeDataForAttribute/mode.getModeP() == null for attribute|"+ completeName +"|" , Warnable.LOG_LEVEL_ERROR );
                ret.setIncomplete ( true );
            }
        }
        catch ( Throwable e )
        {
            Tools.trace ( "DBReaderAdapter/getModeDataForAttribute/problem calling getModeForAttribute ( "+ completeName +" ): " , e , Warnable.LOG_LEVEL_ERROR );
            //Tools.throwDevFailed ( e );
            ret.setIncomplete ( true );
        }
        
        return ret;
    }*/

    /**
     * @param completeName
     * @return
     * @throws ArchivingException
     */
    private String getArchiverForAttribute(String completeName) throws DevFailed 
    {
        //System.out.println ( "getArchiverForAttribute/completeName|"+completeName+"|" );
        //throwDummyException(completeName);
        
        this.openConnectionIfNecessary ( false );
        
        String device = null;
        
        try
        {
            device = database.getDeviceInCharge ( completeName );
        }
        catch ( Exception e )
        {
            Tools.trace ( "DBReaderAdapter/getArchiverForAttribute/problem calling getDeviceInCharge ( "+ completeName +" ): " , e , Warnable.LOG_LEVEL_ERROR );
            Tools.throwDevFailed ( e );
        }
        
        return device;
    }

    /**
     * @param completeName
     * @return
     * @throws ArchivingException
     */
    public Mode getModeForAttribute(String completeName) throws DevFailed 
    {
        //throwDummyException(completeName);
        
        this.openConnectionIfNecessary ( false );
        
        Mode mode = null;
        try
        {
            mode = database.getCurrentArchivingMode ( completeName , this.isHistoric () );
        }
        catch ( Throwable e )
        {
            Tools.trace ( "DBReaderAdapter/getModeForAttribute/problem calling getCurrentArchivingMode ( "+ completeName +" , true ): " , e , Warnable.LOG_LEVEL_ERROR );
            Tools.throwDevFailed ( e );
        }
        return mode;
    }
    
    public void closeConnection () throws DevFailed
    {
        try
        {
            this.manager.closeDatabase ( this.isHistoric () );
        }
        catch ( Exception e )
        {
            Tools.trace ( "DBReaderAdapter/closeConnection/problem calling close ()" , e , Warnable.LOG_LEVEL_ERROR );
            Tools.throwDevFailed ( e );
        }
        
        this.isReady = false;
    }

    
    
    /* (non-Javadoc)
     * @see archwatch.datasource.in.IArchivedAttributesReader#getRecordCount(java.lang.String)
     */
    public int getRecordCount(String completeName) throws DevFailed 
    {
        this.openConnectionIfNecessary ( false );
        
        int res = 0;
        try
        {
            res = database.getAttRecordCount ( completeName );
        }
        catch ( Exception e )
        {
            Tools.trace ( "DBReaderAdapter/getRecordCount/problem calling getAttRecordCount ( "+ completeName +" ): " , e , Warnable.LOG_LEVEL_ERROR );
            Tools.throwDevFailed ( e );
        }
        return res;
    }
    
    public Timestamp getTimeOfLastInsert(String completeName) throws DevFailed 
    {
        this.openConnectionIfNecessary ( false );
        
        Timestamp ret = null;
        try
        {
            ret = database.getTimeOfLastInsert ( completeName , true );
        }
        catch ( ArchivingException ae )
        {
            if ( ae.getCause () != null && ae.getCause () instanceof SQLException )
            {
                SQLException sqlex = (SQLException) ae.getCause ();
                String state = sqlex.getSQLState ();
                int code = sqlex.getErrorCode ();
                Tools.trace ( "DBReaderAdapter/getTimeOfLastInsert/the table doesn't exist for attribute|"+ completeName +"|SQL state|"+state+"|SQL code|"+code+"|" , ae , Warnable.LOG_LEVEL_INFO );
                
                //the table doesn't exist
                return null;
            }
            else
            {
                Tools.trace ( "DBReaderAdapter/getTimeOfLastInsert/problem calling getAttDataLast_n ( "+ completeName +" ): " , ae , Warnable.LOG_LEVEL_ERROR );    
                Tools.throwDevFailed ( ae );
            }
        }
        catch ( Exception e )
        {
            Tools.trace ( "DBReaderAdapter/getTimeOfLastInsert/problem calling getAttDataLast_n ( "+ completeName +" ): " , e , Warnable.LOG_LEVEL_ERROR );
            Tools.throwDevFailed ( e );
        }
        
        return ret;
    }
    
    /* (non-Javadoc)
     * @see archwatch.datasource.in.IArchivedAttributesReader#now()
     */
    public Timestamp now () throws DevFailed 
    {
        return new Timestamp ( System.currentTimeMillis () );
    }
    /*public Timestamp now () throws DevFailed 
    {
        //throwDummyException();
        
        this.openConnectionIfNecessary ( false );
        
        try
        {
            return database.now ();
        }
        catch ( Exception e )
        {
            Tools.trace ( "DBReaderAdapter/now/problem calling now ()" , e , Warnable.LOG_LEVEL_ERROR );
            Tools.throwDevFailed ( e );
        }
        return null;
    }*/

    /* (non-Javadoc)
     * @see archwatch.datasource.in.IArchivedAttributesReader#getRecordCount(java.lang.String, int)
     */
    private void throwDummyException ( int threshold ) throws DevFailed 
    {
        System.out.println ( "CLA/throwDummyException/threshold/"+threshold+ "/cpt/"+cpt);
        cpt++;
        if ( cpt>=threshold)
        {
            Exception e = new Exception ( "CLA/dummy exception throwDummyException-----------------------------------------------------------------------------" );
            Tools.throwDevFailed ( e );    
        }
    }
    
    private void alwaysThrowDummyException () throws DevFailed 
    {
        Exception e = new Exception ( "CLA/dummy exception alwaysThrowDummyException-----------------------------------------------------------------------------" );
        Tools.throwDevFailed ( e );    
    }
    
    public int getRecordCount(String completeName, int period) throws DevFailed 
    {
        //throwDummyException ();
        //throwDummyException ( completeName );
        
        //Tools.trace ( "DBReaderAdapter/completeName|" + completeName + "|period|" + period , Warnable.LOG_LEVEL_DEBUG );
        
        this.openConnectionIfNecessary ( false );
        
        Timestamp now = this.now ();
        DateHeure after = new DateHeure ( now );
        DateHeure before = (DateHeure) after.clone();
        before.addMilliseconde ( -period );
        
        String after_s = now.toString ();
        String before_s = new Timestamp ( before.toDate().getTime() ).toString();
        //Tools.trace ( "DBReaderAdapter/now|" + after_s + "|now-period|" + before_s , Warnable.LOG_LEVEL_DEBUG );
        
        String [] argin = new String [ 3 ];
        argin [ 0 ] = completeName;
        argin [ 1 ] = before_s;
        argin [ 2 ] = after_s;
        
        try
        {
            DbData dbData = database.getAttDataBetweenDates ( argin , SamplingType.getSamplingType ( SamplingType.ALL ) );
            if ( dbData == null || dbData.getData() == null )
            {
                Tools.trace ( "DBReaderAdapter/case ( dbData == null || dbData.getData() == null )" , Warnable.LOG_LEVEL_DEBUG );
                return 0;
            }
            
            return dbData.getData().length;
        }
        catch ( ArchivingException ae )
        {
            if ( ae.getCause () != null && ae.getCause () instanceof SQLException )
            {
                SQLException sqlex = (SQLException) ae.getCause ();
                String state = sqlex.getSQLState ();
                int code = sqlex.getErrorCode ();
                //Tools.trace ( "DBReaderAdapter/getRecordCount/the table doesn't exist for attribute|"+ completeName +"|SQL state|"+state+"|SQL code|"+code+"|" , ae , Warnable.LOG_LEVEL_INFO );
                
                //the table doesn't exist             
                return 0;
            }
            else
            {
                Tools.trace ( "DBReaderAdapter/getRecordCount/problem calling getAttDataBetweenDates ( " + completeName + " , " + before_s + " , " + after_s + " )" , ae , Warnable.LOG_LEVEL_ERROR );
                Tools.throwDevFailed ( ae );
            }
        }
        catch ( Exception e )
        {
            Tools.trace ( "DBReaderAdapter/getRecordCount/problem calling getAttDataBetweenDates ( " + completeName + " , " + before_s + " , " + after_s + " )" , e , Warnable.LOG_LEVEL_ERROR );
            Tools.throwDevFailed ( e );
        }
        return 0;
    }

    private void throwDummyException(String completeName) throws DevFailed 
    {
        if ( completeName.equals ( "tango/tangotest/1/short_scalar" ) )
        {
            Exception e = new Exception ( "CLA/dummy exception for short_scalar-----------------------------------------------------------------------------" );
            Tools.throwDevFailed ( e );    
        }    
    }

    /* (non-Javadoc)
     * @see archwatch.datasource.in.IArchivedAttributesReader#isScalar(java.lang.String)
     */
    public boolean isScalar(String completeName) throws DevFailed 
    {
        this.openConnectionIfNecessary ( false );
        
        try
        {
            int format = database.getAttDataFormat ( completeName );
            boolean ret = (format == AttrDataFormat._SCALAR);
            
            return ret;
        }
        catch ( Exception e )
        {
            Tools.trace ( "DBReaderAdapter/isScalar/problem calling getAttDataFormat ( " + completeName + " )" , e , Warnable.LOG_LEVEL_ERROR );
            Tools.throwDevFailed ( e );
        }
        
        return false;
    }
    
    /* (non-Javadoc)
     * @see archwatch.datasource.in.IArchivedAttributesReader#isScalar(java.lang.String)
     */
    public boolean isScalarOrSpectrum ( String completeName ) throws DevFailed 
    {
        //throwDummyException(completeName);
        
        this.openConnectionIfNecessary ( false );
        
        try
        {
            int format = database.getAttDataFormat ( completeName );
            boolean ret = (format == AttrDataFormat._SCALAR) || (format == AttrDataFormat._SPECTRUM);
            
            return ret;
        }
        catch ( Exception e )
        {
            Tools.trace ( "DBReaderAdapter/isScalarOrSpectrum/problem calling getAttDataFormat ( " + completeName + " )" , e , Warnable.LOG_LEVEL_ERROR );
            Tools.throwDevFailed ( e );
        }
        
        return false;
    }

    /* (non-Javadoc)
     * @see archwatch.datasources.db.IArchivedAttributesReader#getArchiverLoad(java.lang.String, int)
     */
    public int getArchiverLoad(String archiverName, int loadType) throws DevFailed
    {        
        this.openConnectionIfNecessary ( false );
        
        switch ( loadType )
        {
            case IDBReader.LOAD_ALL:
                try
                {
                    return ArchivingManagerApi.get_charge ( archiverName );
                }
                catch ( Exception e )
                {
                    Tools.trace ( "DBReaderAdapter/getArchiverLoad/problem calling get_charge ( " + archiverName + " )" , e , Warnable.LOG_LEVEL_ERROR );
                    Tools.throwDevFailed ( e );
                }
            case IDBReader.LOAD_SCALAR:
                try
                {
                    return ArchivingManagerApi.get_scalar_charge ( archiverName );
                }
                catch ( Exception e )
                {
                    Tools.trace ( "DBReaderAdapter/getArchiverLoad/problem calling get_scalar_charge ( " + archiverName + " )" , e , Warnable.LOG_LEVEL_ERROR );
                    Tools.throwDevFailed ( e );
                }
            case IDBReader.LOAD_SPECTRUM:
                try
                {
                    return ArchivingManagerApi.get_spectrum_charge ( archiverName );
                }
                catch ( Exception e )
                {
                    Tools.trace ( "DBReaderAdapter/getArchiverLoad/problem calling get_spectrum_charge ( " + archiverName + " )" , e , Warnable.LOG_LEVEL_ERROR );
                    Tools.throwDevFailed ( e );
                }
            case IDBReader.LOAD_IMAGE:
                try
                {
                    return ArchivingManagerApi.get_image_charge ( archiverName );
                }
                catch ( Exception e )
                {
                    Tools.trace ( "DBReaderAdapter/getArchiverLoad/problem calling get_image_charge ( " + archiverName + " )" , e , Warnable.LOG_LEVEL_ERROR );
                    Tools.throwDevFailed ( e );
                }
            default:
                Tools.throwDevFailed ( new IllegalArgumentException ( "Expected LOAD_ALL(0), LOAD_SCALAR(1), LOAD_SPECTRUM(2), LOAD_IMAGE(3) got " + loadType + " instead." ) );
        }
        
        return -1;
    }

    /* (non-Javadoc)
     * @see archwatch.datasources.db.IArchivedAttributesReader#isAlive(java.lang.String)
     */
    public boolean isAlive(String archiverName) throws DevFailed 
    {
        this.openConnectionIfNecessary ( false );
        
        try
        {
            return ArchivingManagerApi.deviceLivingTest ( archiverName );
        }
        catch ( Exception ae )
        {
            Tools.trace ( "DBReaderAdapter/isAlive/problem calling deviceLivingTest ( " + archiverName + " )" , ae , Warnable.LOG_LEVEL_INFO );
            return false;
        }
    }

    /* (non-Javadoc)
     * @see archwatch.datasources.db.IArchivedAttributesReader#getStatus(java.lang.String)
     */
    public String getDeviceStatus(String attributeName) throws DevFailed 
    {
        this.openConnectionIfNecessary ( false );
        
        try
        {
            return ArchivingManagerApi.getDeviceStatus ( attributeName );
        }
        catch ( ArchivingException ae )
        {
            //ae.printStackTrace ();
            Tools.trace ( "DBReaderAdapter/getDeviceStatus/problem calling getDeviceStatus ( " + attributeName + " )" , ae , Warnable.LOG_LEVEL_INFO );
            return "DOES NOT ANSWER";
        }
    }

    public boolean isDBConnectionAlive () 
    {
        try
        {
            this.openConnectionIfNecessary ( false );
            database.minimumRequest ();
        }
        catch ( Throwable e )
        {
            Tools.trace ( "DBReaderAdapter/isDBConnectionAlive/The minimum request failed!!! Trying to reopen the DB connection." , e , Warnable.LOG_LEVEL_ERROR );
            
            try
            {
                this.openConnectionIfNecessary ( true );
                database.minimumRequest ();
            }
            catch ( Throwable e1 )
            {
                Tools.trace ( "DBReaderAdapter/isDBConnectionAlive/The DB connection can't be reopened!!!" , e1 , Warnable.LOG_LEVEL_FATAL );
                return false;    
            }    
        }
        
        return true;
        //return false;
    }

    public boolean isLastValueNull(String completeName) throws DevFailed 
    {
        this.openConnectionIfNecessary ( false );
        //throwDummyException (4);
        //System.out.println ( "DBReaderAdapter/isLastValueNull/completeName|"+completeName+"|" );
        try
        {
            //System.out.println ( "DBReaderAdapter/isLastValueNull/before database.isLastDataNull" );
            boolean ret = database.isLastDataNull ( completeName );
            //System.out.println ( "DBReaderAdapter/isLastValueNull/after database.isLastDataNull/ret|"+ret+"|" );
            return ret;
        }
        catch ( ArchivingException ae )
        {
            //System.out.println ( "DBReaderAdapter/isLastValueNull/ArchivingException" );
            
            if ( ae.getCause () != null && ae.getCause () instanceof SQLException )
            {
                SQLException sqlex = (SQLException) ae.getCause ();
                String state = sqlex.getSQLState ();
                int code = sqlex.getErrorCode ();
                Tools.trace ( "DBReaderAdapter/isLastValueNull/the table doesn't exist for attribute|"+ completeName +"|SQL state|"+state+"|SQL code|"+code+"|" , ae , Warnable.LOG_LEVEL_INFO );
                
                //the table doesn't exist
                return true;
            }
            else
            {
                Tools.trace ( "DBReaderAdapter/isLastValueNull/problem calling getAttDataLast_n ( "+ completeName +" ): " , ae , Warnable.LOG_LEVEL_ERROR );    
                Tools.throwDevFailed ( ae );
            }
        }
        catch ( Exception e )
        {
            //System.out.println ( "DBReaderAdapter/isLastValueNull/Exception" );
            
            Tools.trace ( "DBReaderAdapter/isLastValueNull/problem calling getAttDataLast_n ( "+ completeName +" ): " , e , Warnable.LOG_LEVEL_ERROR );
            Tools.throwDevFailed ( e );
        }
        
        //System.out.println ( "DBReaderAdapter/isLastValueNull/Shouldn't be here!!!!!!!" );
        return true;//this line is never reached since throwDevFailed ALWAYS throws even if the compilator doesn't know that
    }
    
    public void openConnectionIfNecessary ( boolean forceOpening ) throws DevFailed
    {
        //alwaysThrowDummyException ();
        
        if ( ( !this.isReady ) || forceOpening )
        {
            String HDBuser = ArchivingWatch.getHDBuser();
            String HDBpassword = ArchivingWatch.getHDBpassword();
            String TDBuser = ArchivingWatch.getTDBuser();
            String TDBpassword = ArchivingWatch.getTDBpassword();;

            String user = this.isHistoric () ? HDBuser : TDBuser;
            String pwd = this.isHistoric () ? HDBpassword : TDBpassword;
            
            Tools.trace ( "DBReaderAdapter/openConnection/trying to connect with parameters ( "+ HDBuser + " , " + HDBpassword + " , "  + TDBuser + " , "  + TDBpassword  +" ): " , Warnable.LOG_LEVEL_DEBUG );
            
            this.closeConnection ();
            
            try
            {
                //ArchivingManagerApi.ArchivingConfigure( HDBuser , HDBpassword , TDBuser , TDBpassword );
                this.manager.ArchivingConfigure( user , pwd , this.isHistoric () );
            }
            catch ( Exception e )
            {
                Tools.trace ( "DBReaderAdapter/openConnection/problem calling ArchivingManagerApi.ArchivingConfigure ( "+ HDBuser + " , " + HDBpassword + " , "  + TDBuser + " , "  + TDBpassword  +" ): " , e , Warnable.LOG_LEVEL_FATAL );
                Tools.throwDevFailed ( e );
            }
            
            database = this.manager.getDataBase( this.isHistoric () );
            database.setAutoConnect ( false );
            
            this.isReady = true;
        }
    }

    protected abstract boolean isHistoric ();
}
