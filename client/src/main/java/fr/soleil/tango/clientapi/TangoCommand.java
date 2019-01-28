package fr.soleil.tango.clientapi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.tango.clientapi.command.ITangoCommand;
import fr.soleil.tango.clientapi.command.MockCommand;
import fr.soleil.tango.clientapi.command.RealCommand;
import fr.soleil.tango.clientapi.util.TypeConversionUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.tango.utils.DevFailedUtils;
import org.tango.utils.TangoUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage a connection to a tango command
 *
 * @author ABEILLE
 */
public final class TangoCommand {

    private static final String TANGO_WRONG_DATA_ERROR = "TANGO_WRONG_DATA_ERROR";
    private final ITangoCommand command;

    /**
     * Build a mock command.
     *
     * @param command
     *            The mock commandbehavior. Default behavior may be changed by using a mock library like
     *            http://mockito.org
     * @throws DevFailed
     */
    public TangoCommand(final ITangoCommand command) throws DevFailed {
        this.command = command;
    }

    /**
     * Build a mock command with parameter and return value
     *
     * @param commandName
     * @param parameterMockValue
     *            the parameter default value
     * @param returnMockValue
     *            the returned default value
     * @throws DevFailed
     */
    public TangoCommand(final String commandName, final Object parameterMockValue, final Object returnMockValue)
            throws DevFailed {
        command = new MockCommand(commandName, parameterMockValue, returnMockValue);
    }

    /**
     * Build a mock command with parameter or return value
     *
     * @param commandName
     * @param isParameterVoid
     *            true: parameter is void. false: return is void
     * @param mockValue
     *            the default mock value
     * @throws DevFailed
     */
    public TangoCommand(final boolean isParameterVoid, final String commandName, final Object mockValue)
            throws DevFailed {
        command = new MockCommand(isParameterVoid, commandName, mockValue);
    }

    /**
     * Build a connection to a tango command
     *
     * @param commandName
     * @param isMock
     *            true if is mocked. Mock command will be void-void.
     * @throws DevFailed
     */
    public TangoCommand(final String commandName, final boolean isMock) throws DevFailed {
        if (isMock) {
            command = new MockCommand(commandName);
        } else {
            command = new RealCommand(commandName);
        }
    }

    /**
     * @param devProxy
     *            The Tango device proxy
     * @param commandName
     *            The command name
     * @throws DevFailed
     */
    public TangoCommand(final DeviceProxy devProxy, final String commandName) throws DevFailed {
        command = new RealCommand(devProxy, commandName);
    }

    /**
     * @param deviceName
     *            The device tango (e.g. domain/family/member)
     * @param commandName
     *            The command name
     * @throws DevFailed
     * @deprecated use {@link this#TangoCommand(DeviceProxy, String)} instead
     */
    @Deprecated
    public TangoCommand(final String deviceName, final String commandName) throws DevFailed {
        command = new RealCommand(deviceName, commandName);
    }

    /**
     * @param commandName
     *            The device and command name in 4 fields (e.g. domain/family/member/commandName)
     * @throws DevFailed
     */
    public TangoCommand(final String commandName) throws DevFailed {
        TangoUtil.checkFullCommandName(commandName);
        command = new RealCommand(commandName);
    }

    public String getCommandName() {
        return command.getCommandName();
    }

    /**
     * Execute the command on a single device or on a group If group is used the return data is not managed for the
     * moment.
     *
     * @throws DevFailed
     */
    public void execute() throws DevFailed {
        command.execute();
    }

    public void execute(final Object value) throws DevFailed {
        command.execute(value);
    }

    public void execute(final Object... value) throws DevFailed {
        command.execute(value);
    }

    public Object executeExtract(final Object value) throws DevFailed {
        return command.executeExtract(value);
    }

    public <T> T execute(final Class<T> clazz) throws DevFailed {
        final Object r = command.executeExtract();
        return TypeConversionUtil.castToType(clazz, r);
    }

