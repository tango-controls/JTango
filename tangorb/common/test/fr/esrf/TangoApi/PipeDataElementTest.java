package fr.esrf.TangoApi;

import fr.esrf.Tango.DevState;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class PipeDataElementTest {
    private static Object int_arr;
    private static Object double_arr;
    private static Object string_arr;
    private static Object state_arr;
    private static Object int_arr_of_int_arr;

    @BeforeClass
    static public void beforeClass() {
        int_arr = new int[]{1, 2, 3};
        double_arr = new double[]{3.14D, 2.87D};
        string_arr = new String[]{"Hello", "World", "!!!"};
        state_arr = new DevState[]{DevState.ON, DevState.OFF};
        int_arr_of_int_arr = new int[][]{new int[]{1, 2}, new int[]{3, 4, 5}};
    }


    @AfterClass
    public static void afterClass() {
        int_arr = null;
        double_arr = null;
        string_arr = null;
        state_arr = null;
        int_arr_of_int_arr = null;
    }

    @Test
    public void testNewInstance_int_arr() throws Exception {
        PipeDataElement instance = PipeDataElement.newInstance("int_arr", int_arr);

        assertArrayEquals(new int[]{1, 2, 3}, instance.extractLongArray());
    }

    @Test
    public void testNewInstance_double_arr() throws Exception {
        PipeDataElement instance = PipeDataElement.newInstance("double_arr", double_arr);

        assertArrayEquals(new double[]{3.14D, 2.87D}, instance.extractDoubleArray(), 0.D);
    }

    @Test
    public void testNewInstance_string_arr() throws Exception {
        PipeDataElement instance = PipeDataElement.newInstance("string_arr", string_arr);

        assertArrayEquals(new String[]{"Hello", "World", "!!!"}, instance.extractStringArray());
    }

    @Test
    public void testNewInstance_state_arr() throws Exception {
        PipeDataElement instance = PipeDataElement.newInstance("state_arr", state_arr);

        assertArrayEquals(new DevState[]{DevState.ON, DevState.OFF}, instance.extractDevStateArray());
    }

    @Test
    public void testNewInstance_int_arr_of_int_arr() throws Exception {
        PipeDataElement instance = PipeDataElement.newInstance("int_arr_of_int_arr", int_arr_of_int_arr);

        assertArrayEquals(new int[]{1, 2}, instance.extractPipeBlob().get(0).extractLongArray());
        assertArrayEquals(new int[]{3, 4, 5}, instance.extractPipeBlob().get(1).extractLongArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewInstance_empty_array() throws Exception {
        PipeDataElement instance = PipeDataElement.newInstance("ka-boom", new double[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewInstance_wrong_array() throws Exception {
        PipeDataElement instance = PipeDataElement.newInstance("ka-boom", new char[]{'a', 'b', 'c'});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewInstance_not_an_array() throws Exception {
        PipeDataElement instance = PipeDataElement.newInstance("ka-boom", "Ouch...");
    }
}