//+=============================================================================
//
// file :               Logging.java
//
// description :        Java source for main Logging features.
//
// project :            TANGO
//
// $Author: :          N.Leclercq
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
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
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-=============================================================================

package fr.esrf.TangoDs;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.Database;
import fr.esrf.TangoApi.DbDatum;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/**
 * Implements the main TANGO logging features
 */
 
public class Logging implements TangoConst
{
 /**
  * The unique instance of the Logging class
  */
	private static Logging _instance = null;

  /** 
   * The TANGO core logger (used to log TANGO core message)
   */
  private static Logger _core_logger = null;

  /** 
   * The logging path 
   */
  private String logging_path = null;
  
  /** 
   * The rolling file threshold
   */
  private long rft = LOGGING_DEF_RFT;
  
  /** 
   * The rolling file threshold
   */
  private int cmd_line_level = 0;

  //- Some default target names
  final String _DefaultTargetName = "default";
  
 /**
  * A private nested class store logging proprety
  */
  public class LoggingProperties {
    public String logging_path = null;
    public String[] logging_targets = null; 
    public Level logging_level = Level.WARN;
    public long logging_rft = LOGGING_DEF_RFT;
  }
  
 /**
  * Get the singleton object reference.
  *
  * This method returns a reference to the object of the Logging class.
  * If the class has not been initialised with it's init method, this method
  * print a message and abort the device server process
  *
  * @return The Logging object reference
  */
	public static Logging instance()
	{
		if (_instance == null)
		{
			System.err.println("Logging is not initialised !!!");
			System.err.println("Exiting");
			System.exit(-1);
		}
		return _instance;
	}
  
 /**
  * Returns the TANGO core logger.
  * Make it static for both avoiding to call instance() each time we want to obtain
  * the core_logger reference and ensuring that UtilPrint does not crash if the
  * core_logger is not already instciated (log statements executed before logging init) 
  */
	public static Logger core_logger()
	{
		return _core_logger;
	}
  
 /**
  * Create and get the singleton object reference.
  *
  * This method returns a reference to the object of the Logging class.
  * If the class singleton object has not been created, it will be 
  * instanciated
  *
  * @param ds_name The device server executable name
  * @param db The database object
  * @return The Logging object reference
  */
	public static Logging init(String ds_name, int trace_level, Database db)
	{
		if (_instance == null) {
			_instance = new Logging(ds_name, trace_level, db);
		}
		return _instance;
	}

