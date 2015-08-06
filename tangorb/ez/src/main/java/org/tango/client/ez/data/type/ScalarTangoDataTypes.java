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
import fr.esrf.Tango.*;
import fr.esrf.TangoDs.TangoConst;
import org.tango.client.ez.data.EnumDevState;
import org.tango.client.ez.data.TangoDataWrapper;
import org.tango.client.ez.util.TangoUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * This class aggregates all scalar data types.
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 04.06.12
 */
public class ScalarTangoDataTypes {
    public static final String DEV_VAR_LONG_STRING_ARRAY1 = "DevVarLongStringArray";
    public static final TangoDataType<DevVarLongStringArray> DEV_VAR_LONG_STRING_ARRAY = new ScalarTangoDataType<DevVarLongStringArray>(
            TangoConst.Tango_DEVVAR_LONGSTRINGARRAY, DEV_VAR_LONG_STRING_ARRAY1, DevVarLongStringArray.class,
            new ValueExtracter<DevVarLongStringArray>() {
                @Override
                public DevVarLongStringArray extract(TangoDataWrapper data) throws ValueExtractionException {
                    try {
                        return data.extractDevVarLongStringArray();
                    } catch (DevFailed devFailed) {
                        throw new ValueExtractionException(devFailed);
                    }
                }
            }, new ValueInserter<DevVarLongStringArray>() {
        @Override
        public void insert(TangoDataWrapper data, DevVarLongStringArray value, int dimX, int dimY) throws ValueInsertionException {
            try {
                data.insert(value);
            } catch (DevFailed devFailed) {
                throw new ValueInsertionException(devFailed);
            }
        }
    }
    );
    public static final String DEV_VAR_DOUBLE_STRING_ARRAY1 = "DevVarDoubleStringArray";
    public static final TangoDataType<DevVarDoubleStringArray> DEV_VAR_DOUBLE_STRING_ARRAY = new ScalarTangoDataType<DevVarDoubleStringArray>(
            TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY, DEV_VAR_DOUBLE_STRING_ARRAY1, DevVarDoubleStringArray.class,
            new ValueExtracter<DevVarDoubleStringArray>() {
                @Override
                public DevVarDoubleStringArray extract(TangoDataWrapper data) throws ValueExtractionException {
                    try {
                        return data.extractDevVarDoubleStringArray();
                    } catch (DevFailed devFailed) {
                        throw new ValueExtractionException(devFailed);
                    }
                }
            }, new ValueInserter<DevVarDoubleStringArray>() {
        @Override
        public void insert(TangoDataWrapper data, DevVarDoubleStringArray value, int dimX, int dimY) throws ValueInsertionException {
            try {
                data.insert(value);
            } catch (DevFailed devFailed) {
                throw new ValueInsertionException(devFailed);
            }
        }
    }
    );
    public static final String DEV_STATE = "DevState";
    public static final TangoDataType<EnumDevState> STATE = new ScalarTangoDataType<EnumDevState>(
            TangoConst.Tango_DEV_STATE, DEV_STATE, EnumDevState.class, new ValueExtracter<EnumDevState>() {
        @Override
        public EnumDevState extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                DevState state = data.extractDevState();
                return EnumDevState.forAlias(state.value());
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
            }
        }
    }, new ValueInserter<EnumDevState>() {
        @Override
        public void insert(TangoDataWrapper data, EnumDevState value, int dimX, int dimY) throws ValueInsertionException {
            data.insert(value.toDevState());
        }
    }
    );
    public static final String DEV_VOID = "DevVoid";
    public static final TangoDataType<Void> VOID = new ScalarTangoDataType<Void>(
            TangoConst.Tango_DEV_VOID, DEV_VOID, Void.class, new ValueExtracter<Void>() {
        @Override
        public Void extract(TangoDataWrapper data) throws ValueExtractionException {
            return null;
        }
    }, new ValueInserter<Void>() {
        @Override
        public void insert(TangoDataWrapper data, Void value, int dimX, int dimY) throws ValueInsertionException {
        }
    }
    );
    public static final String DEV_BOOLEAN = "DevBoolean";
    public static final TangoDataType<Boolean> BOOLEAN = new ScalarTangoDataType<Boolean>(
            TangoConst.Tango_DEV_BOOLEAN, DEV_BOOLEAN, Boolean.class, new ValueExtracter<Boolean>() {
        @Override
        public Boolean extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractBoolean();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
            }
        }
    }, new ValueInserter<Boolean>() {
        @Override
        public void insert(TangoDataWrapper data, Boolean value, int dimX, int dimY) throws ValueInsertionException {
            data.insert(value);
        }
    }
    );
    public static final String DEV_DOUBLE = "DevDouble";
    public static final TangoDataType<Double> DOUBLE = new ScalarTangoDataType<Double>(
            TangoConst.Tango_DEV_DOUBLE, DEV_DOUBLE, Double.class, new ValueExtracter<Double>() {
        @Override
        public Double extract(TangoDataWrapper input) throws ValueExtractionException {
            try {
                return input.extractDouble();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
            }
        }
    }, new ValueInserter<Double>() {
        @Override
        public void insert(TangoDataWrapper data, Double value, int dimX, int dimY) throws ValueInsertionException {
            data.insert(value);
        }
    }
    );
    public static final String DEV_FLOAT = "DevFloat";
    public static final TangoDataType<Float> FLOAT = new ScalarTangoDataType<Float>(
            TangoConst.Tango_DEV_FLOAT, DEV_FLOAT, Float.class, new ValueExtracter<Float>() {
        @Override
        public Float extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractFloat();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
            }
        }
    }, new ValueInserter<Float>() {
        @Override
        public void insert(TangoDataWrapper data, Float value, int dimX, int dimY) throws ValueInsertionException {
            data.insert(value);
        }
    }
    );
    public static final String DEV_SHORT = "DevShort";
    public static final TangoDataType<Short> SHORT = new ScalarTangoDataType<Short>(
            TangoConst.Tango_DEV_SHORT, DEV_SHORT, Short.class, new ValueExtracter<Short>() {
        @Override
        public Short extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractShort();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
            }
        }
    }, new ValueInserter<Short>() {
        @Override
        public void insert(TangoDataWrapper data, Short value, int dimX, int dimY) throws ValueInsertionException {
            data.insert(value);
        }
    }
    );
    public static final String DEV_USHORT = "DevUShort";
    public static final TangoDataType<Integer> U_SHORT = new ScalarTangoDataType<Integer>(
            TangoConst.Tango_DEV_USHORT, DEV_USHORT, Integer.class, new ValueExtracter<Integer>() {
        @Override
        public Integer extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractUShort();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
            }
        }
    }, new ValueInserter<Integer>() {
        @Override
        public void insert(TangoDataWrapper data, Integer value, int dimX, int dimY) throws ValueInsertionException {
            data.insert(value);
        }
    }
    );
    public static final String DEV_LONG = "DevLong";
    public static final TangoDataType<Integer> INT = new ScalarTangoDataType<Integer>(
            TangoConst.Tango_DEV_LONG, DEV_LONG, Integer.class, new ValueExtracter<Integer>() {
        @Override
        public Integer extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractLong();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
            }
        }
    }, new ValueInserter<Integer>() {
        @Override
        public void insert(TangoDataWrapper data, Integer value, int dimX, int dimY) throws ValueInsertionException {
            data.insert(value);
        }
    }
    );
    public static final String DEV_ULONG = "DevULong";
    public static final TangoDataType<Long> U_INT = new ScalarTangoDataType<Long>(
            TangoConst.Tango_DEV_ULONG, DEV_ULONG, Long.class, new ValueExtracter<Long>() {
        @Override
        public Long extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractULong();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
            }
        }
    }, new ValueInserter<Long>() {
        @Override
        public void insert(TangoDataWrapper data, Long value, int dimX, int dimY) throws ValueInsertionException {
            data.insert_ul(value);
        }
    }
    );
    public static final String DEV_LONG64 = "DevLong64";
    public static final TangoDataType<Long> LONG = new ScalarTangoDataType<Long>(
            TangoConst.Tango_DEV_LONG64, DEV_LONG64, Long.class, new ValueExtracter<Long>() {
        @Override
        public Long extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractLong64();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
            }
        }
    }, new ValueInserter<Long>() {
        @Override
        public void insert(TangoDataWrapper data, Long value, int dimX, int dimY) throws ValueInsertionException {
            data.insert(value);
        }
    }
    );
    public static final String DEV_ULONG64 = "DevULong64";
    public static final TangoDataType<Long> U_LONG = new ScalarTangoDataType<Long>(
            TangoConst.Tango_DEV_ULONG64, DEV_ULONG64, Long.class, new ValueExtracter<Long>() {
        @Override
        public Long extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractULong64();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
            }
        }
    }, new ValueInserter<Long>() {
        @Override
        public void insert(TangoDataWrapper data, Long value, int dimX, int dimY) throws ValueInsertionException {
            data.insert_u64(value);
        }
    }
    );
    public static final String DEV_STRING = "DevString";
    public static final TangoDataType<String> STRING = new ScalarTangoDataType<String>(
            TangoConst.Tango_DEV_STRING, DEV_STRING, String.class, new ValueExtracter<String>() {
        @Override
        public String extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return data.extractString();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
            }
        }
    }, new ValueInserter<String>() {
        @Override
        public void insert(TangoDataWrapper data, String value, int dimX, int dimY) throws ValueInsertionException {
            data.insert(value);
        }
    }
    );
    public static final String DEV_UCHAR = "DevUChar";
    public static final TangoDataType<Character> U_CHAR = new ScalarTangoDataType<Character>(
            TangoConst.Tango_DEV_UCHAR, DEV_UCHAR, Character.class, new ValueExtracter<Character>() {
        @Override
        public Character extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                return (char) data.extractUChar();
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
            }
        }
    }, new ValueInserter<Character>() {
        @Override
        public void insert(TangoDataWrapper data, Character value, int dimX, int dimY) throws ValueInsertionException {
            data.insert((short) value.charValue());
        }
    }
    );
    public static final String DEV_ENCODED = "DevEncoded";
    /**
     * This only supports JPEG_GRAY8 format, i.e. use BufferedImage.TYPE_BYTE_GRAY and read images as jpeg to extract data
     */
    public static final TangoDataType<BufferedImage> ENCODED = new TangoDataType<BufferedImage>(
            TangoConst.Tango_DEV_ENCODED, DEV_ENCODED, BufferedImage.class, null, null) {
        public static final String ENCODED_FORMAT = "JPEG_GRAY8";

        @Override
        public BufferedImage extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                DevEncoded devEncoded = data.extractDevEncoded();
                Preconditions.checkArgument(devEncoded.encoded_format.equals(ENCODED_FORMAT), "Only JPEG_GRAY8 encoded format is supported.");
                ByteArrayInputStream bais = new ByteArrayInputStream(devEncoded.encoded_data);
                BufferedImage value = ImageIO.read(bais);
                return value;
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(TangoUtils.convertDevFailedToException(devFailed));
            } catch (IOException e) {
                throw new ValueExtractionException(e);
            }
        }

        @Override
        public void insert(TangoDataWrapper data, BufferedImage value) throws ValueInsertionException {
            if (value.getType() != BufferedImage.TYPE_BYTE_GRAY) {
                BufferedImage converted = new BufferedImage(value.getWidth(), value.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                converted.getGraphics().drawImage(value, 0, 0, null);
                value = converted;
            }
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(value, "jpeg", baos);
                data.insert(new DevEncoded(ENCODED_FORMAT, baos.toByteArray()));
            } catch (IOException e) {
                throw new ValueInsertionException(e);
            }
        }
    };

    private ScalarTangoDataTypes() {
    }

    static Collection<? extends TangoDataType<?>> values() {
        //TODO remove warning
        return Sets.newHashSet(ENCODED, STATE, VOID, BOOLEAN, STRING, SHORT, U_SHORT, U_CHAR, INT, U_INT, LONG, U_LONG, FLOAT, DOUBLE, DEV_VAR_LONG_STRING_ARRAY, DEV_VAR_DOUBLE_STRING_ARRAY);
    }

    public final static class ScalarTangoDataType<T> extends TangoDataType<T> {
        protected ScalarTangoDataType(int tango_dev_data_type, String strAlias, Class<T> clazz, ValueExtracter<T> tValueExtracter, ValueInserter<T> tValueInserter) {
            super(tango_dev_data_type, strAlias, clazz, tValueExtracter, tValueInserter);
        }

        @Override
        public T extract(TangoDataWrapper data) throws ValueExtractionException {
            return extracter.extract(data);
        }

        @Override
        public void insert(TangoDataWrapper data, T value) throws ValueInsertionException {
            inserter.insert(data, value, 1, 0);
        }
    }
}
