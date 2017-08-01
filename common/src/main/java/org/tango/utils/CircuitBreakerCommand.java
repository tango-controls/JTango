package org.tango.utils;

import fr.esrf.Tango.DevFailed;

public interface CircuitBreakerCommand {
    void execute() throws DevFailed;

    void getFallback() throws DevFailed;

    void notifyError(DevFailed e);
}
