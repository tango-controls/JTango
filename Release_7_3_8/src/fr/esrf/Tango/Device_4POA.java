package fr.esrf.Tango;

/**
 *	Generated from IDL interface "Device_4"
 *	@author JacORB IDL compiler V 2.2, 7-May-2004
 */


public abstract class Device_4POA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, fr.esrf.Tango.Device_4Operations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "set_attribute_config_4", new java.lang.Integer(0));
		m_opsHash.put ( "write_read_attributes_4", new java.lang.Integer(1));
		m_opsHash.put ( "write_attributes_3", new java.lang.Integer(2));
		m_opsHash.put ( "info", new java.lang.Integer(3));
		m_opsHash.put ( "_get_status", new java.lang.Integer(4));
		m_opsHash.put ( "set_attribute_config_3", new java.lang.Integer(5));
		m_opsHash.put ( "_get_name", new java.lang.Integer(6));
		m_opsHash.put ( "read_attributes", new java.lang.Integer(7));
		m_opsHash.put ( "read_attribute_history_3", new java.lang.Integer(8));
		m_opsHash.put ( "read_attributes_2", new java.lang.Integer(9));
		m_opsHash.put ( "_get_adm_name", new java.lang.Integer(10));
		m_opsHash.put ( "info_3", new java.lang.Integer(11));
		m_opsHash.put ( "command_inout_2", new java.lang.Integer(12));
		m_opsHash.put ( "get_attribute_config", new java.lang.Integer(13));
		m_opsHash.put ( "read_attribute_history_2", new java.lang.Integer(14));
		m_opsHash.put ( "command_list_query", new java.lang.Integer(15));
		m_opsHash.put ( "get_attribute_config_2", new java.lang.Integer(16));
		m_opsHash.put ( "write_attributes_4", new java.lang.Integer(17));
		m_opsHash.put ( "get_attribute_config_3", new java.lang.Integer(18));
		m_opsHash.put ( "command_list_query_2", new java.lang.Integer(19));
		m_opsHash.put ( "_get_state", new java.lang.Integer(20));
		m_opsHash.put ( "read_attribute_history_4", new java.lang.Integer(21));
		m_opsHash.put ( "command_inout_history_2", new java.lang.Integer(22));
		m_opsHash.put ( "ping", new java.lang.Integer(23));
		m_opsHash.put ( "write_attributes", new java.lang.Integer(24));
		m_opsHash.put ( "command_inout", new java.lang.Integer(25));
		m_opsHash.put ( "command_inout_history_4", new java.lang.Integer(26));
		m_opsHash.put ( "command_query", new java.lang.Integer(27));
		m_opsHash.put ( "command_query_2", new java.lang.Integer(28));
		m_opsHash.put ( "_get_description", new java.lang.Integer(29));
		m_opsHash.put ( "read_attributes_3", new java.lang.Integer(30));
		m_opsHash.put ( "read_attributes_4", new java.lang.Integer(31));
		m_opsHash.put ( "black_box", new java.lang.Integer(32));
		m_opsHash.put ( "set_attribute_config", new java.lang.Integer(33));
		m_opsHash.put ( "command_inout_4", new java.lang.Integer(34));
	}
	private String[] ids = {"IDL:Tango/Device_4:1.0","IDL:Tango/Device_2:1.0","IDL:Tango/Device:1.0","IDL:Tango/Device_3:1.0"};
	public fr.esrf.Tango.Device_4 _this()
	{
		return fr.esrf.Tango.Device_4Helper.narrow(_this_object());
	}
	public fr.esrf.Tango.Device_4 _this(org.omg.CORBA.ORB orb)
	{
		return fr.esrf.Tango.Device_4Helper.narrow(_this_object(orb));
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
			case 0: // set_attribute_config_4
			{
			try
			{
				fr.esrf.Tango.AttributeConfig_3[] _arg0=fr.esrf.Tango.AttributeConfigList_3Helper.read(_input);
				fr.esrf.Tango.ClntIdent _arg1=fr.esrf.Tango.ClntIdentHelper.read(_input);
				_out = handler.createReply();
				set_attribute_config_4(_arg0,_arg1);
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 1: // write_read_attributes_4
			{
			try
			{
				fr.esrf.Tango.AttributeValue_4[] _arg0=fr.esrf.Tango.AttributeValueList_4Helper.read(_input);
				fr.esrf.Tango.ClntIdent _arg1=fr.esrf.Tango.ClntIdentHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.AttributeValueList_4Helper.write(_out,write_read_attributes_4(_arg0,_arg1));
			}
			catch(fr.esrf.Tango.MultiDevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.MultiDevFailedHelper.write(_out, _ex0);
			}
			catch(fr.esrf.Tango.DevFailed _ex1)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex1);
			}
				break;
			}
			case 2: // write_attributes_3
			{
			try
			{
				fr.esrf.Tango.AttributeValue[] _arg0=fr.esrf.Tango.AttributeValueListHelper.read(_input);
				_out = handler.createReply();
				write_attributes_3(_arg0);
			}
			catch(fr.esrf.Tango.MultiDevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.MultiDevFailedHelper.write(_out, _ex0);
			}
			catch(fr.esrf.Tango.DevFailed _ex1)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex1);
			}
				break;
			}
			case 3: // info
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
			case 4: // _get_status
			{
			_out = handler.createReply();
			_out.write_string(status());
				break;
			}
			case 5: // set_attribute_config_3
			{
			try
			{
				fr.esrf.Tango.AttributeConfig_3[] _arg0=fr.esrf.Tango.AttributeConfigList_3Helper.read(_input);
				_out = handler.createReply();
				set_attribute_config_3(_arg0);
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 6: // _get_name
			{
			_out = handler.createReply();
			_out.write_string(name());
				break;
			}
			case 7: // read_attributes
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
			case 8: // read_attribute_history_3
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				int _arg1=_input.read_long();
				_out = handler.createReply();
				fr.esrf.Tango.DevAttrHistoryList_3Helper.write(_out,read_attribute_history_3(_arg0,_arg1));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 9: // read_attributes_2
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
			case 10: // _get_adm_name
			{
			_out = handler.createReply();
			_out.write_string(adm_name());
				break;
			}
			case 11: // info_3
			{
			try
			{
				_out = handler.createReply();
				fr.esrf.Tango.DevInfo_3Helper.write(_out,info_3());
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 12: // command_inout_2
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
			case 13: // get_attribute_config
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
			case 14: // read_attribute_history_2
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
			case 15: // command_list_query
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
			case 16: // get_attribute_config_2
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
			case 17: // write_attributes_4
			{
			try
			{
				fr.esrf.Tango.AttributeValue_4[] _arg0=fr.esrf.Tango.AttributeValueList_4Helper.read(_input);
				fr.esrf.Tango.ClntIdent _arg1=fr.esrf.Tango.ClntIdentHelper.read(_input);
				_out = handler.createReply();
				write_attributes_4(_arg0,_arg1);
			}
			catch(fr.esrf.Tango.MultiDevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.MultiDevFailedHelper.write(_out, _ex0);
			}
			catch(fr.esrf.Tango.DevFailed _ex1)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex1);
			}
				break;
			}
			case 18: // get_attribute_config_3
			{
			try
			{
				java.lang.String[] _arg0=fr.esrf.Tango.DevVarStringArrayHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.AttributeConfigList_3Helper.write(_out,get_attribute_config_3(_arg0));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 19: // command_list_query_2
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
			case 20: // _get_state
			{
			_out = handler.createReply();
			fr.esrf.Tango.DevStateHelper.write(_out,state());
				break;
			}
			case 21: // read_attribute_history_4
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				int _arg1=_input.read_long();
				_out = handler.createReply();
				fr.esrf.Tango.DevAttrHistory_4Helper.write(_out,read_attribute_history_4(_arg0,_arg1));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 22: // command_inout_history_2
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
			case 23: // ping
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
			case 24: // write_attributes
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
			case 25: // command_inout
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
			case 26: // command_inout_history_4
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				int _arg1=_input.read_long();
				_out = handler.createReply();
				fr.esrf.Tango.DevCmdHistory_4Helper.write(_out,command_inout_history_4(_arg0,_arg1));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 27: // command_query
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
			case 28: // command_query_2
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
			case 29: // _get_description
			{
			_out = handler.createReply();
			_out.write_string(description());
				break;
			}
			case 30: // read_attributes_3
			{
			try
			{
				java.lang.String[] _arg0=fr.esrf.Tango.DevVarStringArrayHelper.read(_input);
				fr.esrf.Tango.DevSource _arg1=fr.esrf.Tango.DevSourceHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.AttributeValueList_3Helper.write(_out,read_attributes_3(_arg0,_arg1));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 31: // read_attributes_4
			{
			try
			{
				java.lang.String[] _arg0=fr.esrf.Tango.DevVarStringArrayHelper.read(_input);
				fr.esrf.Tango.DevSource _arg1=fr.esrf.Tango.DevSourceHelper.read(_input);
				fr.esrf.Tango.ClntIdent _arg2=fr.esrf.Tango.ClntIdentHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.AttributeValueList_4Helper.write(_out,read_attributes_4(_arg0,_arg1,_arg2));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 32: // black_box
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
			case 33: // set_attribute_config
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
			case 34: // command_inout_4
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				org.omg.CORBA.Any _arg1=_input.read_any();
				fr.esrf.Tango.DevSource _arg2=fr.esrf.Tango.DevSourceHelper.read(_input);
				fr.esrf.Tango.ClntIdent _arg3=fr.esrf.Tango.ClntIdentHelper.read(_input);
				_out = handler.createReply();
				_out.write_any(command_inout_4(_arg0,_arg1,_arg2,_arg3));
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
