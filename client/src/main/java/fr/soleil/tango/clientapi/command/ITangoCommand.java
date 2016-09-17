package fr.soleil.tango.clientapi.command;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;

public interface ITangoCommand {

    String getCommandName();

    void execute() throws DevFailed;

    void execute(final Object value) throws DevFailed;

    Object executeExtract(final Object value) throws DevFailed;

    Object executeExtract() throws DevFailed;

    void insertMixArgin(final String[] numberArgin, final String[] stringArgin) throws DevFailed;

    void insertMixArgin(final double[] numberArgin, final String[] stringArgin) throws DevFailed;

    void insertMixArgin(final int[] numberArgin, final String[] stringArgin) throws DevFailed;

    String extractToString(final String separator) throws DevFailed;

    String[] getNumMixArrayArgout() throws DevFailed;

    String[] getStringMixArrayArgout() throws DevFailed;

    double[] getNumDoubleMixArrayArgout() throws DevFailed;

    int[] getNumLongMixArrayArgout() throws DevFailed;

    boolean isArginScalar() throws DevFailed;

    boolean isArginSpectrum() throws DevFailed;

    boolean isArginVoid() throws DevFailed;

    boolean isArgoutVoid() throws DevFailed;

    boolean isArgoutSpectrum() throws DevFailed;

    boolean isArginMixFormat() throws DevFailed;

    boolean isArgoutScalar() throws DevFailed;

    boolean isArgoutMixFormat() throws DevFailed;

    DeviceProxy getDeviceProxy();

    int getArginType() throws DevFailed;

    int getArgoutType() throws DevFailed;

    void setTimeout(final int timeout) throws DevFailed;
}
