//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbImpl\\DataBaseApiImpl.java

package fr.soleil.TangoArchiving.ArchivingApi.DbImpl;

import fr.soleil.TangoArchiving.ArchivingApi.DbFactories.IGetInterfaceDataBaseApi;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.AttributeLightMode;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ScalarEvent;

public abstract class DataBaseApiImpl implements IDataBaseApi 
{
	private IDataBaseApiTools m_dataBaseApiTools;
	private IGetInterfaceDataBaseApi m_getInterface;

	/**
	 * @param dataBaseApiTools
	 * @param getInterface
	 * @roseuid 45CC9367036E
	 */
	public DataBaseApiImpl(IDataBaseApiTools dataBaseApiBasic, IGetInterfaceDataBaseApi getInterface) 
	{
		m_dataBaseApiTools = dataBaseApiBasic;
		m_getInterface = getInterface;
	}

	/**
	 * @param attributeLightMode
	 * @throws fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException
	 * @roseuid 45EC229602DE
	 */
	public void insertModeRecord(AttributeLightMode attributeLightMode) throws ArchivingException 
	{
		m_getInterface.getIRecorderDataBaseApi().insertModeRecord(attributeLightMode);
	}

	/**
	 * @param attribute_name
	 * @throws fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException
	 * @roseuid 45EC229602EE
	 */
	public void updateModeRecord(String attribute_name) throws ArchivingException 
	{
		m_getInterface.getIRecorderDataBaseApi().updateModeRecord(attribute_name);
	}

	public void insert_ScalarData(ScalarEvent scalarEvent) throws ArchivingException 
	{
		m_getInterface.getIRecorderDataBaseApi().insert_ScalarData(scalarEvent);
	}
	/**
	 * @return java.lang.String[]
	 * @throws fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException
	 * @roseuid 45EC22A300CB
	 */
	public String[] getCurrentArchivedAtt() throws ArchivingException 
	{
		return m_getInterface.getlExtractorDataBaseApi().getCurrentArchivedAtt();
	}
	
	public String[] get_domains() throws ArchivingException
	{
		return m_getInterface.getlExtractorDataBaseApi().get_domains();
	}
}
