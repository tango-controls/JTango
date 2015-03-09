/**
 * Copyright (C) :     2012
 *
 * 	Synchrotron Soleil
 * 	L'Orme des merisiers
 * 	Saint Aubin
 * 	BP48
 * 	91192 GIF-SUR-YVETTE CEDEX
 *
 * This file is part of Tango.
 *
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.testserver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.tango.server.PolledObjectType;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.tango.clientapi.TangoAttribute;
import fr.soleil.tango.clientapi.TangoCommand;

public class AdminServerTest extends NoDBDeviceManager {

    @Test
    public void testQueryClass() throws DevFailed {
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "QueryClass");
            cmd.execute();
            assertThat(cmd.extractToString(","), containsString(JTangoTest.class.getCanonicalName()));
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void testQueryDevice() throws DevFailed {
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "QueryDevice");
            cmd.execute();
            assertThat(cmd.extractToString(","), containsString(JTangoTest.NO_DB_DEVICE_NAME));
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void testQueryWizardDevProperty() throws DevFailed {
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "QueryWizardDevProperty");
            cmd.execute(JTangoTest.class.getCanonicalName());
            assertThat(cmd.extractToString(","), containsString("myProp"));
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void testQueryWizardClassProperty() throws DevFailed {
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "QueryWizardClassProperty");
            cmd.execute(JTangoTest.class.getCanonicalName());
            assertThat(cmd.extractToString(","), containsString("myClassProp"));
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void testRestartDevice() throws DevFailed {
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "DevRestart");
            cmd.execute(JTangoTest.NO_DB_DEVICE_NAME);
            new DeviceProxy(deviceName).ping();
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void testDevEmptyPollStatus() throws DevFailed {
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "DevPollStatus");
            cmd.execute(JTangoTest.NO_DB_DEVICE_NAME);
            System.out.println(cmd.extractToString(","));
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    private void addAttPoll() throws DevFailed {
        final TangoCommand cmd = new TangoCommand(adminName, "AddObjPolling");
        final int[] param1 = new int[] { 3000 };
        final String[] param2 = new String[] { JTangoTest.NO_DB_DEVICE_NAME, PolledObjectType.ATTRIBUTE.toString(),
                "shortScalar" };
        cmd.insertMixArgin(param1, param2);
        cmd.execute();
    }

    private void remAttPoll() throws DevFailed {
        final TangoCommand rem = new TangoCommand(adminName, "RemObjPolling");
        rem.execute(JTangoTest.NO_DB_DEVICE_NAME, PolledObjectType.ATTRIBUTE.toString(), "shortScalar");
    }

    @Test
    public void testAddPollingAttr() throws DevFailed {
        try {
            addAttPoll();
            final TangoAttribute attr = new TangoAttribute(deviceName + "/shortScalar");
            attr.read();
            final TangoCommand status = new TangoCommand(adminName, "DevPollStatus");
            status.execute(JTangoTest.NO_DB_DEVICE_NAME);
            System.out.println(status.extractToString(","));
            remAttPoll();

        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void testAddPollingCmd() throws DevFailed {
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "AddObjPolling");
            final int[] param1 = new int[] { 500 };
            final String[] param2 = new String[] { JTangoTest.NO_DB_DEVICE_NAME, PolledObjectType.COMMAND.toString(),
                    "testPollingArray" };
            cmd.insertMixArgin(param1, param2);
            cmd.execute();
            final TangoCommand cmdPolled = new TangoCommand(deviceName + "/testPollingArray");
            cmdPolled.execute();
            final TangoCommand rem = new TangoCommand(adminName, "RemObjPolling");
            rem.execute(JTangoTest.NO_DB_DEVICE_NAME, PolledObjectType.COMMAND.toString(), "testPollingArray");

        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void testDevPollStatus() throws DevFailed {
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "DevPollStatus");
            cmd.execute(JTangoTest.NO_DB_DEVICE_NAME);
            System.out.println(cmd.extractToString(","));
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void testStopPolling() throws DevFailed {
        try {
            addAttPoll();
            final TangoCommand cmd = new TangoCommand(adminName, "StopPolling");
            cmd.execute();
            remAttPoll();
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void testStartPolling() throws DevFailed {
        try {
            addAttPoll();
            final TangoCommand cmd = new TangoCommand(adminName, "StopPolling");
            cmd.execute();
            final TangoCommand cmd2 = new TangoCommand(adminName, "StartPolling");
            cmd2.execute();
            remAttPoll();
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void testLoggingLevel() throws DevFailed {
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "SetLoggingLevel");
            final int[] param1 = new int[] { 1 };
            final String[] param2 = new String[] { JTangoTest.NO_DB_DEVICE_NAME };
            cmd.insertMixArgin(param1, param2);
            cmd.execute();

            final TangoCommand cmd2 = new TangoCommand(adminName, "GetLoggingLevel");
            final Object in = new String[] { JTangoTest.NO_DB_DEVICE_NAME };
            cmd2.execute(in);
            final int result = cmd2.getNumLongMixArrayArgout()[0];
            assertThat(result, equalTo(param1[0]));
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    // @Test need a device appender
    // public void testAddLogging() throws DevFailed {
    // try {
    // final TangoCommand cmd = new TangoCommand(adminName, "AddLoggingTarget");
    // final Object in = new String[] { JTangoTest.noDbDeviceName,
    // "device::toto" };
    // cmd.execute(in);
    // } catch (final DevFailed e) {
    // DevFailedUtils.printDevFailed(e);
    // throw e;
    // }
    // }

    @Test
    public void testStopLogging() throws DevFailed {
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "StopLogging");
            cmd.execute();
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void testStartLogging() throws DevFailed {
        try {
            final TangoCommand cmd = new TangoCommand(adminName, "StartLogging");
            cmd.execute();
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    // @Test
    // public void testRestartServer() throws DevFailed {
    // try {
    // final TangoCommand cmd = new TangoCommand(adminName, "RestartServer");
    // cmd.execute();
    // } catch (final DevFailed e) {
    // DevFailedUtils.printDevFailed(e);
    // // throw e;
    // }
    //
    // try {
    // System.out.println("try connect");
    // System.out.println(new DeviceProxy(deviceName).status());
    // } catch (final DevFailed e) {
    // DevFailedUtils.printDevFailed(e);
    // // throw e;
    // }
    //
    // }

    // @Test(expected = DevFailed.class)
    // public void testKillServer() throws DevFailed {
    // try {
    // try {
    // Thread.sleep(1000);
    // } catch (final InterruptedException e1) {
    // // TODO Auto-generated catch block
    // e1.printStackTrace();
    // }
    // final TangoCommand cmd = new TangoCommand(adminName, "Kill");
    // cmd.execute();
    // while (ServerManager.getInstance().isStarted()) {
    // try {
    // Thread.sleep(100);
    // } catch (final InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    //
    // new DeviceProxy("tango://localhost:" + JTangoTest.noDbGiopPort + "/" +
    // JTangoTest.noDbDeviceName
    // + "#dbase=no").ping();
    // } catch (final DevFailed e) {
    // DevFailedUtils.printDevFailed(e);
    // throw e;
    // }
    // }
}
