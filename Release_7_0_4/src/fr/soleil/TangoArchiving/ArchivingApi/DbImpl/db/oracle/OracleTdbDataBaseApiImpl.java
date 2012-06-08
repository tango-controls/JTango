//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbImpl\\db\\oracle\\OracleTdbDataBaseApiImpl.java

package fr.soleil.TangoArchiving.ArchivingApi.DbImpl.db.oracle;

import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.IGetInterfaceDataBaseApi;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.BasicTdbDataBaseApiImpl;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.IDataBaseApiTools;

public class OracleTdbDataBaseApiImpl extends BasicTdbDataBaseApiImpl 
{
   
   /**
    * @roseuid 45CC9EB9017A
    */
   public OracleTdbDataBaseApiImpl(IDataBaseApiTools dataBaseApiTools, IGetInterfaceDataBaseApi getInterface) 
   {
		super(dataBaseApiTools, getInterface);    
   }
   
   /**
    * @param remoteDir
    * @param fileName
    * @param tableName
    * @param writable
    * @roseuid 45C9AC560110
    */
   protected void exportToDB(String remoteDir, String fileName, String tableName, int writable) 
   {
	   System.out.println("SPJZ==> OracleTdbDataBaseApiImpl.exportToDB");
   } 
}

