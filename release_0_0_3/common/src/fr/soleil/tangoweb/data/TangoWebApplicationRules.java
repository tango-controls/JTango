package fr.soleil.tangoweb.data;

import java.util.HashMap;

/**
 * Contains à list of specific application rules
 * @author BARBA-ROSSA
 *
 */
public class TangoWebApplicationRules {
	private HashMap<String, TangoWebRewriteRules> applicationRules = null;
	
	public TangoWebApplicationRules()
	{
		applicationRules = new HashMap<String, TangoWebRewriteRules>();
	}
	
	public void addTangoWebRewriteRules(String application, TangoWebRewriteRules rules)
	{
		applicationRules.put(application, rules);
	}
	
	public TangoWebRewriteRules getTangoWebRewriteRules(String application)
	{
		if(applicationRules.containsKey(application))
			return applicationRules.get(application);
		return null;
	}
}
