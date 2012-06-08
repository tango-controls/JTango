package fr.soleil.webserver.servlet;

import java.util.HashMap;

public class ProcessActionCounter {

	private static ProcessActionCounter singleton = null;
	private HashMap<String, Integer> s_accessCounter = null;
	private HashMap<String, Integer> s_removeCounter = null;
	private HashMap<String, Integer> s_classCounter = null;
	private HashMap<String, Integer> s_methodCounter = null;
	private HashMap<String, Integer> s_classUseCounter = null;
	
	public ProcessActionCounter()
	{
		s_accessCounter = new HashMap<String, Integer>();
		s_removeCounter = new HashMap<String, Integer>();
		s_classCounter = new HashMap<String, Integer>();
		s_methodCounter = new HashMap<String, Integer>();
		s_classUseCounter = new HashMap<String, Integer>();
	}
	public static void createSingleton()
	{
		singleton = new ProcessActionCounter();
	}
	
	public static ProcessActionCounter getSingleton()
	{
		if(singleton == null)
			createSingleton();
		
		return singleton;
	}
	
	
	public void addMethodCounter(String className)
	{
		addSynchMethodCounter(className);
	}
	
	/**
	 * WARNING : this method must be call ONLY for test
	 * @param application
	 */
	private synchronized void addSynchMethodCounter(String className)
	{
		if(s_methodCounter.containsKey(className))
		{
			int count= (Integer)s_methodCounter.get(className);
			count++;
			s_methodCounter.put(className, count);
		}
		else
			s_methodCounter.put(className, 1);
	}	
	
	
	public void addClassCounter(String className)
	{
		addSynchClassCounter(className);
	}
	
	/**
	 * WARNING : this method must be call ONLY for test
	 * @param application
	 */
	private synchronized void addSynchClassCounter(String className)
	{
		if(s_classCounter.containsKey(className))
		{
			int count= (Integer)s_classCounter.get(className);
			count++;
			s_classCounter.put(className, count);
		}
		else
			s_classCounter.put(className, 1);
	}	
	
	public void addClassUseCounter(String className)
	{
		addSynchClassUseCounter(className);
	}	
	
	/**
	 * WARNING : this method must be call ONLY for test
	 * @param application
	 */
	private synchronized void addSynchClassUseCounter(String className)
	{
		if(s_classUseCounter.containsKey(className))
		{
			int count= (Integer)s_classUseCounter.get(className);
			count++;
			s_classUseCounter.put(className, count);
		}
		else
			s_classUseCounter.put(className, 1);
	}	
	
	public void addAccessCounter(String className)
	{
		addSynchAccessCounter(className);
	}	
	
	/**
	 * WARNING : this method must be call ONLY for test
	 * @param application
	 */
	private synchronized void addSynchAccessCounter(String application)
	{
		if(s_accessCounter.containsKey(application))
		{
			int count= (Integer)s_accessCounter.get(application);
			count++;
			s_accessCounter.put(application, count);
		}
		else
			s_accessCounter.put(application, 1);
	}
	
	public void addRemoveCounter(String className)
	{
		addSynchRemoveCounter(className);
	}
	
	/**
	 * WARNING : this method must be call ONLY for test
	 * @param application
	 */
	private synchronized void addSynchRemoveCounter(String application)
	{
		if(s_removeCounter.containsKey(application))
		{
			int count= (Integer)s_removeCounter.get(application);
			count++;
			s_removeCounter.put(application, count);
		}
		else
			s_removeCounter.put(application, 1);
			
	}

	public HashMap<String, Integer> getAccessCounter() {
		return s_accessCounter;
	}

	public void setAccessCounter(HashMap<String, Integer> counter) {
		s_accessCounter = counter;
	}

	public HashMap<String, Integer> getClassCounter() {
		return s_classCounter;
	}

	public void setClassCounter(HashMap<String, Integer> counter) {
		s_classCounter = counter;
	}

	public HashMap<String, Integer> getClassUseCounter() {
		return s_classUseCounter;
	}

	public void setClassUseCounter(HashMap<String, Integer> useCounter) {
		s_classUseCounter = useCounter;
	}

	public HashMap<String, Integer> getMethodCounter() {
		return s_methodCounter;
	}

	public void setMethodCounter(HashMap<String, Integer> counter) {
		s_methodCounter = counter;
	}

	public HashMap<String, Integer> getRemoveCounter() {
		return s_removeCounter;
	}

	public void setRemoveCounter(HashMap<String, Integer> counter) {
		s_removeCounter = counter;
	}	
}
