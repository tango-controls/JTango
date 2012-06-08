//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  Criterions.
//						(Garda Laure) - 1 juil. 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.8  2006/05/30 13:00:40  ounsy
// small bug correction
//
// Revision 1.7  2006/05/16 13:04:19  ounsy
// added a getConditionsHT() method
//
// Revision 1.6  2006/05/04 14:34:12  ounsy
// minor changes
//
// Revision 1.5  2006/03/14 12:36:47  ounsy
// removed useless logs
//
// Revision 1.4  2006/02/17 09:32:35  chinkumo
// Since the structure and the name of some SNAPSHOT database's table changed, this was reported here.
//
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
// Revision 1.1.4.2  2005/08/01 13:51:49  chinkumo
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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import fr.esrf.Tango.ErrSeverity;

/**
 * Description :
 * A Criterion object describes a set of search criteria for a request into the database
 * A Criterion contains :
 * a set of Condition objects.
 *
 * @author GARDA
 */
public class Criterions
{
	private Hashtable conditionsHT; // set of Condition objects

	/**
	 * Default constructor.
	 */
	public Criterions()
	{
		conditionsHT = new Hashtable();
	}

	/**
	 * This constructor takes one parameter as inputs.
	 *
	 * @param conditions a set of Conditions
	 */
	public Criterions(Condition[] conditions)
	{
		conditionsHT = new Hashtable();
		// Sets the Conditions into the Criterion.
		if ( conditions != null )
		{
			int nbOfConditions = conditions.length;
			for ( int i = 0 ; i < nbOfConditions ; i++ )
			{
				Vector currentColumnConditionsList;
				String columnName = conditions[ i ].getColumn();
				// The Conditions are referencedby their table's field (columnName).
				if ( conditionsHT.containsKey(columnName) )
				{
					currentColumnConditionsList = ( Vector ) conditionsHT.get(columnName);
				}
				else
				{
					currentColumnConditionsList = new Vector();
				}

				currentColumnConditionsList.add(conditions[ i ]);
				conditionsHT.put(columnName , currentColumnConditionsList);
			}
		}
	}

	/**
	 * Adds a Condition into the Criterion.
	 *
	 * @param condition A Condition
	 */
	public void addCondition(Condition condition)
	{
		if ( condition == null )
		{
			return;
		}

		String columnName = condition.getColumn();
		Vector currentColumnConditionsList;

		if ( !conditionsHT.containsKey(columnName) )
		{
			currentColumnConditionsList = new Vector();
		}
		else
		{
			currentColumnConditionsList = ( Vector ) conditionsHT.get(columnName);
		}

		currentColumnConditionsList.add(condition);
		conditionsHT.put(columnName , currentColumnConditionsList);
	}

	/**
	 * Returns array of all Condition objects (of this Criterion) for the given table's field
	 *
	 * @param columnName
	 * @return array of all Condition objects for the given table's field
	 */
	public Condition[] getConditions(String columnName)
	{
        if ( columnName == null )
		{
			return null;
		}
        if ( conditionsHT == null )
        {
            return null;
        }
		if ( !conditionsHT.containsKey(columnName) )
		{
            return null;
		}

		Vector columnConditionsList = ( Vector ) conditionsHT.get(columnName);
		Enumeration enumer = columnConditionsList.elements();

        int nbOfConditions = columnConditionsList.size();
		int i = 0;
		Condition[] ret = new Condition[ nbOfConditions ];

		while ( enumer.hasMoreElements() )
		{
			Condition nextCondition = ( Condition ) enumer.nextElement();
			ret[ i ] = nextCondition;
			i++;
		}

		return ret;

	}

