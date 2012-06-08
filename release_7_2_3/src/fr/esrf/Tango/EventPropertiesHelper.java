package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "EventProperties"
 *	@author JacORB IDL compiler 
 */

public final class EventPropertiesHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.EventPropertiesHelper.id(),"EventProperties",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("ch_event", fr.esrf.Tango.ChangeEventPropHelper.type(), null),new org.omg.CORBA.StructMember("per_event", fr.esrf.Tango.PeriodicEventPropHelper.type(), null),new org.omg.CORBA.StructMember("arch_event", fr.esrf.Tango.ArchiveEventPropHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.EventProperties s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.EventProperties extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/EventProperties:1.0";
	}
	public static fr.esrf.Tango.EventProperties read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.EventProperties result = new fr.esrf.Tango.EventProperties();
		result.ch_event=fr.esrf.Tango.ChangeEventPropHelper.read(in);
		result.per_event=fr.esrf.Tango.PeriodicEventPropHelper.read(in);
		result.arch_event=fr.esrf.Tango.ArchiveEventPropHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.EventProperties s)
	{
		fr.esrf.Tango.ChangeEventPropHelper.write(out,s.ch_event);
		fr.esrf.Tango.PeriodicEventPropHelper.write(out,s.per_event);
		fr.esrf.Tango.ArchiveEventPropHelper.write(out,s.arch_event);
	}
}
