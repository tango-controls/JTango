package fr.soleil.tango.clientapi.util;

import java.lang.reflect.Array;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;

public final class TypeConversionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeConversionUtil.class);

    public enum Part {
	READ, WRITE
    };

    private TypeConversionUtil() {
    }

    /**
     * Extract the write part of a scalar attribute
     * 
     * @param array
     * @param writeType
     * @return
     */
    public static Object getWritePart(final Object array, final AttrWriteType writeType) {
	if (writeType.equals(AttrWriteType.READ_WRITE)) {
	    return Array.get(array, 1);
	} else {
	    return Array.get(array, 0);
	}
    }

    private static DefaultConverters creatConv() {
	final DefaultConverters conv = new DefaultConverters();
	conv.addConverter(new DevStateToObjectConverter());
	conv.addConverter(new ObjectToDevStateConverter());
	conv.addConverter(new ObjectToBooleanConverter());
	conv.addConverter(new ObjectToNumberConverter());
	conv.addConverter(1, new DevEncodedConverter());
	return conv;
    }

    /**
     * Convert an array of primitives or Objects like double[][] or Double[] into the requested type. 2D array will be
     * converted to a 1D array
     * 
     * @param <T>
     * @param type
     *            the componant type
     * @param val
     * @return
     * @throws DevFailed
     */

    public static <T> Object castToArray(final Class<T> type, final Object val) throws DevFailed {
	Object result = null;
	final Object array1D = val;
	if (val == null || type.isAssignableFrom(val.getClass())) {
	    result = val;
	} else {
	    LOGGER.debug("converting {} to {}", val.getClass().getCanonicalName(), type.getCanonicalName());
	    final Class<?> typeConv = Array.newInstance(type, 0).getClass();
	    final Transmorph transmorph = new Transmorph(creatConv());
	    try {
		result = transmorph.convert(array1D, typeConv);
	    } catch (final ConverterException e) {
		LOGGER.error("convertion error", e);
		throw DevFailedUtils.newDevFailed(e);
	    }
	}
	return result;
    }

    /**
     * Convert an object to another object.
     * 
     * @see Transmorph
     * @param <T>
     * @param type
     * @param val
     * @return
     * @throws DevFailed
     */
    @SuppressWarnings("unchecked")
    public static <T> T castToType(final Class<T> type, final Object val) throws DevFailed {

	T result;
	if (val == null) {
	    result = null;
	} else if (type.isAssignableFrom(val.getClass())) {
	    result = (T) val;
	} else {
	    LOGGER.debug("converting {} to {}", val.getClass().getCanonicalName(), type.getCanonicalName());
	    // if input is not an array and we want to convert it to an array.
	    // Put
	    // the value in an array
	    Object array = val;
	    if (!val.getClass().isArray() && type.isArray()) {
		array = Array.newInstance(val.getClass(), 1);
		Array.set(array, 0, val);
	    }
	    final Transmorph transmorph = new Transmorph(creatConv());
	    try {
		result = transmorph.convert(array, type);
	    } catch (final ConverterException e) {
		LOGGER.error("convertion error", e);
		throw DevFailedUtils.newDevFailed(e);
	    }
	}
	return result;
    }

    public static boolean containsLetter(final String s) {
	if (s == null) {
	    return false;
	}
	boolean letterFound = false;
	for (int i = 0; !letterFound && i < s.length(); i++) {
	    letterFound = letterFound || Character.isLetter(s.charAt(i));
	}
	return letterFound;
    }

    /**
     * Extract read or write part of a Tango attribute. For spectrum and image
     * 
     * @param part
     * @param da
     * @param readWrite
     * @return
     * @throws DevFailed
     */
    public static Object extractReadOrWrite(final Part part, final DeviceAttribute da, final Object readWrite)
	    throws DevFailed {
	// separate read and written values

	final Object result;
	final int dimRead;
	if (da.getDimY() != 0) {
	    dimRead = da.getDimX() * da.getDimY();
	} else {
	    dimRead = da.getDimX();
	}
	if (Array.getLength(readWrite) < dimRead) {
	    result = readWrite;
	} else if (Part.READ.equals(part)) {
	    result = Array.newInstance(readWrite.getClass().getComponentType(), dimRead);
	    System.arraycopy(readWrite, 0, result, 0, dimRead);
	} else {
	    result = Array.newInstance(readWrite.getClass().getComponentType(), Array.getLength(readWrite) - dimRead);
	    System.arraycopy(readWrite, dimRead, result, 0, Array.getLength(result));
	}

	return result;
    }
}
