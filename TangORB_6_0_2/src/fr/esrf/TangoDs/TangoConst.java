//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  Interface to store all TANGO constants
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.5  2008/04/11 07:16:16  pascal_verdier
// ATT_CONF_EVENT added
//
// Revision 1.4  2007/08/23 08:32:59  ounsy
// updated change from api/java
//
// Revision 3.16  2007/05/29 08:07:43  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.15  2007/04/18 05:50:23  pascal_verdier
// Tango_QualityName String array added.
//
// Revision 3.14  2007/01/05 07:44:55  pascal_verdier
// Service constants added.
//
// Revision 3.13  2006/09/15 14:52:10  pascal_verdier
// Access modes added.
//
// Revision 3.12  2005/11/10 10:28:14  pascal_verdier
// Tango data types modified.
//
// Revision 3.11  2005/08/10 09:12:26  pascal_verdier
// Initial Revision
//
// Revision 3.10  2005/06/02 14:10:44  pascal_verdier
// Tango_DEV_CHAR type added.
//
// Revision 3.9  2004/12/16 10:18:16  pascal_verdier
// Tango_DEV_UCHAR constant added.
//
// Revision 3.8  2004/09/23 14:05:55  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
// Revision 3.7  2004/05/14 13:47:57  pascal_verdier
// Compatibility with Tango-2.2.0 cpp
// (polling commands and attibites).
//
//
// Copyleft 2000 by European Synchrotron Radiation Facility, Grenoble, France
//-======================================================================


package fr.esrf.TangoDs;

/**
 * Interface to store all TANGO constants
 *
 * @author	$Author$
 * @version	$Revision$
 */
 
public interface TangoConst
{
/**
 * maximun length for device server name (255 characters)
 */
	static final int	Tango_MaxServerNameLength = 			255;

/**
 * TANGO property name (TANGO_HOST)
 */	
	static final String	Tango_SysProperty = "TANGO_HOST";
/**
 * Device server process initial output file
 */
	public static final String	Tango_InitialOutput = "Initial Output";
/**
 * TANGO named object name (database)
 */
	public static final String 	Tango_DbObjName = "database";
/**
 * TANGO dserver device domain name (dserver)
 */
	public static final String	Tango_DSDeviceDomain = "dserver";
/**
 * Device default documentation URL
 */
	public static final String 	Tango_DefaultDocUrl = "http://www.esrf.fr/tango";
/**
 * Resource nnot defined flag
 */
  	static final String	Tango_ResNotDefined ="0";
/**
 * Device default description field (Uninitialised)
 */
	static final String	Tango_DescNotSet ="Uninitialised";

/**
 *	The Tango revision number
 */
	public static final String	Tango_TgLibVers = "2.2.0";
/**
 * TANGO system version
 */	
	static final int	Tango_DevVersion = 				2;
/**
 * Device default black box depth (25)
 */
	static final int 	Tango_DefaultBlackBoxDepth = 			25;

/**
 *	Client timeout as defined by omniORB4.0.0
 */
	public static final String	Tango_CLNT_TIMEOUT = "3000";

/**
 *	Max transfer size 8 MBytes (in byte). Needed by omniORB
 */
	public static final String	Tango_MAX_TRANSFER_SIZE = "8388608";

//-------------------------------------------------------------------------
//
//			For Polling
//
//-------------------------------------------------------------------------

/**
 * Default depth for polling ring buffer
 */
	static final int	Tango_DefaultPollRingDepth = 10;

	public static final String	Tango_PollCommand = "command";
	public static final String	Tango_PollAttribute	 = "attribute";

	public static final int		Tango_MIN_POLL_PERIOD = 20;
	public static final int		Tango_DELTA_T = 1002000000;	//	Used in cpp for since epoch in integer
	public static final double	Tango_DISCARD_THRESHOLD = 0.5;
	public static final int		Tango_DEFAULT_TIMEOUT_SEC = 3;
	public static final int		Tango_DEFAULT_TIMEOUT = 
										(Tango_DEFAULT_TIMEOUT_SEC * 1000);
	public static final int		Tango_DEFAULT_POLL_OLD_FACTOR = 4;

