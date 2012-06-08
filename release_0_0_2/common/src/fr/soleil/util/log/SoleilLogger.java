package fr.soleil.util.log;

/**
 * Logger used by application. We can link it with Log4J in the futur. 
 * In this implementation we don't use LOG4J
 * @author BARBA-ROSSA
 *
 */
public class SoleilLogger
{
	// it's the logger modulename 
	private String m_strModuleName = null;
	// the log level for the Module
	private int m_iLogLevel = -1;
	// it's the Appender use in the logger, for the moment we only use a SoleilAppender, but we can change it
	private SoleilAppender m_appender = null;
	
	/**
	 * It's the default constructor
	 * @param strModuleName the module Name (util, experimentaframe ... )
	 * @param iLogLevel The module loglevel
	 */
	public SoleilLogger(String strModuleName, int iLogLevel)
	{
		m_appender = new SoleilAppender();
		m_appender.setLogLevel(iLogLevel);
		m_strModuleName = strModuleName;
		m_iLogLevel = iLogLevel;
	}
	
	/**
	 * Add a fatal log 
	 * @param strError
	 */	
	public void addFATALLog(Object error)
	{
		m_appender.addFATALLog(error, m_strModuleName);
	}

	/**
	 * Add a Error log 
	 * @param strError
	 */
	public void addERRORLog(Object error)
	{
		m_appender.addERRORLog(error, m_strModuleName);
	}	
	
	/**
	 * Add a warning log 
	 * @param strWarning
	 */
	public void addWarningLog(Object warning)
	{
		m_appender.addWarningLog(warning, m_strModuleName);
	}

	/**
	 * Add a information log 
	 * @param strInfo
	 */
	public void addInfoLog(Object info)
	{
		m_appender.addInfoLog(info, m_strModuleName);
	}
	
	/**
	 * Add a debug log 
	 * @param strDebug
	 */
	public void addDebugLog(Object debug)
	{
		m_appender.addDebugLog(debug, m_strModuleName);		
	}	
	
	/**
	 * Return the Log level for this module
	 * @return int
	 */
	public int getLogLevel()
	{
		return m_iLogLevel;
	}

	/**
	 * Modifye the log level for this module.
	 * @param logLevel
	 */
	public void setLogLevel(int logLevel)
	{
		// we change the loglevel for the module...
		m_iLogLevel = logLevel;
		// ..and for the appender 
		m_appender.setLogLevel(logLevel);
	}

	/**
	 * Return the module name
	 * @return String
	 */
	public String getModuleName()
	{
		return m_strModuleName;
	}

	/**
	 * Change the Module Name
	 * @param moduleName
	 */
	public void setModuleName(String moduleName)
	{
		m_strModuleName = moduleName;
	}
}