	/**
	 * Returns the SQL clause describes by the Criterion in the case of a field of the ContextTable.
	 *
	 * @return SQL clause describes by the Criterion in the case of a field of the ContextTable
	 * @throws SnapshotingException
	 */
	public String getContextClause() throws SnapshotingException
	{
		String clause = "";
		String table = GlobalConst.TABS[ 1 ];
		String[] field = GlobalConst.TAB_CONTEXT;
		int count = 0;
		// Cas de l'ID_context.
		Condition[] conditions = getConditions(field[ 0 ]);
		if ( conditions != null )
		{
			// Une seule condition sur l'id_context.
			if ( conditions.length == 1 )
			{
				// Mise en forme du predicat de la requete SQL.
				String predicat = conditions[ 0 ].getPredicatInt();

				// Mise en forme de la requete SQL.
				if ( count == 0 )
				{
					clause = clause + " WHERE " + table + "." + field[ 0 ] + predicat;
					count++;
				}
				else
				{
					clause = clause + " AND " + table + "." + field[ 0 ] + predicat;
				}
			}
			else
			{
				String message = "";
				message = GlobalConst.ERROR_SQL_OPERATOR;
				String reason = GlobalConst.ERROR_SQL_OPERATOR;
				String desc = "Failed while executing Criterions.getSnapshotClause() method...";
				throw new SnapshotingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName());
			}
		}

