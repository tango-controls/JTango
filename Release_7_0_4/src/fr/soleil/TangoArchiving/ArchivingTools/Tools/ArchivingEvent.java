//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description: The ArchivingEvent object describes an event for the filing service.
//				An ArchivingEvent thus contains :
//				- a name,
//				- a timestamp
//				- a value
//				- a property format  (scalar, spectrum, image)
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.3.12.2  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.3.12.1  2005/09/09 08:34:24  chinkumo
// To improve the collecting politic (see Collectors in H/TdbArchivers) this object was enhanced.
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
 * The <I>ArchivingEvent</I> object describes an event for the filing service.
 * An <I>ArchivingEvent</I> thus contains :
 * <ul>
 * <li> a <I>attribute_complete_name</I>,
 * <li> a <I>timestamp</I>
 * <li> a <I>value</I>
 * <li> a property <I>format</I>  (scalar, spectrum, image)
 * </ul>
 * </p>
 *
 * @author Jean CHINKUMO - Synchrotron SOLEIL
 * @version	$Revision$
 * @see fr.soleil.TangoArchiving.ArchivingTools.Tools.AttributeLight
 */
public abstract class ArchivingEvent
{
	// <li> a property <I>type</I> (DevShort, DevLong, DevDouble, DevString)
	// <li> a property <I>writable</I> (read, read_with_write, read_write)

	private String attribute_complete_name = "";
	private int data_type;
	private int data_format;
	private int writable;
	private long timestamp;
	private String table_name = "";
	private Object value;

	/**
	 * Default constructor
	 * Creates a new instance of ArchivingEvent
	 */
	public ArchivingEvent()
	{
	}


	/**
	 * Returns the ArchivingEvent's attribute_complete_name.
	 *
	 * @return the ArchivingEvent's attribute_complete_name.
	 * @see #setAttribute_complete_name
	 * @see #getTimeStamp
	 * @see #getValue
	 */
	public String getAttribute_complete_name()
	{
		return attribute_complete_name;
	}

	/**
	 * Sets the ArchivingEvent's attribute_complete_name.
	 *
	 * @param attribute_complete_name the ArchivingEvent's attribute_complete_name.
	 * @see #getAttribute_complete_name
	 * @see #setTimeStamp
	 * @see #setValue
	 */
	public void setAttribute_complete_name(String attribute_complete_name)
	{
		this.attribute_complete_name = attribute_complete_name;
	}

	/**
	 * @return
	 */
	public int getData_type()
	{
		return data_type;
	}

	/**
	 * @param data_type
	 */
	public void setData_type(int data_type)
	{
		this.data_type = data_type;
	}

	/**
	 * @return
	 */
	public int getData_format()
	{
		return data_format;
	}

	/**
	 * @param data_format
	 */
	public void setData_format(int data_format)
	{
		this.data_format = data_format;
	}

	/**
	 * @return
	 */
	public int getWritable()
	{
		return writable;
	}

	/**
	 * @param writable
	 */
	public void setWritable(int writable)
	{
		this.writable = writable;
	}


	/**
	 * Returns the ArchivingEvent's timestamp.
	 *
	 * @return the ArchivingEvent's timestamp.
	 * @see #getAttribute_complete_name
	 * @see #setTimeStamp
	 * @see #getValue
	 */
	public long getTimeStamp()
	{
		return timestamp;
	}

	/**
	 * Sets the ArchivingEvent's timestamp.
	 *
	 * @param timestamp the ArchivingEvent's timestamp.
	 * @see #setAttribute_complete_name
	 * @see #getTimeStamp
	 * @see #setValue
	 */
	public void setTimeStamp(long timestamp)
	{
		this.timestamp = timestamp;
	}

	/**
	 * @return
	 */
	public String getTable_name()
	{
		return table_name;
	}

	/**
	 * @param table_name
	 */
	public void setTable_name(String table_name)
	{
		this.table_name = table_name;
	}

	/**
	 * Returns the ArchivingEvent's value. Let us note here that what returned is of type "Object". A cast operation is thus necessary.
	 *
	 * @see #getAttribute_complete_name
	 * @see #getTimeStamp
	 * @see #setValue
	 */
	public Object getValue()
	{
		return value;
	}

	/**
	 * Sets the ArchivingEvent's value. Let us note here that the given value has to be of type "Object". A cast operation can thus be necessary.
	 *
	 * @param value ArchivingEvent's value
	 * @see #setAttribute_complete_name
	 * @see #setTimeStamp
	 * @see #setValue
	 */
	public void setValue(Object value)
	{
		this.value = value;
	}


	/**
	 * Returns an array representation of the object <I>ArchivingEvent</I>.
	 *
	 * @return an array representation of the object <I>ArchivingEvent</I>.
	 */
	public abstract String[] toArray();


	/**
	 * Returns a string representation of the object <I>ArchivingEvent</I>.
	 *
	 * @return a string representation of the object <I>ArchivingEvent</I>.
	 */
	public abstract String toString();
}

