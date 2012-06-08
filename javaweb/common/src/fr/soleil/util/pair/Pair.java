package fr.soleil.util.pair;

/**
 * This class allows to manage two objects
 * @author MARECHAL
 *
 */
public class Pair 
{
	private Object m_obj1, m_obj2;
	
	/**
	 * COnstructor
	 * @param obj1
	 * @param obj2
	 */
	public Pair(Object obj1, Object obj2)
	{
		m_obj1 = obj1;
		m_obj2 = obj2;
	}
	
	/**
	 * 
	 * @return first object
	 */
	public Object getObject1()
	{
		return m_obj1;
	}
	
	/**
	 * 
	 * @return second object
	 */
	public Object getObject2()
	{
		return m_obj2;
	}
	
}
