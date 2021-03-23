package org.tango.it;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.Database;

public interface ITWithTangDB {

    void addDevice(String deviceList) throws DevFailed;
    void removeDevice() throws DevFailed;
    String getDBDeviceName();
    Database getDatabase() throws DevFailed;
    void startTestDevice() throws DevFailed;
    void stopTestDevice() throws DevFailed;
}
