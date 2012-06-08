//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  ConfigConst.
//						(Chinkumo Jean) - Mar 4, 2003
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.9  2007/04/05 09:55:34  ounsy
// added the default_sabeansfilename property
//
// Revision 1.8  2006/05/12 09:22:06  ounsy
// CLOB_SEPARATOR in GlobalConst
//
// Revision 1.7  2006/05/04 14:32:53  ounsy
// CLOB_SEPARATOR centralized in ConfigConst
//
// Revision 1.6  2006/02/28 17:05:58  chinkumo
// no message
//
// Revision 1.5  2006/02/17 11:15:35  chinkumo
// no message
//
// Revision 1.4  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.3.2.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.3  2005/08/19 14:04:02  chinkumo
// no message
//
// Revision 1.2.12.1.2.1  2005/08/12 08:08:16  chinkumo
// Unused constants removed.
//
// Revision 1.2.12.1  2005/08/01 13:49:57  chinkumo
// Several changes carried out for the support of the new graphical application (Bensikin).
//
// Revision 1.2  2005/02/04 14:50:42  chinkumo
// Table's name changed in lower cases because of troubles on UNIX platform.
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

import java.io.File;

/**
 * <B>File</B>           :   ConfigConst.java<br/>
 * <B>Project</B>        :   Configuration java classes (hdbconfig package)<br/>
 * <B>Description</B>    :   This file contains all the constants and functions  used by all other classes of the package<br/>
 */
public class ConfigConst
{
	/* ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	                                                                DataBase defaults parameters
	||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||*/

	// ----------------------------------- >>		Historical DataBase
	/**
	 * Parameter that represents the default database host
	 */
	public static final String default_shost = "localhost";
	/**
	 * Parameter that represents the default database names
	 */
	public static final String default_sbd = "hdb";
	/**
	 * Parameter that represents the default database's schema name
	 */
	public static final String default_sschema = "snap"; //
	/**
	 * Parameter that represents the default database manager user id (operators...)
	 */
	public static final String default_smuser = "snap"; //"manager";
	/**
	 * Parameter that represents the default database manager user password
	 */
	public static final String default_smpasswd = "snap"; //"manager";
	/**
	 * Parameter that represents the default database archiver user id (archivers...)
	 */
	public static final String default_sauser = "archiver";
	/**
	 * Parameter that represents the default database archiver user password
	 */
	public static final String default_sapasswd = "archiver";
    /**
     * Parameter that represents the default database archiver user password
     */
    public static final String default_sabeansfilename = "beans.xml";
	/**
	 * Parameter that represents the default database browser user id
	 */
	public static final String default_sbuser = "browser";
	/**
	 * Parameter that represents the default database browser user password (for the default user...)
	 */
	public static final String default_sbpasswd = "browser";
    
    

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
	 * Parameter that represents the PostGreSQL database driver
	 */
	public static final String DRIVER_POSTGRESQL = ""; // todo Give a value to the PostGres JDBC driver
	/**
	 * Port number for the connection
	 */
	public final static String ORACLE_PORT = "1521";


	/* ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	                                                                            Miscellaneous global constants
	    ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||*/
	/**
	 * Parameter that represents an empty String
	 */
	public static final String EMPTY = "";
	// todo mettre en place une solution plus générique pour le "default_path"
	public static String default_path = "C:" + File.separator + "tango" + File.separator + "dsnap" + File.separator + "snapshots";

}
