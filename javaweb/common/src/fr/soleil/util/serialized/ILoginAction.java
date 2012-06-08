package fr.soleil.util.serialized;

/**
 * Interface define a method to launch an login process for enter user's login and password and authenticate the user.
 * @author BARBA-ROSSA
 *
 */
public interface ILoginAction {

	/**
	 * Launch the authentication process.
	 */
	public abstract void authenticateUser();

}