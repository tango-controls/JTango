package fr.soleil.util.serialized;

/**
 * Response send by the server to the client
 * All object put in this class MUST BE Serializable
 * @author BARBA-ROSSA
 * @see  java.io.Serializable
 */
public class WebResponse implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String action = null;
	private String method = null;
	private String objectID = null;
	
	// contains all result in an array
	private Object[] m_aResult = null;

	/**
	 * Return the result list
	 * @return Object[]
	 */
	public Object[] getResult()
	{
		return m_aResult;
	}
	
	/**
	 * Set the Result array
	 * @param result
	 */
	public void setResult(Object[] result)
	{
		this.m_aResult = result;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectId) {
		this.objectID = objectId;
	}
}
