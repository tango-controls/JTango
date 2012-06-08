package fr.soleil.webserver.servlet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import fr.esrf.Tango.AttributeValue;
import fr.esrf.TangoApi.ConnectionFailed;
import fr.soleil.tangoweb.data.TangoWebBean;
import fr.soleil.tangoweb.data.TangoWebBeanList;
import fr.soleil.util.serialized.WebReflectRequest;
import fr.soleil.util.serialized.WebResponse;
import fr.soleil.util.serialized.serializer.IWebObjectStorage;
import fr.soleil.util.serialized.serializer.OptimizedWebObjectStorage;
import fr.soleil.util.serialized.serializer.WebObjectStorage;
import fr.soleil.util.serialized.serializer.WebSerializerObject;
import fr.soleil.webserver.processaction.SerializeObject;

/**
 * This class process launch the action contend in the action attribute of WebRequest class
 *  
 * @author BARBA-ROSSA
 *
 */
public class ProcessAction
{
	private static Map<String, Integer> tempCountMap = null;
	private WebObjectStorage m_webStorage = null;
	// create a map for specific storage which must be optimized such as DeviceAttribute
	private HashMap<String, IWebObjectStorage> m_specificStorage = null;
	private String m_strSessionID = null;
	
	public ProcessAction(String sessionID)
	{
		m_strSessionID = sessionID;
		m_webStorage = new WebObjectStorage(sessionID);
		m_specificStorage = new HashMap<String, IWebObjectStorage>();
	}
	
	public void removeObject(String objectID)
	{
		// we remove the object from the storage
		m_webStorage.removeObject(objectID);
	}

	public WebResponse processRequest(String application, WebReflectRequest reflectRequest)
	{
		// we call the action.
		if(reflectRequest.getObjectID() == null)
		{
			return process(application, reflectRequest.getAction(), reflectRequest.getClassParam(), reflectRequest.getMethod(), reflectRequest.getMethodParam(), reflectRequest.getMethodParamClass());
		}
		else
		{
			return process(application, reflectRequest.getObjectID(), reflectRequest.getAction(), reflectRequest.getMethod(), reflectRequest.getMethodParam(), reflectRequest.getMethodParamClass());
		}		
	} 
	
	/**
	 * call and execute the process corresponding to the action parameter 
	 * @param webRequest
	 * @return WebResponse
	 */
	public WebResponse process(String application, String strClass, Object[] strClassParam, String strMethod, Object[] aParameter, Class[] aParameterClass)
	{
		WebResponse webResponse = null;
		try
		{
			// we call the process action method
			return processAction(application, strClass, strClassParam, strMethod, aParameter, aParameterClass);
		}
		catch(ConnectionFailed ce)
		{
			// for the moment we just use a standard Exception : TODO create TANGOWEB Exceptions
			Exception e = new Exception(ce.getMessage());
			ce.printStackTrace();
			webResponse = new WebResponse();
			webResponse.setResult(new Object[]{ce});
		}
		catch(Exception e)
		{
			e.printStackTrace();
			webResponse = new WebResponse();
			webResponse.setResult(new Object[]{e});
		}
		return webResponse;
	}
	
	/**
	 * Execute the action
	 * @param objectID : objectID of the instanciate class
	 * @param actionName : it's the name of the class to call
	 * @param strMethod : it's the name of the method to execute
	 * @param aParameter
	 * @param aParameterClass : it's important if a parameter is equal to null we can't find the class type without this array 
	 * @return WebResponse
	 * @throws Exception
	 */
	public WebResponse process(String application, String objectID, String actionName, String strMethod, Object[] aParameter, Class[] aParameterClass)
	{
		WebResponse webResponse = null;
		try
		{
			if(actionName == null)
			{
				System.out.println(strMethod + " : " );
				System.out.printf("", aParameter);
			}
			// we get the storage coresponding to the type of action
			IWebObjectStorage storage = getWebObjectStorage(application, actionName);
			// We get the object from the storage
			Object object = storage.getObject(objectID);
			
			// we call the process action method
			return processMethod(application, object, strMethod, aParameter, true, aParameterClass);
		}
		catch(ConnectionFailed ce)
		{
			// for the moment we just use a standard Exception : TODO create TANGOWEB Exceptions
			Exception e = new Exception(ce.getMessage());
			ce.printStackTrace();
			webResponse = new WebResponse();
			webResponse.setResult(new Object[]{ce});
		}
		catch(Exception e)
		{
			e.printStackTrace();
			webResponse = new WebResponse();
			webResponse.setResult(new Object[]{e});
		}
		return webResponse;		
	}
	
