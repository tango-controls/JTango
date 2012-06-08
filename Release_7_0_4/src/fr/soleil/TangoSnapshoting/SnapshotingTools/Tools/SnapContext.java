//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapContext.
//						(Chinkumo Jean) - Jan 22, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.2  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.1.16.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.1  2005/01/26 15:35:37  chinkumo
// Ultimate synchronization before real sharing.
//
// Revision 1.1  2004/12/06 17:39:56  chinkumo
// First commit (new API architecture).
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================
package fr.soleil.TangoSnapshoting.SnapshotingTools.Tools;

import java.util.ArrayList;

/**
 * <p/>
 * <B>Description :</B><BR>
 * The <I>SnapContext</I> object describes the context of a snapshot.
 * To have a meaning, a snapshot must be accompanied with a certain number of informations.
 * This information is thus included in this object.<BR>
 * An <I>SnapContext</I> contains :
 * <ul>
 * <li> the context <I>author's name</I>,
 * <li> the context <I>name</I>
 * <li> the context <I>identifier</I>
 * <li> the context <I>creation date</I>
 * <li> the context <I>reason</I>
 * <li> the context <I>description</I>
 * <li> the <I>list of attributes</I> that are included in the context.
 * </ul>
 * <B>Date :<B> Jan 22, 2004
 * </p>
 *
 * @author Jean CHINKUMO - Synchrotron SOLEIL
 * @version 1.0
 * @see fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeHeavy
 */
public class SnapContext
{
	private String author_name = "";
	private String name = "";
	private int id = 0;
	private java.sql.Date creation_date = null;
	private String reason = "";
	private String description = "";
	private ArrayList attributeList = null;

	/**
	 * Default constructor
	 * Creates a new instance of SnapAttributeHeavy
	 *
	 * @see #SnapContext(String author_name, String name, java.sql.Date creation_date)
	 * @see #SnapContext(String author_name, String name, int id, java.sql.Date creation_date, String reason, String description)
	 */
	public SnapContext()
	{
	}

	/**
	 * This constructor takes several parameters as inputs.
	 *
	 * @param author_name   the context <I>author's name</I>
	 * @param name          the context <I>name</I>
	 * @param creation_date the context <I>creation date</I>
	 * @see #SnapContext()
	 * @see #SnapContext(String author_name, String name, java.sql.Date creation_date)
	 * @see #SnapContext(String[] argin)
	 */
	public SnapContext(String author_name , String name , java.sql.Date creation_date)
	{
		this.author_name = author_name;
		this.name = name;
		this.creation_date = creation_date;
	}

	/**
	 * This constructor takes several parameters as inputs.
	 *
	 * @param author_name   the context <I>author's name</I>
	 * @param name          the context <I>name</I>
	 * @param id            the context <I>identifier</I>
	 * @param creation_date the context <I>creation date</I>
	 * @param reason        the context <I>reason</I>
	 * @param description   the context <I>description</I>
	 * @see #SnapContext()
	 * @see #SnapContext(String author_name, String name, java.sql.Date creation_date)
	 * @see #SnapContext(String[] argin)
	 */
	public SnapContext(String author_name , String name , int id , java.sql.Date creation_date , String reason , String description)
	{
		this.author_name = author_name;
		this.name = name;
		this.id = id;
		this.creation_date = creation_date;
		this.reason = reason;
		this.description = description;
	}

	/**
	 * This constructor takes several parameters as inputs.
	 *
	 * @param author_name   the context <I>author's name</I>
	 * @param name          the context <I>name</I>
	 * @param id            the context <I>identifier</I>
	 * @param creation_date the context <I>creation date</I>
	 * @param reason        the context <I>reason</I>
	 * @param description   the context <I>description</I>
	 * @param attributeList the <I>list of attributes</I> that are included in the context.
	 * @see #SnapContext()
	 * @see #SnapContext(String author_name, String name, java.sql.Date creation_date)
	 * @see #SnapContext(String author_name, String name, int id, java.sql.Date creation_date, String reason, String description, ArrayList attributeList)
	 * @see #SnapContext(String[] argin)
	 */
	public SnapContext(String author_name , String name , int id , java.sql.Date creation_date , String reason , String description , ArrayList attributeList)
	{
		this.author_name = author_name;
		this.name = name;
		this.id = id;
		this.creation_date = creation_date;
		this.reason = reason;
		this.description = description;
		this.attributeList = attributeList;
	}

	/**
	 * This constructor builds an SnapContext from an array
	 *
	 * @param argin an array that contains the SnapContext's author's name, name, identifier,
	 *              creation date, reason, description and, the <I>list of attributes</I> that are included in the context.
	 */
	public SnapContext(String[] argin)
	{
		setAuthor_name(argin[ 0 ]);
		setName(argin[ 1 ]);
		setId(Integer.parseInt(argin[ 2 ]));
		setCreation_date(java.sql.Date.valueOf(argin[ 3 ]));
		setReason(argin[ 4 ]);
		setDescription(argin[ 5 ]);
		// Attribute list construction
		ArrayList attList = new ArrayList(32);
		for ( int i = 6 ; i < argin.length ; i++ )
		{
			attList.add(argin[ i ]);
		}
		attList.trimToSize();
		setAttributeList(attList);
	}

