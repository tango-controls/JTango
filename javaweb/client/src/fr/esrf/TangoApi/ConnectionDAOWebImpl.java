package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevInfo;
import fr.esrf.Tango.DevInfo_3;
import fr.esrf.Tango.DevSource;
import fr.esrf.Tango.Device;
import fr.esrf.webapi.IWebImpl;
import fr.esrf.webapi.WebServerClientUtil;

public class ConnectionDAOWebImpl implements IConnectionDAO, IWebImpl {
	private String m_strHost = null;

	private String m_strPort = null;

	private boolean m_bTransparent_reconnection = true;

	private Object[] classParam = null;

	public ConnectionDAOWebImpl() {
	}

	public void init(Connection connection) {
		classParam = new Object[] {};
	}

	public void init(Connection connection, String strHost, String strPort) {
		classParam = new Object[] { strHost, strPort };
		m_strHost = strHost;
		m_strPort = strPort;
	}

	public void init(Connection connection, String strHost, String strPort, boolean bAuto_reconnect) {
		classParam = new Object[] { strHost, strPort, bAuto_reconnect };
		m_strHost = strHost;
		m_strPort = strPort;
		m_bTransparent_reconnection = bAuto_reconnect;
	}

	public void init(Connection connection, String devname) throws DevFailed {
		classParam = new Object[] { devname };
	}

	public void init(Connection connection, String devname, boolean check_access) throws DevFailed {
		classParam = new Object[] { devname, check_access };
	}

	public void init(Connection connection, String devname, String param, int src) throws DevFailed {
		classParam = new Object[] { devname, param, src };
	}

	public void init(Connection connection, String devname, String host, String port) throws DevFailed {
		classParam = new Object[] { devname, host, port };
		m_strHost = host;
		m_strPort = port;		
	}

	// ==========================================================================
	/**
	 * return the name of connection (host:port)
	 */
	// ==========================================================================
	public String get_tango_host(Connection connection) throws DevFailed {
		return m_strHost + ":" + m_strPort;
	}

	public String getHost(Connection connection) {
		return m_strHost;
	}

	public void setHost(Connection connection, String host) {
		m_strHost = host;
	}

	public String getPort(Connection connection) {
		return m_strPort;
	}

	public void setPort(Connection connection, String port) {
		m_strPort = port;
	}

	public Device get_device(Connection connection) {
		try {
			return (Device) WebServerClientUtil.getResponse(this, classParam, "get_device", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Execute a ping command to a device server.
	 * 
	 * @return the elapsed time for ping command in microseconds.
	 */
	// ===========================================================
	public long ping(Connection connection) throws DevFailed {
		return (Long) WebServerClientUtil.getResponse(this, classParam, "ping", new Object[] {}, new Class[] {});	
	}

	public void build_connection(Connection connection) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "build_connection", new Object[] {}, new Class[] {});

	}

	public void checkIfTaco(Connection connection, String cmdname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "checkIfTaco", new Object[] { cmdname }, new Class[] { String.class });

	}