	public static final int		Tango_TG_IMP_MINOR_TO = 10;
	public static final int		Tango_TG_IMP_MINOR_DEVFAILED = 11;
	public static final int		Tango_TG_IMP_MINOR_NON_DEVFAILED = 12;

/*
 *	Device Version
 */
	public static final int		Tango_REL_1 = 0;
	public static final int		Tango_REL_2 = 1;

/*
 *	Polling Object Type
 */
	public static final int		Tango_POLL_CMD  = 0;
	public static final int		Tango_POLL_ATTR = 1;

/*
 *	Polling Command Code
 */
	public static final int		Tango_POLL_ADD_OBJ    = 0;
	public static final int		Tango_POLL_REM_OBJ    = 1;
	public static final int		Tango_POLL_START      = 2;
	public static final int		Tango_POLL_STOP       = 3;
	public static final int		Tango_POLL_UPD_PERIOD = 4;
	public static final int		Tango_POLL_REM_DEV    = 5;

//-------------------------------------------------------------------------
//
//			For attribute
//
//-------------------------------------------------------------------------


/**
 * default value for the writable attribute property
 */
 	static final boolean 	Tango_DefaultWritableProp = 		false;
/**
 * Alarm value not specified
 */
 	static final String 	Tango_AlrmValueNotSpec = 		"Not specified";
/**
 * Associated writable attribute not specified
 */
 	static final String 	Tango_AssocWritNotSpec =		"None";
/**
 * Label not specified
 */
 	static final String 	Tango_LabelNotSpec = 			"No label";
/**
 * Description not specified
 */
 	static final String 	Tango_DescNotSpec = 			"No description";
/**
 * Unit not specified
 */
 	static final String 	Tango_UnitNotSpec = 			"No unit";
/**
 * Standard unit not specified
 */
 	static final String 	Tango_StdUnitNotSpec = 			"No standard unit";
/**
 * Display unit not specified
 */
 	static final String 	Tango_DispUnitNotSpec = 		"No display unit";
/**
 * Format not specified
 */
 	static final String 	Tango_FormatNotSpec = 			"No format";
/**
 * Special attribute name meaning all attributes
 */
 	public static final String	Tango_AllAttr   = 		"All attributes";
/**
 * Special attribute name meaning all attributes for Device_3Impl and later
 */
	public static final String	Tango_AllAttr_3 = 		"All attributes_3";

//-------------------------------------------------------------------------	
//
// 			For the blackbox
//
//-------------------------------------------------------------------------

	static final int	Req_Unknown = 					0;
	static final int 	Req_Operation = 				1;
	static final int	Req_Attribute = 				2;
		
	static final int	Attr_Unknown = 					0;
	static final int	Attr_Name = 					1;
	static final int	Attr_Description = 				2;
	static final int	Attr_State = 					3;
	static final int	Attr_Status = 					4;
	static final int	Attr_AdmName = 					5;
		
	static final int	Op_Unknown = 					0;
	static final int	Op_Command_inout = 				1;
	static final int	Op_BlackBox = 					2;
	static final int	Op_Ping = 						3;
	static final int	Op_Info = 						4;
	static final int	Op_Command_list = 				5;
	static final int	Op_Command = 					6;
	static final int	Op_Get_Attr_Config =			7;
	static final int	Op_Set_Attr_Config =			8;
	static final int	Op_Read_Attr =					9;
	static final int	Op_Write_Attr =					10;

	static final int	Op_Command_inout_2 =            11;
	static final int	Op_Command_list_2 =             12;
	static final int	Op_Command_2 =                  13;
	static final int	Op_Get_Attr_Config_2 =          14;
	static final int	Op_Read_Attr_2 =                15;
	static final int	Op_Command_inout_history_2 =    16;
	static final int	Op_Read_Attr_history_2 =        17;

//------------------------------------------------------------------------
//
//			For the command and attribute display type
//
//-----------------------------------------------------------------------

	public static final int	Tango_OPERATOR_CMD =			0;
	public static final int	Tango_EXPERT_CMD   =			1;
	
