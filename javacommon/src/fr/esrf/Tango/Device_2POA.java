package fr.esrf.Tango;

/**
 *	Generated from IDL interface "Device_2"
 *	@author JacORB IDL compiler V 2.2, 7-May-2004
 */


public abstract class Device_2POA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, fr.esrf.Tango.Device_2Operations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "info", new java.lang.Integer(0));
		m_opsHash.put ( "_get_status", new java.lang.Integer(1));
		m_opsHash.put ( "_get_name", new java.lang.Integer(2));
		m_opsHash.put ( "read_attributes", new java.lang.Integer(3));
		m_opsHash.put ( "read_attributes_2", new java.lang.Integer(4));
		m_opsHash.put ( "_get_adm_name", new java.lang.Integer(5));
		m_opsHash.put ( "get_attribute_config", new java.lang.Integer(6));
		m_opsHash.put ( "command_inout_2", new java.lang.Integer(7));
		m_opsHash.put ( "read_attribute_history_2", new java.lang.Integer(8));
		m_opsHash.put ( "get_attribute_config_2", new java.lang.Integer(9));
		m_opsHash.put ( "command_list_query", new java.lang.Integer(10));
		m_opsHash.put ( "command_list_query_2", new java.lang.Integer(11));
		m_opsHash.put ( "_get_state", new java.lang.Integer(12));
		m_opsHash.put ( "command_inout_history_2", new java.lang.Integer(13));
		m_opsHash.put ( "ping", new java.lang.Integer(14));
		m_opsHash.put ( "write_attributes", new java.lang.Integer(15));
		m_opsHash.put ( "command_inout", new java.lang.Integer(16));
		m_opsHash.put ( "command_query", new java.lang.Integer(17));
		m_opsHash.put ( "command_query_2", new java.lang.Integer(18));
		m_opsHash.put ( "_get_description", new java.lang.Integer(19));
		m_opsHash.put ( "black_box", new java.lang.Integer(20));
		m_opsHash.put ( "set_attribute_config", new java.lang.Integer(21));
	}
	private String[] ids = {"IDL:Tango/Device_2:1.0","IDL:Tango/Device:1.0"};
	public fr.esrf.Tango.Device_2 _this()
	{
		return fr.esrf.Tango.Device_2Helper.narrow(_this_object());
	}
	public fr.esrf.Tango.Device_2 _this(org.omg.CORBA.ORB orb)
	{
		return fr.esrf.Tango.Device_2Helper.narrow(_this_object(orb));
	}
	public org.omg.CORBA.portable.OutputStream _invoke(String method, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.ResponseHandler handler)
		throws org.omg.CORBA.SystemException
	{
		org.omg.CORBA.portable.OutputStream _out = null;
		// do something
		// quick lookup of operation
		java.lang.Integer opsIndex = (java.lang.Integer)m_opsHash.get ( method );
		if ( null == opsIndex )
			throw new org.omg.CORBA.BAD_OPERATION(method + " not found");
		switch ( opsIndex.intValue() )
		{
			case 0: // info
			{
			try
			{
				_out = handler.createReply();
				fr.esrf.Tango.DevInfoHelper.write(_out,info());
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 1: // _get_status
			{
			_out = handler.createReply();
			_out.write_string(status());
				break;
			}
			case 2: // _get_name
			{
			_out = handler.createReply();
			_out.write_string(name());
				break;
			}
			case 3: // read_attributes
			{
			try
			{
				java.lang.String[] _arg0=fr.esrf.Tango.DevVarStringArrayHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.AttributeValueListHelper.write(_out,read_attributes(_arg0));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 4: // read_attributes_2
			{
			try
			{
				java.lang.String[] _arg0=fr.esrf.Tango.DevVarStringArrayHelper.read(_input);
				fr.esrf.Tango.DevSource _arg1=fr.esrf.Tango.DevSourceHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.AttributeValueListHelper.write(_out,read_attributes_2(_arg0,_arg1));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 5: // _get_adm_name
			{
			_out = handler.createReply();
			_out.write_string(adm_name());
				break;
			}
			case 6: // get_attribute_config
			{
			try
			{
				java.lang.String[] _arg0=fr.esrf.Tango.DevVarStringArrayHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.AttributeConfigListHelper.write(_out,get_attribute_config(_arg0));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 7: // command_inout_2
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				org.omg.CORBA.Any _arg1=_input.read_any();
				fr.esrf.Tango.DevSource _arg2=fr.esrf.Tango.DevSourceHelper.read(_input);
				_out = handler.createReply();
				_out.write_any(command_inout_2(_arg0,_arg1,_arg2));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 8: // read_attribute_history_2
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				int _arg1=_input.read_long();
				_out = handler.createReply();
				fr.esrf.Tango.DevAttrHistoryListHelper.write(_out,read_attribute_history_2(_arg0,_arg1));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 9: // get_attribute_config_2
			{
			try
			{
				java.lang.String[] _arg0=fr.esrf.Tango.DevVarStringArrayHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.AttributeConfigList_2Helper.write(_out,get_attribute_config_2(_arg0));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 10: // command_list_query
			{
			try
			{
				_out = handler.createReply();
				fr.esrf.Tango.DevCmdInfoListHelper.write(_out,command_list_query());
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 11: // command_list_query_2
			{
			try
			{
				_out = handler.createReply();
				fr.esrf.Tango.DevCmdInfoList_2Helper.write(_out,command_list_query_2());
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 12: // _get_state
			{
			_out = handler.createReply();
			fr.esrf.Tango.DevStateHelper.write(_out,state());
				break;
			}
			case 13: // command_inout_history_2
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				int _arg1=_input.read_long();
				_out = handler.createReply();
				fr.esrf.Tango.DevCmdHistoryListHelper.write(_out,command_inout_history_2(_arg0,_arg1));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 14: // ping
			{
			try
			{
				_out = handler.createReply();
				ping();
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 15: // write_attributes
			{
			try
			{
				fr.esrf.Tango.AttributeValue[] _arg0=fr.esrf.Tango.AttributeValueListHelper.read(_input);
				_out = handler.createReply();
				write_attributes(_arg0);
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 16: // command_inout
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				org.omg.CORBA.Any _arg1=_input.read_any();
				_out = handler.createReply();
				_out.write_any(command_inout(_arg0,_arg1));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 17: // command_query
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				fr.esrf.Tango.DevCmdInfoHelper.write(_out,command_query(_arg0));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 18: // command_query_2
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				fr.esrf.Tango.DevCmdInfo_2Helper.write(_out,command_query_2(_arg0));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 19: // _get_description
			{
			_out = handler.createReply();
			_out.write_string(description());
				break;
			}
			case 20: // black_box
			{
			try
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				fr.esrf.Tango.DevVarStringArrayHelper.write(_out,black_box(_arg0));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 21: // set_attribute_config
			{
			try
			{
				fr.esrf.Tango.AttributeConfig[] _arg0=fr.esrf.Tango.AttributeConfigListHelper.read(_input);
				_out = handler.createReply();
				set_attribute_config(_arg0);
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}
