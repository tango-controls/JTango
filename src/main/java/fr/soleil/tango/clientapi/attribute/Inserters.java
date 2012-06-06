package fr.soleil.tango.clientapi.attribute;

import org.tango.utils.ArrayUtils;

import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.soleil.tango.clientapi.util.TypeConversionUtil;

public final class Inserters {
    public static final class BooleanInserter implements IInserter {
	@Override
	public void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	    if (!value.getClass().isArray()) {
		da.insert(TypeConversionUtil.castToType(boolean.class, value));
	    } else if (value.getClass().getComponentType().isArray()) {
		final Object array1D = ArrayUtils.from2DArrayToArray(value);
		da.insert(TypeConversionUtil.castToType(boolean[].class, array1D), ArrayUtils.get2DArrayXDim(value),
			ArrayUtils.get2DArrayYDim(value));
	    } else {
		da.insert(TypeConversionUtil.castToType(boolean[].class, value));
	    }
	}

	@Override
	public void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY)
		throws DevFailed {
	    da.insert(TypeConversionUtil.castToType(boolean[].class, value), dimX, dimY);
	}
    }

    public static final class DevEncodedInserter implements IInserter {
	@Override
	public void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	    if (!value.getClass().isArray()) {
		da.insert((DevEncoded) value);
	    } else {
		throw new NumberFormatException("Type DevEncoded not supported");
	    }
	}

	@Override
	public void insert(final DeviceAttribute da, final Object values, final int dimX, final int dimY)
		throws DevFailed {
	    throw new NumberFormatException("Type DevEncoded not supported");
	}
    }

    public static final class DoubleInserter implements IInserter {
	@Override
	public void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	    if (!value.getClass().isArray()) {
		da.insert(TypeConversionUtil.castToType(double.class, value));
	    } else if (value.getClass().getComponentType().isArray()) {
		final Object array1D = ArrayUtils.from2DArrayToArray(value);
		da.insert(TypeConversionUtil.castToType(double[].class, array1D), ArrayUtils.get2DArrayXDim(value),
			ArrayUtils.get2DArrayYDim(value));
	    } else {
		da.insert(TypeConversionUtil.castToType(double[].class, value));
	    }
	}

	@Override
	public void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY)
		throws DevFailed {
	    da.insert(TypeConversionUtil.castToType(double[].class, value), dimX, dimY);
	}
    }

    public static final class FloatInserter implements IInserter {
	@Override
	public void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	    if (!value.getClass().isArray()) {
		da.insert(TypeConversionUtil.castToType(float.class, value));
	    } else if (value.getClass().getComponentType().isArray()) {
		final Object array1D = ArrayUtils.from2DArrayToArray(value);
		da.insert(TypeConversionUtil.castToType(float[].class, array1D), ArrayUtils.get2DArrayXDim(value),
			ArrayUtils.get2DArrayYDim(value));
	    } else {
		da.insert(TypeConversionUtil.castToType(float[].class, value));
	    }
	}

	@Override
	public void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY)
		throws DevFailed {
	    da.insert(TypeConversionUtil.castToType(float[].class, value), dimX, dimY);
	}
    }

    public static final class IntInserter implements IInserter {
	@Override
	public void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	    if (!value.getClass().isArray()) {
		da.insert(TypeConversionUtil.castToType(int.class, value));
	    } else if (value.getClass().getComponentType().isArray()) {
		final Object array1D = ArrayUtils.from2DArrayToArray(value);
		da.insert(TypeConversionUtil.castToType(int[].class, array1D), ArrayUtils.get2DArrayXDim(value),
			ArrayUtils.get2DArrayYDim(value));
	    } else {
		da.insert(TypeConversionUtil.castToType(int[].class, value));
	    }
	}

	@Override
	public void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY)
		throws DevFailed {
	    da.insert(TypeConversionUtil.castToType(int[].class, value), dimX, dimY);
	}
    }

    public static final class LongInserter implements IInserter {
	@Override
	public void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	    if (!value.getClass().isArray()) {
		da.insert(TypeConversionUtil.castToType(long.class, value));
	    } else if (value.getClass().getComponentType().isArray()) {
		final Object array1D = ArrayUtils.from2DArrayToArray(value);
		da.insert(TypeConversionUtil.castToType(long[].class, array1D), ArrayUtils.get2DArrayXDim(value),
			ArrayUtils.get2DArrayYDim(value));
	    } else {
		da.insert(TypeConversionUtil.castToType(long[].class, value));
	    }
	}

	@Override
	public void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY)
		throws DevFailed {
	    da.insert(TypeConversionUtil.castToType(long[].class, value), dimX, dimY);
	}
    }

    public static final class ShortInserter implements IInserter {
	@Override
	public void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	    if (!value.getClass().isArray()) {
		da.insert(TypeConversionUtil.castToType(short.class, value));
	    } else if (value.getClass().getComponentType().isArray()) {
		final Object array1D = ArrayUtils.from2DArrayToArray(value);
		da.insert(TypeConversionUtil.castToType(short[].class, array1D), ArrayUtils.get2DArrayXDim(value),
			ArrayUtils.get2DArrayYDim(value));
	    } else {
		da.insert(TypeConversionUtil.castToType(short[].class, value));
	    }
	}

	@Override
	public void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY)
		throws DevFailed {
	    da.insert(TypeConversionUtil.castToType(short[].class, value), dimX, dimY);
	}
    }

    public static final class StateInserter implements IInserter {

	@Override
	public void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	    if (!value.getClass().isArray()) {
		da.insert(TypeConversionUtil.castToType(DevState.class, value));
	    } else if (value.getClass().getComponentType().isArray()) {
		final Object array1D = ArrayUtils.from2DArrayToArray(value);
		da.insert(TypeConversionUtil.castToType(DevState[].class, array1D), ArrayUtils.get2DArrayXDim(value),
			ArrayUtils.get2DArrayYDim(value));
	    } else {
		da.insert(TypeConversionUtil.castToType(DevState[].class, value));
	    }
	}

	@Override
	public void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY)
		throws DevFailed {
	    da.insert(TypeConversionUtil.castToType(DevState[].class, value), dimX, dimY);
	}
    }

    public static final class StringInserter implements IInserter {
	@Override
	public void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	    if (!value.getClass().isArray()) {
		da.insert(TypeConversionUtil.castToType(String.class, value));
	    } else if (value.getClass().getComponentType().isArray()) {
		final Object array1D = ArrayUtils.from2DArrayToArray(value);
		da.insert(TypeConversionUtil.castToType(String[].class, array1D), ArrayUtils.get2DArrayXDim(value),
			ArrayUtils.get2DArrayYDim(value));
	    } else {
		da.insert(TypeConversionUtil.castToType(String[].class, value));
	    }
	}

	@Override
	public void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY)
		throws DevFailed {
	    da.insert(TypeConversionUtil.castToType(String[].class, value), dimX, dimY);
	}
    }

    public static final class UCharInserter implements IInserter {
	@Override
	public void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	    if (!value.getClass().isArray()) {
		da.insert_uc(TypeConversionUtil.castToType(short.class, value));
	    } else if (value.getClass().getComponentType().isArray()) {
		final Object array1D = ArrayUtils.from2DArrayToArray(value);
		da.insert_uc(TypeConversionUtil.castToType(short[].class, array1D), ArrayUtils.get2DArrayXDim(value),
			ArrayUtils.get2DArrayYDim(value));
	    } else {
		da.insert_uc(TypeConversionUtil.castToType(short[].class, value));
	    }
	}

	@Override
	public void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY)
		throws DevFailed {
	    da.insert_uc(TypeConversionUtil.castToType(short[].class, value), dimX, dimY);
	}
    }

    public static final class ULong64Inserter implements IInserter {
	@Override
	public void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	    if (!value.getClass().isArray()) {
		da.insert_u64(TypeConversionUtil.castToType(long.class, value));
	    } else if (value.getClass().getComponentType().isArray()) {
		final Object array1D = ArrayUtils.from2DArrayToArray(value);
		da.insert_u64(TypeConversionUtil.castToType(long[].class, array1D), ArrayUtils.get2DArrayXDim(value),
			ArrayUtils.get2DArrayYDim(value));
	    } else {
		da.insert_u64(TypeConversionUtil.castToType(long[].class, value));
	    }
	}

	@Override
	public void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY)
		throws DevFailed {
	    da.insert_u64(TypeConversionUtil.castToType(long[].class, value), dimX, dimY);
	}
    }

    public static final class ULongInserter implements IInserter {
	@Override
	public void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	    if (!value.getClass().isArray()) {
		da.insert_ul(TypeConversionUtil.castToType(long.class, value));
	    } else if (value.getClass().getComponentType().isArray()) {
		final Object array1D = ArrayUtils.from2DArrayToArray(value);
		da.insert_ul(TypeConversionUtil.castToType(long[].class, array1D), ArrayUtils.get2DArrayXDim(value),
			ArrayUtils.get2DArrayYDim(value));
	    } else {
		da.insert_ul(TypeConversionUtil.castToType(long[].class, value));
	    }
	}

	@Override
	public void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY)
		throws DevFailed {
	    da.insert_ul(TypeConversionUtil.castToType(long[].class, value), dimX, dimY);
	}
    }

    public static final class UShortInserter implements IInserter {
	@Override
	public void insert(final DeviceAttribute da, final Object value) throws DevFailed {
	    if (!value.getClass().isArray()) {
		da.insert_us(TypeConversionUtil.castToType(short.class, value));
	    } else if (value.getClass().getComponentType().isArray()) {
		final Object array1D = ArrayUtils.from2DArrayToArray(value);
		da.insert_us(TypeConversionUtil.castToType(short[].class, array1D), ArrayUtils.get2DArrayXDim(value),
			ArrayUtils.get2DArrayYDim(value));
	    } else {
		da.insert_us(TypeConversionUtil.castToType(short[].class, value));
	    }
	}

	@Override
	public void insert(final DeviceAttribute da, final Object value, final int dimX, final int dimY)
		throws DevFailed {
	    da.insert_us(TypeConversionUtil.castToType(short[].class, value), dimX, dimY);
	}
    }
}
