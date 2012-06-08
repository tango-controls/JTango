package fr.soleil.util.parameter;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.Properties;

import fr.soleil.util.UtilLogger;
import fr.soleil.util.database.ConnectionManager;
import fr.soleil.util.database.IDatabaseConnection;

/**
 * Parameter Loader from database
 * 
 * @author BARBA-ROSSA
 * 
 */
public class DatabaseParameterLoaderImpl implements IParameterLoader {
	private String m_strApplicationIUID = null; // the application IUID for TWIST it's -1000

	private String m_strENVKey = null; // the env key : REC, INT, PROD, or the name of the workstation for DEVs.

	private String m_strDefaultENVKey = null; // the default Env Key used only if we have no ENVKey defined

	public DatabaseParameterLoaderImpl(String defaultENVKey, Properties properties) {
		m_strDefaultENVKey = defaultENVKey;

		if (properties == null) 
		{
			// We load properties files
			InputStream stream = null;
			BufferedInputStream bufStream = null;
			try 
			{

				// We use the class loader to load the properties file.
				// This compatible with unix and windows.
				stream = ParameterManager.class.getClassLoader().getResourceAsStream("EnvParameter.properties");
				properties = new Properties();

				// We read the data in the properties file.
				if (stream != null) {
					// We need to use a Buffered Input Stream to load the datas
					bufStream = new BufferedInputStream(stream);
					properties.clear();
					properties.load(bufStream);
				}
			}
			catch (Exception e) 
			{
				System.out.println("FATAL ERROR : Can't load EnvParameter.properties file. TWIST Server can't RUN");
				e.printStackTrace();
			}
			finally 
			{
				// We always close all stream open
				try 
				{
					if (bufStream != null)
						bufStream.close();
					if (stream != null)
						stream.close();
				}
				catch (Exception e) 
				{
					UtilLogger.logger.addERRORLog("ParameterManager, error when closing properties file stream");
				}
			}
		}

		m_strApplicationIUID = properties.getProperty("ApplicationIUID");
		m_strENVKey = properties.getProperty("ENVKey");

		// if the envkey is null on equal to "" we use the default env key
		if (m_strENVKey == null || "".equals(m_strENVKey.trim()))
			m_strENVKey = m_strDefaultENVKey;

		if (m_strApplicationIUID == null || "".equals(m_strApplicationIUID.trim()))
			System.out.println("FATAL ERROR : ApplicationIUID is missing from EnvParameter.properties");

		if (m_strENVKey == null || "".equals(m_strENVKey.trim()))
			System.out.println("FATAL ERROR : ENVKey is missing from EnvParameter.properties");

	}

	/**
	 * This method load parameter from database and add it in the parameter map.
	 * Return : true if loading with success the file, false if an exception
	 * occured
	 * 
	 * @param strFileId :
	 *            the fileID, it can be use to identifie different parameters
	 * @param strParameterName :
	 *            the name of the parameter file
	 * @param bReset :
	 *            reset the old content for this fileID ?
	 * @return boolean
	 */
	public boolean readFile(String strFileId, String strParameterName, boolean bReset) {
		IDatabaseConnection dbConnection = null;
		ResultSet rs = null;
		try {
			String strEndfileName = ""; // String use to specifie the parameter
										// when loading the file
			if (strFileId != null) {
				strEndfileName = strFileId;
			}

			// We get a database connection connector
			dbConnection = ConnectionManager.getDatabaseConnection();

			// We connect to the database
			dbConnection.connect();

			// We load the TWIST Datas strParameterName+strEndfileName
			String strQuery = "SELECT IUID, SCATEGORY, SKEY, SVALUE from USERPARAMETERS where IUID = " + m_strApplicationIUID + " AND SCATEGORY = '" + m_strENVKey + "_" + strParameterName
					+ strEndfileName + "'";

			// We execute the query
			rs = dbConnection.executeQuery(strQuery);
			int iSize = rs.getMetaData().getColumnCount();
			Properties newProperties = new Properties();

			// We load all properties for the element
			String strTempKey = null;
			String strTempValue = null;
			while (rs.next()) {
				strTempKey = rs.getString("SKEY");
				strTempValue = rs.getString("SVALUE");
				if (strTempKey != null && strTempValue != null) {
					newProperties.put(strTempKey.trim(), strTempValue.trim());
				}
			}

			// If reset data is required we simply replace the old properties by
			// the new properties file else we add the new properties
			if (bReset)
				ParameterManager.getParams_value().put(strEndfileName, newProperties);
			else {
				// we add the new properties in the old properties object
				Properties oldProp = (Properties) ParameterManager.getParams_value().get(strEndfileName);
				if (oldProp == null) {
					ParameterManager.getParams_value().put(strEndfileName, newProperties);
					return true;
				}
				// we put all properties from the new properties file in the old
				// file
				oldProp.putAll(newProperties);
				ParameterManager.getParams_value().put(strEndfileName, oldProp);
			}
			return true;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			try {
				// we close the resultset
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// We release the connection
			dbConnection.disconnect();
		}

	}

	/**
	 * Return the env key : REC, INT, PROD, or the name of the workstation for
	 * DEVs.
	 * 
	 * @return String
	 */
	public String getM_strENVKey() {
		return m_strENVKey;
	}

	/**
	 * Change the env key : REC, INT, PROD, or the name of the workstation for
	 * DEVs.
	 * 
	 * @param key
	 */
	public void setENVKey(String key) {
		m_strENVKey = key;
	}

	/**
	 * Return the application IUID for TWIST it's -1000
	 * 
	 * @return String
	 */
	public String getApplicationIUID() {
		return m_strApplicationIUID;
	}

	/**
	 * Change the application IUID for TWIST it's -1000
	 * 
	 * @param applicationIUID
	 */
	public void setApplicationIUID(String applicationIUID) {
		m_strApplicationIUID = applicationIUID;
	}

}
