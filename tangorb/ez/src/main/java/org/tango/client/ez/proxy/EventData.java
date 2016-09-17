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

/**
 * Container for event data.
 * <p/>
 * This class contains a value passed through event and a time in milliseconds.
 * <p/>
 * An instance of this class is passed to {@link TangoEventListener#onEvent(EventData)}.
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 08.06.12
 */
public final class EventData<T> {
    private final T value;
    private final long time;

    public EventData(T value, long time) {
        this.value = value;
        this.time = time;
    }

    public T getValue() {
        return value;
    }

    /**
     * @return time in millis
     */
    public long getTime() {
        return time;
    }
}
