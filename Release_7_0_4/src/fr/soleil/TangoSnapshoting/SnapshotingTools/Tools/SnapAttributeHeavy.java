//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapAttributeHeavy.
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
 * The <I>SnapAttributeHeavy</I> object describes a TANGO attribute about to be registered into the SnapShot filing service.
 * It thus contains all available informations characterizing it.
 * An <I>SnapAttributeHeavy</I> contains :
 * <ul>
 * <li> a <I>registration time</I>,
 * <li> a <I>device name</I> (The device to which it belongs to),
 * <li> a <I>domain</I> (The domain to which it is associated),
 * <li> a <I>family</I> (The family to which it is associated),
 * <li> a <I>member</I> (The member to which it is associated),
 * <li> a <I>name</I> (its personal name, without the device part),
 * <li> an <I>attribute type property</I> (DevShort/DevLong/DevDouble),
 * <li> an <I>attribute data format property</I> (SCALAR/SPECTRUM/IMAGE),
 * <li> an <I>attribute writable property</I> (READ/READ_WRITE/READ_WITH_WRITE),
 * <li> the <I>max_dim_x parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format)
 * <li> the <I>max_dim_y parameter</I> (maximum size for attributes of the IMAGE data format)
 * <li> a <I>level parameter</I> (This parameter is only an help for graphical application - this parameter defines an operator mode or an expert mode).
 * <li> a <I>control system reference</I> (Control system to which belongs the attribute)
 * <li> an <I>archivable property</I> that precises whether the attribute is "on-run-only" archivable, or if it is "always" archivable.
 * <li> a <I>substitute parameter</I> (Name of the attribute eventualy replaced by this one)
 * </ul>
 * </p>
 *
 * @author Jean CHINKUMO - Synchrotron SOLEIL
 * @version 1.0
 * @see fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapContext
 */
public class SnapAttributeHeavy extends SnapAttributeLight
{
	private java.sql.Timestamp registration_time = null;
	//private String attribute_complete_name = "";
	private String attribute_device_name = "";

	private String domain = "";
	private String family = "";
	private String member = "";

	private String attribute_name = "";
	//private int data_type = 0;
	//private int data_format = 0;
	//private int writable = 0;
	private int max_dim_x = 0;
	private int max_dim_y = 0;
	private int level = 0;
	private String ctrl_sys = "";
	private int archivable = 0;
	private int substitute = 0;

	/**
	 * Default constructor
	 * Creates a new instance of SnapAttributeHeavy     *
	 *
	 * @see #SnapAttributeHeavy(java.lang.String)
	 * @see #SnapAttributeHeavy(String[])
	 */
	public SnapAttributeHeavy()
	{
	}

	/**
	 * This constructor takes one parameter as inputs.
	 *
	 * @param attribute_complete_name the attribute's complete name
	 * @see #SnapAttributeHeavy()
	 * @see #SnapAttributeHeavy(String[])
	 */
	public SnapAttributeHeavy(String attribute_complete_name)
	{
		super(attribute_complete_name);
		//this.attribute_complete_name = attribute_complete_name;
	}

	/**
	 * This constructor builds an SnapAttributeHeavy from an array
	 *
	 * @param argin an array that contains the SnapAttributeHeavy's registration time, full name, device name,
	 *              associated domain name, associated family name, associated member name, attribute name,
	 *              data type parameter, data format parameter, writable parameter, max_dim_x value, max_dim_y value, level value,
	 *              belonging control system name, archivable value, substitue name.
	 */
	public SnapAttributeHeavy(String[] argin)
	{
		setRegistration_time(java.sql.Timestamp.valueOf(argin[ 0 ]));  // **************** Attribute registration timestamp
		setAttribute_complete_name(argin[ 1 ]);  // **************** The whole attribute name (device_name + attribute_name)
		setAttribute_device_name(argin[ 2 ]);  // **************** name of the belonging device.
		setDomain(argin[ 3 ]);  // **************** domain to which the attribute is associated
		setFamily(argin[ 4 ]);
		setMember(argin[ 5 ]);
		setAttribute_name(argin[ 6 ]);  // **************** attribute name
		setData_type(Integer.parseInt(argin[ 7 ]));  // **************** Attribute data type
		setData_format(Integer.parseInt(argin[ 8 ]));  // **************** Attribute data format
		setWritable(Integer.parseInt(argin[ 9 ]));  // **************** Attribute read/write type
		setMax_dim_x(Integer.parseInt(argin[ 10 ]));  // **************** Attribute Maximum X dimension
		setMax_dim_y(Integer.parseInt(argin[ 11 ]));  // **************** Attribute Maximum Y dimension
		setLevel(Integer.parseInt(argin[ 12 ]));  // **************** Attribute display level
		setCtrl_sys(argin[ 13 ]);  // **************** Control system to which the attribute belongs
		setArchivable(Integer.parseInt(argin[ 14 ]));  // **************** archivable (Property that precises whether the attribute is "on-run-only" archivable, or if it is "always" archivable
		setSubstitute(Integer.parseInt(argin[ 15 ]));  // **************** substitute
	}

