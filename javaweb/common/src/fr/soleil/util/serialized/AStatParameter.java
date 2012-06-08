package fr.soleil.util.serialized;

import java.util.Date;

import fr.soleil.util.exception.SoleilException;

/**
 * Abstract class for statistic parameters
 * @author MOULHAUD
 *
 */
public abstract class AStatParameter implements java.io.Serializable
{
	
	protected String m_strIpAddress=null; // The ip adress

	protected int m_iUID=-1; // The user id
	
	protected Date m_date=null; // The date
	
	protected String m_strType=null; // The type : Info, error, warning
	
	protected String m_strSessionId=null; // The session id
	
	protected SoleilException m_soleilException=null; // Soleil exception

	protected String m_strActionName=null; // The action name. Ex : Execute Query, Select experimental frame, etc.. 
	
	/**
	 * Get the action name
	 * @return String
	 */
	public String getActionName()
	{
		return m_strActionName;
	}

	/**
	 * Set the action name
	 * @param actionName String
	 */
	public void setActionName(String actionName)
	{
		m_strActionName = actionName;
	}

	
	/**
	 * Type INFO 
	 */
	public final static String TYPE_INFO="INFO";
	
	/**
	 * Type ERROR
	 */
	public final static String TYPE_ERROR="ERROR";
	
	/**
	 * Type WARNING
	 */
	public final static String TYPE_WARNING="WARNING";
	
	/**
	 * Get the user id
	 * @return int
	 */
	public int getUID()
	{
		return m_iUID;
	}

	/**
	 * Set the user id
	 * @param m_iuid int
	 */
	public void setUID(int m_iuid)
	{
		m_iUID = m_iuid;
	}

	/**
	 * Get the ip address
	 * @return String
	 */
	public String getIpAdress()
	{
		return m_strIpAddress;
	}

	/**
	 * Set the ip address
	 * @param ipAdress String
	 */
	public void setIpAdress(String ipAdress)
	{
		m_strIpAddress = ipAdress;
	}
	
	/**
	 * 
	 * @return {@link Date}
	 */
	public Date getDate()
	{
		return m_date;
	}
	
	/**
	 * Set the date
	 * @param date
	 */
	public void setDate(Date date){
		this.m_date=date;
	}
	
	/**
	 * Get the type
	 * @return String
	 */
	public String getType(){
		
		return m_strType;
	}
	
	/**
	 * Set the type
	 * @param strType
	 */
	public void setType(String strType){
		this.m_strType=strType;
		
	}
	
	/**
	 * Set the soleil exception
	 * @param exception {@link SoleilException}
	 */
	public void setSoleilException(SoleilException exception)
	{
		m_soleilException=exception;	
	}
 
	/**
	 * Get the soleil exception
	 * @return {@link SoleilException}
	 */
	public SoleilException getSoleilException(){
		return m_soleilException;
	}

	/**
	 * Get the session identifier
	 * @return String
	 */
	public String getSessionId()
	{
		return m_strSessionId;
	}

	/**
	 * Set the session identifier
	 * @param sessionId String
	 */
	public void setSessionId(String sessionId)
	{
		m_strSessionId = sessionId;
	}

}
