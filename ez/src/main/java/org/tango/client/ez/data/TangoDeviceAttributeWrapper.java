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

import java.lang.reflect.Array;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 05.06.12
 */
public final class TangoDeviceAttributeWrapper extends TangoDataWrapper {
    private final DeviceAttribute data;

    protected TangoDeviceAttributeWrapper(DeviceAttribute data) {
        this.data = data;
    }

    @Override
    public int getType() throws DevFailed {
        return data.getType();
    }

    public boolean extractBoolean() throws DevFailed {
        return data.extractBoolean();
    }

    @Override
    public byte[] extractByteArray() {
        //TODO convert from short
        return new byte[0];
    }

    @Override
    public boolean[] extractBooleanArray() throws DevFailed {
        return data.extractBooleanArray();
    }

    @Override
    public byte[] extractCharArray() throws DevFailed {
        return data.extractCharArray();
    }

    @Override
    public DevEncoded extractDevEncoded() throws DevFailed {
        return data.extractDevEncoded();
    }

    @Override
    public DevEncoded[] extractDevEncodedArray() throws DevFailed {
        return data.extractDevEncodedArray();
    }

    public DevState extractDevState() throws DevFailed {
        return data.extractDevState();
    }

    @Override
    public DevState[] extractDevStateArray() throws DevFailed {
        return data.extractDevStateArray();
    }

    public double extractDouble() throws DevFailed {
        return data.extractDouble();
    }

    public double[] extractDoubleArray() throws DevFailed {
        return data.extractDoubleArray();
    }

    public float extractFloat() throws DevFailed {
        return data.extractFloat();
    }

    public float[] extractFloatArray() throws DevFailed {
        return data.extractFloatArray();
    }

    public int extractLong() throws DevFailed {
        return data.extractLong();
    }

    public long extractLong64() throws DevFailed {
        return data.extractLong64();
    }

    public long[] extractLong64Array() throws DevFailed {
        return data.extractLong64Array();
    }

    public int[] extractLongArray() throws DevFailed {
        return data.extractLongArray();
    }

    public short extractShort() throws DevFailed {
        return data.extractShort();
    }

    public short[] extractShortArray() throws DevFailed {
        return data.extractShortArray();
    }

    @Override
    public short[] extractUCharArray() throws DevFailed {
        return data.extractUCharArray();
    }

    public String extractString() throws DevFailed {
        return data.extractString();
    }

    public String[] extractStringArray() throws DevFailed {
        return data.extractStringArray();
    }

    public short extractUChar() throws DevFailed {
        return data.extractUChar();
    }

    public long extractULong() throws DevFailed {
        return data.extractULong();
    }

    public long extractULong64() throws DevFailed {
        return data.extractULong64();
    }

    public long[] extractULong64Array() throws DevFailed {
        return data.extractULong64Array();
    }

    public long[] extractULongArray() throws DevFailed {
        return data.extractULongArray();
    }

    public int extractUShort() throws DevFailed {
        return data.extractUShort();
    }

    public int[] extractUShortArray() throws DevFailed {
        return data.extractUShortArray();
    }

    @Override
    public DevVarLongStringArray extractDevVarLongStringArray() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public DevVarDoubleStringArray extractDevVarDoubleStringArray() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    public void insert(boolean argin) {
        data.insert(argin);
    }

    @Override
    //TODO test
    public void insert(byte[] argin) {
        short[] shorts = (short[]) Array.newInstance(short.class, argin.length);
        System.arraycopy(argin, 0, shorts, 0, argin.length);
        data.insert(shorts);
    }

