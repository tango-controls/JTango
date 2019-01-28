/*
 * (c) Copyright 2004, iSencia Belgium NV
 * All Rights Reserved.
 * 
 * This software is the proprietary information of iSencia Belgium NV.
 * Use is subject to license terms.
 */
package org.tango.utils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.Database;
import fr.esrf.TangoDs.TangoConst;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Some utilities for tango
 */
public final class TangoUtil {

    public static final String DEVICE_SEPARATOR = "/";
    public static final int FULL_NAME_NR_SEPARATOR = 3;
    public static final String DEVICE_PATTERN = "*";
    /**
     * Contains all tango types for scalar commands
     */
    public static final List<Integer> SCALARS;
    /**
     * Contains all tango types for spectrum commands
     */
    public static final List<Integer> SPECTRUMS;
    public static final Map<String, AttrWriteType> WRITABLE_MAP;
    public static final Map<String, AttrDataFormat> FORMAT_MAP;
    public static final Map<String, Integer> TYPE_MAP;
    public static final String DBASE_NO = "#dbase=no";
    private static final Pattern ENTITY_SPLIT_PATTERN;
    private static final int PREFIX_INDEX = 1;
    private static final int ATTRIBUTE_ALIAS_INDEX = 2;
    private static final int DEVICE_NAME_INDEX = 3;
    private static final int DEVICE_ALIAS_INDEX = 4;

