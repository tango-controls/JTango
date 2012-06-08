//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:	source code 
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
// Revision 1.3  2009/03/31 07:49:32  pascal_verdier
// Tango-7.
//
// Revision 1.2  2008/10/10 11:38:00  pascal_verdier
// Headers changed for LGPL conformity.
//
//
//-======================================================================


package fr.esrf.Tango.factory;

import fr.esrf.TangoApi.*;

public interface ITangoFactory {

	public abstract IConnectionDAO getConnectionDAO();

	public abstract IDeviceProxyDAO getDeviceProxyDAO();

	public abstract ITacoTangoDeviceDAO getTacoTangoDeviceDAO();

	public abstract IDatabaseDAO getDatabaseDAO();

	public abstract IDeviceAttributeDAO getDeviceAttributeDAO();

	public abstract IDeviceAttribute_3DAO getDeviceAttribute_3DAO();

	public abstract IDeviceDataDAO getDeviceDataDAO();

	public abstract IDeviceDataHistoryDAO getDeviceDataHistoryDAO();

	public abstract IApiUtilDAO getApiUtilDAO();

	public abstract IIORDumpDAO getIORDumpDAO();

	public abstract String getFactoryName();

}
