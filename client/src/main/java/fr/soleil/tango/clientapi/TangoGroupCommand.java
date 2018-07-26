package fr.soleil.tango.clientapi;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.tango.utils.DevFailedUtils;
import org.tango.utils.TangoUtil;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoApi.Group.Group;
import fr.esrf.TangoApi.Group.GroupCmdReply;
import fr.esrf.TangoApi.Group.GroupCmdReplyList;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.tango.clientapi.factory.ProxyFactory;

/**
 * Manage a group of tango commands. TODO mock
 *
 * @author ABEILLE
 *
 */
public final class TangoGroupCommand {
    private static final String ALL_INPUT_TYPES_MUST_BE_THE_SAME = "all input types must be the same";
    private static final String TANGO_WRONG_DATA_ERROR = "TANGO_WRONG_DATA_ERROR";
    private final Group group;
    private final String commandName;
    private final DeviceData[] inData;

    public TangoGroupCommand(final String groupName, final String commandName, final String... deviceNames)
            throws DevFailed {
        group = ProxyFactory.getInstance().createGroup(groupName, deviceNames);
        // test if command exists
        for (int i = 0; i < group.get_size(true); i++) {
            final DeviceProxy dev = group.get_device(i + 1);
            if (dev == null) {
                throw DevFailedUtils.newDevFailed("impossible to create " + commandName);
            }
            dev.command_query(commandName);
        }
        this.commandName = commandName;
        inData = new DeviceData[deviceNames.length];
        for (int i = 0; i < deviceNames.length; i++) {
            inData[i] = new DeviceData();
        }
    }

    /**
     * Execute the command on a single device or on a group If group is used the return data is not managed for the
     * moment.
     *
     * @throws DevFailed
     */
    public void execute() throws DevFailed {
        GroupCmdReplyList replies;
        if (isArginVoid()) {
            replies = group.command_inout(commandName, true);
        } else {
            replies = group.command_inout(commandName, inData, true);
        }
        if (replies.has_failed()) {
            // get data to throw e
            for (final Object obj : replies) {
                ((GroupCmdReply) obj).get_data();
            }
        }
    }

    /**
     * insert a single value for all commands
     *
     * @param value
     * @throws DevFailed
     */
    public void insert(final Object value) throws DevFailed {
        for (int i = 0; i < group.get_size(true); i++) {
            final int arginType = group.get_device(i + 1).command_query(commandName).in_type;
            InsertExtractUtils.insert(inData[i], arginType, value);
        }
    }

