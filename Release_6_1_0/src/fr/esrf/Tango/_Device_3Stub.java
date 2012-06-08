package fr.esrf.Tango;


/**
 *	Generated from IDL interface "Device_3"
 *	@author JacORB IDL compiler V 2.2, 7-May-2004
 */

public class _Device_3Stub
	extends org.omg.CORBA.portable.ObjectImpl
	implements fr.esrf.Tango.Device_3
{
	private String[] ids = {"IDL:Tango/Device_3:1.0","IDL:Tango/Device_2:1.0","IDL:Tango/Device:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = fr.esrf.Tango.Device_3Operations.class;
	public void write_attributes_3(fr.esrf.Tango.AttributeValue[] values) throws fr.esrf.Tango.MultiDevFailed,fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "write_attributes_3", true);
				fr.esrf.Tango.AttributeValueListHelper.write(_os,values);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/MultiDevFailed:1.0"))
				{
					throw fr.esrf.Tango.MultiDevFailedHelper.read(_ax.getInputStream());
				}
				else if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "write_attributes_3", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			try
			{
			_localServant.write_attributes_3(values);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public fr.esrf.Tango.DevInfo info() throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "info", true);
				_is = _invoke(_os);
				fr.esrf.Tango.DevInfo _result = fr.esrf.Tango.DevInfoHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "info", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.DevInfo _result;			try
			{
			_result = _localServant.info();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public java.lang.String status()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request("_get_status",true);
				_is = _invoke(_os);
				return _is.read_string();
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}

		else
		{
		org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "_get_status", _opsClass);
		if( _so == null )
			throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			java.lang.String _result;
		try
		{
			_result = _localServant.status();
		}
		finally
		{
			_servant_postinvoke(_so);
		}
		return _result;
		}
		}

	}

	public void set_attribute_config_3(fr.esrf.Tango.AttributeConfig_3[] new_conf) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "set_attribute_config_3", true);
				fr.esrf.Tango.AttributeConfigList_3Helper.write(_os,new_conf);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "set_attribute_config_3", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			try
			{
			_localServant.set_attribute_config_3(new_conf);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public java.lang.String name()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request("_get_name",true);
				_is = _invoke(_os);
				return _is.read_string();
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}

		else
		{
		org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "_get_name", _opsClass);
		if( _so == null )
			throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			java.lang.String _result;
		try
		{
			_result = _localServant.name();
		}
		finally
		{
			_servant_postinvoke(_so);
		}
		return _result;
		}
		}

	}

	public fr.esrf.Tango.AttributeValue[] read_attributes(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "read_attributes", true);
				fr.esrf.Tango.DevVarStringArrayHelper.write(_os,names);
				_is = _invoke(_os);
				fr.esrf.Tango.AttributeValue[] _result = fr.esrf.Tango.AttributeValueListHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "read_attributes", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.AttributeValue[] _result;			try
			{
			_result = _localServant.read_attributes(names);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public fr.esrf.Tango.DevAttrHistory_3[] read_attribute_history_3(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "read_attribute_history_3", true);
				_os.write_string(name);
				_os.write_long(n);
				_is = _invoke(_os);
				fr.esrf.Tango.DevAttrHistory_3[] _result = fr.esrf.Tango.DevAttrHistoryList_3Helper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "read_attribute_history_3", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.DevAttrHistory_3[] _result;			try
			{
			_result = _localServant.read_attribute_history_3(name,n);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public fr.esrf.Tango.AttributeValue[] read_attributes_2(java.lang.String[] names, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "read_attributes_2", true);
				fr.esrf.Tango.DevVarStringArrayHelper.write(_os,names);
				fr.esrf.Tango.DevSourceHelper.write(_os,source);
				_is = _invoke(_os);
				fr.esrf.Tango.AttributeValue[] _result = fr.esrf.Tango.AttributeValueListHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "read_attributes_2", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.AttributeValue[] _result;			try
			{
			_result = _localServant.read_attributes_2(names,source);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public java.lang.String adm_name()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request("_get_adm_name",true);
				_is = _invoke(_os);
				return _is.read_string();
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}

		else
		{
		org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "_get_adm_name", _opsClass);
		if( _so == null )
			throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			java.lang.String _result;
		try
		{
			_result = _localServant.adm_name();
		}
		finally
		{
			_servant_postinvoke(_so);
		}
		return _result;
		}
		}

	}

	public fr.esrf.Tango.DevInfo_3 info_3() throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "info_3", true);
				_is = _invoke(_os);
				fr.esrf.Tango.DevInfo_3 _result = fr.esrf.Tango.DevInfo_3Helper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "info_3", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.DevInfo_3 _result;			try
			{
			_result = _localServant.info_3();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public fr.esrf.Tango.AttributeConfig[] get_attribute_config(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "get_attribute_config", true);
				fr.esrf.Tango.DevVarStringArrayHelper.write(_os,names);
				_is = _invoke(_os);
				fr.esrf.Tango.AttributeConfig[] _result = fr.esrf.Tango.AttributeConfigListHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "get_attribute_config", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.AttributeConfig[] _result;			try
			{
			_result = _localServant.get_attribute_config(names);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public org.omg.CORBA.Any command_inout_2(java.lang.String command, org.omg.CORBA.Any argin, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "command_inout_2", true);
				_os.write_string(command);
				_os.write_any(argin);
				fr.esrf.Tango.DevSourceHelper.write(_os,source);
				_is = _invoke(_os);
				org.omg.CORBA.Any _result = _is.read_any();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "command_inout_2", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			org.omg.CORBA.Any _result;			try
			{
			_result = _localServant.command_inout_2(command,argin,source);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public fr.esrf.Tango.DevAttrHistory[] read_attribute_history_2(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "read_attribute_history_2", true);
				_os.write_string(name);
				_os.write_long(n);
				_is = _invoke(_os);
				fr.esrf.Tango.DevAttrHistory[] _result = fr.esrf.Tango.DevAttrHistoryListHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "read_attribute_history_2", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.DevAttrHistory[] _result;			try
			{
			_result = _localServant.read_attribute_history_2(name,n);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public fr.esrf.Tango.AttributeConfig_2[] get_attribute_config_2(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "get_attribute_config_2", true);
				fr.esrf.Tango.DevVarStringArrayHelper.write(_os,names);
				_is = _invoke(_os);
				fr.esrf.Tango.AttributeConfig_2[] _result = fr.esrf.Tango.AttributeConfigList_2Helper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "get_attribute_config_2", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.AttributeConfig_2[] _result;			try
			{
			_result = _localServant.get_attribute_config_2(names);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public fr.esrf.Tango.DevCmdInfo[] command_list_query() throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "command_list_query", true);
				_is = _invoke(_os);
				fr.esrf.Tango.DevCmdInfo[] _result = fr.esrf.Tango.DevCmdInfoListHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "command_list_query", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.DevCmdInfo[] _result;			try
			{
			_result = _localServant.command_list_query();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public fr.esrf.Tango.DevCmdInfo_2[] command_list_query_2() throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "command_list_query_2", true);
				_is = _invoke(_os);
				fr.esrf.Tango.DevCmdInfo_2[] _result = fr.esrf.Tango.DevCmdInfoList_2Helper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "command_list_query_2", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.DevCmdInfo_2[] _result;			try
			{
			_result = _localServant.command_list_query_2();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public fr.esrf.Tango.AttributeConfig_3[] get_attribute_config_3(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "get_attribute_config_3", true);
				fr.esrf.Tango.DevVarStringArrayHelper.write(_os,names);
				_is = _invoke(_os);
				fr.esrf.Tango.AttributeConfig_3[] _result = fr.esrf.Tango.AttributeConfigList_3Helper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "get_attribute_config_3", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.AttributeConfig_3[] _result;			try
			{
			_result = _localServant.get_attribute_config_3(names);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public fr.esrf.Tango.DevState state()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request("_get_state",true);
				_is = _invoke(_os);
				return fr.esrf.Tango.DevStateHelper.read(_is);
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}

		else
		{
		org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "_get_state", _opsClass);
		if( _so == null )
			throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.DevState _result;
		try
		{
			_result = _localServant.state();
		}
		finally
		{
			_servant_postinvoke(_so);
		}
		return _result;
		}
		}

	}

	public fr.esrf.Tango.DevCmdHistory[] command_inout_history_2(java.lang.String command, int n) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "command_inout_history_2", true);
				_os.write_string(command);
				_os.write_long(n);
				_is = _invoke(_os);
				fr.esrf.Tango.DevCmdHistory[] _result = fr.esrf.Tango.DevCmdHistoryListHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "command_inout_history_2", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.DevCmdHistory[] _result;			try
			{
			_result = _localServant.command_inout_history_2(command,n);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public void ping() throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "ping", true);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "ping", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			try
			{
			_localServant.ping();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public void write_attributes(fr.esrf.Tango.AttributeValue[] values) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "write_attributes", true);
				fr.esrf.Tango.AttributeValueListHelper.write(_os,values);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "write_attributes", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			try
			{
			_localServant.write_attributes(values);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public org.omg.CORBA.Any command_inout(java.lang.String command, org.omg.CORBA.Any argin) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "command_inout", true);
				_os.write_string(command);
				_os.write_any(argin);
				_is = _invoke(_os);
				org.omg.CORBA.Any _result = _is.read_any();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "command_inout", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			org.omg.CORBA.Any _result;			try
			{
			_result = _localServant.command_inout(command,argin);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public fr.esrf.Tango.DevCmdInfo command_query(java.lang.String command) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "command_query", true);
				_os.write_string(command);
				_is = _invoke(_os);
				fr.esrf.Tango.DevCmdInfo _result = fr.esrf.Tango.DevCmdInfoHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "command_query", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.DevCmdInfo _result;			try
			{
			_result = _localServant.command_query(command);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public fr.esrf.Tango.DevCmdInfo_2 command_query_2(java.lang.String command) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "command_query_2", true);
				_os.write_string(command);
				_is = _invoke(_os);
				fr.esrf.Tango.DevCmdInfo_2 _result = fr.esrf.Tango.DevCmdInfo_2Helper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "command_query_2", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.DevCmdInfo_2 _result;			try
			{
			_result = _localServant.command_query_2(command);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public java.lang.String description()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request("_get_description",true);
				_is = _invoke(_os);
				return _is.read_string();
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}

		else
		{
		org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "_get_description", _opsClass);
		if( _so == null )
			throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			java.lang.String _result;
		try
		{
			_result = _localServant.description();
		}
		finally
		{
			_servant_postinvoke(_so);
		}
		return _result;
		}
		}

	}

	public fr.esrf.Tango.AttributeValue_3[] read_attributes_3(java.lang.String[] names, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "read_attributes_3", true);
				fr.esrf.Tango.DevVarStringArrayHelper.write(_os,names);
				fr.esrf.Tango.DevSourceHelper.write(_os,source);
				_is = _invoke(_os);
				fr.esrf.Tango.AttributeValue_3[] _result = fr.esrf.Tango.AttributeValueList_3Helper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "read_attributes_3", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			fr.esrf.Tango.AttributeValue_3[] _result;			try
			{
			_result = _localServant.read_attributes_3(names,source);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public void set_attribute_config(fr.esrf.Tango.AttributeConfig[] new_conf) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "set_attribute_config", true);
				fr.esrf.Tango.AttributeConfigListHelper.write(_os,new_conf);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "set_attribute_config", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			try
			{
			_localServant.set_attribute_config(new_conf);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public java.lang.String[] black_box(int n) throws fr.esrf.Tango.DevFailed
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "black_box", true);
				_os.write_long(n);
				_is = _invoke(_os);
				java.lang.String[] _result = fr.esrf.Tango.DevVarStringArrayHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				if( _id.equals("IDL:Tango/DevFailed:1.0"))
				{
					throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());
				}
				else 
					throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "black_box", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			Device_3Operations _localServant = (Device_3Operations)_so.servant;
			java.lang.String[] _result;			try
			{
			_result = _localServant.black_box(n);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

}
