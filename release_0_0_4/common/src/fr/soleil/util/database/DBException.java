package fr.soleil.util.database;

import java.sql.SQLException;

/**
 * SQL Exception throw by <code>IDatabaseConnection</code> 
 * @author BARBA-ROSSA
 * @see IDatabaseConnection
 */
public class DBException extends Exception
{
	public final static String s_strCONNECTION_FAILURE = "CON_FAIL";
	public final static String s_strRESULTSET_FAILURE = "RS_FAIL";
	
	private SQLException m_Sqle;
	private String m_strCodeMessage;
	
	/**
	 * The DBException
	 * @param sqle
	 * @param codeMessage
	 */
	public DBException(SQLException sqle, String codeMessage)
	{
		m_Sqle = sqle;
		m_strCodeMessage = codeMessage;
	}

	/**
	 * Get the SQLException
	 * @return SQLException
	 */
	public SQLException getSqle()
	{
		return m_Sqle;
	}

	/**
	 * Set a SQLException
	 * @param sqle
	 */
	public void setSqle(SQLException sqle)
	{
		m_Sqle = sqle;
	}

	/**
	 * Get the label code
	 * @return String
	 */
	public String getCodeMessage()
	{
		return m_strCodeMessage;
	}

	/**
	 * We can link a Code Message coresponding to a label
	 * @param codeMessage
	 */
	public void setCodeMessage(String codeMessage)
	{
		m_strCodeMessage = codeMessage;
	}

}
