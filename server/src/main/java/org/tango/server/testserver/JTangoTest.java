/**
 * Copyright (C) :     2012
 * <p>
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
 * <p>
 * This file is part of Tango.
 * <p>
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.testserver;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarLongStringArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.DeviceState;
import org.tango.server.ServerManager;
import org.tango.server.annotation.AroundInvoke;
import org.tango.server.annotation.Attribute;
import org.tango.server.annotation.AttributeProperties;
import org.tango.server.annotation.ClassProperty;
import org.tango.server.annotation.Command;
import org.tango.server.annotation.Delete;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.DeviceManagement;
import org.tango.server.annotation.DeviceProperties;
import org.tango.server.annotation.DeviceProperty;
import org.tango.server.annotation.DynamicManagement;
import org.tango.server.annotation.Init;
import org.tango.server.annotation.Schedule;
import org.tango.server.annotation.State;
import org.tango.server.annotation.StateMachine;
import org.tango.server.annotation.Status;
import org.tango.server.annotation.TransactionType;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.device.DeviceManager;
import org.tango.server.dynamic.DynamicManager;
import org.tango.utils.ArrayUtils;
import org.tango.utils.DevFailedUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Tango device to test all commands and attributes
 *
 * @author FOURNEAU
 *
 */
@Device(transactionType = TransactionType.NONE)
public final class JTangoTest {
    public static final String NO_DB_DEVICE_NAME = "1/1/1";
    // public static final String NO_DB_GIOP_PORT = "12354";
    public static final String INSTANCE_NAME = "1";
    public static final String SERVER_NAME = JTangoTest.class.getSimpleName();
    /*
     * Array size constant
     */
    // Spectrum
    private static final int SPECTRUM_SIZE = 100000;
    // Image
    private static final int X_IMAGE_SIZE = 200;
    private static final int Y_IMAGE_SIZE = 500;
    private final Logger logger = LoggerFactory.getLogger(JTangoTest.class);
    /*
     * ATTRIBUTE
     */
    @Attribute
    @AttributeProperties(deltaTime = "1", deltaValue = "5")
    private final double deltaAttribute = 0;
    @Attribute
    @AttributeProperties(minAlarm = "2")
    private final double invalidQuality = 0;
    @Attribute
    @AttributeProperties(minAlarm = "2")
    private final double invalidQuality2 = 0;
    @State
    private DevState state;
    @Status
    private String status;
    /*
     * PROPERTY
     */
    @DeviceProperties
    private Map<String, String[]> props = new HashMap<String, String[]>();
    @DeviceProperty(defaultValue = "default")
    private String myProp = "toto";
    @DeviceProperty
    private boolean booleanProp = false;
    @ClassProperty(defaultValue = "classDefault")
    private String[] myClassProp = {"test0"};
    private short shortScalar = 0;
    @DeviceProperty
    private String[] emptyArrayProperty;
    @Attribute
    private short[] shortSpectrum = {};
    @Attribute
    private short[][] shortImage = {};
    @Attribute
    private int intScalar = 0;
    @Attribute
    private int[] intSpectrum = {};
    @Attribute
    private int[][] intImage = {};
    @Attribute
    @AttributeProperties(maxAlarm = "10", description = "toto")
    private long longScalar = 0L;
    @Attribute
    @AttributeProperties(minAlarm = "0", maxAlarm = "10", minValue = "-100", maxValue = "1015054014654325L", minWarning = "3", maxWarning = "4", description = "test", deltaTime = "10", deltaValue = "20")
    private long[] longSpectrum = {1, 2};
    @Attribute
    private long[][] longImage = {};
    @Attribute
    private float floatScalar = 0F;
    @Attribute
    private float[] floatSpectrum = {};
    @Attribute
    private float[][] floatImage = {{10.2F, 10.2140F}, {20.01210F, 20.0F}};
    @Attribute
    @AttributeProperties(minAlarm = "0", maxAlarm = "10", minValue = "-100", maxValue = "1002501.125D", deltaTime = "10", deltaValue = "20", minWarning = "3", maxWarning = "4", description = "test")
    private double doubleScalar = 0.0;
    @Attribute
    private double[] doubleSpectrum = {};
    @Attribute
    private double[][] doubleImage = {};
    private byte byteScalar = 1;
    @Attribute
    private byte[] byteSpectrum = {};
    @Attribute
    private byte[][] byteImage = {};
    @Attribute
    private boolean booleanScalar = false;
    @Attribute
    private boolean[] booleanSpectrum = {};
    @Attribute
    private boolean[][] booleanImage = {};
    @Attribute
    private String stringScalar = "totoScal";
    @Attribute
    private String[] stringSpectrum = {};
    @Attribute
    private String[][] stringImage = {};
    @Attribute
    @StateMachine(deniedStates = {DeviceState.OFF})
    private DeviceState stateScalar = DeviceState.ON;
    @Attribute
    private DeviceState[] stateSpectrum = new DeviceState[]{DeviceState.ON, DeviceState.OFF};
    @Attribute
    private DevEncoded devEncodedScalar = new DevEncoded("yfui", new byte[]{1, 2, 3});
    // @Attribute
    // private DevEncoded[] devEncodedSpectrum = new DevEncoded[] { new
    // DevEncoded("yfui", new byte[] { 1, 2, 3 }) };
    //
    // @Attribute
    // private final DevEncoded[][] devEncodedImage = new DevEncoded[][] { { new
    // DevEncoded("yfui", new byte[] { 1, 2, 3 }) } };
    @Attribute
    private TestType enumAttribute = TestType.VALUE1;
    @DynamicManagement
    private DynamicManager dynamicManager;
    @DeviceManagement
    private DeviceManager deviceManager;
    private boolean error = false;
    private boolean error2 = false;
    private volatile boolean isScheduleRunning;

