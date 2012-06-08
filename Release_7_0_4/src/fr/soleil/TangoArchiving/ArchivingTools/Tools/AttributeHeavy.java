//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description: The AttributeHeavy object describes a TANGO attribute about to be registered into the filing service.
//				It thus contains all available informations characterizing it.
//				An AttributeHeavy contains :
//				- a registration time,
//				- a device name (The device to which it belongs to),
//				- a domain (The domain to which it is associated),
//				- a family (The family to which it is associated),
//				- a member (The member to which it is associated),
//				- a name (its personal name, without the device part),
//				- an attribute type property (DevShort/DevLong/DevDouble),
//				- an attribute data format property (SCALAR/SPECTRUM/IMAGE),
//				- an attribute writable property (READ/READ_WRITE/READ_WITH_WRITE),
//				- the max_dim_x parameter (maximum size for attributes of the SPECTRUM and IMAGE data format)
//				- the max_dim_y parameter (maximum size for attributes of the IMAGE data format)
//				- a level parameter (This parameter is only an help for graphical application - this parameter defines an operator mode or an expert mode).
//				- a control system reference (Control system to which belongs the attribute)
//				- an archivable property that precises whether the attribute is "on-run-only" archivable, or if it is "always" archivable.
//				- a substitute parameter (Name of the attribute eventualy replaced by this one)
//				- a description (Attribute description)
//				- a label (Attribute label)
//				- a unit (Attribute unit)
//				- a standard_unit (Conversion factor to MKSA unit)
//				- a display_unit (The attribute unit in a printable form)
//				- a format (How to print attribute value)
//				- a min_value (Attribute min value)
//				- a max_value (Attribute max value)
//				- a min_alarm (Attribute low level alarm)
//				- a max_alarm (Attribute high level alarm)
//				- some optional_properties
//				- a target (TANGO device in charge of inserting datum into the database)
//				- a collector (TANGO device in charge of collecting events)
//				- a mode of filling (Constraints applied to the filing).
//
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
 * The <I>AttributeHeavy</I> object describes a TANGO attribute about to be registered into the filing service.
 * It thus contains all available informations characterizing it.
 * An <I>AttributeHeavy</I> contains :
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
 * <p/>
 * <li> a <I>description</I> (Attribute description)
 * <li> a <I>label</I> (Attribute label)
 * <li> a <I>unit</I> (Attribute unit)
 * <li> a <I>standard_unit</I> (Conversion factor to MKSA unit)
 * <li> a <I>display_unit</I> (The attribute unit in a printable form)
 * <li> a <I>format</I> (How to print attribute value)
 * <li> a <I>min_value</I> (Attribute min value)
 * <li> a <I>max_value</I> (Attribute max value)
 * <li> a <I>min_alarm</I> (Attribute low level alarm)
 * <li> a <I>max_alarm</I> (Attribute high level alarm)
 * <li> some <I>optional_properties</I>
 * <p/>
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
public class AttributeHeavy extends AttributeLightMode
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

	private String description = "";  // **************** DESCRIPTION
	private String label = "";        // **************** LABEL
	private String unit = "";         // **************** UNIT
	private String standard_unit = "";// **************** STANDARD_UNIT
	private String display_unit = ""; // **************** DISPLAY_UNIT
	private String format = "";       // **************** FORMAT
	private String min_value = "";    // **************** MIN_VALUE
	private String max_value = "";    // **************** MAX_VALUE
	private String min_alarm = "";    // **************** MIN_ALARM
	private String max_alarm = "";    // **************** MAX_ALARM
	private String optional_properties = "";  // **************** OPTIONAL_PROPERTIES

	/**
	 * Default constructor
	 * Creates a new instance of AttributeHeavy
	 *
	 * @see #AttributeHeavy(String)
	 * @see #AttributeHeavy(String[])
	 * @see AttributeLight
	 */
	public AttributeHeavy()
	{
	}

	/**
	 * This constructor takes one parameter as inputs.
	 *
	 * @param n the attribute's full name (device part + attribut part)
	 * @see #AttributeHeavy(String[])
	 * @see AttributeLight
	 */
	public AttributeHeavy(String n)
	{
		super(n);
	}

	/**
	 * This constructor builds an AttributeHeavy from an array
	 *
	 * @param argin an array that contains the AttributeHeavy's registration time, full name, device name,
	 *              associated domain name, associated family name, associated member name, attribute name,
	 *              data type parameter, data format parameter, writable parameter, max_dim_x value, max_dim_y value, level value,
	 *              belonging control system name, archivable value, substitue name, description, label, unit, standard unit, display unit, format,
	 *              min value, max value, min alarm, max amarm, optional properties, target, collector and mode.
	 */
	public AttributeHeavy(String[] argin)
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
		/* APT */
		setDescription(argin[ 16 ]);  // **************** DESCRIPTION
		setLabel(argin[ 17 ]);  // **************** LABEL
		setUnit(argin[ 18 ]);  // **************** UNIT
		setStandard_unit(argin[ 19 ]); // **************** STANDARD_UNIT
		setDisplay_unit(argin[ 20 ]);  // **************** DISPLAY_UNIT
		setFormat(argin[ 21 ]);  // **************** FORMAT
		setMin_value(argin[ 22 ]);  // **************** MIN_VALUE
		setMax_value(argin[ 23 ]);  // **************** MAX_VALUE
		setMin_alarm(argin[ 24 ]);  // **************** MIN_ALARM
		setMax_alarm(argin[ 25 ]);  // **************** MAX_ALARM
		setOptional_properties(argin[ 26 ]);  // **************** OPTIONAL_PROPERTIES

	}

	/**
	 * Returns the AttributeHeavy's <I>registration time</I>.
	 *
	 * @return the AttributeHeavy's <I>registration time</I>..
	 * @see #setRegistration_time
	 * @see #getAttribute_complete_name
	 */
	public java.sql.Timestamp getRegistration_time()
	{
		return this.registration_time;
	}

	/**
	 * Sets the AttributeHeavy's <I>registration time</I>.
	 *
	 * @param registration_time the AttributeHeavy's <I>registration time</I>.
	 */
	public void setRegistration_time(java.sql.Timestamp registration_time)
	{
		this.registration_time = registration_time;
	}

	/**
	 * Returns the AttributeHeavy's <I>name</I> (full name).
	 *
	 * @return the AttributeHeavy's <I>name</I> (full name).
	 */
	public String getAttribute_complete_name()
	{
		return super.getAttribute_complete_name();
	}

	/**
	 * Sets the AttributeHeavy's <I>name</I> (full name).
	 *
	 * @param attribute_complete_name the AttributeHeavy's <I>name</I> (full name).
	 */
	public void setAttribute_complete_name(String attribute_complete_name)
	{
		super.setAttribute_complete_name(attribute_complete_name);
	}

	/**
	 * Returns the AttributeHeavy's <I>device name</I> (The device to which it belongs to).
	 *
	 * @return the AttributeHeavy's <I>device name</I> (The device to which it belongs to).
	 */
	public String getAttribute_device_name()
	{
		return this.attribute_device_name;
	}

	/**
	 * Sets the AttributeHeavy's <I>device name</I> (The device to which it belongs to).
	 *
	 * @param attribute_device_name the AttributeHeavy's <I>device name</I> (The device to which it belongs to).
	 */
	public void setAttribute_device_name(String attribute_device_name)
	{
		this.attribute_device_name = attribute_device_name;
	}

	/**
	 * Returns the AttributeHeavy's <I>domain</I> (The domain to which it is associated) .
	 *
	 * @return the AttributeHeavy's <I>domain</I> (The domain to which it is associated).
	 */
	public String getDomain()
	{
		return this.domain;
	}

	/**
	 * Sets the AttributeHeavy's <I>domain</I> (The domain to which it is associated) .
	 *
	 * @param domain the AttributeHeavy's <I>domain</I> (The domain to which it is associated) .
	 */
	public void setDomain(String domain)
	{
		this.domain = domain;
	}

	/**
	 * Returns the AttributeHeavy's <I>family</I> (The family to which it is associated)
	 *
	 * @return the AttributeHeavy's <I>family</I> (The family to which it is associated)
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
	 * Returns the AttributeHeavy's <I>member</I> (The member to which it is associated).
	 *
	 * @return the AttributeHeavy's <I>member</I> (The member to which it is associated) .
	 */
	public String getMember()
	{
		return member;
	}

	/**
	 * Sets the AttributeHeavy's <I>member</I> (The member to which it is associated)
	 *
	 * @param member the AttributeHeavy's <I>member</I> (The member to which it is associated)
	 */
	public void setMember(String member)
	{
		this.member = member;
	}

	/**
	 * Returns the AttributeHeavy's <I>name</I> (its personal name, without the device part).
	 *
	 * @return the AttributeHeavy's <I>name</I> (its personal name, without the device part)
	 */
	public String getAttribute_name()
	{
		return attribute_name;
	}

	/**
	 * Sets the AttributeHeavy's <I>name</I> (its personal name, without the device part)
	 *
	 * @param attribute_name the AttributeHeavy's <I>name</I> (its personal name, without the device part)
	 */
	public void setAttribute_name(String attribute_name)
	{
		this.attribute_name = attribute_name;
	}

	/**
	 * Returns the AttributeHeavy's <I>type property</I> (DevShort/DevLong/DevDouble)
	 *
	 * @return the AttributeHeavy's <I>type property</I> (DevShort/DevLong/DevDouble)
	 */
	public int getData_type()
	{
		return super.getData_type();
	}

	/**
	 * Sets the AttributeHeavy's <I>type property</I> (DevShort/DevLong/DevDouble)
	 *
	 * @param data_type the AttributeHeavy's <I>type property</I> (DevShort/DevLong/DevDouble)
	 */
	public void setData_type(int data_type)
	{
		super.setData_type(data_type);
	}

	/**
	 * Returns the AttributeHeavy's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 *
	 * @return the AttributeHeavy's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 */
	public int getData_format()
	{
		return super.getData_format();
	}

	/**
	 * Sets the AttributeHeavy's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 *
	 * @param data_format the AttributeHeavy's <I>data format property</I> (SCALAR/SPECTRUM/IMAGE)
	 */
	public void setData_format(int data_format)
	{
		super.setData_format(data_format);
	}

	/**
	 * Returns the AttributeHeavy's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 *
	 * @return the AttributeHeavy's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 */
	public int getWritable()
	{
		return super.getWritable();
	}

	/**
	 * Sets the AttributeHeavy's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 *
	 * @param writable the AttributeHeavy's <I>writable property</I> (READ/READ_WRITE/READ_WITH_WRITE).
	 */
	public void setWritable(int writable)
	{
		super.setWritable(writable);
	}

	/**
	 * Returns the AttributeHeavy's <I>max_dim_x parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 *
	 * @return the AttributeHeavy's <I>max_dim_x parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 */
	public int getMax_dim_x()
	{
		return this.max_dim_x;
	}

	/**
	 * Sets the AttributeHeavy's <I>max_dim_x parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 *
	 * @param max_dim_x the AttributeHeavy's <I>max_dim_x parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 */
	public void setMax_dim_x(int max_dim_x)
	{
		this.max_dim_x = max_dim_x;
	}

	/**
	 * Returns the AttributeHeavy's <I>max_dim_y parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 *
	 * @return the AttributeHeavy's <I>max_dim_y parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 */
	public int getMax_dim_y()
	{
		return this.max_dim_y;
	}

	/**
	 * Sets the AttributeHeavy's <I>max_dim_y parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 *
	 * @param max_dim_y the AttributeHeavy's <I>max_dim_y parameter</I> (maximum size for attributes of the SPECTRUM and IMAGE data format).
	 */
	public void setMax_dim_y(int max_dim_y)
	{
		this.max_dim_y = max_dim_y;
	}

	/**
	 * Returns the AttributeHeavy's <I>level parameter</I> (This parameter is only an help for graphical application - this parameter defines an operator mode or an expert mode).
	 *
	 * @return the AttributeHeavy's <I>level parameter</I> (This parameter is only an help for graphical application - this parameter defines an operator mode or an expert mode).
	 */
	public int getLevel()
	{
		return this.level;
	}

	/**
	 * Sets the <I>level parameter</I> (This parameter is only an help for graphical application - this parameter defines an operator mode or an expert mode).
	 *
	 * @param level the <I>level parameter</I> (This parameter is only an help for graphical application - this parameter defines an operator mode or an expert mode).
	 */
	public void setLevel(int level)
	{
		level = this.level;
	}

	/**
	 * Returns the AttributeHeavy's <I>Control system info</I> (Control system to which belongs the attribute).
	 *
	 * @return the AttributeHeavy's <I>Control system info</I> (Control system to which belongs the attribute).
	 */
	public String getCtrl_sys()
	{
		return this.ctrl_sys;
	}

	/**
	 * Sets the AttributeHeavy's <I>Control system info</I> (Control system to which belongs the attribute).
	 *
	 * @param ctrl_sys the AttributeHeavy's <I>Control system info</I> (Control system to which belongs the attribute).
	 */
	public void setCtrl_sys(String ctrl_sys)
	{
		this.ctrl_sys = ctrl_sys;
	}

	/**
	 * Returns the AttributeHeavy's <I>archivable property</I> that precises whether the attribute is "on-run-only" archivable or if it is "always" archivable.
	 *
	 * @return the AttributeHeavy's <I>archivable property</I> that precises whether the attribute is "on-run-only" archivable or if it is "always" archivable.
	 */
	public int getArchivable()
	{
		return this.archivable;
	}

	/**
	 * Sets the AttributeHeavy's <I>archivable property</I> that precises whether the attribute is "on-run-only" archivable or if it is "always" archivable.
	 *
	 * @param archivable the AttributeHeavy's <I>archivable property</I> that precises whether the attribute is "on-run-only" archivable or if it is "always" archivable.
	 */
	public void setArchivable(int archivable)
	{
		this.archivable = archivable;
	}

	/**
	 * Returns the AttributeHeavy's <I>substitute parameter</I> (Name of the attribute eventualy replaced by this one).
	 *
	 * @return the AttributeHeavy's <I>substitute parameter</I> (Name of the attribute eventualy replaced by this one).
	 */
	public int getSubstitute()
	{
		return this.substitute;
	}

	/**
	 * Sets the AttributeHeavy's <I>substitute parameter</I> (Name of the attribute eventualy replaced by this one).
	 *
	 * @param substitute the AttributeHeavy's <I>substitute parameter</I> (Name of the attribute eventualy replaced by this one).
	 */
	public void setSubstitute(int substitute)
	{
		this.substitute = substitute;
	}

	/**
	 * Returns the AttributeHeavy's <I>description</I> (Attribute description).
	 *
	 * @return the AttributeHeavy's <I>description</I> (Attribute description).
	 */
	public String getDescription()
	{
		return this.description;
	}

	/**
	 * Sets the AttributeHeavy's <I>description</I> (Attribute description).
	 *
	 * @param description the AttributeHeavy's <I>description</I> (Attribute description).
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Returns the AttributeHeavy's <I>label</I> (Attribute label).
	 *
	 * @return the AttributeHeavy's <I>label</I> (Attribute label).
	 */
	public String getLabel()
	{
		return this.label;
	}

	/**
	 * Sets the AttributeHeavy's <I>label</I> (Attribute label).
	 *
	 * @param label the AttributeHeavy's <I>label</I> (Attribute label).
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * Returns the AttributeHeavy's <I>unit</I> (Attribute unit).
	 *
	 * @return the AttributeHeavy's <I>unit</I> (Attribute unit).
	 */
	public String getUnit()
	{
		return this.unit;
	}

	/**
	 * Sets the AttributeHeavy's <I>unit</I> (Attribute unit).
	 *
	 * @param unit the AttributeHeavy's <I>unit</I> (Attribute unit).
	 */
	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	/**
	 * Returns the AttributeHeavy's <I>standard_unit</I> (Conversion factor to MKSA unit).
	 *
	 * @return the AttributeHeavy's <I>standard_unit</I> (Conversion factor to MKSA unit).
	 */
	public String getStandard_unit()
	{
		return this.standard_unit;
	}

	/**
	 * Sets the AttributeHeavy's <I>standard_unit</I> (Conversion factor to MKSA unit).
	 *
	 * @param standard_unit the AttributeHeavy's <I>standard_unit</I> (Conversion factor to MKSA unit).
	 */
	public void setStandard_unit(String standard_unit)
	{
		this.standard_unit = standard_unit;
	}

	/**
	 * Returns the AttributeHeavy's <I>display_unit</I> (The attribute unit in a printable form).
	 *
	 * @return the AttributeHeavy's <I>display_unit</I> (The attribute unit in a printable form).
	 */
	public String getDisplay_unit()
	{
		return this.display_unit;
	}

	/**
	 * Sets the AttributeHeavy's <I>display_unit</I> (The attribute unit in a printable form).
	 *
	 * @param display_unit the AttributeHeavy's <I>display_unit</I> (The attribute unit in a printable form).
	 */
	public void setDisplay_unit(String display_unit)
	{
		this.display_unit = display_unit;
	}

	/**
	 * Returns the AttributeHeavy's <I>format</I> (How to print attribute value).
	 *
	 * @return the AttributeHeavy's <I>format</I> (How to print attribute value).
	 */
	public String getFormat()
	{
		return this.format;
	}

	/**
	 * Sets the AttributeHeavy's <I>format</I> (How to print attribute value).
	 *
	 * @param format the AttributeHeavy's <I>format</I> (How to print attribute value).
	 */
	public void setFormat(String format)
	{
		this.format = format;
	}

	/**
	 * Returns the AttributeHeavy's <I>min_value</I> (Attribute min value).
	 *
	 * @return the AttributeHeavy's <I>min_value</I> (Attribute min value).
	 */
	public String getMin_value()
	{
		return this.min_value;
	}

	/**
	 * Sets the AttributeHeavy's <I>min_value</I> (Attribute min value).
	 *
	 * @param min_value the AttributeHeavy's <I>min_value</I> (Attribute min value).
	 */
	public void setMin_value(String min_value)
	{
		this.min_value = min_value;
	}

	/**
	 * Returns the AttributeHeavy's <I>max_value</I> (Attribute max value).
	 *
	 * @return the AttributeHeavy's <I>max_value</I> (Attribute max value).
	 */
	public String getMax_value()
	{
		return this.max_value;
	}

	/**
	 * Sets the AttributeHeavy's <I>max_value</I> (Attribute max value).
	 *
	 * @param max_value the AttributeHeavy's <I>max_value</I> (Attribute max value).
	 */
	public void setMax_value(String max_value)
	{
		this.max_value = max_value;
	}

	/**
	 * Returns the AttributeHeavy's <I>min_alarm</I> (Attribute low level alarm).
	 *
	 * @return the AttributeHeavy's <I>min_alarm</I> (Attribute low level alarm).
	 */
	public String getMin_alarm()
	{
		return this.min_alarm;
	}

	/**
	 * Sets the AttributeHeavy's <I>min_alarm</I> (Attribute low level alarm).
	 *
	 * @param min_alarm the AttributeHeavy's <I>min_alarm</I> (Attribute low level alarm).
	 */
	public void setMin_alarm(String min_alarm)
	{
		this.min_alarm = min_alarm;
	}

	/**
	 * Returns the AttributeHeavy's <I>max_alarm</I> (Attribute high level alarm).
	 *
	 * @return the AttributeHeavy's <I>max_alarm</I> (Attribute high level alarm).
	 */
	public String getMax_alarm()
	{
		return this.max_alarm;
	}

	/**
	 * Sets the AttributeHeavy's <I>max_alarm</I> (Attribute high level alarm)
	 *
	 * @param max_alarm the AttributeHeavy's <I>max_alarm</I> (Attribute high level alarm)
	 */
	public void setMax_alarm(String max_alarm)
	{
		this.max_alarm = max_alarm;
	}

	/**
	 * Returns the AttributeHeavy's <I>optional_properties</I>.
	 *
	 * @return the AttributeHeavy's <I>optional_properties</I>.
	 */
	public String getOptional_properties()
	{
		return this.optional_properties;
	}

	/**
	 * Sets the AttributeHeavy's <I>optional_properties</I>
	 *
	 * @param optional_properties the AttributeHeavy's <I>optional_properties</I>
	 */
	public void setOptional_properties(String optional_properties)
	{
		this.optional_properties = optional_properties;
	}

	/**
	 * Returns an array representation of the object <I>AttributeHeavy</I>.
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
	 * <p/>
	 * <li> the <I>description</I> (Attribute description)
	 * <li> the <I>label</I> (Attribute label)
	 * <li> the <I>unit</I> (Attribute unit)
	 * <li> the <I>standard_unit</I> (Conversion factor to MKSA unit)
	 * <li> the <I>display_unit</I> (The attribute unit in the printable form)
	 * <li> the <I>format</I> (How to print attribute value)
	 * <li> the <I>min_value</I> (Attribute min value)
	 * <li> the <I>max_value</I> (Attribute max value)
	 * <li> the <I>min_alarm</I> (Attribute low level alarm)
	 * <li> the <I>max_alarm</I> (Attribute high level alarm)
	 * <li> some <I>optional_properties</I>
	 * <p/>
	 * <li> the <I>target</I> (TANGO device in charge of inserting datum into the database)
	 * <li> the <I>collector</I> (TANGO device in charge of collecting events)
	 * <li> the <I>mode of filling</I> (Constraints applied to the filing).
	 * </ol>
	 *
	 * @return an array representation of the object <I>AttributeHeavy</I>.
	 */
	public String[] toArray()
	{
		String[] att_tab;
		att_tab = new String[ 27 ];

		att_tab[ 0 ] = getRegistration_time().toString();  // **************** Attribute registration timestamp
		att_tab[ 1 ] = getAttribute_complete_name();  // **************** The whole attribute name (device_name + attribute_name)
		att_tab[ 2 ] = getAttribute_device_name();  // **************** name of the belonging device.

		att_tab[ 3 ] = getDomain();  // **************** domain to which the attribute is associated
		att_tab[ 4 ] = getFamily();
		att_tab[ 5 ] = getMember();

		att_tab[ 6 ] = getAttribute_name();  // **************** attribute name
		att_tab[ 7 ] = Integer.toString(getData_type());  // **************** Attribute data type
		att_tab[ 8 ] = Integer.toString(getData_format());  // **************** Attribute data format
		att_tab[ 9 ] = Integer.toString(getWritable());  // **************** Attribute read/write type
		att_tab[ 10 ] = Integer.toString(getMax_dim_x());  // **************** Attribute Maximum X dimension
		att_tab[ 11 ] = Integer.toString(getMax_dim_y());  // **************** Attribute Maximum Y dimension
		att_tab[ 12 ] = Integer.toString(getLevel());  // **************** Attribute display level
		att_tab[ 13 ] = getCtrl_sys();  // **************** Control system to which the attribute belongs
		att_tab[ 14 ] = Integer.toString(getArchivable());  // **************** archivable (Property that precises whether the attribute is "on-run-only" archivable, or if it is "always" archivable
		att_tab[ 15 ] = Integer.toString(getSubstitute());  // **************** substitute
		/* APT */
		att_tab[ 16 ] = getDescription();  // **************** DESCRIPTION
		att_tab[ 17 ] = getLabel();  // **************** LABEL
		att_tab[ 18 ] = getUnit();  // **************** UNIT
		att_tab[ 19 ] = getStandard_unit(); // **************** STANDARD_UNIT
		att_tab[ 20 ] = getDisplay_unit();  // **************** DISPLAY_UNIT
		att_tab[ 21 ] = getFormat();  // **************** FORMAT
		att_tab[ 22 ] = getMin_value();  // **************** MIN_VALUE
		att_tab[ 23 ] = getMax_value();  // **************** MAX_VALUE
		att_tab[ 24 ] = getMin_alarm();  // **************** MIN_ALARM
		att_tab[ 25 ] = getMax_alarm();  // **************** MAX_ALARM
		att_tab[ 26 ] = getOptional_properties();  // **************** OPTIONAL_PROPERTIES

		return att_tab;
	}

	/**
	 * Returns a string representation of the object <I>AttributeHeavy</I>.
	 *
	 * @return a string representation of the object <I>AttributeHeavy</I>.
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer("");
		buf.append("\tname ......................." + getAttribute_complete_name() + "\n");
		buf.append("\t**** Données propres à l'ADT... **** \n");
		buf.append("\tRegistration Time .........." + getRegistration_time().toString() + "\n");  // **************** Attribute registration timestamp
		buf.append("\tAttribute_complete_name ...." + getAttribute_complete_name() + "\n");  // **************** The whole attribute name (device_name + attribute_name)
		buf.append("\tDevice name ................" + getAttribute_device_name() + "\n");  // **************** name of the belonging device.

		buf.append("\tDomain ....................." + getDomain() + "\n");  // **************** domain to which the attribute is associated
		buf.append("\tFamily ....................." + getFamily() + "\n");  // **************** family to which the attribute is associated
		buf.append("\tMember ....................." + getMember() + "\n");  // **************** member to which the attribute is associated

		buf.append("\tAttribute name ............." + getAttribute_name() + "\n");  // **************** attribute name
		buf.append("\tData type property ........." + Integer.toString(getData_type()) + "\n");  // **************** Attribute data type
		buf.append("\tData format property ......." + Integer.toString(getData_format()) + "\n");  // **************** Attribute data format
		buf.append("\tWritable property .........." + Integer.toString(getWritable()) + "\n");  // **************** Attribute read/write type
		buf.append("\tMaximum X dimension ........" + Integer.toString(getMax_dim_x()) + "\n");  // **************** Attribute Maximum X dimension
		buf.append("\tMaximum X dimension ........" + Integer.toString(getMax_dim_y()) + "\n");  // **************** Attribute Maximum Y dimension
		buf.append("\tDisplay level .............." + Integer.toString(getLevel()) + "\n");  // **************** Attribute display level
		buf.append("\tCtrl system ................" + getCtrl_sys() + "\n");  // **************** Control system to which the attribute belongs
		buf.append("\tArchivable  ................" + Integer.toString(getArchivable()) + "\n");  // **************** archivable (Property that precises whether the attribute is "on-run-only" archivable, or if it is "always" archivable
		buf.append("\tSubstitute ................." + Integer.toString(getSubstitute()) + "\n");  // **************** substitute
		buf.append("\t**** Données propres à l'APT... **** \n");
		buf.append("\tDescription ................" + getDescription() + "\n");  // **************** DESCRIPTION
		buf.append("\tLabel ......................" + getLabel() + "\n");  // **************** LABEL
		buf.append("\tUnit ......................." + getUnit() + "\n");  // **************** UNIT
		buf.append("\tStandard Unit .............." + getStandard_unit() + "\n"); // **************** STANDARD_UNIT
		buf.append("\tDisplay_unit ..............." + getDisplay_unit() + "\n");  // **************** DISPLAY_UNIT
		buf.append("\tFormat ....................." + getFormat() + "\n");  // **************** FORMAT
		buf.append("\tMin_value .................." + getMin_value() + "\n");  // **************** MIN_VALUE
		buf.append("\tMax_value .................." + getMax_value() + "\n");  // **************** MAX_VALUE
		buf.append("\tMin_alarm .................." + getMin_alarm() + "\n");  // **************** MIN_ALARM
		buf.append("\tMax_alarm .................." + getMax_alarm() + "\n");  // **************** MAX_ALARM
		buf.append("\tOptional_properties ........" + getOptional_properties() + "\n");  // **************** OPTIONAL_PROPERTIES
		buf.append("\t**** Données propres à l'AMT... **** \n");
		return buf.toString();
	}

	public boolean equals(Object o)
	{
		if ( this == o ) return true;
		if ( !( o instanceof AttributeHeavy ) ) return false;
		if ( !super.equals(o) ) return false;

		final AttributeHeavy attributeHeavy = ( AttributeHeavy ) o;

		if ( archivable != attributeHeavy.archivable ) return false;
		if ( level != attributeHeavy.level ) return false;
		if ( max_dim_x != attributeHeavy.max_dim_x ) return false;
		if ( max_dim_y != attributeHeavy.max_dim_y ) return false;
		if ( substitute != attributeHeavy.substitute ) return false;
		if ( !attribute_device_name.equals(attributeHeavy.attribute_device_name) ) return false;
		if ( !ctrl_sys.equals(attributeHeavy.ctrl_sys) ) return false;
		if ( description != null ? !description.equals(attributeHeavy.description) : attributeHeavy.description != null ) return false;
		if ( display_unit != null ? !display_unit.equals(attributeHeavy.display_unit) : attributeHeavy.display_unit != null ) return false;
		if ( !domain.equals(attributeHeavy.domain) ) return false;
		if ( !family.equals(attributeHeavy.family) ) return false;
		if ( format != null ? !format.equals(attributeHeavy.format) : attributeHeavy.format != null ) return false;
		if ( label != null ? !label.equals(attributeHeavy.label) : attributeHeavy.label != null ) return false;
		if ( max_alarm != null ? !max_alarm.equals(attributeHeavy.max_alarm) : attributeHeavy.max_alarm != null ) return false;
		if ( max_value != null ? !max_value.equals(attributeHeavy.max_value) : attributeHeavy.max_value != null ) return false;
		if ( !member.equals(attributeHeavy.member) ) return false;
		if ( min_alarm != null ? !min_alarm.equals(attributeHeavy.min_alarm) : attributeHeavy.min_alarm != null ) return false;
		if ( min_value != null ? !min_value.equals(attributeHeavy.min_value) : attributeHeavy.min_value != null ) return false;
		if ( optional_properties != null ? !optional_properties.equals(attributeHeavy.optional_properties) : attributeHeavy.optional_properties != null ) return false;
		if ( !registration_time.equals(attributeHeavy.registration_time) ) return false;
		if ( standard_unit != null ? !standard_unit.equals(attributeHeavy.standard_unit) : attributeHeavy.standard_unit != null ) return false;
		if ( unit != null ? !unit.equals(attributeHeavy.unit) : attributeHeavy.unit != null ) return false;

		return true;
	}

	public int hashCode()
	{
		int result = super.hashCode();
		result = 29 * result + registration_time.hashCode();
		result = 29 * result + attribute_device_name.hashCode();
		result = 29 * result + domain.hashCode();
		result = 29 * result + family.hashCode();
		result = 29 * result + member.hashCode();
		result = 29 * result + max_dim_x;
		result = 29 * result + max_dim_y;
		result = 29 * result + level;
		result = 29 * result + ctrl_sys.hashCode();
		result = 29 * result + archivable;
		result = 29 * result + substitute;
		result = 29 * result + ( description != null ? description.hashCode() : 0 );
		result = 29 * result + ( label != null ? label.hashCode() : 0 );
		result = 29 * result + ( unit != null ? unit.hashCode() : 0 );
		result = 29 * result + ( standard_unit != null ? standard_unit.hashCode() : 0 );
		result = 29 * result + ( display_unit != null ? display_unit.hashCode() : 0 );
		result = 29 * result + ( format != null ? format.hashCode() : 0 );
		result = 29 * result + ( min_value != null ? min_value.hashCode() : 0 );
		result = 29 * result + ( max_value != null ? max_value.hashCode() : 0 );
		result = 29 * result + ( min_alarm != null ? min_alarm.hashCode() : 0 );
		result = 29 * result + ( max_alarm != null ? max_alarm.hashCode() : 0 );
		result = 29 * result + ( optional_properties != null ? optional_properties.hashCode() : 0 );
		return result;
	}
}
