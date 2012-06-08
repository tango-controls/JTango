package fr.soleil.util.serialized;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.CookieHandler;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import fr.soleil.util.UtilLogger;
import fr.soleil.util.exception.SoleilException;
import fr.soleil.util.exception.WebSecurityException;
/**
 * Provide service to send a WebRequest to a WebServer and return the WebResponse 
 * @author BARBA-ROSSA
 *
 */
public class WebServerClient implements IWebServerClient
{
	private String m_strApplication = null;
	
	// We store here the server sessionid
	private String m_strJsessionid = null; 

	// it's the WebServeur URL
	private URL m_Url = null;

//	 Recall manager used to launch a new web request
	private IRecallManager m_recallManager = null; 
	
// LoginAction used to authenticate a user not already authenticated
	private ILoginAction m_iLoginAction = null;
	
	/**
	 * Default constructor with the Web Server URL
	 * @param newUrl
	 */
	public WebServerClient(URL newUrl)
	{
		m_Url = newUrl;
	}	
	

	/**
	 * Send a web Request and returns a response if ok
	 * @param webRequest
	 * @param con
	 * @return {@link WebResponse}
	 * @throws Exception if an exception occurs
	 */
	private WebResponse sendWebRequest(WebRequest webRequest) throws Exception{
		

		WebResponse response = null;
		OutputStream outstream=null;
		URLConnection con = null;
		try{
			
			UtilLogger.logger.addInfoLog("WebServerClient.getObject begin :" + m_Url);
			UtilLogger.logger.addInfoLog("WebServerClient.getObject action :" + webRequest.getAction());
			// We open the connection to the server
			con = getServletConnection("ActionServlet");
			// We open � output stream to send the request
			outstream = con.getOutputStream();		
			
			ObjectOutputStream oos = null;

            try
            {
                oos = new ObjectOutputStream(outstream);
    //			 We serialize the object which contend the request parameter
                oos.writeObject(webRequest);
            }
            finally
            {
                if(oos != null)
                {
                    oos.flush();
                    oos.close();
                    outstream.close();
                }
            }
			// We get the response.
			InputStream instr = null;
            ObjectInputStream inputFromServlet = null;
            try
            {
                instr = con.getInputStream();

                // we get the session id
                String strCookieValue = con.getHeaderField("Set-cookie");
                String sessionID = con.getHeaderField("TANGO_SESSION_ID");
                // we get the jsessionid
                if(strCookieValue != null)
                {
                    // We get the the JSessionID to use that in the next request.
                    // the next request use the same httpsession
                    int iBegin = strCookieValue.indexOf("JSESSIONID=");
                     if(iBegin != -1)
                     {
                         int iEnd = strCookieValue.indexOf(";");
                         if(iEnd != -1)
                             m_strJsessionid = strCookieValue.substring(iBegin, iEnd);
                         else
                             m_strJsessionid = strCookieValue.substring(iBegin);

                     }
                     if(m_strJsessionid == null)
                         m_strJsessionid = sessionID.trim();
                }
                else
                {
                    if((m_strJsessionid == null) && (sessionID != null))
                        m_strJsessionid = sessionID.trim();
                }

    //			System.out.println("m_strJsessionid :" + m_strJsessionid);
    //			System.out.println("sessionID :'" + sessionID+"'");
                inputFromServlet = new ObjectInputStream(instr);
    //			 We get the serialized object
                response = (WebResponse) inputFromServlet.readObject();
            }
            finally
            {
                if(inputFromServlet != null)
                    inputFromServlet.close();
                if(instr != null)
                    instr.close();
            }
			UtilLogger.logger.addInfoLog("WebServerClient.getObject end :" + m_Url);
			
			// We test if the response contains an exception
			if(response==null)
				return null;
			if(response.getResult()==null)
				return null;
			
			// We check if the first element is an Exception or not.
			// If true, a problem occur in the web server
			if(response.getResult().length > 0)
			{
				Object objet = response.getResult()[0];
				if(objet instanceof Exception)
					throw (Exception)objet;
			}
		}
		catch (SoleilException e)
		{
			System.out.println("m_strJsessionid :" + m_strJsessionid);
			throw e;
		}
		catch(WebSecurityException wse)
		{
			System.out.println("m_strJsessionid :" + m_strJsessionid);
			if(m_iLoginAction!=null && WebSecurityException.USER_NOT_CONNECTED.equals(wse.getExceptionCode()))
			{
				
				// we call the loginview
				m_iLoginAction.authenticateUser();
				// we relaunch the request
				return sendWebRequest(webRequest);
			}
			else
			{
				throw wse;
			}
		}		
		catch (MalformedURLException ex)
		{
			UtilLogger.logger.addFATALLog(ex);
			
			throw new WebServerClientException(WebServerClientException.s_str_URL_MALFORMED, WebServerClientException.FATAL ,ex.getMessage());
		}
		catch (FileNotFoundException ex)
		{
			UtilLogger.logger.addFATALLog("Failed to open stream to URL: " + ex);
			throw new WebServerClientException(WebServerClientException.s_str_URL_NOT_FOUND, WebServerClientException.FATAL ,ex.getMessage());
		}
		catch (IOException ex)
		{
			UtilLogger.logger.addFATALLog("Error reading URL content: " + ex);
			throw new WebServerClientException(WebServerClientException.s_str_ERROR_READING_URL_CONTENT, WebServerClientException.FATAL ,ex.getMessage());
		}
		catch (ClassNotFoundException cnfe)
		{
			UtilLogger.logger.addFATALLog("CLasse not found excpetion" + cnfe);
			throw new WebServerClientException(WebServerClientException.s_str_CLASS_NOT_FOUND, WebServerClientException.FATAL ,cnfe.getMessage());
		}
		catch (Exception e)
		{
			UtilLogger.logger.addFATALLog("Exception" + e);
			throw e; //new WebServerClientException(WebServerClientException.s_str_OTHER_EXCEPTION, WebServerClientException.FATAL ,e.getMessage());
		}
		
		return response;
	}
	
