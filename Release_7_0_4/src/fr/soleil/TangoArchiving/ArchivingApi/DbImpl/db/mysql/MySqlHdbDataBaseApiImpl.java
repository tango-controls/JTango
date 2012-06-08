//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbImpl\\db\\mysql\\MySqlHdbDataBaseApiImpl.java

package fr.soleil.TangoArchiving.ArchivingApi.DbImpl.db.mysql;

import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.IGetInterfaceDataBaseApi;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.BasicHdbDatabaseApiImpl;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.IDataBaseApiTools;

public class MySqlHdbDataBaseApiImpl extends BasicHdbDatabaseApiImpl 
{

	/**
	 * @roseuid 45CC9EAF0080
	 */
	public MySqlHdbDataBaseApiImpl(IDataBaseApiTools dataBaseApiTools, IGetInterfaceDataBaseApi getInterface) 
	{
		super(dataBaseApiTools, getInterface);
	}

	/**
	 * @roseuid 45C9AF1501DB
	 */
	protected void insertScalar() 
	{
		System.out.println("SPJZ==> MySqlHdbDataBaseApiImpl.insertScalar");
	}
}
//void MySqlHdbDataBaseApiImpl.updateModeRecord(java.lang.String){

//}
//void MySqlHdbDataBaseApiImpl.getCurrentArchivedAtt(){
//return null;
//}
//void MySqlHdbDataBaseApiImpl.insertModeRecord(fr.soleil.TangoArchiving.ArchivingTools.Tools.AttributeLightMode){

//}
