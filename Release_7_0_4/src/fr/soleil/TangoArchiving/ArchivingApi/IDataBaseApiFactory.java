package fr.soleil.TangoArchiving.ArchivingApi;

import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.IDataBaseApiUtilities;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.IDataBaseApi;

public interface IDataBaseApiFactory 
{
	public IDataBaseApi getDataBaseApi();
	
	public IDataBaseApiUtilities getDataBaseUtilities();
}
