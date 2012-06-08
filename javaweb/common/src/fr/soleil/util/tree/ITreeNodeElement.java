package fr.soleil.util.tree;

import java.util.ArrayList;

/** 
 * 
 * This interface defines all the methods for a tree node element
 * All the sub trees have to implement this interface
 * @author MOULHAUD
 */

public interface ITreeNodeElement
{
	/**
	 * Add a child for a TreeNodeElement
	 * @param element {@link ATreeElement}
	 */
	public void addChild(ATreeElement element);

	/** 
	 * @param i : the child index
	 * @return : {@link ATreeElement}
	 */
	
	public ATreeElement getChildAt(int i);
	
	/**
	 * To get the children list for the current node 
	 * @return {@link ArrayList} : the children list
	 */
	public ArrayList getChildren();
	
	/** 
	 * @return int : the number of childs
	 */
	public int getChildsCount();
	
	/**
	 * Removes a tree element from the tree
	 * @param element ATreeElement
	 */
	public void remove(ATreeElement element);
	
}
