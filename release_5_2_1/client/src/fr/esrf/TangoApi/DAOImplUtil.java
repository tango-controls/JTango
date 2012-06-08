package fr.esrf.TangoApi;

import fr.esrf.webapi.IDAOImplUtil;

public class DAOImplUtil implements IDAOImplUtil {
	
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDAOImplUtil#getDAOImpl(java.lang.Object)
	 */
	public Object getDAOImpl(Object result)
	{
		if(result instanceof DeviceAttribute)
		{
			return ((DeviceAttribute)result).getDeviceattributeDAO();
		}
		if(result instanceof DeviceData)
		{
			return((DeviceData)result).getDevicedataDAO();
		}
		if(result instanceof DeviceDataHistory)
		{
			return ((DeviceDataHistory)result).getDeviceedatahistoryDAO();
		}
		if(result instanceof DeviceProxy)
		{
			return  ((DeviceProxy)result).getDeviceProxy();
		}			
		if(result instanceof Database)
		{
			return ((Database)result).getDatabaseDAO();
		}
		if(result instanceof ApiUtil)
		{
			return ((ApiUtil)result).getApiUtilDAO();
		}		
		return result;
	}
}