 /**
  * Constructs a newly allocated Logging object.
  * This constructor is protected following the singleton pattern
  *
  * @param ds_name The device server executable name
  * @param db The database object
  */
	protected Logging (String ds_name, int trace_level, Database db)
	{
    //- Store trace_level locally
    cmd_line_level = trace_level;
    //- Build DServer name
    final String ds_dev_name = "dserver/" + ds_name;
    verbose("Initializing logging for " + ds_dev_name);
    //- Instanciate the logger 
    Logger logger = Logger.getLogger(ds_dev_name.toLowerCase());
    if (logger == null) {
      verbose("\tFailed to instanciate the TANGO core-logger");
      verbose("\tAborting logging intialization");
      return;
    }
    verbose("\tTANGO core-logger instanciated");
    //- Disable additivity
    logger.setAdditivity(false);
    //- Default logging path
    String default_log_path = "/tmp/tango";
    try {
      //- If running under Windows then change the syntax
      //- Check file.separator instead of os.name (don't know what to expect from os.name)
      String os_name = System.getProperty("os.name");
      verbose("\tRunning on " + os_name);
      if (os_name.toLowerCase().indexOf("windows") != -1)
        default_log_path = "c:/tango";
    }
    catch (java.lang.Exception e) {
      //- caught one of the System.getProperty exceptions
      //- ignore it and return
      verbose("\tException caught while trying to get <os.name> system property");
      verbose("\tAborting logging intialization");
      return;
    }
    //- Get TANGO_LOG_PATH value and set logging_path
    String tango_log_path = System.getProperty(TANGO_LOG_PATH);
    if (tango_log_path == null || tango_log_path.length() == 0) {
      logging_path = default_log_path;
      verbose("\tEnv. variable TANGO_LOG_PATH not set");
    } else {
      logging_path = tango_log_path;
    }
    //- Create process wide default logging directory 
    logging_path += "/" + ds_name;
    verbose("\tDefault logging directory set to " + logging_path);
    //- Is logging level set from command line?
    boolean level_set_from_cmd_line = (cmd_line_level != 0);
    //- Set core logger initial level
    Level initial_level = Level.WARN;
    if (level_set_from_cmd_line) {
      //- v1..2 -> mapped to INFO
      if (cmd_line_level <= 2)
        initial_level = Level.INFO;
      //- v3..5 -> mapped to DEBUG
      else
        initial_level = Level.DEBUG;
    }
    logger.setLevel(initial_level);
    verbose("\tTANGO core-logger intial level is " + initial_level.toString());
    //- If not using the TANGO db then return
    if (db == null) {
     verbose("\tNot using the database. Logging Intialization complete");
     return; 
    }
    verbose("\tReading logging properties from database");
    LoggingProperties properties = get_logging_properties(logger, db);
    if (properties == null) {
      verbose("\tFailed to obtain logging properties from database");
      verbose("\tAborting logging intialization");
      return;
    }
    if (properties.logging_path != null) {
      logging_path = properties.logging_path; 
      verbose("\tLogging path changed to " + logging_path);
    }
    if (level_set_from_cmd_line == false) {
      logger.setLevel(properties.logging_level);
      verbose("\tLogging level set to " + properties.logging_level.toString());
    }
    if (rft != properties.logging_rft) {
      rft = properties.logging_rft;
      verbose("\tRolling threshold changed to " + String.valueOf(rft));
    }
    verbose("\tAdding initial targets to TANGO core-logger");  
    //- Be sure there is a console target if level set from cmd line
    if (level_set_from_cmd_line) {
      try {
        add_logging_target(logger, LOGGING_CONSOLE_TARGET);   
      } catch (DevFailed e) {
        //- ignore any exception
      }
    }
    //- Now, add the remaining targets
    if (properties.logging_targets != null) {
		for (String logging_target : properties.logging_targets)
		{
			try
			{
				add_logging_target(logger, logging_target);
			} catch (DevFailed e)
			{
				//- ignore any exception
			}
		}
    }
    //- Set core logger reference 
    _core_logger = logger;
  }

 /**
  * Reads logging properties from TANGO database 
  */
  public LoggingProperties get_logging_properties (Logger logger, Database db)
  {
    //- Instanciate the returned value
    LoggingProperties properties = new LoggingProperties();
    //- Check input
    if (logger == null) {
      return properties;
    }
    try {
      //- Be sure the specified Database is valid
      if (db == null) {
       return properties;
      }
      //- Get logging properties from database
      String[] prop_names = new String[4];
      //- Logging path property (overwrites env. var)
      prop_names[0] = "logging_path";
      //- Logging file rolling threshold property
      prop_names[1] = "logging_rft";
      //- Logging level property
      prop_names[2] = "logging_level";
      //- Logging target property
      prop_names[3] = "logging_target";
      //- Get properties from db
      DbDatum[] db_data  = db.get_device_property(logger.getName(), prop_names);
      //- Set logging path
      if (db_data[0].is_empty() == false) {
        properties.logging_path = db_data[0].extractString();
      }
      //- Set logging rft
      if (db_data[1].is_empty() == false) {
        properties.logging_rft = db_data[1].extractLong();
      }
      if (properties.logging_rft < LOGGING_MIN_RFT)
         properties.logging_rft = LOGGING_MIN_RFT;  
      else if (properties.logging_rft > LOGGING_MAX_RFT)
         properties.logging_rft = LOGGING_MAX_RFT;  
      //- Set logging level (if not set from cmd line)
      if (db_data[2].is_empty() == false) {
        String level_str = db_data[2].extractString(); 
        properties.logging_level = tango_to_log4j_level(level_str);
      } 
      //- Get logging targets (will be set later)
      if (db_data[3].is_empty() == false) {
        properties.logging_targets = db_data[3].extractStringArray();
      }
    }
    catch (java.lang.Exception e) {
      //- ignore any exception
      e.printStackTrace();
    }
    return properties;
  }
  
