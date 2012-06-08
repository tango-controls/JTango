//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  Condition.
//						(Garda Laure) - 1 juil. 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.3  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.2.2.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.2  2005/08/19 14:48:37  chinkumo
// no message
//
// Revision 1.1.4.3  2005/08/02 07:18:41  chinkumo
// Comments added.
//
// Revision 1.1.4.2  2005/08/01 13:51:48  chinkumo
// Classes added for the support of the new graphical application (Bensikin).
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================
package fr.soleil.TangoSnapshoting.SnapshotingTools.Tools;

import fr.esrf.Tango.ErrSeverity;

/**
 * Description :
 * A Condition object describes a search criterion for a request into the database.
 * A Condition contains :
 * a name of a table's field
 * an operator
 * a value.
 *
 * @author GARDA
 */
public class Condition
{
	private String column;		// Name of the table's field.
	private String operator;	// Operator of the condition.
	private String value;		// Value of the condition.

	/**
	 * This constructor takes three parameters as inputs.
	 *
	 * @param _column
	 * @param _operator
	 * @param _value
	 */
	public Condition(String _column , String _operator , String _value)
	{
		this.column = _column;
		this.operator = _operator;
		this.value = _value;
	}

	/**
	 * Returns the name of the table's field.
	 *
	 * @return Name of the table's field
	 */
	public String getColumn()
	{
		return column;
	}

	/**
	 * Sets the name of the table's field.
	 *
	 * @param column Name of the table's field
	 */
	public void setColumn(String column)
	{
		this.column = column;
	}

	/**
	 * Returns the operator of the condition.
	 *
	 * @return Operator of the condition
	 */
	public String getOperator()
	{
		return operator;
	}

	/**
	 * Sets the operator of the condition.
	 *
	 * @param operator Operator of the condition
	 */
	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	/**
	 * Returns the value of the condition.
	 *
	 * @return Value of the condition
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Sets the value of the condition.
	 *
	 * @param value Value of the condition
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * Returns the predicat of the SQL request for this condition.
	 *
	 * @return Predicat of the SQL request for this condition
	 * @throws SnapshotingException
	 */
	public String getPredicat() throws SnapshotingException
	{
		String predicat = "";
		// Casts the Condition's operator and value in a SQL predicat.
		if ( getOperator().equals(GlobalConst.OP_EQUALS) )
		{
			predicat = " = '" + getValue() + "'";
		}
		else if ( getOperator().equals(GlobalConst.OP_LOWER_THAN_STRICT) )
		{
			predicat = " < '" + getValue() + "'";
		}
		else if ( getOperator().equals(GlobalConst.OP_LOWER_THAN) )
		{
			predicat = " <= '" + getValue() + "'";
		}
		else if ( getOperator().equals(GlobalConst.OP_GREATER_THAN_STRICT) )
		{
			predicat = " > '" + getValue() + "'";
		}
		else if ( getOperator().equals(GlobalConst.OP_GREATER_THAN) )
		{
			predicat = " >= '" + getValue() + "'";
		}
		else if ( getOperator().equals(GlobalConst.OP_CONTAINS) )
		{
			predicat = " LIKE '%" + getValue() + "%'";
		}
		else if ( getOperator().equals(GlobalConst.OP_STARTS_WITH) )
		{
			predicat = " LIKE '" + getValue() + "%'";
		}
		else if ( getOperator().equals(GlobalConst.OP_ENDS_WITH) )
		{
			predicat = " LIKE '%" + getValue() + "'";
		}
		else
		{
			String message = "";
			message = GlobalConst.ERROR_SQL_OPERATOR;
			String reason = GlobalConst.ERROR_SQL_OPERATOR;
			String desc = "Failed while executing DataBaseApi.getPredicat() method...";
			throw new SnapshotingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName());
		}
		return predicat;
	}

	/**
	 * Returns the predicat of the SQL request for this condition in the case of an integer value.
	 *
	 * @return Predicat of the SQL request for this condition in the case of an integer value
	 * @throws SnapshotingException
	 */
	public String getPredicatInt() throws SnapshotingException
	{
		String predicat = "";
		// Casts the Condition's operator and value in a SQL predicat.
		if ( getOperator().equals(GlobalConst.OP_EQUALS) )
		{
			predicat = " = ?";
		}
		else if ( getOperator().equals(GlobalConst.OP_LOWER_THAN_STRICT) )
		{
			predicat = " < ?";
		}
		else if ( getOperator().equals(GlobalConst.OP_LOWER_THAN) )
		{
			predicat = " <= ?";
		}
		else if ( getOperator().equals(GlobalConst.OP_GREATER_THAN_STRICT) )
		{
			predicat = " > ?";
		}
		else if ( getOperator().equals(GlobalConst.OP_GREATER_THAN) )
		{
			predicat = " >= ?";
		}
		/*else if(getOperator().equals(ISnapManager.OP_CONTAINS))
		{
		    predicat = " LIKE '%" + getValue() + "%'";
		}
		else if(getOperator().equals(ISnapManager.OP_STARTS_WITH))
		{
		    predicat = " LIKE '" + getValue() + "%'";
		}
		else if(getOperator().equals(ISnapManager.OP_ENDS_WITH))
		{
		    predicat = " LIKE '%" + getValue() + "'";
		}*/
		else
		{
			String message = "";
			message = GlobalConst.ERROR_SQL_OPERATOR;
			String reason = GlobalConst.ERROR_SQL_OPERATOR;
			String desc = "Failed while executing DataBaseApi.getPredicatInt() method...";
			throw new SnapshotingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName());
		}
		return predicat;
	}

	/**
	 * Returns the predicat of the SQL request for this condition in the case of the full name of an attribute.
	 *
	 * @return Predicat of the SQL request for this condition in the case of the full name of an attribute
	 * @throws SnapshotingException
	 */
	public String getPredicatFullName() throws SnapshotingException
	{
		String predicat = "";
		// Casts the Condition's operator and value in a SQL predicat.
		if ( getValue().equals("*") )
		{
			predicat = "";
		}
		else if ( getValue().startsWith("*") && getValue().endsWith("*") )
		{
			predicat = " LIKE '%" + getValue().substring(1 , getValue().length() - 1) + "%'";
		}
		else if ( getValue().endsWith("*") )
		{
			predicat = " LIKE '" + getValue().substring(0 , getValue().length() - 1) + "%'";
		}
		else if ( getValue().startsWith("*") )
		{
			predicat = " LIKE '%" + getValue().substring(1) + "'";
		}
		else if ( !getValue().equals("") )
		{
			predicat = " = '" + getValue() + "'";
		}
		else
		{
			String message = "";
			message = GlobalConst.ERROR_SQL_OPERATOR;
			String reason = GlobalConst.ERROR_SQL_OPERATOR;
			String desc = "Failed while executing DataBaseApi.getPredicatFullName() method...";
			throw new SnapshotingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName());
		}
		return predicat;
	}

	/**
	 * Returns a String which represente the object Condition.
	 *
	 * @return String which represente the object Condition
	 */
	public String toString()
	{
		String condition_str = "";
		condition_str = getColumn() + " " + getOperator() + " " + getValue() + "\r\n";
		return condition_str;
	}

}
