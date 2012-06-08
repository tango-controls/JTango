package fr.soleil.util.tree.visitor;



/**
 * Interface that a tree element has to implement in order to be visited 
 * @author MOULHAUD
 */
public interface IVisitable
{
	/**
	 * Receive a visitor, and call the visit method of it.
	 * @param visitor
	 * @see IVisitor
	 */
	public void accept(IVisitor visitor);

}
