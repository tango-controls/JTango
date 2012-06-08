package fr.soleil.util.internationalisation;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

import fr.soleil.util.UtilLogger;

/**
 * This class Manage the Label for a list of locale
 * @author BARBA-ROSSA
 *
 */
public class LabelManager
{
	// Name of the properties file
	public final static String s_strLABEL_FILE = "MessageBundle";

	private static HashMap m_labels = new HashMap();
	private static HashMap m_errors = new HashMap();

	/**
	 * Reinit LabelManager contend
	 *
	 */
	public static void reinit()
	{
		m_labels = new HashMap();
		m_errors = new HashMap();
	}
	
	/**
	 * Return true if the locale exist 
	 */
	public static boolean exist(Locale locale)
	{
		if(!m_errors.containsKey(locale)) // first, we check if the locale is not in the errors map
			return m_labels.containsKey(locale); // we return if the locale exist 
		return false;
	}
	
	/**
	 * Return the labels for the selected locale and selected key
	 * @param locale
	 * @param strKey
	 * @return String
	 */
	public static String getLabels(Locale locale, String strKey)
	{
		 // we check if the locale is not in the errors map, if the locale is in the error map, we will throw an exception 
		if(m_errors.containsKey(locale))
			return null; // next throw exception;
		
		// We check if the locale is already loaded
		if(!m_labels.containsKey(locale)){
				readFile(locale);
		}
		
		// We get the labels
		Properties properties = (Properties)m_labels.get(locale);
		if(properties!=null){
			return (String)properties.get(strKey);
		}
		
		// if the label is not found we return a null value
		return null;
	}
	
	/**
	 * Return the labels for the selected locale and selected key
	 * @param locale
	 * @param strKey
	 * @param aDynValues : this array contains all dynbamique values to put into the labels. all $1, $2, $3, ... will be replace by the values in the array
	 * @return String
	 */
	public static String getLabels(Locale locale, String strKey, String[] aDynValues)
	{
		// we get the label 
		String strLabel = getLabels(locale, strKey);
		
		// we replace the joker ($1, $2, $3, ... ) by the values
		if(aDynValues== null)
			return strLabel;
		
		String exp = "\\$";
		for(int i = 0 ;i < aDynValues.length; i++)
		{
			strLabel = strLabel.replaceAll(exp+i, aDynValues[i]);
		}
		return strLabel;
	}
	
	
	/**
	 * This method load a LabelFile and add it in the labels map.
	 * Return : true if loading with success the file, false if an exception occured
	 * @param locale
	 * @return boolean
	 */
	private static boolean readFile(Locale locale)
	{
		InputStream stream = null;
		BufferedInputStream bufStream = null;
        try{
        	String strLocale = ""; // String use to specifie the locale when loading the file
        	if(locale!= null){
        		if(locale.getLanguage() != null)
        			strLocale = "_" + locale.getLanguage();
        		if(locale.getCountry() != null)	
        			strLocale += "_" + locale.getCountry();
        	}
        	
        	// We use the class loader to load the properties file. 
        	// This compatible with unix and windows.
            stream = LabelManager.class.getClassLoader().getResourceAsStream(s_strLABEL_FILE+strLocale+".properties");
    		Properties properties = new Properties();
    		
    		// We read the data in the properties file.
    		if(stream != null){
    			// We need to use a Buffered Input Stream to load the datas
    			bufStream = new BufferedInputStream(stream);
            	properties.clear();
            	properties.load(bufStream);
            	m_labels.put(locale, properties);
            }
    		return true;
        }catch(IOException ioe){
        	m_errors.put(locale, locale);
        	ioe.printStackTrace();
            return false;        	
		}catch(Exception e){
            e.printStackTrace();
            return false;
        }
        finally
        {
        	try{
	        	if(bufStream != null)
	        		bufStream.close();
	        	if(stream != null)
	        		stream.close();
        	}catch(Exception e)
        	{
        		UtilLogger.logger.addERRORLog("LabelManager, error when closing properties file stream");
        	}
        }		
		
	}

}
