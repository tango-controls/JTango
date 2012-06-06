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
package org.tango.server.device;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.tango.server.annotation.TransactionType;

/**
 * Manage a tango device synchronization.
 * 
 * @see TransactionType
 * @author ABEILLE
 * 
 */
public final class DeviceLock {
    /**
     * Lock for attributes
     */
    private final Lock attributeLock;
    /**
     * Lock for commands
     */
    private final Lock commandLock;
    /**
     * Lock for server
     */
    private static final Lock SERVER_LOCK = new ReentrantLock();
    /**
     * Lock for classes
     */
    private static final Map<Class<?>, Lock> CLASS_LOCKS = new HashMap<Class<?>, Lock>();

    /**
     * Ctr
     * 
     * @param txType
     *            lock type
     * @param deviceClass
     *            the class to lock
     */
    public DeviceLock(final TransactionType txType, final Class<?> deviceClass) {

	switch (txType) {
	case DEVICE:
	    attributeLock = new ReentrantLock();
	    commandLock = attributeLock;
	    break;
	case ATTRIBUTE:
	    attributeLock = new ReentrantLock();
	    commandLock = null;
	    break;
	case COMMAND:
	    commandLock = new ReentrantLock();
	    attributeLock = null;
	    break;
	case CLASS:
	    if (CLASS_LOCKS.containsKey(deviceClass)) {
		commandLock = CLASS_LOCKS.get(deviceClass);
		attributeLock = commandLock;
	    } else {
		commandLock = new ReentrantLock();
		attributeLock = commandLock;
		CLASS_LOCKS.put(deviceClass, commandLock);
	    }
	    break;
	case SERVER:
	    attributeLock = SERVER_LOCK;
	    commandLock = SERVER_LOCK;
	    break;
	case ATTRIBUTE_COMMAND:
	    attributeLock = new ReentrantLock();
	    commandLock = new ReentrantLock();
	    break;
	case NONE:
	default:
	    attributeLock = null;
	    commandLock = null;
	    break;
	}
    }

    /**
     * obtain lock for attribute
     */
    public void lockAttribute() {
	if (attributeLock != null) {
	    attributeLock.lock();
	}
    }

    /**
     * Release lock for attribute
     */
    public void unlockAttribute() {
	if (attributeLock != null) {
	    attributeLock.unlock();
	}
    }

    /**
     * Obtain lock for command
     */
    public void lockCommand() {
	if (commandLock != null) {
	    commandLock.lock();
	}
    }

    /**
     * release lock for command
     */
    public void unlockCommand() {
	if (commandLock != null) {
	    commandLock.unlock();
	}
    }

}
