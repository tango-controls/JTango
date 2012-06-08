package fr.soleil.webserver.processaction;

import java.lang.reflect.Field;
import java.util.List;

import fr.soleil.tangoweb.data.TangoWebBean;
import fr.soleil.tangoweb.data.TangoWebBeanList;
import fr.soleil.util.serialized.serializer.IWebObjectStorage;
import fr.soleil.util.serialized.serializer.WebSerializer;
import fr.soleil.util.serialized.serializer.WebSerializerObject;
import fr.soleil.util.serialized.serializer.WebSerializerObjectArray;
import fr.soleil.webserver.servlet.WebApplicationConfig;

/**
 * Serialization class for Object which can't use java default serialize method.
 * we store non-seralize value into a storage in web server and we send value which could be serialize. 
 * @author BARBA-ROSSA
 *
 */
public class SerializeObject {

	/**
	 * We put the object in the Web Storage
	 * @param result
	 * @param bStore
	 * @return WebSerializerObject
	 * @throws Exception
	 */
	public static Object serializeObject(IWebObjectStorage webStorage, Object result) throws Exception
	{
		if(result == null)
			return null;
		
		// if the class is an array, we use the serialize object array
		if(result.getClass().isArray())
			return serializeObjectArray(webStorage, result);
		
		TangoWebBeanList webBeanList = WebApplicationConfig.getSingleton().getBeanList();
		
		Class clazz = result.getClass();
		
		// we get the object class name
		String className = clazz.getName();
		
		// we get the webBean interface 
		TangoWebBean webBean = webBeanList.getBean(className);
		
		// if webBean == null, we return null. Because the object is serializable.
		if(webBean == null)
			return result;
		
		
		
		// we add the class name into the web serializer object
		WebSerializerObject webSerialObject = null;
		if(webBean.isRemoveAllAttribute())
		{
			webSerialObject = new WebSerializerObject(className);
		}else{
			webSerialObject = WebSerializer.serializeObject(result);
			
			// we remove the non serializable attributes
			// we get the list of attributes to remove
			List<String> removeAttrList = webBean.getRemoveAttributes(); 
			// we remove them  beacause they aren't serializable (such as the ANY object)
			if(removeAttrList != null)
				for(int i = 0;i < removeAttrList.size();i++)
					webSerialObject.removeAttribute(removeAttrList.get(i));
		}
		
		// we add the attributes
		if(webBean.getAttributes() != null && webBean.getAttributes().size() > 0)
		{
			Field field = null;
			String strName = null;
			Object fieldValue = null;
			Object tempWebSerialAttr = null;
			
			// for each BeanAttribute we get the value and we put. We serialize it and we put it into the webSerialObject.
			for(int i = 0; i < webBean.getAttributes().size();i++)
			{
				strName = webBean.getAttributes().get(i).getName();
				field = clazz.getDeclaredField(strName);
				fieldValue = WebSerializer.getFieldValue(result, clazz, field);
				
				tempWebSerialAttr = serializeObject(webStorage, fieldValue);
				if(tempWebSerialAttr != null)
				{
					// we put it into the webSerialObject
					webSerialObject.addAttribute(strName, tempWebSerialAttr);
				}
				else
				{
					// else we just remove the attribute.
					webSerialObject.removeAttribute(strName);
				}
			}
		}
		
		// if we need to store the object add it in the webStorage.
		if(webBean.isStoreObject())
		{
			// we put it in the WebStorage
			String objectID = webStorage.addObject(result);
	
			// we put the objectid in the serialized object
			webSerialObject.setObjectID(objectID);
		}
		return webSerialObject;
	}	
	
	/**
	 * We put the object in the Web Storage
	 * @param result
	 * @param bStore
	 * @return WebSerializerObjectArray
	 * @throws Exception
	 */
	public static Object serializeObjectArray(IWebObjectStorage webStorage, Object result) throws Exception
	{
		TangoWebBeanList webBeanList = WebApplicationConfig.getSingleton().getBeanList();
		
		// we check if the component type is in the bean list
		// we get the object class name
		String className = result.getClass().getComponentType().getName();
		
		// we get the webBean interface 
		TangoWebBean webBean = webBeanList.getBean(className);
		
		// if webBean == null, we return null. Because the object is already serializable.
		if(webBean == null)
			return result;
		
		// we cast the result into a object array
		Object[] aResult = (Object[])result;
		// we prepare the array of WebSerializerObject
		WebSerializerObject[] webSerial = new WebSerializerObject[aResult.length];
		for(int i = 0; i < aResult.length ;i++)
		{
			// we serialize each objet contains in the result
			webSerial[i] = (WebSerializerObject)serializeObject(webStorage, aResult[i]);
		}
		
		// we use the WebSerializerObjectArray to store the WebSerializerObject[] and the type content by the array.
		// we need the type to rebuild the array
		// it's important to set the class name of the component type. In the client side, if the array is empty we must create the true type of array.
		WebSerializerObjectArray webObjectArray = new WebSerializerObjectArray(result.getClass().getComponentType().getName());
		webObjectArray.setWebSerializerObject(webSerial);
		
		return webObjectArray;
	}
}
