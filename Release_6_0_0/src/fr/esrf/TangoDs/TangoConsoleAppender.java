//+============================================================================
//
// file :               TangoConsoleAppender.java
//
// description :        Java source code for the TangoConsoleAppender class. 
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

// log4j stuffs
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;

/**
 * A class to log to a console.
 */
 
public class TangoConsoleAppender extends ConsoleAppender implements TangoAppender
{
 /**
  * Construct a newly allocated TangoConsoleAppender object.
  *
  * @param 	appender_name	This appender name
  */
	public TangoConsoleAppender(String appender_name)
	{
    super(new PatternLayout("%d %p %c %m%n"));
    setName(appender_name);
	}
  
 /** 
  * Returns true if the appender is (still) valid, false otherwise.
  */
  public boolean isValid() {
    return true;
  }
  
}
