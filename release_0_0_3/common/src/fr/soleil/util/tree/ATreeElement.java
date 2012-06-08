package fr.soleil.util.tree;

import fr.soleil.util.tree.visitor.IVisitable;



/**
 * This class represents an abstract element of a tree : node or leaf
 * @author MOULHAUD
 */

public abstract class ATreeElement implements IVisitable
{
//	 The value for a tree element. All the derived classes inherit of the field
	protected Object m_value; 
	public Boolean bTest = new Boolean(false);
	
	/**
	 * Default constructor with the name of the node
	 * @param value {@link Object}
	 * 
	 */
	public ATreeElement(Object value){
		this.m_value=value;
	}

	/**
	 * return the node name
	 * @return {@link Object}
	 */
	public Object getM_value()
	{
		return m_value;
	}

	/**
	 * change the node name
	 * @param m_value {@link Object} : the new value
	 */
	public void setM_value(Object m_value)
	{
		this.m_value = m_value;
	}
	
	/**
	 * Duplicates the node element
	 * @return ATreeElement
	 */
	public abstract ATreeElement duplicate();

}
