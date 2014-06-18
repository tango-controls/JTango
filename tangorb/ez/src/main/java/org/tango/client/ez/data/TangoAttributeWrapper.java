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
import fr.esrf.TangoDs.Attribute;
import org.tango.client.ez.util.TangoUtils;

import java.lang.reflect.Field;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 11.06.12
 */
public class TangoAttributeWrapper extends TangoDataWrapper {
    private final Attribute data;

    protected TangoAttributeWrapper(Attribute attr) {
        this.data = attr;
    }

    @Override
    public void insert(boolean argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(byte[] argin) {
        throw new UnsupportedOperationException("Can not insert byte[] from WAttribute");
    }

    @Override
    public void insert(DevState argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
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
    public void insert(double argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(double[] argin) {
        throw new UnsupportedOperationException("Can not insert double[] from WAttribute");
    }

    @Override
    public void insert(float argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(float[] argin) {
        throw new UnsupportedOperationException("Can not insert float[] from WAttribute");
    }

    @Override
    public void insert(int argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(int[] argin) {
        throw new UnsupportedOperationException("Can not insert int[] from WAttribute");
    }

    @Override
    public void insert(long argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(long[] argin) {
        throw new UnsupportedOperationException("Can not insert long[] from WAttribute");
    }

    @Override
    public void insert(short argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(short[] argin) {
        throw new UnsupportedOperationException("Can not insert short[] from WAttribute");
    }

    @Override
    public void insert(String argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(String[] argin) {
        throw new UnsupportedOperationException("Can not insert short[] from WAttribute");
    }

    @Override
    public void insert_u64(long argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert_u64(long[] argin) {
        throw new UnsupportedOperationException("Can not insert long[] from WAttribute");
    }

    @Override
    public void insert_uc(byte argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert_uc(short argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert_ul(int argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert_ul(int[] argin) {
        throw new UnsupportedOperationException("Can not insert int[] from WAttribute");
    }

    @Override
    public void insert_ul(long argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert_ul(long[] argin) {
        throw new UnsupportedOperationException("Can not insert long[] from WAttribute");
    }

    @Override
    public void insert_us(int argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert_us(int[] argin) {
        throw new UnsupportedOperationException("Can not insert int[] from WAttribute");
    }

    @Override
    public void insert_us(short argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert_us(short[] argin) {
        throw new UnsupportedOperationException("Can not insert short[] from WAttribute");
    }

    @Override
    public byte[] extractByteArray() throws DevFailed {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean[] extractBooleanArray() throws DevFailed {
        return new boolean[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public byte[] extractCharArray() throws DevFailed {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public DevEncoded extractDevEncoded() throws DevFailed {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public DevEncoded[] extractDevEncodedArray() throws DevFailed {
        return new DevEncoded[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public DevState[] extractDevStateArray() throws DevFailed {
        return new DevState[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public short[] extractUCharArray() throws DevFailed {
        return new short[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void insert(boolean[] argin) {
        throw new UnsupportedOperationException("Can not insert boolean[] from WAttribute");
    }

    @Override
    public void insert(boolean[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(DevEncoded argin) {
        try {
            data.set_value(argin);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(DevState[] argin) {
        try {
            data.set_value(argin, argin.length);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(DevState[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(double[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(float[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(int[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(long[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(short[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert(String[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert_u64(long[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert_uc(byte[] argin) {
        throw new UnsupportedOperationException("Can not insert byte[] from WAttribute");
    }

    @Override
    public void insert_uc(byte[] argin, int dim_x, int dim_y) {
        throw new UnsupportedOperationException("Can not insert byte[] from WAttribute");
    }

    @Override
    public void insert_uc(short[] argin) {
        throw new UnsupportedOperationException("Can not insert short[] from WAttribute");
    }

    @Override
    public void insert_uc(short[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert_ul(int[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert_ul(long[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert_us(int[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }

    @Override
    public void insert_us(short[] argin, int dim_x, int dim_y) {
        try {
            data.set_value(argin, dim_x, dim_y);
        } catch (DevFailed devFailed) {
            throw new RuntimeException(TangoUtils.convertDevFailedToException(devFailed));
        }
    }


    @Override
    public int getType() throws DevFailed {
        return data.get_data_type();
    }

    @Override
    public boolean extractBoolean() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public DevState extractDevState() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public double extractDouble() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public double[] extractDoubleArray() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public float extractFloat() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public float[] extractFloatArray() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public int extractLong() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public long extractLong64() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public long[] extractLong64Array() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public int[] extractLongArray() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public short extractShort() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public short[] extractShortArray() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public String extractString() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public String[] extractStringArray() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public short extractUChar() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public long extractULong() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public long extractULong64() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public long[] extractULong64Array() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public long[] extractULongArray() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public int extractUShort() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public int[] extractUShortArray() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public DevVarLongStringArray extractDevVarLongStringArray() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public DevVarDoubleStringArray extractDevVarDoubleStringArray() throws DevFailed {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }


    @Override
    public int getDimX() throws DevFailed {
        //TODO make get_x public in Attribute
        try {
            Field fldDimX = data.getClass().getDeclaredField("dim_x");
            int dimX = (Integer) fldDimX.get(data);
            return dimX;
        } catch (NoSuchFieldException e) {
            throw TangoUtils.createDevFailed(e);
        } catch (IllegalAccessException e) {
            throw TangoUtils.createDevFailed(e);
        }
    }

    @Override
    public int getDimY() throws DevFailed {
        //TODO make get_y public in Attribute
        try {
            Field fldDimX = data.getClass().getDeclaredField("dim_y");
            int dimX = (Integer) fldDimX.get(data);
            return dimX;
        } catch (NoSuchFieldException e) {
            throw TangoUtils.createDevFailed(e);
        } catch (IllegalAccessException e) {
            throw TangoUtils.createDevFailed(e);
        }
    }

    @Override
    public int getNbRead() throws DevFailed {
        //TODO
        return -1;
    }
}
