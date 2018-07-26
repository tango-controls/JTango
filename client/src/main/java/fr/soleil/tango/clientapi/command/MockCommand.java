package fr.soleil.tango.clientapi.command;

import java.util.Arrays;

import org.tango.command.CommandTangoType;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.tango.clientapi.util.TypeConversionUtil;

public final class MockCommand implements ITangoCommand {

    private static final String DESC = "input type not supported";
    private static final String TANGO_WRONG_DATA_ERROR = "TANGO_WRONG_DATA_ERROR";

    private Object returnMockValue;
    private final Class<? extends Object> parameterMockType;
    private final String commandName;
    private final Class<? extends Object> returnMockType;
    private boolean returnInput;

    public MockCommand(final String commandName, final Object parameterMockValue, final Object returnMockValue) {
        this.returnMockValue = returnMockValue;
        parameterMockType = parameterMockValue.getClass();
        returnMockType = returnMockValue.getClass();
        if (parameterMockType.equals(returnMockType)) {
            returnInput = true;
        } else {
            returnInput = false;
        }
        this.commandName = commandName;
    }

    public MockCommand(final String commandName) {
        parameterMockType = void.class;
        returnMockType = void.class;
        returnInput = false;
        this.commandName = commandName;
    }

    public MockCommand(final boolean isParameterVoid, final String commandName, final Object mockValue) {
        if (isParameterVoid) {
            parameterMockType = void.class;
            returnMockType = mockValue.getClass();
            returnMockValue = mockValue;
            returnInput = true;
        } else {
            returnMockType = void.class;
            parameterMockType = mockValue.getClass();
            returnInput = false;
        }
        this.commandName = commandName;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public void execute() throws DevFailed {
    }

    @Override
    public void execute(final Object value) throws DevFailed {
        if (returnInput) {
            returnMockValue = TypeConversionUtil.castToType(returnMockType, value);
        }
    }

    @Override
    public Object executeExtract(final Object value) throws DevFailed {
        if (returnInput) {
            returnMockValue = TypeConversionUtil.castToType(returnMockType, value);
        }
        return returnMockValue;
    }

    @Override
    public Object executeExtract() throws DevFailed {
        return returnMockValue;
    }

    @Override
    public void insertMixArgin(final String[] numberArgin, final String[] stringArgin) throws DevFailed {
        if (returnInput) {
            if (DevVarDoubleStringArray.class.isAssignableFrom(parameterMockType)) {
                final double[] d = TypeConversionUtil.castToType(double[].class, numberArgin);
                returnMockValue = new DevVarDoubleStringArray(d, stringArgin);
            } else if (DevVarLongStringArray.class.isAssignableFrom(parameterMockType)) {
                final int[] in = TypeConversionUtil.castToType(int[].class, numberArgin);
                returnMockValue = new DevVarLongStringArray(in, stringArgin);
            } else {
                throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
            }
        }
    }

    @Override
    public void insertMixArgin(final double[] numberArgin, final String[] stringArgin) throws DevFailed {
        if (returnInput) {
            if (DevVarDoubleStringArray.class.isAssignableFrom(parameterMockType)) {
                returnMockValue = new DevVarDoubleStringArray(Arrays.copyOf(numberArgin, numberArgin.length),
                        Arrays.copyOf(stringArgin, stringArgin.length));
            } else if (DevVarLongStringArray.class.isAssignableFrom(parameterMockType)) {
                final int[] in = TypeConversionUtil.castToType(int[].class, numberArgin);
                returnMockValue = new DevVarLongStringArray(in, Arrays.copyOf(stringArgin, stringArgin.length));
            } else {
                throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
            }
        }
    }

    @Override
    public void insertMixArgin(final int[] numberArgin, final String[] stringArgin) throws DevFailed {
        if (returnInput) {
            if (DevVarDoubleStringArray.class.isAssignableFrom(parameterMockType)) {
                final double[] d = TypeConversionUtil.castToType(double[].class, numberArgin);
                returnMockValue = new DevVarDoubleStringArray(d, Arrays.copyOf(stringArgin, stringArgin.length));
            } else if (DevVarLongStringArray.class.isAssignableFrom(parameterMockType)) {
                returnMockValue = new DevVarLongStringArray(Arrays.copyOf(numberArgin, numberArgin.length),
                        Arrays.copyOf(stringArgin, stringArgin.length));
            } else {
                throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
            }
        }
    }

    @Override
    public String extractToString(final String separator) throws DevFailed {
        String argout = "";
        if (isArgoutScalar()) {
            argout = returnMockValue.toString();
        } else if (!isArgoutVoid()) {
            final String[] result = TypeConversionUtil.castToType(String[].class, returnMockValue);
            final StringBuffer buff = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                buff.append(result[i]);
                if (i != result.length - 1) {
                    buff.append(separator);
                }
            }
            argout = buff.toString();
        }
        return argout;
    }