	/**
	 * We execute the action in the corresponding to the webRequest.getAction()
	 * @param webRequest
	 * @return WebResponse
	 * @throws Exception
	 */
	private WebResponse processAction(String application, String className, Object[] aClassParam, String methodName, Object[] aParameter, Class[] aParameterClass) throws Exception
	{
	    try {
	    	//System.out.println("processAction : " + className);
	    	// we create an instance of the action class
	    	Object object = createClass(application, className,  aClassParam);

	        // we execute the method
	        return processMethod(application, object, methodName, aParameter, false, aParameterClass);
	      } catch (ClassNotFoundException e) {
	    	System.out.println("processAction : " + className);
	        System.out.println("Class.forName(  ) can't find the class");
	        throw e;
	      } catch (NoSuchMethodException e2) {
	    	System.out.println("processAction : " + className);
	    	System.out.println("method doesn't exist");
	        System.out.println("className :" + className + ": methodName :" + methodName);
	        throw e2;
	      } catch (IllegalAccessException e3) {
	    	System.out.println("processAction : " + className);
	        System.out.println("no permission to invoke that method");
	        throw e3;
	      } catch (InvocationTargetException e) {
	    	System.out.println("processAction : " + className);
	        System.out.println("an exception ocurred while invoking that method");
	        System.out.println("Method threw an: " + e.getTargetException());
	        
	        // if we have a instanceof Exception we throw it else we throw the invocation exception
	        if(e.getTargetException() instanceof Exception)
	        	throw (Exception)e.getTargetException();
	        else
	        	throw e;
	      }
	}
	
	/**
	 * Create an instance of the class. 
	 * @param className : complete class name to instanciate 
	 * @param aClassParam : constructor parameters
	 * @return Object
	 * @throws Exception
	 */
	private Object createClass(String application, String className, Object[] aClassParam) throws Exception
	{
		String tempStrClass = null;
	    try {

			//System.out.println("WebApplicationConfig.getSingleton().getRules().getDestClass(className); in : '" + className+"'");
			tempStrClass = WebApplicationConfig.getSingleton().getTangoWebRewriteRules(application).getDestClass(className);
			//System.out.println("WebApplicationConfig.getSingleton().getRules().getDestClass(className); out : '" + tempStrClass+"'");
			if(tempStrClass != null && (!"".equals(tempStrClass.trim())) )
			{
				className = tempStrClass;
			}
			
	    	
	    	
	        // we get the class definition object
	        Class c = Class.forName(className);

	        // we implement the class
	        
	        // we take the constructor Param class
	        Class[] constParamClass = null;
	        if(aClassParam == null)
	        {
	        	constParamClass =  new Class[]{};
	        }
	        else
	        {
	        	constParamClass =  new Class[aClassParam.length];
	        	for(int i = 0;i <  aClassParam.length ;i++)
	        	{
	        			constParamClass[i] =  getObjectClass(aClassParam[i]);
	        	}
	        }
	        
	        // we take the constructor
	        Constructor constructor = c.getConstructor(constParamClass);
	        
	        // we create an instance with the default constructor
	        Object object = constructor.newInstance(aClassParam);

	        // we execute the method
	        return object;
	      } catch (ClassNotFoundException e) {
	    	System.out.println("WebApplicationConfig.getSingleton().getRules().getDestClass(className); in : '" + className+"'");	    	  
	    	System.out.println("WebApplicationConfig.getSingleton().getRules().getDestClass(className); out : '" + tempStrClass+"'");
	    	System.out.println("Class.forName(  ) can't find the class");
	        throw e;
	      } catch (NoSuchMethodException e2) {
		    	System.out.println("WebApplicationConfig.getSingleton().getRules().getDestClass(className); in : '" + className+"'");	    	  
		    	System.out.println("WebApplicationConfig.getSingleton().getRules().getDestClass(className); out : '" + tempStrClass+"'");
	        System.out.println("method doesn't exist");
	        throw e2;
	      } catch (IllegalAccessException e3) {
		    	System.out.println("WebApplicationConfig.getSingleton().getRules().getDestClass(className); in : '" + className+"'");	    	  
		    	System.out.println("WebApplicationConfig.getSingleton().getRules().getDestClass(className); out : '" + tempStrClass+"'");
	        System.out.println("no permission to invoke that method");
	        throw e3;
	      } catch (InvocationTargetException e) {
		    	System.out.println("WebApplicationConfig.getSingleton().getRules().getDestClass(className); in : '" + className+"'");	    	  
		    	System.out.println("WebApplicationConfig.getSingleton().getRules().getDestClass(className); out : '" + tempStrClass+"'");
	        System.out.println("an exception ocurred while invoking that method");
	        System.out.println("Method threw an: " + e.getTargetException());
	        
	        // if we have a instanceof Exception we throw it else we throw the invocation exception
	        if(e.getTargetException() instanceof Exception)
	        	throw (Exception)e.getTargetException();
	        else
	        	throw e;
	      }		
	}
	
