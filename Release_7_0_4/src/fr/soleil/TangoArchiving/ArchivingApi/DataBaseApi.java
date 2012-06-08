//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  This class hides the loading of drivers, the connection/disconnection with the database, and more generaly, this class hides number of methods to insert, update and query HDB.
//						(Chinkumo Jean) - Dec 1, 2002
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.129  2007/05/16 09:48:08  ounsy
// minor change
//
// Revision 1.128  2007/05/10 14:59:21  ounsy
// NaN and null distinction
//
// Revision 1.127  2007/05/09 16:41:53  chinkumo
// minor changes
//
// Revision 1.126  2007/04/24 12:09:45  ounsy
// corrected deleteOldRecords so that it continues running if one attribute fails
//
// Revision 1.125  2007/04/24 12:06:49  ounsy
// correctd registerInAPT so that it truncates the description field if it's longer than 255
//
// Revision 1.124  2007/03/16 14:00:50  ounsy
// modified getCurrentArchivedAtt
//
// Revision 1.123  2007/03/16 08:38:29  ounsy
// added a boolean parameter to getTimeOfLastInsert, to choose between min or max time
//
// Revision 1.122  2007/03/05 16:26:08  ounsy
// non-static DataBase
//
// Revision 1.121  2007/03/05 13:48:31  ounsy
// corrected a bug in getTimeOfLastInsert
//
// Revision 1.120  2007/02/28 09:46:37  ounsy
// better null values management
//
// Revision 1.119  2007/02/26 09:53:18  ounsy
// modified deleteOldRecords() so that it uses truncate when possible
//
// Revision 1.118  2007/02/22 08:21:30  ounsy
// slight correction in getTableName()
//
// Revision 1.117  2007/02/21 13:12:08  ounsy
// changed getAttDataBetweenDates()
// the expected input date format is now YYYY-MM-DD hh:mm:ss
// instead of DD-MM-YYYY hh:mm:ss
//
// Revision 1.116  2007/02/16 09:51:27  ounsy
// getAttDataBetweenDates now takes the same date format in Oracle and MysqL
// (the Oracle one)
//
// Revision 1.115  2007/02/08 10:10:01  ounsy
// minor changes
//
// Revision 1.114  2007/02/05 14:32:38  ounsy
// corrected getTimeOfLastInsert() for MySQL
//
// Revision 1.113  2007/02/05 14:21:41  ounsy
// corrected buildAttributeScalarTab_RW: a scalar boolean attributes needs its value columns to be of type varchar instead of double
// corrected exportToDB: changed the export request from LOAD DATA INFILE to LOAD DATA LOCAL INFILE
//
// Revision 1.112  2007/02/01 13:38:02  pierrejoseph
// some methodes can be private
//
// Revision 1.111  2007/01/31 13:01:46  ounsy
// minor changes
//
// Revision 1.110  2007/01/22 11:02:03  pierrejoseph
// Closing cursors an exception is received during a Spectrum insertion - was done for MySql.
//
// Revision 1.109  2007/01/22 10:45:31  ounsy
// minor changees
//
// Revision 1.108  2007/01/11 14:33:07  ounsy
// allow NaN in database
//
// Revision 1.107  2006/12/08 11:16:07  ounsy
// bad connection management
//
// Revision 1.106  2006/12/06 15:53:59  ounsy
// added parametrisable sampling for MySQL
//
// Revision 1.105  2006/12/06 15:09:04  ounsy
// added parametrisable sampling
//
// Revision 1.104  2006/12/06 10:13:28  ounsy
// added additional sampling options
//
// Revision 1.103  2006/11/30 12:48:45  ounsy
// corrected a cast in insertScalarData
//
// Revision 1.102  2006/11/20 15:41:10  ounsy
// corrected the insert_ScalarData/insert_ScalarData_withId naming problem
//
// Revision 1.101  2006/11/16 10:52:13  ounsy
// no autocommit for oracle database
//
// Revision 1.100  2006/11/09 15:44:47  ounsy
// removed the unused exportToDB_Scalar methods, and renamed exportToDB_Scalar2 to the now available exportToDB_Scalar name
//
// Revision 1.99  2006/11/09 14:18:42  ounsy
// added a get_attributes_complete_names method
//
// Revision 1.98  2006/10/31 16:54:24  ounsy
// milliseconds and null values management
//
// Revision 1.97  2006/10/19 12:32:47  ounsy
// added a getAtt_TFW_Data_By_Id(int id) method
//
// Revision 1.96  2006/10/11 08:32:51  ounsy
// minor changes
//
// Revision 1.95  2006/10/03 08:49:09  ounsy
// minor changes
//
// Revision 1.94  2006/09/29 07:59:39  ounsy
// corrected a bug in getAttributes_by_criterion that prevented attributes loading
//
// Revision 1.93  2006/09/27 13:36:54  ounsy
// replaced LIKE clauses by = clauses where applicable
//
// Revision 1.92  2006/09/26 08:36:18  ounsy
// watcher API methods query optimisation
//
// Revision 1.91  2006/09/25 09:51:44  ounsy
// corrected potential NullPointer bugs on cursor closing
//
// Revision 1.90  2006/09/21 10:11:59  ounsy
// made sure everything is properly closed at the end of methods used by watchers
//
// Revision 1.89  2006/09/20 12:45:35  ounsy
// modified getAttScalarData and getAttSpectrumDataOracle to intercept date parsing exceptions
//
// Revision 1.88  2006/09/18 13:03:48  ounsy
// minor changes
//
// Revision 1.87  2006/09/18 09:54:01  ounsy
// modified getCurrentArchivedAtt() to intercept not only SQLExceptions but any Throwable
//
// Revision 1.86  2006/09/15 12:18:42  ounsy
// corrected a null pointer bug
//
// Revision 1.85  2006/09/13 08:19:41  ounsy
// added a getTimeOfLastInsert(completeName) method
//
// Revision 1.84  2006/09/11 14:33:55  ounsy
// avoiding a dead lock
//
// Revision 1.83  2006/09/07 13:37:34  ounsy
// corrected the ArchivingWatcher problem pf the long first step
//
// Revision 1.82  2006/09/05 13:41:16  ounsy
// updated for sampling compatibility
//
// Revision 1.81  2006/08/29 15:09:22  ounsy
// avoiding some exceptions and a dead lock (it looks the dead lock is avoided that way)
//
// Revision 1.80  2006/08/29 13:59:04  ounsy
// added the possibility to cancel the current statement dialing with database
//
// Revision 1.79  2006/08/24 10:20:06  ounsy
// minor changes for test
//
// Revision 1.77  2006/08/23 09:38:36  ounsy
// compatible with the new TDB file loading
//
// Revision 1.76  2006/08/08 13:40:15  ounsy
// images bug fix
//
// Revision 1.75  2006/07/28 10:00:15  ounsy
// spectrum RW bug fixed
//
// Revision 1.74  2006/07/27 12:44:18  ounsy
// modified the get_XXXXX_by_criterion methods: added order by in requests to list devices alphabetically
//
// Revision 1.73  2006/07/27 09:00:12  ounsy
// re acctentuation des commentaires
//
// Revision 1.72  2006/07/27 08:42:42  ounsy
// Setting the date of insertion instead of the date of "trigger" in the insertModeRecord() method, in order not to have a new insert date that is earlier than the previous stop date when restarting an archiver
//
// Revision 1.71  2006/07/25 09:44:06  ounsy
// corrected a bug in forceDatabaseToImportFile() : does nothing in the cas of a mySQL DB
//
// Revision 1.70  2006/07/24 09:53:23  ounsy
// now buffering id's for attribute names
//
// Revision 1.69  2006/07/24 07:35:12  ounsy
// better image support
//
// Revision 1.68  2006/07/21 14:46:28  ounsy
// took into account the Tango_DEV_USHORT type
//
// Revision 1.67  2006/07/20 09:26:17  ounsy
// String encoding/decoding
//
// Revision 1.66  2006/07/19 09:00:22  ounsy
// corrected the "load" bug --> now the DataBaseApi associates itself with the ImageData it creates
//
// Revision 1.65  2006/07/18 10:41:25  ounsy
// image support
//
// Revision 1.64  2006/07/18 08:05:35  ounsy
// minor changes
//
// Revision 1.63  2006/07/13 08:30:36  ounsy
// corrected the forceDatabaseToImportFile with the correct PS name
//
// Revision 1.62  2006/07/11 07:33:45  ounsy
// corrected a bug in isLastValueNull
//
// Revision 1.61  2006/07/07 12:56:05  ounsy
// bug correction in isLastDataNull
//
// Revision 1.60  2006/07/06 09:48:24  ounsy
// added a new method exportToDBByOracleJob
//
// Revision 1.59  2006/07/04 10:04:37  ounsy
// minor changes : commented a never used variable
//
// Revision 1.58  2006/07/04 10:01:36  ounsy
// modified the getCurrentArchivedAtt method to avoid NullPointerExeecption
//
// Revision 1.57  2006/06/30 09:21:31  ounsy
// corrected the getAttScalarDataBetweenDates method (NullPointerException)
//
// Revision 1.56  2006/06/30 08:29:52  ounsy
// modified the insert_ScalarData() method so that a null value is substituted to Nan values
//
// Revision 1.55  2006/06/29 08:41:23  ounsy
// corrected a bug in isLastDataNull ()
//
// Revision 1.54  2006/06/20 15:58:03  ounsy
// minor changes
//
// Revision 1.53  2006/06/15 15:49:52  ounsy
// removed useless logs
//
// Revision 1.52  2006/06/14 15:13:23  ounsy
// added a new method
// getArchiverForAttributeEvenIfTheStopDateIsNotNull
//
// Revision 1.51  2006/06/14 14:30:15  ounsy
// better boolean extraction
//
// Revision 1.50  2006/06/07 12:47:09  ounsy
// minor changes
//
// Revision 1.49  2006/06/02 08:42:14  ounsy
// minor changes
//
// Revision 1.48  2006/05/30 12:58:45  ounsy
// added a isLastDataNull method
//
// Revision 1.47  2006/05/16 13:12:09  ounsy
// corrected a little bug in scalar string extracting
//
// Revision 1.46  2006/05/12 09:22:32  ounsy
// CLOB_SEPARATOR in GlobalConst
//
// Revision 1.45  2006/05/05 13:28:47  ounsy
// merge
//
// Revision 1.44  2006/05/05 13:23:05  ounsy
// null state management
//
// Revision 1.43  2006/05/05 09:02:50  ounsy
// added a minimumRequest method
//
// Revision 1.42  2006/05/04 14:28:09  ounsy
// minor changes (commented useless methods and variables)
//
// Revision 1.41  2006/04/11 09:10:26  ounsy
// added some attribute checking methods
//
// Revision 1.40  2006/04/11 08:35:09  ounsy
// added a log
//
// Revision 1.39  2006/04/05 13:50:51  ounsy
// new types full support
//
// Revision 1.38  2006/03/27 15:19:13  ounsy
// new spectrum types support + better spectrum management
//
// Revision 1.37  2006/03/15 16:07:23  ounsy
// states converted to their string value only in GUI
//
// Revision 1.36  2006/03/14 16:15:36  ounsy
// minor changes
//
// Revision 1.35  2006/03/14 12:49:11  ounsy
// corrected the SNAP/spectrums/RW problem
// about the read and write values having the same length
//
// Revision 1.34  2006/03/13 14:40:53  ounsy
// State as an int management
// Long as an int management
//
// Revision 1.33  2006/03/10 11:31:00  ounsy
// state and string support
//
// Revision 1.32  2006/02/24 12:03:43  ounsy
// CLOB_SEPARATOR_MYSQL and CLOB_SEPARATOR_ORACLE are merged
// back into CLOB_SEPARATOR
//
// Revision 1.31  2006/02/20 14:16:30  ounsy
// bug with spectrums rw corrected
//
// Revision 1.30  2006/02/17 13:23:28  ounsy
// small modifications
//
// Revision 1.29  2006/02/16 14:35:58  chinkumo
// The Oracles procedures were renamed (on server).
// This was reported here.
//
// Revision 1.28  2006/02/15 14:33:52  chinkumo
// The Oracles procedures were renamed (on server).
// This was reported here.
//
// Revision 1.27  2006/02/15 11:45:52  chinkumo
// File import (temporary archiving) enhanced.
//
// Revision 1.26  2006/02/15 09:52:16  ounsy
// Corrected a bug in insertSpectrumData_RW that occurred when the
// R and W parts didn't have the same length
//
// Revision 1.25  2006/02/13 09:06:17  chinkumo
// Methods 'exportToDB_ScalarRO/WO/RW', 'exportToDB_SpectrumRO/WO/RW' and 'exportToDB_ImageRO/WO/RW' were generalized into 'exportToDB_Scalar', 'exportToDB_Spectrum' and 'exportToDB_Image'.
//
// Revision 1.24  2006/02/08 14:58:36  ounsy
// took into account the potential R/W asymmetry
// in getAttDataXXX methods
//
// Revision 1.23  2006/02/08 10:04:07  chinkumo
// insert_SpectrumData_RO and insert_SpectrumData_RW Oracle policy was changed.
//
// Revision 1.22  2006/02/07 11:52:17  ounsy
// bug corrected in all getAttSpectrumDataXXXs
//
// Revision 1.21  2006/02/06 16:28:52  ounsy
// Bug correction : if the spectrum did not respond at a precise date, its dim is set to 0 at this date.
// When the dim equals 0, consider that the value can not be represented
//
// Revision 1.20  2006/02/06 15:16:21  ounsy
// corrected a bug in getAttSpectrumDataBetweenDatesMySql
//
// Revision 1.19  2006/02/06 12:57:31  ounsy
// removed useless logs
//
// Revision 1.18  2006/02/06 12:54:40  ounsy
// added support for spectrum RO/RW extraction and archiving
// (methods getAttDataXXX, getAttDataBetweenDatesXXX,getAttDataLastNXXX )
//
// Revision 1.17  2006/01/27 14:13:00  ounsy
// spectrum extraction allowed
//
// Revision 1.16  2006/01/27 13:00:24  ounsy
// organised imports
//
// Revision 1.15  2006/01/20 09:46:35  ounsy
// Modified the "now ()" method to take the Oracle case into account
// (the sysdate syntax is different)
//
// Revision 1.14  2006/01/19 15:38:57  ounsy
// Added a now () method to return the database's sysdate
//
// Revision 1.13  2005/11/29 17:11:16  chinkumo
// no message
//
// Revision 1.12.2.4  2005/11/15 13:34:37  chinkumo
// no message
//
// Revision 1.12.2.3  2005/09/26 08:44:15  chinkumo
// The getAttDataSupThanBetweenDates(..), getAttDataInfOrSupThan(..) and getAttDataSupAndInfThan(..) methods's bugs were fixed.
//
// Revision 1.12.2.2  2005/09/14 14:07:45  chinkumo
// The bug in the 'insert_ScalarData(..)' method was fixed.
// The oracle side bug in the 'getAttDataBetweenDates(..)' was fixed.
// New methods was implemented to allow filtering :
// 	- get_domains_by_criterion
// 	- get_families_by_criterion
// 	- get_members_by_criterion
// 	- getAttributes_by_criterion(..)
//
// Revision 1.12.2.1  2005/09/09 08:26:42  chinkumo
// Since the extraction politic changed to 'dynamic attribute' (see H/TdbExtractors) the extraction methods were updated.
// Since the collecting politic was improved (see Collectors in H/TdbArchivers) the feeding methods were updated.
//
// Revision 1.12  2005/08/19 14:03:58  chinkumo
// no message
//
// Revision 1.11.6.1.2.1  2005/08/05 10:14:19  chinkumo
// The 'opened cursor' bug was fixed. The statement in the the 'insert_SpectrumData_RO' was closed.
//
// Revision 1.11.6.1  2005/08/04 08:11:17  chinkumo
// The time management in the insert_SpectrumData_RO(...) was simplified.
//
// Revision 1.11  2005/06/28 09:10:54  chinkumo
// A bug was fixed in the alterSession method.
//
// Revision 1.10  2005/06/24 12:06:09  chinkumo
// Some constants were moved from fr.soleil.TangoArchiving.ArchivingApi.ConfigConst to fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst.
// This change was reported here.
//
// Revision 1.9  2005/06/14 10:12:12  chinkumo
// Branch (tangORBarchiving_1_0_1-branch_0)  and HEAD merged.
//
// Revision 1.8.2.4  2005/06/13 15:32:06  chinkumo
// Changes made to improve the management of Exceptions were reported here.
//
// Revision 1.8.2.3  2005/05/11 14:40:01  chinkumo
// Some comments were corrected.
//
// Revision 1.8.2.2  2005/04/29 18:29:55  chinkumo
// Date format changed to improve efficiency while  archiving. This improve a lot temporary archiving.
//
// Revision 1.8.2.1  2005/04/12 13:53:38  chinkumo
// The management of null values (Double.NaN) was improved.
// Some changes was made in the insertion part to improve its efficiency (String > BufferString).
//
// Revision 1.8  2005/04/06 19:05:22  chinkumo
// Changes done to optimize the use of String object type
//
// Revision 1.7  2005/03/10 23:09:11  chinkumo
// Defaut time format changed from 'YYYY-MM-DD HH24:MI:SS.FF' (US) to 'DD-MM-YYYY HH24:MI:SS.FF' (french)
//
// Revision 1.6  2005/03/09 09:27:18  chinkumo
// Bug fixes in the toDbTimeString(...) method
//
// Revision 1.5  2005/03/07 16:33:00  chinkumo
// A new method where created to extract the last n spectrum data
//
// Revision 1.4  2005/03/07 06:31:33  chinkumo
// Every retreiving data methods nows returns DevVarDoubleStringArray object.
// When rw-scalar data, timestamps are encapsulated into the svalue string array while read values (first) and write values are encapsulated into the dvalue string array.
// Changes made into the other class/methods impacted.
//
// Revision 1.3  2005/02/04 17:21:29  chinkumo
// For each statement initialised, an statement.close() was applied.
//
// Revision 1.2  2005/01/26 15:35:37  chinkumo
// Ultimate synchronization before real sharing.
//
// Revision 1.1  2004/12/06 17:39:56  chinkumo
// First commit (new API architecture).
//
// copyleft :   Synchrotron SOLEIL
//			    L'Orme des Merisiers
//			    Saint-Aubin - BP 48
//			    91192 GIF-sur-YVETTE CEDEX
//              FRANCE
//
//+============================================================================

package fr.soleil.TangoArchiving.ArchivingApi;

/*
* Import classes
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.ErrSeverity;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeSeuil;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.TdbSpec;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.AttributeHeavy;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.AttributeLight;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.AttributeLightMode;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.DateUtil;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.DbData;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ImageEvent_RO;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.SamplingType;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ScalarEvent;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ScalarEvent_RO;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ScalarEvent_RW;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.SpectrumEvent_RO;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.SpectrumEvent_RW;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.StringFormater;


/**
 * <B>File</B>           :   DataBaseApi.java<br/>
 * <B>Project</B>        :   HDB API Access<br/>
 * <b>Description : </b> This class hides the loading of drivers, the connection/disconnection with the database, and more generaly, this class hides number of methods to insert, update and query <I>HDB</I>.<br/>
 *
 * @author	Jean CHINKUMO - Synchrotron SOLEIL
 * @version	$Revision$
 */
public class DataBaseApi
{
    /*
	* 	Members
	*/
	/**
	 * Connection dbatabase type (<I>MySQL</I>, <I>Oracle</I>, ...)
	 */
	private int db_type;
	/*Port number for the connection
	private static int port;*/
	/**
	 * JDBC driver used for the connection
	 */
	private String driver;
	/**
	 * Database Host adress
	 */
	private String host;
	/**
	 * User's name for the connection
	 */
	private String user;
	/**
	 * User's password for the connection
	 */
	private String passwd;
	/**
	 * database name
	 */
	private String name;
	/**
	 * database'schema' used
	 */
	private String schema;
	/**
	 * Connection object used
	 */
	private Connection dbconn;
	// True if connection and disconnection are made once
	// False if the connection are or made for every request,
	private boolean autoConnect;
	private String temporarilyPath;
    //private int cpt;
    private Hashtable idsBuffer;

    protected Statement lastStatement = null;
    protected boolean canceled = false;
    
    private boolean isHistoric;
    /*
	* 	Contructors
	*/

	/**
	 * Default constructor. Never used
	 */
	public DataBaseApi()
	{
        idsBuffer = new Hashtable ();
    }

	/**
	 * Constructor using database name.
	 * Not used directly
	 * @param host_name Identifier (name or IP adress) of the machine which supplies the service "data base <I>HDB</I>"
	 * @param db_name   database name
	 * @param db_schema Name of the database's schema used
	 */
	public DataBaseApi(String host_name , String db_name , String db_schema)
	{
        idsBuffer = new Hashtable ();
        
        setHost(host_name);
		setDbName(db_name);
		if ( !db_schema.equals("") )
		{
			setDbShema(db_schema);
		}
		else
		{
			setDbShema(db_name);
		}
	}

	/**
	 * Constructor using host name, user name, password and database name.
	 *
	 * @param host_name Identifier (name or IP adress) of the machine which supplies the service "data base <I>HDB</I>"
	 * @param db_name   Name of the data base used
	 * @param db_schema Name of the database's schema used
	 * @param user_name Name to use to connect
	 * @param password  Password to use to connect
	 */
	public DataBaseApi(String host_name , String db_name , String db_schema , String user_name , String password)
	{
		this(host_name , db_name , db_schema);
        
        
		setUser(user_name);
		setPassword(password);
	}

	/*|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	 *
	 *
	 *                                       PART 1 :      Generals methods used for the connection
	 *
	 *
	 |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||*/

	// Getter methods
	/**
	 * <b>Description : </b> Returns the connected database host identifier.
	 *
	 * @return The host where the connection is done
	 */
	public String getHost()
	{
		return this.host;
	}

	/**
	 * <b>Description : </b>  Gets the database name
	 *
	 * @return The database name
	 */
	public String getDbName()
	{
		return this.name;
	}

	/**
	 * <b>Description : </b>  Gets the database's schema name
	 *
	 * @return The database name
	 */
	public String getDbSchema()
	{
		return this.schema;
	}

	/**
	 * <b>Description : </b>  Gets the driver's name for the connection
	 *
	 * @return The driver used for the connection
	 */
	public String getDriver()
	{
		return this.driver;
	}

	/**
	 * <b>Description : </b>  Gets the current user's name for the connection
	 *
	 * @return The user's name for the connection
	 */
	public String getUser()
	{
		return this.user;
	}

	/**
	 * <b>Description : </b>  Gets the current user's password for the connection
	 *
	 * @return The user's password for the connection
	 */
	public String getPassword()
	{
		return this.passwd;
	}

	/**
	 * <b>Description : </b>  Gets the type of database being used (<I>MySQL</I>, <I>Oracle</I>, ...)
	 *
	 * @return The type of database being used
	 */
	public int getDb_type()
	{
		return this.db_type;
	}

	public String getTemporarilyPath()
	{
		return this.temporarilyPath;
	}

	public void setTemporarilyPath(String temporarilyPath)
	{
		this.temporarilyPath = temporarilyPath;
	}

	/**
	 * <b>Description</b>    	: Returns misc informations about the database and a set of parameters characterizing the connection. <br>
	 */
	public String getInfo() throws ArchivingException
	{
		String msg = null;
		// Then get info from the database
		try
		{
			DatabaseMetaData db_info = dbconn.getMetaData();

			msg = "\tUser : " + db_info.getUserName() + "\n\tdatabase name : " + name;
			msg = msg + "\n\tdatabase product : " + db_info.getDatabaseProductVersion();
			msg = msg + "\n\tURL : " + db_info.getURL();
			msg = msg + "\n\tDriver name : " + db_info.getDriverName();
			msg = msg + " Version " + db_info.getDriverVersion();
			msg = msg + "\n\tdatabase modes : ";
			if ( dbconn.getAutoCommit() )
				msg = msg + "AUTO COMMIT ; ";
			else
				msg = msg + "MANUAL COMMIT ; ";

			if ( dbconn.isReadOnly() )
				msg = msg + "READ ONLY ; ";
			else
				msg = msg + "READ-WRITE ; ";

			if ( db_info.usesLocalFiles() )
				msg = msg + "USES LOCAL FILES";
			else
				msg = msg + "DONT USE LOCAL FILES";

		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;
			//String message = "";
			String reason = GlobalConst.STATEMENT_FAILURE;
			String desc = "Failed while executing DataBaseApi.getInfo() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return msg;

	}

	/**
	 * <b>Description : </b>  Gets the current used connection's object
	 *
	 * @return The connection object return when connecting
	 */
	public Connection getConnectionObject()
	{
		if ( dbconn == null )
		{
			String msg = "Error detected :\n";
			msg = msg + "---> Class : DataBaseApi\n" + "---> Method : getConnectionObject\n";
			msg = msg + "---> Description : Impossible to connect with the database.\n";
			msg = msg + "                   The connection object returned is NULL ... exiting.";
			System.out.println(msg);
			System.exit(-1);
		}
		return dbconn;
	}

	/**
	 * <b>Description : </b>  Gets the current connection's type <br>
	 *
	 * @return True if connection and disconnection are made for every request, False if the connection and disconnection are made once
	 */
	public boolean getAutoConnect()
	{
		return this.autoConnect;
	}

    /**
     * <b>Description : </b>  Gets the current database's autocommit <br>
     *
     * @return True if database is in autocommit mode
     */
    public boolean getAutoCommit()
    {
        switch ( db_type )
        {
            case ConfigConst.BD_MYSQL:
                return ConfigConst.AUTO_COMMIT_MYSQL;
            case ConfigConst.BD_ORACLE:
                return ConfigConst.AUTO_COMMIT_ORACLE;
            default:
                return ConfigConst.AUTO_COMMIT_DEFAULT;
        }
    }

	// Setter methods
	/**
	 * <b>Description : </b>  Sets the host to connect
	 *
	 * @param host_name The host to connect
	 */
	public void setHost(String host_name)
	{
		this.host = host_name;
	}

	/**
	 * <b>Description : </b>  Sets the database name  <br>
	 *
	 * @param db_name The database name
	 */
	public void setDbName(String db_name)
	{
		this.name = db_name;
	}

	/**
	 * <b>Description : </b>  Sets the database name  <br>
	 *
	 * @param db_shema The database shema used
	 */
	public void setDbShema(String db_shema)
	{
		this.schema = db_shema;
	}

	/**
	 * <b>Description : </b>  Sets the database type (<I>MySQL</I>, <I>Oracle</I>, ...) <br>
	 *
	 * @param db_type The database name
	 */
	public void setDb_type(int db_type)
	{
		this.db_type = db_type;
	}

	/**
	 * <b>Description : </b>  Specifies a driver for the connection
	 *
	 * @param driver_name The driver's name to use for the connection
	 */
	public void setDriver(String driver_name)
	{
		this.driver = driver_name;
	}

	/**
	 * <b>Description : </b>  Sets the current user's name for the connection
	 *
	 * @param user_name The user's name for the connection
	 */
	public void setUser(String user_name)
	{
		this.user = user_name;
	}

	/**
	 * <b>Description : </b>  Sets a password for the connection
	 *
	 * @param password The password for the connection
	 */
	public void setPassword(String password)
	{
		this.passwd = password;
	}

	/**
	 * <b>Description : </b>  Sets the current connection's mode
	 *
	 * @param value <li> <I>True</I> : implies that connection and disconnection are made for every request,
	 *              <li> <I>False</I> : implies that connection and disconnection are made once
	 */
	public void setAutoConnect(boolean value)
	{
		this.autoConnect = value;
	}

	/**
	 * <b>Description</b>    	: Sets the auto commit mode to "true" or "false"
	 *
	 * @param value The mode value
	 * @throws ArchivingException
	 */
	public void setAutoCommit(boolean value) throws ArchivingException
	{
		// Set commit mode to manual
		try
		{
			dbconn.setAutoCommit(value);
		}
		catch ( SQLException e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : ";
			String reason = GlobalConst.STATEMENT_FAILURE;
			String desc = "Failed while executing DataBaseApi.setAutoCommit() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);


		}
	}

	/**
	 * <b>Description</b>    	: Load an instance of the JDBC driver according to
	 * the type of database used (db_type)
	 */
	private void loadDriver()
	{
		System.out.println("DataBaseApi.loadDriver");
		try
		{
			switch ( db_type )
			{
				case ConfigConst.BD_MYSQL:
					Class.forName("org.gjt.mm.mysql.Driver"); // D�finition du pilote MySQL
					break;
				case ConfigConst.BD_ORACLE:
					Class.forName("oracle.jdbc.driver.OracleDriver");  //pilote Oracle
					break;
				case ConfigConst.BD_POSTGRESQL:  //pilote PostgreSQL
					Class.forName("org.postgresql.Driver");
					break;
			}
		}
		catch ( ClassNotFoundException e )
		{
			System.err.println("ERROR !! " + "\r\n" +
			                   "\t Origin : \t " + "DataBaseApi.loadDriver" + "\r\n" +
			                   "\t Reason : \t " + getDbSchema().toUpperCase().trim() + "_FAILURE" + "\r\n" +
			                   "\t Description : \t " + e.getMessage() + "\r\n" +
			                   "\t Additional information : \t " + "Driver not found !" + "\r\n");
		}
	}


	/**
	 * <b>Description : </b>    	Build the url string and get a connection object
	 *
	 * @throws ArchivingException
	 */
	private void buildUrlAndConnect() throws ArchivingException
	{
		// Gets connected to the database
		String url = "";
        boolean autoCommit = ConfigConst.AUTO_COMMIT_DEFAULT;
		switch ( db_type )
		{
			case ConfigConst.BD_MYSQL:
				url = ConfigConst.DRIVER_MYSQL + "://" + host + "/" + name; // D�finition du pilote MySQL
                System.out.println("DataBaseApi/buildUrlAndConnect/BD_MYSQL/url|"+url);
                autoCommit = ConfigConst.AUTO_COMMIT_MYSQL;
				break;
			case ConfigConst.BD_ORACLE:
				url = ConfigConst.DRIVER_ORACLE + ":@" + host + ":" + ConfigConst.ORACLE_PORT + ":" + name; //pilote Oracle
                System.out.println("DataBaseApi/buildUrlAndConnect/BD_ORACLE/url|"+url);
                autoCommit = ConfigConst.AUTO_COMMIT_ORACLE;
				break;
		}
		//System.out.println("\turl = " + url);
		try
		{
			dbconn = DriverManager.getConnection(url , user , passwd);
			dbconn.setAutoCommit(autoCommit);
		}
		catch ( SQLException e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + "Error initializing the connection...";
			String reason = GlobalConst.STATEMENT_FAILURE;
			String desc = "Failed while executing DataBaseApi.buildUrlAndConnect() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , e.getClass().getName() , e);
		}
		catch ( Exception e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + "Error initializing the connection...";
			String reason = GlobalConst.STATEMENT_FAILURE;
			String desc = "Failed while executing DataBaseApi.buildUrlAndConnect() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , e.getClass().getName() , e);
		}

	}

	/**
	 * <b>Description : </b>    	Loads the JDBC driver, connects with the database
	 * and initializes. Remark: the type of the base (MySQL/Oracle) must beforehand have been specified...
	 *
	 * @throws ArchivingException
	 */
	private void connect() throws ArchivingException
	{
		// Load the driver
		loadDriver();
		// Gets connected to the database
		buildUrlAndConnect();
		// Set connection's default settings
		//setAutoCommit(ConfigConst.AUTO_COMMIT_DEFAULT); // removed because already managed in buildUrlAndConnect()
		System.out.println("HDBInfo : \n" + getInfo());
	}

	/**
	 * <b>Description : </b> Allows to connect to the database <I>HDB</I>, independently of its type (<I>mySQL</I>/<I>Oracle</I>)
	 */
	public void connect_bis() throws ArchivingException
	{
        System.out.println("DataBaseApi/connect_bis|");
        
        String url = ( db_type == ConfigConst.BD_MYSQL ) ?
		             ConfigConst.DRIVER_MYSQL + "://" + host + "/" + name :
		             ConfigConst.DRIVER_ORACLE + ":@" + host + ":" + ConfigConst.ORACLE_PORT + ":" + name;
		try
		{
			dbconn = DriverManager.getConnection(url , user , passwd);
		}
		catch ( SQLException e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			String reason = "Failed while executing DataBaseApi.connect_bis() method...";
			String desc = "The loggin parameters seem to be wrong... : " + "\r\n" +
			              "\t host      = " + host + "\r\n" +
			              "\t db name   = " + name + "\r\n" +
			              "\t db schema = " + schema + "\r\n" +
			              "\t user      = " + user + "\r\n" +
			              "\t password  = " + passwd;
			throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "" , e);
		}
	}


	/**
	 * <b>Description : </b> Allows to connect to the database <I>HDB</I>, independently of its type (<I>mySQL</I>/<I>Oracle</I>)
	 */
	public void connect_auto() throws ArchivingException
	{
		ArchivingException archivingException1 = null , archivingException2 = null;
		try
		{
			//  try oracle
			connect_oracle();
			System.out.println("DataBaseApi.connect_auto : " + getUser() + "@" + getHost());
			return;
		}
		catch ( ArchivingException e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + "CONNECTION FAILED !";
			String reason = "Failed while executing DataBaseApi.connect_auto() method...";
			String desc = "Failed while connecting to the Oracle archiving database";
			archivingException1 = new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "" , e);
		}
		try
		{
			// try MySQL;
			connect_mysql();
			System.out.println("DataBaseApi.connect_auto : " + getUser() + "@" + getHost());
			return;
		}
		catch ( ArchivingException e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + "CONNECTION FAILED !";
			String reason = "Failed while executing DataBaseApi.connect_auto() method...";
			String desc = "Failed while connecting to the MySQL archiving database";
			archivingException2 = new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "" , e);

			String _desc = "Failed while connecting to the archiving database";

			ArchivingException archivingException = new ArchivingException(message , reason , ErrSeverity.PANIC , _desc , "" , archivingException1);
			archivingException.addStack(message , reason , ErrSeverity.PANIC , _desc , "" , archivingException2);
			throw archivingException;
		}
	}

	/**
	 * <b>Description : </b> Allows to connect to the <I>HDB</I> database when of <I>mySQL</I> type.
	 *
	 * @throws ArchivingException
	 */
	private void connect_mysql() throws ArchivingException
	{
		// Load the driver
		try
		{
			Class.forName("org.gjt.mm.mysql.Driver"); // D�finition du pilote MySQL
			String url = ConfigConst.DRIVER_MYSQL + "://" + host + "/" + name; // D�finition du pilote MySQL
			//url += "?jdbcCompliantTruncation=false";
			System.out.println("DataBaseApi/connect_mysql/url|"+url);
			dbconn = DriverManager.getConnection(url , user , passwd);
			setAutoCommit(ConfigConst.AUTO_COMMIT_MYSQL);
			setDb_type(ConfigConst.BD_MYSQL);
			setDriver(ConfigConst.DRIVER_MYSQL);
		}
		catch ( ClassNotFoundException e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.DRIVER_MISSING;
			String reason = "Failed while executing DataBaseApi.connect_mysql() method...";
			String desc = "No MySQL driver available..., please check !";
			throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "" , e);
		}
		catch ( SQLException e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			String reason = "Failed while executing DataBaseApi.connect_mysql() method...";
			String desc = ( e.getMessage().indexOf(GlobalConst.NO_HOST_EXCEPTION) != -1 ) ?
			              "The 'host' property (" + host + ") might be wrong... please check it..." :
			              "The loggin parameters (host, database name,  user, password) seem to be wrong...";
			throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "" , e);
		}
		catch ( Exception e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			String reason = "Failed while executing DataBaseApi.connect_mysql() method...";
			String desc = ( e.getMessage().indexOf(GlobalConst.NO_HOST_EXCEPTION) != -1 ) ?
			              "The 'host' property (" + host + ") might be wrong... please check it..." :
			              "The loggin parameters (host, database name,  user, password) seem to be wrong...";
			throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "" , e);
		}
	}


	/**
	 * <b>Description : </b> Allows to connect to the <I>HDB</I> database when of <I>Oracle</I> type.
	 *
	 * @throws ArchivingException
	 */
	private void connect_oracle() throws ArchivingException
	{
		// Load the driver
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");  //pilote Oracle
			String url = ConfigConst.DRIVER_ORACLE + ":@" + host + ":" + ConfigConst.ORACLE_PORT + ":" + name; //pilote Oracle
			dbconn = DriverManager.getConnection(url , user , passwd);
			setAutoCommit(ConfigConst.AUTO_COMMIT_ORACLE);
			setDb_type(ConfigConst.BD_ORACLE);
			setDriver(ConfigConst.DRIVER_ORACLE);
			alterSession();
		}
		catch ( ClassNotFoundException e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.DRIVER_MISSING;
			String reason = "Failed while executing DataBaseApi.connect_oracle() method...";
			String desc = "No Oracle driver available..., please check !";
			throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "" , e);
		}
		catch ( SQLException e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			String reason = "Failed while executing DataBaseApi.connect_oracle() method...";
			String desc = "The loggin parameters seem to be wrong... : " + "\r\n" +
			              "\t host      = " + host + "\r\n" +
			              "\t db name   = " + name + "\r\n" +
			              "\t db schema = " + schema + "\r\n" +
			              "\t user      = " + user + "\r\n" +
			              "\t password  = " + passwd;
			throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "" , e);
		}
	}

	/**
	 * <b>Description : </b>    	Closes the connection with the database
	 *
	 * @throws ArchivingException
	 */
	public void close() throws ArchivingException
	{
		if ( dbconn != null )
        {
			try
			{
                if ( ! dbconn.isClosed () )
                {
                    dbconn.close();    
                }
			}
			catch ( SQLException e )
			{
				String message;
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				//String message = ConfigConst.ARCHIVING_ERROR_PREFIX + " : ";
				String reason = GlobalConst.STATEMENT_FAILURE;
				String desc = "Failed while executing DataBaseApi.close() method...";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
        }
	}

	/**
	 * <b>Description : </b>    	Commits actions in the database <br>
	 *
	 * @throws ArchivingException
	 */
	public void commit() throws ArchivingException
	{
        try
		{
			dbconn.commit();
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.STATEMENT_FAILURE;
			String desc = "Failed while executing DataBaseApi.commit() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
	}

	/**
	 * <b>Description : </b>    	Cancels actions in the database <br>
	 *
	 * @throws ArchivingException
	 */
	public void rollback() throws ArchivingException
	{
		try
		{
			dbconn.rollback();
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.STATEMENT_FAILURE;
			String desc = "Failed while executing DataBaseApi.rollback() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
	}

	/**
	 * This method is used when connecting an Oracle database. It tunes the connection to the database.
	 *
	 * @throws ArchivingException
	 */
	private void alterSession() throws ArchivingException
	{
		Statement stmt;
		String sqlStr1 , sqlStr2 , sqlStr3;
		sqlStr1 = "alter session set NLS_NUMERIC_CHARACTERS = \". \"";
		sqlStr2 = "alter session set NLS_TIMESTAMP_FORMAT = 'DD-MM-YYYY HH24:MI:SS.FF'";
		sqlStr3 = "alter session set NLS_DATE_FORMAT = 'DD-MM-YYYY HH24:MI:SS'";
		try
		{
            if (canceled) return;
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return;
			stmt.executeQuery(sqlStr1);
            if (canceled) return;
			stmt.executeQuery(sqlStr2);
            if (canceled) return;
			stmt.executeQuery(sqlStr3);
            if (canceled) return;
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.STATEMENT_FAILURE;
			String desc = "Failed while executing DataBaseApi.alterSession() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
	}


	/*|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	 *
	 *
	 *                                       PART 2 :      Generals methods used to query the database (SELECT)
	 *                                                                            (Extraction)
	 *
	 |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||*/


	/*=================================================================================================
                                          Blocage / Autorisation d'acc�s
    =================================================================================================*/

    public void cancel() throws ArchivingException
    {
        if (lastStatement != null && !canceled)
        {
            canceled = true;
            try
            {
                lastStatement.cancel();
            }
            catch (SQLException e)
            {
                System.err.println("Failed to cancel last statement");
                e.printStackTrace();
            }
            finally
            {
                if ( getAutoConnect() )
                    close();
                lastStatement = null;
            }
        }
    }

    public void allow()
    {
        canceled = false;
    }

    public boolean isCanceled()
    {
        return canceled;
    }

    /*=================================================================================================
	                                      Extraction li�es � l'ADT
	=================================================================================================*/

	/**
	 * <b>Description : </b> Gets all the registered domains.
	 *
	 * @return all the registered domains (array of strings)
	 * @throws ArchivingException
	 */
	public String[] get_domains() throws ArchivingException
	{
        if (canceled) return null;
		Vector argout_vect = new Vector();
		String[] argout;
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 4 ] + " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		//System.out.println("sqlStr = " + sqlStr);
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;

			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				argout_vect.addElement(rset.getString(ConfigConst.TAB_DEF[ 4 ]));
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.get_domains() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the families list
		argout = toStringArray(argout_vect);
		return argout;
	}

	public String[] get_domains_by_criterion(String dom_regexp) throws ArchivingException
	{
        if (canceled) return null;
		Vector argout_vect = new Vector();
		String[] argout;
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 4 ] + " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ]
		         + " WHERE " + ConfigConst.TAB_DEF[ 4 ] + " LIKE " + "'" + dom_regexp.replace('*' , '%') + "'"
                 //CLA 25/07/06
                 +" ORDER BY " + ConfigConst.TAB_DEF[ 4 ]
                 ;
		//System.out.println("sqlStr = " + sqlStr);
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;

			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				argout_vect.addElement(rset.getString(ConfigConst.TAB_DEF[ 4 ]));
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.get_domains() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the families list
		argout = toStringArray(argout_vect);
		return argout;
	}
    
    public String[] get_attributes_complete_names() throws ArchivingException
    {
        if (dbconn==null) return null;
        if (canceled) return null;
        Vector argout_vect = new Vector();
        String[] argout;
        Statement stmt;
        ResultSet rset;
        // First connect with the database
        if ( getAutoConnect() )
            connect();
        // Create and execute the SQL query string
        String sqlStr;
        sqlStr = "SELECT  " + ConfigConst.TAB_DEF[ 2 ] + " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];
                 //+" ORDER BY " + ConfigConst.TAB_DEF[ 4 ];
        //System.out.println("sqlStr = " + sqlStr);
        try
        {
            stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;

            rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;
            // Gets the result of the query
            while ( rset.next() )
            {
                argout_vect.addElement(rset.getString(1));
            }
            close ( stmt );
        }
        catch ( SQLException e )
        {
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.get_domains() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        // Close the connection with the database
        if ( getAutoConnect() )
            close();
        // Returns the families list
        argout = toStringArray(argout_vect);
        return argout;
    }


	/**
	 * <b>Description : </b> Returns the number of distinct registered domains.
	 *
	 * @return the number of distinct registered domains.
	 * @throws ArchivingException
	 */
	public int get_domainsCount() throws ArchivingException
	{
        if (canceled) return 0;
		int counter = 0;
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT COUNT(DISTINCT " + ConfigConst.TAB_DEF[ 4 ] + ") FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		//System.out.println("sqlStr = " + sqlStr);
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;

			rset = stmt.executeQuery(sqlStr);
            if (canceled) return 0;
			// Gets the result of the query
			while ( rset.next() )
				counter = rset.getInt(1);
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.get_domainsCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the corresponding number
		return counter;
	}

	/**
	 * <b>Description : </b> Gets all the registered families.
	 *
	 * @return All the registered families (array of strings).
	 * @throws ArchivingException
	 */
	public String[] get_families() throws ArchivingException
	{
        if (canceled) return null;
		Vector argout_vect = new Vector();
		String[] argout;
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 5 ] + " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		sqlStr = sqlStr + " WHERE " + ConfigConst.TAB_DEF[ 5 ] + " IS NOT NULL";
		//System.out.println("sqlStr = " + sqlStr);
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;

			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				argout_vect.addElement(rset.getString(ConfigConst.TAB_DEF[ 5 ]));
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.get_families() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the families list
		argout = toStringArray(argout_vect);
		return argout;
	}

	/**
	 * <b>Description : </b> Returns the number of distinct registered families.
	 *
	 * @return the number of distinct registered families.
	 * @throws ArchivingException
	 */
	public int get_familiesCount() throws ArchivingException
	{
        if (canceled) return 0;
		int counter = 0;
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT COUNT(DISTINCT " + ConfigConst.TAB_DEF[ 5 ] + ") FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		sqlStr = sqlStr + " WHERE " + ConfigConst.TAB_DEF[ 5 ] + " IS NOT NULL";
		//System.out.println("sqlStr = " + sqlStr);
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;

			rset = stmt.executeQuery(sqlStr);
            if (canceled) return 0;
			// Gets the result of the query
			while ( rset.next() )
				counter = rset.getInt(1);
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.get_familiesCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the corresponding number
		return counter;
	}

	/**
	 * <b>Description : </b> Gets all the registered families for the given domain
	 *
	 * @param domain_name the given domain
	 * @return array of strings
	 * @throws ArchivingException
	 */
	public String[] get_families(String domain_name) throws ArchivingException
	{
        if (canceled) return null;
		Vector argout_vect = new Vector();
		String[] argout;
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 5 ] + " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		sqlStr = sqlStr + " WHERE ";
		sqlStr = sqlStr + ConfigConst.TAB_DEF[ 4 ] + " LIKE " + "'" + domain_name.replace('*' , '%') + "'";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				argout_vect.addElement(rset.getString(ConfigConst.TAB_DEF[ 5 ]));
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.get_families() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the families list
		argout = toStringArray(argout_vect);
		return argout;
	}

	public String[] get_families_by_criterion(String domain_name , String family_regexp) throws ArchivingException
	{
        if (canceled) return null;
		Vector argout_vect = new Vector();
		String[] argout;
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 5 ] + " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		sqlStr = sqlStr + " WHERE ";
		sqlStr = sqlStr + ConfigConst.TAB_DEF[ 4 ] + " LIKE " + "'" + domain_name.replace('*' , '%') + "'";
		sqlStr = sqlStr + " AND ";
		sqlStr = sqlStr + ConfigConst.TAB_DEF[ 5 ] + " LIKE " + "'" + family_regexp.replace('*' , '%') + "'";
		//  CLA 25/07/06
        sqlStr = sqlStr + " ORDER BY " + ConfigConst.TAB_DEF[ 5 ];
        
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				argout_vect.addElement(rset.getString(ConfigConst.TAB_DEF[ 5 ]));
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.get_families() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the families list
		argout = toStringArray(argout_vect);
		return argout;
	}

	/**
	 * <b>Description : </b> Returns the number of distinct registered families for a given domain.
	 *
	 * @param domain_name the given domain
	 * @return the number of distinct registered families for a given domain.
	 * @throws ArchivingException
	 */
	public int get_familiesCount(String domain_name) throws ArchivingException
	{
        if (canceled) return 0;
		int counter = 0;
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT COUNT(DISTINCT " + ConfigConst.TAB_DEF[ 5 ] + ") FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		sqlStr = sqlStr + " WHERE ";
		if ( domain_name.trim().equals("*") )
		{
			sqlStr = sqlStr + "(" + ConfigConst.TAB_DEF[ 4 ] + " LIKE '%' OR " + ConfigConst.TAB_DEF[ 4 ] + " IS NULL)";
		}
		else
		{
			sqlStr = sqlStr + ConfigConst.TAB_DEF[ 4 ] + " LIKE " + "'" + domain_name.replace('*' , '%') + "'";
		}

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return 0;
			// Gets the result of the query
			while ( rset.next() )
				counter = rset.getInt(1);
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.get_familiesCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the corresponding number
		return counter;
	}

	/**
	 * <b>Description : </b> Gets all the registered members
	 *
	 * @return array of strings
	 * @throws ArchivingException
	 */
	public String[] get_members() throws ArchivingException
	{
        if (canceled) return null;
		Vector argout_vect = new Vector();
		String[] argout;
		Statement stmt;
		ResultSet rset;

		// First connect with the database
		if ( getAutoConnect() )
			connect();

		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 6 ] + " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		sqlStr = sqlStr + " WHERE " + ConfigConst.TAB_DEF[ 6 ] + " IS NOT NULL";
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				argout_vect.addElement(rset.getString(ConfigConst.TAB_DEF[ 6 ]));
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.get_members() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();

		// Returns the families list
		argout = toStringArray(argout_vect);
		return argout;
	}

	/**
	 * <b>Description : </b> Returns the number of distinct members.
	 *
	 * @return the number of distinct members.
	 * @throws ArchivingException
	 */
	public int get_membersCount() throws ArchivingException
	{
        if (canceled) return 0;
		int counter = 0;
		Statement stmt;
		ResultSet rset;

		// First connect with the database
		if ( getAutoConnect() )
			connect();

		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT COUNT(DISTINCT " + ConfigConst.TAB_DEF[ 6 ] + ") FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		sqlStr = sqlStr + " WHERE " + ConfigConst.TAB_DEF[ 6 ] + " IS NOT NULL";
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return 0;
			// Gets the result of the query
			while ( rset.next() )
				counter = rset.getInt(1);
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.get_membersCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();

		// Returns the corresponding number
		return counter;
	}

	/**
	 * <b>Description : </b> Gets all the registered members for the given domain and family
	 *
	 * @param domain_name the given domain
	 * @param family_name the given family
	 * @return array of strings
	 * @throws ArchivingException
	 */
	public String[] get_members(String domain_name , String family_name) throws ArchivingException
	{
        if (canceled) return null;
		Vector argout_vect = new Vector();
		String[] argout;
		Statement stmt;
		ResultSet rset;

		// First connect with the database
		if ( getAutoConnect() )
			connect();

		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 6 ] + " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		sqlStr = sqlStr + " WHERE (";
		sqlStr = sqlStr + ConfigConst.TAB_DEF[ 4 ] + " LIKE " + "'" + domain_name.replace('*' , '%') + "'";
		sqlStr = sqlStr + " AND ";
		sqlStr = sqlStr + ConfigConst.TAB_DEF[ 5 ] + " LIKE " + "'" + family_name.replace('*' , '%') + "'";
		sqlStr = sqlStr + " AND ";
		sqlStr = sqlStr + ConfigConst.TAB_DEF[ 6 ] + " IS NOT NULL" + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;

			// Gets the result of the query
			while ( rset.next() )
				argout_vect.addElement(rset.getString(ConfigConst.TAB_DEF[ 6 ]));

			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.get_members() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();

		// Returns the families list
		argout = toStringArray(argout_vect);
		return argout;
	}

	public String[] get_members_by_criterion(String domain_name , String family_name , String member_regexp) throws ArchivingException
	{
        if (canceled) return null;
		Vector argout_vect = new Vector();
		String[] argout;
		Statement stmt;
		ResultSet rset;

		// First connect with the database
		if ( getAutoConnect() )
			connect();

		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 6 ] + " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		sqlStr = sqlStr + " WHERE (";
		sqlStr = sqlStr + ConfigConst.TAB_DEF[ 4 ] + " LIKE " + "'" + domain_name.replace('*' , '%') + "'";
		sqlStr = sqlStr + " AND ";
		sqlStr = sqlStr + ConfigConst.TAB_DEF[ 5 ] + " LIKE " + "'" + family_name.replace('*' , '%') + "'";
		sqlStr = sqlStr + " AND ";
		sqlStr = sqlStr + ConfigConst.TAB_DEF[ 6 ] + " LIKE " + "'" + member_regexp.replace('*' , '%') + "'";
		sqlStr = sqlStr + " ) ";
        //CLA 25/07/06
        sqlStr = sqlStr + " ORDER BY " + ConfigConst.TAB_DEF[ 6 ]; 

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;

			// Gets the result of the query
			while ( rset.next() )
				argout_vect.addElement(rset.getString(ConfigConst.TAB_DEF[ 6 ]));

			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.get_members() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();

		// Returns the families list
		argout = toStringArray(argout_vect);
		return argout;
	}

	/**
	 * <b>Description : </b> Returns the number of distinct registered members for the given domain and family.
	 *
	 * @param domain_name the given domain
	 * @param family_name the given family
	 * @return the number of distinct registered members for the given domain and family.
	 * @throws ArchivingException
	 */
	public int get_membersCount(String domain_name , String family_name) throws ArchivingException
	{
        if (canceled) return 0;
		int counter = 0;
		Statement stmt;
		ResultSet rset;

		// First connect with the database
		if ( getAutoConnect() )
			connect();

		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT COUNT(DISTINCT " + ConfigConst.TAB_DEF[ 6 ] + ") FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		sqlStr = sqlStr + " WHERE (";
		sqlStr = sqlStr + ConfigConst.TAB_DEF[ 4 ] + " LIKE " + "'" + domain_name.replace('*' , '%') + "'";
		sqlStr = sqlStr + " AND ";
		sqlStr = sqlStr + ConfigConst.TAB_DEF[ 5 ] + " LIKE " + "'" + family_name.replace('*' , '%') + "'";
		sqlStr = sqlStr + " AND ";
		sqlStr = sqlStr + ConfigConst.TAB_DEF[ 6 ] + " IS NOT NULL" + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return 0;
			// Gets the result of the query
			while ( rset.next() )
				counter = rset.getInt(1);
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.get_membersCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();

		// Returns the corresponding number
		return counter;
	}

	/**
	 * <b>Description : </b>    	Gets all the attributes's names for a given  domain, family, member<br>
	 *
	 * @param domain The given domain used to retrieve the names
	 * @param family The given family used to retrieve the names
	 * @param member The given member used to retrieve the names
	 * @return array of name strings
	 * @throws ArchivingException
	 */
	public String[] getAttributes(String domain , String family , String member) throws ArchivingException
	{
        if (canceled) return null;
		Vector nameList = new Vector();
		String[] argout;
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		/*String sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 2 ] + " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ] +
		                " WHERE " + ConfigConst.TAB_DEF[ 4 ] + " LIKE " + "'" + domain + "'" +
		                " AND " + ConfigConst.TAB_DEF[ 5 ] + " LIKE " + "'" + family + "'" +
		                " AND " + ConfigConst.TAB_DEF[ 6 ] + " LIKE " + "'" + member + "'";*/
        String sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 2 ] + " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ] +
        " WHERE " + ConfigConst.TAB_DEF[ 4 ] + " = " + "'" + domain + "'" +
        " AND " + ConfigConst.TAB_DEF[ 5 ] + " = " + "'" + family + "'" +
        " AND " + ConfigConst.TAB_DEF[ 6 ] + " = " + "'" + member + "'";
        //CLA 25/07/06

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				nameList.addElement(rset.getString(ConfigConst.TAB_DEF[ 2 ]));
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttributes() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the names list
		argout = toStringArray(nameList);
		return argout;
	}

	public String[] getAttributes_by_criterion(String domain , String family , String member , String att_regexp) throws ArchivingException
	{
        if (canceled) return null;
		Vector nameList = new Vector();
		String[] argout;
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		/*String sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 2 ] + " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ] +
		                " WHERE " + ConfigConst.TAB_DEF[ 4 ] + " LIKE " + "'" + domain + "'" +
		                " AND " + ConfigConst.TAB_DEF[ 5 ] + " LIKE " + "'" + family + "'" +
		                " AND " + ConfigConst.TAB_DEF[ 6 ] + " LIKE " + "'" + member + "'" +
		                " AND " + ConfigConst.TAB_DEF[ 7 ] + " LIKE " + "'" + att_regexp.replace('*' , '%') + "'";*/
        String sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 2 ] + " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ] +
        " WHERE " + ConfigConst.TAB_DEF[ 4 ] + " = " + "'" + domain + "'" +
        " AND " + ConfigConst.TAB_DEF[ 5 ] + " = " + "'" + family + "'" +
        " AND " + ConfigConst.TAB_DEF[ 6 ] + " = " + "'" + member + "'" 
        //+" AND " + ConfigConst.TAB_DEF[ 7 ] + " = " + "'" + att_regexp.replace('*' , '%') + "'"
        ;
        //CLA 25/07/06
        sqlStr += " ORDER BY " + ConfigConst.TAB_DEF[ 2 ];

        //System.out.println ( "DataBaseApi/getAttributes_by_criterion/sqlStr|"+sqlStr+"|" );
        
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				nameList.addElement(rset.getString(ConfigConst.TAB_DEF[ 2 ]));
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttributes() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the names list
		argout = toStringArray(nameList);
		return argout;
	}

	/**
	 * <b>Description : </b>    	Returns the number of registered the attributes for a given  domain, family, member<br>
	 *
	 * @param domain The given domain used to retrieve the names
	 * @param family The given family used to retrieve the names
	 * @param member The given member used to retrieve the names
	 * @return array of name strings
	 * @throws ArchivingException
	 */
	public int getAttributesCount(String domain , String family , String member) throws ArchivingException
	{
        if (canceled) return 0;
		int counter = 0;
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		String sqlStr = "SELECT COUNT(DISTINCT " + ConfigConst.TAB_DEF[ 2 ] + ") FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ] +
		                " WHERE " + ConfigConst.TAB_DEF[ 4 ] + "='" + domain + "'" +
		                " AND " + ConfigConst.TAB_DEF[ 5 ] + "='" + family + "'" +
		                " AND " + ConfigConst.TAB_DEF[ 6 ] + "='" + member + "'";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return 0;
// Gets the result of the query
			while ( rset.next() )
				counter = rset.getInt(1);
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttributesCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the corresponding number
		return counter;
	}

	/**
	 * <b>Description : </b>    	Gets for a specified attribute its ID as defined in HDB
	 *
	 * @param att_name The attribute's name
	 * @return The <I>HDB</I>'s ID that caracterize the given attribute
	 * @throws ArchivingException
	 */
	public int getAttID(String att_name) throws ArchivingException
	{
        if (canceled) return 0;
        if (dbconn == null) return 0;
		int attributesID = 0;
		ResultSet rset;
		PreparedStatement ps_get_att_id;
		// My statement
		String selectString = "SELECT " + ConfigConst.TAB_DEF[ 0 ] +
		                      " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ] +
		                      //" WHERE " + ConfigConst.TAB_DEF[ 2 ] + " like ?";
                              " WHERE " + ConfigConst.TAB_DEF[ 2 ] + " = ?";
		try
		{
			ps_get_att_id = dbconn.prepareStatement(selectString);
            lastStatement = ps_get_att_id;
            if (canceled) return 0;
			String field1 = att_name.trim();
            if (canceled) return 0;
			ps_get_att_id.setString(1 , field1);
			try
			{
				rset = ps_get_att_id.executeQuery();
				// Gets the result of the query
				if ( rset.next() )
					attributesID = rset.getInt(1);
				close(ps_get_att_id);
			}
			catch ( SQLException e )
			{
                close(ps_get_att_id);
				throw e;
			}

		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttID() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Returns the total number of signals defined in HDB
		return attributesID;
	}

	/**
	 * <b>Description : </b>    	Gets the total number of attributes defined in <I>HDB</I> with the given type
	 * (0: DevShort - 1: DevLong - 2: DevDouble - 3: DevString) <br>
	 *
	 * @param data_type
	 * @return The total number of attributes for the specified type
	 * @throws ArchivingException
	 */
	public int getAttributesCountT(int data_type) throws ArchivingException
	{
        if (canceled) return 0;
		int attributesCount = 0;
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		// (if not already done)
		if ( getAutoConnect() )
			connect();
		// Build the query string
		String getAttributeCountQuery;
		getAttributeCountQuery = "SELECT count(*) FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ] +
		                         " WHERE " + ConfigConst.TAB_DEF[ 8 ] + "=" + data_type;
		// Execute the SQL query string

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeCountQuery);
            if (canceled) return 0;
			// Gets the result of the query
			if ( rset.next() )
				attributesCount = rset.getInt(1);
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttributesCountT() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the total number of signals defined in HDB
		return attributesCount;
	}

	/**
	 * <b>Description : </b>    	Gets the total number of attributes defined in <I>HDB</I> with the given format
	 * (0 -> scalar | 1 -> spectrum | 2 -> image) <br>
	 *
	 * @param data_format
	 * @return The total number of attributes for the specified type
	 * @throws ArchivingException
	 */
	public int getAttributesCountF(int data_format) throws ArchivingException
	{
        if (canceled) return 0;
		int attributesCount = 0;
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		// (if not already done)
		if ( getAutoConnect() )
			connect();
		// Build the query string
		String getAttributeCountQuery;
		getAttributeCountQuery = "SELECT count(*) FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ] +
		                         " WHERE " + ConfigConst.TAB_DEF[ 9 ] + "=" + data_format;

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			// Execute the SQL query string
			rset = stmt.executeQuery(getAttributeCountQuery);
            if (canceled) return 0;
			// Gets the result of the query
			if ( rset.next() )
				attributesCount = rset.getInt(1);
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttributesCountF() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the total number of signals defined in HDB
		return attributesCount;
	}

	/**
	 * <b>Description : </b>    	Gets the list of attributes registered in <I>HDB</I>
	 *
	 * @return an array of String containing the attributes names <br>
	 * @throws ArchivingException
	 */
	public Vector getAttributes() throws ArchivingException
	{
        if (canceled) return null;
		Vector nameList = new Vector();
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 2 ] +
		         " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				nameList.addElement(rset.getString(ConfigConst.TAB_DEF[ 2 ]));
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttributes() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the names list
		return nameList;
	}

	/**
	 * <b>Description : </b>    	Gets the list of attributes registered in the historical (resp. temporary) database, that belong to the specified facility.
	 *
	 * @return an array of String containing the attributes names <br>
	 * @throws ArchivingException
	 */
	public Vector getAttributes(String facility) throws ArchivingException
	{
        if (canceled) return null;
		System.out.println("DataBaseApi.getAttributes : " + facility);
		Vector nameList = new Vector();
		PreparedStatement preparedStatement;
		ResultSet rset;
		// Create and execute the SQL query string

		String select_field = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ];
		String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		String clause_1 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 14 ] + " LIKE ?";
		String getAttributeDataQuery = "SELECT DISTINCT(" + select_field + ")" + " FROM " + table_1 + " WHERE " + "(" + clause_1 + ")";

		try
		{
			preparedStatement = dbconn.prepareStatement(getAttributeDataQuery);
            lastStatement = preparedStatement;
            if (canceled) return null;
			preparedStatement.setString(1 , facility.trim());
			rset = preparedStatement.executeQuery();
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				nameList.addElement(rset.getString(1));
			close(preparedStatement);
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttributes() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the names list
		return nameList;
	}


	/**
	 * <b>Description : </b>    	Returns the number of attributes defined in <I>HDB</I>
	 *
	 * @return The total number of attributes defined in <I>HDB</I>
	 * @throws ArchivingException
	 */
	public int getAttributesCount() throws ArchivingException
	{
        if (canceled) return 0;
		int attributesCount = 0;
		Statement stmt;
		ResultSet rset;

		// Build the query string
		String getAttributeCountQuery;
		getAttributeCountQuery = "SELECT count(*) FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			// Execute the SQL query string
			rset = stmt.executeQuery(getAttributeCountQuery);
            if (canceled) return 0;
			// Gets the result of the query
			if ( rset.next() )
				attributesCount = rset.getInt(1);
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttributesCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the total number of signals defined in HDB
		return attributesCount;
	}

	/**
	 * <b>Description : </b>    	Gets the list of <I>HDB</I> registered attributes for the given format (0 -> scalar | 1 -> spectrum | 2 -> image)
	 *
	 * @return An array  containing the attributes names <br>
	 * @throws ArchivingException
	 */
	public Vector getAttributesNamesF(int data_format) throws ArchivingException
	{
        if (canceled) return null;
		Vector nameList = new Vector();
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 2 ] +
		         " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ] +
		         " WHERE " + ConfigConst.TAB_DEF[ 9 ] + "=" + data_format;

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				nameList.addElement(rset.getString(ConfigConst.TAB_DEF[ 2 ]));
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttributesNamesF() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the names list
		return nameList;
	}

	/**
	 * <b>Description : </b>    	Gets the list of registered <I>HDB</I> attributes for the given type (2 -> Tango::DevShort | 3 -> Tango::DevLong | 5 -> Tango::DevDouble and 8 -> Tango::DevString)
	 *
	 * @return An array  containing the attributes names <br>
	 * @throws ArchivingException
	 */
	public Vector getAttributesNamesT(int data_type) throws ArchivingException
	{
        if (canceled) return null;
		Vector nameList = new Vector();
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		String sqlStr;
		sqlStr = "SELECT DISTINCT " + ConfigConst.TAB_DEF[ 2 ] +
		         " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ] +
		         " WHERE " + ConfigConst.TAB_DEF[ 8 ] + "=" + data_type;

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				nameList.addElement(rset.getString(ConfigConst.TAB_DEF[ 2 ]));
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttributesNamesT() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the names list
		return nameList;
	}

	/*=================================================================================================
	                                    Extractions li�es � l'APT
	 =================================================================================================*/
	/**
	 * This methos returns the display format for the attribute of the given name
	 *
	 * @param att_name : the attribut's name
	 * @return the display format for the attribute of the given name
	 * @throws ArchivingException
	 */
	public String getDisplayFormat(String att_name) throws ArchivingException
	{
        if (canceled) return null;
		String displayFormat = "";
		ResultSet rset;
		PreparedStatement ps_get_att_id;
		// My statement
		String selectString = "SELECT " + ConfigConst.TAB_PROP[ 7 ] +
		                      " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ] + ", " + getDbSchema() + "." + ConfigConst.TABS[ 1 ] +
		                      //" WHERE " + ConfigConst.TAB_DEF[ 2 ] + " like ?" +
                              " WHERE " + ConfigConst.TAB_DEF[ 2 ] + " = ?" +
		                      " AND " +
		        // apt.id = adt.id
		                      ConfigConst.TABS[ 1 ] + "." + ConfigConst.TAB_PROP[ 0 ] + "=" + ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 0 ];
		try
		{
			ps_get_att_id = dbconn.prepareStatement(selectString);
            lastStatement = ps_get_att_id;
            if (canceled) return null;
			String field1 = att_name.trim();
			ps_get_att_id.setString(1 , field1);
			try
			{
				rset = ps_get_att_id.executeQuery();
                if (canceled) return null;
				// Gets the result of the query
				if ( rset.next() )
					displayFormat = rset.getString(1);
				close(ps_get_att_id);
			}
			catch ( SQLException e )
			{
				close(ps_get_att_id);
				throw e;
			}

		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getDisplayFormat() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Returns the total number of signals defined in HDB
		return displayFormat;
	}

	/*=================================================================================================
	                                   Extractions li�es � l'AMT
	=================================================================================================*/
	/**
	 * <b>Description : </b>    	Gets the list of attributes that are being archived in <I>HDB</I>
	 *
	 * @return the number of attributes being archived in <I>HDB</I>
	 * @throws SQLException 
	 * @throws ArchivingException
	 */
    /*public String[] getCurrentArchivedAtt() throws ArchivingException
    {
        throw new ArchivingException ( "CLA/DataBaseApi/getCurrentArchivedAtt/DUMMY ArchivingException!!!!!!!!!" );
    }*/
    
    private static void close ( Statement stmt ) throws SQLException
    {
        if ( stmt != null )
        {
            stmt.close ();
        }
    }
    
    private static void close ( ResultSet rset ) throws SQLException
    {
        if ( rset != null )
        {
            rset.close ();
        }
    }
    
	public String[] getCurrentArchivedAtt(boolean archivingOnly) throws ArchivingException
	{
        Statement stmt = null;
        ResultSet rset = null;
        
        try
        {
    	    if (canceled) return null;
            Vector nameVect = new Vector();
    		String[] nameArr = new String[ 5 ];
    		
    		// First connect with the database
    		if ( getAutoConnect() )
            {
    			connect();
            }
    
    		// Create and execute the SQL query string
    		// Build the query string
    		String select_field = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ];
    		String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
    		String table_2 = getDbSchema() + "." + ConfigConst.TABS[ 2 ];
    		String tables = table_1 + ", " + table_2;
    		String clause_1 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 3 ] + " IS NULL";
    		String clause_2 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 0 ] + " = " + ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_MOD[ 0 ];
    
    		//String getAttributeDataQuery = "SELECT DISTINCT(" + select_field + ")" + " FROM " + tables + " WHERE " +
            String whereClause = archivingOnly ? "(" + clause_1 + " AND " + clause_2 + ")" : clause_2;
            String getAttributeDataQuery = "SELECT " + select_field + " FROM " + tables + " WHERE " + whereClause;
    		                               
            stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
            //System.out.println ( "CLA/DataBaseApi/getCurrentArchivedAtt/query|"+getAttributeDataQuery+"|" );
            rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return null;
			while ( rset.next() )
			{
                String next = rset.getString(1);
                if ( next != null )
                {
                    nameVect.addElement ( next );
                }
			}
            
            nameArr = toStringArray(nameVect);
            
            // Returns the names list
            return nameArr;
		}
		catch ( SQLException e )
		{
			e.printStackTrace ();
            
            String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getCurrentArchivedAtt() method...";
			throw new ArchivingException(message , reason , ErrSeverity.ERR , desc , this.getClass().getName() , e);
		}
        catch ( Throwable t )
        {
            t.printStackTrace ();
            
            SQLException sqle = new SQLException ( t.toString() );
            sqle.setStackTrace ( t.getStackTrace() );
            
            String message = "";
            if ( t.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || t.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed2 while executing DataBaseApi.getCurrentArchivedAtt() method...";
            throw new ArchivingException(message , reason , ErrSeverity.ERR , desc , this.getClass().getName() , sqle );
        }
        finally
        {
            try 
            {
                close ( rset );
                close ( stmt );
                
                // Close the connection with the database
                if ( getAutoConnect() )
                {
                    close();
                }
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.getCurrentArchivedAtt() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
	}

	/**
	 * <b>Description : </b>    	Gets the number of attributes that are being archived in <I>HDB</I>
	 *
	 * @return An integer which is the number of attributes being archived in <I>HDB</I>
	 * @throws ArchivingException
	 */
	public int getCurrentArchivedAttCount() throws ArchivingException
	{
        if (canceled) return 0;
		// todo test
		int activeSimpleSignalCount = 0;
		Statement stmt;
		ResultSet rset;

		// First connect with the database
		// (if not already done)
		if ( getAutoConnect() )
			connect();

		// Create and execute the SQL query string
		String select_field = "COUNT(*)";
		String table = getDbSchema() + "." + ConfigConst.TABS[ 2 ];
		String clause_1 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 2 ] + " IS NOT NULL";
		String clause_2 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 3 ] + " IS NULL";
		String getAttributeDataQuery = "SELECT DISTINCT(" + select_field + ")" + " FROM " + table + " WHERE " +
		                               "(" + clause_1 + " AND " + clause_2 + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			// Gets the result of the query
			if ( rset.next() )
				activeSimpleSignalCount = rset.getInt(1);
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getCurrentArchivedAttCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the number of active simple signals defined in HDB
		return activeSimpleSignalCount;
	}

	/**
	 * <b>Description : </b>    	Gets the current archiving mode for a given attribute name.
	 *
	 * @return An array of string containing all the current mode's informations for a given attibute's name
	 * @throws ArchivingException
	 */
	public Mode getCurrentArchivingMode(String attribut_name , boolean historic) throws ArchivingException
	{
        PreparedStatement preparedStatement = null;
        ResultSet rset = null;
        
        try
        {
            if (canceled) return null;
    		Mode mode = new Mode();
    		
    		// Create and execute the SQL query string
    		// Build the query string
    		String getCurrentArchivingModeQuery = "";
    		String select_field =
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 4 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 5 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 6 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 7 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 8 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 9 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 10 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 11 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 12 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 13 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 14 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 15 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 16 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 17 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 18 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 19 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 20 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 21 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 22 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 23 ] + ", " +
    		        ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 24 ] + ", ";
    
    		if ( historic )
    		{
    			select_field = select_field + ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 25 ];
    		}
    		else
    		{
    			select_field = select_field +
    			               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 25 ] + ", " +
    			               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 26 ] + ", " +
    			               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 27 ];
    		}
    
    		String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 2 ];
    		String table_2 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
    		String clause_1 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 0 ] + " = " + ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_MOD[ 0 ];
    		//String clause_2 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " LIKE " + "?";
            String clause_2 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " = " + "?";
    		String clause_3 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 3 ] + " IS NULL ";
    
    		getCurrentArchivingModeQuery = "SELECT " + select_field + " FROM " + table_1 + ", " + table_2 +
    		                               " WHERE (" + "(" + clause_1 + ")" + " AND " + "(" + clause_2 + ")" + " AND " + "(" + clause_3 + ")" + ") ";
		
            //System.out.println ( "CLA/DataBaseApi/getCurrentArchivingMode/query|"+getCurrentArchivingModeQuery+"|" );
            
            preparedStatement = dbconn.prepareStatement(getCurrentArchivingModeQuery);
            lastStatement = preparedStatement;
            if (canceled) return null;
			preparedStatement.setString(1 , attribut_name.trim());
			rset = preparedStatement.executeQuery();
            if (canceled) return null;
			while ( rset.next() )
			{
				if ( rset.getInt(ConfigConst.TAB_MOD[ 4 ]) == 1 )
				{
					ModePeriode modePeriode = new ModePeriode(rset.getInt(ConfigConst.TAB_MOD[ 5 ]));
					mode.setModeP(modePeriode);
				}
				if ( rset.getInt(ConfigConst.TAB_MOD[ 6 ]) == 1 )
				{
					ModeAbsolu modeAbsolu = new ModeAbsolu(rset.getInt(ConfigConst.TAB_MOD[ 7 ]) ,
					                                       rset.getDouble(ConfigConst.TAB_MOD[ 8 ]) , rset.getDouble(ConfigConst.TAB_MOD[ 9 ]));
					mode.setModeA(modeAbsolu);
				}
				if ( rset.getInt(ConfigConst.TAB_MOD[ 10 ]) == 1 )
				{
					ModeRelatif modeRelatif = new ModeRelatif(rset.getInt(ConfigConst.TAB_MOD[ 11 ]) ,
					                                          rset.getDouble(ConfigConst.TAB_MOD[ 12 ]) , rset.getDouble(ConfigConst.TAB_MOD[ 13 ]));
					mode.setModeR(modeRelatif);
				}
				if ( rset.getInt(ConfigConst.TAB_MOD[ 14 ]) == 1 )
				{
					ModeSeuil modeSeuil = new ModeSeuil(rset.getInt(ConfigConst.TAB_MOD[ 15 ]) ,
					                                    rset.getDouble(ConfigConst.TAB_MOD[ 16 ]) , rset.getDouble(ConfigConst.TAB_MOD[ 17 ]));
					mode.setModeT(modeSeuil);
				}
				if ( rset.getInt(ConfigConst.TAB_MOD[ 18 ]) == 1 )
				{
					ModeCalcul modeCalcul = new ModeCalcul(rset.getInt(ConfigConst.TAB_MOD[ 19 ]) ,
					                                       rset.getInt(ConfigConst.TAB_MOD[ 20 ]) , rset.getInt(ConfigConst.TAB_MOD[ 21 ]));
					mode.setModeC(modeCalcul);
					// Warning Field 18 is not used yet ...
				}
				if ( rset.getInt(ConfigConst.TAB_MOD[ 23 ]) == 1 )
				{
					ModeDifference modeDifference = new ModeDifference(rset.getInt(ConfigConst.TAB_MOD[ 24 ]));
					mode.setModeD(modeDifference);
				}
				if ( rset.getInt(ConfigConst.TAB_MOD[ 25 ]) == 1 )
				{
					ModeExterne modeExterne = new ModeExterne();
					mode.setModeE(modeExterne);
				}

				if ( !historic )
				{
					TdbSpec tdbSpec = new TdbSpec(rset.getInt(ConfigConst.TAB_MOD[ 26 ]) , rset.getInt(ConfigConst.TAB_MOD[ 27 ]));
					mode.setTdbSpec(tdbSpec);
				}
			}
            
            // Returns the names list
            return mode;
		}
		catch ( SQLException e )
		{
            e.printStackTrace ();
            
            String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getCurrentArchivingMode() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
        catch ( Throwable t )
        {
            t.printStackTrace ();
            
            SQLException sqle = new SQLException ( t.toString() );
            sqle.setStackTrace ( t.getStackTrace() );
            
            String message = "";
            if ( t.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || t.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed2 while executing DataBaseApi.getCurrentArchivingMode() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , sqle);
        }
        finally
        {
            try 
            {
                close ( rset );
                close ( preparedStatement );
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.getCurrentArchivingMode() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
	}

	/**
	 * This method retrieves a given archiver's current tasks and for a given archiving type.
	 *
	 * @param archiverName
	 * @param historic
	 * @return The current task for a given archiver and for a given archiving type.
	 * @throws ArchivingException
	 */
	public Vector getArchiverCurrentTasks(String archiverName , boolean historic) throws ArchivingException
	{
        if (canceled) return null;
		Vector attributeListConfig = new Vector();
		PreparedStatement preparedStatement;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getArchiverCurrentTasksQuery = "";
		// ADT
		String select_field =
		        ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + ", " + //	fullname
		        ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 8 ] + ", " + //	data_type
		        ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 9 ] + ", " + //	data_format
		        ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 10 ] + ", ";		//	writable
		// AMT
		select_field = select_field +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 4 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 5 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 6 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 7 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 8 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 9 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 10 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 11 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 12 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 13 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 14 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 15 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 16 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 17 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 18 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 19 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 20 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 21 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 22 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 23 ] + ", " +
		               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 24 ] + ", ";

		if ( historic )
		{
			select_field = select_field + ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 25 ];
		}
		else
		{
			select_field = select_field +
			               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 25 ] + ", " +
			               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 26 ] + ", " +
			               ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 27 ];
		}

		//adt.full_name, adt.data_type, adt.data_format, adt.writable, amt.*
		String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		String table_2 = getDbSchema() + "." + ConfigConst.TABS[ 2 ];
		String clause_1 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 0 ] + " = " + ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 0 ];
		//String clause_2 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 1 ] + " LIKE " + "?";
        String clause_2 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 1 ] + " = " + "?";
		String clause_3 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 3 ] + " IS NULL ";

		getArchiverCurrentTasksQuery = "SELECT " + select_field + " FROM " + table_1 + ", " + table_2 +
		                               " WHERE (" + "(" + clause_1 + ")" + " AND " + "(" + clause_2 + ")" + " AND " + "(" + clause_3 + ")" + ") ";
		
		
		/*if ( db_type == ConfigConst.BD_MYSQL )
		{
		    System.out.println ( "CLA/BD_MYSQL!!!" );
		}
		else 
		{
		    System.out.println ( "CLA/BD_ORACLE!!!" );
		}
		System.out.println ( "CLA/getArchiverCurrentTasksQuery/"+getArchiverCurrentTasksQuery );*/
		
		try
		{
			preparedStatement = dbconn.prepareStatement(getArchiverCurrentTasksQuery);
            //System.out.println("/historic/"+historic+"/getArchiverCurrentTasksQuery/"+getArchiverCurrentTasksQuery);
            lastStatement = preparedStatement;
            if (canceled) return null;
			preparedStatement.setString(1 , archiverName.trim());
			rset = preparedStatement.executeQuery();
            if (canceled) return null;
			while ( rset.next() )
			{
				AttributeLightMode attributeLightMode = new AttributeLightMode();

				attributeLightMode.setAttribute_complete_name(rset.getString(1));
				attributeLightMode.setData_type(rset.getInt(2));
				attributeLightMode.setData_format(rset.getInt(3));
				attributeLightMode.setWritable(rset.getInt(4));
				attributeLightMode.setDevice_in_charge(archiverName);
				//Mode
				Mode mode = new Mode();
				if ( rset.getInt(ConfigConst.TAB_MOD[ 4 ]) != 0 )
				{
					ModePeriode modePeriode = new ModePeriode(rset.getInt(ConfigConst.TAB_MOD[ 5 ]));
					mode.setModeP(modePeriode);
				}
				if ( rset.getInt(ConfigConst.TAB_MOD[ 6 ]) != 0 )
				{
					ModeAbsolu modeAbsolu = new ModeAbsolu(rset.getInt(ConfigConst.TAB_MOD[ 7 ]) , rset.getDouble(ConfigConst.TAB_MOD[ 8 ]) , rset.getDouble(ConfigConst.TAB_MOD[ 9 ]));
					mode.setModeA(modeAbsolu);
				}
				if ( rset.getInt(ConfigConst.TAB_MOD[ 10 ]) != 0 )
				{
					ModeRelatif modeRelatif = new ModeRelatif(rset.getInt(ConfigConst.TAB_MOD[ 11 ]) , rset.getDouble(ConfigConst.TAB_MOD[ 12 ]) , rset.getDouble(ConfigConst.TAB_MOD[ 13 ]));
					mode.setModeR(modeRelatif);
				}
				if ( rset.getInt(ConfigConst.TAB_MOD[ 14 ]) != 0 )
				{
					ModeSeuil modeSeuil = new ModeSeuil(rset.getInt(ConfigConst.TAB_MOD[ 15 ]) , rset.getDouble(ConfigConst.TAB_MOD[ 16 ]) , rset.getDouble(ConfigConst.TAB_MOD[ 17 ]));
					mode.setModeT(modeSeuil);
				}
				if ( rset.getInt(ConfigConst.TAB_MOD[ 18 ]) != 0 )
				{
					ModeCalcul modeCalcul = new ModeCalcul(rset.getInt(ConfigConst.TAB_MOD[ 19 ]) , rset.getInt(ConfigConst.TAB_MOD[ 20 ]) , rset.getInt(ConfigConst.TAB_MOD[ 21 ]));
					mode.setModeC(modeCalcul);
				}
				if ( rset.getInt(ConfigConst.TAB_MOD[ 23 ]) != 0 )
				{
					ModeDifference modeDifference = new ModeDifference(rset.getInt(ConfigConst.TAB_MOD[ 24 ]));
					mode.setModeD(modeDifference);
				}
				if ( rset.getInt(ConfigConst.TAB_MOD[ 25 ]) != 0 )
				{
					ModeExterne modeExterne = new ModeExterne();
					mode.setModeE(modeExterne);
				}

				if ( !historic )
				{
					TdbSpec tdbSpec = new TdbSpec(rset.getInt(ConfigConst.TAB_MOD[ 26 ]) , rset.getInt(ConfigConst.TAB_MOD[ 27 ]));
					mode.setTdbSpec(tdbSpec);
				}
				attributeLightMode.setMode(mode);
				attributeListConfig.add(attributeLightMode);
			}
			close(preparedStatement);
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getArchiverCurrentTasks() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return attributeListConfig;
	}

	/**
	 * <b>Description : </b>    	Gets the name of the device in charge of the archiving of the given attribute.
	 *
	 * @return the name of the device in charge of the archiving of the given attribute.
	 * @throws ArchivingException
	 */
	public String getDeviceInCharge(String attribut_name) throws ArchivingException
	{
        if (canceled) return null;
		String deviceInCharge = "";
		PreparedStatement preparedStatement = null;
		ResultSet rset = null;
		// Create and execute the SQL query string
		// Build the query string
		String getDeviceInChargeQuery = "";
		String select_field = "";
		select_field = select_field + ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 1 ];

		String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 2 ];
		String table_2 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		String clause_1 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 0 ] + " = " + ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 0 ];
		//String clause_2 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " LIKE " + "?";
        String clause_2 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + "=" + "?";
		String clause_3 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 3 ] + " IS NULL ";

		getDeviceInChargeQuery = "SELECT " + select_field + " FROM " + table_1 + ", " + table_2 +
		                         " WHERE (" + "(" + clause_1 + ")" + " AND " + "(" + clause_2 + ")" + " AND " + "(" + clause_3 + ")" + ") ";

		try
		{
            //System.out.println ( "CLA/DataBaseApi/getDeviceInCharge/query|"+getDeviceInChargeQuery+"|" );
            
            preparedStatement = dbconn.prepareStatement(getDeviceInChargeQuery);
            lastStatement = preparedStatement;
            if (canceled) return null;
			preparedStatement.setString(1 , attribut_name.trim());
			rset = preparedStatement.executeQuery();
            if (canceled) return null;
			while ( rset.next() )
			{
				deviceInCharge = rset.getString(ConfigConst.TAB_MOD[ 1 ]);
			}
		}
		catch ( SQLException e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + GlobalConst.EXTRAC_FAILURE;
			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getDeviceInCharge() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
        finally
        {
            try 
            {
                close ( rset );
                close ( preparedStatement );
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.getDeviceInCharge() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
		// Returns the names list
		return deviceInCharge;
	}

	/*=================================================================================================
	      Extractions li�es aux tables propres aux attributs
	=================================================================================================*/

	public DbData getAttData(String att_name) throws ArchivingException
	{
        if (canceled) return null;
		DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray();
		DbData dbData = new DbData(att_name);
		int[] tfw = getAtt_TFW_Data(att_name);
		dbData.setData_type(tfw[ 0 ]);
		dbData.setData_format(tfw[ 1 ]);
		dbData.setWritable(tfw[ 2 ]);
		String message = "" , reason = "" , desc = "";
		switch ( dbData.getData_format() )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				dvdsa = getAttScalarData(att_name , dbData.getWritable());
				dbData.setData(dvdsa);
				break;
			case AttrDataFormat._SPECTRUM:
				/*message = "Failed retrieving timedAttrData ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());*/
			    Vector list = this.getAttSpectrumData ( att_name );
				dbData.setData(list);
				//dbData.setMax_x ( dbData.size() );
				break;
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving timedAttrData ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		//System.out.println ( "CLA/getAttData/END" );
		return dbData;
	}

	/**
	 * <b>Description : </b> Gets all the data archieved for a scalar attribute
	 * (writable = ReadOnly or WriteOnly)
	 * 
	 * @return All the data associated with their corresponding timestamp <br>
	 * @throws ArchivingException
	 */
	public DevVarDoubleStringArray getAttScalarData(String att_name , int writable) throws ArchivingException
	{
        if (canceled) return null;
		DevVarDoubleStringArray dvdsa;
		Vector timeVect = new Vector();
		Vector valueRVect = new Vector();
		Vector valueWVect = new Vector();
		String[] timeArr = new String[ 5 ];
		double[] valueRWArr = new double[ 5 ];
		int data_type = getAtt_TFW_Data(att_name)[0];
		boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
		Statement stmt;
		ResultSet rset;

		// Create and execute the SQL query string
		// Build the query string
		String tableName = getDbSchema() + "." + getTableName(att_name);
		String fields = ro_fields ?
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] ); // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
		// My statement
		String query = "SELECT " + fields + " FROM " + tableName;

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(query);
            if (canceled) return null;
            while ( rset.next() )
            {
                String rawDate = rset.getString ( 1 );
                String displayDate;
                
                try
                {
                    displayDate = DateUtil.stringToDisplayString ( rawDate );
                }
                catch ( Exception e )
                {
                    e.printStackTrace();

                    String _reason = "FAILED TO PARSE DATE|"+rawDate+"|";
                    String message= _reason;
                    String _desc = "Failed while executing DataBaseApi.getAttScalarData() method...";
                    throw new ArchivingException(message , _reason , ErrSeverity.WARN , _desc , this.getClass().getName() , e);
                }
                
                timeVect.addElement ( displayDate );
                
                if ( ro_fields )
    			{
    				if (data_type == TangoConst.Tango_DEV_STRING)
    				{
                        String result = rset.getString(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement(  "null"  );
                        }
                        else
                        {
                            valueRVect.addElement(  StringFormater.formatStringToRead( result )  );
                        }
    				}
    				else
    				{
                        double result = rset.getDouble(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                        }
                        else
                        {
                            valueRVect.addElement(new Double(result));
                        }
    				}
    			}
    			else
    			{
    				if (data_type == TangoConst.Tango_DEV_STRING)
    				{
                        String result1 = rset.getString(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement(  "null"  );
                        }
                        else
                        {
                            valueRVect.addElement(  StringFormater.formatStringToRead( result1 )  );
                        }
                        String result2 = rset.getString(3);
                        if (rset.wasNull())
                        {
                            valueWVect.addElement(  "null"  );
                        }
                        else
                        {
                            valueWVect.addElement(  StringFormater.formatStringToRead( result2 )  );
                        }
    				}
    				else
    				{
                        double result1 = rset.getDouble(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement(  new Double(GlobalConst.NAN_FOR_NULL)  );
                        }
                        else
                        {
                            valueRVect.addElement(  new Double(result1)  );
                        }
                        double result2 = rset.getDouble(3);
                        if (rset.wasNull())
                        {
                            valueWVect.addElement(  new Double(GlobalConst.NAN_FOR_NULL)  );
                        }
                        else
                        {
                            valueWVect.addElement(  new Double(result2)  );
                        }
    				}
    			}
            }
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttData() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		if (data_type == TangoConst.Tango_DEV_STRING)
		{
			// in case of Tango_DEV_STRING, time is coded in double, value in String
			timeArr = toStringArray(timeVect);
			valueRWArr = new double[timeArr.length];
			for (int i = 0; i < timeArr.length; i++)
			{
				valueRWArr[i] = DateUtil.stringToMilli( timeArr[i] );
			}
			timeArr = toStringArray(valueRVect , valueWVect);
			dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
		}
		else
		{
			timeArr = toStringArray(timeVect);
			valueRWArr = toDoubleArray(valueRVect , valueWVect);
			dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
		}
		return dvdsa;
	}

	/**
	 * <b>Description : </b>    	Returns the number of the data archieved for an attribute
	 *
	 * @return The number of the data archieved for an attribute<br>
	 * @throws ArchivingException
	 */
	public int getAttDataCount(String att_name) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		String tableName = getDbSchema() + "." + getTableName(att_name.trim());
		// MySQL and Oracle querry are the same in this case : no test
		getAttributeDataQuery =
		"SELECT " + " COUNT(*) " + " FROM " + tableName;

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				valuesCount = rset.getInt(1);
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Returns the number of corresponding records
		return valuesCount;
	}

	/**
	 * <b>Description : </b>    	Retrieves data beetwen two dates, for a given scalar attribute.
	 *
	 * @param argin The attribute's name, the beginning date  (DD-MM-YYYY HH24:MI:SS.FF) and the ending date (DD-MM-YYYY HH24:MI:SS.FF).
	 * @param samplingFactor 
	 * @return The scalar data for the specified attribute <br>
	 * @throws ArchivingException
	 */
	public DbData getAttDataBetweenDates(String[] argin, SamplingType samplingFactor) throws ArchivingException
	{
        if (canceled) return null;
        String att_name = argin[ 0 ].trim();

        
        String time_0 = ( db_type == ConfigConst.BD_MYSQL ) ? argin[ 1 ] : DateUtil.stringToDisplayString(argin[ 1 ]);
		String time_1 = ( db_type == ConfigConst.BD_MYSQL ) ? argin[ 2 ] : DateUtil.stringToDisplayString(argin[ 2 ]);
        /*String time_0 = ( db_type == ConfigConst.BD_MYSQL ) ? DateUtil.oracleToMySQL ( argin[ 1 ] ) : DateUtil.stringToDisplayString(argin[ 1 ]);
        String time_1 = ( db_type == ConfigConst.BD_MYSQL ) ? DateUtil.oracleToMySQL ( argin[ 2 ] ) : DateUtil.stringToDisplayString(argin[ 2 ]);*/
        //System.out.println("getAttDataBetweenDates/argin[ 1 ]/"+argin[ 1 ]+"/time_0/"+time_0);
        
		DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray();
		Vector result;
		DbData dbData = new DbData(att_name);
		int[] tfw = getAtt_TFW_Data(att_name);
		dbData.setData_type(tfw[ 0 ]);
		dbData.setData_format(tfw[ 1 ]);
		dbData.setWritable(tfw[ 2 ]);
		String message = "" , reason = "" , desc = "";
		if (canceled) return null;
		switch ( dbData.getData_format() )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
                dvdsa = getAttScalarDataBetweenDates(att_name , time_0 , time_1 , dbData.getWritable() , samplingFactor );
            	dbData.setData(dvdsa);
				break;
			case AttrDataFormat._SPECTRUM:
			    result = getAttSpectrumDataBetweenDates(att_name, time_0, time_1, dbData.getData_type() , samplingFactor );
		        dbData.setData(result);		        
		        break;
			case AttrDataFormat._IMAGE:
                result = getAttImageDataBetweenDates(att_name, time_0, time_1, dbData.getData_type() , samplingFactor );
                dbData.setData(result); 
                break;
            default:
                message = "Failed retrieving dvdsa ! ";
                reason = "The attribute should be Scalar ";
                desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Image) !!";
                throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		return dbData;
	}
    
    /**
     * <b>Description : </b>        Retrieves timestamps associated with data records data beetwen two dates, for a given scalar attribute.
     *
     * @param argin The attribute's name, the beginning date  (DD-MM-YYYY HH24:MI:SS.FF) and the ending date (DD-MM-YYYY HH24:MI:SS.FF).
     * @param samplingType 
     * @return The list of timestamps for the specified attribute <br>
     * @throws ArchivingException
     */
    public Vector getAttPartialImageDataBetweenDates(String[] argin ,  boolean _historic, SamplingType samplingType) throws ArchivingException
    {
        if (canceled) return null;
        String att_name = argin[ 0 ].trim();
        
        String time_0 = ( db_type == ConfigConst.BD_MYSQL ) ? argin[ 1 ] : DateUtil.stringToDisplayString(argin[ 1 ]);
        String time_1 = ( db_type == ConfigConst.BD_MYSQL ) ? argin[ 2 ] : DateUtil.stringToDisplayString(argin[ 2 ]);

        String message = "" , reason = "" , desc = "";
        
        Statement stmt;
        ResultSet rset;
        // Create and execute the SQL query string
        // Build the query string
        String query = "";
        String fields = "";
        String whereClause = "";
        String groupByClause = "";
        String orderByClause = "";
        String tableName = getDbSchema() + "." + getTableName(att_name);
        //--
        //System.out.println ( "CLA/getAttPartialImageDataBetweenDates/BEFORE/"+new Timestamp (System.currentTimeMillis()) );
        //String viewName = createViewForImageAttribute ( tableName );

        if ( samplingType.hasSampling () )
        {            
            boolean isMySQL = ( this.db_type == ConfigConst.BD_MYSQL );
            String format = isMySQL ? samplingType.getMySqlFormat () : samplingType.getOracleFormat ();
            
            String selectField_0 = toDbTimeFieldString( ConfigConst.TAB_IMAGE_RO[ 0 ] , format );
            String selectField_1 = SamplingType.AVERAGING_NORMALISATION + " ( " + ConfigConst.TAB_IMAGE_RO[ 1 ]  + " ) "; 
            String selectField_2 = SamplingType.AVERAGING_NORMALISATION + " ( " + ConfigConst.TAB_IMAGE_RO[ 2 ]  + " ) ";
            fields = selectField_0 + ", " + selectField_1 + ", " + selectField_2;
            
            whereClause = ConfigConst.TAB_IMAGE_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
            groupByClause = " GROUP BY " +toDbTimeFieldString(ConfigConst.TAB_IMAGE_RO[ 0 ], format);
            orderByClause = " ORDER BY " +toDbTimeFieldString(ConfigConst.TAB_IMAGE_RO[ 0 ], format);
            
            query = "SELECT " + fields + " FROM " + tableName + " WHERE " + whereClause + groupByClause + orderByClause;;            
        }
        else
        {
            fields = ( toDbTimeFieldString(ConfigConst.TAB_IMAGE_RO[ 0 ]) + ", " + ConfigConst.TAB_IMAGE_RO[ 1 ] + ", " + ConfigConst.TAB_IMAGE_RO[ 2 ] );
            whereClause = ConfigConst.TAB_IMAGE_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
            query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + whereClause + ")";    
        }
        
        //System.out.println ( "CLA/getAttPartialImageDataBetweenDates/query/"+query+"/" );
        
        try
        {
            stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
            
            rset = stmt.executeQuery(query);
            if (canceled) return null;
            //System.out.println ( "CLA/getAttPartialImageDataBetweenDates/AFTER/"+new Timestamp (System.currentTimeMillis()) );
            Vector ret = new Vector ();
            
            while ( rset.next() )
            {
                String date = rset.getString (1);
                Timestamp _date = new Timestamp ( DateUtil.stringToMilli ( date ) );
                int dimX = rset.getInt ( 2 );
                int dimY = rset.getInt ( 3 );
                
                ImageData data = new ImageData (this);
                data.setDate ( _date );
                data.setDimX ( dimX );
                data.setDimY ( dimY );
                data.setName ( att_name );
                data.setHistoric ( _historic );
                
                ret.add ( data );
                //System.out.println ( "CLA/getAttPartialImageDataBetweenDates/next/date|"+date+"|" );
            }
            
            close ( stmt );
            return ret;
        }
        catch ( SQLException e )
        {
            message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            reason = GlobalConst.QUERY_FAILURE;
            desc = "Failed while executing DataBaseApi.getAttDataDatesBetweenDates() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        
        
    }
    
    public DevVarDoubleStringArray getAttScalarDataBetweenDates2(String att_name , String time_0 , String time_1 , int writable, int samplingFactor) throws ArchivingException
    {
        if (canceled) return null;
        DevVarDoubleStringArray dvdsa;
        Vector timeVect = new Vector();
        Vector valueRVect = new Vector();
        Vector valueWVect = new Vector();
        String[] timeArr = new String[ 5 ];
        double[] valueRWArr = new double[ 5 ];
        boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
        
        ResultSet rset;
        int data_type = getAtt_TFW_Data(att_name)[0];

        // Create and execute the SQL query string
        // Build the query string
        
        String tableName = getDbSchema() + "." + getTableName(att_name);
        
        CallableStatement callableStatement = null;
        //StringBuffer query = new StringBuffer().append("{call ").append(getDbSchema()).append(".notify_of_file_to_import(?, ?, ?)}");
        StringBuffer query = new StringBuffer().append("{call ").append(getDbSchema()).append(".sample_data(?, ?, ?, ?)}");
        
        try
        {
            callableStatement = dbconn.prepareCall(query.toString());
            lastStatement = callableStatement;
            callableStatement.setString(1 , tableName);
            callableStatement.setString(2 , time_0);
            callableStatement.setString(3 , time_1);
            callableStatement.setInt(4 , samplingFactor);
         
            if (canceled) return null;
            rset = callableStatement.executeQuery ();
            if (canceled) return null;
            
            if ( ro_fields )
            {
                while ( rset.next() )
                {
                    if (data_type == TangoConst.Tango_DEV_STRING)
                    {
                        timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                        String val = rset.getString(2);
                        if (rset.wasNull())
                        {
                            val = "null";
                        }
                        else
                        {
                            val = StringFormater.formatStringToRead(val);
                        }
                        valueRVect.addElement(new String(val));
                    }
                    else if (data_type == TangoConst.Tango_DEV_BOOLEAN)
                    {
                        String result = rset.getString(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement(new Double( GlobalConst.NAN_FOR_NULL ));
                        }
                        else
                        {
                            timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                            boolean resultValue = ( ("1".equalsIgnoreCase(result.trim())) || ("true".equalsIgnoreCase(result.trim())) || ("vrai".equalsIgnoreCase(result.trim())) );
                            valueRVect.addElement(new Double( (resultValue?1:0) ));
                        }
                    }
                    else
                    {
                        timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                        valueRVect.addElement(new Double(rset.getDouble(2)));
                    }
                }
            }
            else
            {
                while ( rset.next() )
                {
                    if (data_type == TangoConst.Tango_DEV_STRING)
                    {
                        timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                        String result1 = rset.getString(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement(  "null"  );
                        }
                        else
                        {
                            valueRVect.addElement(  StringFormater.formatStringToRead( result1 )  );
                        }
                        String result2 = rset.getString(3);
                        if (rset.wasNull())
                        {
                            valueWVect.addElement(  "null"  );
                        }
                        else
                        {
                            valueWVect.addElement(  StringFormater.formatStringToRead( result2 )  );
                        }
                    }
                    else if (data_type == TangoConst.Tango_DEV_BOOLEAN)
                    {
                        String result1 = rset.getString(2);
                        if (result1 == null) result1 = "";
                        if (rset.wasNull())
                        {
                            valueRVect.addElement(  new Double(GlobalConst.NAN_FOR_NULL)  );
                        }
                        else
                        {
                            boolean resultValueRead = ( ("1".equalsIgnoreCase(result1.trim())) || ("true".equalsIgnoreCase(result1.trim())) || ("vrai".equalsIgnoreCase(result1.trim())) );
                            valueRVect.addElement(new Double( (resultValueRead?1:0) ));
                        }
                        String result2 = rset.getString(3);
                        if (rset.wasNull())
                        {
                            valueWVect.addElement(  new Double(GlobalConst.NAN_FOR_NULL)  );
                        }
                        else
                        {
                            boolean resultValueWrite = ( ("1".equalsIgnoreCase(result2.trim())) || ("true".equalsIgnoreCase(result2.trim())) || ("vrai".equalsIgnoreCase(result2.trim())) );
                            valueWVect.addElement(new Double( (resultValueWrite?1:0) ));
                        }
                        timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    }
                    else
                    {
                        timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                        double result1 = rset.getDouble(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement(  new Double(GlobalConst.NAN_FOR_NULL)  );
                        }
                        else
                        {
                            valueRVect.addElement(new Double(result1));
                        }
                        double result2 = rset.getDouble(3);
                        if (rset.wasNull())
                        {
                            valueWVect.addElement(  new Double(GlobalConst.NAN_FOR_NULL)  );
                        }
                        else
                        {
                            valueWVect.addElement(new Double(result2));
                        }
                    }
                }
            }
        }
        catch ( SQLException e )
        {
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.getAttDataBetweenDates() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        finally
        {
            try 
            {
                callableStatement.close ();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
                
                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Unknown error while executing DataBaseApi.getAttDataBetweenDates() method...";
                throw new ArchivingException(desc , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
        
        if (data_type == TangoConst.Tango_DEV_STRING)
        {
            // in case of Tango_DEV_STRING, time is coded in double, value in String
            timeArr = toStringArray(timeVect);
            valueRWArr = new double[timeArr.length];
            for (int i = 0; i < timeArr.length; i++)
            {
                valueRWArr[i] = DateUtil.stringToMilli(timeArr[i]);
            }
            timeArr = toStringArray(valueRVect , valueWVect);
            dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
        }
        else
        {
            timeArr = toStringArray(timeVect);
            valueRWArr = toDoubleArray(valueRVect , valueWVect);
            dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
        }
        return dvdsa;
    }
   
    public DevVarDoubleStringArray getAttScalarDataBetweenDates(String att_name , String time_0 , String time_1 , int writable, SamplingType samplingType) throws ArchivingException
    {
        /*System.out.println ( "getAttScalarDataBetweenDates/samplingType VVVVVVVVVV" );
        System.out.println ( samplingType.getDescription() );
        System.out.println ( "getAttScalarDataBetweenDates/samplingType ^^^^^^^^^^" );*/
        DevVarDoubleStringArray dvdsa;
        Vector timeVect = new Vector();
        Vector valueRVect = new Vector();
        Vector valueWVect = new Vector();
        String[] timeArr = new String[ 5 ];
        double[] valueRWArr = new double[ 5 ];
        boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
        Statement stmt = null;
        ResultSet rset = null;
        int data_type = getAtt_TFW_Data(att_name)[0];

        // Create and execute the SQL query string
        // Build the query string
        String query = "";
        String fields = "";
        String tableName = getDbSchema() + "." + getTableName(att_name);
        String whereClause = "";
        String groupByClause = "";
        String orderByClause = "";
        
        if ( samplingType.hasSampling () )
        {
            if ( ! samplingType.hasAdditionalFiltering () )
            {
                String format = (db_type == ConfigConst.BD_MYSQL) ? samplingType.getMySqlFormat () : samplingType.getOracleFormat ();
                String groupingNormalisationType = SamplingType.getGroupingNormalisationType ( data_type );
                
                fields = ro_fields ?
                         ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ], format) + ", " + groupingNormalisationType + "(" + ConfigConst.TAB_SCALAR_RO[ 1 ] ) + ")" :
                         ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ], format) + ", " + groupingNormalisationType + "(" + ConfigConst.TAB_SCALAR_RW[ 1 ] + ") , " + groupingNormalisationType + "(" + ConfigConst.TAB_SCALAR_RW[ 2 ] ) + ")";
                         //System.out.println ( "CLA/getAttScalarDataBetweenDates/fields1|"+fields+"|" );
                         whereClause = ConfigConst.TAB_SCALAR_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
                groupByClause = " GROUP BY " +toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ], format);
                orderByClause = " ORDER BY " +toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ], format);
                
                query = "SELECT " + fields + " FROM " + tableName + " WHERE " + whereClause + groupByClause + orderByClause;
            }
            else
            {
                String format = samplingType.getOneLevelHigherFormat ( db_type == ConfigConst.BD_MYSQL );
                //System.out.println ( "getAttScalarDataBetweenDates/format|"+format+"|" );
                //String fullFormat = ( SamplingType.getSamplingType ( SamplingType.ALL ) ).getFormat ( db_type == ConfigConst.BD_MYSQL );
                String fullFormat = ( SamplingType.getSamplingType ( SamplingType.SECOND ) ).getFormat ( db_type == ConfigConst.BD_MYSQL );
                
                String groupingNormalisationType = SamplingType.getGroupingNormalisationType ( data_type );
                
                String minTime = "MIN("+ConfigConst.TAB_SCALAR_RO[ 0 ]+")";
                String convertedMinTime = toDbTimeFieldString(minTime, fullFormat); 
                
                fields = ro_fields ?
                         convertedMinTime + ", " + groupingNormalisationType + "(" + ConfigConst.TAB_SCALAR_RO[ 1 ] + ")" :
                         convertedMinTime + ", " + groupingNormalisationType + "(" + ConfigConst.TAB_SCALAR_RW[ 1 ] + ") , " + groupingNormalisationType + "(" + ConfigConst.TAB_SCALAR_RW[ 2 ] + ")";
                         //System.out.println ( "CLA/getAttScalarDataBetweenDates/fields2|"+fields+"|" );
                         whereClause = ConfigConst.TAB_SCALAR_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
                
                groupByClause = " GROUP BY " +toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ], format);
                //System.out.println ( "getAttScalarDataBetweenDates/groupByClause|"+groupByClause+"|" );
                
                groupByClause += samplingType.getAdditionalFilteringClause ( db_type == ConfigConst.BD_MYSQL , ConfigConst.TAB_SCALAR_RO[ 0 ] );
                orderByClause = ( db_type == ConfigConst.BD_MYSQL )? "" : " ORDER BY " +"MIN("+ConfigConst.TAB_SCALAR_RW[ 0 ]+")";
                
                query = "SELECT " + fields + " FROM " + tableName + " WHERE " + whereClause + groupByClause + orderByClause;
            }
        }
        else
        {
            fields = ro_fields ?
                 ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
                 ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] );
                 
            //System.out.println ( "CLA/getAttScalarDataBetweenDates/fields3|"+fields+"|" );
            whereClause = ConfigConst.TAB_SCALAR_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());

            query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + whereClause + ")";
        }
       
        //System.out.println ( "CLA/getAttScalarDataBetweenDates/query|"+query+"|" );
        try
        {
            stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
            //System.out.println ( "CLA/getAttScalarDataBetweenDates/BEFORE" );
            rset = stmt.executeQuery(query);
            //System.out.println ( "CLA/getAttScalarDataBetweenDates/AFTER" );
            if (canceled) return null;
            if ( ro_fields )
            {
                while ( rset.next() )
                {
                    timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    
                    if (data_type == TangoConst.Tango_DEV_STRING)
                    {
                        String val = rset.getString(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement("null");
                        }
                        else
                        {
                            val = StringFormater.formatStringToRead(val);
                            valueRVect.addElement(new String(val));
                        }
                    }
                    else if (data_type == TangoConst.Tango_DEV_BOOLEAN)
                    {
                        String result = rset.getString(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement(new Double( GlobalConst.NAN_FOR_NULL ));
                        }
                        else
                        {
                            boolean resultValue = ( ("1".equalsIgnoreCase(result.trim())) || ("true".equalsIgnoreCase(result.trim())) || ("vrai".equalsIgnoreCase(result.trim())) );
                            valueRVect.addElement(new Double( (resultValue?1:0) ));
                        }
                    }
                    else
                    {
                        double dvalue = rset.getDouble(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement(new Double( GlobalConst.NAN_FOR_NULL ));
                        }
                        else
                        {
                            valueRVect.addElement(new Double(dvalue));
                        }
                    }
                }
            }
            else
            {
                while ( rset.next() )
                {
                    timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    
                    if (data_type == TangoConst.Tango_DEV_STRING)
                    {
                        String result1 = rset.getString(2);
                        if (rset.wasNull()) result1 = "null";
                        String result2 = rset.getString(3);
                        if (rset.wasNull()) result2 = "null";
                        valueRVect.addElement(  StringFormater.formatStringToRead( result1 )  );
                        valueWVect.addElement(  StringFormater.formatStringToRead( result2 )  );
                    }
                    else if (data_type == TangoConst.Tango_DEV_BOOLEAN)
                    {
                        String result1 = rset.getString(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement(new Double( GlobalConst.NAN_FOR_NULL ));
                        }
                        else
                        {
                            boolean resultValueRead = ( ("1".equalsIgnoreCase(result1.trim())) || ("true".equalsIgnoreCase(result1.trim())) || ("vrai".equalsIgnoreCase(result1.trim())) );
                            valueRVect.addElement(new Double( (resultValueRead?1:0) ));
                        }
                        String result2 = rset.getString(3);
                        if (rset.wasNull())
                        {
                            valueWVect.addElement(new Double( GlobalConst.NAN_FOR_NULL ));
                        }
                        else
                        {
                            boolean resultValueWrite = ( ("1".equalsIgnoreCase(result2.trim())) || ("true".equalsIgnoreCase(result2.trim())) || ("vrai".equalsIgnoreCase(result2.trim())) );
                            valueWVect.addElement(new Double( (resultValueWrite?1:0) ));
                        }
                    }
                    else
                    {
                        double result1 = rset.getDouble(2);
                        if (rset.wasNull())
                        {
                            valueRVect.add(new Double(GlobalConst.NAN_FOR_NULL));
                        }
                        else
                        {
                            valueRVect.addElement(new Double(result1));
                        }
                        double result2 = rset.getDouble(3);
                        if (rset.wasNull())
                        {
                            valueWVect.add(new Double(GlobalConst.NAN_FOR_NULL));
                        }
                        else
                        {
                            valueWVect.addElement(new Double(result2));
                        }
                    }
                }
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            if (canceled) return null;
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.getAttDataBetweenDates() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                close ( rset );
                close ( stmt );
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.getAttDataBetweenDates() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
        
        if (data_type == TangoConst.Tango_DEV_STRING)
        {
            // in case of Tango_DEV_STRING, time is coded in double, value in String
            timeArr = toStringArray(timeVect);
            valueRWArr = new double[timeArr.length];
            for (int i = 0; i < timeArr.length; i++)
            {
                valueRWArr[i] = DateUtil.stringToMilli(timeArr[i]);
            }
            timeArr = toStringArray(valueRVect , valueWVect);
            dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
        }
        else
        {
            timeArr = toStringArray(timeVect);
            valueRWArr = toDoubleArray(valueRVect , valueWVect);
            dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);            
        }
        
        return dvdsa;
    }
    
    /**
	 * <b>Description : </b>    	Returns the number of data beetwen two dates and, for a given scalar attribute.
	 *
	 * @param argin The attribute's name, the beginning date  (DD-MM-YYYY HH24:MI:SS.FF) and the ending date (DD-MM-YYYY HH24:MI:SS.FF).
	 * @return The number of data beetwen two dates and, for a given scalar attribute.<br>
	 * @throws ArchivingException
	 */
	public int getAttDataBetweenDatesCount(String[] argin) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;
		Statement stmt;
		ResultSet rset;

		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery =
		        "SELECT " + "COUNT(*)" + " FROM " + getDbSchema() + "." + getTableName(argin[ 0 ].trim()) +
		        " WHERE " + ConfigConst.TAB_SCALAR_RO[ 0 ] + " BETWEEN " + toDbTimeString(argin[ 1 ].trim()) + " AND " + toDbTimeString(argin[ 2 ].trim());

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				valuesCount = rset.getInt(1);
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataBetweenDatesCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}

		// Returns the names list
		// Returns the number of corresponding records
		return valuesCount;
	}
    
    /**
	 * <b>Description : </b>    	Retrieves data beetwen two dates (date_1 & date_2).
	 * Data are lower than the given value x OR higher than the given value y.
	 *
	 * @param argin The attribute's name, the lower limit, the upper limit, the beginning date (DD-MM-YYYY HH24:MI:SS.FF) and the ending date (DD-MM-YYYY HH24:MI:SS.FF).
	 * @return The scalar data for the specified attribute<br>
	 * @throws ArchivingException
	 */
	public DbData getAttDataInfOrSupThanBetweenDates(String[] argin) throws ArchivingException
	{
        if (canceled) return null;
		String att_name = argin[ 0 ];
		int lower_value = Integer.parseInt(argin[ 1 ]);
		int upper_value = Integer.parseInt(argin[ 2 ]);
		String time_0 = argin[ 3 ];
		String time_1 = argin[ 4 ];
		DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray();
		DbData dbData = new DbData(att_name);
		int[] tfw = getAtt_TFW_Data(att_name);
		dbData.setData_type(tfw[ 0 ]);
		dbData.setData_format(tfw[ 1 ]);
		dbData.setWritable(tfw[ 2 ]);
		String message = "" , reason = "" , desc = "";
		switch ( dbData.getData_format() )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				dvdsa = getAttScalarDataInfOrSupThanBetweenDates(att_name , lower_value , upper_value , time_0 , time_1 , dbData.getWritable());
				dbData.setData(dvdsa);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}

		// Returns the names list
		return dbData;
	}

	public DevVarDoubleStringArray getAttScalarDataInfOrSupThanBetweenDates(String att_name , int lower_value , int upper_value , String time_0 , String time_1 , int writable) throws ArchivingException
	{
        if (canceled) return null;
		DevVarDoubleStringArray dvdsa;
		Vector timeVect = new Vector();
		Vector valueRVect = new Vector();
		Vector valueWVect = new Vector();
		String[] timeArr = new String[ 5 ];
		double[] valueRWArr = new double[ 5 ];
		boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
		Statement stmt;
		ResultSet rset;

		// Create and execute the SQL query string
		// Build the query string
		String tableName = getDbSchema() + "." + getTableName(att_name);
		String fields = ro_fields ?
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] ); // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
		String selectField = ro_fields ? ConfigConst.TAB_SCALAR_RO[ 1 ] : ConfigConst.TAB_SCALAR_RW[ 1 ];
		String dateClause = ConfigConst.TAB_SCALAR_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
		// My statement
		//query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + whereClause + ")";
		String query =
		        "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField + " < " + lower_value + " OR " + selectField + " > " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(query);
            if (canceled) return null;
			if ( ro_fields )
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
					valueRVect.addElement(new Double(rset.getDouble(2)));
				}
			}
			else
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
					valueRVect.addElement(new Double(rset.getDouble(2)));
					valueWVect.addElement(new Double(rset.getDouble(3)));
				}
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataInfOrSupThanBetweenDates() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		timeArr = toStringArray(timeVect);
		valueRWArr = toDoubleArray(valueRVect , valueWVect);
		dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
		return dvdsa;
	}


	/**
	 * <b>Description : </b>    	Returns the number of data beetwen two dates (date_1 & date_2).
	 * Data are lower than the given value x OR higher than the given value y.
	 *
	 * @param argin The attribute's name, the lower limit, the upper limit, the beginning date (DD-MM-YYYY HH24:MI:SS.FF) and the ending date (DD-MM-YYYY HH24:MI:SS.FF).
	 * @return The number of scalar data lower than the given value x OR higher than the given value y, beetwen two dates and for the specified attribute<br>
	 * @throws ArchivingException
	 */
	public int getAttDataInfOrSupThanBetweenDatesCount(String[] argin) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;
		String att_name = argin[ 0 ];
		int lower_value = Integer.parseInt(argin[ 1 ]);
		int upper_value = Integer.parseInt(argin[ 2 ]);
		String time_0 = argin[ 3 ];
		String time_1 = argin[ 4 ];

		// Retreive informations on format and writable
		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				valuesCount = getAttScalarDataInfOrSupThanBetweenDatesCount(att_name , lower_value , upper_value , time_0 , time_1 , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		// Returns the number of records
		return valuesCount;
	}

	public int getAttScalarDataInfOrSupThanBetweenDatesCount(String att_name , int lower_value , int upper_value , String time_0 , String time_1 , int writable) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		String selectField_0 = "";
		String selectField_1 = "";
		String selectFields = "";
		String dateClause = "";
		String tableName = getDbSchema() + "." + getTableName(att_name);
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			selectField_0 = ConfigConst.TAB_SCALAR_RO[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			selectField_0 = ConfigConst.TAB_SCALAR_RW[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		selectFields = "COUNT(*)";

		dateClause = selectField_0 + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
		getAttributeDataQuery =
		"SELECT " + selectFields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField_1 + " < " + lower_value + " OR " + selectField_1 + " > " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				valuesCount = rset.getInt(1);
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataInfOrSupThanBetweenDatesCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return valuesCount;
	}

	/**
	 * <b>Description : </b>    	Retrieves data beetwen two dates (date_1 & date_2) - Data are higher than the given value y.
	 *
	 * @param argin The attribute's name, the lower limit, the beginning date  (DD-MM-YYYY HH24:MI:SS.FF) and the ending date  (DD-MM-YYYY HH24:MI:SS.FF).
	 * @return The scalar data for the specified attribute<br>
	 * @throws ArchivingException
	 */
	public DbData getAttDataSupThanBetweenDates(String[] argin) throws ArchivingException
	{
        if (canceled) return null;
		String att_name = argin[ 0 ];
		int lower_value = Integer.parseInt(argin[ 1 ]);
		String time_0 = argin[ 2 ];
		String time_1 = argin[ 3 ];
		DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray();
		DbData dbData = new DbData(att_name);
		int[] tfw = getAtt_TFW_Data(att_name);
		dbData.setData_type(tfw[ 0 ]);
		dbData.setData_format(tfw[ 1 ]);
		dbData.setWritable(tfw[ 2 ]);
		String message = "" , reason = "" , desc = "";
		switch ( dbData.getData_format() )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				dvdsa = getAttScalarDataSupThanBetweenDates(att_name , lower_value , time_0 , time_1 , dbData.getWritable());
				dbData.setData(dvdsa);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}

		// Returns the names list
		return dbData;
	}

	public DevVarDoubleStringArray getAttScalarDataSupThanBetweenDates(String att_name , int lower_value , String time_0 , String time_1 , int writable) throws ArchivingException
	{
        if (canceled) return null;
		DevVarDoubleStringArray dvdsa;
		Vector timeVect = new Vector();
		Vector valueRVect = new Vector();
		Vector valueWVect = new Vector();
		String[] timeArr = new String[ 5 ];
		double[] valueRWArr = new double[ 5 ];
		boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
		Statement stmt;
		ResultSet rset;

		// Create and execute the SQL query string
		// Build the query string
		String tableName = getDbSchema() + "." + getTableName(att_name);
		String fields = ro_fields ?
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] ); // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
		String selectField = ro_fields ? ConfigConst.TAB_SCALAR_RO[ 1 ] : ConfigConst.TAB_SCALAR_RW[ 1 ];
		String dateClause = ConfigConst.TAB_SCALAR_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
		// My statement
		// String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField + " < " + lower_value + " OR " + selectField + " > " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";
		String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField + " > " + lower_value + ")" + " AND " + "(" + dateClause + ")" + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(query);
            if (canceled) return null;
			if ( ro_fields )
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result));
                    }
				}
			}
			else
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result1 = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result1));
                    }
                    double result2 = rset.getDouble(3);
                    if (rset.wasNull())
                    {
                        valueWVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueWVect.addElement(new Double(result2));
                    }
				}
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataSupThanBetweenDates() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		timeArr = toStringArray(timeVect);
		valueRWArr = toDoubleArray(valueRVect , valueWVect);
		dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
		return dvdsa;
	}

	/**
	 * <b>Description : </b>    	Returns the number of data higher than the given value y, and beetwen two dates (date_1 & date_2).
	 *
	 * @param argin The attribute's name, the lower limit, the beginning date  (DD-MM-YYYY HH24:MI:SS.FF) and the ending date  (DD-MM-YYYY HH24:MI:SS.FF).
	 * @return The number of data higher than the given value y, and beetwen two dates (date_1 & date_2).<br>
	 * @throws ArchivingException
	 */
	public int getAttDataSupThanBetweenDatesCount(String[] argin) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;

		String att_name = argin[ 0 ];
		int lower_value = Integer.parseInt(argin[ 1 ]);
		String time_0 = argin[ 2 ];
		String time_1 = argin[ 3 ];

		// Retreive informations on format and writable
		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				valuesCount = getAttScalarDataSupThanBetweenDatesCount(att_name , lower_value , time_0 , time_1 , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		// Returns the number of records
		return valuesCount;
	}

	public int getAttScalarDataSupThanBetweenDatesCount(String att_name , int lower_value , String time_0 , String time_1 , int writable) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		String selectField_0 = "";
		String selectField_1 = "";
		String selectFields = "";
		String dateClause = "";
		String tableName = getDbSchema() + "." + getTableName(att_name);
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			selectField_0 = ConfigConst.TAB_SCALAR_RO[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			selectField_0 = ConfigConst.TAB_SCALAR_RW[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		selectFields = "COUNT(*)";
		dateClause = selectField_0 + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());

		getAttributeDataQuery =
		"SELECT " + selectFields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField_1 + " > " + lower_value + ")" + " AND " + "(" + dateClause + ")" + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				valuesCount = rset.getInt(1);
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataSupThanBetweenDatesCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Returns the number of corresponding records
		return valuesCount;
	}

	/**
	 * <b>Description : </b>    	Retrieves data beetwen two dates (date_1 & date_2) - Data are higher than the given value x AND lower than the given value y.
	 *
	 * @param argin The attribute's name, the lower limit, the upper limit, the beginning date (DD-MM-YYYY HH24:MI:SS.FF) and the ending date (DD-MM-YYYY HH24:MI:SS.FF).
	 * @return The scalar data for the specified attribute<br>
	 * @throws ArchivingException
	 */
	public DbData getAttDataSupAndInfThanBetweenDates(String[] argin) throws ArchivingException
	{
        if (canceled) return null;
		String att_name = argin[ 0 ];
		int lower_value = Integer.parseInt(argin[ 1 ]);
		int upper_value = Integer.parseInt(argin[ 2 ]);
		String time_0 = argin[ 3 ];
		String time_1 = argin[ 4 ];
		DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray();
		DbData dbData = new DbData(att_name);
		int[] tfw = getAtt_TFW_Data(att_name);
		dbData.setData_type(tfw[ 0 ]);
		dbData.setData_format(tfw[ 1 ]);
		dbData.setWritable(tfw[ 2 ]);
		String message = "" , reason = "" , desc = "";
		switch ( dbData.getData_format() )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				dvdsa = getAttScalarDataSupAndInfThanBetweenDates(att_name , lower_value , upper_value , time_0 , time_1 , dbData.getWritable());
				dbData.setData(dvdsa);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}

		// Returns the names list
		return dbData;
	}

	public DevVarDoubleStringArray getAttScalarDataSupAndInfThanBetweenDates(String att_name , int lower_value , int upper_value , String time_0 , String time_1 , int writable) throws ArchivingException
	{
        if (canceled) return null;
		DevVarDoubleStringArray dvdsa;
		Vector timeVect = new Vector();
		Vector valueRVect = new Vector();
		Vector valueWVect = new Vector();
		String[] timeArr = new String[ 5 ];
		double[] valueRWArr = new double[ 5 ];
		boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
		Statement stmt;
		ResultSet rset;

		// Create and execute the SQL query string
		// Build the query string
		String tableName = getDbSchema() + "." + getTableName(att_name);
		String fields = ro_fields ?
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] ); // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
		String selectField = ro_fields ? ConfigConst.TAB_SCALAR_RO[ 1 ] : ConfigConst.TAB_SCALAR_RW[ 1 ];
		String dateClause = ConfigConst.TAB_SCALAR_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
		// My statement
		// String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField + " < " + lower_value + " OR " + selectField + " > " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";
		String query =
		        "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField + " > " + lower_value + " AND " + selectField + " < " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(query);
            if (canceled) return null;
			if ( ro_fields )
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result));
                    }
				}
			}
			else
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result1 = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result1));
                    }
                    double result2 = rset.getDouble(3);
                    if (rset.wasNull())
                    {
                        valueWVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueWVect.addElement(new Double(result2));
                    }
				}
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataSupAndInfThanBetweenDates() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		timeArr = toStringArray(timeVect);
		valueRWArr = toDoubleArray(valueRVect , valueWVect);
		dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
		return dvdsa;
	}

	/**
	 * <b>Description : </b>    	Returns the number of data higher than the given value x, (AND) lower than the given value y, and beetwen two dates (date_1 & date_2).
	 *
	 * @param argin The attribute's name, the lower limit, the upper limit, the beginning date (DD-MM-YYYY HH24:MI:SS.FF) and the ending date (DD-MM-YYYY HH24:MI:SS.FF).
	 * @return The number of data higher than the given value x, (AND) lower than the given value y, and beetwen two dates (date_1 & date_2).<br>
	 * @throws ArchivingException
	 */
	public int getAttDataSupAndInfThanBetweenDatesCount(String[] argin) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;

		String att_name = argin[ 0 ];
		int lower_value = Integer.parseInt(argin[ 1 ]);
		int upper_value = Integer.parseInt(argin[ 2 ]);
		String time_0 = argin[ 3 ];
		String time_1 = argin[ 4 ];

		// Retreive informations on format and writable
		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				valuesCount = getAttScalarDataSupAndInfThanBetweenDatesCount(att_name , lower_value , upper_value , time_0 , time_1 , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		// Returns the number of records
		return valuesCount;
	}

	public int getAttScalarDataSupAndInfThanBetweenDatesCount(String att_name , int lower_value , int upper_value , String time_0 , String time_1 , int writable) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		String selectField_0 = "";
		String selectField_1 = "";
		String selectFields = "";
		String dateClause = "";
		String tableName = getDbSchema() + "." + getTableName(att_name);
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			selectField_0 = ConfigConst.TAB_SCALAR_RO[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			selectField_0 = ConfigConst.TAB_SCALAR_RW[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		selectFields = "COUNT(*)";
		dateClause = selectField_0 + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());

		getAttributeDataQuery =
		"SELECT " + selectFields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField_1 + " > " + lower_value + " AND " + selectField_1 + " < " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				valuesCount = rset.getInt(1);
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataSupAndInfThanBetweenDatesCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return valuesCount;
	}

	/**
	 * <b>Description : </b>    	Retrieves data beetwen two dates (date_1 & date_2) - Data are lower than the given value x.
	 *
	 * @param argin The attribute's name, the lower limit, the upper limit, the beginning date (DD-MM-YYYY HH24:MI:SS.FF) and the ending date (DD-MM-YYYY HH24:MI:SS.FF).
	 * @return The scalar data for the specified attribute<br>
	 * @throws ArchivingException
	 */
	public DbData getAttDataInfThanBetweenDates(String[] argin) throws ArchivingException
	{
        if (canceled) return null;
		String att_name = argin[ 0 ];
		int upper_value = Integer.parseInt(argin[ 1 ]);
		String time_0 = argin[ 2 ];
		String time_1 = argin[ 3 ];
		DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray();
		DbData dbData = new DbData(att_name);
		int[] tfw = getAtt_TFW_Data(att_name);
		dbData.setData_type(tfw[ 0 ]);
		dbData.setData_format(tfw[ 1 ]);
		dbData.setWritable(tfw[ 2 ]);
		String message = "" , reason = "" , desc = "";
		switch ( dbData.getData_format() )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				dvdsa = getAttScalarDataInfThanBetweenDates(att_name , upper_value , time_0 , time_1 , dbData.getWritable());
				dbData.setData(dvdsa);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}

		// Returns the names list
		return dbData;
	}

	public DevVarDoubleStringArray getAttScalarDataInfThanBetweenDates(String att_name , int upper_value , String time_0 , String time_1 , int writable) throws ArchivingException
	{
        if (canceled) return null;
		DevVarDoubleStringArray dvdsa;
		Vector timeVect = new Vector();
		Vector valueRVect = new Vector();
		Vector valueWVect = new Vector();
		String[] timeArr = new String[ 5 ];
		double[] valueRWArr = new double[ 5 ];
		boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
		Statement stmt;
		ResultSet rset;

		// Create and execute the SQL query string
		// Build the query string
		String tableName = getDbSchema() + "." + getTableName(att_name);
		String fields = ro_fields ?
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] ); // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
		String selectField = ro_fields ? ConfigConst.TAB_SCALAR_RO[ 1 ] : ConfigConst.TAB_SCALAR_RW[ 1 ];
		String dateClause = ConfigConst.TAB_SCALAR_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
		// My statement
		// String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField + " < " + lower_value + " OR " + selectField + " > " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";
		String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField + " < " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(query);
            if (canceled) return null;
			if ( ro_fields )
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result));
                    }
				}
			}
			else
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result1 = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result1));
                    }
                    double result2 = rset.getDouble(3);
                    if (rset.wasNull())
                    {
                        valueWVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueWVect.addElement(new Double(result2));
                    }
				}
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataInfThanBetweenDates() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		timeArr = toStringArray(timeVect);
		valueRWArr = toDoubleArray(valueRVect , valueWVect);
		dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
		return dvdsa;
	}

	/**
	 * <b>Description : </b>    	Returns the number data lower than the given value x, and beetwen two dates (date_1 & date_2).
	 *
	 * @param argin The attribute's name, the lower limit, the beginning date (DD-MM-YYYY HH24:MI:SS.FF) and the ending date (DD-MM-YYYY HH24:MI:SS.FF).
	 * @return The number data lower than the given value x, and beetwen two dates (date_1 & date_2).<br>
	 * @throws ArchivingException
	 */
	public int getAttDataInfThanBetweenDatesCount(String[] argin) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;

		String att_name = argin[ 0 ];
		int upper_value = Integer.parseInt(argin[ 1 ]);
		String time_0 = argin[ 2 ];
		String time_1 = argin[ 3 ];

		// Retreive informations on format and writable
		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				valuesCount = getAttScalarDataInfThanBetweenDatesCount(att_name , upper_value , time_0 , time_1 , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		// Returns the number of records
		return valuesCount;
	}

	public int getAttScalarDataInfThanBetweenDatesCount(String att_name , int upper_value , String time_0 , String time_1 , int writable) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		String selectField_0 = "";
		String selectField_1 = "";
		String selectFields = "";
		String dateClause = "";
		String tableName = getDbSchema() + "." + getTableName(att_name);
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			selectField_0 = ConfigConst.TAB_SCALAR_RO[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			selectField_0 = ConfigConst.TAB_SCALAR_RW[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		selectFields = "COUNT(*)";
		dateClause = selectField_0 + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());

		getAttributeDataQuery = "SELECT " + selectFields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField_1 + " < " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				valuesCount = rset.getInt(1);
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataInfThanBetweenDatesCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return valuesCount;
	}

	/**
	 * <b>Description : </b>    	Retrieves the number of records for a given attribute.
	 *
	 * @param att_name The attribute's name.
	 * @return The record's number (int)
	 * @throws ArchivingException
	 */
	public int getAttRecordCount(String att_name) throws ArchivingException
	{
        if (canceled) return 0;
		int count = 0;
		Statement stmt = null;
		ResultSet rset = null;

		// First connect with the database
		if ( getAutoConnect() )
        {
			connect();
        }

		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		getAttributeDataQuery =
		"SELECT COUNT(*)" +
		" FROM " +
		getDbSchema() + "." + getTableName(att_name.trim());
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				count = rset.getInt(1);
			}
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttRecordCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
        finally
        {
            try 
            {
                close ( rset );
                close ( stmt );
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.getAttRecordCount() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
            //Close the connection with the database
            if ( getAutoConnect() )
            {
                close();    
            }
        }

		return count;
	}

	/**
	 * <b>Description : </b>    	Retrieves n last data, for a given scalar attribute.
	 *
	 * @param argin The attribute's name and the number which specifies the number of desired data.
	 * @return The scalar data for the specified attribute<br>
	 * @throws ArchivingException
	 */
	public DbData getAttDataLast_n(String[] argin) throws ArchivingException
	{
        if (canceled) return null;
		String att_name = argin[ 0 ];
		int number = Integer.parseInt(argin[ 1 ]);
		DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray();
		DbData dbData = new DbData(att_name);
		int[] tfw = getAtt_TFW_Data(att_name);
		dbData.setData_type(tfw[ 0 ]);
		dbData.setData_format(tfw[ 1 ]);
		dbData.setWritable(tfw[ 2 ]);
		String message = "" , reason = "" , desc = "";
		switch ( dbData.getData_format() )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				dvdsa = getAttScalarDataLast_n(att_name , number , dbData.getWritable());
				dbData.setData(dvdsa);
				break;
			case AttrDataFormat._SPECTRUM:
				/*message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());*/
			    Vector list = this.getAttSpectrumDataLast_n ( att_name , number , tfw[ 2 ] );
				dbData.setData(list);
				break;
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}

		// Returns the names list
		return dbData;
	}

	public DevVarDoubleStringArray getAttScalarDataLast_n(String att_name , int number , int writable) throws ArchivingException
	{
        if (canceled) return null;
		DevVarDoubleStringArray dvdsa;
		Vector timeVect = new Vector();
		Vector valueRVect = new Vector();
		Vector valueWVect = new Vector();
		String[] timeArr = new String[ 5 ];
		double[] valueRWArr = new double[ 5 ];
		boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
		Statement stmt;
		ResultSet rset;
		int data_type = getAtt_TFW_Data(att_name)[0];

		// Create and execute the SQL query string
		// Build the query string
		String tableName = getDbSchema() + "." + getTableName(att_name);
		String fields = ro_fields ?
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] ); // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
		//String selectField = ro_fields ? ConfigConst.TAB_SCALAR_RO[1] : ConfigConst.TAB_SCALAR_RW[1];
		String orderField = ( ro_fields ? ConfigConst.TAB_SCALAR_RO[ 0 ] : ConfigConst.TAB_SCALAR_RW[ 0 ] );
		// My statement
		// String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField + " < " + lower_value + " OR " + selectField + " > " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";
		String query = "";
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			int total_count = getAttRecordCount(att_name);
			int lim_inf = total_count - number;
			if ( lim_inf < 0 )
			{
				lim_inf = 0;
			}
			int lim_sup = total_count;
			query = "SELECT " + fields + " FROM " + tableName + " LIMIT " + lim_inf + ", " + lim_sup;
		}
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
			query = "SELECT " + fields +
			        " FROM " + "(" + "SELECT * FROM " + tableName + " ORDER BY " + orderField + " DESC" + ")" +
			        " WHERE rownum <= " + number + " ORDER BY  " + orderField + " ASC";
            //query = "SELECT MAX (time) FROM " + tableName;  
		}
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
            rset = stmt.executeQuery(query);
            if (canceled) return null;
			if ( ro_fields )
			{
				while ( rset.next() )
				{
					if (data_type == TangoConst.Tango_DEV_STRING)
					{
						timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                        String result = rset.getString(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement("null");
                        }
                        else
                        {
                            valueRVect.addElement( StringFormater.formatStringToRead(result) );
                        }
                        //System.out.println ( "CLA/getAttScalarDataLast_n/rset.getString(2)/"+rset.getString(2) );
					}
					else
					{
						timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                        double result = rset.getDouble(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                        }
                        else
                        {
                            valueRVect.addElement(new Double(result));
                        }
                        //System.out.println ( "CLA/getAttScalarDataLast_n/rset.getDouble(2)/"+rset.getDouble(2)+"/rset.getString(2)/"+rset.getString(2) );
					}
				}
			}
			else
			{
				while ( rset.next() )
				{
					if (data_type == TangoConst.Tango_DEV_STRING)
					{
						timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                        String result1 = rset.getString(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement("null");
                        }
                        else
                        {
                            valueRVect.addElement( StringFormater.formatStringToRead(result1) );
                        }
                        String result2 = rset.getString(3);
                        if (rset.wasNull())
                        {
                            valueWVect.addElement("null");
                        }
                        else
                        {
                            valueWVect.addElement( StringFormater.formatStringToRead(result2) );
                        }
                        //System.out.println ( "CLA/getAttScalarDataLast_n/rset.getString(2)/"+rset.getString(2)+"/rset.getString(3)/"+rset.getString(3) );
					}
					else
					{
						timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                        double result1 = rset.getDouble(2);
                        if (rset.wasNull())
                        {
                            valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                        }
                        else
                        {
                            valueRVect.addElement(new Double(result1));
                        }
                        double result2 = rset.getDouble(3);
                        if (rset.wasNull())
                        {
                            valueWVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                        }
                        else
                        {
                            valueWVect.addElement(new Double(result2));
                        }
                        
                        //System.out.println ( "CLA/getAttScalarDataLast_n/rset.getDouble(2)/"+rset.getDouble(2)+"/rset.getDouble(3)/"+rset.getDouble(3)+"/rset.getString(2)/"+rset.getString(2)+"/rset.getString(3)/"+rset.getString(3) );
					}
				}
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataLast_n() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		if (data_type == TangoConst.Tango_DEV_STRING)
		{
			// in case of Tango_DEV_STRING, time is coded in double, value in String
			timeArr = toStringArray(timeVect);
			valueRWArr = new double[timeArr.length];
			for (int i = 0; i < timeArr.length; i++)
			{
				valueRWArr[i] = DateUtil.stringToMilli(timeArr[i]);
			}
			timeArr = toStringArray(valueRVect , valueWVect);
			dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
		}
		else
		{
			timeArr = toStringArray(timeVect);
			valueRWArr = toDoubleArray(valueRVect , valueWVect);
			dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
		}
		return dvdsa;
	}
    
    public boolean isLastDataNull(String att_name ) throws ArchivingException
    {
        if (canceled) return true;
        int [] tfw = getAtt_TFW_Data(att_name);
        int format = tfw [1];
        int writable = tfw [2];
        boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
        
        Statement stmt = null;
        ResultSet rset = null;
        //int data_type = tfw [0];
        
        // Create and execute the SQL query string
        // Build the query string
        String tableName = getDbSchema() + "." + getTableName(att_name);
        String fields = ro_fields ?
                        ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
                        ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] );

        //String orderField = ( ro_fields ? ConfigConst.TAB_SCALAR_RO[ 0 ] : ConfigConst.TAB_SCALAR_RW[ 0 ] );

        String query = "SELECT " + fields + " FROM " + tableName /*+ " LIMIT 1"*/;
        
        try
        {
            stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return true;
            rset = stmt.executeQuery(query);
            if (canceled) return true;
            rset.next();
            boolean ret;
            
            if ( ro_fields )
            {
                if ( format == AttrDataFormat._SCALAR )
                {
                ret = ( rset.getString(2) == null || rset.getString(2).equals ( "" ) );
                    //System.out.println ( "CLA/hasDataLast_n/completeName|"+att_name+"|rset.getString(2)|"+rset.getString(2)+"|ret|"+ret );
            }
            else
            {
                    ret = ( rset.getClob(2) == null || rset.getClob(2).equals ( "" ) );
                    //System.out.println ( "CLA/hasDataLast_n/completeName|"+att_name+"|rset.getClob(2)|"+rset.getClob(2)+"|ret|"+ret );
                }
            }
            else
            {
                if ( format == AttrDataFormat._SCALAR )
                {
                ret = ( rset.getString(2) == null || rset.getString(2).equals ( "" ) );
                ret = ret && ( rset.getString(3) == null || rset.getString(3).equals ( "" ) );
                    //System.out.println ( "CLA/hasDataLast_n/completeName|"+att_name+"|rset.getString(2)|"+rset.getString(2)+"|rset.getString(3)|"+rset.getString(3)+"|ret|"+ret );
            }            
                else
                {
                    ret = ( rset.getClob(2) == null || rset.getClob(2).equals ( "" ) );
                    ret = ret && ( rset.getClob(3) == null || rset.getClob(3).equals ( "" ) );
                    //System.out.println ( "CLA/hasDataLast_n/completeName|"+att_name+"|rset.getClob(2)|"+rset.getClob(2)+"|rset.getClob(3)|"+rset.getClob(3)+"|ret|"+ret );
                }
            }            
            
            return ret;
        }
        catch ( SQLException e )
        {
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.hasDataLast_n() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        finally
        {
            try 
            {
                close ( rset );
                close ( stmt );
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.hasDataLast_n() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
    }

	/**
	 * <b>Description : </b>    	Returns the data lower than the given value.
	 *
	 * @param argin The attribute's name and the  upper limit.
	 * @return The scalar data for the specified attribute<br>
	 * @throws ArchivingException
	 */
	public DbData getAttDataInfThan(String[] argin) throws ArchivingException
	{
        if (canceled) return null;
		String att_name = argin[ 0 ];
		int upper_value = Integer.parseInt(argin[ 1 ]);
		DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray();
		DbData dbData = new DbData(att_name);
		int[] tfw = getAtt_TFW_Data(att_name);
		dbData.setData_type(tfw[ 0 ]);
		dbData.setData_format(tfw[ 1 ]);
		dbData.setWritable(tfw[ 2 ]);
		String message = "" , reason = "" , desc = "";
		switch ( dbData.getData_format() )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				dvdsa = getAttScalarDataInfThan(att_name , upper_value , dbData.getWritable());
				dbData.setData(dvdsa);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}

		// Returns the names list
		return dbData;
	}

	public DevVarDoubleStringArray getAttScalarDataInfThan(String att_name , int upper_value , int writable) throws ArchivingException
	{
        if (canceled) return null;
		DevVarDoubleStringArray dvdsa;
		Vector timeVect = new Vector();
		Vector valueRVect = new Vector();
		Vector valueWVect = new Vector();
		String[] timeArr = new String[ 5 ];
		double[] valueRWArr = new double[ 5 ];
		boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
		Statement stmt;
		ResultSet rset;

		// Create and execute the SQL query string
		// Build the query string
		String tableName = getDbSchema() + "." + getTableName(att_name);
		String fields = ro_fields ?
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] ); // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
		String selectField = ro_fields ? ConfigConst.TAB_SCALAR_RO[ 1 ] : ConfigConst.TAB_SCALAR_RW[ 1 ];

		// My statement
		// String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField + " < " + lower_value + " OR " + selectField + " > " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";
		String query =
		        "SELECT " + fields + " FROM " + tableName + " WHERE " + selectField + " < " + upper_value;

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(query);
            if (canceled) return null;
			if ( ro_fields )
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result));
                    }
				}
			}
			else
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result1 = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result1));
                    }
                    double result2 = rset.getDouble(3);
                    if (rset.wasNull())
                    {
                        valueWVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueWVect.addElement(new Double(result2));
                    }
				}
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataInfThan() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		timeArr = toStringArray(timeVect);
		valueRWArr = toDoubleArray(valueRVect , valueWVect);
		dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
		return dvdsa;
	}

	/**
	 * <b>Description : </b>    	Returns the number of data lower than the given value.
	 *
	 * @param argin The attribute's name and the  upper limit.
	 * @return The number of scalar data lower than the given value and for the specified attribute.<br>
	 * @throws ArchivingException
	 */
	public int getAttDataInfThanCount(String[] argin) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;

		String att_name = argin[ 0 ];
		int upper_value = Integer.parseInt(argin[ 1 ]);

		// Retreive informations on format and writable
		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				valuesCount = getAttScalarDataInfThanCount(att_name , upper_value , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		// Returns the number of records
		return valuesCount;
	}

	public int getAttScalarDataInfThanCount(String att_name , int upper_value , int writable) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		//String selectField_0 = "";
		String selectField_1 = "";
		String selectFields = "";

		String tableName = getDbSchema() + "." + getTableName(att_name);
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			//selectField_0 = ConfigConst.TAB_SCALAR_RO[0];
			selectField_1 = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			//selectField_0 = ConfigConst.TAB_SCALAR_RW[0];
			selectField_1 = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		selectFields = "COUNT(*)";
		getAttributeDataQuery =
		"SELECT " + selectFields + " FROM " + tableName + " WHERE " + selectField_1 + " < " + upper_value;

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				valuesCount = rset.getInt(1);
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataInfThanCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return valuesCount;
	}

	/**
	 * <b>Description : </b>    	Returns the data higher than the given value.
	 *
	 * @param argin The attribute's name and the  lower limit
	 * @return The scalar data for the specified attribute<br>
	 * @throws ArchivingException
	 */
	public DbData getAttDataSupThan(String[] argin) throws ArchivingException
	{
        if (canceled) return null;
		String att_name = argin[ 0 ];
		int upper_value = Integer.parseInt(argin[ 1 ]);
		DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray();
		DbData dbData = new DbData(att_name);
		int[] tfw = getAtt_TFW_Data(att_name);
		dbData.setData_type(tfw[ 0 ]);
		dbData.setData_format(tfw[ 1 ]);
		dbData.setWritable(tfw[ 2 ]);
		String message = "" , reason = "" , desc = "";
		switch ( dbData.getData_format() )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				dvdsa = getAttScalarDataSupThan(att_name , upper_value , dbData.getWritable());
				dbData.setData(dvdsa);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}

		// Returns the names list
		return dbData;
	}

	public DevVarDoubleStringArray getAttScalarDataSupThan(String att_name , int lower_value , int writable) throws ArchivingException
	{
        if (canceled) return null;
		DevVarDoubleStringArray dvdsa;
		Vector timeVect = new Vector();
		Vector valueRVect = new Vector();
		Vector valueWVect = new Vector();
		String[] timeArr = new String[ 5 ];
		double[] valueRWArr = new double[ 5 ];
		boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
		Statement stmt;
		ResultSet rset;

		// Create and execute the SQL query string
		// Build the query string
		String tableName = getDbSchema() + "." + getTableName(att_name);
		String fields = ro_fields ?
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] ); // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
		String selectField = ro_fields ? ConfigConst.TAB_SCALAR_RO[ 1 ] : ConfigConst.TAB_SCALAR_RW[ 1 ];

		// My statement
		// String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField + " < " + lower_value + " OR " + selectField + " > " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";
		String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + selectField + " > " + lower_value;

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(query);
            if (canceled) return null;
			if ( ro_fields )
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result));
                    }
				}
			}
			else
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result1 = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result1));
                    }
                    double result2 = rset.getDouble(3);
                    if (rset.wasNull())
                    {
                        valueWVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueWVect.addElement(new Double(result2));
                    }
				}
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataSupThan() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		timeArr = toStringArray(timeVect);
		valueRWArr = toDoubleArray(valueRVect , valueWVect);
		dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
		return dvdsa;
	}

	/**
	 * <b>Description : </b>    	Returns the number of data higher than the given value.
	 *
	 * @param argin The attribute's name and the  lower limit
	 * @return The number of data higher than the given value.<br>
	 * @throws ArchivingException
	 */
	public int getAttDataSupThanCount(String[] argin) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;

		String att_name = argin[ 0 ];
		int lower_value = Integer.parseInt(argin[ 1 ]);

		// Retreive informations on format and writable
		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				valuesCount = getAttScalarDataSupThanCount(att_name , lower_value , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		// Returns the number of records
		return valuesCount;
	}

	public int getAttScalarDataSupThanCount(String att_name , int lower_value , int writable) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		//String selectField_0 = "";
		String selectField_1 = "";
		String selectFields = "";

		String tableName = getDbSchema() + "." + getTableName(att_name);
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			//selectField_0 = ConfigConst.TAB_SCALAR_RO[0];
			selectField_1 = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			//selectField_0 = ConfigConst.TAB_SCALAR_RW[0];
			selectField_1 = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		selectFields = "COUNT(*)";

		getAttributeDataQuery =
		"SELECT " + selectFields + " FROM " + tableName + " WHERE " + selectField_1 + " > " + lower_value;


		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				valuesCount = rset.getInt(1);
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataSupThanCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return valuesCount;
	}

	/**
	 * <b>Description : </b>    	Returns data that are lower than the given value x OR higher than the given value y.
	 *
	 * @param argin The attribute's name, the lower limit and the upper limit
	 * @return The scalar data for the specified attribute<br>
	 * @throws ArchivingException
	 */
	public DbData getAttDataInfOrSupThan(String[] argin) throws ArchivingException
	{
        if (canceled) return null;
		String att_name = argin[ 0 ];
		int lower_value = Integer.parseInt(argin[ 1 ]);
		int upper_value = Integer.parseInt(argin[ 2 ]);
		DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray();
		DbData dbData = new DbData(att_name);
		int[] tfw = getAtt_TFW_Data(att_name);
		dbData.setData_type(tfw[ 0 ]);
		dbData.setData_format(tfw[ 1 ]);
		dbData.setWritable(tfw[ 2 ]);
		String message = "" , reason = "" , desc = "";
		switch ( dbData.getData_format() )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				dvdsa = getAttScalarDataInfOrSupThan(att_name , lower_value , upper_value , dbData.getWritable());
				dbData.setData(dvdsa);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}

		// Returns the names list
		return dbData;
	}

	public DevVarDoubleStringArray getAttScalarDataInfOrSupThan(String att_name , int lower_value , int upper_value , int writable) throws ArchivingException
	{
        if (canceled) return null;
		DevVarDoubleStringArray dvdsa;
		Vector timeVect = new Vector();
		Vector valueRVect = new Vector();
		Vector valueWVect = new Vector();
		String[] timeArr = new String[ 5 ];
		double[] valueRWArr = new double[ 5 ];
		boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
		Statement stmt;
		ResultSet rset;

		// Create and execute the SQL query string
		// Build the query string
		String tableName = getDbSchema() + "." + getTableName(att_name);
		String fields = ro_fields ?
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] ); // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
		String selectField = ro_fields ? ConfigConst.TAB_SCALAR_RO[ 1 ] : ConfigConst.TAB_SCALAR_RW[ 1 ];

		// My statement
		// String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField + " < " + lower_value + " OR " + selectField + " > " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";
		String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + selectField + " < " + lower_value + " OR " + selectField + " > " + upper_value + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(query);
            if (canceled) return null;
			if ( ro_fields )
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result));
                    }
				}
			}
			else
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result1 = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result1));
                    }
                    double result2 = rset.getDouble(3);
                    if (rset.wasNull())
                    {
                        valueWVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueWVect.addElement(new Double(result2));
                    }
				}
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataInfOrSupThan() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		timeArr = toStringArray(timeVect);
		valueRWArr = toDoubleArray(valueRVect , valueWVect);
		dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
		return dvdsa;
	}

	/**
	 * <b>Description : </b>    	Returns the number of data lower than the given value x OR higher than the given value y.
	 *
	 * @param argin The attribute's name, the lower limit and the upper limit
	 * @return The number of scalar data lower than the given value x OR higher than the given value y, associated with their corresponding timestamp <br>
	 * @throws ArchivingException
	 */
	public int getAttDataInfOrSupThanCount(String[] argin) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;

		String att_name = argin[ 0 ];
		int lower_value = Integer.parseInt(argin[ 1 ]);
		int upper_value = Integer.parseInt(argin[ 2 ]);

		// Retreive informations on format and writable
		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				valuesCount = getAttScalarDataInfOrSupThanCount(att_name , lower_value , upper_value , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		// Returns the number of records
		return valuesCount;
	}

	public int getAttScalarDataInfOrSupThanCount(String att_name , int lower_value , int upper_value , int writable) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		//String selectField_0 = "";
		String selectField_1 = "";
		String selectFields = "";

		String tableName = getDbSchema() + "." + getTableName(att_name);
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			//selectField_0 = ConfigConst.TAB_SCALAR_RO[0];
			selectField_1 = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			//selectField_0 = ConfigConst.TAB_SCALAR_RW[0];
			selectField_1 = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		selectFields = "COUNT(*)";

		getAttributeDataQuery =
		"SELECT " + selectFields + " FROM " + tableName + " WHERE " + "(" + selectField_1 + " < " + lower_value + " OR " + selectField_1 + " > " + upper_value + ") ";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				valuesCount = rset.getInt(1);
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataInfOrSupThanCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return valuesCount;
	}

	/**
	 * <b>Description : </b>    	Returns data that are highter than the given value x OR lower than the given value y.
	 *
	 * @param argin The attribute's name, the lower limit and the upper limit
	 * @return The scalar data for the specified attribute<br>
	 * @throws ArchivingException
	 */
	public DbData getAttDataSupAndInfThan(String[] argin) throws ArchivingException
	{
        if (canceled) return null;
		String att_name = argin[ 0 ];
		int lower_value = Integer.parseInt(argin[ 1 ]);
		int upper_value = Integer.parseInt(argin[ 2 ]);
		DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray();
		DbData dbData = new DbData(att_name);
		int[] tfw = getAtt_TFW_Data(att_name);
		dbData.setData_type(tfw[ 0 ]);
		dbData.setData_format(tfw[ 1 ]);
		dbData.setWritable(tfw[ 2 ]);
		String message = "" , reason = "" , desc = "";
		switch ( dbData.getData_format() )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				dvdsa = getAttScalarDataSupAndInfThan(att_name , lower_value , upper_value , dbData.getWritable());
				dbData.setData(dvdsa);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + dbData.getData_format() + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}

		// Returns the names list
		return dbData;
	}

	public DevVarDoubleStringArray getAttScalarDataSupAndInfThan(String att_name , int lower_value , int upper_value , int writable) throws ArchivingException
	{
        if (canceled) return null;
		DevVarDoubleStringArray dvdsa;
		Vector timeVect = new Vector();
		Vector valueRVect = new Vector();
		Vector valueWVect = new Vector();
		String[] timeArr = new String[ 5 ];
		double[] valueRWArr = new double[ 5 ];
		boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
		Statement stmt;
		ResultSet rset;

		// Create and execute the SQL query string
		// Build the query string
		String tableName = getDbSchema() + "." + getTableName(att_name);
		String fields = ro_fields ?
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
		                ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] ); // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
		String selectField = ro_fields ? ConfigConst.TAB_SCALAR_RO[ 1 ] : ConfigConst.TAB_SCALAR_RW[ 1 ];

		// My statement
		// String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + "(" + selectField + " < " + lower_value + " OR " + selectField + " > " + upper_value + ")" + " AND " + "(" + dateClause + ")" + ")";
		String query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + selectField + " > " + lower_value + " AND " + selectField + " < " + upper_value + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(query);
            if (canceled) return null;
			if ( ro_fields )
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result));
                    }
				}
			}
			else
			{
				while ( rset.next() )
				{
					timeVect.addElement(DateUtil.stringToDisplayString(rset.getString(1)));
                    double result1 = rset.getDouble(2);
                    if (rset.wasNull())
                    {
                        valueRVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueRVect.addElement(new Double(result1));
                    }
                    double result2 = rset.getDouble(3);
                    if (rset.wasNull())
                    {
                        valueWVect.addElement(new Double(GlobalConst.NAN_FOR_NULL));
                    }
                    else
                    {
                        valueWVect.addElement(new Double(result2));
                    }
				}
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataSupAndInfThan() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		timeArr = toStringArray(timeVect);
		valueRWArr = toDoubleArray(valueRVect , valueWVect);
		dvdsa = new DevVarDoubleStringArray(valueRWArr , timeArr);
		return dvdsa;
	}

	/**
	 * <b>Description : </b>    	Returns data that are highter than the given value x AND lower than the given value y.
	 *
	 * @param argin The attribute's name, the lower limit and the upper limit
	 * @return The scalar data for the specified attribute<br>
	 * @throws ArchivingException
	 */
	public int getAttDataSupAndInfThanCount(String[] argin) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;

		String att_name = argin[ 0 ];
		int lower_value = Integer.parseInt(argin[ 1 ]);
		int upper_value = Integer.parseInt(argin[ 2 ]);

		// Retreive informations on format and writable
		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				valuesCount = getAttScalarDataSupAndInfThanCount(att_name , lower_value , upper_value , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		// Returns the number of records
		return valuesCount;
	}

	public int getAttScalarDataSupAndInfThanCount(String att_name , int lower_value , int upper_value , int writable) throws ArchivingException
	{
        if (canceled) return 0;
		int valuesCount = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		//String selectField_0 = "";
		String selectField_1 = "";
		String selectFields = "";

		String tableName = getDbSchema() + "." + getTableName(att_name);
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			//selectField_0 = ConfigConst.TAB_SCALAR_RO[0];
			selectField_1 = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			//selectField_0 = ConfigConst.TAB_SCALAR_RW[0];
			selectField_1 = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		selectFields = "COUNT(*)";

		getAttributeDataQuery =
		"SELECT " + selectFields + " FROM " + tableName + " WHERE " + "(" + selectField_1 + " > " + lower_value + " AND " + selectField_1 + " < " + upper_value + ") ";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				valuesCount = rset.getInt(1);
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataSupAndInfThanCount() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return valuesCount;
	}

	/**
	 * <b>Description</b> Returns the lower value recorded for the given attribute
	 *
	 * @param att_name The attribute's name
	 * @return The lower scalar data for the specified attribute
	 * @throws ArchivingException
	 */
	public double getAttDataMin(String att_name) throws ArchivingException
	{
        if (canceled) return 0;
		double min = 0;
		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				min = getAttScalarDataMin(att_name , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		return min;
	}

	public double getAttScalarDataMin(String att_name , int writable) throws ArchivingException
	{
        if (canceled) return 0;
		double min = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String selectField = "";
		String tableName = getDbSchema() + "." + getTableName(att_name.trim());
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			selectField = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			selectField = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		String getAttributeDataQuery = "";
		getAttributeDataQuery = "SELECT MIN(" + selectField + ")" + " FROM " + tableName;

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				min = rset.getDouble(1);
                if (rset.wasNull())
                {
                    min = Double.NaN;
                }
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataMin() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return min;
	}


	/**
	 * <b>Description : </b>  Returns the lower value recorded for the given attribute
	 * and beetwen two dates (date_1 & date_2).
	 *
	 * @param argin The attribute's name, the beginning date (DD-MM-YYYY HH24:MI:SS.FF) and the ending date (DD-MM-YYYY HH24:MI:SS.FF).
	 * @return The lower scalar data for the specified attribute and for the specified period
	 * @throws ArchivingException
	 */
	public double getAttDataMinBetweenDates(String[] argin) throws ArchivingException
	{
        if (canceled) return 0;
		String att_name = argin[ 0 ].trim();
		String time_0 = argin[ 1 ].trim();
		String time_1 = argin[ 2 ].trim();
		double min = 0;

		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				min = getAttScalarDataMinBetweenDates(att_name , time_0 , time_1 , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		return min;
	}

	public double getAttScalarDataMinBetweenDates(String att_name , String time_0 , String time_1 , int writable) throws ArchivingException
	{
        if (canceled) return 0;
		double min = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		String selectField_0 = "";
		String selectField_1 = "";
		String dateClause = "";
		String tableName = getDbSchema() + "." + getTableName(att_name);
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			selectField_0 = ConfigConst.TAB_SCALAR_RO[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			selectField_0 = ConfigConst.TAB_SCALAR_RW[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		dateClause = selectField_0 + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());

		getAttributeDataQuery =
		"SELECT MIN(" + selectField_1 + ")" + " FROM " + tableName + " WHERE " + "(" + dateClause + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				min = rset.getDouble(1);
                if (rset.wasNull())
                {
                    min = Double.NaN;
                }
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataMinBetweenDates() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Returns the names list
		return min;
	}

	/**
	 * <b>Description : </b>  Returns the biggest value generated by the given attribute
	 *
	 * @param att_name The attribute's name
	 * @return The biggest value generated by the attribute
	 * @throws ArchivingException
	 */
	public double getAttDataMax(String att_name) throws ArchivingException
	{
        if (canceled) return 0;
		double max = 0;
		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				max = getAttScalarDataMax(att_name , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		return max;
	}

	public double getAttScalarDataMax(String att_name , int writable) throws ArchivingException
	{
        if (canceled) return 0;
		double max = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String selectField = "";
		String tableName = getDbSchema() + "." + getTableName(att_name.trim());
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			selectField = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			selectField = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		String getAttributeDataQuery = "";
		getAttributeDataQuery = "SELECT MAX(" + selectField + ")" + " FROM " + tableName;

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				max = rset.getDouble(1);
                if (rset.wasNull())
                {
                    max = Double.NaN;
                }
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataMax() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return max;
	}

	/**
	 * <b>Description : </b>  Returns the upper value recorded for the given attribute during a given period.
	 * and beetwen two dates (date_1 & date_2).
	 *
	 * @param argin The attribute's name, the beginning date (DD-MM-YYYY HH24:MI:SS.FF) and the ending date (DD-MM-YYYY HH24:MI:SS.FF).
	 * @return The upper scalar data for the specified attribute and for the specified period.
	 * @throws ArchivingException
	 */
	public double getAttDataMaxBetweenDates(String[] argin) throws ArchivingException
	{
        if (canceled) return 0;
		String att_name = argin[ 0 ].trim();
		String time_0 = argin[ 1 ].trim();
		String time_1 = argin[ 2 ].trim();
		double max = 0;

		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				max = getAttScalarDataMaxBetweenDates(att_name , time_0 , time_1 , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		return max;
	}

	public double getAttScalarDataMaxBetweenDates(String att_name , String time_0 , String time_1 , int writable) throws ArchivingException
	{
        if (canceled) return 0;
		double max = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		String selectField_0 = "";
		String selectField_1 = "";
		String dateClause = "";
		String tableName = getDbSchema() + "." + getTableName(att_name);
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			selectField_0 = ConfigConst.TAB_SCALAR_RO[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			selectField_0 = ConfigConst.TAB_SCALAR_RW[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		dateClause = selectField_0 + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());

		getAttributeDataQuery =
		"SELECT MAX(" + selectField_1 + ")" + " FROM " + tableName + " WHERE " + "(" + dateClause + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return 0;
			while ( rset.next() )
			{
				max = rset.getDouble(1);
                if (rset.wasNull())
                {
                    max = Double.NaN;
                }
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataMaxBetweenDates() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Returns the names list
		return max;
	}

	/**
	 * <b>Description : </b>  Returns the mean value for the given attribute
	 *
	 * @param att_name The attribute's name
	 * @return The mean scalar data for the specified attribute
	 * @throws ArchivingException
	 */
	public double getAttDataAvg(String att_name) throws ArchivingException
	{
        if (canceled) return 0;
		double avg = 0;
		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				avg = getAttScalarDataAvg(att_name , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		return avg;
	}

	public double getAttScalarDataAvg(String att_name , int writable) throws ArchivingException
	{
        if (canceled) return Double.NaN;
		//System.out.println("DataBaseApi.getAttDataAvg");
		double avg = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		String selectField = "";
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			selectField = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			selectField = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		getAttributeDataQuery =
		"SELECT AVG(" + selectField + ")" + " FROM " + getDbSchema() + "." + getTableName(att_name.trim());

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return Double.NaN;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return Double.NaN;
			while ( rset.next() )
			{
				avg = rset.getDouble(1);
                if (rset.wasNull()) avg = Double.NaN;
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataAvg() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return avg;
	}

	/**
	 * <b>Description : </b>    	Returns the mean value for the given attribute and during a given period.
	 * and beetwen two dates (date_1 & date_2).
	 *
	 * @param argin The attribute's name, the beginning date (DD-MM-YYYY HH24:MI:SS.FF) and the ending date (DD-MM-YYYY HH24:MI:SS.FF).
	 * @return The mean scalar data for the specified attribute and for the specified period.
	 * @throws ArchivingException
	 */
	public double getAttDataAvgBetweenDates(String[] argin) throws ArchivingException
	{
        if (canceled) return Double.NaN;
		String att_name = argin[ 0 ].trim();
		String time_0 = argin[ 1 ].trim();
		String time_1 = argin[ 2 ].trim();
		double avg = 0;
		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				avg = getAttScalarDataAvgBetweenDates(att_name , time_0 , time_1 , writable);
				break;
			case AttrDataFormat._SPECTRUM:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Spectrum) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Scalar ";
				desc = "The attribute format is  not Scalar : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		return avg;
	}

	public double getAttScalarDataAvgBetweenDates(String att_name , String time_0 , String time_1 , int writable) throws ArchivingException
	{
        if (canceled) return Double.NaN;
		double avg = 0;
		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		String selectField_0 = "";
		String selectField_1 = "";
		String dateClause = "";
		String tableName = getDbSchema() + "." + getTableName(att_name);
		if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
		{
			selectField_0 = ConfigConst.TAB_SCALAR_RO[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RO[ 1 ];
		}
		else
		{ // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
			selectField_0 = ConfigConst.TAB_SCALAR_RW[ 0 ];
			selectField_1 = ConfigConst.TAB_SCALAR_RW[ 1 ];
		}
		dateClause = selectField_0 + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());

		getAttributeDataQuery =
		"SELECT AVG(" + selectField_1 + ")" + " FROM " + tableName + " WHERE " + "(" + dateClause + ")";

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return Double.NaN;
			rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return Double.NaN;
			while ( rset.next() )
			{
				avg = rset.getDouble(1);
                if (rset.wasNull()) avg = Double.NaN;
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
            if (canceled) return Double.NaN;
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataAvgBetweenDates() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Returns the names list
		return avg;
	}

	public Vector getAttSpectrumDataMySQL(String att_name) throws ArchivingException
	{
        if (canceled) return null;
//		System.out.println ( "CLA/getAttSpectrumData2/---------------1" );
	    Vector my_spectrumS = new Vector();

		int writable = getAttDataWritable(att_name);
		int data_format = getAttDataFormat(att_name);
		int data_type = getAtt_TFW_Data(att_name)[0];
		

		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		String selectField_0 = "";
		String selectField_1 = "";
		String selectField_2 = "";
		String selectField_3 = null;
		String selectFields = "";
		String message = "" , reason = "" , desc = "";
		//System.out.println ( "CLA/getAttSpectrumData2/---------------2" );
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Spectrum ";
				desc = "The attribute format is  not Spectrum : " + data_format + " (Scalar) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._SPECTRUM:
				//System.out.println ( "CLA/getAttSpectrumData2/---------------3" );
				String tableName = getDbSchema() + "." + getTableName(att_name);
				//System.out.println ( "CLA/getAttSpectrumData2/tableName/"+tableName+"/" );
				boolean isBothReadAndWrite = !( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE ); 
				if ( ! isBothReadAndWrite )
				{
					selectField_0 = ConfigConst.TAB_SPECTRUM_RO[ 0 ];
					selectField_1 = ConfigConst.TAB_SPECTRUM_RO[ 1 ];
					selectField_2 = ConfigConst.TAB_SPECTRUM_RO[ 2 ];
				}
				else 
				{
					selectField_0 = ConfigConst.TAB_SPECTRUM_RW[ 0 ];
					selectField_1 = ConfigConst.TAB_SPECTRUM_RW[ 1 ];
					selectField_2 = ConfigConst.TAB_SPECTRUM_RW[ 2 ];
					selectField_3 = ConfigConst.TAB_SPECTRUM_RW[ 3 ];
				}
				selectFields = toDbTimeFieldString(selectField_0) + ", " + selectField_1 + ", " + selectField_2;
				//System.out.println ( "CLA/getAttSpectrumData2/---------------4" );
				// todo Correct the BUG on the oracle side
				if ( isBothReadAndWrite )
				{
				    selectFields += ", " + selectField_3;
				}


				getAttributeDataQuery =
				"SELECT " + selectFields + " FROM " + tableName;
				try
				{
				    //System.out.println ( "CLA/getAttSpectrumData2/---------------5" );
				    stmt = dbconn.createStatement();
                    lastStatement = stmt;
                    if (canceled) return null;
					rset = stmt.executeQuery(getAttributeDataQuery);
                    if (canceled) return null;
					//System.out.println ( "CLA/getAttSpectrumData2/---------------6" );
                    while ( rset.next() )
                    {
                        SpectrumEvent_RO spectrumEvent_ro = new SpectrumEvent_RO();
                        SpectrumEvent_RW spectrumEvent_rw = new SpectrumEvent_RW();
                        // Timestamp
                        try
                        {
                            spectrumEvent_ro.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                            spectrumEvent_rw.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                        }
                        catch (Exception e)
                        {
                            if (canceled) return null;
                        }

                        // Dim
                        if (canceled) return null;
                        int dim_x = 0;
                        try
                        {
                            dim_x = rset.getInt(2);
                        }
                        catch (Exception e)
                        {
                            if (canceled) return null;
                        }
                        spectrumEvent_ro.setDim_x(dim_x);
                        spectrumEvent_rw.setDim_x(dim_x);
                        // Value
                        if (canceled) return null;
                        String valueReadSt = null;
                        try
                        {
                            valueReadSt = rset.getString(3);
                            if (rset.wasNull())
                            {
                                valueReadSt = "null";
                            }
                        }
                        catch (Exception e)
                        {
                            if (canceled) return null;
                        }
                        String valueWriteSt = null;
                        if ( isBothReadAndWrite )
                        {
                            try
                            {
                                valueWriteSt = rset.getString(4);
                                if (rset.wasNull())
                                {
                                    valueWriteSt = "null";
                                }
                            }
                            catch (Exception e)
                            {
                                if (canceled) return null;
                            }
                        }
                        Object value = getSpectrumValue(valueReadSt, valueWriteSt, data_type);
                        if (isBothReadAndWrite)
                        {
                            spectrumEvent_rw.setValue(value);
                            my_spectrumS.add(spectrumEvent_rw);
                        }
                        else
                        {
                            spectrumEvent_ro.setValue(value);
                            my_spectrumS.add(spectrumEvent_ro);
                        }
                        spectrumEvent_ro = null;
                        spectrumEvent_rw = null;
                        value = null;
                        if (canceled) return null;
                    }

					close ( stmt );
				}
				catch ( SQLException e )
				{
				    //System.out.println ( "CLA/getAttSpectrumData2/---------------9" );
				    e.printStackTrace();
				    
				    if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
						message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
					else
						message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

					String _reason = GlobalConst.QUERY_FAILURE;
					String _desc = "Failed while executing DataBaseApi.getAttSpectrumData() method...";
					throw new ArchivingException(message , _reason , ErrSeverity.WARN , _desc , this.getClass().getName() , e);
				}
				break;
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Spectrum ";
				desc = "The attribute format is  not Spectrum : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		//System.out.println ( "CLA/getAttSpectrumData2/---------------10" );
		return my_spectrumS;
	}
	
	public Vector getAttSpectrumDataOracle(String att_name) throws ArchivingException
	{
        if (canceled) return null;
//		System.out.println ( "CLA/getAttSpectrumData2/---------------1" );
	    Vector my_spectrumS = new Vector();

		int writable = getAttDataWritable(att_name);
		int data_format = getAttDataFormat(att_name);
		int data_type = getAtt_TFW_Data(att_name)[0];

		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		String selectField_0 = "";
		String selectField_1 = "";
		String selectField_2 = "";
		String selectField_3 = null;
		String selectFields = "";
		String message = "" , reason = "" , desc = "";
		//System.out.println ( "CLA/getAttSpectrumData2/---------------2" );
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Spectrum ";
				desc = "The attribute format is  not Spectrum : " + data_format + " (Scalar) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._SPECTRUM:
			    //System.out.println ( "CLA/getAttSpectrumData2/---------------3" );
				String tableName = getDbSchema() + "." + getTableName(att_name);
			//System.out.println ( "CLA/getAttSpectrumData2/tableName/"+tableName+"/" );
				boolean isBothReadAndWrite = !( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE ); 
				if ( ! isBothReadAndWrite )
				{
					selectField_0 = ConfigConst.TAB_SPECTRUM_RO[ 0 ];
					selectField_1 = ConfigConst.TAB_SPECTRUM_RO[ 1 ];
					selectField_2 = ConfigConst.TAB_SPECTRUM_RO[ 2 ];
				}
				else 
				{
					selectField_0 = ConfigConst.TAB_SPECTRUM_RW[ 0 ];
					selectField_1 = ConfigConst.TAB_SPECTRUM_RW[ 1 ];
					selectField_2 = ConfigConst.TAB_SPECTRUM_RW[ 2 ];
					selectField_3 = ConfigConst.TAB_SPECTRUM_RW[ 3 ];
				}
				selectFields = toDbTimeFieldString(selectField_0) + ", " + selectField_1 + ", " + selectField_2;
				//System.out.println ( "CLA/getAttSpectrumData2/---------------4" );
				// todo Correct the BUG on the oracle side
				if ( isBothReadAndWrite )
				{
				    selectFields += ", " + selectField_3;
				}


				getAttributeDataQuery =
				"SELECT " + selectFields + " FROM " + tableName;
				try
				{
				    //System.out.println ( "CLA/getAttSpectrumData2/---------------5" );
				    stmt = dbconn.createStatement();
                    lastStatement = stmt;
                    if (canceled) return null;
					rset = stmt.executeQuery(getAttributeDataQuery);
                    if (canceled) return null;
					//System.out.println ( "CLA/getAttSpectrumData2/---------------6" );
					while ( rset.next() )
					{
					    //System.out.println ( "CLA/getAttSpectrumData2/--------------7" );
					    SpectrumEvent_RO spectrumEvent_ro = new SpectrumEvent_RO();
					    SpectrumEvent_RW spectrumEvent_rw = new SpectrumEvent_RW();
						// Timestamp
                        String rawDate = rset.getString ( 1 );
                        long milliDate;
                        
                        try
                        {
                            milliDate = DateUtil.stringToMilli ( rawDate );
                        }
                        catch ( Exception e )
                        {
                            e.printStackTrace();

                            String _reason = "FAILED TO PARSE DATE|"+rawDate+"|";
                            message= _reason;
                            String _desc = "Failed while executing DataBaseApi.getAttSpectrumData() method...";
                            throw new ArchivingException(message , _reason , ErrSeverity.WARN , _desc , this.getClass().getName() , e);
                        }
                        
                        spectrumEvent_ro.setTimeStamp ( milliDate );
                        spectrumEvent_rw.setTimeStamp ( milliDate );

						// Dim
                        int dim_x = 0;
                        try
                        {
                            dim_x = rset.getInt(2);
                        }
                        catch (Exception e)
                        {
                            if (canceled) return null;
                        }
						spectrumEvent_ro.setDim_x(dim_x);
						spectrumEvent_rw.setDim_x(dim_x);
						// Value
						Clob readClob = null;
						String readString = null;

                        try
                        {
                            readClob = rset.getClob ( 3 );
                            if (rset.wasNull())
                            {
                                readString = "null";
                            }
                            else
                            {
                                readString = readClob.getSubString ( 1 , (int) readClob.length() );
                            }
                        }
                        catch(Exception e)
                        {
                            if (canceled) return null;
                        }
						
						Clob writeClob = null;
						String writeString = null;
						if ( isBothReadAndWrite )
						{
                            try
                            {
                                writeClob = rset.getClob ( 4 );
                                if (rset.wasNull())
                                {
                                    writeString = "null";
                                }
                                else
                                {
                                    writeString = writeClob.getSubString ( 1 , (int) writeClob.length() );
                                }
                            }
                            catch(Exception e)
                            {
                                if (canceled) return null;
                            }
						}

                        Object value = getSpectrumValue(readString, writeString, data_type);
                        if (isBothReadAndWrite)
                        {
                            spectrumEvent_rw.setValue(value);
                            my_spectrumS.add(spectrumEvent_rw);
                        }
                        else
                        {
                            spectrumEvent_ro.setValue(value);
                            my_spectrumS.add(spectrumEvent_ro);
                        }
                        spectrumEvent_ro = null;
                        spectrumEvent_rw = null;
                        value = null;
                        if (canceled) return null;
					}

					close ( stmt );
				}
				catch ( SQLException e )
				{
				    //System.out.println ( "CLA/getAttSpectrumData2/---------------9" );
				    e.printStackTrace();
				    
				    if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
						message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
					else
						message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

					String _reason = GlobalConst.QUERY_FAILURE;
					String _desc = "Failed while executing DataBaseApi.getAttSpectrumData() method...";
					throw new ArchivingException(message , _reason , ErrSeverity.WARN , _desc , this.getClass().getName() , e);
				}
				break;
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Spectrum ";
				desc = "The attribute format is  not Spectrum : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		//System.out.println ( "CLA/getAttSpectrumData2/---------------10" );
		return my_spectrumS;
	}
    
    public Vector getAttImageData(String att_name ) throws ArchivingException
    {
        if (canceled) return null;
        if ( db_type == ConfigConst.BD_MYSQL )
        {
            return getAttImageDataMySQL(att_name);
        }
        else 
        {
            return getAttImageDataOracle(att_name);
        }
    }

    public double [][] getAttImageDataForDate(String att_name , String time ) throws ArchivingException
    {
        if (canceled) return null;
        Statement stmt;
        ResultSet rset;
        // Create and execute the SQL query string
        // Build the query string
        String query = "";
        String selectField_0 = "";
        String selectFields = "";
        String dateClause = "";
        String tableName = "";
        tableName = getDbSchema() + "." + getTableName(att_name);
        
        selectField_0 = ConfigConst.TAB_IMAGE_RO[ 3 ];
        String dateField = ConfigConst.TAB_IMAGE_RO [ 0 ];
        selectFields = selectField_0;
        
        dateClause = dateField + " = " + "'" + time.trim() + "'";
        query = "SELECT " + selectFields + " FROM " + tableName + " WHERE " + "(" + dateClause + ")";
        //System.out.println ( "CLA/DataBaseApi/getAttImageDataForDate/query/"+query+"/" );
        
        double [][]  dvalueArr  = null;

        try
        {
            stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
            rset = stmt.executeQuery(query);
            if (canceled) return null;

            if ( !rset.next() )
            {
                return null;
            }

            // Value
            String valueReadSt = null;
            StringTokenizer stringTokenizerReadRows = null;
            if ( rset.getObject(1) != null )
            {
                valueReadSt = rset.getString(1);
                if (rset.wasNull())
                {
                    valueReadSt = "null";
                }
                stringTokenizerReadRows = new StringTokenizer(valueReadSt , GlobalConst.CLOB_SEPARATOR_IMAGE_ROWS);
            }

            int rowIndex = 0;
            int numberOfRows = 0;
            int numberOfCols = 0;

            if (stringTokenizerReadRows != null)
            {
                numberOfRows = stringTokenizerReadRows.countTokens (); 
                dvalueArr = new double [numberOfRows][];

                while ( stringTokenizerReadRows.hasMoreTokens() )
                {
                    String currentRowRead = stringTokenizerReadRows.nextToken();
                    if ( currentRowRead == null || currentRowRead.trim().equals ( "" ) )
                    {
                        break;
                    }
                
                    StringTokenizer stringTokenizerReadCols = new StringTokenizer(currentRowRead , GlobalConst.CLOB_SEPARATOR_IMAGE_COLS);
                    numberOfCols = stringTokenizerReadCols.countTokens ();
                    double [] currentRow = new double [ numberOfCols ]; 
                    int colIndex = 0;
                
                    while ( stringTokenizerReadCols.hasMoreTokens() )
                    {
                        String currentValRead = stringTokenizerReadCols.nextToken();
                        if ( currentValRead == null )
                        {
                            break;
                        }
                        currentValRead = currentValRead.trim ();
                        if ( currentValRead.equals ( "" ) )
                        {
                            break;
                        }
                        
                        currentRow [ colIndex ] = Double.parseDouble(currentValRead);
                        colIndex++;
                    }
                    
                    dvalueArr [ rowIndex ] = currentRow;
                    rowIndex++;
                }
            }
            close ( stmt );
        }
        catch ( SQLException e )
        {
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.getAttSpectrumDataBetweenDatesMySql() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        return dvalueArr;
    }

    // todo mettre ent�te
    public Vector getAttImageDataBetweenDates(String[] argin) throws ArchivingException
    {
        if (canceled) return null;
        String att_name = argin[ 0 ].trim();
        String time_0 = argin[ 1 ].trim();
        String time_1 = argin[ 2 ].trim();
        int[] tfw = getAtt_TFW_Data(att_name);
        Vector my_imageS = new Vector();
        int data_format = getAttDataFormat(att_name);
        String message = "" , reason = "" , desc = "";
        switch ( data_format )
        {  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
            case AttrDataFormat._SCALAR:
                message = "Failed retrieving data ! ";
                reason = "The attribute should be Image ";
                desc = "The attribute format is  not Image : " + data_format + " (Scalar) !!";
                throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
            case AttrDataFormat._SPECTRUM:
                message = "Failed retrieving data ! ";
                reason = "The attribute should be Image ";
                desc = "The attribute format is  not Image : " + data_format + " (Spectrum) !!";
                throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
            case AttrDataFormat._IMAGE:
                my_imageS = getAttImageDataBetweenDates(att_name , time_0 , time_1, tfw[ 0 ],SamplingType.getSamplingType ( SamplingType.ALL ));
                break;
        }
        return my_imageS;
    }
    
    public Vector getAttImageDataBetweenDates(String att_name , String time_0 , String time_1, int data_type, SamplingType samplingFactor) throws ArchivingException
    {
        if (canceled) return null;
        if ( db_type == ConfigConst.BD_MYSQL )
        {
            return getAttImageDataBetweenDatesMySql(att_name , time_0 , time_1, data_type , samplingFactor );
        }
        else  
        {
            return getAttImageDataBetweenDatesOracle(att_name , time_0 , time_1, data_type , samplingFactor);
        }
    }
    
    public Vector getAttImageDataOracle(String att_name) throws ArchivingException
    {
        if (canceled) return null;
//      System.out.println ( "CLA/getAttSpectrumData2/---------------1" );
        Vector my_imageS = new Vector();

        int writable = getAttDataWritable(att_name);
        int data_format = getAttDataFormat(att_name);
        int data_type = getAtt_TFW_Data(att_name)[0];

        Statement stmt;
        ResultSet rset;
        // Create and execute the SQL query string
        // Build the query string
        String getAttributeDataQuery = "";
        String selectField_0 = "";
        String selectField_1 = "";
        String selectField_2 = "";
        String selectField_3 = "";
        String selectField_4 = null;
        String selectFields = "";
        String message = "" , reason = "" , desc = "";
        //System.out.println ( "CLA/getAttSpectrumData2/---------------2" );
        switch ( data_format )
        {  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
            case AttrDataFormat._SCALAR:
                message = "Failed retrieving data ! ";
                reason = "The attribute should be Image ";
                desc = "The attribute format is  not Image : " + data_format + " (Scalar) !!";
                throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
            case AttrDataFormat._SPECTRUM:
                message = "Failed retrieving data ! ";
                reason = "The attribute should be Image ";
                desc = "The attribute format is  not Image : " + data_format + " (Spectrum) !!";
                throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
            case AttrDataFormat._IMAGE:
                String tableName = getDbSchema() + "." + getTableName(att_name);
                //System.out.println ( "CLA/getAttSpectrumData2/tableName/"+tableName+"/" );
                    boolean isBothReadAndWrite = !( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE ); 
                    if ( ! isBothReadAndWrite )
                    {
                        selectField_0 = ConfigConst.TAB_IMAGE_RO[ 0 ];
                        selectField_1 = ConfigConst.TAB_IMAGE_RO[ 1 ];
                        selectField_2 = ConfigConst.TAB_IMAGE_RO[ 2 ];
                        selectField_3 = ConfigConst.TAB_IMAGE_RO[ 3 ];
                    }
                    else 
                    {
                        selectField_0 = ConfigConst.TAB_IMAGE_RW[ 0 ];
                        selectField_1 = ConfigConst.TAB_IMAGE_RW[ 1 ];
                        selectField_2 = ConfigConst.TAB_IMAGE_RW[ 2 ];
                        selectField_3 = ConfigConst.TAB_IMAGE_RW[ 3 ];
                        selectField_4 = ConfigConst.TAB_IMAGE_RW[ 4 ];
                    }
                    selectFields = toDbTimeFieldString(selectField_0) + ", " + selectField_1 + ", " + selectField_2 + ", " + selectField_3;
                    if ( isBothReadAndWrite )
                    {
                        selectFields += ", " + selectField_4;
                    }


                    getAttributeDataQuery =
                    "SELECT " + selectFields + " FROM " + tableName;
                    try
                    {
                        //System.out.println ( "CLA/getAttSpectrumData2/---------------5" );
                        stmt = dbconn.createStatement();
                        lastStatement = stmt;
                        if (canceled) return null;
                        rset = stmt.executeQuery(getAttributeDataQuery);
                        if (canceled) return null;
                        //System.out.println ( "CLA/getAttSpectrumData2/---------------6" );
                        while ( rset.next() )
                        {
                            //System.out.println ( "CLA/getAttSpectrumData2/--------------7" );
                            ImageEvent_RO imageEvent_ro = new ImageEvent_RO();
                            //SpectrumEvent_RW spectrumEvent_rw = new SpectrumEvent_RW();
                            // Timestamp
                            try
                            {
                                imageEvent_ro.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                            }
                            catch (Exception e)
                            {
                                if (canceled) return null;
                            }
                            //spectrumEvent_rw.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));

                            // Dim
                            int dim_x = rset.getInt(2);
                            int dim_y = rset.getInt(3);
                            //System.out.println ( "CLA/getAttSpectrumData/dim_x/"+dim_x+"/" );
                            imageEvent_ro.setDim_x(dim_x);
                            imageEvent_ro.setDim_y(dim_y);
                            //spectrumEvent_rw.setDim_x(dim_x);
                            // Value
                            String readString;
                            Clob readClob = rset.getClob ( 4 );
                            if (rset.wasNull())
                            {
                                readString = "null";
                            }
                            else
                            {
                                readString = readClob.getSubString ( 1 , (int) readClob.length() );
                            }
                            
                            /*Clob writeClob = null;
                            String writeString = null;
                            if ( isBothReadAndWrite )
                            {
                                writeClob = rset.getClob ( 4 );
                                writeString = writeClob.getSubString ( 1 , (int) writeClob.length() );
                            }*/
                            
                            
                            if ( isBothReadAndWrite )
                            {
                                // TODO: not supported yet
                            }
                            else
                            {
                                imageEvent_ro.setValue( getImageValue(readString, data_type) );
                                my_imageS.add(imageEvent_ro);
                            }
                        }

                        close ( stmt );
                    }
                    catch ( SQLException e )
                    {
                        //System.out.println ( "CLA/getAttSpectrumData2/---------------9" );
                        e.printStackTrace();
                        
                        if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                            message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                        else
                            message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                        String _reason = GlobalConst.QUERY_FAILURE;
                        String _desc = "Failed while executing DataBaseApi.getAttImageData() method...";
                        throw new ArchivingException(message , _reason , ErrSeverity.WARN , _desc , this.getClass().getName() , e);
                    }
                    break;
        }
        //System.out.println ( "CLA/getAttSpectrumData2/---------------10" );
        return my_imageS;
    }
    
    public Vector getAttImageDataMySQL(String att_name) throws ArchivingException
    {
        if (canceled) return null;
//      System.out.println ( "CLA/getAttSpectrumData2/---------------1" );
        Vector my_imageS = new Vector();

        int writable = getAttDataWritable(att_name);
        int data_format = getAttDataFormat(att_name);
        int data_type = getAtt_TFW_Data(att_name)[0];

        Statement stmt;
        ResultSet rset;
        // Create and execute the SQL query string
        // Build the query string
        String getAttributeDataQuery = "";
        String selectField_0 = "";
        String selectField_1 = "";
        String selectField_2 = "";
        String selectField_3 = "";
        String selectField_4 = null;
        String selectFields = "";
        String message = "" , reason = "" , desc = "";
        //System.out.println ( "CLA/getAttSpectrumData2/---------------2" );
        switch ( data_format )
        {  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
            case AttrDataFormat._SCALAR:
                message = "Failed retrieving data ! ";
                reason = "The attribute should be Image ";
                desc = "The attribute format is  not Image : " + data_format + " (Scalar) !!";
                throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
            case AttrDataFormat._SPECTRUM:
                message = "Failed retrieving data ! ";
                reason = "The attribute should be Image ";
                desc = "The attribute format is  not Image : " + data_format + " (Spectrum) !!";
                throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
            case AttrDataFormat._IMAGE:
                String tableName = getDbSchema() + "." + getTableName(att_name);
                //System.out.println ( "CLA/getAttSpectrumData2/tableName/"+tableName+"/" );
                    boolean isBothReadAndWrite = !( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE ); 
                    if ( ! isBothReadAndWrite )
                    {
                        selectField_0 = ConfigConst.TAB_IMAGE_RO[ 0 ];
                        selectField_1 = ConfigConst.TAB_IMAGE_RO[ 1 ];
                        selectField_2 = ConfigConst.TAB_IMAGE_RO[ 2 ];
                        selectField_3 = ConfigConst.TAB_IMAGE_RO[ 3 ];
                    }
                    else 
                    {
                        selectField_0 = ConfigConst.TAB_IMAGE_RW[ 0 ];
                        selectField_1 = ConfigConst.TAB_IMAGE_RW[ 1 ];
                        selectField_2 = ConfigConst.TAB_IMAGE_RW[ 2 ];
                        selectField_3 = ConfigConst.TAB_IMAGE_RW[ 3 ];
                        selectField_4 = ConfigConst.TAB_IMAGE_RW[ 4 ];
                    }
                    selectFields = selectField_0 + ", " + selectField_1 + ", " + selectField_2 + ", " + selectField_3;
                    if ( isBothReadAndWrite )
                    {
                        selectFields += ", " + selectField_4;
                    }


                    getAttributeDataQuery =
                    "SELECT " + selectFields + " FROM " + tableName;
                    try
                    {
                        //System.out.println ( "CLA/getAttSpectrumData2/---------------5" );
                        stmt = dbconn.createStatement();
                        lastStatement = stmt;
                        if (canceled) return null;
                        rset = stmt.executeQuery(getAttributeDataQuery);
                        if (canceled) return null;
                        //System.out.println ( "CLA/getAttSpectrumData2/---------------6" );
                        while ( rset.next() )
                        {
                            //System.out.println ( "CLA/getAttSpectrumData2/--------------7" );
                            ImageEvent_RO imageEvent_ro = new ImageEvent_RO();
                            //SpectrumEvent_RW spectrumEvent_rw = new SpectrumEvent_RW();
                            // Timestamp
                            try
                            {
                                imageEvent_ro.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                            }
                            catch (Exception e)
                            {
                                if (canceled) return null;
                            }
                            //spectrumEvent_rw.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));

                            // Dim
                            int dim_x = rset.getInt(2);
                            int dim_y = rset.getInt(3);
                            //System.out.println ( "CLA/getAttSpectrumData/dim_x/"+dim_x+"/" );
                            imageEvent_ro.setDim_x(dim_x);
                            imageEvent_ro.setDim_y(dim_y);
                            //spectrumEvent_rw.setDim_x(dim_x);
                            // Value
                            String valueReadSt = rset.getString(4);
                            if (rset.wasNull())
                            {
                                valueReadSt = "null";
                            }

                            /*String valueWriteSt = null;
                            if ( isBothReadAndWrite )
                            {
                                valueWriteSt = rset.getString(5);    
                            }*/
                            
                            if ( isBothReadAndWrite )
                            {
                                // TODO: not supported yet
                            }
                            else
                            {
                                imageEvent_ro.setValue( getImageValue(valueReadSt, data_type) );
                                my_imageS.add(imageEvent_ro);
                            }
                        }

                        close ( stmt );
                    }
                    catch ( SQLException e )
                    {
                        //System.out.println ( "CLA/getAttSpectrumData2/---------------9" );
                        e.printStackTrace();
                        
                        if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                            message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                        else
                            message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                        String _reason = GlobalConst.QUERY_FAILURE;
                        String _desc = "Failed while executing DataBaseApi.getAttImageData() method...";
                        throw new ArchivingException(message , _reason , ErrSeverity.WARN , _desc , this.getClass().getName() , e);
                    }
                    break;
        }
        //System.out.println ( "CLA/getAttSpectrumData2/---------------10" );
        return my_imageS;
    }

    public Vector getAttImageDataBetweenDatesOracle(String att_name , String time_0 , String time_1, int data_type, SamplingType samplingType) throws ArchivingException
    {
        if (canceled) return null;
        //System.out.println ( "CLA/getAttSpectrumDataBetweenDatesOracle/" );
        
        Vector my_imageS = new Vector();
        int writable = getAttDataWritable(att_name);
        Statement stmt = null;
        ResultSet rset = null;
        // Create and execute the SQL query string
        // Build the query string
        String query = "";
        String selectField_0 = "";
        String selectField_1 = "";
        String selectField_2 = "";
        String selectField_3 = "";
        String selectField_4 = null;
        String selectFields = "";
        String dateClause = "";
        String tableName = "";
        tableName = getDbSchema() + "." + getTableName(att_name) + " T";
        boolean isBothReadAndWrite = !( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE ); 
        
        if ( samplingType.hasSampling () )
        {
            String format = samplingType.getOracleFormat ();
            if ( ! isBothReadAndWrite )
            {
                selectField_0 = toDbTimeFieldString( "T" + "." + ConfigConst.TAB_IMAGE_RO[ 0 ] , format );
                selectField_1 = "AVG (" + "T" + "." + ConfigConst.TAB_IMAGE_RO[ 1 ] + ")";
                selectField_2 = "AVG (" + "T" + "." + ConfigConst.TAB_IMAGE_RO[ 2 ] + ")";
                selectField_3 = "to_clob ( MIN ( to_char (" + "T" + "." + ConfigConst.TAB_IMAGE_RO[ 3 ] + ") ) )";
            }
            else
            {
                selectField_0 = toDbTimeFieldString( "T" + "." + ConfigConst.TAB_IMAGE_RW[ 0 ] , format );
                selectField_1 = "AVG (" + "T" + "." + ConfigConst.TAB_IMAGE_RW[ 1 ] + ")";
                selectField_2 = "AVG (" + "T" + "." + ConfigConst.TAB_IMAGE_RW[ 2 ] + ")";
                selectField_3 = "to_clob ( MIN ( to_char (" + "T" + "." + ConfigConst.TAB_IMAGE_RW[ 3 ] + ") ) )";
                selectField_4 = "to_clob ( MIN ( to_char (" + "T" + "." + ConfigConst.TAB_IMAGE_RW[ 4 ] + ") ) )";
            }

            String whereClause = ConfigConst.TAB_IMAGE_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
            String groupByClause = " GROUP BY " +toDbTimeFieldString(ConfigConst.TAB_IMAGE_RO[ 0 ], format);
            String orderByClause = " ORDER BY " +toDbTimeFieldString(ConfigConst.TAB_IMAGE_RO[ 0 ], format);

            selectFields = selectField_0 + ", " + selectField_1 + ", " + selectField_2 + ", " + selectField_3;
            if ( isBothReadAndWrite )
            {
                selectFields += ", " + selectField_4;
            }
            
            query = "SELECT " + selectFields + " FROM " + tableName + " WHERE " + whereClause + groupByClause + orderByClause;
        }
        else
        {
            if ( ! isBothReadAndWrite )
            {
                selectField_0 = "T" + "." + ConfigConst.TAB_IMAGE_RO[ 0 ];
                selectField_1 = "T" + "." + ConfigConst.TAB_IMAGE_RO[ 1 ];
                selectField_2 = "T" + "." + ConfigConst.TAB_IMAGE_RO[ 2 ];
                selectField_3 = "T" + "." + ConfigConst.TAB_IMAGE_RO[ 3 ];
            }
            else
            {
                selectField_0 = "T" + "." + ConfigConst.TAB_IMAGE_RW[ 0 ];
                selectField_1 = "T" + "." + ConfigConst.TAB_IMAGE_RW[ 1 ];
                selectField_2 = "T" + "." + ConfigConst.TAB_IMAGE_RW[ 2 ];
                selectField_3 = "T" + "." + ConfigConst.TAB_IMAGE_RW[ 3 ];
                selectField_4 = "T" + "." + ConfigConst.TAB_IMAGE_RW[ 4 ];
            }
            selectFields = toDbTimeFieldString(selectField_0) + ", " + selectField_1 + ", " + selectField_2 + ", " + selectField_3;
            if ( isBothReadAndWrite )
            {
                selectFields += ", " + selectField_4;
            }

            dateClause = selectField_0 + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());

            query ="SELECT " + selectFields + " FROM " + tableName + " WHERE " + "(" + dateClause + ")";
        }
        
        try
        {
            stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
            rset = stmt.executeQuery(query);
            if (canceled) return null;
            while ( rset.next() )
            {
                ImageEvent_RO imageEvent_ro = new ImageEvent_RO();

                try
                {
                    imageEvent_ro.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                }
                catch (Exception e)
                {
                    if (canceled) return null;
                }

                int dim_x = rset.getInt(2);
                int dim_y = rset.getInt(3);
                imageEvent_ro.setDim_x(dim_x);
                imageEvent_ro.setDim_y(dim_y);

                Clob readClob = null;
                String readString = null;
                if ( rset.getObject(3) != null )
                {
                    readClob = rset.getClob ( 3 );
                    if (rset.wasNull())
                    {
                        readString = "null";
                    }
                    else
                    {
                        readString = readClob.getSubString ( 1 , (int) readClob.length() );
                    }
                }
                
                /*Clob writeClob = null;
                String writeString = null;
                if ( isBothReadAndWrite )
                {
                    if ( rset.getObject(4) != null )
                    {
                        writeClob = rset.getClob ( 4 );
                        writeString = writeClob.getSubString ( 1 , (int) writeClob.length() );
                    }
                }*/

                if ( isBothReadAndWrite )
                {
                    //TODO : images RW not supported
                }
                else
                {
                    imageEvent_ro.setValue( getImageValue(readString, data_type) );
                    my_imageS.add(imageEvent_ro);
                }
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.getAttImageDataBetweenDatesOracle() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        finally
        {
            try 
            {
                close ( rset );
                close ( stmt );
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.getAttImageDataBetweenDatesOracle() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
        return my_imageS;
    }

    public Vector getAttImageDataBetweenDatesMySql(String att_name , String time_0 , String time_1, int data_type, SamplingType samplingType) throws ArchivingException
    {
        if (canceled) return null;
        //System.out.println ( "CLA/getAttSpectrumDataBetweenDatesOracle/" );
        
        Vector my_imageS = new Vector();
        int writable = getAttDataWritable(att_name);
        Statement stmt = null;
        ResultSet rset = null;
        // Create and execute the SQL query string
        // Build the query string
        String query = "";
        String selectField_0 = "";
        String selectField_1 = "";
        String selectField_2 = "";
        String selectField_3 = "";
        String selectField_4 = null;
        String selectFields = "";
        String dateClause = "";
        String tableName = "";
        tableName = getDbSchema() + "." + getTableName(att_name) + " T";
        boolean isBothReadAndWrite = !( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
        if ( samplingType.hasSampling () )
        {
            String format = samplingType.getMySqlFormat ();
            if ( ! isBothReadAndWrite )
            {
                selectField_0 = toDbTimeFieldString( "T" + "." + ConfigConst.TAB_IMAGE_RO[ 0 ] , format );
                selectField_1 = "AVG (" + "T" + "." + ConfigConst.TAB_IMAGE_RO[ 1 ] + ")";
                selectField_2 = "AVG (" + "T" + "." + ConfigConst.TAB_IMAGE_RO[ 2 ] + ")";
                selectField_3 = "to_clob ( MIN ( to_char (" + "T" + "." + ConfigConst.TAB_IMAGE_RO[ 3 ] + ") ) )";
            }
            else
            {
                selectField_0 = toDbTimeFieldString( "T" + "." + ConfigConst.TAB_IMAGE_RW[ 0 ] , format );
                selectField_1 = "AVG (" + "T" + "." + ConfigConst.TAB_IMAGE_RW[ 1 ] + ")";
                selectField_2 = "AVG (" + "T" + "." + ConfigConst.TAB_IMAGE_RW[ 2 ] + ")";
                selectField_3 = "to_clob ( MIN ( to_char (" + "T" + "." + ConfigConst.TAB_IMAGE_RW[ 3 ] + ") ) )";
                selectField_4 = "to_clob ( MIN ( to_char (" + "T" + "." + ConfigConst.TAB_IMAGE_RW[ 4 ] + ") ) )";
            }

            String whereClause = ConfigConst.TAB_IMAGE_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
            String groupByClause = " GROUP BY " +toDbTimeFieldString(ConfigConst.TAB_IMAGE_RO[ 0 ], format);
            String orderByClause = " ORDER BY " +toDbTimeFieldString(ConfigConst.TAB_IMAGE_RO[ 0 ], format);

            selectFields = selectField_0 + ", " + selectField_1 + ", " + selectField_2 + ", " + selectField_3;
            if ( isBothReadAndWrite )
            {
                selectFields += ", " + selectField_4;
            }
            
            query = "SELECT " + selectFields + " FROM " + tableName + " WHERE " + whereClause + groupByClause + orderByClause;
        }
        else
        {
            if ( ! isBothReadAndWrite )
            {
                selectField_0 = "T" + "." + ConfigConst.TAB_IMAGE_RO[ 0 ];
                selectField_1 = "T" + "." + ConfigConst.TAB_IMAGE_RO[ 1 ];
                selectField_2 = "T" + "." + ConfigConst.TAB_IMAGE_RO[ 2 ];
                selectField_3 = "T" + "." + ConfigConst.TAB_IMAGE_RO[ 3 ];
            }
            else
            {
                selectField_0 = "T" + "." + ConfigConst.TAB_IMAGE_RW[ 0 ];
                selectField_1 = "T" + "." + ConfigConst.TAB_IMAGE_RW[ 1 ];
                selectField_2 = "T" + "." + ConfigConst.TAB_IMAGE_RW[ 2 ];
                selectField_3 = "T" + "." + ConfigConst.TAB_IMAGE_RW[ 3 ];
                selectField_4 = "T" + "." + ConfigConst.TAB_IMAGE_RW[ 4 ];
            }
            selectFields = selectField_0 + ", " + selectField_1 + ", " + selectField_2 + ", " + selectField_3;
            if ( isBothReadAndWrite )
            {
                selectFields += ", " + selectField_4;
            }
    
            dateClause = selectField_0 + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
            query ="SELECT " + selectFields + " FROM " + tableName + " WHERE " + "(" + dateClause + ")";
        }
        
        try
        {
            stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
            rset = stmt.executeQuery(query);
            if (canceled) return null;
            while ( rset.next() )
            {
                ImageEvent_RO imageEvent_ro = new ImageEvent_RO();

                try
                {
                    imageEvent_ro.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                }
                catch (Exception e)
                {
                    if (canceled) return null;
                }

                int dim_x = rset.getInt(2);
                int dim_y = rset.getInt(3);
                imageEvent_ro.setDim_x(dim_x);
                imageEvent_ro.setDim_y(dim_y);

                String valueReadSt = rset.getString(4);
                if (rset.wasNull())
                {
                    valueReadSt = "null";
                }

                /*String valueWriteSt = null;
                if ( isBothReadAndWrite )
                {
                    valueWriteSt = rset.getString(5);    
                }*/

                if ( isBothReadAndWrite )
                {
                    //TODO : images RW not supported
                }
                else
                {
                    imageEvent_ro.setValue( getImageValue(valueReadSt, data_type) );
                    my_imageS.add(imageEvent_ro);
                }
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.getAttImageDataBetweenDatesOracle() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        finally
        {
            try 
            {
                close ( rset );
                close ( stmt );
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.getAttImageDataBetweenDatesOracle() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
        return my_imageS;
    }

    private Object[] getSpectrumValue(String readString, String writeString, int data_type)
    {
        int size = 0;
        StringTokenizer readTokenizer;
        if (readString == null || "".equals(readString) || "null".equals(readString))
        {
            readTokenizer = null;
        }
        else
        {
            readTokenizer = new StringTokenizer ( readString , GlobalConst.CLOB_SEPARATOR );
            size += readTokenizer.countTokens();
        }

        StringTokenizer writeTokenizer; 
        if (writeString == null || "".equals(writeString) || "null".equals(writeString))
        {
            writeTokenizer = null;
        }
        else
        {
            writeTokenizer = new StringTokenizer ( writeString , GlobalConst.CLOB_SEPARATOR );
            size += writeTokenizer.countTokens();
        }

        Double[]  dvalueArr  = null;
        Byte[]    cvalueArr  = null;
        Integer[]     lvalueArr  = null;
        Short[]   svalueArr  = null;
        Boolean[] bvalueArr  = null;
        Float[]   fvalueArr  = null;
        String[]  stvalueArr = null;
        switch (data_type)
        {
            case TangoConst.Tango_DEV_BOOLEAN:
                bvalueArr = new Boolean[ size ];
                break;
            case TangoConst.Tango_DEV_CHAR:
            case TangoConst.Tango_DEV_UCHAR:
                cvalueArr = new Byte[ size ];
                break;
            case TangoConst.Tango_DEV_STATE:
            case TangoConst.Tango_DEV_LONG:
            case TangoConst.Tango_DEV_ULONG:
                lvalueArr = new Integer[ size ];
                break;
            case TangoConst.Tango_DEV_SHORT:
            case TangoConst.Tango_DEV_USHORT:
                svalueArr = new Short[ size ];
                break;
            case TangoConst.Tango_DEV_FLOAT:
                fvalueArr = new Float[ size ];
                break;
            case TangoConst.Tango_DEV_STRING:
                stvalueArr = new String[ size ];
                break;
            case TangoConst.Tango_DEV_DOUBLE:
            default:
                dvalueArr = new Double[ size ];
        }
        int i = 0;

        if (readTokenizer != null)
        {
            while ( readTokenizer.hasMoreTokens() )
            {
                String currentValRead = readTokenizer.nextToken();
                if ( currentValRead == null || currentValRead.trim().equals ( "" ) )
                {
                    break;
                }
                switch (data_type)
                {
                    case TangoConst.Tango_DEV_BOOLEAN:
                        try
                        {
                            if (currentValRead == null || "".equals(currentValRead) || "null".equals(currentValRead) || "NaN".equalsIgnoreCase(currentValRead))
                            {
                                bvalueArr[i] = null;
                            }
                            else
                            {
                                bvalueArr[ i ] = new Boolean( Double.valueOf(currentValRead).intValue() != 0 );
                            }
                        }
                        catch (NumberFormatException n)
                        {
                            bvalueArr[ i ] = new Boolean( "true".equalsIgnoreCase(currentValRead.trim()) );
                        }
                        break;
                    case TangoConst.Tango_DEV_CHAR:
                    case TangoConst.Tango_DEV_UCHAR:
                        try
                        {
                            if (currentValRead == null || "".equals(currentValRead) || "null".equals(currentValRead) || "NaN".equalsIgnoreCase(currentValRead))
                            {
                                cvalueArr[i] = null;
                            }
                            else
                            {
                                cvalueArr[ i ] = Byte.valueOf(currentValRead);
                            }
                        }
                        catch (NumberFormatException n)
                        {
                            cvalueArr[ i ] = new Byte(Double.valueOf(currentValRead).byteValue());
                        }
                        break;
                    case TangoConst.Tango_DEV_STATE:
                    case TangoConst.Tango_DEV_LONG:
                    case TangoConst.Tango_DEV_ULONG:
                        try
                        {
                            if (currentValRead == null || "".equals(currentValRead) || "null".equals(currentValRead) || "NaN".equalsIgnoreCase(currentValRead))
                            {
                                lvalueArr[i] = null;
                            }
                            else
                            {
                                lvalueArr[ i ] = Integer.valueOf(currentValRead);
                            }
                        }
                        catch (NumberFormatException n)
                        {
                            lvalueArr[ i ] = new Integer(Double.valueOf(currentValRead).intValue());
                        }
                        break;
                    case TangoConst.Tango_DEV_SHORT:
                    case TangoConst.Tango_DEV_USHORT:
                        try
                        {
                            if (currentValRead == null || "".equals(currentValRead) || "null".equals(currentValRead) || "NaN".equalsIgnoreCase(currentValRead))
                            {
                                svalueArr[i] = null;
                            }
                            else
                            {
                                svalueArr[ i ] = Short.valueOf(currentValRead);
                            }
                        }
                        catch (NumberFormatException n)
                        {
                            svalueArr[ i ] = new Short(Double.valueOf(currentValRead).shortValue());
                        }
                        break;
                    case TangoConst.Tango_DEV_FLOAT:
                        if (currentValRead == null || "".equals(currentValRead) || "null".equals(currentValRead) || "NaN".equalsIgnoreCase(currentValRead))
                        {
                            fvalueArr[i] = null;
                        }
                        else
                        {
                            fvalueArr[ i ] = Float.valueOf(currentValRead);
                        }
                        break;
                    case TangoConst.Tango_DEV_STRING:
                        if (currentValRead == null || "".equals(currentValRead) || "null".equals(currentValRead) || "NaN".equalsIgnoreCase(currentValRead))
                        {
                            stvalueArr[i] = null;
                        }
                        else
                        {
                            stvalueArr[ i ] = StringFormater.formatStringToRead( new String(currentValRead) );
                        }
                        break;
                    case TangoConst.Tango_DEV_DOUBLE:
                    default:
                        if (currentValRead == null || "".equals(currentValRead) || "null".equals(currentValRead) || "NaN".equalsIgnoreCase(currentValRead))
                        {
                            dvalueArr[i] = null;
                        }
                        else
                        {
                            dvalueArr[ i ] = Double.valueOf(currentValRead);
                        }
                }
                i++;
            }
        }

        if ( writeTokenizer != null )
        {
            while ( writeTokenizer.hasMoreTokens() )
            {
                String currentValWrite = writeTokenizer.nextToken();
                if ( currentValWrite == null || currentValWrite.trim().equals ( "" ) )
                {
                    break;
                }
                switch (data_type)
                {
                    case TangoConst.Tango_DEV_BOOLEAN:
                        try
                        {
                            if (currentValWrite == null || "".equals(currentValWrite) || "null".equals(currentValWrite) || "NaN".equalsIgnoreCase(currentValWrite))
                            {
                                bvalueArr[i] = null;
                            }
                            else
                            {
                                bvalueArr[ i ] = new Boolean( Double.valueOf(currentValWrite).intValue() != 0 );
                            }
                        }
                        catch (NumberFormatException n)
                        {
                            bvalueArr[ i ] = new Boolean( "true".equalsIgnoreCase(currentValWrite.trim()) );
                        }
                        break;
                    case TangoConst.Tango_DEV_CHAR:
                    case TangoConst.Tango_DEV_UCHAR:
                        try
                        {
                            if (currentValWrite == null || "".equals(currentValWrite) || "null".equals(currentValWrite) || "NaN".equalsIgnoreCase(currentValWrite))
                            {
                                cvalueArr[i] = null;
                            }
                            else
                            {
                                cvalueArr[ i ] = Byte.valueOf(currentValWrite);
                            }
                        }
                        catch (NumberFormatException n)
                        {
                            cvalueArr[ i ] = new Byte(Double.valueOf(currentValWrite).byteValue());
                        }
                        break;
                    case TangoConst.Tango_DEV_STATE:
                    case TangoConst.Tango_DEV_LONG:
                    case TangoConst.Tango_DEV_ULONG:
                        try
                        {
                            if (currentValWrite == null || "".equals(currentValWrite) || "null".equals(currentValWrite) || "NaN".equalsIgnoreCase(currentValWrite))
                            {
                                lvalueArr[i] = null;
                            }
                            else
                            {
                                lvalueArr[ i ] = Integer.valueOf(currentValWrite);
                            }
                        }
                        catch (NumberFormatException n)
                        {
                            lvalueArr[ i ] = new Integer(Double.valueOf(currentValWrite).intValue());
                        }
                        break;
                    case TangoConst.Tango_DEV_SHORT:
                    case TangoConst.Tango_DEV_USHORT:
                        try
                        {
                            if (currentValWrite == null || "".equals(currentValWrite) || "null".equals(currentValWrite) || "NaN".equalsIgnoreCase(currentValWrite))
                            {
                                svalueArr[i] = null;
                            }
                            else
                            {
                                svalueArr[ i ] = Short.valueOf(currentValWrite);
                            }
                        }
                        catch (NumberFormatException n)
                        {
                            svalueArr[ i ] = new Short(Double.valueOf(currentValWrite).shortValue());
                        }
                        break;
                    case TangoConst.Tango_DEV_FLOAT:
                        if (currentValWrite == null || "".equals(currentValWrite) || "null".equals(currentValWrite) || "NaN".equalsIgnoreCase(currentValWrite))
                        {
                            fvalueArr[i] = null;
                        }
                        else
                        {
                            fvalueArr[ i ] = Float.valueOf(currentValWrite);
                        }
                        break;
                    case TangoConst.Tango_DEV_STRING:
                        if (currentValWrite == null || "".equals(currentValWrite) || "null".equals(currentValWrite) || "NaN".equalsIgnoreCase(currentValWrite))
                        {
                            stvalueArr[i] = null;
                        }
                        else
                        {
                            stvalueArr[ i ] = StringFormater.formatStringToRead( new String(currentValWrite) );
                        }
                        break;
                    case TangoConst.Tango_DEV_DOUBLE:
                    default:
                        if (currentValWrite == null || "".equals(currentValWrite) || "null".equals(currentValWrite) || "NaN".equalsIgnoreCase(currentValWrite))
                        {
                            dvalueArr[i] = null;
                        }
                        else
                        {
                            dvalueArr[ i ] = Double.valueOf(currentValWrite);
                        }
                }
                i++;
            }
        }

        if (readTokenizer == null && writeTokenizer == null) {
            return null;
        }

        switch (data_type)
        {
            case TangoConst.Tango_DEV_BOOLEAN:
                return bvalueArr;
            case TangoConst.Tango_DEV_CHAR:
            case TangoConst.Tango_DEV_UCHAR:
                return cvalueArr;
            case TangoConst.Tango_DEV_STATE:
            case TangoConst.Tango_DEV_LONG:
            case TangoConst.Tango_DEV_ULONG:
                return lvalueArr;
            case TangoConst.Tango_DEV_SHORT:
            case TangoConst.Tango_DEV_USHORT:
                return svalueArr;
            case TangoConst.Tango_DEV_FLOAT:
                return fvalueArr;
            case TangoConst.Tango_DEV_STRING:
                return stvalueArr;
            case TangoConst.Tango_DEV_DOUBLE:
            default:
                return dvalueArr;
        }
    }

    private Object[][] getImageValue(String dbValue, int data_type)
    {
        if (dbValue == null || "".equals(dbValue) || "null".equals(dbValue))
        {
            return null;
        }
        Object[] valArray = null;
        String value = new String(dbValue);
        value = value.replaceAll("\\[", "");
        value = value.replaceAll("\\]", "");

        StringTokenizer readTokenizer = null;
        int rowSize = 0, colSize = 0;
 
        readTokenizer = new StringTokenizer(value , GlobalConst.CLOB_SEPARATOR_IMAGE_ROWS);
        rowSize = readTokenizer.countTokens();

        if ( readTokenizer != null )
        {
            valArray = new Object[rowSize];
            int i = 0;
            while (readTokenizer.hasMoreTokens())
            {
                valArray[i++] = readTokenizer.nextToken().trim().split(GlobalConst.CLOB_SEPARATOR_IMAGE_COLS);
            }
            if (rowSize > 0)
            {
                colSize = ((String[])valArray[0]).length;
            }
        }
        else return null;

        //System.out.println("rowSize,Colsize:"+rowSize+","+colSize);
        Double[][]  dvalueArr  = null;
        Byte[][]    cvalueArr  = null;
        Integer[][] lvalueArr  = null;
        Short[][]   svalueArr  = null;
        Boolean[][] bvalueArr  = null;
        Float[][]   fvalueArr  = null;
        String[][]  stvalueArr = null;
        switch (data_type)
        {
            case TangoConst.Tango_DEV_BOOLEAN:
                bvalueArr = new Boolean[rowSize][colSize];
                for (int i = 0; i < rowSize; i++)
                {
                    for (int j = 0; j < colSize; j++)
                    {
                        try
                        {
                            if (((String[])valArray[i])[j] == null || "".equals(((String[])valArray[i])[j]) || "null".equals(((String[])valArray[i])[j]) || "NaN".equalsIgnoreCase(((String[])valArray[i])[j]))
                            {
                                bvalueArr[i][j] = null;
                            }
                            else
                            {
                                bvalueArr[i][j] = new Boolean( Double.valueOf( ((String[])valArray[i])[j].trim() ).intValue() != 0 );
                            }
                        }
                        catch (NumberFormatException n)
                        {
                            bvalueArr[i][j] = new Boolean( "true".equalsIgnoreCase(((String[])valArray[i])[j].trim()) );
                        }
                    }
                }
                return bvalueArr;
            case TangoConst.Tango_DEV_CHAR:
            case TangoConst.Tango_DEV_UCHAR:
                cvalueArr = new Byte[rowSize][colSize];
                for (int i = 0; i < valArray.length; i++)
                {
                    for (int j = 0; j < colSize; j++)
                    {
                        try
                        {
                            if (((String[])valArray[i])[j] == null || "".equals(((String[])valArray[i])[j]) || "null".equals(((String[])valArray[i])[j]) || "NaN".equalsIgnoreCase(((String[])valArray[i])[j]))
                            {
                                cvalueArr[i][j] = null;
                            }
                            else
                            {
                                cvalueArr[i][j] = Byte.valueOf(((String[])valArray[i])[j].trim());
                            }
                        }
                        catch (NumberFormatException n)
                        {
                            cvalueArr[i][j] = new Byte(Double.valueOf(((String[])valArray[i])[j].trim()).byteValue());
                        }
                    }
                }
                return cvalueArr;
            case TangoConst.Tango_DEV_STATE:
            case TangoConst.Tango_DEV_LONG:
            case TangoConst.Tango_DEV_ULONG:
                lvalueArr = new Integer[rowSize][colSize];
                for (int i = 0; i < valArray.length; i++)
                {
                    for (int j = 0; j < colSize; j++)
                    {
                        try
                        {
                            if (((String[])valArray[i])[j] == null || "".equals(((String[])valArray[i])[j]) || "null".equals(((String[])valArray[i])[j]) || "NaN".equalsIgnoreCase(((String[])valArray[i])[j]))
                            {
                                lvalueArr[i][j] = null;
                            }
                            else
                            {
                                lvalueArr[i][j] = Integer.valueOf(((String[])valArray[i])[j].trim());
                            }
                        }
                        catch (NumberFormatException n)
                        {
                            lvalueArr[i][j] = new Integer(Double.valueOf(((String[])valArray[i])[j].trim()).intValue());
                        }
                    }
                }
                return lvalueArr;
            case TangoConst.Tango_DEV_SHORT:
            case TangoConst.Tango_DEV_USHORT:
                svalueArr = new Short[rowSize][colSize];
                for (int i = 0; i < valArray.length; i++)
                {
                    for (int j = 0; j < colSize; j++)
                    {
                        try
                        {
                            if (((String[])valArray[i])[j] == null || "".equals(((String[])valArray[i])[j]) || "null".equals(((String[])valArray[i])[j]) || "NaN".equalsIgnoreCase(((String[])valArray[i])[j]))
                            {
                                svalueArr[i][j] = null;
                            }
                            else
                            {
                                svalueArr[i][j] = Short.valueOf(((String[])valArray[i])[j].trim());
                            }
                        }
                        catch (NumberFormatException n)
                        {
                            svalueArr[i][j] = new Short(Double.valueOf(((String[])valArray[i])[j].trim()).shortValue());
                        }
                    }
                }
                return svalueArr;
            case TangoConst.Tango_DEV_FLOAT:
                fvalueArr = new Float[rowSize][colSize];
                for (int i = 0; i < valArray.length; i++)
                {
                    for (int j = 0; j < colSize; j++)
                    {
                        try
                        {
                            if (((String[])valArray[i])[j] == null || "".equals(((String[])valArray[i])[j]) || "null".equals(((String[])valArray[i])[j]) || "NaN".equalsIgnoreCase(((String[])valArray[i])[j]))
                            {
                                fvalueArr[i][j] = null;
                            }
                            else
                            {
                                fvalueArr[i][j] = Float.valueOf(((String[])valArray[i])[j].trim());
                            }
                        }
                        catch (NumberFormatException n)
                        {
                            fvalueArr[i][j] = new Float(Double.valueOf(((String[])valArray[i])[j].trim()).floatValue());
                        }
                    }
                }
                return fvalueArr;
            case TangoConst.Tango_DEV_STRING:
                stvalueArr = new String[rowSize][colSize];
                for (int i = 0; i < valArray.length; i++)
                {
                    for (int j = 0; j < colSize; j++)
                    {
                        if (((String[])valArray[i])[j] == null || "".equals(((String[])valArray[i])[j]) || "null".equals(((String[])valArray[i])[j]) || "NaN".equalsIgnoreCase(((String[])valArray[i])[j]))
                        {
                            stvalueArr[i][j] = null;
                        }
                        else
                        {
                            stvalueArr[i][j] = StringFormater.formatStringToRead( new String(((String[])valArray[i])[j].trim()) );
                        }
                    }
                }
                return stvalueArr;
            case TangoConst.Tango_DEV_DOUBLE:
            default:
                dvalueArr = new Double[rowSize][colSize];
                for (int i = 0; i < valArray.length; i++)
                {
                    for (int j = 0; j < colSize; j++)
                    {
                        if (((String[])valArray[i])[j] == null || "".equals(((String[])valArray[i])[j]) || "null".equals(((String[])valArray[i])[j]) || "NaN".equalsIgnoreCase(((String[])valArray[i])[j]))
                        {
                            dvalueArr[i][j] = null;
                        }
                        else
                        {
                            dvalueArr[i][j] = Double.valueOf( ((String[])valArray[i])[j].trim() );
                        }
                    }
                }
                return dvalueArr;
        }

    }

	public Vector getAttSpectrumDataLast_n(String[] argin) throws ArchivingException
	{
        if (canceled) return null;
		String att_name = argin[ 0 ];
		int number = Integer.parseInt(argin[ 1 ]);
		Vector my_spectrumS = new Vector();
		// Retreive informations on format and writable
		int[] tfw = getAtt_TFW_Data(att_name);
		//int data_type = tfw[0];
		int data_format = tfw[ 1 ];
		int writable = tfw[ 2 ];
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Spectrum ";
				desc = "The attribute format is  not Spectrum : " + data_format + " (Scalar) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._SPECTRUM:
				my_spectrumS = getAttSpectrumDataLast_n(att_name , number , writable);
				break;
			case AttrDataFormat._IMAGE:
                message = "Failed retrieving data ! ";
                reason = "The attribute should be Spectrum ";
				desc = "The attribute format is  not Spectrum : " + data_format + " (Image) !!";
                throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
                }

		// Returns the names list
		return my_spectrumS;
	}

	public Vector getAttSpectrumDataLast_n(String att_name , int number , int writable) throws ArchivingException
	{
        if (canceled) return null;
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			return getAttSpectrumDataLast_n_MySQL(att_name , number , writable);
		}
		else //if (db_type == ConfigConst.BD_ORACLE) {
			return getAttSpectrumDataLast_n_Oracle(att_name , number , writable);
	}

	public Vector getAttSpectrumDataLast_n_MySQL(String att_name , int number , int writable) throws ArchivingException
	{
        if (canceled) return null;
		Vector my_spectrumS = new Vector();
		boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
		int data_type = getAtt_TFW_Data(att_name)[0];

		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string

		String tableName = getDbSchema() + "." + getTableName(att_name);
		String fields = ro_fields ?
		                ( toDbTimeFieldString(ConfigConst.TAB_SPECTRUM_RO[ 0 ]) + ", " + ConfigConst.TAB_SPECTRUM_RO[ 1 ] + ", " + ConfigConst.TAB_SPECTRUM_RO[ 2 ] ) :
		                ( toDbTimeFieldString(ConfigConst.TAB_SPECTRUM_RW[ 0 ]) + ", " + ConfigConst.TAB_SPECTRUM_RW[ 1 ] + ", " + ConfigConst.TAB_SPECTRUM_RW[ 2 ] + ", " + ConfigConst.TAB_SPECTRUM_RW[ 3 ]); // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
		//String selectField = ro_fields ? ConfigConst.TAB_SPECTRUM_RO[1] : ConfigConst.TAB_SPECTRUM_RW[1];

		int total_count = getAttRecordCount(att_name);
		int lim_inf = total_count - number;
		if ( lim_inf < 0 )
		{
			lim_inf = 0;
		}
		int lim_sup = total_count;
		String query = "SELECT " + fields + " FROM " + tableName + " LIMIT " + lim_inf + ", " + lim_sup;

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(query);
            if (canceled) return null;
            while ( rset.next() )
            {
                SpectrumEvent_RO spectrumEvent_ro = new SpectrumEvent_RO();
                SpectrumEvent_RW spectrumEvent_rw = new SpectrumEvent_RW();
                // Timestamp
                try
                {
                    spectrumEvent_ro.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                    spectrumEvent_rw.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                }
                catch (Exception e)
                {
                    if (canceled) return null;
                }

                // Dim
                if (canceled) return null;
                int dim_x = 0;
                try
                {
                    dim_x = rset.getInt(2);
                }
                catch (Exception e)
                {
                    if (canceled) return null;
                }
                spectrumEvent_ro.setDim_x(dim_x);
                spectrumEvent_rw.setDim_x(dim_x);
                // Value
                if (canceled) return null;
                String valueReadSt = null;
                try
                {
                    valueReadSt = rset.getString(3);
                    if (rset.wasNull())
                    {
                        valueReadSt = "null";
                    }
                }
                catch (Exception e)
                {
                    if (canceled) return null;
                }
                String valueWriteSt = null;
                if ( !ro_fields )
                {
                    try
                    {
                        valueWriteSt = rset.getString(4);
                        if (rset.wasNull())
                        {
                            valueWriteSt = "null";
                        }
                    }
                    catch (Exception e)
                    {
                        if (canceled) return null;
                    }
                }
                Object value = getSpectrumValue(valueReadSt, valueWriteSt, data_type);
                if (!ro_fields)
                {
                    spectrumEvent_rw.setValue(value);
                    my_spectrumS.add(spectrumEvent_rw);
                }
                else
                {
                    spectrumEvent_ro.setValue(value);
                    my_spectrumS.add(spectrumEvent_ro);
                }
                spectrumEvent_ro = null;
                spectrumEvent_rw = null;
                value = null;
                if (canceled) return null;
            }

			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttSpectrumDataLast_n() method..." +
			              "\r\n" + query;
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		/*if ( dimVect.size() != 0 )
		{
			for ( int i = 0 ; i < dimVect.size() ; i++ )
			{
				String[] timeArr = new String[ 2 ];
				timeArr[ 0 ] = ( String ) timeVect.get(i);
				timeArr[ 1 ] = ( ( Integer ) dimVect.elementAt(i) ).toString();

				Vector spect_valueV = new Vector();
				String spect_valueS = ( ( String ) svalueVect.get(i) ).toString();
				StringTokenizer st = new StringTokenizer(spect_valueS , "[],");
				while ( st.hasMoreTokens() )
					spect_valueV.addElement(new Double(st.nextToken()));
				double[] valueArr = toDoubleArray(spect_valueV);

				my_spectrumS.addElement(new DevVarDoubleStringArray(valueArr , timeArr));
			}
		}*/


		return my_spectrumS;
	}

	public Vector getAttSpectrumDataLast_n_Oracle(String att_name , int number , int writable) throws ArchivingException
	{
        if (canceled) return null;
		Vector tmpSpectrumVect = new Vector();
		//, timeVect = new Vector(), dimVect = new Vector(), svalueVect = new Vector();

		int data_type = getAtt_TFW_Data(att_name)[0];
		boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );

		Statement stmt;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string

		String tableName = getDbSchema() + "." + getTableName(att_name);
		String selectField_0 , selectField_1 , selectField_2 , selectField_3 = null ,selectFields , orderField;
		if ( ro_fields )
		{

			selectField_0 = ConfigConst.TAB_SPECTRUM_RO[ 0 ];
			selectField_1 = ConfigConst.TAB_SPECTRUM_RO[ 1 ];
			selectField_2 = ConfigConst.TAB_SPECTRUM_RO[ 2 ];
		}
		else
		{
			selectField_0 = ConfigConst.TAB_SPECTRUM_RW[ 0 ];
			selectField_1 = ConfigConst.TAB_SPECTRUM_RW[ 1 ];
			selectField_2 = ConfigConst.TAB_SPECTRUM_RW[ 2 ];
			selectField_3 = ConfigConst.TAB_SPECTRUM_RW[ 3 ];
		}

		selectFields = selectField_0 + ", " + selectField_1 + ", " + selectField_2;
		orderField = ( ro_fields ? ConfigConst.TAB_SPECTRUM_RO[ 0 ] : ConfigConst.TAB_SPECTRUM_RW[ 0 ] );
		String whereClause = "rownum" + " <= " + number + " ORDER BY  " + orderField + " ASC";

		String query1 = "SELECT * FROM " + tableName + " ORDER BY " + selectField_0 + " DESC";
		tableName = getDbSchema() + "." + getTableName(att_name) + " T";
		if ( ro_fields )
		{
			selectField_0 = "T" + "." + ConfigConst.TAB_SPECTRUM_RO[ 0 ];
			selectField_1 = "T" + "." + ConfigConst.TAB_SPECTRUM_RO[ 1 ];
			selectField_2 = "T" + "." + ConfigConst.TAB_SPECTRUM_RO[ 2 ];
		}
		else
		{
			selectField_0 = "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 0 ];
			selectField_1 = "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 1 ];
			selectField_2 = "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 2 ];
			selectField_3 = "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 3 ];
		}
		selectFields = toDbTimeFieldString(selectField_0) + ", " + selectField_1 + ", " + selectField_2;
		if ( ! ro_fields )
		{
		    selectFields += ", " + selectField_3;
		}
		
		String query =
		        "SELECT " + selectFields + " FROM (" + query1 + ") T WHERE " + whereClause;
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(query);
            if (canceled) return null;
            while ( rset.next() )
            {
                //System.out.println ( "CLA/getAttSpectrumData2/--------------7" );
                SpectrumEvent_RO spectrumEvent_ro = new SpectrumEvent_RO();
                SpectrumEvent_RW spectrumEvent_rw = new SpectrumEvent_RW();
                // Timestamp
                try
                {
                    spectrumEvent_ro.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                    spectrumEvent_rw.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                }
                catch (Exception e)
                {
                    if (canceled) return null;
                }

                // Dim
                int dim_x = 0;
                try
                {
                    dim_x = rset.getInt(2);
                }
                catch (Exception e)
                {
                    if (canceled) return null;
                }
                spectrumEvent_ro.setDim_x(dim_x);
                spectrumEvent_rw.setDim_x(dim_x);
                // Value
                Clob readClob = null;
                String readString = null;

                try
                {
                    readClob = rset.getClob ( 3 );
                    if (rset.wasNull())
                    {
                        readString = "null";
                    }
                    else
                    {
                        readString = readClob.getSubString ( 1 , (int) readClob.length() );
                    }
                }
                catch(Exception e)
                {
                    if (canceled) return null;
                }
                
                Clob writeClob = null;
                String writeString = null;
                if ( !ro_fields )
                {
                    try
                    {
                        writeClob = rset.getClob ( 4 );
                        if (rset.wasNull())
                        {
                            writeString = "null";
                        }
                        else
                        {
                            writeString = writeClob.getSubString ( 1 , (int) writeClob.length() );
                        }
                    }
                    catch(Exception e)
                    {
                        if (canceled) return null;
                    }
                }

                Object value = getSpectrumValue(readString, writeString, data_type);
                if (!ro_fields)
                {
                    spectrumEvent_rw.setValue(value);
                    tmpSpectrumVect.add(spectrumEvent_rw);
                }
                else
                {
                    spectrumEvent_ro.setValue(value);
                    tmpSpectrumVect.add(spectrumEvent_ro);
                }
                spectrumEvent_ro = null;
                spectrumEvent_rw = null;
                value = null;
                if (canceled) return null;
            }
            if (canceled) return null;
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttSpectrumDataLast_n() method..." +
			              "\r\n" + query;
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}

		return tmpSpectrumVect;
	}

	// todo mettre ent�te
	public Vector getAttSpectrumDataBetweenDates(String[] argin) throws ArchivingException
	{
        if (canceled) return null;
		String att_name = argin[ 0 ].trim();
		String time_0 = argin[ 1 ].trim();
		String time_1 = argin[ 2 ].trim();
		int[] tfw = getAtt_TFW_Data(att_name);
		Vector my_spectrumS = new Vector();
		int data_format = getAttDataFormat(att_name);
		String message = "" , reason = "" , desc = "";
		switch ( data_format )
		{  // [0 - > SCALAR] (1 - > SPECTRUM] [2 - > IMAGE]
			case AttrDataFormat._SCALAR:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Spectrum ";
				desc = "The attribute format is  not Spectrum : " + data_format + " (Scalar) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
			case AttrDataFormat._SPECTRUM:
				my_spectrumS = getAttSpectrumDataBetweenDates(att_name , time_0 , time_1, tfw[ 0 ],SamplingType.getSamplingType ( SamplingType.ALL ));
				break;
			case AttrDataFormat._IMAGE:
				message = "Failed retrieving data ! ";
				reason = "The attribute should be Spectrum ";
				desc = "The attribute format is  not Spectrum : " + data_format + " (Image) !!";
				throw new ArchivingException(message , reason , null , desc , this.getClass().getName());
		}
		return my_spectrumS;
	}

    public Vector getAttSpectrumDataBetweenDatesOracle(String att_name , String time_0 , String time_1, int data_type, SamplingType samplingType) throws ArchivingException
    {
        if (canceled) return null;
        //System.out.println ( "CLA/getAttSpectrumDataBetweenDatesOracle/" );
        
        Vector my_spectrumS = new Vector();
        int writable = getAttDataWritable(att_name);
        Statement stmt = null;
        ResultSet rset = null;
        // Create and execute the SQL query string
        // Build the query string
        String query = "";
        String selectField_0 = "";
        String selectField_1 = "";
        String selectField_2 = "";
        String selectField_3 = null;
        String selectFields = "";
        String dateClause = "";
        String whereClause = "";
        String groupByClause = "";
        String orderByClause = "";
        String tableName = "";
        tableName = getDbSchema() + "." + getTableName(att_name) + " T";
        boolean isBothReadAndWrite = !( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE ); 
        
        
        if ( samplingType.hasSampling () )
        {
            String groupingNormalisationType = "MIN"; //can't average a spectrum
            if ( ! samplingType.hasAdditionalFiltering () )
            {
                String format = samplingType.getOracleFormat ();
                if ( ! isBothReadAndWrite )
                {
                    selectField_0 = toDbTimeFieldString( "T" + "." + ConfigConst.TAB_SPECTRUM_RO[ 0 ] , format );
                    selectField_1 = "AVG (" + "T" + "." + ConfigConst.TAB_SPECTRUM_RO[ 1 ] + ")";
                    selectField_2 = "to_clob ( " + groupingNormalisationType + " ( to_char (" + "T" + "." + ConfigConst.TAB_SPECTRUM_RO[ 2 ] + ") ) )";
                }
                else
                {
                    selectField_0 = toDbTimeFieldString( "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 0 ] , format );
                    selectField_1 = "AVG (" + "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 1 ] + ")";
                    selectField_2 = "to_clob ( " + groupingNormalisationType + " ( to_char (" + "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 2 ] + ") ) )";
                    selectField_3 = "to_clob ( " + groupingNormalisationType + " ( to_char (" + "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 3 ] + ") ) )";
                }
                
                selectFields = selectField_0 + ", " + selectField_1 + ", " + selectField_2;
                if ( isBothReadAndWrite )
                {
                    selectFields += ", " + selectField_3;
                }
                
                whereClause = ConfigConst.TAB_SPECTRUM_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
                orderByClause = " ORDER BY " +toDbTimeFieldString(ConfigConst.TAB_SPECTRUM_RO[ 0 ], format);
                groupByClause = " GROUP BY " +toDbTimeFieldString(ConfigConst.TAB_SPECTRUM_RO[ 0 ], format);
                
                query = "SELECT " + selectFields + " FROM " + tableName + " WHERE " + whereClause + groupByClause + orderByClause;
            }
            else
            {
                String format = samplingType.getOneLevelHigherFormat ( db_type == ConfigConst.BD_MYSQL );
                String fullFormat = ( SamplingType.getSamplingType ( SamplingType.SECOND ) ).getFormat ( db_type == ConfigConst.BD_MYSQL );                
                
                String minTime = "MIN("+ConfigConst.TAB_SCALAR_RO[ 0 ]+")";
                selectField_0 = toDbTimeFieldString(minTime, fullFormat);
                
                if ( ! isBothReadAndWrite )
                {
                    selectField_1 = "AVG (" + "T" + "." + ConfigConst.TAB_SPECTRUM_RO[ 1 ] + ")";
                    selectField_2 = "to_clob ( " + groupingNormalisationType + " ( to_char (" + "T" + "." + ConfigConst.TAB_SPECTRUM_RO[ 2 ] + ") ) )";
                }
                else
                {
                    selectField_1 = "AVG (" + "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 1 ] + ")";
                    selectField_2 = "to_clob ( " + groupingNormalisationType + " ( to_char (" + "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 2 ] + ") ) )";
                    selectField_3 = "to_clob ( " + groupingNormalisationType + " ( to_char (" + "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 3 ] + ") ) )";
                }
                
                selectFields = selectField_0 + ", " + selectField_1 + ", " + selectField_2;
                if ( isBothReadAndWrite )
                {
                    selectFields += ", " + selectField_3;
                }
                
                whereClause = ConfigConst.TAB_SPECTRUM_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
                groupByClause = " GROUP BY " +toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ], format);
                groupByClause += samplingType.getAdditionalFilteringClause ( db_type == ConfigConst.BD_MYSQL , ConfigConst.TAB_SCALAR_RO[ 0 ] );
                orderByClause = ( db_type == ConfigConst.BD_MYSQL )? "" : " ORDER BY " +"MIN("+ConfigConst.TAB_SCALAR_RW[ 0 ]+")";
                
                query = "SELECT " + selectFields + " FROM " + tableName + " WHERE " + whereClause + groupByClause + orderByClause;
            }
        }
        else
        {
            if ( ! isBothReadAndWrite )
            {
                selectField_0 = "T" + "." + ConfigConst.TAB_SPECTRUM_RO[ 0 ];
                selectField_1 = "T" + "." + ConfigConst.TAB_SPECTRUM_RO[ 1 ];
                selectField_2 = "T" + "." + ConfigConst.TAB_SPECTRUM_RO[ 2 ];
            }
            else
            {
                selectField_0 = "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 0 ];
                selectField_1 = "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 1 ];
                selectField_2 = "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 2 ];
                selectField_3 = "T" + "." + ConfigConst.TAB_SPECTRUM_RW[ 3 ];
            }
            selectFields = toDbTimeFieldString(selectField_0) + ", " + selectField_1 + ", " + selectField_2;
            if ( isBothReadAndWrite )
            {
                selectFields += ", " + selectField_3;
            }
            
            dateClause = selectField_0 + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
            query ="SELECT " + selectFields + " FROM " + tableName + " WHERE " + "(" + dateClause + ")";
        }
        
        try
        {
            //int i = 0;
            stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
            rset = stmt.executeQuery(query);
            if (canceled) return null;
            while ( rset.next() )
            {
                if (canceled) return null;
                //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/rset.next(): i="+i );
                SpectrumEvent_RO spectrumEvent_ro = new SpectrumEvent_RO();
                SpectrumEvent_RW spectrumEvent_rw = new SpectrumEvent_RW();
                // Timestamp
                try
                {
                    //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get TimeStamp: i="+i );
                    spectrumEvent_ro.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                    spectrumEvent_rw.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                    //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get TimeStamp OK: i="+i );
                }
                catch (Exception e)
                {
                    if (canceled) return null;
                    //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get TimeStamp KO: i="+i );
                }

                // Dim
                int dim_x = 0;
                try
                {
                    //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get dim x: i="+i );
                    dim_x = rset.getInt(2);
                    //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get dim x OK: i="+i );
                }
                catch (Exception e)
                {
                    //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get dim x KO: i="+i );
                    if (canceled) return null;
                }
                spectrumEvent_ro.setDim_x(dim_x);
                spectrumEvent_rw.setDim_x(dim_x);
                // Value
                Clob readClob = null;
                String readString = null;

                try
                {
                    //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get read CLOB: i="+i );
                    readClob = rset.getClob ( 3 );
                    if (rset.wasNull())
                    {
                        readString = "null";
                    }
                    else
                    {
                        readString = readClob.getSubString ( 1 , (int) readClob.length() );
                    }
                    //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get read CLOB OK - get substring OK: i="+i );
                }
                catch(Exception e)
                {
                    //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get read CLOB KO: i="+i );
                    if (canceled) return null;
                }
                
                Clob writeClob = null;
                String writeString = null;
                if ( isBothReadAndWrite )
                {
                    try
                    {
                        //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get write CLOB: i="+i );
                        writeClob = rset.getClob ( 4 );
                        if (rset.wasNull())
                        {
                            writeString = "null";
                        }
                        else
                        {
                            writeString = writeClob.getSubString ( 1 , (int) writeClob.length() );
                        }
                        //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get write CLOB OK - get substring OK: i="+i );
                    }
                    catch(Exception e)
                    {
                        //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get write CLOB KO: i="+i );
                        if (canceled) return null;
                    }
                }

                //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get spectrum value: i="+i );
                Object value = getSpectrumValue(readString, writeString, data_type);
                //System.out.println ( "GIR/getAttSpectrumDataBetweenDatesOracle/get spectrum value OK: i="+i );
                if (isBothReadAndWrite)
                {
                    spectrumEvent_rw.setValue(value);
                    my_spectrumS.add(spectrumEvent_rw);
                }
                else
                {
                    spectrumEvent_ro.setValue(value);
                    my_spectrumS.add(spectrumEvent_ro);
                }
                spectrumEvent_ro = null;
                spectrumEvent_rw = null;
                value = null;
                if (canceled) return null;
            }
            if (canceled) return null;
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.getAttSpectrumDataBetweenDatesOracle() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        finally
        {
            try 
            {
                close ( rset );
                close ( stmt );
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.getAttSpectrumDataBetweenDatesOracle() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
            //Close the connection with the database
            if ( getAutoConnect() )
            {
                close();    
            }
        }
        return my_spectrumS;
    }
	

	public Vector getAttSpectrumDataBetweenDatesMySql(String att_name , String time_0 , String time_1, int data_type, SamplingType samplingType) throws ArchivingException
	{
        if (canceled) return null;
	    //System.out.println ( "CLA/getAttSpectrumDataBetweenDatesMySql/" );
	    
	    Vector my_spectrumS = new Vector();
		int writable = getAttDataWritable(att_name);
		Statement stmt = null;
		ResultSet rset = null;
		// Create and execute the SQL query string
		// Build the query string
		String getAttributeDataQuery = "";
		String selectField_0 = "";
		String selectField_1 = "";
		String selectField_2 = "";
		String selectField_3 = null;
		String selectFields = "";
		String dateClause = "";
		String tableName = "";
        String whereClause = "";
        String groupByClause = "";
        String orderByClause = "";
		tableName = getDbSchema() + "." + getTableName(att_name);
		
		boolean isBothReadAndWrite = !( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE ); 
        
        if ( samplingType.hasSampling () )
        {
            if ( ! samplingType.hasAdditionalFiltering () )
            {
                String format = samplingType.getMySqlFormat ();
                if ( ! isBothReadAndWrite )
                {
                    selectField_0 = toDbTimeFieldString( ConfigConst.TAB_SPECTRUM_RO[ 0 ] , format );
                    selectField_1 = "AVG (" + ConfigConst.TAB_SPECTRUM_RO[ 1 ] + ")";
                    selectField_2 = "MIN(CAST(" + ConfigConst.TAB_SPECTRUM_RO[ 2 ] + " AS CHAR))";
                }
                else
                {
                    selectField_0 = toDbTimeFieldString( ConfigConst.TAB_SPECTRUM_RW[ 0 ] , format );
                    selectField_1 = "AVG (" + ConfigConst.TAB_SPECTRUM_RW[ 1 ] + ")";
                    selectField_2 = "MIN(CAST(" + ConfigConst.TAB_SPECTRUM_RW[ 2 ] + " AS CHAR))";
                    selectField_3 = "MIN(CAST(" + ConfigConst.TAB_SPECTRUM_RW[ 3 ] + " AS CHAR))";
                }
    
                whereClause = ConfigConst.TAB_SPECTRUM_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
                groupByClause = " GROUP BY " +toDbTimeFieldString(ConfigConst.TAB_SPECTRUM_RO[ 0 ], format);
                orderByClause = " ORDER BY " +toDbTimeFieldString(ConfigConst.TAB_SPECTRUM_RO[ 0 ], format);
    
                selectFields = selectField_0 + "," + selectField_1 + "," + selectField_2;
                if ( isBothReadAndWrite )
                {
                    selectFields += "," + selectField_3;
                }                
                getAttributeDataQuery = "SELECT " + selectFields + " FROM " + tableName + " WHERE " + whereClause + groupByClause + orderByClause;
            }
            else
            {
                String format = samplingType.getOneLevelHigherFormat ( db_type == ConfigConst.BD_MYSQL );
                String fullFormat = ( SamplingType.getSamplingType ( SamplingType.SECOND ) ).getFormat ( db_type == ConfigConst.BD_MYSQL );                
                
                String minTime = "MIN("+ConfigConst.TAB_SCALAR_RO[ 0 ]+")";
                selectField_0 = toDbTimeFieldString(minTime, fullFormat);
                
                if ( ! isBothReadAndWrite )
                {
                    selectField_1 = "AVG (" + ConfigConst.TAB_SPECTRUM_RO[ 1 ] + ")";
                    selectField_2 = "MIN(CAST(" + ConfigConst.TAB_SPECTRUM_RO[ 2 ] + " AS CHAR))";
                }
                else
                {
                    selectField_1 = "AVG (" + ConfigConst.TAB_SPECTRUM_RW[ 1 ] + ")";
                    selectField_2 = "MIN(CAST(" + ConfigConst.TAB_SPECTRUM_RW[ 2 ] + " AS CHAR))";
                    selectField_3 = "MIN(CAST(" + ConfigConst.TAB_SPECTRUM_RW[ 3 ] + " AS CHAR))";
                }
                
                selectFields = selectField_0 + ", " + selectField_1 + ", " + selectField_2;
                if ( isBothReadAndWrite )
                {
                    selectFields += ", " + selectField_3;
                }
                
                whereClause = ConfigConst.TAB_SPECTRUM_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
                groupByClause = " GROUP BY " +toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ], format);
                groupByClause += samplingType.getAdditionalFilteringClause ( db_type == ConfigConst.BD_MYSQL , ConfigConst.TAB_SCALAR_RO[ 0 ] );
                orderByClause = ( db_type == ConfigConst.BD_MYSQL )? "" : " ORDER BY " +"MIN("+ConfigConst.TAB_SCALAR_RW[ 0 ]+")";
                
                getAttributeDataQuery = "SELECT " + selectFields + " FROM " + tableName + " WHERE " + whereClause + groupByClause + orderByClause;
            }
        }
        else
        {
            if ( ! isBothReadAndWrite )
    		{
    			selectField_0 = ConfigConst.TAB_SPECTRUM_RO[ 0 ];
    			selectField_1 = ConfigConst.TAB_SPECTRUM_RO[ 1 ];
    			selectField_2 = ConfigConst.TAB_SPECTRUM_RO[ 2 ];
    		}
    		else
    		{
    			selectField_0 = ConfigConst.TAB_SPECTRUM_RW[ 0 ];
    			selectField_1 = ConfigConst.TAB_SPECTRUM_RW[ 1 ];
    			selectField_2 = ConfigConst.TAB_SPECTRUM_RW[ 2 ];
    			selectField_3 = ConfigConst.TAB_SPECTRUM_RW[ 3 ];
    		}
    		selectFields = selectField_0 + ", " + selectField_1 + ", " + selectField_2;
    		if ( isBothReadAndWrite )
    		{
    		    selectFields += ", " + selectField_3;
    		}
    		
    		dateClause = selectField_0 + " BETWEEN " + "'" + time_0.trim() + "'" + " AND " + "'" + time_1.trim() + "'";
    		getAttributeDataQuery =
    		"SELECT " + selectFields + " FROM " + tableName + " WHERE " + "(" + dateClause + ")";
        }

        //System.out.println ( "CLA/getAttSpectrumDataBetweenDatesMySql/getAttributeDataQuery/"+getAttributeDataQuery );
        
        try
		{
			
            stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
            rset = stmt.executeQuery(getAttributeDataQuery);
            if (canceled) return null;
			//System.out.println ( "CLA/getAttSpectrumDataBetweenDatesMySql/ 1" );
            while ( rset.next() )
            {
                SpectrumEvent_RO spectrumEvent_ro = new SpectrumEvent_RO();
                SpectrumEvent_RW spectrumEvent_rw = new SpectrumEvent_RW();
                // Timestamp
                try
                {
                    spectrumEvent_ro.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                    spectrumEvent_rw.setTimeStamp(DateUtil.stringToMilli(rset.getString(1)));
                }
                catch (Exception e)
                {
                    if (canceled) return null;
                }

                // Dim
                if (canceled) return null;
                int dim_x = 0;
                try
                {
                    dim_x = rset.getInt(2);
                }
                catch (Exception e)
                {
                    if (canceled) return null;
                }
                spectrumEvent_ro.setDim_x(dim_x);
                spectrumEvent_rw.setDim_x(dim_x);
                // Value
                if (canceled) return null;
                String valueReadSt = null;
                try
                {
                    valueReadSt = rset.getString(3);
                    if (rset.wasNull())
                    {
                        valueReadSt = "null";
                    }
                }
                catch (Exception e)
                {
                    if (canceled) return null;
                }
                String valueWriteSt = null;
                if ( isBothReadAndWrite )
                {
                    try
                    {
                        valueWriteSt = rset.getString(4); 
                        if (rset.wasNull())
                        {
                            valueWriteSt = "null";
                        }
                    }
                    catch (Exception e)
                    {
                        if (canceled) return null;
                    }
                }
                Object value = getSpectrumValue(valueReadSt, valueWriteSt, data_type);
                if (isBothReadAndWrite)
                {
                    spectrumEvent_rw.setValue(value);
                    my_spectrumS.add(spectrumEvent_rw);
                }
                else
                {
                    spectrumEvent_ro.setValue(value);
                    my_spectrumS.add(spectrumEvent_ro);
                }
                spectrumEvent_ro = null;
                spectrumEvent_rw = null;
                value = null;
                if (canceled) return null;
            }
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttSpectrumDataBetweenDatesMySql() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
        finally
        {
            try 
            {
                close ( rset );
                close ( stmt );
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.getAttSpectrumDataBetweenDatesMySql() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
		return my_spectrumS;
	}

	public Vector getAttSpectrumDataBetweenDates(String att_name , String time_0 , String time_1, int data_type, SamplingType samplingFactor) throws ArchivingException
	{
        if (canceled) return null;
	    //getAttSpectrumDataBetweenDatesOracle
	    
	    if ( db_type == ConfigConst.BD_MYSQL )
		{
			return getAttSpectrumDataBetweenDatesMySql(att_name , time_0 , time_1, data_type , samplingFactor);
		}
		else 
        {
			return getAttSpectrumDataBetweenDatesOracle(att_name , time_0 , time_1, data_type , samplingFactor);
        }
	}
    
	public Vector getAttSpectrumData(String att_name ) throws ArchivingException
	{
        if (canceled) return null;
	    if ( db_type == ConfigConst.BD_MYSQL )
		{
			return getAttSpectrumDataMySQL(att_name);
		}
		else 
		{
			return getAttSpectrumDataOracle(att_name);
		}
	}

    public Vector getAttStringScalarDataBetweenDates(String att_name , String time_0 , String time_1 , int writable) throws ArchivingException
    {
        if (canceled) return null;
        Vector result = new Vector();
        boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
        Statement stmt;
        ResultSet rset;

        // Create and execute the SQL query string
        // Build the query string
        String query = "";
        String fields = "";
        String tableName = getDbSchema() + "." + getTableName(att_name);
        String whereClause = "";
        fields = ro_fields ?
                 ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
                 ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] ); // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
        whereClause = ConfigConst.TAB_SCALAR_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
        // My statement
        query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + whereClause + ")";
        //query = "SELECT " + fields + " FROM " + getDbSchema() + "." + getTableName(att_name.trim());
        try
        {
            stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
            rset = stmt.executeQuery(query);
            if (canceled) return null;
            if ( ro_fields )
            {
                while ( rset.next() )
                {
                    ScalarEvent_RO event;
                    String[] tempVal = new String[3];
                    long timeStamp = DateUtil.stringToMilli(rset.getString(1));
                    tempVal[0] = att_name;
                    tempVal[1] = Long.toString(timeStamp);
                    tempVal[2] = "0";
                    event = new ScalarEvent_RO(tempVal);
                    String resultst = rset.getString(2);
                    if (rset.wasNull())
                    {
                        resultst = "null";
                    }
                    event.setScalarValue( resultst );
                    result.add(event);
                }
            }
            else
            {
                while ( rset.next() )
                {
                    ScalarEvent_RW event;
                    String[] tempVal = new String[4];
                    long timeStamp = DateUtil.stringToMilli(rset.getString(1));
                    tempVal[0] = att_name;
                    tempVal[1] = Long.toString(timeStamp);
                    tempVal[2] = "0";
                    tempVal[3] = "0";
                    event = new ScalarEvent_RW(tempVal);
                    String[] value = new String[2];
                    value[0] = rset.getString(2);
                    if (rset.wasNull())
                    {
                        value[0] = "null";
                    }
                    value[1] = rset.getString(3);
                    if (rset.wasNull())
                    {
                        value[1] = "null";
                    }
                    event.setScalarValueRW(value);
                    result.add(event);
                }
            }
            close ( stmt );
        }
        catch ( SQLException e )
        {
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.getAttDataBetweenDates() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        
        return result;
    }

    public Vector getAttStateScalarDataBetweenDates(String att_name , String time_0 , String time_1 , int writable) throws ArchivingException
    {
        if (canceled) return null;
        Vector result = new Vector();
        boolean ro_fields = ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE );
        Statement stmt;
        ResultSet rset;

        // Create and execute the SQL query string
        // Build the query string
        String query = "";
        String fields = "";
        String tableName = getDbSchema() + "." + getTableName(att_name);
        String whereClause = "";
        fields = ro_fields ?
                 ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RO[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RO[ 1 ] ) :
                 ( toDbTimeFieldString(ConfigConst.TAB_SCALAR_RW[ 0 ]) + ", " + ConfigConst.TAB_SCALAR_RW[ 1 ] + ", " + ConfigConst.TAB_SCALAR_RW[ 2 ] ); // if (writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE)
        whereClause = ConfigConst.TAB_SCALAR_RO[ 0 ] + " BETWEEN " + toDbTimeString(time_0.trim()) + " AND " + toDbTimeString(time_1.trim());
        // My statement
        query = "SELECT " + fields + " FROM " + tableName + " WHERE " + "(" + whereClause + ")";
        //query = "SELECT " + fields + " FROM " + getDbSchema() + "." + getTableName(att_name.trim());
        try
        {
            stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
            rset = stmt.executeQuery(query);
            if (canceled) return null;
            if ( ro_fields )
            {
                while ( rset.next() )
                {
                    ScalarEvent_RO event;
                    String[] tempVal = new String[3];
                    long timeStamp = DateUtil.stringToMilli(rset.getString(1));
                    tempVal[0] = att_name;
                    tempVal[1] = Long.toString(timeStamp);
                    tempVal[2] = Integer.toString(rset.getInt(2));
                    if (rset.wasNull())
                    {
                        tempVal[2] = "null";
                    }
                    event = new ScalarEvent_RO(tempVal);
                    result.add(event);
                }
            }
            else
            {
                while ( rset.next() )
                {
                    ScalarEvent_RW event;
                    String[] tempVal = new String[4];
                    long timeStamp = DateUtil.stringToMilli(rset.getString(1));
                    tempVal[0] = att_name;
                    tempVal[1] = Long.toString(timeStamp);
                    tempVal[2] = Integer.toString(rset.getInt(2));
                    if (rset.wasNull())
                    {
                        tempVal[2] = "null";
                    }
                    tempVal[3] = Integer.toString(rset.getInt(3));
                    if (rset.wasNull())
                    {
                        tempVal[3] = "null";
                    }
                    event = new ScalarEvent_RW(tempVal);
                    result.add(event);
                }
            }
            close ( stmt );
        }
        catch ( SQLException e )
        {
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.getAttDataBetweenDates() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        
        return result;
    }

	/**
	 * This methods retreives some informations associated to the given attribute, builds an AttributeLight and returns it.
	 *
	 * @param att_name attribute name
	 * @return an AttributeLight object (built with the retrieved informations).
	 * @throws ArchivingException
	 */
	public AttributeLight getAttributeLightInfo(String att_name) throws SQLException , ArchivingException
	{
        if (canceled) return null;
		AttributeLight attributeLight = new AttributeLight(att_name);
		int attributeTypeInfo = 0;
		int attributeFormatInfo = 0;
		int attributeWritableInfo = 0;
		ResultSet rset;

		PreparedStatement ps_get_attributeLight_info;
		String table_name = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		String field_fullName = ConfigConst.TAB_DEF[ 2 ];
		String field_type = ConfigConst.TAB_DEF[ 8 ];
		String field_format = ConfigConst.TAB_DEF[ 9 ];
		String field_writable = ConfigConst.TAB_DEF[ 10 ];

		// My statement
		String selectString = "SELECT " + field_type + ", " + field_format + ", " + field_writable +
		                      " FROM " + table_name +
		                      //" WHERE " + field_fullName + " LIKE ?";
                              " WHERE " + field_fullName + " = ?";
		ps_get_attributeLight_info = dbconn.prepareStatement(selectString);
        lastStatement = ps_get_attributeLight_info;
        if (canceled) return null;
		try
		{
			String field1 = att_name.trim();
			ps_get_attributeLight_info.setString(1 , field1);
			rset = ps_get_attributeLight_info.executeQuery();
            if (canceled) return null;
			// Gets the result of the query
			if ( rset.next() )
			{
				attributeTypeInfo = rset.getInt(1);
				attributeFormatInfo = rset.getInt(2);
				attributeWritableInfo = rset.getInt(3);
			}
			close(ps_get_attributeLight_info);
			attributeLight.setData_type(attributeTypeInfo);
			attributeLight.setData_format(attributeFormatInfo);
			attributeLight.setWritable(attributeWritableInfo);
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttributeLightInfo() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return attributeLight;
	}

	/**
	 * <b>Description : </b>    	Returns an array containing the differents definition informations for the given attribute
	 *
	 * @param att_name The attribute's name
	 * @return An array containing the differents definition informations for the given attribute
	 * @throws ArchivingException
	 */
	public Vector getAttDefinitionData(String att_name) throws ArchivingException
	{
        if (canceled) return null;
		Vector definitionsList = new Vector();
		Statement stmt;
		ResultSet rset;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		String sqlStr = "";
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			sqlStr = "SELECT * FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ] +
			         " WHERE " + ConfigConst.TAB_DEF[ 2 ] + "='" + att_name.trim() + "'";
		}
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
			sqlStr = "SELECT ";
			for ( int i = 0 ; i < ConfigConst.TAB_DEF.length - 1 ; i++ )
			{
				sqlStr = sqlStr + "to_char(" + ConfigConst.TAB_DEF[ i ] + ")" + ", ";
			}
			sqlStr = sqlStr + "to_char(" + ConfigConst.TAB_DEF[ ConfigConst.TAB_DEF.length - 1 ] + ")";
			sqlStr = sqlStr +
			         " FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ] +
			         " WHERE " + ConfigConst.TAB_DEF[ 2 ] + "='" + att_name.trim() + "'";
		}

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;
			rset = stmt.executeQuery(sqlStr);
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				for ( int i = 0 ; i < ConfigConst.TAB_DEF.length ; i++ )
				{
					String info = ConfigConst.TAB_DEF[ i ] + "::" + rset.getString(i + 1);
					definitionsList.addElement(info);
				}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDefinitionData() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Close the connection with the database
		if ( getAutoConnect() )
			close();
		// Returns the names list
		return definitionsList;
	}

	/**
	 * Returns the type, the format and the writable property of a given attribute
	 *
	 * @param att_name
	 * @return An array (int) containing the type, the format and the writable property of the given attribute.
	 * @throws ArchivingException
	 */
	public int[] getAtt_TFW_Data(String att_name) throws ArchivingException
	{
        if (canceled) return null;
		int[] TFW_Data = new int[ 3 ];

		PreparedStatement preparedStatement;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String get_TFW_DataQuery = "";
		String select_field = "";
		select_field = select_field +
		               ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 8 ] + ", " +
		               ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 9 ] + ", " +
		               ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 10 ];

		String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		//String clause_1 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " LIKE " + "?";
        String clause_1 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " = " + "?";

		get_TFW_DataQuery = "SELECT " + select_field + " FROM " + table_1 + " WHERE (" + clause_1 + ") ";
		try
		{
			preparedStatement = dbconn.prepareStatement(get_TFW_DataQuery);
            lastStatement = preparedStatement;
            if (canceled) return null;
			preparedStatement.setString(1 , att_name.trim());
			try
			{
				rset = preparedStatement.executeQuery();
                if (canceled) return null;
				while ( rset.next() )
				{
					TFW_Data[ 0 ] = rset.getInt(ConfigConst.TAB_DEF[ 8 ]);
					TFW_Data[ 1 ] = rset.getInt(ConfigConst.TAB_DEF[ 9 ]);
					TFW_Data[ 2 ] = rset.getInt(ConfigConst.TAB_DEF[ 10 ]);
				}
				close(preparedStatement);
			}
			catch ( SQLException e )
			{
				close(preparedStatement);
				throw e;
			}
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAtt_TFW_Data() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Returns the names list
		return TFW_Data;
	}
    
    /**
     * Returns the type, the format and the writable property of a given attribute
     *
     * @param att_name
     * @return An array (int) containing the type, the format and the writable property of the given attribute.
     * @throws ArchivingException
     */
    public int[] getAtt_TFW_Data_By_Id(int id) throws ArchivingException
    {
        if (canceled) return null;
        int[] TFW_Data = new int[ 3 ];

        PreparedStatement preparedStatement;
        ResultSet rset;
        // Create and execute the SQL query string
        // Build the query string
        String get_TFW_DataQuery = "";
        String select_field = "";
        select_field = select_field +
                       ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 8 ] + ", " +
                       ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 9 ] + ", " +
                       ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 10 ];

        String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
        String clause_1 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 0 ] + " = " + "?";

        get_TFW_DataQuery = "SELECT " + select_field + " FROM " + table_1 + " WHERE (" + clause_1 + ") ";
        //System.out.println ( "getAtt_TFW_Data_By_Id/query/"+get_TFW_DataQuery );
        try
        {
            preparedStatement = dbconn.prepareStatement(get_TFW_DataQuery);
            lastStatement = preparedStatement;
            if (canceled) return null;
            preparedStatement.setInt(1 , id );
            //preparedStatement.setString(1 , id.trim());
            try
            {
                rset = preparedStatement.executeQuery();
                if (canceled) return null;
                while ( rset.next() )
                {
                    TFW_Data[ 0 ] = rset.getInt(ConfigConst.TAB_DEF[ 8 ]);
                    TFW_Data[ 1 ] = rset.getInt(ConfigConst.TAB_DEF[ 9 ]);
                    TFW_Data[ 2 ] = rset.getInt(ConfigConst.TAB_DEF[ 10 ]);
                }
                close(preparedStatement);
            }
            catch ( SQLException e )
            {
                close(preparedStatement);
                throw e;
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace ();
            
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.getAtt_TFW_Data_By_Id() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        // Returns the names list
        return TFW_Data;
    }

	/**
	 * Returns the tango type of a given attribute
	 *
	 * @param att_name
	 * @return An int containing that describe the type of the given attribute.
	 * @throws ArchivingException
	 */
	public int getAttDataType(String att_name) throws SQLException , ArchivingException
	{
        if (canceled) return 0;
        if (dbconn == null) return 0;
		int dataType = 0;

		PreparedStatement preparedStatement;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String get_DataTypeQuery = "";
		String select_field = "";
		select_field = select_field +
		               ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 8 ];

		String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		//String clause_1 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " LIKE " + "?";
        String clause_1 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " = " + "?";

		get_DataTypeQuery = "SELECT " + select_field + " FROM " + table_1 + " WHERE (" + clause_1 + ") ";
		preparedStatement = dbconn.prepareStatement(get_DataTypeQuery);
        lastStatement = preparedStatement;
        if (canceled) return 0;
		try
		{
			preparedStatement.setString(1 , att_name.trim());
			rset = preparedStatement.executeQuery();
            if (canceled) return 0;
			while ( rset.next() )
			{
				dataType = rset.getInt(1);
			}
			close(preparedStatement);
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataType() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Returns the names list
		return dataType;
	}

	/**
	 * Returns the tango format of a given attribute
	 *
	 * @param att_name
	 * @return An int that describe the format of the given attribute.
	 * @throws ArchivingException
	 */
	public int getAttDataFormat(String att_name) throws ArchivingException
	{
        if (canceled) return 0;
        if (dbconn == null) return 0;
		int dataFormat = 0;

		PreparedStatement preparedStatement = null;
		ResultSet rset = null;
		// Create and execute the SQL query string
		// Build the query string
		String get_DataFormatQuery = "";
		String select_field = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 9 ];
		String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		//String clause_1 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " LIKE " + "?";
        String clause_1 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + "=" + "?"+"";
		get_DataFormatQuery = "SELECT " + select_field + " FROM " + table_1 + " WHERE (" + clause_1 + ") ";

		try
		{
            //System.out.println ( "CLA/DataBaseApi/getAttDataFormat/query|"+get_DataFormatQuery+"|" );
            
            preparedStatement = dbconn.prepareStatement(get_DataFormatQuery);
            lastStatement = preparedStatement;
            if (canceled) return 0;
			preparedStatement.setString(1 , att_name.trim());
			rset = preparedStatement.executeQuery();
            if (canceled) return 0;
			while ( rset.next() )
			{
				dataFormat = rset.getInt(1);
			}
			close(preparedStatement);
		}
		catch ( SQLException e )
		{
			e.printStackTrace ();
            
            String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataFormat() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
        finally
        {
            try 
            {
                close ( rset );
                close ( preparedStatement );
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.getAttDataFormat() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
		// Returns the names list
		return dataFormat;
	}

	/**
	 * Returns the tango writable parameter for a given attribute
	 *
	 * @param att_name
	 * @return An int that is the writable value of the given attribute.
	 * @throws ArchivingException
	 */
	public int getAttDataWritable(String att_name) throws ArchivingException
	{
        if (canceled) return 0;
		int dataWritable = 0;

		PreparedStatement preparedStatement;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String get_DataWritableQuery = "";
		String select_field = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 10 ];
		String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		//String clause_1 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " LIKE " + "?";
        String clause_1 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " = " + "?";
		get_DataWritableQuery = "SELECT " + select_field + " FROM " + table_1 + " WHERE (" + clause_1 + ") ";
		try
		{
			preparedStatement = dbconn.prepareStatement(get_DataWritableQuery);
            lastStatement = preparedStatement;
            if (canceled) return 0;
			preparedStatement.setString(1 , att_name.trim());
			rset = preparedStatement.executeQuery();
            if (canceled) return 0;
			while ( rset.next() )
			{
				dataWritable = rset.getInt(1);
			}
			close(preparedStatement);
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttDataWritable() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);

		}
		// Returns the names list
		return dataWritable;
	}

	/**
	 * <b>Description : </b>    	Returns an array containing the differents properties informations for the given attribute
	 *
	 * @param att_name The attribute's name
	 * @return An array containing the differents properties informations for the given attribute
	 * @throws ArchivingException
	 */
	public Vector getAttPropertiesData(String att_name) throws ArchivingException
	{
        if (canceled) return null;
		Vector properties = new Vector();
		PreparedStatement preparedStatement;
		ResultSet rset;
		// Create and execute the SQL query string
		// Build the query string
		String select_field = ConfigConst.TABS[ 1 ] + "." + "*";
		String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		String table_2 = getDbSchema() + "." + ConfigConst.TABS[ 1 ];
		String clause_1 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 0 ] + " = " + ConfigConst.TABS[ 1 ] + "." + ConfigConst.TAB_PROP[ 0 ];
		//String clause_2 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " LIKE " + "?";
        String clause_2 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " = " + "?";

		String sqlStr = "SELECT " + select_field + " FROM " + table_1 + ", " + table_2 +
		                " WHERE (" + "(" + clause_1 + ")" + " AND " + "(" + clause_2 + ")" + ") ";


		try
		{
			preparedStatement = dbconn.prepareStatement(sqlStr);
            lastStatement = preparedStatement;
            if (canceled) return null;
			preparedStatement.setString(1 , att_name.trim());
			rset = preparedStatement.executeQuery();
            if (canceled) return null;
			// Gets the result of the query
			while ( rset.next() )
				for ( int i = 0 ; i < ConfigConst.TAB_PROP.length ; i++ )
				{
					String info = ConfigConst.TAB_PROP[ i ] + "::" + rset.getString(i + 1);
					properties.addElement(info);
				}
			close(preparedStatement);
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getAttPropertiesData() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		// Returns the names list
		return properties;
	}

	/**
	 * <b>Description : </b> Returns the new id for the attribute being referenced in <I>HDB</I>.<I>(mySQL only)</I>
	 *
	 * @return the new id for the attribute being referenced in <I>HDB</I>
	 * @throws ArchivingException
	 */
	public int getMaxID() throws ArchivingException
	{
        if (canceled) return 0;
		Statement stmt;
		ResultSet rset;
		int new_ID = 10000;
		int res = 10000;
		// First connect with the database
		if ( getAutoConnect() )
			connect();
		// Create and execute the SQL query string
		// Build the query string

		String getMaxIdQuery;
		getMaxIdQuery = "SELECT MAX(" + ConfigConst.TAB_DEF[ 0 ] + ") FROM " + getDbSchema() + "." + ConfigConst.TABS[ 0 ];

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return 0;
			rset = stmt.executeQuery(getMaxIdQuery);
            if (canceled) return 0;
			// Gets the result of the query
			if ( rset.next() )
			{
				res = rset.getInt("max(ID)");
			}
			if ( res < new_ID )
			{
				//System.out.println("La table/base est vide");
				res = new_ID;
			}
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.getMaxID() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		return res;
	}

	/**
	 * <b>Description : </b> Checks if the attribute of the given name, is already registered in <I>HDB</I>
	 * (and more particularly in the table of the definitions).
	 *
	 * @param att_name The name of the attribute to check.
	 * @return boolean
	 * @throws ArchivingException
	 */
	public boolean isRegisteredADT(String att_name) throws ArchivingException
	{
        if (canceled) return false;
		int id = getAttID(att_name.trim());

		if ( id != 0 )
		{
			return true;
		}
		else
			return false;
	}

	/**
	 * <b>Description : </b>
	 *
	 * @param att_name The name of the attribute
	 * @return a boolean : "true" if the attribute of given name is currently archived, "false" otherwise.
	 * @throws ArchivingException
	 */
	public boolean isArchived(String att_name) throws ArchivingException
	{
        if (dbconn==null) return false;
        if (canceled) return false;
		//int res = 0;
        boolean ret = false;
		PreparedStatement preparedStatement = null;
		ResultSet rset = null;
		// Build the query string
		String isArchivedQuery = "";
		String select_field = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 0 ];
		String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 2 ];
		String table_2 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
		String clause_1 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 0 ] + " = " + ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_MOD[ 0 ];
		String clause_2 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " = " + "?";
		String clause_3 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 3 ] + " IS NULL ";

		isArchivedQuery = "SELECT " + select_field + " FROM " + table_1 + ", " + table_2 +
		                  " WHERE (" + "(" + clause_1 + ")" + " AND " + "(" + clause_2 + ")" + " AND " + "(" + clause_3 + ")" + ") ";
        
        //System.out.println ( "DataBaseApi/isArchived/isArchivedQuery|"+isArchivedQuery+"|" );
        
		try
		{
			preparedStatement = dbconn.prepareStatement(isArchivedQuery);
            lastStatement = preparedStatement;
            if (canceled) return false;
			preparedStatement.setString(1 , att_name.trim());
			rset = preparedStatement.executeQuery();
            if (canceled) return false;
			//while ( rset.next() )
            if ( rset.next() )
			{
				//IdVect.addElement(new Integer(rset.getInt(1)));
                ret = true;
			}
		}
		catch ( SQLException e )
		{
            if (canceled) return false;
			String message = "";
			if ( e.getMessage().equalsIgnoreCase("Io exception: Broken pipe") || e.getMessage().indexOf("Communication link failure") != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.isArchived(att_name) method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
        finally
        {
            try 
            {
                close(rset);
                close(preparedStatement);
            } 
            catch (SQLException e) 
            {
                String message = "";
                if ( e.getMessage().equalsIgnoreCase("Io exception: Broken pipe") || e.getMessage().indexOf("Communication link failure") != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.isArchived(att_name) method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
        
        return ret;
	}

    /**
     * <b>Description : </b>
     * 
     * @param att_name
     *            The name of the attribute
     * @param device_name
     *            The name of the device in charge
     * @return a boolean : "true" if the attribute named att_name is currently
     *         archived by the device named device_name, "false" otherwise.
     * @throws ArchivingException
     */
    public boolean isArchived(String att_name, String device_name) throws ArchivingException
    {
        if (canceled) return false;
        boolean ret = false;
        PreparedStatement preparedStatement = null;
        ResultSet rset = null;
        // Build the query string
        String isArchivedQuery = "";
        String select_field = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 0 ];
        String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 2 ];
        String table_2 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
        String clause_1 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 0 ] + " = " + ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_MOD[ 0 ];
        /*String clause_2 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " LIKE " + "?";
        String clause_3 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 1 ] + " LIKE " + "?";*/
        String clause_2 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " = " + "?";
        String clause_3 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 1 ] + " = " + "?";
        String clause_4 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 3 ] + " IS NULL ";

        isArchivedQuery = "SELECT " + select_field + " FROM " + table_1 + ", " + table_2 +
                          " WHERE ("
                              + "(" + clause_1 + ")"
                              + " AND " + "(" + clause_2 + ")"
                              + " AND " + "(" + clause_3 + ")"
                              + " AND " + "(" + clause_4 + ")"
                              + ") ";
        try
        {
            preparedStatement = dbconn.prepareStatement(isArchivedQuery);
            lastStatement = preparedStatement;
            if (canceled) return false;
            preparedStatement.setString(1 , att_name.trim());
            preparedStatement.setString(2 , device_name.trim());
            rset = preparedStatement.executeQuery();
            if (canceled) return false;
            if ( rset.next() )
            {
                ret = true;
            }
        }
        catch ( SQLException e )
        {
            String message = "";
            if ( e.getMessage().equalsIgnoreCase("Io exception: Broken pipe") || e.getMessage().indexOf("Communication link failure") != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.isArchived(att_name,device_name) method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        finally
        {
            try 
            {
                close(rset);
                close(preparedStatement);
            } 
            catch (SQLException e) 
            {
                String message = "";
                if ( e.getMessage().equalsIgnoreCase("Io exception: Broken pipe") || e.getMessage().indexOf("Communication link failure") != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.isArchived(att_name) method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
        // Close the connection with the database
        return ret;
    }

    /**
     * <b>Description : </b>
     * 
     * @param att_name
     *            The name of the attribute
     * @return a String : The name of the corresponding archiver if the
     *         attribute is beeing archived, an empty String otherwise
     * @throws ArchivingException
     */
    public String getArchiverForAttribute(String att_name) throws ArchivingException
    {
        if (canceled) return null;
        Vector archivVect = new Vector();
        int res = 0;
        PreparedStatement preparedStatement;
        ResultSet rset;
        // Build the query string
        String isArchivedQuery = "";
        String select_field = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 1 ];
        String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 2 ];
        String table_2 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
        String clause_1 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 0 ] + " = " + ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_MOD[ 0 ];
        //String clause_2 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " LIKE " + "?";
        String clause_2 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " = " + "?";
        String clause_3 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 3 ] + " IS NULL ";

        isArchivedQuery = "SELECT " + select_field + " FROM " + table_1 + ", " + table_2 +
                          " WHERE (" + "(" + clause_1 + ")" + " AND " + "(" + clause_2 + ")" + " AND " + "(" + clause_3 + ")" + ") ";
        try
        {
            preparedStatement = dbconn.prepareStatement(isArchivedQuery);
            lastStatement = preparedStatement;
            if (canceled) return null;
            preparedStatement.setString(1 , att_name.trim());
            rset = preparedStatement.executeQuery();
            if (canceled) return null;
            while ( rset.next() )
            {
                archivVect.addElement(rset.getString(1));
                res++;
            }
            close(preparedStatement);
        }
        catch ( SQLException e )
        {
            String message = "";
            if ( e.getMessage().equalsIgnoreCase("Io exception: Broken pipe") || e.getMessage().indexOf("Communication link failure") != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.getArchiverForAttribute(att_name) method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        // Close the connection with the database
        if ( res > 0 )
        {
            return (String)archivVect.firstElement();
        }
        else
        {
            return "";
        }
    }
    
    /**
     * <b>Description : </b>
     * 
     * @param att_name
     *            The name of the attribute
     * @return a String : The name of the corresponding archiver if the
     *         attribute is beeing archived, an empty String otherwise
     * @throws ArchivingException
     */
    public String getArchiverForAttributeEvenIfTheStopDateIsNotNull(String att_name ) throws ArchivingException
    {
        if (canceled) return null;
        Vector archivVect = new Vector();
        int res = 0;
        PreparedStatement preparedStatement;
        ResultSet rset;
        // Build the query string
        String isArchivedQuery = "";
        String select_field = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 1 ];
        String table_1 = getDbSchema() + "." + ConfigConst.TABS[ 2 ];
        String table_2 = getDbSchema() + "." + ConfigConst.TABS[ 0 ];
        String clause_1 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 0 ] + " = " + ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_MOD[ 0 ];
        String clause_2 = ConfigConst.TABS[ 0 ] + "." + ConfigConst.TAB_DEF[ 2 ] + " = " + "?";
        String clause_3 = ConfigConst.TABS[ 2 ] + "." + ConfigConst.TAB_MOD[ 3 ] + " IS NULL ";

        isArchivedQuery = "SELECT " + select_field + " FROM " + table_1 + ", " + table_2 +
                          " WHERE (" + "(" + clause_1 + ")" + " AND " + "(" + clause_2 + ")" + " AND " + "(" + clause_3 + ")" + ") ";
        try
        {
            preparedStatement = dbconn.prepareStatement(isArchivedQuery);
            lastStatement = preparedStatement;
            if (canceled) return null;
            preparedStatement.setString(1 , att_name.trim());
            rset = preparedStatement.executeQuery();
            if (canceled) return null;
            while ( rset.next() )
            {
                archivVect.addElement(rset.getString(1));
                res++;
            }
            close(preparedStatement);
        }
        catch ( SQLException e )
        {
            String message = "";
            if ( e.getMessage().equalsIgnoreCase("Io exception: Broken pipe") || e.getMessage().indexOf("Communication link failure") != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.getArchiverForAttribute(att_name) method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        // Close the connection with the database
        if ( res > 0 )
        {
            return (String)archivVect.firstElement();
        }
        else
        {
            return "";
        }
    }

	/*|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	 *
	 *
	 *                                              PART 3 :      Generals methods used to update HDB (INSERT, UPDATE)
	 *                                                                            (Insertions, Mises � jour)
	 *
	 *
	 |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||*/

	/**
	 * This method registers a given attribute into the hdb database
	 * It inserts a record in the "Attribute Definition Table" <I>(mySQL only)</I>
	 * This methos does not take care of id parameter of the given attribute as this parameter is managed in the database side (autoincrement).
	 *
	 * @param attributeHeavy the attribute to register
	 * @throws ArchivingException
	 */
	public void registerAttribute(AttributeHeavy attributeHeavy) throws ArchivingException
	{
        if (canceled) return;
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			// Register the attribute in the 'Attribute Definition Table'
			registerInADT(attributeHeavy);
			//int id = getAttID(attributeHeavy.getAttribute_complete_name());
            int id = getBufferedAttID(attributeHeavy.getAttribute_complete_name());
            
			// Register the attribute in the 'Attribute Property Table'
			registerInAPT(id , attributeHeavy);
			// Build the associated table name
			String tableName = getTableName(id);
			// Build the associated table
			buildAttributeTab(tableName , attributeHeavy.getData_type() , attributeHeavy.getData_format() , attributeHeavy.getWritable());
		}
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
			CallableStatement cstmt_register_in_hdb;
			String myStatement = "{call " + getDbSchema() + ".crtab (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
			try
			{
				cstmt_register_in_hdb = dbconn.prepareCall(myStatement);
                lastStatement = cstmt_register_in_hdb;
                if (canceled) return;
				cstmt_register_in_hdb.setTimestamp(1 , attributeHeavy.getRegistration_time());
				cstmt_register_in_hdb.setString(2 , attributeHeavy.getAttribute_complete_name());
				cstmt_register_in_hdb.setString(3 , attributeHeavy.getAttribute_device_name());
				cstmt_register_in_hdb.setString(4 , attributeHeavy.getDomain());

				cstmt_register_in_hdb.setString(5 , attributeHeavy.getFamily());
				cstmt_register_in_hdb.setString(6 , attributeHeavy.getMember());
				cstmt_register_in_hdb.setString(7 , attributeHeavy.getAttribute_name());

				cstmt_register_in_hdb.setInt(8 , attributeHeavy.getData_type());
				cstmt_register_in_hdb.setInt(9 , attributeHeavy.getData_format());
				cstmt_register_in_hdb.setInt(10 , attributeHeavy.getWritable());
				//System.out.println ( "CLA/cstmt_register_in_hdb/attributeHeavy.getWritable()/"+attributeHeavy.getWritable() );

				cstmt_register_in_hdb.setInt(11 , attributeHeavy.getMax_dim_x());
				cstmt_register_in_hdb.setInt(12 , attributeHeavy.getMax_dim_y());

				cstmt_register_in_hdb.setInt(13 , attributeHeavy.getLevel());
				cstmt_register_in_hdb.setString(14 , attributeHeavy.getCtrl_sys());
				cstmt_register_in_hdb.setInt(15 , attributeHeavy.getArchivable());
				cstmt_register_in_hdb.setInt(16 , attributeHeavy.getSubstitute());

				cstmt_register_in_hdb.setString(17 , attributeHeavy.getDescription());
				cstmt_register_in_hdb.setString(18 , attributeHeavy.getLabel());
				cstmt_register_in_hdb.setString(19 , attributeHeavy.getUnit());
				cstmt_register_in_hdb.setString(20 , attributeHeavy.getStandard_unit());

				cstmt_register_in_hdb.setString(21 , attributeHeavy.getDisplay_unit());
				cstmt_register_in_hdb.setString(22 , attributeHeavy.getFormat());
				cstmt_register_in_hdb.setString(23 , attributeHeavy.getMin_value());
				cstmt_register_in_hdb.setString(24 , attributeHeavy.getMax_value());
				cstmt_register_in_hdb.setString(25 , attributeHeavy.getMin_alarm());
				cstmt_register_in_hdb.setString(26 , attributeHeavy.getMax_alarm());
				cstmt_register_in_hdb.setString(27 , "");

				cstmt_register_in_hdb.executeUpdate();
                commit();
				close(cstmt_register_in_hdb);
			}
			catch ( SQLException e )
			{
                rollback();
				String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.QUERY_FAILURE;
				String desc = "Failed while executing DataBaseApi.registerAttribute() method...";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);

			}
            catch ( ArchivingException ae )
            {
                throw ae;
            }
		}
	}

	/**
	 * This method registers a given attribute into the hdb database
	 * This method does not take care of id parameter of the given attribute as this parameter is managed in the database side (autoincrement).
	 *
	 * @param attributeHeavy the attribute to register
	 * @throws ArchivingException
	 */
	public void registerInADT(AttributeHeavy attributeHeavy) throws ArchivingException
	{
        if (canceled) return;
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			PreparedStatement preparedStatement;
			String tableName = getDbSchema() + "." + ConfigConst.TABS[ 0 ];

			// Create and execute the SQL query string
			// Build the query string
			String insert_query;
			insert_query = "INSERT INTO " + tableName + " (";
			for ( int i = 1 ; i < ConfigConst.TAB_DEF.length - 1 ; i++ )
			{
				insert_query = insert_query + ConfigConst.TAB_DEF[ i ] + ", ";
			}
			insert_query = insert_query +
			               ConfigConst.TAB_DEF[ ConfigConst.TAB_DEF.length - 1 ] + ")";
			insert_query = insert_query + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			try
			{
				preparedStatement = dbconn.prepareStatement(insert_query);
                lastStatement = preparedStatement;
                if (canceled) return;
				//preparedStatement.setInt(1, attributeHeavy.getAttribute_id());
				preparedStatement.setTimestamp(1 , attributeHeavy.getRegistration_time());
				preparedStatement.setString(2 , attributeHeavy.getAttribute_complete_name());
				preparedStatement.setString(3 , attributeHeavy.getAttribute_device_name());
				preparedStatement.setString(4 , attributeHeavy.getDomain());
				preparedStatement.setString(5 , attributeHeavy.getFamily());
				preparedStatement.setString(6 , attributeHeavy.getMember());
				preparedStatement.setString(7 , attributeHeavy.getAttribute_name());
				preparedStatement.setInt(8 , attributeHeavy.getData_type());
				preparedStatement.setInt(9 , attributeHeavy.getData_format());
				preparedStatement.setInt(10 , attributeHeavy.getWritable());
				preparedStatement.setInt(11 , attributeHeavy.getMax_dim_x());
				preparedStatement.setInt(12 , attributeHeavy.getMax_dim_y());
				preparedStatement.setInt(13 , attributeHeavy.getLevel());
				
                //System.out.println ( "CLA/attributeHeavy.getCtrl_sys()/"+attributeHeavy.getCtrl_sys()+"/" );
                preparedStatement.setString(14 , attributeHeavy.getCtrl_sys());
				preparedStatement.setInt(15 , attributeHeavy.getArchivable());
				preparedStatement.setInt(16 , attributeHeavy.getSubstitute());

				preparedStatement.executeUpdate();
				close(preparedStatement);
			}
			catch ( SQLException e )
			{
				String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.QUERY_FAILURE;
				String desc = "Failed while executing DataBaseApi.registerInADT() method...";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
		}
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
			// This methos is not called by the Oracle side
		}
	}

	/**
	 * This method registers a given attribute into the hdb database
	 * It inserts a record in the "Attribute Properties Table" <I>(mySQL only)</I>
	 * This method does not take care of id parameter of the given attribute as this parameter is managed in the database side (autoincrement).
	 *
	 * @param attributeHeavy the attribute to register
	 * @throws ArchivingException
	 */
	public void registerInAPT(int id , AttributeHeavy attributeHeavy) throws ArchivingException
	{
        if (canceled) return;
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			PreparedStatement preparedStatement;
			String tableName = getDbSchema() + "." + ConfigConst.TABS[ 1 ];

			// Create and execute the SQL query string
			// Build the query string
			String insert_query;
			insert_query = "INSERT INTO " + tableName + " (";// +
			for ( int i = 0 ; i < ConfigConst.TAB_PROP.length - 1 ; i++ )
			{
				insert_query = insert_query + ConfigConst.TAB_PROP[ i ] + ", ";
			}
			insert_query = insert_query +
			               ConfigConst.TAB_PROP[ ConfigConst.TAB_PROP.length - 1 ] + ")";
			insert_query = insert_query + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";

			try
			{
				preparedStatement = dbconn.prepareStatement(insert_query);
                lastStatement = preparedStatement;
                if (canceled) return;
				preparedStatement.setInt(1 , id);
				preparedStatement.setTimestamp(2 , attributeHeavy.getRegistration_time());
				
                String description = attributeHeavy.getDescription();
                int maxDescriptionLength = 255;
                if ( description != null && description.length() > maxDescriptionLength )
                {
                    description = description.substring ( 0 , maxDescriptionLength );
                }
                preparedStatement.setString(3 , description);
				
                preparedStatement.setString(4 , attributeHeavy.getLabel());
				preparedStatement.setString(5 , attributeHeavy.getUnit());
				preparedStatement.setString(6 , attributeHeavy.getStandard_unit());
				preparedStatement.setString(7 , attributeHeavy.getDisplay_unit());
				preparedStatement.setString(8 , attributeHeavy.getFormat());
				preparedStatement.setString(9 , attributeHeavy.getMin_value());
				preparedStatement.setString(10 , attributeHeavy.getMax_value());
				preparedStatement.setString(11 , attributeHeavy.getMin_alarm());
				preparedStatement.setString(12 , attributeHeavy.getMax_alarm());

				preparedStatement.executeUpdate();
				close(preparedStatement);
			}
			catch ( SQLException e )
			{
				String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.QUERY_FAILURE;
				String desc = "Failed while executing DataBaseApi.registerInAPT() method...";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
		}
	}


	public void buildAttributeTab(String tableName , int data_type , int data_format , int writable) throws ArchivingException
	{
        if (canceled) return;
		switch ( data_format )
		{
			case fr.esrf.Tango.AttrDataFormat._SCALAR:
				switch ( writable )
				{
					case fr.esrf.Tango.AttrWriteType._READ:
						buildAttributeScalarTab_R(tableName , data_type);
						break;
					case fr.esrf.Tango.AttrWriteType._READ_WITH_WRITE:
						buildAttributeScalarTab_RW(tableName , data_type);
						break;
					case fr.esrf.Tango.AttrWriteType._WRITE:
						buildAttributeScalarTab_W(tableName , data_type);
						break;
					case fr.esrf.Tango.AttrWriteType._READ_WRITE:
						buildAttributeScalarTab_RW(tableName , data_type);
						break;
				}
				break;
			case fr.esrf.Tango.AttrDataFormat._SPECTRUM:
				switch ( writable )
				{
					case fr.esrf.Tango.AttrWriteType._READ:
						buildAttributeSpectrumTab_R(tableName , data_type);
						break;
					case fr.esrf.Tango.AttrWriteType._READ_WITH_WRITE:
						buildAttributeSpectrumTab_RW(tableName , data_type);
						break;
					case fr.esrf.Tango.AttrWriteType._WRITE:
						//buildAttributeSpectrumTab_W(tableName , data_type);
						break;
					case fr.esrf.Tango.AttrWriteType._READ_WRITE:
						buildAttributeSpectrumTab_RW(tableName , data_type);
						break;
				}
				break;
			case fr.esrf.Tango.AttrDataFormat._IMAGE:
				switch ( writable )
				{
					case fr.esrf.Tango.AttrWriteType._READ:
						buildAttributeImageTab_R(tableName , data_type);
						break;
					case fr.esrf.Tango.AttrWriteType._READ_WITH_WRITE:
						buildAttributeImageTab_RW(tableName , data_type);
						break;
					case fr.esrf.Tango.AttrWriteType._WRITE:
						//buildAttributeImageTab_W(tableName , data_type);
						break;
					case fr.esrf.Tango.AttrWriteType._READ_WRITE:
						buildAttributeImageTab_RW(tableName , data_type);
						break;
				}
				break;
		}
	}

	/**
	 * <b>Description : </b> Inserts a record in the "Attribut Mode Table" <I>(mySQL only)</I>.
	 * Each time that the archiving of an attribute is triggered, this table is fielded.
	 */
	public void insertModeRecord(AttributeLightMode attributeLightMode) throws ArchivingException
	{
        if (canceled) return;
		PreparedStatement preparedStatement;
		//int id = getAttID(attributeLightMode.getAttribute_complete_name());
        int id = getBufferedAttID(attributeLightMode.getAttribute_complete_name());
		StringBuffer tableName = new StringBuffer().append(getDbSchema()).append(".").append(ConfigConst.TABS[ 2 ]);
		boolean historic = ( attributeLightMode.getMode().getTdbSpec() == null );

		StringBuffer select_field =
		        new StringBuffer().append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 0 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 1 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 2 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 4 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 5 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 6 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 7 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 8 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 9 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 10 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 11 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 12 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 13 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 14 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 15 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 16 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 17 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 18 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 19 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 20 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 21 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 22 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 23 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 24 ]).append(", ");

		if ( historic )
		{
			// historic archiving
			select_field.append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 25 ]);
		}
		else
		{
			// temporary archiving
			select_field.append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 25 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 26 ]).append(", ").append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 27 ]);
		}

		// Create and execute the SQL query string
		// Build the query string
		StringBuffer query = new StringBuffer();
		if ( historic )
		{
			query.append("INSERT INTO ").append(tableName).append(" (").append(select_field).append(")").append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		}
		else
		{
			query.append("INSERT INTO ").append(tableName).append(" (").append(select_field).append(")").append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		}
		//System.out.println("DataBaseApi.insertModeRecord : Query ... \r\n\t" + query);

		try
		{
			preparedStatement = dbconn.prepareStatement(query.toString());
            lastStatement = preparedStatement;
            if (canceled) return;
			preparedStatement.setInt(1 , id);
			preparedStatement.setString(2 , attributeLightMode.getDevice_in_charge());
			//preparedStatement.setTimestamp(3 , attributeLightMode.getTrigger_time());
			preparedStatement.setTimestamp(3 , new Timestamp(System.currentTimeMillis()));
			// the field named "stop_date (3) is not included"

			// Periodical Mode
			if ( attributeLightMode.getMode().getModeP() != null )
			{
				preparedStatement.setInt(4 , 1);
				preparedStatement.setInt(5 , attributeLightMode.getMode().getModeP().getPeriod());
			}
			else
			{
				preparedStatement.setInt(4 , 0);
				preparedStatement.setInt(5 , 0);
			}
			// Absolute Mode
			if ( attributeLightMode.getMode().getModeA() != null )
			{
				preparedStatement.setInt(6 , 1);
				preparedStatement.setInt(7 , attributeLightMode.getMode().getModeA().getPeriod());
				preparedStatement.setDouble(8 , attributeLightMode.getMode().getModeA().getValInf());
				preparedStatement.setDouble(9 , attributeLightMode.getMode().getModeA().getValSup());
			}
			else
			{
				preparedStatement.setInt(6 , 0);
				preparedStatement.setInt(7 , 0);
				preparedStatement.setInt(8 , 0);
				preparedStatement.setInt(9 , 0);
			}
			// Relative Mode
			if ( attributeLightMode.getMode().getModeR() != null )
			{
				preparedStatement.setInt(10 , 1);
				preparedStatement.setInt(11 , attributeLightMode.getMode().getModeR().getPeriod());
				preparedStatement.setDouble(12 , attributeLightMode.getMode().getModeR().getPercentInf());
				preparedStatement.setDouble(13 , attributeLightMode.getMode().getModeR().getPercentSup());
			}
			else
			{
				preparedStatement.setInt(10 , 0);
				preparedStatement.setInt(11 , 0);
				preparedStatement.setInt(12 , 0);
				preparedStatement.setInt(13 , 0);
			}
			// Threshold Mode
			if ( attributeLightMode.getMode().getModeT() != null )
			{
				preparedStatement.setInt(14 , 1);
				preparedStatement.setInt(15 , attributeLightMode.getMode().getModeT().getPeriod());
				preparedStatement.setDouble(16 , attributeLightMode.getMode().getModeT().getThresholdInf());
				preparedStatement.setDouble(17 , attributeLightMode.getMode().getModeT().getThresholdSup());
			}
			else
			{
				preparedStatement.setInt(14 , 0);
				preparedStatement.setInt(15 , 0);
				preparedStatement.setInt(16 , 0);
				preparedStatement.setInt(17 , 0);
			}
			// On Calculation Mode
			if ( attributeLightMode.getMode().getModeC() != null )
			{
				preparedStatement.setInt(18 , 1);
				preparedStatement.setInt(19 , attributeLightMode.getMode().getModeC().getPeriod());
				preparedStatement.setInt(20 , attributeLightMode.getMode().getModeC().getRange());
				preparedStatement.setInt(21 , attributeLightMode.getMode().getModeC().getTypeCalcul());
				preparedStatement.setString(22 , "");
			}
			else
			{
				preparedStatement.setInt(18 , 0);
				preparedStatement.setInt(19 , 0);
				preparedStatement.setInt(20 , 0);
				preparedStatement.setInt(21 , 0);
				preparedStatement.setString(22 , "");
			}
			// On Difference Mode
			if ( attributeLightMode.getMode().getModeD() != null )
			{
				preparedStatement.setInt(23 , 1);
				preparedStatement.setInt(24 , attributeLightMode.getMode().getModeD().getPeriod());
			}
			else
			{
				preparedStatement.setInt(23 , 0);
				preparedStatement.setInt(24 , 0);
			}
			// On External Mode
			if ( attributeLightMode.getMode().getModeE() != null )
			{
				preparedStatement.setInt(25 , 1);
			}
			else
			{
				preparedStatement.setInt(25 , 0);
			}
			// Specif Tdb
			if ( attributeLightMode.getMode().getTdbSpec() != null )
			{
				preparedStatement.setLong(26 , attributeLightMode.getMode().getTdbSpec().getExportPeriod());
				preparedStatement.setLong(27 , attributeLightMode.getMode().getTdbSpec().getKeepingPeriod());
			}

			try
			{
                preparedStatement.executeUpdate();
                if(!getAutoCommit())
                {
                    commit();
                }
                close(preparedStatement);
			}
			catch ( SQLException e )
			{
                if (!getAutoCommit())
                {
                    rollback();
                }
                e.printStackTrace ();
                close(preparedStatement);
				throw e;
			}
            catch ( ArchivingException ae )
            {
                close(preparedStatement);
                throw ae;
            }
		    catch ( Throwable t )
			{
                t.printStackTrace ();
			}
		}
		catch ( SQLException e )
		{
			e.printStackTrace ();
		    
		    String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.UPDATE_FAILURE;
			String desc = "Failed while executing DataBaseApi.insertModeRecord() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
        catch ( Throwable t )
        {
            t.printStackTrace();
        }
	}


	/**
	 * Method which updates a record in the "Attribute Mode Table"
	 * Each time that the archiving of an attribute is stopped, one of this table's row is fielded.
	 */
	public void updateModeRecord(String attribute_name) throws ArchivingException
	{
        if (canceled) return;
		PreparedStatement preparedStatement = null;

		// Create and execute the SQL query string
		// Build the query string
		String update_query = "";

		// *******************		new policy compatible with the 3.23.58-nt mysql version
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			//Make the query in two steps for the several version of Mysql
			//First get the ID of the complete name
			//int att_Id = getAttID(attribute_name);
            int att_Id = getBufferedAttID(attribute_name);
            
			//Then put the stop date
			String updated_table = new StringBuffer().append(getDbSchema()).append(".").append(ConfigConst.TABS[ 2 ]).toString();
			String set_clause = new StringBuffer().append(ConfigConst.TAB_MOD[ 3 ]).append(" = ?").toString();
			String clause_1 = new StringBuffer().append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 0 ]).append(" = ").append(att_Id).toString();
			String clause_3 = new StringBuffer().append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 3 ]).append(" IS NULL ").toString();
			update_query = new StringBuffer().append("UPDATE ").append(updated_table).append(" SET ").append(set_clause).append(" WHERE ").append("(").append("(").append(clause_1).append(")").append(" AND ").append("(").append(clause_3).append(")").append(")").toString();
			try
			{
				preparedStatement = dbconn.prepareStatement(update_query);
                lastStatement = preparedStatement;
                if (canceled) return;
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				preparedStatement.setTimestamp(1 , timestamp);
				try
				{
					preparedStatement.executeUpdate();
					close(preparedStatement);
				}
				catch ( SQLException e )
				{
					close(preparedStatement);
					throw e;
				}
			}
			catch ( SQLException e )
			{
				String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.UPDATE_FAILURE;
				String desc = "Failed while executing DataBaseApi.updateModeRecord() method...";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
		}
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
			update_query = "";
			String updated_table = new StringBuffer().append(getDbSchema()).append(".").append(ConfigConst.TABS[ 2 ]).toString();
			String set_clause = new StringBuffer().append(ConfigConst.TABS[ 2 ]).append(".").append(ConfigConst.TAB_MOD[ 3 ]).append(" = ?").toString();
            String clause_1 = new StringBuffer().append(ConfigConst.TAB_MOD[ 0 ]).append(" = ").append("(SELECT ").append(ConfigConst.TAB_DEF[ 0 ]).append(" FROM ").append(getDbSchema()).append(".").append(ConfigConst.TABS[ 0 ]).append(" WHERE ").append(ConfigConst.TAB_DEF[ 2 ]).append(" = ").append("?)").toString();
			String clause_2 = new StringBuffer().append(ConfigConst.TAB_MOD[ 3 ]).append(" IS NULL").toString();
			update_query = new StringBuffer().append("UPDATE ").append(updated_table).append(" SET ").append(set_clause).append(" WHERE ").append("(").append("(").append(clause_1).append(")").append(" AND ").append("(").append(clause_2).append(")").append(")").toString();
			try
			{
				preparedStatement = dbconn.prepareStatement(update_query);
                lastStatement = preparedStatement;
                if (canceled) return;
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				preparedStatement.setTimestamp(1 , timestamp);
				preparedStatement.setString(2 , attribute_name);
				try
				{
					preparedStatement.executeUpdate();
                    commit();
					close(preparedStatement);
				}
				catch ( SQLException e )
				{
                    rollback();
					close(preparedStatement);
					throw e;
				}
                catch ( ArchivingException ae )
                {
                    close(preparedStatement);
                    throw ae;
                }
			}
			catch ( SQLException e )
			{
				String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.UPDATE_FAILURE;
				String desc = "Failed while executing DataBaseApi.updateModeRecord() method...";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
		}

		// *******************		old policy only compatible with mysql version earlier than  3.23.58-nt
		/*
		if (db_type == ConfigConst.BD_MYSQL) {
		String updated_table = getDbSchema() + "." + ConfigConst.TABS[2] + ", " + getDbSchema() + "." + ConfigConst.TABS[0];
		String set_clause = ConfigConst.TABS[2] + "." + ConfigConst.TAB_MOD[3] + " = ?";
		String clause_1 = ConfigConst.TABS[2] + "." + ConfigConst.TAB_MOD[0] + " = " + ConfigConst.TABS[0] + "." + ConfigConst.TAB_MOD[0];
		String clause_2 = ConfigConst.TABS[0] + "." + ConfigConst.TAB_DEF[2] + " LIKE " + "?";
		String clause_3 = ConfigConst.TABS[2] + "." + ConfigConst.TAB_MOD[3] + " IS NULL ";
		update_query = "UPDATE " + updated_table + " SET " + set_clause + " WHERE " + "(" + "(" + clause_1 + ")" + " AND " + "(" + clause_2 + ")" + " AND " + "(" + clause_3 + ")" + ")";
		} else if (db_type == ConfigConst.BD_ORACLE) {
		update_query = "";
		String updated_table = getDbSchema() + "." + ConfigConst.TABS[2];
		String set_clause = ConfigConst.TABS[2] + "." + ConfigConst.TAB_MOD[3] + " = ?";
		String clause_1 = ConfigConst.TAB_MOD[0] + " = " +
		    "(SELECT " + ConfigConst.TAB_DEF[0] +
		    " FROM " + getDbSchema() + "." + ConfigConst.TABS[0] +
		    " WHERE " + ConfigConst.TAB_DEF[2] + " LIKE " + "?)";
		String clause_2 = ConfigConst.TAB_MOD[3] + " IS NULL";
		update_query = "UPDATE " + updated_table + " SET " + set_clause + " WHERE " + "(" + "(" + clause_1 + ")" + " AND " + "(" + clause_2 + ")" + ")";
		}
		try {
		preparedStatement = dbconn.prepareStatement(update_query);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		preparedStatement.setTimestamp(1, timestamp);
		preparedStatement.setString(2, attribute_name);
		preparedStatement.executeUpdate();
		close(preparedStatement);
		} catch (SQLException e) {
		System.err.println("DataBaseApi.updateModeRecord0 : " + e.getMessage() +
		    "\r\n\t - Statement : " + update_query);
		throw e;
		}
		*/
	}
    
    //public void insert_ScalarData_withId(ScalarEvent scalarEvent) throws ArchivingException
    public void insert_ScalarData(ScalarEvent scalarEvent) throws ArchivingException
    {
        if (canceled) return;
        String name = scalarEvent.getAttribute_complete_name().trim();
        long time = scalarEvent.getTimeStamp();
        Timestamp timeSt = new Timestamp(time);
        int writable = scalarEvent.getWritable();
        int idPosition;

        Object value = scalarEvent.getValue();
        StringBuffer query = new StringBuffer();
        
        String selectFields = "";
        // Create and execute the SQL query string
        // Build the query string
        if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
        {
            selectFields = "?, ?";
        }
        else
        {
            selectFields = "?, ?, ?";
        }

        if ( db_type == ConfigConst.BD_MYSQL )
        {
            StringBuffer tableName = new StringBuffer().append(getDbSchema()).append(".").append(getTableName(name));
            StringBuffer tableFields = new StringBuffer();
            if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
            {
                tableFields = new StringBuffer().append(ConfigConst.TAB_SCALAR_RO[ 0 ]).append(", ").append(ConfigConst.TAB_SCALAR_RO[ 1 ]);
            }
            else
            { 
                tableFields = new StringBuffer().append(ConfigConst.TAB_SCALAR_RW[ 0 ]).append(", ").append(ConfigConst.TAB_SCALAR_RW[ 1 ]).append(", ").append(ConfigConst.TAB_SCALAR_RW[ 2 ]);
            }
            PreparedStatement preparedStatement;
            query.append("INSERT INTO ").append(tableName).append(" (").append(tableFields).append(")").append(" VALUES").append(" (").append(selectFields).append(")");

            try
            {
                preparedStatement = dbconn.prepareStatement(query.toString());
                lastStatement = preparedStatement;
                if (canceled) return;
                preparedStatement.setTimestamp(1 , timeSt);
                if ( value != null )
                {
                    if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
                    {
                        switch ( scalarEvent.getData_type() )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                preparedStatement.setString( 2 , StringFormater.formatStringToWrite( ( String ) value ) );
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                preparedStatement.setByte(2 , ( ( Byte ) value ).byteValue());
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                preparedStatement.setInt(2 , ( ( DevState ) value ).value());
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                            case TangoConst.Tango_DEV_LONG:
                                preparedStatement.setInt(2 , ( ( Integer ) value ).intValue());
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                preparedStatement.setBoolean(2 , ( ( Boolean ) value ).booleanValue());
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                            case TangoConst.Tango_DEV_USHORT:
                                preparedStatement.setShort(2 , ( ( Short ) value ).shortValue());
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                preparedStatement.setFloat(2 , ( ( Float ) value ).floatValue());
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                preparedStatement.setDouble(2 , ( ( Double ) value ).doubleValue());
                                break;
                            default:
                                preparedStatement.setDouble(2 , ( ( Double ) value ).doubleValue());
                        }
                    }
                    else
                    {
                        switch ( scalarEvent.getData_type() )
                        {
                            case TangoConst.Tango_DEV_ULONG:
                            case TangoConst.Tango_DEV_LONG:
                            case TangoConst.Tango_DEV_STATE:
                                Integer[] intvalue = (Integer[]) value;
                                if (intvalue[0] == null)
                                {
                                    preparedStatement.setNull(2, Types.NUMERIC);
                                }
                                else
                                {
                                    preparedStatement.setInt(2 , intvalue[ 0 ].intValue());
                                }
                                if (intvalue[1] == null)
                                {
                                    preparedStatement.setNull(3, Types.NUMERIC);
                                }
                                else
                                {
                                    preparedStatement.setInt(3 , intvalue[ 1 ].intValue());
                                }
                                intvalue = null;
                                break;
                            case TangoConst.Tango_DEV_STRING:
                                String[] strvalue = (String[]) value;
                                if (strvalue[0] == null)
                                {
                                    preparedStatement.setNull(2, Types.VARCHAR);
                                }
                                else
                                {
                                    preparedStatement.setString(2 , StringFormater.formatStringToWrite( ( ( String[] ) value )[ 0 ] ));
                                }
                                if (strvalue[1] == null)
                                {
                                    preparedStatement.setNull(3, Types.VARCHAR);
                                }
                                else
                                {
                                    preparedStatement.setString(3 , StringFormater.formatStringToWrite( ( ( String[] ) value )[ 1 ] ));
                                }
                                strvalue = null;
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                Byte[] bytevalue = (Byte[]) value;
                                if (bytevalue[0] == null)
                                {
                                    preparedStatement.setNull(2, Types.NUMERIC);
                                }
                                else
                                {
                                    preparedStatement.setByte(2 , bytevalue[ 0 ].byteValue());
                                }
                                if (bytevalue[1] == null)
                                {
                                    preparedStatement.setNull(3, Types.NUMERIC);
                                }
                                else
                                {
                                    preparedStatement.setByte(3 , bytevalue[ 1 ].byteValue());
                                }
                                bytevalue = null;
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                Boolean[] boolvalue = (Boolean[]) value;
                                if (boolvalue[0] == null)
                                {
                                    preparedStatement.setNull(2, Types.BOOLEAN);
                                }
                                else
                                {
                                    preparedStatement.setBoolean(2 , boolvalue[ 0 ].booleanValue());
                                }
                                if (boolvalue[1] == null)
                                {
                                    preparedStatement.setNull(3, Types.BOOLEAN);
                                }
                                else
                                {
                                    preparedStatement.setBoolean(3 , boolvalue[ 1 ].booleanValue());
                                }
                                boolvalue = null;
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                            case TangoConst.Tango_DEV_USHORT:
                                Short[] shortvalue = (Short[]) value;
                                if (shortvalue[0] == null)
                                {
                                    preparedStatement.setNull(2, Types.NUMERIC);
                                }
                                else
                                {
                                    preparedStatement.setShort(2 , shortvalue[ 0 ].shortValue());
                                }
                                if (shortvalue[1] == null)
                                {
                                    preparedStatement.setNull(3, Types.NUMERIC);
                                }
                                else
                                {
                                    preparedStatement.setShort(3 , shortvalue[ 1 ].shortValue());
                                }
                                shortvalue = null;
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                Float[] floatvalue = (Float[]) value;
                                if (floatvalue[0] == null)
                                {
                                    preparedStatement.setNull(2, Types.NUMERIC);
                                }
                                else
                                {
                                    preparedStatement.setFloat(2 , floatvalue[ 0 ].floatValue());
                                }
                                if (floatvalue[1] == null)
                                {
                                    preparedStatement.setNull(3, Types.NUMERIC);
                                }
                                else
                                {
                                    preparedStatement.setFloat(3 , floatvalue[ 1 ].floatValue());
                                }
                                floatvalue = null;
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                            default:
                                Double[] doublevalue = (Double[]) value;
                                if (doublevalue[0] == null)
                                {
                                    preparedStatement.setNull(2, Types.NUMERIC);
                                }
                                else
                                {
                                    preparedStatement.setDouble(2 , doublevalue[ 0 ].doubleValue());
                                }
                                if (doublevalue[1] == null)
                                {
                                    preparedStatement.setNull(3, Types.NUMERIC);
                                }
                                else
                                {
                                    preparedStatement.setDouble(3 , doublevalue[ 1 ].doubleValue());
                                }
                                doublevalue = null;
                        }
                    }

                }
                else
                {
                    if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
                    {
                        switch ( scalarEvent.getData_type() )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                preparedStatement.setNull(2 , java.sql.Types.VARCHAR);
                                break;
                            case TangoConst.Tango_DEV_STATE:
                            case TangoConst.Tango_DEV_UCHAR:
                            case TangoConst.Tango_DEV_LONG:
                            case TangoConst.Tango_DEV_ULONG:
                            case TangoConst.Tango_DEV_BOOLEAN:
                            case TangoConst.Tango_DEV_SHORT:
                            case TangoConst.Tango_DEV_USHORT:
                            case TangoConst.Tango_DEV_FLOAT:
                            case TangoConst.Tango_DEV_DOUBLE:
                            default:
                                preparedStatement.setNull(2 , java.sql.Types.NUMERIC);
                        }
                    }
                    else
                    {
                        switch ( scalarEvent.getData_type() )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                preparedStatement.setNull(2 , java.sql.Types.VARCHAR);
                                preparedStatement.setNull(3 , java.sql.Types.VARCHAR);
                                break;
                            case TangoConst.Tango_DEV_STATE:
                            case TangoConst.Tango_DEV_UCHAR:
                            case TangoConst.Tango_DEV_LONG:
                            case TangoConst.Tango_DEV_ULONG:
                            case TangoConst.Tango_DEV_BOOLEAN:
                            case TangoConst.Tango_DEV_SHORT:
                            case TangoConst.Tango_DEV_USHORT:
                            case TangoConst.Tango_DEV_FLOAT:
                            case TangoConst.Tango_DEV_DOUBLE:
                            default:
                                preparedStatement.setNull(2 , java.sql.Types.NUMERIC);
                                preparedStatement.setNull(3 , java.sql.Types.NUMERIC);
                        }
                    }

                }
                try
                {
                    preparedStatement.executeUpdate();
                    close(preparedStatement);
                }
                catch ( SQLException e )
                {
                    close(preparedStatement);
                    throw e;
                }
            }
            catch ( SQLException e )
            {
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.INSERT_FAILURE;
                String desc = "Failed while executing DataBaseApi.insert_ScalarData_RO() method...\r\n" + query;
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
        else if ( db_type == ConfigConst.BD_ORACLE )
        {
            CallableStatement callableStatement;
            if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
            {
                idPosition = 4;
                
                switch ( scalarEvent.getData_type() )
                {
                    case TangoConst.Tango_DEV_STRING:
                        query.append("{call ").append(getDbSchema()).append(".ins_sc_str_1val_with_id(?, ?, ?, ?)}");
                        break;
                    case TangoConst.Tango_DEV_STATE:
                    case TangoConst.Tango_DEV_UCHAR:
                    case TangoConst.Tango_DEV_LONG:
                    case TangoConst.Tango_DEV_ULONG:
                    case TangoConst.Tango_DEV_BOOLEAN:
                    case TangoConst.Tango_DEV_SHORT:
                    case TangoConst.Tango_DEV_USHORT:
                    case TangoConst.Tango_DEV_FLOAT:
                    case TangoConst.Tango_DEV_DOUBLE:
                    default:
                        query.append("{call ").append(getDbSchema()).append(".ins_sc_num_1val_with_id(?, ?, ?, ?)}");
                }
            }
            else
            {
                idPosition = 5;
                
                switch ( scalarEvent.getData_type() )
                {
                    case TangoConst.Tango_DEV_STRING:
                        query.append("{call ").append(getDbSchema()).append(".ins_sc_str_2val_with_id(?, ?, ?, ?, ?)}");
                        break;
                    case TangoConst.Tango_DEV_STATE:
                    case TangoConst.Tango_DEV_UCHAR:
                    case TangoConst.Tango_DEV_LONG:
                    case TangoConst.Tango_DEV_ULONG:
                    case TangoConst.Tango_DEV_BOOLEAN:
                    case TangoConst.Tango_DEV_SHORT:
                    case TangoConst.Tango_DEV_USHORT:
                    case TangoConst.Tango_DEV_FLOAT:
                    case TangoConst.Tango_DEV_DOUBLE:
                    default:
                        query.append("{call ").append(getDbSchema()).append(".ins_sc_num_2val_with_id(?, ?, ?, ?, ?)}");
                }
            }

            try
            {
                callableStatement = dbconn.prepareCall(query.toString());
                lastStatement = callableStatement;
                if (canceled) return;
                
                //int id = getAttID ( name );
                int id = getBufferedAttID ( name );
                callableStatement.setInt(idPosition , id);
                
                callableStatement.setString(1 , name);
                callableStatement.setTimestamp(2 , timeSt);
                if ( value != null )
                {
                    if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
                    {
                        switch ( scalarEvent.getData_type() )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                callableStatement.setString(3 , StringFormater.formatStringToWrite(  ( String ) value ));
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                callableStatement.setInt(3 , ( ( DevState ) value ).value());
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                callableStatement.setByte(3 , ( ( Byte ) value ).byteValue());
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                callableStatement.setInt(3 , ( ( Integer ) value ).intValue());
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                callableStatement.setInt(3 , ( ( Integer ) value ).intValue());
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                callableStatement.setBoolean(3 , ( ( Boolean ) value ).booleanValue());
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                            case TangoConst.Tango_DEV_USHORT:
                                callableStatement.setShort(3 , ( ( Short ) value ).shortValue());
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                callableStatement.setFloat(3 , ( ( Float ) value ).floatValue());
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                if ( Double.isNaN ( (( Double ) value ).doubleValue()  ) )
                                {
                                    // XXX : Should not replace NaN by null
                                    callableStatement.setNull(3,java.sql.Types.NUMERIC);    
                                }
                                else
                                {
                                    callableStatement.setDouble(3 , ( ( Double ) value ).doubleValue());
                                }
                                
                                break;
                            default:
                                if ( Double.isNaN ( (( Double ) value ).doubleValue()  ) )
                                {
                                    // XXX : Should not replace NaN by null
                                    callableStatement.setNull(3,java.sql.Types.NUMERIC);    
                                }
                                else
                                {
                                    callableStatement.setDouble(3 , ( ( Double ) value ).doubleValue());
                                }
                        }
                    }
                    else
                    { 
                        switch ( scalarEvent.getData_type() )
                        {
                            case TangoConst.Tango_DEV_STATE:
                                if ( ( ( Integer[] ) value )[ 0 ] == null)
                                {
                                    callableStatement.setNull(3, java.sql.Types.NUMERIC);
                                }
                                else
                                {
                                    callableStatement.setInt(3 , ( ( Integer[] ) value )[ 0 ].intValue());
                                }
                                if ( ( ( Integer[] ) value )[ 1 ] == null)
                                {
                                    callableStatement.setNull(4, java.sql.Types.NUMERIC);
                                }
                                else
                                {
                                    callableStatement.setInt(4 , ( ( Integer[] ) value )[ 1 ].intValue());
                                }
                                break;
                            case TangoConst.Tango_DEV_STRING:
                                if ( ( ( String[] ) value )[ 0 ] == null)
                                {
                                    callableStatement.setNull(3, java.sql.Types.VARCHAR);
                                }
                                else
                                {
                                    callableStatement.setString(3 , ( ( String[] ) value )[ 0 ]);
                                }
                                if ( ( ( String[] ) value )[ 1 ] == null)
                                {
                                    callableStatement.setNull(4, java.sql.Types.VARCHAR);
                                }
                                else
                                {
                                    callableStatement.setString(4 , ( ( String[] ) value )[ 1 ]);
                                }
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                if ( ( ( Byte[] ) value )[ 0 ] == null)
                                {
                                    callableStatement.setNull(3, java.sql.Types.NUMERIC);
                                }
                                else
                                {
                                    callableStatement.setByte(3 , ( ( Byte[] ) value )[ 0 ].byteValue());
                                }
                                if ( ( ( Byte[] ) value )[ 1 ] == null)
                                {
                                    callableStatement.setNull(4, java.sql.Types.NUMERIC);
                                }
                                else
                                {
                                    callableStatement.setByte(4 , ( ( Byte[] ) value )[ 1 ].byteValue());
                                }
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                if ( ( ( Integer[] ) value )[ 0 ] == null)
                                {
                                    callableStatement.setNull(3, java.sql.Types.NUMERIC);
                                }
                                else
                                {
                                    callableStatement.setInt(3 , ( ( Integer[] ) value )[ 0 ].intValue());
                                }
                                if ( ( ( Integer[] ) value )[ 1 ] == null)
                                {
                                    callableStatement.setNull(4, java.sql.Types.NUMERIC);
                                }
                                else
                                {
                                    callableStatement.setInt(4 , ( ( Integer[] ) value )[ 1 ].intValue());
                                }
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                if ( ( ( Integer[] ) value )[ 0 ] == null)
                                {
                                    callableStatement.setNull(3, java.sql.Types.NUMERIC);
                                }
                                else
                                {
                                    callableStatement.setInt(3 , ( ( Integer[] ) value )[ 0 ].intValue());
                                }
                                if ( ( ( Integer[] ) value )[ 1 ] == null)
                                {
                                    callableStatement.setNull(4, java.sql.Types.NUMERIC);
                                }
                                else
                                {
                                    callableStatement.setInt(4 , ( ( Integer[] ) value )[ 1 ].intValue());
                                }
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                if ( ( ( Boolean[] ) value )[ 0 ] == null)
                                {
                                    callableStatement.setNull(3, java.sql.Types.BOOLEAN);
                                }
                                else
                                {
                                    callableStatement.setBoolean(3 , ( ( Boolean[] ) value )[ 0 ].booleanValue());
                                }
                                if ( ( ( Boolean[] ) value )[ 1 ] == null)
                                {
                                    callableStatement.setNull(4, java.sql.Types.BOOLEAN);
                                }
                                else
                                {
                                    callableStatement.setBoolean(4 , ( ( Boolean[] ) value )[ 1 ].booleanValue());
                                }
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                            case TangoConst.Tango_DEV_USHORT:
                                if ( ( ( Short[] ) value )[ 0 ] == null)
                                {
                                    callableStatement.setNull(3, java.sql.Types.NUMERIC);
                                }
                                else
                                {
                                    callableStatement.setShort(3 , ( ( Short[] ) value )[ 0 ].shortValue());
                                }
                                if ( ( ( Short[] ) value )[ 1 ] == null)
                                {
                                    callableStatement.setNull(4, java.sql.Types.NUMERIC);
                                }
                                else
                                {
                                    callableStatement.setShort(4 , ( ( Short[] ) value )[ 1 ].shortValue());
                                }
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                if ( ( ( Float[] ) value )[ 0 ] == null)
                                {
                                    callableStatement.setNull(3, java.sql.Types.NUMERIC);
                                }
                                else
                                {
                                    callableStatement.setFloat(3 , ( ( Float[] ) value )[ 0 ].floatValue());
                                }
                                if ( ( ( Float[] ) value )[ 1 ] == null)
                                {
                                    callableStatement.setNull(4, java.sql.Types.NUMERIC);
                                }
                                else
                                {
                                    callableStatement.setFloat(4 , ( ( Float[] ) value )[ 1 ].floatValue());
                                }
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                if ( (( Double[] ) value )[ 0 ] == null || (( Double[] ) value )[ 0 ].isNaN() )
                                {
                                    // XXX : Should not replace NaN by null
                                    callableStatement.setNull(3,java.sql.Types.NUMERIC);    
                                }
                                else
                                {
                                    callableStatement.setDouble(3 , ( ( Double[] ) value )[ 0 ].doubleValue());
                                }
                                if ( (( Double[] ) value )[ 1 ] == null || (( Double[] ) value )[ 1 ].isNaN() )
                                {
                                    // XXX : Should not replace NaN by null
                                    callableStatement.setNull(4,java.sql.Types.NUMERIC);    
                                }
                                else
                                {
                                callableStatement.setDouble(4 , ( ( Double[] ) value )[ 1 ].doubleValue());
                                }
                                break;
                            default:
                                if ( (( Double[] ) value )[ 0 ] == null || (( Double[] ) value )[ 0 ].isNaN() )
                                {
                                    // XXX : Should not replace NaN by null
                                    callableStatement.setNull(3,java.sql.Types.NUMERIC);    
                                }
                                else
                                {
                                    callableStatement.setDouble(3 , ( ( Double[] ) value )[ 0 ].doubleValue());
                                }
                                if ( (( Double[] ) value )[ 1 ] == null || (( Double[] ) value )[ 1 ].isNaN() )
                                {
                                    // XXX : Should not replace NaN by null
                                    callableStatement.setNull(4,java.sql.Types.NUMERIC);    
                                }
                                else
                                {
                                    callableStatement.setDouble(4 , ( ( Double[] ) value )[ 1 ].doubleValue());
                                }
                        }
                    }

                }
                else
                {
                    if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
                    {
                        switch ( scalarEvent.getData_type() )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                callableStatement.setNull(3 , java.sql.Types.VARCHAR);
                                break;
                            case TangoConst.Tango_DEV_STATE:
                            case TangoConst.Tango_DEV_UCHAR:
                            case TangoConst.Tango_DEV_LONG:
                            case TangoConst.Tango_DEV_ULONG:
                            case TangoConst.Tango_DEV_BOOLEAN:
                            case TangoConst.Tango_DEV_SHORT:
                            case TangoConst.Tango_DEV_USHORT:
                            case TangoConst.Tango_DEV_FLOAT:
                            case TangoConst.Tango_DEV_DOUBLE:
                            default:
                                callableStatement.setNull(3 , java.sql.Types.NUMERIC);
                        }
                    }
                    else
                    {
                        switch ( scalarEvent.getData_type() )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                callableStatement.setNull(3 , java.sql.Types.VARCHAR);
                                callableStatement.setNull(4 , java.sql.Types.VARCHAR);
                                break;
                            case TangoConst.Tango_DEV_STATE:
                            case TangoConst.Tango_DEV_UCHAR:
                            case TangoConst.Tango_DEV_LONG:
                            case TangoConst.Tango_DEV_ULONG:
                            case TangoConst.Tango_DEV_BOOLEAN:
                            case TangoConst.Tango_DEV_SHORT:
                            case TangoConst.Tango_DEV_USHORT:
                            case TangoConst.Tango_DEV_FLOAT:
                            case TangoConst.Tango_DEV_DOUBLE:
                            default:
                                callableStatement.setNull(3 , java.sql.Types.NUMERIC);
                                callableStatement.setNull(4 , java.sql.Types.NUMERIC);
                        }
                    }

                }
                try
                {
                    callableStatement.executeUpdate();                  
                    close(callableStatement);
                }
                catch ( SQLException e )
                {
                    close(callableStatement);
                    e.printStackTrace();
                    throw e;
                }
            }
            catch ( SQLException e )
            {
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.INSERT_FAILURE;
                String desc = "Failed while executing DataBaseApi.insert_ScalarData_RO() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
    }

	public int getBufferedAttID(String name) throws ArchivingException 
    {
        if (canceled) return 0;
        Integer idI = (Integer) this.idsBuffer.get ( name );
        int id;
        
        if ( idI == null )
        {
            id = getAttID ( name );
            //System.out.println ( "CLA/getBufferedAttID/BUFFERING for attr|"+name+"|id|"+id );
            
            idI = new Integer ( id );
            this.idsBuffer.put ( name , idI );
        }
        else
        {
            id = idI.intValue ();
            //System.out.println ( "CLA/getBufferedAttID/USING BUFFER for attr|"+name+"|id|"+id );
        }
        
        return id;
    }

    /**
	 * <b>Description : </b> Inserts a spectrum type attribute's data
	 *
	 * @param hdbSpectrumEvent_RO an object that contains the attribute's name, the timestamp's value and the value.
	 */
	public void insert_SpectrumData_RO(SpectrumEvent_RO hdbSpectrumEvent_RO) throws ArchivingException
	{
        if (canceled) return;
		String att_name = hdbSpectrumEvent_RO.getAttribute_complete_name().trim();
		StringBuffer tableName = new StringBuffer().append(getDbSchema()).append(".").append(getTableName(att_name));
		long time = hdbSpectrumEvent_RO.getTimeStamp();
		Timestamp timeSt = new Timestamp(time);
		int dim_x = hdbSpectrumEvent_RO.getDim_x();
		int data_type = -1;
		Double[]  dvalue  = null;
		Float[]   fvalue  = null;
		Integer[]     lvalue  = null;
		Short[]   svalue  = null;
		Byte[]    cvalue  = null;
		Boolean[] bvalue  = null;
		String[]  stvalue = null;
		if (hdbSpectrumEvent_RO.getValue() instanceof Double[])
		{
			dvalue = (Double[])hdbSpectrumEvent_RO.getValue();
			data_type = TangoConst.Tango_DEV_DOUBLE;
		}
		else if (hdbSpectrumEvent_RO.getValue() instanceof Float[])
		{
			fvalue = (Float[])hdbSpectrumEvent_RO.getValue();
			data_type = TangoConst.Tango_DEV_FLOAT;
		}
		else if (hdbSpectrumEvent_RO.getValue() instanceof Integer[])
		{
			lvalue = (Integer[])hdbSpectrumEvent_RO.getValue();
			data_type = TangoConst.Tango_DEV_LONG;
		}
		else if (hdbSpectrumEvent_RO.getValue() instanceof Short[])
		{
			svalue = (Short[])hdbSpectrumEvent_RO.getValue();
			data_type = TangoConst.Tango_DEV_SHORT;
		}
		else if (hdbSpectrumEvent_RO.getValue() instanceof Byte[])
		{
			cvalue = (Byte[])hdbSpectrumEvent_RO.getValue();
			data_type = TangoConst.Tango_DEV_CHAR;
		}
		else if (hdbSpectrumEvent_RO.getValue() instanceof Boolean[])
		{
			bvalue = (Boolean[])hdbSpectrumEvent_RO.getValue();
			data_type = TangoConst.Tango_DEV_BOOLEAN;
		}
		else if (hdbSpectrumEvent_RO.getValue() instanceof String[])
		{
			stvalue = (String[])hdbSpectrumEvent_RO.getValue();
			data_type = TangoConst.Tango_DEV_STRING;
		}
		StringBuffer valueStr = new StringBuffer();
		StringBuffer query = new StringBuffer();

		// First connect with the database
		if ( getAutoConnect() )
			connect();

		// Create and execute the SQL query string
		// Build the query string
		StringBuffer tableFields = new StringBuffer().append(ConfigConst.TAB_SPECTRUM_RO[ 0 ]).append(", ").append(ConfigConst.TAB_SPECTRUM_RO[ 1 ]).append(", ").append(ConfigConst.TAB_SPECTRUM_RO[ 2 ]);
        switch(data_type)
        {
            case TangoConst.Tango_DEV_DOUBLE:
                if (dvalue == null) valueStr = null;
                else
                {
                    for ( int i = 0 ; i < dvalue.length - 1 ; i++ )
                    {
                        valueStr.append(dvalue[ i ]).append(GlobalConst.CLOB_SEPARATOR).append(" ");
                    }
                    if (dvalue.length > 0)
                    {
                        valueStr.append(dvalue[ dvalue.length - 1 ]);
                    }
                }
                break;

            case TangoConst.Tango_DEV_FLOAT:
                if (fvalue == null) valueStr = null;
                else
                {
                    for ( int i = 0 ; i < fvalue.length - 1 ; i++ )
                    {
                        valueStr.append(fvalue[ i ]).append(GlobalConst.CLOB_SEPARATOR).append(" ");
                    }
                    if (fvalue.length > 0)
                    {
                        valueStr.append(fvalue[ fvalue.length - 1 ]);
                    }
                }
                break;

            case TangoConst.Tango_DEV_LONG:
                if (lvalue == null) valueStr = null;
                else
                {
                    for ( int i = 0 ; i < lvalue.length - 1 ; i++ )
                    {
                        valueStr.append(lvalue[ i ]).append(GlobalConst.CLOB_SEPARATOR).append(" ");
                    }
                    if (lvalue.length > 0)
                    {
                        valueStr.append(lvalue[ lvalue.length - 1 ]);
                    }
                }
                break;

            case TangoConst.Tango_DEV_SHORT:
            case TangoConst.Tango_DEV_USHORT:
                if (svalue == null) valueStr = null;
                else
                {
                    for ( int i = 0 ; i < svalue.length - 1 ; i++ )
                    {
                        valueStr.append(svalue[ i ]).append(GlobalConst.CLOB_SEPARATOR).append(" ");
                    }
                    if (svalue.length > 0)
                    {
                        valueStr.append(svalue[ svalue.length - 1 ]);
                    }
                }
                break;

            case TangoConst.Tango_DEV_CHAR:
                if (cvalue == null) valueStr = null;
                else
                {
                    for ( int i = 0 ; i < cvalue.length - 1 ; i++ )
                    {
                        valueStr.append(cvalue[ i ]).append(GlobalConst.CLOB_SEPARATOR).append(" ");
                    }
                    if (cvalue.length > 0)
                    {
                        valueStr.append(cvalue[ cvalue.length - 1 ]);
                    }
                }
                break;

            case TangoConst.Tango_DEV_BOOLEAN:
                if (bvalue == null) valueStr = null;
                else
                {
                    for ( int i = 0 ; i < bvalue.length - 1 ; i++ )
                    {
                        valueStr.append(bvalue[ i ]).append(GlobalConst.CLOB_SEPARATOR).append(" ");
                    }
                    if (bvalue.length > 0)
                    {
                        valueStr.append(bvalue[ bvalue.length - 1 ]);
                    }
                }
                break;

            case TangoConst.Tango_DEV_STRING:
                if (stvalue == null) valueStr = null;
                else
                {
                    for ( int i = 0 ; i < stvalue.length - 1 ; i++ )
                    {
                        valueStr.append(StringFormater.formatStringToWrite( stvalue[ i ] )).append(GlobalConst.CLOB_SEPARATOR).append(" ");
                    }
                    if (stvalue.length > 0)
                    {
                        valueStr.append(StringFormater.formatStringToWrite( stvalue[ stvalue.length - 1 ] ));
                    }
                }
                break;
        }
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			PreparedStatement preparedStatement;
			query.append("INSERT INTO ").append(tableName).append(" (").append(tableFields).append(")").append(" VALUES (?, ?, ?)");

			try
			{
				preparedStatement = dbconn.prepareStatement(query.toString());
                lastStatement = preparedStatement;
                if (canceled) return;
				preparedStatement.setTimestamp(1 , timeSt);
				preparedStatement.setInt(2 , dim_x);
                if (valueStr == null)
                {
                    preparedStatement.setNull(3 , java.sql.Types.BLOB);
                }
                else
                {
                    preparedStatement.setString(3 , valueStr.toString());
                }
				try
				{
					preparedStatement.executeUpdate();
					close(preparedStatement);
				}
				catch ( SQLException e )
				{
					close(preparedStatement);
					throw e;
				}
			}
			catch ( SQLException e )
			{
				String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.INSERT_FAILURE;
				String desc = "Failed while executing DataBaseApi.insert_SpectrumData_RO() method...";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
		}
		else if ( db_type == ConfigConst.BD_ORACLE )
		{

			CallableStatement cstmt_ins_spectrum_ro;
			//ins_sptest(att varchar2, vartime timestamp, dimension number, bigstrrec long)
			query = new StringBuffer().append("{call ").append(getDbSchema()).append(".ins_sp_1val (?, ?, ?, ?)}");

			try
			{

				cstmt_ins_spectrum_ro = dbconn.prepareCall(query.toString());
                lastStatement = cstmt_ins_spectrum_ro;
                if (canceled) return;
				cstmt_ins_spectrum_ro.setString(1 , att_name);
				cstmt_ins_spectrum_ro.setTimestamp(2 , timeSt);
				cstmt_ins_spectrum_ro.setInt(3 , dim_x);
                if (valueStr == null)
                {
                    cstmt_ins_spectrum_ro.setNull(4 , java.sql.Types.CLOB);
                }
                else
                {
                    cstmt_ins_spectrum_ro.setString(4 , valueStr.toString());
                }

                try
                {
				cstmt_ins_spectrum_ro.executeUpdate();
				close(cstmt_ins_spectrum_ro);
			}
			catch ( SQLException e )
			{
                	close(cstmt_ins_spectrum_ro);
                	throw e;
                }
			}
			catch ( SQLException e )
			{
				String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.INSERT_FAILURE;
				String desc = "Failed while executing DataBaseApi.insert_SpectrumData_RO() method..." +
				              "\r\n\t\t Query : " + query +
				              "\r\n\t\t Param 1 (Attribute name) : " + att_name +
				              "\r\n\t\t Param 2 (Timestamp)      : " + timeSt.toString() +
				              "\r\n\t\t Param 3 (Dimension)      : " + dim_x +
				              "\r\n\t\t Param 4 (Value)          : " + valueStr.toString() +
				              "\r\n\t\t Code d'erreur : " + e.getErrorCode() +
				              "\r\n\t\t Message : " + e.getMessage() +
				              "\r\n\t\t SQL state : " + e.getSQLState() +
				              "\r\n\t\t Stack : ";
				e.printStackTrace();

				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
		}
// Close the connection with the database
		if ( getAutoConnect() )
			close();
	}

	public void insert_SpectrumData_RW(SpectrumEvent_RW hdbSpectrumEvent_RW) throws ArchivingException
	{
        if (canceled) return;
		String att_name = hdbSpectrumEvent_RW.getAttribute_complete_name().trim();
		StringBuffer tableName = new StringBuffer().append(getDbSchema()).append(".").append(getTableName(att_name));
		long time = hdbSpectrumEvent_RW.getTimeStamp();
		Timestamp timeSt = new Timestamp(time);
		int dim_x = hdbSpectrumEvent_RW.getDim_x();
		//System.out.println ( "CLA/insert_SpectrumData_RW/dim_x/"+dim_x );
		//double[] valueRead = hdbSpectrumEvent_RW.getSpectrumValueRW();
		int data_type = -1;
		Double[]  dvalueRead  = null, dvalueWrite   = null;
		Float[]   fvalueRead  = null, fvalueWrite   = null;
        Integer[] lvalueRead  = null, lvalueWrite   = null;
		Short[]   svalueRead  = null, svalueWrite   = null;
		Byte[]    cvalueRead  = null, cvalueWrite   = null;
		Boolean[] bvalueRead  = null, bvalueWrite   = null;
		String[]  stvalueRead = null, stvalueWrite  = null;
		
        //int readLength = 0;
        //int writeLength = 0;
        
        if (hdbSpectrumEvent_RW.getValue() instanceof Double[])
		{
			dvalueRead = (Double[])hdbSpectrumEvent_RW.getSpectrumValueRWRead();
			dvalueWrite = (Double[])hdbSpectrumEvent_RW.getSpectrumValueRWWrite();
			data_type = TangoConst.Tango_DEV_DOUBLE;
            
            //readLength = dvalueRead.length;
            //writeLength = dvalueWrite.length;
		}
		else if (hdbSpectrumEvent_RW.getValue() instanceof Float[])
		{
			fvalueRead = (Float[])hdbSpectrumEvent_RW.getSpectrumValueRWRead();
			fvalueWrite = (Float[])hdbSpectrumEvent_RW.getSpectrumValueRWWrite();
			data_type = TangoConst.Tango_DEV_FLOAT;
            
            //readLength = fvalueRead.length;
            //writeLength = fvalueWrite.length;
		}
		else if (hdbSpectrumEvent_RW.getValue() instanceof Integer[])
		{
			lvalueRead = (Integer[])hdbSpectrumEvent_RW.getSpectrumValueRWRead();
			lvalueWrite = (Integer[])hdbSpectrumEvent_RW.getSpectrumValueRWWrite();
			data_type = TangoConst.Tango_DEV_LONG;
            
            //readLength = lvalueRead.length;
            //writeLength = lvalueWrite.length;
		}
		else if (hdbSpectrumEvent_RW.getValue() instanceof Short[])
		{
			svalueRead = (Short[])hdbSpectrumEvent_RW.getSpectrumValueRWRead();
			svalueWrite = (Short[])hdbSpectrumEvent_RW.getSpectrumValueRWWrite();
			data_type = TangoConst.Tango_DEV_SHORT;
            
            //readLength = svalueRead.length;
            //writeLength = svalueWrite.length;
		}
		else if (hdbSpectrumEvent_RW.getValue() instanceof Byte[])
		{
			cvalueRead = (Byte[])hdbSpectrumEvent_RW.getSpectrumValueRWRead();
			cvalueWrite = (Byte[])hdbSpectrumEvent_RW.getSpectrumValueRWWrite();
			data_type = TangoConst.Tango_DEV_CHAR;
            
            //readLength = cvalueRead.length;
            //writeLength = cvalueWrite.length;
		}
		else if (hdbSpectrumEvent_RW.getValue() instanceof Boolean[])
		{
			bvalueRead = (Boolean[])hdbSpectrumEvent_RW.getSpectrumValueRWRead();
			bvalueWrite = (Boolean[])hdbSpectrumEvent_RW.getSpectrumValueRWWrite();
			data_type = TangoConst.Tango_DEV_BOOLEAN;
            
            //readLength = bvalueRead.length;
            //writeLength = bvalueWrite.length;
		}
		else if (hdbSpectrumEvent_RW.getValue() instanceof String[])
		{
			stvalueRead = (String[])hdbSpectrumEvent_RW.getSpectrumValueRWRead();
			stvalueWrite = (String[])hdbSpectrumEvent_RW.getSpectrumValueRWWrite();
			data_type = TangoConst.Tango_DEV_STRING;
            
            //readLength = stvalueRead.length;
            //writeLength = stvalueWrite.length;
		}
        
		StringBuffer valueReadStr = new StringBuffer();
		StringBuffer valueWriteStr = new StringBuffer();
		StringBuffer query = new StringBuffer();
        
        switch(data_type)
        {
            case TangoConst.Tango_DEV_DOUBLE:
                if (dvalueRead == null) valueReadStr = null;
                else
                {
                    for ( int i = 0 ; i < dvalueRead.length ; i++ )
                    {
                        valueReadStr.append(dvalueRead[ i ]);
                        if ( i < dvalueRead.length - 1 )
                        {
                            valueReadStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");
                        }                        
                    }
                }

                if (dvalueWrite == null) valueWriteStr = null;
                else
                {
                    for ( int i = 0 ; i < dvalueWrite.length ; i++ )
                    {
                        valueWriteStr.append(dvalueWrite[ i ]);
                        if ( i < dvalueWrite.length - 1 )
                        {
                            valueWriteStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");    
                        }
                    }
                }
                break;

            case TangoConst.Tango_DEV_FLOAT:
                if (fvalueRead == null) valueReadStr = null;
                else
                {
                    for ( int i = 0 ; i < fvalueRead.length ; i++ )
                    {
                        valueReadStr.append(fvalueRead[ i ]);
                        if ( i < fvalueRead.length - 1 )
                        {
                            valueReadStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");     
                        }
                    }
                }
                
                if (fvalueWrite == null) valueWriteStr = null;
                else
                {
                    for ( int i = 0 ; i < fvalueWrite.length ; i++ )
                    {
                        valueWriteStr.append(fvalueWrite[ i ]);
                        if ( i < fvalueWrite.length - 1 )
                        {
                            valueWriteStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");    
                        }
                    }
                }
                break;

            case TangoConst.Tango_DEV_LONG:
                if (lvalueRead == null) valueReadStr = null;
                else
                {
                    for ( int i = 0 ; i < lvalueRead.length ; i++ )
                    {
                        valueReadStr.append(lvalueRead[ i ]);
                        if ( i < lvalueRead.length - 1 )
                        {
                            valueReadStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");    
                        }
                    }
                }

                if (lvalueWrite == null) valueWriteStr = null;
                else
                {
                    for ( int i = 0 ; i < lvalueWrite.length ; i++ )
                    {
                        valueWriteStr.append(lvalueWrite[ i ]);
                        if ( i < lvalueWrite.length - 1 )
                        {
                            valueWriteStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");
                        }
                    }
                }
                break;

            case TangoConst.Tango_DEV_SHORT:
            case TangoConst.Tango_DEV_USHORT:
                if (svalueRead == null) valueReadStr = null;
                else
                {
                    for ( int i = 0 ; i < svalueRead.length ; i++ )
                    {
                        valueReadStr.append(svalueRead[ i ]);
                        if ( i < svalueRead.length - 1 )
                        {
                            valueReadStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");    
                        }
                    }
                }

                if (svalueWrite == null) valueWriteStr = null;
                else
                {
                    for ( int i = 0 ; i < svalueWrite.length ; i++ )
                    {
                        valueWriteStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");
                        if ( i < svalueWrite.length - 1 )
                        {
                            valueWriteStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");    
                        }
                    }
                }
                break;

            case TangoConst.Tango_DEV_CHAR:
                if (cvalueRead == null) valueReadStr = null;
                else
                {
                    for ( int i = 0 ; i < cvalueRead.length ; i++ )
                    {
                        valueReadStr.append(cvalueRead[ i ]);
                        if ( i < cvalueRead.length - 1 )
                        {
                            valueReadStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");    
                        }
                    }
                }
                
                if (cvalueWrite == null) valueWriteStr = null;
                else
                {
                    for ( int i = 0 ; i < cvalueWrite.length ; i++ )
                    {
                        valueWriteStr.append(cvalueWrite[ i ]);
                        if ( i < cvalueWrite.length - 1 )
                        {
                            valueWriteStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");    
                        }
                    }
                }

                break;

            case TangoConst.Tango_DEV_BOOLEAN:
                if (bvalueRead == null) valueReadStr = null;
                else
                {
                    for ( int i = 0 ; i < bvalueRead.length ; i++ )
                    {
                        valueReadStr.append(bvalueRead[ i ]);
                        if ( i < bvalueRead.length - 1 )
                        {
                            valueReadStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");    
                        }
                    }
                }
                
                if (bvalueWrite == null) valueWriteStr = null;
                else
                {
                    for ( int i = 0 ; i < bvalueWrite.length ; i++ )
                    {
                        valueWriteStr.append(bvalueWrite[ i ]);
                        if ( i < bvalueWrite.length - 1 )
                        {
                            valueWriteStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");                            
                        }
                    }
                }

                break;

            case TangoConst.Tango_DEV_STRING:
                if (stvalueRead == null) valueReadStr = null;
                else
                {
                    for ( int i = 0 ; i < stvalueRead.length ; i++ )
                    {
                        valueReadStr.append(StringFormater.formatStringToWrite( stvalueRead[ i ] ));
                        if ( i < stvalueRead.length - 1 )
                        {
                            valueReadStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");                            
                        }
                    }
                }

                if (stvalueWrite == null) valueWriteStr = null;
                else
                {
                    for ( int i = 0 ; i < stvalueWrite.length ; i++ )
                    {
                        valueWriteStr.append(stvalueWrite[ i ]);
                        if ( i < stvalueWrite.length - 1 )
                        {
                            valueWriteStr.append(GlobalConst.CLOB_SEPARATOR).append(" ");    
                        }
                    }
                }
                break;
        }

		// First connect with the database
		if ( getAutoConnect() )
			connect();

		// Create and execute the SQL query string
		// Build the query string
		StringBuffer tableFields = new StringBuffer().append(ConfigConst.TAB_SPECTRUM_RW[ 0 ]).append(", ").append(ConfigConst.TAB_SPECTRUM_RW[ 1 ]).append(", ").append(ConfigConst.TAB_SPECTRUM_RW[ 2 ]).append(", ").append(ConfigConst.TAB_SPECTRUM_RW[ 3 ]);
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			PreparedStatement preparedStatement;
			query.append("INSERT INTO ").append(tableName).append(" (").append(tableFields).append(")").append(" VALUES (?, ?, ?, ?)");
			//System.out.println ( "query/"+query.toString() );
			
			try
			{
				preparedStatement = dbconn.prepareStatement(query.toString());
                lastStatement = preparedStatement;
                if (canceled) return;
				preparedStatement.setTimestamp(1 , timeSt);
				preparedStatement.setInt(2 , dim_x);
                if (valueReadStr == null)
                {
                    preparedStatement.setNull(3, java.sql.Types.BLOB);
                }
                else
                {
                    preparedStatement.setString(3 , valueReadStr.toString());
                }
                if (valueWriteStr == null)
                {
                    preparedStatement.setNull(4, java.sql.Types.BLOB);
                }
                else
                {
                    preparedStatement.setString(4 , valueWriteStr.toString());
                }
				try
				{
					preparedStatement.executeUpdate();
					close(preparedStatement);
				}
				catch ( SQLException e )
				{
					close(preparedStatement);
					throw e;
				}
			}
			catch ( SQLException e )
			{
				String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.INSERT_FAILURE;
				String desc = "Failed while executing DataBaseApi.insert_SpectrumData_RO() method...";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
		}
		else if ( db_type == ConfigConst.BD_ORACLE )
		{

			CallableStatement cstmt_ins_spectrum_rw;
			query = new StringBuffer().append("{call ").append(getDbSchema()).append(".ins_sp_2val (?, ?, ?, ?, ?)}");
			
			try
			{
				cstmt_ins_spectrum_rw = dbconn.prepareCall(query.toString());
                lastStatement = cstmt_ins_spectrum_rw;
                if (canceled) return;
				cstmt_ins_spectrum_rw.setString(1 , att_name);
				cstmt_ins_spectrum_rw.setTimestamp(2 , timeSt);
				cstmt_ins_spectrum_rw.setInt(3 , dim_x);
                if (valueReadStr == null)
                {
                    cstmt_ins_spectrum_rw.setNull(4, java.sql.Types.CLOB);
                }
                else
                {
                    cstmt_ins_spectrum_rw.setString(4 , valueReadStr.toString());
                }
                if (valueWriteStr == null)
                {
                    cstmt_ins_spectrum_rw.setNull(5, java.sql.Types.CLOB);
                }
                else
                {
                    cstmt_ins_spectrum_rw.setString(5 , valueWriteStr.toString());
                }

                try
                {
				cstmt_ins_spectrum_rw.executeUpdate();
				close(cstmt_ins_spectrum_rw);
            	}
            	catch(SQLException e)
            	{
            		close(cstmt_ins_spectrum_rw);
            		throw e;
            	}

			}
			catch ( SQLException e )
			{
				String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.INSERT_FAILURE;
				String desc = "Failed while executing DataBaseApi.insert_SpectrumData_RO() method..." +
				              "\r\n\t\t Query : " + query +
				              "\r\n\t\t Param 1 (Attribute name) : " + att_name +
				              "\r\n\t\t Param 2 (Timestamp)      : " + timeSt.toString() +
				              "\r\n\t\t Param 3 (Dimension)      : " + dim_x +
				              "\r\n\t\t Param 4 (Value Read)     : " + valueReadStr.toString() +
				              "\r\n\t\t Param 5 (Value Write)    : " + valueWriteStr.toString() +
				              "\r\n\t\t Code d'erreur : " + e.getErrorCode() +
				              "\r\n\t\t Message : " + e.getMessage() +
				              "\r\n\t\t SQL state : " + e.getSQLState() +
				              "\r\n\t\t Stack : ";
				e.printStackTrace();

				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
		}
// Close the connection with the database
		if ( getAutoConnect() )
			close();
	}

	/**
	 * <b>Description : </b> Inserts an image type attribute's data
	 *
	 * @param imageEvent_RO an object that contains the attribute's name, the timestamp's value and the value.
	 * @throws ArchivingException
	 */
	public void insert_ImageData_RO(ImageEvent_RO imageEvent_RO) throws SQLException , IOException, ArchivingException
	{
        if (canceled) return;
		//Runtime runtime = Runtime.getRuntime ();
		//long freeMemory = runtime.freeMemory (); 
		//long maxMemory = runtime.maxMemory ();
		//long totalMemory = runtime.totalMemory ();
	    //System.out.println ( "CLA 0/freeMemory/"+freeMemory+"/maxMemory/"+maxMemory+"/totalMemory/"+totalMemory+"/" );
	    
		//new imageManagerThread(imageEvent_RO).start();
		String att_name = imageEvent_RO.getAttribute_complete_name().trim();
		StringBuffer tableName = new StringBuffer().append(getDbSchema()).append(".").append(getTableName(att_name));
		long time = imageEvent_RO.getTimeStamp();
		Timestamp timeSt = new Timestamp(time);
		
		int dim_x = imageEvent_RO.getDim_x();
		int dim_y = imageEvent_RO.getDim_y();
		/*int dim_x = 50;
		int dim_y = 50;*/
		
		int data_type = -1;

		//System.out.println("DataBaseApi.insert_ImageData_RO/dim_x/"+dim_x+"/dim_y/"+dim_y);
		
		Double[][] dvalue = null;
		
		if (imageEvent_RO.getValue() instanceof Double [][])
		{
			dvalue  = (Double[][])imageEvent_RO.getValue();
			data_type = TangoConst.Tango_DEV_DOUBLE;
	    }
		else
		{
		    return;
		}

		StringBuffer valueStr = new StringBuffer();
		StringBuffer query = new StringBuffer();

		// First connect with the database
		if ( getAutoConnect() )
		{
		    connect();
		}
			

		// Create and execute the SQL query string
		// Build the query string
		StringBuffer tableFields = new StringBuffer().append(ConfigConst.TAB_IMAGE_RO[ 0 ]).append(", ").append(ConfigConst.TAB_IMAGE_RO[ 1 ]).append(", ").append(ConfigConst.TAB_IMAGE_RO[ 2 ]).append(", ").append(ConfigConst.TAB_IMAGE_RO[ 3 ]);
		if (dvalue != null)
        {
            switch(data_type)
            {
                case TangoConst.Tango_DEV_DOUBLE:
                    for ( int i = 0 ; i < dim_y ; i++ )
                    {
                        for ( int j = 0 ; j < dim_x ; j++ )
                        {   
                            valueStr.append ( dvalue[ i ] [ j ] );
                            if ( j < dim_x - 1 )
                            {
                                valueStr.append(GlobalConst.CLOB_SEPARATOR_IMAGE_COLS).append(" ");
                            }
                        }
                        
                        if ( i < dim_y - 1 )
                        {
                            valueStr.append(GlobalConst.CLOB_SEPARATOR_IMAGE_ROWS).append(" ");
                        }
                    }
                break;
            }
        }
		
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			PreparedStatement preparedStatement;
			query.append("INSERT INTO ").append(tableName).append(" (").append(tableFields).append(")").append(" VALUES (?, ?, ?, ?)");
			
			try
			{
				preparedStatement = dbconn.prepareStatement(query.toString());
                lastStatement = preparedStatement;
                if (canceled) return;
				preparedStatement.setTimestamp(1 , timeSt);
				preparedStatement.setInt(2 , dim_x);
				preparedStatement.setInt(3 , dim_y);
                if (dvalue == null)
                {
                    preparedStatement.setNull(4 , java.sql.Types.BLOB);
                }
                else preparedStatement.setString(4 , valueStr.toString());
				try
				{
				    preparedStatement.executeUpdate();
					close(preparedStatement);
					
					//System.out.println ( "DataBaseApi/insert_ImageData_RO/DONE!!" );
				}
				catch ( SQLException e )
				{
					close(preparedStatement);
					throw e;
				}
			}
			catch ( SQLException e )
			{
				String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.INSERT_FAILURE;
				String desc = "Failed while executing DataBaseApi.insert_ImageData_RO() method...";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
		}
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
		    CallableStatement cstmt_ins_spectrum_ro;
			//ins_sptest(att varchar2, vartime timestamp, dimension number, bigstrrec long)
			query = new StringBuffer().append("{call ").append(getDbSchema()).append(".ins_im_1val (?, ?, ?, ?, ?)}");

			//System.out.println ( "CLA/INSERTING IMAGE VALUE OF LENGTH/" + valueStr.toString().length() );
			try
			{
				cstmt_ins_spectrum_ro = dbconn.prepareCall(query.toString());
                lastStatement = cstmt_ins_spectrum_ro;
                if (canceled) return;
				cstmt_ins_spectrum_ro.setString(1 , att_name);
				cstmt_ins_spectrum_ro.setTimestamp(2 , timeSt);
				cstmt_ins_spectrum_ro.setInt(3 , dim_x);
                cstmt_ins_spectrum_ro.setInt(4 , dim_y);
                if (dvalue == null)
                {
                    cstmt_ins_spectrum_ro.setNull(4 , java.sql.Types.CLOB);
                }
                else cstmt_ins_spectrum_ro.setString(5 , valueStr.toString());

				cstmt_ins_spectrum_ro.executeUpdate();
				close(cstmt_ins_spectrum_ro);

			}
			catch ( SQLException e )
			{
				String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.INSERT_FAILURE;
				String desc = "Failed while executing DataBaseApi.insert_ImageData_RO() method..." +
				              "\r\n\t\t Query : " + query +
				              "\r\n\t\t Param 1 (Attribute name) : " + att_name +
				              "\r\n\t\t Param 2 (Timestamp)      : " + timeSt.toString() +
				              "\r\n\t\t Param 3 (X Dimension)    : " + dim_x +
                              "\r\n\t\t Param 3 (Y Dimension)    : " + dim_y +
				              "\r\n\t\t Param 4 (Value)          : " + valueStr.toString() +
				              "\r\n\t\t Code d'erreur : " + e.getErrorCode() +
				              "\r\n\t\t Message : " + e.getMessage() +
				              "\r\n\t\t SQL state : " + e.getSQLState() +
				              "\r\n\t\t Stack : ";
				e.printStackTrace();

				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}

		}
// Close the connection with the database
		if ( getAutoConnect() )
		{
			close();
		}
	}

	public void insert_ImageData_RO_Small(ImageEvent_RO hdbImageEvent_ro) throws SQLException , ArchivingException
	{
        if (canceled) return;
		// developer note solution 1
		// M�thode inserrant les matrices au sein m�me de la BD (insertion par ligne)
		// Matrice de petites taille
		CallableStatement cstmt_insert_ImageData_R;
		CallableStatement cstmt_reclob_ImageData_R;
		StringBuffer myStatement1 = new StringBuffer().append("{call ").append(getDbSchema()).append(".rec_lob(?, ?, ?, ?)}");
		StringBuffer myStatement2 = new StringBuffer().append("{call ").append(getDbSchema()).append(".feed_lob(?, ?, ?)}");
		int num_row = 0;
		int num_column = 0;
		try
		{
			cstmt_reclob_ImageData_R = dbconn.prepareCall(myStatement1.toString());
            lastStatement = cstmt_reclob_ImageData_R;
            if (canceled) return;
			cstmt_insert_ImageData_R = dbconn.prepareCall(myStatement2.toString());
			cstmt_reclob_ImageData_R.setString(1 , name);
			cstmt_reclob_ImageData_R.setTimestamp(2 , new Timestamp(hdbImageEvent_ro.getTimeStamp()));
			cstmt_reclob_ImageData_R.setInt(3 , hdbImageEvent_ro.getDim_x());
			cstmt_reclob_ImageData_R.setInt(4 , hdbImageEvent_ro.getDim_y());
			cstmt_reclob_ImageData_R.executeUpdate();
            lastStatement = cstmt_insert_ImageData_R;
            if (canceled) return;

			cstmt_insert_ImageData_R.setString(1 , name);
			cstmt_insert_ImageData_R.setTimestamp(2 , new Timestamp(hdbImageEvent_ro.getTimeStamp()));
            if (hdbImageEvent_ro.getImageValueRO() == null)
            {
                cstmt_insert_ImageData_R.setNull(3 , java.sql.Types.CLOB);
                cstmt_insert_ImageData_R.executeUpdate();
            }
            else
            {
                for ( int i = 0 ; i < hdbImageEvent_ro.getImageValueRO().length ; i++ )
                {    // lignes
                    StringBuffer current_ligne = new StringBuffer();
                    num_row = i;
                    for ( int j = 0 ; j < ( hdbImageEvent_ro.getImageValueRO()[ i ].length ) ; j++ )
                    { // colonnes
                        current_ligne.append(hdbImageEvent_ro.getImageValueRO()[ i ][ j ]).append(", ");
                        num_column = j;
                    }
                    cstmt_insert_ImageData_R.setString(3 , current_ligne.toString());
                    cstmt_insert_ImageData_R.executeUpdate();
                }
            }
			close(cstmt_insert_ImageData_R);
		}
		catch ( SQLException e )
		{
			System.err.println("ERROR !! " + "\r\n" +
			                   "\t Origin : \t " + "DataBaseApi.insert_ImageData_RO_Small" + "\r\n" +
			                   "\t Reason : \t " + getDbSchema().toUpperCase().trim() + "_FAILURE" + "\r\n" +
			                   "\t Description : \t " + e.getMessage() + "\r\n" +
			                   "\t Additional information : \r\n" +
			                   "\t\t Statement 1 : " + myStatement1 + "\r\n" +
			                   "\t\t\t 1) name : " + name +
			                   "\t\t\t 2) timestamp : " + ( new Timestamp(hdbImageEvent_ro.getTimeStamp()) ).toString() + "\r\n" +
			                   "\t\t\t 3) dim_x : " + hdbImageEvent_ro.getDim_x() + "\r\n" +
			                   "\t\t\t 4) dim_y : " + hdbImageEvent_ro.getDim_y() + "\r\n" +
			                   "\t\t Statement 2 : " + myStatement1 + "\r\n" +
			                   "\t\t\t 1) name : " + name + "\r\n" +
			                   "\t\t\t 2) timestamp : " + ( new Timestamp(hdbImageEvent_ro.getTimeStamp()) ).toString() + "\r\n" +
			                   "\t\t\t 3) Position of the element being inserted (row,  column) : " + "(" + num_row + ", " + num_column + ")");
			throw e;
		}
	}

	/**
	 * <b>Description : </b> Inserts an image type attribute's data
	 *
	 * @param argin an array that contains (0) The attribute's name - (1) The timestamp's value - (2) The value.
	 */
	public void insert_ImageData_RW(String[] argin) throws SQLException , ArchivingException
	{
        if (canceled) return;
		// Ne sert pas pour l'instant
		System.out.println("DataBaseApi.insert_ImageData_RW");
	}


	public void deleteOldRecords(long keepedPeriod , String[] attributeList) throws ArchivingException
	{
        if (canceled) return;
		long current_date = System.currentTimeMillis();
		long keeped_date = current_date - keepedPeriod;
		long time = keeped_date;
        //long time = current_date;
		Timestamp timeSt = new Timestamp(time);
		for ( int i = 0 ; i < attributeList.length ; i++ )
		{
			String name = attributeList[ i ];
			String tableName = getDbSchema() + "." + getTableName(name);
			String tableField = ConfigConst.TAB_SCALAR_RO[ 0 ];
			PreparedStatement ps_delete;
			String deleteString = "DELETE FROM  " + tableName + " WHERE " + tableField + " <= ?";
            String truncateString = "TRUNCATE TABLE  " + tableName;
            Timestamp lastInsert = this.getTimeOfLastInsert(name,true);
            boolean everythingIsOld = false;
            if ( lastInsert != null )
            {
                everythingIsOld = lastInsert.getTime() - timeSt.getTime() < 0;
            }
            //System.out.println("DataBaseApi/deleteOldRecords/name|"+name+"|lastInsert|"+lastInsert+"|threshold|"+timeSt+"|everythingIsOld|"+everythingIsOld);
            
			try
			{
				String query = everythingIsOld ? truncateString : deleteString;
                //System.out.println("DataBaseApi/deleteOldRecords/query|"+query);
                
                ps_delete = dbconn.prepareStatement ( query );
                lastStatement = ps_delete;
                if (canceled) return;
				
                if ( ! everythingIsOld )
                {
                    ps_delete.setTimestamp(1 , timeSt);    
                }
                
                ps_delete.executeUpdate();
				close(ps_delete);
			}
			catch ( SQLException e )
			{
				e.printStackTrace();
                continue;
			}
		}
	}

/*|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
 *
 *
 *                                              PART 4 :      Generals methods used to manage database's structure (CREATE, DROP)
 *                                         								     (Table creation, Table drop, ...)
 *
 |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||*/

	/**
	 * <b>Description : </b> Creates the table of a scalar read type attribute <I>(mySQL only)</I>.
	 *
	 * @param att_id The ID of the associated attribute
	 */
	public void buildAttributeScalarTab_R(String att_id , int data_type) throws ArchivingException
	{
        if (canceled) return;
		Statement stmt;
// Create and execute the SQL query string
// Build the query string
		String create_query = "";
        String type = "double";
        if (data_type == TangoConst.Tango_DEV_STRING)
        {
            type = "varchar(255)";
        }

		if ( db_type == ConfigConst.BD_MYSQL )
		{
			create_query = "CREATE TABLE `" + att_id + "` (" +
//"`" + ConfigConst.TAB_SCALAR_RO[0] + "` " + "timestamp(14) NOT NULL default ''," +
			               "`" + ConfigConst.TAB_SCALAR_RO[ 0 ] + "` " + "datetime NOT NULL default '0000-00-00 00:00:00', " +
			               "`" + ConfigConst.TAB_SCALAR_RO[ 1 ] + "` " + type + " default NULL)";
		}
// Cette fonctionnalit�e a �t� confi�e au serveur Oracle (Proc�dures stock�es) � l'enregistrement de l'attribut
// This functionality is no longer use.
// It is now managed by the Oracle Server during the attribute registration.
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
		}
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return;
			stmt.executeUpdate(create_query.toString().trim());
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.TAB_BUILD_FAILURE;
			String desc = "Failed while executing DataBaseApi.buildAttributeScalarTab_R() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);

		}

	}

	/**
	 * <b>Description : </b> Creates the table of a scalar wrte type attribute <I>(mySQL only)</I>.
	 *
	 * @param tableName The ID of the associated attribute
	 */
	public void buildAttributeScalarTab_W(String tableName , int data_type) throws ArchivingException
	{
        if (canceled) return;
		Statement statement;
// Create and execute the SQL query string
// Build the query string
		//String fullTableName = getDbSchema() + "." + tableName;
		String create_query = "";
        String type = "double";
        if (data_type == TangoConst.Tango_DEV_STRING)
        {
            type = "varchar(255)";
        }

		if ( db_type == ConfigConst.BD_MYSQL )
		{
			create_query = "CREATE TABLE `" + tableName + "` (" +
//"`" + ConfigConst.TAB_SCALAR_WO[0] + "` " + "timestamp(14) NOT NULL default ''," +
			               "`" + ConfigConst.TAB_SCALAR_WO[ 0 ] + "` " + "datetime NOT NULL default '0000-00-00 00:00:00', " +
			               "`" + ConfigConst.TAB_SCALAR_WO[ 1 ] + "` " + type + " default NULL)";
		}
// Cette fonctionnalit�e a �t� confi�e au serveur Oracle (Proc�dures stock�es) � l'enregistrement de l'attribut
// This functionality is no longer use.
// It is now managed by the Oracle Server during the attribute registration.
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
		}
		try
		{
			statement = dbconn.createStatement();
            lastStatement = statement;
            if (canceled) return;
			statement.executeUpdate(create_query);
			close(statement);
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.TAB_BUILD_FAILURE;
			String desc = "Failed while executing DataBaseApi.buildAttributeScalarTab_W() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
	}

	/**
	 * <b>Description : </b> Creates the table of a scalar read/write type attribute <I>(mySQL only)</I>.
	 *
	 * @param att_id The ID of the associated attribute
	 */
	public void buildAttributeScalarTab_RW(String att_id , int data_type) throws ArchivingException
	{
        if (canceled) return;
		Statement stmt;
// Create and execute the SQL query string
// Build the query string
		String create_query = "";
        String type = "double";
        //if (data_type == TangoConst.Tango_DEV_STRING)
        if (data_type == TangoConst.Tango_DEV_STRING || data_type == TangoConst.Tango_DEV_BOOLEAN )
        {
            type = "varchar(255)";
        }

		if ( db_type == ConfigConst.BD_MYSQL )
		{
			create_query = "CREATE TABLE `" + att_id + "` (" +
//"`" + ConfigConst.TAB_SCALAR_RO[0] + "` " + "timestamp(14) NOT NULL default ''," +
			               "`" + ConfigConst.TAB_SCALAR_RW[ 0 ] + "` " + "datetime NOT NULL default '0000-00-00 00:00:00', " +
			               "`" + ConfigConst.TAB_SCALAR_RW[ 1 ] + "` " + type + " default NULL, " +
			               "`" + ConfigConst.TAB_SCALAR_RW[ 2 ] + "` " + type + " default NULL)";
		}
// Cette fonctionnalit�e a �t� confi�e au serveur Oracle (Proc�dures stock�es) � l'enregistrement de l'attribut
// This functionality is no longer use.
// It is now managed by the Oracle Server during the attribute registration.
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
		}
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return;
			stmt.executeUpdate(create_query.toString().trim());
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.TAB_BUILD_FAILURE;
			String desc = "Failed while executing DataBaseApi.buildAttributeScalarTab_RW() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
	}

	/**
	 * <b>Description : </b> Creates the table of a spectrum read type attribute <I>(mySQL only)</I>.
	 *
	 * @param att_id The ID of the associated attribute
	 */
	public void buildAttributeSpectrumTab_R(String att_id , int data_type) throws ArchivingException
	{
        if (canceled) return;
		Statement stmt;
// Create and execute the SQL query string
// Build the query string
		String create_query = "";

		if ( db_type == ConfigConst.BD_MYSQL )
		{
			create_query = "CREATE TABLE `" + att_id + "` (" +
			               "`" + ConfigConst.TAB_SPECTRUM_RO[ 0 ] + "` " + "DATETIME NOT NULL default '0000-00-00 00:00:00', " +
			               "`" + ConfigConst.TAB_SPECTRUM_RO[ 1 ] + "` " + "SMALLINT NOT NULL, " +
			               "`" + ConfigConst.TAB_SPECTRUM_RO[ 2 ] + "` " + "BLOB default NULL)";
		}
// Cette fonctionnalit�e a �t� confi�e au serveur Oracle (Proc�dures stock�es) � l'enregistrement de l'attribut
// This functionality is no longer use.
// It is now managed by the Oracle Server during the attribute registration.
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
		}
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return;
			stmt.executeUpdate(create_query.toString().trim());
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.TAB_BUILD_FAILURE;
			String desc = "Failed while executing DataBaseApi.buildAttributeSpectrumTab_R() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
	}

	/**
	 * <b>Description : </b> Creates the table of a spectrum read/write type attribute <I>(mySQL only)</I>.
	 *
	 * @param att_id The ID of the associated attribute
	 */
	public void buildAttributeSpectrumTab_RW(String att_id , int data_type) throws ArchivingException
	{
        if (canceled) return;
		Statement stmt;
// Create and execute the SQL query string
// Build the query string
		String create_query = "";

		if ( db_type == ConfigConst.BD_MYSQL )
		{
			create_query = "CREATE TABLE `" + att_id + "` (" +
			               "`" + ConfigConst.TAB_SPECTRUM_RW[ 0 ] + "` " + "DATETIME NOT NULL default '0000-00-00 00:00:00', " +
			               "`" + ConfigConst.TAB_SPECTRUM_RW[ 1 ] + "` " + "SMALLINT NOT NULL, " +
			               "`" + ConfigConst.TAB_SPECTRUM_RW[ 2 ] + "` " + "BLOB default NULL, " +
			               "`" + ConfigConst.TAB_SPECTRUM_RW[ 3 ] + "` " + "BLOB default NULL)";
		}
// Cette fonctionnalit�e a �t� confi�e au serveur Oracle (Proc�dures stock�es) � l'enregistrement de l'attribut
// This functionality is no longer use.
// It is now managed by the Oracle Server during the attribute registration.
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
		}
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return;
			stmt.executeUpdate(create_query.toString().trim());
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.TAB_BUILD_FAILURE;
			String desc = "Failed while executing DataBaseApi.buildAttributeSpectrumTab_RW() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
	}

	/**
	 * This method builds for a given attribute the name of its associated table (table in wich will host its archived values)
	 *
	 * @param index the index associated to the attribute (cf. ADT in HDB).
	 * @return the name of the table to     *
	 */
	public String getTableName(int index)
	{
		String tableName = ConfigConst.TAB_PREF;
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			if ( index < 10 )
			{
				tableName = tableName + "0000" + index;
			}
			else if ( index < 100 )
			{
				tableName = tableName + "000" + index;
			}
			else if ( index < 1000 )
			{
				tableName = tableName + "00" + index;
			}
			else
			{ // if (index < 10000) {
				tableName = tableName + "0" + index;
			}
		}
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
			tableName = tableName + index;
		}
		return tableName;
	}

	/**
	 * This method returns the name of the table associated (table in wich will host its archived values)  to the given attribute
	 *
	 * @param att_name the attribute's name (cf. ADT in HDB).
	 * @return the name of the table associated (table in wich will host its archived values)  to the given attribute.
	 */
	public String getTableName(String att_name) throws ArchivingException
	{
		String table_name = "";
		//table_name = getTableName(getAttID(att_name));
		int id = getBufferedAttID(att_name);
		if ( id <= 0 )
		{
			throw new ArchivingException ("Invalid attribute: "+att_name);
		}
		table_name = getTableName(id);
		return table_name;
	}

	/**
	 * <b>Description : </b> Creates the table of an image read type attribute <I>(mySQL only)</I>.
	 *
	 * @param att_id The ID of the associated attribute
	 */
	public void buildAttributeImageTab_R(String att_id , int data_type) throws ArchivingException
	{
        if (canceled) return;
		Statement stmt;
// Create and execute the SQL query string
// Build the query string
		String create_query = "";

		if ( db_type == ConfigConst.BD_MYSQL )
		{
			create_query = "CREATE TABLE `" + att_id + "` (" +
			               "`" + ConfigConst.TAB_IMAGE_RO[ 0 ] + "` " + "DATETIME NOT NULL default '0000-00-00 00:00:00', " +
			               "`" + ConfigConst.TAB_IMAGE_RO[ 1 ] + "` " + "SMALLINT NOT NULL, " +
			               "`" + ConfigConst.TAB_IMAGE_RO[ 2 ] + "` " + "SMALLINT NOT NULL, " +
			               "`" + ConfigConst.TAB_IMAGE_RO[ 3 ] + "` " + "LONGBLOB default NULL)";
		}
// Cette fonctionnalit�e a �t� confi�e au serveur Oracle (Proc�dures stock�es) � l'enregistrement de l'attribut
// This functionality is no longer use.
// It is now managed by the Oracle Server during the attribute registration.
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
		}
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return;
			stmt.executeUpdate(create_query.toString().trim());
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.TAB_BUILD_FAILURE;
			String desc = "Failed while executing DataBaseApi.buildAttributeImageTab_R() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
	}

	/**
	 * <b>Description : </b> Creates the table of an image read/write type attribute <I>(mySQL only)</I>.
	 *
	 * @param att_id The ID of the associated attribute
	 */
	public void buildAttributeImageTab_RW(String att_id , int data_type) throws ArchivingException
	{
        if (canceled) return;
		Statement stmt;
// Create and execute the SQL query string
// Build the query string
		String create_query = "";

		if ( db_type == ConfigConst.BD_MYSQL )
		{
			create_query = "CREATE TABLE `" + att_id + "` (" +
			               "`" + ConfigConst.TAB_IMAGE_RW[ 0 ] + "` " + "DATETIME NOT NULL default '0000-00-00 00:00:00', " +
			               "`" + ConfigConst.TAB_IMAGE_RW[ 1 ] + "` " + "SMALLINT NOT NULL, " +
			               "`" + ConfigConst.TAB_IMAGE_RW[ 2 ] + "` " + "SMALLINT NOT NULL, " +
			               "`" + ConfigConst.TAB_IMAGE_RW[ 3 ] + "` " + "LONGBLOB default NULL, " +
			               "`" + ConfigConst.TAB_IMAGE_RW[ 4 ] + "` " + "LONGBLOB default NULL)";
		}
// Cette fonctionnalit�e a �t� confi�e au serveur Oracle (Proc�dures stock�es) � l'enregistrement de l'attribut
// This functionality is no longer use.
// It is now managed by the Oracle Server during the attribute registration.
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
		}
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return;
			stmt.executeUpdate(create_query.toString().trim());
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.TAB_BUILD_FAILURE;
			String desc = "Failed while executing DataBaseApi.buildAttributeImageTab_RW() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
	}

	/**
	 * Method that writes a read only type image data into a file.
	 *
	 * @param rootPath         the root directory path into wich the file will be writen
	 * @param hdbImageEvent_ro the read only type image data
	 * @return the full path to the file (directory + file name)
	 * @throws ArchivingException
	 */
	public String[] writeInFile_ImageEvent_RO(String rootPath , String tableName , ImageEvent_RO hdbImageEvent_ro) throws ArchivingException
	{
		String[] full_file_path = new String[ 3 ];
		full_file_path[ 0 ] = rootPath + File.separator + "hdb_image" + File.separator + tableName;
		full_file_path[ 1 ] = hdbImageEvent_ro.getTimeStamp() + ".txt";
		full_file_path[ 2 ] = full_file_path[ 0 ] + File.separator + full_file_path[ 1 ];

		File hdbDir = new File(full_file_path[ 0 ]);
		if ( !hdbDir.exists() )
			hdbDir.mkdirs();

		FileWriter fileWriter = null;
		try
		{
			fileWriter = new FileWriter(full_file_path[ 2 ]);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			if ( db_type == ConfigConst.BD_MYSQL )
			{
				bufferedWriter.write(new StringBuffer().append(toDbTimeString(( new Timestamp(hdbImageEvent_ro.getTimeStamp()) ).toString())).append(",").toString());
				bufferedWriter.newLine();
				bufferedWriter.write(hdbImageEvent_ro.getDim_x() + ",");
				bufferedWriter.newLine();
				bufferedWriter.write(hdbImageEvent_ro.getDim_y() + ",");
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
			Double[][] value = hdbImageEvent_ro.getImageValueRO();
			for ( int i = 0 ; i < value.length ; i++ )
			{
                // lignes
				for ( int j = 0 ; j < ( value[ i ].length - 1 ) ; j++ )
				{ // colonnes
					bufferedWriter.write(value[ i ][ j ] + "\t");
				}
				bufferedWriter.write("" + value[ i ][ value[ i ].length - 1 ]);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
			bufferedWriter.newLine();
			bufferedWriter.write("%%%");
			bufferedWriter.flush();
			bufferedWriter.close();
			fileWriter.close();
			return full_file_path;
		}
		catch ( IOException e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.WRITING_FILE_EXCEPTION;
			String reason = GlobalConst.WRITING_FILE_EXCEPTION;
			String desc = "Failed while executing DataBaseApi.writeInFile_ImageEvent_RO() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);

		}

	}

	public void exportToDB(String tableName , String fullFileName) throws ArchivingException
	{
        if (canceled) return;
		Statement stmt;
		fullFileName = fullFileName.replaceAll(new StringBuffer().append(File.separator).append(File.separator).toString() , new StringBuffer().append(File.separator).append(File.separator).append(File.separator).append(File.separator).toString());
		StringBuffer export_query = new StringBuffer().append("LOAD DATA LOCAL INFILE  '").append(fullFileName).append("' INTO TABLE ").append(tableName).append(" FIELDS TERMINATED BY ',\\n' LINES TERMINATED BY '\\n%%%\\n'");

		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return;
			try
			{
				//System.out.println("export_query.toString()/"+export_query.toString());
				stmt.executeUpdate(export_query.toString());
				close ( stmt );
			}
			catch ( SQLException e )
			{
				close ( stmt );
				throw e;
			}
		}
		catch ( SQLException e )
		{
			e.printStackTrace ();
		    
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.EXPORTING_FILE_EXCEPTION;
			String desc = "Failed while executing DataBaseApi.exportToDB() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
	}
    
    /*public void exportToDBOracle(String tableName , String fullFileName) throws ArchivingException
    {
        if (canceled) return;
        Statement stmt;
        fullFileName = fullFileName.replaceAll(new StringBuffer().append(File.separator).append(File.separator).toString() , new StringBuffer().append(File.separator).append(File.separator).append(File.separator).append(File.separator).toString());
        //StringBuffer export_query = new StringBuffer().append("LOAD DATA INFILE  '").append(fullFileName).append("' INTO TABLE ").append(tableName).append(" FIELDS TERMINATED BY ',' LINES TERMINATED BY '"+ConfigConst.NEW_LINE+"'");

        try
        {
            stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return;
            try
            {
                //System.out.println ("CLA/exportToDBOracle|"+export_query.toString()+"|");
                //stmt.executeUpdate(export_query.toString());
                //stmt.executeUpdate("LOAD DATA INFILE '/mnt/monteclair/att_values/att_4-1151674332470' INTO TABLE att_4 FIELDS TERMINATED BY ',' LINES TERMINATED BY ';'");
                stmt.execute ("LOAD DATA INFILE '/mnt/monteclair/att_values/att_4-1151674332470' INTO TABLE att_4 FIELDS TERMINATED BY ',' LINES TERMINATED BY ';'");
                close ( stmt );
            }
            catch ( SQLException e )
            {
                close ( stmt );
                throw e;
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace ();
            
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.EXPORTING_FILE_EXCEPTION;
            String desc = "Failed while executing DataBaseApi.exportToDB() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
    }*/

	/*public void exportToDB_Scalar(String remoteDir , String fileName , String tableName, int writable) throws ArchivingException
	{
        if (canceled) return;
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			if ( remoteDir.endsWith("/") || remoteDir.endsWith("\\") )
				exportToDB(tableName , remoteDir + fileName);
			else
				exportToDB(tableName , remoteDir + File.separator + fileName);
		}
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
			CallableStatement callableStatement;
			StringBuffer query = new StringBuffer().append("{call ").append(getDbSchema()).append(".fast_file_import(?, ?, ?)}");

			try
			{
				callableStatement = dbconn.prepareCall(query.toString());
                lastStatement = callableStatement;
                if (canceled) return;
				callableStatement.setString(1 , remoteDir);
				callableStatement.setString(2 , fileName);
				callableStatement.setString(3 , tableName);
				try
				{
					//System.out.println ( "CLA/exportToDB_Scalar/BEFORE" );
				    callableStatement.executeUpdate();
					close(callableStatement);
					//System.out.println ( "CLA/exportToDB_Scalar/AFTER" );
				}
				catch ( SQLException e )
				{
					e.printStackTrace ();
				    close(callableStatement);
					throw e;
				}
			}
			catch ( SQLException e )
			{
                e.printStackTrace ();
                
                String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.EXPORTING_FILE_EXCEPTION;
				String desc = "Failed while executing DataBaseApi.exportToDB_Scalar() method..." +
				              "\r\n\t\t Query : " + query +
				              "\r\n\t\t Param 1 (remote directory) : " + remoteDir +
				              "\r\n\t\t Param 2 (file name)        : " + fileName +
				              "\r\n\t\t Param 3 (table name)       : " + tableName +
				              "\r\n\t\t Param 4 (writable)         : " + writable +
				              "\r\n\t\t Code d'erreur : " + e.getErrorCode() +
				              "\r\n\t\t Message : " + e.getMessage() +
				              "\r\n\t\t SQL state : " + e.getSQLState() +
				              "\r\n\t\t Stack : ";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
		}
	}*/
    
    public void exportToDB_Scalar(String remoteDir , String fileName , String tableName, int writable) throws ArchivingException
    {
        if (canceled) return;
        if ( db_type == ConfigConst.BD_MYSQL )
        {
            if ( remoteDir.endsWith("/") || remoteDir.endsWith("\\") )
                exportToDB(tableName , remoteDir + fileName);
            else
                exportToDB(tableName , remoteDir + File.separator + fileName);
        }
        else if ( db_type == ConfigConst.BD_ORACLE )
        {
            addToFiles (remoteDir , fileName , tableName);
        }
    }
    

	private void addToFiles(String remoteDir , String fileName , String tableName ) throws ArchivingException
    {
        //System.out.println("----canceled = " + canceled);
        if (canceled) return;
        CallableStatement callableStatement;
        //StringBuffer query = new StringBuffer().append("{call ").append(getDbSchema()).append(".notify_of_file_to_import(?, ?, ?)}");
        StringBuffer query = new StringBuffer().append("{call ").append(getDbSchema()).append(".crcontrolfile(?, ?, ?)}");

        try
        {
            callableStatement = dbconn.prepareCall(query.toString());
            lastStatement = callableStatement;
            //System.out.println("++++canceled = " + canceled);
            if (canceled) return;
            callableStatement.setString(1 , remoteDir);
            callableStatement.setString(2 , fileName);
            callableStatement.setString(3 , tableName);
            try
            {
                System.out.println("CLA/DataBaseApi/exportToDBByOracleJob BEFORE PS crcontrolfile: "+ new Timestamp(System.currentTimeMillis()).toString());
                callableStatement.executeUpdate();
                System.out.println("CLA/DataBaseApi/exportToDBByOracleJob AFTER PS crcontrolfile: "+ new Timestamp(System.currentTimeMillis()).toString());
                close(callableStatement);
            }
            catch ( SQLException e )
            {
                e.printStackTrace ();
                close(callableStatement);
                throw e;
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace ();

            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.EXPORTING_FILE_EXCEPTION;
            String desc = "Failed while executing DataBaseApi.exportToDBByOracleJob() method..." +
                          "\r\n\t\t Query : " + query +
                          "\r\n\t\t Param 1 (remote directory) : " + remoteDir +
                          "\r\n\t\t Param 2 (file name)        : " + fileName +
                          "\r\n\t\t Param 3 (table name)       : " + tableName +
                          "\r\n\t\t Code d'erreur : " + e.getErrorCode() +
                          "\r\n\t\t Message : " + e.getMessage() +
                          "\r\n\t\t SQL state : " + e.getSQLState() +
                          "\r\n\t\t Stack : ";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }    
    }

	public Timestamp now () throws ArchivingException
	{
        if (canceled) return null;
		Statement stmt = null;
		ResultSet rset = null;
		// First connect with the database
		if ( getAutoConnect() )
		{
		    connect();
		}
			
		// Create and execute the SQL query string
		String sqlStr = "SELECT SYSDATE";
		int dbType = this.getDb_type ();
		switch ( dbType )
		{
			case ConfigConst.BD_MYSQL:
			    sqlStr += "()";
			break;
			
			case ConfigConst.BD_ORACLE:
			    sqlStr += " FROM DUAL";
			break;
		}
		try
		{
			stmt = dbconn.createStatement();
            lastStatement = stmt;
            if (canceled) return null;

            //System.out.println ( "CLA/DataBaseApi/now/query|"+sqlStr+"|" );
            
            rset = stmt.executeQuery(sqlStr);
			rset.next();
			// Gets the result of the query
			
			Timestamp date = rset.getTimestamp ( 1 );
			//System.out.println("date = " + date);
			
			return date;
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.QUERY_FAILURE;
			String desc = "Failed while executing DataBaseApi.now() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
		finally
		{
            try 
            {
                close ( rset );
                close ( stmt );
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.now() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
		    //Close the connection with the database
			if ( getAutoConnect() )
			{
				close();    
			}
		}
	}
	//---------------------------------
	
	/*public void exportToDB_Spectrum(String remoteDir , String fileName , String tableName , int writable) throws ArchivingException
	{
        if (canceled) return;
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			if ( remoteDir.endsWith("/") || remoteDir.endsWith("\\") )
				exportToDB(tableName , remoteDir + fileName);
			else
				exportToDB(tableName , remoteDir + File.separator + fileName);
		}
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
			CallableStatement callableStatement;
			StringBuffer query = new StringBuffer().append("{call ").append(getDbSchema()).append(".fast_file_import(?, ?, ?)}");

			try
			{
				callableStatement = dbconn.prepareCall(query.toString());
                lastStatement = callableStatement;
                if (canceled) return;
				callableStatement.setString(1 , remoteDir);
				callableStatement.setString(2 , fileName);
				callableStatement.setString(3 , tableName);
				callableStatement.executeUpdate();
				close(callableStatement);
			}
			catch ( SQLException e )
			{
				String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.EXPORTING_FILE_EXCEPTION;
				String desc = "Failed while executing DataBaseApi.exportToDB_Spectrum() method..." +
				              "\r\n\t\t Query : " + query +
				              "\r\n\t\t Param 1 (remote directory) : " + remoteDir +
				              "\r\n\t\t Param 2 (file name)        : " + fileName +
				              "\r\n\t\t Param 3 (table name)       : " + tableName +
				              "\r\n\t\t Param 4 (writable)         : " + writable +
				              "\r\n\t\t Code d'erreur : " + e.getErrorCode() +
				              "\r\n\t\t Message : " + e.getMessage() +
				              "\r\n\t\t SQL state : " + e.getSQLState() +
				              "\r\n\t\t Stack : ";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
		}
	}*/
    
    public void exportToDB_Spectrum(String remoteDir , String fileName , String tableName , int writable) throws ArchivingException
    {
        if (canceled) return;
        if ( db_type == ConfigConst.BD_MYSQL )
        {
            if ( remoteDir.endsWith("/") || remoteDir.endsWith("\\") )
                exportToDB(tableName , remoteDir + fileName);
            else
                exportToDB(tableName , remoteDir + File.separator + fileName);
        }
        else if ( db_type == ConfigConst.BD_ORACLE )
        {
            addToFiles (remoteDir , fileName , tableName);
        }
    }

	/*public void exportToDB_Image(String remoteDir , String fileName , String tableName, int writable) throws ArchivingException
	{
        if (canceled) return;
		if ( db_type == ConfigConst.BD_MYSQL )
		{
			if ( remoteDir.endsWith("/") || remoteDir.endsWith("\\") )
				exportToDB(tableName , remoteDir + fileName);
			else
				exportToDB(tableName , remoteDir + File.separator + fileName);
		}
		else if ( db_type == ConfigConst.BD_ORACLE )
		{
			CallableStatement callableStatement;
			String proc2use = "";
			switch ( writable )
			{
				case AttrWriteType._READ:
					proc2use = "fast_im_ro(?, ?, ?)";
					break;
				case AttrWriteType._READ_WITH_WRITE:
					proc2use = "fast_im_rw(?, ?, ?)";
					break;
				case AttrWriteType._WRITE:
					proc2use = "fast_im_rw(?, ?, ?)";
					break;
				case AttrWriteType._READ_WRITE:
					proc2use = "fast_im_wo(?, ?, ?)";
					break;
			}
			StringBuffer query = new StringBuffer().append("{call ").append(getDbSchema()).append("." + proc2use + "}");

			try
			{
				callableStatement = dbconn.prepareCall(query.toString());
                lastStatement = callableStatement;
                if (canceled) return;
				callableStatement.setString(1 , remoteDir);
				callableStatement.setString(2 , fileName);
				callableStatement.setString(3 , tableName);
				callableStatement.executeUpdate();
				close(callableStatement);
			}
			catch ( SQLException e )
			{
				String message = "";
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.EXPORTING_FILE_EXCEPTION;
				String desc = "Failed while executing DataBaseApi.exportToDB_Image() method..." +
				              "\r\n\t\t Query : " + query +
				              "\r\n\t\t Param 1 (remote directory) : " + remoteDir +
				              "\r\n\t\t Param 2 (file name)        : " + fileName +
				              "\r\n\t\t Param 3 (table name)       : " + tableName +
				              "\r\n\t\t Param 4 (writable)         : " + writable +
				              "\r\n\t\t Code d'erreur : " + e.getErrorCode() +
				              "\r\n\t\t Message : " + e.getMessage() +
				              "\r\n\t\t SQL state : " + e.getSQLState() +
				              "\r\n\t\t Stack : ";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
		}
	}*/
    
    public void exportToDB_Image(String remoteDir , String fileName , String tableName, int writable) throws ArchivingException
    {
        if (canceled) return;
        if ( db_type == ConfigConst.BD_MYSQL )
        {
            if ( remoteDir.endsWith("/") || remoteDir.endsWith("\\") )
                exportToDB(tableName , remoteDir + fileName);
            else
                exportToDB(tableName , remoteDir + File.separator + fileName);
        }
        else if ( db_type == ConfigConst.BD_ORACLE )
        {
            addToFiles (remoteDir , fileName , tableName);
        }
    }


/*|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
 *
 *
 *                                              PART 5 :      Miscellaneous others methods used by the API
 *
 *
 |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||*/

	/**
	 * <b>Description : </b>    	Build a array of String with the given String Vector
	 *
	 * @param my_vector The given String Vector
	 * @return a String type array that contains the differents vector's String type elements <br>
	 */
	public String[] toStringArray(Vector my_vector)
	{
		String[] my_array;
		my_array = new String[ my_vector.size() ];
		for ( int i = 0 ; i < my_vector.size() ; i++ )
		{
			my_array[ i ] = ( String ) my_vector.elementAt(i);
		}
		return my_array;
	}

	/**
	 * <b>Description : </b>    	Build a array of String with the two String Double Vectors
	 *
	 * @param my_vector1 The first given String Vector
	 * @param my_vector2 The second given String Vector
	 * @return a String type array that contains the first and the second String elements <br>
	 */
	private String[] toStringArray(Vector my_vector1 , Vector my_vector2)
	{
		for ( int i = 0 ; i < my_vector2.size() ; i++ )
		{
			my_vector1.addElement(( String ) my_vector2.elementAt(i));
		}
		return toStringArray(my_vector1);
	}

	/**
	 * <b>Description : </b>    	Build a array of Double with the given Double Vector
	 *
	 * @param my_vector The given Double Vector
	 * @return a Double type array that contains the differents vector's Double type elements <br>
	 */
	private double[] toDoubleArray
	        (Vector
	        my_vector)
	{
		double[] my_array;
		my_array = new double[ my_vector.size() ];
		for ( int i = 0 ; i < my_vector.size() ; i++ )
		{
			my_array[ i ] = ( ( Double ) my_vector.elementAt(i) ).doubleValue();
		}
		return my_array;
	}

	/**
	 * <b>Description : </b>    	Build a array of Double with the two given Double Vectors
	 *
	 * @param my_vector1 The first given Double Vector
	 * @param my_vector2 The second given Double Vector
	 * @return a Double type array that contains the first and the second Double elements <br>
	 */
	private double[] toDoubleArray(Vector my_vector1 , Vector my_vector2)
	{
		for ( int i = 0 ; i < my_vector2.size() ; i++ )
		{
			my_vector1.addElement(( Double ) my_vector2.elementAt(i));
		}
		return toDoubleArray(my_vector1);
	}

	/**
	 * Method that write correct field string in case of MySQL or Oracle database
	 *
	 * @param timeField
	 * @return the correct field string
	 * @see #toDbTimeString
	 */
	private String toDbTimeFieldString(String timeField)
	{
		return ( db_type == ConfigConst.BD_MYSQL ?
		         " `" + timeField + "`" :
		         "to_char(" + timeField + " ,'DD-MM-YYYY HH24:MI:SS.FF')" );
	}
    
    private String toDbTimeFieldString(String timeField,String format)
    {
        if ( db_type == ConfigConst.BD_MYSQL )
        {
            //return " `" + timeField + "`";
            return "DATE_FORMAT(" + timeField + " ,'" + format + "')";
        }
        else
        {
            return "to_char(" + timeField + " ,'" + format + "')";    
        }
        
    }

	/**
	 * Method that write correct string in case of MySQL or Oracle database
	 *
	 * @param timeField
	 * @return the correct string
	 * @see #toDbTimeFieldString
	 */
	private String toDbTimeString(String timeField)
	{
		return ( db_type == ConfigConst.BD_MYSQL ?
		         " \"" + timeField + "\"" : //MySQL
		         "\'" + timeField + "" + "\'" );     // Oracle
	}

	public int getRelativePathIndex(String attDirPath , String prefix , int nb_file_max)
	{
		File attDirectory = new File(attDirPath);
		//Le r�pertoire est il vide ?
		if ( !attDirectory.exists() )
		{
			// Si 'oui', je le cr�� et je renvoi 1
			attDirectory.mkdirs();
			return 1;
		}
		else
		{
			// Si 'non' je choisi le r�peroire courant (d'index maximum) : je compte pour cel� le nombre de sous r�pertoire
			int current_index_dir = attDirectory.listFiles().length;
			String currentPath = attDirPath + File.separator + prefix + current_index_dir;
			File attCurrentDirectory = new File(currentPath);
			// Si le nombre de fichier du r�pertoire courant est inf�rieur au nombre max... je retourne le nombre courant
			if ( attCurrentDirectory.listFiles().length < nb_file_max )
			{
				return current_index_dir;
			}
			else
			{
				return current_index_dir + 1;
			}
		}
	}

    public void minimumRequest() throws SQLException
    {
        if (canceled) return;
        String tableName = getDbSchema() + ".adt";
        String query = "SELECT NULL FROM " + tableName + " WHERE ID=1";

        Statement stmt = dbconn.createStatement();
        lastStatement = stmt;
        if (canceled) return;
        //ResultSet rset = 
        try
        {
            stmt.executeQuery(query);
            close ( stmt );
        }
        catch ( SQLException e )
        {
            throw e;
        }
        finally
        {
            stmt.close ();
        }
        
        //throw new SQLException ( "CLA/DatabaseApi/minimumRequest/DUMMY SQLException!!!!!!!!!!!!!!!!!!!!!!" );
    }

    public void forceDatabaseToImportFile(String tableName) throws ArchivingException 
    {
        //System.out.println("DataBaseApi/forceDatabaseToImportFile/START");
        
        if (canceled) return;
        if ( db_type == ConfigConst.BD_MYSQL )
        {
            return;   //nothing to do, the data is already exported
        }
        
        CallableStatement callableStatement;
        //StringBuffer query = new StringBuffer().append("{call ").append(getDbSchema()).append(".force_import_from_file(?)}");
        StringBuffer query = new StringBuffer().append("{call ").append(getDbSchema()).append(".force_load_data(?)}");

        try
        {
            callableStatement = dbconn.prepareCall(query.toString());
            lastStatement = callableStatement;
            if (canceled) return;
            //System.out.println ( "forceDatabaseToImportFile/tableName|"+tableName+"|" );
            callableStatement.setString(1 , tableName);
            
            try
            {
                System.out.println("CLA/DataBaseApi/forceDatabaseToImportFile BEFORE PS force_load_data: "+ new Timestamp(System.currentTimeMillis()).toString());
                callableStatement.executeUpdate();
                close(callableStatement);
                System.out.println("CLA/DataBaseApi/forceDatabaseToImportFile AFTER PS force_load_data: "+ new Timestamp(System.currentTimeMillis()).toString());
            }
            catch ( SQLException e )
            {
                e.printStackTrace ();
                close(callableStatement);
                throw e;
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace ();
            
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.EXPORTING_FILE_EXCEPTION;
            String desc = "Failed while executing DataBaseApi.exportToDBByOracleJob() method..." +
                          "\r\n\t\t Query : " + query +
                          "\r\n\t\t Param 1 (table name)       : " + tableName +
                          "\r\n\t\t Code d'erreur : " + e.getErrorCode() +
                          "\r\n\t\t Message : " + e.getMessage() +
                          "\r\n\t\t SQL state : " + e.getSQLState() +
                          "\r\n\t\t Stack : ";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }        
    }

    public Timestamp getTimeOfLastInsert(String completeName, boolean max) throws ArchivingException 
    {
        Statement stmt = null;
        ResultSet rset = null;
        Timestamp ret = null;

        String maxOrMin = max ? "MAX" : "MIN";
        
        String field; 
        if ( db_type == ConfigConst.BD_MYSQL )
        {
            field = maxOrMin+"("+ConfigConst.TAB_SCALAR_RO[ 0 ]+")";            
        }
        else
        {
            field = toDbTimeFieldString ( maxOrMin+"("+ConfigConst.TAB_SCALAR_RO[ 0 ]+")" );    
        }
        
        //System.out.println ( "CLA/DataBaseApi/getTimeOfLastInsert/field|"+field+"|" );
        String tableName;
        try
        {
            tableName = getDbSchema() + "." + getTableName(completeName);
        }
        catch ( ArchivingException e )
        {
            return null;
        }
        String query = "select "+ field + " from " + tableName;
        //System.out.println ( "CLA/DataBaseApi/getTimeOfLastInsert/query|"+query+"|" );
        
        try
        {
            stmt = dbconn.createStatement();
            
            rset = stmt.executeQuery(query);
            rset.next ();
            String rawDate = rset.getString ( 1 );
            if ( rawDate == null )
            {
                return null;
            }
            
            //String stringToDisplayString = 
            DateUtil.stringToDisplayString(rawDate);
            long stringToMilli = DateUtil.stringToMilli ( rawDate );
            ret = new Timestamp ( stringToMilli );
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.QUERY_FAILURE;
            String desc = "Failed while executing DataBaseApi.getTimeOfLastInsert() method...";
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
        catch ( Exception e )
        {
            return null;
        }
        finally
        {
            try 
            {
                close ( rset );
                close ( stmt );
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                
                String message = "";
                if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
                else
                    message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

                String reason = GlobalConst.QUERY_FAILURE;
                String desc = "Failed while executing DataBaseApi.getTimeOfLastInsert() method...";
                throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
            }
        }
        return ret;
    }

    public boolean isHistoric ()
    {
        return isHistoric;
    }

    public void setHistoric (boolean isHistoric)
    {
        this.isHistoric = isHistoric;
    }

}

