package fr.soleil.tango.clientapi.attribute;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import fr.esrf.TangoDs.TangoConst;

public enum AttributeValueType {

    /**
     *
     */
    BOOLEAN(TangoConst.Tango_DEV_BOOLEAN, new Inserters.BooleanInserter(), new Extractors.BooleanExtractor()),
    /**
     *
     */
    CHAR(TangoConst.Tango_DEV_CHAR, new Inserters.ShortInserter(), new Extractors.ShortExtractor()),
    /**
     *
     */
    DOUBLE(TangoConst.Tango_DEV_DOUBLE, new Inserters.DoubleInserter(), new Extractors.DoubleExtractor()),
    /**
     *
     */
    FLOAT(TangoConst.Tango_DEV_FLOAT, new Inserters.FloatInserter(), new Extractors.FloatExtractor()),
    /**
     *
     */
    INT(TangoConst.Tango_DEV_INT, new Inserters.IntInserter(), new Extractors.LongExtractor()),
    /**
     *
     */
    LONG(TangoConst.Tango_DEV_LONG, new Inserters.IntInserter(), new Extractors.LongExtractor()),
    /**
     *
     */
    LONG64(TangoConst.Tango_DEV_LONG64, new Inserters.LongInserter(), new Extractors.Long64Extractor()),
    /**
     *
     */
    SHORT(TangoConst.Tango_DEV_SHORT, new Inserters.ShortInserter(), new Extractors.ShortExtractor()),
    /**
     *
     */
    STATE(TangoConst.Tango_DEV_STATE, new Inserters.StateInserter(), new Extractors.StateExtractor()),
    /**
     *
     */
    STRING(TangoConst.Tango_DEV_STRING, new Inserters.StringInserter(), new Extractors.StringExtractor()),
    /**
     *
     */
    UCHAR(TangoConst.Tango_DEV_UCHAR, new Inserters.UCharInserter(), new Extractors.UCharExtractor()),
    /**
     *
     */
    ULONG(TangoConst.Tango_DEV_ULONG, new Inserters.ULongInserter(), new Extractors.ULongExtractor()),
    /**
     *
     */
    ULONG64(TangoConst.Tango_DEV_ULONG64, new Inserters.ULong64Inserter(), new Extractors.ULong64Extractor()),
    /**
     *
     */
    USHORT(TangoConst.Tango_DEV_USHORT, new Inserters.UShortInserter(), new Extractors.UShortExtractor()),
    /**
     *
     */
    DEVENCODED(TangoConst.Tango_DEV_ENCODED, new Inserters.DevEncodedInserter(), new Extractors.DevEncodedExtractor()),
    /**
     *
     */
    DEVENUM(TangoConst.Tango_DEV_ENUM, new Inserters.ShortInserter(), new Extractors.ShortExtractor());

    private static final Map<Integer, IExtractor> EXTRACTORS_MAP = new HashMap<Integer, IExtractor>();
    private static final Map<Integer, IInserter> INSERTERS_MAP = new HashMap<Integer, IInserter>();

    static {
        for (final AttributeValueType s : EnumSet.allOf(AttributeValueType.class)) {
            EXTRACTORS_MAP.put(s.getDataType(), s.getExtractor());
        }
    }
    static {
        for (final AttributeValueType s : EnumSet.allOf(AttributeValueType.class)) {
            INSERTERS_MAP.put(s.getDataType(), s.getInserter());
        }
    }

    public static int getDataTypeFromExtractor(final IExtractor extractor) {
        for (final int o : EXTRACTORS_MAP.keySet()) {
            if (EXTRACTORS_MAP.get(o).equals(extractor)) {
                return o;
            }
        }
        throw new EnumConstantNotPresentException(AttributeValueType.class, "no inserter found");
    }

    public static int getDataTypeFromInserter(final IInserter inserter) {
        for (final int o : INSERTERS_MAP.keySet()) {
            if (INSERTERS_MAP.get(o).equals(inserter)) {
                return o;
            }
        }
        throw new EnumConstantNotPresentException(AttributeValueType.class, "no inserter found");
    }

    public static IExtractor getExtractorFromDataType(final int dataType) {
        return EXTRACTORS_MAP.get(dataType);
    }

    public static IInserter getInserterFromDataType(final int dataType) {
        return INSERTERS_MAP.get(dataType);
    }

    private int dataType;

    private IExtractor extrator;

    private IInserter inserter;

    AttributeValueType(final int dataType, final IInserter inserter, final IExtractor extrator) {
        this.dataType = dataType;
        this.extrator = extrator;
        this.inserter = inserter;
    }

    public int getDataType() {
        return dataType;
    }

    public IExtractor getExtractor() {
        return extrator;
    }

    public IInserter getInserter() {
        return inserter;
    }
}
