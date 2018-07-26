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

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tango.server.Constants;
import org.tango.server.PolledObjectType;
import org.tango.server.ServerManager;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.soleil.tango.clientapi.TangoAttribute;
import fr.soleil.tango.clientapi.TangoCommand;

/**
 * TODO: test polling
 * 
 * @author ABEILLE
 * 
 */

public class ForwardedAttributeTest {

    private static String deviceNameRoot;
    private static String deviceName;
    private static String adminName;
    private static Process process;

    @BeforeClass
    public static void startDevice() throws DevFailed, IOException {

        ServerSocket ss1 = null;
        ServerSocket ss2 = null;
        try {
            // start root server
            ss2 = new ServerSocket(0);
            ss2.setReuseAddress(true);
            ss2.close();
            final String classpath = System.getProperty("java.class.path");
            final ProcessBuilder pb = new ProcessBuilder("java", "-cp", classpath, JTangoTest.class.getCanonicalName(),
                    "1", "NODB", Integer.toString(ss2.getLocalPort()));
            process = pb.start();

            inheritIO(process.getInputStream(), System.out);
            inheritIO(process.getErrorStream(), System.err);

            try {
                Thread.sleep(10000);
            } catch (final InterruptedException e) {
            }

            deviceNameRoot = "tango://localhost:" + ss2.getLocalPort() + "/" + JTangoTest.NO_DB_DEVICE_NAME
                    + "#dbase=no";
            ForwardedServer.setNoDbFwdAttributeName(deviceNameRoot + "/doubleScalar");

            // start forwarded server
            ss1 = new ServerSocket(0);
            ss1.setReuseAddress(true);
            ss1.close();
            ForwardedServer.startNoDb(ss1.getLocalPort());
            deviceName = "tango://localhost:" + ss1.getLocalPort() + "/" + ForwardedServer.NO_DB_DEVICE_NAME
                    + "#dbase=no";
            adminName = "tango://localhost:" + ss1.getLocalPort() + "/" + Constants.ADMIN_DEVICE_DOMAIN + "/"
                    + ServerManager.getInstance().getServerName() + "#dbase=no";

        } finally {
            if (ss1 != null) {
                ss1.close();
            }
            if (ss2 != null) {
                ss2.close();
            }
        }
    }

    public static void inheritIO(final InputStream src, final PrintStream dest) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Scanner sc = new Scanner(src);
                while (sc.hasNextLine()) {
                    dest.println("//forked process// "+ sc.nextLine());
                }
                dest.flush();
                sc.close();
            }
        }).start();
    }

    @AfterClass
    public static void stopDevice() throws DevFailed {
        process.destroy();
        ServerManager.getInstance().stop();
    }

    @Test
    public void readWriteForwaded() throws DevFailed {
        final TangoAttribute attr = new TangoAttribute(deviceName + "/testfowarded");
        final TangoAttribute attrRoot = new TangoAttribute(deviceNameRoot + "/doubleScalar");
        attr.write(2.3);
        final double result = attr.read(double.class);
        final double resultRoot = attrRoot.read(double.class);
        assertThat(result, equalTo(2.3));
        assertThat(resultRoot, equalTo(2.3));
    }

    @Test
    public void readWriteRoot() throws DevFailed {
        final TangoAttribute attr = new TangoAttribute(deviceName + "/testfowarded");
        final TangoAttribute attrRoot = new TangoAttribute(deviceNameRoot + "/doubleScalar");
        attrRoot.write(4.6);
        final double result = attr.read(double.class);
        final double resultRoot = attrRoot.read(double.class);
        assertThat(result, equalTo(4.6));
        assertThat(resultRoot, equalTo(4.6));
    }

    @Test
    public void setAttributeConfig() throws DevFailed {
        final TangoAttribute attr = new TangoAttribute(deviceName + "/testfowarded");
        final TangoAttribute attrRoot = new TangoAttribute(deviceNameRoot + "/doubleScalar");
        final AttributeInfoEx info = attr.getAttributeProxy().get_info_ex();
        assertThat(info.name, equalTo("testfowarded"));
        assertThat(info.label, equalTo("testfowarded"));
        System.out.println("name " + info.name);
        info.description = "a description";
        info.label = "a label";
        attr.getAttributeProxy().set_info(new AttributeInfoEx[] { info });
        final AttributeInfoEx infoRoot = attrRoot.getAttributeProxy().get_info_ex();
        assertThat(infoRoot.description, equalTo(info.description));
        assertThat(infoRoot.label, equalTo("doubleScalar"));
        final AttributeInfoEx info2 = attr.getAttributeProxy().get_info_ex();
        assertThat(info2.description, equalTo(info.description));
        assertThat(info2.label, equalTo("a label"));
        // reset value for others tests
        info.label = "testfowarded";
        attr.getAttributeProxy().set_info(new AttributeInfoEx[] { info });
    }

    @Test
    public void setAttributeConfigRoot() throws DevFailed {
        final TangoAttribute attr = new TangoAttribute(deviceName + "/testfowarded");
        final TangoAttribute attrRoot = new TangoAttribute(deviceNameRoot + "/doubleScalar");
        final AttributeInfoEx info = attrRoot.getAttributeProxy().get_info_ex();
        info.description = "a description";
        info.label = "a label";
        attrRoot.getAttributeProxy().set_info(new AttributeInfoEx[] { info });
        final AttributeInfoEx infoRoot = attrRoot.getAttributeProxy().get_info_ex();
        assertThat(infoRoot.description, equalTo(info.description));
        assertThat(infoRoot.label, equalTo(info.label));
        final AttributeInfoEx info2 = attr.getAttributeProxy().get_info_ex();
        assertThat(info2.description, equalTo(info.description));
        assertThat(info2.label, equalTo("testfowarded"));
        // reset value for others tests
        info.label = "doubleScalar";
        attrRoot.getAttributeProxy().set_info(new AttributeInfoEx[] { info });
    }

    @Test(expected = DevFailed.class)
    public void configurePolling() throws DevFailed {
        // install polling
        final TangoCommand cmd = new TangoCommand(adminName, "AddObjPolling");
        final int[] param1 = new int[] { 10 };
        final String[] param2 = new String[] { ForwardedServer.NO_DB_DEVICE_NAME,
                PolledObjectType.ATTRIBUTE.toString(), "testfowarded" };
        cmd.insertMixArgin(param1, param2);
        cmd.execute();
    }
}
