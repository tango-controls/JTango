package fr.soleil.tango.clientapi;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.esrf.Tango.DevState;
import fr.soleil.tango.clientapi.util.DevEncodedConverter;
import fr.soleil.tango.clientapi.util.DevStateToObjectConverter;
import fr.soleil.tango.clientapi.util.ObjectToBooleanConverter;
import fr.soleil.tango.clientapi.util.ObjectToDevStateConverter;
import fr.soleil.tango.clientapi.util.ObjectToNumberConverter;

public class TestConversion {

    private static Transmorph transmorph;

    @BeforeClass
    public static void setUp() {
        final DefaultConverters conv = new DefaultConverters();
        conv.addConverter(new DevStateToObjectConverter());
        conv.addConverter(new ObjectToDevStateConverter());
        conv.addConverter(new ObjectToBooleanConverter());
        conv.addConverter(new ObjectToNumberConverter());
        conv.addConverter(1, new DevEncodedConverter());
        transmorph = new Transmorph(conv);
    }

    @Test
    public void testBoolean() throws ConverterException {
        assertThat(transmorph.convert(10.3, boolean.class), equalTo(true));
        assertThat(transmorph.convert("fzefef", boolean.class), equalTo(false));
        assertThat(transmorph.convert("0", boolean.class), equalTo(false));
        assertThat(transmorph.convert("", boolean.class), equalTo(false));
    }

    @Test
    public void testInt() throws ConverterException {
        assertThat(transmorph.convert(10.3, int.class), equalTo(10));
        assertThat(transmorph.convert(false, int.class), equalTo(0));
        assertThat(transmorph.convert("true", int.class), equalTo(1));
        assertThat(transmorph.convert("", int.class), equalTo(0));
    }

    @Test
    public void testBig() throws ConverterException {
        assertThat(transmorph.convert("10.3", BigDecimal.class), equalTo(new BigDecimal("10.3")));
        assertThat(transmorph.convert("10.3", BigInteger.class), equalTo(new BigInteger("10")));
    }

    @Test
    public void testState() throws ConverterException {
        assertThat(transmorph.convert(DevState.RUNNING, int.class), equalTo(10));
        assertThat(transmorph.convert(DevState.RUNNING, String.class), equalTo("RUNNING"));
        assertThat(transmorph.convert(10, DevState.class), equalTo(DevState.RUNNING));
    }

    @Test
    public void testArray() throws ConverterException {
        assertThat(transmorph.convert(new String[][] { { "0", "2.1" }, { "0.8", "25.1" } }, short[][].class),
                equalTo(new short[][] { { 0, 2 }, { 0, 25 } }));
    }
}
