package fr.soleil.util.serialized.serializer;

import java.util.HashMap;

/**
 * Contains Value for a non-serialized object
 * @author BARBA-ROSSA
 *
 */
public class WebSerializerObject implements java.io.Serializable{

	private String m_ObjectID = null; 
	private String m_strClass = null;
	private HashMap<String, Object> m_attributes = null;
	
	public WebSerializerObject(String strClass)
	{
		m_strClass = strClass;
		m_attributes = new HashMap<String, Object>();
	}
	
	/**
	 * Add an attribut in the serialized class
	 * @param attrName
	 * @param attrValue
	 */
	public void addAttribute(String attrName, Object attrValue)
	{
		m_attributes.put(attrName, attrValue);
	}
	
	/**
	 * return true if the attrName is an Attribute.
	 * @param attrName
	 * @return boolean
	 */
	public boolean isAttribute(String attrName)
	{
		if(m_attributes.containsKey(attrName))
			return true;
		else
			return false;		
	}
	
	public Object getAttribute(String attrName)
	{
		if(m_attributes.containsKey(attrName))
			return m_attributes.get(attrName);
		else
			return null;
	}

	public void removeAttribute(String attrName)
	{
		if(m_attributes.containsKey(attrName))
			m_attributes.remove(attrName);
	}
	
	public String getClazz() {
		return m_strClass;
	}

	public void setClazz(String class1) {
		m_strClass = class1;
	}

	public String getObjectID() {
		return m_ObjectID;
	}

	public void setObjectID(String objectID) {
		m_ObjectID = objectID;
	}
	
}