	public static final int	Tango_OPERATOR_ATTR = 			0;
	public static final int	Tango_EXPERT_ATTR   = 			1;
	
//------------------------------------------------------------------------
//
// 			All the TANGO types
//
//------------------------------------------------------------------------
	public static final String	NotANumber = "NaN";

/**
 * Constant for the TANGO DevVoid type
 */
	public static final int	Tango_DEV_VOID =			0;
/**
 * Constant for the TANGO DevBoolean type
 */
	public static final int	Tango_DEV_BOOLEAN =			1;
/**
 * Constant for the TANGO DevShort type
 */
	public static final int	Tango_DEV_SHORT = 			2;
/**
 * Constant for the TANGO DevLong type
 */
	public static final int	Tango_DEV_LONG =			3;
/**
 * Constant for the TANGO DevFloat type
 */
 	public static final int	Tango_DEV_FLOAT =			4;
/**
 * Constant for the TANGO DevDouble type
 */
 	public static final int	Tango_DEV_DOUBLE =			5;
/**
 * Constant for the TANGO DevUShort type
 */
	public static final int	Tango_DEV_USHORT =			6;
/**
 * Constant for the TANGO DevULong type
 */
	public static final int	Tango_DEV_ULONG =			7;
/**
 * Constant for the TANGO DevString type
 */
	public static final int	Tango_DEV_STRING =			8;
/**
 * Constant for the TANGO DevVarCharArray type
 */
	public static final int	Tango_DEVVAR_CHARARRAY =		9;
/**
 * Constant for the TANGO DevVarShortArray type
 */
	public static final int	Tango_DEVVAR_SHORTARRAY =		10;
/**
 * Constant for the TANGO DevVarLongArray type
 */
	public static final int	Tango_DEVVAR_LONGARRAY =		11;
/**
 * Constant for the TANGO DevVarFloatArray type
 */
	public static final int	Tango_DEVVAR_FLOATARRAY =		12;
/**
 * Constant for the TANGO DevVarDoubleArray type
 */
	public static final int	Tango_DEVVAR_DOUBLEARRAY =		13;
/**
 * Constant for the TANGO DevVarUShortArray type
 */
	public static final int	Tango_DEVVAR_USHORTARRAY =		14;
/**
 * Constant for the TANGO DevVarULongArray type
 */
	public static final int	Tango_DEVVAR_ULONGARRAY =		15;
/**
 * Constant for the TANGO DevVarStringArray type
 */
	public static final int	Tango_DEVVAR_STRINGARRAY =		16;
/**
 * Constant for the TANGO DevVarLongStringArray type
 */
	public static final int	Tango_DEVVAR_LONGSTRINGARRAY =		17;
/**
 * Constant for the TANGO DevVarDoubleStringArray type
 */
	public static final int	Tango_DEVVAR_DOUBLESTRINGARRAY =	18;
/**
 * Constant for the TANGO State type
 */
	public static final int	Tango_DEV_STATE	=			19;
/**
 * Constant for the TANGO  char type
 */
	public static final int	Tango_DEV_CHAR	=			21;
/**
 * Constant for the TANGO  const string  type
 */
	public static final int	Tango_CONST_DEV_STRING	=	20;
/**
 * Constant for the TANGO unsigned char type
 */
	public static final int	Tango_DEV_UCHAR	=			22;
/**
 * Constant for the TANGO long 64 bits
 */
	public static final int	Tango_DEV_LONG64 = 		    23;
/**
 * Constant for the TANGO unsigned long 64 bits
 */
	public static final int	Tango_DEV_ULONG64 =		    24;
/**
 * Constant for the TANGO Array of long 64 bits
 */
	public static final int Tango_DEVVAR_LONG64ARRAY =	25;
/**
 * Constant for the TANGO Array of unsigned long 64 bits
 */
	public static final int	Tango_DEVVAR_ULONG64ARRAY =	26;
/**
 * Constant for the TANGO int
 */
	public static final int	Tango_DEV_INT	=		27;

/**
 * Array with TANGO data type name. To get the type name for type Tango_DEV_LONG, 
 * use Tango_CmdArgTypeName[Tango_DEV_LONG]
 */
	public static final String[] Tango_CmdArgTypeName = {
		"DevVoid",
		"DevBoolean",
		"DevShort",
		"DevLong",
		"DevFloat",
		"DevDouble",
		"DevUShort",
		"DevULong",
		"DevString",
		"DevVarCharArray",
		"DevVarShortArray",
		"DevVarLongArray",
		"DevVarFloatArray",
		"DevVarDoubleArray",
		"DevVarUShortArray",
		"DevVarULongArray",
		"DevVarStringArray",
		"DevVarLongStringArray",
		"DevVarDoubleStringArray",
		"State",
		"ConstDevString",
		"DeVarBooleanArray",
		"DevUChar",
		"DevLong64",
		"DevULong64",
		"DevVarLong64Array",
		"DevVarULong64Array",
	};

//-------------------------------------------------------------------------
//
// 			All the TANGO state names
//
//-------------------------------------------------------------------------

/**
 * Array with TANGO device state name. To get the device state name for state ON, 
 * use Tango_StateName[State._ON]
 */
	public static final String[] Tango_DevStateName = {
		"ON",
		"OFF",
		"CLOSE",
		"OPEN",
		"INSERT",
		"EXTRACT",
		"MOVING",
		"STANDBY",
		"FAULT",
		"INIT",
		"RUNNING",
		"ALARM",
		"DISABLE",
		"UNKNOWN"
	};

