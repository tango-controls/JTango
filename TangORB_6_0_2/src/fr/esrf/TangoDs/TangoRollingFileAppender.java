//+============================================================================
//
// file :               TangoRollingFileAppender.java
//
// description :        Java source code for the TangoRollingFileAppender class. 
//
// project :            TANGO
//
// author(s) :          N.Leclercq
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;

// java stuffs
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.xml.XMLLayout;

import java.io.IOException;

/**
 * A class to log to a rolling file.
 */
 
public class TangoRollingFileAppender extends RollingFileAppender implements TangoAppender
{
 /**
  * Construct a newly allocated TangoRollingFileAppender object.
  *
  * @param 	name	This appender name
  * @param 	file_name	The name of the file to log to
  * @param 	rtf	The rolling threshold in Kb
  */
	public TangoRollingFileAppender(String appender_name, 
                                  String file_name,
                                  long rtf) throws IOException
	{
    super(new XMLLayout(), file_name, true);
    setName(appender_name);
    setMaximumFileSize(rtf * 1024);
	}

 /** 
 * Returns true if the appender is (still) valid, false otherwise.
 */
  public boolean isValid() {
   return closed;
  }
  
}