 /**
  * Adds logging target(s) to the specified device(s)
  *
  * @param 	dvsa A string array where str[i]=dev-name and str[i+1]=target_type::target_name
  */	
	public void add_logging_target (String[] dvsa) throws DevFailed
	{
    //- Fight against "zombie appender" synfrom
    kill_zombie_appenders();
    //- N x [device-name, ttype::tname] expected
    if ((dvsa.length % 2) != 0) {
      String desc = "Incorrect number of arguments";
      Except.throw_exception("API_MethodArgument", desc, "Logging::add_logging_target");
    }
    //- Device name pattern
    String pattern;
    //- The device name list which name match pattern
    Vector dl;
    //- For each tuple {dev-name, ttype::tname} in dvsa
    for (int t = 0; t < dvsa.length;) {
      //- Get device name pattern
      pattern = dvsa[t++].toLowerCase();
      //- Get devices which name match the pattern
      dl = Util.instance().get_device_list(pattern);
      //- Throw exception if list is empty
      if (dl.size() == 0) {
        String desc = "No device matching pattern <" + pattern + ">";
        Except.throw_exception("API_DeviceNotFound", desc, "Logging::add_logging_target");
      }
      //- For each device matching pattern ...
		for (Object aDl : dl)
		{
			Logger logger = ((DeviceImpl) aDl).get_logger();
			add_logging_target(logger, dvsa[t++]);
		}
    }
	}

 /**
  * Adds a logging target to the specified logger (i.e. device).
  *
  * @param 	logger A lo4j logger to which the target will be added
  * @param 	ttype The target type
  * @param 	tname The target name
  */	
	public void add_logging_target (Logger logger, String ttype, String tname) throws DevFailed
	{
    add_logging_target(logger, ttype + LOGGING_SEPARATOR + tname);
	}
  
