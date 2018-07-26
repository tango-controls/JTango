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
package org.tango.server.servant;

import java.util.Locale;
import java.util.StringTokenizer;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongAdapter;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.client.database.DeviceExportInfo;
import org.tango.orb.IORDump;
import org.tango.orb.ORBManager;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.Device;
import fr.esrf.Tango.Device_5;

public final class ORBUtils {

    // private static Logger logger = LoggerFactory.getLogger(ORBUtils.class);
    private static final XLogger XLOGGER = XLoggerFactory.getXLogger(ORBUtils.class);

    private ORBUtils() {

    }

    public static void exportDevice(final DeviceImpl dev, final String hostName, final String pid) throws DevFailed {
        if (DatabaseFactory.isUseDb()) {
            exportDeviceWithDatabase(dev, hostName, pid);
        } else {
            exportDeviceWithoutDatabase(dev);
        }
    }

    /**
     * description : This method exports a device to the outside world. This is done by sending its CORBA network
     * parameter (mainly the IOR) to the Tango database
     * 
     * @param dev
     * @throws DevFailed
     */
    private static void exportDeviceWithDatabase(final DeviceImpl dev, final String hostName, final String pid)
            throws DevFailed {
        XLOGGER.entry(dev.getName());

        final ORB orb = ORBManager.getOrb();
        // Activate the CORBA object incarnated by the Java object
        final Device_5 d = dev._this(orb);

        // Get the object id and store it

        final POA poa = ORBManager.getPoa();

        try {
            dev.setObjId(poa.reference_to_id(d));
        } catch (final WrongAdapter e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final WrongPolicy e) {
            throw DevFailedUtils.newDevFailed(e);
        }

        final DeviceExportInfo info = new DeviceExportInfo(dev.getName(), orb.object_to_string(d), hostName,
                Integer.toString(DeviceImpl.SERVER_VERSION), pid, dev.getClassName());
        DatabaseFactory.getDatabase().exportDevice(info);

        XLOGGER.exit();
    }

    private static void exportDeviceWithoutDatabase(final DeviceImpl dev) throws DevFailed {
        XLOGGER.entry(dev.getName());

        // For server without db usage.
        // it is necessary to create our own CORBA object id and to bind it into
        // the OOC Boot Manager for access through a stringified object
        // reference
        // constructed using the corbaloc style
        final String lowerCaseDevice = dev.getName().toLowerCase(Locale.ENGLISH);
        final byte[] oid = lowerCaseDevice.getBytes();
        final POA poa = ORBManager.getPoa();
        try {
            poa.activate_object_with_id(oid, dev);
        } catch (final ServantAlreadyActive e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final ObjectAlreadyActive e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final WrongPolicy e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        // Get the object id and store it
        dev._this(ORBManager.getOrb());
        dev.setObjId(oid);

        registerDeviceForJacorb(lowerCaseDevice);

        XLOGGER.exit();
    }

    public static IORDump getIORDump(final DeviceImpl dev) throws DevFailed {
        final ORB orb = ORBManager.getOrb();
        final Device d = dev._this(orb);
        return new IORDump(dev.getName(), orb.object_to_string(d));
    }

    public static void unexportDevice(final DeviceImpl device) throws DevFailed {
        XLOGGER.entry(device.getName());
        device.deleteDevice();
        final POA poa = ORBManager.getPoa();
        try {
            poa.deactivate_object(device.getObjId());
        } catch (final ObjectNotActive e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final WrongPolicy e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        XLOGGER.exit();
    }

    /**
     * WARNING: The following code is JacORB specific. Add device name in HashTable used for JacORB objectKeyMap if
     * _UseDb==false.
     * 
     * @param name
     *            The device's name.
     * @throws DevFailed
     */
    private static void registerDeviceForJacorb(final String name) throws DevFailed {
        // Get the 3 fields of device name
        final StringTokenizer st = new StringTokenizer(name, "/");
        final String[] field = new String[3];
        for (int i = 0; i < 3 && st.countTokens() > 0; i++) {
            field[i] = st.nextToken();
        }

        // After a header used by JacORB, in the device name
        // the '/' char must be replaced by another separator
        final String separator = "&%25";
        final String targetname = ORBManager.SERVER_IMPL_NAME + "/" + ORBManager.NODB_POA + "/" + field[0] + separator
                + field[1] + separator + field[2];
        // And set the JacORB objectKeyMap HashMap
        final org.jacorb.orb.ORB jacorb = (org.jacorb.orb.ORB) ORBManager.getOrb();
        jacorb.addObjectKey(name, targetname);
    }
}
