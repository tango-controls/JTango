package fr.soleil.util.serialized.serializer;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.sun.org.apache.xml.internal.serializer.ToUnknownStream;

/**
 * Serialize and Rebuild java object which cannot use the serialize interface
 * @author BARBA-ROSSA
 *
 */
public class WebSerializer {
	
	public static WebSerializerObject serializeObject(Object object) throws Exception
	{
		if(object == null)
			return null;
		
		Class clazz = object.getClass();
		
		// we get the object class name
		String className = clazz.getName();
		// we add the class name into the web serializer object
		WebSerializerObject serialObj = new WebSerializerObject(className);
		
		// we get the value and we put them into the webserializer object
		Field[] fields = clazz.getDeclaredFields();
		addSerializeFields(object, clazz, fields, serialObj);
		addSerializeFields(object, clazz, clazz.getSuperclass().getDeclaredFields(), serialObj);
		return serialObj;
	} 

	private static void addSerializeFields(Object object, Class clazz, Field[] fields,WebSerializerObject serialObj) throws Exception
	{
		Field field = null;
		int fieldModif = -1;
		Object fieldValue = null;
		Method getMethod = null;
		
		for(int i=0; i < fields.length; i++)
		{
			field = fields[i];
			fieldModif = field.getModifiers();
			
			// if the field is final we don't serialize it
			if(!Modifier.isFinal(fieldModif))
			{
				if(Modifier.isPublic(fieldModif))
				{
					// if the field is public we just take the value
					fieldValue = field.get(object);
					serialObj.addAttribute(field.getName(), fieldValue);
				}
				else
				{
					// else we search a getter to take the value
					getMethod = clazz.getMethod(getFieldGetterName(field), new Class[]{});
					
					// we call the method
					fieldValue = getMethod.invoke(object, new Object[]{});
					serialObj.addAttribute(field.getName(), fieldValue);
				}
			}
		}
	}
	
	public static Object getFieldValue(Object object, Class clazz, Field field) throws Exception
	{
		int fieldModif = -1;
		Object fieldValue = null;
		Method getMethod = null;

		fieldModif = field.getModifiers();
		
		// if the field is final we don't serialize it
		if(!Modifier.isFinal(fieldModif))
		{
			if(Modifier.isPublic(fieldModif))
			{
				// if the field is public we just take the value
				fieldValue = field.get(object);
				return fieldValue;
			}
			else
			{
				// else we search a getter to take the value
				getMethod = clazz.getMethod(getFieldGetterName(field), new Class[]{});
				
				// we call the method
				fieldValue = getMethod.invoke(object, new Object[]{});
				return fieldValue;
			}
		}
		return null;
	}
	
	private static String getFieldGetterName(Field field)
	{
		String name = field.getName();
		if(name.length()==1)
			return "get" + name.toUpperCase();
		else
			return "get"+ name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	private static String getFieldSetterName(Field field)
	{
		String name = field.getName();
		if(name.length()==1)
			return "set" + name.toUpperCase();
		else
			return "set"+ name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	
	public static WebSerializerObjectArray serializeObject(Object[] object) throws Exception
	{
		if(object == null)
			return null;
		
		Class arrayType = object.getClass().getComponentType(); 
		if(arrayType == null) // if the result is null the object is not an array ... 
			return null;
		
		
		
		WebSerializerObjectArray webArray = new WebSerializerObjectArray(arrayType.getName());
		WebSerializerObject[] webs = new WebSerializerObject[object.length];
		for(int i=0; i < object.length;i++)
		{
			webs[i] = serializeObject(object[i]);
		}
		webArray.setWebSerializerObject(webs);
		return webArray;
		
	}
	
	public static Object rebuild(Object object)
	{
		try
		{
			if(object instanceof WebSerializerObjectArray)
				return reBuild((WebSerializerObjectArray) object);
	
			if(object instanceof WebSerializerObject)
				return reBuild((WebSerializerObject) object);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return object;
	}
	
	public static Object reBuild(WebSerializerObject webSerializerObject) throws Exception
	{
		
		//  we get the class name 
		String className= webSerializerObject.getClazz();
		
        // we get the class definition object
        Class clazz = Class.forName(className);		
		
        // we take the constructor
        Constructor constructor = clazz.getConstructor(new Class[]{});
        
        // we create an instance with the default constructor
        Object object = constructor.newInstance(new Object[]{});
        
        // for each field we put a value
		Field[] fields = clazz.getDeclaredFields();
		putFieldValue(webSerializerObject, clazz, fields, object);
		putFieldValue(webSerializerObject, clazz, clazz.getSuperclass().getDeclaredFields(), object);
		
		return object;
	}

	private static void putFieldValue(WebSerializerObject webSerializerObject, Class clazz, Field[] fields, Object object) throws Exception
	{
		Field field = null;
		int fieldModif = -1;
		Object fieldValue = null;
		Method setMethod = null;
		for(int i=0; i < fields.length; i++)
		{
			field = fields[i];
			fieldModif = field.getModifiers();
			
			// if the field is final we don't rebuild it
			if(!Modifier.isFinal(fieldModif))
			{
				
				if(webSerializerObject.isAttribute(field.getName())) // we test if the field is a serialized attribute
				{
					// we get the value from the WebSerializerObject
					fieldValue = webSerializerObject.getAttribute(field.getName());
					
					// we test if the value is an instance of WebSerializerObject
					if(fieldValue instanceof WebSerializerObject)
					{
						// we call rebuild the class
						fieldValue = reBuild((WebSerializerObject)fieldValue);
						
					}
					
					
					if(Modifier.isPublic(fieldModif))
					{
						// if the field is public we just take the value
						field.set(object, fieldValue);
					}
					else
					{
						// else we search a getter to take the value
						// we get the setter method
						setMethod = clazz.getMethod(getFieldSetterName(field), new Class[]{field.getType()});
						
						// we invoke the setter
						setMethod.invoke(object, new Object[]{fieldValue});
					}
				}
			}
		}		
	}
	
	public static Object[] reBuild(WebSerializerObjectArray webSerializerObjectArray) throws Exception
	{
		if(webSerializerObjectArray == null)
			return null;
		
		String className = webSerializerObjectArray.getClazz();
		
        // we get the class definition object
        Class clazz = Class.forName(className);
        
        WebSerializerObject[] webSerializerObject = webSerializerObjectArray.getWebSerializerObject();
        int size = 0;
        if(webSerializerObject != null)
        	size = webSerializerObject.length;
        
        
		Object[] webs = (Object[])Array.newInstance(clazz, size);
		if(size == 0)
			return webs;
		
		for(int i=0; i < webSerializerObject.length;i++)
		{
			webs[i] = reBuild(webSerializerObject[i]);
		}
		return webs;	
	}
}
