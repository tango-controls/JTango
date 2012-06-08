package fr.soleil.util.database;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

import fr.soleil.util.UtilLogger;
import fr.soleil.util.exception.SoleilException;
import fr.soleil.util.log.SoleilAppender;

/**
 * This class contains the database connection parameter
 * @author BARBA-ROSSA
 *
 */
public class DBProperties
{ 
	private static DBProperties s_singleton = null; 	
	
// List of the properties key used in the dbproperties file.
	// Name of the properties file
	public final static String s_strPROPERTIES_NAME = "db.properties";
	
	// Error for file not found
	public final static String s_strFILE_NOT_FOUND="PROPERTIES_FILE_NOT_FOUND";
	
	//	 Database connection parameter
	public final static String s_strHOST = "HOST";
	public final static String s_strPORT = "PORT";
	public final static String s_strNAME_BDD = "NAME_BDD";
	public final static String s_strUSER = "USER";
	public final static String s_strPASSWD = "PASSWD";
	//	 The SQL driver used by the application
	public final static String s_strDRIVER = "DRIVER";
	
// Value of the database properties
	//Database server host
	public String m_strHost = null; 
	//Database server port
	public String m_strPort = null;
	//Database name
	public String m_strNameBDD = null; 
	//Database User
	public String m_strUser = null; 
	//Database password
	public String m_strPasswd = null; 
	//	 The SQL driver used by the application
	//Database driver name
	public String m_strDriver = null;	 
	
	/**
	 * Default constructor
	 *
	 */
	public DBProperties()
	{
		super();
		// we build the properties
		build(null);
	}

	/**
	 * Default constructor
	 *
	 */
	public DBProperties(Properties properties)
	{
		super();
		// we build the properties
		build(properties);
	}	
	
	/**
	 * Intends to load the file properties
	 * @throws Exception : if the file can not be founded
	 */
	public static void loadPropertiesFile() throws Exception
	{		
		InputStream stream = null;
    	// We use the class loader to load the properties file. 
		// This compatible with unix and windows.
		stream=DBProperties.class.getClassLoader().getResourceAsStream(s_strPROPERTIES_NAME);

		if(stream==null){
			System.out.println("***************************************************************");
		    System.out.println("  THE FILE " +s_strPROPERTIES_NAME + " CAN NOT BE FOUND !!     ");
			System.out.println("***************************************************************");
			throw new SoleilException(s_strFILE_NOT_FOUND,SoleilAppender.s_strFATAL);
		}
	}
	
	/**
	 * This class build read the database connection parameter in the properties file
	 *
	 */
	public void build(Properties properties)
	{
		if(properties == null)
		{
			InputStream stream = null;
			BufferedInputStream bufStream = null;
	        try{
	        	// We use the class loader to load the properties file. 
	        	// This compatible with unix and windows.
	            stream = DBProperties.class.getClassLoader().getResourceAsStream(s_strPROPERTIES_NAME);
	    		properties = new Properties();
	    		// We read the data in the properties file.
	    		if(stream!=null){
	    			// We need to use a Buffered Input Stream to load the datas
	    			bufStream = new BufferedInputStream(stream);
	            	properties.clear();
	            	properties.load(bufStream);
	            }
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        finally
	        {
	        	try{
		        	if(bufStream != null)
		        		bufStream.close();
		        	if(stream !=null)
		        		stream.close();
	        	}catch(Exception e)
	        	{
	        		UtilLogger.logger.addERRORLog("DBProperties, error when closing properties file stream");
	        	}
	        }
		}
		
        // We get the data from the properties
    	m_strHost = properties.getProperty(s_strHOST);
    	m_strPort = properties.getProperty(s_strPORT);
    	m_strNameBDD = properties.getProperty(s_strNAME_BDD);
    	m_strUser = properties.getProperty(s_strUSER);
    	m_strPasswd = properties.getProperty(s_strPASSWD);           	
    	m_strDriver = properties.getProperty(s_strDRIVER);
        
	}

	
	/**
	 * It's the singleton getter. We don't neeed to create multiple object for to read the database parameter. 
	 * @return DBProperties
	 */
	public static DBProperties getSingleton()
	{
		// if no DBProperties implemntation exist, we create one.
		if(s_singleton==null)
			s_singleton = new DBProperties();
		return s_singleton;
	}

	// It's the getter and setter
	
	/**
	 * Return the Database driver name
	 * @return String
	 */
	public String getDriver()
	{
		return m_strDriver;
	}

	/**
	 * Set the Database driver name
	 * @param driver
	 */
	public void setDriver(String driver)
	{
		m_strDriver = driver;
	}

	/**
	 * return the Database server host
	 * @return String
	 */
	public String getHost()
	{
		return m_strHost;
	}

	/**
	 * Set the Database server host
	 * @param host
	 */
	public void setHost(String host)
	{
		m_strHost = host;
	}

	/**
	 * Get the Database name
	 * @return String
	 */
	public String getNameBDD()
	{
		return m_strNameBDD;
	}

	/**
	 * Set the Database name
	 * @param nameBDD
	 */
	public void setNameBDD(String nameBDD)
	{
		m_strNameBDD = nameBDD;
	}

	/**
	 * Get the database password
	 * @return
	 */
	public String getPasswd()
	{
		return m_strPasswd;
	}

	/**
	 * Set the database password
	 * @param passwd
	 */
	public void setPasswd(String passwd)
	{
		m_strPasswd = passwd;
	}

	/**
	 * Get the database server port
	 * @return
	 */
	public String getPort()
	{
		return m_strPort;
	}

	/**
	 * Set the Database server port
	 * @param port
	 */
	public void setPort(String port)
	{
		m_strPort = port;
	}

	/**
	 * Get the database User
	 * @return String
	 */
	public String getUser()
	{
		return m_strUser;
	}

	/**
	 * Set the database User
	 * @param user
	 */
	public void setUser(String user)
	{
		m_strUser = user;
	}

	/**
	 * Change the DBProperties's singleton value
	 * We generally need only one instance of DBProperties because application connect only to one Database
	 * @param s_singleton
	 */
	public static void setSingleton(DBProperties s_singleton)
	{
		DBProperties.s_singleton = s_singleton;
	}

	public static void createSingleton(Properties properties)
	{
		s_singleton = new DBProperties(properties);
	}
	
}
