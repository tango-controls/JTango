//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbInterfaces\\ITdbDataBaseApi.java

package fr.soleil.TangoArchiving.ArchivingApi.DbImpl;

import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;

public interface ITdbDataBaseApi extends IDataBaseApi 
{
   
   /**
    * @param remoteDir
    * @param fileName
    * @param tableName
    * @param writable
    * @throws fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException
    * @roseuid 45E710DB023D
    */
   public void exportToDB_Scalar(String remoteDir, String fileName, String tableName, int writable) throws ArchivingException;
}
