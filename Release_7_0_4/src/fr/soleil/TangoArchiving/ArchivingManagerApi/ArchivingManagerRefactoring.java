package fr.soleil.TangoArchiving.ArchivingManagerApi;

import java.util.Vector;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.Database;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.TangoArchiving.ArchivingApi.ConfigConst;
import fr.soleil.TangoArchiving.ArchivingApi.DataBaseApiRefactoring;
import fr.soleil.TangoArchiving.ArchivingApi.GetConf;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst;

public class ArchivingManagerRefactoring 
{
	
	//	Historic Data Base
	private String[] m_hdbExportedArchiverList;
    private String[] m_hdbNotExportedArchiverList;
    private boolean m_hdbFacility = false;
 
    //  Temporary Data Base
	private String[] m_tdbExportedArchiverList;
    private String[] m_tdbNotExportedArchiverList;
    private boolean m_tdbFacility = false;   

    // Constrtuctor
	public ArchivingManagerRefactoring()
	{
		// Nothing to do
	}

	// Accessors
	public boolean getHdbFacility()
	{
		return m_hdbFacility;
	}

	public boolean getTdbFacility()
	{
		return m_tdbFacility;
	}
	public String [] getExportedArchiversList ( boolean historic )
    {
        return historic ? m_hdbExportedArchiverList : m_tdbExportedArchiverList;
    }	
	
	// DataBase Objects creation
	public void ArchivingConfigureHDB()
	{
		System.out.println( "ArchivingConfigureHDB");
		m_hdbFacility = getFacility(true);
	}

	public void ArchivingConfigureTDB() throws ArchivingException
	{
		System.out.println( "ArchivingConfigureTDB");
		m_tdbFacility = getFacility(true);
	}

	
	public void initHdbArchiverList (  ) throws ArchivingException
	{
		initDbArchiverList(true);
	}
	
	public void initTdbArchiverList (  ) throws ArchivingException
	{
		initDbArchiverList(false);
	}
	
