//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: pascal_verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,
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
// $Revision: 25296 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.AttrQuality;

import java.util.Hashtable;

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