	public void checkIfTango(Connection connection, String cmdname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "checkIfTango", new Object[] { cmdname }, new Class[] { String.class });
	}

	public void dev_import(Connection connection) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "dev_import", new Object[] {}, new Class[] {});
	}

	public void throw_dev_failed(Connection connection, Exception e, String command, boolean from_inout_cmd) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "throw_dev_failed", new Object[] { e, command, from_inout_cmd }, new Class[] { Exception.class, String.class, boolean.class });
	}

	public String adm_name(Connection connection) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "adm_name", new Object[] {}, new Class[] {});
	}

	public String[] black_box(Connection connection, int length) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "adm_name", new Object[] { length }, new Class[] { int.class });
	}

	public DeviceData command_inout(Connection connection, String command, DeviceData argin) throws DevFailed {
		return (DeviceData) WebServerClientUtil.getResponse(this, classParam, "command_inout", new Object[] { command, argin }, new Class[] { String.class, DeviceData.class });
	}

	public DeviceData command_inout(Connection connection, String command) throws DevFailed {
		return (DeviceData) WebServerClientUtil.getResponse(this, classParam, "command_inout", new Object[] { command }, new Class[] { String.class });
	}

	public CommandInfo[] command_list_query(Connection connection) throws DevFailed {
		return (CommandInfo[]) WebServerClientUtil.getResponse(this, classParam, "command_list_query", new Object[] {}, new Class[] {});
	}

	public String description(Connection connection) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "description", new Object[] {}, new Class[] {});
	}

	public int getAccessControl(Connection connection) {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "getAccessControl", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int get_idl_version(Connection connection) throws DevFailed {
		 try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "get_idl_version", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String get_ior(Connection connection) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "get_ior", new Object[] {}, new Class[] {});
	}
	
	public String get_class_name(Connection arg0) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "get_class_name", new Object[] {}, new Class[] {});
	}

	public String get_host_name(Connection arg0) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "get_host_name", new Object[] {}, new Class[] {});
	}

	public String get_server_name(Connection arg0) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "get_server_name", new Object[] {}, new Class[] {});
	}
	
	public String get_name(Connection connection) {
		try {
			return (String) WebServerClientUtil.getResponse(this, classParam, "get_name", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			e.printStackTrace();
			return null;
		}
	}

	public DevSource get_source(Connection connection) throws DevFailed {
		return (DevSource) WebServerClientUtil.getResponse(this, classParam, "get_source", new Object[] {}, new Class[] {});
	}

	public int get_timeout(Connection connection) {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "get_timeout", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int get_timeout_millis(Connection connection) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "get_timeout_millis", new Object[] {}, new Class[] {});
	}

	public boolean get_transparency_reconnection(Connection connection) {
		try {
			return (Boolean) WebServerClientUtil.getResponse(this, classParam, "get_transparency_reconnection", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			e.printStackTrace();
			return false;
		}
	}

	public DevInfo info(Connection connection) throws DevFailed {
		return (DevInfo) WebServerClientUtil.getResponse(this, classParam, "info", new Object[] {}, new Class[] {});
	}

	public DevInfo_3 info_3(Connection connection) throws DevFailed {
		return (DevInfo_3) WebServerClientUtil.getResponse(this, classParam, "info_3", new Object[] {}, new Class[] {});
	}

	public boolean isAllowedCommand(Connection connection, String cmd) throws DevFailed {
		return (Boolean) WebServerClientUtil.getResponse(this, classParam, "isAllowedCommand", new Object[] { cmd }, new Class[] { String.class });
	}

	public boolean is_taco(Connection connection) {
		try {
			return (Boolean) WebServerClientUtil.getResponse(this, classParam, "is_taco", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setAccessControl(Connection connection, int access) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "setAccessControl", new Object[] { access }, new Class[] { int.class });
		} catch (DevFailed e) {
			e.printStackTrace();
		}
	}

	public void set_source(Connection connection, DevSource new_src) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "set_source", new Object[] { new_src }, new Class[] { DevSource.class });
	}

	public void set_timeout_millis(Connection connection, int millis) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "set_timeout_millis", new Object[] { millis }, new Class[] { int.class });
	}

	public void set_transparency_reconnection(Connection connection, boolean val) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "set_transparency_reconnection", new Object[] { val }, new Class[] { boolean.class });
		} catch (DevFailed e) {
			e.printStackTrace();
		}
	}

	public TangoUrl getUrl(Connection connection) {
		try {
			return (TangoUrl) WebServerClientUtil.getResponse(this, classParam, "getUrl", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getAccess(Connection connection) {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "getAccess", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String getDevname(Connection connection) {
		try {
			return (String) WebServerClientUtil.getResponse(this, classParam, "getDevname", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean isCheck_access(Connection connection) {
		try {
			return (Boolean) WebServerClientUtil.getResponse(this, classParam, "isCheck_access", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setAccess(Connection connection, int access) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "setAccess", new Object[] { access }, new Class[] { int.class });
		} catch (DevFailed e) {
			e.printStackTrace();
		}
	}

	protected void finalize(Connection connection) throws Throwable {
		// call the WebServerClientUtil to call the remove of the object in the
		// server
		WebServerClientUtil.removeObject(this);
		super.finalize();
	}

	public Object[] getClassParam() {
		return classParam;
	}

	public void setClassParam(Object[] classParam) {
		this.classParam = classParam;
	}

}
