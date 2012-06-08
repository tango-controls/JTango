package fr.soleil.util.tree;

/**
 * This class represents an operand filter.
 * An operand filter elements contains a value, a type (String, int, Float..). We can specify the float precision used if the value has a type float
 * @author MOULHAUD 
 */

public class OperandFilter
{
//	 The value of the operand
	private Object m_value; 
//	 The type : String, int, number ...
	private Object Type; 
//	 The float precision
 
	private int m_iPowPrecision=0; // Indicates the pow of the precision. Ex :3 for 10 exp -3 / 0 indicates no precision 
	
	/**
	 * The default constructor
	 * @param m_value : Operand value
	 * @param type : the operand type
	 */
	public OperandFilter(Object m_value, Object type)
	{
		super();
		setM_value(m_value);
		Type = type;
	}
	
	/**
	 * Return the value
	 * @return Object
	 */
	public Object getM_value()
	{
		return m_value;
	}

	/**
	 * Change the object value 
	 * @param m_value
	 */
	public void setM_value(Object m_value)
	{
		if(m_value== null)
			this.m_value = m_value;
		
		if(m_value instanceof String){
			String strTempValue = (String)m_value;
			if(strTempValue.length()>0){
				if(strTempValue.charAt(0) == '\'')
					strTempValue = strTempValue.substring(1);
				if(strTempValue.charAt(strTempValue.length()-1)== '\'')
					strTempValue = strTempValue.substring(0,strTempValue.length()-1);
			}
			this.m_value = strTempValue;
		}
		else
			this.m_value = m_value;
	}

	/**
	 * Return the type of the value
	 * @return Object
	 */
	public Object getType()
	{
		return Type;
	}

	/**
	 * Change the type of the value
	 * @param type
	 */
	public void setType(Object type)
	{
		Type = type;
	}

	/**
	 * duplicate the value inside the operand filter
	 * @return {@link OperandFilter}
	 */
	public OperandFilter duplicate()
	{
		// return the operand filter copy
		return new OperandFilter(getM_value(), getType());
	}
	
	/**
	 * Get the pow of the precision
	 * @return int
	 */
	public int getPowPrecision()
	{
		return m_iPowPrecision;
	}

	/**
	 * Set the pow of the precision
	 * @param powPrecision int
	 */
	public void setPowPrecision(int powPrecision)
	{
		m_iPowPrecision = powPrecision;
	}
	
}
