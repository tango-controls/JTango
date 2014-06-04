// +======================================================================
//  $Source$
//
//  Project:   ezTangORB
//
//  Description:  java source code for the simplified TangORB API.
//
//  $Author: ingvord $
//
//  Copyright (C) :      2014
//                         Helmholtz-Zentrum Geesthacht
//                       Max-Planck-Strasse, 1, Geesthacht 21502
//                       GERMANY
// 			http://hzg.de
//
//  This file is part of Tango.
//
//  Tango is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  Tango is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public License
//  along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
//  $Revision: 25721 $
//
// -======================================================================


package org.tango.client.ez.attribute;

import fr.esrf.Tango.AttrQuality;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 17.04.13
 */
public enum Quality {
    VALID(0),
    INVALID(1),
    ALARM(2),
    CHANGING(3),
    WARNING(4);

    private final int id;

    private Quality(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Quality fromAttrQuality(AttrQuality quality) {
        switch (quality.value()) {
            case 0:
                return VALID;
            case 1:
                return INVALID;
            case 2:
                return ALARM;
            case 3:
                return CHANGING;
            case 4:
                return WARNING;
        }
        throw new AssertionError("Should not happen!");
    }
}
