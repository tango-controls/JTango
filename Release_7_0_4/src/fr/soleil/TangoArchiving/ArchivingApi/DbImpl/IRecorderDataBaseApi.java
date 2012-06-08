//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbInterfaces\\IRecorderDataBaseApi.java

package fr.soleil.TangoArchiving.ArchivingApi.DbImpl;

import fr.soleil.TangoArchiving.ArchivingTools.Tools.AttributeLightMode;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ScalarEvent;

public interface IRecorderDataBaseApi 
{
   
   /**
    * @param attributeLightMode
    * @throws fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException
    * @roseuid 45E7114D005D
    */
   public void insertModeRecord(AttributeLightMode attributeLightMode) throws ArchivingException;
   
   /**
    * @param attribute_name
    * @throws fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException
    * @roseuid 45E7115702E4
    */
   public void updateModeRecord(String attribute_name) throws ArchivingException;
   
   public void insert_ScalarData(ScalarEvent scalarEvent) throws ArchivingException;
}
