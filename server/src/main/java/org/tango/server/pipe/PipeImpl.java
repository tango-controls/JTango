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

import org.tango.server.DeviceBehaviorObject;
import org.tango.server.IReadableWritable;

import fr.esrf.Tango.DevFailed;

public final class PipeImpl extends DeviceBehaviorObject implements Comparable<PipeImpl>, IReadableWritable<PipeValue> {

    private final IPipeBehavior behavior;
    private final String name;
    private final PipeConfiguration config;
    private PipeValue readValue;
    private PipeValue writeValue;
    private final String deviceName;

    public PipeImpl(final IPipeBehavior behavior, final String deviceName) {
        this.behavior = behavior;
        this.deviceName = deviceName;
        this.config = behavior.getConfiguration();
        this.name = behavior.getConfiguration().getName();
    }

    @Override
    public void updateValue() throws DevFailed {
        updateValue(behavior.getValue());
    }

    @Override
    public void updateValue(final PipeValue inValue) throws DevFailed {
        readValue = inValue;
    }

    @Override
    public void setValue(final PipeValue value) throws DevFailed {
        writeValue = value;
        behavior.setValue(value);
    }

    public PipeConfiguration getConfiguration() {
        return config;
    }

    public void loadConfiguration() throws DevFailed {
        config.load(deviceName);
    }

    public void setConfiguration(final String label, final String description) throws DevFailed {
        config.setLabel(label);
        config.setDescription(description);
        config.persist(deviceName);
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(final PipeImpl o) {
        return getConfiguration().getName().compareTo(o.behavior.getConfiguration().getName());
    }

    @Override
    public PipeValue getWriteValue() {
        return writeValue;
    }

    @Override
    public PipeValue getReadValue() {
        return readValue;
    }
}
