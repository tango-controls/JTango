//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the AttributeAlarmInfo class definition .
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:58  ounsy
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

import fr.esrf.Tango.AttributeAlarm;


/**
 *	Class Description: This class is the same class as AttributeAlarm, 
 *	                   but created for C++ compatibility.
 *
 * @author  verdier
 * @version  $Revision$
 */
public class AttributeAlarmInfo implements java.io.Serializable
{
	public String min_alarm = "";
	public String max_alarm = "";
	public String min_warning = "";
	public String max_warning = "";
	public String delta_t = "";
	public String delta_val = "";
	public String[] extensions;
	//-======================================================================
	//-======================================================================
	public AttributeAlarmInfo(String min_alarm, String max_alarm, String min_warning, String max_warning, String delta_t, String delta_val, String[] extensions)
	{
		this.min_alarm   = min_alarm;
		this.max_alarm   = max_alarm;
		this.min_warning = min_warning;
		this.max_warning = max_warning;
		this.delta_t     = delta_t;
		this.delta_val   = delta_val;
		this.extensions  = extensions;
	}
	//-======================================================================
	//-======================================================================
	public AttributeAlarmInfo(AttributeAlarm al)
	{
		this.min_alarm   = al.min_alarm;
		this.max_alarm   = al.max_alarm;
		this.min_warning = al.min_warning;
		this.max_warning = al.max_warning;
		this.delta_t     = al.delta_t;
		this.delta_val   = al.delta_val;
		this.extensions  = al.extensions;
	}
	//-======================================================================
	//-======================================================================
	public AttributeAlarm getTangoObj()
	{
		return new AttributeAlarm(min_alarm, max_alarm,
								min_warning, max_warning,
								delta_t, delta_val, extensions);
	}
}
