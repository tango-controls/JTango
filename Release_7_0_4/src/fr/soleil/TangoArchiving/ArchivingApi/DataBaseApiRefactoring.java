package fr.soleil.TangoArchiving.ArchivingApi;

import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;

public class DataBaseApiRefactoring 
{
	private IDataBaseApiFactory m_dataBaseApiInstance = null;
	private IDataBaseConnectionDefinition m_dbConnInstance = null;
	
	public DataBaseApiRefactoring() 
	{
		// Nothing To do
	}
	
	public IDataBaseApiFactory getDataBaseApiInstance()
	{
		return m_dataBaseApiInstance;
	}
	
	public void createHdbDataBaseInstance(String user , String password) throws ArchivingException
	{
		createDataBaseInstance(user,password,true);
	}
	public void createTdbDataBaseInstance(String user , String password) throws ArchivingException
	{
		createDataBaseInstance(user,password,false);
	}
	
	private void createDataBaseInstance(String user , String password, boolean historic) throws ArchivingException
	{
		if(historic)
			 m_dbConnInstance = DataBaseConnectionFactory.getImpl(DataBaseConnectionFactory.HDB_IMPL_TYPE,user,password);
		else m_dbConnInstance = DataBaseConnectionFactory.getImpl(DataBaseConnectionFactory.TDB_IMPL_TYPE,user,password);
		
		try
		{
			// Automatique Database connection
			m_dbConnInstance.autoConnection();
			switch (m_dbConnInstance.getDbType())
			{
			case IDataBaseConnectionDefinition.BD_MYSQL:
				m_dataBaseApiInstance = DataBaseApiFactory.getImpl(
						(historic ? DataBaseApiFactory.HDB_MYSQL_IMPL_TYPE :DataBaseApiFactory.TDB_MYSQL_IMPL_TYPE), m_dbConnInstance);
				break;
			case IDataBaseConnectionDefinition.BD_ORACLE:
				m_dataBaseApiInstance = DataBaseApiFactory.getImpl(
						(historic ? DataBaseApiFactory.HDB_ORACLE_IMPL_TYPE :DataBaseApiFactory.TDB_ORACLE_IMPL_TYPE), m_dbConnInstance);
			default:
				System.out.println("One problem has been encountered at the connection step");
			}
		}
		catch(ArchivingException e)
		{
			throw e;
		}
	}
	
	public boolean isAutoCommitMode ()
	{
		return m_dbConnInstance.isAutoCommit();
	}
	
	public boolean isDataBaseConnected()
	{
		return (m_dbConnInstance.getDbConnection() != null);
	}
	
	public void closeDatabase(boolean historic)
	{
		try
		{
			m_dataBaseApiInstance = null;
//				is_tdb_connected = false;
				
			if(m_dbConnInstance != null)
				m_dbConnInstance.closeConnection();				
		}
		catch ( ArchivingException e )
		{
			// Nothing to do
		}
	}
	/****************** TEST method ***************************************/	
	/* Méthode temporaire permettant la mise en place du refactoring de la DbApi */
/*	public void ArchivingConfigureWithoutArchiverListInitForTDB(String user , String password) throws ArchivingException
	{
		// A QUOI CELA SERT-IL ?
		m_tdbFacility = getFacility(false);
		
		String[] dom;
		
		IDataBaseConnectionDefinition dbConnTdb = DataBaseConnectionFactory.getImpl(DataBaseConnectionFactory.TDB_IMPL_TYPE,user,password);
		IDataBaseApiFactory m_tdbDataBaseApi = null;
		try
		{	
			// Connection to TDB
			// Automatique Database connection
			dbConnTdb.autoConnection();
			
//			 Database Factory creation
			switch (dbConnTdb.getDbType())
			{
			case IDataBaseConnectionDefinition.BD_MYSQL:
				m_tdbDataBaseApi = DataBaseApiFactory.getImpl(DataBaseApiFactory.TDB_MYSQL_IMPL_TYPE, dbConnTdb);
				break;
			case IDataBaseConnectionDefinition.BD_ORACLE:
				m_tdbDataBaseApi = DataBaseApiFactory.getImpl(DataBaseApiFactory.TDB_ORACLE_IMPL_TYPE, dbConnTdb);
				break;
			default:
				System.out.println("One problem has been encountered at the connection step");
			}
		}
		catch(ArchivingException e)
		{
			throw e;
		}
		if (m_tdbDataBaseApi != null)
		{
			// getDomain common to HDB and TDB and MySql/Oracle
			dom = m_tdbDataBaseApi.getIDataBaseApi().get_domains();
		
			for (int i = 0 ; i < dom.length;i++)
				System.out.println("Domain = " + dom[i]);
			
			// updateModeRecord diff between MySql and Oracle
			m_tdbDataBaseApi.getIDataBaseApi().updateModeRecord("");
		
			// export_ScalarData specific at TDB and diff between MySql and Oracle
			((ITdbDataBaseApi) m_tdbDataBaseApi.getIDataBaseApi()).exportToDB_Scalar("", "", "", 0)	;
		}
		dbConnTdb.closeConnection();
	}
*/	

/****************** TEST method ***************************************/	
	/* Méthode temporaire permettant la mise en place du refactoring de la DbApi */
/*public void ArchivingConfigureWithoutArchiverListInitForHDB(String user , String password) throws ArchivingException
	{
		m_hdbFacility = getFacility(true);

		String[] dom;
		
		IDataBaseConnectionDefinition dbConnHdb = DataBaseConnectionFactory.getImpl(DataBaseConnectionFactory.HDB_IMPL_TYPE,user,password);
		IDataBaseApiFactory m_hdbDataBaseApi = null;
		try
		{	
			// Connection to TDB
			// Automatique Database connection
			dbConnHdb.autoConnection();
			
//			 Database Factory creation
			switch (dbConnHdb.getDbType())
			{
			case IDataBaseConnectionDefinition.BD_MYSQL:
				m_hdbDataBaseApi = DataBaseApiFactory.getImpl(DataBaseApiFactory.HDB_MYSQL_IMPL_TYPE, dbConnHdb);
				break;
			case IDataBaseConnectionDefinition.BD_ORACLE:
				m_hdbDataBaseApi = DataBaseApiFactory.getImpl(DataBaseApiFactory.HDB_ORACLE_IMPL_TYPE, dbConnHdb);
				break;
			default:
				System.out.println("One problem has been encountered at the connection step");
			}
		}
		catch(ArchivingException e)
		{
			throw e;
		}
		if (m_hdbDataBaseApi != null)
		{
			// getDomain common to HDB and TDB and MySql/Oracle
			dom = m_hdbDataBaseApi.getIDataBaseApi().get_domains();
			for (int i = 0 ; i < dom.length;i++)
				System.out.println("Domain = " + dom[i]);
			
			// updateModeRecord diff between MySql and Oracle
			m_hdbDataBaseApi.getIDataBaseApi().updateModeRecord("");
		
			// insert_ScalarData specific at HDB and diff between MySql and Oracle
			((IHdbDataBaseApi) m_hdbDataBaseApi.getIDataBaseApi()).insert_ScalarData(null);
		}
		dbConnHdb.closeConnection();
	}
*/	

}

