package fr.esrf.Tango;

/**
 *	Generated from IDL interface "Device_2"
 *	@author JacORB IDL compiler V 2.2, 7-May-2004
 */


public interface Device_2Operations
	extends fr.esrf.Tango.DeviceOperations
{
	/* constants */
	/* operations  */
	org.omg.CORBA.Any command_inout_2(java.lang.String command, org.omg.CORBA.Any argin, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.AttributeValue[] read_attributes_2(java.lang.String[] names, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.AttributeConfig_2[] get_attribute_config_2(java.lang.String[] names) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.DevCmdInfo_2[] command_list_query_2() throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.DevCmdInfo_2 command_query_2(java.lang.String command) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.DevCmdHistory[] command_inout_history_2(java.lang.String command, int n) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.DevAttrHistory[] read_attribute_history_2(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed;
}
