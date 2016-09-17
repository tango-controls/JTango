package fr.esrf.Tango;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "Device_5".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public class Device_5POATie
	extends Device_5POA
{
	private Device_5Operations _delegate;

	private POA _poa;
	public Device_5POATie(Device_5Operations delegate)
	{
		_delegate = delegate;
	}
	public Device_5POATie(Device_5Operations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public fr.esrf.Tango.Device_5 _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		fr.esrf.Tango.Device_5 __r = fr.esrf.Tango.Device_5Helper.narrow(__o);
		return __r;
	}
	public fr.esrf.Tango.Device_5 _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		fr.esrf.Tango.Device_5 __r = fr.esrf.Tango.Device_5Helper.narrow(__o);
		return __r;
	}
	public Device_5Operations _delegate()
	{
		return _delegate;
	}
	public void _delegate(Device_5Operations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		return super._default_POA();
	}
	public fr.esrf.Tango.AttributeConfig_3[] get_attribute_config_3(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.get_attribute_config_3(names);
	}

	public fr.esrf.Tango.DevAttrHistory[] read_attribute_history_2(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attribute_history_2(name,n);
	}

	public fr.esrf.Tango.DevAttrHistory_4 read_attribute_history_4(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attribute_history_4(name,n);
	}

	public fr.esrf.Tango.DevState state()
	{
		return _delegate.state();
	}

	public fr.esrf.Tango.DevAttrHistory_3[] read_attribute_history_3(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attribute_history_3(name,n);
	}

	public fr.esrf.Tango.DevAttrHistory_5 read_attribute_history_5(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attribute_history_5(name,n);
	}

	public fr.esrf.Tango.AttributeConfig_5[] get_attribute_config_5(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.get_attribute_config_5(names);
	}

	public fr.esrf.Tango.AttributeValue[] read_attributes_2(java.lang.String[] names, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attributes_2(names,source);
	}

	public fr.esrf.Tango.DevPipeData write_read_pipe_5(fr.esrf.Tango.DevPipeData value, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.write_read_pipe_5(value,cl_ident);
	}

	public org.omg.CORBA.Any command_inout_4(java.lang.String command, org.omg.CORBA.Any argin, fr.esrf.Tango.DevSource source, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_inout_4(command,argin,source,cl_ident);
	}

	public fr.esrf.Tango.DevCmdHistory[] command_inout_history_2(java.lang.String command, int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_inout_history_2(command,n);
	}

	public fr.esrf.Tango.DevCmdHistory_4 command_inout_history_4(java.lang.String command, int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_inout_history_4(command,n);
	}

	public void set_attribute_config_4(fr.esrf.Tango.AttributeConfig_3[] new_conf, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed
	{
_delegate.set_attribute_config_4(new_conf,cl_ident);
	}

	public void write_pipe_5(fr.esrf.Tango.DevPipeData value, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed
	{
_delegate.write_pipe_5(value,cl_ident);
	}

	public fr.esrf.Tango.AttributeValue_5[] read_attributes_5(java.lang.String[] names, fr.esrf.Tango.DevSource source, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attributes_5(names,source,cl_ident);
	}

	public void write_attributes_4(fr.esrf.Tango.AttributeValue_4[] values, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.MultiDevFailed,fr.esrf.Tango.DevFailed
	{
_delegate.write_attributes_4(values,cl_ident);
	}

	public fr.esrf.Tango.AttributeValue_4[] write_read_attributes_4(fr.esrf.Tango.AttributeValue_4[] values, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.MultiDevFailed,fr.esrf.Tango.DevFailed
	{
		return _delegate.write_read_attributes_4(values,cl_ident);
	}

	public java.lang.String adm_name()
	{
		return _delegate.adm_name();
	}

	public void write_attributes_3(fr.esrf.Tango.AttributeValue[] values) throws fr.esrf.Tango.MultiDevFailed,fr.esrf.Tango.DevFailed
	{
_delegate.write_attributes_3(values);
	}

	public fr.esrf.Tango.DevInfo_3 info_3() throws fr.esrf.Tango.DevFailed
	{
		return _delegate.info_3();
	}

	public java.lang.String[] black_box(int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.black_box(n);
	}

	public fr.esrf.Tango.PipeConfig[] get_pipe_config_5(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.get_pipe_config_5(names);
	}

	public fr.esrf.Tango.DevCmdInfo_2[] command_list_query_2() throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_list_query_2();
	}

	public fr.esrf.Tango.AttributeValue[] read_attributes(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attributes(names);
	}

	public fr.esrf.Tango.DevCmdInfo_2 command_query_2(java.lang.String command) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_query_2(command);
	}

	public fr.esrf.Tango.AttributeConfig_2[] get_attribute_config_2(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.get_attribute_config_2(names);
	}

	public fr.esrf.Tango.AttributeConfig[] get_attribute_config(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.get_attribute_config(names);
	}

	public void set_attribute_config(fr.esrf.Tango.AttributeConfig[] new_conf) throws fr.esrf.Tango.DevFailed
	{
_delegate.set_attribute_config(new_conf);
	}

	public fr.esrf.Tango.AttributeValue_3[] read_attributes_3(java.lang.String[] names, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attributes_3(names,source);
	}

	public org.omg.CORBA.Any command_inout(java.lang.String command, org.omg.CORBA.Any argin) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_inout(command,argin);
	}

	public void set_pipe_config_5(fr.esrf.Tango.PipeConfig[] new_conf, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed
	{
_delegate.set_pipe_config_5(new_conf,cl_ident);
	}

	public org.omg.CORBA.Any command_inout_2(java.lang.String command, org.omg.CORBA.Any argin, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_inout_2(command,argin,source);
	}

	public fr.esrf.Tango.DevInfo info() throws fr.esrf.Tango.DevFailed
	{
		return _delegate.info();
	}

	public void set_attribute_config_5(fr.esrf.Tango.AttributeConfig_5[] new_conf, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed
	{
_delegate.set_attribute_config_5(new_conf,cl_ident);
	}

	public void set_attribute_config_3(fr.esrf.Tango.AttributeConfig_3[] new_conf) throws fr.esrf.Tango.DevFailed
	{
_delegate.set_attribute_config_3(new_conf);
	}

	public fr.esrf.Tango.AttributeValue_5[] write_read_attributes_5(fr.esrf.Tango.AttributeValue_4[] values, java.lang.String[] r_names, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.MultiDevFailed,fr.esrf.Tango.DevFailed
	{
		return _delegate.write_read_attributes_5(values,r_names,cl_ident);
	}

	public fr.esrf.Tango.DevPipeData read_pipe_5(java.lang.String name, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_pipe_5(name,cl_ident);
	}

	public java.lang.String name()
	{
		return _delegate.name();
	}

	public void ping() throws fr.esrf.Tango.DevFailed
	{
_delegate.ping();
	}

	public java.lang.String status()
	{
		return _delegate.status();
	}

	public java.lang.String description()
	{
		return _delegate.description();
	}

	public fr.esrf.Tango.DevCmdInfo[] command_list_query() throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_list_query();
	}

	public void write_attributes(fr.esrf.Tango.AttributeValue[] values) throws fr.esrf.Tango.DevFailed
	{
_delegate.write_attributes(values);
	}

	public fr.esrf.Tango.DevCmdInfo command_query(java.lang.String command) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_query(command);
	}

	public fr.esrf.Tango.AttributeValue_4[] read_attributes_4(java.lang.String[] names, fr.esrf.Tango.DevSource source, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attributes_4(names,source,cl_ident);
	}

}
