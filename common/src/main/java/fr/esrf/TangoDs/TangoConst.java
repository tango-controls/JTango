//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: ingvord $
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
// $Revision: 28511 $
//
//-======================================================================


package fr.esrf.TangoDs;

/**
 * Interface to store all TANGO constants
 *
 * @author $Author: ingvord $
 * @version $Revision: 28511 $
 */
 
public interface TangoConst {
	public static final int	COMMAND    = 0;
	public static final int	ATTRIBUTE  = 1;
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
//			Serialization models
//
//-----------------------------------------------------------------------
	public static final int	BY_DEVICE  = 0;
	public static final int	BY_CLASS   = 1;
	public static final int	BY_PROCESS = 2;	//	Not yet implemented
	public static final int	NO_SYNC    = 3;



	
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
	 * Constant for the TANGO  const string  type
	 */
	public static final int	Tango_CONST_DEV_STRING	=	20;
	/**
 	* Constant for the TANGO  char type
 	*/
	public static final int	Tango_DEV_CHAR	=			21;
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
	public static final int	Tango_DEV_INT		=		27;

/**
 * Constant for the TANGO DevEncoded
 */
	public static final int	Tango_DEV_ENCODED	=		28;
/**
 * Constant for the TANGO Enum
 */
	public static final int	Tango_DEV_ENUM	   =		29;
/**
 * Constant for the TANGO pipe blob
 */
	public static final int	Tango_DEV_PIPE_BLOB	   =    30;
	/**
	 * Constant for the TANGO bool array
	 */
	int Tango_DEVVAR_BOOLEANARRAY = 31;
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
		"DevVarBooleanArray",
		"DevUChar",
		"DevLong64",
		"DevULong64",
		"DevVarLong64Array",
		"DevVarULong64Array",
		"DevInt",
		"DevEncoded",
		"DevEnum",
		"DevPipeBlob",
		"DevVarBooleanArray"
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
  
	public static final String[] Tango_AttrDataFormatName = {
		"Scalar",
		"Spectrum",
		"Image",
		"Unknown"
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
	public static final int	DATA_READY_EVENT = 6;
	public static final int	INTERFACE_CHANGE = 7;
	public static final int	PIPE_EVENT       = 8;
	public static final String[]	eventNames = {
        "change",
        "quality_change",	//	Deprecated !
        "periodic",
        "archive",
		"user_event",
		"attr_conf",
		"data_ready",
		"intr_change",
		"pipe",
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
	 *	Values used for tango access control.
	 */
	public static final int	ACCESS_READ    = 0;
	public static final int	ACCESS_WRITE   = 1;

	/**
	 *	Service Property Names
	 */
	public static final String	CONTROL_SYSTEM    = "CtrlSystem";
	public static final String	SERVICE_PROP_NAME = "Services";
	public static final String	ACCESS_SERVICE    = "AccessControl";



	public static final int	DEFAULT_LOCK_VALIDITY = 10;	//	Seconds
}
