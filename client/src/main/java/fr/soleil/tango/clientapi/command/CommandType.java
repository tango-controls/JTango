package fr.soleil.tango.clientapi.command;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import fr.esrf.TangoDs.TangoConst;

public enum CommandType {

    VOID(TangoConst.Tango_DEV_VOID, new CommandInserters.VoidInserter(), new CommandExtractors.VoidExtractor()),
    /**
     *
     */
    BOOLEAN(TangoConst.Tango_DEV_BOOLEAN, new CommandInserters.BooleanInserter(),
            new CommandExtractors.BooleanExtractor()),
            /**
             *
             */
            SHORT(TangoConst.Tango_DEV_SHORT, new CommandInserters.ShortInserter(), new CommandExtractors.ShortExtractor()),
            /**
             *
             */
            LONG(TangoConst.Tango_DEV_LONG, new CommandInserters.IntInserter(), new CommandExtractors.LongExtractor()),
            /**
             *
             */
            LONG64(TangoConst.Tango_DEV_LONG64, new CommandInserters.LongInserter(), new CommandExtractors.Long64Extractor()),
            /**
             *
             */
            FLOAT(TangoConst.Tango_DEV_FLOAT, new CommandInserters.FloatInserter(), new CommandExtractors.FloatExtractor()),
            /**
             *
             */
            DOUBLE(TangoConst.Tango_DEV_DOUBLE, new CommandInserters.DoubleInserter(), new CommandExtractors.DoubleExtractor()),
            /**
             *
             */
            USHORT(TangoConst.Tango_DEV_USHORT, new CommandInserters.UShortInserter(), new CommandExtractors.UShortExtractor()),
            /**
             *
             */
            ULONG(TangoConst.Tango_DEV_ULONG, new CommandInserters.ULongInserter(), new CommandExtractors.ULongExtractor()),
            /**
             *
             */
            ULONG64(TangoConst.Tango_DEV_ULONG64, new CommandInserters.ULong64Inserter(),
                    new CommandExtractors.ULong64Extractor()),
                    /**
                     *
                     */
                    STRING(TangoConst.Tango_DEV_STRING, new CommandInserters.StringInserter(), new CommandExtractors.StringExtractor()),
                    /**
                     *
                     */
                    CONSTSTRING(TangoConst.Tango_CONST_DEV_STRING, new CommandInserters.StringInserter(),
                            new CommandExtractors.StringExtractor()),
                            /**
                             *
                             */
                            UCHAR(TangoConst.Tango_DEV_UCHAR, new CommandInserters.UCharInserter(), new CommandExtractors.UCharExtractor()),
                            /**
                             *
                             */
                            CHAR(TangoConst.Tango_DEV_CHAR, new CommandInserters.UCharInserter(), new CommandExtractors.UCharExtractor()),
                            /**
                             *
                             */
                            CHARARRAY(TangoConst.Tango_DEVVAR_CHARARRAY, new CommandInserters.CharInserterArray(),
                                    new CommandExtractors.CharExtractorArray()),
                                    /**
                                     *
                                     */
                                    LONG64ARRAY(TangoConst.Tango_DEVVAR_LONG64ARRAY, new CommandInserters.LongInserterArray(),
                                            new CommandExtractors.Long64ExtractorArray()),
                                            /**
                                             *
                                             */
                                            ULONG64ARRAY(TangoConst.Tango_DEVVAR_ULONG64ARRAY, new CommandInserters.ULong64InserterArray(),
                                                    new CommandExtractors.ULong64ExtractorArray()),
                                                    /**
                                                     *
                                                     */
                                                    ENCODED(TangoConst.Tango_DEV_ENCODED, new CommandInserters.DevEncodedInserter(),
                                                            new CommandExtractors.DevEncodedExtractor()),
                                                            /**
                                                             *
                                                             */
                                                            SHORTARRAY(TangoConst.Tango_DEVVAR_SHORTARRAY, new CommandInserters.ShortInserterArray(),
                                                                    new CommandExtractors.ShortExtractorArray()),
                                                                    /**
                                                                     *
                                                                     */
                                                                    LONGARRAY(TangoConst.Tango_DEVVAR_LONGARRAY, new CommandInserters.IntInserterArray(),
                                                                            new CommandExtractors.LongExtractorArray()),
                                                                            /**
                                                                             *
                                                                             */
                                                                            FLOATARRAY(TangoConst.Tango_DEVVAR_FLOATARRAY, new CommandInserters.FloatInserterArray(),
                                                                                    new CommandExtractors.FloatExtractorArray()),
                                                                                    /**
                                                                                     *
                                                                                     */
                                                                                    DOUBLEARRAY(TangoConst.Tango_DEVVAR_DOUBLEARRAY, new CommandInserters.DoubleInserterArray(),
                                                                                            new CommandExtractors.DoubleExtractorArray()),
                                                                                            /**
                                                                                             *
                                                                                             */
                                                                                            USHORTARRAY(TangoConst.Tango_DEVVAR_USHORTARRAY, new CommandInserters.UShortInserterArray(),
                                                                                                    new CommandExtractors.UShortExtractorArray()),
                                                                                                    /**
                                                                                                     *
                                                                                                     */
                                                                                                    ULONGARRAY(TangoConst.Tango_DEVVAR_ULONGARRAY, new CommandInserters.ULongInserterArray(),
                                                                                                            new CommandExtractors.ULongExtractorArray()),
                                                                                                            /**
                                                                                                             *
                                                                                                             */
                                                                                                            STRINGARRAY(TangoConst.Tango_DEVVAR_STRINGARRAY, new CommandInserters.StringInserterArray(),
                                                                                                                    new CommandExtractors.StringExtractorArray()),
                                                                                                                    /**
                                                                                                                     *
                                                                                                                     */
                                                                                                                    STATE(TangoConst.Tango_DEV_STATE, new CommandInserters.StateInserter(), new CommandExtractors.StateExtractor()),
    /**
                                                                                                                     *
                                                                                                                     */
    BOLEANARRAY(TangoConst.Tango_DEVVAR_BOOLEANARRAY, new CommandInserters.BooleanInserter(),
            new CommandExtractors.BooleanExtractor());
    // UCHARARRAY(
    // TangoConst.Tango_DEV_UCHAR,
    // new CommandInserters.UCharInserterArray()),
    // // TODO
    // DOUBLESTRINGARRAY(TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY,new
    // CommandInserters().),
    // LONGSTRINGARRAY(TangoConst.Tango_DEVVAR_LONGSTRINGARRAY,new
    // CommandInserters().);

