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
package org.tango.server.monitoring;

import fr.esrf.Tango.ClntIdent;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevSource;
import org.tango.server.history.DeviceBlackBox;

import java.io.Closeable;

public class DeviceMonitoring {
    private static final String SEPARATOR = " - ";
    private final String deviceName;
    private final DeviceBlackBox blackbox;
    private final TangoStats monitoring;

    public DeviceMonitoring(final String deviceName) {
        this.deviceName = deviceName;
        blackbox = new DeviceBlackBox();
        monitoring = TangoStats.getInstance();
    }

    public Request startRequest(final String request) {
        blackbox.insertInblackBox(request);
        return new Request(deviceName + SEPARATOR + request);
    }

    public Request startRequest(final String request, final ClntIdent clt) {
        blackbox.insertInblackBox(request, clt);
        return new Request(deviceName + SEPARATOR + request);
    }

    public Request startRequest(final String request, final DevSource devSource) {
        blackbox.insertInblackBox(request, devSource);
        return new Request(deviceName + SEPARATOR + request);
    }

    public Request startRequest(final String request, final DevSource devSource, final ClntIdent clt) {
        blackbox.insertInblackBox(request, devSource, clt);
        return new Request(deviceName + SEPARATOR + request);
    }

    public void addError() {
        monitoring.addError();
    }

    public void endRequest(final long id) {
        monitoring.endRequest(id);
    }

    public String[] getBlackBox(final int size) throws DevFailed {
        return blackbox.toArray(size);
    }

    public class Request implements Closeable {
        public final long id;

        Request(String request) {
            this.id = monitoring.addRequest(request);
        }

        @Override
        public void close() {
            monitoring.endRequest(id);
        }
    }

}
