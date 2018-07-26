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
package org.tango.orb;

import fr.esrf.Tango.*;
import org.omg.CORBA.*;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.client.database.DeviceImportInfo;
import org.tango.utils.DevFailedUtils;

import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Initialize, shutdown the ORB
 *
 * @author ABEILLE
 *
 */
public final class ORBManager {

    private static final String INIT_ERROR = "INIT_ERROR";
    private static final XLogger XLOGGER = XLoggerFactory.getXLogger(ORBManager.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(ORBManager.class);
    /**
     * POA for no db device
     */
    public static final String NODB_POA = "nodb_poa";
    /**
     * impl name for nodb device
     */
    public static final String SERVER_IMPL_NAME = "nodb.device";
    /**
     * A jacorb system property for IP address on multi-homed host
     */
    public static final String OAI_ADDR = System.getProperty("OAIAddr");
    private static ORB orb;
    private static POA poa;
    private static ExecutorService orbStart;

    private ORBManager() {
    }

    /**
     * Initialise the ORB
     *
     * @param useDb
     *            is using tango db
     * @param adminDeviceName
     *            admin device name
     * @throws DevFailed
     */
    public static synchronized void init(final boolean useDb, final String adminDeviceName) throws DevFailed {
        // Modified properties fo ORB usage.
        final Properties props = System.getProperties();
        props.put("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
        props.put("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton");
        // register interceptors
        props.put("org.omg.PortableInterceptor.ORBInitializerClass.ForwardInit",
                InterceptorInitializer.class.getCanonicalName());
        // Set retry properties
        props.put("jacorb.retries", "0");
        props.put("jacorb.retry_interval", "100");

        props.put("jacorb.codeset", true);
        // props.put("jacorb.config.dir", "fr/esrf/TangoApi");

        // Initial timeout for establishing a connection.
        props.put("jacorb.connection.client.connect_timeout", "5000");

        // Set the Largest transfert.
        final String str = checkORBgiopMaxMsgSize();
        props.put("jacorb.maxManagedBufSize", str);

        // Set jacorb verbosity at minimum value
        props.put("jacorb.config.log.verbosity", "0");

        // only used for no db device
        props.setProperty("jacorb.implname", SERVER_IMPL_NAME);
        // System.setProperties(props);
        // props.setProperty("jacorb.net.tcp_listener",
        // ConnectionListener.class.getName());

        // Initialize ORB
        orb = ORB.init(new String[] {}, props);

        try {
            poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            // boot_manager =
            // BootManagerHelper.narrow(orb.resolve_initial_references("BootManager"));
        } catch (final InvalidName e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final INITIALIZE e) {
            // ignore, occurs when starting several times a server that failed
            if (!useDb) {
                throw DevFailedUtils.newDevFailed(e);
            }
        }

        try {
            if (!useDb) {
                // If the database is not used, create a POA with the
                // USER_ID policy
                final org.omg.CORBA.Policy[] policies = new org.omg.CORBA.Policy[2];
                policies[0] = poa.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID);
                policies[1] = poa.create_lifespan_policy(LifespanPolicyValue.PERSISTENT);
                final org.omg.PortableServer.POAManager manager = poa.the_POAManager();
                poa = poa.create_POA(NODB_POA, manager, policies);
            }
        } catch (final org.omg.PortableServer.POAPackage.AdapterAlreadyExists e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final org.omg.PortableServer.POAPackage.InvalidPolicy e) {
            throw DevFailedUtils.newDevFailed(e);
        }

        final POAManager manager = poa.the_POAManager();
        try {
            manager.activate();
        } catch (final org.omg.PortableServer.POAManagerPackage.AdapterInactive ex) {
            throw DevFailedUtils.newDevFailed("API_CantActivatePOAManager", "The POA activate method throws an exception");
        }

        if (useDb) {

            // Build device name and try to import it from database
            final DeviceImportInfo importInfo = DatabaseFactory.getDatabase().importDevice(adminDeviceName);
            if (importInfo.isExported()) {
                LOGGER.debug("{} is set as exported in tango db - checking if it is already running", adminDeviceName);
                // if is exported, try to connect to it
                ORBManager.checkServerRunning(importInfo, adminDeviceName);
            }
        }
    }

    /**
     * Create an {@link Any}
     *
     * @return a Any
     * @throws DevFailed
     */
    public static Any createAny() throws DevFailed {
        return orb.create_any();
    }

    /**
     * Check the server is already running
     *
     * @param importInfo
     * @param toBeImported
     * @throws DevFailed
     */
    private static void checkServerRunning(final DeviceImportInfo importInfo, final String toBeImported)
            throws DevFailed {
        XLOGGER.entry();
        Device_5 devIDL5 = null;
        Device_4 devIDL4 = null;
        Device_3 devIDL3 = null;
        Device_2 devIDL2 = null;
        Device devIDL1 = null;
        try {
            // try IDL5
            try {
                devIDL5 = narrowIDL5(importInfo);
            } catch (final BAD_PARAM e) {
                // try IDL4
                try {
                    devIDL4 = narrowIDL4(importInfo);
                } catch (final BAD_PARAM e4) {
                    // maybe another IDL is currently running
                    // try IDL3
                    try {
                        devIDL3 = narrowIDL3(importInfo);
                    } catch (final BAD_PARAM e1) {
                        // maybe another IDL is currently running
                        // try IDL2
                        try {
                            devIDL2 = narrowIDL2(importInfo);
                        } catch (final BAD_PARAM e2) {
                            // maybe another IDL is currently running
                            // try IDL1
                            try {
                                devIDL1 = narrowIDL1(importInfo);
                            } catch (final BAD_PARAM e3) {
                                // may not occur, unknown CORBA server
                                throw DevFailedUtils.newDevFailed(e);
                            }
                        }
                    }
                }
            }
            if (devIDL5 == null && devIDL4 == null && devIDL3 == null && devIDL2 == null && devIDL1 == null) {
                LOGGER.debug("out, device is not running");
            } else {
                checkDeviceName(toBeImported, devIDL5, devIDL4, devIDL3, devIDL2, devIDL1);
            }
        } catch (final org.omg.CORBA.TIMEOUT e) {
            // Receive a Timeout exception ---> It is not running !!!!
            LOGGER.debug("out on TIMEOUT");
        } catch (final BAD_OPERATION e) {
            // System.err.println("Can't pack/unpack data sent to/from database in/to Any object");
            throw DevFailedUtils.newDevFailed(e);
        } catch (final TRANSIENT e) {
            LOGGER.debug("out on TRANSIENT, device is not running");
        } catch (final OBJECT_NOT_EXIST e) {
            LOGGER.debug("out on OBJECT_NOT_EXIST, device is not running");
        } catch (final COMM_FAILURE e) {
            LOGGER.debug("out on COMM_FAILURE,, device is not running");
        } catch (final BAD_INV_ORDER e) {
            LOGGER.debug("out on BAD_INV_ORDER,, device is not running");
        }

        XLOGGER.exit();
    }

    private static void checkDeviceName(final String toBeImported, final Device_5 devIDL5, final Device_4 devIDL4,
            final Device_3 devIDL3, final Device_2 devIDL2, final Device devIDL1) throws DevFailed {
        // get the device name from the server
        try {
            if (devIDL5 != null) {
                checkDev(toBeImported, devIDL5.name(), "5");
            } else if (devIDL4 != null) {
                checkDev(toBeImported, devIDL4.name(), "4");
            } else if (devIDL3 != null) {
                checkDev(toBeImported, devIDL3.name(), "3");
            } else if (devIDL2 != null) {
                checkDev(toBeImported, devIDL2.name(), "2");
            } else if (devIDL1 != null) {
                checkDev(toBeImported, devIDL1.name(), "1");
            }

        } catch (final NO_RESPONSE e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final COMM_FAILURE e) {
            LOGGER.debug("out on COMM_FAILURE, device is not running");
        } catch (final OBJECT_NOT_EXIST e) {
            LOGGER.debug("out on OBJECT_NOT_EXIST, device is not running");
        } catch (final TRANSIENT e) {
            LOGGER.debug("out on TRANSIENT, device is not running");
        } catch (final BAD_INV_ORDER e) {
            LOGGER.debug("out on BAD_INV_ORDER, device is not running");
        }
    }

    private static void checkDev(final String toBeImported, final String deviceName, final String version)
            throws DevFailed {
        if (deviceName.equals(toBeImported)) {
            throw DevFailedUtils.newDevFailed(INIT_ERROR, "This server is already running in IDL" + version + ", exiting!");
        }
    }

    private static Device_5 narrowIDL5(final DeviceImportInfo importInfo) throws DevFailed {
        Device_5 dev = null;
        final org.omg.CORBA.Object obj = orb.string_to_object(importInfo.getIor());
        LOGGER.debug("try narrow {} as IDL5 with PID {} because it is exported ", importInfo.getName(),
                importInfo.getPid());
        dev = Device_5Helper.narrow(obj);
        LOGGER.debug("narrow IDL5 done");
        return dev;
    }

    private static Device_4 narrowIDL4(final DeviceImportInfo importInfo) throws DevFailed {
        Device_4 dev = null;
        final org.omg.CORBA.Object obj = orb.string_to_object(importInfo.getIor());
        LOGGER.debug("try narrow {} as IDL4 with PID {} because it is exported ", importInfo.getName(),
                importInfo.getPid());
        dev = Device_4Helper.narrow(obj);
        LOGGER.debug("narrow IDL4 done");
        return dev;
    }

    private static Device_3 narrowIDL3(final DeviceImportInfo importInfo) throws DevFailed {
        Device_3 dev = null;
        final org.omg.CORBA.Object obj = orb.string_to_object(importInfo.getIor());
        LOGGER.debug("try narrow {} as IDL3 with PID {}", importInfo.getName(), importInfo.getPid());
        dev = Device_3Helper.narrow(obj);
        LOGGER.debug("narrow IDL3 done");
        return dev;
    }

    private static Device_2 narrowIDL2(final DeviceImportInfo importInfo) throws DevFailed {
        Device_2 dev = null;
        final org.omg.CORBA.Object obj = orb.string_to_object(importInfo.getIor());
        LOGGER.debug("try narrow {} as IDL2 with PID {}", importInfo.getName(), importInfo.getPid());
        dev = Device_2Helper.narrow(obj);
        LOGGER.debug("narrow IDL2 done");
        return dev;
    }

    private static Device narrowIDL1(final DeviceImportInfo importInfo) throws DevFailed {
        Device dev = null;
        final org.omg.CORBA.Object obj = orb.string_to_object(importInfo.getIor());
        LOGGER.debug("try narrow {} as IDL1 with PID {}", importInfo.getName(), importInfo.getPid());
        dev = DeviceHelper.narrow(obj);
        LOGGER.debug("narrow IDL1 done");
        return dev;
    }

    /**
     * Start the ORB. Blocks until stopped.
     */
    private static void start() {
        if (orb != null) {
            orb.run();
        }
    }

    /**
     * Start the ORB. non blocking.
     */
    public static void startDetached() {
        orbStart = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(final Runnable r) {
                return new Thread(r, "ORB run");
            }
        });
        orbStart.submit(new StartTask());
        LOGGER.debug("ORB started");
    }

    private static class StartTask implements Callable<Void> {
        @Override
        public Void call() {
            ORBManager.start();
            return null;
        }
    }

    /**
     * Check if the checkORBgiopMaxMsgSize has been set. This environment
     * variable should be set in Mega bytes.
     */
    private static String checkORBgiopMaxMsgSize() {
        /*
         * JacORB definition (see jacorb.properties file):
         *
         * This is NOT the maximum buffer size that can be used, but just the
         * largest size of buffers that will be kept and managed. This value
         * will be added to an internal constant of 5, so the real value in
         * bytes is 2**(5+maxManagedBufSize-1). You only need to increase this
         * value if you are dealing with LOTS of LARGE data structures. You may
         * decrease it to make the buffer manager release large buffers
         * immediately rather than keeping them for later reuse.
         */
        String str = "20"; // Set to 16 Mbytes

        // Check if environment ask for bigger size.
        final String tmp = System.getProperty("ORBgiopMaxMsgSize");
        if (tmp != null && checkBufferSize(tmp) != null) {
            str = tmp;
        }
        return str;
    }

    private static String checkBufferSize(final String str) {
        String result = null;
        // try to get value
        int nbMega = 0;
        try {
            nbMega = Integer.parseInt(str);
        } catch (final NumberFormatException e) {
        }

        // Compute the real size and the power of 2
        final long size = (long) nbMega * 1024 * 1024;
        long l = size;
        int cnt;
        for (cnt = 0; l > 0; cnt++) {
            l >>= 1;
        }
        cnt--;

        // Check if number ob Mb is not power of 2
        if (Math.pow(2, cnt) < size) {
            cnt++;
        }
        // System.out.println(nb_mega + " Mbytes  (2^" + cnt + ")");

        final int jacorbSize = cnt - 4;
        result = Integer.toString(jacorbSize);
        return result;
    }

    /**
     * Get the ORB
     *
     * @return the ORB
     * @throws DevFailed
     */
    public static ORB getOrb() throws DevFailed {
        if (orb == null) {
            throw DevFailedUtils.newDevFailed("ORB not initialized");
        }
        return orb;
    }

    /**
     * Get the POA
     *
     * @return the POA
     * @throws DevFailed
     */
    public static POA getPoa() throws DevFailed {
        if (poa == null) {
            throw DevFailedUtils.newDevFailed("ORB not initialized");
        }
        return poa;
    }

    /**
     * Shutdown the ORB
     */
    public static void shutdown() {
        if (orbStart != null) {
            orbStart.shutdown();
        }
        if (orb != null) {
            orb.shutdown(true);
            LOGGER.debug("ORB shutdown");
        }
    }

}
