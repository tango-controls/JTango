package fr.soleil.util.properties;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import fr.soleil.util.UtilLogger;

/**
 * Load a properties file
 * @author BARBA-ROSSA
 *
 */
public class PropertiesFileLoader
{

	/**
	 * Load properties files from inside a Jar/War
	 * 
	 * @param classLoader : the class loader used to load the properties file
	 * @param propertieName : the propertie file name
	 * @return Properties
	 */
	public static Properties loadProperties(ClassLoader classLoader, String propertieName) throws Exception {

		InputStream stream = null;
		BufferedInputStream bufStream = null;
		try {
			stream = classLoader.getResourceAsStream(propertieName + ".properties");
			Properties properties = null;

			// We read the data in the properties file.
			if (stream != null) {
				properties = new Properties();
				
				// We need to use a Buffered Input Stream to load the datas
				bufStream = new BufferedInputStream(stream);
				properties.clear();
				properties.load(bufStream);
			}
			return properties;
		} catch (IOException ioe) {
			UtilLogger.logger.addERRORLog("Error during loading file : " + propertieName + ", please check if the file exist ");
			ioe.printStackTrace();
			throw ioe;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// We always close all stream open
			try {
				if (bufStream != null)
					bufStream.close();
				if (stream != null)
					stream.close();
			} catch (Exception e) {
				UtilLogger.logger.addERRORLog("ParameterManager, error when closing properties file stream");
			}
		}
	}
	
	public static Properties loadProperties(File dirPath, String propertieName) throws Exception {

		InputStream stream = null;
		BufferedInputStream bufStream = null;
		try {
			File file = new File(dirPath, propertieName+".properties");
			stream = new FileInputStream(file);
			Properties properties = null;

			// We read the data in the properties file.
			if (stream != null) {
				properties = new Properties();
				
				// We need to use a Buffered Input Stream to load the datas
				bufStream = new BufferedInputStream(stream);
				properties.clear();
				properties.load(bufStream);
			}
			return properties;
		} catch (IOException ioe) {
			UtilLogger.logger.addERRORLog("Error during loading file : " + propertieName + ", please check if the file exist ");
			ioe.printStackTrace();
			throw ioe;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// We always close all stream open
			try {
				if (bufStream != null)
					bufStream.close();
				if (stream != null)
					stream.close();
			} catch (Exception e) {
				UtilLogger.logger.addERRORLog("ParameterManager, error when closing properties file stream");
			}
		}
	}	
	
}
