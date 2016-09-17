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
package org.tango.server.command;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.tango.command.CommandTangoType;
import org.tango.server.PolledObjectConfig;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DispLevel;
import fr.esrf.TangoDs.TangoConst;

public final class CommandConfiguration implements PolledObjectConfig {

    public static final String UNINITIALISED = "Uninitialised";
    /**
     * The command name
     */
    private String name = "";
    /**
     * The command input parameter type
     */
    private Class<?> inType = Void.class;
    /**
     * The command input parameter type
     */
    private int inTangoType = TangoConst.Tango_DEV_VOID;
    /**
     * The command output parameter type
     */
    private Class<?> outType = Void.class;
    /**
     * The command input parameter type
     */
    private int outTangoType = TangoConst.Tango_DEV_VOID;
    /**
     * The command input parameter description
     */
    private String inTypeDesc = UNINITIALISED;
    /**
     * The command output parameter type
     */
    private String outTypeDesc = UNINITIALISED;
    /**
     * The command display level.
     */
    private DispLevel dispLevel = DispLevel.OPERATOR;
    private boolean isPolled = false;
    private int pollingPeriod = 0;

    public CommandConfiguration() {

    }

    public CommandConfiguration(final String name, final Class<?> inType, final Class<?> outType,
            final String inTypeDesc, final String outTypeDesc, final DispLevel dispLevel, final boolean isPolled,
            final int pollingPeriod) throws DevFailed {
        super();
        this.name = name;
        this.inType = inType;
        inTangoType = CommandTangoType.getTypeFromClass(inType).getTangoIDLType();
        this.outType = outType;
        outTangoType = CommandTangoType.getTypeFromClass(outType).getTangoIDLType();
        this.inTypeDesc = inTypeDesc;
        this.outTypeDesc = outTypeDesc;
        this.dispLevel = dispLevel;
        this.isPolled = isPolled;
        this.pollingPeriod = pollingPeriod;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Class<?> getInType() {
        return inType;
    }

    public void setInType(final Class<?> inType) throws DevFailed {
        this.inType = inType;
        inTangoType = CommandTangoType.getTypeFromClass(inType).getTangoIDLType();
    }

    public Class<?> getOutType() {
        return outType;
    }

    public void setOutType(final Class<?> outType) throws DevFailed {
        this.outType = outType;
        outTangoType = CommandTangoType.getTypeFromClass(outType).getTangoIDLType();
    }

    public String getInTypeDesc() {
        return inTypeDesc;
    }

    public void setInTypeDesc(final String inTypeDesc) {
        this.inTypeDesc = inTypeDesc;
    }

    public String getOutTypeDesc() {
        return outTypeDesc;
    }

    public void setOutTypeDesc(final String outTypeDesc) {
        this.outTypeDesc = outTypeDesc;
    }

    public DispLevel getDispLevel() {
        return dispLevel;
    }

    public void setDispLevel(final DispLevel dispLevel) {
        this.dispLevel = dispLevel;
    }

    public int getInTangoType() {
        return inTangoType;
    }

    public void setInTangoType(final int inTangoType) throws DevFailed {
        this.inTangoType = inTangoType;
        inType = CommandTangoType.getTypeFromTango(inTangoType).getCmdClass();
    }

    public int getOutTangoType() {
        return outTangoType;
    }

    public void setOutTangoType(final int outTangoType) throws DevFailed {
        this.outTangoType = outTangoType;
        outType = CommandTangoType.getTypeFromTango(outTangoType).getCmdClass();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public int getPollingPeriod() {
        return pollingPeriod;
    }

    @Override
    public void setPollingPeriod(final int pollingPeriod) {
        this.pollingPeriod = pollingPeriod;
    }

    public boolean isPolled() {
        return isPolled;
    }

    @Override
    public void setPolled(final boolean isPolled) {
        this.isPolled = isPolled;
    }
}
