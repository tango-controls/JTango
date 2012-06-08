package fr.soleil.util.exception;

/**
 * Standard exception use in application.
 * We can specified an error level : FATAL, ERROR, WARNING
 * And we can specified an Error code for the label manager.
 * @author BARBA-ROSSA
 *
 */
public class SoleilException extends Exception
{
	public final static int FATAL = 0;
	public final static int ERROR = 1;
	public final static int WARNING = 2;
	
	// the error code for the label manager
	private String m_strErrorCode = null;
	// The error level
	private int m_iLevel = -1;
	// A technical comment
	private String m_strTechComment = null;
	
	public SoleilException()
	{
	}
	
	/**
	 * Default constructor
	 * @param strErrorCode
	 * @param iLevel
	 */
	public SoleilException(String strErrorCode, int iLevel)
	{
		this.setErrorCode(strErrorCode);
		this.setLevel(iLevel);
	}	
	
	/**
	 * Default constructor
	 * @param strErrorCode
	 * @param iLevel
	 * @param strTechnicalComment
	 */
	public SoleilException(String strErrorCode, int iLevel, String strTechnicalComment)
	{
		this.setErrorCode(strErrorCode);
		this.setLevel(iLevel);
		this.setTechComment(strTechnicalComment);
	}	
	
	/**
	 * Return the Error Level
	 * @return int
	 */
	public int getLevel()
	{
		return m_iLevel;
	}
	
	/**
	 * Set the Error level
	 * @param level
	 */
	public void setLevel(int level)
	{
		m_iLevel = level;
	}
	
	/**
	 * Return error code for the label manager
	 * @return String
	 */
	public String getErrorCode()
	{
		return m_strErrorCode;
	}
	
	/**
	 * Set the Error Code
	 * @param errorCode
	 */
	public void setErrorCode(String errorCode)
	{
		m_strErrorCode = errorCode;
	}
	
	/**
	 * Standard message output :
	 * "Level : iLevel :  strERRORCODE"
	 */
	public String getMessage()
	{
		return "Level : " + Integer.toString(m_iLevel) + " : " + m_strErrorCode;
	}

	/**
	 * Get the technical comment
	 * @return String
	 */
	public String getTechComment()
	{
		return m_strTechComment;
	}

	/**
	 * Change the technical comment
	 * @param techComment
	 */
	public void setTechComment(String techComment)
	{
		m_strTechComment = techComment;
	}
	
}
