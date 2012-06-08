package fr.soleil.tangoweb.data;

import java.util.HashMap;
import java.util.Map;

public class TangoWebBeanList implements java.io.Serializable
{
	private Map<String, TangoWebBean> m_beanMap = null;
	
	public TangoWebBeanList()
	{
		m_beanMap = new HashMap<String, TangoWebBean>();
	}

	public Map<String, TangoWebBean> getMap() {
		return m_beanMap;
	}

	public void setMap(Map<String, TangoWebBean> map) {
		m_beanMap = map;
	}
	
	public void addBean(TangoWebBean newBean)
	{
		if(newBean == null)
			return;
		m_beanMap.put(newBean.getClassName(), newBean);
	}
	
	
	public TangoWebBean getBean(String className)
	{
		if(className == null)
			return null; 
		
		return m_beanMap.get(className);
	}
}
