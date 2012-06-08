package fr.soleil.util.tree;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This list contains a group of ATreeElement.
 * It can be used to store different tree and manage them
 * @author BARBA-ROSSA
 *
 */
public class ATreeElementList
{
	private ArrayList m_list = new ArrayList(); // the list
	private HashMap m_map = new HashMap(); // The map
	
	/**
	 * Add a Tree in the end of the list
	 * @param newATreeElement
	 */
	public void addATreeElement(ATreeElement newATreeElement){
		m_list.add(newATreeElement);
	}

	/**
	 * Get a tree by it's index
	 * @param index
	 * @return ATreeElement
	 */
	public ATreeElement getATreeElementbyIndex(int index)
	{
		return (ATreeElement)m_list.get(index);
	}
	
	/**
	 * Add a key for a Tree.
	 * The key may be use to retreive the Tree by it 
	 * @param strKey
	 * @param elementIndex
	 */
	public void addATreeElementKey(String strKey, int elementIndex )
	{
		ATreeElement newATreeElement = getATreeElementbyIndex(elementIndex);
		m_map.put(strKey, newATreeElement);
	}
	
	/**
	 * Get a Tree by it's key. 
	 * The method <code>ATreeElementList.addATreeElementKey</code> must be call before.
	 * @param strKey 
	 * @return {@link ATreeElement}
	 */
	public ATreeElement getATreeElementbyKey(String strKey)
	{
		if(!m_map.containsKey(strKey))
			return null;
		else
			return (ATreeElement)m_map.get(strKey);
	}
	
	/**
	 * Return the size of the list
	 * @return int
	 */
	public int size()
	{
		if (m_list == null)
			return 0;
		return m_list.size();
	}
	
	/**
	 * Remove all elements from this list
	 *
	 */
	public void clear()
	{
		if(m_list!=null)
			m_list.clear();
		if(m_map != null)
			m_map.clear();
	}
	
}
