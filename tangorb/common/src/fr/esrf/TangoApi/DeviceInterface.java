//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: pascal_verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision: 25296 $
//
//======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevIntrChange;

/**
 * Object defining device interface (commands and attributes and
 * to callback when a InterfaceChange event is received.
 *
 * @author pascal_verdier
 */
public class DeviceInterface {
    /**
     * Attribute configuration list for the device.
     */
    public AttributeInfoEx[] attributeInfoList;
    /**
     * Command info list for the device.
     */
    public CommandInfo[]        commandInfoList;
    /**
     * false if change is possible, true if change is sure (in case of event).
     */
    public boolean              deviceStarted;
    //=============================================
    /**
     * Creates a new instance of DeviceInterface for specified device.
     * @param deviceName specified device name
     * @throws DevFailed if device connection failed.
     */
    //=============================================
    public DeviceInterface(String deviceName) throws DevFailed {
        this(new DeviceProxy(deviceName));
    }
    //=============================================
    /**
     * Creates a new instance of DeviceInterface for specified device.
     * @param deviceProxy specified device proxy object.
     * @throws DevFailed if device connection failed.
     */
    //=============================================
    public DeviceInterface(DeviceProxy deviceProxy) throws DevFailed {
        attributeInfoList = deviceProxy.get_attribute_info_ex();
        commandInfoList = deviceProxy.command_list_query();
        deviceStarted = false;
    }
    //=============================================
    /**
     * Creates a new instance of DeviceInterface
     */
    //=============================================
    public DeviceInterface(boolean deviceStarted,
                              CommandInfo[] commandInfoList, AttributeInfoEx[] attributeInfoList) {
        this.deviceStarted = deviceStarted;
        this.commandInfoList = commandInfoList;
        this.attributeInfoList = attributeInfoList;
    }
    //=============================================
    /**
     * Creates a new instance of DeviceInterface
     */
    //=============================================
    public DeviceInterface(DevIntrChange interfaceChange) {
        this.deviceStarted = interfaceChange.dev_started;
        this.commandInfoList = new CommandInfo[interfaceChange.cmds.length];
        for (int i=0 ; i<interfaceChange.cmds.length ; i++)
            this.commandInfoList[i] = new CommandInfo(interfaceChange.cmds[i]);
        this.attributeInfoList = new AttributeInfoEx[interfaceChange.atts.length];
        for (int i=0 ; i<interfaceChange.atts.length ; i++)
            this.attributeInfoList[i] = new AttributeInfoEx(interfaceChange.atts[i]);
    }
    //=============================================
    /**
     * @return the attribute number.
     */
    //=============================================
    public int getAttributeNumber() {
        return attributeInfoList.length;
    }
    //=============================================
    /**
     * @return the command number.
     */
    //=============================================
    public int getCommandNumber() {
        return commandInfoList.length;
    }
    //=============================================
    /**
     * @return the attribute configuration list for the device.
     */
    //=============================================
    public AttributeInfoEx[] getAttributeInfoList() {
        return attributeInfoList;
    }
    //=============================================
    /**
     * @param index Specified index for attribute info list.
     * @return the attribute configuration for specified index.
     */
    //=============================================
    public AttributeInfoEx getAttributeInfo(int index) {
        return attributeInfoList[index];
    }
    //=============================================
    /**
     * @param attributeName Specified name for attribute.
     * @return the attribute configuration for specified attribute name,
     *         null if attribute name not found.
     */
    //=============================================
    public AttributeInfoEx getAttributeInfo(String attributeName) {
        for (AttributeInfoEx attributeInfoEx : attributeInfoList)
            if (attributeInfoEx.name.toLowerCase().equals(attributeName.toLowerCase()))
                return attributeInfoEx;
        return null;
    }
    //=============================================
    /**
     * @return the command info list for the device.
     */
    //=============================================
    public CommandInfo[] getCommandInfoList() {
        return commandInfoList;
    }
    //=============================================
    /**
     * @param index Specified index for command info list.
     * @return the command configuration for specified index.
     */
    //=============================================
    public CommandInfo getCommandInfo(int index) {
        return commandInfoList[index];
    }
    //=============================================
    /**
     * @param commandName Specified name for command.
     * @return the command configuration for specified command name,
     *         null if command name not found.
     */
    //=============================================
    public CommandInfo getCommandInfo(String commandName) {
        for (CommandInfo commandInfo : commandInfoList)
            if (commandInfo.cmd_name.toLowerCase().equals(commandName.toLowerCase()))
                return commandInfo;
        return null;
    }
    //=============================================
    //=============================================

    //=============================================
    /**
     * @return false if change is possible, true if change is sure (in case of event).
     */
    //=============================================
    public boolean changeIsSure() {
        return deviceStarted;
    }
    //=============================================
    //=============================================
}
