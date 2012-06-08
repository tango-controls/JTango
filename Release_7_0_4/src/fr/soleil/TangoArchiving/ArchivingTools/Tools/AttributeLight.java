//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description: The AttributeLight object describes a TANGO attribute for the filing service, and containing the minimum of information characterizing it.
//				An AttributeLight thus contains :
//				- a name,
//				- a target (TANGO device in charge of inserting datum into the database)
//				- a collector (TANGO device in charge of collecting events)
//				- a mode of filling (Constraints applied to the filing).
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.3.12.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.3  2005/06/14 10:12:11  chinkumo
// Branch (tangORBarchiving_1_0_1-branch_0)  and HEAD merged.
//
// Revision 1.2.4.1  2005/04/29 18:35:03  chinkumo
// Changes made to remove deprecated objetcs (ListeAttributs, ListeDevices, Device)
//
// Revision 1.2  2005/01/26 15:35:37  chinkumo
// Ultimate synchronization before real sharing.
//
// Revision 1.1  2004/12/06 17:39:56  chinkumo
// First commit (new API architecture).
//
//
// copyleft :   Synchrotron SOLEIL
//			    L'Orme des Merisiers
//			    Saint-Aubin - BP 48
//			    91192 GIF-sur-YVETTE CEDEX
//              FRANCE
//
//+============================================================================

package fr.soleil.TangoArchiving.ArchivingTools.Tools;


/**
 * <p/>
 * <B>Description :</B><BR>
 * The <I>AttributeLight</I> object describes a TANGO attribute for the filing service, and containing the minimum of information characterizing it.
 * An <I>AttributeLight</I> thus contains :
 * <ul>
 * <li> a <I>name</I>,
 * <li> a <I>target</I> (TANGO device in charge of inserting datum into the database)
 * <li> a <I>collector</I> (TANGO device in charge of collecting events)
 * <li> a <I>mode of filling</I> (Constraints applied to the filing).
 * </ul>
 * </p>
 *
 * @author Jean CHINKUMO - Synchrotron SOLEIL
 * @version	$Revision$
 * @see fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingEvent
 */
public class AttributeLight
{
	private String attribute_complete_name = null;
	private int data_type = 0;
	private int data_format = 0;
	private int writable = 0;

	public static int HDB_ATTRIBUTE_LIGHT_LENGTH = 4;

	/**
	 * Default constructor
	 * Creates a new instance of AttributeLight
	 *
	 * @see #AttributeLight(java.lang.String)
	 */
	public AttributeLight()
	{
		attribute_complete_name = null;
	}

	/**
	 * This constructor takes one parameter as inputs.
	 *
	 * @param n the attribute's name
	 * @see #AttributeLight()
	 */
	public AttributeLight(String n)
	{
		attribute_complete_name = n;
	}


	/**
	 * This constructor builds an AttributeLight from an array
	 *
	 * @param att_tab an array that contains the AttributeLight's name, the name of the device in charge and the mode of filling informations.
	 */
	public AttributeLight(String[] att_tab)
	{
		setAttribute_complete_name(att_tab[ 0 ]);
		setData_type(Integer.parseInt(att_tab[ 1 ]));
		setData_format(Integer.parseInt(att_tab[ 2 ]));
		setWritable(Integer.parseInt(att_tab[ 3 ]));
	}

	/*public static int getAttributeLight_length() {
	    return attributeLight_length;
	}*/

	/**
	 * Sets the AttributeLight's name.
	 *
	 * @param n the AttributeLight's name.
	 * @see #getAttribute_complete_name
	 */
	public void setAttribute_complete_name(String n)
	{
		this.attribute_complete_name = n;
	}

	/**
	 * Returns the AttributeLight's <I>type property</I> (DevShort/DevLong/DevDouble)
	 *
	 * @return the AttributeLight's <I>type property</I> (DevShort/DevLong/DevDouble)
	 */
	public int getData_type()
	{
		return this.data_type;
	}

	/**
	 * Sets the AttributeLight's <I>type property</I> (DevShort/DevLong/DevDouble)
	 *
	 * @param data_type the AttributeLight's <I>type property</I> (DevShort/DevLong/DevDouble)
	 */
	public void setData_type(int data_type)
	{
		this.data_type = data_type;
	}

	/**
	 * Returns the AttributeLight's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 *
	 * @return the AttributeLight's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 */
	public int getData_format()
	{
		return this.data_format;
	}

	/**
	 * Sets the AttributeLight's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 *
	 * @param data_format the AttributeLight's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 */
	public void setData_format(int data_format)
	{
		this.data_format = data_format;
	}

	/**
	 * Returns the AttributeLight's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 *
	 * @return the AttributeLight's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 */
	public int getWritable()
	{
		return this.writable;
	}

	/**
	 * Sets the AttributeLight's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 *
	 * @param writable the AttributeLight's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 */
	public void setWritable(int writable)
	{
		this.writable = writable;
	}

	/**
	 * Returns the AttributeLight's name.
	 *
	 * @return the AttributeLight's name.
	 * @see #setAttribute_complete_name
	 */
	public String getAttribute_complete_name()
	{
		return this.attribute_complete_name;
	}

	/**
	 * Returns a string representation of the object <I>AttributeLight</I>.
	 *
	 * @return a string representation of the object <I>AttributeLight</I>.
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer("");
		buf.append("\t Name ......................." + getAttribute_complete_name() + "\n");
		buf.append("\t DataType : ................" + getData_type() + "\n");
		buf.append("\t DataFormat : ............" + getData_format() + "\n");
		buf.append("\t Writable : ................." + getWritable() + "\n");

		return buf.toString();
	}

	public String[] toArray()
	{
		String[] att_tab;
		att_tab = new String[ 4 ];
		att_tab[ 0 ] = getAttribute_complete_name();
		att_tab[ 1 ] = Integer.toString(getData_type());
		att_tab[ 2 ] = Integer.toString(getData_format());
		att_tab[ 3 ] = Integer.toString(getWritable());

		return att_tab;
	}

	public boolean equals(Object o)
	{
		if ( this == o ) return true;
		if ( !( o instanceof AttributeLight ) ) return false;

		final AttributeLight attributeLight = ( AttributeLight ) o;

		if ( data_format != attributeLight.data_format ) return false;
		if ( data_type != attributeLight.data_type ) return false;
		if ( writable != attributeLight.writable ) return false;
		if ( !attribute_complete_name.equals(attributeLight.attribute_complete_name) ) return false;

		return true;
	}

	public int hashCode()
	{
		int result;
		result = attribute_complete_name.hashCode();
		result = 29 * result + data_type;
		result = 29 * result + data_format;
		result = 29 * result + writable;

		return result;
	}
}