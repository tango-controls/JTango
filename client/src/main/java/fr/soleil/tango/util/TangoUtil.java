/*
 * (c) Copyright 2004, iSencia Belgium NV
 * All Rights Reserved.
 * 
 * This software is the proprietary information of iSencia Belgium NV.
 * Use is subject to license terms.
 */
package fr.soleil.tango.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.Database;
import fr.esrf.TangoDs.TangoConst;

/**
 * Some utilities for tango. Moved to JTangoCommons, org.tango.utils
 */
@Deprecated
public final class TangoUtil {

    private static final String DBASE_NO = "#dbase=no";
    /**
     * Contains all tango types for scalar commands
     */
    public static final List<Integer> SCALARS;
    static {
        final List<Integer> tempList = new ArrayList<Integer>();
        tempList.add(TangoConst.Tango_DEV_BOOLEAN);
        tempList.add(TangoConst.Tango_DEV_DOUBLE);
        tempList.add(TangoConst.Tango_DEV_FLOAT);
        tempList.add(TangoConst.Tango_DEV_LONG);
        tempList.add(TangoConst.Tango_DEV_SHORT);
        tempList.add(TangoConst.Tango_DEV_UCHAR);
        tempList.add(TangoConst.Tango_DEV_ULONG);
        tempList.add(TangoConst.Tango_DEV_USHORT);
        tempList.add(TangoConst.Tango_DEV_STRING);
        tempList.add(TangoConst.Tango_DEV_UCHAR);
        tempList.add(TangoConst.Tango_DEV_ULONG64);
        tempList.add(TangoConst.Tango_DEV_LONG64);
        SCALARS = Collections.unmodifiableList(tempList);
    }

    /**
     * Contains all tango types for spectrum commands
     */
    public static final List<Integer> SPECTRUMS;
    static {
        final List<Integer> tempList2 = new ArrayList<Integer>();
        tempList2.add(TangoConst.Tango_DEVVAR_CHARARRAY);
        tempList2.add(TangoConst.Tango_DEVVAR_SHORTARRAY);
        tempList2.add(TangoConst.Tango_DEVVAR_LONGARRAY);
        tempList2.add(TangoConst.Tango_DEVVAR_FLOATARRAY);
        tempList2.add(TangoConst.Tango_DEVVAR_DOUBLEARRAY);
        tempList2.add(TangoConst.Tango_DEVVAR_USHORTARRAY);
        tempList2.add(TangoConst.Tango_DEVVAR_ULONGARRAY);
        tempList2.add(TangoConst.Tango_DEVVAR_STRINGARRAY);
        tempList2.add(TangoConst.Tango_DEVVAR_LONG64ARRAY);
        tempList2.add(TangoConst.Tango_DEVVAR_ULONG64ARRAY);
        SPECTRUMS = Collections.unmodifiableList(tempList2);
    }

    private TangoUtil() {

    }

    /**
     * Get the full device name for an attribute
     * 
     * @param attributeName
     * @return
     * @throws DevFailed
     */
    public static String getfullDeviceNameForAttribute(final String attributeName) throws DevFailed {
        String result;
        final String[] fields = attributeName.split("/");
        final Database db = ApiUtil.get_db_obj();
        if (fields.length == 1) {
            result = db.get_attribute_from_alias(fields[0]);
        } else if (fields.length == 2) {
            result = db.get_device_from_alias(fields[0]);
        } else if (fields.length == 4) {
            result = fields[0] + "/" + fields[1] + "/" + fields[2];
        } else {
            throw DevFailedUtils.newDevFailed("TANGO_WRONG_DATA_ERROR", "cannot retrieve device name");
        }
        return result;
    }

    /**
     * Get the full attribute name
     * 
     * @param attributeName
     * @return
     * @throws DevFailed
     */
    public static String getfullAttributeNameForAttribute(final String attributeName) throws DevFailed {
        String result;
        final String[] fields = attributeName.split("/");
        final Database db = ApiUtil.get_db_obj();
        if (attributeName.contains(DBASE_NO)) {
            result = attributeName;
        } else if (fields.length == 1) {
            result = db.get_attribute_from_alias(fields[0]);
        } else if (fields.length == 2) {
            result = db.get_device_from_alias(fields[0]) + "/" + fields[1];
        } else {
            result = attributeName;
        }
        return result;
    }

    public static String getFullDeviceNameForCommand(final String commandName) throws DevFailed {
        return getfullNameForDevice(commandName.substring(0, commandName.lastIndexOf('/')));
    }

    /**
     * Get the full device name
     * 
     * @param deviceName
     * @return
     * @throws DevFailed
     */
    public static String getfullNameForDevice(final String deviceName) throws DevFailed {
        if (deviceName == null) {
            throw DevFailedUtils.newDevFailed("cannot retrieve device name", "device name is empty");
        }
        String result;
        final String[] fields = deviceName.split("/");
        final Database db = ApiUtil.get_db_obj();
        if (deviceName.contains(DBASE_NO)) {
            result = deviceName;
        } else if (fields.length == 1) {
            result = db.get_device_from_alias(fields[0]);
        } else {
            result = deviceName;
        }
        return result;
    }

    /**
     * Get the list of device names which matches the pattern p
     * 
     * @param deviceNamePattern
     *            The pattern. The wild char is *
     * @return A list of device names
     * @throws DevFailed
     */
    public static String[] getDevicesForPattern(final String deviceNamePattern) throws DevFailed {
        String[] devices;
        // is p a device name or a device name pattern ?
        if (!deviceNamePattern.contains("*")) {
            // p is a pure device name
            devices = new String[1];
            devices[0] = TangoUtil.getfullNameForDevice(deviceNamePattern);
        } else {
            // ask the db the list of device matching pattern p
            final Database db = ApiUtil.get_db_obj();
            devices = db.get_device_exported(deviceNamePattern);
        }
        return devices;
    }

    /**
     * Return the attribute name part without device name It is able to resolve attribute alias before
     * 
     * @param fullname
     * @return
     * @throws DevFailed
     */
    public static String getAttributeName(final String fullname) throws DevFailed {
        final String s = TangoUtil.getfullAttributeNameForAttribute(fullname);
        return s.substring(s.lastIndexOf('/') + 1);
    }

}