	/**
	 * Returns the SnapAttributeHeavy's <I>registration time</I>.
	 *
	 * @return the SnapAttributeHeavy's <I>registration time</I>..
	 * @see #setRegistration_time
	 * @see #getAttribute_complete_name
	 */
	public java.sql.Timestamp getRegistration_time()
	{
		return registration_time;
	}

	public void setRegistration_time(java.sql.Timestamp registration_time)
	{
		this.registration_time = registration_time;
	}

	/**
	 * Returns the SnapAttributeHeavy's name.
	 * @return the SnapAttributeHeavy's name.
	 * @see #setAttribute_complete_name
	 */
	/*public String getAttribute_complete_name() {
	    return getAttribute_complete_name();
	    //return attribute_complete_name;
	}*/

	/**
	 * Sets the SnapAttributeHeavy's name.
	 * @param attribute_complete_name the SnapAttributeHeavy's name.
	 * @see #getAttribute_complete_name
	 */
	/*public void setAttribute_complete_name(String attribute_complete_name) {
	    this.attribute_complete_name = attribute_complete_name;
	}*/

	/**
	 * Returns the SnapAttributeHeavy's <I>device name</I> (The device to which it belongs to).
	 *
	 * @return the SnapAttributeHeavy's <I>device name</I> (The device to which it belongs to).
	 */
	public String getAttribute_device_name()
	{
		return attribute_device_name;
	}

	/**
	 * Sets the SnapAttributeHeavy's <I>device name</I> (The device to which it belongs to).
	 *
	 * @param attribute_device_name the SnapAttributeHeavy's <I>device name</I> (The device to which it belongs to).
	 */
	public void setAttribute_device_name(String attribute_device_name)
	{
		this.attribute_device_name = attribute_device_name;
	}

	/**
	 * Returns the SnapAttributeHeavy's <I>domain</I> (The domain to which it is associated) .
	 *
	 * @return the SnapAttributeHeavy's <I>domain</I> (The domain to which it is associated).
	 */
	public String getDomain()
	{
		return domain;
	}

	/**
	 * Sets the SnapAttributeHeavy's <I>domain</I> (The domain to which it is associated) .
	 *
	 * @param domain the SnapAttributeHeavy's <I>domain</I> (The domain to which it is associated) .
	 */
	public void setDomain(String domain)
	{
		this.domain = domain;
	}

	/**
	 * Returns the SnapAttributeHeavy's <I>family</I> (The family to which it is associated)
	 *
	 * @return the SnapAttributeHeavy's <I>family</I> (The family to which it is associated)
	 */
	public String getFamily()
	{
		return family;
	}

	/**
	 * Sets the <I>family</I> (The family to which it is associated)
	 *
	 * @param family the <I>family</I> (The family to which it is associated)
	 */
	public void setFamily(String family)
	{
		this.family = family;
	}

	/**
	 * Returns the SnapAttributeHeavy's <I>member</I> (The member to which it is associated).
	 *
	 * @return the SnapAttributeHeavy's <I>member</I> (The member to which it is associated) .
	 */
	public String getMember()
	{
		return member;
	}

	/**
	 * Sets the SnapAttributeHeavy's <I>member</I> (The member to which it is associated)
	 *
	 * @param member the SnapAttributeHeavy's <I>member</I> (The member to which it is associated)
	 */
	public void setMember(String member)
	{
		this.member = member;
	}

	/**
	 * Returns the SnapAttributeHeavy's <I>name</I> (its personal name, without the device part).
	 *
	 * @return the SnapAttributeHeavy's <I>name</I> (its personal name, without the device part)
	 */
	public String getAttribute_name()
	{
		return attribute_name;
	}

