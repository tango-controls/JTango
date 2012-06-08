package fr.soleil.util.serialized;

/**
 * Web request object used by the client to get data from the Server
 * All object put in this class MUST BE Serializable
 * @author BARBA-ROSSA
 * @see  java.io.Serializable
 */
public class WebRequest implements java.io.Serializable
{
	
	/**
	 * Default serialisable UID
	 */
	private static final long serialVersionUID = 1L;
	
	// Not use for the moment
	private String m_strDeviceName = null;
	
	private String m_strApplication = null;

	// it's the certificat id for security check
	private String m_strCertificatID = null;
	// it's the workstation user id also for security check
	private String m_strWorkStationUserID = null;
	// indicate if we use a temp certificat. A temp certficat is only available for one session
	private boolean m_bTempCertificat = false;
	
	
	// it's the action name
	private String m_strAction = null;
	// it's the number of the package we want to get.  When query result is very big we could transfert it in multiple package 
	private int m_iPackageNumber = 0;
	// the list of argument use by the action
	private Object[] m_aArguments = null;
	
	private AStatParameter m_statParameter = null; // Stat parameter used to save datas of the webRequest
	
	/**
	 * Return the action name
	 * @return
	 */
	public String getAction()
	{
		return m_strAction;
	}
	
	/**
	 * change the action name
	 * @param strAction
	 */
	public void setAction(String strAction)
	{
		this.m_strAction = strAction;
	}
	
	/**
	 * Return the list of arguments in an Array
	 * @return Object[]
	 */
	public Object[] getArguments()
	{
		return m_aArguments;
	}
	
	/**
	 * Change the list of parameter 
	 * @param arguments
	 */
	public void setArguments(Object[] arguments)
	{
		this.m_aArguments = arguments;
	}
	
	/**
	 * Return the Device NAme, use for TANGO access
	 * @return String
	 */
	public String getDeviceName()
	{
		return m_strDeviceName;
	}
	
	/**
	 * Change the Device Name 
	 * @param strDeviceName
	 */
	public void setDeviceName(String strDeviceName)
	{
		this.m_strDeviceName = strDeviceName;
	}
	
	/**
	 * Return the package number to send to the client
	 * @return int
	 */
	public int getM_iPackageNumber()
	{
		return m_iPackageNumber;
	}
	
	/**
	 * Change the Package Number to get
	 * @param packageNumber
	 */
	public void setM_iPackageNumber(int packageNumber)
	{
		m_iPackageNumber = packageNumber;
	}
	
	/**
	 * Return the user's certificat ID
	 * @return String
	 */
	public String getCertificatID()
	{
		return m_strCertificatID;
	}
	
	/**
	 * Change the user's certificat ID
	 * @param certificatID
	 */
	public void setCertificatID(String certificatID)
	{
		m_strCertificatID = certificatID;
	}
	
	/**
	 * Return the workstation user id use for security check
	 * @return String
	 */
	public String getWorkStationUserID()
	{
		return m_strWorkStationUserID;
	}
	
	/**
	 * Change the workstation user id use for security check
	 * @param workStationUserID
	 */
	public void setWorkStationUserID(String workStationUserID)
	{
		m_strWorkStationUserID = workStationUserID;
	}
	
	/**
	 * Indicate if we use a temp certificat. A temp certficat is only available for one session
	 * @return boolean
	 */
	public boolean isTempCertificat()
	{
		return m_bTempCertificat;
	}
	
	/**
	 * Change the Temp Certificat, indicate if we use a temp certificat. A temp certficat is only available for one session
	 * @param tempCertificat
	 */
	public void setTempCertificat(boolean tempCertificat)
	{
		m_bTempCertificat = tempCertificat;
	}

	public String getApplication() {
		return m_strApplication;
	}

	public void setApplication(String application) {
		m_strApplication = application;
	}
	
	/**
	 * Get the stat parameter
	 * @return {@link AStatParameter}
	 */
	public AStatParameter getStatParameter()
	{
		return m_statParameter;
	}

	/**
	 * Set the stat parameter
	 * @param parameter {@link AStatParameter}
	 */
	public void setStatParameter(AStatParameter parameter)
	{
		m_statParameter = parameter;
	}	
}