    private static final Map<Integer, ICommandInserter> INSERTERS_MAP = new HashMap<Integer, ICommandInserter>();
    static {
        for (final CommandType s : EnumSet.allOf(CommandType.class)) {
            INSERTERS_MAP.put(s.getDataType(), s.getInserter());
        }
    }
    private static final Map<Integer, ICommandExtractor> EXTRACTORS_MAP = new HashMap<Integer, ICommandExtractor>();
    static {
        for (final CommandType s : EnumSet.allOf(CommandType.class)) {
            EXTRACTORS_MAP.put(s.getDataType(), s.getExtractor());
        }
    }
    private int dataType;
    private ICommandInserter inserter;
    private ICommandExtractor extractor;

    CommandType(final int dataType, final ICommandInserter inserter, final ICommandExtractor extractor) {
        this.dataType = dataType;
        this.inserter = inserter;
        this.extractor = extractor;
    }

    public ICommandInserter getInserter() {
        return inserter;
    }

    public ICommandExtractor getExtractor() {
        return extractor;
    }

    public int getDataType() {
        return dataType;
    }

    public static ICommandInserter getInserterFromDataType(final int dataType) {
        return INSERTERS_MAP.get(dataType);
    }

    public static ICommandExtractor getExtractorFromDataType(final int dataType) {
        return EXTRACTORS_MAP.get(dataType);
    }

    public static int getDataTypeFromInserter(final ICommandInserter inserter) {
        for (final int o : INSERTERS_MAP.keySet()) {
            if (INSERTERS_MAP.get(o).equals(inserter)) {
                return o;
            }
        }
        throw new EnumConstantNotPresentException(CommandType.class, "no inserter found");
    }

    public static int getDataTypeFromExtractor(final ICommandExtractor extractor) {
        for (final int o : EXTRACTORS_MAP.keySet()) {
            if (EXTRACTORS_MAP.get(o).equals(extractor)) {
                return o;
            }
        }
        throw new EnumConstantNotPresentException(CommandType.class, "no inserter found");
    }
}
