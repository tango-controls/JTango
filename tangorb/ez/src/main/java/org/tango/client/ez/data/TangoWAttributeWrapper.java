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

package org.tango.client.ez.data;

import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoDs.WAttribute;
import org.tango.client.ez.util.TangoUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 11.06.12
 */
//TODO implement insert(array) by calling insert(array,int,int)
public final class TangoWAttributeWrapper extends TangoAttributeWrapper {
    private final WAttribute data;

    public TangoWAttributeWrapper(WAttribute attr) {
        super(attr);
        this.data = attr;
    }

    @Override
    public boolean extractBoolean() throws DevFailed {
        return data.getBooleanWriteValue();
    }

    @Override
    public DevState extractDevState() throws DevFailed {
        return data.getStateWriteValue();
    }

    @Override
    public double extractDouble() throws DevFailed {
        return data.getDoubleWriteValue();
    }

    @Override
    public double[] extractDoubleArray() throws DevFailed {
        //TODO make get_double_value public in Attribute
        try {
            Method mtdGetDoubleValue = data.getClass().getDeclaredMethod("get_double_value");
            double[] result = (double[]) mtdGetDoubleValue.invoke(data);
            return result;
        } catch (NoSuchMethodException e) {
            throw TangoUtils.createDevFailed(e);
        } catch (IllegalAccessException e) {
            throw TangoUtils.createDevFailed(e);
        } catch (InvocationTargetException e) {
            throw TangoUtils.createDevFailed(e);
        }
    }

    @Override
    public float extractFloat() throws DevFailed {
        throw new DevFailed("Can not extract float from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract float from WAttribute", "Can not extract double[] from WAttribute")
        });
    }

    @Override
    public float[] extractFloatArray() throws DevFailed {
        //TODO make get_float_value public in Attribute
        try {
            Method mtdGetFloatValue = data.getClass().getDeclaredMethod("get_float_value");
            float[] result = (float[]) mtdGetFloatValue.invoke(data);
            return result;
        } catch (NoSuchMethodException e) {
            throw TangoUtils.createDevFailed(e);
        } catch (IllegalAccessException e) {
            throw TangoUtils.createDevFailed(e);
        } catch (InvocationTargetException e) {
            throw TangoUtils.createDevFailed(e);
        }
    }

    @Override
    public int extractLong() throws DevFailed {
        return data.getLongWriteValue();
    }

    @Override
    public long extractLong64() throws DevFailed {
        return data.getLong64WriteValue();
    }

    @Override
    public long[] extractLong64Array() throws DevFailed {
        throw new DevFailed("Can not extract long[] from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract long[] from WAttribute", "Can not extract double[] from WAttribute")
        });
    }

    @Override
    public int[] extractLongArray() throws DevFailed {
        throw new DevFailed("Can not extract int[] from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract int[] from WAttribute", "Can not extract double[] from WAttribute")
        });
    }

    @Override
    public short extractShort() throws DevFailed {
        return data.getShortWriteValue();
    }

    @Override
    public short[] extractShortArray() throws DevFailed {
        throw new DevFailed("Can not extract short[] from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract short[] from WAttribute", "Can not extract double[] from WAttribute")
        });
    }

    @Override
    public String extractString() throws DevFailed {
        return data.getStringWriteValue();
    }

    @Override
    public String[] extractStringArray() throws DevFailed {
        throw new DevFailed("Can not extract String[] from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract String[] from WAttribute", "Can not extract String[] from WAttribute")
        });
    }

    @Override
    public short extractUChar() throws DevFailed {
        throw new DevFailed("Can not extract UChar from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract UChar from WAttribute", "Can not extract UChar from WAttribute")
        });
    }

    @Override
    public long extractULong() throws DevFailed {
        return data.getULongWriteValue();
    }

    @Override
    public long extractULong64() throws DevFailed {
        return data.getULong64WriteValue();
    }

    @Override
    public long[] extractULong64Array() throws DevFailed {
        throw new DevFailed("Can not extract ULong64Array from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract ULong64Array from WAttribute", "Can not extract ULong64Array from WAttribute")
        });
    }

    @Override
    public long[] extractULongArray() throws DevFailed {
        throw new DevFailed("Can not extract ULongArray from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract ULongArray from WAttribute", "Can not extract ULongArray from WAttribute")
        });
    }

    @Override
    public int extractUShort() throws DevFailed {
        return data.getUShortWriteValue();
    }

    @Override
    public int[] extractUShortArray() throws DevFailed {
        throw new DevFailed("Can not extract UShortArray from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract UShortArray from WAttribute", "Can not extract UShortArray from WAttribute")
        });
    }

    @Override
    public byte[] extractByteArray() throws DevFailed {
        throw new DevFailed("Can not extract ByteArray from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract ByteArray from WAttribute", "Can not extract ByteArray from WAttribute")
        });
    }

    @Override
    public boolean[] extractBooleanArray() throws DevFailed {
        throw new DevFailed("Can not extract BooleanArray from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract BooleanArray from WAttribute", "Can not extract BooleanArray from WAttribute")
        });
    }

    @Override
    public byte[] extractCharArray() throws DevFailed {
        throw new DevFailed("Can not extract CharArray from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract CharArray from WAttribute", "Can not extract CharArray from WAttribute")
        });
    }

    @Override
    public DevEncoded extractDevEncoded() throws DevFailed {
        throw new DevFailed("Can not extract DevEncoded from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract DevEncoded from WAttribute", "Can not extract DevEncoded from WAttribute")
        });
    }

    @Override
    public DevEncoded[] extractDevEncodedArray() throws DevFailed {
        throw new DevFailed("Can not extract DevEncodedArray from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract DevEncodedArray from WAttribute", "Can not extract DevEncodedArray from WAttribute")
        });
    }

    @Override
    public DevState[] extractDevStateArray() throws DevFailed {
        throw new DevFailed("Can not extract DevStateArray from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract DevStateArray from WAttribute", "Can not extract DevStateArray from WAttribute")
        });
    }

    @Override
    public short[] extractUCharArray() throws DevFailed {
        throw new DevFailed("Can not extract UCharArray from WAttribute", new DevError[]{
                TangoUtils.createDevError("Can not extract UCharArray from WAttribute", "Can not extract UCharArray from WAttribute")
        });
    }

}
