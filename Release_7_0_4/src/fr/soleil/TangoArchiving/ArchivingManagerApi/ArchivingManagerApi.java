//+============================================================================
//Source: package fr.soleil.TangoArchiving.HdbManager;/ArchivingManagerApi.java
//
//project :     ArchivingDev
//
//Description: This class hides
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.61  2007/03/22 16:21:36  pierrejoseph
// DataBaseApi Refactoring
//
// Revision 1.60  2007/03/21 14:57:29  pierrejoseph
// For the DataBaseApi refactoring
//
// Revision 1.59  2007/03/05 16:26:08  ounsy
// non-static DataBase
//
// Revision 1.58  2007/02/26 16:12:24  ounsy
// modified initDbArchiverList so that it uses the archiver class name instead of a tango pattern
//
// Revision 1.57  2007/02/01 13:40:41  pierrejoseph
// The ArchivingConfigure method has been duplicated : in some cases the archiver lists have not to be loaded
//
// Revision 1.56  2007/01/05 12:45:58  pierrejoseph
// Modification of the ArchivingMessConfig and AttributeLightMode object creation
//
// Revision 1.55  2006/12/21 15:09:40  pierrejoseph
// getArchivingMessStartArray became setAttributeInfoInArchivingMess + minor changes
//
// Revision 1.54  2006/12/08 11:17:03  ounsy
// bad connection management
//
// Revision 1.53  2006/11/30 13:48:36  ounsy
// modified get_xxx_charge: if the archiver doesn't answer, its charge is Integer.MAX_VALUE. this way it will never be selected by the load-balancing, without throwing an exception
//
// Revision 1.52  2006/11/24 13:47:41  pierrejoseph
// Display a message in case of TimeOut reception during the ArchivingStop call
//
// Revision 1.51  2006/11/24 11:04:48  pierrejoseph
// ArchivingStart (the trigger_archive_conf is no more send towards all the archiver devices) and selectArchiversTask modification.
//
// Revision 1.50  2006/11/23 09:59:32  ounsy
// removed the old getClassAndDevicexxx methods
//
// Revision 1.49  2006/11/22 10:46:07  ounsy
// modified ArchivingStop's exception management the same way as ArchivingStart
//
// Revision 1.48  2006/11/20 09:33:30  ounsy
// initDbArchiverList now replaces 2 methods (HDB+TDB), getAttributeInfo has a new forceRefresh parameter
//
// Revision 1.47  2006/11/15 15:50:40  ounsy
// uses the device.ping again
//
// Revision 1.46  2006/11/14 09:00:27  ounsy
// correctred deviceLivingTest
//
// Revision 1.45  2006/11/13 15:59:40  ounsy
// modified deviceLivingTest to use the Database's exported filed instead of pinging devices
//
// Revision 1.44  2006/11/09 14:19:04  ounsy
// minor changes
//
// Revision 1.43  2006/11/09 09:50:50  ounsy
// added comments in selectArchiversTask
//
// Revision 1.42  2006/10/31 16:54:24  ounsy
// milliseconds and null values management
//
// Revision 1.41  2006/10/30 14:37:06  ounsy
// removed the ArchivingConfigure(String mhuser , String mhpassword , String mtuser , String mtpassword) method
//
// Revision 1.40  2006/10/19 12:32:09  ounsy
// modified initHdbArchiversList() and  initTdbArchiversList()  so that they load the list of non exported devices as well (needed for dedoicated archivers)
//
// Revision 1.39  2006/10/18 14:34:14  ounsy
// corrected a bug in ArchivingStart that caused an IllegalStateException for some cases of timeout
//
// Revision 1.38  2006/10/11 09:00:01  ounsy
// moved initArchivers() from loadBalancedList to avoid a cross-reference problem at compile time
//
// Revision 1.37  2006/10/11 08:33:21  ounsy
// minor changes
//
// Revision 1.36  2006/10/09 14:00:37  ounsy
// renamed getDeviceDefinedAttributesToDedicatedArchiver as getAttributesToDedicatedArchiver
//
// Revision 1.35  2006/10/09 08:54:26  ounsy
// better exception management on archiving start
//
// Revision 1.34  2006/10/09 08:34:29  ounsy
// better exception management on archiving stop
//
// Revision 1.33  2006/09/29 07:58:12  ounsy
// removed logs
//
// Revision 1.32  2006/09/27 13:37:26  ounsy
// removed the unused ArchivingConfigure(String mhuser , String mhpassword , String mtuser , String mtpassword , boolean archiversLists) method
//
// Revision 1.31  2006/09/26 15:52:52  ounsy
// added timeout management in both archivingStop methods
//
// Revision 1.30  2006/09/26 15:06:19  ounsy
// added isDueToATimeOut management in archivingStart
//
// Revision 1.29  2006/09/26 13:06:41  ounsy
// modified both ArchivingStop methods so thta they load the necessary Type, Format, Writable infomation from the archiving DB instead of from looking up the tango device for information
//
// Revision 1.28  2006/09/20 12:46:02  ounsy
// minor changes
//
// Revision 1.27  2006/09/19 12:32:10  ounsy
// minor changes
//
// Revision 1.26  2006/09/19 09:47:57  ounsy
// minor changes
//
// Revision 1.25  2006/09/19 07:37:06  ounsy
// minor changes
//
// Revision 1.24  2006/09/15 15:27:09  ounsy
// added diary logging for "null modeP" debugging
//
// Revision 1.23  2006/09/14 11:32:00  ounsy
// corrected a NullPinterException bug in arrangeDevByClass()
//
// Revision 1.22  2006/08/23 09:40:17  ounsy
// minor changes
//
// Revision 1.21  2006/07/24 07:35:43  ounsy
// minor changes
//
// Revision 1.20  2006/07/13 08:31:51  ounsy
// modified the ExportData2Tdb so that it calls forceDatabaseToImportFile
//
// Revision 1.19  2006/06/20 16:00:30  ounsy
// When an attribute of an archiving configuration fails to stop archiving, it does not avoid the other ones to stop
//
// Revision 1.18  2006/06/16 09:12:32  ounsy
// moved the getArchiver method to solve dependency problems
//
// Revision 1.17  2006/06/15 15:22:31  ounsy
// -added a getDeviceDefinedAttributesToDedicatedArchiver method that maps attributes to their dedicated archivers
// -modified the selectArchiversTask to take into account the mapping of attributes to given archivers, whether this mapping is user-defined or device-defined
//
// Revision 1.16  2006/06/07 12:53:45  ounsy
// added a getArchiversList (isHistoric) method that returns the list of HDB/TDB achivers
//
// Revision 1.15  2006/06/06 16:41:17  ounsy
// added a new method arrangeDevByClassCLA that corrects the bugs of arrangeDevByClass
// (for correcting the Mambo table selection)
//
// Revision 1.14  2006/05/04 14:28:56  ounsy
// minor changes (commented useless methods and variables)
//
// Revision 1.13  2006/04/21 14:30:25  chinkumo
// Cyclic redundancy bug fixed.
//
// Revision 1.12  2006/04/11 09:08:22  ounsy
// double archiving protection
//
// Revision 1.11  2006/04/11 08:34:41  ounsy
// corrected a problem in ArchivingStart and IsArchivedStart that could cause double records
//
// Revision 1.10  2006/02/28 09:28:48  ounsy
// minor changes
//
// Revision 1.9  2006/02/24 12:04:21  ounsy
// added a getDomains ( String domainsRegExp ) mehod
//
// Revision 1.8  2006/02/07 11:53:42  ounsy
// small modification
//
// Revision 1.7  2006/01/19 15:40:02  ounsy
// -Added a getDeviceStatus (deviceName) method
// -Made all get_XXX_charge public
//
// Revision 1.6  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.5.10.4  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.5.10.3  2005/09/16 08:06:42  chinkumo
// As the ArchivingStart command now use the ArchivingMessConfig object, the old ArchivingStart is temporary private.
//
// Revision 1.5.10.2  2005/09/14 14:13:45  chinkumo
// New methods were added to allow the management of ArchivingMessConfig
// 	- ArchivingStart(..)
// 	- IsArchivedStart(..)
// 	- getArchivingMessStartArray(..)
//
// Revision 1.5.10.1  2005/09/09 08:31:02  chinkumo
// The 'checkAttributeSupport(..)' method was added. It improves the attribute support management.
//
// Revision 1.5  2005/06/24 12:06:11  chinkumo
// Some constants were moved from fr.soleil.TangoArchiving.ArchivingApi.ConfigConst to fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst.

// This change was reported here.
//
// Revision 1.4  2005/06/14 10:12:17  chinkumo
// Branch (tangORBarchiving_1_0_1-branch_0)  and HEAD merged.
//
// Revision 1.3.4.3  2005/06/13 15:32:18  chinkumo
// Changes made to improve the management of Exceptions were reported here.
//
// Revision 1.3.4.2  2005/05/10 08:24:30  chinkumo
// Changes made to make the name of Hdb/Tdb Archivers free.
// The archivers list is no more name dependant, but class dependant.
//
// Revision 1.3.4.1  2005/05/03 16:34:44  chinkumo
// Some constants in the class 'fr.soleil.TangoArchiving.ArchivingApi.ConfigConst' were renamed. Changes reported here.
//
// Revision 1.3  2005/02/04 17:23:03  chinkumo
// The grouped stopping functionnality was added.
//
// Revision 1.2  2005/01/26 15:35:37  chinkumo
// Ultimate synchronization before real sharing.
//
// Revision 1.1  2004/12/06 17:39:56  chinkumo
// First commit (new API architecture).
//
//
//copyleft :Synchrotron SOLEIL
//			L'Orme des Merisiers
//			Saint-Aubin - BP 48
//			91192 GIF-sur-YVETTE CEDEX
//			FRANCE
//
//+============================================================================
package fr.soleil.TangoArchiving.ArchivingManagerApi;

//============================================================
//Import classes

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.AttributeInfo;
import fr.esrf.TangoApi.Database;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoDs.Util;
import fr.soleil.TangoArchiving.ArchivingApi.ConfigConst;
import fr.soleil.TangoArchiving.ArchivingApi.DataBaseApi;
import fr.soleil.TangoArchiving.ArchivingApi.DataBaseApiFactory;
import fr.soleil.TangoArchiving.ArchivingApi.DataBaseConnectionFactory;
import fr.soleil.TangoArchiving.ArchivingApi.GetConf;
import fr.soleil.TangoArchiving.ArchivingApi.IDataBaseApiFactory;
import fr.soleil.TangoArchiving.ArchivingApi.IDataBaseConnectionDefinition;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.IHdbDataBaseApi;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.ITdbDataBaseApi;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.Archiver;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingMessConfig;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.AttributeHeavy;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.AttributeLightMode;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.AttributeSupport;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.LoadBalancedList;

