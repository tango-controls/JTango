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
 * A device property
 * </p>
 * 
 * <pre>
 * &#064;DeviceProperty
 * private String[] myProp = { &quot;toto&quot; };
 * </pre>
 * 
 * @author ABEILLE
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DeviceProperty {
    /**
     * Default name is field name
     * 
     * @return The attribute name.
     */
    String name() default "";

    /**
     * Description of the property
     * 
     * @return the description
     */
    String description() default "";

    /**
     * The default value if not present in tango db
     * 
     * @return the default value
     */
    String[] defaultValue() default "";

    /**
     * Define if the property is mandatory
     * 
     * @return true or false
     */
    boolean isMandatory() default false;

}
