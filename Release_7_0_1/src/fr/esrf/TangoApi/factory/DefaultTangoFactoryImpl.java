//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:	source code for default factories
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision$
//
// $Log$
//
//-======================================================================



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
