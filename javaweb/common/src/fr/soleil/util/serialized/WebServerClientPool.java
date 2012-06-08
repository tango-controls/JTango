package fr.soleil.util.serialized;

import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class WebServerClientPool implements IWebServerClient
{
	// Pool max item
	private static WebServerClientList  webServerClientList = null;
	// --------------------------------------------------------------------
	
	private boolean firstAccess = true;

	

	/**
	 * Default constructor with the Web Server URL
	 * @param newUrl
	 */
	public WebServerClientPool(String newUrl)
	{
		webServerClientList = new WebServerClientList();
		webServerClientList.setM_Url(newUrl);

	}

	/* (non-Javadoc)
	 * @see fr.soleil.util.serialized.IWebServerClient#getObject(fr.soleil.util.serialized.WebRequest)
	 */
	public WebResponse getObject(WebRequest webRequest) throws Exception 
	{
		// TEMP find a better solution ------------------
		if(webServerClientList.getM_recallManager() != null &&  !webServerClientList.getM_recallManager().getStatus())
			throw new Exception();
		// ----------------------------------------------
		WebServerClient currentWebServerClient = null;
		try
		{
			// we get an available webServerClient
			currentWebServerClient = getAvailableWebServerClient();
			
			// we get the response.
			WebResponse webResponse = currentWebServerClient.getObject(webRequest);
			return webResponse; 
		}
		finally
		{
			// 
			if(webServerClientList.getM_strJsessionid() == null)
			{
				if(currentWebServerClient.getM_strJsessionid() != null)
					webServerClientList.setM_strJsessionid(currentWebServerClient.getM_strJsessionid());
				else
					firstAccess = true; // we have an exception... 
			}

			//we release the connection
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
			if(webServerClientList.getM_strJsessionid() == null)
			{
				// temp
				System.out.println("Wait the end on the first connection");
				while(webServerClientList.getM_strJsessionid() == null && !firstAccess)
				{
					// we wait...			
				}
			}
		}
		
		return webServerClientList.get();
	}
	

	public void setApplication(String application) {
		webServerClientList.setM_strApplication(application);
		
	}

	public void setM_iLoginAction(ILoginAction loginAction) {
		webServerClientList.setM_iLoginAction(loginAction);
		
	}

	public void setRecallManager(IRecallManager manager) {
		webServerClientList.setM_recallManager(manager);
		
	}

	public String getApplication() {
		return webServerClientList.getM_strApplication();
	}

	public ILoginAction getM_iLoginAction() {
		return webServerClientList.getM_iLoginAction();
	}

	public IRecallManager getRecallManager() {
		return webServerClientList.getM_recallManager();
	}


	

}
