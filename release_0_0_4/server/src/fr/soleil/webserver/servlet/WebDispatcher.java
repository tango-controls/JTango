package fr.soleil.webserver.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.soleil.tangoweb.data.TangoWebApplicationRules;
import fr.soleil.tangoweb.data.TangoWebBeanList;
import fr.soleil.tangoweb.data.TangoWebRewriteRules;
import fr.soleil.util.UtilLogger;
import fr.soleil.util.exception.WebSecurityException;
import fr.soleil.util.parameter.FileParameterLoaderImpl;
import fr.soleil.util.parameter.ParameterManager;
import fr.soleil.util.serialized.WebReflectRequest;
import fr.soleil.util.serialized.WebRequest;
import fr.soleil.util.serialized.WebResponse;
import fr.soleil.webserver.config.XMLConfigLoader;
import fr.soleil.webserver.security.ISearchUserInfos;
import fr.soleil.webserver.security.IUser;

public class WebDispatcher {
	
	private static WebDispatcher singleton = null; 
	
	private static ISearchUserInfos  securityManager = null;

	/**
	 * Return the singleton object
	 * @return WebDispatcher
	 */
	public static WebDispatcher getSingleton()
	{
		if(singleton == null)
			singleton = new WebDispatcher();
		
		return singleton;
	}
	
	/**
	 * Create the singleton
	 *
	 */
	public static void createSingleton()
	{
		singleton = new WebDispatcher();
	}
	
