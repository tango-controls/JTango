/**
 * Copyright (C) :     2012
 * <p>
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
 * <p>
 * This file is part of Tango.
 * <p>
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.testserver;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import org.tango.DeviceState;
import org.tango.server.ServerManager;
import org.tango.server.annotation.Attribute;
import org.tango.server.annotation.AttributeProperties;
import org.tango.server.annotation.Command;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.DeviceManagement;
import org.tango.server.annotation.Init;
import org.tango.server.annotation.State;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.device.DeviceManager;
import org.tango.server.events.EventType;
import org.tango.utils.DevFailedUtils;

/**
 * A device to test Tango events.
 *
 * @author ABEILLE
 */
@Device
public class EventServer {

    public static final String INSTANCE_NAME = "1";
    public static final String NO_DB_DEVICE_NAME = "1/2/3";
    public static final String SERVER_NAME = EventServer.class.getSimpleName();
    @Attribute(isPolled = true, pollingPeriod = 100)
    @AttributeProperties(changeEventAbsolute = "1", periodicEvent = "100")
    private final double[] doubleArrayAtt = new double[]{1};
    @Attribute(isPolled = true, pollingPeriod = 100)
    private final String[] stringArrayAtt = new String[]{"1"};
    @Attribute(isPolled = true, pollingPeriod = 100)
    @AttributeProperties(changeEventAbsolute = "100")
    private final boolean[] booleanArrayAtt = new boolean[]{false};
    @Attribute(isPolled = true, pollingPeriod = 100)
    DeviceState[] stateArray = new DeviceState[]{DeviceState.OFF};
    @DeviceManagement
    DeviceManager deviceManager;
    @Attribute(isPolled = true, pollingPeriod = 100)
    @AttributeProperties(changeEventAbsolute = "1", periodicEvent = "100")
    private volatile double doubleAtt = 1;
    @Attribute(isPolled = true, pollingPeriod = 100)
    @AttributeProperties(changeEventRelative = "1")
    private double changeRelative = 1;
    @Attribute(isPolled = true, pollingPeriod = 100)
    private String stringAtt = "1";
    @Attribute(isPolled = true, pollingPeriod = 100)
    @AttributeProperties(changeEventAbsolute = "5")
    private int qualityAtt;
    private AttrQuality quality = AttrQuality.ATTR_VALID;
    @Attribute(isPolled = true, pollingPeriod = 100)
    @AttributeProperties(archiveEventPeriod = "100")
    private long archive = 1;
    @Attribute(isPolled = true, pollingPeriod = 100)
    @AttributeProperties(changeEventAbsolute = "100")
    private boolean booleanAtt = false;
    @Attribute(isPolled = true, pollingPeriod = 100)
    @AttributeProperties(changeEventAbsolute = "1")
    private DevEncoded devEncodedAttr;
    private byte counterEncoded = 0;
    @State
    private DeviceState state = DeviceState.OFF;
    private int counter = 1;
    private int error = 0;

    public static void startNoDb(final int portNr) throws DevFailed {
        System.setProperty("OAPort", Integer.toString(portNr));
        ServerManager.getInstance().addClass(EventServer.class.getCanonicalName(), EventServer.class);
        ServerManager.getInstance().startError(new String[]{INSTANCE_NAME, "-nodb", "-dlist", NO_DB_DEVICE_NAME},
                SERVER_NAME);
    }

    public static void start() throws DevFailed {
        ServerManager.getInstance().addClass(EventServer.class.getSimpleName(), EventServer.class);
        ServerManager.getInstance().startError(new String[]{INSTANCE_NAME}, EventServer.class.getSimpleName());
        System.out.println("Event server started ");
    }

    public static void main(final String[] args) throws DevFailed {
        //System.setProperty("TANGO_HOST", "tango9-db1.ica.synchrotron-soleil.fr:20001");
        EventServer.start();
    }

