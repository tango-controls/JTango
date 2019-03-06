package org.tango.utils;

import fr.esrf.Tango.DevFailed;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 3/6/19
 */
public class DevFailedUtilsTest {

    @Test
    public void newDevFailed_no_desc() {
        DevFailed result = DevFailedUtils.newDevFailed(new IllegalArgumentException());

        assertEquals(IllegalArgumentException.class.getCanonicalName(), result.errors[0].reason);
        assertEquals("NA", result.errors[0].desc);
    }

    @Test
    public void newDevFailed_desc() {
        DevFailed result = DevFailedUtils.newDevFailed(new IllegalStateException("Illegal state has been reached"));

        assertEquals(IllegalStateException.class.getCanonicalName(), result.errors[0].reason);
        assertEquals("Illegal state has been reached", result.errors[0].desc);
    }
}