	/**
	 * Execute the method
	 * @param object : object containing the method to execute. 
	 * @param methodName : method to execute
	 * @param aParameter : method's parameters
	 * @param bIsSaved : is the the object is saved
	 * @param aParameterClass : it's important if a parameter is equal to null we can't find the class type without this array 
	 * @return WebResponse
	 * @throws Exception
	 */
	private WebResponse processMethod(String application, Object object, String methodName, Object[] aParameter, boolean bIsSaved, Class[] aParameterClass) throws Exception
	{
	    try {
	    	
	    	// we retrieve the parameters
	    	aParameter = retrieveObject(application, aParameter);
	    	
	        Method m = null;
	        // we get the method definition
	        Class[] aClass = null;
	        
	        if(aParameterClass ==null)
	        {
	        	if(aParameter!=null)
		        {
		        	aClass = new Class[aParameter.length];
		        	for(int i=0; i < aParameter.length;i++)
		        	{
		        		aClass[i] = getObjectClass(aParameter[i]);
		        	}
		        }
		        else
		        {
		        	aClass = new Class[]{};
		        }
	    	}
	        else
	        {
	        	aClass = aParameterClass;
	        }
	        // we get the class definition object
	        Class c = object.getClass();
	        
	        m = c.getMethod(methodName, aClass);
	        
	        // We execute the method.
	        Object ret = m.invoke(object, (Object[])aParameter);
	        
	        WebResponse response = new WebResponse();
	        
	        // we check if the content is serializable, if not we put the object's attribute into a container for the web transmission
	        if(ret != null)
	        {
	        	IWebObjectStorage storage = null; 
	        	// if the result is an array we need to check if the content is serializable ... we check only the first element
	        	if(ret.getClass().isArray())
	        	{
	        		if(!ret.getClass().getComponentType().isPrimitive())
	        		{
		        		Object[] aRet = (Object[])ret; 
		        		if(aRet.length > 0)
				        	if(!(aRet[0] instanceof java.io.Serializable) )
				        	{
				        		storage = getWebObjectStorage(application,  ret.getClass().getComponentType().getName());
				        		ret = SerializeObject.serializeObject(storage, ret); //serializeResult(m_webStorage, ret);
				        	}
	        		}
	        	}
	        	else	
	        		if(!ret.getClass().isPrimitive())
	        		{
			        	if(!(ret instanceof java.io.Serializable) || (ret instanceof AttributeValue ))
			        	{
					        String className = ret.getClass().getName();
			        		storage = getWebObjectStorage(application, className);
			        		ret = SerializeObject.serializeObject(storage, ret); //serializeResult(m_webStorage,ret);
			        	}
	        		}
	        }
	        if(!bIsSaved)
	        {
	        	// we serialize the object : temp implementation
		        String className = object.getClass().getName();
		        IWebObjectStorage storage = getWebObjectStorage(application, className);
	        	
		        response.setResult(new Object[]{ret, SerializeObject.serializeObject(storage,object)}); //serializeCallObject(m_webStorage,object)});
	        }
	        else
		        response.setResult(new Object[]{ret, null});
	        
	        return response;
	      } catch (NoSuchMethodException e2) {
	        System.out.println("method doesn't exist");
	        throw e2;
	      } catch (IllegalAccessException e3) {
	        System.out.println("no permission to invoke that method");
	        throw e3;
	      } catch (InvocationTargetException e) {
	        System.out.println("an exception ocurred while invoking that method");
	        System.out.println("Method threw an: " + e.getTargetException());
	        // object, String methodName, Object[] aParameter, boolean bIsSaved
	        System.out.println("Class Name :" + object.getClass().getName());
	        System.out.print("Method Name :" + methodName + " : ");
	        for(Object param : aParameter)
	        {
	        	if(param != null)
	        		System.out.print( param.getClass() + ", ");
	        }
	        System.out.println();
	        
	        
	        // if we have a instanceof Exception we throw it else we throw the invocation exception
	        if(e.getTargetException() instanceof Exception)
	        	throw (Exception)e.getTargetException();
	        else
	        	throw e;
	      }
	}	
	