    public double getDoubleAtt() throws DevFailed {
        doubleAtt = doubleAtt + 1;
        final String value = Double.toString(doubleAtt);
        final AttributePropertiesImpl properties = deviceManager.getAttributeProperties("doubleAtt");
        properties.setLabel(value);
        properties.setArchivingEventAbsChange(value);
        deviceManager.setAttributeProperties("doubleAtt", properties);
        return doubleAtt;
    }

    public double getChangeRelative() throws DevFailed {
        changeRelative = changeRelative + 1;
        return changeRelative;
    }

    public double[] getDoubleArrayAtt() throws DevFailed {
        doubleArrayAtt[0] = doubleArrayAtt[0] + 1;
        return doubleArrayAtt;
    }

    public String getStringAtt() {
        stringAtt = Double.toString(Double.parseDouble(stringAtt) + 1);
        return stringAtt;
    }

    public String[] getStringArrayAtt() {
        stringArrayAtt[0] = Double.toString(Double.parseDouble(stringArrayAtt[0]) + 1);
        return stringArrayAtt;
    }

    public AttributeValue getQualityAtt() throws DevFailed {
        quality = quality == AttrQuality.ATTR_VALID ? AttrQuality.ATTR_CHANGING : AttrQuality.ATTR_VALID;
        return new AttributeValue(10, quality);
    }

    public long getArchive() {
        archive = archive + 1;
        return archive;
    }

    public AttributeValue getBooleanAtt() throws DevFailed {
        final AttributeValue val = new AttributeValue();
        booleanAtt = !booleanAtt;
        val.setValue(booleanAtt, 34567L);
        return val;
    }

    public boolean[] getBooleanArrayAtt() {
        booleanArrayAtt[0] = !booleanArrayAtt[0];
        return booleanArrayAtt;
    }

    public DevEncoded getDevEncodedAttr() {
        devEncodedAttr = new DevEncoded("toto", new byte[]{counterEncoded});
        counterEncoded++;
        return devEncodedAttr;
    }

    @Init
    public void init() throws DevFailed {
        deviceManager.startPolling("State", 100);

    }

    @Attribute
    public int getErrorAtt() throws DevFailed {
        throw DevFailedUtils.newDevFailed("test");
    }

    public DeviceState[] getStateArray() {
        stateArray = stateArray[0] == DeviceState.OFF ? new DeviceState[]{DeviceState.ON}
                : new DeviceState[]{DeviceState.OFF};
        return stateArray;
    }

    @Attribute(pushDataReady = true)
    public double getDataReady() {
        return 10.0;
    }

    @Command
    public void pushDataReady() throws DevFailed {
        deviceManager.pushDataReadyEvent("doubleArrayAtt", counter++);
    }

    @Attribute
    public String getUserEvent() throws DevFailed {
        return "Hello";
    }

    @Command
    public void pushUserEvent() throws DevFailed {
        deviceManager.pushEvent("userEvent", EventType.USER_EVENT);
    }

    @Attribute(pushChangeEvent = true, checkChangeEvent = true)
    @AttributeProperties(changeEventAbsolute = "100")
    public int getError() throws DevFailed {
        switch (error) {
            case 0:
                throw DevFailedUtils.newDevFailed("error0");
            case 1:
                throw DevFailedUtils.newDevFailed("error1");
            default:
                break;
        }
        return 0;
    }

    @Command
    public void setError(final int error) throws DevFailed {
        this.error = error;
    }

    @Command
    public void pushError() throws DevFailed {
        deviceManager.pushEvent("error", EventType.CHANGE_EVENT);
    }

    public DeviceState getState() throws DevFailed {
        state = state == DeviceState.OFF ? DeviceState.ON : DeviceState.OFF;
        return state;
    }

    @Command
    public void pushDeviceStateEvents() throws DevFailed {
        DeviceState value = DeviceState.FAULT;
        deviceManager.pushEvent("State", new AttributeValue(value), EventType.USER_EVENT);
    }

    @Command
    public void pushDevStateEvents() throws DevFailed {
        DevState value = DevState.ALARM;
        deviceManager.pushEvent("State", new AttributeValue(value), EventType.USER_EVENT);
    }

    public void setState(final DeviceState state) {
        this.state = state;
    }

    public void setDeviceManager(final DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }
}
