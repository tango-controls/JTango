package fr.soleil.util.parameter;

import java.util.HashMap;
import java.util.Properties;

/**
 * This class Manage the Label for a list of locale
 * @author BARBA-ROSSA
 *
 */
public class ParameterManager
{
	
	// Name of the properties file
	public final static String s_strENVPARAMETER_FILE = "EnvParameter";
	public final static String s_strPARAMETER_FILE = "Parameter";
	public final static String s_strPARAMETER_TYPE_FILE = "ParameterType";

	private static IParameterLoader m_loader = null; 
	private static HashMap m_params_value = new HashMap();
	private static HashMap m_errors = new HashMap();

	/**
	 * Reinit ParameterManager contend
	 *
	 */
	public static void reinit()
	{
		m_params_value = new HashMap();
		m_errors = new HashMap();
	}
	
	/**
	 * Reload the application parameters
	 *
	 */
	public static void reload(String strFileId)
	{
		// we load the parameters
		m_loader.readFile(strFileId, s_strENVPARAMETER_FILE, true);
		m_loader.readFile(strFileId, s_strPARAMETER_FILE, false);		
	}
	
	/**
	 * Return true if the strFileId exist 
	 */
	public static boolean exist(String strFileId)
	{
		if(!m_errors.containsKey(strFileId)) // first, we check if the fileId is not in the errors map
			return m_params_value.containsKey(strFileId); // we return if the fileId exist 
		return false;
	}
	
	/**
	 * Return the parameter for the selected fileID and selected key
	 * @param strFileId : it could be a part of a parameter, an userid, or other.
	 * @param strKey
	 * @return String
	 */
	public static String getStringParameter(String strFileId, String strKey)
	{
		 // we check if the locale is not in the errors map, if the parameter is in the error map, we will throw an exception 
		if(m_errors.containsKey(strFileId))
			return null; // next throw exception;
		
		// We check if the locale is already loaded
		if(!m_params_value.containsKey(strFileId)){
				m_loader.readFile(strFileId, s_strENVPARAMETER_FILE, true);
				m_loader.readFile(strFileId, s_strPARAMETER_FILE, false);
		}
		
		// We get the labels
		Properties properties = (Properties)m_params_value.get(strFileId);
		if(properties!=null){
			return (String)properties.get(strKey);
		}
		
		// if the label is not found we return a null value
		return null;
	}
	
	/**
	 * Return the properties for the fileId
	 * @param strFileId
	 * @return Properties
	 */
	public static Properties getProperties(String strFileId)
	{
		if(m_params_value.containsKey(strFileId))
			return (Properties)m_params_value.get(strFileId);
		return null;
	}
	
	/**
	 * Return the parameter for the selected fileID and selected key
	 * @param strFileId : it could be a part of a parameter, an userid, or other.
	 * @param strKey
	 * @return Integer
	 */
	public static Integer getIntegerParameter(String strFileId, String strKey)
	{
		String strValue = getStringParameter(strFileId, strKey);
		Integer iValue = new Integer(strValue);
		return iValue;
	}
	
	/**
	 * Return the parameter for the selected fileID and selected key
	 * @param strFileId : it could be a part of a parameter, an userid, or other.
	 * @param strKey
	 * @return Long
	 */
	public static Long getLongParameter(String strFileId, String strKey)
	{
		String strValue = getStringParameter(strFileId, strKey);
		Long iValue = new Long(strValue);
		return iValue;
	}	
	
	/**
	 * Return the parameter for the selected fileID and selected key
	 * @param strFileId : it could be a part of a locale, an userid, or other.
	 * @param strKey
	 * @return Boolean
	 */
	public static Boolean getBooleanParameter(String strFileId, String strKey)
	{
		String strValue = getStringParameter(strFileId, strKey);
		Boolean bValue = new Boolean(strValue);
		return bValue;
	}	

	/**
	 * Return an array of parameter for the selected fileID and selected key
	 * @param strFileId
	 * @param strKey
	 * @return String[]
	 */
	public static String[] getStringArrayParameter(String strFileId, String strKey)
	{
		Integer iSize = getIntegerParameter(strFileId, strKey+ "_size");
		String[] result = new String[iSize.intValue()];
		// We take all parameter value for the key
		for(int i = 0; i < iSize.intValue(); i++)
		{
			result[i] = getStringParameter(strFileId, strKey+ "_" + i);
		}
		return result; 
	}
	
	/**
	 * Return an array of parameter for the selected fileID and selected key
	 * @param strFileId
	 * @param strKey
	 * @return Boolean[]
	 */
	public static Boolean[] getBooleanArrayParameter(String strFileId, String strKey)
	{
		Integer iSize = getIntegerParameter(strFileId, strKey+ "_size");
		Boolean[] result = new Boolean[iSize.intValue()];
		// We take all parameter value for the key
		for(int i = 0; i < iSize.intValue(); i++)
		{
			result[i] = getBooleanParameter(strFileId, strKey+ "_" + i);
		}
		return result; 
	}	
	
	/**
	 * Return an array of parameter for the selected fileID and selected key
	 * @param strFileId
	 * @param strKey
	 * @return Integer[]
	 */
	public static Integer[] getIntegerArrayParameter(String strFileId, String strKey)
	{
		Integer iSize = getIntegerParameter(strFileId, strKey+ "_size");
		Integer[] result = new Integer[iSize.intValue()];
		// We take all parameter value for the key
		for(int i = 0; i < iSize.intValue(); i++)
		{
			result[i] = getIntegerParameter(strFileId, strKey+ "_" + i);
		}
		return result; 
	}	

	/**
	 * Return the error loading map
	 * @return HashMap
	 */
	public static HashMap getErrors()
	{
		return m_errors;
	}

	/**
	 * Get the error loading map
	 * @param m_errors
	 */
	public static void setErrors(HashMap m_errors)
	{
		ParameterManager.m_errors = m_errors;
	}

	/**
	 * Get the map which contains properties file with value
	 * @return HashMap
	 */
	public static HashMap getParams_value()
	{
		return m_params_value;
	}

	/**
	 * Change the map which contains properties file with value
	 * @param m_params_value
	 */
	public static void setParams_value(HashMap m_params_value)
	{
		ParameterManager.m_params_value = m_params_value;
	}

	/**
	 * Get the parameter loader implementation. 
	 * @return IParameterLoader
	 */
	public static IParameterLoader getLoader()
	{
		return m_loader;
	}

	/**
	 * Change the parameter loader implementation
	 * @param m_loader
	 */
	public static void setLoader(IParameterLoader m_loader)
	{
		ParameterManager.m_loader = m_loader;
	}

}
