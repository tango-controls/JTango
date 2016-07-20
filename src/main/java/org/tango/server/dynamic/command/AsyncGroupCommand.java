/**
 * Copyright (C) :     2012
 *
 * 	Synchrotron Soleil
 * 	L'Orme des merisiers
 * 	Saint Aubin
 * 	BP48
 * 	91192 GIF-SUR-YVETTE CEDEX
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
package org.tango.server.dynamic.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.tango.server.StateMachineBehavior;
import org.tango.server.command.CommandConfiguration;
import org.tango.server.command.ICommandBehavior;
import org.tango.utils.TangoUtil;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;

/**
 * Send void commands and forget
 *
 * @author ABEILLE
 *
 */
public class AsyncGroupCommand implements ICommandBehavior {

    private final CommandConfiguration config = new CommandConfiguration();
    private final Map<DeviceProxy, String> proxyList = new HashMap<DeviceProxy, String>();

    public AsyncGroupCommand(final String name, final String... commandFullNames) throws DevFailed {
        config.setName(name);
        config.setInType(void.class);
        config.setOutType(void.class);
        config.setInTypeDesc("execute commands " + Arrays.toString(commandFullNames));
        for (final String commandName : commandFullNames) {
            final String deviceName = TangoUtil.getFullDeviceNameForCommand(commandName);
            final DeviceProxy deviceProxy = new DeviceProxy(deviceName);
            proxyList.put(deviceProxy, TangoUtil.getAttributeName(commandName));
        }
    }

    @Override
    public CommandConfiguration getConfiguration() throws DevFailed {
        return config;
    }

    @Override
    public Object execute(final Object arg) throws DevFailed {
        for (final Entry<DeviceProxy, String> entry : proxyList.entrySet()) {
            entry.getKey().command_inout_asynch(entry.getValue());
        }
        return null;
    }

    @Override
    public StateMachineBehavior getStateMachine() throws DevFailed {
        return null;
    }

}
