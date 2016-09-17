package fr.esrf.Tango;


/**
 * Generated from IDL interface "Device_5".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public interface Device_5Operations
	extends fr.esrf.Tango.Device_4Operations
{
	/* constants */
	/* operations  */
	fr.esrf.Tango.AttributeConfig_5[] get_attribute_config_5(java.lang.String[] names) throws fr.esrf.Tango.DevFailed;
	void set_attribute_config_5(fr.esrf.Tango.AttributeConfig_5[] new_conf, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.AttributeValue_5[] read_attributes_5(java.lang.String[] names, fr.esrf.Tango.DevSource source, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.AttributeValue_5[] write_read_attributes_5(fr.esrf.Tango.AttributeValue_4[] values, java.lang.String[] r_names, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.MultiDevFailed,fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.DevAttrHistory_5 read_attribute_history_5(java.lang.String name, int n) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.PipeConfig[] get_pipe_config_5(java.lang.String[] names) throws fr.esrf.Tango.DevFailed;
	void set_pipe_config_5(fr.esrf.Tango.PipeConfig[] new_conf, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.DevPipeData read_pipe_5(java.lang.String name, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed;
	void write_pipe_5(fr.esrf.Tango.DevPipeData value, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed;
	fr.esrf.Tango.DevPipeData write_read_pipe_5(fr.esrf.Tango.DevPipeData value, fr.esrf.Tango.ClntIdent cl_ident) throws fr.esrf.Tango.DevFailed;
}
