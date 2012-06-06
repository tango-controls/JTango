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

import org.tango.server.InvocationContext;

/**
 * <p>
 * Defines an interceptor method that interposes on business methods (command, attribute). May be applied to any method
 * with a single parameter of type {@link InvocationContext} and return type is void.
 * </p>
 * 
 * <pre>
 * &#064;AroundInvoke
 * public void intercept(InvocationContext ctx) throws DevFailed { ... }
 * </pre>
 * 
 * <p>
 * A class may not declare more than one <tt>&#064;AroundInvoke</tt> method.
 * </p>
 * 
 * @author ABEILLE
 * 
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AroundInvoke {
}
