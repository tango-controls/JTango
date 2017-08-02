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

import com.google.common.collect.Lists;
import fr.esrf.Tango.DevFailed;
import org.junit.Test;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.build.DeviceClassBuilder;
import org.tango.server.command.CommandImpl;
import org.tango.server.servant.DeviceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 8/2/17
 */
public class PollStatusCommandTest {
    private List<DeviceClassBuilder> classList = new ArrayList<>();

    {
        DeviceClassBuilder mockDeviceClassBuilder = mock(DeviceClassBuilder.class);
        List<DeviceImpl> deviceList = new ArrayList<>();

        createNonPolledDevice(deviceList);

        createPolledDevice(deviceList);

        doReturn(deviceList).when(mockDeviceClassBuilder).getDeviceImplList();

        classList.add(mockDeviceClassBuilder);
    }

    private void createNonPolledDevice(List<DeviceImpl> deviceList) {
        //device without any polled entities
        DeviceImpl mockDeviceImpl = mock(DeviceImpl.class);
        doReturn("1/1/1").when(mockDeviceImpl).getName();
        deviceList.add(mockDeviceImpl);
    }

    private void createPolledDevice(List<DeviceImpl> deviceList) {
        DeviceImpl polledDevice = mock(DeviceImpl.class);
        doReturn("1/1/2").when(polledDevice).getName();

        CommandImpl notPolledCommand = mock(CommandImpl.class);
        doReturn(false).when(notPolledCommand).isPolled();

        CommandImpl polledCommand = mock(CommandImpl.class);
        doReturn(true).when(polledCommand).isPolled();
        doReturn("").when(polledCommand).getLastDevFailed();

        AttributeImpl notPolledAttribute = mock(AttributeImpl.class);
        doReturn(false).when(notPolledAttribute).isPolled();

        AttributeImpl polledAttribute = mock(AttributeImpl.class);
        doReturn(true).when(polledAttribute).isPolled();
        doReturn("").when(polledAttribute).getLastDevFailed();

        doReturn(Lists.newArrayList(polledCommand, notPolledCommand)).when(polledDevice).getCommandList();
        doReturn(Lists.newArrayList(notPolledAttribute, polledAttribute)).when(polledDevice).getAttributeList();
        deviceList.add(polledDevice);
    }

    @Test
    public void noPolledCommandsNorAttributes() throws Exception {
        PollStatusCommand instance = new PollStatusCommand("1/1/1", classList);
        String[] result = instance.call();

        assertArrayEquals(new String[0], result);
    }

    @Test
    public void polledCommandsAndAttributes() throws Exception {
        PollStatusCommand instance = new PollStatusCommand("1/1/2", classList);
        String[] result = instance.call();

        assertEquals(2, result.length);
    }

    @Test
    public void nonExistingDevice() throws DevFailed {
        try {
            new PollStatusCommand("1/1/3", classList).call();
        } catch (DevFailed devFailed) {
            assertEquals("API_DeviceNotFound", devFailed.getMessage());
        }
    }
}
