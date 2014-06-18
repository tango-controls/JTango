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

import org.tango.client.ez.data.TangoDataWrapper;

/**
 * Base class for all TangoDataTypes
 *
 * @param <T> extracted value type
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 27.04.12
 */
public abstract class TangoDataType<T> {
    private final int tangoDevDataType;
    private final Class<T> targetClazz;
    protected final ValueExtracter<T> extracter;
    protected final ValueInserter<T> inserter;

    private final String strAlias;

    protected TangoDataType(int tango_dev_data_type, String strAlias, Class<T> clazz, ValueExtracter<T> extracter, ValueInserter<T> inserter) {
        this.tangoDevDataType = tango_dev_data_type;
        this.strAlias = strAlias;
        this.targetClazz = clazz;
        this.extracter = extracter;
        this.inserter = inserter;
    }

    public Class<T> getDataType() {
        return targetClazz;
    }

    public abstract T extract(TangoDataWrapper data) throws ValueExtractionException;

    public abstract void insert(TangoDataWrapper data, T value) throws ValueInsertionException;

    public int getAlias() {
        return tangoDevDataType;
    }

    public final String toString() {
        return strAlias;
    }
}