    /**
     * Start a device with tango database. The server must be declared in tango
     * db.
     *
     * @throws DevFailed
     */
    public static void start() throws DevFailed {
        ServerManager.getInstance().addClass(JTangoTest.class.getCanonicalName(), JTangoTest.class);
        ServerManager.getInstance().startError(new String[]{INSTANCE_NAME}, SERVER_NAME);
    }

    public static void startNoDbFile(final int portNr) throws DevFailed {
        System.setProperty("OAPort", Integer.toString(portNr));
        ServerManager.getInstance().addClass(JTangoTest.class.getCanonicalName(), JTangoTest.class);
        ServerManager.getInstance().startError(
                new String[]{INSTANCE_NAME, "-nodb", "-dlist", NO_DB_DEVICE_NAME,
                        "-file=" + JTangoTest.class.getResource("/noDbproperties.txt").getPath()}, SERVER_NAME);
    }

    public static void startNoDb(final int portNr) throws DevFailed {
        System.setProperty("OAPort", Integer.toString(portNr));
        ServerManager.getInstance().addClass(JTangoTest.class.getCanonicalName(), JTangoTest.class);
        ServerManager.getInstance().startError(new String[]{INSTANCE_NAME, "-nodb", "-dlist", NO_DB_DEVICE_NAME},
                SERVER_NAME);
    }

    public static void main(final String[] args) {
        if (args.length > 1 && args[1].equals("NODB")) {
            try {
                startNoDb(Integer.valueOf(args[2]));
            } catch (final NumberFormatException e) {
                e.printStackTrace();
            } catch (final DevFailed e) {
                e.printStackTrace();
            }
        } else {
            // System.setProperty("TANGO_HOST", "tango9-db1.ica.synchrotron-soleil.fr:20001");
            ServerManager.getInstance().addClass(JTangoTest.class.getCanonicalName(), JTangoTest.class);
            ServerManager.getInstance().start(new String[]{"1"}, SERVER_NAME);
        }
    }

    @Command
    public String[] getEmptyArrayProperty() {
        return emptyArrayProperty;
    }

