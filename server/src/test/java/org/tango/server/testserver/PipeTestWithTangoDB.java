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

import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.Database;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.tango.server.ServerManager;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.CallBack;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoApi.events.EventData;
import fr.esrf.TangoDs.TangoConst;

/**
 * Integration tests with tango db
 */
@Ignore
public class PipeTestWithTangoDB {

    private static final String deviceName = "tango9/java/pipe.1";


    @BeforeClass
    public static void start() throws DevFailed {
        Database tangoDb = ApiUtil.get_db_obj();
        tangoDb.add_device(deviceName, PipeServer.class.getSimpleName(), PipeServer.SERVER_NAME + "/"+PipeServer.INSTANCE_NAME);
      //  System.setProperty("TANGO_HOST", "tango9-db1.ica.synchrotron-soleil.fr:20001");
        PipeServer.start();
    }

    @AfterClass
    public static void stopDevice() throws DevFailed {
        ServerManager.getInstance().stop();
    }

    @Test
    public void testPipeEvent() throws DevFailed {
        final DeviceProxy dev = new DeviceProxy(deviceName);
        try {

            dev.subscribe_event("mypipe", TangoConst.PIPE_EVENT, new CallBack() {
                @Override
                public void push_event(final EventData evt) {
                    System.out.println("///////Received event " + evt.devicePipe.getPipeName());
                    // super.push_event(evt);
                    System.out.println(evt.devicePipe.getPipeBlob());

                }

            }, new String[0]);
            dev.command_inout("pushPipeEvent");
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            dev.command_inout("pushPipeEvent");
            dev.command_inout("pushPipeEvent");
            dev.command_inout("pushPipeEvent");
            try {
                Thread.sleep(20000);
            } catch (final InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (final DevFailed e) {
            e.printStackTrace();
            DevFailedUtils.printDevFailed(e);
            throw e;
        }

    }
}