	public static final String[] Tango_QualityName = {
		"VALID",
		"INVALID",
		"ALARM",
		"CHANGING",
		"WARNING"
	};
  
	/**
	 *	Event types (as numerical values)
	 */
	public static final int	CHANGE_EVENT     = 0;
	public static final int	QUALITY_EVENT    = 1;
	public static final int	PERIODIC_EVENT   = 2;
	public static final int	ARCHIVE_EVENT    = 3;
	public static final int	USER_EVENT       = 4;
	public static final int	ATT_CONF_EVENT   = 5;
	public static final String[]	eventNames = {
        "change",
        "quality_change",
        "periodic",
        "archive",
		"user_event",
		"attr_conf",
	};
	public static final boolean	NOT_STATELESS = false;
	public static final boolean	STATELESS     = true;
	
	/**
	 *	Logging levels (as numerical values)
	 */
	public static final int	LOGGING_OFF     = 0;
	public static final int	LOGGING_FATAL   = 1;
	public static final int	LOGGING_ERROR   = 2;
	public static final int	LOGGING_WARN    = 3;
	public static final int	LOGGING_INFO    = 4;
	public static final int	LOGGING_DEBUG   = 5;
  
	/**
	 *	Logging levels (as strings)
	 */
  public static final String[] LOGGING_LEVELS = 
       { "OFF", "FATAL", "ERROR", "WARN", "INFO", "DEBUG"};
  
	/**
	 *	Logging target names
	 */
  public static final String LOGGING_FILE_TARGET    = "file";
  public static final String LOGGING_DEVICE_TARGET  = "device";
  public static final String LOGGING_CONSOLE_TARGET = "console";
  
	/**
	 *	Logging target names
	 */
  public static final int LOGGING_UNKNOWN_TARGET_ID = -1;
  public static final int LOGGING_FILE_TARGET_ID    =  0;
  public static final int LOGGING_DEVICE_TARGET_ID  =  1;
  public static final int LOGGING_CONSOLE_TARGET_ID =  2;
  
	/**
	 *	Logging target type/name separator
	 */
  public static final String LOGGING_SEPARATOR = "::";

	/**
	 *	Rolling threshold for file targets (in Kb)
	 */
  public static final long LOGGING_MIN_RFT = 500;
  public static final long LOGGING_DEF_RFT = 2048;
  public static final long LOGGING_MAX_RFT = 20480;
   
	/**
	 * Main logging path environment variable name
	 */
  public static final String TANGO_LOG_PATH = "TANGO_LOG_PATH";

	/**
	 *	Values used for tango access controle.
	 */
	public static final int	ACCESS_READ    = 0;
	public static final int	ACCESS_WRITE   = 1;

	/**
	 *	Service Property Names
	 */
	public static final String	CONTROL_SYSTEM    = "CtrlSystem";
	public static final String	SERVICE_PROP_NAME = "Services";
	public static final String	ACCESS_SERVICE    = "AccessControl";


}
