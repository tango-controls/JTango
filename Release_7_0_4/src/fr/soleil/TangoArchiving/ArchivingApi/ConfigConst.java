//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  This file contains all the constants and functions used by all other classes of the package.
//						(Chinkumo Jean) - Dec 1, 2002
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.16  2007/03/22 16:21:36  pierrejoseph
// DataBaseApi Refactoring
//
// Revision 1.15  2006/11/16 10:52:14  ounsy
// no autocommit for oracle database
//
// Revision 1.14  2006/05/12 09:22:06  ounsy
// CLOB_SEPARATOR in GlobalConst
//
// Revision 1.13  2006/05/04 14:27:30  ounsy
// minor changes
//
// Revision 1.12  2006/02/24 12:03:14  ounsy
// CLOB_SEPARATOR_MYSQL and CLOB_SEPARATOR_ORACLE are merged
// back into CLOB_SEPARATOR
//
// Revision 1.11  2006/02/20 14:15:06  ounsy
// clob separator oracle/mysql
//
// Revision 1.10  2006/02/16 14:31:58  chinkumo
// The spectrums and images database table's fields were renamed.
// This was reported here.
//
// Revision 1.9  2006/02/08 14:22:16  ounsy
// added a CLOB_SEPARATOR=; constant
//
// Revision 1.8  2006/02/08 12:42:55  chinkumo
// The 'dim1' parameter was renamed into 'dim' (TAB_SPECTRUM_RW).
//
// Revision 1.7  2006/02/07 10:03:16  chinkumo
// AUTO_COMMIT_DEFAULT parameter added.
//
// Revision 1.6  2005/11/29 17:11:16  chinkumo
// no message
//
// Revision 1.5.10.1  2005/11/15 13:34:37  chinkumo
// no message
//
// Revision 1.5  2005/06/24 12:03:34  chinkumo
// All constants related to errors were moved from fr.soleil.TangoArchiving.ArchivingApi.ConfigConst to fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst.
//
// Revision 1.4  2005/06/14 10:12:12  chinkumo
// Branch (tangORBarchiving_1_0_1-branch_0)  and HEAD merged.
//
// Revision 1.3.4.2  2005/06/13 15:31:45  chinkumo
// New constants were created since exceptions are now managed by the archiving service.
//
// Revision 1.3.4.1  2005/05/03 16:37:01  chinkumo
// Constants were renamed and put upper case.
//
// Revision 1.3  2005/03/07 16:26:35  chinkumo
// Minor change (tag renamed)
//
// Revision 1.2  2005/01/26 15:35:37  chinkumo
// Ultimate synchronization before real sharing.
//
// Revision 1.1  2004/12/06 17:39:56  chinkumo
// First commit (new API architecture).
//
//
// copyleft :   Synchrotron SOLEIL
//			    L'Orme des Merisiers
//			    Saint-Aubin - BP 48
//			    91192 GIF-sur-YVETTE CEDEX
//              FRANCE
//
//+============================================================================

package fr.soleil.TangoArchiving.ArchivingApi;

import java.io.File;

/**
 * <B>File</B>           :   ConfigConst.java<br/>
 * <B>Project</B>        :   HDB configuration java classes (hdbconfig package)<br/>
 * <B>Description</B>    :   This file contains all the constants and functions
 * used by all other classes of the package<br/>
 * <B>Original</B>       :   Mar 4, 2003   -   6:13:47 PM
 *
 * @author	Jean CHINKUMO - Synchrotron SOLEIL
 * @version	$Revision$
 */
public class ConfigConst
{
	/* ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	*                                       PART 1 :      DataBase defaults parameters
	* ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||*/

	// ----------------------------------- >>		Historical DataBase
	/**
	 * Parameter that represents the default database host
	 */
	public static final String HDB_HOST = "localhost";
	/**
	 * Parameter that represents the default database names
	 */
	public static final String HDB_SCHEMA_NAME = "hdb";
	/**
	 * Parameter that represents the default database manager user id (operators...)
	 */
	public static final String HDB_MANAGER_USER = "manager";
	/**
	 * Parameter that represents the default database manager user password
	 */
	public static final String HDB_MANAGER_PASSWORD = "manager";
	/**
	 * Parameter that represents the default database archiver user id (archivers...)
	 */
	public static final String HDB_ARCHIVER_USER = "archiver";
	/**
	 * Parameter that represents the default database archiver user password
	 */
	public static final String HDB_ARCHIVER_PASSWORD = "archiver";
	/**
	 * Parameter that represents the default database browser user id
	 */
	public static final String HDB_BROWSER_USER = "browser";
	/**
	 * Parameter that represents the default database browser user password (for the default user...)
	 */
	public static final String HDB_BROWSER_PASSWORD = "browser";
	
	/** 
	 * Parameter that represents the HDB device class name
	 */
	public static final String HDB_CLASS_DEVICE = "HdbArchiver";

	// ----------------------------------- >>		Temporary DataBase
	/**
	 * Parameter that represents the default database host
	 */
	public static final String TDB_HOST = "localhost";
	/**
	 * Parameter that represents the default database names
	 */
	public static final String TDB_SCHEMA_NAME = "tdb";
	/**
	 * Parameter that represents the default database manager user id (operators...)
	 */
	public static final String TDB_MANAGER_USER = "manager";
	/**
	 * Parameter that represents the default database manager user password
	 */
	public static final String TDB_MANAGER_PASSWORD = "manager";
	/**
	 * Parameter that represents the default database archiver user id (archivers...)
	 */
	public static final String TDB_ARCHIVER_USER = "archiver";
	/**
	 * Parameter that represents the default database archiver user password
	 */
	public static final String TDB_ARCHIVER_PASSWORD = "archiver";
	/**
	 * Parameter that represents the default database browser user id
	 */
	public static final String TDB_BROWSER_USER = "browser";
	/**
	 * Parameter that represents the default database browser user password (for the default user...)
	 */
	public static final String TDB_BROWSER_PASSWORD = "browser";
	/** 
	 * Parameter that represents the TDB device class name
	 */
	public static final String TDB_CLASS_DEVICE = "TdbArchiver";


