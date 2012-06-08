package fr.esrf.TangoApi.factory;

import fr.esrf.Tango.factory.ITangoFactory;
import fr.esrf.TangoApi.ApiUtilDAOWebImpl;
import fr.esrf.TangoApi.ConnectionDAOWebImpl;
import fr.esrf.TangoApi.DatabaseDAOWebImpl;
import fr.esrf.TangoApi.DeviceAttributeDAOWebImpl;
import fr.esrf.TangoApi.DeviceDataDAOWebImpl;
import fr.esrf.TangoApi.DeviceDataHistoryDAOWebImpl;
import fr.esrf.TangoApi.DeviceProxyDAOWebImpl;
import fr.esrf.TangoApi.IApiUtilDAO;
import fr.esrf.TangoApi.IConnectionDAO;
import fr.esrf.TangoApi.IDatabaseDAO;
import fr.esrf.TangoApi.IDeviceAttributeDAO;
import fr.esrf.TangoApi.IDeviceDataDAO;
import fr.esrf.TangoApi.IDeviceDataHistoryDAO;
import fr.esrf.TangoApi.IDeviceProxyDAO;
import fr.esrf.TangoApi.IIORDumpDAO;
import fr.esrf.TangoApi.IORDumpDAOWebImpl;

public class WebTangoFactoryImpl implements ITangoFactory {
	
    public WebTangoFactoryImpl()
    {
    }

    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getConnectionDAO()
	 */
    public IConnectionDAO getConnectionDAO()
    {
    	return new ConnectionDAOWebImpl();
    }
    
    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getDeviceProxyDAO()
	 */
    public IDeviceProxyDAO getDeviceProxyDAO()
    {
    	return new DeviceProxyDAOWebImpl();
    }

    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getDatabaseDAO()
	 */
    public IDatabaseDAO getDatabaseDAO()
    {
    	try
    	{
    	return new DatabaseDAOWebImpl();
    	}catch(Exception e)
    	{
    		// we may not have exception we build  DatabaseDAOWebImpl but ... 
    		e.printStackTrace();
    		return null;
    	}
    }    
    
    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getDeviceAttributeDAO()
	 */
    public IDeviceAttributeDAO getDeviceAttributeDAO()
    {
    	return new DeviceAttributeDAOWebImpl();
    }
    
    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getDeviceDataDAO()
	 */
    public IDeviceDataDAO getDeviceDataDAO()
    {
    	return new DeviceDataDAOWebImpl();
    }    

    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getDeviceDataHistoryDAO()
	 */
    public IDeviceDataHistoryDAO getDeviceDataHistoryDAO()
    {
    	return new DeviceDataHistoryDAOWebImpl();
    }
    
    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getApiUtilDAO()
	 */
    public IApiUtilDAO getApiUtilDAO()
    {
    	return new ApiUtilDAOWebImpl();
    }

    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getIORDumpDAO()
	 */
    public IIORDumpDAO getIORDumpDAO()
    {
    	return new IORDumpDAOWebImpl();
    }    

    public String getFactoryName()
    {
    	return "TANGORB Web";
    }
    
}
