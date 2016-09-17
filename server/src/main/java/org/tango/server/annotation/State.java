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

import org.tango.DeviceState;

/**
 * <p>
 * Manage state of the device.
 * </p>
 * 
 * <pre>
 * &#064;State
 * private {@link DeviceState} state;
 * 
 * public {@link DeviceState} getState(){...}
 * 
 * public void setState({@link DeviceState} state){...}
 * </pre>
 * <p>
 * A class may not declare more than one <tt>&#064;State</tt> method.
 * </p>
 * 
 * @author ABEILLE
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface State {
    /**
     * define if attribute is polled. period must be configured. see {@link Attribute#pollingPeriod()}
     * 
     * @return is polled
     */
    boolean isPolled() default false;

    /**
     * Configure polling period in ms. use only is {@link Attribute#isPolled()} is true
     * 
     * @return polling period
     */
    int pollingPeriod() default 0;
}
