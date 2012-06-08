package fr.soleil.webserver.servlet;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.soleil.util.serialized.WebResponse;

/**
 * Servlet which process request from the Tango Client.
 * @author BARBA-ROSSA
 *
 */
public class ActionServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initialize the application context such as Server's logger 
	 */
	public void init() throws ServletException
	{
		super.init();
		
		// we call the init method
		WebDispatcher.getSingleton().initWebApplication();
	}
	
	/**
	 * Execute the client request
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processCall(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
			// the response content type is only java serialized object. 
			// We don't use the servlet's post method for other case.
			response.setContentType("application/x-java-serialized-object");
			// we complete the header section before calling the action in case of exception returned ...
			String sessionID = request.getSession().getId();
			response.addHeader("TANGO_SESSION_ID", sessionID);

			WebResponse webResponse = WebDispatcher.getSingleton().processAction(request, response);
			try
			{
				// The output stream get the webresponse and send it to the client
				// The webresponse object and ALL of it's CHILD must be seralizable
				if(webResponse != null)
				{
				//	System.out.print("Action :" + webResponse.getAction());
				//	System.out.print(", Method " + webResponse.getMethod());
				//	System.out.print(", ObjectID " + webResponse.getObjectID());
				}			
				//System.out.print("Serialization Begin");
				OutputStream outstr = response.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(outstr);
				// we put the response into the stream
				oos.writeObject(webResponse);
				// we send the response
				oos.flush();
				// and close the stream, it's very important.
				oos.close();
				//System.out.print("Serialization End");
			}
			catch(NotSerializableException ioe)
			{

				System.err.print("Erreur " + ioe.getMessage());
				if(webResponse != null)
				{
					System.err.print("Action :" + webResponse.getAction());
					System.err.print(", Method " + webResponse.getMethod());
					System.err.print(", ObjectID " + webResponse.getObjectID());
				}
				throw ioe;
			}
	}
	
	/**
	 * For the moment the Get method is only use the check if the servlet running
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.getWriter().println("SERVLET RUNNING : " + System.currentTimeMillis());
		response.getWriter().println("");
		
		Set keySet = ProcessActionCounter.getSingleton().getAccessCounter().keySet();
		Object[] keys = keySet.toArray();
		String key = null;
		int access = 0;
		int remove = 0;
		for(int i = 0;i < keys.length;i++)
		{
			key = (String)keys[i];
			access = ProcessActionCounter.getSingleton().getAccessCounter().get(key);
			if(ProcessActionCounter.getSingleton().getRemoveCounter().containsKey(key))
				remove = ProcessActionCounter.getSingleton().getRemoveCounter().get(key);
			else
				remove = 0;
			response.getWriter().println(key+":"+access+":"+remove);
		}
		long totalMemory = Runtime.getRuntime().totalMemory();
		long freeMemory = Runtime.getRuntime().freeMemory();
		long usedMemory = totalMemory-freeMemory;
		response.getWriter().println("Total Memory : "+ NumberFormat.getNumberInstance().format(totalMemory) );
		response.getWriter().println("Free Memory : "+ NumberFormat.getNumberInstance().format(freeMemory));
		response.getWriter().println("Used Memory : "+ NumberFormat.getNumberInstance().format(usedMemory));
		response.getWriter().println("");
		response.getWriter().println("Classes uses :");

		keySet = ProcessActionCounter.getSingleton().getClassUseCounter().keySet();
		keys = keySet.toArray();
		for(int i = 0;i < keys.length;i++)
		{
			key = (String)keys[i];
			if(ProcessActionCounter.getSingleton().getClassCounter().containsKey(key))
				access = ProcessActionCounter.getSingleton().getClassCounter().get(key);
			else
				access = 0;
			if(ProcessActionCounter.getSingleton().getClassUseCounter().containsKey(key))
				remove = ProcessActionCounter.getSingleton().getClassUseCounter().get(key);
			else
				remove = 0;
			response.getWriter().println(key+":"+access+":"+remove);
		}
		response.getWriter().println("");
		response.getWriter().println("Method uses :");
		
		keySet = ProcessActionCounter.getSingleton().getMethodCounter().keySet();
		keys = keySet.toArray();		
		for(int i = 0;i < keys.length;i++)
		{
			key = (String)keys[i];
			access = ProcessActionCounter.getSingleton().getMethodCounter().get(key);
			response.getWriter().println(key+":"+access);
		}		
	}
	
	/**
	 * We receive in post method only the client request
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		processCall(req, resp);
	}
	
}
