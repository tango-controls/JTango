// +======================================================================
//   $Source$
//
//   Project:   ezTangORB
//
//   Description:  java source code for the simplified TangORB API.
//
//   $Author: Igor Khokhriakov <igor.khokhriakov@hzg.de> $
//
//   Copyright (C) :      2014
//                        Helmholtz-Zentrum Geesthacht
//                        Max-Planck-Strasse, 1, Geesthacht 21502
//                        GERMANY
//                        http://hzg.de
//
//   This file is part of Tango.
//
//   Tango is free software: you can redistribute it and/or modify
//   it under the terms of the GNU Lesser General Public License as published by
//   the Free Software Foundation, either version 3 of the License, or
//   (at your option) any later version.
//
//   Tango is distributed in the hope that it will be useful,
//   but WITHOUT ANY WARRANTY; without even the implied warranty of
//   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//   GNU Lesser General Public License for more details.
//
//   You should have received a copy of the GNU Lesser General Public License
//   along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
//  $Revision: 25721 $
//
// -======================================================================

package org.tango.client.ez.data.type;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.TangoConst;
import org.tango.client.ez.data.TangoDataWrapper;
import org.tango.client.ez.util.TangoUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Collection;

/**
 * This class aggregates spectrum data types.
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 04.06.12
 */
public class SpectrumTangoDataTypes {
    private SpectrumTangoDataTypes() {
    }

    public static final TangoDataType<String[]> STRING_ARR = new SpectrumTangoDataType<String[]>(
            TangoConst.Tango_DEVVAR_STRINGARRAY, "DevVarStringArr", String[].class,
            new ValueExtracter<String[]>() {
                @Override
                public String[] extract(TangoDataWrapper data) throws ValueExtractionException {
                    try {
                        return data.extractStringArray();
                    } catch (DevFailed devFailed) {
                        throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
                    }
                }
            },
            new ValueInserter<String[]>() {
                @Override
                public void insert(TangoDataWrapper data, String[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value);
                }
            }
    );

    public static final TangoDataType<double[]> DOUBLE_ARR = new SpectrumTangoDataType<double[]>(
            TangoConst.Tango_DEVVAR_DOUBLEARRAY, "DevVarDoubleArr", double[].class,
            new ValueExtracter<double[]>() {
                @Override
                public double[] extract(TangoDataWrapper data) throws ValueExtractionException {
                    try {
                        return data.extractDoubleArray();
                    } catch (DevFailed devFailed) {
                        throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
                    }
                }
            },
            new ValueInserter<double[]>() {
                @Override
                public void insert(TangoDataWrapper data, double[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value);
                }
            }
    );

    public static final TangoDataType<float[]> FLOAT_ARR = new SpectrumTangoDataType<float[]>(
            TangoConst.Tango_DEVVAR_FLOATARRAY, "DevVarFloatArr", float[].class,
            new ValueExtracter<float[]>() {
                @Override
                public float[] extract(TangoDataWrapper data) throws ValueExtractionException {
                    try {
                        return data.extractFloatArray();
                    } catch (DevFailed devFailed) {
                        throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
                    }
                }
            },
            new ValueInserter<float[]>() {
                @Override
                public void insert(TangoDataWrapper data, float[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value);
                }
            }
    );

    public static final TangoDataType<short[]> SHORT_ARR = new SpectrumTangoDataType<short[]>(
            TangoConst.Tango_DEVVAR_SHORTARRAY, "DevVarShortArr", short[].class,
            new ValueExtracter<short[]>() {
                @Override
                public short[] extract(TangoDataWrapper data) throws ValueExtractionException {
                    try {
                        return data.extractShortArray();
                    } catch (DevFailed devFailed) {
                        throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
                    }
                }
            },
            new ValueInserter<short[]>() {
                @Override
                public void insert(TangoDataWrapper data, short[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value);
                }
            }
    );

    public static final TangoDataType<int[]> INT_ARR = new SpectrumTangoDataType<int[]>(
            TangoConst.Tango_DEVVAR_LONGARRAY, "DevVarLongArr", int[].class,
            new ValueExtracter<int[]>() {
                @Override
                public int[] extract(TangoDataWrapper data) throws ValueExtractionException {
                    try {
                        return data.extractLongArray();
                    } catch (DevFailed devFailed) {
                        throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
                    }
                }
            },
            new ValueInserter<int[]>() {
                @Override
                public void insert(TangoDataWrapper data, int[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value);
                }
            }
    );

