package fr.soleil.tango.clientapi;

import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceData;
import fr.soleil.tango.clientapi.factory.InsertExtractFactory;
import fr.soleil.tango.clientapi.util.TypeConversionUtil;

public final class InsertExtractUtils {

    private static final String ERROR_MSG_DA = "cannot insert/extract a null DeviceAttribute";
    private static final String ERROR_MSG_DD = "cannot insert/extract a null DeviceData";

    private InsertExtractUtils() {

    }

    /**
     * Extract read and write part values to an object for SCALAR, SPECTRUM and IMAGE
     * 
     * @param da
     * @return array of primitives for SCALAR, array of primitives for SPECTRUM, array of primitives array of primitives
     *         for IMAGE
     * @throws DevFailed
     */
    public static Object extract(final DeviceAttribute da) throws DevFailed {
	if (da == null) {
		throw DevFailedUtils.newDevFailed(ERROR_MSG_DA);
	}
	return InsertExtractFactory.getAttributeExtractor(da.getType()).extract(da);
    }

    /**
     * Extract read values to an object for SCALAR, SPECTRUM and IMAGE
     * 
     * @param da
     * @return single value for SCALAR, array of primitives for SPECTRUM, 2D array of primitives for IMAGE
     * @throws DevFailed
     */
    public static Object extractRead(final DeviceAttribute da, final AttrDataFormat format) throws DevFailed {
	if (da == null) {
		throw DevFailedUtils.newDevFailed(ERROR_MSG_DA);
	}
	return InsertExtractFactory.getAttributeExtractor(da.getType()).extractRead(da, format);
    }

    /**
     * Extract read values to an object for SCALAR, SPECTRUM and IMAGE
     * 
     * @param da
     * @return single value for SCALAR, array of primitives for SPECTRUM and IMAGE
     * @throws DevFailed
     */
    public static Object extractReadArray(final DeviceAttribute da, final AttrDataFormat format) throws DevFailed {
	if (da == null) {
		throw DevFailedUtils.newDevFailed(ERROR_MSG_DA);
	}
	return InsertExtractFactory.getAttributeExtractor(da.getType()).extractReadArray(da, format);
    }

    /**
     * Extract write values to an object for SCALAR, SPECTRUM and IMAGE
     * 
     * @param da
     * @return single value for SCALAR, array of primitives for SPECTRUM, 2D array of primitives for IMAGE
     * @throws DevFailed
     */
    public static Object extractWrite(final DeviceAttribute da, final AttrWriteType writeType,
	    final AttrDataFormat format) throws DevFailed {
	if (da == null) {
		throw DevFailedUtils.newDevFailed(ERROR_MSG_DA);
	}
	return InsertExtractFactory.getAttributeExtractor(da.getType()).extractWrite(da, writeType, format);
    }

    /**
     * Extract write values to an object for SCALAR, SPECTRUM and IMAGE
     * 
     * @param da
     * @return single value for SCALAR, array of primitives for SPECTRUM and IMAGE
     * @throws DevFailed
     */
    public static Object extractWriteArray(final DeviceAttribute da, final AttrWriteType writeType,
	    final AttrDataFormat format) throws DevFailed {
	if (da == null) {
	    throw DevFailedUtils.newDevFailed(ERROR_MSG_DA);
	}
	return InsertExtractFactory.getAttributeExtractor(da.getType()).extractWriteArray(da, writeType, format);
    }

    /**
     * Extract read and write part values to an object for SCALAR, SPECTRUM and IMAGE to the requested type
     * 
     * @param <T>
     * @param da
     * @param type
     *            the output type (e.g. double[].class for SCALAR, double[].class for SPECTRUM, double[][].class for
     *            IMAGE)
     * @return array of primitives for SCALAR, array of primitives for SPECTRUM, 2D array of primitives for IMAGE
     * @throws DevFailed
     */
    public static <T> T extract(final DeviceAttribute da, final Class<T> type) throws DevFailed {
	if (da == null) {
	    throw DevFailedUtils.newDevFailed(ERROR_MSG_DA);
	}
	return TypeConversionUtil.castToType(type, extract(da));
    }

