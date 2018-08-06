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
package org.tango.server.annotation;

/**
 * Possible values for a tango device transaction
 * 
 * @author ABEILLE
 * 
 */
public enum TransactionType {
    /**
     * One client request per server (that may contain several classes). WARNING: can lead to performance issues
     */
    SERVER,
    /**
     * One client request per device class (that may contain several devices). WARNING: can lead to performance issues
     */
    CLASS,
    /**
     * One client request per device. WARNING: can lead to performance issues.
     */
    DEVICE,
    /**
     * One client request per attribute or command. WARNING: can lead to performance issues
     */
    ATTRIBUTE_COMMAND,
    /**
     * One client request per attribute. WARNING: can lead to performance issues
     */
    ATTRIBUTE,
    /**
     * One client request per command. WARNING: can lead to performance issues
     */
    COMMAND,

    /**
     * All client requests can be done at the same time.
     */
    NONE
}
