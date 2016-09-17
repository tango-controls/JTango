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

import fr.esrf.TangoApi.CommandInfo;
import org.tango.client.ez.data.type.TangoDataType;
import org.tango.client.ez.data.type.TangoDataTypes;
import org.tango.client.ez.data.type.UnknownTangoDataType;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 12.10.12
 */
public class TangoCommandInfoWrapper {
    private final CommandInfo info;
    private final TangoDataType<?> typeIn;
    private final TangoDataType<?> typeOut;

    public TangoCommandInfoWrapper(CommandInfo info) throws UnknownTangoDataType {
        this.info = info;
        this.typeIn = TangoDataTypes.forTangoDevDataType(info.in_type);
        this.typeOut = TangoDataTypes.forTangoDevDataType(info.out_type);
    }

    public Class<?> getArginType() {
        return typeIn.getDataType();
    }

    public Class<?> getArgoutType() {
        return typeOut.getDataType();
    }

    public CommandInfo toCommandInfo() {
        return info;
    }
}
