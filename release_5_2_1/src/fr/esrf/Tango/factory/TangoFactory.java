package fr.esrf.Tango.factory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import fr.esrf.TangoApi.IApiUtilDAO;
import fr.esrf.TangoApi.IConnectionDAO;
import fr.esrf.TangoApi.IDatabaseDAO;
import fr.esrf.TangoApi.IDeviceAttributeDAO;
import fr.esrf.TangoApi.IDeviceDataDAO;
import fr.esrf.TangoApi.IDeviceDataHistoryDAO;
import fr.esrf.TangoApi.IDeviceProxyDAO;
//import fr.esrf.TangoApi.tangorbversion.ITangORBversionDAO;
import fr.esrf.TangoApi.IIORDumpDAO;

/**
 * 
 * @author BARBA-ROSSA
 *
 */
public class TangoFactory {
    public static final String FACTORY_PROPERTIES = "tango_factory.properties";
    public static final String TANGORB_VERSION_IMPL = "TANGORB_VERSION_IMPL";
    
    public static final String DEVICE_PROXY_IMPL = "DEVICE_PROXY_IMPL";
    public static final String CONNECTION_IMPL = "CONNECTION_IMPL";
    public static final String DATABASE_IMPL = "DATABASE_IMPL";
    
    public static final String DEVICE_ATTRIBUTE_IMPL = "DEVICE_ATTRIBUTE_IMPL";
    public static final String DEVICE_DATA_IMPL = "DEVICE_DATA_IMPL";
    public static final String DEVICE_DATA_HISTORY_IMPL = "DEVICE_DATA_HISTORY_IMPL";
    public static final String API_UTIL_IMPL = "API_UTIL_IMPL";
    public static final String IOR_DUMP_IMPL = "IOR_DUMP_IMPL";
    
    
    
    private static TangoFactory singleton = null;
    private Map<String, Object> implMap = null;
    private Properties properties =null; 
    
    public TangoFactory()
    {
    	initTangoFactory();
    }

    /**
     * Load properties with impl specification and create instances
     *
     */
    public void initTangoFactory()
    {
    	// we create the map which contaisn impl instances
    	implMap = new HashMap<String, Object>();

    	// we get the properties with instance of objects
    	properties = getPropertiesFile();
    	
    	// we get the TangORBversion
    	//String className = properties.getProperty(TANGORB_VERSION_IMPL);
    	//implMap.put(TANGORB_VERSION_IMPL, getObject(className));
    	
    }
    
    public static TangoFactory getSingleton()
    {
    	if(singleton == null)
    		createSingleton();
    	return singleton;
    }
    
    public static void createSingleton()
    {
    	singleton = new TangoFactory();
    	
    }
    
    /**
     * Return an Implementation of TangORBVersion class
     * @return ITangORBversion
     */
    /*public ITangORBversionDAO getTangORBVersion()
    {
    	return (ITangORBversionDAO) implMap.get(TANGORB_VERSION_IMPL);
    }*/

    public IConnectionDAO getConnectionDAO()
    {
    	String className = properties.getProperty(CONNECTION_IMPL);
    	return (IConnectionDAO) getObject(className);
    }
    
    public IDeviceProxyDAO getDeviceProxyDAO()
    {
    	String className = properties.getProperty(DEVICE_PROXY_IMPL);
    	return (IDeviceProxyDAO) getObject(className);
    }

    public IDatabaseDAO getDatabaseDAO()
    {
    	String className = properties.getProperty(DATABASE_IMPL);
    	return (IDatabaseDAO) getObject(className);
    }    
    
    public IDeviceAttributeDAO getDeviceAttributeDAO()
    {
    	String className = properties.getProperty(DEVICE_ATTRIBUTE_IMPL);
    	return (IDeviceAttributeDAO) getObject(className);
    }
    
    public IDeviceDataDAO getDeviceDataDAO()
    {
    	String className = properties.getProperty(DEVICE_DATA_IMPL);
    	return (IDeviceDataDAO) getObject(className);
    }    

    public IDeviceDataHistoryDAO getDeviceDataHistoryDAO()
    {
    	String className = properties.getProperty(DEVICE_DATA_HISTORY_IMPL);
    	return (IDeviceDataHistoryDAO) getObject(className);
    }
    
    public IApiUtilDAO getApiUtilDAO()
    {
    	String className = properties.getProperty(API_UTIL_IMPL);
    	return (IApiUtilDAO) getObject(className);
    }

    public IIORDumpDAO getIORDumpDAO()
    {
    	String className = properties.getProperty(IOR_DUMP_IMPL);
    	return (IIORDumpDAO) getObject(className);
    }    
    
    /**
     * We get the properties file which contains default properties
     * @return Properties
     */
    private static Properties getPropertiesFile()
    {
		InputStream stream = null;
		BufferedInputStream bufStream = null;
        try{
        	
        	// We use the class loader to load the properties file. 
        	// This compatible with unix and windows.
            stream = TangoFactory.class.getClassLoader().getResourceAsStream(FACTORY_PROPERTIES);
    		Properties properties = new Properties();
    		
    		// We read the data in the properties file.
    		if(stream != null){
    			// We need to use a Buffered Input Stream to load the datas
    			bufStream = new BufferedInputStream(stream);
            	properties.clear();
            	properties.load(bufStream);
    		}
    		return properties;
        }
    	catch(Exception e)
    	{
			e.printStackTrace();
    		return null;
    	}
    }

    /**
     * We instanciate the Component
     * @param className
     * @return Object
     */
    private static Object getObject(String className)
    {
    	try {
        	// we get the class coresponding to the life cycle name
			Class clazz = Class.forName(className);
			
			// we get the default constructor (with no parameter)
			Constructor contructor = clazz.getConstructor(new Class[]{});
			
			// we create an instance of the class using the constructor
			Object object = contructor.newInstance(new Object[]{});
			return object;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
