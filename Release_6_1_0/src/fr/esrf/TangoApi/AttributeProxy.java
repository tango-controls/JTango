//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.3  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.2  2007/08/23 08:32:59  ounsy
// updated change from api/java
//
// Revision 3.11  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.10  2005/08/30 07:27:01  pascal_verdier
// Set public some methods.
//
// Revision 3.9  2005/06/02 14:07:26  pascal_verdier
// IDL version added.
//
// Revision 3.8  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.7  2004/11/05 11:59:20  pascal_verdier
// Attribute Info TANGO 5 compatibility.
//
// Revision 3.6  2004/04/15 14:25:47  ounsy
// Replaced EventCallback with Callback
//
// Revision 3.5  2004/03/12 13:15:22  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.1  2004/03/08 11:35:40  pascal_verdier
// AttributeProxy and aliases management added.
// First revision for event management classes.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoDs.Except;

/**
 *	Class Description:
 *	This class manage device connection for Tango attribute access.
 *
 * @author  verdier
 * @version  $Revision$
 */
public class AttributeProxy implements ApiDefs, java.io.Serializable
{
	private	String		full_attname = null;
	private	String		attname = null;
	private String		alias = null;
	private	DeviceProxy	dev;
	private int			idl_version = -1;

	//===================================================================
	/**
	 *	AttributeProxy constructor. It will import the device.
	 *
	 *	@param	attname	name of the attribute or its alias.
	 */
	//===================================================================
	public AttributeProxy(String attname)	throws DevFailed
	{
		//	Check if alias or att name
		if (attname.indexOf('/')<0)
		{
			//	Get the attribute name for specified alias
			alias = attname;
			attname = ApiUtil.get_db_obj().get_attribute_alias(alias);
		}

		//	Extract device name
		String	devname =
				attname.substring(0, attname.lastIndexOf("/", attname.length()-1));

		//	Store full attribute name
		full_attname = attname;
		//	Store Tango attribute name
		this.attname =
			attname.substring(attname.lastIndexOf("/", attname.length()-1)+1);

		//	And crate DeviceProxy Object
		dev = new DeviceProxy(devname);
	}
	//==========================================================================
	//==========================================================================
	public void set_timeout_millis(int millis) throws DevFailed
	{
		dev.set_timeout_millis(millis);
	}
	//==========================================================================
	//==========================================================================
	public DeviceProxy getDeviceProxy()
	{
		return dev;
	}
	//==========================================================================
	//==========================================================================
	public int get_idl_version()	throws DevFailed
	{
		if (idl_version<0)
			idl_version = dev.get_idl_version();
		return idl_version;
	}
	//==========================================================================
	/**
	 *	Return full attribute name
	 */
	//==========================================================================
	public String fullName()
	{
		return full_attname;
	}
	//==========================================================================
	/**
	 *	Return attribute name
	 */
	//==========================================================================
	public String name()
	{
		return attname;
	}
	//==========================================================================
	/**
	 *	Ping the device proxy of this attribute.
	 */
	//==========================================================================
	public long ping() throws DevFailed
	{
		return dev.ping();
	}
	//==========================================================================
	/**
	 *	Check state of the device proxy of this attribute.
	 */
	//==========================================================================
	public DevState state() throws DevFailed
	{
		return dev.state();
	}
	//==========================================================================
	/**
	 *	Check status of the device proxy of this attribute.
	 */
	//==========================================================================
	public String status() throws DevFailed
	{
		return dev.status();
	}
	//==========================================================================
	/**
	 *	Query the database for a device attribute
	 *	property for this device.
	 *
	 *	@return property in DbAttribute object.
	 */
	//==========================================================================
	public DbAttribute get_property()
				throws DevFailed
	{
		return dev.get_attribute_property(attname);
	}
	//==========================================================================
	/**
	 *	Insert or update an attribute properties for this device.
	 *	The property names and their values are specified by the DbAttribute array.
	 *	
	 *	@param property attribute  property (names and values).
	 */
	//==========================================================================
	public void put_property(DbDatum property)
				throws DevFailed
	{
		DbAttribute	db_att = new DbAttribute(attname);
		db_att.add(property);
		dev.put_attribute_property(db_att);
	}

