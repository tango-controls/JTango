package fr.soleil.tango.clientapi;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrValUnion;
import fr.esrf.Tango.AttributeDim;
import fr.esrf.Tango.AttributeValue_4;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.DeviceAttribute;

public class InsertExtractTest {

    @Test
    public void testBoolean() throws DevFailed {
	final boolean insert = false;
	final AttributeValue_4 val = newAttrVal();
	val.value.bool_att_value(new boolean[] { insert });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SCALAR;
	final boolean extract = (Boolean) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testBooleanArray() throws DevFailed {
	final boolean[] insert = new boolean[] { false, true };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 0;
	val.value.bool_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SPECTRUM;
	final boolean[] extract = (boolean[]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testDouble() throws DevFailed {
	final double insert = 10.0;
	final AttributeValue_4 val = newAttrVal();
	val.value.double_att_value(new double[] { insert });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SCALAR;
	final double extract = (Double) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testDoubleArray() throws DevFailed {
	final double[] insert = new double[] { 10.0, 15.2 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 0;
	val.value.double_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SPECTRUM;
	final double[] extract = (double[]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testDoubleImage() throws DevFailed {
	final double[] insert = new double[] { 10.0, 15.2, 10.0, 15.2 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 2;
	val.value.double_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	final double[][] insert2 = new double[][] { { 10.0, 15.2 }, { 10.0, 15.2 } };
	InsertExtractUtils.insert(da, insert2);
	val.data_format = AttrDataFormat.IMAGE;
	final double[][] extract = (double[][]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert2));
    }

    @Test
    public void testFloat() throws DevFailed {
	final float insert = 10.0F;
	final AttributeValue_4 val = newAttrVal();
	val.value.float_att_value(new float[] { insert });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SCALAR;
	final float extract = (Float) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testFloatArray() throws DevFailed {
	final float[] insert = new float[] { 10.0F, 15.2F };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 0;
	val.value.float_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SPECTRUM;
	final float[] extract = (float[]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testFloatImage() throws DevFailed {
	final float[] insert = new float[] { 10.0F, 15.2F, 10.0F, 15.2F };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 2;
	val.value.float_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	final float[][] insert2 = new float[][] { { 10.0F, 15.2F }, { 10.0F, 15.2F } };
	InsertExtractUtils.insert(da, insert2);
	val.data_format = AttrDataFormat.IMAGE;
	final float[][] extract = (float[][]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert2));
    }

    @Test
    public void testShort() throws DevFailed {
	final short insert = 10;
	final AttributeValue_4 val = newAttrVal();

	val.value.short_att_value(new short[] { insert });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SCALAR;
	final short extract = (Short) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testShortArray() throws DevFailed {
	final short[] insert = new short[] { 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 0;
	val.value.short_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SPECTRUM;
	final short[] extract = (short[]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testShortImage() throws DevFailed {
	final short[] insert = new short[] { 10, 15, 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 2;
	val.value.short_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	final short[][] insert2 = new short[][] { { 10, 15 }, { 10, 15 } };
	InsertExtractUtils.insert(da, insert2);
	val.data_format = AttrDataFormat.IMAGE;
	final short[][] extract = (short[][]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert2));
    }

    @Test
    public void testUShort() throws DevFailed {
	final short insert = 10;
	final AttributeValue_4 val = newAttrVal();
	val.value.ushort_att_value(new short[] { insert });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SCALAR;
	final int extract = (Integer) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat((short) extract, equalTo(insert));
    }

    @Test
    public void testUShortArray() throws DevFailed {
	final short[] insert = new short[] { 10, 15 };
	final int[] insertAsInt = new int[] { 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 0;
	val.value.ushort_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SPECTRUM;
	final int[] extract = (int[]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insertAsInt));
    }

    @Test
    public void testUShortImage() throws DevFailed {
	final short[] insert = new short[] { 10, 15, 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 2;
	val.value.ushort_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	final int[][] insert2 = new int[][] { { 10, 15 }, { 10, 15 } };
	InsertExtractUtils.insert(da, insert2);
	val.data_format = AttrDataFormat.IMAGE;
	final int[][] extract = (int[][]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert2));
    }

    @Test
    public void testUChar() throws DevFailed {
	final short insert = 10;
	final AttributeValue_4 val = newAttrVal();
	val.value.uchar_att_value(new byte[] { insert });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SCALAR;
	final short extract = (Short) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testUCharArray() throws DevFailed {
	final short[] insert = new short[] { 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 0;
	val.value.uchar_att_value(new byte[] { 10, 15 });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SPECTRUM;
	final short[] extract = (short[]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testUCharImage() throws DevFailed {
	final byte[] insert = new byte[] { 10, 15, 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 2;
	val.value.uchar_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	final short[][] insert2 = new short[][] { { 10, 15 }, { 10, 15 } };
	InsertExtractUtils.insert(da, new byte[][] { { 10, 15 }, { 10, 15 } });
	val.data_format = AttrDataFormat.IMAGE;
	final short[][] extract = (short[][]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert2));
    }

    @Test
    public void testLong64() throws DevFailed {
	final long insert = 10;
	final AttributeValue_4 val = newAttrVal();
	val.value.long64_att_value(new long[] { insert });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SCALAR;
	final long extract = (Long) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testLong64Array() throws DevFailed {
	final long[] insert = new long[] { 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 0;
	val.value.long64_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SPECTRUM;
	final long[] extract = (long[]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testLong64Image() throws DevFailed {
	final long[] insert = new long[] { 10, 15, 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 2;
	val.value.long64_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	final long[][] insert2 = new long[][] { { 10, 15 }, { 10, 15 } };
	InsertExtractUtils.insert(da, insert2);
	val.data_format = AttrDataFormat.IMAGE;
	final long[][] extract = (long[][]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert2));
    }

    @Test
    public void testULong64() throws DevFailed {
	final long insert = 10;
	final AttributeValue_4 val = newAttrVal();
	val.value.ulong64_att_value(new long[] { insert });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SCALAR;
	final long extract = (Long) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testULong64Array() throws DevFailed {
	final long[] insert = new long[] { 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 0;
	val.value.ulong64_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SPECTRUM;
	final long[] extract = (long[]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testULong64Image() throws DevFailed {
	final long[] insert = new long[] { 10, 15, 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 2;
	val.value.ulong64_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	final long[][] insert2 = new long[][] { { 10, 15 }, { 10, 15 } };
	InsertExtractUtils.insert(da, insert2);
	val.data_format = AttrDataFormat.IMAGE;
	final long[][] extract = (long[][]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert2));
    }

    @Test
    public void testULong() throws DevFailed {
	final int insert = 10;
	final AttributeValue_4 val = newAttrVal();
	val.value.ulong_att_value(new int[] { insert });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SCALAR;
	final long extract = (Long) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat((int) extract, equalTo(insert));
    }

    @Test
    public void testULongArray() throws DevFailed {
	final int[] insert = new int[] { 10, 15 };
	final long[] insertAsLong = new long[] { 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 0;
	val.value.ulong_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SPECTRUM;
	final long[] extract = (long[]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insertAsLong));
    }

    @Test
    public void testULongImage() throws DevFailed {
	final int[] insert = new int[] { 10, 15, 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 2;
	val.value.ulong_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	final long[][] insert2 = new long[][] { { 10, 15 }, { 10, 15 } };
	InsertExtractUtils.insert(da, insert2);
	val.data_format = AttrDataFormat.IMAGE;
	final long[][] extract = (long[][]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert2));
    }

    @Test
    public void testLong() throws DevFailed {
	final int insert = 10;
	final AttributeValue_4 val = newAttrVal();
	val.value.long_att_value(new int[] { insert });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SCALAR;
	final int extract = (Integer) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testLongArray() throws DevFailed {
	final int[] insert = new int[] { 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 0;
	val.value.long_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SPECTRUM;
	final int[] extract = (int[]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testLongImage() throws DevFailed {
	final int[] insert = new int[] { 10, 15, 10, 15 };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 2;
	val.value.long_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	final int[][] insert2 = new int[][] { { 10, 15 }, { 10, 15 } };
	InsertExtractUtils.insert(da, insert2);
	val.data_format = AttrDataFormat.IMAGE;
	final int[][] extract = (int[][]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert2));
    }

    @Test
    public void testString() throws DevFailed {
	final String insert = "10";
	final AttributeValue_4 val = newAttrVal();
	val.value.string_att_value(new String[] { insert });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SCALAR;
	final String extract = (String) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testStringArray() throws DevFailed {
	final String[] insert = new String[] { "10", "15" };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 0;
	val.value.string_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SPECTRUM;
	final String[] extract = (String[]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testStringImage() throws DevFailed {
	final String[] insert = new String[] { "10", "15", "10", "15" };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 2;
	val.value.string_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	final String[][] insert2 = new String[][] { { "10", "15" }, { "10", "15" } };
	InsertExtractUtils.insert(da, insert2);
	val.data_format = AttrDataFormat.IMAGE;
	final String[][] extract = (String[][]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert2));
    }

    @Test
    public void testDevState() throws DevFailed {
	final DevState insert = DevState.DISABLE;
	final AttributeValue_4 val = newAttrVal();
	val.value.state_att_value(new DevState[] { insert });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SCALAR;
	final DevState extract = (DevState) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testDevStateArray() throws DevFailed {
	final DevState[] insert = new DevState[] { DevState.DISABLE, DevState.ALARM };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 0;
	val.value.state_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SPECTRUM;
	final DevState[] extract = (DevState[]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test
    public void testDevStateImage() throws DevFailed {
	final DevState[] insert = new DevState[] { DevState.DISABLE, DevState.ALARM, DevState.DISABLE, DevState.ALARM };
	final AttributeValue_4 val = newAttrVal();
	val.r_dim.dim_x = 2;
	val.r_dim.dim_y = 2;
	val.value.state_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	final DevState[][] insert2 = new DevState[][] { { DevState.DISABLE, DevState.ALARM },
		{ DevState.DISABLE, DevState.ALARM } };
	InsertExtractUtils.insert(da, insert2);
	val.data_format = AttrDataFormat.IMAGE;
	final DevState[][] extract = (DevState[][]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert2));
    }

    @Test
    public void testDevEncoded() throws DevFailed {
	final DevEncoded insert = new DevEncoded("test", new byte[] { 1, 2, 3 });
	final AttributeValue_4 val = newAttrVal();
	val.value.encoded_att_value(new DevEncoded[] { insert });
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SCALAR;
	final DevEncoded extract = (DevEncoded) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    @Test(expected = NumberFormatException.class)
    public void testDevEncodedArray() throws DevFailed {
	final DevEncoded[] insert = new DevEncoded[] { new DevEncoded("test", new byte[] { 1, 2, 3 }),
		new DevEncoded("test", new byte[] { 1, 2, 3 }) };
	final AttributeValue_4 val = newAttrVal();
	val.value.encoded_att_value(insert);
	final DeviceAttribute da = new DeviceAttribute(val);
	InsertExtractUtils.insert(da, insert);
	val.data_format = AttrDataFormat.SPECTRUM;
	final DevEncoded[] extract = (DevEncoded[]) InsertExtractUtils.extractRead(da, val.data_format);
	assertThat(extract, equalTo(insert));
    }

    private AttributeValue_4 newAttrVal() {
	final AttributeValue_4 val = new AttributeValue_4();
	val.r_dim = new AttributeDim();
	val.w_dim = new AttributeDim();
	val.name = "";
	val.value = new AttrValUnion();
	return val;
    }
}