    /**
     * Extract read part values to an object for SCALAR, SPECTRUM and IMAGE to the requested type
     * 
     * @param <T>
     * @param da
     * @param type
     *            the output type (e.g. double.class for SCALAR, double[].class for SPECTRUM, double[][].class for
     *            IMAGE)
     * @return single value for SCALAR, array of primitives for SPECTRUM, 2D array of primitives for IMAGE
     * @throws DevFailed
     */
    public static <T> T extractRead(final DeviceAttribute da, final AttrDataFormat format, final Class<T> type)
	    throws DevFailed {
	if (da == null) {
	    throw DevFailedUtils.newDevFailed(ERROR_MSG_DA);
	}
	return TypeConversionUtil.castToType(type, extractRead(da, format));
    }

    /**
     * Extract read part values to an object for SCALAR, SPECTRUM and IMAGE to the requested type
     * 
     * @param <T>
     * @param da
     * @param type
     *            the output type (e.g. double.class for SCALAR, double[].class for SPECTRUM, double[].class for IMAGE)
     * @return single value for SCALAR, array of primitives for SPECTRUM and IMAGE
     * @throws DevFailed
     */
    public static <T> T extractReadArray(final DeviceAttribute da, final AttrDataFormat format, final Class<T> type)
	    throws DevFailed {
	if (da == null) {
	    throw DevFailedUtils.newDevFailed(ERROR_MSG_DA);
	}
	return TypeConversionUtil.castToType(type, extractReadArray(da, format));
    }

    /**
     * Extract write part values to an object for SCALAR, SPECTRUM and IMAGE to the requested type
     * 
     * @param <T>
     * @param da
     * @param type
     *            the output type (e.g. double.class for SCALAR, double[].class for SPECTRUM, double[][].class for
     *            IMAGE)
     * @return single value for SCALAR, array of primitives for SPECTRUM, 2D array of primitives for IMAGE
     * @throws DevFailed
     */
    public static <T> T extractWrite(final DeviceAttribute da, final AttrDataFormat format,
	    final AttrWriteType writeType, final Class<T> type) throws DevFailed {
	if (da == null) {
	    throw DevFailedUtils.newDevFailed(ERROR_MSG_DA);
	}
	return TypeConversionUtil.castToType(type, extractWrite(da, writeType, format));
    }

    /**
     * Extract write part values to an object for SCALAR, SPECTRUM and IMAGE to the requested type
     * 
     * @param <T>
     * @param da
     * @param type
     *            the output type (e.g. double.class for SCALAR, double[].class for SPECTRUM, double[].class for IMAGE)
     * @return single value for SCALAR, array of primitives for SPECTRUM and IMAGE
     * @throws DevFailed
     */
    public static <T> T extractWriteArray(final DeviceAttribute da, final AttrWriteType writeType,
	    final AttrDataFormat format, final Class<T> type) throws DevFailed {
	if (da == null) {
	    throw DevFailedUtils.newDevFailed(ERROR_MSG_DA);
	}
	return TypeConversionUtil.castToType(type, extractWriteArray(da, writeType, format));
    }

    public static Object extract(final DeviceData dd) throws DevFailed {
	if (dd == null) {
	    throw DevFailedUtils.newDevFailed(ERROR_MSG_DD);
	}
	return InsertExtractFactory.getCommandExtractor(dd.getType()).extract(dd);
    }

    public static <T> T extract(final DeviceData dd, final Class<T> type) throws DevFailed {
	if (dd == null) {
	    throw DevFailedUtils.newDevFailed(ERROR_MSG_DD);
	}
	final Object r = extract(dd);
	return TypeConversionUtil.castToType(type, r);
    }

    public static void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	if (da == null) {
	    throw DevFailedUtils.newDevFailed(ERROR_MSG_DA);
	}
	InsertExtractFactory.getAttributeInserter(da.getType()).insert(da, value);
    }

    public static void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY)
	    throws DevFailed {
	if (da == null) {
	    throw DevFailedUtils.newDevFailed(ERROR_MSG_DA);
	}
	InsertExtractFactory.getAttributeInserter(da.getType()).insert(da, value, dimX, dimY);
    }

    public static void insert(final DeviceData dd, final int dataType, final Object value) throws DevFailed {
	if (dd == null) {
	    throw DevFailedUtils.newDevFailed(ERROR_MSG_DD);
	}
	InsertExtractFactory.getCommandInserter(dataType).insert(dd, value);
    }

}
