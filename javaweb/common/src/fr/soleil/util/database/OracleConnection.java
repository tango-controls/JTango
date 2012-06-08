package fr.soleil.util.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.soleil.util.UtilLogger;

/**
 * @author  BARBA-ROSSA
 */
public class OracleConnection implements IDatabaseConnection {

	private String m_strHost;
	private String m_strPort;
	private String m_strUser;
	private String m_strPasswd;
	private String m_strNameBDD;

	private Connection conn;
	private Statement m_stmt;
	
	static{
		try
		 {
			Class.forName(DBProperties.getSingleton().getDriver());
	     }
		  catch ( ClassNotFoundException E)
		  {
			  UtilLogger.logger.addFATALLog("Fatal Error : The driver could not be instantiated.");
			  E.printStackTrace();
		  }
		  catch ( NullPointerException E)
		  {
			  UtilLogger.logger.addFATALLog(E);
		  }
		  catch ( Exception E)
		  {
			  UtilLogger.logger.addFATALLog(E);
		  }		  
	}
	
	/**
	 * Constructor
	 * This constructor use the DBProperties file to get the database connection parameter
	 *
	 */
	public OracleConnection() {
        this.m_strHost = DBProperties.getSingleton().getHost();
        this.m_strPort = DBProperties.getSingleton().getPort();
        this.m_strUser = DBProperties.getSingleton().getUser();
        this.m_strPasswd = DBProperties.getSingleton().getPasswd();
        this.m_strNameBDD = DBProperties.getSingleton().getNameBDD();
    }	
	
	/******************************************************************************
	 * Constructor
	 * @param host String : the host name
	 * @param user String : the user name
	 * @param passwd String : the password
	 * @param name String : the database name
	 *****************************************************************************/
	public OracleConnection(String host, String user, String passwd, String name) {
        this.m_strHost = host;
        this.m_strPort = "1521";
        this.m_strUser = user;
        this.m_strPasswd = passwd;
        this.m_strNameBDD = name;
    }
	
	/*****************************************************************************************
	 * This method shows if the Oracle connection is Ok or not
	 * @param connection Connection : the connection to test
	 * @return boolean : true if the connection is OK
	 *****************************************************************************************/
	public static boolean isConnectionValid(Connection connection)
	{
		   if(connection==null)
		   {
		      return false;
		   }
		   ResultSet ping = null;
		   Statement stmt = null;
		   try
		   {
		      if(connection.isClosed()){return false;}
		      stmt = connection.createStatement();
		      ping = stmt.executeQuery("SELECT 1"); // à modifier pour effectuer le test.
		      return ping.next();
		   }
		   catch(SQLException sqle)
		   {
		      return false;
		   }
		   finally
		   {
		      if(ping!=null)
		      {
		    	  	try
		    	  	{
		    	  		ping.close();
		    	  	}
		    	  	catch(Exception e)
		    	  	{
		    	  		UtilLogger.logger.addFATALLog(e.getMessage());
		    	  	}
		      }
		      if(stmt!=null)
		      {
		    	  try
		    	  {   // We always close the statement
		    		  stmt.close();
		    	  }
		    	  catch(Exception e){
		    		  UtilLogger.logger.addFATALLog(e.getMessage());
		    	  }
		      }
		   }  
	}
	
