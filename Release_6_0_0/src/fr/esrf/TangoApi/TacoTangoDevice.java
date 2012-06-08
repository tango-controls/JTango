//+===========================================================================
// $Source$
//
// Project:   Tango API
//
// Description:  Java source for conversion between Tango/TACO library
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.1  2007/08/23 12:27:44  ounsy
// add TacoTangoDevice
//
// Revision 1.9  2007/06/07 09:24:47  pascal_verdier
// Taco connection has been set to TCP by default.
//
// Revision 1.8  2007/04/27 10:17:35  pascal_verdier
// Bug on full attribute name fixed.
//
// Revision 1.7  2007/04/27 09:05:17  pascal_verdier
// Taco device multinathost supported.
//
// Revision 1.6  2006/04/10 13:43:38  jlpons
// Added state for D_STATE_FLOAT_READPOINT taco type
//
// Revision 1.5  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 1.4  2005/11/29 05:34:55  pascal_verdier
// TACO info added.
//
// Revision 1.3  2005/10/10 14:09:57  pascal_verdier
// set_source and get_source methods added for Taco device mapping.
// Compatibility with Taco-1.5.jar.
//
// Revision 1.2  2005/09/14 07:34:39  pascal_verdier
// Bug fixed in attribute type.
//
// Revision 1.1  2005/08/10 08:22:20  pascal_verdier
// Initial Revision.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-===========================================================================
//         (c) - Software Engineering Group - ESRF
//============================================================================


package fr.esrf.TangoApi;


import fr.esrf.TacoApi.TacoCommand;
import fr.esrf.TacoApi.TacoConst;
import fr.esrf.TacoApi.TacoData;
import fr.esrf.TacoApi.TacoException;

import fr.esrf.Tango.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

import java.util.StringTokenizer;
import java.util.Vector;


/**
 *	<b>Class Description:</b><Br>
 *	This class is a wrapper for TACO device.
 *	It is an interface between TANGO DeviceProxy and TACO device.
 *	It replace the fr.esrf.TacoApi.TacoDevice class using JNI library abd use true
 *	java classes found in Taco.jar (ESRF specific).
 *	
 * @author  verdier
 * @version	$Revision$
 */


public class TacoTangoDevice implements TangoConst, TacoConst
{

	/**
	 *	the device name
	 */
	private	String	devname;
	
	private fr.esrf.TacoApi.TacoDevice	dev;
	