    public void setEmptyArrayProperty(String[] emptyArrayProperty) {
        this.emptyArrayProperty = emptyArrayProperty;
    }

    @Schedule(activationProperty = "isRunRefresh", cronExpression = "0/1 * * * * ?")
    public void refresh() {
        isScheduleRunning = true;
    }

    @Attribute
    public boolean isScheduleRunning() {
        return isScheduleRunning;
    }

    /**
     * Automatically called when device starts
     */
    @Init
    @StateMachine(endState = DeviceState.ON)
    public void init() throws DevFailed {
        isScheduleRunning = false;
        setDefaultValues();

        shortScalar = 10;

        createDynamicAttributes();

        createDynamicCommands();
        logger.debug("props {}", props);
        status = "hello";
    }

    // SHORT

    @Command
    public String getName() {
        return deviceManager.getName();
    }

    private void setDefaultValues() {
        shortSpectrum = new short[SPECTRUM_SIZE];
        Arrays.fill(shortSpectrum, (short) 1);
        intSpectrum = new int[SPECTRUM_SIZE];
        Arrays.fill(intSpectrum, 0);
        byteSpectrum = new byte[SPECTRUM_SIZE];
        Arrays.fill(byteSpectrum, (byte) 0);
        doubleSpectrum = new double[SPECTRUM_SIZE];
        Arrays.fill(doubleSpectrum, 0);
        floatSpectrum = new float[SPECTRUM_SIZE];
        Arrays.fill(floatSpectrum, 0);
        booleanSpectrum = new boolean[SPECTRUM_SIZE];
        Arrays.fill(booleanSpectrum, true);
        stringSpectrum = new String[SPECTRUM_SIZE];
        Arrays.fill(stringSpectrum, "");

        shortImage = new short[X_IMAGE_SIZE][Y_IMAGE_SIZE];
        for (final short[] element : shortImage) {
            Arrays.fill(element, (short) 0);
        }

        intImage = new int[X_IMAGE_SIZE][Y_IMAGE_SIZE];
        for (final int[] element : intImage) {
            Arrays.fill(element, 0);
        }
        byteImage = new byte[X_IMAGE_SIZE][Y_IMAGE_SIZE];
        for (final byte[] element : byteImage) {
            Arrays.fill(element, (byte) 0);
        }
        doubleImage = new double[X_IMAGE_SIZE][Y_IMAGE_SIZE];
        for (final double[] element : doubleImage) {
            Arrays.fill(element, 0);
        }
        floatImage = new float[X_IMAGE_SIZE][Y_IMAGE_SIZE];
        for (final float[] element : floatImage) {
            Arrays.fill(element, 0);
        }
        booleanImage = new boolean[X_IMAGE_SIZE][Y_IMAGE_SIZE];
        for (final boolean[] element : booleanImage) {
            Arrays.fill(element, true);
        }
        stringImage = new String[X_IMAGE_SIZE][Y_IMAGE_SIZE];
        for (final String[] element : stringImage) {
            Arrays.fill(element, "");
        }
    }

    /**
     * @return shortSpectrum attribute
     */
    // public short[] getShortSpectrum() {
    // return Arrays.copyOf(shortSpectrum, shortSpectrum.length);
    // }
    private void createDynamicCommands() throws DevFailed {
        dynamicManager.addCommand(new DynamicCommandTest(String.class));
        dynamicManager.addCommand(new DynamicCommandTest(String[].class));
        dynamicManager.addCommand(new DynamicCommandTest(double.class));
        dynamicManager.addCommand(new DynamicCommandTest(double[].class));
        dynamicManager.addCommand(new DynamicCommandTest(int.class));
        dynamicManager.addCommand(new DynamicCommandTest(int[].class));
        dynamicManager.addCommand(new DynamicCommandTest(float.class));
        dynamicManager.addCommand(new DynamicCommandTest(float[].class));
        dynamicManager.addCommand(new DynamicCommandTest(short.class));
        dynamicManager.addCommand(new DynamicCommandTest(short[].class));
        dynamicManager.addCommand(new DynamicCommandTest(long.class));
        dynamicManager.addCommand(new DynamicCommandTest(long[].class));
        dynamicManager.addCommand(new DynamicCommandTest(byte.class));
        dynamicManager.addCommand(new DynamicCommandTest(byte[].class));
        dynamicManager.addCommand(new DynamicCommandTest(boolean.class));
        dynamicManager.addCommand(new DynamicCommandTest(DevVarDoubleStringArray.class));
        dynamicManager.addCommand(new DynamicCommandTest(DevVarLongStringArray.class));
        // dynManager.addCommand(new DynamicAttributeTest(boolean[].class));
    }

