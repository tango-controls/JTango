//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbFactories\\db\\oracle\\OracleTdbDataBaseApiConcreteFactory.java

package fr.soleil.TangoArchiving.ArchivingApi.DbFactories.db.oracle;

import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.DataBaseApiAbstractFactory;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.db.oracle.OracleTdbDataBaseApiImpl;
import fr.soleil.TangoArchiving.ArchivingApi.DbConnection.IDataBaseConnection;

public class OracleTdbDataBaseApiConcreteFactory extends DataBaseApiAbstractFactory 
{

	/**
	 * @param dbConn
	 * @roseuid 45EC2B9E020D
	 */
	public OracleTdbDataBaseApiConcreteFactory(IDataBaseConnection dbConn) 
	{
		super(dbConn);
	}

	/**
	 * @roseuid 45ED83BF021F
	 */
	protected void createIDataBaseApi() 
	{
		m_DataBaseApiInstance = new OracleTdbDataBaseApiImpl(m_SupportDataBaseApiInstance, m_SupportDataBaseApiInstance);
	}

	/**
	 * @param dbConn
	 * @roseuid 45CC96A20071
	 */
	protected void createSupportDataBaseApiAbstractFactory(IDataBaseConnection dbConn) 
	{
		m_SupportDataBaseApiInstance = new OracleSupportDataBaseApiConcreteFactory(dbConn);
	}
}