	//======================================================
	/**
	 *	Constructor for a Taco Device Object.
	 *
	 *	@param	name	the device name
	 */
	//======================================================
	TacoTangoDevice(String devname, String nethost) throws DevFailed
	{
		if (nethost!=null)
			if(nethost.length()>0)
				devname = "//" + nethost + "/" + devname;
		//System.out.println("TacoTangoDevice(" + devname + ")");
		try
		{
			dev = new fr.esrf.TacoApi.TacoDevice(devname);
			this.devname = devname ;
			set_source(DevSource.CACHE_DEV);
			set_rpc_protocol(fr.esrf.TacoApi.TacoDevice.PROTOCOL_TCP);
			//read_commandListQuery();
		}
		catch(TacoException e)
		{
			Except.throw_exception("Api_TacoFailed",
					e.getErrorString(),
					"TacoTangoDevice.TacoTangoDevice("+devname+")");
		}
	}
	//======================================================
	/**
	 *	Excute a command on a TACO device
	 *
	 *	@param	command	Command name to be executed on the device
	 *	@param	argin	Input command argument (Tango data)
	 *
	 *	@return	Output command argument (Tango data)
	 */
	//======================================================
	DeviceData command_inout(String command, DeviceData argin) throws DevFailed
	{
		DeviceData	argout = null;
		try
		{
			TacoData	taco_in  = dataToTaco(argin);
			TacoData	taco_out = dev.putGet(dev.getCommandCode(command), taco_in);
			argout = dataToTango(taco_out);
		}
		catch(TacoException e)
		{
			String	desc = e.getErrorString();
			if (desc.startsWith("Device command") &&
				desc.indexOf("not found for this device")>0)
				Except.throw_exception("TACO_CMD_UNAVAILABLE",
							desc,
							"TacoTangoDevice.command_inout() for "+devname);
			else
				Except.throw_exception("Api_TacoFailed",
							desc,
							"TacoTangoDevice.command_inout() for "+devname);
		}
		return argout;
	}
	//======================================================
	//======================================================
	CommandInfo[] commandListQuery() throws DevFailed
	{
		CommandInfo[]	info = null;
		try
		{
			TacoCommand[]	tc = dev.commandQuery();
			info = dataToTango(tc);
		}
		catch(TacoException e)
		{
			Except.throw_exception("Api_TacoFailed",
					e.getErrorString(),
					"TacoTangoDevice.commandListQuery() for "+devname);
		}
		return info;
	}
	//======================================================
	//======================================================
	CommandInfo commandQuery(String cmdname) throws DevFailed
	{
		CommandInfo		argout = null;
		CommandInfo[]	cmdlist = commandListQuery();

		for (int i=0 ; i<cmdlist.length && argout==null ; i++)
			if (cmdlist[i].cmd_name.equals(cmdname))
				argout = cmdlist[i];
		if (argout==null)
			Except.throw_exception("Api_TacoFailed",
					"Command \'"+cmdname+"\' Not Found",
					"TacoTangoDevice.commandQuery() for "+devname);
		return argout;
	}
	//======================================================
	/**
	 *	Execute the dev_rpc_protocol TACO command to change RPC protocol mode.
	 *
	 *	@param	mode RPC protocol mode to be seted 
	 *		(TangoApi.ApiDefs.<b>D_TCP</b> or ApiDefs.TacoTangoDevice.<b>D_TCP</b>).
	 */
	//======================================================
	void set_rpc_protocol(int mode)	throws DevFailed
	{
		if (mode != fr.esrf.TacoApi.TacoDevice.PROTOCOL_UDP  && 
			mode != fr.esrf.TacoApi.TacoDevice.PROTOCOL_TCP)
			Except.throw_wrong_syntax_exception("BAD_PARAMETER",
							"Bad parameter for dev_rpc_protocol command",
							"TacoTangoDevice.dev_rpc_protocol()");
		try
		{
			dev.setProtocol(mode);
		}
		catch(TacoException e)
		{
			Except.throw_exception("Api_TacoFailed",
					e.getErrorString(),
					"TacoTangoDevice.commandListQuery() for "+devname);
		}
	}

	//======================================================
	/**
	 *	Return the dev_rpc_protocol use by TACO device
	 *
	 *	@return	 RPC protocol used
	 *		(TangoApi.ApiDefs.<b>D_TCP</b> or ApiDefs.TacoTangoDevice.<b>D_TCP</b>).
	 */
	//======================================================
	int get_rpc_protocol()	throws DevFailed
	{
		return dev.getProtocol();
	}

	//======================================================
	/**
	 *	Execute the dev_rpc_timeout TACO command to get RPC timeout.
	 */
	//======================================================
	int get_rpc_timeout()	throws DevFailed
	{
		return dev.getTimeout();
	}
	//======================================================
	/**
	 *	Execute the dev_rpc_timeout TACO command to set RPC timeout.
	 */
	//======================================================
	void set_rpc_timeout(int millis)	throws DevFailed
	{
		dev.setTimeout(millis);
	}
	//==========================================================================
	/**
	 *	Returns  TACO device information.
	 *
	 *	@return  TACO device information as String array.
	 *	<li> Device name.
	 *	<li> Class name
	 *	<li> Device type
	 *	<li> Device server name
	 *	<li> Host name
	 *	<li> Nethost name
	 */
	//==========================================================================
	String[] dev_inform() throws DevFailed
	{
		String[]	info = null;
		info = infoToTango(dev.getInfo());
		return info;
	}
	//==========================================================================
	//==========================================================================
	public DbDatum[] get_property(String[] propnames) throws DevFailed
	{
		DbDatum[]	data = new DbDatum[propnames.length];

		try
		{
			for (int i=0 ; i<propnames.length ; i++)
			{
				data[i] = new DbDatum(propnames[i]);
				String[]	val = dev.getResource(propnames[i]);
				if (val.length>0)
					data[i].insert(val);
			}
			
		}
		catch(TacoException e)
		{
			Except.throw_exception("Api_TacoFailed",
					e.getErrorString(),
					"TacoTangoDevice.get_property() for "+devname);
		}
		return data;
	}