	/* 
	 * We open a connection and a statement.
	 * @see fr.soleil.util.database.IDatabaseConnection#connect()
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	public void connect() throws InstantiationException, IllegalAccessException, SQLException
	{
		try
		 {
			  String url = "jdbc:oracle:thin:@" + m_strHost + ":"+m_strPort+":" + m_strNameBDD; //pilote Oracle
			  if((conn==null)||(conn.isClosed()))
				  conn = DriverManager.getConnection(url,m_strUser,m_strPasswd); // We create a connection if a connection is not available.
			  
			  m_stmt = conn.createStatement();
	      }
		  catch ( SQLException E)
		  {
			  UtilLogger.logger.addFATALLog("SQLException: " + E.getMessage());
			  UtilLogger.logger.addFATALLog("SQLState:     " + E.getSQLState());
			  UtilLogger.logger.addFATALLog("VendorError:  " + E.getErrorCode());
	          throw E;
		  }
		  catch ( NullPointerException E)
		  {
			  UtilLogger.logger.addFATALLog(E);
		  }
	}
	
	/* 
	 * We open a connection and a statement.
	 * @param strHost
	 * @param strUser
	 * @param strPasswd
	 * @param strNameBDD 
	 * @see fr.soleil.util.database.IDatabaseConnection#connect(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean connect (String strHost, String strUser, String strPasswd, String strNameBDD) throws InstantiationException, IllegalAccessException, SQLException
	{
		boolean bConnected = false;
		
		try
		 {
			  String url = "jdbc:oracle:thin:@" + strHost + ":"+m_strPort+":" + strNameBDD; //pilote Oracle
			  if((conn==null)||(conn.isClosed()))
				  conn = DriverManager.getConnection(url,strUser,strPasswd); // We create a connection if a connection is not available.
			  m_stmt = conn.createStatement();
			  bConnected = true;
	      }
		  catch ( SQLException E)
		  {
			  UtilLogger.logger.addFATALLog("SQLException: " + E.getMessage());
			  UtilLogger.logger.addFATALLog("SQLState:     " + E.getSQLState());
			  UtilLogger.logger.addFATALLog("VendorError:  " + E.getErrorCode());
		  }
		  catch ( NullPointerException E)
		  {
			  UtilLogger.logger.addFATALLog(E);
		  }
		
		return bConnected;
	}

	public void closeStatement()
	{
		try
		{
			if(m_stmt!=null)
				m_stmt.close();
		}
		catch (SQLException E)
		{
			UtilLogger.logger.addERRORLog("SQLException: " + E.getMessage());
			UtilLogger.logger.addERRORLog("SQLState:     " + E.getSQLState());
			UtilLogger.logger.addERRORLog("VendorError:  " + E.getErrorCode());
		}
	}
	
	/* 
	 * This method close the statement and the connection  
	 * @see fr.soleil.util.database.IDatabaseConnection#disconnect()
	 */
	public void disconnect(){
		try{
			if(m_stmt!=null)
				m_stmt.close();
			
			if(conn !=null)
				conn.close();
		}
		catch (SQLException E)
		{
			UtilLogger.logger.addERRORLog("SQLException: " + E.getMessage());
			UtilLogger.logger.addERRORLog("SQLState:     " + E.getSQLState());
			UtilLogger.logger.addERRORLog("VendorError:  " + E.getErrorCode());
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.soleil.util.database.IDatabaseConnection#executeQuery(java.lang.String)
	 */
	public  synchronized ResultSet executeQuery(String query) throws SQLException
	{
		ResultSet rs = null;
		try 
		{
			UtilLogger.logger.addInfoLog("Execute Query begin : " + query);
			rs = m_stmt.executeQuery(query);
			UtilLogger.logger.addInfoLog("Execute Query end : " + query);
		}
		catch (SQLException e)
		{
			UtilLogger.logger.addERRORLog("SQLException: " + e.getMessage());
			UtilLogger.logger.addERRORLog("SQLState:     " + e.getSQLState());
			UtilLogger.logger.addERRORLog("VendorError:  " + e.getErrorCode());
			throw e;
		}
		return rs;
	}
	
	/* (non-Javadoc)
	 * @see fr.soleil.util.database.IDatabaseConnection#getColumnsName(java.sql.ResultSet)
	 */
	
	public List getColumnsName(ResultSet rsResult) throws SQLException
	{
	   ResultSetMetaData metadata = rsResult.getMetaData();
	   List names = new ArrayList();
	   for(int i = 0; i < metadata.getColumnCount(); i++)
	   {
	      String strColumnName = metadata.getColumnName(i+1);
	      names.add(strColumnName);
	   }
	   return names;
	}

	
	
	
	
	// Getter and Setter
	
	public Connection getConn()
	{
		return conn;
	}

	public void setConn(Connection conn)
	{
		this.conn = conn;
	}

	public Statement getM_stmt()
	{
		return m_stmt;
	}

	public void setM_stmt(Statement m_stmt)
	{
		this.m_stmt = m_stmt;
	}

	protected void finalize() throws Throwable
	{
		super.finalize();
		// if a connection is open, we close it
		if((conn!=null)&&(!conn.isClosed()))
			conn.close();
	}
	
	
	
}
