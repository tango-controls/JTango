package fr.esrf.Tango;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "Device_4"
 *	@author JacORB IDL compiler V 2.2, 7-May-2004
 */

public class Device_4POATie
	extends Device_4POA
{
	private Device_4Operations _delegate;

	private POA _poa;
	public Device_4POATie(Device_4Operations delegate)
	{
		_delegate = delegate;
	}
	public Device_4POATie(Device_4Operations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public fr.esrf.Tango.Device_4 _this()
	{
		return fr.esrf.Tango.Device_4Helper.narrow(_this_object());
	}
	public fr.esrf.Tango.Device_4 _this(org.omg.CORBA.ORB orb)
	{
		return fr.esrf.Tango.Device_4Helper.narrow(_this_object(orb));
	}
	public Device_4Operations _delegate()
	{
		return _delegate;
	}
	public void _delegate(Device_4Operations delegate)
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
	public void set_attribute_config_4(fr.esrf.Tango.AttributeConfig_3[] new_conf, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed
	{
_delegate.set_attribute_config_4(new_conf,cl_ident);
	}

	public fr.esrf.Tango.AttributeValue_4[] write_read_attributes_4(fr.esrf.Tango.AttributeValue_4[] values, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.MultiDevFailed,fr.esrf.Tango.DevFailed
	{
		return _delegate.write_read_attributes_4(values,cl_ident);
	}

	public void write_attributes_3(fr.esrf.Tango.AttributeValue[] values) throws fr.esrf.Tango.MultiDevFailed,fr.esrf.Tango.DevFailed
	{
_delegate.write_attributes_3(values);
	}

	public fr.esrf.Tango.DevInfo info() throws fr.esrf.Tango.DevFailed
	{
		return _delegate.info();
	}

	public java.lang.String status()
	{
		return _delegate.status();
	}

	public void set_attribute_config_3(fr.esrf.Tango.AttributeConfig_3[] new_conf) throws fr.esrf.Tango.DevFailed
	{
_delegate.set_attribute_config_3(new_conf);
	}

	public java.lang.String name()
	{
		return _delegate.name();
	}

	public fr.esrf.Tango.AttributeValue[] read_attributes(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attributes(names);
	}

	public fr.esrf.Tango.DevAttrHistory_3[] read_attribute_history_3(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attribute_history_3(name,n);
	}

	public fr.esrf.Tango.AttributeValue[] read_attributes_2(java.lang.String[] names, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attributes_2(names,source);
	}

	public java.lang.String adm_name()
	{
		return _delegate.adm_name();
	}

	public fr.esrf.Tango.DevInfo_3 info_3() throws fr.esrf.Tango.DevFailed
	{
		return _delegate.info_3();
	}

	public org.omg.CORBA.Any command_inout_2(java.lang.String command, org.omg.CORBA.Any argin, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_inout_2(command,argin,source);
	}

	public fr.esrf.Tango.AttributeConfig[] get_attribute_config(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.get_attribute_config(names);
	}

	public fr.esrf.Tango.DevAttrHistory[] read_attribute_history_2(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attribute_history_2(name,n);
	}

	public fr.esrf.Tango.DevCmdInfo[] command_list_query() throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_list_query();
	}

	public fr.esrf.Tango.AttributeConfig_2[] get_attribute_config_2(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.get_attribute_config_2(names);
	}

	public void write_attributes_4(fr.esrf.Tango.AttributeValue_4[] values, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.MultiDevFailed,fr.esrf.Tango.DevFailed
	{
_delegate.write_attributes_4(values,cl_ident);
	}

	public fr.esrf.Tango.AttributeConfig_3[] get_attribute_config_3(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.get_attribute_config_3(names);
	}

	public fr.esrf.Tango.DevCmdInfo_2[] command_list_query_2() throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_list_query_2();
	}

	public fr.esrf.Tango.DevState state()
	{
		return _delegate.state();
	}

	public fr.esrf.Tango.DevAttrHistory_4 read_attribute_history_4(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attribute_history_4(name,n);
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

	public fr.esrf.Tango.DevCmdHistory_4 command_inout_history_4(java.lang.String command, int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_inout_history_4(command,n);
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

	public fr.esrf.Tango.AttributeValue_3[] read_attributes_3(java.lang.String[] names, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attributes_3(names,source);
	}

	public fr.esrf.Tango.AttributeValue_4[] read_attributes_4(java.lang.String[] names, fr.esrf.Tango.DevSource source, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attributes_4(names,source,cl_ident);
	}

	public java.lang.String[] black_box(int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.black_box(n);
	}

	public void set_attribute_config(fr.esrf.Tango.AttributeConfig[] new_conf) throws fr.esrf.Tango.DevFailed
	{
_delegate.set_attribute_config(new_conf);
	}

	public org.omg.CORBA.Any command_inout_4(java.lang.String command, org.omg.CORBA.Any argin, fr.esrf.Tango.DevSource source, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_inout_4(command,argin,source,cl_ident);
	}

}
