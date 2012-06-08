package fr.soleil.tangoweb.data;

import java.util.HashMap;
import java.util.Map;

public class TangoWebRewriteRules {
	private Map<String, String> rulesMap = null;
	
	public TangoWebRewriteRules()
	{
		rulesMap = new HashMap<String, String>();
	}
	
	public void addRules(String srcClass, String destClass)
	{
		rulesMap.put(srcClass, destClass);
	}
	
	public String getDestClass(String srcClass)
	{
		if(srcClass != null)
		{
			return rulesMap.get(srcClass);
		}
		return null;
	}

	public Map<String, String> getRulesMap() {
		return rulesMap;
	}

	public void setRulesMap(Map<String, String> rulesMap) {
		this.rulesMap = rulesMap;
	}	
}
