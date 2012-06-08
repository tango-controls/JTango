package fr.soleil.webserver.servlet;

import fr.soleil.tangoweb.data.TangoWebApplicationRules;
import fr.soleil.tangoweb.data.TangoWebBeanList;
import fr.soleil.tangoweb.data.TangoWebRewriteRules;

public class WebApplicationConfig {
	private static WebApplicationConfig singleton = null;
	private TangoWebBeanList m_beanList = null;
	private TangoWebRewriteRules m_rules = null;
	private TangoWebApplicationRules m_appRules = null;
	
	public static void createSingleton()
	{
		singleton = new WebApplicationConfig();
	}
	
	public static WebApplicationConfig getSingleton()
	{
		if(singleton == null)
			createSingleton();
		return singleton;
	}

	public TangoWebBeanList getBeanList() {
		return m_beanList;
	}

	public void setBeanList(TangoWebBeanList list) {
		m_beanList = list;
	}

	public TangoWebRewriteRules getRules() {
		return m_rules;
	}

	public void setRules(TangoWebRewriteRules m_rules) {
		this.m_rules = m_rules;
	}

	public TangoWebApplicationRules getAppRules() {
		return m_appRules;
	}

	public void setAppRules(TangoWebApplicationRules rules) {
		m_appRules = rules;
	}
	
	public TangoWebRewriteRules getTangoWebRewriteRules(String application)
	{
		TangoWebRewriteRules tangoWebRewriteRules = m_appRules.getTangoWebRewriteRules(application);
		if(tangoWebRewriteRules==null)
			return m_rules;
		else
			return tangoWebRewriteRules;
	}
	
}
