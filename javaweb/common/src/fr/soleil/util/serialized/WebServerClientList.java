package fr.soleil.util.serialized;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebServerClientList {
	private int maxWebClient = 5; //20;
	private int webServerClientNumbers = 0; 
	private List<WebServerClient> webServerClientList = null;
	
	private String m_strApplication = null;
	
	// We store here the server sessionid
	private String m_strJsessionid = null;
	
	// it's the WebServeur URL
	private String m_Url = null;	
	
//	 Recall manager used to launch a new web request
	private IRecallManager m_recallManager = null; 
	
// LoginAction used to authenticate a user not already authenticated
	private ILoginAction m_iLoginAction = null;	
	
	public WebServerClientList()
	{
		webServerClientList = new ArrayList<WebServerClient>();
	}
	
	public synchronized void add(WebServerClient webServerClient)
	{
		webServerClientList.add(webServerClient);
		notifyAll();
	}
	
	public synchronized WebServerClient get() throws Exception
	{
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
				System.out.println("Wait for a connection");
				// we wait a connection ...
				wait();
			}			
		}
		WebServerClient webServerClient = webServerClientList.get(0);
		webServerClientList.remove(0);
		return webServerClient;
	}

	public int getMaxWebClient() {
		return maxWebClient;
	}

	public void setMaxWebClient(int maxWebClient) {
		this.maxWebClient = maxWebClient;
	}

	public int getWebServerClientNumbers() {
		return webServerClientNumbers;
	}

	public void setWebServerClientNumbers(int webServerClientNumbers) {
		this.webServerClientNumbers = webServerClientNumbers;
	}

	public List<WebServerClient> getWebServerClientList() {
		return webServerClientList;
	}

	public void setWebServerClientList(List<WebServerClient> webServerClientList) {
		this.webServerClientList = webServerClientList;
	}

	public String getM_strApplication() {
		return m_strApplication;
	}

	public void setM_strApplication(String application) {
		m_strApplication = application;
	}

	public String getM_strJsessionid() {
		return m_strJsessionid;
	}

	public void setM_strJsessionid(String jsessionid) {
		m_strJsessionid = jsessionid;
	}

	public String getM_Url() {
		return m_Url;
	}

	public void setM_Url(String url) {
		m_Url = url;
	}

	public IRecallManager getM_recallManager() {
		return m_recallManager;
	}

	public void setM_recallManager(IRecallManager manager) {
		m_recallManager = manager;
	}

	public ILoginAction getM_iLoginAction() {
		return m_iLoginAction;
	}

	public void setM_iLoginAction(ILoginAction loginAction) {
		m_iLoginAction = loginAction;
	}
	
}