    @Override
    public String[] getNumMixArrayArgout() throws DevFailed {
        final String[] argout;
        if (DevVarDoubleStringArray.class.isAssignableFrom(returnMockType)) {
            final DevVarDoubleStringArray dvdsa = (DevVarDoubleStringArray) returnMockValue;
            argout = TypeConversionUtil.castToType(String[].class, dvdsa.dvalue);
        } else if (DevVarLongStringArray.class.isAssignableFrom(returnMockType)) {
            final DevVarLongStringArray dvlsa = (DevVarLongStringArray) returnMockValue;
            argout = TypeConversionUtil.castToType(String[].class, dvlsa.lvalue);
        } else {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
        }
        return argout;
    }

    @Override
    public String[] getStringMixArrayArgout() throws DevFailed {
        final String[] argout;
        if (DevVarDoubleStringArray.class.isAssignableFrom(returnMockType)) {
            final DevVarDoubleStringArray dvdsa = (DevVarDoubleStringArray) returnMockValue;
            argout = Arrays.copyOf(dvdsa.svalue, dvdsa.svalue.length);
        } else if (DevVarLongStringArray.class.isAssignableFrom(returnMockType)) {
            final DevVarLongStringArray dvlsa = (DevVarLongStringArray) returnMockValue;
            argout = Arrays.copyOf(dvlsa.svalue, dvlsa.svalue.length);
        } else {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
        }
        return argout;
    }

    @Override
    public double[] getNumDoubleMixArrayArgout() throws DevFailed {
        final double[] argout;
        if (DevVarDoubleStringArray.class.isAssignableFrom(returnMockType)) {
            final DevVarDoubleStringArray dvdsa = (DevVarDoubleStringArray) returnMockValue;
            argout = Arrays.copyOf(dvdsa.dvalue, dvdsa.dvalue.length);
        } else if (DevVarLongStringArray.class.isAssignableFrom(returnMockType)) {
            final DevVarLongStringArray dvlsa = (DevVarLongStringArray) returnMockValue;
            argout = TypeConversionUtil.castToType(double[].class, dvlsa.lvalue);
        } else {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
        }
        return argout;
    }

    @Override
    public int[] getNumLongMixArrayArgout() throws DevFailed {
        final int[] argout;
        if (DevVarLongStringArray.class.isAssignableFrom(returnMockType)) {
            final DevVarLongStringArray dvlsa = (DevVarLongStringArray) returnMockValue;
            argout = Arrays.copyOf(dvlsa.lvalue, dvlsa.lvalue.length);
        } else {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
        }
        return argout;
    }

    @Override
    public boolean isArginScalar() throws DevFailed {
        return !parameterMockType.isArray();
    }

    @Override
    public boolean isArginSpectrum() throws DevFailed {
        return parameterMockType.isArray() && !isArginVoid();
    }

    @Override
    public boolean isArginVoid() throws DevFailed {
        return void.class.isAssignableFrom(parameterMockType);
    }

    @Override
    public boolean isArgoutVoid() throws DevFailed {
        return void.class.isAssignableFrom(returnMockType);
    }

    @Override
    public boolean isArgoutSpectrum() throws DevFailed {
        return returnMockType.isArray();
    }

    @Override
    public boolean isArginMixFormat() throws DevFailed {
        return DevVarDoubleStringArray.class.isAssignableFrom(parameterMockType)
                || DevVarLongStringArray.class.isAssignableFrom(parameterMockType);
    }

    @Override
    public boolean isArgoutScalar() throws DevFailed {
        return !returnMockType.isArray() && !isArgoutVoid();
    }

    @Override
    public boolean isArgoutMixFormat() throws DevFailed {
        return DevVarDoubleStringArray.class.isAssignableFrom(returnMockType)
                || DevVarLongStringArray.class.isAssignableFrom(returnMockType);
    }

    @Override
    public DeviceProxy getDeviceProxy() {
        return null;
    }

    @Override
    public int getArginType() throws DevFailed {
        return CommandTangoType.getTypeFromClass(parameterMockType).getTangoIDLType();
    }

    @Override
    public int getArgoutType() throws DevFailed {
        return CommandTangoType.getTypeFromClass(returnMockType).getTangoIDLType();
    }

    @Override
    public void setTimeout(final int timeout) {
    }

}
