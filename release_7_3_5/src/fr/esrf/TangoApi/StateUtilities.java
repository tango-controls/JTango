//+============================================================================
//
// Description: This class provides several useful method to get State device information
//
// Author: SAINTIN
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
// $Revision: 1.1
//
// $Log$
// Revision 1.3  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.2  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
//
//
//+============================================================================



package fr.esrf.TangoApi;

import fr.esrf.Tango.DevState;

import java.util.Hashtable;

public class StateUtilities
{
    private Hashtable<DevState, String> STATETABLE = new Hashtable<DevState, String>();
    private static StateUtilities m_StateUtilities = null;
	 public final static String[]	STATELIST =
	 		fr.esrf.TangoDs.TangoConst.Tango_DevStateName;
	/*
    public final static String[] STATELIST = new String[] { "ON", "OFF",
            "CLOSE", "OPEN", "INSERT", "EXTRACT", "MOVING", "STANDBY", "FAULT",
            "INIT", "RUNNING", "ALARM", "DISABLE", "UNKNOWN" };
	*/
    
    private StateUtilities()
    {
        STATETABLE.put(DevState.ON,      STATELIST[DevState._ON]);
        STATETABLE.put(DevState.OFF,     STATELIST[DevState._OFF]);
        STATETABLE.put(DevState.CLOSE,   STATELIST[DevState._CLOSE]);
        STATETABLE.put(DevState.OPEN,    STATELIST[DevState._OPEN]);
        STATETABLE.put(DevState.INSERT,  STATELIST[DevState._INSERT]);
        STATETABLE.put(DevState.EXTRACT, STATELIST[DevState._EXTRACT]);
        STATETABLE.put(DevState.MOVING,  STATELIST[DevState._MOVING]);
        STATETABLE.put(DevState.STANDBY, STATELIST[DevState._STANDBY]);
        STATETABLE.put(DevState.FAULT,   STATELIST[DevState._FAULT]);
        STATETABLE.put(DevState.INIT,    STATELIST[DevState._INIT]);
        STATETABLE.put(DevState.RUNNING, STATELIST[DevState._RUNNING]);
        STATETABLE.put(DevState.ALARM,   STATELIST[DevState._ALARM]);
        STATETABLE.put(DevState.DISABLE, STATELIST[DevState._DISABLE]);
        STATETABLE.put(DevState.UNKNOWN, STATELIST[DevState._UNKNOWN]);
    }
    
    public static StateUtilities getInstance()
    {
        if(m_StateUtilities == null)
            m_StateUtilities = new StateUtilities();
        
        return m_StateUtilities;
    }
    
    public static Hashtable getStateTable()
    {
        return StateUtilities.getInstance().STATETABLE;
    }
    
    public static DevState getStateForName(String aStateName)
    {
        try 
        {
            //To avoid the pb of case
            aStateName = aStateName.trim().toUpperCase();
            //Use the reflection
            return (DevState) DevState.class.getField(aStateName).get(aStateName);
        }
        catch (Exception e)
        {
            return DevState.UNKNOWN;
        }
    }
    
    public static String getNameForState(DevState aDevState)
    {
        //return (String)StateUtilities.getInstance().STATETABLE.get(aDevState);
		return ApiUtil.stateName(aDevState);
    }
    public static String getNameForState(short st_val)
    {
		return ApiUtil.stateName(st_val);
    }

    public static boolean isStateExist(String aStateName){
        return StateUtilities.getInstance().STATETABLE.containsValue(aStateName);
    }
}