		// Pour les autres champs.
		for ( int i = 1 ; i < field.length ; i++ ) // Parcours de tous les champs de la table des contextes, sauf l'ID_context.
		{
			conditions = getConditions(field[ i ]);
			if ( conditions != null )
			{
				for ( int j = 0 ; j < conditions.length ; j++ )
				{
					// Mise en forme du predicat de la requete SQL.
					String predicat = conditions[ j ].getPredicat();

					// Mise en forme de la requete SQL.
					if ( count == 0 )
					{
						clause = clause + " WHERE " + table + "." + field[ i ] + predicat;
						count++;
					}
					else
					{
						clause = clause + " AND " + table + "." + field[ i ] + predicat;
					}
				}
			}

		}
		return clause;

	}

	/**
	 * Returns the SQL clause describes by the Criterion in the case of a field of the SnapshotTable.
	 *
	 * @return SQL clause describes by the Criterion in the case of a field of the SnashotTable
	 * @throws SnapshotingException
	 */
	public String getSnapshotClause() throws SnapshotingException
	{
		String clause = "";
		String table = GlobalConst.TABS[ 3 ];
		String[] field = GlobalConst.TAB_SNAP;
		int count = 0;
		// Cas de l'id_snap.
		Condition[] conditions = getConditions(field[ 0 ]);
		if ( conditions != null )
		{
			//System.out.println("conditions.length : " + conditions.length);
			// Une seule condition sur l'id_snap.
			if ( conditions.length == 1 )
			{
				// Mise en forme du predicat de la requete SQL.
				String predicat = conditions[ 0 ].getPredicatInt();

				// Mise en forme de la requete SQL.
				if ( count == 0 )
				{
					clause = clause + " WHERE " + table + "." + field[ 0 ] + predicat;
					count++;
				}
				else
				{
					clause = clause + " AND " + table + "." + field[ 0 ] + predicat;
				}
			}
			else
			{
				String message = "";
				message = GlobalConst.ERROR_SQL_OPERATOR;
				String reason = GlobalConst.ERROR_SQL_OPERATOR;
				String desc = "Failed while executing Criterions.getSnapshotClause() method...";
				throw new SnapshotingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName());
			}

		}
		// Cas de l'ID_context.
		conditions = getConditions(field[ 1 ]);
		if ( conditions != null )
		{
			// Une seule condition sur l'id_context.
			if ( conditions.length == 1 )
			{
				// Mise en forme du predicat de la requete SQL.
				String predicat = conditions[ 0 ].getPredicatInt();

				// Mise en forme de la requete SQL.
				if ( count == 0 )
				{
					clause = clause + " WHERE " + table + "." + field[ 1 ] + predicat;
					count++;
				}
				else
				{
					clause = clause + " AND " + table + "." + field[ 1 ] + predicat;
				}
			}
			else
			{
				String message = "";
				message = GlobalConst.ERROR_SQL_OPERATOR;
				String reason = GlobalConst.ERROR_SQL_OPERATOR;
				String desc = "Failed while executing Criterions.getSnapshotClause() method...";
				throw new SnapshotingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName());
			}

		}

		for ( int i = 2 ; i < field.length ; i++ ) // Parcours de tous les champs de la table des contextes.
		{
			conditions = getConditions(field[ i ]);
			if ( conditions != null )
			{
				for ( int j = 0 ; j < conditions.length ; j++ )
				{
					// Mise en forme du predicat de la requete SQL.
					String predicat = conditions[ j ].getPredicat();
					// Mise en forme de la requete SQL.
					if ( count == 0 )
					{
						clause = clause + " WHERE " + table + "." + field[ i ] + predicat;
						count++;
					}
					else
					{
						clause = clause + " AND " + table + "." + field[ i ] + predicat;
					}
				}
			}
		}
		//System.out.println("Criterions.getSnapshotClause/clause : " + clause);
		return clause;
	}

	/**
	 * Returns the id of a context of the SnapTable.
	 *
	 * @return id of a context of the SnapTable
	 * @throws SnapshotingException
	 */
	public int getIdContextSnapTable() throws SnapshotingException
	{
		int id_context = -1; // value if no condition on this id.

		Condition[] id_condition = getConditions(GlobalConst.TAB_SNAP[ 1 ]);
		if ( id_condition != null )
		{
			id_context = Integer.parseInt(id_condition[ 0 ].getValue());
		}
		return id_context;
	}

	/**
	 * Returns the id of a context.
	 *
	 * @return the id of a context
	 * @throws SnapshotingException
	 */
	public int getIdContextContextTable() throws SnapshotingException
	{
		int id_context = -1; // value if no condition on this id.

		Condition[] id_condition = getConditions(GlobalConst.TAB_CONTEXT[ 0 ]);
		if ( id_condition != null )
		{
			id_context = Integer.parseInt(id_condition[ 0 ].getValue());
		}
		return id_context;
	}

	/**
	 * Returns  the id of a snapshot.
	 *
	 * @return the id of a snapshot
	 * @throws SnapshotingException
	 */
	public int getIdSnap() throws SnapshotingException
	{
		int id_snap = -1; // value if no condition on this id.

		Condition[] id_condition = getConditions(GlobalConst.TAB_SNAP[ 0 ]);
		if ( id_condition != null )
		{
			id_snap = Integer.parseInt(id_condition[ 0 ].getValue());
		}
		return id_snap;
	}

	/**
	 * Returns the SQL clause describes by the Criterion in the case of a field of the AttribteTable.
	 *
	 * @return SQL clause describes by the Criterion in the case of a field of the AttributeTable
	 * @throws SnapshotingException
	 */
	public String getAttributeClause() throws SnapshotingException
	{
		String clause = "";
		String table = GlobalConst.TABS[ 0 ];
		String[] field = GlobalConst.TAB_DEF;
		int count = 0;
		for ( int i = 4 ; i < 8 ; i++ ) // Parcours des champs de la table des definitions.
		{
			Condition[] conditions = getConditions(field[ i ]);
			if ( conditions != null )
			{
				for ( int j = 0 ; j < conditions.length ; j++ )
				{
					// Mise en forme du predicat de la requete SQL.
					String predicat = conditions[ j ].getPredicatFullName();
					// Mise en forme de la requete SQL.
					if ( !predicat.equals("") )
					{
						if ( count == 0 )
						{
							clause = clause + " WHERE " + table + "." + field[ i ] + predicat;
							count++;
						}
						else
						{
							clause = clause + " AND " + table + "." + field[ i ] + predicat;
						}
					}
				}
			}

		}
		return clause;
	}

	/**
	 * Returns a String which represente the object Criterion.
	 *
	 * @return String which represente the object Criterion
	 */
	public String toString()
	{
		String criterions_str = "";
		if ( conditionsHT.isEmpty() )
		{
			return null;
		}

		Enumeration key = conditionsHT.keys();

		while ( key.hasMoreElements() )
		{
			Vector columnConditionsList = ( Vector ) conditionsHT.get(key.nextElement());

			Enumeration enumer = columnConditionsList.elements();

			while ( enumer.hasMoreElements() )
			{
				Condition nextCondition = ( Condition ) enumer.nextElement();
				criterions_str = criterions_str + nextCondition.toString();
			}
		}
		return criterions_str;

	}

	public Hashtable getConditionsHT ()
    {
	    return this.conditionsHT;    
    }
}
