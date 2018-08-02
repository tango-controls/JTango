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
package org.tango.server.lock;

import fr.esrf.Tango.ClntIdent;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.Tango.LockerLanguage;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.orb.ServerRequestInterceptor;
import org.tango.server.Chronometer;
import org.tango.server.ExceptionMessages;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.ClientIDUtil;
import org.tango.utils.DevFailedUtils;

import java.util.Arrays;

/**
 * Manage device locking with client request
 *
 * @author ABEILLE
 *
 */
public final class ClientLocking {

    private static final int _6 = 6;

    private static final int _3 = 3;

    private final Logger logger = LoggerFactory.getLogger(ClientLocking.class);

    private final String deviceName;
    /**
     * Used for locking device from client
     */
    private final Chronometer clientLockChrono = new Chronometer();
    private ClntIdent clientIdentityLock = new ClntIdent();
    private ClntIdent previousLocker = new ClntIdent();
    private String lockerHost = "";
    private int lockDuration;
    private boolean hasBeenForced = false;
    private String[] allowedCommands = new String[0];
    /**
     * Use for the tango concept of "Re-entrant lock". Unlock must be called several times if locked several times!
     */
    private int lockingCounter;

    public ClientLocking(final String deviceName, final String className) {
        this.deviceName = deviceName;
        lockingCounter = 0;
        hasBeenForced = false;
        // get command that cannot be locked
        try {
            final String access = DatabaseFactory.getDatabase().getAccessDeviceName();
            if (!access.isEmpty()) {
                final DeviceData in = new DeviceData();
                in.insert(className);
                allowedCommands = new DeviceProxy(access).command_inout("GetAllowedCommands", in).extractStringArray();
            }
        } catch (final DevFailed e) {
            logger.error("failed to retrieve tango access control - {}", DevFailedUtils.toString(e));
        }
    }

    public void init() {
        lockingCounter = 0;
    }

    public void relock() throws DevFailed {
        unLock(false);
        lock(lockDuration, clientIdentityLock, lockerHost);
    }

    public void lock(final int validity, final ClntIdent clientIdentity, final String hostName) throws DevFailed {
        logger.debug("locking for client {}- {}", hostName, ClientIDUtil.toString(clientIdentity));
        clientIdentityLock = clientIdentity;
        lockerHost = hostName;
        lockDuration = validity;
        clientLockChrono.start(validity * 1000L);
        lockingCounter++;
    }

    public void unLock(final boolean isForced) {
        if (isForced) {
            lockingCounter = 0;
            hasBeenForced = true;
            previousLocker = clientIdentityLock;
        } else {
            hasBeenForced = false;
            if (lockingCounter > 0) {
                lockingCounter--;
            }
        }

        if (lockingCounter == 0) {
            clientLockChrono.stop();
        }
    }

    public boolean isOver() {
        final boolean isOver = clientLockChrono.isOver();
        if (lockingCounter > 0 && isOver) {
            lockingCounter--;
        }
        return isOver;

    }

    /**
     *
     * @param clIdent
     * @param names
     * @throws DevFailed
     */
    public void checkClientLocking(final ClntIdent clIdent, final String... names) throws DevFailed {
        logger.debug("check for client {} - {}", ServerRequestInterceptor.getInstance().getGiopHostAddress(),
                ClientIDUtil.toString(clIdent));
        boolean doCheck = true;
        for (final String name : names) {
            if (ArrayUtils.contains(allowedCommands, name)) {
                doCheck = false;
                break;
            }
        }
        if (doCheck && !ArrayUtils.contains(names, DeviceImpl.STATE_NAME)
                && !ArrayUtils.contains(names, DeviceImpl.STATUS_NAME)) {
            // not locked for state and status
            if (!clientLockChrono.isOver()
                    && !lockerHost.equalsIgnoreCase(ServerRequestInterceptor.getInstance().getGiopHostAddress())
                    && !ClientIDUtil.clientIdentEqual(clIdent, clientIdentityLock)) {
                // device is locked
                throw DevFailedUtils.newDevFailed(ExceptionMessages.DEVICE_LOCKED, "device is locked by " + lockerHost
                        + "- " + ClientIDUtil.toString(clIdent));
            } else if (hasBeenForced && ClientIDUtil.clientIdentEqual(clIdent, previousLocker)) {
                // if was unlock by client
                hasBeenForced = false;
                // lockingCounter = 0;
                throw DevFailedUtils.newDevFailed(ExceptionMessages.DEVICE_UNLOCKED, "device unlock was forced");
            }
        }
    }

    public DevVarLongStringArray getLockStatus() {
        // The strings contain:
        // 1 - The locker process hostname(like giop:tcp:[::ffff:172.28.1.114]:1897)
        // 2 - The java main class (in case of Java locker)
        // 0 - A string which summarizes the locking status
        // The longs contain:
        // 1 - A locked flag (0 means not locked, 1 means locked)
        // 2 - The locker process PID (C++ client)
        // 3 - The locker UUID (Java client) which needs 4 longs
        final String[] strings = new String[_3];
        final int[] longs = new int[_6];

        if (!clientLockChrono.isOver()) {
            strings[1] = lockerHost;
            longs[0] = 1;
            strings[0] = "Device " + deviceName + " is locked by " + lockerHost;

            if (clientIdentityLock.discriminator().equals(LockerLanguage.CPP)) {
                strings[2] = "Not defined";
                longs[1] = clientIdentityLock.cpp_clnt();
                for (int i = 2; i < longs.length; i++) {
                    longs[i] = 0;
                }
            } else {
                strings[2] = clientIdentityLock.java_clnt().MainClass;
                longs[1] = 0;
                for (int i = 0; i < clientIdentityLock.java_clnt().uuid.length; i++) {
                    longs[i + 2] = (int) clientIdentityLock.java_clnt().uuid[i];
                }
            }
        } else {
            // strings[0] = "Not defined";
            strings[0] = "Device " + deviceName + " is not locked";
            strings[1] = "Not defined";
            strings[2] = "Not defined";

            Arrays.fill(longs, 0);
        }
        return new DevVarLongStringArray(longs, strings);
    }

    public boolean isHasBeenForced() {
        return hasBeenForced;
    }

}
