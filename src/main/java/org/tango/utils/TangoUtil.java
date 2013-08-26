/*
 * (c) Copyright 2004, iSencia Belgium NV
 * All Rights Reserved.
 * 
 * This software is the proprietary information of iSencia Belgium NV.
 * Use is subject to license terms.
 */
package org.tango.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.Database;
import fr.esrf.TangoDs.TangoConst;

/**
 * Some utilities for tango
 */
public final class TangoUtil {

    private static final String DEVICE_SEP = "/";
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
        tempList.add(TangoConst.Tango_DEV_ENCODED);
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

    public static final Map<String, AttrWriteType> WRITABLE_MAP;
    static {
        final Map<String, AttrWriteType> tmpMap1 = new HashMap<String, AttrWriteType>();
        tmpMap1.put(AttrWriteType.READ.toString(), AttrWriteType.READ);
        tmpMap1.put(AttrWriteType.READ_WITH_WRITE.toString(), AttrWriteType.READ_WITH_WRITE);
        tmpMap1.put(AttrWriteType.WRITE.toString(), AttrWriteType.WRITE);
        tmpMap1.put(AttrWriteType.READ_WRITE.toString(), AttrWriteType.READ_WRITE);
        WRITABLE_MAP = Collections.unmodifiableMap(tmpMap1);

    }

    public static final Map<String, AttrDataFormat> FORMAT_MAP;
    static {
        final Map<String, AttrDataFormat> tmpMap2 = new HashMap<String, AttrDataFormat>();
        tmpMap2.put(AttrDataFormat.SCALAR.toString(), AttrDataFormat.SCALAR);
        tmpMap2.put(AttrDataFormat.SPECTRUM.toString(), AttrDataFormat.SPECTRUM);
        tmpMap2.put(AttrDataFormat.IMAGE.toString(), AttrDataFormat.IMAGE);
        tmpMap2.put(AttrDataFormat.FMT_UNKNOWN.toString(), AttrDataFormat.FMT_UNKNOWN);
        FORMAT_MAP = Collections.unmodifiableMap(tmpMap2);
    }

    public static final Map<String, Integer> TYPE_MAP;
    static {
        final Map<String, Integer> tmpMap3 = new HashMap<String, Integer>();
        tmpMap3.put("VOID", 0);
        tmpMap3.put("BOOLEAN", 1);
        tmpMap3.put("SHORT", 2);
        tmpMap3.put("LONG", 3);
        tmpMap3.put("FLOAT", 4);
        tmpMap3.put("DOUBLE", 5);
        tmpMap3.put("USHORT", 6);
        tmpMap3.put("ULONG", 7);
        tmpMap3.put("STRING", 8);
        tmpMap3.put("STATE", 19);
        tmpMap3.put("CONST_STRING", 20);
        tmpMap3.put("CHAR", 21);
        tmpMap3.put("UCHAR", 22);
        tmpMap3.put("LONG64", 23);
        tmpMap3.put("ULONG64", 24);
        tmpMap3.put("INT", 27);
        tmpMap3.put("ENCODED", 28);
        TYPE_MAP = Collections.unmodifiableMap(tmpMap3);
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
        final String[] fields = attributeName.split(DEVICE_SEP);
        if (fields.length == 1) {
            result = ApiUtil.get_db_obj().get_attribute_from_alias(fields[0]);
        } else if (fields.length == 2) {
            result = ApiUtil.get_db_obj().get_device_from_alias(fields[0]);
        } else if (fields.length == 4) {
            result = fields[0] + DEVICE_SEP + fields[1] + DEVICE_SEP + fields[2];
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
        final String[] fields = attributeName.split(DEVICE_SEP);
        if (attributeName.contains(DBASE_NO)) {
            result = attributeName;
        } else {
            final Database db = ApiUtil.get_db_obj();
            if (fields.length == 1) {
                result = db.get_attribute_from_alias(fields[0]);
            } else if (fields.length == 2) {
                result = db.get_device_from_alias(fields[0]) + DEVICE_SEP + fields[1];
            } else {
                result = attributeName;
            }
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
            DevFailedUtils.throwDevFailed("cannot retrieve device name", "device name is empty");
        }
        String result;
        final String[] fields = deviceName.split(DEVICE_SEP);

        if (deviceName.contains(DBASE_NO)) {
            result = deviceName;
        } else {
            final Database db = ApiUtil.get_db_obj();
            if (fields.length == 1) {
                result = db.get_device_from_alias(fields[0]);
            } else {
                result = deviceName;
            }
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
     * Return the attribute name part without device name It is able to resolve
     * attribute alias before
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