	//==========================================================================
	/**
	 *	Set the device data source
	 *
	 *	@param src	new data source (CACHE_DEV, CACHE or DEV).
	 */
	//==========================================================================
	void set_source(DevSource src) throws DevFailed
	{
		try
		{
			if (src==DevSource.DEV)
				dev.setSource(fr.esrf.TacoApi.TacoDevice.SOURCE_DEVICE);
			else
			if (src==DevSource.CACHE_DEV)
				dev.setSource(fr.esrf.TacoApi.TacoDevice.SOURCE_CACHE_DEVICE);
			else
			if (src==DevSource.CACHE)
				dev.setSource(fr.esrf.TacoApi.TacoDevice.SOURCE_CACHE);
		}
		catch(TacoException e)
		{
			Except.throw_exception("Api_TacoFailed",
							e.getErrorString(),
							"TacoTangoDevice.set_source() for " + devname);
			/*
			System.out.println("WARNIONG:\n" +
					"Cannot set source on " + devname + "\n" +
					e.getErrorString() + "\n" +
					"TacoTangoDevice.set_source()");
			*/
		}
	}
	//==========================================================================
	/**
	 *	returns the device data source
	 *
	 *	@return  data source (CACHE_DEV, CACHE or DEV).
	 */
	//==========================================================================
	DevSource get_source()
	{
		DevSource src = DevSource.DEV;
		switch (dev.getSource())
		{
		case fr.esrf.TacoApi.TacoDevice.SOURCE_DEVICE:
			src = DevSource.DEV;
			break;
		case fr.esrf.TacoApi.TacoDevice.SOURCE_CACHE_DEVICE:
			src = DevSource.CACHE_DEV;
			break;
		case fr.esrf.TacoApi.TacoDevice.SOURCE_CACHE:
			src = DevSource.CACHE;
			break;
		}
		return src;
	}
	
	//==========================================================================
	//==========================================================================







	//==========================================================================
	/*
	 *	Signal / Attribute management
	 */
	//==========================================================================

