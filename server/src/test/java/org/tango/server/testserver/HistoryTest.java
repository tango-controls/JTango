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

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.DeviceDataHistory;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.tango.clientapi.TangoCommand;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.tango.DeviceState;
import org.tango.server.Constants;
import org.tango.server.PolledObjectType;
import org.tango.server.ServerManager;
import org.tango.utils.DevFailedUtils;

import java.util.Arrays;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

//TODO move to integration tests (start db)
@Ignore(value = "attribute_history does not work without db")
public class HistoryTest {

    // XXX: device must be declared in tango db before running this test
    private static String deviceName = "test/tango/jtangotest.1";

    @BeforeClass
    public static void connect() throws DevFailed {
        JTangoTest.start();
    }

    @AfterClass
    public static void disconnect() throws DevFailed {
        ServerManager.getInstance().stop();
    }

    @Test
    public void fillHistory() throws DevFailed {
        try {
            DeviceProxy dev = new DeviceProxy(deviceName);
            TangoCommand cmd = new TangoCommand(deviceName, "fillHistory");
            cmd.execute();
            DeviceDataHistory[] history = dev.attribute_history("fillHistory");
            for (int i = 0; i < history.length; i++) {
                // System.out.println(history[i].getErrStack());
                assertThat(history[i].extractShort(), equalTo((short) i));
            }
        } catch (DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void attributeHistory() throws DevFailed {
        // install polling
        final String adminName = Constants.ADMIN_DEVICE_DOMAIN + "/" + ServerManager.getInstance().getServerName();
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "AddObjPolling");
            final int[] param1 = new int[]{10};
            final String[] param2 = new String[]{deviceName, PolledObjectType.ATTRIBUTE.toString(), "shortScalar"};
            cmd.insertMixArgin(param1, param2);
            cmd.execute();

            // get its history
            final DeviceProxy dev = new DeviceProxy(deviceName);
            final DeviceDataHistory[] hist = dev.attribute_history("shortScalar");

            for (final DeviceDataHistory element : hist) {
                assertThat(element.extractShort(), equalTo((short) 10));
            }
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        } finally {

            final TangoCommand rem = new TangoCommand(adminName, "RemObjPolling");
            rem.execute(deviceName, PolledObjectType.ATTRIBUTE.toString(), "shortScalar");
        }

    }

    @Test
    public void stateHistory() throws DevFailed {
        // install polling
        final String adminName = Constants.ADMIN_DEVICE_DOMAIN + "/" + ServerManager.getInstance().getServerName();
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "AddObjPolling");
            final int[] param1 = new int[]{10};
            final String[] param2 = new String[]{deviceName, PolledObjectType.ATTRIBUTE.toString(), "State"};
            cmd.insertMixArgin(param1, param2);
            cmd.execute();

            // get its history
            final DeviceProxy dev = new DeviceProxy(deviceName);
            final DeviceDataHistory[] hist = dev.attribute_history("State");

