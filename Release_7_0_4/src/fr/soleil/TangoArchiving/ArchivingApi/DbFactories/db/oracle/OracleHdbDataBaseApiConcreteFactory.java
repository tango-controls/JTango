//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbFactories\\db\\oracle\\OracleHdbDataBaseApiConcreteFactory.java

package fr.soleil.TangoArchiving.ArchivingApi.DbFactories.db.oracle;

import fr.soleil.TangoArchiving.ArchivingApi.DbConnection.IDataBaseConnection;
import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.DataBaseApiAbstractFactory;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.db.oracle.OracleHdbDataBaseApiImpl;

public class OracleHdbDataBaseApiConcreteFactory extends DataBaseApiAbstractFactory 
{

	/**
	 * @param dbConn
	 * @roseuid 45C9E8820353
	 */
	public OracleHdbDataBaseApiConcreteFactory(IDataBaseConnection dbConn) 
	{
		super(dbConn);
	}

	/**
	 * @roseuid 45ED83B401FC
	 */
	protected void createIDataBaseApi() 
	{
		m_DataBaseApiInstance = new OracleHdbDataBaseApiImpl(m_SupportDataBaseApiInstance, m_SupportDataBaseApiInstance);
	}

	/**
	 * @param dbConn
	 * @roseuid 45ED842500F2
	 */
	protected void createSupportDataBaseApiAbstractFactory(IDataBaseConnection dbConn) 
	{
		m_SupportDataBaseApiInstance = new OracleSupportDataBaseApiConcreteFactory(dbConn);
	}
}
/**
 * void OracleHdbDataBaseApiConcreteFactory.createSupportDataBaseApiAbstractFactory(){
 *     
 *    }
 */
