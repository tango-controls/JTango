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

package org.tango.client.ez.data.format;

import fr.esrf.Tango.DevFailed;
import org.tango.client.ez.data.TangoDataWrapper;
import org.tango.client.ez.data.type.TangoDataType;
import org.tango.client.ez.data.type.ValueExtracter;
import org.tango.client.ez.data.type.ValueExtractionException;
import org.tango.client.ez.data.type.ValueInsertionException;

import java.lang.reflect.Array;

import static fr.esrf.TangoDs.TangoConst.*;

/**
 * Implementation based on code from AttributePanel.java in Jive.
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 31.07.12
 */
public class UnknownTangoDataFormat<T> extends TangoDataFormat<T> {
    private final ValueExtracter<Object> commonExtractor = new ValueExtracter<Object>() {
        @Override
        public Object extract(TangoDataWrapper data) throws ValueExtractionException {
            try {
                switch (data.getType()) {
                    case Tango_DEV_STATE: {
                        return data.extractDevStateArray();
                    }
                    case Tango_DEV_UCHAR: {
                        return data.extractUCharArray();
                    }
                    case Tango_DEV_SHORT: {
                        return data.extractShortArray();
                    }

                    case Tango_DEV_BOOLEAN: {
                        return data.extractBooleanArray();
                    }

                    case Tango_DEV_USHORT: {
                        return data.extractUShortArray();
                    }

                    case Tango_DEV_LONG: {
                        return data.extractLongArray();
                    }

                    case Tango_DEV_ULONG: {
                        return data.extractULongArray();
                    }

                    case Tango_DEV_LONG64: {
                        return data.extractLong64Array();
                    }

                    case Tango_DEV_ULONG64: {
                        return data.extractULong64Array();
                    }

                    case Tango_DEV_DOUBLE: {
                        return data.extractDoubleArray();
                    }

                    case Tango_DEV_FLOAT: {
                        return data.extractFloatArray();
                    }

                    case Tango_DEV_STRING: {
                        return data.extractStringArray();
                    }

                    //            case Tango_DEV_ENCODED:
                    //            {
                    //                printIndex = true;
                    //                DevEncoded e = data.extractDevEncoded();
                    //                ret_string.append("Format: " + e.encoded_format + "\n");
                    //                int nbRead = e.encoded_data.length;
                    //                int start = getLimitMin(checkLimit,ret_string,nbRead);
                    //                int end = getLimitMax(checkLimit,ret_string,nbRead,false);
                    //                for (int i = start; i < end; i++) {
                    //                    short vs = (short)e.encoded_data[i];
                    //                    vs = (short)(vs & 0xFF);
                    //                    printArrayItem(ret_string,i,printIndex,Short.toString(vs),false);
                    //                }
                    //            }

                    default: {
                        throw new ValueExtractionException("Unsupported attribute type code=" + data.getType());
                    }
                }
            } catch (DevFailed devFailed) {
                throw new ValueExtractionException(devFailed);
            }
        }
    };


    /**
     * Returned type does not used in insert/extract methods
     *
     * @param devDataType int code of TangoConst.Tango_DEV_XXX
     * @return UnsupportedOperationException
     * @throws UnsupportedOperationException
     */
    @Override
    public TangoDataType<T> getDataType(int devDataType) {
        throw new UnsupportedOperationException("Unknown format does not support getDataType method.");
    }

    protected UnknownTangoDataFormat(int alias, String strAlias) {
        super(alias, strAlias);
    }

    /**
     * Always returns an array of type defined by data.getType()
     * <p/>
     * Uses commonExtractor to generalize code.
     *
     * @param data value container
     * @return an array
     * @throws ValueExtractionException
     */
    @Override
    public T extract(TangoDataWrapper data) throws ValueExtractionException {
        try {
            Object dummy = commonExtractor.extract(data);
            int start = 0;
            int end = data.getNbRead();
            Object result = Array.newInstance(dummy.getClass().getComponentType(), end);
            System.arraycopy(dummy, start, result, start, end);
            //TODO writable?!
            return (T) result;//Object to Object cast
        } catch (DevFailed devFailed) {
            throw new ValueExtractionException(devFailed);
        }
    }

    @Override
    public void insert(TangoDataWrapper data, Object value, int devDataType) throws ValueInsertionException {
    }

    @Override
    public void insert(TangoDataWrapper data, T value, TangoDataType<T> type) throws ValueInsertionException {
    }
}
