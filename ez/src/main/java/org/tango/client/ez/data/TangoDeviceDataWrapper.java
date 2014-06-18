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
import fr.esrf.TangoApi.DeviceData;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 05.06.12
 */
public final class TangoDeviceDataWrapper extends TangoDataWrapper {
    private final DeviceData data;

    protected TangoDeviceDataWrapper(DeviceData data) {
        this.data = data;
    }

    @Override
    public void insert(DevVarLongStringArray argin) throws DevFailed {
        data.insert(argin);
    }

    @Override
    public void insert(DevVarDoubleStringArray argin) throws DevFailed {
        data.insert(argin);
    }

    @Override
    public int getType() throws DevFailed {
        return data.getType();
    }

    @Override
    public boolean extractBoolean() {
        return data.extractBoolean();
    }

    @Override
    public byte[] extractByteArray() {
        return data.extractByteArray();
    }

    @Override
    public boolean[] extractBooleanArray() throws DevFailed {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] extractCharArray() throws DevFailed {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public DevEncoded extractDevEncoded() throws DevFailed {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public DevEncoded[] extractDevEncodedArray() throws DevFailed {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public DevState[] extractDevStateArray() throws DevFailed {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public short[] extractUCharArray() throws DevFailed {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert(boolean[] argin) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert(boolean[] argin, int dim_x, int dim_y) {
        //TODO
        throw new UnsupportedOperationException();

    }

    @Override
    public void insert(DevEncoded argin) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert(DevState[] argin) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert(DevState[] argin, int dim_x, int dim_y) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert(double[] argin, int dim_x, int dim_y) {
        data.insert(argin);
    }

    @Override
    public void insert(float[] argin, int dim_x, int dim_y) {
        data.insert(argin);
    }

    @Override
    public void insert(int[] argin, int dim_x, int dim_y) {
        data.insert(argin);
    }

    @Override
    public void insert(long[] argin, int dim_x, int dim_y) {
        data.insert(argin);
    }

    @Override
    public void insert(short[] argin, int dim_x, int dim_y) {
        data.insert(argin);
    }

    @Override
    public void insert(String[] argin, int dim_x, int dim_y) {
        data.insert(argin);
    }

    @Override
    public void insert_u64(long[] argin, int dim_x, int dim_y) {
        data.insert_u64(argin);
    }

    @Override
    public void insert_uc(byte[] argin) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert_uc(byte[] argin, int dim_x, int dim_y) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert_uc(short[] argin) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert_uc(short[] argin, int dim_x, int dim_y) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert_ul(int[] argin, int dim_x, int dim_y) {
        data.insert_ul(argin);
    }

    @Override
    public void insert_ul(long[] argin, int dim_x, int dim_y) {
        data.insert_ul(argin);
    }

    @Override
    public void insert_us(int[] argin, int dim_x, int dim_y) {
        data.insert(argin);
    }

    @Override
    public void insert_us(short[] argin, int dim_x, int dim_y) {
        data.insert_us(argin);
    }

    @Override
    public int getDimX() throws DevFailed {
        //TODO
        return -1;
    }

    @Override
    public int getDimY() throws DevFailed {
        //TODO
        return -1;
    }

    @Override
    public int getNbRead() {
        //TODO
        return -1;
    }

    @Override
    public DevState extractDevState() {
        return data.extractDevState();
    }

    @Override
    public double extractDouble() {
        return data.extractDouble();
    }

    @Override
    public double[] extractDoubleArray() {
        return data.extractDoubleArray();
    }

    @Override
    public float extractFloat() {
        return data.extractFloat();
    }

    @Override
    public float[] extractFloatArray() {
        return data.extractFloatArray();
    }

    @Override
    public int extractLong() {
        return data.extractLong();
    }

    @Override
    public long extractLong64() {
        return data.extractLong64();
    }

    @Override
    public long[] extractLong64Array() {
        return data.extractLong64Array();
    }

    @Override
    public int[] extractLongArray() {
        return data.extractLongArray();
    }

    @Override
    public short extractShort() {
        return data.extractShort();
    }

    @Override
    public short[] extractShortArray() {
        return data.extractShortArray();
    }

    @Override
    public String extractString() {
        return data.extractString();
    }

    @Override
    public String[] extractStringArray() {
        return data.extractStringArray();
    }

    @Override
    public short extractUChar() {
        return data.extractUChar();
    }

    @Override
    public long extractULong() {
        return data.extractULong();
    }

    @Override
    public long extractULong64() {
        return data.extractULong64();
    }

    @Override
    public long[] extractULong64Array() {
        return data.extractULong64Array();
    }

    @Override
    public long[] extractULongArray() {
        return data.extractULongArray();
    }

    @Override
    public int extractUShort() {
        return data.extractUShort();
    }

    @Override
    public int[] extractUShortArray() {
        return data.extractUShortArray();
    }

    @Override
    public DevVarLongStringArray extractDevVarLongStringArray() throws DevFailed {
        return data.extractLongStringArray();
    }

    @Override
    public DevVarDoubleStringArray extractDevVarDoubleStringArray() throws DevFailed {
        return data.extractDoubleStringArray();
    }

    @Override
    public void insert(boolean argin) {
        data.insert(argin);
    }

    @Override
    public void insert(byte[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert(DevState argin) {
        data.insert(argin);
    }

    @Override
    public void insert(double argin) {
        data.insert(argin);
    }

    @Override
    public void insert(double[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert(float argin) {
        data.insert(argin);
    }

    @Override
    public void insert(float[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert(int argin) {
        data.insert(argin);
    }

    @Override
    public void insert(int[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert(long argin) {
        data.insert(argin);
    }

    @Override
    public void insert(long[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert(short argin) {
        data.insert(argin);
    }

    @Override
    public void insert(short[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert(String argin) {
        data.insert(argin);
    }

    @Override
    public void insert(String[] argin) {
        data.insert(argin);
    }

    @Override
    public void insert_u64(long argin) {
        data.insert_u64(argin);
    }

    @Override
    public void insert_u64(long[] argin) {
        data.insert_u64(argin);
    }

    @Override
    public void insert_uc(byte argin) {
        data.insert_uc(argin);
    }

    @Override
    public void insert_uc(short argin) {
        data.insert_uc(argin);
    }

    @Override
    public void insert_ul(int argin) {
        data.insert_ul(argin);
    }

    @Override
    public void insert_ul(int[] argin) {
        data.insert_ul(argin);
    }

    @Override
    public void insert_ul(long argin) {
        data.insert_ul(argin);
    }

    @Override
    public void insert_ul(long[] argin) {
        data.insert_ul(argin);
    }

    @Override
    public void insert_us(int argin) {
        data.insert_us(argin);
    }

    @Override
    public void insert_us(int[] argin) {
        data.insert_us(argin);
    }

    @Override
    public void insert_us(short argin) {
        data.insert_us(argin);
    }

    @Override
    public void insert_us(short[] argin) {
        data.insert_us(argin);
    }
}
