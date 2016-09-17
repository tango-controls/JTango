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

import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DispLevel;

/**
 * <p>
 * Declare an attribute of a tango device.
 * </p>
 * 
 * Read only attribute called myAttribute of type double:
 * 
 * <pre>
 * &#064;Attribute
 * private double myAttribute;
 * 
 * public double getMyAttribute(){..};
 * </pre>
 * 
 * Read / write attribute called myAttribute of type double:
 * 
 * <pre>
 * &#064;Attribute
 * private double myAttribute;
 * 
 * public double getMyAttribute(){..};
 * public void setMyAttribute(double myAttribute){..};
 * </pre>
 * 
 * <p>
 * Possible types ares:
 * </p>
 * boolean, boolean[],boolean[][],<br>
 * short, short[], short[][], <br>
 * long, long[],long[][], <br>
 * float, float[], float[][],<br>
 * double, double[], double[][],<br>
 * String, String[], String[][], <br>
 * int, int[], int[][], <br>
 * {@link DeviceState}, {@link DeviceState}[], {@link DeviceState}[][], <br>
 * byte, byte[], byte[][],<br>
 * {@link DevEncoded}<br>
 * 
 * 
 * @author ABEILLE
 * 
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {
    /**
     * Default name is field name
     * 
     * @return The attribute name.
     */
    String name() default "";

    /**
     * The command display level. see @link {@link DispLevel}
     * 
     * @return display level
     */
    int displayLevel() default 0;// DispLevel.OPERATOR;

    /**
     * The attribute write value is persisted after each write. For performance issues, it works only for scalar
     * attributes.
     * 
     * @return is memorized
     */
    boolean isMemorized() default false;

    /**
     * The memorized value is written on attribute at init.
     * 
     * @return is memorized at init
     */
    boolean isMemorizedAtInit() default true;

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

    /**
     * 
     * @return max dim x
     */
    int maxDimX() default Integer.MAX_VALUE;

    /**
     * 
     * @return max dim y
     */
    int maxDimY() default Integer.MAX_VALUE;

    /**
     * Data Ready event pushed from device
     * 
     * @return
     */
    boolean pushDataReady() default false;

    /**
     * Change event pushed from device
     * 
     * @return
     */
    boolean pushChangeEvent() default false;

    /**
     * The framework will check event conditions before firing it
     * 
     * @return
     */
    boolean checkChangeEvent() default true;

    /**
     * Archive event pushed from device
     * 
     * @return
     */
    boolean pushArchiveEvent() default false;

    /**
     * The framework will check event conditions before firing it
     * 
     * @return
     */
    boolean checkArchivingEvent() default true;

}
