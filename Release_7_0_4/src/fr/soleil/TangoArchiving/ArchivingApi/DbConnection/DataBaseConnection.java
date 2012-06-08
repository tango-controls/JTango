//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbConnection\\DataBaseConnection.java

package fr.soleil.TangoArchiving.ArchivingApi.DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import fr.esrf.Tango.ErrSeverity;
import fr.soleil.TangoArchiving.ArchivingApi.ConfigConst;
import fr.soleil.TangoArchiving.ArchivingApi.GetConf;
import fr.soleil.TangoArchiving.ArchivingApi.IDataBaseConnectionDefinition;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst;

public abstract class DataBaseConnection implements IDataBaseConnectionDefinition
{
	private String m_classDevice;
	private String m_dbUser;
	private String m_dbPasswd;

	private String m_dbHost = "";
	private String m_dbName = "";
	private String m_dbSchema = "";
	
	private int m_dbType = BD_UNKNOWN;
	
	private Connection m_dbConnection = null;
	/**
	 * @roseuid 45CC87EF02E2
	 */
	protected DataBaseConnection(String classDevice, String muser, String mpassword) 
	{
		m_classDevice = classDevice;

		if(muser.equals(""))
			m_dbUser = getDefaultDbUser();
		else
			m_dbUser = muser;

		if(mpassword.equals(""))
			m_dbPasswd = getDefaultDbPasswd();
		else
			m_dbPasswd = mpassword;

		retrieveConnectionParameters();
	}
	// Accessors
	/**
	 * @return int
	 * @roseuid 45CC88650013
	 */
	public int getDbType() 
	{
		return m_dbType;
	}
	
	public String getDbSchema()
	{
		return m_dbSchema;
	}
	
	public Connection getDbConnection()
	{
		return m_dbConnection;
	}
	// Abstract methods
	protected abstract String getDefaultDbHost();
	protected abstract String getDefaultDbName();
	protected abstract String getDefaultDbSchema();
	protected abstract String getDefaultDbUser();
	protected abstract String getDefaultDbPasswd();

	public boolean isAutoCommit()
	{
		if (m_dbType == BD_ORACLE)
			return ConfigConst.AUTO_COMMIT_ORACLE;
		if (m_dbType == BD_MYSQL)
			return  ConfigConst.AUTO_COMMIT_MYSQL;
		
		return false;
	}
	