public class ArchivingManagerApi
{

	//============================================================
	//Members

	//Command name
	private static final String m_ARCHIVINGSTART = "TriggerArchiveConf";
	private static final String m_ARCHIVINGSTOP = "StopArchiveAtt";
	private static final String m_ARCHIVINGSTOPGROUP = "StopArchiveConf";
	private static final String m_ARCHIVINGEXPORT = "ExportData2Db";

	//Attribute Name
	private static final String m_SCALARCHARGE = "scalar_charge";
	private static final String m_SPECTRUMCHARGE = "spectrum_charge";
	private static final String m_IMAGECHARGE = "image_charge";

	//Historic Data Base
	private static final String m_hdbClassDevice = "HdbArchiver";
	private static String[] m_hdbExportedArchiverList;
    private static String[] m_hdbNotExportedArchiverList;
	private DataBaseApi m_hdbDataBase = null;
	
	//Intermediate Data Base
	private static final String m_tdbClassDevice = "TdbArchiver";
	private static String[] m_tdbExportedArchiverList;
    private static String[] m_tdbNotExportedArchiverList;
	private DataBaseApi m_tdbDataBase = null;
    
    private static final String m_tdbDevicePattern = "archiving/tdbarchiver/*";
    private static final String m_hdbDevicePattern = "archiving/hdbarchiver/*";

	private static boolean hFacility = false;
	private static boolean tFacility = false;

	private boolean is_hdb_connected = false;
	private boolean is_tdb_connected = false;

	private static boolean isNew = false;
    private final static String exceptionSeparator = "\n---***---\n";
    
    private static Map attributeToDeviceInfo = new Hashtable ();
    
    
   
	//============================================================
	//Accessors

	/**
	 * @return the Host of the database
	 */
	public String getDbHost(boolean historic)
	{
		if ( historic )
		{
			if ( m_hdbDataBase != null )
				return m_hdbDataBase.getHost();
			else
				return "";
		}
		else
		{
			if ( m_tdbDataBase != null )
				return m_tdbDataBase.getHost();
			else
				return "";
		}
	}

	/**
	 * @return the name of the database
	 */
	public String getDbName(boolean historic)
	{
		if ( historic )
		{
			if ( m_hdbDataBase != null )
				return m_hdbDataBase.getDbName();
			else
				return "";
		}
		else
		{
			if ( m_tdbDataBase != null )
				return m_tdbDataBase.getDbName();
			else
				return "";
		}
	}

	/**
	 * @return the user of the database
	 */
	public String getDbUser(boolean historic)
	{
		if ( historic )
		{
			if ( m_hdbDataBase != null )
				return m_hdbDataBase.getUser();
			else
				return "";
		}
		else
		{
			if ( m_tdbDataBase != null )
				return m_tdbDataBase.getUser();
			else
				return "";
		}
	}

	/**
	 * @return the password of the database
	 */
	public String getDbPassword(boolean historic)
	{
		if ( historic )
		{
			if ( m_hdbDataBase != null )
				return m_hdbDataBase.getPassword();
			else
				return "";
		}
		else
		{
			if ( m_tdbDataBase != null )
				return m_tdbDataBase.getPassword();
			else
				return "";
		}
	}

	/**
	 * @return the database type SQL, ORACLE...
	 */
	public int getDbType(boolean historic)
	{
		if ( historic )
		{
			if ( m_hdbDataBase != null )
				return m_hdbDataBase.getDb_type();
			return -1;
		}
		else
		{
			if ( m_tdbDataBase != null )
				return m_tdbDataBase.getDb_type();
			return -1;
		}
	}

	/**
	 * @return the facility property for that type of archiving.
	 */
	private static boolean getFacility(boolean historic)
	{
		boolean facility = false;
		try
		{
			facility = ( historic ) ? GetConf.getFacility(m_hdbClassDevice) : GetConf.getFacility(m_tdbClassDevice);
		}
		catch ( ArchivingException e )
		{
			System.err.println(e.toString());
		}
		return facility;
	}

	public static boolean getHdbFacility()
	{
		return hFacility;
	}

	public static boolean getTdbFacility()
	{
		return tFacility;
	}

	//============================================================
	//Methodes

	/**
	 * initialize hdbManagerApi
	 */
	public void Init()
	{
		closeDatabase(true);
		closeDatabase(false);
	}

