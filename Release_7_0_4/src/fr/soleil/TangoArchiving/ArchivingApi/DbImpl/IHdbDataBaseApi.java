//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbInterfaces\\IHdbDataBaseApi.java

package fr.soleil.TangoArchiving.ArchivingApi.DbImpl;

import fr.soleil.TangoArchiving.ArchivingTools.Tools.ScalarEvent;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;

public interface IHdbDataBaseApi extends IDataBaseApi 
{
   
   /**
    * @param scalarEvent
    * @throws fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException
    * @roseuid 45E710AB0018
    */
   public void insert_ScalarData(ScalarEvent scalarEvent) throws ArchivingException;
}
