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
import java.net.ServerSocket;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tango.server.Constants;
import org.tango.server.ServerManager;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DispLevel;
import fr.esrf.Tango.PipeWriteType;
import fr.esrf.TangoApi.DevicePipe;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoApi.PipeBlob;
import fr.esrf.TangoApi.PipeDataElement;
import fr.esrf.TangoApi.PipeInfo;
import fr.esrf.TangoApi.PipeScanner;

public class PipesTest extends NoDBDeviceManager {

    @BeforeClass
    public static void startDevice() throws DevFailed, IOException {
        ServerSocket ss1 = null;
        try {
            ss1 = new ServerSocket(0);
            ss1.setReuseAddress(true);
            ss1.close();
            PipeServer.startNoDb(ss1.getLocalPort());
            deviceName = "tango://localhost:" + ss1.getLocalPort() + "/" + PipeServer.NO_DB_DEVICE_NAME + "#dbase=no";
            adminName = "tango://localhost:" + ss1.getLocalPort() + "/" + Constants.ADMIN_DEVICE_DOMAIN + "/"
                    + ServerManager.getInstance().getServerName() + "#dbase=no";
        } finally {
            if (ss1 != null) {
                ss1.close();
            }
        }
        System.out.println(deviceName);
    }

    @AfterClass
    public static void stopDevice() throws DevFailed {
        ServerManager.getInstance().stop();
    }

    @Test
    public void readPipeConfig() throws DevFailed {
        try {

            final DeviceProxy dev = new DeviceProxy(deviceName);
            final List<PipeInfo> infoList = dev.getPipeConfig();
            final PipeInfo info1 = infoList.get(0);
            assertThat(info1.getName(), equalTo("myPipe"));
            assertThat(info1.getLabel(), equalTo("coucou"));
            assertThat(info1.getDescription(), equalTo("hello"));
            assertThat(info1.getWriteType(), equalTo(PipeWriteType.PIPE_READ_WRITE));
            assertThat(info1.getLevel(), equalTo(DispLevel.EXPERT));

            final PipeInfo info2 = infoList.get(1);
            assertThat(info2.getName(), equalTo("myPipeRO"));
            assertThat(info2.getLabel(), equalTo("myPipeRO"));
            assertThat(info2.getDescription(), equalTo("No description"));
            assertThat(info2.getWriteType(), equalTo(PipeWriteType.PIPE_READ));
            assertThat(info2.getLevel(), equalTo(DispLevel.OPERATOR));

            /*
            PipeInfo info = infoList.get(3);
            info.setDescription("has been set by java Api");
            deviceProxy.setPipeConfig(infoList);
            */
        } catch (final DevFailed e) {
            e.printStackTrace();
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void writePipeConfig() throws DevFailed {
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final List<PipeInfo> infoList = dev.getPipeConfig();
        final PipeInfo info1 = infoList.get(0);
        info1.setDescription("description");
        dev.setPipeConfig(infoList);
        final List<PipeInfo> infoListRead = dev.getPipeConfig();
        final PipeInfo infoRead = infoListRead.get(0);
        assertThat(infoRead.getDescription(), equalTo("description"));
    }

    @Test
    public void readPipe() throws DevFailed {
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final DevicePipe pipe = dev.readPipe("myPipe");
        assertThat(pipe.getPipeName(), equalTo("myPipe"));
        assertThat(pipe.getPipeBlob().getDataElementNumber(), equalTo(1));
        assertThat(pipe.nextString(), equalTo("Gif"));
//        System.out.println(pipe.getPipeName());
//        System.out.println(ReflectionToStringBuilder.toString(pipe.getDevPipeDataObject(),
//                ToStringStyle.MULTI_LINE_STYLE));
//        System.out.println(ReflectionToStringBuilder.toString(pipe.getDevPipeDataObject().data_blob,
//                ToStringStyle.MULTI_LINE_STYLE));
//        System.out.println(ReflectionToStringBuilder.toString(pipe.getPipeBlob(), ToStringStyle.MULTI_LINE_STYLE));
    }

    @Test
    public void writePipe() throws DevFailed {
        try {
            // Build an inner blob
            final PipeBlob children = new PipeBlob("Name / Age");
            children.add(new PipeDataElement("Chloe", 30));
            children.add(new PipeDataElement("Nicolas", 28));
            children.add(new PipeDataElement("Auxane", 21));

            // Build the main blob and insert inner one
            final PipeBlob pipeBlob = new PipeBlob("Pascal");
            pipeBlob.add(new PipeDataElement("City", "Grenoble"));
            pipeBlob.add(new PipeDataElement("Values", new float[] { 1.23f, 4.56f, 7.89f }));
            pipeBlob.add(new PipeDataElement("Children", children));
            pipeBlob.add(new PipeDataElement("Status", DevState.RUNNING));

            final DevicePipe devicePipe = new DevicePipe("myPipe", pipeBlob);
            final DeviceProxy dev = new DeviceProxy(deviceName);

            // Then write the pipe
            dev.writePipe(devicePipe);

            // read back for assert
            final DevicePipe pipe = dev.readPipe("myPipe");
            assertThat(pipe.getPipeName(), equalTo("myPipe"));
            assertThat(pipe.getPipeBlob().getDataElementNumber(), equalTo(4));
            assertThat(pipe.nextString(), equalTo("Grenoble"));
            final float[] array = new float[3];
            pipe.nextArray(array, 3);
            assertThat(array, equalTo(new float[] { 1.23f, 4.56f, 7.89f }));
            final PipeScanner scanner = pipe.nextScanner();
            assertThat(scanner.nextInt(), equalTo(30));
            assertThat(scanner.nextInt(), equalTo(28));
            assertThat(scanner.nextInt(), equalTo(21));

            // init for others tests
            dev.command_inout("Init");
        } catch (final DevFailed e) {
            e.printStackTrace();
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

}
