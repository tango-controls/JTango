package fr.soleil.util.serialized;

/**
 * Recall manager interface. Used by web server client
 * @author MOULHAUD
 *
 */
public interface IRecallManager
{

	
	/**
	 * Get the status
	 * @return boolean
	 */
	public boolean getStatus();
	/**
	 * Set the status
	 * @param status
	 */
	public void setStatus(boolean status);

	
	
	/**
	 * Get the number of calls
	 * @return int
	 */
	public int getCounterCalls();
	/**
	 * Set the number of calls
	 * @param counterCalls
	 */
	public void setCounterCalls(int counterCalls);
	/**
	 * Get the maximum of calls
	 * @return int
	 */
	public int getMaxCalls();

	/**
	 * Set the maximum of calls
	 * @param maxCalls
	 */
	public void setMaxCalls(int maxCalls);

	/**
	 * Increment the counter
	 *
	 */
	public void increment();
	
	/**
	 * Displays a message to invoke the recall or not
	 * @return boolean
	 */
	public boolean call();
	
	/**
	 * Displays a message to invoke the recall or not passing the exception that occured
	 * @param strMessage
	 * @return
	 */
	public boolean call(String strMessage);	
	/**
	 * Get the time between two calls in milliseconds
	 * @return int
	 */
	public int getTime();
	/**
	 * Set the time between two calls in milliseconds
	 */
	public void setTime(int time);
	/**
	 * Set a specific message
	 * @param strMessage String
	 */
	public void setMessage(String strMessage);
	public void setException(WebServerClientException e);
	
}
