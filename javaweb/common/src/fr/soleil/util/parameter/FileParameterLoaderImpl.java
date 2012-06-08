package fr.soleil.util.parameter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import fr.soleil.util.UtilLogger;

/**
 * Parameter Loader from properties file
 * @author BARBA-ROSSA
 *
 */
public class FileParameterLoaderImpl implements IParameterLoader
{

	/**
	 * This method load parameter from properties file and add it in the parameter map.
	 * Return : true if loading with success the file, false if an exception occured
	 * @param strFileId : the fileID, it can be use to identifie different parameters
	 * @param strParameterName : the name of the parameter file
	 * @param bReset : reset the old content for this fileID ?
	 * @return boolean
	 */	
	public boolean readFile(String strFileId, String strParameterName,
			boolean bReset)
	{
		InputStream stream = null;
		BufferedInputStream bufStream = null;
        try{
        	String strEndfileName = ""; // String use to specifie the parameter when loading the file
        	if(strFileId!= null){
        		strEndfileName = strFileId;
        	}
        	
        	// We use the class loader to load the properties file. 
        	// This compatible with unix and windows.
            stream = ParameterManager.class.getClassLoader().getResourceAsStream(strParameterName+strEndfileName+".properties");
    		Properties properties = new Properties();
    		
    		// We read the data in the properties file.
    		if(stream != null){
    			// We need to use a Buffered Input Stream to load the datas
    			bufStream = new BufferedInputStream(stream);
            	properties.clear();
            	properties.load(bufStream);

            	if(bReset)
            		ParameterManager.getParams_value().put(strEndfileName, properties);
            	else
            	{
            		// we add the new properties in the old properties object
            		Properties oldProp = (Properties)ParameterManager.getParams_value().get(strEndfileName);
            		if(oldProp== null)
            		{
            			ParameterManager.getParams_value().put(strEndfileName, properties);
            			return true;
            		}
            		// we put all properties from the new properties file in the old file
            		oldProp.putAll(properties);
            		ParameterManager.getParams_value().put(strEndfileName, oldProp);
            	}
            	
            }
    		return true;
        }catch(IOException ioe){
        	ParameterManager.getErrors().put(strFileId, strFileId);
        	UtilLogger.logger.addERRORLog("Error during loading file : " + strFileId+ ", please check if the file exist ");
        	ioe.printStackTrace();
            return false;
		}catch(Exception e){
            e.printStackTrace();
            return false;
        }
        finally
        {
        	// We always close all stream open
        	try{
	        	if(bufStream != null)
	        		bufStream.close();
	        	if(stream != null)
	        		stream.close();
        	}catch(Exception e)
        	{
        		UtilLogger.logger.addERRORLog("ParameterManager, error when closing properties file stream");
        	}
        }		
		
	}

}
