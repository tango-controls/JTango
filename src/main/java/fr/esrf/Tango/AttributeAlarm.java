package fr.esrf.Tango;

/**
 * Generated from IDL struct "AttributeAlarm".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class AttributeAlarm
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public AttributeAlarm(){}
	public java.lang.String min_alarm = "";
	public java.lang.String max_alarm = "";
	public java.lang.String min_warning = "";
	public java.lang.String max_warning = "";
	public java.lang.String delta_t = "";
	public java.lang.String delta_val = "";
	public java.lang.String[] extensions;
	public AttributeAlarm(java.lang.String min_alarm, java.lang.String max_alarm, java.lang.String min_warning, java.lang.String max_warning, java.lang.String delta_t, java.lang.String delta_val, java.lang.String[] extensions)
	{
		this.min_alarm = min_alarm;
		this.max_alarm = max_alarm;
		this.min_warning = min_warning;
		this.max_warning = max_warning;
		this.delta_t = delta_t;
		this.delta_val = delta_val;
		this.extensions = extensions;
	}
}
