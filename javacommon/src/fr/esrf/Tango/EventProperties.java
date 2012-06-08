package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "EventProperties"
 *	@author JacORB IDL compiler 
 */

public final class EventProperties
	implements org.omg.CORBA.portable.IDLEntity
{
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
