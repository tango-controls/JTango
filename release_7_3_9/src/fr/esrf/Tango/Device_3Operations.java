package fr.esrf.Tango;

/**
 *	Generated from IDL interface "Device_3"
 *	@author JacORB IDL compiler V 2.2, 7-May-2004
 */


public interface Device_3Operations
	extends fr.esrf.Tango.Device_2Operations
{
	/* constants */
	/* operations  */
	fr.esrf.Tango.AttributeValue_3[] read_attributes_3(java.lang.String[] names, fr.esrf.Tango.DevSource source) throws fr.esrf.Tango.DevFailed;
	void write_attributes_3(fr.esrf.Tango.AttributeValue[] values) throws fr.esrf.Tango.MultiDevFailed,fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.DevAttrHistory_3[] read_attribute_history_3(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.DevInfo_3 info_3() throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.AttributeConfig_3[] get_attribute_config_3(java.lang.String[] names) throws fr.esrf.Tango.DevFailed;
	void set_attribute_config_3(fr.esrf.Tango.AttributeConfig_3[] new_conf) throws fr.esrf.Tango.DevFailed;
}
