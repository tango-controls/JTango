package fr.soleil.util.parameter;

import java.util.Enumeration;
import java.util.Properties;
import java.util.prefs.Preferences;

/**
 * This factory build parameters
 * @author BARBA-ROSSA
 *
 */
public class ParameterFactory
{
	protected static String s_strParameterNode = "parameternode";
	
	/**
	 * We load the properties using the preference API
	 * @return Properties
	 * @throws Exception
	 */
	public static Properties loadLocaleUserParameter() throws Exception
	{
		Properties properties = new Properties();
		
		// We get the preferences values from the paramerternode in userRoot preference
		Preferences prefs = Preferences.userRoot().node(s_strParameterNode);
		
		// We get the preference values using the preference API
		String[] keys = prefs.keys();
		if(keys!=null)
		{
			for(int i = 0;i < keys.length;i++)
			{
				properties.put(keys[i], prefs.get(keys[i], null));
			}
		}
		return properties;
	}

	/**
	 * We save the properties in the Workstation
	 * @param properties
	 */
	public static void saveLocaleUserParameter(Properties properties) throws Exception
	{
		// We get the preferences values from the paramerternode in userRoot preference
		Preferences prefs = Preferences.userRoot().node(s_strParameterNode);
		
		// We save the properties using the preference API
		if(properties!=null)
		{
			Enumeration keys = properties.keys();
			String currentKey = null;
			while(keys.hasMoreElements())
			{
				currentKey = (String)keys.nextElement();
				prefs.put(currentKey, properties.getProperty(currentKey));
			}
		}
		
		
	}
	
}
