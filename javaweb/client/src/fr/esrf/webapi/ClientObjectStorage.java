package fr.esrf.webapi;

import java.util.HashMap;

public class ClientObjectStorage {
	public final static int storageMaxSize = 200;  
	
	private static ClientObjectStorage singleton = null;
	private HashMap<Object, String> m_storedObjectMap = null;
	private HashMap<Object, String> m_old_storedObjectMap = null;
	
	public ClientObjectStorage()
	{
		m_storedObjectMap = new HashMap<Object, String>();
		m_old_storedObjectMap = null;
	}
	
	public static ClientObjectStorage getSingleton(){
		if(singleton == null)
			createSingleton();
		return singleton;
	}
	
	public static void createSingleton()
	{
		singleton = new ClientObjectStorage();
	}
	
	public synchronized void remove(Object keyObject)
	{
		if(m_storedObjectMap.containsKey(keyObject))
			m_storedObjectMap.remove(keyObject);
		else
			if(m_old_storedObjectMap != null && m_old_storedObjectMap.containsKey(keyObject))
				m_old_storedObjectMap.remove(keyObject);
		
		
	}
	public synchronized void put(Object keyObject, String value)
	{
		m_storedObjectMap.put(keyObject, value);
		if(m_storedObjectMap.size() >= storageMaxSize)
		{
			m_old_storedObjectMap = m_storedObjectMap;
			m_storedObjectMap = new HashMap<Object, String>();
		}
	}
	public synchronized boolean containsKey(Object hashcode)
	{
		if(m_storedObjectMap.containsKey(hashcode))
			return true;
		else
			if(m_old_storedObjectMap != null && m_old_storedObjectMap.containsKey(hashcode))
				return true;
		return false;
	}
	
	public synchronized String get(Object keyObject)
	{
		if(m_storedObjectMap.containsKey(keyObject))
			return m_storedObjectMap.get(keyObject);
		else
			if(m_old_storedObjectMap != null && m_old_storedObjectMap.containsKey(keyObject))
				return m_old_storedObjectMap.get(keyObject);
		return null;
	}
}