	/**
	* The created lists are useful during the start archiving action
	*/ 
	private void initDbArchiverList ( boolean isHistoric ) throws ArchivingException
    {
        System.out.println( "initDbArchiverList/isHistoric/"+isHistoric );
        try
        {
            String m_dbClassDevice = isHistoric ? ConfigConst.HDB_CLASS_DEVICE : ConfigConst.TDB_CLASS_DEVICE;
            
            boolean facility = isHistoric ? m_hdbFacility : m_tdbFacility;
            String [] m_dbExportedArchiverList = isHistoric ? m_hdbExportedArchiverList : m_tdbExportedArchiverList;
            String [] m_dbNotExportedArchiverList;
            String db = isHistoric ? "Hdb" : "Tdb";
            
            Vector runningDevicesListVector = new Vector();
            Vector notRunningDevicesListVector = new Vector();
            
            Database dbase = ApiUtil.get_db_obj ();
            
            //String[] devicesList = dbase.getDevices ( m_dbDevice );
            String[] devicesList = dbase.get_device_exported_for_class ( m_dbClassDevice );
            
            for ( int i = 0 ; i < devicesList.length ; i++ )
            {
                if ( deviceLivingTest(devicesList[ i ]) )
                {
                    runningDevicesListVector.add(( ( facility ) ? "//" + dbase.get_tango_host() + "/" : "" ) + devicesList[ i ]);
                }
                else
                {
                    notRunningDevicesListVector.add(( ( facility ) ? "//" + dbase.get_tango_host() + "/" : "" ) + devicesList[ i ]);
                }
            }
            
            m_dbExportedArchiverList = new String[ runningDevicesListVector.size() ];
            m_dbNotExportedArchiverList = new String[ notRunningDevicesListVector.size() ];
            
            for ( int i = 0 ; i < runningDevicesListVector.size() ; i++ )
            {
                m_dbExportedArchiverList[ i ] = ( String ) runningDevicesListVector.elementAt(i);
                System.out.println(db+"Archiver (EXPORTED) " + m_dbExportedArchiverList[ i ]);
            }
            for ( int i = 0 ; i < notRunningDevicesListVector.size() ; i++ )
            {
                m_dbNotExportedArchiverList[ i ] = ( String ) notRunningDevicesListVector.elementAt(i);
                System.out.println(db+"Archiver (NOT EXPORTED) " + m_dbNotExportedArchiverList[ i ]);
            }
            
            if ( isHistoric )
            {
                m_hdbNotExportedArchiverList = m_dbNotExportedArchiverList;    
                m_hdbExportedArchiverList = m_dbExportedArchiverList;
            }
            else
            {
                m_tdbNotExportedArchiverList = m_dbNotExportedArchiverList;    
                m_tdbExportedArchiverList = m_dbExportedArchiverList;
            }
            
            /*if ( isHistoric )
            {
                for ( int i = 0 ; i < runningDevicesListVector.size() ; i++ )
                {
                    System.out.println("HDBArchiver (EXPORTED)2 " + m_hdbExportedArchiverList[ i ]);
                }
                for ( int i = 0 ; i < notRunningDevicesListVector.size() ; i++ )
                {
                    System.out.println("HDBArchiver (NOT EXPORTED)2 " + m_hdbNotExportedArchiverList[ i ]);
                }
            }
            else
            {
                for ( int i = 0 ; i < runningDevicesListVector.size() ; i++ )
                {
                    System.out.println("TDBArchiver (EXPORTED)2 " + m_tdbExportedArchiverList[ i ]);
                }
                for ( int i = 0 ; i < notRunningDevicesListVector.size() ; i++ )
                {
                    System.out.println("TDBArchiver (NOT EXPORTED)2 " + m_tdbNotExportedArchiverList[ i ]);
                }
            }*/
        }
        catch ( DevFailed devFailed )
        {
            String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.DBT_UNREACH_EXCEPTION;
            String reason = GlobalConst.TANGO_COMM_EXCEPTION;
            String desc = "Failed while executing ArchivingManagerApi.initDbArchiverList("+isHistoric+") method...";
            throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "" , devFailed);
        }
    }
	
	public DataBaseApiRefactoring createHdbDataBaseInstance (String user , String password) throws ArchivingException
	{
		return createDataBaseInstance(user ,  password, true);
	}

	public DataBaseApiRefactoring createTdbDataBaseInstance (String user , String password) throws ArchivingException
	{
		return createDataBaseInstance(user ,  password, false);
	}
	
	private DataBaseApiRefactoring createDataBaseInstance (String user , String password, boolean historic) throws ArchivingException
	{
	DataBaseApiRefactoring dbApi;
	
		dbApi = new DataBaseApiRefactoring();
		
		if(historic)
				dbApi.createHdbDataBaseInstance(user ,  password);
		else 	dbApi.createTdbDataBaseInstance(user ,  password);
		
		return dbApi;
	}
	/**
	 * @return the facility property for that type of archiving.
	 */
	private boolean getFacility(boolean historic)
	{
		boolean facility = false;
		try
		{
			facility = ( historic ) ? GetConf.getFacility(ConfigConst.HDB_CLASS_DEVICE) : GetConf.getFacility(ConfigConst.TDB_CLASS_DEVICE);
		}
		catch ( ArchivingException e )
		{
			System.err.println(e.toString());
		}
		return facility;
	}
	

	/**
	 * Tests if the given device is alive
	 *
	 * @param deviceName
	 * @return true if the device is running
	 */
	public static boolean deviceLivingTest(String deviceName)
	{
		try
		{
            DeviceProxy deviceProxy = new DeviceProxy(deviceName);
			deviceProxy.ping();
            return true;

            /*DbDevice device = new DbDevice(deviceName);
            boolean isExported =device.get_info ().exported;
            return isExported;*/
		}
		catch ( DevFailed e )
		{
			return false;
		}
	}
}