    public static final TangoDataType<long[]> LONG_ARR = new SpectrumTangoDataType<long[]>(
            TangoConst.Tango_DEVVAR_LONG64ARRAY, "DevVarLong64Arr", long[].class,
            new ValueExtracter<long[]>() {
                @Override
                public long[] extract(TangoDataWrapper data) throws ValueExtractionException {
                    try {
                        return data.extractLong64Array();
                    } catch (DevFailed devFailed) {
                        throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
                    }
                }
            },
            new ValueInserter<long[]>() {
                @Override
                public void insert(TangoDataWrapper data, long[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value);
                }
            }
    );


    public static final String UTF_8 = "UTF-8";
    public static final TangoDataType<char[]> CHAR_ARR = new SpectrumTangoDataType<char[]>(
            TangoConst.Tango_DEVVAR_CHARARRAY, "DevVarCharArr", char[].class,
            new ValueExtracter<char[]>() {
                @Override
                public char[] extract(TangoDataWrapper data) throws ValueExtractionException {
                    try {
                        //TODO creating temporary String object seems to be not the best way
                        return new String(data.extractCharArray(), UTF_8).toCharArray();
                    } catch (DevFailed devFailed) {
                        throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
                    } catch (UnsupportedEncodingException e) {
                        throw new ValueExtractionException(e);
                    }
                }
            },
            new ValueInserter<char[]>() {
                @Override
                public void insert(TangoDataWrapper data, char[] value, int dimX, int dimY) throws ValueInsertionException {
                    try {
                        //TODO creating temporary String object seems to be not the best way
                        data.insert(new String(value).getBytes(UTF_8));
                    } catch (UnsupportedEncodingException e) {
                        throw new ValueInsertionException(e);
                    }
                }
            }
    );

    public static final TangoDataType<int[]> USHORT_ARR = new SpectrumTangoDataType<int[]>(
            TangoConst.Tango_DEVVAR_USHORTARRAY, "DevVarUShortArr", int[].class,
            new ValueExtracter<int[]>() {
                @Override
                public int[] extract(TangoDataWrapper data) throws ValueExtractionException {
                    try {
                        return data.extractUShortArray();
                    } catch (DevFailed devFailed) {
                        throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
                    }
                }
            },
            new ValueInserter<int[]>() {
                @Override
                public void insert(TangoDataWrapper data, int[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value);
                }
            }
    );

    public static final TangoDataType<long[]> UINT_ARR = new SpectrumTangoDataType<long[]>(
            TangoConst.Tango_DEVVAR_ULONGARRAY, "DevVarULongArr", long[].class,
            new ValueExtracter<long[]>() {
                @Override
                public long[] extract(TangoDataWrapper data) throws ValueExtractionException {
                    try {
                        return data.extractULongArray();
                    } catch (DevFailed devFailed) {
                        throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
                    }
                }
            },
            new ValueInserter<long[]>() {
                @Override
                public void insert(TangoDataWrapper data, long[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value);
                }
            }
    );

    public static final TangoDataType<long[]> ULONG_ARR = new SpectrumTangoDataType<long[]>(
            TangoConst.Tango_DEVVAR_ULONG64ARRAY, "DevVarULong64Arr", long[].class,
            new ValueExtracter<long[]>() {
                @Override
                public long[] extract(TangoDataWrapper data) throws ValueExtractionException {
                    try {
                        return data.extractULong64Array();
                    } catch (DevFailed devFailed) {
                        throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
                    }
                }
            },
            new ValueInserter<long[]>() {
                @Override
                public void insert(TangoDataWrapper data, long[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value);
                }
            }
    );


    static Collection<? extends TangoDataType<?>> values() {
        return Sets.newHashSet(STRING_ARR, DOUBLE_ARR, FLOAT_ARR, SHORT_ARR, INT_ARR, LONG_ARR, CHAR_ARR, USHORT_ARR, UINT_ARR, ULONG_ARR);
    }

    public static final class SpectrumTangoDataType<T> extends TangoDataType<T> {
        protected SpectrumTangoDataType(int tango_dev_data_type, String strAlias, Class<T> clazz, ValueExtracter<T> tValueExtracter, ValueInserter<T> tValueInserter) {
            super(tango_dev_data_type, strAlias, clazz, tValueExtracter, tValueInserter);
        }

        @Override
        public T extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                Object src = extracter.extract(data);
                int newLength = data.getDimX() > -1 ? data.getDimX() : Array.getLength(src);
                if (data.getDimY() > 0) {
                    newLength *= data.getDimY();
                }
                Object result = Array.newInstance(src.getClass().getComponentType(), newLength);
                System.arraycopy(src, 0, result, 0, newLength);
                return (T) result;
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(devFailed);
            }
        }

        @Override
        public void insert(TangoDataWrapper data, T value) throws ValueInsertionException {
            Preconditions.checkArgument(value.getClass().isArray());
            inserter.insert(data, value, Array.getLength(value), 0);
        }
    }
}