	// ----------------------------------- >>		DataBase Type
	/**
	 * Parameter that represents the MySQL database type
	 */
	public static final int BD_MYSQL = 0;
	/**
	 * Parameter that represents the Oracle database type
	 */
	public static final int BD_ORACLE = 1;
	/**
	 * Parameter that represents the PostGreSQL database type
	 */
	public static final int BD_POSTGRESQL = 2;

	// ----------------------------------- >>		Drivers Types
	/**
	 * Parameter that represents the MySQL database driver
	 */
	public static final String DRIVER_MYSQL = "jdbc:mysql";
	/**
	 * Parameter that represents the Oracle database driver
	 */
	public static final String DRIVER_ORACLE = "jdbc:oracle:thin";
	/**
	 * Port number for the connection
	 */
	public final static String ORACLE_PORT = "1521";

    /**
	 * Auto commit database default value
	 */
	public final static boolean AUTO_COMMIT_DEFAULT = true;

    /**
     * Auto commit database for mysql
     */
    public final static boolean AUTO_COMMIT_MYSQL = true;

    /**
     * Auto commit database for oracle
     */
    public final static boolean AUTO_COMMIT_ORACLE = false;


	/* ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	*                                     PART 2 :      Special constants used to describe the database structure
	||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||*/
	/**
	 * Array that contains the main database table's name
	 */
	public static final String[] TABS = {"adt", "apt", "amt"};  // 0 -> 2

	/**
	 * Array that contains the name's fields for the database Definition Table
	 */
	public static final String[] TAB_DEF = {"ID", "time", "full_name", "device", // 0 -> 3
	                                        "domain", "family", "member", "att_name", // 4 -> 7
	                                        "data_type", "data_format", "writable", // 8 -> 10
	                                        "max_dim_x", "max_dim_y", // 11 -> 12
	                                        "levelg", "facility", "archivable", "substitute"};               // 13 -> 16

	/**
	 * Array that contains the name's fields for the database Property Table
	 */
	// todo add the "optional_properties" field !!!
	public static final String[] TAB_PROP = {"ID", "time", // 0 -> 1
	                                         "description", "label", // 2 -> 3
	                                         "unit", "standard_unit", "display_unit", "format", // 4 -> 7
	                                         "min_value", "max_value", "min_alarm", "max_alarm"};    // 8 -> 11

	/**
	 * Array that contains the name's fields for the database Mode Table
	 * If database is historic, its lenght will be 23
	 * If database is temporary, its lenght will be 25
	 */
	public static final String[] TAB_MOD = {"ID", "archiver", "start_date", "stop_date", // 0 -> 3
	                                        "per_mod", "per_per_mod", // 4 -> 5
	                                        "abs_mod", "per_abs_mod", "dec_del_abs_mod", "gro_del_abs_mod", // 6 -> 9
	                                        "rel_mod", "per_rel_mod", "n_percent_rel_mod", "p_percent_rel_mod", // 10 -> 13
	                                        "thr_mod", "per_thr_mod", "min_val_thr_mod", "max_val_thr_mod", // 14 -> 17
	                                        "cal_mod", "per_cal_mod", "val_cal_mod", "type_cal_mod", "algo_cal_mod", // 18 -> 22
	                                        "dif_mod", "per_dif_mod", "ext_mod", // 23 -> 25
	                                        "export_per", "keeping_per"};		// 26 -> 27

	/**
	 * Parameter that represents the prefix substring for attributes tables
	 */
	public static final String TAB_PREF = "att_";
	/**
	 * Array that contains the name's fields for the Scalar_Read Attribute's Table
	 */
	public static final String[] TAB_SCALAR_RO = {"time", "value"};
	/**
	 * Array that contains the name's fields for the Scalar_Write Attribute's Table
	 */
	public static final String[] TAB_SCALAR_WO = {"time", "value"};
	/**
	 * Array that contains the name's fields for the Scalar_ReadWrite Attribute's Table
	 */
	public static final String[] TAB_SCALAR_RW = {"time", "read_value", "write_value"};
	/**
	 * Array that contains the name's fields for the Spectrum_Read Attribute's Table
	 */
	public static final String[] TAB_SPECTRUM_RO = {"time", "dim_x", "value"};
	/**
	 * Array that contains the name's fields for the Spectrum_ReadWrite Attribute's Table
	 */
	public static final String[] TAB_SPECTRUM_RW = {"time", "dim_x", "read_value", "write_value"};
	/**
	 * Array that contains the name's fields for the Image_Read Attribute's Table
	 */
	public static final String[] TAB_IMAGE_RO = {"time", "dim_x", "dim_y", "value"};
	/**
	 * Array that contains the name's fields for the Image_ReadWrite Attribute's Table
	 */
	public static final String[] TAB_IMAGE_RW = {"time", "dim_x", "dim_y", "read_value", "write_value"};


	/* ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	*                                     PART 4 :      Miscellaneous global constants
	* ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||*/

	public static boolean FACILITY_NAMED = false;

	// todo mettre en place une solution plus générique pour le "common_dir_path"
	static String common_dir_path = "D:" + File.separator + "Temporaire" + File.separator + "partage";

	public static String NEW_LINE = "\r\n";
	public static String FIELDS_LIMIT = ",\n";
	public static String LINES_LIMIT = "\n" + "%%%" + "\n";

}
