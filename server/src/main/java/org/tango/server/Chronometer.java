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

/**
 * Calculate elapsed time
 *
 * @author ABEILLE
 *
 */
public final class Chronometer {

    // private static final int TO_SECS = 1000;
    private long startTime = 0;
    private long duration = 0;
    private boolean isOver = true;

    /**
     * Start the chronometer
     *
     * @param duration
     *            the duration in milliseconds
     */
    public void start(final long duration) {
        startTime = System.currentTimeMillis();
        this.duration = duration;
        isOver = false;
    }

    /**
     * Start the chronometer with infinite duration
     *
     */
    public void start() {
        startTime = System.currentTimeMillis();
        duration = 0;
        isOver = false;
    }

    public long getElapsedTime() {
        final long now = System.currentTimeMillis();
        return now - startTime;
    }

    /**
     * stop the chronometer
     */
    public void stop() {
        isOver = true;
    }

    /**
     * Check if the started duration is over
     *
     * @return true if over
     */
    public boolean isOver() {
        if (!isOver) {
            final long now = System.currentTimeMillis();
            isOver = now - startTime >= duration;
        }
        return isOver;
    }
}
