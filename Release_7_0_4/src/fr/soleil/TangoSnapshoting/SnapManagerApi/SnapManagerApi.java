//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapManagerApi.
//						(Chinkumo Jean) - Nov 2, 2004
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.18  2007/03/14 15:43:50  ounsy
// modified launchSnap2Archiver to no longer wait after launching a snapshot
//
// Revision 1.17  2007/01/22 10:47:13  ounsy
// added a giveup condition in launchSnap2Archiver
//
// Revision 1.16  2006/11/20 09:27:18  ounsy
// minor changes
//
// Revision 1.15  2006/10/31 16:54:24  ounsy
// milliseconds and null values management
//
// Revision 1.14  2006/10/30 14:37:21  ounsy
// minor change
//
// Revision 1.13  2006/06/28 12:43:58  ounsy
// image support
//
// Revision 1.12  2006/05/17 10:13:08  ounsy
// added the name of the attribute the command failed on for the TriggerSetEquipment method
//
// Revision 1.11  2006/05/04 14:32:38  ounsy
// minor changes (commented useless methods and variables)
//
// Revision 1.10  2006/04/13 12:46:48  ounsy
// spectrum management updated
//
// Revision 1.9  2006/04/12 15:32:59  ounsy
// added a wait loop in launchSnap2Archiver so that the snap is completely loaded
//
// Revision 1.8  2006/02/17 09:29:50  chinkumo
// Minor change : code reformated.
//
// Revision 1.7  2006/02/15 09:02:56  ounsy
// Spectrums Management
//
// Revision 1.6  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.5.2.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.5  2005/08/19 14:04:02  chinkumo
// no message
//
// Revision 1.4.6.1.2.1  2005/08/11 08:16:49  chinkumo
// The 'SetEquipement' command and thus functionnality was added.
//
// Revision 1.4.6.1  2005/08/01 13:49:57  chinkumo
// Several changes carried out for the support of the new graphical application (Bensikin).
//
// Revision 1.4  2005/06/28 09:10:16  chinkumo
// Changes made to improve the management of exceptions were reported here.
//
// Revision 1.3  2005/06/24 12:04:18  chinkumo
// The fr.soleil.TangoSnapshoting.SnapshotingApi.DataBaseAPI.crateNewSnap method was renamed into createNewSnap.
// This change was reported here.
//
// Revision 1.2  2005/01/26 15:35:37  chinkumo
// Ultimate synchronization before real sharing.
//
// Revision 1.1  2004/12/06 17:39:56  chinkumo
// First commit (new API architecture).
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================

package fr.soleil.TangoSnapshoting.SnapManagerApi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.AttributeInfo;
import fr.esrf.TangoApi.AttributeProxy;
import fr.esrf.TangoApi.CommandInfo;
import fr.esrf.TangoApi.Database;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoDs.TangoConst;
import fr.esrf.TangoDs.Util;
import fr.soleil.TangoSnapshoting.SnapshotingApi.ConfigConst;
import fr.soleil.TangoSnapshoting.SnapshotingApi.DataBaseAPI;
import fr.soleil.TangoSnapshoting.SnapshotingApi.GetConf;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.Criterions;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.GlobalConst;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeExtract;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeHeavy;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeLight;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapContext;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapShot;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapShotLight;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapshotingException;

public class SnapManagerApi
{
	//Command name
	private static final String m_LAUNCHSNAP = "LaunchSnapShot";
	private static final String m_CREATECONTEXT = "CreateNewContext";

	//Snap Data Base
	private static DataBaseAPI m_snapDataBase = null;

	private static final String m_snapArchiverClassDevice = "SnapArchiver";
	private static String[] m_snapArchiverList;
	private static final String m_snapManagerClassDevice = "SnapManager";
	private static String[] m_snapManagerList;
	private static final String m_snapBrowserClassDevice = "SnapExtractor";
	private static String[] m_snapBrowserList;
	//private static boolean sFacility = false;
	//private static boolean is_snap_connected = false;
	//============================================================
	//Accessors

	/**
	 * @return the Host of the snap database
	 */
	public static String getSnapHost()
	{
		if ( m_snapDataBase != null )
			return m_snapDataBase.getHost();
		else
			return "";
	}

	/**
	 * @return the name of the snap database
	 */
	public static String getSnapDatabaseName()
	{
		if ( m_snapDataBase != null )
			return m_snapDataBase.getDbName();
		else
			return "";
	}

	/**
	 * @return the user of the snap database
	 */
	public static String getSnapUser()
	{
		if ( m_snapDataBase != null )
			return m_snapDataBase.getUser();
		else
			return "";
	}

	/**
	 * @return the password of the snap database
	 */
	public static String getSnapPassword()
	{
		if ( m_snapDataBase != null )
			return m_snapDataBase.getPassword();
		else
			return "";
	}

	/**
	 * @return the snap database type (MySQL, ORACLE)...
	 */
	public static int getSnapDbType()
	{
		if ( m_snapDataBase != null )
			return m_snapDataBase.getDb_type();
		return -1;
	}

	/**
	 * @return the facility property for that type of archiving.
	 */
	private static boolean getFacility()
	{
		boolean facility = false;
		try
		{
			facility = GetConf.getFacility(m_snapArchiverClassDevice);
		}
		catch ( SnapshotingException e )
		{
			System.err.println(e.toString());
		}
		return facility;
	}

	//============================================================
	//Methodes

