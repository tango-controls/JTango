package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;

public interface IIORDumpDAO {

	public void init(IORdump iORdump, String devname, String iorString) throws DevFailed;

	// ===============================================================
	// ===============================================================
	public void init(IORdump iORdump, String devname) throws DevFailed;
	// ===============================================================
	// ===============================================================
	public void init(IORdump iORdump, DeviceProxy dev) throws DevFailed;	
	
	//===============================================================
	/**
	 *	Return a string with ID type, IIOP version, host name, and port number.
	 */
	public String toString(IORdump iORdump);

	//===============================================================
	/**
	 *	Return the ID type
	 */
	public String get_type_id();

	//===============================================================
	/**
	 *	Return the host where the process is running.
	 */
	public String get_host();

	//===============================================================
	/**
	 *	Return the host name where the process is running.
	 */
	public String get_hostname();

	//===============================================================
	/**
	 *	Return the connection port.
	 */
	public int get_port();

	//===============================================================
	/**
	 *	Return the connection TACO prg_number.
	 */
	public int get_prg_number();

	//===============================================================
	/**
	 *	Return the IIOP version number.
	 */
	public String get_iiop_version();

}