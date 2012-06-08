package fr.soleil.TangoArchiving.ArchivingApi;

import fr.soleil.TangoArchiving.ArchivingApi.DbConnection.IDataBaseConnection;
import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.db.mysql.MySqlHdbDataBaseApiConcreteFactory;
import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.db.mysql.MySqlTdbDataBaseApiConcreteFactory;
import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.db.oracle.OracleHdbDataBaseApiConcreteFactory;
import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.db.oracle.OracleTdbDataBaseApiConcreteFactory;

public class DataBaseApiFactory
{
	public static final int HDB_ORACLE_IMPL_TYPE = 1;
	public static final int HDB_MYSQL_IMPL_TYPE = 2;
	public static final int TDB_ORACLE_IMPL_TYPE = 3;
	public static final int TDB_MYSQL_IMPL_TYPE = 4;

	public static IDataBaseApiFactory getImpl ( int typeOfImpl, IDataBaseConnection dbConn)
	{
		IDataBaseApiFactory newImpl = null;

		switch ( typeOfImpl )
		{
		case HDB_ORACLE_IMPL_TYPE:
			newImpl = new OracleHdbDataBaseApiConcreteFactory(dbConn);
			break;
		case HDB_MYSQL_IMPL_TYPE:
			newImpl = new MySqlHdbDataBaseApiConcreteFactory(dbConn);
			break;
		case TDB_MYSQL_IMPL_TYPE:
			newImpl = new MySqlTdbDataBaseApiConcreteFactory(dbConn);
			break;
		case TDB_ORACLE_IMPL_TYPE:
			newImpl = new OracleTdbDataBaseApiConcreteFactory(dbConn);
			break;		
		default:
			throw new IllegalStateException( "Expected either HDB_ORACLE_IMPL_TYPE (1) or HDB_MYSQL_IMPL_TYPE (2) or TDB_ORACLE_IMPL_TYPE (3) or TDB_MYSQL_IMPL_TYPE (4)." );
		}

		return newImpl;
	}

}