	/**
	 * initialize snapManagerApi
	 */
	public static void Init()
	{
		closeSnapDatabase();
	}

	/**
	 * This method returns the name of one of the running devices, according to the given class
	 *
	 * @param deviceClass The device's class
	 * @return The device's name
	 */
	private static String chooseDevice(String deviceClass)
	{
		String device_name = "";
		String[] devicesList = null;
		if ( deviceClass == m_snapArchiverClassDevice )
		{
			initDeviceList(m_snapArchiverClassDevice);
			devicesList = m_snapArchiverList;
		}
		else if ( deviceClass == m_snapManagerClassDevice )
		{
			initDeviceList(m_snapManagerClassDevice);
			devicesList = m_snapManagerList;
		}
		else if ( deviceClass == m_snapBrowserClassDevice )
		{
			initDeviceList(m_snapBrowserClassDevice);
			devicesList = m_snapBrowserList;
		}
		Random hasard = new Random(System.currentTimeMillis());
		if ( devicesList.length > 0 )
		{
			int choosed_index = hasard.nextInt(devicesList.length);
			device_name = devicesList[ choosed_index ];
		}
		return device_name;
	}

	/**
	 * Returns the attribute registred status
	 *
	 * @param attributeName the complete name of the attribute
	 * @return true if the attribute has been registered
	 */
	/*private static boolean isRegistred(String attributeName)
	{
		try
		{
			if ( m_snapDataBase != null )
			{
				if ( m_snapDataBase.isRegistered(attributeName) )
					return true;
			}
		}
		catch ( SnapshotingException e )
		{
			System.err.println(e.toString());
			return false;
		}
		return false;
	}*/

	/**
	 * This method gets all the running SnapArchivers and stores the name in the m_snapArchiverList
	 */
	private static void initDeviceList(String deviceClass)
	{
		try
		{
            Database dbase = ApiUtil.get_db_obj ();
			String[] runningDeviceList = dbase.get_device_exported("*" + deviceClass + "*");
			String[] myRunningDeviceList = new String[ runningDeviceList.length ];
			int j = 0;
			for ( int i = 0 ; i < runningDeviceList.length ; i++ )
			{
				if ( deviceLivingTest(runningDeviceList[ i ] , deviceClass) )
				{
					myRunningDeviceList[ j ] = runningDeviceList[ i ];
					j++;
				}
			}

			if ( deviceClass == m_snapArchiverClassDevice )
			{	// SnapArchiverList building
				m_snapArchiverList = new String[ j ];
				for ( int i = 0 ; i < j ; i++ )
					m_snapArchiverList[ i ] = myRunningDeviceList[ i ];
			}
			else if ( deviceClass == m_snapManagerClassDevice )
			{	// SnapManagerList building
				m_snapManagerList = new String[ j ];
				for ( int i = 0 ; i < j ; i++ )
					m_snapManagerList[ i ] = myRunningDeviceList[ i ];
			}
			else if ( deviceClass == m_snapBrowserClassDevice )
			{	// SnapBrowserList building
				m_snapBrowserList = new String[ j ];
				for ( int i = 0 ; i < j ; i++ )
					m_snapBrowserList[ i ] = myRunningDeviceList[ i ];
			}
		}
		catch ( DevFailed e )
		{
			Util.out2.println("ERROR !! " + "\r\n" +
			                  "\t Origin : \t " + "SnapManagerApi.initDeviceList" + "\r\n" +
			                  "\t Reason : \t " + "SNAP_FAILURE" + "\r\n" +
			                  "\t Description : \t " + e.getMessage() + "\r\n" +
			                  "\t Additional information : \t " + "Failed initializing " + deviceClass + "'s list" + "\r\n");
		}
	}

	/**
	 * This method returns AttributeInfo tango class
	 *
	 * @param attributeName
	 * @return AttributeInfo
	 */
	/*private static AttributeInfo getAttributeInfo(String attributeName)
	{
		try
		{
			int index = attributeName.lastIndexOf("/");
			String device_name = attributeName.substring(0 , index);

			DeviceProxy deviceProxy = new DeviceProxy(device_name);
			String att_name = attributeName.substring(index + 1);
			AttributeInfo att_info = deviceProxy.get_attribute_info(att_name);
			return att_info;
		}
		catch ( DevFailed devFailed )
		{
			System.err.println("ERROR !! " + "\r\n" +
			                   "\t Origin : \t " + "SnapManagerApi.getAttributeInfo" + "\r\n" +
			                   "\t Reason : \t " + "SNAP_FAILURE" + "\r\n" +
			                   "\t Description : \t " + devFailed.getMessage() + "\r\n" +
			                   "\t Additional information : \t " + "Failed retrieving attribute's info..." + "\r\n");
			return null;
		}
	}*/

	/**
	 * This method returns data_format value of the attribute
	 *
	 * @param attributeName
	 * @return AttrDataFormat
	 */
	/*private static int getAttributeDataFormat(String attributeName)
	{
		AttributeInfo attribute = getAttributeInfo(attributeName);
		if ( attribute != null )
			return attribute.data_format.value();

		return AttrDataFormat._SCALAR;
	}*/

