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

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * This class aggregates image data types.
 * <p/>
 * Image is a rectangle matrix.
 * <p/>
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 12.06.12
 */
public class ImageTangoDataTypes {
    public static final String TANGO_IMAGE_EXTRACTER_USES_MULTITHREADING = "tango.image.extracter.use.multithreading";

    private ImageTangoDataTypes() {
    }

    public static final TangoDataType<float[][]> FLOAT_IMAGE = new ImageTangoDataType<float[][], float[]>(
            TangoConst.Tango_DEV_FLOAT, "DevFloatImage", float[][].class, new ValueExtracter<float[]>() {
        @Override
        public float[] extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractFloatArray();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(devFailed);
            }
        }
    },
            new ValueInserter<float[]>() {
                @Override
                public void insert(TangoDataWrapper data, float[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value, dimX, dimY);
                }
            }
    );

    public static final TangoDataType<double[][]> DOUBLE_IMAGE = new ImageTangoDataType<double[][], double[]>(
            TangoConst.Tango_DEV_DOUBLE, "DevDoubleImage", double[][].class, new ValueExtracter<double[]>() {
        @Override
        public double[] extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractDoubleArray();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(devFailed);
            }
        }
    },
            new ValueInserter<double[]>() {
                @Override
                public void insert(TangoDataWrapper data, double[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value, dimX, dimY);
                }
            }
    );

    public static final TangoDataType<short[][]> SHORT_IMAGE = new ImageTangoDataType<short[][], short[]>(
            TangoConst.Tango_DEV_SHORT, "DevShortImage", short[][].class, new ValueExtracter<short[]>() {
        @Override
        public short[] extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractShortArray();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(devFailed);
            }
        }
    },
            new ValueInserter<short[]>() {
                @Override
                public void insert(TangoDataWrapper data, short[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value, dimX, dimY);
                }
            }
    );

    public static final TangoDataType<int[][]> USHORT_IMAGE = new ImageTangoDataType<int[][], int[]>(
            TangoConst.Tango_DEV_USHORT, "DevUShortImage", int[][].class, new ValueExtracter<int[]>() {
        @Override
        public int[] extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractUShortArray();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(devFailed);
            }
        }
    },
            new ValueInserter<int[]>() {
                @Override
                public void insert(TangoDataWrapper data, int[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert_us(value, dimX, dimY);
                }
            }
    );

    public static final TangoDataType<int[][]> INT_IMAGE = new ImageTangoDataType<int[][], int[]>(
            TangoConst.Tango_DEV_LONG, "DevLongImage", int[][].class, new ValueExtracter<int[]>() {
        @Override
        public int[] extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractLongArray();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(devFailed);
            }
        }
    },
            new ValueInserter<int[]>() {
                @Override
                public void insert(TangoDataWrapper data, int[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value, dimX, dimY);
                }
            }
    );

    public static final TangoDataType<long[][]> LONG64_IMAGE = new ImageTangoDataType<long[][], long[]>(
            TangoConst.Tango_DEV_LONG64, "DevLong64Image", long[][].class, new ValueExtracter<long[]>() {
        @Override
        public long[] extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractLong64Array();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(devFailed);
            }
        }
    },
            new ValueInserter<long[]>() {
                @Override
                public void insert(TangoDataWrapper data, long[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert(value, dimX, dimY);
                }
            }
    );

    static Collection<? extends TangoDataType<?>> values() {
        return Sets.newHashSet(FLOAT_IMAGE, DOUBLE_IMAGE, SHORT_IMAGE, USHORT_IMAGE, INT_IMAGE, LONG64_IMAGE);
    }

    public static final class InsertExtractHelper {
        /**
         * Starts several threads to speedup extraction process if dimY/CPUS > THRESHOLD
         * AND System.getProperty(TANGO_IMAGE_EXTRACTER_USE_MULTITHREADING) is set to true.
         *
         * @param value 1-dimensional array
         * @param dimX  x
         * @param dimY  y
         * @return 2-dimensional array:x,y
         */
        public Object extract(final Object value, final int dimX, final int dimY) {
            final Object result = Array.newInstance(value.getClass().getComponentType(), dimY, dimX);

            for (int i = 0, k = 0; i < dimY; i++, k += dimX)
                System.arraycopy(value, k, Array.get(result, i), 0, dimX);

            return result;
        }

        /**
         * @param value 2-dimensional array:x,y
         * @param dimX  x
         * @param dimY  y
         * @param <V>   1-dimensional array
         * @return 1-dimensional array
         */
        public <V> V insert(Object value, int dimX, int dimY) {
            Object result = Array.newInstance(value.getClass().getComponentType().getComponentType(), dimX * dimY);
            for (int i = 0, k = 0; i < dimY; i++, k += dimX)
                System.arraycopy(Array.get(value, i), 0, result, k, dimX);
            return (V) result;
        }
    }

    /**
     * @param <T> 2-dimensional array
     * @param <V> 1-dimensional array
     */
    public static final class ImageTangoDataType<T, V> extends TangoDataType<T> {
        private final ValueExtracter<V> extracter;
        private final ValueInserter<V> inserter;
        private final InsertExtractHelper helper = new InsertExtractHelper();

        protected ImageTangoDataType(int tango_dev_data_type, String strAlias, Class<T> clazz, ValueExtracter<V> extracter, ValueInserter<V> inserter) {
            super(tango_dev_data_type, strAlias, clazz, null, null);
            this.extracter = extracter;
            this.inserter = inserter;
        }

        public T extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                int dimY = data.getDimY();
                int dimX = data.getDimX();
                Object value = this.extracter.extract(data);
                Object result = helper.extract(value, dimX, dimY);
                return getDataType().cast(result);
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(devFailed);
            }
        }

        @Override
        public void insert(TangoDataWrapper data, T src) throws ValueInsertionException {
            Preconditions.checkArgument(src.getClass().isArray());
            int dimX = Array.getLength(Array.get(src, 0));
            int dimY = Array.getLength(src);
            for (int i = 0; i < dimY; i++) {
                Preconditions.checkArgument(Array.getLength(Array.get(src, i)) == dimX, "All sub arrays must be of the same length");
            }

            V value = helper.insert(src, dimX, dimY);
            this.inserter.insert(data, value, dimX, dimY);
        }
    }
}