    /**
     * insert a value per command
     *
     * @param value
     *            The values to insert. Size must be equals to the number of commands
     * @throws DevFailed
     */
    public void insert(final Object... value) throws DevFailed {
        if (value.length == 1) {
            for (int i = 0; i < group.get_size(true); i++) {
                final int arginType = group.get_device(i + 1).command_query(commandName).in_type;
                InsertExtractUtils.insert(inData[i], arginType, value);
            }
        } else {
            if (value.length != group.get_size(true)) {
                throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, group.get_size(true) + " values must be provided");
            }
            for (int i = 0; i < group.get_size(true); i++) {
                final int arginType = group.get_device(i + 1).command_query(commandName).in_type;
                InsertExtractUtils.insert(inData[i], arginType, value[i]);
            }
        }
    }

    public void insertMixArgin(final double[] numberArgin, final String[] stringArgin) throws DevFailed {
        int arginType = 0;
        int previousType = 0;
        for (int i = 0; i < group.get_size(true); i++) {
            previousType = arginType;
            final DeviceProxy dev = group.get_device(i + 1);
            arginType = dev.command_query(commandName).in_type;
            if (i > 0 && arginType != previousType) {
                throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, ALL_INPUT_TYPES_MUST_BE_THE_SAME);
            }
        }
        if (arginType == TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY) {
            final DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray(numberArgin, stringArgin);
            for (int i = 0; i < group.get_size(true); i++) {
                inData[i].insert(dvdsa);
            }
        } else if (arginType == TangoConst.Tango_DEVVAR_LONGSTRINGARRAY) {
            final int[] in = new int[numberArgin.length];
            for (int i = 0; i < numberArgin.length; i++) {
                in[i] = (int) numberArgin[i];
            }
            final DevVarLongStringArray dvlsa = new DevVarLongStringArray(in, stringArgin);
            for (int i = 0; i < group.get_size(true); i++) {
                inData[i].insert(dvlsa);
            }
        } else {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, "input type " + " not supported");
        }
    }

    public void insertMixArgin(final String[] numberArgin, final String[] stringArgin) throws DevFailed {
        int arginType = 0;
        int previousType = 0;
        for (int i = 0; i < group.get_size(true); i++) {
            previousType = arginType;
            final DeviceProxy dev = group.get_device(i + 1);
            arginType = dev.command_query(commandName).in_type;
            if (i > 0 && arginType != previousType) {
                throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, ALL_INPUT_TYPES_MUST_BE_THE_SAME);
            }
        }

        if (arginType == TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY) {
            final double[] d = new double[numberArgin.length];
            for (int i = 0; i < numberArgin.length; i++) {
                d[i] = Double.parseDouble(numberArgin[i]);
            }
            final DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray(d, stringArgin);
            for (int i = 0; i < group.get_size(true); i++) {
                inData[i].insert(dvdsa);
            }
        } else if (arginType == TangoConst.Tango_DEVVAR_LONGSTRINGARRAY) {
            final int[] in = new int[numberArgin.length];
            for (int i = 0; i < numberArgin.length; i++) {
                in[i] = Integer.parseInt(numberArgin[i]);
            }
            final DevVarLongStringArray dvlsa = new DevVarLongStringArray(in, stringArgin);
            for (int i = 0; i < group.get_size(true); i++) {
                inData[i].insert(dvlsa);
            }
        } else {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, "input type " + " not supported");
        }
    }

    /**
     *
     * @return true if all parameter types are void
     * @throws DevFailed
     */
    public boolean isArginVoid() throws DevFailed {
        boolean isArginVoid = true;
        for (int i = 0; i < group.get_size(true); i++) {
            final DeviceProxy dev = group.get_device(i + 1);
            final int arginType = dev.command_query(commandName).in_type;
            if (arginType != TangoConst.Tango_DEV_VOID) {
                isArginVoid = false;
                break;
            }
        }
        return isArginVoid;
    }

    /**
     *
     * @return true if all parameter types are scalars
     * @throws DevFailed
     */
    public boolean isArginScalar() throws DevFailed {
        boolean isArginScalar = true;
        for (int i = 0; i < group.get_size(true); i++) {
            final DeviceProxy dev = group.get_device(i + 1);
            final int arginType = dev.command_query(commandName).in_type;
            isArginScalar = TangoUtil.SCALARS.contains(arginType);
            if (!isArginScalar) {
                break;
            }
        }
        return isArginScalar;
    }

    /**
     *
     * @return true if all parameter types are spectrum
     * @throws DevFailed
     */
    public boolean isArginSpectrum() throws DevFailed {
        boolean isArginSpectrum = true;
        for (int i = 0; i < group.get_size(true); i++) {
            final DeviceProxy dev = group.get_device(i + 1);
            final int arginType = dev.command_query(commandName).in_type;
            isArginSpectrum = TangoUtil.SPECTRUMS.contains(arginType);
            if (!isArginSpectrum) {
                break;
            }
        }
        return isArginSpectrum;
    }

    /**
     *
     * @return rue if all parameter types DEVVAR_DOUBLESTRINGARRAY or DEVVAR_LONGSTRINGARRAY
     * @throws DevFailed
     */
    public boolean isArginMixFormat() throws DevFailed {
        boolean isArginMixFormat = false;
        for (int i = 0; i < group.get_size(true); i++) {
            final DeviceProxy dev = group.get_device(i + 1);
            final int arginType = dev.command_query(commandName).in_type;
            if (arginType == TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY
                    || arginType == TangoConst.Tango_DEVVAR_LONGSTRINGARRAY) {
                isArginMixFormat = true;
                break;
            }
        }
        return isArginMixFormat;
    }

    @Override
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this);
        str.append("name", commandName);
        return str.toString();
    }
}