	/**
	 * Retrieve Object's store in the Server Storage. 
	 * @return Object[]
	 */
	public Object[] retrieveObject(String application, Object[] aParameter) throws Exception
	{
		Object[] aNewParam = new Object[aParameter.length];
		WebSerializerObject webSerialParam = null;
		// objectId parameter
		String objectID = null;

		// class name et class params if we need to create a new instance of the parameters
		String className = null;
		Object[] aClassParam = null;
		
		IWebObjectStorage tempStorage = null; 
		for(int i=0;i<aParameter.length;i++)
		{
			if( (aParameter[i] instanceof WebSerializerObject) && (aParameter[i] != null))
			{
				webSerialParam=(WebSerializerObject)aParameter[i];
				className = webSerialParam.getClazz();
				// we use the class name to retrieve the object
				tempStorage = getWebObjectStorage(application, className);
				objectID = webSerialParam.getObjectID();
				if(objectID != null && tempStorage.isObjectID(objectID))
						aNewParam[i] = tempStorage.getObject(objectID); // we just take the object coresponding to the objectid
				else
				{
					// we build a new instance of the parameters with the build information (classname and constructor parameters)
					aClassParam = (Object[])webSerialParam.getAttribute("aActionParam");
					// we create an instance of the class and we store it
					aNewParam[i] = createClass(application, className,  aClassParam);
					if(objectID != null)
						tempStorage.addObject(objectID, aNewParam[i]);
				}
			}
			else
				aNewParam[i] = aParameter[i];
		}
		return aNewParam;
	}
	
	// temp method
	private Class getObjectClass(Object object)
	{
		// we control the type of the method and return the primitive type 
		if(object instanceof Integer)
			return int.class;
		if(object instanceof Short)
			return short.class;
		if(object instanceof Character)
			return char.class;
		if(object instanceof Boolean)
			return boolean.class;
		if(object instanceof Long)
			return long.class;
		if(object instanceof Double)
			return double.class;
		if(object instanceof Byte)
			return byte.class;
		if(object instanceof Float)
			return float.class;
		if(object == null)
			System.out.println(object);
		
		return object.getClass();
	}
	
	/**
	 * Return the object storage corresponding to the class 
	 * @param className
	 * @return WebObjectStorage
	 */
	public IWebObjectStorage getWebObjectStorage(String application, String className)
	{
		// we check if the class have not a rewrite rule defined in the config file
		String tempStrClass = WebApplicationConfig.getSingleton().getTangoWebRewriteRules(application).getDestClass(className);
		if(tempStrClass != null && (!"".equals(tempStrClass.trim())) )
		{
			// we find the name to use in the config, we replace the old name by the new one.
			className = tempStrClass;
		}
		
		TangoWebBeanList webBeanList = WebApplicationConfig.getSingleton().getBeanList();
		TangoWebBean bean = webBeanList.getBean(className);
		try
		{
			if(bean.isOptimizedStorage())
			{
				if(m_specificStorage.containsKey(className))
				{
					return m_specificStorage.get(className);
				}
				else
				{
					OptimizedWebObjectStorage storage = new OptimizedWebObjectStorage(m_strSessionID);
					m_specificStorage.put(className, storage);
					return storage;
				}
			}
			else
				return m_webStorage;
		}
		catch(NullPointerException npe)
		{
			System.err.println(npe.getMessage() + ":" + className);
			throw npe;
			
		}
	}	
}
