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

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "Device"
 *	@author JacORB IDL compiler V 2.2, 7-May-2004
 */

public class DevicePOATie
	extends DevicePOA
{
	private DeviceOperations _delegate;

	private POA _poa;
	public DevicePOATie(DeviceOperations delegate)
	{
		_delegate = delegate;
	}
	public DevicePOATie(DeviceOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public fr.esrf.Tango.Device _this()
	{
		return fr.esrf.Tango.DeviceHelper.narrow(_this_object());
	}
	public fr.esrf.Tango.Device _this(org.omg.CORBA.ORB orb)
	{
		return fr.esrf.Tango.DeviceHelper.narrow(_this_object(orb));
	}
	public DeviceOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(DeviceOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		else
		{
			return super._default_POA();
		}
	}
	public void write_attributes(fr.esrf.Tango.AttributeValue[] values) throws fr.esrf.Tango.DevFailed
	{
_delegate.write_attributes(values);
	}

	public java.lang.String name()
	{
		return _delegate.name();
	}

	public void ping() throws fr.esrf.Tango.DevFailed
	{
_delegate.ping();
	}

	public java.lang.String description()
	{
		return _delegate.description();
	}

	public fr.esrf.Tango.AttributeValue[] read_attributes(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.read_attributes(names);
	}

	public java.lang.String adm_name()
	{
		return _delegate.adm_name();
	}

	public fr.esrf.Tango.DevCmdInfo[] command_list_query() throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_list_query();
	}

	public fr.esrf.Tango.DevInfo info() throws fr.esrf.Tango.DevFailed
	{
		return _delegate.info();
	}

	public org.omg.CORBA.Any command_inout(java.lang.String command, org.omg.CORBA.Any argin) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_inout(command,argin);
	}

	public fr.esrf.Tango.DevState state()
	{
		return _delegate.state();
	}

	public java.lang.String status()
	{
		return _delegate.status();
	}

	public void set_attribute_config(fr.esrf.Tango.AttributeConfig[] new_conf) throws fr.esrf.Tango.DevFailed
	{
_delegate.set_attribute_config(new_conf);
	}

	public java.lang.String[] black_box(int n) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.black_box(n);
	}

	public fr.esrf.Tango.AttributeConfig[] get_attribute_config(java.lang.String[] names) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.get_attribute_config(names);
	}

	public fr.esrf.Tango.DevCmdInfo command_query(java.lang.String command) throws fr.esrf.Tango.DevFailed
	{
		return _delegate.command_query(command);
	}

}
