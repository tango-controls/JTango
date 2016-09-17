package fr.soleil.tango.clientapi.attribute;

import java.lang.reflect.Array;

import org.tango.utils.ArrayUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.soleil.tango.clientapi.util.TypeConversionUtil;
import fr.soleil.tango.clientapi.util.TypeConversionUtil.Part;

public final class Extractors {

    public abstract static class AExtractor implements IExtractor {
	@Override
	public abstract Object extract(final DeviceAttribute da) throws DevFailed;

	@Override
	public final Object extractRead(final DeviceAttribute da, final AttrDataFormat format) throws DevFailed {
	    Object result;
	    final Object tmp = extractReadArray(da, format);
	    if (format.equals(AttrDataFormat.IMAGE) && Array.getLength(tmp) > 0) {
		// transform image to matrix
		final int dimX = da.getDimX();
		final int dimY = da.getDimY();
		result = ArrayUtils.fromArrayTo2DArray(tmp, dimX, dimY);
	    } else {
		result = tmp;
	    }
	    return result;
	}

	@Override
	public final Object extractWrite(final DeviceAttribute da, final AttrWriteType writeType,
		final AttrDataFormat format) throws DevFailed {
	    Object result;
	    final Object tmp = extractWriteArray(da, writeType, format);
	    if (format.equals(AttrDataFormat.IMAGE) && Array.getLength(tmp) > 0) {
		// transform image to matrix
		final int dimX = da.getWrittenDimX();
		final int dimY = da.getWrittenDimY();
		result = ArrayUtils.fromArrayTo2DArray(tmp, dimX, dimY);
	    } else {
		result = tmp;
	    }
	    return result;
	}

	@Override
	public final Object extractReadArray(final DeviceAttribute da, final AttrDataFormat format) throws DevFailed {
	    Object result;
	    if (format.equals(AttrDataFormat.SCALAR)) {
		result = Array.get(extract(da), 0);
	    } else {
		result = TypeConversionUtil.extractReadOrWrite(Part.READ, da, extract(da));
	    }
	    return result;
	}

	@Override
	public final Object extractWriteArray(final DeviceAttribute da, final AttrWriteType writeType,
		final AttrDataFormat format) throws DevFailed {
	    Object result;
	    if (format.equals(AttrDataFormat.SCALAR)) {
		result = TypeConversionUtil.getWritePart(extract(da), writeType);
	    } else {
		result = TypeConversionUtil.extractReadOrWrite(Part.WRITE, da, extract(da));

	    }
	    return result;
	}
    }

    public static final class BooleanExtractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractBooleanArray();
	}
    }

    public static final class DoubleExtractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractDoubleArray();
	}

    }

    public static final class FloatExtractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractFloatArray();
	}
    }

    public static final class Long64Extractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractLong64Array();
	}
    }

    public static final class LongExtractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractLongArray();
	}

    }

    public static final class ShortExtractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractShortArray();
	}
    }

    public static final class StateExtractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractDevStateArray();
	}
    }

    public static final class StringExtractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractStringArray();
	}
    }

    public static final class UCharExtractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractUCharArray();
	}

    }

    public static final class CharExtractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractCharArray();
	}

    }

    public static final class ULong64Extractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractULong64Array();
	}
    }

    public static final class ULongExtractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractULongArray();
	}
    }

    public static final class UShortExtractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractUShortArray();
	}
    }

    public static final class DevEncodedExtractor extends AExtractor {
	@Override
	public Object extract(final DeviceAttribute da) throws DevFailed {
	    return da.extractDevEncodedArray();
	}
    }

}
