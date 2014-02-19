package fr.soleil.tango.clientapi;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

//@Ignore(value = "a tangotest must be started automatically")
@RunWith(Parameterized.class)
public class TangoAttributeMockTest {

    @Parameters
    public static List<Object[]> getParameters() {
	final List<Object[]> result = new ArrayList<Object[]>();
	result.add(new Object[] { 12.5, "attrDouble" });
	result.add(new Object[] { 12, "attrInt" });
	result.add(new Object[] { "12", "attrString" });
	result.add(new Object[] { false, "attrBoolean" });

	result.add(new Object[] { new double[] { 12.5, 13, 14 }, "attrDoubleArray" });
	result.add(new Object[] { new int[] { 12, 3, 6 }, "attrIntArray" });
	result.add(new Object[] { new String[] { "12", "13" }, "attrStringArray" });
	result.add(new Object[] { new boolean[] { false, true, true }, "attrBooleanArray" });

	result.add(new Object[] { new double[][] { { 12.5, 13, 14 }, { 12.5, 13, 14 } }, "attrDoubleImage" });
	result.add(new Object[] { new int[][] { { 12, 3, 6 }, { 12, 3, 6 } }, "attrIntImage" });
	result.add(new Object[] { new String[][] { { "12", "13" }, { "12", "13" } }, "attrStringImage" });
	result.add(new Object[] { new boolean[][] { { false, true, true }, { false, true, true } }, "attrBooleanImage" });

	return result;
    }

    private final String attributeName;
    private final Object defaultValue;

    public TangoAttributeMockTest(final Object defaultValue, final String attributeName) {
	this.attributeName = attributeName;
	this.defaultValue = defaultValue;
    }

    @Test
    public void testMock() throws DevFailed {
	try {
	    final TangoAttribute ta = new TangoAttribute(attributeName, defaultValue);
	    System.out.println("#### " + ta + ":");
	    if (ta.isImage()) {
		final double[][] insert = new double[][] { { 1, 0, 1 }, { 0, 1, 0 } };
		ta.write(insert);
		ta.update();
		assertThat(ta.extract(double[][].class), equalTo(insert));
	    } else if (ta.isSpectrum()) {
		final double[] insert = new double[] { 1, 0, 1, 0, 1, 0 };
		ta.write(insert);
		ta.update();
		assertThat(ta.extract(double[].class), equalTo(insert));
	    } else if (ta.isScalar()) {
		ta.write(0);
		ta.update();
		assertThat(ta.extract(int.class), equalTo(0));
	    }
	} catch (final DevFailed e) {
	    System.out.println(DevFailedUtils.toString(e));
	    throw e;
	} catch (final AssertionError e) {
	    System.err.println(attributeName + " FAILED");
	    throw e;
	}

    }
}
