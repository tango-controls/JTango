//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the AsyncCallObject class definition .
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:57  ounsy
// updated change from api/java
//
// Revision 3.7  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:22  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.0  2003/04/29 08:03:29  pascal_verdier
// Asynchronous calls added.
// Logging related methods.
// little bugs fixed.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

 
package fr.esrf.TangoApi;
 
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import org.omg.CORBA.Request;


class  AsyncCallObject implements ApiDefs, java.io.Serializable
{
	int			id = 0;
	Request		request;
	DeviceProxy	dev;
	int			cmd_type;
	CallBack	cb;
	int			reply_model;
	String[]	names;


	//===============================================================
	//===============================================================
	AsyncCallObject(Request request, DeviceProxy dev, int cmd_type, 
					String[] names, int reply_model)
	{
		this.request     = request;
		this.dev         = dev;
		this.cmd_type    = cmd_type;
		this.names       = names;
		this.reply_model = reply_model;
	}
	//===============================================================
	//===============================================================
	AsyncCallObject(Request request, DeviceProxy dev, int cmd_type, String[] names)
	{
		this(request, dev, cmd_type, names, POLLING);
	}
	//===============================================================
	//===============================================================
	void command_inout_reply(int timeout)
	{
		DevError[]	errors = null;
		DeviceData	argout = null;
		try
		{
			if (timeout==NO_TIMEOUT)
				argout = dev.command_inout_reply(this);
			else
				argout = dev.command_inout_reply(this, timeout);
		}
		catch(AsynReplyNotArrived e)
		{
			errors = e.errors;
		}
		catch(DevFailed e)
		{
			errors = e.errors;
		}
		cb.cmd_ended(new CmdDoneEvent(dev, names[0], argout, errors));
	}
	//===============================================================
	//===============================================================
	void read_attribute_reply(int timeout)
	{
		DevError[]			errors = null;
		DeviceAttribute[]	argout = null;
		try
		{
			if (timeout==NO_TIMEOUT)
				argout = dev.read_attribute_reply(id);
			else
				argout = dev.read_attribute_reply(id, timeout);
		}
		catch(AsynReplyNotArrived e)
		{
			errors = e.errors;
		}
		catch(DevFailed e)
		{
			errors = e.errors;
		}
		cb.attr_read(new AttrReadEvent(dev, names, argout, errors));
	}
	//===============================================================
	//===============================================================
	void write_attribute_reply(int timeout)
	{
		DevError[]	errors = null;
		try
		{
			if (timeout==NO_TIMEOUT)
				dev.write_attribute_reply(id);
			else
				dev.write_attribute_reply(id, 0);
		}
		catch(AsynReplyNotArrived e)
		{
			errors = e.errors;
		}
		catch(DevFailed e)
		{
			errors = e.errors;
		}
		cb.attr_written(new AttrWrittenEvent(dev, names, errors));
	}
	//===============================================================
	//===============================================================
	void manage_reply(int timeout)
	{
		switch (cmd_type)
		{
		case CMD:
			command_inout_reply(timeout);
			break;
		case ATT_R:
			read_attribute_reply(timeout);
			break;
		case ATT_W:
			write_attribute_reply(timeout);
			break;
		}
	}
	//===============================================================
	//===============================================================
	void manage_reply()
	{
		manage_reply(NO_TIMEOUT);	//	No Timeout
	}
	//===============================================================
	//===============================================================
}
