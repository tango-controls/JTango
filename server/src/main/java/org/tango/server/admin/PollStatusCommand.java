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
package org.tango.server.admin;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import fr.esrf.Tango.DevFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.ExceptionMessages;
import org.tango.server.IPollable;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.build.DeviceClassBuilder;
import org.tango.server.command.CommandImpl;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;
import org.tango.utils.TangoUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 8/2/17
 */
public class PollStatusCommand implements Callable<String[]> {
    private final Logger logger = LoggerFactory.getLogger(PollStatusCommand.class);

    private final String deviceName;
    private final List<DeviceClassBuilder> classList;

    PollStatusCommand(String deviceName, List<DeviceClassBuilder> classList) {
        this.deviceName = deviceName;
        this.classList = classList;
    }

    /**
     * @return an array of strings with polling info
     * @throws DevFailed in case device cannot be found
     */
    @Override
    public String[] call() throws DevFailed {
        final List<String> result = new ArrayList<String>();

        final DeviceImpl device = tryFindDeviceByName(deviceName);

        result.addAll(getPolledStatuses(device, device.getCommandList()));

        result.addAll(getPolledStatuses(device, device.getAttributeList()));

        return result.toArray(new String[result.size()]);
    }

    private Collection<String> getPolledStatuses(final DeviceImpl device, List<? extends IPollable> pollableList) {
        Collection<? extends IPollable> polledCommands = Collections2.filter(pollableList, new Predicate<IPollable>() {
            @Override
            public boolean apply(IPollable pollable) {
                return pollable.isPolled();
            }
        });
        return Collections2.transform(polledCommands, new Function<IPollable, String>() {
                    @Override
                    public String apply(IPollable pollable) {
                        return buildPollingStatus(device, pollable);
                    }
                });
    }

    private DeviceImpl tryFindDeviceByName(final String deviceName) throws DevFailed {
        List<DeviceImpl> allDevices = Lists.newLinkedList(Iterables.concat(Iterables.transform(classList, new Function<DeviceClassBuilder, List<DeviceImpl>>() {
            @Override
            public List<DeviceImpl> apply(DeviceClassBuilder input) {
                return input.getDeviceImplList();
            }
        })));

        Optional<DeviceImpl> device = Iterables.tryFind(allDevices, new Predicate<DeviceImpl>() {
            @Override
            public boolean apply(DeviceImpl input) {
                return deviceName.equalsIgnoreCase(input.getName());
            }
        });
        if (!device.isPresent()) {
            //try to find device by alias
            device = Iterables.tryFind(allDevices, new Predicate<DeviceImpl>() {
                @Override
                public boolean apply(DeviceImpl input) {
                    try {
                        //returns alias or deviceName
                        return TangoUtil.getfullNameForDevice(deviceName).equalsIgnoreCase(input.getName());
                    } catch (DevFailed devFailed) {
                        logger.warn("Failed to get full name for device {}", deviceName);
                        DevFailedUtils.logDevFailed(devFailed, logger);
                        return false;
                    }
                }
            });
        }
        if (!device.isPresent()) {
            DevFailedUtils.throwDevFailed(ExceptionMessages.DEVICE_NOT_FOUND, deviceName + AdminDevice.DOES_NOT_EXIST);
        }
        return device.get();
    }

    private static final String POLL_STATUS_OUTPUT_TEMPLATE =
            "Polled %s name = %s\n" +
            "%s\n" + //Polling period (mS) = %d or external triggering
            "Polling ring buffer depth = %d\n" +
            "%s\n" +   //No data recorded yet
            "%s"; //Last attribute read failed or status
    private static final String LAST_RECORDED_DATA_STATUS_TEMPLATE =
            "Time needed for the last attribute reading (mS) = %.1f\n" +
            "Data not updated since %d mS\n" +
            "Delta between last records (in mS) = %.1f";


    //TODO extract to a class
    private String buildPollingStatus(final DeviceImpl device, final IPollable pollable) {
        if(pollable instanceof AttributeImpl)
            return buildPollingStatus(
                    "attribute",
                    device.getAttributeHistorySize((AttributeImpl) pollable) > 0,
                    pollable);
        else if(pollable instanceof CommandImpl)
            return buildPollingStatus(
                    "command",
                    device.getCommandHistorySize((CommandImpl) pollable) > 0,
                    pollable);
        else
            throw new AssertionError("Can not happen: pollable must be either AttributeImpl or CommandImpl");
    }

    private String buildPollingStatus(String entity, final boolean hasRecordedData, final IPollable pollable) {
        // XXX WARN!!! The string table is parsed by jive.Do not change a letter
        // of
        // the result!
        return String.format(POLL_STATUS_OUTPUT_TEMPLATE, entity, pollable.getName(),
                pollable.getPollingPeriod() == 0 ?
                        "Polling externally triggered" :
                        String.format("Polling period (mS) = %d", pollable.getPollingPeriod()),
                pollable.getPollRingDepth(),
                hasRecordedData ? "" : "No data recorded yet",
                pollable.getLastDevFailed().isEmpty() ?
                        String.format(LAST_RECORDED_DATA_STATUS_TEMPLATE,
                                pollable.getExecutionDuration(),
                                System.currentTimeMillis() - (long) pollable.getLastUpdateTime(),
                                pollable.getDeltaTime()):
                        String.format("Last attribute read FAILED :\n%s", pollable.getLastDevFailed())
            );
    }
}