	/**
	 * Get the status of the archiver in charge of the given attribute
	 *
	 * @param attributeName
	 * @param historic      true if historical archiving, false otherwise.
	 * @return the status of the Archiver driver
	 * @throws ArchivingException
	 */
	public String getStatus(String attributeName , boolean historic) throws ArchivingException
	{

		try
		{
			String archiverName = "";
			if ( historic )
			{
				if ( m_hdbDataBase != null )
				{
					archiverName = m_hdbDataBase.getDeviceInCharge(attributeName);
				}
			}
			else
			{
				if ( m_tdbDataBase != null )
				{
					archiverName = m_tdbDataBase.getDeviceInCharge(attributeName);
				}
			}
			DeviceProxy archiverProxy = new DeviceProxy(archiverName);
			return archiverProxy.status();
		}
		catch ( DevFailed devFailed )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ARC_UNREACH_EXCEPTION;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.getStatus() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);

		}
	}
	
	/**
	 * Get the status of the archiver
	 *
	 * @param archiverName
	 * @return the status of the Archiver driver
	 * @throws ArchivingException
	 */
	public static String getDeviceStatus ( String archiverName ) throws ArchivingException
	{
		try
		{
			DeviceProxy archiverProxy = new DeviceProxy(archiverName);
			return archiverProxy.status();
		}
		catch ( DevFailed devFailed )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ARC_UNREACH_EXCEPTION;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.getArchiverStatus() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}

	/**
	 * @return true if the connection to hdb exists
	 */
	public boolean is_db_connected(boolean historic)
	{
		if ( historic )
			return is_hdb_connected;
		else
			return is_tdb_connected;
	}

	/**
	 * Start the archiving for the given attribute list and for the given archiving type
	 *
	 * @param historic            true if historical archiving, false otherwise.
	 * @param archivingMessConfig An ArchivingMessConfig object (a list of AttributeLightMode object)
	 * @throws ArchivingException
	 */
	public void ArchivingStart(boolean historic , ArchivingMessConfig archivingMessConfig) throws ArchivingException
	{
        //long beforeInitArchiverList = System.currentTimeMillis ();
        ArchivingException archivingStartException = new ArchivingException();
		boolean needsThrow = false;

        initDbArchiverList ( historic );
    
		if ( historic )
		{
			if ( m_hdbDataBase == null )
			{
				String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.CANNOT_TALK_TO_ADB;
				String reason = "Failed while executing ArchivingManagerApi.ArchivingStart() method...";
				String desc = "The connection to the archiving database has not been initialized !";
				throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "");
			}
		}
		else
		{
			if ( m_tdbDataBase == null )
			{
				String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.CANNOT_TALK_TO_ADB;
				String reason = "Failed while executing ArchivingManagerApi.ArchivingStart() method...";
				String desc = "The connection to the archiving database has not been initialized !";
				throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "");
			}
		}
        //long afterInitArchiverList = System.currentTimeMillis ();
		// *********************************************************************************************************
		Vector goodAttributeList = new Vector();
		Vector badAttributeList = new Vector();
        //long beforeBuildingGoodAttributeList = System.currentTimeMillis ();
		// Check the support type, format and writable...
        Enumeration attributeNameList = archivingMessConfig.getAttributeListKeys();
		while ( attributeNameList.hasMoreElements() )
		{
			String attribute = ( String ) attributeNameList.nextElement();
            //System.out.println ( "ArchivingManagerApi/ArchivingStart/attribute|"+attribute );
            try
			{
                if ( checkAttributeSupport(attribute) )
                {
					goodAttributeList.add(attribute);
                }
				else
				{
                    String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + "Attribute format, type or writable not supported !";
					String reason = "Failed while executing ArchivingManagerApi.ArchivingStart() method...";
					String desc = "The archiving of the attribute named '" + attribute + "'" + "was not launched ! : \r\n";
					ArchivingException archivingException = new ArchivingException(message , reason , ErrSeverity.WARN , desc , "");
					archivingStartException.addStack(message , reason , ErrSeverity.WARN , desc , "" , archivingException);
					badAttributeList.add(attribute);
				}
			}
			catch ( ArchivingException e )
			{
                e.printStackTrace();
                
                String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : ";
				String reason = "Failed while executing ArchivingManagerApi.ArchivingStart() method...";
				String desc = "The archiving of the attribute named '" + attribute + "'" + "was not launched ! : \r\n";
				archivingStartException.addStack(message , reason , ErrSeverity.WARN , desc , "" , e);
				badAttributeList.add(attribute);
				
				needsThrow = true;
			}
            catch ( Exception e )
            {
                e.printStackTrace();
                
                String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : ";
                String reason = "Failed while executing ArchivingManagerApi.ArchivingStart() method...";
                String desc = "The archiving of the attribute named '" + attribute + "'" + "was not launched ! : \r\n";
                archivingStartException.addStack(message , reason , ErrSeverity.WARN , desc , "" , new ArchivingException(e.getMessage()));
                badAttributeList.add(attribute);
                
                needsThrow = true;
            }
		}
        
        if ( goodAttributeList.size () == 0 )
        {
            String reason = "Failed while executing ArchivingManagerApi.ArchivingStart() method, no supported attributes";
            ArchivingException archivingException = new ArchivingException(reason , reason , ErrSeverity.ERR , reason , "ArchivingManagerApi");
            throw archivingException;
        }
        
        //long afterBuildingGoodAttributeList = System.currentTimeMillis ();
		// Build the new list in case of bad attribute(s)
        //long beforeBuildingFinalAttributeList = System.currentTimeMillis ();
        String[] finalAttributeList = new String[ goodAttributeList.size() ];
		for ( int i = 0 ; i < goodAttributeList.size() ; i++ )
		{
			finalAttributeList[ i ] = ( String ) goodAttributeList.elementAt(i);
		}

		//if the creation table doest not work the archiving is not launched
		finalAttributeList = checkRegistration(historic , finalAttributeList);
        //long afterBuildingFinalAttributeList = System.currentTimeMillis ();
        //long beforeBuildingLoadBalancedList = System.currentTimeMillis ();
		LoadBalancedList loadBalancedList = selectArchiversTask(archivingMessConfig.isGrouped(),archivingMessConfig.getAttributeToDedicatedArchiverHasthable () , historic , finalAttributeList);
        //long afterBuildingLoadBalancedList = System.currentTimeMillis ();
		// Sending order to archivers
		Enumeration my_archivers = loadBalancedList.getArchivers();
		boolean initCauseAlreadyDoneInException = false;
		
        //long beforeCallingArchivers = System.currentTimeMillis ();
        while ( my_archivers.hasMoreElements() )
		{
			String my_archiver = ( String ) my_archivers.nextElement();
            //System.out.println ( "ArchivingManagerApi/ArchivingStart/my_archiver|"+my_archiver );
			String[] assignedAttributes = loadBalancedList.getArchiverAssignedAtt(my_archiver);
			
			if(assignedAttributes.length > 0)
			{
				ArchivingMessConfig archiver_archivingMessConfig = ArchivingMessConfig.basicObjectCreation();

				for ( int i = 0 ; i < assignedAttributes.length ; i++ )
				{
					archiver_archivingMessConfig.add(archivingMessConfig.getAttribute(assignedAttributes[ i ]));
					//System.out.println ( "ArchivingManagerApi/ArchivingStart/assignedAttributes[ i ]|"+assignedAttributes[ i ] );
				}

				DeviceProxy archiverProxy = null;
				try
				{
					// Set the data_type, format and writable characteristics of each attributs
					setAttributeInfoInArchivingMess(archiver_archivingMessConfig);
					//String[] archivingMessStartArray = getArchivingMessStartArray(archiver_archivingMessConfig);
					DeviceData device_data = new DeviceData();
					device_data.insert(archiver_archivingMessConfig.toArray());

					//System.out.println ( "ArchivingManagerApi/ArchivingStart/BEFORE command_inout/my_archiver|"+my_archiver );
					//archiverProxy.set_timeout_millis(5000);
					archiverProxy = new DeviceProxy(my_archiver);
					archiverProxy.command_inout(m_ARCHIVINGSTART , device_data);
					//System.out.println ( "ArchivingManagerApi/ArchivingStart/AFTER command_inout" );
				}
				catch ( DevFailed devFailed )
				{
					devFailed.printStackTrace ();

					String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : ";
					String reason = "Failed while executing ArchivingManagerApi.ArchivingStart() method...";
					String desc = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ARC_UNREACH_EXCEPTION;
					desc += "(Archiver: " + my_archiver + ")";
					ArchivingException archivingException = new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "ArchivingManagerApi");
					if(!initCauseAlreadyDoneInException)
					{
						try
						{
							archivingStartException.initCause ( devFailed );
							initCauseAlreadyDoneInException = true;
						}
						catch ( IllegalStateException e )
						{
							System.out.println ( "CLA/Failed during initCause!" );
							e.printStackTrace ();
							//do nothing
						}
					}
					boolean computeIsDueToATimeOut = archivingStartException.computeIsDueToATimeOut ();
					if (computeIsDueToATimeOut)
					{
						desc += "\n--Time out";
					}
					archivingStartException.addStack(message , reason , ErrSeverity.WARN , desc , "" , archivingException);
					
					needsThrow = true;
				}
				catch ( Exception e )
				{
					String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : ";
					String reason = "Failed while executing ArchivingManagerApi.ArchivingStart() method...";
					String desc = GlobalConst.ARCHIVING_ERROR_PREFIX + " : Unexpected exception";
					desc += "(Archiver: " + my_archiver + ")";
					ArchivingException archivingException = new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "ArchivingManagerApi");
					archivingStartException.addStack(message , reason , ErrSeverity.WARN , desc , "" , archivingException);
					needsThrow = true;
				}
				/*catch ( Throwable e )
            	{
                	e.printStackTrace();
            	}*/
			}
		}
        /*long afterCallingArchivers = System.currentTimeMillis ();
        
        long timeInitArchiverList = afterInitArchiverList - beforeInitArchiverList;
        long timeBuildingGoodAttributeList = afterBuildingGoodAttributeList - beforeBuildingGoodAttributeList;
        long timeBuildingFinalAttributeList = afterBuildingFinalAttributeList - beforeBuildingFinalAttributeList;
        long timeBuildingLoadBalancedList = afterBuildingLoadBalancedList - beforeBuildingLoadBalancedList;
        long timeCallingArchivers = afterCallingArchivers - beforeCallingArchivers;
        long totalTime = afterCallingArchivers - beforeInitArchiverList;
        
        System.out.println( "timeInitArchiverList/"+timeInitArchiverList );
        System.out.println( "timeBuildingGoodAttributeList/"+timeBuildingGoodAttributeList );
        System.out.println( "timeBuildingFinalAttributeList/"+timeBuildingFinalAttributeList );
        System.out.println( "timeBuildingLoadBalancedList/"+timeBuildingLoadBalancedList );
        System.out.println( "timeCallingArchivers/"+timeCallingArchivers );
        System.out.println( "totalTime/"+totalTime );*/
        
		throwArchivingStartException ( archivingStartException , needsThrow , badAttributeList.size() );
		}

	
	
	
	/**
     * @param archivingStartException
     * @param needsThrow
	 * @param badAttributeListSize
	 * @throws ArchivingException
     */
    private static void throwArchivingStartException(ArchivingException archivingStartException, boolean needsThrow, int badAttributeListSize) throws ArchivingException 
    {
        if ( needsThrow )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = "Failed while executing ArchivingManagerApi.ArchivingStart() method...";
			
			if ( badAttributeListSize != -1 )
			{
				String desc = "Impossible to launch the archiving of " + badAttributeListSize + " attributes ! : \r\n";
				ArchivingException aef = new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "ArchivingManagerApi");
				archivingStartException.addStack(message , reason , ErrSeverity.PANIC , desc , "ArchivingManagerApi" , aef);
			}
			
            throw archivingStartException;
		}        
    }
    
    private static void throwArchivingStopException(ArchivingException archivingStopException, boolean needsThrow, int badAttributeListSize) throws ArchivingException 
    {
        if ( needsThrow )
        {
            String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
            String reason = "Failed while executing ArchivingManagerApi.ArchivingStop() method...";
            
            if ( badAttributeListSize != -1 )
            {
                String desc = "Impossible to stop the archiving of " + badAttributeListSize + " attributes ! : \r\n";
                ArchivingException aef = new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "ArchivingManagerApi");
                archivingStopException.addStack(message , reason , ErrSeverity.PANIC , desc , "ArchivingManagerApi" , aef);
            }
            
            throw archivingStopException;
        }        
    }

	/**
	 * Stop the archiving for the given attribute list and for the given archiving type
	 *
	 * @param historic          true if historical archiving, false otherwise.
	 * @param attributeNameList the list of attributes (The archiving of each element of this list is about to be stopped)
	 * @throws ArchivingException
	 */
	public void ArchivingStop(boolean historic , String[] attributeNameList) throws ArchivingException
	{
        boolean needsThrow = false;
        int badAttributes = 0;
        
        if ( !IsArchivedStop(historic , attributeNameList) )
			throw new ArchivingException(GlobalConst.ALREADY_ARCHIVINGSTOP);

        if ( historic )
		{
			if ( m_hdbDataBase == null )
				throw new ArchivingException(GlobalConst.UNCONNECTECTED_ADB);
		}
		else
		{
			if ( m_tdbDataBase == null )
				throw new ArchivingException(GlobalConst.UNCONNECTECTED_ADB);
		}
        
		String attributeName = "";
		String archiverName = "";
  
        ArchivingException archivingStopException = new ArchivingException ();
        boolean initCauseAlreadyDoneInException = false;
        
        for ( int i = 0 ; i < attributeNameList.length ; i++ )
		{
            attributeName = attributeNameList[ i ];
            
            try
            {
                //get the Archiver that take in charge the archiving
                archiverName = ( ( historic ) ? m_hdbDataBase : m_tdbDataBase ).getDeviceInCharge(attributeName);
                if ( archiverName == "" || !deviceLivingTest(archiverName) )
                    throw new ArchivingException(GlobalConst.ARC_UNREACH_EXCEPTION);

                if ( isArchived(attributeName , historic) )
                {
                    Mode mode = GetArchivingMode(attributeName , historic);
                    AttributeLightMode attributeLightMode = new AttributeLightMode(attributeName);
                    attributeLightMode.setDevice_in_charge(archiverName);
                    
                    /*AttributeInfo attributeInfo = getAttributeInfoFromDatabase(attributeName);
                    attributeLightMode.setData_type(attributeInfo.data_type);
                    attributeLightMode.setData_format(attributeInfo.data_format.value());
                    attributeLightMode.setWritable(attributeInfo.writable.value());*/
                    DataBaseApi database = historic ? m_hdbDataBase : m_tdbDataBase;
                    int [] tfw = database.getAtt_TFW_Data ( attributeName );
                    attributeLightMode.setData_type ( tfw [ 0 ] );
                    attributeLightMode.setData_format ( tfw [ 1 ] );
                    attributeLightMode.setWritable ( tfw [ 2 ] );
                    attributeLightMode.setMode(mode);

                    DeviceProxy archiverProxy = new DeviceProxy(archiverName);
                    String[] attributeLightModeArray = attributeLightMode.toArray();

                    DeviceData device_data = new DeviceData();
                    device_data.insert(attributeLightModeArray);
                    archiverProxy.command_inout(m_ARCHIVINGSTOP , device_data);
                }
            }
            catch (DevFailed devFailed)
            {
                devFailed.printStackTrace ();
                
                String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : ";
                String reason = "Failed while executing ArchivingManagerApi.ArchivingStop() method...";
                String desc = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ARC_UNREACH_EXCEPTION;
                desc += "(Archiver: " + archiverName + ")";
                ArchivingException archivingException = new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "ArchivingManagerApi");
                
                if(!initCauseAlreadyDoneInException)
                {
                	try
                	{
                		archivingStopException.initCause ( devFailed );
                		initCauseAlreadyDoneInException = true;
                	}
                	catch ( IllegalStateException e )
                	{
                		System.out.println ( "CLA/Failed during initCause!" );
                		e.printStackTrace ();
                		//do nothing
                	}
                }
                
				if (archivingStopException.computeIsDueToATimeOut ())
				{
					desc += "\n--Time out";
				}
				
                archivingStopException.addStack(message , reason , ErrSeverity.WARN , desc , "" , archivingException);
                needsThrow = true;
                badAttributes ++;
            }
            catch ( Exception e )
            {
                String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : ";
                String reason = "Failed while executing ArchivingManagerApi.ArchivingStop() method...";
                String desc = GlobalConst.ARCHIVING_ERROR_PREFIX + " : Unexpected exception";
                desc += "(Archiver: " + archiverName + ")";
                ArchivingException archivingException = new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "ArchivingManagerApi");
                archivingStopException.addStack(message , reason , ErrSeverity.WARN , desc , "" , archivingException);
                needsThrow = true;
                badAttributes ++;
            }
		}
        
        throwArchivingStopException ( archivingStopException , needsThrow , badAttributes );
	}

	/**
	 * Sstop the archiving in the intermediate database
	 *
	 * @param attributeNameList the list of attributes (The archiving of each element of this list is about to be stopped)
	 * @param historic
	 * @throws ArchivingException
	 */
	public void ArchivingStopConf(String[] attributeNameList , boolean historic) throws ArchivingException
	{
        Hashtable myStoppingConf = new Hashtable();
		String attributeName = "";
		String archiverName = "";

		if ( !IsArchivedStop(historic , attributeNameList) )
			throw new ArchivingException(GlobalConst.ALREADY_ARCHIVINGSTOP);

		try
		{
			for ( int i = 0 ; i < attributeNameList.length ; i++ )
			{
				attributeName = attributeNameList[ i ];
				//get the Archiver that take in charge the archiving

				if ( !( archiverName = ( ( historic ) ? m_hdbDataBase : m_tdbDataBase ).getDeviceInCharge(attributeName) ).equals("") )
				{
					if ( myStoppingConf.containsKey(archiverName) || !deviceLivingTest(archiverName) )
					{
					}
					//return ArchivingManagerResult.INEXISTANT_ARCHIVER;
					if ( isArchived(attributeName , historic) )
					{
						Mode mode = GetArchivingMode(attributeName , historic);
						AttributeLightMode attributeLightMode = new AttributeLightMode(attributeName);						
						attributeLightMode.setDevice_in_charge(archiverName);
						
                        /*AttributeInfo attributeInfo = getAttributeInfoFromDatabase(attributeName);
                        attributeLightMode.setData_type(attributeInfo.data_type);
						attributeLightMode.setData_format(attributeInfo.data_format.value());
						attributeLightMode.setWritable(attributeInfo.writable.value());*/
                        DataBaseApi database = historic ? m_hdbDataBase : m_tdbDataBase;
                        int [] tfw = database.getAtt_TFW_Data ( attributeName );
                        attributeLightMode.setData_type ( tfw [ 0 ] );
                        attributeLightMode.setData_format ( tfw [ 1 ] );
                        attributeLightMode.setWritable ( tfw [ 2 ] );
						
                        attributeLightMode.setMode(mode);
						if ( !myStoppingConf.containsKey(archiverName) )
							myStoppingConf.put(archiverName , new Vector());

						( ( Vector ) myStoppingConf.get(archiverName) ).add(attributeLightMode);
					}
				}
			}

			for ( Enumeration e = myStoppingConf.keys() ; e.hasMoreElements() ; )
			{
				archiverName = ( String ) e.nextElement();
				DeviceProxy archiverProxy = new DeviceProxy(archiverName);
				DeviceData device_data = new DeviceData();
				String[] archiverStoppingConf = toStoppingArrayAttLightMode(( Vector ) myStoppingConf.get(archiverName));
				device_data.insert(archiverStoppingConf);
				archiverProxy.command_inout(m_ARCHIVINGSTOPGROUP , device_data);
			}


		}
		catch ( DevFailed devFailed )
		{
			String message = GlobalConst.ERROR_ARCHIVINGSTOP + " : " +
			                 "Problems talking to the archiver " + archiverName + " for the attribute " + attributeName;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.ArchivingStopConf() method...";
            
            ArchivingException toBeThrown = new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
            toBeThrown.initCause ( devFailed );
            //boolean computeIsDueToATimeOut = 
            toBeThrown.computeIsDueToATimeOut ();
			throw toBeThrown;
		}
		catch ( ArchivingException e )
		{
			throw e;
		}
	}

    /**
	 * Export for a given attribute, its file stored data to the database.
	 *
	 * @param attributeName The name of the given attribute
	 */
	public void ExportData2Tdb(String attributeName) throws ArchivingException
	{
        String archiverName = "";
		if ( m_tdbDataBase == null )
			throw new ArchivingException(GlobalConst.UNCONNECTECTED_ADB);
		
		try
		{
		    if ( isArchived(attributeName , false) )		        
			{
				//get the Archiver that take in charge the archiving
				archiverName = m_tdbDataBase.getDeviceInCharge(attributeName);
				if ( archiverName == "" || !deviceLivingTest(archiverName) )
				{
				    //throw new ArchivingException(GlobalConst.ARC_UNREACH_EXCEPTION);
				    throw new ArchivingException(GlobalConst.ARC_UNREACH_EXCEPTION,GlobalConst.ARC_UNREACH_EXCEPTION,ErrSeverity.ERR,GlobalConst.ARC_UNREACH_EXCEPTION,GlobalConst.ARC_UNREACH_EXCEPTION );
				    //ArchivingException(String message , String reason , ErrSeverity archSeverity , String desc , String origin)
				}

                String tableName;
                try
                {
                    //System.out.println ( "CLA/ArchivingManagerApi/ExportData2Tdb/attributeName A0|" + attributeName + "|" );
                    tableName = m_tdbDataBase.getTableName ( attributeName );
                    //System.out.println ( "CLA/ArchivingManagerApi/ExportData2Tdb/attributeName A1|" + attributeName + "|tableName|" + tableName + "|" );
                }
                catch ( Throwable t )
                {
                    t.printStackTrace ();
                    return;
                }
                
                
                Mode mode = GetArchivingMode(attributeName , false);
				AttributeLightMode attributeLightMode = new AttributeLightMode(attributeName);
				AttributeInfo attributeInfo = getAttributeInfo(attributeName,true);
				attributeLightMode.setDevice_in_charge(archiverName);
				attributeLightMode.setData_type(attributeInfo.data_type);
				attributeLightMode.setData_format(attributeInfo.data_format.value());
				attributeLightMode.setWritable(attributeInfo.writable.value());
				attributeLightMode.setMode(mode);
                
				DeviceProxy archiverProxy = new DeviceProxy(archiverName);
                String[] attributeLightModeArray = attributeLightMode.toArray();
				DeviceData device_data = new DeviceData();
				device_data.insert(attributeLightModeArray);
				
                //System.out.println ( "ArchivingManagerApi/ExportData2Tdb/BEFORE archiverProxy.command_inout" );
                //DeviceData argout = 
                archiverProxy.command_inout(m_ARCHIVINGEXPORT , device_data);
                
                
                //System.out.println ( "ArchivingManagerApi/ExportData2Tdb/BEFORE forceDatabaseToImportFile" );
                forceDatabaseToImportFile ( tableName );
                //System.out.println ( "ArchivingManagerApi/ExportData2Tdb/AFTER forceDatabaseToImportFile" );
			}
		    else
		    {
		    }
            
            //System.out.println ( "CLA/ArchivingManagerApi/ExportData2Tdb/attributeName Z0" );
		}
		catch ( DevFailed devFailed )
		{
		    String message = GlobalConst.ERROR_ARCHIVINGSTOP + " : " +
			                 "Problems talking to the archiver " + archiverName + " for the attribute " + attributeName;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.ExportData2Tdb() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
		catch ( ArchivingException e )
		{		 
		    e.printStackTrace();
		    throw e;
		}
        
        //System.out.println ( "CLA/ArchivingManagerApi/ExportData2Tdb/attributeName Z1" );
	}

    public void forceDatabaseToImportFile(String tableName) throws ArchivingException 
    {
        m_tdbDataBase.forceDatabaseToImportFile ( tableName );
    }

    /**
	 * get the archiving mode of an attribute.
	 *
	 * @param attributeName the complete name of the attribute
	 * @param historic      true if historical archiving, false otherwise.
	 * @return the mode
	 */
	public  Mode GetArchivingMode(String attributeName , boolean historic)
	{
		try
		{
			if ( !isRegistred(historic , attributeName) )
				return null;
			if ( historic )
			{
				Mode mode = m_hdbDataBase.getCurrentArchivingMode(attributeName , historic);
				return mode;
			}
			else
			{
				Mode mode = m_tdbDataBase.getCurrentArchivingMode(attributeName , historic);
				return mode;
			}
		}
		catch ( ArchivingException e )
		{
			System.err.println(e.toString());
			return null;
		}
	}

	/**
	 * return the attribute registred status
	 *
	 * @param historic      true if historical archiving, false otherwise.
	 * @param attributeName the complete name of the attribute
	 * @return true if the attribute has been archived
	 */
	private boolean isRegistred(boolean historic , String attributeName)
	{
		try
		{
			if ( historic )
			{
				if ( m_hdbDataBase != null )
				{
					if ( m_hdbDataBase.isRegisteredADT(attributeName) )
						return true;
				}
			}
			else
			{
				if ( m_tdbDataBase != null )
				{
					if ( m_tdbDataBase.isRegisteredADT(attributeName) )
						return true;
				}
			}
		}
		catch ( ArchivingException e )
		{
			System.err.println(e.toString());
			return false;
		}
		return false;
	}

	/**
	 * return the attribute archiving status
	 *
	 * @param attributeName the complete name of the attribute
	 * @param historic      true if historical archiving, false otherwise.
	 * @return true if the attribute is being archived
	 * @throws ArchivingException
	 */
	public boolean isArchived(String attributeName , boolean historic) throws ArchivingException
	{
		if ( historic )
		{
			if ( m_hdbDataBase == null )
				return false;
			else
				return m_hdbDataBase.isArchived(attributeName);
		}
		else
		{
			if ( m_tdbDataBase == null )
				return false;
			else
				return m_tdbDataBase.isArchived(attributeName);
		}
	}

	/**
	 * Use for ArchivingStop method, to know if all the list is already in archiving
	 *
	 * @param historic          true if historical archiving, false otherwise.
	 * @param attributeNameList
	 * @return true if at least one attribute of the list is not in progress filing
	 * @throws ArchivingException
	 */
	private boolean IsArchivedStop(boolean historic , String[] attributeNameList) throws ArchivingException
	{
        for ( int i = 0 ; i < attributeNameList.length ; i++ )
		{
			if ( isArchived(attributeNameList[ i ] , historic) )
				return true;
		}
		return false;
	}

	/**
	 * generate the ArchivingStart array command used in the Archiver driver
	 *
	 * @param archivingMessConfig
	 * @return the array that correspond to the archivingMessConfig object.
	 */
	//private static String[] getArchivingMessStartArray(ArchivingMessConfig archivingMessConfig) throws ArchivingException
	private static void setAttributeInfoInArchivingMess(ArchivingMessConfig archivingMessConfig) throws ArchivingException
	{
		Enumeration m_attributs = archivingMessConfig.getAttributeListKeys();

		while ( m_attributs.hasMoreElements() )
		{
			String m_att = ( String ) m_attributs.nextElement();
			AttributeInfo attributeInfo = null;
			try
			{
				attributeInfo = getAttributeInfo(m_att,false);
				archivingMessConfig.getAttribute(m_att).setData_type(attributeInfo.data_type);
				archivingMessConfig.getAttribute(m_att).setData_format(attributeInfo.data_format.value());
				archivingMessConfig.getAttribute(m_att).setWritable(attributeInfo.writable.value());

			}
			catch ( DevFailed devFailed )
			{
				String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : ";
				String reason = "Failed while executing ArchivingManagerApi.getArchivingStartArray() method...";
				String desc = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ATT_UNREACH_EXCEPTION + " [" + m_att + "]";
				throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "ArchivingManagerApi");
			}
		}
		//return archivingMessConfig.toArray();
	}

	/**
	 * Distribute attributes on archivers the archiver that will take in charge the archiving
	 *
	 * @param grouped           true if all attributes are associated to the same archiver
	 * @param historic          true if historical archiving, false otherwise.
	 * @param attributeNameList the attributes to distribute on archivers
	 * @return An hashtable with archivers name as keys, and attributes list as values
	 * @throws ArchivingException
	 */
    private LoadBalancedList selectArchiversTask(boolean grouped , Hashtable attributeToChosenArchiverHasthable , boolean historic , String[] attributeNameList) throws ArchivingException
    {
        Hashtable attributeToDedicatedArchiver = getAttributesToDedicatedArchiver ( historic , false );//CLA 10/11/06
        
        LoadBalancedList loadBalancedList = new LoadBalancedList ( historic );
        String[] exportedArchiversList = ( historic ) ? m_hdbExportedArchiverList : m_tdbExportedArchiverList;
        ArchivingManagerApi.initArchivers ( loadBalancedList , grouped , historic , exportedArchiversList );
        
        // Attribute distribution
        for ( int i = 0 ; i < attributeNameList.length ; i++ )
        {
            String currentAttributeName = attributeNameList[ i ];
            String chosenArchiver = (String) attributeToChosenArchiverHasthable.get ( currentAttributeName );
            boolean theCurrentAttributeHasAChosenArchiver = chosenArchiver != null && ! chosenArchiver.equals ( "" );  
            String dedicatedArchiver = null;
            boolean theCurrentAttributeHasADedicatedArchiver = false;
            //System.out.println ( "ArchivingManagerApi/selectArchiversTask/currentAttributeName|"+currentAttributeName+"|chosenArchiver|"+chosenArchiver+"|" );
            
            if ( attributeToDedicatedArchiver != null )
            {
            	if(attributeToDedicatedArchiver.size() > 0)
            	{
            		Archiver archiver = (Archiver) attributeToDedicatedArchiver.get ( currentAttributeName );
            		dedicatedArchiver = archiver == null ? null : archiver.getName ();
            		//System.out.println ( "ArchivingManagerApi/selectArchiversTask/dedicatedArchiver NOT NULL/dedicatedArchiver|"+dedicatedArchiver+"|" );
            		if ( dedicatedArchiver != null && ! dedicatedArchiver.equals ( "" ) )
            		{
            			theCurrentAttributeHasADedicatedArchiver = true;
            		}
            	}
            }
            
            String archiver = "";
            
            //first let's find out whether the currentAttribute has already been archived
            //if it has, we will use the same archiver 
            if ( historic )
            {
                archiver = m_hdbDataBase.getArchiverForAttribute ( currentAttributeName );
                //System.out.println ( "ArchivingManagerApi/selectArchiversTask/m_hdbDataBase.getArchiverForAttribute/archiver|"+archiver+"|" );
            }
            else
            {
                archiver = m_tdbDataBase.getArchiverForAttribute ( currentAttributeName );
                //System.out.println ( "ArchivingManagerApi/selectArchiversTask/m_tdbDataBase.getArchiverForAttribute/archiver|"+archiver+"|" );
            }
            //System.out.println ( "ArchivingManagerApi/selectArchiversTask/current archiver|"+archiver+"|" );
            
            //if it hasn't, let's see if the currentAttribute has a dedicatedArchiver
            if ( archiver == null || "".equals(archiver))
            {
                //si l'attribut n'est pas deja en cours d'archivage on regarde s'il a un archiver reserve
                if ( theCurrentAttributeHasADedicatedArchiver )
                {
                    archiver = dedicatedArchiver;
                    //System.out.println ( "ArchivingManagerApi/selectArchiversTask/selected a NEW DEDICATED archiver" );
                }
                
                if ( archiver == null || "".equals(archiver))
                {
                    //si l'attribut n'a pas un archiver reserve , on regarde si l'utilisateur a specifie un choisi
                    if ( theCurrentAttributeHasAChosenArchiver )
                    {
                        archiver = chosenArchiver;
                    }
                    //sinon le load balancing se chargera de lui en trouver un.
                    else
                    {
                        archiver = "";
                    }    
                }
            }
            
            boolean canForce = false;
            if (!"".equals(archiver))
            {
                // On a determine sur quel archiver l'attribut devait aller:
                // on est dans un cas ou l'attribut a un archiver soit parce qu'il est deja archive, ou parce qu'il a un archiver choisi ou dedie
                // mais il faut encore verifier qu'on ne met pas un attribut non-reserve sur un archiver dedie.
                // Par exemple si l'attribut etait archive par un archiver non-dedie, mais que cet archiver est maintenant dedie: l'attribut n'a pas le droit de rester dessus
                // Ou autre exemple l'archiver choisi est un archiver dedie (meme si Mambo ne le permet plus, l'API doit faire le controle independamment de l'interface graphique)
                
                //On regarde si l'archiver sur lequel on veut mettre l'attribut est dedie. 
                boolean isArchiverDedicated = false;
                if ( attributeToDedicatedArchiver != null )
                {
                    Iterator it = attributeToDedicatedArchiver.values ().iterator ();
                    //On parcourt la liste des archivers dedies, et pour chacun d'entre eux on regarde son nom:
                    //si ce nom = le nom recherche, alors l'archiver sur lequel on veut mettre l'attribut est un des archivers dedies. 
                    while ( it.hasNext () )
                    {
                        Archiver next = (Archiver) it.next ();
                        if ( next.getName () != null && next.getName ().equals ( archiver ) )
                        {
                            isArchiverDedicated = true;
                            break;
                        }
                    }
                }
                
                //Si oui (l'archiver sur lequel on veut mettre l'attribut est dedie) il faut verifier que :
                //-l'attribut est reserve
                //-que son archiver dedie est l'archiver choisi
                if ( isArchiverDedicated )
                {
                    //Pour ce faire, on regarde si l'attribut a un archiver dedie, et si oui si c'est l'archiver determine plus tot
                    boolean theCurrentAttributeIsRegisteredToThisDedicatedArchiver = dedicatedArchiver != null && dedicatedArchiver.equals ( archiver );
                    //Si oui, pas de probleme
                    if ( theCurrentAttributeIsRegisteredToThisDedicatedArchiver )
                    {
                        canForce = true;
                    }
                    //Sinon, on a indument tente d'affecter un attribut a un archiver dedie
                    //Il faut donc trouver un autre archiver a cet attribut
                    else
                    {
                        canForce = false;
                    }   
                }
                else
                {
                    canForce = true;
                }
            }
            
            if ( canForce )
            {
                loadBalancedList.forceAddAtt2Archiver ( currentAttributeName , archiver, getAttributeDataFormat(attributeNameList[ i ]));    
            }
            else
            {
                loadBalancedList.addAtt2LBL ( currentAttributeName , getAttributeDataFormat ( attributeNameList [ i ] ) );    
            }
        }
        return loadBalancedList;
    }
    
    /**
	 * get the number of attribute taken in charge for the given Archiver device
	 *
	 * @param archiverName
	 * @return the number of attribute in charge
	 * @throws ArchivingException
	 */
	public static int get_charge(String archiverName) throws ArchivingException
	{
		try
		{
			int attribute_device_charge = 0;
			DeviceProxy archiverProxy = new DeviceProxy(archiverName);
			String[] attributeList = archiverProxy.get_attribute_list();
			for ( int j = 0 ; j < attributeList.length ; j++ )
			{
				DeviceAttribute devattr = archiverProxy.read_attribute(attributeList[ j ]);
				short[] shortarray = devattr.extractShortArray();
				short value = shortarray[ 0 ];
				//System.out.println(attributeList[ j ] + " = " + value);
				attribute_device_charge = attribute_device_charge + value;
			}
			return attribute_device_charge;
		}
		catch ( DevFailed devFailed )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.get_charge() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}


	/**
	 * get the number of scalar taken in charge for the given Archiver device
	 *
	 * @param archiverName
	 * @return the number of scalar in charge
	 * @throws ArchivingException
	 */
	public static int get_scalar_charge(String archiverName) throws ArchivingException
	{
		try
		{
			DeviceProxy archiverProxy = new DeviceProxy(archiverName);
			DeviceAttribute devattr = archiverProxy.read_attribute(m_SCALARCHARGE);
			short[] shortarray = devattr.extractShortArray();
			//System.out.println(m_SCALARCHARGE + " = " + shortarray[ 0 ]);
			return shortarray[ 0 ];
		}
		catch ( DevFailed devFailed )
		{
			/*String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.get_scalar_charge() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);*/
            return Integer.MAX_VALUE;
		}
	}

	/**
	 * get the number of spectrum taken in charge for the given Archiver device
	 *
	 * @param archiverName
	 * @return the number of spectrum in charge
	 */
	public static int get_spectrum_charge(String archiverName)
	{
		try
		{
			DeviceProxy archiverProxy = new DeviceProxy(archiverName);
			DeviceAttribute devattr = archiverProxy.read_attribute(m_SPECTRUMCHARGE);
			short[] shortarray = devattr.extractShortArray();
			//System.out.println(m_SPECTRUMCHARGE + " = " + shortarray[ 0 ]);
			return shortarray[ 0 ];
		}
		catch ( DevFailed devFailed )
		{
			/*DevError[] errorlist = devFailed.errors;
			for ( int i = 0 ; i < errorlist.length ; i++ )
			{
				System.err.println("Description : " + errorlist[ i ].desc);
				System.err.println("Origin		: " + errorlist[ i ].origin);
				System.err.println("Reason		: " + errorlist[ i ].reason);
			}
			return 0;*/
            return Integer.MAX_VALUE;
		}
	}

	/**
	 * get the number of image taken in charge for the given Archiver device
	 *
	 * @param archiverName
	 * @return the image of spectrum in charge
	 */
	public static int get_image_charge(String archiverName)
	{
		try
		{
			DeviceProxy archiverProxy = new DeviceProxy(archiverName);
			DeviceAttribute devattr = archiverProxy.read_attribute(m_IMAGECHARGE);
			short[] shortarray = devattr.extractShortArray();
			//System.out.println(m_IMAGECHARGE + " = " + shortarray[ 0 ]);
			return shortarray[ 0 ];
		}
		catch ( DevFailed devFailed )
		{
			/*DevError[] errorlist = devFailed.errors;
			for ( int i = 0 ; i < errorlist.length ; i++ )
			{
				System.err.println("Description : " + errorlist[ i ].desc);
				System.err.println("Origin		: " + errorlist[ i ].origin);
				System.err.println("Reason		: " + errorlist[ i ].reason);
			}
			return 0;*/
            return Integer.MAX_VALUE;
		}
	}
    
    private static void initDbArchiverList ( boolean isHistoric ) throws ArchivingException
    {
        System.out.println( "initDbArchiverList/isHistoric/"+isHistoric );
        try
        {
            String m_dbDevicePattern = isHistoric ? m_hdbDevicePattern : m_tdbDevicePattern;
            String m_dbClassDevice = isHistoric ? m_hdbClassDevice : m_tdbClassDevice;
            
            boolean facility = isHistoric ? hFacility : tFacility;
            String [] m_dbExportedArchiverList = isHistoric ? m_hdbExportedArchiverList : m_tdbExportedArchiverList;
            //String [] m_dbNotExportedArchiverList = isHistoric ? m_hdbNotExportedArchiverList : m_tdbNotExportedArchiverList;
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
    
	/**
	 * @return @return the number of archivers for the given archiving type
	 */
	public static int getArchiverListSize(boolean historic)
	{
        System.out.println( "CLA/historic/"+historic);
        if ( m_hdbExportedArchiverList == null )
        {
		    System.out.println( "CLA/HDB == null");
        }
        if ( m_tdbExportedArchiverList == null )
        {
            System.out.println( "CLA/TDB == null");
        }
        return ( historic ) ? m_hdbExportedArchiverList.length : m_tdbExportedArchiverList.length;
	}

	/**
	 * this method return AttributeInfo tango class
	 *
	 * @param attributeName
	 * @return AttributeInfo
	 */
	private static AttributeInfo getAttributeInfo(String attributeName, boolean doRefresh) throws DevFailed
	{
        AttributeInfo att_info = null;
        if ( ! doRefresh )
        {
            att_info = getBufferedAttributeInfo ( attributeName );    
        }
        
        if ( att_info == null )
        {
            att_info = getAttributeInfoFromTheDevice ( attributeName );    
        }
        
        if ( doRefresh && att_info != null )
        {
            bufferAttributeInfo ( attributeName , att_info );    
        }
        
		return att_info;
	}
    
    private static void bufferAttributeInfo(String attributeName, AttributeInfo att_info) 
    {
        attributeToDeviceInfo.put ( attributeName , att_info );
    }

    private static AttributeInfo getBufferedAttributeInfo(String attributeName) 
    {
        return (AttributeInfo) attributeToDeviceInfo.get ( attributeName );
    }

    private static AttributeInfo getAttributeInfoFromTheDevice(String attributeName) throws DevFailed
    {
        int index = attributeName.lastIndexOf("/");
        String device_name = attributeName.substring(0 , index);
        DeviceProxy deviceProxy = new DeviceProxy(device_name);
        String att_name = attributeName.substring(index + 1);
        AttributeInfo att_info = deviceProxy.get_attribute_info(att_name);
        return att_info;
    }

	public static String getTangoHost()
	{
		String tangoHost = "Host:port";
		try
		{
			Database db = new Database();
			tangoHost = db.get_tango_host();
		}
		catch ( DevFailed devFailed )
		{
			devFailed.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		return tangoHost;
	}

	/**
	 * this method return data_format value of the attribute
	 *
	 * @param attributeName
	 * @return AttrDataFormat
	 */
	private static int getAttributeDataFormat(String attributeName)
	{
		AttributeInfo attribute = null;
		try
		{
			attribute = getAttributeInfo(attributeName,false);
			return attribute.data_format.value();
		}
		catch ( DevFailed devFailed )
		{
			Util.out2.println("ERROR !! " + "\r\n" +
			                  "\t Origin : \t " + "ArchivingManagerApi.getAttributeDataFormat" + "\r\n" +
			                  "\t Reason : \t " + "UNKNOW_ERROR" + "\r\n" +
			                  "\t Description : \t " + devFailed.getMessage() + "\r\n" +
			                  "\t Additional information : \t " + "Attribute named '" + attributeName + "' not found !" + "\r\n");
		}
		return AttrDataFormat._SCALAR;
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

	/**
	 * This method checks the registration and create the table of the attributes in the database
	 *
	 * @param historic          true if historical archiving, false otherwise.
	 * @param attributeNameList
	 * @return true if the registration works
	 */
	private String[] checkRegistration(boolean historic , String[] attributeNameList) throws ArchivingException
	{
		String tangoHost = getTangoHost();
		Vector myNewListV = new Vector();
		for ( int i = 0 ; i < attributeNameList.length ; i++ )
		{
			String attributeName = attributeNameList[ i ];
			try
			{
				if ( !isRegistred(historic , attributeName) )
				{
					AttributeInfo attributeInfo = getAttributeInfo ( attributeName , false );
					int index = attributeName.lastIndexOf("/");
					String deviceName = attributeName.substring(0 , index);
					String[] attributeSplitName = split_att_name_3_fields(attributeName);
					java.sql.Timestamp time = new java.sql.Timestamp(new java.util.Date().getTime());
					AttributeHeavy attributeHeavy = new AttributeHeavy(attributeName);
					attributeHeavy.setAttribute_complete_name(attributeName); // **************** The whole attribute name (device_name + attribute_name)
					attributeHeavy.setAttribute_device_name(deviceName);
					attributeHeavy.setRegistration_time(time);  // **************** Attribute registration timestamp
					attributeHeavy.setDomain(attributeSplitName[ 1 ]);  // **************** domain to which the attribute is associated
					attributeHeavy.setFamily(attributeSplitName[ 2 ]);  // **************** family to which the attribute is associated
					attributeHeavy.setMember(attributeSplitName[ 3 ]);  // **************** member to which the attribute is associated
					attributeHeavy.setAttribute_name(attributeSplitName[ 4 ]);  // **************** attribute name
					attributeHeavy.setData_type(attributeInfo.data_type);  // **************** Attribute data type
					attributeHeavy.setData_format(attributeInfo.data_format.value());  // **************** Attribute data format
					attributeHeavy.setWritable(attributeInfo.writable.value());  // **************** Attribute read/write type
					attributeHeavy.setMax_dim_x(attributeInfo.max_dim_x);  // **************** Attribute Maximum X dimension
					attributeHeavy.setMax_dim_y(attributeInfo.max_dim_y);  // **************** Attribute Maximum Y dimension
					attributeHeavy.setLevel(attributeInfo.level.value());  // **************** Attribute display level
					attributeHeavy.setCtrl_sys(tangoHost);  // **************** Control system to which the attribute belongs
					attributeHeavy.setArchivable(0);  // **************** archivable (Property that precises whether the attribute is "on-run-only" archivable, or if it is "always" archivable
					attributeHeavy.setSubstitute(0);  // **************** substitute
					attributeHeavy.setDescription(attributeInfo.description);
					attributeHeavy.setLabel(attributeInfo.label);
					attributeHeavy.setUnit(attributeInfo.unit);
					attributeHeavy.setStandard_unit(attributeInfo.standard_unit);
					attributeHeavy.setDisplay_unit(attributeInfo.display_unit);
					attributeHeavy.setFormat(attributeInfo.format);
					attributeHeavy.setMin_value(attributeInfo.min_value);
					attributeHeavy.setMax_value(attributeInfo.max_value);
					attributeHeavy.setMin_alarm(attributeInfo.min_alarm);
					attributeHeavy.setMax_alarm(attributeInfo.max_alarm);

					if ( historic )
					{
						m_hdbDataBase.registerAttribute(attributeHeavy);
					}
					else
					{
						m_tdbDataBase.registerAttribute(attributeHeavy);
					}
				}
				myNewListV.add(attributeName);
			}
			catch ( DevFailed devFailed )
			{
				String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.DEV_UNREACH_EXCEPTION + " (" + attributeName + ")";
				String reason = GlobalConst.TANGO_COMM_EXCEPTION;
				String desc = "Failed while executing ArchivingManagerApi.checkRegistration() method...";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
			}
			catch ( ArchivingException e )
			{
				throw e;
			}
		}
		return toStringArray(myNewListV);
	}

	private static String[] split_att_name_3_fields(String att_name)
	{
		String host = "";
		String domain = "";
		String family = "";
		String member = "";
		String attribut = "";
		String[] argout = new String[ 5 ];// = {"HOST", "DOMAIN", "FAMILY", "MEMBER", "ATTRIBUT"};
		String[] decoupe; // d�coupage en 5 partie : host, domain, family, member, attribut

		// Host name management
		if ( att_name.startsWith("//") )
			att_name = att_name.substring(2 , att_name.length());
		else
			att_name = getTangoHost() + "/" + att_name;

		// Spliting
		decoupe = att_name.split("/"); // spliting the name in 3 fields
		host = decoupe[ 0 ];
		domain = decoupe[ 1 ];
		family = decoupe[ 2 ];
		member = decoupe[ 3 ];
		attribut = decoupe[ 4 ];

		argout[ 0 ] = host;
		argout[ 1 ] = domain;
		argout[ 2 ] = family;
		argout[ 3 ] = member;
		argout[ 4 ] = attribut;
		return argout;
	}

	/**
	 * This method allows to open the connection on either the historic or the temporary database
	 *
	 * @param historic  true if historical archiving, false otherwise.
	 * @param muser
	 * @param mpassword
	 * @throws ArchivingException
	 */
	private void connectArchivingDatabase(boolean historic , String muser , String mpassword) throws ArchivingException
	{
        if ( ( ( historic ) ? m_hdbDataBase : m_tdbDataBase ) == null )
		{
			// Host
			String host = null;
			try
			{
				host = GetConf.getHost(( historic ) ? m_hdbClassDevice : m_tdbClassDevice);
				if ( host.equals("") )
                {
					host = ( historic ) ? ConfigConst.HDB_HOST : ConfigConst.TDB_HOST;
                }
			}
			catch ( ArchivingException e )
			{
				System.err.println(e.toString());
				host = ( historic ) ? ConfigConst.HDB_HOST : ConfigConst.TDB_HOST;
			}

			// Name
			String name = null;
			try
			{
                name = GetConf.getName(( historic ) ? m_hdbClassDevice : m_tdbClassDevice);
				if ( name.equals("") )
                {
					name = ( historic ) ? ConfigConst.HDB_SCHEMA_NAME : ConfigConst.TDB_SCHEMA_NAME;
                }

			}
			catch ( ArchivingException e )
			{
				System.err.println(e.toString());
				name = ( historic ) ? ConfigConst.HDB_SCHEMA_NAME : ConfigConst.TDB_SCHEMA_NAME;
			}

			// Schema
			String schema = null;
			try
			{
				schema = GetConf.getSchema(( historic ) ? m_hdbClassDevice : m_tdbClassDevice);
				if ( schema.equals("") )
					schema = ( historic ) ? ConfigConst.HDB_SCHEMA_NAME : ConfigConst.TDB_SCHEMA_NAME;
			}
			catch ( ArchivingException e )
			{
				System.err.println(e.toString());
				schema = ( historic ) ? ConfigConst.HDB_SCHEMA_NAME : ConfigConst.TDB_SCHEMA_NAME;
			}

			// User
			String user = muser;
			if ( user.equals("") )
				user = ( historic ) ? ConfigConst.HDB_BROWSER_USER : ConfigConst.TDB_BROWSER_USER;

			String pass = mpassword;
			if ( pass.equals("") )
				pass = ( historic ) ? ConfigConst.HDB_BROWSER_PASSWORD : ConfigConst.TDB_BROWSER_PASSWORD;

			if ( historic )
			{
				m_hdbDataBase = new DataBaseApi(host , name , schema , user , pass);
				m_hdbDataBase.connect_auto();
                m_hdbDataBase.setHistoric ( historic );
				is_hdb_connected = true;
			}
			else
			{
				m_tdbDataBase = new DataBaseApi(host , name , schema , user , pass);
				m_tdbDataBase.connect_auto();
                m_tdbDataBase.setHistoric ( historic );
				is_tdb_connected = true;
			}
		}
	}

	/**
	 * Close the connection of the given type archiving the database
	 *
	 * @param historic
	 */
	public void closeDatabase(boolean historic)
	{
		if ( historic )
		{
			try
			{
				if ( m_hdbDataBase != null )
				{
					m_hdbDataBase.close();
					m_hdbDataBase = null;
					is_hdb_connected = false;
				}
			}
			catch ( ArchivingException e )
			{
				m_hdbDataBase = null;
			}
		}
		else
		{
			try
			{
				if ( m_tdbDataBase != null )
				{
					m_tdbDataBase.close();
					m_tdbDataBase = null;
					is_tdb_connected = false;
				}
			}
			catch ( ArchivingException e )
			{
				m_tdbDataBase = null;
			}
		}
	}

	/**
	 * Configure the Archiving
	 *
	 * @param user
	 * @param password
	 * @param historic
	 * @throws ArchivingException
	 */
	public void ArchivingConfigure(String user , String password , boolean historic) throws ArchivingException
	{
		System.out.println( "ArchivingConfigure/historic/"+historic);
		
		ArchivingConfigureWithoutArchiverListInit( user ,  password ,  historic);
        
        // The created lists are useful during the start archiving action 
        initDbArchiverList ( historic );
	}
    
	public void ArchivingConfigureWithoutArchiverListInit(String user , String password , boolean historic) throws ArchivingException
	{
		System.out.println( "ArchivingConfigureWithoutArchiverListInit/historic/"+historic+"/user/"+user);
		if ( historic )
		{
			hFacility = getFacility(historic);
		}
		else
		{
			tFacility = getFacility(historic);
		}

		// Database connection
		connectArchivingDatabase(historic , user , password);
	}
	
	/**
	 * @param historic
	 */
	public DataBaseApi getDataBase(boolean historic)
	{
		if ( historic )
			return m_hdbDataBase;
		else
			return m_tdbDataBase;
	}

	/**
	 * @param my_vector
	 */
	public static String[] toStringArray(Vector my_vector)
	{
		String[] my_array;
		my_array = new String[ my_vector.size() ];
		for ( int i = 0 ; i < my_vector.size() ; i++ )
		{
			my_array[ i ] = ( String ) my_vector.elementAt(i);
		}
		return my_array;
	}

	/**
	 * @param my_vector
	 */
	public static String[] toStoppingArrayAttLightMode(Vector my_vector)
	{
		Vector myResultVect = new Vector();

		for ( int i = 0 ; i < my_vector.size() ; i++ )
		{
			AttributeLightMode attributeLightMode = ( AttributeLightMode ) my_vector.elementAt(i);
			String[] attributeLightMode_Arr = attributeLightMode.toArray();
			myResultVect.add(Integer.toString(attributeLightMode_Arr.length));
			for ( int j = 0 ; j < attributeLightMode_Arr.length ; j++ )
			{
				myResultVect.add(attributeLightMode_Arr[ j ]);
			}
		}
		return toStringArray(myResultVect);
	}

	/**
	 * @param my_light_attributs
	 */
	public static Vector stoppingVector(String[] my_light_attributs)
	{
		Vector myResultVect = new Vector();
		int index = 0;
		while ( index < my_light_attributs.length )
		{
			int myAttLenght = Integer.parseInt(my_light_attributs[ index ]);
			index++;
			String[] myLightAttribut = new String[ myAttLenght ];
			System.arraycopy(my_light_attributs , index , myLightAttribut , 0 , myAttLenght);
			index = index + myAttLenght;
			AttributeLightMode attributeLightMode = AttributeLightMode.creationWithFullInformation(myLightAttribut);
			myResultVect.add(attributeLightMode);
		}
		return myResultVect;
	}

	public static boolean checkAttributeSupport(String attributeName) throws ArchivingException
	{
		try
		{
			AttributeInfo attributeInfo = getAttributeInfo ( attributeName , true);
			int data_type = attributeInfo.data_type;
			int data_format = attributeInfo.data_format.value();
			int writable = attributeInfo.writable.value();
			return AttributeSupport.checkAttributeSupport(attributeName , data_type , data_format , writable);
		}
		catch ( DevFailed
		        devFailed )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.TANGO_COMM_EXCEPTION + " or " + GlobalConst.ATT_UNREACH_EXCEPTION;
			String reason = "Failed while executing ArchivingManagerApi.checkAttributeSupport() method...";
			String desc = "Failed while retrieving informations for the attribute named '" + attributeName + "'";
			throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , DevFailed.class.getName() , devFailed);
		}
	}


	/**
	 * PART Tango side queries
	 */

	public static Vector getDomains() throws ArchivingException
	{
		try
		{
			Database database = ApiUtil.get_db_obj();
			Vector domains = new Vector();
			String[] res_domains = database.get_device_domain("*");
			for ( int i = 0 ; i < res_domains.length ; i++ )
			{
				String res_domain = res_domains[ i ];
				if ( !res_domain.equals("dserver") )
					domains.add(res_domain);
			}
			return domains;
		}
		catch ( DevFailed devFailed )
		{
			System.out.println("ArchivingManagerApi.getDomains");
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.getDomains() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}
	
	public static Vector getDomains ( String domainsRegExp ) throws ArchivingException
	{
		try
		{
			Database database = ApiUtil.get_db_obj();
			Vector domains = new Vector();
			String[] res_domains = database.get_device_domain ( domainsRegExp /* + "*" */ );
			for ( int i = 0 ; i < res_domains.length ; i++ )
			{
				String res_domain = res_domains[ i ];
				if ( !res_domain.equals("dserver") )
					domains.add(res_domain);
			}
			return domains;
		}
		catch ( DevFailed devFailed )
		{
			System.out.println("ArchivingManagerApi.getDomains");
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.getDomains() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}

	private static String[] getFamilies(String domain) throws ArchivingException
	{
		try
		{
			Database database = ApiUtil.get_db_obj();
			return database.get_device_family(domain + "/*");
		}
		catch ( DevFailed devFailed )
		{
			System.out.println("ArchivingManagerApi.getFamilies");
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.getFamilies() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}

	private static String[] getMembers(String domain , String family) throws ArchivingException
	{
		try
		{
			Database database = ApiUtil.get_db_obj();
			return database.get_device_member(domain + "/" + family + "/*");
		}
		catch ( DevFailed devFailed )
		{
			System.out.println("ArchivingManagerApi.getMembers");
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.getMembers() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}

	private static Vector buildDevicesList(String domain) throws ArchivingException
	{
		try
		{
			Vector devList = new Vector();
			String[] families = getFamilies(domain);
			for ( int i = 0 ; i < families.length ; i++ )
			{
				String[] members = getMembers(domain , families[ i ]);
				for ( int j = 0 ; j < members.length ; j++ )
				{
					String new_dev = domain + "/" + families[ i ] + "/" + members[ j ];
					devList.add(new_dev);
				}
			}
			return devList;
		}
		catch ( ArchivingException e )
		{
			System.out.println("ArchivingManagerApi.buildDevicesList");
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.buildDevicesList() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , e);
		}
	}

	private static Vector getDServersList(Vector devices) throws ArchivingException
	{
		try
		{
            Database database = ApiUtil.get_db_obj();
			
            Vector dservers = new Vector();
			for ( int i = 0 ; i < devices.size() ; i++ )
			{
				String dev = ( String ) devices.elementAt(i);
				dservers.add(( database.get_device_info(dev) ).server);
			}
            
			return dservers;
		}
		catch ( DevFailed devFailed )
		{
			System.out.println("ArchivingManagerApi.getDServersList");
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.getDServersList() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
    }

    public static Hashtable getClassAndDevices(String domain) throws ArchivingException
    {
        try
        {
            long innerMostLoop = 0;
            
            Database database = ApiUtil.get_db_obj();
            Vector devicesCapitalised = new Vector ();
            Hashtable deviceClassToDevices = new Hashtable ();

            String[] families = getFamilies(domain);            
            for ( int i = 0 ; i < families.length ; i++ )
            {
                String[] members = getMembers(domain , families[ i ]);
                
                for ( int j = 0 ; j < members.length ; j++ )
                {
                    String new_dev = domain + "/" + families[ i ] + "/" + members[ j ];
                    devicesCapitalised.add ( new_dev.toUpperCase () );
                    
                    String dServer = ( database.get_device_info(new_dev) ).server;
                    String[] res = database.get_device_class_list ( dServer );
                    
                    //long beforeInnerMostLoop = System.currentTimeMillis ();
                    for ( int k = 0 ; k < res.length / 2 ; k++ )
                    {
                        String device = res[ 2*k ];
                        String deviceClass = res[ 2*k + 1 ];
                        //System.out.println ( "CLA/getClassesList/j|"+j+"|device|"+device+"|deviceClass|"+deviceClass );
                        if ( devicesCapitalised.indexOf ( device.toUpperCase () ) == -1 )
                        {
                            continue;
                        }
                        
                        Vector devicesForTheCurrentDeviceClass = ( Vector ) deviceClassToDevices.get(deviceClass);
                        if ( devicesForTheCurrentDeviceClass == null )
                        {
                            devicesForTheCurrentDeviceClass = new Vector ();                        
                            deviceClassToDevices.put ( deviceClass , devicesForTheCurrentDeviceClass );
                        }

                        devicesForTheCurrentDeviceClass.add ( device );
                    }
                    /*long afterInnerMostLoop = System.currentTimeMillis ();
                    innerMostLoop += ( afterInnerMostLoop - beforeInnerMostLoop );*/
                }
            }
            
            //System.out.println ( "---------------------------------------/innerMostLoop|"+ innerMostLoop );
            return deviceClassToDevices;
        }
        catch ( DevFailed devFailed )
        {
            System.out.println("ArchivingManagerApi.getClassAndDevices5");
            String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
            String reason = GlobalConst.TANGO_COMM_EXCEPTION;
            String desc = "Failed while executing ArchivingManagerApi.getClassAndDevices5() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
        }
    }
    
	
	/**
	 * @param dservers
	 * @param devices
	 * @return
	 * @throws ArchivingException
	 */
	private static Hashtable arrangeDevByClass(Vector dservers, Vector devices) throws ArchivingException 
	{
		try
		{
			Enumeration enumDevices = devices.elements ( );
			Vector devicesCapitalised = new Vector ( devices.size () );
			while (enumDevices.hasMoreElements ()  )
			{
				String nextDevice = (String) enumDevices.nextElement ();
				//System.out.println ( "CLA/getClassesList/nextDevice|"+nextDevice+"|" );
				devicesCapitalised.add ( nextDevice.toUpperCase () );
			}
			
			Database database = ApiUtil.get_db_obj();
			//Vector classes = new Vector();
			
			Hashtable deviceToDeviceClass = new Hashtable ();
			Hashtable deviceClassToDevices = new Hashtable ();
			
			for ( int i = 0 ; i < dservers.size() ; i++ )
			{
				String ds = ( String ) dservers.elementAt(i);
				String[] res = database.get_device_class_list(ds);
				
				for ( int j = 0 ; j < res.length / 2 ; j++ )
				{
					String device = res[ 2*j ];
					String deviceClass = res[ 2*j + 1 ];
					//System.out.println ( "CLA/getClassesList/j|"+j+"|device|"+device+"|deviceClass|"+deviceClass );

					String deviceCapitalised = device.toUpperCase ();
					if ( devicesCapitalised.indexOf ( deviceCapitalised ) == -1 )
					{
						//System.out.println ( "		INCONNU|"+device+"|" );
						continue;
					}
					deviceToDeviceClass.put ( device , deviceClass );
					
					boolean testContainsIgnoreCase = false;
					Enumeration en = deviceClassToDevices.keys ();
					while ( en.hasMoreElements () )
					{
						String nextKey = (String) en.nextElement ();
						if ( nextKey.equalsIgnoreCase ( deviceClass ) )
						{
							testContainsIgnoreCase = true;
							break;
						}
					}
					
					if ( ! testContainsIgnoreCase )
					{
						deviceClassToDevices.put(deviceClass , new Vector());
					}
                   
                    Vector devicesForTheCurrentDeviceClass = ( Vector ) deviceClassToDevices.get(deviceClass);
                    if ( devicesForTheCurrentDeviceClass != null )
                    {
                        devicesForTheCurrentDeviceClass.add ( device );
                    }
				}
			}
			
			return deviceClassToDevices;
		}
		catch ( DevFailed devFailed )
		{
			System.out.println("ArchivingManagerApi.getClassesList");
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.getClassesList() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}

	private static void traceXX(Hashtable deviceClassToDevices) 
    {
        if ( deviceClassToDevices == null )
        {
            System.out.println ( "traceXX/NULL!!" );
        }
        Enumeration e = deviceClassToDevices.keys ();
        System.out.println ( "Device classes/START" );
        while ( e.hasMoreElements( ) )
        {
            String nextKey =(String) e.nextElement ();
            System.out.println ( "    Device class START|" + nextKey+ "|" );
            Vector nextVal =(Vector) deviceClassToDevices.get ( nextKey );
            if ( nextVal != null )
            {
                Enumeration e2 =nextVal.elements ();
                while ( e2.hasMoreElements () )
                {
                    String nextDev = (String) e2.nextElement ();
                    System.out.println ( "        Device|" + nextDev+ "|" );
                }
            }
            System.out.println ( "    Device class END|" + nextKey+ "|" );
            
        }
        System.out.println ( "Device classes/END" );
    }

    public static String[] getAttributesForClass(String classe , Vector devOfDomain) throws ArchivingException
	{
		String[] exported_dev;
		String[] att_list = new String[ 5 ];
		try
		{
			Database database = ApiUtil.get_db_obj();
			DeviceProxy deviceProxy;
			exported_dev = database.get_device_exported_for_class(classe);
			for ( int i = 0 ; i < exported_dev.length ; i++ )
			{
				String current_dev = exported_dev[ i ];
				if ( devOfDomain.contains(current_dev) )
				{
					try
					{
						deviceProxy = new DeviceProxy(current_dev);
						att_list = deviceProxy.get_attribute_list();
						break;
					}
					catch ( DevFailed devFailed )
					{
						System.out.println("Unable to reach " + current_dev);

					}
				}
			}
			return att_list;
		}
		catch ( DevFailed devFailed )
		{
			System.out.println("ArchivingManagerApi.getAttributesForClass");
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION;
			String desc = "Failed while executing ArchivingManagerApi.getAttributesForClass() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}
    
    public static String [] getExportedArchiversList ( boolean historic ) throws ArchivingException
    {
        return historic ? m_hdbExportedArchiverList : m_tdbExportedArchiverList;
    }
    
    private static String [] getNotExportedArchiversList ( boolean historic ) throws ArchivingException
    {
        return historic ? m_hdbNotExportedArchiverList : m_tdbNotExportedArchiverList;
    }
    
    public static Hashtable getAttributesToDedicatedArchiver ( boolean historic , boolean refreshArchivers ) throws ArchivingException
    {
        if ( refreshArchivers )
        {
            initDbArchiverList ( historic );
        }
     
        String [] exportedArchiversNameList = getExportedArchiversList ( historic );
        String [] notExportedArchiversNameList = getNotExportedArchiversList ( historic );
        if ( exportedArchiversNameList.length == 0 && notExportedArchiversNameList.length == 0 )
        {
            return null;
        }
        
        Archiver [] achiversList = new Archiver [ exportedArchiversNameList.length + notExportedArchiversNameList.length ];
        for ( int i = 0 ; i < exportedArchiversNameList.length ; i ++ )
        {
            String currentExportedArchiverName = exportedArchiversNameList [ i ];
            Archiver currentExportedArchiver = Archiver.findArchiver ( currentExportedArchiverName , historic );
            currentExportedArchiver.setExported ( true );
            achiversList [ i ] = currentExportedArchiver;
        }
        for ( int i = 0 ; i < notExportedArchiversNameList.length ; i ++ )
        {
            String currentNotExportedArchiverName = notExportedArchiversNameList [ i ];
            Archiver currentNotExportedArchiver = Archiver.findArchiver ( currentNotExportedArchiverName , historic );
            currentNotExportedArchiver.setExported ( false );
            achiversList [ i + exportedArchiversNameList.length ] = currentNotExportedArchiver;
        }

        Hashtable ret = dispatchAttributeByArchiver ( achiversList );
        Archiver.setAttributesToDedicatedArchiver ( historic , ret );
        return ret;
    }
    
    
    /*private static String[] getAllArchiversList(boolean historic) throws ArchivingException 
    {
        String [] exported = getExportedArchiversList ( historic );
        String [] notExported = getNotExportedArchiversList ( historic );
        
        int exportedLength = exported == null ? 0 : exported.length;
        int notExportedLength = notExported == null ? 0 : notExported.length;
        String [] all = new String [ exportedLength + notExportedLength ];
        
        for ( int i = 0 ; i < exportedLength ; i++ )
        {
            all [ i ] = exported [ i ];
        }
        for ( int i = 0 ; i < notExportedLength ; i++ )
        {
            all [ i + exportedLength ] = notExported [ i ];
        }
        
        return all;
    }*/

    private static Hashtable dispatchAttributeByArchiver(Archiver[] archiversList) 
    {
        if ( archiversList == null || archiversList.length == 0 )
        {
            System.out.println ( "ArchivingManagerApi/dispatchAttributeByArchiver/REPERE 1|" );
            return null;
        }
        
        Hashtable ret = new Hashtable ();
        for ( int i = 0 ; i < archiversList.length ; i ++ )
        {
            Archiver currentArchiver = archiversList [ i ];
            boolean isDedicated = currentArchiver.isDedicated ();
            if ( !isDedicated )
            {
                continue;
            }
            
            String [] reservedAttributes = currentArchiver.getReservedAttributes ();
            if ( reservedAttributes == null || reservedAttributes.length == 0 )
            {
                continue;
            }
            
            for ( int j = 0 ; j < reservedAttributes.length ; j ++ )
            {
                ret.put ( reservedAttributes [ j ] , currentArchiver );
            }
        }
        
        return ret;
    }
    
    private static void initArchivers(LoadBalancedList loadBalancedList,boolean grouped, boolean historic,String[] referenceArchiverList) throws ArchivingException 
    {
        if ( referenceArchiverList.length == 0 )
        {
            String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
            String reason = GlobalConst.NO_ARC_EXCEPTION;
            String desc = "Failed while executing ArchivingManagerApi.selectArchiver() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "");
        }

        // LoadBalancedList archivers initializing
        if ( grouped )
        {
            loadBalancedList.addArchiver(referenceArchiverList[ 0 ] ,
                    ArchivingManagerApi.get_scalar_charge(referenceArchiverList[ 0 ]) ,
                    ArchivingManagerApi.get_spectrum_charge(referenceArchiverList[ 0 ]) ,
                    ArchivingManagerApi.get_image_charge(referenceArchiverList[ 0 ]));
        }
        else
        {
            for ( int i = 0 ; i < referenceArchiverList.length ; i++ )
            {
                loadBalancedList.addArchiver(referenceArchiverList[ i ] ,
                                             ArchivingManagerApi.get_scalar_charge(referenceArchiverList[ i ]) ,
                                             ArchivingManagerApi.get_spectrum_charge(referenceArchiverList[ i ]) ,
                                             ArchivingManagerApi.get_image_charge(referenceArchiverList[ i ]));
            }
        }
    }
}



