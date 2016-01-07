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

import org.tango.server.Constants;

/**
 * <p>
 * Default attribute properties. used with {@link Attribute}
 * </p>
 *
 * <pre>
 * &#064;Attribute
 * &#064;AttributeProperties(format = "%6.3f")
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

    String description() default Constants.NO_DESCRIPTION;

    String unit() default Constants.NO_UNIT;

    String standardUnit() default Constants.NO_STD_UNIT;

    String displayUnit() default Constants.NO_DIPLAY_UNIT;

    String format() default "";

    String minValue() default Constants.NOT_SPECIFIED;

    String maxValue() default Constants.NOT_SPECIFIED;

    String minAlarm() default Constants.NOT_SPECIFIED;

    String maxAlarm() default Constants.NOT_SPECIFIED;

    String minWarning() default Constants.NOT_SPECIFIED;

    String maxWarning() default Constants.NOT_SPECIFIED;

    String deltaTime() default Constants.NOT_SPECIFIED;

    String deltaValue() default Constants.NOT_SPECIFIED;

    String periodicEvent() default Constants.PERIOD_1000;

    String changeEventAbsolute() default Constants.NOT_SPECIFIED;

    String changeEventRelative() default Constants.NOT_SPECIFIED;

    String archiveEventAbsolute() default Constants.NOT_SPECIFIED;

    String archiveEventRelative() default Constants.NOT_SPECIFIED;

    String archiveEventPeriod() default Constants.NOT_SPECIFIED;

    String rootAttribute() default Constants.NOT_SPECIFIED;
}
