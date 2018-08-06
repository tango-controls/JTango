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
package org.tango.server.history;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

import org.apache.commons.lang3.ArrayUtils;
import org.omg.CORBA.BAD_OPERATION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.orb.ServerRequestInterceptor;
import org.tango.server.Constants;
import org.tango.server.ExceptionMessages;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.ClntIdent;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevSource;
import fr.esrf.Tango.LockerLanguage;

public final class DeviceBlackBox {
    private final Logger logger = LoggerFactory.getLogger(DeviceBlackBox.class);
    private final Logger clientRequestsLogger = LoggerFactory.getLogger(Constants.CLIENT_REQUESTS_LOGGER);
    private final Queue<String> blackbox = new ArrayDeque<String>(Constants.QUEUE_CAPACITY);

    public String[] toArray(final int size) throws DevFailed {
        if (blackbox.size() == 0) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.BLACK_BOX_EMPTY, "blackbox is emty");
        }
        final String[] blackboxArray = blackbox.toArray(new String[0]);
        String[] result;
        if (size < Constants.QUEUE_CAPACITY && size < blackbox.size()) {
            result = new String[size];
            System.arraycopy(blackboxArray, blackboxArray.length - size, result, 0, result.length);
        } else {
            result = blackboxArray;
        }
        ArrayUtils.reverse(result);
        return result;
    }

    public void insertInblackBox(final String message) {
        offerInblackBox(message + insertHostName());
    }

    private synchronized void offerInblackBox(final String message) {
        while (blackbox.size() >= Constants.QUEUE_CAPACITY - 1) {
            blackbox.poll();
        }
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
        final StringBuilder sb = new StringBuilder(dateFormat.format(new Date()));
        sb.append(" : ").append(message);
        clientRequestsLogger.debug(message);
        final boolean isInserted = blackbox.offer(sb.toString());
        if (!isInserted) {
            logger.debug("{} not inserted in black box queue ", sb);
        }
    }

    public void insertInblackBox(final String message, final DevSource devSource) {
        final StringBuilder sb = insertSource(message, devSource);
        offerInblackBox(sb.toString());
    }

    public void insertInblackBox(final String message, final DevSource devSource, final ClntIdent clt) {
        final StringBuilder sb = insertSource(message, devSource);
        String cli = "";
        try {
            if (clt.discriminator() == LockerLanguage.CPP) {
                cli = "(CPP/Python client with PID " + Integer.valueOf(clt.cpp_clnt()).toString() + ")";
            } else {
                cli = "(Java client with main class " + clt.java_clnt().MainClass + ")";
            }
        } catch (final BAD_OPERATION e) {
            // ignore
            logger.debug("{}", e);
        }
        sb.append(insertHostName()).append(" ").append(cli);
        offerInblackBox(sb.toString());
    }

    private StringBuilder insertHostName() {
        final StringBuilder sb = new StringBuilder();
        sb.append(" requested from ").append(ServerRequestInterceptor.getInstance().getClientHostName());
        return sb;
    }

    private StringBuilder insertSource(final String message, final DevSource devSource) {
        final StringBuilder sb = new StringBuilder(message);
        sb.append(" from ").append(devSource.toString());
        return sb;
    }

    public void insertInblackBox(final String message, final ClntIdent clt) {
        String cli = "";
        try {
            if (clt.discriminator() == LockerLanguage.CPP) {
                cli = Integer.valueOf(clt.cpp_clnt()).toString();
            } else {
                cli = clt.java_clnt().MainClass;
            }
        } catch (final BAD_OPERATION e) {
            // ignore
            logger.debug("{}", e);
        }
        offerInblackBox(message + " from  \"" + cli + "\"");
    }
}