 /**
  * Adds a logging target to the specified logger (i.e. device)
  *
  * @param 	logger A lo4j logger to which the target will be added
  * @param 	ttype_tname A string containing something like target_type::target_name
  */	
	public void add_logging_target (Logger logger, String ttype_tname) throws DevFailed
	{
    //- Check input (is logger valid)
    if (logger == null) {
     return;
    }
    //- Avoid case sensitive troubles
    ttype_tname = ttype_tname.toLowerCase();
    //- Split ttype_tname into target type and name
    String ttype = get_target_type(ttype_tname);
    String tname = get_target_name(ttype_tname); 
    //- Find out target type
    int type_id = LOGGING_UNKNOWN_TARGET_ID;
    if (LOGGING_FILE_TARGET.equals(ttype)) {
      type_id = LOGGING_FILE_TARGET_ID;
    }
    else if (LOGGING_DEVICE_TARGET.equals(ttype)) {
      type_id = LOGGING_DEVICE_TARGET_ID;
    }
    else if (LOGGING_CONSOLE_TARGET.equals(ttype)) {
      type_id = LOGGING_CONSOLE_TARGET_ID;
    }
    else {
      String desc = "Invalid logging target type specified (" + ttype + ")";
      Except.throw_exception("API_MethodArgument", desc, "Logging::add_logging_target");
    }
    //- Full file name for file target
    String full_file_name = null;
    //- Internal target name
    String appender_name = ttype + LOGGING_SEPARATOR;
    switch (type_id) {
      case LOGGING_FILE_TARGET_ID:
        if (tname.equals(_DefaultTargetName)) {
          //- use default path and file name
          full_file_name = logging_path + "/";
          full_file_name += device_to_file_name(logger.getName()) + ".log";
        }
        else if (tname.indexOf('/') != -1) {
          //- user specified path and file name
          full_file_name = tname;
        }
        else {
          //- user specified file name
          full_file_name = logging_path + "/" + tname;
        }
        File file = new File(full_file_name);
        File dir = new File(file.getParent());
        if (dir.isDirectory() == false) {
          try {
            dir.mkdirs();
          } 
          catch (Exception e) {
            String desc = "Failed to create directory " + dir.getPath();
            Except.throw_exception("API_SystemException", desc, "Logging::add_logging_target");
          }
        }
        tname = full_file_name;
        appender_name += tname;
        break;
      case LOGGING_DEVICE_TARGET_ID:
        if (tname.equals(_DefaultTargetName)) {
          String desc = "Device target name must be specified (no default value)";
          Except.throw_exception("API_MethodArgument", desc, "Logging::add_logging_target");
        }
        appender_name += tname;
        break;
      case LOGGING_CONSOLE_TARGET_ID:
        appender_name += LOGGING_CONSOLE_TARGET;
        break;
    }
    //- Attach appender to the logger (if not already attached)
    Appender appender = logger.getAppender(appender_name);
    if (appender != null) {
      Util.out4.println("Target " + appender_name + " is already attached to " + logger.getName());  
      return;
    }
    //- Instanciate the appender
    try {
      Util.out4.println("Adding " + appender_name + " to " +  logger.getName());
      switch (type_id) {
        case LOGGING_FILE_TARGET_ID:
			//noinspection NestedTryStatement
			try {
			  appender = new TangoRollingFileAppender(appender_name, full_file_name, rft);
			} catch (IOException ioe) {
			  String desc = "Could not open logging file " + full_file_name;
			  Util.out4.println(desc);
			  Except.throw_exception("API_CannotOpenFile", desc, "Logging::add_logging_target");
			}
          break;
        case LOGGING_DEVICE_TARGET_ID:
          appender = new TangoDeviceAppender(logger.getName(), appender_name, tname);
          break;
        case LOGGING_CONSOLE_TARGET_ID:
          appender = new TangoConsoleAppender(appender_name);
          break;
      }
      //- Attach appender to the logger
      logger.addAppender(appender);
      Util.out4.println("Added logging target " + appender_name + " to " +  logger.getName());
    }
    catch (DevFailed df) {
      throw df;
    }
    catch (Exception e) {
      String desc = "System exception caugth while trying to add target " + ttype_tname + " to " +  logger.getName();
      Except.throw_exception("API_SystemException", desc , "Logging::add_logging_target");
    }
	}
  
