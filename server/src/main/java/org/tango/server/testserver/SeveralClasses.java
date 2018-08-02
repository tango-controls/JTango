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
import org.tango.server.ServerManager;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.Init;
import org.tango.utils.DevFailedUtils;

/**
 * A server with 2 classes
 *
 * @author ABEILLE
 *
 */
public class SeveralClasses {

    public static void start() throws DevFailed {
        // System.setProperty("OAPort", Integer.toString(1245));
        ServerManager.getInstance().addClass(Class2.class.getSimpleName(), Class2.class);
        ServerManager.getInstance().addClass(Class1.class.getSimpleName(), Class1.class);

//        ServerManager.getInstance().startError(new String[] { "instance", "-nodb", "-dlist", "deviceName", "device2" },
//                "serverName");
        ServerManager.getInstance().startError(new String[]{"1"}, SeveralClasses.class.getSimpleName());
    }

    public static void main(final String[] args) {
        try {
            SeveralClasses.start();
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Device
    public static class Class1 {

        @Init(lazyLoading = true)
        public void init() throws DevFailed {
            try {
                Thread.sleep(3000);
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            throw DevFailedUtils.newDevFailed("msg");
        }

    }

    @Device
    public static class Class2 {

    }

}