	//==========================================================================
	/**
	 *	Insert or update an attribute properties for this device.
	 *	The property names and their values are specified by the DbAttribute array.
	 *	
	 *	@param properties attribute  properties (names and values).
	 */
	//==========================================================================
	public void put_property(DbDatum[] properties)
				throws DevFailed
	{
		DbAttribute	db_att = new DbAttribute(attname);
		for (int i=0 ; i<properties.length ; i++)
			db_att.add(properties[i]);
		dev.put_attribute_property(db_att);
	}

	//==========================================================================
	/**
	 *	Delete a property for this object.
	 *	@param propname Property name.
	 */
	//==========================================================================
	public void delete_property(String propname)
				throws DevFailed
	{
		dev.delete_attribute_property(attname, propname);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this object.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public void delete_property(String[] propnames)
				throws DevFailed
	{
		dev.delete_attribute_property(attname, propnames);
	}
	//==========================================================================
	/**
	 *	Get the attribute info.
	 *
	 *	@return the attributes configuration.
	 */
	//==========================================================================
	public AttributeInfo get_info() throws DevFailed
	{
		return dev.get_attribute_info(attname);
	}
	//==========================================================================
	/**
	 *	Get the attribute extended info.
	 *
	 *	@return the attributes configuration.
	 */
	//==========================================================================
	public AttributeInfoEx get_info_ex() throws DevFailed
	{
		return dev.get_attribute_info_ex(attname);
	}
	//==========================================================================
	/**
	 *	Update the attributes info for the specified device.
	 *
	 *	@param attr the attributes info.
	 */
	//==========================================================================
	public void set_info(AttributeInfo[] attr) throws DevFailed
	{
		dev.set_attribute_info(attr);
	}
	//==========================================================================
	/**
	 *	Update the attributes extended info for the specified device.
	 *
	 *	@param attr the attributes info.
	 */
	//==========================================================================
	public void set_info(AttributeInfoEx[] attr) throws DevFailed
	{
		dev.set_attribute_info(attr);
	}
	//==========================================================================
	/**
	 *	Read the attribute value for the specified device.
	 *
	 *	@return the attribute value.
	 */
	//==========================================================================
	public DeviceAttribute read() throws DevFailed
	{
		return dev.read_attribute(attname);
	}
	//==========================================================================
	/**
	 *	Write the attribute value for the specified device.
	 *
	 *	@param devattr	attribute name and value.
	 */
	//==========================================================================
	public void write(DeviceAttribute devattr) throws DevFailed
	{
		dev.write_attribute(devattr);
	}
	//==========================================================================
	/**
	 *	Return the history for attribute polled.
	 *
	 *	@param	nb		nb data to read.
	 */
	//==========================================================================
	public DeviceDataHistory[] history(int nb) throws DevFailed
	{
		return dev.attribute_history(attname, nb);
	}
	//==========================================================================
	/**
	 *	Return the full history for attribute polled.
	 */
	//==========================================================================
	public DeviceDataHistory[] history() throws DevFailed
	{
		return dev.attribute_history(attname);
	}
	//==========================================================================
	/**
	 *	Add a attribute to be polled for the device.
	 *	If already polled, update its polling period.
	 *
	 *	@param	period	polling period.
	 */
	//==========================================================================
	public void poll(int period) throws DevFailed
	{
		dev.poll_attribute(attname, period);
	}
	//==========================================================================
	/**
	 *	Remove attribute of polled object list
	 */
	//==========================================================================
	public void stop_poll() throws DevFailed
	{
		dev.stop_poll_attribute(attname);
	}
	//==========================================================================
	/**
	 *	Asynchronous read_attribute.
	 */
	//==========================================================================
	public int read_asynch() throws DevFailed
	{
		return dev.read_attribute_asynch(attname);
	}
	//==========================================================================
	/**
	 *	Asynchronous read_attribute using callback for reply.
	 *
	 *	@param	cb		a CallBack object instance.
	 */
	//==========================================================================
	public void read_asynch(CallBack cb) throws DevFailed
	{
		dev.read_attribute_asynch(attname, cb);
	}
	//==========================================================================
	/**
	 *	Read Asynchronous read_attribute reply.
	 *
	 *	@param	id	asynchronous call id (returned by read_attribute_asynch).
	 *	@param	timeout	number of millisonds to wait reply before throw an excption.
	 */
	//==========================================================================
	public DeviceAttribute[] read_reply(int id, int timeout)
				throws DevFailed, AsynReplyNotArrived
	{
		return dev.read_attribute_reply(id, timeout);
	}
	//==========================================================================
	/**
	 *	Read Asynchronous read_attribute reply.
	 *
	 *	@param	id	asynchronous call id (returned by read_attribute_asynch).
	 */
	//==========================================================================
	public DeviceAttribute[] read_reply(int id)
				throws DevFailed, AsynReplyNotArrived
	{
		return dev.read_attribute_reply(id);
	}
	//==========================================================================
	/**
	 *	Asynchronous write_attribute.
	 *
	 *	@param	attr	Attribute value (name, writing value...)
	 */
	//==========================================================================
	public int write_asynch(DeviceAttribute attr)
				throws DevFailed
	{
		return dev.write_attribute_asynch(attr);
	}
	//==========================================================================
	/**
	 *	Asynchronous write_attribute.
	 *
	 *	@param	attr	Attribute value (name, writing value...)
	 *	@param	forget	forget the response if true
	 */
	//==========================================================================
	public int write_asynch(DeviceAttribute attr, boolean forget)
				throws DevFailed
	{
		return dev.write_attribute_asynch(attr, forget);
	}
	//==========================================================================
	/**
	 *	Asynchronous write_attribute using callback for reply.
	 *
	 *	@param	attr	Attribute values (name, writing value...)
	 *	@param	cb		a CallBack object instance.
	 */
	//==========================================================================
	public void write_asynch(DeviceAttribute attr, CallBack cb)
				throws DevFailed
	{
		dev.write_attribute_asynch(attr, cb);
	}
	//==========================================================================
	/**
	 *	check for Asynchronous write_attribute reply.
	 *
	 *	@param	id	asynchronous call id (returned by read_attribute_asynch).
	 */
	//==========================================================================
	public void write_reply(int id)
			throws DevFailed, AsynReplyNotArrived
	{
		dev.write_attribute_reply(id);
	}
	//==========================================================================
	/**
	 *	check for Asynchronous write_attribute reply.
	 *
	 *	@param	id	asynchronous call id (returned by write_attribute_asynch).
	 *	@param	timeout	number of millisonds to wait reply before throw an excption.
	 */
	//==========================================================================
	public void write_reply(int id, int timeout)
			throws DevFailed, AsynReplyNotArrived
	{
		dev.write_attribute_reply(id, timeout);
	}
	//==========================================================================
	/**
	 *	Subscribe to an event.
	 *
	 *	@param	event      event name.
     *  @param  callback   event callback.
	 */
	//==========================================================================
	public int subscribe_event(int event, CallBack callback, String[] filters)
                   throws DevFailed
    {
		return dev.subscribe_event(attname, event, callback, filters);
	}
	//==========================================================================
	//==========================================================================







	//==========================================================================
	/**
	 *	Just a main method to check API methods.
	 */
	//==========================================================================
	public static void main (String args[])
	{
		String	attname = "tango/admin/corvus/hoststate";
		try
		{
			AttributeProxy	att = new AttributeProxy(attname);
			att.ping();
			System.out.println(att.name() + " is alive");
			DbAttribute	db_att = att.get_property();
			for (int i=0 ; i<db_att.size() ; i++)
			{
				DbDatum		datum = db_att.datum(i);
				System.out.println(datum.name + " : " + datum.extractString());
			}
			
			DeviceAttribute da = att.read();
			System.out.println(att.name() + " : " + da.extractShort());
			System.out.println(att.name() + " state  : " + ApiUtil.stateName(att.state()));
			System.out.println(att.name() + " status : " + att.status());
		}
		catch(DevFailed e)
		{
			Except.print_exception(e);
		}
	}
}