	/**
	 * Initialize the Web Application
	 * @throws ServletException
	 */
	public void initWebApplication() throws ServletException
	{
		try
		{
			// If we already hase a loader we don't replace it
			if(ParameterManager.getLoader()==null)
			{
				FileParameterLoaderImpl loader = new FileParameterLoaderImpl();
				ParameterManager.setLoader(loader);
			}
		} catch (Exception e)
		{
			throw new ServletException();
		}

        // we load the configuration
		try {
			TangoWebBeanList beanList = XMLConfigLoader.loadSerializationConfig("serializationConfig.xml");
			WebApplicationConfig.getSingleton().setBeanList(beanList);
			
			TangoWebRewriteRules ruleList = XMLConfigLoader.loadRulesConfig("serializationConfig.xml");
			WebApplicationConfig.getSingleton().setRules(ruleList);

			TangoWebApplicationRules appRuleList = XMLConfigLoader.loadApplicationRulesConfig("serializationConfig.xml");
			WebApplicationConfig.getSingleton().setAppRules(appRuleList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.setProperty("TANGO_HOST", System.getenv("TANGO_HOST"));
		System.out.println("Use TANGO_HOST at : " + System.getProperty("TANGO_HOST"));
		System.setProperty("TANGO_WEB_SERVER", System.getenv("TANGO_WEB_SERVER"));
		System.setProperty("STATIC_WEB_SERVER", System.getenv("STATIC_WEB_SERVER"));
	
		try
		{
			System.setProperty("TANGO_RCM_SERVER", System.getenv("TANGO_RCM_SERVER"));
			System.setProperty("STATIC_RCM_SERVER", System.getenv("STATIC_RCM_SERVER"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Use blaise as server : http://blaise/ATKWebServer");
			System.setProperty("TANGO_RCM_SERVER", "http://blaise/ATKWebServer");
			System.setProperty("STATIC_RCM_SERVER", "http://blaise/ATKWebServer");
		}
		// securityManager = new SearchUserInfosDefaultImpl();
		// need to implement a configuration parameter for create an instance of a selected security module
		
		// need to create a system to call a class for loading other parameters
		
		try
		{
			// We load the server properties
			FileParameterLoaderImpl fileLoader = new FileParameterLoaderImpl();
			fileLoader.readFile("", "server", false);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// we load the security module 
		LoadISearchUserInfos();
		
		try
		{
			// we call the init class
			ActionServletInit.init();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}		
	}
	
	/**
	 * Load the security module from properties files
	 *
	 */
	private void LoadISearchUserInfos()
	{
		try
		{
			boolean securityActivated = ParameterManager.getBooleanParameter("","SECURITY_ACTIVATED");
			if(securityActivated)
			{
				String securityClassName = ParameterManager.getStringParameter("","SECURITY_CLASS");
				
		        // we get the class definition object
		        Class c = Class.forName(securityClassName);
	
		        Constructor constructor = c.getConstructor(new Class[]{});
		        securityManager = (ISearchUserInfos) constructor.newInstance(new Object[]{});
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
	
	/**
	 * Process the request from TWIST
	 * @param request
	 * @param response
	 * @return WebResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public WebResponse processAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// we get the input stream
		InputStream in = request.getInputStream();
		// We get the object sending by the client in the input stream
		ObjectInputStream inputFromApplet = new ObjectInputStream(in);
		WebRequest webRequest = null;
		WebResponse webResponse = null;
		WebReflectRequest reflectRequest = null;
		String strAction = null;
		String strMethod = null;
		String objectID = null;
		try{
			// We read the object and we put it into a WebRequest instance
			webRequest = (WebRequest) inputFromApplet.readObject();

			//Test display application
			//System.out.println("************* Application : " + webRequest.getApplication()+" *******************");
			
			// We execute the action
			//
			ProcessAction processAction  = (ProcessAction)request.getSession().getAttribute("ProcessAction");
			if(processAction == null)
			{
				processAction = new ProcessAction(request.getSession().getId());
				request.getSession().setAttribute("ProcessAction", processAction);
			}
			
			Object[] arguments =webRequest.getArguments();
			reflectRequest = (WebReflectRequest)arguments[0];
			strAction = reflectRequest.getAction();
			strMethod = reflectRequest.getMethod();
			objectID = reflectRequest.getObjectID();
			
			if("Storage.remove".equalsIgnoreCase(strAction))
			{
				removeObjectFromStorage(request.getSession(), webRequest);
			} 
			else if("Security.authenticate".equalsIgnoreCase(strAction))
			{
				webResponse = authenticateUser(request.getSession(), webRequest);
			}
			else
			{
				ProcessActionCounter.getSingleton().addAccessCounter(webRequest.getApplication());
				
				if(ParameterManager.getBooleanParameter("","SECURITY_ACTIVATED"))
				{
					boolean authenticationNeeded = securityManager.isAuthenticationNeeded(webRequest.getApplication());
					if(authenticationNeeded)
					{
						// if the use is not authenticate we throws an exception
						boolean authenticated = false;
						if(request.getSession() != null && request.getSession().getAttribute("authenticated") != null)
							authenticated = (Boolean)request.getSession().getAttribute("authenticated");
						if(!authenticated)
						{
							throw new WebSecurityException(WebSecurityException.USER_NOT_CONNECTED);
						}
					}
				}				
				if(reflectRequest.getObjectID() == null)
				{
					ProcessActionCounter.getSingleton().addMethodCounter(strAction+"."+strMethod);
					ProcessActionCounter.getSingleton().addClassCounter(strAction);
					ProcessActionCounter.getSingleton().addClassUseCounter(strAction);
				}
				else
				{
					ProcessActionCounter.getSingleton().addMethodCounter(strAction+"."+strMethod);
					ProcessActionCounter.getSingleton().addClassUseCounter(strAction);			
				}
				
				// we call the action.
				// we execute the action
				webResponse =  processAction.processRequest(webRequest.getApplication(), reflectRequest);
			}					
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			
			UtilLogger.logger.addInfoLog("WebRequest Action : " + webRequest.getAction());
			// We call the process action object to get the result
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage()+ " : " + strAction+"."+strMethod);
			// if we have an exception we send it to the client
			webResponse = new WebResponse();
			webResponse.setResult(new Object[]{e});
			UtilLogger.logger.addERRORLog("ActionServlet :" + e.getMessage());
		}
		inputFromApplet.close();
		if(webResponse != null)
		{
			webResponse.setAction(strAction);
			webResponse.setMethod(strMethod);
			webResponse.setObjectID(objectID);
		}
		return webResponse;
	} 
	
	public static ISearchUserInfos getSecurityManager() {
		return securityManager;
	}

	public static void setSecurityManager(ISearchUserInfos securityManager) {
		WebDispatcher.securityManager = securityManager;
	}

	/**
	 * Authenticate the User
	 * @param session
	 * @param webRequest
	 * @return WebResponse
	 * @throws Exception
	 */
	public WebResponse authenticateUser(HttpSession session, WebRequest webRequest) throws Exception
	{
		Object[] arguments =webRequest.getArguments();
		WebReflectRequest reflectRequest = (WebReflectRequest)arguments[0];
		// here we call the services to authenticate the user
		String strLogin = (String)reflectRequest.getClassParam()[0];
		String strpassWord = (String)reflectRequest.getClassParam()[1];
		boolean authenticated = false;
			if(securityManager.controlAccess(strLogin, strpassWord))
		{
			authenticated = true;

			
			boolean authenticationNeeded = securityManager.isAuthenticationNeeded(webRequest.getApplication());
			IUser user = securityManager.getUser(strLogin, strpassWord);
			if(authenticationNeeded)
			{
				// if the user is not authorized to access to the application we throws an exception
				if(!securityManager.checkAccess(webRequest.getApplication(), user))
				{
					if(user != null)
						System.err.println("User not authorized : " + user.getUserDN());
					throw new WebSecurityException(WebSecurityException.INVALID_USER);
				}
			}
			session.setAttribute("user", user);
			session.setAttribute("authenticated", authenticated);
		}
		else
		{
			authenticated = false;
		}

		// We put the result of authentication
		WebResponse webResponse = new WebResponse();
		webResponse.setResult(new Object[]{authenticated});
		return webResponse;
	
	}
	
	/**
	 * Remove an object from the object's storage components 
	 * @param session
	 * @param webRequest
	 * @throws Exception
	 */
	public void removeObjectFromStorage(HttpSession session, WebRequest webRequest) throws Exception
	{
		Object[] arguments =webRequest.getArguments();
		WebReflectRequest reflectRequest = (WebReflectRequest)arguments[0];		
		ProcessActionCounter.getSingleton().addRemoveCounter(webRequest.getApplication());

		// we execute the action
		ProcessAction processAction = getProcessAction(session);
		processAction.removeObject(reflectRequest.getObjectID());		
	}
	
	/**
	 * Execute an action
	 * @param session
	 * @param webRequest
	 * @return WebResponse
	 * @throws Exception
	 */
	public WebResponse executeAction(HttpSession session, WebRequest webRequest) throws Exception
	{
		Object[] arguments =webRequest.getArguments();
		WebReflectRequest reflectRequest = (WebReflectRequest)arguments[0];
		
		
		ProcessActionCounter.getSingleton().addAccessCounter(webRequest.getApplication());

		if(ParameterManager.getBooleanParameter("","SECURITY_ACTIVATED"))
		{
			boolean authenticationNeeded = securityManager.isAuthenticationNeeded(webRequest.getApplication());
			if(authenticationNeeded)
			{
				// if the use is not authenticate we throws an exception
				boolean authenticated = false;
				if(session != null && session.getAttribute("authenticated") != null)
					authenticated = (Boolean)session.getAttribute("authenticated");
				if(!authenticated)
				{
					System.err.println("User not authenticated");
					throw new WebSecurityException(WebSecurityException.USER_NOT_CONNECTED);
				}
			}
		}
		
		String strAction = reflectRequest.getAction();
		String strMethod = reflectRequest.getMethod();
		
		if(reflectRequest.getObjectID() == null)
		{
			ProcessActionCounter.getSingleton().addMethodCounter(strAction+"."+strMethod);
			ProcessActionCounter.getSingleton().addClassCounter(strAction);
			ProcessActionCounter.getSingleton().addClassUseCounter(strAction);
		}
		else
		{
			ProcessActionCounter.getSingleton().addMethodCounter(strAction+"."+strMethod);
			ProcessActionCounter.getSingleton().addClassUseCounter(strAction);			
		}
		
		// we call the action.
		// we execute the action
		ProcessAction processAction = getProcessAction(session);
		WebResponse webResponse =  processAction.processRequest(webRequest.getApplication(), reflectRequest);		
		return webResponse;
	}
	
	/**
	 * Return the processaction classes store in the HttpSession or create it if necessary
	 * @param session
	 * @return ProcessAction
	 */
	public ProcessAction getProcessAction(HttpSession session)
	{
		ProcessAction processAction  = (ProcessAction)session.getAttribute("ProcessAction");
		if(processAction == null)
		{
			processAction = new ProcessAction(session.getId());
			session.setAttribute("ProcessAction", processAction);
		}
		return processAction;
		
	}
	
}