	/**
	 * Returns the SnapContext's <I>author's name</I>.
	 *
	 * @return the SnapContext's <I>author's name</I>.
	 */
	public String getAuthor_name()
	{
		return author_name;
	}

	/**
	 * Sets the context <I>author's name</I>.
	 *
	 * @param author_name the context <I>author's name</I>.
	 */
	public void setAuthor_name(String author_name)
	{
		this.author_name = author_name;
	}

	/**
	 * Returns the SnapContext's <I>name</I>.
	 *
	 * @return the SnapContext's <I>name</I>.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the context <I>name</I>.
	 *
	 * @param name the context <I>name</I>.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Returns the SnapContext's <I>identifier</I>.
	 *
	 * @return the SnapContext's <I>identifier</I>.
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Sets the context <I>identifier</I>.
	 *
	 * @param id the context <I>identifier</I>.
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Returns the SnapContext's <I>creation date</I>.
	 *
	 * @return the SnapContext's <I>creation date</I>.
	 */
	public java.sql.Date getCreation_date()
	{
		return creation_date;
	}

	/**
	 * Sets the context <I>creation date</I>.
	 *
	 * @param creation_date the context <I>creation date</I>.
	 */
	public void setCreation_date(java.sql.Date creation_date)
	{
		this.creation_date = creation_date;
	}

	/**
	 * Returns the SnapContext's <I>reason</I>.
	 *
	 * @return the SnapContext's <I>reason</I>.
	 */
	public String getReason()
	{
		return reason;
	}

	/**
	 * Sets the context <I>reason</I>.
	 *
	 * @param reason the context <I>reason</I>.
	 */
	public void setReason(String reason)
	{
		this.reason = reason;
	}

	/**
	 * Returns the SnapContext's <I>description</I>.
	 *
	 * @return the SnapContext's <I>description</I>.
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the context <I>description</I>.
	 *
	 * @param description the context <I>description</I>.
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Returns the <I>list of attributes</I> that are included in the context.
	 *
	 * @return the <I>list of attributes</I> that are included in the context.
	 */
	public ArrayList getAttributeList()
	{
		return attributeList;
	}

	/**
	 * Sets the SnapContext's <I>list of attributes</I> included in the context.
	 *
	 * @param attributeList the <I>list of attributes</I> that are included in the context.
	 */
	public void setAttributeList(ArrayList attributeList)
	{
		this.attributeList = attributeList;
	}

	/**
	 * Returns an array representation of the object <I>SnapContext</I>.
	 * In this order, array fields are :
	 * <ol>
	 * <li> the context <I>author's name</I>,
	 * <li> the context <I>name</I>
	 * <li> the context <I>identifier</I>
	 * <li> the context <I>creation date</I>
	 * <li> the context <I>reason</I>
	 * <li> the context <I>description</I>
	 * <li> The suite table describes the context's <I>list of attributes</I>
	 * </ol>
	 *
	 * @return an array representation of the object <I>SnapContext</I>.
	 */
	public String[] toArray()
	{
		String[] snapContext;
		snapContext = new String[ 6 + attributeList.size() ];

		snapContext[ 0 ] = author_name;
		snapContext[ 1 ] = name;
		snapContext[ 2 ] = Integer.toString(id);
		snapContext[ 3 ] = creation_date.toString();
		snapContext[ 4 ] = reason;
		snapContext[ 5 ] = description;

		for ( int i = 0 ; i < attributeList.size() ; i++ )
		{
			snapContext[ i + 6 ] = attributeList.get(i).toString();
		}

		return snapContext;
	}

	public String toString()
	{
		String snapString = new String("");
		snapString = "Context" + "\r\n" +
		             "Id : \t" + id + "\r\n" +
		             "Author name : \t" + author_name + "\r\n" +
		             "Context name : \t" + name + "\r\n" +
		             "Creation date : \t" + creation_date + "\r\n" +
		             "Reason : \t" + reason + "\r\n" +
		             "Description : \t" + description + "\r\n" +
		             "Attribute(s) : " + "\r\n";
		if ( attributeList != null )
		{
			for ( int i = 0 ; i < attributeList.size() ; i++ )
			{
				SnapAttributeLight snapAttributeLight = ( SnapAttributeLight ) attributeList.get(i);
				String attribute_name = ( String ) snapAttributeLight.getAttribute_complete_name();
				int attribute_type = ( int ) snapAttributeLight.getData_type();
				int attribute_format = ( int ) snapAttributeLight.getData_format();
				int attribute_writable = ( int ) snapAttributeLight.getWritable();
				snapString = snapString + "\t\t" + "[" + i + "]" + "\t" + attribute_name + "\r\n";
				snapString = snapString + "\t\t\t" + "type : \t" + attribute_type + "\r\n";
				snapString = snapString + "\t\t\t" + "format : \t" + attribute_format + "\r\n";
				snapString = snapString + "\t\t\t" + "writable : \t" + attribute_writable + "\r\n";
			}
		}
		else
		{
			snapString = snapString + "\t\t" + "NULL" + "\r\n";
		}
		return snapString;
	}

}
