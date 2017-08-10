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

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DispLevel;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.command.CommandTangoType;
import org.tango.server.DeviceBehaviorObject;
import org.tango.server.IPollable;
import org.tango.server.cache.PollingUtils;
import org.tango.server.properties.AttributePropertiesManager;

public class CommandImpl extends DeviceBehaviorObject implements Comparable<CommandImpl>, IPollable {

    public static final int TANGO_OPERATOR_CMD = 0;
    public static final int TANGO_EXPERT_CMD = 1;
    // private static Logger logger =
    // LoggerFactory.getLogger(CommandImpl.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(CommandImpl.class);
    private final String name;
    private final CommandConfiguration config;
    private final ICommandBehavior behavior;
    /**
     * The command input parameter type
     */
    private final CommandTangoType inType;
    /**
     * The command output parameter type
     */
    private final CommandTangoType outType;

    private final CommandHistory history;

    private final AttributePropertiesManager attributePropertiesManager;

    private volatile double executionDuration;
    private volatile double lastUpdateTime;
    private volatile double deltaTime;

    private DevFailed lastError;

    public CommandImpl(final ICommandBehavior behavior, final String deviceName) throws DevFailed {
        super();
        config = behavior.getConfiguration();
        name = config.getName();
        this.behavior = behavior;
        this.attributePropertiesManager = new AttributePropertiesManager(deviceName);
        inType = CommandTangoType.getTypeFromTango(config.getInTangoType());
        outType = CommandTangoType.getTypeFromTango(config.getOutTangoType());
        history = new CommandHistory(config.getOutTangoType());
    }

    /**
     * Execute the command.
     */
    public Object execute(final Object dataIn) throws DevFailed {
        xlogger.entry(name);
        Object result;
        try {
            result = behavior.execute(dataIn);
        } catch (final DevFailed e) {
            lastError = e;
            throw e;
        }
        xlogger.exit(name);
        return result;
    }

    /**
     * Return the command name.
     *
     * @return The command name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Return the input parameter type.
     *
     * @return The input parameter type
     */

    public CommandTangoType getInType() {
        return inType;
    }

    public int getInTangoType() {
        return config.getInTangoType();
    }

    public boolean isArginPrimitive() {
        return config.getInType().isPrimitive();
    }

    /**
     * Return the output parameter type.
     *
     * @return The output parameter type
     */

    public CommandTangoType getOutType() {
        return outType;
    }

    public int getOutTangoType() {
        return config.getOutTangoType();
    }

    /**
     * Return the input parameter description.
     *
     * @return The input parameter description
     */

    public String getInTypeDesc() {
        return config.getInTypeDesc();
    }

    /**
     * Return the output parameter description.
     *
     * @return The output parameter description
     */

    public String getOutTypeDesc() {
        return config.getOutTypeDesc();
    }

    public int getTag() {
        if (config.getDispLevel().equals(DispLevel.OPERATOR)) {
            return TANGO_OPERATOR_CMD;
        } else {
            return TANGO_EXPERT_CMD;
        }
    }

    public DispLevel getDisplayLevel() {
        return config.getDispLevel();
    }

    public void setDisplayLevel(final DispLevel level) {
        config.setDispLevel(level);
    }

    public void addToHistory(final Object value) throws DevFailed {
        history.addToHistory(value, new DevError[0]);
    }

    public void addErrorToHistory(final DevFailed e) throws DevFailed {
        history.addToHistory(null, e.errors);
    }

    public CommandHistory getHistory() {
        return history;
    }

    @Override
    public String toString() {
        final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(this,
                ToStringStyle.MULTI_LINE_STYLE);
        reflectionToStringBuilder.setExcludeFieldNames(new String[] { "inType", "outType", "history" });
        return reflectionToStringBuilder.toString();
    }

    public ICommandBehavior getBehavior() {
        return behavior;
    }

    @Override
    public int getPollingPeriod() {
        return config.getPollingPeriod();
    }

    @Override
    public boolean isPolled() {
        return config.isPolled();
    }

    @Override
    public void configurePolling(final int pollingPeriod) throws DevFailed {
        PollingUtils.configurePolling(pollingPeriod, config, attributePropertiesManager);
        history.clear();
    }

    @Override
    public void resetPolling() throws DevFailed {
        PollingUtils.resetPolling(config, attributePropertiesManager);
    }

    public void updatePollingConfigFromDB() throws DevFailed {
        PollingUtils.updatePollingConfigFromDB(config, attributePropertiesManager);
    }

    @Override
    public int compareTo(final CommandImpl o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public int getPollRingDepth() {
        return history.getMaxSize();
    }

    @Override
    public void setPollRingDepth(final int pollRingDepth) {
        history.setMaxSize(pollRingDepth);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CommandImpl other = (CommandImpl) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public double getExecutionDuration() {
        return executionDuration;
    }

    @Override
    public double getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public double getDeltaTime() {
        return deltaTime;
    }

    @Override
    public void setPollingStats(final double executionDuration, final double lastUpdateTime, final double deltaTime) {
        this.executionDuration = executionDuration;
        this.lastUpdateTime = lastUpdateTime;
        this.deltaTime = deltaTime;
    }

    @Override
    public String getLastDevFailed() {
        return PollingUtils.toString(lastError);
    }

}
