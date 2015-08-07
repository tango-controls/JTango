package fr.esrf.TangoApi;

import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevState;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements builder pattern for {@link PipeBlob}
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 06.10.14
 */
//@NotThreadSafe
public class PipeBlobBuilder {
    private final String blobName;
    private final List<PipeDataElement> elements = new ArrayList<PipeDataElement>();

    public PipeBlobBuilder(String blobName) {
        this.blobName = blobName;
    }

    public void add(String name, boolean value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, boolean[] value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, short value){
        elements.add(new PipeDataElement(name, value));
    }

    /**
     *
     * @param name
     * @param value
     * @param asUChar treat Java short (2 bytes) as C++ unsigned char (1 byte)
     */
    public void add(String name, short[] value, boolean asUChar){
        elements.add(new PipeDataElement(name, value, asUChar));
    }

    public void add(String name, int value){
        elements.add(new PipeDataElement(name, value));
    }

    /**
     *
     * @param name
     * @param value
     * @param asUShort treat Java int (4 bytes) as C++ unsigned short (2 bytes)
     */
    public void add(String name, int[] value, boolean asUShort){
        elements.add(new PipeDataElement(name, value, asUShort));
    }

    public void add(String name, long value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, long[] value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, float value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, float[] value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, double value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, double[] value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, String value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, String[] value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, DevState value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, DevState[] value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, DevEncoded value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, DevEncoded[] value){
        elements.add(new PipeDataElement(name, value));
    }

    public void add(String name, PipeBlob value){
        elements.add(new PipeDataElement(name, value));
    }

    public PipeBlob build(){
        PipeBlob blob = new PipeBlob(blobName);
        blob.addAll(elements);
        return blob;
    }
}
