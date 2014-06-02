// +======================================================================
//  $Source$
//
//  Project:   ezTangORB
//
//  Description:  java source code for the simplified TangORB API.
//
//  $Author: ingvord $
//
//  Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,
//                         European Synchrotron Radiation Facility
//                       BP 220, Grenoble 38043
//                       FRANCE
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
