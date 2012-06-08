//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description: Cette classe définit un quelques constantes utilisées dans nombre d'outils liés à l'archivage.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.12  2007/05/11 14:01:42  pierrejoseph
// Attribute addition : release version
//
// Revision 1.11  2007/05/10 14:59:21  ounsy
// NaN and null distinction
//
// Revision 1.10  2006/10/31 16:54:24  ounsy
// milliseconds and null values management
//
// Revision 1.9  2006/07/06 14:06:58  ounsy
// image support
//
// Revision 1.8  2006/06/15 15:26:10  ounsy
// Added a NO_NONDEDICATED_ARC_EXCEPTION error message, for the case when there are archivers running, but all of them are dedicated and we need to archive a non-dedicated attribute
//
// Revision 1.7  2006/05/12 09:22:06  ounsy
// CLOB_SEPARATOR in GlobalConst
//
// Revision 1.6  2006/05/04 14:29:36  ounsy
// CLOB_SEPARATOR centralized in ConfigConst
//
// Revision 1.5  2006/02/28 17:05:58  chinkumo
// no message
//
// Revision 1.4  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.3.10.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.3  2005/06/24 12:03:35  chinkumo
// All constants related to errors were moved from fr.soleil.TangoArchiving.ArchivingApi.ConfigConst to fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst.
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

package fr.soleil.TangoArchiving.ArchivingTools.Tools;

/**
 * <p/>
 * <B>Description :</B><BR>
 * Cette classe définit un quelques constantes utilisées dans nombre d'outils liés à l'archivage.<BR>
 * </p>
 *
 * @author Jean CHINKUMO - Synchrotron SOLEIL
 * @version	$Revision$
 */
public class GlobalConst
{
	public final static String ARCHIVING_VERSION = "1.2.0";
	
	protected final static String MODE_P = "MODE_P";
	protected final static String MODE_A = "MODE_A";
	protected final static String MODE_R = "MODE_R";
	protected final static String MODE_C = "MODE_C";
	protected final static String MODE_D = "MODE_D";
	protected final static String MODE_E = "MODE_E";

	/* ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	*                                     PART 3 :      Miscellaneous constants for errors
	* ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||*/
	public static final String ARCHIVING_ERROR_PREFIX = "ARCHIVING ERROR : ";
	public static final String DRIVER_MISSING = "Driver missing";
	public static final String ADB_CONNECTION_FAILURE = "Failled connecting to archiving database";
	public static final String UNCONNECTECTED_ADB = "Archiving Database not connected !";
	public static final String CANNOT_TALK_TO_ADB = "Archiving Database unreachable";
	public static final String NO_HOST_EXCEPTION = "java.net.UnknownHostException";
	public static final String NO_HOST_EXCEPTION2 = "java.net.ConnectException";
	public static final String WRITING_FILE_EXCEPTION = "Problem writing file";
	public static final String EXPORTING_FILE_EXCEPTION = "Problem exporting file from file system to archiving database";
	public static final String STATEMENT_FAILURE = "The statement sent to the archiving database failed";
	public static final String QUERY_FAILURE = "Failed while querying the database";
	public static final String EXTRAC_FAILURE = "Failled retrieving data from archiving database";
	public static final String INSERT_FAILURE = "Failed while inserting data into the archiving database";
	public static final String DELETE_FAILURE = "Failed while deleting data from the archiving database";
	public static final String UPDATE_FAILURE = "Failed while updating data into the archiving database";
	public static final String TAB_BUILD_FAILURE = "Failed while building a archiving database table";
	public static final String TANGO_COMM_EXCEPTION = "Tango communication error";
	public static final String DBT_UNREACH_EXCEPTION = "Tango database (dbt) unreachable";
	public static final String DEV_UNREACH_EXCEPTION = "Device unreachable";
	public static final String ATT_UNREACH_EXCEPTION = "Attribute unreachable";
	public static final String MAN_UNREACH_EXCEPTION = "Archiving Manager unreachable";
	public static final String ARC_UNREACH_EXCEPTION = "Archiving Archiver unreachable";
	public static final String EXT_UNREACH_EXCEPTION = "Archiving Extractor unreachable";
	public static final String NO_MAN_EXCEPTION = "No Manager found !";
	public static final String NO_ARC_EXCEPTION = "No Archiver found !";
    public static final String NO_NONDEDICATED_ARC_EXCEPTION = "No non-dedicated Archiver found !";
	public static final String NO_EXT_EXCEPTION = "No Extractor found !";
	public static final String DATA_TYPE_EXCEPTION = "Attribute data type not supported...";
	public static final String DATA_FORMAT_EXCEPTION = "Attribute data format not supported...";
	public static final String DATA_WRITABLE_EXCEPTION = "Attribute writable access not supported...";
	public static final String DATE_PARSING_EXCEPTION = "Failed while parsing date...";
	public static final String COMM_FAILURE_MYSQL = "Io exception: Broken pipe";
	public static final String COMM_FAILURE_ORACLE = "Communication link failure";
	public static final String ALREADY_ARCHIVINGSTART = "At least one attribute was already being archived !";
	public static final String ERROR_ARCHIVINGSTART = "Error while launching archiving !";
	public static final String ALREADY_ARCHIVINGSTOP = "At least one attribute was not being archived !";
	public static final String ERROR_ARCHIVINGSTOP = "Unable to stop archiving";

    public static final String CLOB_SEPARATOR = ",";
    public static final String CLOB_SEPARATOR_IMAGE_COLS = ",";
    public static final String CLOB_SEPARATOR_IMAGE_ROWS = "~";

    /**
     * This String can be used by archivers to represent a null (Object) value as a String
     */
    public final static String ARCHIVER_NULL_VALUE = "-!ARCHIVER_NULL_VALUE!-";

    /**
     * String used to set a column to null from a file for Oracle
     */
    public final static String ORACLE_NULL_VALUE = "";

    /**
     * String used to set a column to null from a file for MySQL
     */
    public final static String MYSQL_NULL_VALUE = "\\N";

    /**
     * double used to code a null value readen in database.
     * (corresponds with the NAN_FOR_NULL in atk JLDataView)
     */
    public static final double NAN_FOR_NULL = Double.longBitsToDouble( 0x7ff0000bad0000ffL );

}
