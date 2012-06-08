//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the AttributeInfoEx class definition .
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:59  ounsy
// updated change from api/java
//
// Revision 3.3  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.2  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.1  2004/11/05 11:59:19  pascal_verdier
// Attribute Info TANGO 5 compatibility.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================
package fr.esrf.TangoApi;

import fr.esrf.Tango.AttributeConfig;
import fr.esrf.Tango.AttributeConfig_2;
import fr.esrf.Tango.AttributeConfig_3;


/**
 *	Class Description: This class is an extention of AttributeInfo class.
 *	<Br><Br>
 *	<Br><b> Usage example: </b> <Br>
 *	<ul><i>
 *	AttributeInfoEx[]	ac = dev.get_attribute_info_ex();	<Br>
 *	for (int i=0 ; i < ac.length ; i++) <Br>
 *	{	<Br><ul>
 *		System.out.println(ac[i].name +
 *           " generate a change event for an absolute change of " +
 *           ac[i].events.ch_event.abs_change);	<Br>
 *		</ul>
 *	} <Br>
 *	</ul></i>
 *
 *
 * @author  verdier
 * @version  $Revision$
 */


public class AttributeInfoEx extends AttributeInfo implements ApiDefs, java.io.Serializable
{
	public AttributeAlarmInfo	alarms = null;
	public AttributeEventInfo	events = null;
	public String[]				extensions = null;
	public String[]				sys_extensions = null;

	//==========================================================================
	//==========================================================================
	public AttributeInfoEx(AttributeConfig_3 ac)
	{
		super(ac);
		alarms = new AttributeAlarmInfo(ac.att_alarm);
		events = new AttributeEventInfo(ac.event_prop);
		extensions     = ac.extensions;
		sys_extensions = ac.sys_extensions;
	}
	//==========================================================================
	//==========================================================================
	public AttributeInfoEx(AttributeConfig_2 ac)
	{
		super(ac);
		extensions     = new String[0];
		sys_extensions = new String[0];
	}
	//==========================================================================
	//==========================================================================
	public AttributeInfoEx(AttributeConfig ac)
	{
		super(ac);
		extensions     = new String[0];
		sys_extensions = new String[0];
	}

	//==========================================================================
	//==========================================================================
	public AttributeConfig_3 get_attribute_config_obj_3()
	{
		return new AttributeConfig_3(name, writable, data_format, data_type,
						max_dim_x, max_dim_y, description, label, unit, 
						standard_unit, display_unit, format, min_value, 
						max_value, writable_attr_name, level, 
						alarms.getTangoObj(),
						events.getTangoObj(),
						extensions, sys_extensions);
	}
}
