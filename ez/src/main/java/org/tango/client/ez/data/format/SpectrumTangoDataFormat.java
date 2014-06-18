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

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import fr.esrf.TangoDs.TangoConst;
import org.tango.client.ez.data.type.TangoDataType;
import org.tango.client.ez.data.type.TangoDataTypes;
import org.tango.client.ez.data.type.UnknownTangoDataType;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 05.06.12
 */
public final class SpectrumTangoDataFormat<T> extends TangoDataFormat<T> {
    private static final BiMap<Integer, Integer> typesMapping = HashBiMap.create();

    public SpectrumTangoDataFormat(int alias, String strAlias) {
        super(alias, strAlias);
    }

    static {
        typesMapping.put(TangoConst.Tango_DEV_STRING, TangoConst.Tango_DEVVAR_STRINGARRAY);
        typesMapping.put(TangoConst.Tango_DEV_DOUBLE, TangoConst.Tango_DEVVAR_DOUBLEARRAY);
        typesMapping.put(TangoConst.Tango_DEV_FLOAT, TangoConst.Tango_DEVVAR_FLOATARRAY);
        typesMapping.put(TangoConst.Tango_DEV_SHORT, TangoConst.Tango_DEVVAR_SHORTARRAY);
        typesMapping.put(TangoConst.Tango_DEV_LONG, TangoConst.Tango_DEVVAR_LONGARRAY);
        typesMapping.put(TangoConst.Tango_DEV_LONG64, TangoConst.Tango_DEVVAR_LONG64ARRAY);
        typesMapping.put(TangoConst.Tango_DEV_CHAR, TangoConst.Tango_DEVVAR_CHARARRAY);
        typesMapping.put(TangoConst.Tango_DEV_USHORT, TangoConst.Tango_DEVVAR_USHORTARRAY);
        typesMapping.put(TangoConst.Tango_DEV_ULONG, TangoConst.Tango_DEVVAR_ULONGARRAY);
        typesMapping.put(TangoConst.Tango_DEV_ULONG64, TangoConst.Tango_DEVVAR_ULONG64ARRAY);
        //TODO other
    }

    /**
     * Looks for any possible match Scalar -> Spectrum, Spectrum -> Scalar
     *
     * @param devDataType alias ({@link TangoConst}.Tango_DEV_XXX)
     * @return TangoDataType
     * @throws NullPointerException if no mapping was found
     * @throws org.tango.client.ez.data.type.UnknownTangoDataType
     *
     */
    @Override
    public TangoDataType<T> getDataType(int devDataType) throws UnknownTangoDataType {
        Integer typeMapping = typesMapping.get(devDataType);
        if (typeMapping == null) {
            typeMapping = typesMapping.inverse().get(devDataType);
        }
        Preconditions.checkNotNull(typeMapping);
        return TangoDataTypes.forTangoDevDataType(typeMapping);
    }
}
