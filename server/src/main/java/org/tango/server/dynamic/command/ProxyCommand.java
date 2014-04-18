/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tango.server.dynamic.command;

import org.tango.server.StateMachineBehavior;
import org.tango.server.command.CommandConfiguration;
import org.tango.server.command.ICommandBehavior;

import fr.esrf.Tango.DevFailed;
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
	cc.setInTangoType(proxy.getArginType());
	cc.setOutTangoType(proxy.getArgoutType());
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
