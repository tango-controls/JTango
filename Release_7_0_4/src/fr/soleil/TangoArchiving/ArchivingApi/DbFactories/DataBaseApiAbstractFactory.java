package fr.soleil.TangoArchiving.ArchivingApi.DbFactories;

import java.sql.Connection;
import java.sql.SQLException;

import fr.soleil.TangoArchiving.ArchivingApi.IDataBaseApiFactory;
import fr.soleil.TangoArchiving.ArchivingApi.DbConnection.IDataBaseConnection;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.IDataBaseApi;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.IDataBaseApiTools;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;

public abstract class DataBaseApiAbstractFactory  implements IDataBaseApiFactory
{
	protected SupportDataBaseApiAbstractFactory m_SupportDataBaseApiInstance;
	protected IDataBaseApi m_DataBaseApiInstance;
	
	//private IDataBaseConnection m_dbConnection;
	
	/**
	 * @param dbConn
	 * @roseuid 45C9E8F403E4
	 */
	protected DataBaseApiAbstractFactory(IDataBaseConnection dbConn) 
	{
		//m_dbConnection = dbConn;
	
		createSupportDataBaseApiAbstractFactory(dbConn);
		createIDataBaseApi();
		
	}
	
	protected abstract void createIDataBaseApi() ;
	protected abstract void createSupportDataBaseApiAbstractFactory(IDataBaseConnection dbConn); 

	public IDataBaseApi getDataBaseApi() 
	{
		return m_DataBaseApiInstance;
	}
	
	public IDataBaseApiUtilities getDataBaseUtilities()
	{
		return m_SupportDataBaseApiInstance;
	}
	/**
	 * @roseuid 45EC2ABF01B5
	 */
/*	public void sqlExceptionTreatment(SQLException e, String methodeName) throws  ArchivingException
	{
		m_SupportDataBaseApiInstance.sqlExceptionTreatment(e, methodeName);
	}

	public Connection getDbConnection() 
	{
		return m_dbConnection.getDbConnection();
	}
	
	public String getDbSchema()
	{
		return m_dbConnection.getDbSchema();
	}*/
}
