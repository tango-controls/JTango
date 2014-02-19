package fr.soleil.tango.clientapi;

import java.util.HashMap;
import java.util.Map;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.tango.clientapi.factory.ProxyFactory;

public final class TangoDevice {
    private final Map<String, TangoAttribute> attributes = new HashMap<String, TangoAttribute>();
    private final Map<String, TangoCommand> commands = new HashMap<String, TangoCommand>();
    private final DeviceProxy dev;
    private final String deviceName;

    public TangoDevice(final String deviceName) throws DevFailed {
	this(ProxyFactory.getInstance().createDeviceProxy(deviceName));
    }

    public TangoDevice(final DeviceProxy deviceProxy) {
	super();
	deviceName = deviceProxy.name();
	dev = deviceProxy;
    }

    public DeviceProxy getDeviceProxy() {
	return dev;
    }

    public TangoAttribute getTangoAttribute(final String attributeName) throws DevFailed {
	TangoAttribute ta;
	if (attributes.containsKey(attributeName)) {
	    ta = attributes.get(attributeName);
	} else {
	    ta = new TangoAttribute(deviceName + "/" + attributeName);
	    attributes.put(attributeName, ta);
	}
	return ta;
    }

    public TangoCommand getTangoCommand(final String commandName) throws DevFailed {
	TangoCommand tc;
	if (commands.containsKey(commandName)) {
	    tc = commands.get(commandName);
	} else {
	    tc = new TangoCommand(deviceName, commandName);
	    commands.put(commandName, tc);
	}
	return tc;
    }

    public DevState state() throws DevFailed {
	return dev.state();
    }
}