	private void retrieveConnectionParameters() 
	{
		// Host
		try
		{
			m_dbHost = GetConf.getHost( m_classDevice );
		}
		catch ( ArchivingException e )
		{
			System.err.println(e.toString());
		}
		if ( m_dbHost.equals("") ) 
			m_dbHost =  getDefaultDbHost();

		// Name
		try
		{
			m_dbName = GetConf.getName( m_classDevice);
		}
		catch ( ArchivingException e )
		{
			System.err.println(e.toString());
		}
		if ( m_dbName.equals("") )
			m_dbName = getDefaultDbName() ;

		// Schema
		try
		{
			m_dbSchema = GetConf.getSchema( m_classDevice );
		}
		catch ( ArchivingException e )
		{
			System.err.println(e.toString());
		}
		if ( m_dbSchema.equals("") )
			m_dbSchema = getDefaultDbSchema () ;

	}
	/**
	 * @return fr.soleil.TangoArchiving.ArchivingApi.DbConnection.DataBaseConnection
	 * @roseuid 45CC880902F1
	 */
	public void autoConnection() throws ArchivingException
	{
		try
		{
			//  try oracle
			oracleConnection();
			System.out.println("DataBaseConnection.autoConnection : " + m_dbUser + "@" + m_dbHost);
			return;
		}
		catch ( ArchivingException e )
		{
			// Nothing to do
			System.out.println("DataBaseConnection.autoConnection raised an exception during connection to Oracle");
		}
		try
		{
			// try MySQL;
			mySqlConnection();
			System.out.println("DataBaseConnection.autoConnection : " + m_dbUser + "@" + m_dbHost);
			return;
		}
		catch ( ArchivingException e )
		{
			throw e;
		}
	}
	
	
	private void raiseArchivingExceptionForFailedOracleCnt(Exception e) throws ArchivingException
	{
		String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
		String reason = "Failed while executing DataBaseConnection.oracleConnection() method...";
		String desc = "The loggin parameters seem to be wrong... : " + "\r\n" +
		              "\t host      = " + m_dbHost + "\r\n" +
		              "\t db name   = " + m_dbName + "\r\n" +
		              "\t db schema = " + m_dbSchema + "\r\n" +
		              "\t user      = " + m_dbUser + "\r\n" +
		              "\t password  = " + m_dbPasswd;
		throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "" , e);
	}

	/**
	 * @return fr.soleil.TangoArchiving.ArchivingApi.DbConnection.DataBaseConnection
	 * @roseuid 45CC88480042
	 */
	public void oracleConnection() throws ArchivingException
	{
		// Load the driver
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");  //pilote Oracle
			String url = ConfigConst.DRIVER_ORACLE + ":@" + m_dbHost + ":" + ConfigConst.ORACLE_PORT + ":" + m_dbName; //pilote Oracle
			m_dbConnection = DriverManager.getConnection(url , m_dbUser , m_dbPasswd);
			setAutoCommit(ConfigConst.AUTO_COMMIT_ORACLE);
			
			//setDb_type(ConfigConst.BD_ORACLE);
			//setDriver(ConfigConst.DRIVER_ORACLE);
			
			// VOIR SI ALTER TOUJOURS NECESSAIRE
			alterSession();
			
			m_dbType = BD_ORACLE;
		}
		catch ( ClassNotFoundException e )
		{
			m_dbConnection = null;
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.DRIVER_MISSING;
			String reason = "Failed while executing DataBaseConnection.oracleConnection() method...";
			String desc = "No Oracle driver available..., please check !";
			throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "" , e);
		}
		catch ( SQLException e )
		{
			m_dbConnection = null;
			raiseArchivingExceptionForFailedOracleCnt(e);
		}
		catch(ArchivingException e)
		{
			m_dbConnection = null;
			throw e;
		}
		catch ( Exception e )
		{
			m_dbConnection = null;
			raiseArchivingExceptionForFailedOracleCnt(e);
		}
	}

	/**
	 * This method is used when connecting an Oracle database. It tunes the connection to the database.
	 *
	 * @throws ArchivingException
	 */
	private void alterSession() throws ArchivingException
	{
/*		Statement stmt;
		String sqlStr1 , sqlStr2 , sqlStr3;
		sqlStr1 = "alter session set NLS_NUMERIC_CHARACTERS = \". \"";
		sqlStr2 = "alter session set NLS_TIMESTAMP_FORMAT = 'DD-MM-YYYY HH24:MI:SS.FF'";
		sqlStr3 = "alter session set NLS_DATE_FORMAT = 'DD-MM-YYYY HH24:MI:SS'";
		try
		{
 //           if (canceled) return;
			stmt = dbconn.createStatement();
   //         lastStatement = stmt;
     //       if (canceled) return;
			stmt.executeQuery(sqlStr1);
       //     if (canceled) return;
			stmt.executeQuery(sqlStr2);
         //   if (canceled) return;
			stmt.executeQuery(sqlStr3);
           // if (canceled) return;
			close ( stmt );
		}
		catch ( SQLException e )
		{
			String message = "";
			if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
			else
				message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

			String reason = GlobalConst.STATEMENT_FAILURE;
			String desc = "Failed while executing DataBaseApi.alterSession() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}*/
	}
	
	private void raiseArchivingExceptionForFailedMySqlCnt(Exception e) throws ArchivingException
	{
		String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
		String reason = "Failed while executing DataBaseConnection.mySqlConnection() method...";
		String desc = ( e.getMessage().indexOf(GlobalConst.NO_HOST_EXCEPTION) != -1 ) ?
	              	  "The 'host' property (" + m_dbHost + ") might be wrong... please check it..." :
	            	  "The loggin parameters seem to be wrong... : " + "\r\n" + 
	            	  "\t host      = " + m_dbHost + "\r\n" +
		              "\t db name   = " + m_dbName + "\r\n" +
		              "\t db schema = " + m_dbSchema + "\r\n" +
		              "\t user      = " + m_dbUser + "\r\n" +
		              "\t password  = " + m_dbPasswd;
		throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "" , e);
	}
	/**
	 * @return fr.soleil.TangoArchiving.ArchivingApi.DbConnection.DataBaseConnection
	 * @roseuid 45CC8848015B
	 */
	public void  mySqlConnection() throws ArchivingException
	{
		// Load the driver
		try
		{
			Class.forName("org.gjt.mm.mysql.Driver"); // Definition du pilote MySQL
			String url = ConfigConst.DRIVER_MYSQL + "://" + m_dbHost + "/" + m_dbName; // Definition du pilote MySQL
			m_dbConnection = DriverManager.getConnection(url , m_dbUser , m_dbPasswd);
			setAutoCommit(ConfigConst.AUTO_COMMIT_MYSQL);
			m_dbType = BD_MYSQL;
			
			// setDb_type(ConfigConst.BD_MYSQL);
			// setDriver(ConfigConst.DRIVER_MYSQL);
		}
		catch ( ClassNotFoundException e )
		{
			m_dbConnection = null;
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.DRIVER_MISSING;
			String reason = "Failed while executing DataBaseConnection.mySqlConnection() method...";
			String desc = "No MySQL driver available..., please check !";
			throw new ArchivingException(message , reason , ErrSeverity.PANIC , desc , "" , e);
		}
		catch ( SQLException e )
		{
			m_dbConnection = null;
			raiseArchivingExceptionForFailedMySqlCnt(e);
		}
		catch(ArchivingException e)
		{
			m_dbConnection = null;
			throw e;
		}
		catch ( Exception e )
		{
			m_dbConnection = null;
			raiseArchivingExceptionForFailedMySqlCnt(e);
		}
	}

	private void setAutoCommit(boolean value) throws ArchivingException
	{
		// Set commit mode to manual
		try
		{
			m_dbConnection.setAutoCommit(value);
		}
		catch ( SQLException e )
		{
			String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : ";
			String reason = GlobalConst.STATEMENT_FAILURE;
			String desc = "Failed while executing DataBaseConnection.setAutoCommit() method...";
			throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
		}
	}

	/**
	 * Description : Closes the connection with the database
	 *
	 * @throws ArchivingException
	 */
	public void closeConnection() throws ArchivingException
	{
		if ( m_dbConnection != null )
        {
			try
			{
                if ( ! m_dbConnection.isClosed () )
                {
                	m_dbConnection.close();    
                }
			}
			catch ( SQLException e )
			{
				String message;
				if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
				else
					message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

				String reason = GlobalConst.STATEMENT_FAILURE;
				String desc = "Failed while executing DataBaseConnection.close() method...";
				throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
			}
        }
	}
}
