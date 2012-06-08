package fr.soleil.util.serialized;

import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class WebServerClientPool implements IWebServerClient
{
	// Pool max item
	private int maxWebClient = 20;
	private int webServerClientNumbers = 0; 
	private List<WebServerClient> webServerClientList = null;
	
	// --------------------------------------------------------------------
	
	private String m_strApplication = null;
	
	// We store here the server sessionid
	private String m_strJsessionid = null;
	private boolean firstAccess = true;
	
	// it's the WebServeur URL
	private String m_Url = null;	
	
//	 Recall manager used to launch a new web request
	private IRecallManager m_recallManager = null; 
	
// LoginAction used to authenticate a user not already authenticated
	private ILoginAction m_iLoginAction = null;

	/**
	 * Default constructor with the Web Server URL
	 * @param newUrl
	 */
	public WebServerClientPool(String newUrl)
	{
		
		m_Url = newUrl;
		webServerClientList = new ArrayList<WebServerClient>();
	}

	/* (non-Javadoc)
	 * @see fr.soleil.util.serialized.IWebServerClient#getObject(fr.soleil.util.serialized.WebRequest)
	 */
	public WebResponse getObject(WebRequest webRequest) throws Exception 
	{
		// TEMP find a better solution ------------------
		if(m_recallManager != null &&  !m_recallManager.getStatus())
			throw new Exception();
		// ----------------------------------------------
		WebServerClient currentWebServerClient = null;
		try
		{
			// we get an available webServerClient
			currentWebServerClient = getAvailableWebServerClient();
			
			// we get the response.
			return currentWebServerClient.getObject(webRequest);
		}
		finally
		{
			// 
			if(m_strJsessionid == null)
			{
				if(currentWebServerClient.getM_strJsessionid() != null)
					m_strJsessionid =  currentWebServerClient.getM_strJsessionid();
				else
					firstAccess = true; // we have an exception... 
				
			}

			if(currentWebServerClient!= null)
				webServerClientList.add(currentWebServerClient);
		}
		
	}

	
	public synchronized WebServerClient getAvailableWebServerClient() throws Exception
	{
		
		if(firstAccess)
		{
			firstAccess = false;
		}
		else
		{
			// temp
			while(m_strJsessionid == null && !firstAccess)
			{
				// we wait...			
			}
		}
		
		if(webServerClientList.size() == 0)
		{
			if(webServerClientNumbers < maxWebClient)
			{
				URL url = new URL(m_Url);
				webServerClientNumbers++;
				WebServerClient webServerClient = new WebServerClient(url);
				webServerClient.setRecallManager(m_recallManager);
				webServerClient.setApplication(m_strApplication);
				webServerClient.setM_iLoginAction(m_iLoginAction);
				webServerClient.setM_strJsessionid(m_strJsessionid);
				return webServerClient;
				
			}
			else
			{
				long beginTime = System.currentTimeMillis();
				while(webServerClientList.size() == 0)
				{
				}
				System.out.println(beginTime);
			}			
		}
		WebServerClient webServerClient = webServerClientList.get(0);
		webServerClientList.remove(0);
		return webServerClient;
	}
	
	public String getApplication() {
		return m_strApplication;
	}

	public ILoginAction getM_iLoginAction() {
		return m_iLoginAction;
	}

	public IRecallManager getRecallManager() {
		// TODO Auto-generated method stub
		return m_recallManager;
	}

	public void setApplication(String application) {
		m_strApplication = application;
		
	}

	public void setM_iLoginAction(ILoginAction loginAction) {
		m_iLoginAction = loginAction;
		
	}

	public void setRecallManager(IRecallManager manager) {
		m_recallManager = manager;
		
	}


	

}
