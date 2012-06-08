package fr.soleil.webserver.security;

/**
 * 
 * This interface provides the folowing methods :
 * - get the UID of a user
 * - get all the groups a user belongs to
 * - get all the projects a user belongs to
 * 
 * All this informations are searched in a LDAP directory
 * @author MOULHAUD
 */
public interface ISearchUserInfos
{	

	// need to add methods for manage user's access : application, beamline, ...
	
	/**
	 * Method that allows to control that the user belongs to the LDAP directory
	 * 
	 * @param strPassword
	 * @param connection
	 * @return boolean. True if ok. False otherwise
	 */	
	public boolean controlAccess(String strUserDN, String strPassword);
	
	/**
	 * Return true if authentication is need by application
	 * @param application
	 * @return boolean. True if authentication is need by the application. False otherwise
	 */
	public boolean isAuthenticationNeeded(String application);
	
	/**
	 * Return true if user could access to the application
	 * @return boolean
	 */
	public boolean checkAccess(String application, IUser iUser);
	
	/**
	 * Get User
	 * @return IUser
	 */
	public IUser getUser(String strUserDN, String strPassword) throws Exception;
}
