package fr.soleil.TangoArchiving.ArchivingApi.DbConnection;

import java.sql.Connection;

public interface IDataBaseConnection 
{
	// Return the SQL object database connection 
	public Connection getDbConnection();
	
	// Return the name of the Schema
	public String getDbSchema();
	
	// Return true is the DataBase is in auto commit mode, false otherwise 
	public boolean isAutoCommit();
}
