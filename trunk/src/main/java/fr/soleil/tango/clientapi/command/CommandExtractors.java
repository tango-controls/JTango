package fr.soleil.tango.clientapi.command;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceData;

public final class CommandExtractors {

    public static final class VoidExtractor implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return null;
	}
    }

    public static final class BooleanExtractor implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractBoolean();
	}
    }

    public static final class StringExtractor implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractString();
	}
    }

    public static final class StateExtractor implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractDevState();
	}
    }

    public static final class DoubleExtractor implements ICommandExtractor {

	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractDouble();
	}
    }

    public static final class FloatExtractor implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractFloat();
	}

    }

    public static final class LongExtractor implements ICommandExtractor {

	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractLong();
	}
    }

    public static final class Long64Extractor implements ICommandExtractor {

	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractLong64();
	}
    }

    public static final class ShortExtractor implements ICommandExtractor {

	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractShort();
	}
    }

    public static final class UShortExtractor implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractUShort();
	}
    }

    public static final class ULongExtractor implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractULong();
	}
    }

    public static final class UCharExtractor implements ICommandExtractor {

	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return (byte) dd.extractUChar();
	}
    }

    public static final class ULong64Extractor implements ICommandExtractor {

	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractULong64();
	}
    }

    public static final class StringExtractorArray implements ICommandExtractor {

	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractStringArray();
	}
    }

    public static final class LongExtractorArray implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractLongArray();
	}
    }

    // extract for boolean array does not exists
    // public class BooleanExtractorArray implements ICommandExtractor {
    // @Override
    // public <T> List<T> extract(final DeviceData dd, final Class<T> type)
    // throws DevFailed {
    // return TypeConversionUtil.getResultAsList(type, dd
    // .extractBooleanArray());
    // }
    // }

    public static final class ShortExtractorArray implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractShortArray();
	}
    }

    public static final class CharExtractorArray implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractByteArray();
	}
    }

    public static final class FloatExtractorArray implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractFloatArray();
	}

    }

    public static final class UShortExtractorArray implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractUShortArray();
	}

    }

    public static final class DoubleExtractorArray implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractDoubleArray();
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

    public static final class ULongExtractorArray implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractULongArray();
	}

    }

    public static final class ULong64ExtractorArray implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractULong64Array();
	}
    }

    public static final class Long64ExtractorArray implements ICommandExtractor {
	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return dd.extractLong64Array();
	}
    }

    // public class IntExtractorArray implements ICommandExtractor {
    //
    // @Override
    // public <T> List<T> extract(final DeviceData dd, final Class<T> type)
    // throws DevFailed {
    // return TypeConversionUtil.getResultAsList(type, dd
    // .extract());
    // }
    // }

    // use short inserter
    // public class CharInserter implements ISpectrumImageInserter {

    public static final class DevEncodedExtractor implements ICommandExtractor {

	@Override
	public Object extract(final DeviceData dd) throws DevFailed {
	    return null;
	}
    }

}
