package fr.soleil.TangoArchiving.ArchivingApi;

import fr.soleil.TangoArchiving.ArchivingApi.DbConnection.IDataBaseConnection;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;

public interface IDataBaseConnectionDefinition extends IDataBaseConnection
{

	// Parameter that represents the MySql database type
	public static final int BD_MYSQL = 0;
	
	// Parameter that represents the Oracle database type
	public static final int BD_ORACLE = 1;
	
	public static final int BD_UNKNOWN = -1;
	
	// Automatique connection : first to Oracle and if failed to MySql
	public void autoConnection() throws ArchivingException;
	
	// Gets the type of database being used
	public int getDbType();
	
	// Try to connect to an Oracle Database
	public void oracleConnection() throws ArchivingException;
	
	// Try to connect to a MySql Database
	public void mySqlConnection() throws ArchivingException;
	
	// Close the Database Connection
	public void closeConnection() throws ArchivingException;
	

	
}
