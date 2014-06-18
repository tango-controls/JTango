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

package org.tango.client.ez.proxy;

import fr.esrf.TangoApi.AttributeInfo;
import org.tango.client.ez.data.format.TangoDataFormat;
import org.tango.client.ez.data.type.TangoDataType;
import org.tango.client.ez.data.type.TangoDataTypes;
import org.tango.client.ez.data.type.UnknownTangoDataType;

/**
 * This class encapsulates {@link AttributeInfo} along with {@link TangoDataFormat}, {@link TangoDataType} and {@link Class}.
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 08.06.12
 */
public final class TangoAttributeInfoWrapper {
    /**
     * Tango attribute info
     */
    protected final AttributeInfo info;

    /**
     * Attribute TangoDataType
     */
    protected final TangoDataType<?> type;

    /**
     * Corresponding to TangoDataType Class
     */
    protected final Class<?> clazz;

    /**
     * Attribute TangoDataFormat
     */
    protected final TangoDataFormat<?> format;

    /**
     * This class is created internally in {@link DeviceProxyWrapper}
     *
     * @param info attribute info
     */
    TangoAttributeInfoWrapper(AttributeInfo info) throws UnknownTangoDataType {
        this.info = info;
        this.format = TangoDataFormat.createForAttrDataFormat(info.data_format);
        this.type = TangoDataTypes.forTangoDevDataType(info.data_type);
        this.clazz = this.format.getDataType(this.type.getAlias()).getDataType();
    }

    public TangoDataFormat<?> getFormat() {
        return format;
    }

    public TangoDataType<?> getType() {
        return type;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public int getMaxDimX() {
        return info.max_dim_x;
    }

    public int getMaxDimY() {
        return info.max_dim_y;
    }

    public AttributeInfo toAttributeInfo() {
        return info;
    }
}