    public Number executeExtractNumber() throws DevFailed {
        final Object result = command.executeExtract();
        Number returnVal = null;
        if (Array.getLength(result) >= 1) {
            if (Array.get(result, 0) instanceof String || Array.get(result, 0) instanceof Boolean) {
                throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, "this command must return a java.lang.Number");
            }
            returnVal = (Number) Array.get(result, 0);
        }
        return returnVal;
    }

    public <T> List<T> executeExtractList(final Class<T> clazz) throws DevFailed {
        return extractList(command.executeExtract(clazz));
    }

    public Number[] executeExtractNumberArray() throws DevFailed {
        final Object result = command.executeExtract();
        return extractAsNumberArray(result);
    }

    public <T> T execute(final Class<T> clazz, final Object value) throws DevFailed {
        final Object r = command.executeExtract(value);
        return TypeConversionUtil.castToType(clazz, r);
    }

    public <T> T execute(final Class<T> clazz, final Object... value) throws DevFailed {
        final Object r = command.executeExtract(value);
        return TypeConversionUtil.castToType(clazz, r);
    }

    public Number executeExtractNumber(final Object value) throws DevFailed {
        final Object result = command.executeExtract(value);
        Number returnVal = null;
        if (Array.getLength(result) >= 1) {
            if (Array.get(result, 0) instanceof String || Array.get(result, 0) instanceof Boolean) {
                throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, "this command must return a java.lang.Number");
            }
            returnVal = (Number) Array.get(result, 0);
        }
        return returnVal;
    }

    /**
     * Execute a command with argin which is a single value
     *
     * @param <T>
     * @param clazz
     * @param value
     * @return
     * @throws DevFailed
     */
    public <T> List<T> executeExtractList(final Class<T> clazz, final Object value) throws DevFailed {
        final Object r = command.executeExtract(value);
        return extractList(TypeConversionUtil.castToType(clazz, r));
    }

    /**
     * Execute a command with argin which is an array
     *
     * @param <T>
     * @param clazz
     * @param value
     * @return
     * @throws DevFailed
     */
    public <T> List<T> executeExtractList(final Class<T> clazz, final Object... value) throws DevFailed {
        final Object result = command.executeExtract(value);
        return extractList(TypeConversionUtil.castToArray(clazz, result));
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> extractList(final Object result) throws DevFailed {
        final List<T> converted = new ArrayList<T>();
        if (isArgoutScalar()) {
            converted.add((T) result);
        } else if (!isArgoutVoid()) {
            for (int i = 0; i < Array.getLength(result); i++) {
                final T val = (T) Array.get(result, i);
                converted.add(val);
            }
        }
        return converted;
    }

    private Number[] extractAsNumberArray(final Object result) throws DevFailed {
        Number[] returned = null;
        if (Array.getLength(result) >= 1) {
            if (Array.get(result, 0) instanceof String || Array.get(result, 0) instanceof Boolean) {
                throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, "this command must return a java.lang.Number");
            }
            returned = new Number[Array.getLength(result)];
            for (int i = 0; i < returned.length; i++) {
                returned[i] = (Number) Array.get(result, i);
            }
        }
        return returned;
    }

    public void insertMixArgin(final String[] numberArgin, final String[] stringArgin) throws DevFailed {
        command.insertMixArgin(numberArgin, stringArgin);
    }

    public void insertMixArgin(final double[] numberArgin, final String[] stringArgin) throws DevFailed {
        command.insertMixArgin(numberArgin, stringArgin);
    }

    public void insertMixArgin(final int[] numberArgin, final String[] stringArgin) throws DevFailed {
        command.insertMixArgin(numberArgin, stringArgin);
    }

    public String extractToString(final String separator) throws DevFailed {
        return command.extractToString(separator);
    }

    /**
     * Get Num part of DEVVASXSTRINGARRAY and convert to string
     *
     * @return
     * @throws DevFailed
     *             3 juin 2005
     */
    public String[] getNumMixArrayArgout() throws DevFailed {
        return command.getNumMixArrayArgout();
    }

    public String[] getStringMixArrayArgout() throws DevFailed {
        return command.getStringMixArrayArgout();
    }

    public double[] getNumDoubleMixArrayArgout() throws DevFailed {
        return command.getNumDoubleMixArrayArgout();
    }

    public int[] getNumLongMixArrayArgout() throws DevFailed {
        return command.getNumLongMixArrayArgout();
    }

    public boolean isArginScalar() throws DevFailed {
        return command.isArginScalar();
    }

    public boolean isArginSpectrum() throws DevFailed {
        return command.isArginSpectrum();
    }

    public boolean isArginVoid() throws DevFailed {
        return command.isArginVoid();
    }

    public boolean isArgoutVoid() throws DevFailed {
        return command.isArgoutVoid();
    }

    public boolean isArgoutSpectrum() throws DevFailed {
        return command.isArgoutSpectrum();
    }

    public boolean isArginMixFormat() throws DevFailed {
        return command.isArginMixFormat();
    }

    public boolean isArgoutScalar() throws DevFailed {
        return command.isArgoutScalar();
    }

    public boolean isArgoutMixFormat() throws DevFailed {
        return command.isArgoutMixFormat();
    }

    public DeviceProxy getDeviceProxy() {
        return command.getDeviceProxy();
    }

    @Override
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this);
        str.append("name", command.getCommandName());
        try {
            str.append("argin type", TangoConst.Tango_CmdArgTypeName[command.getArginType()]);
        } catch (final DevFailed e) {
            str.append("argin type", "UNKNOWN");
        }
        try {
            str.append("argout type", TangoConst.Tango_CmdArgTypeName[command.getArgoutType()]);
        } catch (final DevFailed e) {
            str.append("argout type", "UNKNOWN");
        }
        return str.toString();
    }

    public int getArginType() throws DevFailed {
        return command.getArginType();
    }

    public int getArgoutType() throws DevFailed {
        return command.getArgoutType();
    }

    public void setTimeout(final int timeout) throws DevFailed {
        command.setTimeout(timeout);
    }
}
