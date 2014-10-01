//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: ingvord $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision: $
//
//-======================================================================
package fr.esrf.TangoApi;

import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;

/**
 * This interface provides a way for users to read from {@link DevicePipe} objects in a convenient way.
 *
 * Note that inner blobs are considered as top level blobs, i.e.
 *
 * <pre>
 *     Blob = {123, 3.14, Blob {"Hello"," world"}, "!!!"}
 * </pre>
 *
 * must be read as:
 *
 * <pre>
 *     pipe.nextInt();pipe.nextDouble();pipe.nextString();pipe.nextString();pipe.nextString()
 * </pre>
 *
 * the result of the code above is obviously: 123,3.14,Hello, World,!!!
 * <p>
 * Also note that this interface narrows down possible output types to the java types, i.e. if one needs to read
 * DevUShort one should invoke nextInt()[or nextLong()]
 * </p>
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 01.10.14
 */
public interface PipeScanner {
    boolean hasNext();
    //TODO can we avoid throws?
    boolean nextBoolean() throws DevFailed;
    byte nextByte() throws DevFailed;
    char nextChar() throws DevFailed;
    short nextShort() throws DevFailed;
    int nextInt() throws DevFailed;
    long nextLong() throws DevFailed;
    float nextFloat() throws DevFailed;
    double nextDouble() throws DevFailed;
    String nextString() throws DevFailed;
    DevState nextState() throws DevFailed;
    DevEncoded nextEncoded() throws DevFailed;

    <T> void nextArray(T[] target, int size) throws DevFailed;
}
