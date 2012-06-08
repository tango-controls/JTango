package fr.soleil.util.log;

import java.util.GregorianCalendar;

/**
 * The default appender use in soleil
 * We can create an other appender or link it to the LOG44J API
 * @author BARBA-ROSSA
 *
 */
public class SoleilAppender
{
	public final static int s_strFATAL = 0;
	public final static int s_strERROR = 1;
	public final static int s_strWARNING = 2;
	public final static int s_strINFO = 3;
	public final static int s_strDEBUG = 4;
	
	/**
	 * The Log Level indicate which level of log is need by the application.
	 * For example : 
	 * If the LogLevel equals 0 only Fatal message will be displayed
	 * If the LogLevel equals 4 all message will be displayed
	 */
	private int m_iLogLevel = 1;
	
	/**
	 * Add a fatal log 
	 * @param strError
	 * @param strModule
	 */
	public void addFATALLog(Object error, String strModule)
	{
		out(s_strFATAL, error, strModule);
	}

	/**
	 * Add a Error log 
	 * @param strError
	 * @param strModule
	 */
	public void addERRORLog(Object error, String strModule)
	{
		out(s_strERROR, error, strModule);
	}	
	
	/**
	 * Add a warning log 
	 * @param strWarning
	 * @param strModule
	 */
	public void addWarningLog(Object warning, String strModule)
	{
		out(s_strWARNING, warning, strModule);
	}

	/**
	 * Add a information log 
	 * @param strInfo
	 * @param strModule
	 */
	public void addInfoLog(Object info, String strModule)
	{
		out(s_strINFO, info, strModule);
	}
	
	/**
	 * Add a debug log 
	 * @param strDebug
	 * @param strModule
	 */
	public void addDebugLog(Object debug, String strModule)
	{
		out(s_strDEBUG, debug, strModule);
	}
	
	/**
	 * Write the log in the output
	 * @param ilevel
	 * @param error
	 * @param strModule
	 */
	private void out(int ilevel, Object error, String strModule){
		if(m_iLogLevel>=ilevel)
			System.out.println("[" + getLogLevelString(ilevel)+ "] " + getTime()+ " - " + strModule + " : "+ error);
	}

	/**
	 * Get the current time formatted in String in yyyy/MM/dd HH:MM:SS.mmm
	 * @return String
	 */
	private String getTime()
	{
		// we get the current time
		long time = System.currentTimeMillis();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(time);
		// we return the formatted date
		return calendar.get(GregorianCalendar.YEAR) + "/" + calendar.get(GregorianCalendar.MONTH)+1 +"/" + calendar.get(GregorianCalendar.DAY_OF_MONTH) + " " + calendar.get(GregorianCalendar.HOUR_OF_DAY) + ":" + calendar.get(GregorianCalendar.MINUTE) + ":" + calendar.get(GregorianCalendar.SECOND) + "." + calendar.get(GregorianCalendar.MILLISECOND);
	}
	
	/**
	 * Return a string representing the log level
	 * @return
	 */
	private String getLogLevelString(int iLevel)
	{
		// We test the log level end we return a String for the current log level
		switch(iLevel){
			case s_strFATAL : 
				return "FATAL";
			case s_strERROR :
				return "ERROR";
			case s_strWARNING :
				return "WARNING";
			case s_strINFO :
				return "INFO";
			case s_strDEBUG :
				return "DEBUG";
			default :
				return "UNKNOWN";
		}
	}
	
	// getter and setter for the Log level
	
	/**
	 * Get the log level
	 */
	public int getLogLevel()
	{
		return m_iLogLevel;
	}

	/**
	 * Modify the log level
	 * Use the LogLevel constante : s_strFATAL, s_strERROR, s_strWARNING, s_strINFO, s_strDEBUG
	 * @param logLevel
	 */
	public void setLogLevel(int logLevel)
	{
		m_iLogLevel = logLevel;
	} 

	
}
