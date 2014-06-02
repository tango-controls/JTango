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
