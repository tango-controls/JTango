//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbFactories\\db\\oracle\\OracleSupportDataBaseApiConcreteFactory.java

package fr.soleil.TangoArchiving.ArchivingApi.DbFactories.db.oracle;

import fr.esrf.Tango.ErrSeverity;
import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.SupportDataBaseApiAbstractFactory;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.db.oracle.OracleRecorderDataBaseApiImpl;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.db.oracle.OracleExtractorDataBaseApiImpl;
import java.sql.SQLException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst;
import fr.soleil.TangoArchiving.ArchivingApi.DbConnection.IDataBaseConnection;

public class OracleSupportDataBaseApiConcreteFactory extends SupportDataBaseApiAbstractFactory 
{

	/**
	 * @param dataBaseApiTools
	 * @roseuid 45C9BD510276
	 */
	public OracleSupportDataBaseApiConcreteFactory(IDataBaseConnection dbConn) 
	{
		super(dbConn);
	}

	/**
	 * @roseuid 45ED86B6034F
	 */
	protected void createIExtractorDataBaseApi() 
	{
		m_IExtractorInstance = new OracleExtractorDataBaseApiImpl(this);
	}

	/**
	 * @roseuid 45ED86B60350
	 */
	protected void createIRecorderDataBaseApi() 
	{
		m_IRecorderInstance = new OracleRecorderDataBaseApiImpl(this);
	}

	/**
	 * @param e
	 * @param methodeName
	 * @throws fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException
	 * @roseuid 45ED86B6035F
	 */
	public void sqlExceptionTreatment(SQLException e, String methodeName) throws ArchivingException 
	{
		String message = "";
		if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE))
			message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
		else
			message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

		String reason = GlobalConst.QUERY_FAILURE;
		String desc = "Failed while executing " + methodeName + " method...";
		throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);

	}
}