	/**
	 * Sets the SnapAttributeHeavy's <I>name</I> (its personal name, without the device part)
	 *
	 * @param attribute_name the SnapAttributeHeavy's <I>name</I> (its personal name, without the device part)
	 */
	public void setAttribute_name(String attribute_name)
	{
		this.attribute_name = attribute_name;
	}

	/**
	 * Returns the SnapAttributeHeavy's <I>type property</I> (DevShort/DevLong/DevDouble)
	 * @return the SnapAttributeHeavy's <I>type property</I> (DevShort/DevLong/DevDouble)
	 */
	/*public int getData_type() {
	    return data_type;
	}*/

	/**
	 * Sets the SnapAttributeHeavy's <I>type property</I> (DevShort/DevLong/DevDouble)
	 * @param data_type the SnapAttributeHeavy's <I>type property</I> (DevShort/DevLong/DevDouble)
	 */
	/*public void setData_type(int data_type) {
	    this.data_type = data_type;
	}*/

	/**
	 * Returns the SnapAttributeHeavy's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 * @return the SnapAttributeHeavy's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 */
	/*public int getData_format() {
	    return data_format;
	}*/

	/**
	 * Sets the SnapAttributeHeavy's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 * @param data_format the SnapAttributeHeavy's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 */
	/*public void setData_format(int data_format) {
	    this.data_format = data_format;
	}*/

	/**
	 * Returns the SnapAttributeHeavy's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 * @return the SnapAttributeHeavy's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 */
	/*public int getWritable() {
	    return writable;
	}*/

	/**
	 * Sets the SnapAttributeHeavy's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 * @param writable the SnapAttributeHeavy's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 */
	/*public void setWritable(int writable) {
	    this.writable = writable;
	}*/

	/**
	 * Returns the SnapAttributeHeavy's <I>max_dim_x parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 *
	 * @return the SnapAttributeHeavy's <I>max_dim_x parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 */
	public int getMax_dim_x()
	{
		return max_dim_x;
	}

	/**
	 * Sets the SnapAttributeHeavy's <I>max_dim_x parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 *
	 * @param max_dim_x the SnapAttributeHeavy's <I>max_dim_x parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 */
	public void setMax_dim_x(int max_dim_x)
	{
		this.max_dim_x = max_dim_x;
	}

	/**
	 * Returns the SnapAttributeHeavy's <I>max_dim_y parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 *
	 * @return the SnapAttributeHeavy's <I>max_dim_y parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 */
	public int getMax_dim_y()
	{
		return max_dim_y;
	}

	/**
	 * Sets the SnapAttributeHeavy's <I>max_dim_y parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 *
	 * @param max_dim_y the SnapAttributeHeavy's <I>max_dim_y parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 */
	public void setMax_dim_y(int max_dim_y)
	{
		this.max_dim_y = max_dim_y;
	}

	/**
	 * Returns the SnapAttributeHeavy's <I>level parameter</I> (This parameter is only an help for graphical application - this parameter defines an operator mode or an expert mode).
	 *
	 * @return the SnapAttributeHeavy's <I>level parameter</I> (This parameter is only an help for graphical application - this parameter defines an operator mode or an expert mode).
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * Sets the <I>level parameter</I> (This parameter is only an help for graphical application - this parameter defines an operator mode or an expert mode).
	 *
	 * @param level the <I>level parameter</I> (This parameter is only an help for graphical application - this parameter defines an operator mode or an expert mode).
	 */
	public void setLevel(int level)
	{
		this.level = level;
	}

	/**
	 * Returns the SnapAttributeHeavy's <I>Control system info</I> (Control system to which belongs the attribute).
	 *
	 * @return the SnapAttributeHeavy's <I>Control system info</I> (Control system to which belongs the attribute).
	 */
	public String getCtrl_sys()
	{
		return ctrl_sys;
	}

	/**
	 * Sets the SnapAttributeHeavy's <I>Control system info</I> (Control system to which belongs the attribute).
	 *
	 * @param ctrl_sys the SnapAttributeHeavy's <I>Control system info</I> (Control system to which belongs the attribute).
	 */
	public void setCtrl_sys(String ctrl_sys)
	{
		this.ctrl_sys = ctrl_sys;
	}

