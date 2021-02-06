package org.tango.it;

import fr.esrf.Tango.DevFailed;

import java.io.IOException;
import java.util.List;

public interface ITWithoutTangoDB {

    void startDevices(String deviceList) throws DevFailed, IOException;
    void stopDevices() throws DevFailed;
    String getDefaultDeviceFullName();
    List<String> getDeviceFullNameList();
    String getFullAdminName();
}
