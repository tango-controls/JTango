//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbInterfaces\\IGetInterfaceDataBaseApi.java

package fr.soleil.TangoArchiving.ArchivingApi.DbFactories;

import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.IExtractorDataBaseApi;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.IRecorderDataBaseApi;


public interface IGetInterfaceDataBaseApi 
{
   
   /**
    * @return fr.soleil.TangoArchiving.ArchivingApi.DbInterfaces.IRecorderDataBaseApi
    * @roseuid 45C9CBCC0345
    */
   public IRecorderDataBaseApi getIRecorderDataBaseApi();
   
   /**
    * @return fr.soleil.TangoArchiving.ArchivingApi.DbInterfaces.IExtractorDataBaseApi
    * @roseuid 45C9CBCC0335
    */
   public IExtractorDataBaseApi getlExtractorDataBaseApi();
}
