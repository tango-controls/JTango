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
package org.tango.server.attribute;

import java.util.Arrays;

import fr.esrf.Tango.DevError;

class HistoryItem {
    private final AttributeValue readValue;
    private final AttributeValue writeValue;
    private final DevError[] error;

    public HistoryItem(final AttributeValue readValue, final AttributeValue writeValue, final DevError[] error) {
        super();
        this.readValue = readValue;
        this.writeValue = writeValue;
        this.error = Arrays.copyOf(error, error.length);
    }

    public AttributeValue getReadValue() {
        return readValue;
    }

    public AttributeValue getWriteValue() {
        return writeValue;
    }

    public DevError[] getError() {
        return error;
    }

}