 /**
  * Removes logging target(s) from target list of the specified device(s) 
  *
  * @param 	dvsa A string array where str[i]=dev-name and str[i+1]=target_type::target_name
  */	
	public void remove_logging_target (String[] dvsa) throws DevFailed
	{ 
    //- Fight against "zombie appender" synfrom
    kill_zombie_appenders();
    //- N x [device-name, ttype::tname] expected
    if ((dvsa.length % 2) != 0) {
      String desc = "Incorrect number of arguments";
      Except.throw_exception("API_MethodArgument", desc, "Logging::remove_logging_target");
    }
    //- The device name list which name match pattern
    Vector dl;
    //- For each tuple {dev-name, ttype::tname} in dvsa
    for (int t = 0; t < dvsa.length;) {
      //- Get device name pattern
      String pattern = dvsa[t++].toLowerCase();
      //- Get devices which name match the pattern
      dl = Util.instance().get_device_list(pattern);
      //- Throw exception if list is empty
      if (dl.size() == 0) {
        String desc = "No device matching pattern <" + pattern + ">";
        Except.throw_exception("API_DeviceNotFound", desc, "Logging::remove_logging_target");
      }
      //- Split type::name into target type and name
      String type = get_target_type(dvsa[t]).toLowerCase();
      String name = get_target_name(dvsa[t++]).toLowerCase();
      int type_id = LOGGING_UNKNOWN_TARGET_ID;
      if (LOGGING_FILE_TARGET.equals(type)) {
        type_id = LOGGING_FILE_TARGET_ID;
      }
      else if (LOGGING_DEVICE_TARGET.equals(type)) {
        type_id = LOGGING_DEVICE_TARGET_ID;
      }
      else if (LOGGING_CONSOLE_TARGET.equals(type)) {
        type_id = LOGGING_CONSOLE_TARGET_ID;
        name = LOGGING_CONSOLE_TARGET;
      }
      else {
        String desc = "Invalid logging target type specified (" + type + ")";
        Except.throw_exception("API_MethodArgument", desc, "Logging::remove_logging_target");
      }
      //- Remove all targets ?
      boolean remove_all_targets = name.equals("*");
      //- For each device matching pattern ...
		for (Object aDl : dl)
		{
			Logger logger = ((DeviceImpl) aDl).get_logger();
			// CASE I: remove ONE target of type <type>
			if (remove_all_targets == false)
			{
				// build full appender name
				String full_name = type + LOGGING_SEPARATOR;
				// a special case : target is the a file
				if (type_id == LOGGING_FILE_TARGET_ID)
				{
					if (name.equals(_DefaultTargetName))
					{
						//- use default path and file name
						full_name = logging_path + "/";
						full_name += device_to_file_name(logger.getName()) + ".log";
					}
					else if (name.indexOf('/') != -1)
					{
						//- user specified path and file name
						full_name = name;
					}
					else
					{
						//- user specified file name
						full_name = logging_path + "/" + name;
					}
				}
				else
				{
					full_name += name;
				}
				Util.out4.println("Removing target " + full_name + " from " + logger.getName());
				// remove appender from device's logger
				logger.removeAppender(full_name);
			}
			// CASE II: remove ALL targets of type <tg_type_str>
			else
			{
				Util.out4.println("Removing ALL <" + type + "> targets from " + logger.getName());
				// get logger's appender list
				Enumeration all_appenders = logger.getAllAppenders();
				// find appenders of type <type> in <al>
				String prefix = type + LOGGING_SEPARATOR;
				// for each appender in <al>
				while (all_appenders.hasMoreElements())
				{
					Appender appender = (Appender) all_appenders.nextElement();
					if (appender.getName().indexOf(prefix) != -1)
					{
						logger.removeAppender(appender.getName());
					}
				} // all_appenders.hasMoreElements
			} // else (if remove_all_targets)
		} // for each device in <dl>
    }
	}

  /**
  * Set logging level for the specified devices
  *
  * @param dvlsa A Tango.DevVarLongStringArray containing device-names and logging levels 
  */	
	public void set_logging_level (DevVarLongStringArray dvlsa) throws DevFailed
	{ 
    //- Check input
    if (dvlsa.svalue.length != dvlsa.svalue.length) {
      String desc = "Imcompatible command argument type, long and string arrays must have the same length";
      Except.throw_exception("API_IncompatibleCmdArgumentType", desc, "Logging::set_logging_level");
    }
    //- For each entry in dvlsa.svalue
    for (int i = 0; i < dvlsa.svalue.length; i++) {
      //- Check/get logging level (may throw DevFailed)
      Level level = tango_to_log4j_level(dvlsa.lvalue[i]);
      //- Get ith wilcard
      String pattern = dvlsa.svalue[i].toLowerCase();
      //- Get devices which name matches the pattern pattern
      Vector dl = Util.instance().get_device_list(pattern);
      //- For each device in dl
      Iterator it = dl.iterator();
      while (it.hasNext()) {
        Logger logger = ((DeviceImpl)it.next()).get_logger();
        if (logger == null) {
          String desc = "Internal error. Got invalid logger for device " + logger.getName();
          Except.throw_exception("API_InternalError", desc, "Logging::set_logging_level");
        }
        // set logger's level
        logger.setLevel(level);
        Util.out4.println("Logging level set to " + level.toString() + " for device " + logger.getName());
      } //  while it.hasNext
    } // for i
	}

