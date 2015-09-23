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

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;

/**
 * Exported Exception.
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 07.06.12
 */
public class TangoProxyException extends Exception {
    public final String device;

    public final String reason;
    public final String desc;
    public final String severity;
    public final String origin;

    private final DevFailed devFailed;

    public TangoProxyException(String device, DevFailed devFailed) {
        this.device = device;

        DevError error = devFailed.errors[0];
        this.reason = error.reason;
        this.desc = error.desc;
        this.severity = error.severity.toString();
        this.origin = error.origin;

        this.devFailed = devFailed;
    }

    public TangoProxyException(String device, Throwable cause) {
        super(cause);
        this.device = device;

        this.reason = cause.toString();
        this.severity = ErrSeverity.ERR.toString();
        this.desc = cause.getLocalizedMessage();
        this.origin = cause.getStackTrace()[0].toString();

        this.devFailed = null;
    }

    public TangoProxyException(String device, String msg) {
        super(msg);
        this.device = device;

        this.reason = "Exception";
        this.severity = ErrSeverity.ERR.toString();
        this.desc = msg;
        this.origin = Thread.currentThread().getStackTrace()[1].toString();

        this.devFailed = null;
    }

    @Override
    public String getMessage() {
        return String.format("%s:%s[%s]", device, reason, desc);
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    @Override
    public String toString() {
        if (devFailed != null) {
            StringBuilder result = new StringBuilder(
                    String.format("ProxyException in %s", device));


            result.append(String.format("\n%s: %s\n\t%s\n\t%s", severity, reason, desc, origin));
            for (int i = 1; i < devFailed.errors.length; ++i) {
                result.append("\n\n");
                DevError error = devFailed.errors[i];
                result.append(
                        String.format("%s: %s\n\t%s\n\t%s", error.severity.toString(), error.reason, error.desc, error.origin));
            }
            return result.toString();
        } else {
            return String.format("%s: %s\n\t%s\n\t%s", severity, reason, desc, origin);
        }
    }
}
