package fr.esrf.Tango;

/**
 *	Generated from IDL interface "Device_4"
 *	@author JacORB IDL compiler V 2.2, 7-May-2004
 */


public interface Device_4Operations
	extends fr.esrf.Tango.Device_3Operations
{
	/* constants */
	/* operations  */
	fr.esrf.Tango.DevAttrHistory_4 read_attribute_history_4(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.DevCmdHistory_4 command_inout_history_4(java.lang.String command, int n) throws fr.esrf.Tango.DevFailed;
	org.omg.CORBA.Any command_inout_4(java.lang.String command, org.omg.CORBA.Any argin, fr.esrf.Tango.DevSource source, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.AttributeValue_4[] read_attributes_4(java.lang.String[] names, fr.esrf.Tango.DevSource source, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed;
	void write_attributes_4(fr.esrf.Tango.AttributeValue_4[] values, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.MultiDevFailed,fr.esrf.Tango.DevFailed;
	void set_attribute_config_4(fr.esrf.Tango.AttributeConfig_3[] new_conf, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.AttributeValue_4[] write_read_attributes_4(fr.esrf.Tango.AttributeValue_4[] values, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.MultiDevFailed,fr.esrf.Tango.DevFailed;
}