  /**
  * Get logging level for the specified devices
  *
  * @param dvsa A Tango.DevVarStringArray containing device names
  */	
	public DevVarLongStringArray get_logging_level (String[] dvsa) throws DevFailed
	{ 
    //- Temp vector
    int i;
    Iterator it;
    Vector tmp_name = new Vector();
    Vector tmp_level = new Vector();
    //- For each entry in dvsa
    for (i = 0; i < dvsa.length; i++) {
      //- Get devices which name matches the pattern pattern
      Vector dl = Util.instance().get_device_list(dvsa[i].toLowerCase());
      //- For each device in dl
      it = dl.iterator();
      while (it.hasNext()) {
        DeviceImpl dev = (DeviceImpl)it.next();
        tmp_name.addElement(dev.get_name());
        tmp_level.addElement(dev.get_logger().getLevel());
      }
    }
    //- Instanciate retuned value
    DevVarLongStringArray dvlsa = new DevVarLongStringArray();
    dvlsa.lvalue = new int[tmp_level.size()];
    dvlsa.svalue = new String[tmp_name.size()];
    i = 0;
    Iterator name_it = tmp_name.iterator();
    Iterator level_it = tmp_level.iterator();
    while (name_it.hasNext() && level_it.hasNext()) {
      dvlsa.svalue[i] = (String)name_it.next();
      dvlsa.lvalue[i] = log4j_to_tango_level((Level)level_it.next());
      i++; 
    }
    return dvlsa;
	}

  /**
  * Get logging target for the specified devices
  *
  * @param dev_name The device names
  */	
	public String[] get_logging_target (String dev_name) throws DevFailed
	{ 
    //- Get device by name
    DeviceImpl dev = Util.instance().get_device_by_name(dev_name);
    //- Get device targets (i.e appenders)
    Enumeration all_appenders  = dev.get_logger().getAllAppenders();
    //- Instanciate returned value 
    int num_appenders = 0;
    Enumeration a_shame_copy = dev.get_logger().getAllAppenders();
    while (a_shame_copy.hasMoreElements()) {
      num_appenders++;
      a_shame_copy.nextElement();
    }
    String[] targets = new String[num_appenders];
    //- Populate the returned value
    num_appenders = 0;
    while (all_appenders.hasMoreElements()) {
      Appender appender = (Appender)all_appenders.nextElement();
      targets[num_appenders++] = appender.getName();
    }
    return targets;
	}
  
  /**
  * For each device, save its current logging Level then set it to OFF
  */	
	public void stop_logging ()
	{ 
   		Vector dl = Util.instance().get_device_list("*");
		for (Object aDl : dl)
		{
			((DeviceImpl) aDl).stop_logging();
		}
	}

  /**
  * For each device, restore the logging level to the value saved during a previous call to stop_logging 
  */	
	public void start_logging ()
	{ 
    	Vector dl = Util.instance().get_device_list("*");
		for (Object aDl : dl)
		{
			((DeviceImpl) aDl).start_logging();
		}
	}
  
 /**
  * Kills zombie targets (i.e. appenders)
  */	
	public void kill_zombie_appenders ()
	{ 
   		//- Get all devices
    	Vector dl = Util.instance().get_device_list("*");
    	//- Check appenders validity then kill them if needed
		for (Object aDl : dl)
		{
			//- Get device reference
			DeviceImpl dev = (DeviceImpl) aDl;
			//- Get device logger
			Logger logger = dev.get_logger();
			if (logger != null)
			{
				Enumeration all_appenders = logger.getAllAppenders();
				while (all_appenders.hasMoreElements())
				{
					Appender appender = (Appender) all_appenders.nextElement();
					if (((TangoAppender) appender).isValid() == false)
					{
						Util.out4.println("Removing zombie appender " + dev.get_name() + LOGGING_SEPARATOR + appender.getName());
						logger.removeAppender(appender);
					}
				}
			}
		}
	} 

