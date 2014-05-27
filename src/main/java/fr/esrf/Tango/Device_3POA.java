package fr.esrf.Tango;


/**
 * Generated from IDL interface "Device_3".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public abstract class Device_3POA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, fr.esrf.Tango.Device_3Operations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "write_attributes_3", Integer.valueOf(0));
		m_opsHash.put ( "info", Integer.valueOf(1));
		m_opsHash.put ( "_get_status", Integer.valueOf(2));
		m_opsHash.put ( "set_attribute_config_3", Integer.valueOf(3));
		m_opsHash.put ( "_get_name", Integer.valueOf(4));
		m_opsHash.put ( "read_attributes", Integer.valueOf(5));
		m_opsHash.put ( "read_attribute_history_3", Integer.valueOf(6));
		m_opsHash.put ( "read_attributes_2", Integer.valueOf(7));
		m_opsHash.put ( "_get_adm_name", Integer.valueOf(8));
		m_opsHash.put ( "info_3", Integer.valueOf(9));
		m_opsHash.put ( "get_attribute_config", Integer.valueOf(10));
		m_opsHash.put ( "command_inout_2", Integer.valueOf(11));
		m_opsHash.put ( "read_attribute_history_2", Integer.valueOf(12));
		m_opsHash.put ( "get_attribute_config_2", Integer.valueOf(13));
		m_opsHash.put ( "command_list_query", Integer.valueOf(14));
		m_opsHash.put ( "command_list_query_2", Integer.valueOf(15));
		m_opsHash.put ( "get_attribute_config_3", Integer.valueOf(16));
		m_opsHash.put ( "_get_state", Integer.valueOf(17));
		m_opsHash.put ( "command_inout_history_2", Integer.valueOf(18));
		m_opsHash.put ( "ping", Integer.valueOf(19));
		m_opsHash.put ( "write_attributes", Integer.valueOf(20));
		m_opsHash.put ( "command_inout", Integer.valueOf(21));
		m_opsHash.put ( "command_query", Integer.valueOf(22));
		m_opsHash.put ( "command_query_2", Integer.valueOf(23));
		m_opsHash.put ( "_get_description", Integer.valueOf(24));
		m_opsHash.put ( "read_attributes_3", Integer.valueOf(25));
		m_opsHash.put ( "set_attribute_config", Integer.valueOf(26));
		m_opsHash.put ( "black_box", Integer.valueOf(27));
	}
	private String[] ids = {"IDL:Tango/Device_3:1.0","IDL:Tango/Device_2:1.0","IDL:Tango/Device:1.0"};
	public fr.esrf.Tango.Device_3 _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		fr.esrf.Tango.Device_3 __r = fr.esrf.Tango.Device_3Helper.narrow(__o);
		if (__o != null && __o != __r)
		{
			((org.omg.CORBA.portable.ObjectImpl)__o)._set_delegate(null);

		}
		return __r;
	}
	public fr.esrf.Tango.Device_3 _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		fr.esrf.Tango.Device_3 __r = fr.esrf.Tango.Device_3Helper.narrow(__o);
		if (__o != null && __o != __r)
		{
			((org.omg.CORBA.portable.ObjectImpl)__o)._set_delegate(null);

		}
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
			case 0: // write_attributes_3
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
			case 1: // info
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
			case 2: // _get_status
			{
			_out = handler.createReply();
			java.lang.String tmpResult111 = status();
_out.write_string( tmpResult111 );
				break;
			}
			case 3: // set_attribute_config_3
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
			case 4: // _get_name
			{
			_out = handler.createReply();
			java.lang.String tmpResult112 = name();
_out.write_string( tmpResult112 );
				break;
			}
			case 5: // read_attributes
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
			case 6: // read_attribute_history_3
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
			case 8: // _get_adm_name
			{
			_out = handler.createReply();
			java.lang.String tmpResult113 = adm_name();
_out.write_string( tmpResult113 );
				break;
			}
			case 9: // info_3
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
			case 10: // get_attribute_config
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
			case 11: // command_inout_2
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
			case 12: // read_attribute_history_2
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
			case 13: // get_attribute_config_2
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
			case 14: // command_list_query
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
			case 15: // command_list_query_2
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
			case 16: // get_attribute_config_3
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
			case 17: // _get_state
			{
			_out = handler.createReply();
			fr.esrf.Tango.DevStateHelper.write(_out,state());
				break;
			}
			case 18: // command_inout_history_2
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
			case 19: // ping
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
			case 20: // write_attributes
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
			case 21: // command_inout
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
			case 22: // command_query
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
			case 23: // command_query_2
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
			case 24: // _get_description
			{
			_out = handler.createReply();
			java.lang.String tmpResult114 = description();
_out.write_string( tmpResult114 );
				break;
			}
			case 25: // read_attributes_3
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
			case 26: // set_attribute_config
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
			case 27: // black_box
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
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}
