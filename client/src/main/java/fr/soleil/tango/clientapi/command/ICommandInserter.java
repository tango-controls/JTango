package fr.soleil.tango.clientapi.command;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceData;

public interface ICommandInserter {

    void insert(final DeviceData dd, final Object value) throws DevFailed;
}
