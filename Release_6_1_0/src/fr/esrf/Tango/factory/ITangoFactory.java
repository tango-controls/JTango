package fr.esrf.Tango.factory;

import fr.esrf.TangoApi.IApiUtilDAO;
import fr.esrf.TangoApi.IConnectionDAO;
import fr.esrf.TangoApi.IDatabaseDAO;
import fr.esrf.TangoApi.IDeviceAttributeDAO;
import fr.esrf.TangoApi.IDeviceDataDAO;
import fr.esrf.TangoApi.IDeviceDataHistoryDAO;
import fr.esrf.TangoApi.IDeviceProxyDAO;
import fr.esrf.TangoApi.IIORDumpDAO;

public interface ITangoFactory {

	public abstract IConnectionDAO getConnectionDAO();

	public abstract IDeviceProxyDAO getDeviceProxyDAO();

	public abstract IDatabaseDAO getDatabaseDAO();

	public abstract IDeviceAttributeDAO getDeviceAttributeDAO();

	public abstract IDeviceDataDAO getDeviceDataDAO();

	public abstract IDeviceDataHistoryDAO getDeviceDataHistoryDAO();

	public abstract IApiUtilDAO getApiUtilDAO();

	public abstract IIORDumpDAO getIORDumpDAO();

	public abstract String getFactoryName();
	
}