	/* (non-Javadoc)
	 * @see fr.soleil.util.serialized.IWebServerClient#getObject(fr.soleil.util.serialized.WebRequest)
	 */
	public synchronized WebResponse getObject(WebRequest webRequest) throws Exception 
	{
		// TEMP find a better solution ------------------
		if(m_recallManager != null &&  !m_recallManager.getStatus())
			throw new Exception();
		// ----------------------------------------------
	
		return getResponse(webRequest);
		
	}
	
	/**
	 * Get a web response for a web request
	 * @param webRequest {@link WebRequest}
	 * @return {@link WebResponse}
	 * @throws Exception : Throws a webServerClientException if we can't establish a connection with the server. 
	 * Throws a soleil exception if we have a problem. 
	 * Throws other exception otherwise 
	 * 
	 */
	private WebResponse getResponse(WebRequest webRequest) throws Exception 
	{
		try
		{
			// we put the code application in the webRequest to identifie the sender
			if(webRequest != null)
				webRequest.setApplication(getApplication());
			
			WebResponse response = sendWebRequest(webRequest);
			if(m_recallManager != null)
					m_recallManager.setStatus(true);
					
			return response;
		}
		catch(WebServerClientException e){
			// we call the racel manager to get if we need to do an another call to the server
			if(m_recallManager != null && m_recallManager.call(e.getTechComment()) )
			{
				m_recallManager.setStatus(false);
				m_recallManager.setMessage(e.getTechComment());
				
				return getResponse(webRequest);
			}
			else{
				throw e;
			}
		}	
		catch (SoleilException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("WebServerClient.getResponse(WebRequest webRequest) :" + e.getMessage());
		
			throw e;
		}
	}
	
	/**
	 * Return an URLConnection to send a query to the server.
	 * 
	 * @param strServlet_name
	 * @return URLConnection
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private URLConnection getServletConnection(String strServlet_name)
			throws MalformedURLException, IOException
	{
	
		URL urlServlet = null;
		if(strServlet_name == null)
			urlServlet = m_Url;
		else
		{
			urlServlet = new URL(m_Url, strServlet_name);
		}
		URLConnection connection = urlServlet.openConnection();
		// we add a timeout
		connection.setConnectTimeout(180000);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		// don't use cache ... it's better to have the correct response
		connection.setUseCaches(false);
		
		
		// We must specified set content type to URLConnection
		connection.setRequestProperty("Content-Type",
				"application/x-java-serialized-object");

		// we add the jsessionid in the request to specify the httpsession used.
		if(m_strJsessionid != null)
		{
			// we add the server sessionid
			connection.setRequestProperty("Cookie",
					m_strJsessionid);
		}
		
		return connection;	
	
	}

	// The getter and setter of the url

	/* (non-Javadoc)
	 * @see fr.soleil.util.serialized.IWebServerClient#getUrl()
	 */
	public URL getUrl()
	{
		return m_Url;
	}

	/* (non-Javadoc)
	 * @see fr.soleil.util.serialized.IWebServerClient#setUrl(java.net.URL)
	 */
	public void setUrl(URL url)
	{
		this.m_Url = url;
	}


	/* (non-Javadoc)
	 * @see fr.soleil.util.serialized.IWebServerClient#getRecallManager()
	 */
	public IRecallManager getRecallManager()
	{
		return m_recallManager;
	}

	/* (non-Javadoc)
	 * @see fr.soleil.util.serialized.IWebServerClient#setRecallManager(fr.soleil.util.serialized.IRecallManager)
	 */
	public void setRecallManager(IRecallManager manager)
	{
		m_recallManager = manager;
	}

	/* (non-Javadoc)
	 * @see fr.soleil.util.serialized.IWebServerClient#getM_iLoginAction()
	 */
	public ILoginAction getM_iLoginAction() {
		return m_iLoginAction;
	}
	

	/* (non-Javadoc)
	 * @see fr.soleil.util.serialized.IWebServerClient#setM_iLoginAction(fr.soleil.util.serialized.ILoginAction)
	 */
	public void setM_iLoginAction(ILoginAction loginAction) {
		m_iLoginAction = loginAction;
	}


	/* (non-Javadoc)
	 * @see fr.soleil.util.serialized.IWebServerClient#getApplication()
	 */
	public String getApplication() {
		return m_strApplication;
	}


	/* (non-Javadoc)
	 * @see fr.soleil.util.serialized.IWebServerClient#setApplication(java.lang.String)
	 */
	public void setApplication(String application) {
		m_strApplication = application;
	}


	public String getM_strJsessionid() {
		return m_strJsessionid;
	}


	public void setM_strJsessionid(String jsessionid) {
		m_strJsessionid = jsessionid;
	}	
}
