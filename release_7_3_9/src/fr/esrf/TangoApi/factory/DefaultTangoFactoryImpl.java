//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:	source code for default factories
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
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
// Revision 1.3  2009/09/24 12:34:45  pascal_verdier
// ITangoFactory#getDeviceAttribute_3DAO added
//
// Revision 1.2  2008/10/10 11:29:53  pascal_verdier
// Headers changed for LGPL conformity.
//
//
//-======================================================================



package fr.esrf.TangoApi.factory;

import fr.esrf.Tango.factory.ITangoFactory;
import fr.esrf.TangoApi.*;

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
		 * @see fr.esrf.TangoApi.factory.ITangoFactory#getTacoTangoDeviceDAO()
		 */
	public ITacoTangoDeviceDAO getTacoTangoDeviceDAO()
	{
		return new TacoTangoDeviceDAODefaultImpl();
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
		 * @see fr.esrf.TangoApi.factory.ITangoFactory#getDeviceAttribute_3DAO()
		 */
	public IDeviceAttribute_3DAO getDeviceAttribute_3DAO()
	{
		return new DeviceAttribute_3DAODefaultImpl();
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
