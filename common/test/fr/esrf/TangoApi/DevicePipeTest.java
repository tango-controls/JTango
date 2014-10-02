package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 02.10.14
 */
public class DevicePipeTest {
    private PipeBlob blob;

    @Before
    public void before() throws Exception{
        PipeBlob child = new PipeBlob("Name / Age");
        child.add(new PipeDataElement("Chloe", 30));
        child.add(new PipeDataElement("Nicolas", 28));
        child.add(new PipeDataElement("Auxane", 21));

        //  Build the main blob and insert inner one
        PipeBlob  pipeBlob = new PipeBlob("Pascal");
        pipeBlob.add(new PipeDataElement("City",     "Grenoble"));
        pipeBlob.add(new PipeDataElement("Values",   new float[]{1.23f, 4.56f, 7.89f}));
        pipeBlob.add(new PipeDataElement("Children", child));
        pipeBlob.add(new PipeDataElement("Status",   DevState.RUNNING));

        blob = pipeBlob;
    }

    @Test
    public void testScannerAPI() throws Exception{
        PipeScanner instance = new DevicePipe("TestPipe",blob);

        assertEquals("Grenoble", instance.nextString());
        float[] actualFloatArray = new float[3];
        instance.nextArray(actualFloatArray, 3);
        assertArrayEquals(new float[]{1.23f, 4.56f, 7.89f}, actualFloatArray, 0.1F);

        PipeScanner innerInstance = instance.nextScanner();
        assertEquals(30, innerInstance.nextInt());
        assertEquals(28, innerInstance.nextInt());
        assertEquals(21, innerInstance.nextInt());

        assertSame(DevState.RUNNING, instance.nextState());

        assertFalse(instance.hasNext());
    }

    @Test(expected = DevFailed.class)
    public void testScannerAPI_wrongType() throws Exception{
        PipeScanner instance = new DevicePipe("TestPipe",blob);

        instance.nextDouble();
    }

    @Test(expected = DevFailed.class)
    public void testScannerAPI_wrongSize() throws Exception{
        PipeScanner instance = new DevicePipe("TestPipe",blob);

        instance.move();

        instance.nextArray(new float[0], 5);
    }
}
