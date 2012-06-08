//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  GetConf.
//						(Chinkumo Jean) - Mar 4, 2003
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2006/05/04 14:33:35  ounsy
// minor changes (commented useless methods and variables)
//
// Revision 1.3  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.2.10.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.2  2005/06/28 09:10:16  chinkumo
// Changes made to improve the management of exceptions were reported here.
//
// Revision 1.1  2005/01/26 15:35:38  chinkumo
// Ultimate synchronization before real sharing.
//
// Revision 1.1  2004/12/06 17:39:56  chinkumo
// First commit (new API architecture).
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================

package fr.soleil.TangoSnapshoting.SnapshotingApi;

import fr.esrf.TangoApi.Database;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.DbDatum;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapshotingException;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.GlobalConst;

public class GetConf
{
	private static final String m_hostProperty = "dbHost";
	private static final String m_nameProperty = "dbName";
	private static final String m_schemaProperty = "dbSchema";
	private static final String m_typeProperty = "dbType";
	//private static final String m_userProperty = "dbUser";
	//private static final String m_passProperty = "dbPassword";
	private static final String m_facilityProperty = "facility";

	/**
	 * return the host property define for the given class
	 *
	 * @param className , the name of the class
	 */
	public static String getHost(String className) throws SnapshotingException
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
			String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION + " or " + propname + " property missing...";
			String desc = "Failed while executing GetConf.getHost() method...";
			throw new SnapshotingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}

	/**
	 * return the name property define for the given class
	 *
	 * @param className , the name of the class
	 */
	public static String getName(String className) throws SnapshotingException
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
			String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION + " or " + propname + " property missing...";
			String desc = "Failed while executing GetConf.getName() method...";
			throw new SnapshotingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}

	/**
	 * return the name property define for the given class
	 *
	 * @param className , the name of the class
	 */
	public static String getSchema(String className) throws SnapshotingException
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
			String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION + " or " + propname + " property missing...";
			String desc = "Failed while executing GetConf.getSchema() method...";
			throw new SnapshotingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}

	/**
	 * return the name property define for the given class
	 *
	 * @param className , the name of the class
	 */
	public static int getType(String className) throws SnapshotingException
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
			String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION + " or " + propname + " property missing...";
			String desc = "Failed while executing GetConf.getType() method...";
			throw new SnapshotingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}

	/**
	 * return the facility property define for the given class
	 *
	 * @param className , the name of the class
	 * @throws SnapshotingException
	 */
	public static boolean getFacility(String className) throws SnapshotingException
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
			String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX;
			String reason = GlobalConst.TANGO_COMM_EXCEPTION + " or " + propname + " property missing...";
			String desc = "Failed while executing GetConf.getFacility() method...";
			throw new SnapshotingException(message , reason , ErrSeverity.WARN , desc , "" , devFailed);
		}
	}
}
