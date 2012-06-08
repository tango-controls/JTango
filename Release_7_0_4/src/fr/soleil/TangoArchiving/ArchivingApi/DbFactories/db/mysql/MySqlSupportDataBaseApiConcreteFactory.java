//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbFactories\\db\\mysql\\MySqlSupportDataBaseApiConcreteFactory.java

package fr.soleil.TangoArchiving.ArchivingApi.DbFactories.db.mysql;

import java.sql.SQLException;

import fr.esrf.Tango.ErrSeverity;
import fr.soleil.TangoArchiving.ArchivingApi.DbConnection.IDataBaseConnection;
import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.SupportDataBaseApiAbstractFactory;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.db.mysql.MySqlExtractorDataBaseApiImpl;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.db.mysql.MySqlRecorderDataBaseApiImpl;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst;


public class MySqlSupportDataBaseApiConcreteFactory extends SupportDataBaseApiAbstractFactory 
{

	/**
	 * @param dataBaseApiTools
	 * @roseuid 45C9BD580351
	 */
	public MySqlSupportDataBaseApiConcreteFactory(IDataBaseConnection dbConn) 
	{
		super(dbConn);
	}

	/**
	 * @param dataBaseApiTools
	 * @roseuid 45C9CC5002D4
	 */
	protected void createIExtractorDataBaseApi() 
	{
		m_IExtractorInstance = new MySqlExtractorDataBaseApiImpl(this);
	}

	/**
	 * @param dataBaseApiTools
	 * @roseuid 45C9CC5002D5
	 */
	protected void createIRecorderDataBaseApi() 
	{
		m_IRecorderInstance = new MySqlRecorderDataBaseApiImpl(this);
	}

	/**
	 * @roseuid 45EC2858035F
	 */
	public void sqlExceptionTreatment(SQLException e, String methodeName) throws  ArchivingException
	{
		String message = "";
		
		if ( e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
			message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
		else
			message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

		String reason = GlobalConst.QUERY_FAILURE;
		String desc = "Failed while executing " + methodeName + " method...";
		throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
	}
}