  /**
   * Split type::name into type and name - returns type
   */
  private String get_target_type (String ttype_tname) 
  {
    String[] split;
    try {
      split = ttype_tname.split(LOGGING_SEPARATOR);
    } catch (Exception e) {
      return "unknown";
    }
    return split[0];
  }
  
  /**
   * Split type::name into type and name - returns name
   */
  private String get_target_name (String ttype_tname) 
  {
    String[] split;
    try {
      split = ttype_tname.split(LOGGING_SEPARATOR);
    } catch (Exception e) {
      return _DefaultTargetName;
    }
    return (split.length > 1) ? split[1] :  _DefaultTargetName;
  }

  /**
   * Given a device name returns its default logging file name
   */
  private String device_to_file_name (String dev_name) 
  {
    return dev_name.replace('/', '_');
  }
  
  /**
   * Prints logging initialization messages
   */
  private void verbose(String msg) 
  {
    if (cmd_line_level >= 4) {
     System.out.println(msg); 
    }
  }

  /**
   * Given to TANGO logging level, converts it to lo4j level
   */
  public Level tango_to_log4j_level (int level) throws DevFailed
  {
    switch (level) {
      case LOGGING_OFF:
        return Level.OFF;
      case LOGGING_FATAL:
        return Level.FATAL;
      case LOGGING_ERROR:
        return Level.ERROR;
     case LOGGING_WARN:
        return Level.WARN;
      case LOGGING_INFO:
        return Level.INFO;
      case LOGGING_DEBUG:
        return Level.DEBUG;
      default:
        String desc = "Invalid logging level specified (out of range)";
        Except.throw_exception("API_MethodArgument", desc, "Logging::tango_to_log4j_level");
    }
    //- Make compiler happy
    return Level.WARN;
  }
  
  /**
   * Given to TANGO logging level, converts it to lo4j level
   */
  public Level tango_to_log4j_level (String level) 
  {
   level = level.toUpperCase();
   if (level.equals(LOGGING_LEVELS[LOGGING_OFF])) {
     return Level.OFF;
   }
   if (level.equals(LOGGING_LEVELS[LOGGING_FATAL])) {
     return Level.FATAL;
   }
   if (level.equals(LOGGING_LEVELS[LOGGING_ERROR])) {
     return Level.ERROR;
   }
   if (level.equals(LOGGING_LEVELS[LOGGING_INFO])) {
     return Level.INFO;
   }
   if (level.equals(LOGGING_LEVELS[LOGGING_DEBUG])) {
     return Level.DEBUG;
   }
   return Level.WARN;
  }
  
  /**
   * Given to log4j logging level, converts it to TANGO level
   */
  public int log4j_to_tango_level (Level level) 
  {
   if (level.equals(Level.OFF)) {
     return LOGGING_OFF;
   }
   if (level.equals(Level.FATAL)) {
     return LOGGING_FATAL;
   }
   if (level.equals(Level.ERROR)) {
     return LOGGING_ERROR;
   }
   if (level.equals(Level.WARN)) {
     return LOGGING_WARN;
   }
   if (level.equals(Level.INFO)) {
     return LOGGING_INFO;
   }
   return LOGGING_DEBUG;
  }
  
  /**
   * Set the specified logger's rolling threshold 
   */
  public void set_rolling_file_threshold (Logger logger, long rft)
  {
    if (logger == null) {
      return; 
    }
    if (rft < LOGGING_MIN_RFT) {
      rft = LOGGING_MIN_RFT;  
    }
    else if (rft > LOGGING_MAX_RFT) {
      rft = LOGGING_MAX_RFT;  
    }
    String prefix = LOGGING_FILE_TARGET + LOGGING_SEPARATOR;
    Enumeration all_appenders = logger.getAllAppenders();
    while (all_appenders.hasMoreElements()) {
      Appender appender = (Appender)all_appenders.nextElement();
      if (appender.getName().indexOf(prefix) != -1) {
        TangoRollingFileAppender trfa = (TangoRollingFileAppender)appender;
        trfa.setMaximumFileSize(rft * 1024);
      }
    }
  }
}
  