            for (final DeviceDataHistory element : hist) {
                System.out.println(element.extractDevState().value());
                assertThat(element.extractDevState(), equalTo(DevState.ON));
            }
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        } finally {

            final TangoCommand rem = new TangoCommand(adminName, "RemObjPolling");
            rem.execute(deviceName, PolledObjectType.ATTRIBUTE.toString(), "State");
        }
    }

    @Test
    public void stateCommandHistory() throws DevFailed {
        // install polling
        final String adminName = Constants.ADMIN_DEVICE_DOMAIN + "/" + ServerManager.getInstance().getServerName();
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "AddObjPolling");
            final int[] param1 = new int[]{10};
            final String[] param2 = new String[]{deviceName, PolledObjectType.ATTRIBUTE.toString(), "State"};
            cmd.insertMixArgin(param1, param2);
            cmd.execute();

            // get its history
            final DeviceProxy dev = new DeviceProxy(deviceName);
            final DeviceDataHistory[] hist = dev.command_history("State");

            for (final DeviceDataHistory element : hist) {
                System.out.println(element.extractDevState().value());
                assertThat(element.extractDevState(), equalTo(DevState.ON));
            }
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        } finally {

            final TangoCommand rem = new TangoCommand(adminName, "RemObjPolling");
            rem.execute(deviceName, PolledObjectType.ATTRIBUTE.toString(), "State");
        }
    }

    @Test
    public void stateSpectrumHistory() throws DevFailed {
        // install polling
        final String adminName = Constants.ADMIN_DEVICE_DOMAIN + "/" + ServerManager.getInstance().getServerName();
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "AddObjPolling");
            final int[] param1 = new int[]{10};
            final String[] param2 = new String[]{deviceName, PolledObjectType.ATTRIBUTE.toString(), "stateSpectrum"};
            cmd.insertMixArgin(param1, param2);
            cmd.execute();
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
            // final TangoCommand rem = new TangoCommand(adminName, "RemObjPolling");
            // rem.execute(deviceName, PolledObjectType.ATTRIBUTE.toString(), "stateSpectrum");
            // get its history
            final DeviceProxy dev = new DeviceProxy(deviceName);
            final DeviceDataHistory[] hist = dev.attribute_history("stateSpectrum");
            System.out.println(hist.length);
            for (final DeviceDataHistory element : hist) {
                final DevState[] result = element.extractDevStateArray();
                for (final DevState element2 : result) {
                    System.out.println(DeviceState.toString(element2));
                }
                System.out.println(Arrays.toString(element.extractDevStateArray()));
                assertThat(element.extractDevStateArray(), equalTo(new DevState[]{DevState.ON, DevState.OFF,
                        DevState.UNKNOWN}));

            }

        } catch (final DevFailed e) {
            e.printStackTrace();
            DevFailedUtils.printDevFailed(e);
            throw e;
        } finally {
            final TangoCommand rem = new TangoCommand(adminName, "RemObjPolling");
            rem.execute(deviceName, PolledObjectType.ATTRIBUTE.toString(), "stateSpectrum");
        }

    }

    @Test
    public void attributeSpectrumHistory() throws DevFailed {
        // install polling
        final String adminName = Constants.ADMIN_DEVICE_DOMAIN + "/" + ServerManager.getInstance().getServerName();
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "AddObjPolling");
            final int[] param1 = new int[]{10};
            final String[] param2 = new String[]{deviceName, PolledObjectType.ATTRIBUTE.toString(), "pollSpectrum"};
            cmd.insertMixArgin(param1, param2);
            cmd.execute();
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
            // final TangoCommand rem = new TangoCommand(adminName, "RemObjPolling");
            // rem.execute(deviceName, PolledObjectType.ATTRIBUTE.toString(), "pollSpectrum");
            // get its history
            final DeviceProxy dev = new DeviceProxy(deviceName);
            final DeviceDataHistory[] hist = dev.attribute_history("pollSpectrum");
            System.out.println(hist.length);
            for (final DeviceDataHistory element : hist) {
                if (!element.failed) {
                    System.out.println(Arrays.toString(element.extractLong64Array()));
                    assertThat(element.extractLong64Array(), equalTo(new long[]{1, 2, 0}));
                } else {
                    assertThat(element.getErrStack()[0].desc, equalTo("error pollSpectrum"));
                    System.out.println(element.getErrStack()[0].desc);
                }
            }

        } catch (final DevFailed e) {
            e.printStackTrace();
            DevFailedUtils.printDevFailed(e);
            throw e;
        } finally {
            final TangoCommand rem = new TangoCommand(adminName, "RemObjPolling");
            rem.execute(deviceName, PolledObjectType.ATTRIBUTE.toString(), "pollSpectrum");
        }

    }

    @Test
    public void commandHistory() throws DevFailed {
        // install polling
        final String adminName = Constants.ADMIN_DEVICE_DOMAIN + "/" + ServerManager.getInstance().getServerName();
        try {

            final TangoCommand cmd = new TangoCommand(adminName, "AddObjPolling");
            final int[] param1 = new int[]{100};
            final String[] param2 = new String[]{deviceName, PolledObjectType.COMMAND.toString(), "testPolling"};
            cmd.insertMixArgin(param1, param2);
            cmd.execute();
            // sleep to allow history to be filled
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
            final DeviceProxy dev = new DeviceProxy(deviceName);
            final DeviceDataHistory[] hist = dev.command_history("testPolling");
            for (final DeviceDataHistory element : hist) {
                if (!element.failed) {
                    // System.out.println(element.extractDouble());
                    assertThat(element.extractDouble(), equalTo(12.0));
                } else {
                    assertThat(element.getErrStack()[0].desc, equalTo("error"));
                    // System.out.println(element.getErrStack()[0].desc);
                }
            }

        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        } finally {
            final TangoCommand rem = new TangoCommand(adminName, "RemObjPolling");
            rem.execute(deviceName, PolledObjectType.COMMAND.toString(), "testPolling");
        }
    }

    @Test
    public void commandHistoryArray() throws DevFailed {
        // install polling
        final String adminName = Constants.ADMIN_DEVICE_DOMAIN + "/" + ServerManager.getInstance().getServerName();
        try {

            final TangoCommand cmd = new TangoCommand(adminName, "AddObjPolling");
            final int[] param1 = new int[]{100};
            final String[] param2 = new String[]{deviceName, PolledObjectType.COMMAND.toString(), "testPollingArray"};
            cmd.insertMixArgin(param1, param2);
            cmd.execute();
            final DeviceProxy dev = new DeviceProxy(deviceName);
            final DeviceDataHistory[] hist = dev.command_history("testPollingArray");
            for (final DeviceDataHistory element : hist) {
                assertThat(element.extractLongArray(), equalTo(new int[]{1, 2}));
            }

        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        } finally {
            final TangoCommand rem = new TangoCommand(adminName, "RemObjPolling");
            rem.execute(deviceName, PolledObjectType.COMMAND.toString(), "testPollingArray");
        }
    }
}
