package fr.soleil.tango.clientapi.command;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceData;

public interface ICommandExtractor {
    Object extract(final DeviceData dd) throws DevFailed;
}