	/**
	 * Tests if the given device is alive
	 *
	 * @param deviceName
	 * @return true if the device is running
	 */
	private static boolean deviceLivingTest(String deviceName , String deviceClass)
	{
		try
		{
			DeviceProxy deviceProxy = new DeviceProxy(deviceName);
			deviceProxy.ping();

			//verification de l existance d une commande
			CommandInfo[] commandList = deviceProxy.command_list_query();
			if ( deviceClass == m_snapArchiverClassDevice )
			{
				for ( int i = 0 ; i < commandList.length ; i++ )
				{
					if ( m_LAUNCHSNAP.equals(commandList[ i ].cmd_name) )
						return true;
				}
			}
			else if ( deviceClass == m_snapManagerClassDevice )
			{
				for ( int i = 0 ; i < commandList.length ; i++ )
				{
					if ( m_CREATECONTEXT.equals(commandList[ i ].cmd_name) )
						return true;
				}
			}
			return false;
		}
		catch ( DevFailed devFailed )
		{
			System.err.println("ERROR !! " + "\r\n" +
			                   "\t Origin : \t " + "SnapManagerApi.deviceLivingTest" + "\r\n" +
			                   "\t Reason : \t " + "SNAP_FAILURE" + "\r\n" +
			                   "\t Description : \t " + devFailed.getMessage() + "\r\n" +
			                   "\t Additional information : \t " + "The device " + deviceName + " does not answer..." + "\r\n");
			return false;
		}
	}

	/**
	 * This method checks the registration and create the table of the attributes in the database
	 *
	 * @param attributeNameList
	 * @return true if the registration works
	 */
	/*private static boolean checkRegistration(String[] attributeNameList) throws SnapshotingException
	{

		for ( int i = 0 ; i < attributeNameList.length ; i++ )
		{
			String attributeName = attributeNameList[ i ];
			try
			{
				if ( !isRegistred(attributeName) )
				{
					AttributeInfo attributeInfo = getAttributeInfo(attributeName);
					int index = attributeName.lastIndexOf("/");
					String deviceName = attributeName.substring(0 , index);
					String[] attributeSplitName = split_att_name_3_fields(attributeName);
					java.sql.Timestamp time = new java.sql.Timestamp(new java.util.Date().getTime());
					SnapAttributeHeavy snapAttributeHeavy = new SnapAttributeHeavy(attributeName);
					snapAttributeHeavy.setAttribute_complete_name(attributeName); // **************** The whole attribute name (device_name + attribute_name)
					snapAttributeHeavy.setAttribute_device_name(deviceName);
					snapAttributeHeavy.setRegistration_time(time);  // **************** Attribute registration timestamp
					snapAttributeHeavy.setDomain(attributeSplitName[ 1 ]);  // **************** domain to which the attribute is associated
					snapAttributeHeavy.setFamily(attributeSplitName[ 2 ]);  // **************** family to which the attribute is associated
					snapAttributeHeavy.setMember(attributeSplitName[ 3 ]);  // **************** member to which the attribute is associated
					snapAttributeHeavy.setAttribute_name(attributeSplitName[ 4 ]);  // **************** attribute name
					snapAttributeHeavy.setData_type(attributeInfo.data_type);  // **************** Attribute data type
					snapAttributeHeavy.setData_format(attributeInfo.data_format.value());  // **************** Attribute data format
					snapAttributeHeavy.setWritable(attributeInfo.writable.value());  // **************** Attribute read/write type
					snapAttributeHeavy.setMax_dim_x(attributeInfo.max_dim_x);  // **************** Attribute Maximum X dimension
					snapAttributeHeavy.setMax_dim_y(attributeInfo.max_dim_y);  // **************** Attribute Maximum Y dimension
					snapAttributeHeavy.setLevel(attributeInfo.level.value());  // **************** Attribute display level
					snapAttributeHeavy.setCtrl_sys(attributeSplitName[ 0 ]);  // **************** Control system to which the attribute belongs
					snapAttributeHeavy.setArchivable(0);  // **************** archivable (Property that precises whether the attribute is "on-run-only" archivable, or if it is "always" archivable
					snapAttributeHeavy.setSubstitute(0);  // **************** substitute
					snapAttributeHeavy.setDescription(attributeInfo.description);
					snapAttributeHeavy.setLabel(attributeInfo.label);
					snapAttributeHeavy.setUnit(attributeInfo.unit);
					snapAttributeHeavy.setStandard_unit(attributeInfo.standard_unit);
					snapAttributeHeavy.setDisplay_unit(attributeInfo.display_unit);
					snapAttributeHeavy.setFormat(attributeInfo.format);
					snapAttributeHeavy.setMin_value(attributeInfo.min_value);
					snapAttributeHeavy.setMax_value(attributeInfo.max_value);
					snapAttributeHeavy.setMin_alarm(attributeInfo.min_alarm);
					snapAttributeHeavy.setMax_alarm(attributeInfo.max_alarm);
					
					if ( m_snapDataBase == null )
						return false;
					else
						m_snapDataBase.registerAttribute(snapAttributeHeavy);

				}
			}
			catch ( SnapshotingException e )
			{
				throw e;
			}
		}
		return true;
	}*/

