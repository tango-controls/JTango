//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbInterfaces\\IExtractorDataBaseApi.java

package fr.soleil.TangoArchiving.ArchivingApi.DbImpl;

import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;

public interface IExtractorDataBaseApi 
{
   
   /**
    * @return java.lang.String[]
    * @throws fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException
    * @roseuid 45E7113602C4
    */
   public String[] getCurrentArchivedAtt() throws ArchivingException;
   
   public String[] get_domains() throws ArchivingException;
}
