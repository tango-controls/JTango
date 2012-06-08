//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbImpl\\BasicRecorderDataBaseApiImpl.java

package fr.soleil.TangoArchiving.ArchivingApi.DbImpl;

import java.sql.Timestamp;

import fr.esrf.Tango.AttrWriteType;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.AttributeLightMode;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ScalarEvent;

public abstract class BasicRecorderDataBaseApiImpl implements IRecorderDataBaseApi 
{
   private IDataBaseApiTools m_dataBaseApiTools;
   
   /**
    * @roseuid 45CC9E810311
    */
   public BasicRecorderDataBaseApiImpl(IDataBaseApiTools dataBaseApiBasic) 
   {
	   m_dataBaseApiTools = dataBaseApiBasic;
   }
   
   // Abstract Method
   protected abstract void prepareQueryInsertModeRecord();
    
   public abstract void insert_ScalarData(ScalarEvent scalarEvent) throws ArchivingException;
 
   /**
    * @param attributeLightMode
    * @throws fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException
    * @roseuid 45EC1D2303B2
    */
   public void insertModeRecord(AttributeLightMode attributeLightMode) throws ArchivingException 
   {
    
   }
   
   
   /**
    * @param attribute_name
    * @throws fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException
    * @roseuid 45EC21B601DA
    */
   public void updateModeRecord(String attribute_name) throws ArchivingException 
   {
    
   }
}
//void BasicRecorderDataBaseApiImpl.insertModeRecord(){
//    
//   }
//void BasicRecorderDataBaseApiImpl.updateModeRecord(){
//    
//   }