	private static String[] split_att_name_3_fields(String att_name)
	{
		String host = "";
		String domain = "";
		String family = "";
		String member = "";
		String attribut = "";
		String[] argout = new String[ 5 ];// = {"HOST", "DOMAIN", "FAMILY", "MEMBER", "ATTRIBUT"};
		String[] decoupe; // découpage en 5 partie : host, domain, family, member, attribut

		// Host name management
		if ( att_name.startsWith("//") )
			att_name = att_name.substring(2 , att_name.length());
		else
			att_name = "HOST:port/" + att_name;

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
	 * This method allows to open the connection on the historic database
	 */
	private static void connectSnapDatabase(String muser , String mpassword) throws SnapshotingException
	{
		if ( m_snapDataBase == null )
		{
			// Host
			String host = null;
			try
			{
				host = GetConf.getHost(m_snapArchiverClassDevice);
				if ( host.equals("") )
					host = ConfigConst.default_shost;
			}
			catch ( SnapshotingException e )
			{
				System.err.println(e.toString());
				host = ConfigConst.default_shost;
			}

			// Name
			String name = null;
			try
			{
				name = GetConf.getName(m_snapArchiverClassDevice);
				if ( name.equals("") )
					name = ConfigConst.default_sbd;

			}
			catch ( SnapshotingException e )
			{
				System.err.println(e.toString());
				name = ConfigConst.default_sbd;
			}

			// Schema
			String schema = null;
			try
			{
				schema = GetConf.getSchema(m_snapArchiverClassDevice);
				if ( schema.equals("") )
					schema = ConfigConst.default_sschema;
			}
			catch ( SnapshotingException e )
			{
				System.err.println(e.toString());
				schema = ConfigConst.default_sschema;
			}

			// User
			String user = muser;
			if ( user.equals("") )
				user = ConfigConst.default_smuser;

			String pass = mpassword;
			if ( pass.equals("") )
				pass = ConfigConst.default_smpasswd;


			m_snapDataBase = new DataBaseAPI(host , name , schema , user , pass);
			m_snapDataBase.connect_auto();
			//is_snap_connected = true;
		}

	}

	/**
	 * Close the connection of the historic database
	 */
	public static void closeSnapDatabase()
	{
		try
		{
			if ( m_snapDataBase != null )
			{
				m_snapDataBase.close();
				m_snapDataBase = null;
				//is_snap_connected = false;
			}

		}
		catch ( SnapshotingException e )
		{
			m_snapDataBase = null;
		}
	}

	/**
	 * configure the Archiving
	 */
	public static void SnapshotingConfigure(String user , String password) throws SnapshotingException
	{
		//sFacility = getFacility();
        getFacility();
		connectSnapDatabase(user , password);
	}

	/**
	 * @return
	 */
	public static DataBaseAPI getSnapDataBase()
	{
		return m_snapDataBase;
	}

	/**
	 * This method gets informations on a given attribute and registers the attribute into the database "Snap"
	 *
	 * @param att_complete_name the given attribute
	 * @throws SnapshotingException exception throwned in case of communications problems with the device or database
	 */
	private static void register(String att_complete_name) throws SnapshotingException
	{
		try
		{
			AttributeInfo att_info = attributeGetInfo(att_complete_name);


			int index = att_complete_name.lastIndexOf("/");
			String device_name = att_complete_name.substring(0 , index);

			String[] att_splitted_name = split_att_name_3_fields(att_complete_name);

			Timestamp time = new Timestamp(new java.util.Date().getTime());

			SnapAttributeHeavy snapAttribute = new SnapAttributeHeavy(att_complete_name);
			snapAttribute.setAttribute_device_name(device_name);
			snapAttribute.setRegistration_time(time);  // **************** Attribute registration timestamp
			snapAttribute.setAttribute_complete_name(att_complete_name); // **************** The whole attribute name (device_name + attribute_name)
			snapAttribute.setAttribute_device_name(device_name);  // **************** name of the belonging device.
			snapAttribute.setDomain(att_splitted_name[ 1 ]);  // **************** domain to which the attribute is associated
			snapAttribute.setFamily(att_splitted_name[ 2 ]);  // **************** family to which the attribute is associated
			snapAttribute.setMember(att_splitted_name[ 3 ]);  // **************** member to which the attribute is associated
			snapAttribute.setAttribute_name(att_splitted_name[ 4 ]);  // **************** attribute name
			snapAttribute.setData_type(att_info.data_type);  // **************** Attribute data type
			snapAttribute.setData_format(att_info.data_format.value());  // **************** Attribute data format
			snapAttribute.setWritable(att_info.writable.value());  // **************** Attribute read/write type
			snapAttribute.setMax_dim_x(att_info.max_dim_x);  // **************** Attribute Maximum X dimension
			snapAttribute.setMax_dim_y(att_info.max_dim_y);  // **************** Attribute Maximum Y dimension
			snapAttribute.setLevel(att_info.level.value());  // **************** Attribute display level
			snapAttribute.setCtrl_sys(att_splitted_name[ 0 ]);  // **************** Control system to which the attribute belongs
			snapAttribute.setArchivable(0);  // **************** archivable (Property that precises whether the attribute is "on-run-only" archivable, or if it is "always" archivable
			snapAttribute.setSubstitute(0);  // **************** substitute

			m_snapDataBase.registerAttribute(snapAttribute);
		}
		catch ( DevFailed devFailed )
		{
			String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX + " : " + GlobalConst.ATT_UNREACH_EXCEPTION;
			String reason = "Failed while executing SnapManagerApi.register() method...";
			String desc = "";
			throw new SnapshotingException(message , reason , ErrSeverity.PANIC , desc , "" , devFailed);
		}
		catch ( SnapshotingException e )
		{
			throw e;
		}
	}

	/**
	 * This method retrieves the information associated to a given attribute
	 *
	 * @param att_complete_name the attribute name
	 * @return the associated informations
	 * @throws DevFailed execption throwned in case of communications problems with the device
	 */
	private static AttributeInfo attributeGetInfo(String att_complete_name) throws DevFailed
	{
		int index = att_complete_name.lastIndexOf("/");
		String device_name = att_complete_name.substring(0 , index);
		String attribute_name = att_complete_name.substring(index + 1);
		DeviceProxy deviceProxy = new DeviceProxy(device_name);
		AttributeInfo att_info = deviceProxy.get_attribute_info(attribute_name);
		return att_info;
	}

	/**
	 * This method insure that a given attribute was registered into Snap DB
	 *
	 * @param attributeName the attribute name.
	 */
	public static void insureRegitration(String attributeName) throws SnapshotingException
	{
		if ( !m_snapDataBase.isRegistered(attributeName) )
		{
			register(attributeName);
		}
	}

	/**
	 * TODO LG
	 *
	 * @param att_name
	 * @return
	 * @throws SnapshotingException
	 */
	public static int getAttId(String att_name) throws SnapshotingException
	{
		return m_snapDataBase.getAttID(att_name);
	}

	public static int createContext(SnapContext snapContext) throws SnapshotingException
	{
		return m_snapDataBase.create_context(snapContext);
	}

	/**
	 * @param snapContext
	 */
	public static int createContext2Manager(SnapContext snapContext) throws SnapshotingException
	{
		int timeout = 3000;
		int newContextID = -1;
		try
		{

			String device = chooseDevice(m_snapManagerClassDevice);
			if ( !device.equals("") )
			{
				DeviceProxy deviceProxy = new DeviceProxy(device);
				deviceProxy.set_timeout_millis(snapContext.getAttributeList().size() * timeout);
				deviceProxy.ping();
				DeviceData device_data_in = null;
				device_data_in = new DeviceData();
				device_data_in.insert(snapContext.toArray());
				DeviceData device_data_out = deviceProxy.command_inout("CreateNewContext" , device_data_in);
				newContextID = device_data_out.extractLong();
			}
			else
			{
				String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX + " : " + GlobalConst.ERROR_SNAPPATTERN_CREATION;
				String reason = "Failed while executing SnapManagerApi.createContext2Manager() method...";
				String desc = "";
				throw new SnapshotingException(message , reason , ErrSeverity.PANIC , desc , "");
			}
		}
		catch ( DevFailed devFailed )
		{
			String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX + " : " + GlobalConst.DEV_UNREACH_EXCEPTION;
			String reason = "Failed while executing SnapManagerApi.createContext2Manager() method...";
			String desc = "";
			throw new SnapshotingException(message , reason , ErrSeverity.PANIC , desc , "" , devFailed);
		}
		return newContextID;

	}

	public static ArrayList getAllContext() throws SnapshotingException
	{
		return m_snapDataBase.getAllContext();
	}


	/**
	 * TODO LG
	 * Description : Extracts the clause SQL from the given criterions and gets the contexts which subscribe to thoses conditions
	 *
	 * @param criterions Conditions related to the fields of the context table
	 * @return a list of contexts which subscribe to the given conditions (Criterions)
	 * @throws SnapshotingException
	 */
	public static ArrayList getContext(Criterions criterions) throws SnapshotingException
	{
		// Gets the clause that corresponds to the given criterions
		String clause = criterions.getContextClause();
		// Gets the conditions related to the context identifier
		int context_id = criterions.getIdContextContextTable();
		// Gets the list of the contexts which subscribe to all thoses conditions
		return m_snapDataBase.getContext(clause , context_id);
	}

	/**
	 * This method
	 * - registers the new Sanpshot in the database SnapDB,
	 * - gets the ID of the snapshot being built and
	 * - return a 'SnapShot' object with the ID field filled.
	 *
	 * @return a 'SnapShot' object with the snapID field filled.
	 * @throws SnapshotingException
	 */
	public static SnapShot registerSnapShot(int contextID) throws SnapshotingException
	{
		Timestamp time = new Timestamp(System.currentTimeMillis());
		SnapShot snapShot = m_snapDataBase.createNewSnap(contextID , time);
		return snapShot;
	}

	/**
	 * This method triggers the snapshoting oh attributes that belong to the context identified by the the given contextID
	 *
	 * @param contextID The context identifier
	 * @return The 'SnapManagerResult.OK_SNAPLAUNCH' if success, 'SnapManagerResult.ERROR_SNAPLAUNCH' otherwise
	 */
    public static int launchSnap2Archiver(int contextID) throws DevFailed 
    {
        String snapArchiverName = chooseDevice(m_snapArchiverClassDevice);
        if ( snapArchiverName.equals("") )
        {
            throw new DevFailed (); 
        }
        
        DeviceProxy snapArchiverProxy = new DeviceProxy(snapArchiverName);
        DeviceData device_data = null;
        device_data = new DeviceData();
        device_data.insert(( short ) contextID);
        
        DeviceData device_data_out = snapArchiverProxy.command_inout("LaunchSnapShot" , device_data);
        
        int snapID = device_data_out.extractLong();
        return snapID;
    }
    /*public static short launchSnap2Archiver(int contextID) 
    {
        try
        {
            String snapArchiverName = chooseDevice(m_snapArchiverClassDevice);
            if ( !snapArchiverName.equals("") )
            {
                DeviceProxy snapArchiverProxy = new DeviceProxy(snapArchiverName);
                long ping = snapArchiverProxy.ping();
                System.out.println("ping = " + ping);
                DeviceData device_data = null;
                device_data = new DeviceData();
                device_data.insert(( short ) contextID);
                
                DeviceData device_data_out = snapArchiverProxy.command_inout("LaunchSnapShot" , device_data);
                
                int snapID = device_data_out.extractLong();
                
                //device_data = new DeviceData();
                //device_data.insert(( short ) snapID);
            }
            else
            {
                return SnapManagerResult.ERROR_SNAPLAUNCH;
            }
        }
        catch ( DevFailed devFailed )
        {
            System.err.println("ERROR !! " + "\r\n" +
                               "\t Origin : \t " + "SnapManagerApi.launchSnap2Archiver" + "\r\n" +
                               "\t Reason : \t " + "SNAP_FAILURE" + "\r\n" +
                               "\t Description : \t " + devFailed.getMessage() + "\r\n" +
                               "\t Additional information : \t " + "Failed talking to the SnapArchiver device..." + "\r\n");
        }
        return SnapManagerResult.OK_SNAPLAUNCH;
    }*/

	public static ArrayList getContextAssociatedAttributes(int id_context) throws SnapshotingException
	{
		return m_snapDataBase.getContextAssociatedAttributes(id_context);
	}

	/**
	 * TODO LG
	 * This method returns a list of  attributes that belong to the context identified by the given id_context and subscribe to the given conditions (criterions)
	 *
	 * @param id_context The context identifier
	 * @param criterions Conditions related to fields of the context table
	 * @return a list of  attributes that belong to the context identified by the given id_context and subscribe to the given conditions (criterions)
	 * @throws SnapshotingException
	 */
	public static ArrayList getContextAssociatedAttributes(int id_context , Criterions criterions) throws SnapshotingException
	{
		ArrayList arrayList = new ArrayList(512);

		// Gets the list of attributes asociated to the given context
		ArrayList attributes = getContextAssociatedAttributes(id_context);

		// creates the clause
		String clause = criterions.getAttributeClause();

		// Gets the attributes which match to the given conditions
		for ( int i = 0 ; i < attributes.size() ; i++ )
		{
			SnapAttributeLight snapAttributeLight = ( SnapAttributeLight ) attributes.get(i);
			ArrayList res = null;
			res = m_snapDataBase.getAttDefinitionData(snapAttributeLight.getAttribute_complete_name() , clause);
			if ( !res.isEmpty() )
			{
				arrayList.add(res.get(0));
			}
		}

		return arrayList;
	}

	/**
	 * TODO LG
	 *
	 * @param snapshot
	 * @return
	 * @throws SnapshotingException
	 */
	public static ArrayList getSnapshotAssociatedAttributes(SnapShotLight snapshot) throws SnapshotingException
	{

		ArrayList arrayList = new ArrayList(512);

		int contextID = m_snapDataBase.getContextID(snapshot.getId_snap());
		// Gets the attributes list
		ArrayList theoricList = getContextAssociatedAttributes(contextID);
		// Gets the attributes's values
		for ( int i = 0 ; i < theoricList.size() ; i++ )
		{
			SnapAttributeLight snapAttributeLight = ( SnapAttributeLight ) theoricList.get(i);
			SnapAttributeExtract snapAttributeExtract = m_snapDataBase.getSnapResult(snapAttributeLight , snapshot.getId_snap());
			arrayList.add(snapAttributeExtract);
		}

		return arrayList;
	}

	public static Vector getAttDefinitionData(String attributeName) throws SnapshotingException
	{
		Vector vector = null;
		vector = m_snapDataBase.getAttDefinitionData(attributeName);
		return vector;
	}

	public static int getMaxID() throws SnapshotingException
	{
		return m_snapDataBase.getMaxID();
	}

	public static ArrayList getContextAssociatedSnapshots(int id_context) throws SnapshotingException
	{
		return m_snapDataBase.getContextAssociatedSnapshots(id_context);
	}

	/**
	 * TODO LG
	 * Description : Extracts the clause SQL from the given criterions and gets the snapshots which subscribe to thoses conditions
	 *
	 * @param criterions Conditions related to the fields of the snapshot table
	 * @return a list of snapshots which subscribe to the given conditions (Criterions)
	 * @throws SnapshotingException
	 */
	public static ArrayList getContextAssociatedSnapshots(Criterions criterions) throws SnapshotingException
	{
		// Gets the clause that corresponds to the given criterions
		String clause = criterions.getSnapshotClause();
		// Gets the conditions related to the context identifier and the snapshot identifier
		int id_context = criterions.getIdContextSnapTable();
		int id_snap = criterions.getIdSnap();
		// Gets the list of the snapshots which subscribe to all thoses conditions
		return m_snapDataBase.getContextAssociatedSnapshots(clause , id_context , id_snap);
	}

	public static ArrayList getSnapResult(int id_snap) throws SnapshotingException
	{
		ArrayList arrayList = new ArrayList(512);

		int contextID = m_snapDataBase.getContextID(id_snap);
		ArrayList theoricList = getContextAssociatedAttributes(contextID);
		for ( int i = 0 ; i < theoricList.size() ; i++ )
		{
			SnapAttributeLight snapAttributeLight = ( SnapAttributeLight ) theoricList.get(i);
			SnapAttributeExtract snapAttributeExtract = m_snapDataBase.getSnapResult(snapAttributeLight , id_snap);
			arrayList.add(snapAttributeExtract);
		}

		return arrayList;
	}

	/**
	 * TODO LG
	 *
	 * @param id_snap
	 * @param new_comment
	 * @throws SnapshotingException
	 */
	public static void updateSnapComment(int id_snap , String new_comment) throws SnapshotingException
	{
		m_snapDataBase.updateSnapComment(id_snap , new_comment);
	}

	/**
	 * This method is used by a client (GUI) to trigger an equipment setting.
	 *
	 * @param snapShot
	 * @throws SnapshotingException
	 */
	public static void setEquipmentsWithSnapshot(SnapShot snapShot) throws SnapshotingException
	{
		try
		{
			int timeout = 3000;
			String device = chooseDevice(m_snapManagerClassDevice);
			if ( !device.equals("") )
			{
				DeviceProxy deviceProxy = new DeviceProxy(device);
				deviceProxy.set_timeout_millis(snapShot.getAttribute_List().size() * timeout);
				deviceProxy.ping();
				DeviceData device_data = null;
				device_data = new DeviceData();
				device_data.insert(snapShot.toArray());
				//System.out.println("----------------------------On transfère au SnapManager");
				deviceProxy.command_inout("SetEquipmentsWithSnapshot" , device_data);
			}
			else
			{
				String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX + " : " + GlobalConst.ERROR_SNAP_SET_EQUIPMENT;
				String reason = "Failed while executing SnapManagerApi.setEquipmentsWithSnapshot() method...";
				String desc = "";
				throw new SnapshotingException(message , reason , ErrSeverity.PANIC , desc , "");
			}
		}
		catch ( DevFailed devFailed )
		{
			//devFailed.printStackTrace();
			String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX + " : " + GlobalConst.DEV_UNREACH_EXCEPTION;
			String reason = "Failed while executing SnapManagerApi.setEquipmentsWithSnapshot() method...";
			String desc = "";
			throw new SnapshotingException(message , reason , ErrSeverity.PANIC , desc , "" , devFailed);
		}
	}

	/**
	 * This method is used by a snapshoting device to actually set equipements to the their values on the given snapshot.
	 *
	 * @param snapShot
	 * @throws SnapshotingException
	 */
	public static void TriggerSetEquipments(SnapShot snapShot) throws SnapshotingException
	{
        ArrayList attribute_List = snapShot.getAttribute_List();
		for ( int i = 0 ; i < attribute_List.size() ; i++ )
		{
			SnapAttributeExtract snapAttributeExtract = ( SnapAttributeExtract ) attribute_List.get(i);
			if ( snapAttributeExtract.getWritable() != AttrWriteType._READ && snapAttributeExtract.getWritable() != AttrWriteType._READ_WITH_WRITE )
			{
				try
				{
					//System.out.println("TriggerSetEquipments test0");
					AttributeProxy attributeProxy = new AttributeProxy(snapAttributeExtract.getAttribute_complete_name());
					//System.out.println("TriggerSetEquipments test1");
					DeviceAttribute deviceAttribute = new DeviceAttribute(attributeProxy.name());
					//System.out.println("TriggerSetEquipments test2");
					Object value = snapAttributeExtract.getWriteValue();
					//System.out.println("TriggerSetEquipments test3");
					switch ( snapAttributeExtract.getData_format() )
					{
						case AttrDataFormat._SCALAR:
                            if ( value == null || "NaN".equals(value) )
                            {
                                break;
                            }
							switch ( snapAttributeExtract.getData_type() )
							{
								case TangoConst.Tango_DEV_STRING:
									deviceAttribute.insert(( ( String ) value ).toString());
									break;
								case TangoConst.Tango_DEV_STATE:
									deviceAttribute.insert(( ( Integer ) value ).intValue());
									break;
								case TangoConst.Tango_DEV_UCHAR:
									deviceAttribute.insert_uc(( ( Byte ) value ).byteValue());
									break;
								case TangoConst.Tango_DEV_LONG:
									deviceAttribute.insert(( ( Long ) value ).intValue());
									break;
								case TangoConst.Tango_DEV_ULONG:
									deviceAttribute.insert_us(( ( Long ) value ).intValue());
									break;
								case TangoConst.Tango_DEV_BOOLEAN:
									deviceAttribute.insert(( ( Boolean ) value ).booleanValue());
									break;
                                case TangoConst.Tango_DEV_USHORT:
                                    deviceAttribute.insert_us(( ( Short ) value ).shortValue());
                                    break;
								case TangoConst.Tango_DEV_SHORT:
									deviceAttribute.insert(( ( Short ) value ).shortValue());
									break;
								case TangoConst.Tango_DEV_FLOAT:
									deviceAttribute.insert(( ( Float ) value ).floatValue());
									break;
								case TangoConst.Tango_DEV_DOUBLE:
									deviceAttribute.insert(( ( Double ) value ).doubleValue());
									break;
								default:
									deviceAttribute.insert(( ( Double ) value ).doubleValue());
									break;
							}
							break;
						case AttrDataFormat._SPECTRUM:
							if ( value == null || "[NaN]".equals(value) || "NaN".equals(value) )
							{
								break;
							}
							switch ( snapAttributeExtract.getData_type() )
							{
								case TangoConst.Tango_DEV_UCHAR:
									Byte[] byteVal = ( Byte[] ) value;
                                    byte[] byteVal2 = null;
                                    if (byteVal != null)
                                    {
                                        byteVal2 = new byte[byteVal.length];
                                        for (int j = 0; j < byteVal.length; j++)
                                        {
                                            byte val = 0;
                                            if (byteVal[j] != null)
                                            {
                                                val = byteVal[j].byteValue();
                                            }
                                            byteVal2[j] = val;
                                        }
                                    }
									deviceAttribute.insert_uc(byteVal2 , byteVal.length , 0);
									break;
								case TangoConst.Tango_DEV_LONG:
                                    Integer[] longVal = ( Integer[] ) value;
                                    int[] longVal2 = null;
                                    if (longVal != null)
                                    {
                                        longVal2 = new int[longVal.length];
                                        for (int j = 0; j < longVal.length; j++)
                                        {
                                            int val = 0;
                                            if (longVal[j] != null)
                                            {
                                                val = longVal[j].intValue();
                                            }
                                            longVal2[j] = val;
                                        }
                                    }
									deviceAttribute.insert(longVal2);
									break;
								case TangoConst.Tango_DEV_ULONG:
                                    Integer[] longValu = ( Integer[] ) value;
                                    int[] longValu2 = null;
                                    if (longValu != null)
                                    {
                                        longValu2 = new int[longValu.length];
                                        for (int j = 0; j < longValu.length; j++)
                                        {
                                            int val = 0;
                                            if (longValu[j] != null)
                                            {
                                                val = longValu[j].intValue();
                                            }
                                            longValu2[j] = val;
                                        }
                                    }
									deviceAttribute.insert_us(longValu2);
									break;
								case TangoConst.Tango_DEV_SHORT:
									Short[] shortVal = ( Short[] ) value;
                                    short[] shortVal2 = null;
                                    if (shortVal != null)
                                    {
                                        shortVal2 = new short[shortVal.length];
                                        for (int j = 0; j < shortVal.length; j++)
                                        {
                                            short val = 0;
                                            if (shortVal[j] != null)
                                            {
                                                val = shortVal[j].shortValue();
                                            }
                                            shortVal2[j] = val;
                                        }
                                    }
									deviceAttribute.insert(shortVal2);
									break;
								case TangoConst.Tango_DEV_USHORT:
									Short[] shortValu = ( Short[] ) value;
                                    short[] shortValu2 = null;
                                    if (shortValu != null)
                                    {
                                        shortValu2 = new short[shortValu.length];
                                        for (int j = 0; j < shortValu.length; j++)
                                        {
                                            short val = 0;
                                            if (shortValu[j] != null)
                                            {
                                                val = shortValu[j].shortValue();
                                            }
                                            shortValu2[j] = val;
                                        }
                                    }
									deviceAttribute.insert_us(shortValu2);
									break;
								case TangoConst.Tango_DEV_FLOAT:
									Float[] floatVal = ( Float[] ) value;
                                    float[] floatVal2 = null;
                                    if (floatVal != null)
                                    {
                                        floatVal2 = new float[floatVal.length];
                                        for (int j = 0; j < floatVal.length; j++)
                                        {
                                            float val = 0;
                                            if (floatVal[j] != null)
                                            {
                                                val = floatVal[j].floatValue();
                                            }
                                            floatVal2[j] = val;
                                        }
                                    }
									deviceAttribute.insert(floatVal2);
									break;
								case TangoConst.Tango_DEV_DOUBLE:
									Double[] doubleVal = ( Double[] ) value;
                                    double[] doubleVal2 = null;
                                    if (doubleVal != null)
                                    {
                                        doubleVal2 = new double[doubleVal.length];
                                        for (int j = 0; j < doubleVal.length; j++)
                                        {
                                            double val = Double.NaN;
                                            if (doubleVal[j] != null)
                                            {
                                                val = doubleVal[j].doubleValue();
                                            }
                                            doubleVal2[j] = val;
                                        }
                                    }
									deviceAttribute.insert(doubleVal2);
									break;
                                case TangoConst.Tango_DEV_STRING:
                                    String[] stringVal = ( String[] ) value;
                                    deviceAttribute.insert(stringVal);
                                    break;
                                case TangoConst.Tango_DEV_BOOLEAN:
                                    Boolean[] boolVal = ( Boolean[] ) value;
                                    boolean[] boolVal2 = null;
                                    if (boolVal != null)
                                    {
                                        boolVal2 = new boolean[boolVal.length];
                                        for (int j = 0; j < boolVal.length; j++)
                                        {
                                            boolean val = false;
                                            if (boolVal[j] != null)
                                            {
                                                val = boolVal[j].booleanValue();
                                            }
                                            boolVal2[j] = val;
                                        }
                                    }
                                    deviceAttribute.insert(boolVal2);
                                    break;
								default:
									break;
							}
							break;
						default:
							//nothing to do
					}
					attributeProxy.write(deviceAttribute);
				}
				catch ( DevFailed devFailed )
				{
                    String nameOfFailure = snapAttributeExtract.getAttribute_complete_name ();
                    
                    String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX + " : " + GlobalConst.ERROR_SNAP_SET_EQUIPMENT + " on attribute: "+nameOfFailure;
					String reason = "Failed while executing SnapManagerApi.TriggerSetEquipments() method on attribute:" + nameOfFailure;
					String desc = reason;
                    //String origin = "Attribute: " + nameOfFailure;
					throw new SnapshotingException(message , reason , ErrSeverity.PANIC , desc , "" , devFailed);
				}
			}
		}
	}
}
