package fr.soleil.util.parameter;

/**
 * Parameter Loader
 * @author BARBA-ROSSA
 *
 */
public interface IParameterLoader
{

	/**
	 * This method load a ParameterFile and add it in the parameter map.
	 * Return : true if loading with success the file, false if an exception occured
	 * @param strFileId : the fileID, it can be use to identifie different parameters
	 * @param strParameterName : the name of the parameter file
	 * @param bReset : reset the old content for this fileID ?
	 * @return boolean
	 */
	public boolean readFile(String strFileId, String strParameterName, boolean bReset);	
	
}
