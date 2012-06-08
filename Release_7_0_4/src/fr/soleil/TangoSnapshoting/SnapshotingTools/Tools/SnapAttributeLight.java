//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapAttributeLight.
//						(Chinkumo Jean) - Mar 24, 2004
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

/**
 * <p/>
 * <B>Description :</B><BR>
 * The <I>SnapAttributeLight</I> object describes a TANGO attribute about to be snapshooted.
 * It thus contains all available informations characterizing it.
 * An <I>SnapAttributeLight</I> contains :
 * <ul>
 * <li> the <I>attribute complete name</I>,
 * <li> an <I>attribute type property</I> (DevShort/DevLong/DevDouble),
 * <li> an <I>attribute data format property</I> (SCALAR/SPECTRUM/IMAGE),
 * <li> an <I>attribute writable property</I> (READ/READ_WRITE/READ_WITH_WRITE),
 * </ul>
 * </p>
 *
 * @author Jean CHINKUMO - Synchrotron SOLEIL
 * @version 1.0
 * @see SnapAttributeLight
 * @see SnapContext
 */
public class SnapAttributeLight
{
	private String attribute_complete_name = "";
	private int attribute_id = 0;
	private int data_type = 0;
	private int data_format = 0;
	private int writable = 0;

	/**
	 * Default constructor
	 * Creates a new instance of SnapAttributeLight
	 *
	 * @see #SnapAttributeLight(String)
	 * @see #SnapAttributeLight(String[])
	 */
	public SnapAttributeLight()
	{
	}

	/**
	 * This constructor takes one parameter as inputs.
	 *
	 * @param attribute_complete_name the attribute's complete name
	 * @see #SnapAttributeLight()
	 * @see #SnapAttributeLight(String[])
	 */
	public SnapAttributeLight(String attribute_complete_name)
	{
		this.attribute_complete_name = attribute_complete_name;
	}

	/**
	 * This constructor builds an SnapAttributeLight from an array
	 *
	 * @param argin an array that contains the SnapAttributeLight's full name, data type parameter, data format parameter, writable parameter.
	 */
	public SnapAttributeLight(String[] argin)
	{
		setAttribute_complete_name(argin[ 0 ]);  // **************** The whole attribute name (device_name + attribute_name)
		setAttribute_id(Integer.parseInt(argin[ 1 ])); // **************** Attribute identifier used for the database SNAP
		setData_type(Integer.parseInt(argin[ 2 ]));  // **************** Attribute data type
		setData_format(Integer.parseInt(argin[ 3 ]));  // **************** Attribute data format
		setWritable(Integer.parseInt(argin[ 4 ]));  // **************** Attribute read/write type
	}

	/**
	 * Returns the SnapAttributeLight's name.
	 *
	 * @return the SnapAttributeLight's name.
	 * @see #setAttribute_complete_name
	 */
	public String getAttribute_complete_name()
	{
		return attribute_complete_name;
	}

	/**
	 * Sets the SnapAttributeLight's name.
	 *
	 * @param attribute_complete_name the SnapAttributeLight's name.
	 * @see #getAttribute_complete_name
	 */
	public void setAttribute_complete_name(String attribute_complete_name)
	{
		this.attribute_complete_name = attribute_complete_name;
	}

	/**
	 * Returns the SnapAttributeLight's identifier used for the database SNAP.
	 *
	 * @return the SnapAttributeLight's identifier used for the database SNAP.
	 */
	public int getAttribute_id()
	{
		return attribute_id;
	}

	/**
	 * Sets the SnapAttributeLight's identifier used for the database SNAP.
	 *
	 * @param attribute_id identifier used for the database SNAP.
	 */
	public void setAttribute_id(int attribute_id)
	{
		this.attribute_id = attribute_id;
	}

	/**
	 * Returns the SnapAttributeLight's <I>type property</I> (DevShort/DevLong/DevDouble)
	 *
	 * @return the SnapAttributeLight's <I>type property</I> (DevShort/DevLong/DevDouble)
	 */
	public int getData_type()
	{
		return data_type;
	}

	/**
	 * Sets the SnapAttributeLight's <I>type property</I> (DevShort/DevLong/DevDouble)
	 *
	 * @param data_type the SnapAttributeLight's <I>type property</I> (DevShort/DevLong/DevDouble)
	 */
	public void setData_type(int data_type)
	{
		this.data_type = data_type;
	}

	/**
	 * Returns the SnapAttributeLight's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 *
	 * @return the SnapAttributeLight's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 */
	public int getData_format()
	{
		return data_format;
	}

	/**
	 * Sets the SnapAttributeLight's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 *
	 * @param data_format the SnapAttributeLight's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 */
	public void setData_format(int data_format)
	{
		this.data_format = data_format;
	}

	/**
	 * Returns the SnapAttributeLight's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 *
	 * @return the SnapAttributeLight's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 */
	public int getWritable()
	{
		return writable;
	}

	/**
	 * Sets the SnapAttributeLight's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 *
	 * @param writable the SnapAttributeLight's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 */
	public void setWritable(int writable)
	{
		this.writable = writable;
	}


	/**
	 * Returns an array representation of the object <I>SnapAttributeLight</I>.
	 * In this order, array fields are :
	 * <ol>
	 * <li> the <I>attribute complete name</I> (device_name + attribute_name),
	 * <li> the <I>attribute type property</I> (DevShort/DevLong/DevDouble),
	 * <li> the <I>attribute data format property</I> (SCALAR/SPECTRUM/IMAGE),
	 * <li> the <I>attribute writable property</I> (READ/READ_WRITE/READ_WITH_WRITE),
	 * </ol>
	 *
	 * @return an array representation of the object <I>SnapAttributeLight</I>.
	 */
	public String[] toArray()
	{
		String[] snapLightAtt;
		snapLightAtt = new String[ 5 ];

		snapLightAtt[ 0 ] = attribute_complete_name;
		snapLightAtt[ 1 ] = Integer.toString(attribute_id);
		snapLightAtt[ 2 ] = Integer.toString(data_type);
		snapLightAtt[ 3 ] = Integer.toString(data_format);
		snapLightAtt[ 4 ] = Integer.toString(writable);

		return snapLightAtt;
	}

	public String toString()
	{
		String snapString = new String("");
		snapString = "Attribut : " + getAttribute_complete_name() + "\r\n" +
		             "\t" + "Attribute Id : \t" + getAttribute_id() + "\r\n" +
		             "\t" + "data_type : \t" + getData_type() + "\r\n" +
		             "\t" + "data_format : \t" + getData_format() + "\r\n" +
		             "\t" + "writable : \t" + getWritable() + "\r\n";
		return snapString;
	}
}

