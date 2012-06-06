package fr.soleil.tango.clientapi.command;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.DeviceData;
import fr.soleil.tango.clientapi.util.TypeConversionUtil;

public final class CommandInserters {

    public static final class VoidInserter implements ICommandInserter {
	@Override
	public void insert(final DeviceData dd, final Object value) {
	    // dummy inserter
	}
    }

    public static final class StringInserter implements ICommandInserter {
	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(String.class, value));
	}
    }

    public static final class StateInserter implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(DevState.class, value));
	}
    }

    public static final class BooleanInserter implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(Boolean.class, value));
	}
    }

    public static final class DoubleInserter implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(Double.class, value));
	}
    }

    public static final class ShortInserter implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(short.class, value));
	}
    }

    public static final class LongInserter implements ICommandInserter {
	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(long.class, value));
	}
    }

    public static final class LongInserterArray implements ICommandInserter {
	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(long[].class, value));
	}
    }

    public static final class IntInserter implements ICommandInserter {
	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(int.class, value));
	}
    }

    public static final class FloatInserter implements ICommandInserter {
	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(float.class, value));
	}
    }

    public static final class ULongInserter implements ICommandInserter {
	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    dd.insert_ul(TypeConversionUtil.castToType(long.class, value));
	}
    }

    public static final class UShortInserter implements ICommandInserter {
	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    dd.insert_us(TypeConversionUtil.castToType(int.class, value));
	}
    }

    public static final class UCharInserter implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    dd.insert_uc(TypeConversionUtil.castToType(byte.class, value));
	}
    }

    public static final class ULong64Inserter implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    dd.insert_u64(TypeConversionUtil.castToType(long.class, value));
	}
    }

    public static final class DevEncodedInserter implements ICommandInserter {
	@Override
	public void insert(final DeviceData dd, final Object value) throws DevFailed {
	    throw new NumberFormatException("Type DevEncoded not supported");

	}
    }

    public static final class StringInserterArray implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object values) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(String[].class, values));
	}
    }

    // insert for boolean array does not exists
    // public class BooleanInserter implements ICommandArrayInserter {
    // @Override
    // public void insert(DeviceData dd, Object... values)
    // throws NumberFormatException {
    // dd.insert(TypeConversionUtil.getBooleanValue(values));
    // }
    //
    // }

    public static final class ShortInserterArray implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object values) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(short[].class, values));

	}
    }

    public static final class CharInserterArray implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object values) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(byte[].class, values));

	}
    }

    public static final class FloatInserterArray implements ICommandInserter {
	@Override
	public void insert(final DeviceData dd, final Object values) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(float[].class, values));
	}

    }

    public static final class UShortInserterArray implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object values) throws DevFailed {
	    dd.insert_us(TypeConversionUtil.castToType(short[].class, values));
	}

    }

    public static final class DoubleInserterArray implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object values) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(double[].class, values));
	}

    }

    // not supported by tangorb
    // public class UCharInserterArray implements ICommandInserter {
    // @Override
    // public void insert(DeviceData dd, Object... values)
    // throws NumberFormatException {
    // // TODO: should convert to Byte
    // dd.insert_uc(TypeConversionUtil.getShortValue(values));
    // }
    //
    // }

    public static final class ULongInserterArray implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object values) throws DevFailed {
	    dd.insert_ul(TypeConversionUtil.castToType(int[].class, values));
	}

    }

    public static final class Long64InserterArray implements ICommandInserter {
	@Override
	public void insert(final DeviceData dd, final Object values) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(long[].class, values));
	}

    }

    public static final class ULong64InserterArray implements ICommandInserter {
	@Override
	public void insert(final DeviceData dd, final Object values) throws DevFailed {
	    dd.insert_u64(TypeConversionUtil.castToType(long[].class, values));
	}

    }

    public static final class IntInserterArray implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object values) throws DevFailed {
	    dd.insert(TypeConversionUtil.castToType(int[].class, values));
	}
    }

    // use short inserter
    // public class CharInserter implements ISpectrumImageInserter {

    public static final class LongStringInserterArray implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object values) throws DevFailed {
	    throw new NumberFormatException("Type LongStringArray not supported");
	}

    }

    public static final class DoubleStringInserterArray implements ICommandInserter {

	@Override
	public void insert(final DeviceData dd, final Object values) throws DevFailed {
	    throw new NumberFormatException("Type DoubleStringArray not supported");
	}

    }
}
