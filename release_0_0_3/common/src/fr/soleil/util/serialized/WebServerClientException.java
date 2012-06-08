package fr.soleil.util.serialized;

import fr.soleil.util.exception.SoleilException;

public class WebServerClientException extends SoleilException
{
	/**
	 * Problem during deploy the application parameter. The Server URL is incorrect. Contact Your IT department.
	 */
	public final static String s_str_URL_MALFORMED = "URL_MALFORMED";
	
	/**
	 * URL Not found. To resolve the problem : check if your internet connection work and if the server is running. 
	 */
	public final static String s_str_URL_NOT_FOUND = "URL_NOT_FOUND";
	
	/**
	 * Error during reading the URL content.
	 */
	public final static String s_str_ERROR_READING_URL_CONTENT = "ERROR_READING_URL_CONTENT";
	
	/**
	 * Class not found exception, we cannot found a class transmitted by the server
	 * The server version could by deploy during client execution
	 */
	public final static String s_str_CLASS_NOT_FOUND = "CLASS_NOT_FOUND";

	/**
	 * UnknownException : other Exception not already identified  
	 */
	public final static String s_str_OTHER_EXCEPTION = "OTHER_EXCEPTION";
	
	/**
	 * Default constructor
	 * @param strErrorCode
	 * @param iLevel
	 */
	public WebServerClientException(String strErrorCode, int iLevel)
	{
		super(strErrorCode,iLevel);
	}	
	
	/**
	 * Default constructor
	 * @param strErrorCode
	 * @param iLevel
	 * @param strTechnicalComment
	 */
	public WebServerClientException(String strErrorCode, int iLevel, String strTechnicalComment)
	{
		super(strErrorCode, iLevel, strTechnicalComment);
	}	
	
}
