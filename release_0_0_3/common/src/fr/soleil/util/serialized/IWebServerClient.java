package fr.soleil.util.serialized;

import java.net.URL;

public interface IWebServerClient {

	/**
	 * This method send à query and return a WebTangoResponse object.
	 * 
	 * @param webRequest
	 * @return WebResponse
	 * @throws Exception
	 */
	public abstract WebResponse getObject(WebRequest webRequest) throws Exception;

	/**
	 * Get the recall manager
	 * @return {@link IRecallManager}
	 */
	public abstract IRecallManager getRecallManager();

	/**
	 * Set the recall manager
	 * @param manager {@link IRecallManager}
	 */
	public abstract void setRecallManager(IRecallManager manager);

	public abstract ILoginAction getM_iLoginAction();

	public abstract void setM_iLoginAction(ILoginAction loginAction);

	public abstract String getApplication();

	public abstract void setApplication(String application);

}