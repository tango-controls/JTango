package fr.soleil.util.tree;

import fr.soleil.util.tree.visitor.IVisitor;

/**
 * 
 * Defines a node element that contains a single child
 * @author MOULHAUD
 */
public class SimpleTreeNodeElement extends ATreeElement
{
//	 The single child of the node
	private ATreeElement m_child=null; 
	
	
	/**
	 * Default constructor with the TreeNode name
	 * @param value Object
	 */
	public SimpleTreeNodeElement(Object value)
	{
		super(value);
	}
	
	/**
	 * Return the child
	 * @return {@link ATreeElement}
	 */
	
	public ATreeElement getM_child()
	{
		return m_child;
	}
	
	/**
	 * Set the child
	 * @param m_child {@link ATreeElement}
	 */
	
	public void setM_child(ATreeElement m_child)
	{
		this.m_child = m_child;
	}

	/**
	 * Receive a visitor in order to visit the node. 
	 * @param visitor
	 * @see ATreeElement
	 */
	public void accept(IVisitor visitor)
	{
		visitor.visit(this);
		
	}
	
	/**
	 * return true if the node has a child
	 * @return boolean
	 */
	public boolean hasChild(){
		return m_child!=null;
	}

	/**
	 * Remove the child
	 * @param child {@link ATreeElement}
	 */
	public void remove(ATreeElement child){
		if(child!=null){
			this.m_child=null;
		}
	}
	
	/**
	 * Duplicate the TreeNode Element without the child
	 */
	public ATreeElement duplicate()
	{
		SimpleTreeNodeElement newTreeNode = new SimpleTreeNodeElement(getM_value());
		return newTreeNode;
	}	

}
