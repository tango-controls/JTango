package fr.soleil.util.tree.visitor;


import fr.soleil.util.tree.ATreeElement;
import fr.soleil.util.tree.SimpleTreeNodeElement;
import fr.soleil.util.tree.TreeLeafElement;
import fr.soleil.util.tree.TreeNodeElement;

/**
 * Visitor Interface in order to visit a treeElement
 * @author MOULHAUD
 * 
 */

public interface IVisitor
{
	/**
	 * Visit TreeNodeElement
	 * @param node
	 * @see TreeNodeElement
	 */
	public void visit(TreeNodeElement node);
	
	/**
	 * Visit ATreeElement
	 * @param treeElt
	 * @see ATreeElement
	 */
	public void visit(ATreeElement treeElt);
	
	/**
	 * Visit TreeLeafElement
	 * @param element
	 * @see TreeLeafElement
	 */
	public void visit(TreeLeafElement element);
	
	/**
	 * Visit SimpleTreeNodeElement
	 * @param element
	 * @see SimpleTreeNodeElement
	 */
	public void visit(SimpleTreeNodeElement element);
}
