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

import fr.esrf.Tango.*;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceData;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 05.06.12
 */
//TODO encapsulate TangoDataType
public abstract class TangoDataWrapper {
    public static TangoDataWrapper create(DeviceAttribute attribute) {
        return new TangoDeviceAttributeWrapper(attribute);
    }

    public static TangoDataWrapper create(DeviceData data) {
        return new TangoDeviceDataWrapper(data);
    }

    //TODO return generic token instead of int
    public abstract int getType() throws DevFailed;

    public abstract boolean extractBoolean() throws DevFailed;

    public abstract DevState extractDevState() throws DevFailed;

    public abstract double extractDouble() throws DevFailed;

    public abstract double[] extractDoubleArray() throws DevFailed;

    public abstract float extractFloat() throws DevFailed;

    public abstract float[] extractFloatArray() throws DevFailed;

    public abstract int extractLong() throws DevFailed;

    public abstract long extractLong64() throws DevFailed;

    public abstract long[] extractLong64Array() throws DevFailed;

    public abstract int[] extractLongArray() throws DevFailed;

    public abstract short extractShort() throws DevFailed;

    public abstract short[] extractShortArray() throws DevFailed;

    public abstract String extractString() throws DevFailed;

    public abstract String[] extractStringArray() throws DevFailed;

    public abstract short extractUChar() throws DevFailed;

    public abstract long extractULong() throws DevFailed;

    public abstract long extractULong64() throws DevFailed;

    public abstract long[] extractULong64Array() throws DevFailed;

    public abstract long[] extractULongArray() throws DevFailed;

    public abstract int extractUShort() throws DevFailed;

    public abstract int[] extractUShortArray() throws DevFailed;

    public abstract DevVarLongStringArray extractDevVarLongStringArray() throws DevFailed;

    public abstract DevVarDoubleStringArray extractDevVarDoubleStringArray() throws DevFailed;

    public abstract void insert(boolean argin);

    public abstract void insert(byte[] argin);

    public abstract void insert(DevState argin);

    public abstract void insert(DevVarLongStringArray argin) throws DevFailed;

    public abstract void insert(DevVarDoubleStringArray argin) throws DevFailed;

    public abstract void insert(double argin);

    public abstract void insert(double[] argin);

    public abstract void insert(float argin);

    public abstract void insert(float[] argin);

    public abstract void insert(int argin);

    public abstract void insert(int[] argin);

    public abstract void insert(long argin);

    public abstract void insert(long[] argin);

    public abstract void insert(short argin);

    public abstract void insert(short[] argin);

    public abstract void insert(String argin);

    public abstract void insert(String[] argin);

    public abstract void insert_u64(long argin);

    public abstract void insert_u64(long[] argin);

    public abstract void insert_uc(byte argin);

    public abstract void insert_uc(short argin);

    public abstract void insert_ul(int argin);

    public abstract void insert_ul(int[] argin);

    public abstract void insert_ul(long argin);

    public abstract void insert_ul(long[] argin);

    public abstract void insert_us(int argin);

    public abstract void insert_us(int[] argin);

    public abstract void insert_us(short argin);

    public abstract void insert_us(short[] argin);

    public abstract byte[] extractByteArray() throws DevFailed;

    public abstract boolean[] extractBooleanArray() throws DevFailed;

    public abstract byte[] extractCharArray() throws DevFailed;

    public abstract DevEncoded extractDevEncoded() throws DevFailed;

    public abstract DevEncoded[] extractDevEncodedArray() throws DevFailed;

    public abstract DevState[] extractDevStateArray() throws DevFailed;

    public abstract short[] extractUCharArray() throws DevFailed;

    public abstract void insert(boolean[] argin);

    public abstract void insert(boolean[] argin, int dim_x, int dim_y);

    public abstract void insert(DevEncoded argin);

    public abstract void insert(DevState[] argin);

    public abstract void insert(DevState[] argin, int dim_x, int dim_y);

    public abstract void insert(double[] argin, int dim_x, int dim_y);

    public abstract void insert(float[] argin, int dim_x, int dim_y);

    public abstract void insert(int[] argin, int dim_x, int dim_y);

    public abstract void insert(long[] argin, int dim_x, int dim_y);

    public abstract void insert(short[] argin, int dim_x, int dim_y);

    public abstract void insert(String[] argin, int dim_x, int dim_y);

    public abstract void insert_u64(long[] argin, int dim_x, int dim_y);

    public abstract void insert_uc(byte[] argin);

    public abstract void insert_uc(byte[] argin, int dim_x, int dim_y);

    public abstract void insert_uc(short[] argin);

    public abstract void insert_uc(short[] argin, int dim_x, int dim_y);

    public abstract void insert_ul(int[] argin, int dim_x, int dim_y);

    public abstract void insert_ul(long[] argin, int dim_x, int dim_y);

    public abstract void insert_us(int[] argin, int dim_x, int dim_y);

    public abstract void insert_us(short[] argin, int dim_x, int dim_y);

    public abstract int getDimX() throws DevFailed;

    public abstract int getDimY() throws DevFailed;


    public abstract int getNbRead() throws DevFailed;
}
