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

package org.tango.client.ez.util;

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.ErrSeverity;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 18.06.12
 */
public class TangoUtilsTest {
    @Test
    public void testCreateDevError_StringReason() throws Exception {
        DevError result = TangoUtils.createDevError("Test reason", "failed due testing");

        assertEquals("Test reason", result.reason);
        assertEquals("failed due testing", result.desc);
        assertSame(ErrSeverity.ERR, result.severity);
        assertTrue(result.origin.startsWith("[org.tango.client.ez.util.TangoUtilsTest.testCreateDevError_StringReason"));
    }

    @Test
    public void testCreateDevError_ThrowableReason() throws Exception {
        Throwable exception = new Exception("Ooops!", new Exception("failed due testing"));
        DevError result = TangoUtils.createDevError(exception);

        assertEquals("Ooops!", result.reason);
        assertEquals("failed due testing", result.desc);
        assertSame(ErrSeverity.ERR, result.severity);
        assertTrue(result.origin.startsWith("[org.tango.client.ez.util.TangoUtilsTest.testCreateDevError_ThrowableReason"));
    }
}
