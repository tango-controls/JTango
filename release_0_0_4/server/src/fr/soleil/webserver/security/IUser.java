package fr.soleil.webserver.security;

/**
 * Interface user used for authentication
 * @author BARBA-ROSSA
 *
 */
public interface IUser {

	/**
	 * Return the user DN 
	 * @return String
	 */
	public String getUserDN();
	
	/**
	 * Return the list of user's group
	 * @return String[]
	 */
	public String[] getUserGroup();
}
