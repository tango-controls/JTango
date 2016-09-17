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

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.DevFailed;
import org.tango.client.ez.data.TangoDataWrapper;
import org.tango.client.ez.data.type.TangoDataType;
import org.tango.client.ez.data.type.UnknownTangoDataType;
import org.tango.client.ez.data.type.ValueExtractionException;
import org.tango.client.ez.data.type.ValueInsertionException;

/**
 * This class represents corresponding {@link AttrDataFormat} from Tango Java API.
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 04.06.12
 */
public abstract class TangoDataFormat<T> {
    /**
     * int code of {@link AttrDataFormat}
     */
    protected final int alias;
    private final String strAlias;

    protected TangoDataFormat(int alias, String strAlias) {
        this.alias = alias;
        this.strAlias = strAlias;
    }

    /**
     * @return associated with this instance AttrDataFormat
     */
    public final AttrDataFormat toAttrDataFormat() {
        return AttrDataFormat.from_int(alias);
    }

    public static <T> TangoDataFormat<T> createScalarDataFormat() {
        return createForAttrDataFormat(AttrDataFormat.SCALAR);
    }

    public static <T> TangoDataFormat<T> createSpectrumDataFormat() {
        return createForAttrDataFormat(AttrDataFormat.SPECTRUM);
    }

    public static <T> TangoDataFormat<T> createImageDataFormat() {
        return createForAttrDataFormat(AttrDataFormat.IMAGE);
    }

    /**
     * Creates a new TangoDataFormat.
     *
     * @param alias int alias
     * @param <T>   type of underlying value
     * @return TangoDataFormat
     * @throws NullPointerException if no format was found for alias
     */
    public static <T> TangoDataFormat<T> createForAlias(int alias) {
        switch (alias) {
            case AttrDataFormat._SCALAR:
                return new ScalarTangoDataFormat<T>(alias, "Scalar");
            case AttrDataFormat._SPECTRUM:
                return new SpectrumTangoDataFormat<T>(alias, "Spectrum");
            case AttrDataFormat._IMAGE:
                return new ImageTangoDataFormat<T>(alias, "Image");
            case AttrDataFormat._FMT_UNKNOWN:
            default:
                return new UnknownTangoDataFormat<T>(alias, "Unknown format");
        }
    }

    /**
     * Creates a new TangoDataFormat.
     *
     * @param attrDataFormat AttrDataFormat instance
     * @param <T>            type of underlying value
     * @return TangoDataFormat
     */
    public static <T> TangoDataFormat<T> createForAttrDataFormat(AttrDataFormat attrDataFormat) {
        return createForAlias(attrDataFormat.value());
    }

    /**
     * Returns a match for int code provided. The match is an instance of TangoDataType.
     * So for Spectrum data format int code for double matches to TangoDataTypes.DOUBLE_ARR.
     *
     * @param devDataType int code of TangoConst.Tango_DEV_XXX
     * @return appropriate TangoDataType
     */
    //TODO replace int with enum
    public abstract TangoDataType<T> getDataType(int devDataType) throws UnknownTangoDataType;

    /**
     * @param data value container
     * @return extracted value
     * @throws ValueExtractionException
     */
    public T extract(TangoDataWrapper data) throws ValueExtractionException {
        try {
            int devDataType = data.getType();
            TangoDataType<T> type = getDataType(devDataType);
            return type.extract(data);
        } catch (DevFailed e) {
            throw new ValueExtractionException(e);
        } catch (UnknownTangoDataType e) {
            throw new ValueExtractionException(e);
        }
    }

    /**
     * @param data        value container
     * @param value
     * @param devDataType we need this because usually data is being passed does not contain type information
     * @throws org.tango.client.ez.data.type.ValueInsertionException
     *
     */
    public void insert(TangoDataWrapper data, T value, int devDataType) throws ValueInsertionException {
        try {
            TangoDataType<T> type = getDataType(devDataType);
            type.insert(data, value);
        } catch (UnknownTangoDataType unknownTangoDataType) {
            throw new ValueInsertionException(unknownTangoDataType);
        }
    }

    /**
     * @param data  value container
     * @param value
     * @param type  we need this because usually data is being passed does not contain type information
     * @throws ValueInsertionException
     */
    public void insert(TangoDataWrapper data, T value, TangoDataType<T> type) throws ValueInsertionException {
        type.insert(data, value);
    }

    @Override
    public String toString() {
        return strAlias;
    }
}
