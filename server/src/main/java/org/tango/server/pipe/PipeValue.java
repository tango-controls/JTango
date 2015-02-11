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
package org.tango.server.pipe;

import org.tango.server.IValue;

import fr.esrf.TangoApi.PipeBlob;

public final class PipeValue implements IValue<PipeBlob> {

    private PipeBlob value;

    private long time = 0;

    public PipeValue() {
        super();
    }

    public PipeValue(final PipeBlob value) {
        super();
        this.value = value;
        this.time = System.currentTimeMillis();
    }

    public PipeValue(final PipeBlob value, final long time) {
        super();
        this.value = value;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    /**
     * Set timestamp. By default, time is set to System.currentTimeMillis() in {@link #setValue(Object)}.
     * 
     * @param time
     *            timestamp in milliseconds
     */
    @Override
    public void setTime(final long time) {
        this.time = time;
    }

    @Override
    public PipeBlob getValue() {
        return value;
    }

    @Override
    public void setValue(final PipeBlob value) {
        this.time = System.currentTimeMillis();
        this.value = value;
    }

    @Override
    public void setValue(final PipeBlob value, final long time) {
        this.value = value;
        this.time = time;
    }

}
