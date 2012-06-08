package fr.soleil.TangoSnapshoting.SnapshotingTools.Tools;

//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  GlobalConst.
//						(chinkumo) - 24 juin 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.10  2006/06/28 12:43:58  ounsy
// image support
//
// Revision 1.9  2006/06/16 08:51:34  ounsy
// ready for images
//
// Revision 1.8  2006/05/12 09:22:06  ounsy
// CLOB_SEPARATOR in GlobalConst
//
// Revision 1.7  2006/05/04 14:34:26  ounsy
// CLOB_SEPARATOR centralized in ConfigConst
//
// Revision 1.6  2006/03/07 10:01:04  ounsy
// clob separator updated
//
// Revision 1.5  2006/02/28 17:05:58  chinkumo
// no message
//
// Revision 1.4  2006/02/17 09:32:35  chinkumo
// Since the structure and the name of some SNAPSHOT database's table changed, this was reported here.
//
// Revision 1.3  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.2.2.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.2  2005/08/19 14:04:02  chinkumo
// no message
//
// Revision 1.1.6.2.2.1  2005/08/11 08:16:49  chinkumo
// The 'SetEquipement' command and thus functionnality was added.
//
// Revision 1.1.6.2  2005/08/04 07:57:19  chinkumo
// Obsolete comments removed.
//
// Revision 1.1.6.1  2005/08/01 13:49:58  chinkumo
// Several changes carried out for the support of the new graphical application (Bensikin).
//
// Revision 1.1  2005/06/28 07:40:56  chinkumo
// New constants related to errors and exceptions were added.
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================

public class GlobalConst
{
	public static final String SNAPSHOTING_ERROR_PREFIX = "ARCHIVING ERROR : ";
	public static final String DRIVER_MISSING = "Driver missing";
	public static final String ADB_CONNECTION_FAILURE = "Failled connecting to snapshoting database";
	public static final String UNCONNECTECTED_ADB = "Snapshoting Database not connected !";
	public static final String CANNOT_TALK_TO_ADB = "Snapshoting Database unreachable";
	public static final String NO_HOST_EXCEPTION = "java.net.UnknownHostException";
	public static final String NO_HOST_EXCEPTION2 = "java.net.ConnectException";
	public static final String WRITING_FILE_EXCEPTION = "Problem writing file";
	public static final String EXPORTING_FILE_EXCEPTION = "Problem exporting file from file system to snapshoting database";
	public static final String STATEMENT_FAILURE = "The statement sent to the snapshoting database failed";
	public static final String QUERY_FAILURE = "Failed while querying the database";
	public static final String EXTRAC_FAILURE = "Failled retrieving data from snapshoting database";
	public static final String INSERT_FAILURE = "Failed while inserting data into the snapshoting database";
	public static final String DELETE_FAILURE = "Failed while deleting data from the snapshoting database";
	public static final String UPDATE_FAILURE = "Failed while updating data into the snapshoting database";
	public static final String TAB_BUILD_FAILURE = "Failed while building a snapshoting database table";
	public static final String TANGO_COMM_EXCEPTION = "Tango communication error";
	public static final String DBT_UNREACH_EXCEPTION = "Tango database (dbt) unreachable";
	public static final String DEV_UNREACH_EXCEPTION = "Device unreachable";
	public static final String ATT_UNREACH_EXCEPTION = "Attribute unreachable";
	public static final String MAN_UNREACH_EXCEPTION = "Snapshoting Manager unreachable";
	public static final String ARC_UNREACH_EXCEPTION = "Snapshoting Archiver unreachable";
	public static final String EXT_UNREACH_EXCEPTION = "Snapshoting Extractor unreachable";
	public static final String NO_MAN_EXCEPTION = "No Manager found !";
	public static final String NO_ARC_EXCEPTION = "No Archiver found !";
	public static final String NO_EXT_EXCEPTION = "No Extractor found !";
	public static final String DATA_TYPE_EXCEPTION = "Attribute data type not supported...";
	public static final String DATA_FORMAT_EXCEPTION = "Attribute data format not supported...";
	public static final String DATA_WRITABLE_EXCEPTION = "Attribute writable access not supported...";
	public static final String DATE_PARSING_EXCEPTION = "Failed while parsing date...";
	public static final String COMM_FAILURE_MYSQL = "Io exception: Broken pipe";
	public static final String COMM_FAILURE_ORACLE = "Communication link failure";
	public static final String ALREADY_SNAPSHOTINGSTART = "At least one attribute was already being archived !";
	public static final String ERROR_SNAPPATTERN_CREATION = "Error while creating new snap pattern !";
	public static final String ERROR_LAUNCHINGSNAP = "Error while lauching snapshot !";
	public static final String ERROR_SNAP_SET_EQUIPMENT = "Error while repositionning equipment !";
	public static final String ERROR_RET_ATT = "Error retrieving attributes !";
	public static final String ERROR_RET_SNAP = "Error retrieving associated snapshots !";
	public static final String ERROR_SQL_OPERATOR = "Error illegal operator in SQL statement !";

