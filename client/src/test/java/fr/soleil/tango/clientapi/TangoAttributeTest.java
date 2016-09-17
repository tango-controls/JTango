package fr.soleil.tango.clientapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;

@Ignore(value = "a tangotest must be started automatically")
@RunWith(Parameterized.class)
public class TangoAttributeTest {

    @Parameters
    public static List<Object[]> getParameters() throws DevFailed {
        final String deviceName = "tango/tangotest/1";
        TangoDevice dev = null;
        final List<Object[]> result = new ArrayList<Object[]>();
        String[] attrList = null;

        dev = new TangoDevice(deviceName);
        attrList = dev.getDeviceProxy().get_attribute_list();

        for (final String attrName : attrList) {
            result.add(new Object[] { dev, attrName });
        }
        return result;
    }

    private final String attributeName;
    TangoDevice dev;

    public TangoAttributeTest(final TangoDevice dev, final String attributeName) {
        this.attributeName = attributeName;
        this.dev = dev;
    }

    // static String deviceName;
    // static TangoUnitClient client;

    @BeforeClass
    public static void setUp() {
        System.setProperty("TANGO_HOST", "calypso:20001");
        // try {
        // client = TangoUnitFactory.instance().createTangoUnitClient(
        // MODE.remote);
        // final Device d = client.addDevice("TangoTest");
        //
        // System.out.println("TEST - create");
        // client.create();
        // System.out.println("TEST - starting");
        // client.start();
        // deviceName = d.getProxy().get_name();
        // System.out.println("TEST - init done for " + deviceName);
        //
        // } catch (final DevFailed e) {
        // // e.printStackTrace();
        // Except.print_exception(e);
        // Assert.fail(TangoUtil.getDevFailedString(e));
        // } catch (final TimeoutException e) {
        // e.printStackTrace();
        // Assert.fail();
        // }
    }

    @Test
    // @Ignore(value = "a tangotest must be started automatically")
    public void testAllTangoTestTypes() throws Exception {

        if (!attributeName.equals("no_value") && !attributeName.equals("throw_exception")) {
            try {
                final AttrWriteType writeType = dev.getDeviceProxy().get_attribute_info(attributeName).writable;
                final AttrDataFormat format = dev.getDeviceProxy().get_attribute_info(attributeName).data_format;
                final TangoAttribute ta = dev.getTangoAttribute(attributeName);
                System.out.println("#### " + ta + ":");

                if (format.equals(AttrDataFormat.IMAGE)) {
                    if (writeType == AttrWriteType.READ_WRITE || writeType == AttrWriteType.WRITE) {
                        ta.write(new String[][] { { "1", "2.3", "3" }, { "4", "3", "0" } });
                        ta.writeImage(2, 2, new double[] { 1.0, 2.3, 3.0, 4.0 });
                    }
                    ta.update();
                    // long time1 = System.nanoTime();
                    // ta.extractSpecOrImage(String.class);
                    // long time2 = System.nanoTime();
                    // System.out.println("time ms for READ: " + (time2 - time1)
                    // / 1000000.0);
                    final long time1 = System.nanoTime();
                    // ta.extractWrittenSpecOrImage(String.class);
                    ta.extractWritten();
                    final long time2 = System.nanoTime();
                    System.out.println("time ms for getting WRITE: " + (time2 - time1) / 1000000.0);
                } else if (format.equals(AttrDataFormat.SPECTRUM)) {
                    if (writeType == AttrWriteType.READ_WRITE || writeType == AttrWriteType.WRITE) {
                        ta.write(new String[] { "1", "2.3", "3", "4", "3", "0" });
                        // ta.writeSpectrum(new String[] { "1", "2.3", "3", "4",
                        // "3", "0" });
                    }
                    ta.update();
                    System.out.println("READ: " + Arrays.toString(ta.extractSpecOrImage(String.class)));
                    System.out.println("WRITE: " + Arrays.toString(ta.extractWrittenSpecOrImage(String.class)));
                    // System.out.println("As Number :"
                    // +
                    // ta.extractNumberSpecOrImage().getClass().getComponentType());

                } else {
                    if (writeType == AttrWriteType.READ_WRITE || writeType == AttrWriteType.WRITE) {
                        ta.write(12.5);
                    }
                    ta.update();
                    System.out.println("[READ: " + ta.extract(String.class) + ", WRITE : "
                            + ta.extractWritten(String.class) + "]");
                }

                // if (format.equals(AttrDataFormat.IMAGE)) {
                // final long time1 = System.nanoTime();
                // ta.extractToString(",", " #");
                // final long time2 = System.nanoTime();
                // System.out.println("time ms for TOSTRING: " + (time2 - time1)
                // / 1000000.0);
                // }

                final long time1 = System.nanoTime();
                final Object o = ta.extract();
                final long time2 = System.nanoTime();
                System.out.println("time ms for READ: " + (time2 - time1) / 1000000.0);
                if (o.getClass().isArray()) {
                    // System.out.println("ARRAY :" +
                    // o.getClass().getComponentType());
                    if (o.getClass().getComponentType().isArray()) {
                        System.out.println("MATRIX :" + o.getClass().getCanonicalName());
                    }
                } else {
                    System.out.println("SINGLE :" + o.getClass());
                }
                // if (!format.equals(AttrDataFormat.IMAGE) ||
                // attributeName.equals("double_image")) {
                // System.out.println("TO STRING: " + ta.extractToString(" ,",
                // " #"));
                // }
                if (format.equals(AttrDataFormat.SPECTRUM)) {
                    final long time3 = System.nanoTime();
                    final String[] array = (String[]) ta.extractArray(String.class);
                    final long time4 = System.nanoTime();
                    System.out.println("ARRAY time ms for READ: " + (time4 - time3) / 1000000.0);
                    System.out.println("extract array" + Arrays.toString(array));
                }
            } catch (final DevFailed e) {
                System.out.println(DevFailedUtils.toString(e));
                throw e;
            } catch (final Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    @Ignore(value = "a tangotest must be started automatically")
    @Test
    public void test() throws DevFailed {
        final String deviceName = "tango/tangotest/1";
        // String[] attrList = null;
        // TangoDevice dev = null;

        final TangoAttribute ta = new TangoAttribute(deviceName + "/double_scalar");
        ta.insert(12.3);
        ta.write();
        ta.update();
        System.out.println(ta.extractToString(",", "\n"));
        final TangoAttribute ta2 = new TangoAttribute(deviceName + "/short_spectrum");
        final short[] s = { 1 };
        ta2.insert(s);
        ta2.write();
        ta2.update();
        System.out.println(ta2.extractToString(",", "\n"));
    }

    @Ignore(value = "a tangotest must be started automatically")
    @Test
    public void testExtractNumber() throws DevFailed {
        final TangoAttribute ta = new TangoAttribute("tango/tangotest/1/short_spectrum");
        ta.update();
        System.out.println(ta.extractNumberSpecOrImage().getClass());
    }

    // @AfterClass
    // public static void tearDown() {
    // try {
    // if (client != null) {
    // System.out.println("TEST - stopping");
    // client.stop().delete();
    // TangoUnitFactory.instance().releaseTangoUnitClient(client);
    // System.out.println("TEST - everything killed");
    // }
    // } catch (final DevFailed e) {
    // // TODO Auto-generated catch block
    // // e.printStackTrace();
    // Except.print_exception(e);
    // // Assert.fail();
    //
    // } catch (final TimeoutException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // // Assert.fail();
    // }
    // }
}
