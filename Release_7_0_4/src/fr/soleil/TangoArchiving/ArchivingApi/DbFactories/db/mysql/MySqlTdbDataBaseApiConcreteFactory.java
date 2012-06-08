//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbFactories\\db\\mysql\\MySqlTdbDataBaseApiConcreteFactory.java

package fr.soleil.TangoArchiving.ArchivingApi.DbFactories.db.mysql;

import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.DataBaseApiAbstractFactory;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.db.mysql.MySqlTdbDataBaseApiImpl;
import fr.soleil.TangoArchiving.ArchivingApi.DbConnection.IDataBaseConnection;

public class MySqlTdbDataBaseApiConcreteFactory extends DataBaseApiAbstractFactory 
{

	/**
	 * @param dbConn
	 * @roseuid 45EC2B89020C
	 */
	public MySqlTdbDataBaseApiConcreteFactory(IDataBaseConnection dbConn) 
	{
		super(dbConn);
	}

	/**
	 * @roseuid 45ED83C90061
	 */
	protected void createIDataBaseApi() 
	{
		m_DataBaseApiInstance = new MySqlTdbDataBaseApiImpl(m_SupportDataBaseApiInstance, m_SupportDataBaseApiInstance);
	}

	/**
	 * @param dbConn
	 * @roseuid 45CC96F400AF
	 */
	protected void createSupportDataBaseApiAbstractFactory(IDataBaseConnection dbConn) 
	{
		m_SupportDataBaseApiInstance = new MySqlSupportDataBaseApiConcreteFactory(dbConn);
	}
}
