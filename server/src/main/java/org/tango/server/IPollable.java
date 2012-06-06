/**
 * Copyright (C) :     2012
 *
 * 	Synchrotron Soleil
 * 	L'Orme des merisiers
 * 	Saint Aubin
 * 	BP48
 * 	91192 GIF-SUR-YVETTE CEDEX
 *
 * This file is part of Tango.
 *
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server;

import fr.esrf.Tango.DevFailed;

/**
 * An object that can be polled
 * 
 * @author ABEILLE
 * 
 */
public interface IPollable {

    String getName();

    int getPollingPeriod();

    boolean isPolled();

    void configurePolling(final int pollingPeriod) throws DevFailed;

    void resetPolling() throws DevFailed;

    int getPollRingDepth();

    void setPollRingDepth(final int pollRingDepth);

    String getLastDevFailed();

    double getExecutionDuration();

    double getLastUpdateTime();

    double getDeltaTime();

    void setPollingStats(double executionDuration, double lastUpdateTime, double deltaTime);

}
