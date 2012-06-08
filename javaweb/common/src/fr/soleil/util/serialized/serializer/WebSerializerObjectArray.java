package fr.soleil.util.serialized.serializer;


/**
 * Contains Value for a non-serialized object
 * @author BARBA-ROSSA
 *
 */
public class WebSerializerObjectArray implements java.io.Serializable{

	private String m_strClass = null;
	private WebSerializerObject[] m_WebSerializerObject = null;
	
	public WebSerializerObjectArray(String strClass)
	{
		m_strClass = strClass;
	}
	
	

	public String getClazz() {
		return m_strClass;
	}

	public void setClazz(String class1) {
		m_strClass = class1;
	}



	public WebSerializerObject[] getWebSerializerObject() {
		return m_WebSerializerObject;
	}



	public void setWebSerializerObject(WebSerializerObject[] webSerializerObject) {
		m_WebSerializerObject = webSerializerObject;
	}
	
}
