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
package org.tango.server.dynamic.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.StateMachineBehavior;
import org.tango.server.command.CommandConfiguration;
import org.tango.server.command.ICommandBehavior;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;
import fr.esrf.TangoApi.CommandInfo;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoApi.Group.Group;
import fr.esrf.TangoApi.Group.GroupCmdReply;
import fr.esrf.TangoApi.Group.GroupCmdReplyList;
import fr.esrf.TangoApi.Group.GroupReply;
import fr.soleil.tango.clientapi.InsertExtractUtils;

public final class GroupCommand implements ICommandBehavior {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupCommand.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private final CommandConfiguration config = new CommandConfiguration();
    private final Group group;
    private final Map<String, String> errorReportMap = Collections.synchronizedMap(new HashMap<String, String>());
    private final String name;

    public GroupCommand(final String commandName, final Group group) throws DevFailed {
        name = commandName;
        this.group = group;
        checkCommand(group);
    }

    public GroupCommand(final String commandName, final String... deviceNames) throws DevFailed {
        name = commandName;
        // test if devices exist because add will not check it
        for (final String deviceName : deviceNames) {
            new DeviceProxy(deviceName);
        }
        group = new Group(name);
        group.add(deviceNames);
        checkCommand(group);
    }

    private void checkCommand(final Group group) throws DevFailed {
        config.setName(name + "All");
        config.setOutType(void.class);
        int found = 0;
        // Search if the command exists
        final int grpSize = group.get_size(true);
        for (int k = 0; k < grpSize; k++) {
            final DeviceProxy tmpProxy = group.get_device(k + 1);
            // The list begin from 1
            if (tmpProxy != null) {
                final CommandInfo[] tmpCommandInfoList = tmpProxy.command_list_query();
                for (final CommandInfo tmpCommandInfo : tmpCommandInfoList) {
                    if (name.equalsIgnoreCase(tmpCommandInfo.cmd_name)) {
                        found++;
                        config.setInTangoType(tmpCommandInfo.in_type);
                        break;
                    }
                }
                if (found != k + 1) {
                    throw DevFailedUtils.newDevFailed("INIT_ERROR", "Cannot create command " + name
                            + ", it does not exist on " + tmpProxy.get_name());
                }
                // else {
                // logger.debug("command found {}",
                // commandInfo.cmd_name);
                // }
            }
        }
    }

    /**
     * execute all commands and read back all errors
     */
    @Override
    public Object execute(final Object arg) throws DevFailed {
        errorReportMap.clear();
        String tmpReplyName = "";
        boolean hasFailed = false;
        final List<DevError[]> errors = new ArrayList<DevError[]>();
        int size = 0;
        final DeviceData argin = new DeviceData();
        InsertExtractUtils.insert(argin, config.getInTangoType(), arg);
        final GroupCmdReplyList tmpReplyList = group.command_inout(name, argin, true);

        for (final Object tmpReply : tmpReplyList) {
            tmpReplyName = ((GroupReply) tmpReply).dev_name();
            LOGGER.debug("getting answer for {}", tmpReplyName);
            try {
                ((GroupCmdReply) tmpReply).get_data();
            } catch (final DevFailed e) {
                LOGGER.error("command failed on {}/{} - {}",
                        new Object[] { tmpReplyName, name, DevFailedUtils.toString(e) });
                hasFailed = true;
                errors.add(e.errors);
                size = +e.errors.length;
                errorReportMap.put(tmpReplyName, dateFormat.format(new Date()) + " : " + name + " result "
                        + DevFailedUtils.toString(e));

            }
        }

        if (hasFailed) {
            final DevError[] totalErrors = new DevError[errors.size() * size + 1];
            totalErrors[0] = new DevError("CONNECTION_ERROR", ErrSeverity.ERR, "cannot execute command ", this
                    .getClass().getCanonicalName() + ".executeCommand(" + name + ")");
            int i = 1;
            for (final DevError[] devErrors : errors) {
                for (final DevError devError : devErrors) {
                    totalErrors[i++] = devError;
                }
            }
            throw new DevFailed(totalErrors);
        }
        return null;
    }

    @Override
    public CommandConfiguration getConfiguration() {
        return config;
    }

    public Map<String, String> getErrorReportMap() {
        return new HashMap<String, String>(errorReportMap);
    }

    @Override
    public StateMachineBehavior getStateMachine() {
        return null;
    }

    public boolean isArgPrimitiveType() {
        return false;
    }
}