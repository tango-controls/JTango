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

import org.tango.server.attribute.AttributePropertiesImpl;

/**
 * <p>
 * Default attribute properties. used with {@link Attribute}
 * </p>
 * 
 * <pre>
 * &#064;Attribute
 * &#064;AttributeProperty(format = "%6.3f")
 * private double myAttribute;
 * 
 * public double getMyAttribute(){..};
 * </pre>
 * 
 * @author ABEILLE
 * 
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AttributeProperties {

    String label() default "";

    String description() default AttributePropertiesImpl.NO_DESCRIPTION;

    String unit() default AttributePropertiesImpl.NO_UNIT;

    String standardUnit() default AttributePropertiesImpl.NO_STD_UNIT;

    String displayUnit() default AttributePropertiesImpl.NO_DIPLAY_UNIT;

    String format() default AttributePropertiesImpl.FORMAT;

    String minValue() default AttributePropertiesImpl.NOT_SPECIFIED;

    String maxValue() default AttributePropertiesImpl.NOT_SPECIFIED;

    String minAlarm() default AttributePropertiesImpl.NOT_SPECIFIED;

    String maxAlarm() default AttributePropertiesImpl.NOT_SPECIFIED;

    String minWarning() default AttributePropertiesImpl.NOT_SPECIFIED;

    String maxWarning() default AttributePropertiesImpl.NOT_SPECIFIED;

    String deltaTime() default AttributePropertiesImpl.NOT_SPECIFIED;

    String deltaValue() default AttributePropertiesImpl.NOT_SPECIFIED;
}
