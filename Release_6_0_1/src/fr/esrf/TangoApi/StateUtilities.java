//+============================================================================
//
//Description: This class provides several useful method to get State device information
//
//Author: SAINTIN
//
//Revision: 1.1
//
//Log:
//
//copyleft :Synchrotron SOLEIL
//          L'Orme des Merisiers
//          Saint-Aubin - BP 48
//          91192 GIF-sur-YVETTE CEDEX
//          FRANCE
//
//+============================================================================
package fr.esrf.TangoApi;

import java.util.Hashtable;
import fr.esrf.Tango.DevState;

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
