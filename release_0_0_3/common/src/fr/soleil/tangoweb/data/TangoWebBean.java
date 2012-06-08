package fr.soleil.tangoweb.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the bean definition
 * @author BARBA-ROSSA
 *
 */
public class TangoWebBean implements java.io.Serializable
{
	private String m_strBeanID = null;
	private String m_strClassName = null;
	private boolean m_bStoreObject = true;
	private boolean m_bOptimizedStorage = false;
	private boolean removeAllAttribute = false;
	private List<String> m_removeAttributes = null;
	private List<TangoWebBeanAttribute> m_attributes = null;
	
	public TangoWebBean()
	{
	}

	public TangoWebBean(String strBeanID, String strClassName)
	{
		m_strBeanID = strBeanID;
		m_strClassName = strClassName;
		m_removeAttributes = new ArrayList<String>();
		m_attributes = new ArrayList<TangoWebBeanAttribute>();
	}
	
	
	public List<TangoWebBeanAttribute> getAttributes() {
		return m_attributes;
	}
	public void setAttributes(List<TangoWebBeanAttribute> attributes) {
		this.m_attributes = attributes;
	}
	public boolean isStoreObject() {
		return m_bStoreObject;
	}
	public void setStoreObject(boolean storeObject) {
		m_bStoreObject = storeObject;
	}
	public String getBeanID() {
		return m_strBeanID;
	}
	public void setBeanID(String beanID) {
		m_strBeanID = beanID;
	}
	public String getClassName() {
		return m_strClassName;
	}
	public void setClassName(String className) {
		m_strClassName = className;
	}
	public boolean isRemoveAllAttribute() {
		return removeAllAttribute;
	}
	public void setRemoveAllAttribute(boolean removeAllAttribute) {
		this.removeAllAttribute = removeAllAttribute;
	}
	public List<String> getRemoveAttributes() {
		return m_removeAttributes;
	}
	public void setRemoveAttributes(List<String> removeAttributes) {
		this.m_removeAttributes = removeAttributes;
	}

	public boolean isOptimizedStorage() {
		return m_bOptimizedStorage;
	}

	public void setOptimizedStorage(boolean optimizedStorage) {
		m_bOptimizedStorage = optimizedStorage;
	}
}
