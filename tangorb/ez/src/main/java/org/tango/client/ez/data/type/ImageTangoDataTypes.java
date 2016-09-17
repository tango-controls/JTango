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

import com.google.common.collect.Sets;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.TangoConst;
import org.tango.client.ez.data.TangoDataWrapper;

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
    public static final TangoDataType<TangoImage<float[]>> FLOAT_IMAGE = new ImageTangoDataType<TangoImage<float[]>, float[]>(
            TangoConst.Tango_DEV_FLOAT, "DevFloatImage", (Class<TangoImage<float[]>>)(Class<?>)TangoImage.class, new ValueExtracter<float[]>() {
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
    public static final TangoDataType<TangoImage<double[]>> DOUBLE_IMAGE = new ImageTangoDataType<TangoImage<double[]>, double[]>(
            TangoConst.Tango_DEV_DOUBLE, "DevDoubleImage", (Class<TangoImage<double[]>>)(Class<?>) TangoImage.class, new ValueExtracter<double[]>() {
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
    public static final TangoDataType<TangoImage<short[]>> SHORT_IMAGE = new ImageTangoDataType<TangoImage<short[]>, short[]>(
            TangoConst.Tango_DEV_SHORT, "DevShortImage", (Class<TangoImage<short[]>>) (Class<?>) TangoImage.class, new ValueExtracter<short[]>() {
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
    public static final TangoDataType<TangoImage<int[]>> USHORT_IMAGE = new ImageTangoDataType<TangoImage<int[]>, int[]>(
            TangoConst.Tango_DEV_USHORT, "DevUShortImage", (Class<TangoImage<int[]>>) (Class<?>) TangoImage.class, new ValueExtracter<int[]>() {
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
    public static final TangoDataType<TangoImage<short[]>> UCHAR_IMAGE = new ImageTangoDataType<TangoImage<short[]>, short[]>(
            TangoConst.Tango_DEV_UCHAR, "DevUCharImage", (Class<TangoImage<short[]>>) (Class<?>) TangoImage.class, new ValueExtracter<short[]>() {
        @Override
        public short[] extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractUCharArray();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(devFailed);
            }
        }
    },
            new ValueInserter<short[]>() {
                @Override
                public void insert(TangoDataWrapper data, short[] value, int dimX, int dimY) throws ValueInsertionException {
                    data.insert_us(value, dimX, dimY);
                }
            }
    );
    public static final TangoDataType<TangoImage<int[]>> INT_IMAGE = new ImageTangoDataType<TangoImage<int[]>, int[]>(
            TangoConst.Tango_DEV_LONG, "DevLongImage", (Class<TangoImage<int[]>>) (Class<?>)TangoImage.class, new ValueExtracter<int[]>() {
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
    public static final TangoDataType<TangoImage<long[]>> LONG64_IMAGE = new ImageTangoDataType<TangoImage<long[]>, long[]>(
            TangoConst.Tango_DEV_LONG64, "DevLong64Image", (Class<TangoImage<long[]>>)(Class<?>)TangoImage.class, new ValueExtracter<long[]>() {
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

    private ImageTangoDataTypes() {
    }

    static Collection<? extends TangoDataType<?>> values() {
        return Sets.newHashSet(FLOAT_IMAGE, DOUBLE_IMAGE, SHORT_IMAGE, USHORT_IMAGE, UCHAR_IMAGE, INT_IMAGE, LONG64_IMAGE);
    }

    /**
     * @param <T> 2-dimensional array
     * @param <V> 1-dimensional array
     */
    public static final class ImageTangoDataType<T extends TangoImage<V>, V> extends TangoDataType<T> {
        private final ValueExtracter<V> extracter;
        private final ValueInserter<V> inserter;

        protected ImageTangoDataType(int tango_dev_data_type, String strAlias, Class<T> clazz, ValueExtracter<V> extracter, ValueInserter<V> inserter) {
            super(tango_dev_data_type, strAlias, clazz, null, null);
            this.extracter = extracter;
            this.inserter = inserter;
        }

        public T extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                int dimY = data.getDimY();
                int dimX = data.getDimX();
                Object value = this.extracter.extract(data);//float[]
                return getDataType().cast(new TangoImage<Object>(value, dimX, dimY));
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(devFailed);
            }
        }

        @Override
        public void insert(TangoDataWrapper data, T src) throws ValueInsertionException {
            if(src.getClass().isArray()) {
                TangoImage<V> argin = TangoImage.from2DArray(src);
                this.inserter.insert(data, argin.getData(), argin.getWidth(), argin.getHeight());
            } else
                this.inserter.insert(data, src.getData(), src.getWidth(), src.getHeight());
        }
    }
}
