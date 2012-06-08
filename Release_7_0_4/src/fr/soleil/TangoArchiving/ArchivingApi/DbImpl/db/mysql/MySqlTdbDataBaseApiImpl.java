//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbImpl\\db\\mysql\\MySqlTdbDataBaseApiImpl.java

package fr.soleil.TangoArchiving.ArchivingApi.DbImpl.db.mysql;

import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.IGetInterfaceDataBaseApi;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.BasicTdbDataBaseApiImpl;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.IDataBaseApiTools;

public class MySqlTdbDataBaseApiImpl extends BasicTdbDataBaseApiImpl 
{
   
   /**
    * @roseuid 45CC9EC701B9
    */
   public MySqlTdbDataBaseApiImpl(IDataBaseApiTools dataBaseApiTools, IGetInterfaceDataBaseApi getInterface) 
   {
	   super(dataBaseApiTools, getInterface);    
   }
   
    /**
    * @param remoteDir
    * @param fileName
    * @param tableName
    * @param writable
    * @roseuid 4600E148022D
    */
   protected void exportToDB(String remoteDir, String fileName, String tableName, int writable) 
   {
	   System.out.println("SPJZ==> MySqlTdbDataBaseApiImpl.exportToDB");
   }
}