	//======================================================
	//======================================================
	private String onlyAttName(String fullname) throws DevFailed
	{
		int	start = 0;
		for (int i=0 ; i<3 ; i++, start++)
		{
			start = fullname.indexOf('/', start);
			if (start<0)
				Except.throw_wrong_syntax_exception("BAD_PARAMETER",
						"Attribute name not found in " + fullname,
						"TacoTangoDevice.onlyAttName()");
		}
		return fullname.substring(start).trim();
	}
	//======================================================
	//======================================================
	private String fullName(String attname)
	{
		String	fullname = new String(devname + "/" + attname);
		//	Take off nethost if exists
		if (fullname.startsWith("//"))
			return fullname.substring(fullname.indexOf('/',2)+1);
		 else
		 	return fullname;
	}
	//==========================================================================
	//==========================================================================
	String[] get_attribute_list() throws DevFailed
	{
		//	Check if att_config ihas been initialized.
		if (att_config==null)
			initializeAttributeConfig();

		String[]	list = new String[att_config.length];
		for (int i=0 ; i<att_config.length ; i++)
			list[i] = att_config[i].name;
		return list;
	}
	//======================================================
	//======================================================
	AttributeConfig[] get_attribute_config(String[] attrnames) throws DevFailed
	{
		//	Check if att_config ihas been initialized.
		if (att_config==null)
			initializeAttributeConfig();

		if (attrnames[0].equals("All attributes"))
			return att_config;

		AttributeConfig[]	tmp_config = new AttributeConfig[attrnames.length];
		for (int i=0 ; i<attrnames.length ; i++)
		{
			for (int j=0 ; j<att_config.length ; j++)
				if (att_config[j].name.equals(attrnames[i]))
					tmp_config[i] = att_config[j];
		}
		if (tmp_config[0]==null)
			Except.throw_wrong_syntax_exception("BAD_PARAMETER",
						"Attribute " + attrnames[0] + " not found !",
						"TacoTangoDevice.get_attribute_config() for "+devname);
			
		return tmp_config;
	}
	//======================================================
	//======================================================
	DeviceAttribute[] read_attribute(String[] attrnames) throws DevFailed
	{
		//	Check if att_config ihas been initialized.
		if (att_config==null)
			initializeAttributeConfig();

		//	Read the signals
		DeviceData	argout = command_inout("DevReadSigValues", null);

			//	Get output cmd arg type
		CommandInfo cmd = commandQuery("DevReadSigValues");
		int			orig_type = cmd.out_type;
		int[]		l_val = null;
		float[]		f_val = null;
		double[]	d_val = null;
		switch(orig_type)
		{
		case Tango_DEVVAR_LONGARRAY:
			l_val = argout.extractLongArray();
			break;
		case Tango_DEVVAR_FLOATARRAY:
			f_val = argout.extractFloatArray();
			break;
		case Tango_DEVVAR_DOUBLEARRAY:
			d_val = argout.extractDoubleArray();
			break;
		}
		//	Allocate an array to be returned and fill it
		DeviceAttribute[]	dev_attr = new DeviceAttribute[attrnames.length];
		for (int i=0 ; i<attrnames.length ; i++)
		{
			//	search attribute name
			int	idx = -1;
			for (int j=0 ; j<att_config.length ; j++)
				if (att_config[j].name.toLowerCase().equals(attrnames[i].toLowerCase()))
					idx = j;

			//	check if found
			if (idx==-1)
				Except.throw_wrong_syntax_exception("BAD_PARAMETER",
							"Attribute "+ attrnames[i] +" not found for this device",
							"TacoTangoDevice.read_attribute()  for "+devname);
			//	Fill the attribute value
			switch(orig_type)
			{
			case Tango_DEVVAR_LONGARRAY:
				dev_attr[i] =  new DeviceAttribute(attrnames[i], l_val[idx]);
				break;
			case Tango_DEVVAR_FLOATARRAY:
				dev_attr[i] =  new DeviceAttribute(attrnames[i], f_val[idx]);
				break;
			case Tango_DEVVAR_DOUBLEARRAY:
				dev_attr[i] =  new DeviceAttribute(attrnames[i], d_val[idx]);
				break;
			}
		}
		return dev_attr;
	}
	//==========================================================================
	/**
	 *	Execute DevGetSigConfig to initialize AttributeConfig array
	 */
	//==========================================================================
	private void initializeAttributeConfig() throws DevFailed
	{
		int	nb_x = 1;
		int	nb_y = 0;
		int	attr_data_type = Tango_DEV_DOUBLE;

		//	Read Signal config if not already done
		String[]	attr_strconfig = new String[0];
		try
		{
			//	Read the config
			DeviceData	argout = command_inout("DevGetSigConfig", null);
			attr_strconfig = argout.extractStringArray();
			//	Get output cmd arg type
			CommandInfo cmd = commandQuery("DevReadSigValues");
			attr_data_type = getAttrDataType(cmd.out_type);
		}
		catch(DevFailed e)
		{
			//	Check if command implemented (if not -> no signal available)
			if (e.errors[0].reason.equals("TACO_CMD_UNAVAILABLE"))
			{
				att_config = new AttributeConfig[0];
				return;
			}
			else
				throw e;
		}
		
		//	allocate output array (for all attr or just some of them ?)
		int	modulo = Integer.parseInt(attr_strconfig[0]);
		att_config = new AttributeConfig[attr_strconfig.length/modulo];

		//	reconstruct attrnames with all attr found
		String[]	attrnames = new String[attr_strconfig.length/modulo];
		for (int i=0, j=1 ; j<attr_strconfig.length ; j+=modulo)
			attrnames[i++] = onlyAttName(attr_strconfig[j]);
			att_config = new AttributeConfig[attrnames.length];
	
	
		for (int i=0 ; i<attrnames.length ; i++)
		{
			//	search attribute name
			String	fullname = fullName(attrnames[i]);
			boolean found = false;
			for (int j=1 ; j<attr_strconfig.length ; j+=modulo)
			{
				if (attr_strconfig[j].equals(fullname))
				{
					//	default initilization of config fields
					String		description = "";
					String		label = "";
					String		unit = "";
					String		standard_unit = "";
					String		display_unit = "";
					String		format = "";
					String		min_value = "";
					String		max_value = "";
					String		min_alarm = "";
					String		max_alarm = "";
					String		writable_attr_name = "";
					String[]	extensions = null;
					found = true;

					//	Fill values
					String	not_specified = "No Description";
					if (attr_strconfig[j+4].equals(not_specified)==false)
						description = attr_strconfig[j+4];
					not_specified = "Not specified";
					if (attr_strconfig[j+1].equals(not_specified)==false)
						label = attr_strconfig[j+1];
					if (attr_strconfig[j+2].equals(not_specified)==false)
					{
						unit          = attr_strconfig[j+2];
						display_unit  = attr_strconfig[j+2];	
					}
					if (attr_strconfig[j+3].equals(not_specified)==false)
						format = attr_strconfig[j+3];
					if (attr_strconfig[j+5].equals(not_specified)==false)
						max_value = attr_strconfig[j+5];
					if (attr_strconfig[j+6].equals(not_specified)==false)
						min_value = attr_strconfig[j+6];
					if (attr_strconfig[j+7].equals(not_specified)==false)
						max_alarm = attr_strconfig[j+7];
					if (attr_strconfig[j+8].equals(not_specified)==false)
						min_alarm = attr_strconfig[j+8];
					att_config[i] = new AttributeConfig(attrnames[i],
											AttrWriteType.READ,
											AttrDataFormat.SCALAR,
											attr_data_type,
											nb_x,
											nb_y,
											description,
											label,
											unit,
											standard_unit,
											display_unit,
											format,
											min_value,
											max_value,
											min_alarm,
											max_alarm,
											writable_attr_name,
											extensions);
				}
			}
			//	check if found
			if (found==false)
				Except.throw_wrong_syntax_exception("BAD_PARAMETER",
							"Attribute name not found for this device",
							"TacoTangoDevice.initializeAttributeConfig()");
		}
	}
	//======================================================
	/**
	 *	Set the attribute data type from DevReadSigValues
	 *	TACO command argout.
	 */
	//======================================================
	private int getAttrDataType(int type) throws DevFailed
	{
		//	Check attr_data_type
		int	attr_data_type = 0;
		switch(type)
		{
		case Tango_DEVVAR_LONGARRAY:
			attr_data_type = Tango_DEV_LONG;
			break;
		case Tango_DEVVAR_FLOATARRAY:
			attr_data_type = Tango_DEV_FLOAT;
			break;
		case Tango_DEVVAR_DOUBLEARRAY:
			attr_data_type = Tango_DEV_DOUBLE;
			break;
		default:
			try {
			
				Except.throw_wrong_syntax_exception("BAD_PARAMETER",
							"Output parameter not supported.",
							"TacoTangoDevice.getAttrDataType()");
			}
			catch(DevFailed e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return attr_data_type;
	}
	//==========================================================================
	//==========================================================================
	private AttributeConfig[]	att_config = null;






	//======================================================
	/*
	 *	Taco  <--> Tango  data convertion methods
	 */
	//======================================================

	//======================================================
	/**
	 *	Taco  --> Tango  data convertion method
	 *
	 *	@param	taco_type Taco data
	 *	@return the TANGO converted data
	 */
	//======================================================
	int tangoType(int taco_type)
	{
		int	tango_type = D_VOID_TYPE;
		switch(taco_type)
		{
		case D_VOID_TYPE:
			tango_type = Tango_DEV_VOID;
			break;
		case D_SHORT_TYPE:
			tango_type = Tango_DEV_SHORT;
			break;
		case D_USHORT_TYPE:
			tango_type = Tango_DEV_USHORT;
			break;
		case D_LONG_TYPE:
			tango_type = Tango_DEV_LONG;
			break;
		case D_ULONG_TYPE:
			tango_type = Tango_DEV_ULONG;
			break;
		case D_STRING_TYPE:
			tango_type = Tango_DEV_STRING;
			break;
		case D_FLOAT_TYPE:
			tango_type = Tango_DEV_FLOAT;
			break;
		case D_DOUBLE_TYPE:
			tango_type = Tango_DEV_DOUBLE;
			break;

		case D_VAR_SHORTARR:
			tango_type = Tango_DEVVAR_SHORTARRAY;
			break;
		case D_VAR_USHORTARR:
			tango_type = Tango_DEVVAR_USHORTARRAY;
			break;
		case D_VAR_LONGARR:
			tango_type = Tango_DEVVAR_LONGARRAY;
			break;
		case D_VAR_ULONGARR:
			tango_type = Tango_DEVVAR_ULONGARRAY;
			break;
		case D_VAR_FLOATARR:
			tango_type = Tango_DEVVAR_FLOATARRAY;
			break;
		case D_VAR_DOUBLEARR:
			tango_type = Tango_DEVVAR_DOUBLEARRAY;
			break;
		case D_VAR_STRINGARR:
			tango_type = Tango_DEVVAR_STRINGARRAY;
			break;
		case D_STATE_FLOAT_READPOINT:
			tango_type = Tango_DEVVAR_FLOATARRAY;
			break;
		}
		return tango_type;
	}
	//======================================================
	/**
	 *	Tango  --> Taco  data convertion method
	 *
	 *	@param	argin Tango data
	 *	@return the TACO converted data
	 */
	//======================================================
	private TacoData dataToTaco(DeviceData argin) throws DevFailed
	{
		TacoData	argout = new TacoData();
		if (argin==null)
			return argout;



		int	type = argin.getType();
		switch(type)
		{
		case -1:
		case Tango_DEV_VOID:
			//	Nothing to insert
			break;

		case Tango_DEV_SHORT:
			argout.insert(argin.extractShort());
			break;
		case Tango_DEVVAR_SHORTARRAY:
			argout.insert(argin.extractShortArray());
			break;

		case Tango_DEV_USHORT:
			argout.insertUShort(argin.extractUShort());
			break;
		case Tango_DEVVAR_USHORTARRAY:
			argout.insertUShortArray(argin.extractUShortArray());
			break;

		case Tango_DEV_LONG:
			argout.insert(argin.extractLong());
			break;
		case Tango_DEVVAR_LONGARRAY:
			argout.insert(argin.extractLongArray());
			break;
		case Tango_DEV_ULONG:
			argout.insertULong(argin.extractLong());
			break;
		case Tango_DEVVAR_ULONGARRAY:
			//argout.insertULongs(argin.extractLongArray());
			break;

		case Tango_DEV_FLOAT:
			argout.insert(argin.extractFloat());
			break;
		case Tango_DEVVAR_FLOATARRAY:
			argout.insert(argin.extractFloatArray());
			break;

		case Tango_DEVVAR_DOUBLEARRAY:
			argout.insert(argin.extractDouble());
			break;
		case Tango_DEV_DOUBLE:
			argout.insert(argin.extractDoubleArray());
			break;

		case Tango_DEV_STRING:
			argout.insert(argin.extractString());
			break;
		case Tango_DEVVAR_STRINGARRAY:
			argout.insert(argin.extractStringArray());
			break;

		default:
			Except.throw_exception("Api_TacoFailed",
					"Argument type " + Tango_CmdArgTypeName[type] +
					"Not supported for TACO interface",
					"TacoTangoDevice.dataToTaco() for "+ devname);
/*
		case Tango_DEV_BOOLEAN:
		case Tango_DEVVAR_CHARARRAY:
		case Tango_DEV_CHAR:
		case Tango_DEVVAR_LONGSTRINGARRAY:
		case Tango_DEVVAR_DOUBLESTRINGARRAY:
*/
		}
		return argout;
	}
	//======================================================
	/**
	 *	Taco  --> Tango  data convertion method
	 *
	 *	@param	argin Taco data
	 *	@return the TANGO converted data
	 */
	//======================================================
	private DeviceData dataToTango(TacoData argin) throws DevFailed
	{
		int	type = argin.getType();
		DeviceData	argout = new DeviceData();
		try
		{
			switch(type)
			{
			case -1:
			case D_VOID_TYPE:
				//	Nothing to insert
				break;
			case D_SHORT_TYPE:
				argout.insert(argin.extractShort());
				break;
			case D_USHORT_TYPE:
				argout.insert_us(argin.extractUShort());
				break;
			case D_LONG_TYPE:
				argout.insert(argin.extractLong());
				break;
			case D_ULONG_TYPE:
				argout.insert_ul(argin.extractULong());
				break;
			case D_STRING_TYPE:
				argout.insert(argin.extractString());
				break;
			case D_FLOAT_TYPE:
				argout.insert(argin.extractFloat());
				break;
			case D_DOUBLE_TYPE:
				argout.insert(argin.extractDouble());
				break;

			case D_VAR_SHORTARR:
				argout.insert(argin.extractShortArray());
				break;
			case D_VAR_USHORTARR:
				argout.insert_us(argin.extractUShortArray());
				break;
			case D_VAR_LONGARR:
				argout.insert(argin.extractLongArray());
				break;
			case D_VAR_ULONGARR:
				argout.insert_ul(argin.extractULongArray());
				break;
			case D_VAR_FLOATARR:
				argout.insert(argin.extractFloatArray());
				break;
			case D_VAR_DOUBLEARR:
				argout.insert(argin.extractDoubleArray());
				break;
			case D_VAR_STRINGARR:
				argout.insert(argin.extractStringArray());
				break;
			case D_STATE_FLOAT_READPOINT:
        float[] ret = new float[3];
        float[] f = argin.extractStateFloatReadPoint();
        ret[0] = (float)argin.extractStateofSFR();
        ret[1] = f[0];
        ret[2] = f[1];
        argout.insert(ret);
				break;
			}
		}
		catch(TacoException e)
		{
			Except.throw_exception("Api_TacoFailed",
					e.getErrorString(),
					"TacoTangoDevice.dataToTango() for "+ devname);
		}
		return argout;
	}
	//======================================================
	/**
	 *	Taco  --> Tango  data convertion method
	 *
	 *	@param	tc Taco data
	 *	@return the TANGO converted data
	 */
	//======================================================
	CommandInfo[] dataToTango(TacoCommand[]	tc)
	{
		CommandInfo[]	info = new CommandInfo[tc.length];

		for (int i=0 ; i<tc.length ; i++)
			info[i] = new CommandInfo(tc[i].cmdName,
									 0,
									 tangoType(tc[i].inType),
									 tangoType(tc[i].outType),
									 tc[i].inName,
									 tc[i].outName);

		return info;		
	}
	//======================================================
	/**
	 *	Taco  --> Tango  data convertion method
	 *
	 *	@param	taco_info Taco data
	 *	@return the TANGO converted data
     *  <ul>
	 *	    <li> Device name.
	 *	    <li> Class name
	 *	    <li> Device type
	 *	    <li> Device server name
	 *	    <li> Host name
     *  </ul>
	 */
	//======================================================
	String[] infoToTango(String taco_info)
	{

		//	Parse each field
		Vector v = new Vector();
		StringTokenizer st = new StringTokenizer(taco_info, "\n");
		while (st.hasMoreTokens())
			v.add(st.nextToken());

		String[]	info = new String[v.size()];
		//	If one line (error) do not  format
		if (v.size()==1)
			info[0] = (String)v.elementAt(0);
		else
		for (int i=0 ; i<v.size() ; i++)
		{
			String line = (String)v.elementAt(i);
			int	idx = line.indexOf(":");
			if (idx>0)
				info[i] = line.substring(idx+1).trim();
			else
				info[i] = line;
		}
		return info;
	}
	//======================================================
	//======================================================
}
