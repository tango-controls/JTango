/**
 * Copyright (C) :     2004
 *
 *     European Synchrotron Radiation Facility
 *     BP 220, Grenoble 38043
 *     FRANCE
 *
 * This file is part of Tango.
 *
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
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
