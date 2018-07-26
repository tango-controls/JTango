package fr.soleil.tango.clientapi.command;

import java.util.Arrays;

import org.tango.utils.DevFailedUtils;
import org.tango.utils.TangoUtil;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.tango.clientapi.InsertExtractUtils;
import fr.soleil.tango.clientapi.Properties;
import fr.soleil.tango.clientapi.factory.ProxyFactory;
import fr.soleil.tango.clientapi.util.TypeConversionUtil;
import fr.soleil.tango.errorstrategy.RetriableTask;
import fr.soleil.tango.errorstrategy.Task;

public final class RealCommand implements ITangoCommand {

    private static final String DESC = "input type not supported";
    private static final String TANGO_WRONG_DATA_ERROR = "TANGO_WRONG_DATA_ERROR";
    private DeviceProxy devProxy;
    private final String commandName;
    private final DeviceData inData = new DeviceData();
    private DeviceData outData;
    private int arginType;
    private int argoutType;
    private final int delay;
    private final int retries;

    /**
     *
     * @param deviceName
     *            The device tango (e.g. domain/family/member)
     * @param commandName
     *            The command name
     * @throws DevFailed
     */
    public RealCommand(final String deviceName, final String commandName) throws DevFailed {
        delay = Properties.getDelay();
        retries = Properties.getRetries();
        this.commandName = commandName;
        final Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws DevFailed {
                devProxy = ProxyFactory.getInstance().createDeviceProxy(deviceName);
                arginType = devProxy.command_query(commandName).in_type;
                argoutType = devProxy.command_query(commandName).out_type;
                return null;
            }
        };
        final RetriableTask<Void> retriable = new RetriableTask<Void>(retries, delay);
        retriable.execute(task);
    }

    /**
     *
     * @param commandName
     *            The device and command name in 4 fields (e.g. domain/family/member/commandName)
     * @throws DevFailed
     */
    public RealCommand(final String commandName) throws DevFailed {
        this(commandName.substring(0, commandName.lastIndexOf(TangoUtil.DEVICE_SEPARATOR)), commandName
                .substring(commandName.lastIndexOf(TangoUtil.DEVICE_SEPARATOR) + 1));
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public void execute() throws DevFailed {
        final Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws DevFailed {
                if (isArginVoid()) {
                    outData = devProxy.command_inout(commandName);
                } else {
                    outData = devProxy.command_inout(commandName, inData);
                }
                return null;
            }
        };

        final RetriableTask<Void> retriable = new RetriableTask<Void>(retries, delay);
        retriable.execute(task);
    }

    @Override
    public void execute(final Object value) throws DevFailed {
        insert(value);
        execute();

    }

    @Override
    public Object executeExtract(final Object value) throws DevFailed {
        insert(value);
        execute();
        return extract();
    }

    @Override
    public Object executeExtract() throws DevFailed {
        execute();
        return extract();
    }

    @Override
    public void insertMixArgin(final String[] numberArgin, final String[] stringArgin) throws DevFailed {
        if (arginType == TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY) {
            final double[] d = TypeConversionUtil.castToType(double[].class, numberArgin);
            final DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray(d, stringArgin);
            inData.insert(dvdsa);
        } else if (arginType == TangoConst.Tango_DEVVAR_LONGSTRINGARRAY) {
            final int[] in = TypeConversionUtil.castToType(int[].class, numberArgin);
            final DevVarLongStringArray dvlsa = new DevVarLongStringArray(in, stringArgin);
            inData.insert(dvlsa);
        } else {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
        }
    }

    @Override
    public void insertMixArgin(final double[] numberArgin, final String[] stringArgin) throws DevFailed {
        if (arginType == TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY) {
            final DevVarDoubleStringArray dvdsa = new DevVarDoubleStringArray(numberArgin, stringArgin);
            inData.insert(dvdsa);
        } else if (arginType == TangoConst.Tango_DEVVAR_LONGSTRINGARRAY) {
            final int[] in = TypeConversionUtil.castToType(int[].class, numberArgin);
            final DevVarLongStringArray dvlsa = new DevVarLongStringArray(in, stringArgin);
            inData.insert(dvlsa);
        } else {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
        }
    }

    @Override
    public void insertMixArgin(final int[] numberArgin, final String[] stringArgin) throws DevFailed {
        if (arginType == TangoConst.Tango_DEVVAR_LONGSTRINGARRAY) {
            final int[] in = TypeConversionUtil.castToType(int[].class, numberArgin);
            final DevVarLongStringArray dvlsa = new DevVarLongStringArray(in, stringArgin);
            inData.insert(dvlsa);
        } else {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
        }
    }

    @Override
    public String extractToString(final String separator) throws DevFailed {
        String argout = "";
        if (isArgoutScalar()) {
            argout = InsertExtractUtils.extract(outData, String.class);
        } else if (!isArgoutVoid()) {
            final String[] result = InsertExtractUtils.extract(outData, String[].class);
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
        if (argoutType == TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY) {
            final DevVarDoubleStringArray dvdsa = outData.extractDoubleStringArray();
            argout = TypeConversionUtil.castToType(String[].class, dvdsa.dvalue);
        } else if (argoutType == TangoConst.Tango_DEVVAR_LONGSTRINGARRAY) {
            final DevVarLongStringArray dvlsa = outData.extractLongStringArray();
            argout = TypeConversionUtil.castToType(String[].class, dvlsa.lvalue);
        } else {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
        }
        return argout;
    }

    @Override
    public String[] getStringMixArrayArgout() throws DevFailed {
        final String[] argout;
        if (argoutType == TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY) {
            final DevVarDoubleStringArray dvdsa = outData.extractDoubleStringArray();
            argout = Arrays.copyOf(dvdsa.svalue, dvdsa.svalue.length);
        } else if (argoutType == TangoConst.Tango_DEVVAR_LONGSTRINGARRAY) {
            final DevVarLongStringArray dvlsa = outData.extractLongStringArray();
            argout = Arrays.copyOf(dvlsa.svalue, dvlsa.svalue.length);
        } else {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
        }
        return argout;
    }

    @Override
    public double[] getNumDoubleMixArrayArgout() throws DevFailed {
        final double[] argout;
        if (argoutType == TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY) {
            final DevVarDoubleStringArray dvdsa = outData.extractDoubleStringArray();
            argout = Arrays.copyOf(dvdsa.dvalue, dvdsa.dvalue.length);
        } else if (argoutType == TangoConst.Tango_DEVVAR_LONGSTRINGARRAY) {
            final DevVarLongStringArray dvlsa = outData.extractLongStringArray();
            argout = TypeConversionUtil.castToType(double[].class, dvlsa.lvalue);
        } else {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
        }
        return argout;
    }

    @Override
    public int[] getNumLongMixArrayArgout() throws DevFailed {
        final int[] argout;
        if (argoutType == TangoConst.Tango_DEVVAR_LONGSTRINGARRAY) {
            final DevVarLongStringArray dvlsa = outData.extractLongStringArray();
            argout = Arrays.copyOf(dvlsa.lvalue, dvlsa.lvalue.length);
        } else {
            throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, DESC);
        }
        return argout;
    }

    @Override
    public boolean isArginScalar() throws DevFailed {
        return TangoUtil.SCALARS.contains(arginType);
    }

    @Override
    public boolean isArginSpectrum() throws DevFailed {
        return TangoUtil.SPECTRUMS.contains(arginType);
    }

    @Override
    public boolean isArginVoid() throws DevFailed {
        return arginType == TangoConst.Tango_DEV_VOID;
    }

    @Override
    public boolean isArgoutVoid() throws DevFailed {
        return argoutType == TangoConst.Tango_DEV_VOID;
    }

    @Override
    public boolean isArgoutSpectrum() throws DevFailed {
        return TangoUtil.SPECTRUMS.contains(argoutType);
    }

    @Override
    public boolean isArginMixFormat() throws DevFailed {
        return arginType == TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY
                || arginType == TangoConst.Tango_DEVVAR_LONGSTRINGARRAY;
    }

    @Override
    public boolean isArgoutScalar() throws DevFailed {
        return TangoUtil.SCALARS.contains(argoutType);
    }

    @Override
    public boolean isArgoutMixFormat() throws DevFailed {
        return argoutType == TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY
                || argoutType == TangoConst.Tango_DEVVAR_LONGSTRINGARRAY;
    }

    @Override
    public DeviceProxy getDeviceProxy() {
        return devProxy;
    }

    @Override
    public int getArginType() throws DevFailed {
        return arginType;
    }

    @Override
    public int getArgoutType() throws DevFailed {
        return argoutType;
    }

    private void insert(final Object value) throws DevFailed {
        InsertExtractUtils.insert(inData, arginType, value);
    }

    private Object extract() throws DevFailed {
        return InsertExtractUtils.extract(outData);
    }

    @Override
    public void setTimeout(final int timeout) throws DevFailed {
        devProxy.set_timeout_millis(timeout);
    }

}
