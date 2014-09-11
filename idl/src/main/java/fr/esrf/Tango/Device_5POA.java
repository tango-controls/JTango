package fr.esrf.Tango;


/**
 * Generated from IDL interface "Device_5".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class Device_5POA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, fr.esrf.Tango.Device_5Operations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "get_attribute_config_3", Integer.valueOf(0));
		m_opsHash.put ( "read_attribute_history_2", Integer.valueOf(1));
		m_opsHash.put ( "read_attribute_history_4", Integer.valueOf(2));
		m_opsHash.put ( "_get_state", Integer.valueOf(3));
		m_opsHash.put ( "read_attribute_history_3", Integer.valueOf(4));
		m_opsHash.put ( "read_attribute_history_5", Integer.valueOf(5));
		m_opsHash.put ( "get_attribute_config_5", Integer.valueOf(6));
		m_opsHash.put ( "read_attributes_2", Integer.valueOf(7));
		m_opsHash.put ( "write_read_pipe_5", Integer.valueOf(8));
		m_opsHash.put ( "command_inout_4", Integer.valueOf(9));
		m_opsHash.put ( "command_inout_history_2", Integer.valueOf(10));
		m_opsHash.put ( "command_inout_history_4", Integer.valueOf(11));
		m_opsHash.put ( "set_attribute_config_4", Integer.valueOf(12));
		m_opsHash.put ( "write_pipe_5", Integer.valueOf(13));
		m_opsHash.put ( "read_attributes_5", Integer.valueOf(14));
		m_opsHash.put ( "write_attributes_4", Integer.valueOf(15));
		m_opsHash.put ( "write_read_attributes_4", Integer.valueOf(16));
		m_opsHash.put ( "_get_adm_name", Integer.valueOf(17));
		m_opsHash.put ( "write_attributes_3", Integer.valueOf(18));
		m_opsHash.put ( "info_3", Integer.valueOf(19));
		m_opsHash.put ( "black_box", Integer.valueOf(20));
		m_opsHash.put ( "get_pipe_config_5", Integer.valueOf(21));
		m_opsHash.put ( "command_list_query_2", Integer.valueOf(22));
		m_opsHash.put ( "read_attributes", Integer.valueOf(23));
		m_opsHash.put ( "command_query_2", Integer.valueOf(24));
		m_opsHash.put ( "get_attribute_config_2", Integer.valueOf(25));
		m_opsHash.put ( "get_attribute_config", Integer.valueOf(26));
		m_opsHash.put ( "set_attribute_config", Integer.valueOf(27));
		m_opsHash.put ( "read_attributes_3", Integer.valueOf(28));
		m_opsHash.put ( "command_inout", Integer.valueOf(29));
		m_opsHash.put ( "set_pipe_config_5", Integer.valueOf(30));
		m_opsHash.put ( "command_inout_2", Integer.valueOf(31));
		m_opsHash.put ( "info", Integer.valueOf(32));
		m_opsHash.put ( "set_attribute_config_5", Integer.valueOf(33));
		m_opsHash.put ( "set_attribute_config_3", Integer.valueOf(34));
		m_opsHash.put ( "write_read_attributes_5", Integer.valueOf(35));
		m_opsHash.put ( "read_pipe_5", Integer.valueOf(36));
		m_opsHash.put ( "_get_name", Integer.valueOf(37));
		m_opsHash.put ( "ping", Integer.valueOf(38));
		m_opsHash.put ( "_get_status", Integer.valueOf(39));
		m_opsHash.put ( "_get_description", Integer.valueOf(40));
		m_opsHash.put ( "command_list_query", Integer.valueOf(41));
		m_opsHash.put ( "write_attributes", Integer.valueOf(42));
		m_opsHash.put ( "command_query", Integer.valueOf(43));
		m_opsHash.put ( "read_attributes_4", Integer.valueOf(44));
	}
	private String[] ids = {"IDL:Tango/Device_5:1.0","IDL:Tango/Device_2:1.0","IDL:Tango/Device_3:1.0","IDL:Tango/Device_4:1.0","IDL:Tango/Device:1.0"};
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
			case 0: // get_attribute_config_3
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
			case 1: // read_attribute_history_2
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
			case 2: // read_attribute_history_4
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
			case 3: // _get_state
			{
			_out = handler.createReply();
			fr.esrf.Tango.DevStateHelper.write(_out,state());
				break;
			}
			case 4: // read_attribute_history_3
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
			case 5: // read_attribute_history_5
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				int _arg1=_input.read_long();
				_out = handler.createReply();
				fr.esrf.Tango.DevAttrHistory_5Helper.write(_out,read_attribute_history_5(_arg0,_arg1));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 6: // get_attribute_config_5
			{
			try
			{
				java.lang.String[] _arg0=fr.esrf.Tango.DevVarStringArrayHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.AttributeConfigList_5Helper.write(_out,get_attribute_config_5(_arg0));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 7: // read_attributes_2
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
			case 8: // write_read_pipe_5
			{
			try
			{
				fr.esrf.Tango.DevPipeData _arg0=fr.esrf.Tango.DevPipeDataHelper.read(_input);
				fr.esrf.Tango.ClntIdent _arg1=fr.esrf.Tango.ClntIdentHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.DevPipeDataHelper.write(_out,write_read_pipe_5(_arg0,_arg1));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 9: // command_inout_4
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
			case 10: // command_inout_history_2
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
			case 11: // command_inout_history_4
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
			case 12: // set_attribute_config_4
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
			case 13: // write_pipe_5
			{
			try
			{
				fr.esrf.Tango.DevPipeData _arg0=fr.esrf.Tango.DevPipeDataHelper.read(_input);
				fr.esrf.Tango.ClntIdent _arg1=fr.esrf.Tango.ClntIdentHelper.read(_input);
				_out = handler.createReply();
				write_pipe_5(_arg0,_arg1);
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 14: // read_attributes_5
			{
			try
			{
				java.lang.String[] _arg0=fr.esrf.Tango.DevVarStringArrayHelper.read(_input);
				fr.esrf.Tango.DevSource _arg1=fr.esrf.Tango.DevSourceHelper.read(_input);
				fr.esrf.Tango.ClntIdent _arg2=fr.esrf.Tango.ClntIdentHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.AttributeValueList_5Helper.write(_out,read_attributes_5(_arg0,_arg1,_arg2));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 15: // write_attributes_4
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
			case 16: // write_read_attributes_4
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
			case 17: // _get_adm_name
			{
			_out = handler.createReply();
			java.lang.String tmpResult148 = adm_name();
_out.write_string( tmpResult148 );
				break;
			}
			case 18: // write_attributes_3
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
			case 19: // info_3
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
			case 21: // get_pipe_config_5
			{
			try
			{
				java.lang.String[] _arg0=fr.esrf.Tango.DevVarStringArrayHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.PipeConfigListHelper.write(_out,get_pipe_config_5(_arg0));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 22: // command_list_query_2
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
			case 23: // read_attributes
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
			case 24: // command_query_2
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
			case 25: // get_attribute_config_2
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
			case 26: // get_attribute_config
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
			case 27: // set_attribute_config
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
			case 28: // read_attributes_3
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
			case 29: // command_inout
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
			case 30: // set_pipe_config_5
			{
			try
			{
				fr.esrf.Tango.PipeConfig[] _arg0=fr.esrf.Tango.PipeConfigListHelper.read(_input);
				fr.esrf.Tango.ClntIdent _arg1=fr.esrf.Tango.ClntIdentHelper.read(_input);
				_out = handler.createReply();
				set_pipe_config_5(_arg0,_arg1);
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 31: // command_inout_2
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
			case 32: // info
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
			case 33: // set_attribute_config_5
			{
			try
			{
				fr.esrf.Tango.AttributeConfig_5[] _arg0=fr.esrf.Tango.AttributeConfigList_5Helper.read(_input);
				fr.esrf.Tango.ClntIdent _arg1=fr.esrf.Tango.ClntIdentHelper.read(_input);
				_out = handler.createReply();
				set_attribute_config_5(_arg0,_arg1);
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 34: // set_attribute_config_3
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
			case 35: // write_read_attributes_5
			{
			try
			{
				fr.esrf.Tango.AttributeValue_4[] _arg0=fr.esrf.Tango.AttributeValueList_4Helper.read(_input);
				java.lang.String[] _arg1=fr.esrf.Tango.DevVarStringArrayHelper.read(_input);
				fr.esrf.Tango.ClntIdent _arg2=fr.esrf.Tango.ClntIdentHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.AttributeValueList_5Helper.write(_out,write_read_attributes_5(_arg0,_arg1,_arg2));
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
			case 36: // read_pipe_5
			{
			try
			{
				java.lang.String _arg0=_input.read_string();
				fr.esrf.Tango.ClntIdent _arg1=fr.esrf.Tango.ClntIdentHelper.read(_input);
				_out = handler.createReply();
				fr.esrf.Tango.DevPipeDataHelper.write(_out,read_pipe_5(_arg0,_arg1));
			}
			catch(fr.esrf.Tango.DevFailed _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esrf.Tango.DevFailedHelper.write(_out, _ex0);
			}
				break;
			}
			case 37: // _get_name
			{
			_out = handler.createReply();
			java.lang.String tmpResult149 = name();
_out.write_string( tmpResult149 );
				break;
			}
			case 38: // ping
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
			case 39: // _get_status
			{
			_out = handler.createReply();
			java.lang.String tmpResult150 = status();
_out.write_string( tmpResult150 );
				break;
			}
			case 40: // _get_description
			{
			_out = handler.createReply();
			java.lang.String tmpResult151 = description();
_out.write_string( tmpResult151 );
				break;
			}
			case 41: // command_list_query
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
			case 42: // write_attributes
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
			case 43: // command_query
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
			case 44: // read_attributes_4
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
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}
