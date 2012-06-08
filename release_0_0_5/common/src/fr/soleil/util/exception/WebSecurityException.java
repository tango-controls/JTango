package fr.soleil.util.exception;

public class WebSecurityException extends Exception {
	public final static String INVALID_USER = "INVALID_USER";
	public final static String USER_NOT_CONNECTED = "USER_NOT_CONNECTED";
	private String m_strException = null;
	
	public WebSecurityException(String strException)
	{
		m_strException = strException;
	}

	public String getExceptionCode() {
		return m_strException;
	}

	public void setExceptionCode(String exception) {
		m_strException = exception;
	}
	
}
