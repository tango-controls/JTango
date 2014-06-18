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

import fr.esrf.TangoDs.TangoConst;

/**
 * This enum contains items corresponded to {@link TangoConst}.XXX_EVENT.
 * Item of this enum is passed to {@link DeviceProxyWrapper#subscribeToEvent(String, TangoEvent)}
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 07.06.12
 */
public enum TangoEvent {
    CHANGE(TangoConst.CHANGE_EVENT),
    PERIODIC(TangoConst.PERIODIC_EVENT),
    ARCHIVE(TangoConst.ARCHIVE_EVENT),
    USER(TangoConst.USER_EVENT);
    //TODO other events

    private final int alias;

    private TangoEvent(int alias) {
        this.alias = alias;
    }

    public int getAlias() {
        return alias;
    }
}
