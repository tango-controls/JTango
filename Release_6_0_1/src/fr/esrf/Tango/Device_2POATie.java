package fr.esrf.Tango;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "Device_2"
 *	@author JacORB IDL compiler V 2.2, 7-May-2004
 */

public class Device_2POATie
	extends Device_2POA
{
	private Device_2Operations _delegate;

	private POA _poa;
	public Device_2POATie(Device_2Operations delegate)
	{
		_delegate = delegate;
	}
	public Device_2POATie(Device_2Operations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public fr.esrf.Tango.Device_2 _this()
	{
		return fr.esrf.Tango.Device_2Helper.narrow(_this_object());
	}
	public fr.esrf.Tango.Device_2 _this(org.omg.CORBA.ORB orb)
	{
		return fr.esrf.Tango.Device_2Helper.narrow(_this_object(orb));
	}
	public Device_2Operations _delegate()
	{
		return _delegate;
	}
	public void _delegate(Device_2Operations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		else
		{
			return super._default_POA();
		}
	}
	public fr.esrf.Tango.DevInfo info() throws fr.esrf.Tango.DevFailed
	{
		return _delegate.info();
	}

	public java.lang.String status()
	{
		return _delegate.status();
	}

	public java.lang.String name()
	{
		return _delegate.name();
	}

	public fr.esrf.Tango.AttributeValue[] read_attributes(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attributes(names);
	}

	public fr.esrf.Tango.AttributeValue[] read_attributes_2(java.lang.String[] names, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attributes_2(names,source);
	}

	public java.lang.String adm_name()
	{
		return _delegate.adm_name();
	}

	public fr.esrf.Tango.AttributeConfig[] get_attribute_config(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.get_attribute_config(names);
	}

	public org.omg.CORBA.Any command_inout_2(java.lang.String command, org.omg.CORBA.Any argin, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_inout_2(command,argin,source);
	}

	public fr.esrf.Tango.DevAttrHistory[] read_attribute_history_2(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attribute_history_2(name,n);
	}

	public fr.esrf.Tango.AttributeConfig_2[] get_attribute_config_2(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.get_attribute_config_2(names);
	}

	public fr.esrf.Tango.DevCmdInfo[] command_list_query() throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_list_query();
	}

	public fr.esrf.Tango.DevCmdInfo_2[] command_list_query_2() throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_list_query_2();
	}

	public fr.esrf.Tango.DevState state()
	{
		return _delegate.state();
	}

	public fr.esrf.Tango.DevCmdHistory[] command_inout_history_2(java.lang.String command, int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_inout_history_2(command,n);
	}

	public void ping() throws fr.esrf.Tango.DevFailed
	{
_delegate.ping();
	}

	public void write_attributes(fr.esrf.Tango.AttributeValue[] values) throws fr.esrf.Tango.DevFailed
	{
_delegate.write_attributes(values);
	}

	public org.omg.CORBA.Any command_inout(java.lang.String command, org.omg.CORBA.Any argin) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_inout(command,argin);
	}

	public fr.esrf.Tango.DevCmdInfo command_query(java.lang.String command) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_query(command);
	}

	public fr.esrf.Tango.DevCmdInfo_2 command_query_2(java.lang.String command) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_query_2(command);
	}

	public java.lang.String description()
	{
		return _delegate.description();
	}

	public java.lang.String[] black_box(int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.black_box(n);
	}

	public void set_attribute_config(fr.esrf.Tango.AttributeConfig[] new_conf) throws fr.esrf.Tango.DevFailed
	{
_delegate.set_attribute_config(new_conf);
	}

}
