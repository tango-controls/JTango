package fr.soleil.util.database;

/**
 * This class return a Database implementation of the DataBaseConnection interface.
 * The first implementation of the manager can only return an <code>OracleConnection</code> as a <code>IDatabaseConnection</code>.
 * 
 * @author BARBA-ROSSA
 * @see OracleConnection
 * @see IDatabaseConnection
 */
public class ConnectionManager
{
	
	/** 
	 * 	Return an Oracle Connection this de default implementation.
	 * 	@deprecated
	 *  @param host String : the host name
	 *  @param user String : the user name
	 *  @param passwd String : the password
	 *  @param name String : the name of the database
	 *  @return void */
	
	
	public static IDatabaseConnection getDatabaseConnection(String host, String user, String passwd, String name){
		return new OracleConnection(host, user, passwd, name);		
	}

	/**
	 * Return a SQL Connection.
	 * As default, this method return an OracleConnection
	 * @return IDatabaseConnection
	 */
	public static IDatabaseConnection getDatabaseConnection(){
		return new OracleConnection();		
	}
	
}
