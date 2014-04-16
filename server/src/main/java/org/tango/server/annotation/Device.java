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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Declare a class as a Tango Device.
 * </p>
 * 
 * <pre>
 * &#064;Device
 * public class MyTangoDevice{...}
 * </pre>
 * 
 * This annotation is mandatory.
 * 
 * @author ABEILLE
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Device {

    /**
     * Declares the transaction granularity {@link TransactionType} This class with be created with default ctr. Default
     * value
     * 
     * @return transaction type
     */
    TransactionType transactionType() default TransactionType.DEVICE;

    /**
     * The device type returned by "info"
     * 
     * @return The device type
     */
    String deviceType() default "Uninitialised";
}
