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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.tango.server.Constants;
import org.tango.server.IConfigurable;
import org.tango.server.properties.PropertiesUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DispLevel;
import fr.esrf.Tango.PipeWriteType;

public final class PipeConfiguration implements IConfigurable {

    private final String name;
    private String label = "";
    private String description = "";
    private DispLevel displayLevel;
    private PipeWriteType writeType;
    // private int pollingPeriod = 0;
    // private boolean isPolled = false;
    private String[] extensions = new String[0];

    public PipeConfiguration(final String name) {
        this.name = name;
    }

    public DispLevel getDisplayLevel() {
        return displayLevel;
    }

    public void setDisplayLevel(final DispLevel displayLevel) {
        this.displayLevel = displayLevel;
    }

    public PipeWriteType getWriteType() {
        return writeType;
    }

    public void setWriteType(final PipeWriteType writeType) {
        this.writeType = writeType;
    }

    public String[] getExtensions() {
        return extensions;
    }

    public void setExtensions(final String[] extensions) {
        this.extensions = extensions;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        if (!label.isEmpty()) {
            this.label = label;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        if (description.isEmpty() || description.equalsIgnoreCase(Constants.NOT_SPECIFIED)
                || description.equalsIgnoreCase(Constants.NONE)) {
            this.description = Constants.NO_DESCRIPTION;
        } else {
            this.description = description;
        }
    }

    @Override
    public void persist(final String deviceName) throws DevFailed {
        final Map<String, String[]> properties = new HashMap<String, String[]>();
        if (!getLabel().isEmpty()) {
            properties.put(Constants.LABEL, new String[] { getLabel() });
        }
        if (!getDescription().equals(Constants.NO_DESCRIPTION)) {
            properties.put(Constants.DESC, new String[] { getDescription() });
        }
        PropertiesUtils.setDevicePipePropertiesInDB(deviceName, name, properties);
    }

    @Override
    public void load(final String deviceName) throws DevFailed {
        // TODO set class name
        final String[] labels = PropertiesUtils.getDevicePipeProperty(deviceName, "", name, Constants.LABEL);
        if (labels.length == 1) {
            setLabel(labels[0]);
        }
        final String[] descs = PropertiesUtils.getDevicePipeProperty(deviceName, "", name, Constants.DESC);
        if (descs.length == 1) {
            setDescription(descs[0]);
        }
    }

    @Override
    public void clear(final String deviceName) throws DevFailed {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
        return reflectionToStringBuilder.toString();
    }

}