    private void createDynamicAttributes() throws DevFailed {
        dynamicManager.addAttribute(new DynamicAttributeTest(String.class));
        dynamicManager.addAttribute(new DynamicAttributeTest(String[].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(String[][].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(double.class));
        dynamicManager.addAttribute(new DynamicAttributeTest(double[].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(double[][].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(int.class));
        dynamicManager.addAttribute(new DynamicAttributeTest(int[].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(int[][].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(float.class));
        dynamicManager.addAttribute(new DynamicAttributeTest(float[].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(float[][].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(short.class));
        dynamicManager.addAttribute(new DynamicAttributeTest(short[].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(short[][].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(long.class));
        dynamicManager.addAttribute(new DynamicAttributeTest(long[].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(long[][].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(byte.class));
        dynamicManager.addAttribute(new DynamicAttributeTest(byte[].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(byte[][].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(boolean.class));
        dynamicManager.addAttribute(new DynamicAttributeTest(boolean[].class));
        dynamicManager.addAttribute(new DynamicAttributeTest(boolean[][].class));
        dynamicManager.addAttribute(new DynamicEnumAttribute());
        dynamicManager.addAttribute(new DynamicAttributeTest(double.class, "testfowarded"));
    }

    /**
     * Automatically called when device ends
     *
     * @throws DevFailed
     */
    @Delete
    public void delete() throws DevFailed {
        dynamicManager.clearAll();
    }

    // INTEGER

    /**
     *
     * @return shortScalar attribute
     */
    @Attribute
    public short getShortScalar() {
        return shortScalar;
    }

    /**
     *
     * @param shortScalar
     *            scalar
     */

    public void setShortScalar(final short shortScalar) {
        this.shortScalar = shortScalar;
    }

    /**
     *
     * @param shortSpectrum
     *            spectrum
     */
    public void setShortSpectrum(final short[] shortSpectrum) {
        this.shortSpectrum = new short[shortSpectrum.length];
        System.arraycopy(shortSpectrum, 0, this.shortSpectrum, 0, shortSpectrum.length);
    }

    /**
     *
     * @return Image of short
     */
    public short[][] getShortImage() {
        return Arrays.copyOf(shortImage, shortImage.length);
    }

    /**
     *
     * @param shortImage
     *            image
     */
    public void setShortImage(final short[][] shortImage) {
        this.shortImage = ArrayUtils.copyOf(shortImage);
    }

    public int getIntScalar() {
        return intScalar;
    }

    // LONG

    public void setIntScalar(final int intScalar) {
        this.intScalar = intScalar;
    }

    public int[] getIntSpectrum() {
        return Arrays.copyOf(intSpectrum, intSpectrum.length);
    }

    public void setIntSpectrum(final int[] intSpectrum) {
        this.intSpectrum = new int[intSpectrum.length];
        System.arraycopy(intSpectrum, 0, this.intSpectrum, 0, intSpectrum.length);
    }

    public int[][] getIntImage() {
        return ArrayUtils.copyOf(intImage);
    }

    public void setIntImage(final int[][] intImage) {
        this.intImage = ArrayUtils.copyOf(intImage);
    }

    public long getLongScalar() {
        return longScalar;
    }

    public void setLongScalar(final long longScalar) {
        this.longScalar = longScalar;
    }

    public long[] getLongSpectrum() {
        return Arrays.copyOf(longSpectrum, longSpectrum.length);
    }

    // FLOAT

    public void setLongSpectrum(final long[] longSpectrum) {
        this.longSpectrum = new long[longSpectrum.length];
        System.arraycopy(longSpectrum, 0, this.longSpectrum, 0, longSpectrum.length);
    }

    @Attribute
    public long[] getPollSpectrum() throws DevFailed {
        error2 = !error2;
        if (error2) {
            throw DevFailedUtils.newDevFailed("error pollSpectrum");
        }

        return new long[]{1, 2};
    }

    public void setPollSpectrum(final long[] value) {

    }

    public long[][] getLongImage() {
        return ArrayUtils.copyOf(longImage);
    }

    public void setLongImage(final long[][] longImage) {
        this.longImage = ArrayUtils.copyOf(longImage);
    }

    public float getFloatScalar() {
        return floatScalar;
    }

    // DOUBLE

    public void setFloatScalar(final float floatScalar) {
        this.floatScalar = floatScalar;
    }

    public float[] getFloatSpectrum() {
        return Arrays.copyOf(floatSpectrum, floatSpectrum.length);
    }

    public void setFloatSpectrum(final float[] floatSpectrum) {
        this.floatSpectrum = new float[floatSpectrum.length];
        System.arraycopy(floatSpectrum, 0, this.floatSpectrum, 0, floatSpectrum.length);
    }

    public float[][] getFloatImage() {
        return ArrayUtils.copyOf(floatImage);
    }

    public void setFloatImage(final float[][] floatImage) {
        this.floatImage = ArrayUtils.copyOf(floatImage);
    }

    public double getDoubleScalar() {
        return doubleScalar;
    }

    // BOOLEAN

    public void setDoubleScalar(final double doubleScalar) {
        this.doubleScalar = doubleScalar;
    }

    public double[] getDoubleSpectrum() {
        return Arrays.copyOf(doubleSpectrum, doubleSpectrum.length);
    }

    public void setDoubleSpectrum(final double[] doubleSpectrum) {
        this.doubleSpectrum = new double[doubleSpectrum.length];
        System.arraycopy(doubleSpectrum, 0, this.doubleSpectrum, 0, doubleSpectrum.length);
    }

    public double[][] getDoubleImage() {
        return ArrayUtils.copyOf(doubleImage);
    }

    public void setDoubleImage(final double[][] doubleImage) {
        this.doubleImage = ArrayUtils.copyOf(doubleImage);
    }

    public AttributeValue isBooleanScalar() throws DevFailed {
        final AttributeValue val = new AttributeValue(booleanScalar, AttrQuality.ATTR_CHANGING);
        val.setValue(booleanScalar, 123456L);
        return val;
    }

    // STRING

    public void setBooleanScalar(final boolean booleanScalar) {
        this.booleanScalar = booleanScalar;
    }

    public boolean[] getBooleanSpectrum() {
        return Arrays.copyOf(booleanSpectrum, booleanSpectrum.length);
    }

    public void setBooleanSpectrum(final boolean[] booleanSpectrum) {
        this.booleanSpectrum = new boolean[booleanSpectrum.length];
        System.arraycopy(booleanSpectrum, 0, this.booleanSpectrum, 0, booleanSpectrum.length);
    }

    public boolean[][] getBooleanImage() {
        return ArrayUtils.copyOf(booleanImage);
    }

    public void setBooleanImage(final boolean[][] booleanImage) {
        this.booleanImage = ArrayUtils.copyOf(booleanImage);
    }

    public String getStringScalar() {
        return stringScalar;
    }

    public void setStringScalar(final String stringScalar) {
        this.stringScalar = stringScalar;
    }

    public String[] getStringSpectrum() {
        return Arrays.copyOf(stringSpectrum, stringSpectrum.length);
    }

    public void setStringSpectrum(final String[] stringSpectrum) {
        this.stringSpectrum = new String[stringSpectrum.length];
        System.arraycopy(stringSpectrum, 0, this.stringSpectrum, 0, stringSpectrum.length);
    }

    public String[][] getStringImage() {
        return ArrayUtils.copyOf(stringImage);
    }

    public void setStringImage(final String[][] stringImage) {
        this.stringImage = ArrayUtils.copyOf(stringImage);
    }

    @Attribute
    public byte getByteScalar() {
        return byteScalar;
    }

    public void setByteScalar(final byte byteScalar) {
        this.byteScalar = byteScalar;
    }

    public byte[] getByteSpectrum() {
        return Arrays.copyOf(byteSpectrum, byteSpectrum.length);
    }

    public void setByteSpectrum(final byte[] byteSpectrum) {
        this.byteSpectrum = new byte[byteSpectrum.length];
        System.arraycopy(byteSpectrum, 0, this.byteSpectrum, 0, byteSpectrum.length);
    }

    public byte[][] getByteImage() {
        return ArrayUtils.copyOf(byteImage);
    }

    public void setByteImage(final byte[][] byteImage) {
        this.byteImage = ArrayUtils.copyOf(byteImage);
    }

    // SHORT

    public AttributeValue getInvalidQuality() throws DevFailed {
        return new AttributeValue(invalidQuality, AttrQuality.ATTR_INVALID);
    }

    public AttributeValue getInvalidQuality2() throws DevFailed {
        final AttributeValue value = new AttributeValue();
        value.setQuality(AttrQuality.ATTR_INVALID);
        value.setValue(invalidQuality2);
        return value;
    }

    // INTEGER

    /*
     *
     * COMMAND
     */
    @Command(outTypeDesc = "do nothing")
    public void voidCommand() {
    }

    @Command(outTypeDesc = "returns the input value (short)")
    public short shortCommand(final short value) {
        return value;
    }

    // Byte

    @Command(outTypeDesc = "returns the input value (short[])")
    public short[] shortSpectrumCommand(final short[] value) {
        return value;
    }

    @Command(outTypeDesc = "returns the input value (int)")
    public int intCommand(final int value) {
        return value;
    }

    // LONG

    @Command(outTypeDesc = "returns the input value (int[])")
    public int[] intSpectrumCommand(final int[] value) {
        return value;
    }

    @Command(outTypeDesc = "returns the input value (byte)")
    public byte byteCommand(final byte value) {
        return value;
    }

    // DEVENCODED

    // @Command(outTypeDesc = "returns the input value (DevEncoded)")
    // public DevEncoded devEncodedCommand(final DevEncoded value) {
    // return value;
    // }

    // FLOAT

    @Command(outTypeDesc = "returns the input value (byte[])")
    public byte[] byteSpectrumCommand(final byte[] value) {
        return value;
    }

    @Command(outTypeDesc = "returns the input value (long)")
    public long longCommand(final long value) {
        return value;
    }

    // DOUBLE

    @Command(outTypeDesc = "returns the input value (long[])")
    public long[] longSpectrumCommand(final long[] value) {
        return value;
    }

    @Command(outTypeDesc = "returns the input value (float)")
    public float floatCommand(final float value) {
        return value;
    }

    // BOOLEAN

    @Command(outTypeDesc = "returns the input value (float[])")
    public float[] floatSpectrumCommand(final float[] value) {
        return value;
    }

    // STRING

    @Command(outTypeDesc = "returns the input value (double)")
    public double doubleCommand(final double value) {
        return value;
    }

    @Command(outTypeDesc = "returns the input value (double[])")
    public double[] doubleSpectrumCommand(final double[] value) {
        return value;
    }

    // DEVVAR

    @Command(outTypeDesc = "returns the input value (boolean)")
    public boolean booleanCommand(final boolean value) {
        return value;
    }

    @Command(outTypeDesc = "returns the input value (String)")
    public String stringCommand(final String value) {
        return value;
    }

    @Command(outTypeDesc = "returns the input value (String[])")
    public String[] stringSpectrumCommmand(final String[] value) {
        return value;
    }

    @Command(outTypeDesc = "returns the input value (DevVarLongStringArray)")
    public DevVarLongStringArray longStringCommand(final DevVarLongStringArray value) {
        return value;
    }

    @Command(outTypeDesc = "returns the input value (DevVarDoubleStringArray)")
    public DevVarDoubleStringArray doubleStringCommand(final DevVarDoubleStringArray value) {
        return value;
    }

    @Command
    public double testPolling() throws DevFailed {
        final double value = 12;
        error = !error;
        if (error) {
            throw DevFailedUtils.newDevFailed("error");
        }

        return value;
    }

    @Command
    public int[] testPollingArray() {
        return new int[]{1, 2};
    }

    @Command
    @StateMachine(endState = DeviceState.FAULT)
    public void testState() {
    }

    /**
     *
     * @param myProp
     *            String []
     */
    public void setMyProp(final String myProp) {
        this.myProp = myProp;
    }

    /*
     * GETTER AND SETTER ATTRIBUTE//
     */

    // PROPERTIES

    /**
     *
     * @param myClassProp
     *            String []
     */
    public void setMyClassProp(final String[] myClassProp) {
        this.myClassProp = Arrays.copyOf(myClassProp, myClassProp.length);
    }

    /**
     * @return device property myProp
     */
    @Command
    public String getMyProperty() {
        return myProp;
    }

    /**
     *
     * @return class property myClassProp
     */
    @Command
    public String[] getMyClassProperty() {
        return Arrays.copyOf(myClassProp, myClassProp.length);
    }

    public DeviceState getStateScalar() {
        return stateScalar;
    }

    public void setStateScalar(final DeviceState stateScalar) {
        this.stateScalar = stateScalar;
    }

    public DevEncoded getDevEncodedScalar() {
        return devEncodedScalar;
    }

    public void setDevEncodedScalar(final DevEncoded devEncodedScalar) {
        this.devEncodedScalar = devEncodedScalar;
    }

    // public DevEncoded[] getDevEncodedSpectrum() {
    // return devEncodedSpectrum;
    // }
    //
    // public void setDevEncodedSpectrum(final DevEncoded[] devEncodedSpectrum)
    // {
    // this.devEncodedSpectrum = devEncodedSpectrum;
    // }

    @AroundInvoke
    public void alwaysHook(final org.tango.server.InvocationContext ctx) {
        logger.debug(ctx.toString());
    }

    public DevState getState() {
        return state;
    }

    public void setState(final DevState state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setDynamicManager(final DynamicManager dynamicManager) {
        this.dynamicManager = dynamicManager;
    }

    public void setProps(final Map<String, String[]> props) {
        this.props = props;
    }

    @Command
    public boolean isBooleanProp() {
        return booleanProp;
    }

    public void setBooleanProp(final boolean booleanProp) {
        this.booleanProp = booleanProp;
    }

    public DeviceState[] getStateSpectrum() {
        return Arrays.copyOf(stateSpectrum, stateSpectrum.length);
    }

    public void setStateSpectrum(final DeviceState[] stateSpectrum) {
        this.stateSpectrum = Arrays.copyOf(stateSpectrum, stateSpectrum.length);
    }

    public double getDeltaAttribute() {
        return deltaAttribute;
    }

    public void setDeltaAttribute(final double deltaAttribute) {
        // this.deltaAttribute = deltaAttribute;
    }

    public void setDeviceManager(final DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    public TestType getEnumAttribute() {
        return enumAttribute;
    }

    public void setEnumAttribute(final TestType enumAttribute) {
        this.enumAttribute = enumAttribute;
    }

    public enum TestType {
        VALUE1, VALUE2
    }

}
