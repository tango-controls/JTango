package fr.esrf.Tango;

/**
 * Generated from IDL struct "EventProperties".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class EventProperties
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public EventProperties(){}
	public fr.esrf.Tango.ChangeEventProp ch_event;
	public fr.esrf.Tango.PeriodicEventProp per_event;
	public fr.esrf.Tango.ArchiveEventProp arch_event;
	public EventProperties(fr.esrf.Tango.ChangeEventProp ch_event, fr.esrf.Tango.PeriodicEventProp per_event, fr.esrf.Tango.ArchiveEventProp arch_event)
	{
		this.ch_event = ch_event;
		this.per_event = per_event;
		this.arch_event = arch_event;
	}
}
