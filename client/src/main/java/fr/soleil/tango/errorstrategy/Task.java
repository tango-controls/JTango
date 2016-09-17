package fr.soleil.tango.errorstrategy;

import fr.esrf.Tango.DevFailed;

public interface Task<T> {
    T call() throws DevFailed;
}
