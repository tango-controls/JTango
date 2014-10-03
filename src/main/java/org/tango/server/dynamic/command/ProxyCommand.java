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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tango.server.dynamic.command;

import org.tango.server.StateMachineBehavior;
import org.tango.server.command.CommandConfiguration;
import org.tango.server.command.ICommandBehavior;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.tango.clientapi.TangoCommand;

/**
 * A Tango dynamic command, that creates a proxy to an existing command The input and output types are the same as those
 * of the proxy command
 * 
 * @author hardion
 */
public final class ProxyCommand implements ICommandBehavior {

    // Configuration of attribute from the targeted attribute proxy
    private final CommandConfiguration cc;

    // Proxy Part
    private final TangoCommand proxy;

    /**
     * Ctr
     * 
     * @param commandName
     *            The name of the created command
     * @param commandProxyName
     *            The name of the proxy command with syntax like deviceName/commandName. i.e. tmp/test/device1/Start
     * @throws DevFailed
     */
    public ProxyCommand(final String commandName, final String commandProxyName) throws DevFailed {
        proxy = new TangoCommand(commandProxyName);
        cc = new CommandConfiguration();
        cc.setName(commandName);
        final int arginType = proxy.getArginType();

        cc.setInTangoType(arginType);
        // Fix: client api returns a different type that the server must provide
        final int argoutType = proxy.getArgoutType();
        if (argoutType == TangoConst.Tango_DEV_UCHAR) {
            cc.setOutTangoType(TangoConst.Tango_DEV_SHORT);
        } else if (argoutType == TangoConst.Tango_DEV_USHORT) {
            cc.setOutTangoType(TangoConst.Tango_DEV_LONG);
        } else if (argoutType == TangoConst.Tango_DEVVAR_USHORTARRAY) {
            cc.setOutTangoType(TangoConst.Tango_DEVVAR_LONGARRAY);
        } else if (argoutType == TangoConst.Tango_DEV_ULONG) {
            cc.setOutTangoType(TangoConst.Tango_DEV_LONG64);
        } else if (argoutType == TangoConst.Tango_DEVVAR_ULONGARRAY) {
            cc.setOutTangoType(TangoConst.Tango_DEVVAR_ULONG64ARRAY);
        } else {
            cc.setOutTangoType(argoutType);
        }
        cc.setInTypeDesc("proxied from " + proxy.getCommandName());
        cc.setOutTypeDesc("proxied from " + proxy.getCommandName());
    }

    @Override
    public StateMachineBehavior getStateMachine() {
        return null;
    }

    @Override
    public CommandConfiguration getConfiguration() {
        return cc;
    }

    /**
     * Execute the command on the proxy
     * 
     * @param arg
     *            The command input parameter
     * @return The result returned by the command
     */
    @Override
    public Object execute(final Object arg) throws DevFailed {
        return proxy.executeExtract(arg);
    }
}
