package fr.soleil.TangoArchiving.ArchivingApi;

import fr.soleil.TangoArchiving.ArchivingApi.DbConnection.HdbDataBaseConnection;
import fr.soleil.TangoArchiving.ArchivingApi.DbConnection.TdbDataBaseConnection;

public class DataBaseConnectionFactory
{
    public static final int HDB_IMPL_TYPE = 1;
    public static final int TDB_IMPL_TYPE = 2;
  
    public static IDataBaseConnectionDefinition getImpl ( int typeOfImpl, String muser, String mpassword )
    {
    	IDataBaseConnectionDefinition newImpl = null;
    	
        switch ( typeOfImpl )
        {
            case HDB_IMPL_TYPE:
                 newImpl = new HdbDataBaseConnection(muser, mpassword);
                 break;
            case TDB_IMPL_TYPE:
            	newImpl = new TdbDataBaseConnection(muser, mpassword);
                break;

            default:
                throw new IllegalStateException( "Expected either HDB_IMPL_TYPE (1) or TDB_IMPL_TYPE (2)." );
        }

        return newImpl;
    }

}
