package fr.esrf.TangoApi.factory;

import fr.esrf.Tango.factory.ITangoFactory;
import fr.esrf.TangoApi.ApiUtilDAODefaultImpl;
import fr.esrf.TangoApi.ConnectionDAODefaultImpl;
import fr.esrf.TangoApi.DatabaseDAODefaultImpl;
import fr.esrf.TangoApi.DeviceAttributeDAODefaultImpl;
import fr.esrf.TangoApi.DeviceDataDAODefaultImpl;
import fr.esrf.TangoApi.DeviceDataHistoryDAODefaultImpl;
import fr.esrf.TangoApi.DeviceProxyDAODefaultImpl;
import fr.esrf.TangoApi.IApiUtilDAO;
import fr.esrf.TangoApi.IConnectionDAO;
import fr.esrf.TangoApi.IDatabaseDAO;
import fr.esrf.TangoApi.IDeviceAttributeDAO;
import fr.esrf.TangoApi.IDeviceDataDAO;
import fr.esrf.TangoApi.IDeviceDataHistoryDAO;
import fr.esrf.TangoApi.IDeviceProxyDAO;
import fr.esrf.TangoApi.IIORDumpDAO;
import fr.esrf.TangoApi.IORDumpDAODefaultImpl;

public class DefaultTangoFactoryImpl implements ITangoFactory {
	
    public DefaultTangoFactoryImpl()
    {
    }

    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getConnectionDAO()
	 */
    public IConnectionDAO getConnectionDAO()
    {
    	return new ConnectionDAODefaultImpl();
    }
    
    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getDeviceProxyDAO()
	 */
    public IDeviceProxyDAO getDeviceProxyDAO()
    {
    	return new DeviceProxyDAODefaultImpl();
    }

    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getDatabaseDAO()
	 */
    public IDatabaseDAO getDatabaseDAO()
    {
    	try
    	{
    	return new DatabaseDAODefaultImpl();
    	}catch(Exception e)
    	{
    		// we may not have exception we build  DatabaseDAODefaultImpl but ... 
    		e.printStackTrace();
    		return null;
    	}
    }    
    
    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getDeviceAttributeDAO()
	 */
    public IDeviceAttributeDAO getDeviceAttributeDAO()
    {
    	return new DeviceAttributeDAODefaultImpl();
    }
    
    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getDeviceDataDAO()
	 */
    public IDeviceDataDAO getDeviceDataDAO()
    {
    	return new DeviceDataDAODefaultImpl();
    }    

    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getDeviceDataHistoryDAO()
	 */
    public IDeviceDataHistoryDAO getDeviceDataHistoryDAO()
    {
    	return new DeviceDataHistoryDAODefaultImpl();
    }
    
    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getApiUtilDAO()
	 */
    public IApiUtilDAO getApiUtilDAO()
    {
    	return new ApiUtilDAODefaultImpl();
    }

    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.factory.ITangoFactory#getIORDumpDAO()
	 */
    public IIORDumpDAO getIORDumpDAO()
    {
    	return new IORDumpDAODefaultImpl();
    }    

    public String getFactoryName()
    {
    	return "TANGORB Default";
    }
    
}
