package fr.soleil.util.tree;

import fr.soleil.util.tree.visitor.IVisitor;

/** 
 * This class represents a leaf element in a tree.
 * @author MOULHAUD
 *
 */

public abstract class TreeLeafElement extends ATreeElement
{
	/**
	 * Construct the leaf element with the leaf value 
	 * @param value
	 */
	public TreeLeafElement(Object value)
	{
		super(value);
		
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
	 * Duplicate the leaf element
	 * @return {@link ATreeElement}
	 */
	public abstract ATreeElement duplicate();
}