    @Override
    public void insert(boolean[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert(boolean[] argin, int dim_x, int dim_y) {
        data.insert(argin, dim_x, dim_y);
    }

    @Override
    public void insert(DevEncoded argin) {
        data.insert(argin);
    }

    public void insert(DevState argin) {
        data.insert(argin);
    }

    @Override
    public void insert(DevVarLongStringArray argin) throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public void insert(DevVarDoubleStringArray argin) throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public void insert(DevState[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert(DevState[] argin, int dim_x, int dim_y) {
        data.insert(argin, dim_x, dim_y);
    }

    public void insert(double argin) {
        data.insert(argin);
    }

    public void insert(double[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert(double[] argin, int dim_x, int dim_y) {
        data.insert(argin, dim_x, dim_y);
    }

    public void insert(float argin) {
        data.insert(argin);
    }

    public void insert(float[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert(float[] argin, int dim_x, int dim_y) {
        data.insert(argin, dim_x, dim_y);
    }

    public void insert(int argin) {
        data.insert(argin);
    }

    public void insert(int[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert(int[] argin, int dim_x, int dim_y) {
        data.insert(argin, dim_x, dim_y);
    }

    public void insert(long argin) {
        data.insert(argin);
    }

    public void insert(long[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert(long[] argin, int dim_x, int dim_y) {
        data.insert(argin, dim_x, dim_y);
    }

    @Override
    public void insert(short[] argin, int dim_x, int dim_y) {
        data.insert(argin, dim_x, dim_y);
    }

    public void insert(short argin) {
        data.insert(argin);
    }

    public void insert(short[] argin) {
        data.insert(argin);
    }

    public void insert(String argin) {
        data.insert(argin);
    }

    public void insert(String[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert(String[] argin, int dim_x, int dim_y) {
        data.insert(argin, dim_x, dim_y);
    }

    public void insert_u64(long argin) {
        data.insert_u64(argin);
    }

    public void insert_u64(long[] argin) {
        data.insert_u64(argin);
    }

    @Override
    public void insert_u64(long[] argin, int dim_x, int dim_y) {
        data.insert_u64(argin, dim_x, dim_y);
    }

    public void insert_uc(byte argin) {
        data.insert_uc(argin);
    }

    @Override
    public void insert_uc(byte[] argin) {
        data.insert_uc(argin);
    }

    @Override
    public void insert_uc(byte[] argin, int dim_x, int dim_y) {
        data.insert_uc(argin, dim_x, dim_y);
    }

    public void insert_uc(short argin) {
        data.insert_uc(argin);
    }

    @Override
    public void insert_uc(short[] argin) {
        data.insert_uc(argin);
    }

    @Override
    public void insert_uc(short[] argin, int dim_x, int dim_y) {
        data.insert_uc(argin, dim_x, dim_y);
    }

    public void insert_ul(int argin) {
        data.insert_ul(argin);
    }

    public void insert_ul(int[] argin) {
        data.insert_ul(argin);
    }

    @Override
    public void insert_ul(int[] argin, int dim_x, int dim_y) {
        data.insert_ul(argin, dim_x, dim_y);
    }

    public void insert_ul(long argin) {
        data.insert_ul(argin);
    }

    public void insert_ul(long[] argin) {
        data.insert_ul(argin);
    }

    @Override
    public void insert_ul(long[] argin, int dim_x, int dim_y) {
        data.insert_ul(argin, dim_x, dim_y);
    }

    public void insert_us(int argin) {
        data.insert_us(argin);
    }

    public void insert_us(int[] argin) {
        data.insert_us(argin);
    }

    @Override
    public void insert_us(int[] argin, int dim_x, int dim_y) {
        data.insert_us(argin, dim_x, dim_y);
    }

    public void insert_us(short argin) {
        data.insert_us(argin);
    }

    public void insert_us(short[] argin) {
        data.insert_us(argin);
    }

    @Override
    public void insert_us(short[] argin, int dim_x, int dim_y) {
        data.insert_us(argin, dim_x, dim_y);
    }

    @Override
    public int getDimX() throws DevFailed {
        //TODO
        return Math.max(data.getDimX(), data.getWrittenDimX());
    }

    @Override
    public int getDimY() throws DevFailed {
        //TODO
        return Math.max(data.getDimY(), data.getWrittenDimY());
    }

    @Override
    public int getNbRead() throws DevFailed {
        return data.getNbRead();
    }
}
