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

import org.tango.server.ServerManager;
import org.tango.server.annotation.Command;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.DeviceManagement;
import org.tango.server.annotation.Init;
import org.tango.server.annotation.Pipe;
import org.tango.server.device.DeviceManager;
import org.tango.server.pipe.PipeValue;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DispLevel;
import fr.esrf.TangoApi.PipeBlob;
import fr.esrf.TangoApi.PipeDataElement;

@Device
public class PipeServer {

    public static final String INSTANCE_NAME = "1";

    public static final String SERVER_NAME = "PipeServer";

    public static final String NO_DB_DEVICE_NAME = "testpipe/1/2";

    public static String deviceName;

    public static String adminName;

    @DeviceManagement
    DeviceManager devManager;

    @Pipe(description = "hello", displayLevel = DispLevel._EXPERT, label = "coucou")
    private PipeValue myPipe;
    @Pipe
    private PipeValue myPipeRO;

    @Init
    public void init() {
        final PipeBlob myPipeBlob = new PipeBlob("Gwen");
        myPipeBlob.add(new PipeDataElement("City", "Gif"));
        myPipe = new PipeValue(myPipeBlob);
        final PipeBlob myPipeBlob2 = new PipeBlob("A");
        myPipeBlob2.add(new PipeDataElement("City", "B"));
        myPipeRO = new PipeValue(myPipeBlob2);
    }

    public PipeValue getMyPipe() {
        return myPipe;
    }

    public void setMyPipe(final PipeValue myPipe) {
        this.myPipe = myPipe;
    }

    public PipeValue getMyPipeRO() {
        return myPipeRO;
    }

    @Command
    public void pushPipeEvent() throws DevFailed {
        devManager.pushPipeEvent("myPipe", myPipe);
    }

    public static void startNoDb(final int portNr) throws DevFailed {
        System.setProperty("OAPort", Integer.toString(portNr));
        ServerManager.getInstance().addClass(PipeServer.class.getCanonicalName(), PipeServer.class);
        ServerManager.getInstance().startError(new String[] { INSTANCE_NAME, "-nodb", "-dlist", NO_DB_DEVICE_NAME },
                SERVER_NAME);
    }

    public void setDevManager(final DeviceManager devManager) {
        this.devManager = devManager;
    }

    public static void start() {
        ServerManager.getInstance().start(new String[] { INSTANCE_NAME }, PipeServer.class);
    }

    public static void main(final String[] args) {
        //System.setProperty("TANGO_HOST", "tango9-db1.ica.synchrotron-soleil.fr:20001");
        ServerManager.getInstance().start(new String[] { INSTANCE_NAME }, PipeServer.class);
    }

}
