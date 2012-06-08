//+============================================================================
//$ Source: package fr.soleil.TangoArchiving.HdbApi;/GetConf.java
//
//project :     ArchivingDev
//
//Description: This class hides
//
//$Author$
//
//$Revision$
//
// $Log$
// Revision 1.6  2006/05/04 14:28:09  ounsy
// minor changes (commented useless methods and variables)
//
// Revision 1.5  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.4.10.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.4  2005/06/24 12:06:11  chinkumo
// Some constants were moved from fr.soleil.TangoArchiving.ArchivingApi.ConfigConst to fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst.

// This change was reported here.
//
// Revision 1.3  2005/06/14 10:12:12  chinkumo
// Branch (tangORBarchiving_1_0_1-branch_0)  and HEAD merged.
//
// Revision 1.2.4.2  2005/06/13 15:20:52  chinkumo
// Changes made to improve the management of Exceptions were reported here.
//
// Revision 1.2.4.1  2005/05/12 16:46:18  chinkumo
// A check control is done to be sure that the property being retrieved is defined into the static database.
//
// Revision 1.2  2005/01/26 15:35:37  chinkumo
// Ultimate synchronization before real sharing.
//
// Revision 1.1  2004/12/06 17:39:56  chinkumo
// First commit (new API architecture).
//
//
//copyleft :Synchrotron SOLEIL
//			L'Orme des Merisiers
//			Saint-Aubin - BP 48
//			91192 GIF-sur-YVETTE CEDEX
//			FRANCE
//
//+============================================================================
/**
 * <B>File</B>           :   GetConf.java<br/>
 * <B>Project</B>        :   HDB configuration java classes (hdbconfig package)<br/>
 * <B>Description</B>    :   This file contains all the values customizd by the cliebt<br/>
 * <B>Original</B>       :   Mar 4, 2003   -   6:13:47 PM
 * @author	ho
 * @version	$Revision$
 */

package fr.soleil.TangoArchiving.ArchivingApi;

import fr.esrf.TangoApi.DbDatum;
import fr.esrf.TangoApi.Database;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst;

public class GetConf
{
	private static final String m_hostProperty = "dbHost";
	private static final String m_nameProperty = "dbName";
	private static final String m_schemaProperty = "dbSchema";
	private static final String m_typeProperty = "dbType";
	private static final String m_facilityProperty = "facility";
	//private static final String m_userProperty = "dbUser";
	//private static final String m_passProperty = "dbPassword";


	/**
	 * return the host property define for the given class
	 *
	 * @param className , the name of the class
	 * @throws ArchivingException
	 */
	public static String getHost(String className) throws ArchivingException
	{
		String propname = m_hostProperty;
		try
		{
			Database dbase = ApiUtil.get_db_obj();
			String property = new String();
			DbDatum dbdatum = dbase.get_class_property(className , propname);
			if ( !dbdatum.is_empty() )
				property = dbdatum.extractString();
			return property;
		}
		catch ( DevFailed devFailed )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION + " or " + propname + " property missing...";
			String desc = "Failed while executing GetConf.getHost() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}

	/**
	 * return the name property define for the given class
	 *
	 * @param className , the name of the class
	 * @throws ArchivingException
	 */
	public static String getName(String className) throws ArchivingException
	{
		String propname = m_nameProperty;
		try
		{
			Database dbase = ApiUtil.get_db_obj();
			String property = new String();
			DbDatum dbdatum = dbase.get_class_property(className , propname);
			if ( !dbdatum.is_empty() )
				property = dbdatum.extractString();
			return property;
		}
		catch ( DevFailed devFailed )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION + " or " + propname + " property missing...";
			String desc = "Failed while executing GetConf.getName() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}

	/**
	 * return the name property define for the given class
	 *
	 * @param className , the name of the class
	 * @throws ArchivingException
	 */
	public static String getSchema(String className) throws ArchivingException
	{
		String propname = m_schemaProperty;
		try
		{
			Database dbase = ApiUtil.get_db_obj();
			String property = new String();
			DbDatum dbdatum = dbase.get_class_property(className , propname);
			if ( !dbdatum.is_empty() )
				property = dbdatum.extractString();
			return property;
		}
		catch ( DevFailed devFailed )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION + " or " + propname + " property missing...";
			String desc = "Failed while executing GetConf.getSchema() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}

	/**
	 * return the name property define for the given class
	 *
	 * @param className , the name of the class
	 * @throws ArchivingException
	 */
	public static int getType(String className) throws ArchivingException
	{
		String propname = m_typeProperty;
		try
		{
			Database dbase = ApiUtil.get_db_obj();
			int property = ConfigConst.BD_MYSQL;
			DbDatum dbdatum = dbase.get_class_property(className , propname);
			if ( !dbdatum.is_empty() )
				property = dbdatum.extractLong();
			return property;
		}
		catch ( DevFailed devFailed )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION + " or " + propname + " property missing...";
			String desc = "Failed while executing GetConf.getType() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}

	/**
	 * return the facility property define for the given class
	 *
	 * @param className , the name of the class
	 * @throws ArchivingException
	 */
	public static boolean getFacility(String className) throws ArchivingException
	{
		String propname = m_facilityProperty;
		try
		{
			Database dbase = ApiUtil.get_db_obj();
			boolean property = false;
			propname = m_facilityProperty;
			DbDatum dbdatum = dbase.get_class_property(className , propname);
			if ( !dbdatum.is_empty() )
				property = dbdatum.extractBoolean();
			return property;
		}
		catch ( DevFailed devFailed )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION + " or " + propname + " property missing...";
			String desc = "Failed while executing GetConf.getFacility() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}
}
