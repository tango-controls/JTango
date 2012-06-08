//+============================================================================
//
//Description: This class provides several useful method to get Quality attribute information
//
//Author: SAINTIN
//
//Revision: 1.1
//
//Log:
//
//copyleft :Synchrotron SOLEIL
//			L'Orme des Merisiers
//			Saint-Aubin - BP 48
//			91192 GIF-sur-YVETTE CEDEX
//			FRANCE
//
//+============================================================================
package fr.esrf.TangoApi;

import java.util.Hashtable;
import fr.esrf.Tango.AttrQuality;

public class QualityUtilities
{
    private Hashtable<AttrQuality, String> QUALITYTABLE = new Hashtable<AttrQuality, String>();
    private static QualityUtilities m_QualityUtilities = null;
    public final static String[] QUALITYIST =
		fr.esrf.TangoDs.TangoConst.Tango_QualityName;
    
    private QualityUtilities()
    {
        QUALITYTABLE.put(AttrQuality.ATTR_VALID,
				QUALITYIST[AttrQuality._ATTR_VALID]);
        QUALITYTABLE.put(AttrQuality.ATTR_INVALID,
				QUALITYIST[AttrQuality._ATTR_INVALID]);
        QUALITYTABLE.put(AttrQuality.ATTR_ALARM,
				QUALITYIST[AttrQuality._ATTR_ALARM]);
        QUALITYTABLE.put(AttrQuality.ATTR_CHANGING,
				QUALITYIST[AttrQuality._ATTR_CHANGING]);
        QUALITYTABLE.put(AttrQuality.ATTR_WARNING,
				QUALITYIST[AttrQuality._ATTR_WARNING]);
    }
    
    public static QualityUtilities getInstance()
    {
        if(m_QualityUtilities == null)
            m_QualityUtilities = new QualityUtilities();
        
        return m_QualityUtilities;
    }
    
    public static Hashtable getQualityTable()
    {
        return QualityUtilities.getInstance().QUALITYTABLE;
    }
    
    public static AttrQuality getQualityForName(String aQualityName)
    {
        try 
        {
            //To avoid the pb of case
            aQualityName = "ATTR_" + aQualityName.trim().toUpperCase();
            //Use the reflection
            return (AttrQuality) AttrQuality.class.getField(aQualityName).get(aQualityName);
        }
        catch (Exception e)
        {
            return AttrQuality.ATTR_INVALID;
        }
    }
    
    public static String getNameForQuality(AttrQuality aQuality)
    {
        //return (String)QualityUtilities.getInstance().QUALITYTABLE.get(aQuality);
		return ApiUtil.qualityName(aQuality);
    }
    public static String getNameForQuality(short att_q_val)
    {
		return ApiUtil.qualityName(att_q_val);
    }
    
    public static boolean isQualityExist(String aQualityName){
        return QualityUtilities.getInstance().QUALITYTABLE.containsValue(aQualityName);
    }
}