	/**
	 * Returns the SnapAttributeHeavy's <I>archivable property</I> that precises whether the attribute is "on-run-only" archivable or if it is "always" archivable.
	 *
	 * @return the SnapAttributeHeavy's <I>archivable property</I> that precises whether the attribute is "on-run-only" archivable or if it is "always" archivable.
	 */
	public int getArchivable()
	{
		return archivable;
	}

	/**
	 * Sets the SnapAttributeHeavy's <I>archivable property</I> that precises whether the attribute is "on-run-only" archivable or if it is "always" archivable.
	 *
	 * @param archivable the SnapAttributeHeavy's <I>archivable property</I> that precises whether the attribute is "on-run-only" archivable or if it is "always" archivable.
	 */
	public void setArchivable(int archivable)
	{
		this.archivable = archivable;
	}

	/**
	 * Returns the SnapAttributeHeavy's <I>substitute parameter</I> (Name of the attribute eventualy replaced by this one).
	 *
	 * @return the SnapAttributeHeavy's <I>substitute parameter</I> (Name of the attribute eventualy replaced by this one).
	 */
	public int getSubstitute()
	{
		return substitute;
	}

	/**
	 * Sets the SnapAttributeHeavy's <I>substitute parameter</I> (Name of the attribute eventualy replaced by this one).
	 *
	 * @param substitute the SnapAttributeHeavy's <I>substitute parameter</I> (Name of the attribute eventualy replaced by this one).
	 */
	public void setSubstitute(int substitute)
	{
		this.substitute = substitute;
	}

	/**
	 * Returns an array representation of the object <I>SnapAttributeHeavy</I>.
	 * In this order, array fields are :
	 * <ol>
	 * <li> the <I>registration time</I>,
	 * <li> the <I>attribute complete name</I> (device_name + attribute_name),
	 * <li> the <I>device name</I> (The device to which it belongs to),
	 * <li> the <I>domain</I> (The domain to which it is associated),
	 * <li> the <I>family</I> (The family to which it is associated),
	 * <li> the <I>member</I> (The member to which it is associated),
	 * <li> the <I>name</I> (its personal name, without the device part),
	 * <li> the <I>attribute type property</I> (DevShort/DevLong/DevDouble),
	 * <li> the <I>attribute data format property</I> (SCALAR/SPECTRUM/IMAGE),
	 * <li> the <I>attribute writable property</I> (READ/READ_WRITE/READ_WITH_WRITE),
	 * <li> the <I>max_dim_x parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format)
	 * <li> the <I>max_dim_y parameter</I> (maximum size for attributes of the IMAGE data format)
	 * <li> the <I>level parameter</I> (This parameter is only an help for graphical application - this parameter defines an operator mode or an expert mode).
	 * <li> the <I>control system reference</I> (Control system to which belongs the attribute)
	 * <li> the <I>archivable property</I> that precises whether the attribute is "on-run-only" archivable, or if it is "always" archivable.
	 * <li> the <I>substitute parameter</I> (Name of the attribute eventualy replaced by this one)
	 * </ol>
	 *
	 * @return an array representation of the object <I>SnapAttributeHeavy</I>.
	 */
	public String[] toArray()
	{
		String[] snapAtt;
		snapAtt = new String[ 16 ];
		snapAtt[ 0 ] = registration_time.toString();
		snapAtt[ 1 ] = getAttribute_complete_name(); //attribute_complete_name;
		snapAtt[ 2 ] = attribute_device_name;
		snapAtt[ 3 ] = domain;
		snapAtt[ 4 ] = family;
		snapAtt[ 5 ] = member;
		snapAtt[ 6 ] = attribute_name;
		snapAtt[ 7 ] = Integer.toString(getData_type());//Integer.toString(data_type);
		snapAtt[ 8 ] = Integer.toString(getData_format()); //Integer.toString(data_format);
		snapAtt[ 9 ] = Integer.toString(getWritable());	//Integer.toString(writable);
		snapAtt[ 10 ] = Integer.toString(max_dim_x);
		snapAtt[ 11 ] = Integer.toString(max_dim_y);
		snapAtt[ 12 ] = Integer.toString(level);
		snapAtt[ 13 ] = ctrl_sys;
		snapAtt[ 14 ] = Integer.toString(archivable);
		snapAtt[ 15 ] = Integer.toString(substitute);

		return snapAtt;
	}
}

