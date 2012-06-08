package fr.soleil.util.tree;

import java.util.List;
import java.util.ArrayList;

import fr.soleil.util.tree.visitor.IVisitor;

/**
 * 
 * This class represents a node element in a tree.
 * @author MOULHAUD
 */

public abstract class TreeNodeElement extends ATreeElement implements ITreeNodeElement 
{
	// This is the list of children for the node
	protected ArrayList m_children=new ArrayList(); 
	
	/**
	 * Construct the TreeNodeElement with his name
	 * @param value
	 */
	public TreeNodeElement(Object value)
	{
		super(value);
	}
	
	/**
	 * Add a child for a TreeNodeElement
	 * @param element {@link ATreeElement}
	 */

	public void addChild(ATreeElement element){
		m_children.add(element);
	}
	
	/**
	 * Add a list of ATreeElement
	 * @param elementList
	 */
	public void addChilds(List elementList)	
	{
		if(elementList != null)
		{
			ATreeElement tempElt = null;
			for(int i = 0;i < elementList.size();i++)
			{
				tempElt = (ATreeElement)elementList.get(i);
				addChild(tempElt);
			}
		}
	}
	
	/**
	 * @param i : the child index
	 * @return : {@link ATreeElement}
	 */
	
	public ATreeElement getChildAt(int i){
		if(i<0 || i>m_children.size()-1) return null;
		return (ATreeElement)m_children.get(i);
	}
	

	
	/**
	 * To get the children list for the current node 
	 * @return {@link ArrayList} : the children list
	 */
	
	public ArrayList getChildren()
	{
		return m_children;
	}
	
	/** 
	 * @return int : the number of childs
	 */
	public int getChildsCount()
	{
		if(m_children==null) return 0;
		return m_children.size();
	}
	
	/**
	 * Receive a visitor, and call the visit method of it.
	 * @param visitor
	 */	
	public void accept(IVisitor visitor)
	{
		visitor.visit(this);
		
	}
	
	/**
	 * Remove the ATreeElement from the list of child
	 */
	public void remove(ATreeElement element)
	{
		if(m_children!=null && element!=null) m_children.remove(element);
	}
	
	/**
	 * Remove all element from the Tree Node Element
	 *
	 */
	public void removeAll()
	{
		m_children=new ArrayList();
	}
}