    /*
     * http://www.esrf.eu/computing/cs/tango/tango_doc/kernel_doc/ds_prog/node13.html
     *
     * [protocol://][host:port/]device_name[/attribute][->property][#dbase=xx]
     *
     * From the naming schema described above, the reserved characters are :,#,/ and the reserved string is : ->.
     */
    private static final int ENTITY_INDEX = 5;
    private static final int NO_DB_INDEX = 6;

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
        tempList.add(TangoConst.Tango_DEV_STATE);
        SCALARS = Collections.unmodifiableList(tempList);
    }

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

    static {
        final Map<String, AttrWriteType> tmpMap1 = new HashMap<String, AttrWriteType>();
        tmpMap1.put(AttrWriteType.READ.toString(), AttrWriteType.READ);
        tmpMap1.put(AttrWriteType.READ_WITH_WRITE.toString(), AttrWriteType.READ_WITH_WRITE);
        tmpMap1.put(AttrWriteType.WRITE.toString(), AttrWriteType.WRITE);
        tmpMap1.put(AttrWriteType.READ_WRITE.toString(), AttrWriteType.READ_WRITE);
        WRITABLE_MAP = Collections.unmodifiableMap(tmpMap1);

    }

    static {
        final Map<String, AttrDataFormat> tmpMap2 = new HashMap<String, AttrDataFormat>();
        tmpMap2.put(AttrDataFormat.SCALAR.toString(), AttrDataFormat.SCALAR);
        tmpMap2.put(AttrDataFormat.SPECTRUM.toString(), AttrDataFormat.SPECTRUM);
        tmpMap2.put(AttrDataFormat.IMAGE.toString(), AttrDataFormat.IMAGE);
        tmpMap2.put(AttrDataFormat.FMT_UNKNOWN.toString(), AttrDataFormat.FMT_UNKNOWN);
        FORMAT_MAP = Collections.unmodifiableMap(tmpMap2);
    }

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

    static {
        // (?: xxx ) = non capturing group
        // ( xxx ) = capturing group. Group content is "xxx" once the matcher is finished

        final String nameR = "[\\w\\-\\.]+";// ie [a-zA-Z_0-9-.]
        // String protocolR = "(?:(?:tango|taco)://)";
        final String protocolR = "(?:(?:(?:tango|taco):)?//)";// not interested in protocol
        final String hostPortR = "(?:" + nameR + ":\\d+/)";// not interested in host and port
        final String prefixR = "(" + protocolR + "?" + hostPortR + "?)";// interested in prefix
        final String deviceAliasR = "(" + nameR + ")";
        final String attributeAliasR = "(" + nameR + ")";
        final String deviceR = "(" + nameR + "/" + nameR + "/" + nameR + ")";
        final String attributeR = "(?:/(" + nameR + "))";// interested in attribute's name
        final String propertyR = "(?:->" + nameR + ")?";// not interested in property
        final String dbaseR = "(?:#dbase=(yes|(no)))?";// interested in 'no' only

        // prefix is always in a group, can be empty
        // attribute is mandatory for the purpose of splitting to "device/entity"
        final String entityR = prefixR + "(?:" + attributeAliasR + "|(?:(?:" + deviceR + "|" + deviceAliasR + ")"
                + attributeR + "))" + propertyR + dbaseR;

        // This pattern is designed for splitting full device name and entity name.
        // It cannot be used for syntax validation.
        ENTITY_SPLIT_PATTERN = Pattern.compile(entityR, Pattern.CASE_INSENSITIVE);
    }

    private TangoUtil() {

    }

    /**
     * Splits an entity name into full device name and attribute name. Aliases will be resolved against tango db first.
     *
     * @param entityName the entity name to split. It can contain aliases for device or attribute
     * @return a Map.Entry containing the full device name as key and the attribute name as value, or null if split was
     * not possible
     * @throws DevFailed in case of DB access problem
     */
    public static final Entry<String, String> splitDeviceEntity(final String entityName) throws DevFailed {
        Entry<String, String> result = null;

        final Matcher matcher = ENTITY_SPLIT_PATTERN.matcher(entityName.trim());
        if (matcher.matches()) {
            String device = null;
            String entity = null;
            final String prefixGroup = matcher.group(PREFIX_INDEX);

            final boolean noDb = matcher.group(NO_DB_INDEX) != null;
            if (noDb) {
                // TODO cas device alias qui marche soleil
                if (matcher.group(DEVICE_NAME_INDEX) != null && matcher.group(ENTITY_INDEX) != null) {
                    final String deviceNameGroup = matcher.group(DEVICE_NAME_INDEX);
                    final String entityGroup = matcher.group(ENTITY_INDEX);

                    device = prefixGroup + deviceNameGroup;
                    entity = entityGroup;
                }
            } else {
                if (matcher.group(ATTRIBUTE_ALIAS_INDEX) != null) {
                    final String attributeAliasGroup = matcher.group(ATTRIBUTE_ALIAS_INDEX);
                    final String fullAttributeName = ApiUtil.get_db_obj().get_attribute_from_alias(attributeAliasGroup);
                    final int lastIndexOf = fullAttributeName.lastIndexOf(DEVICE_SEPARATOR);
                    device = prefixGroup + fullAttributeName.substring(0, lastIndexOf);// TODO exception ?
                    entity = fullAttributeName.substring(lastIndexOf + 1);
                } else if (matcher.group(DEVICE_ALIAS_INDEX) != null) {
                    final String deviceAliasGroup = matcher.group(DEVICE_ALIAS_INDEX);
                    final String entityGroup = matcher.group(ENTITY_INDEX);
                    final String fullDeviceName = ApiUtil.get_db_obj().get_device_from_alias(deviceAliasGroup);
                    device = prefixGroup + fullDeviceName;
                    entity = entityGroup;
                } else {
                    final String deviceNameGroup = matcher.group(DEVICE_NAME_INDEX);
                    final String entityGroup = matcher.group(ENTITY_INDEX);
                    device = prefixGroup + deviceNameGroup;
                    entity = entityGroup;
                }
            }

            if (device != null && entity != null) {
                result = new SimpleEntry<String, String>(device, entity);
            }
        }

        return result;
    }

    /**
     * Splits a collection of entity names and group attributes by device.
     *
     * @param entityNames a list of entity names to split
     * @return a map containing a list of attributes for each device.
     */
    public static Map<String, Collection<String>> splitDeviceEntities(final Collection<String> entityNames) {
        final Map<String, Collection<String>> result = new HashMap<String, Collection<String>>();

        String device;
        String entity;
        for (final String entityName : entityNames) {
            try {
                final Entry<String, String> deviceEntity = splitDeviceEntity(entityName);
                if (deviceEntity != null) {
                    device = deviceEntity.getKey();
                    entity = deviceEntity.getValue();
                    if (device != null && entity != null) {
                        Collection<String> attributes = result.get(device);
                        if (attributes == null) {
                            attributes = new HashSet<String>();
                            result.put(device, attributes);
                        }
                        attributes.add(entity);
                    }
                }
            } catch (final DevFailed e) {
                // nop
            }
        }

        return result;
    }

    /**
     * Get the full device name for an attribute
     *
     * @param attributeName
     * @return
     * @throws DevFailed
     */
    public static String getfullDeviceNameForAttribute(final String attributeName) throws DevFailed {
        checkNullOrEmptyString(attributeName);
        String result;
        if (attributeName.contains(DBASE_NO)) {
            result = attributeName.substring(0, attributeName.lastIndexOf(DEVICE_SEPARATOR));
        } else {
            final String[] fields = attributeName.split(DEVICE_SEPARATOR);
            if (fields.length == 1) {
                result = getfullDeviceNameForAttribute(ApiUtil.get_db_obj().get_attribute_from_alias(fields[0]));
            } else if (fields.length == 2) {
                result = ApiUtil.get_db_obj().get_device_from_alias(fields[0]);
            } else if (fields.length == 4) {
                result = fields[0] + DEVICE_SEPARATOR + fields[1] + DEVICE_SEPARATOR + fields[2];
            } else {
                throw DevFailedUtils.newDevFailed("TANGO_WRONG_DATA_ERROR", "cannot retrieve device name");
            }
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
        checkNullOrEmptyString(attributeName);
        String result;
        if (attributeName.contains(DBASE_NO)) {
            result = attributeName;
        } else {
            final String[] fields = attributeName.split(DEVICE_SEPARATOR);
            final Database db = ApiUtil.get_db_obj();
            if (fields.length == 1) {
                result = db.get_attribute_from_alias(fields[0]);
            } else if (fields.length == 2) {
                result = db.get_device_from_alias(fields[0]) + DEVICE_SEPARATOR + fields[1];
            } else {
                result = attributeName;
            }
        }
        return result;
    }

    public static String getFullDeviceNameForCommand(final String commandName) throws DevFailed {
        checkNullOrEmptyString(commandName);
        return getfullNameForDevice(commandName.substring(0, commandName.lastIndexOf('/')));
    }

    public static void checkFullCommandName(final String fullCommandName) throws DevFailed {
        checkNullOrEmptyString(fullCommandName);
//        if (!fullCommandName.contains(DBASE_NO)
//                && StringUtils.countMatches(fullCommandName, TangoUtil.DEVICE_SEPARATOR) != TangoUtil.FULL_NAME_NR_SEPARATOR) {
//            throw DevFailedUtils.newDevFailed(fullCommandName + " command must contains 4 fields");
//        }
    }

    /**
     * Get the full device name
     *
     * @param deviceName
     * @return
     * @throws DevFailed
     */
    public static String getfullNameForDevice(final String deviceName) throws DevFailed {
        checkNullOrEmptyString(deviceName);
        final String[] fields = deviceName.split(DEVICE_SEPARATOR);

        if (deviceName.contains(DBASE_NO) || fields.length != 1) {
            return deviceName;
        } else {
            final Database db = ApiUtil.get_db_obj();
            return db.get_device_from_alias(fields[0]);
        }
    }

    /**
     * Get the list of device names which matches the pattern p
     *
     * @param deviceNamePattern The pattern. The wild char is *
     * @return A list of device names
     * @throws DevFailed
     */
    public static String[] getDevicesForPattern(final String deviceNamePattern) throws DevFailed {
        checkNullOrEmptyString(deviceNamePattern);
        String[] devices;
        // is p a device name or a device name pattern ?
        if (!deviceNamePattern.contains(DEVICE_PATTERN)) {
            // p is a pure device name
            devices = new String[1];
            devices[0] = getfullNameForDevice(deviceNamePattern);
        } else {
            // ask the db the list of device matching pattern p
            final Database db = ApiUtil.get_db_obj();
            devices = db.get_device_exported(deviceNamePattern);
        }
        return devices;
    }

    /**
     * Get the list of device names which matches the pattern p
     *
     * @param deviceNamePattern The pattern. The wild char is *
     * @param boolean           to get the exported device
     * @return A list of device names
     * @throws DevFailed
     */
    public static String[] getDevicesForPattern(final String deviceNamePattern, final boolean exported)
            throws DevFailed {
        checkNullOrEmptyString(deviceNamePattern);
        String[] devices;
        if (exported) {
            devices = getDevicesForPattern(deviceNamePattern);
        } else {
            if (!deviceNamePattern.contains(DEVICE_PATTERN)) {
                // p is a pure device name
                devices = new String[1];
                devices[0] = getfullNameForDevice(deviceNamePattern);
            } else {
                // ask the db the list of device matching pattern p
                final Database db = ApiUtil.get_db_obj();
                devices = db.get_device_list(deviceNamePattern);
            }
        }
        return devices;
    }

    /**
     * Return the attribute name part without device name
     * It is able to resolve attribute alias before
     *
     * @param fullname
     * @return
     * @throws DevFailed
     */
    public static String getAttributeName(final String fullname) throws DevFailed {
        final String s = getfullAttributeNameForAttribute(fullname);
        return s.substring(s.lastIndexOf(DEVICE_SEPARATOR) + 1);
    }

    private static void checkNullOrEmptyString(final String s) throws DevFailed {
        if (s == null || s.isEmpty()) {
            throw DevFailedUtils.newDevFailed("string is null or empty");
        }
    }

}
