//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the CallBack class definition .
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:58  ounsy
// updated change from api/java
//
// Revision 3.7  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.1  2004/03/08 11:35:40  pascal_verdier
// AttributeProxy and aliases management added.
// First revision for event management classes.
//
// Revision 3.0  2003/04/29 08:03:28  pascal_verdier
// Asynchronous calls added.
// Logging related methods.
// little bugs fixed.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.TangoApi.events.EventData;

/**
 *	<b>Class Description :</b><Br>
 *	 This class define the object to be be called at 
 *	command_inout/reat_attribute/write_attribute asynchronous call reply.
 * <Br><Br>
 *	<Br><Br>
 *	<Br><b> Usage example: </b> <Br>
 *	<ul><i>
 *     class MyCallback extends CallBack<Br>
 *     {<Br><ul>
 *        public void cmd_ended(CmdDoneEvent evt)<Br>
 *        {<Br><ul>
 *           if (evt.err)<Br>
 *              Except.print_exception(evt.errors);<Br>             
 *           else<Br>
 *              System.out.println("The command " + evt.cmdname +<Br><ul>
 *                        " returns " + evt.argout.extractDouble());<Br></ul></ul>
 *        } <Br></ul>
 *     }<Br>
 *<Br>
 *    class MyClass <Br>
 *    {<Br><ul>
 *       public set_read_current(double setpoint)<Br>
 *       {<Br><ul>
 *              :        :        :<Br>
 *              :        :        :<Br>
 *              :        :        :<Br>
 *           DeviceData data = new DeviceData();<Br>
 *           data.insert(setpoint);<Br>
 *           dev.command_inout_asynch("SetReadCurrent", data, MyCallback);<Br>
 *              :        :        :<Br>
 *              :        :        :<Br>
 *              :        :        :<Br></ul>
 *       }<Br></ul>
 *    }<Br>
 *	</ul></i>
 */


public class  CallBack implements java.io.Serializable
{
	//===============================================================
	//===============================================================
	public void cmd_ended(CmdDoneEvent evt)
	{
	}
	//===============================================================
	//===============================================================
	public void attr_read(AttrReadEvent evt)
	{
	}
	//===============================================================
	//===============================================================
	public void attr_written(AttrWrittenEvent evt)
	{
	}
	//===============================================================
	//===============================================================
	public void push_event(EventData evt)
	{
	}
}