	// Operateurs.
	public static final String OP_EQUALS = "=";
	public static final String OP_GREATER_THAN = ">=";
	public static final String OP_LOWER_THAN = "<=";
	public static final String OP_GREATER_THAN_STRICT = ">";
	public static final String OP_LOWER_THAN_STRICT = "<";
	public static final String OP_CONTAINS = "Contains";
	public static final String OP_STARTS_WITH = "Starts with";
	public static final String OP_ENDS_WITH = "Ends with";
	/* ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
														Special constants used to describe the database structure
			||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||*/
	public static final String[] TABS = {"ast", "context", "list", "snapshot",  // 0 -> 3
	                                     "t_im_1val", "t_im_2val",              // 4 -> 5
	                                     "t_sp_1val", "t_sp_2val",              // 6 -> 7
	                                     "t_sc_num_1val", "t_sc_num_2val",      // 8 -> 9
	                                     "t_sc_str_1val", "t_sc_str_2val"};      // 10 -> 11

	public static final String[] TAB_DEF = {"ID", "time", "full_name", "device", // 0 -> 3
	                                        "domain", "family", "member", "att_name", // 4 -> 7
	                                        "data_type", "data_format", "writable", // 8 -> 10
	                                        "max_dim_x", "max_dim_y", // 11 -> 12
	                                        "levelg", "facility", "archivable", "substitute"};               // 13 -> 16

	public static final String[] TAB_SNAP = {"id_snap", "id_context", "time", "snap_comment"};    // 0 -> 2
	/**
	 * Array that contains the name's fields for the HDB Mode Table
	 */
	public static final String[] TAB_CONTEXT = {"ID_context", "time", "name", "author", "reason", "description"};    // 0 -> 5
	/**
	 * Array that contains the name's fields for the HDB Mode Table
	 */
	public static final String[] TAB_LIST = {"id_context", "id_att"};  // 0 -> 2
	/**
	 * Array that contains the name's fields for the Scalar_Read Attribute's Table
	 */
	public static final String[] TAB_SC_RO_NUM = {"id_snap", "id_att", "value"};
	/**
	 * Array that contains the name's fields for the Scalar_Read Attribute's Table
	 */
	public static final String[] TAB_SC_RO_STR = {"id_snap", "id_att", "value"};
	/**
	 * Array that contains the name's fields for the Scalar_Read Attribute's Table
	 */
	public static final String[] TAB_SC_WO_NUM = {"id_snap", "id_att", "value"};
	/**
	 * Array that contains the name's fields for the Scalar_Read Attribute's Table
	 */
	public static final String[] TAB_SC_WO_STR = {"id_snap", "id_att", "value"};
	/**
	 * Array that contains the name's fields for the Scalar_ReadWrite Attribute's Table
	 */
	public static final String[] TAB_SC_RW_NUM = {"id_snap", "id_att", "read_value", "write_value"};
	/**
	 * Array that contains the name's fields for the Scalar_Read Attribute's Table
	 */
	public static final String[] TAB_SC_RW_STR = {"id_snap", "id_att", "read_value", "write_value"};
	/**
	 * Array that contains the name's fields for the Scalar_ReadWrite Attribute's Table
	 */
	//public static final String[] TAB_SP_RO_NUM = {"id_snap", "id_att", "value"};
	public static final String[] TAB_SP_RO_NUM = {"id_snap", "id_att", "dim_x", "value"};
	/**
	 * Array that contains the name's fields for the Scalar_Read Attribute's Table
	 */
	public static final String[] TAB_SP_RO_STR = {"id_snap", "id_att", "dim_x", "value"};
	/**
	 * Array that contains the name's fields for the Scalar_ReadWrite Attribute's Table
	 */
	public static final String[] TAB_SP_RW_NUM = {"id_snap", "id_att", "dim_x", "read_value", "write_value"};
	/**
	 * Array that contains the name's fields for the Scalar_Read Attribute's Table
	 */
	public static final String[] TAB_SP_RW_STR = {"id_snap", "id_att", "dim_x", "read_value", "write_value"};
	/**
	 * Array that contains the name's fields for the Scalar_ReadWrite Attribute's Table
	 */
	public static final String[] TAB_IM_RO_NUM = {"id_snap", "id_att", "dim_x", "dim_y", "value"};
	/**
	 * Array that contains the name's fields for the Scalar_Read Attribute's Table
	 */
	public static final String[] TAB_IM_RO_STR = {"id_snap", "id_att", "dim_x", "dim_y", "value"};
	/**
	 * Array that contains the name's fields for the Scalar_ReadWrite Attribute's Table
	 */
	public static final String[] TAB_IM_RW_NUM = {"id_snap", "id_att", "dim_x", "dim_y", "read_value", "write_value"};
	/**
	 * Array that contains the name's fields for the Scalar_Read Attribute's Table
	 */
	public static final String[] TAB_IM_RW_STR = {"id_snap", "id_att", "read_value", "write_value"};

    public static final String CLOB_SEPARATOR = ",";
    public static final String CLOB_SEPARATOR_IMAGE_COLS = ",";
    public static final String CLOB_SEPARATOR_IMAGE_ROWS = "~";

}
