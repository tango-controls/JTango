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
import org.tango.server.annotation.Delete;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.DynamicManagement;
import org.tango.server.annotation.Init;
import org.tango.server.attribute.ForwardedAttribute;
import org.tango.server.dynamic.DynamicManager;

import fr.esrf.Tango.DevFailed;

@Device
public class ForwardedServer {
    public static final String NO_DB_DEVICE_NAME = "2/2/2";
    public static final String INSTANCE_NAME = "1";
    public static final String SERVER_NAME = ForwardedServer.class.getSimpleName();
    // private static String fwdAttributeName = JTangoTest.NO_DB_DEVICE_NAME + "/doubleScalar";
    private static String fwdAttributeName = "";
    private static String fwdAttributeName2 = "";
    private static String fwdAttributeName3 = "";

    @DynamicManagement
    DynamicManager dynMger;

    public static void setNoDbFwdAttributeName(final String name) {
        fwdAttributeName = name;
    }

    public static void setNoDbFwdAttributeName2(final String name) {
        fwdAttributeName2 = name;
    }

    public static void setNoDbFwdAttributeName3(final String name) {
        fwdAttributeName3 = name;
    }

    @Init
    public void init() throws DevFailed {
        if (!fwdAttributeName.isEmpty()) {
            dynMger.addAttribute(new ForwardedAttribute(fwdAttributeName, "testfowarded", "testfowarded"));
        }
        if (!fwdAttributeName2.isEmpty()) {
            dynMger.addAttribute(new ForwardedAttribute(fwdAttributeName2, "testfowarded2", "testfowarded2"));
        }
        if (!fwdAttributeName3.isEmpty()) {
            dynMger.addAttribute(new ForwardedAttribute(fwdAttributeName3, "testfowarded3", "testfowarded3"));
        }
    }

    @Delete
    public void delete() throws DevFailed {
        dynMger.clearAll();
    }

    public static void startNoDb(final int portNr) throws DevFailed {
        System.setProperty("OAPort", Integer.toString(portNr));
        ServerManager.getInstance().addClass(ForwardedServer.class.getCanonicalName(), ForwardedServer.class);
        ServerManager.getInstance().startError(new String[] { INSTANCE_NAME, "-nodb", "-dlist", NO_DB_DEVICE_NAME },
                SERVER_NAME);
    }

    public static void main(final String[] args) {
       // System.setProperty("TANGO_HOST", "tango9-db1.ica.synchrotron-soleil.fr:20001");
        // ForwardedServer.setNoDbFwdAttributeName("tango9/java/events.1/doubleAtt");
        // ForwardedServer.setNoDbFwdAttributeName2("tango9/java/events.1/doubleArrayAtt");
        ServerManager.getInstance().start(new String[] { "1" }, ForwardedServer.class);
    }

    public void setDynMger(final DynamicManager dynMger) {
        this.dynMger = dynMger;
    }
}
