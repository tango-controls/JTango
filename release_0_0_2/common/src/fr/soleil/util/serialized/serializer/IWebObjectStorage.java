package fr.soleil.util.serialized.serializer;

import java.util.HashMap;

public interface IWebObjectStorage {

	/**
	 * Add an Object to the container
	 * @param object
	 * @return String : the Object key 
	 */
	public abstract String addObject(Object object);

	/**
	 * Use this method carrefully if the objectid already exist you could delete the olb value
	 * @param objectID
	 * @param object
	 * @return 
	 */
	public abstract void addObject(String objectID, Object object);

	/**
	 * We return the object coresponding to the objectID 
	 * @param objectID
	 * @return Object
	 */
	public abstract Object getObject(String objectID);

	public abstract void removeObject(String objectID);

	/**
	 * Return true if the objectid is an objectid used in this storage 
	 * @return boolean
	 */
	public abstract boolean isObjectID(String objectID);

	public abstract HashMap<String, Object> getMap();

	public abstract void setMap(HashMap<String, Object> map);

	public abstract String getSessionID();

	public abstract void setSessionID(String sessionID);

}