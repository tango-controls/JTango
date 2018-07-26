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

import java.io.Serializable;
import java.lang.reflect.Array;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.tango.attribute.AttributeTangoType;
import org.tango.server.IValue;
import org.tango.utils.ArrayUtils;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevFailed;

public final class AttributeValue implements Cloneable, Serializable, IValue<Object> {

    private static final String CANNOT_BE_AN_ATTRIBUTE = " cannot be an attribute";

    /**
     *
     */
    private static final long serialVersionUID = 717857459565709770L;

    private Object value = null;
    private AttrQuality quality = AttrQuality.ATTR_VALID;
    private long time = 0;
    private int xDim = 1;
    private int yDim = 1;

    public AttributeValue() {
        super();
    }

    public AttributeValue(final Object value) throws DevFailed {
        super();
        setValue(value);
        quality = AttrQuality.ATTR_VALID;
        time = System.currentTimeMillis();
    }

    public AttributeValue(final Object value, final AttrQuality quality) throws DevFailed {
        super();
        setValue(value);
        this.quality = quality;
        time = System.currentTimeMillis();
    }

    public AttributeValue(final Object value, final AttrQuality quality, final int xDim, final int yDim) {
        super();
        this.value = value;
        this.quality = quality;
        time = System.currentTimeMillis();
        this.xDim = xDim;
        this.yDim = yDim;
    }

    public AttributeValue(final Object value, final AttrQuality quality, final int xDim, final int yDim, final long time) {
        super();
        this.value = value;
        this.quality = quality;
        this.time = time;
        this.xDim = xDim;
        this.yDim = yDim;
    }

    @Override
    public Object getValue() {
        return value;
    }

    /**
     * Set the value of an attribute. see {@link AttributeTangoType#ATTRIBUTE_CLASSES} for allowed types. Some default
     * dimensions are set automatically:
     * <ul>
     * <li>scalar: x=1, y=0</li>
     * <li>1D array: x=array.length, y=0</li>
     * <li>2D array: x=array[0].length, y=array.length</li>
     * </ul>
     * Time is set to System.currentTimeMillis().
     *
     * @param value
     *            attribute value.
     * @throws DevFailed
     */
    @Override
    public void setValue(final Object value) throws DevFailed {
        if (time==0) // Initialize if not already done
            time = System.currentTimeMillis();
        if (value != null) {
            if (!value.getClass().isArray()) { // SCALAR
                // check if this value can be an attribute value
                if (!AttributeTangoType.ATTRIBUTE_CLASSES.contains(value.getClass())) {
                    throw DevFailedUtils.newDevFailed(value.getClass().getCanonicalName() + CANNOT_BE_AN_ATTRIBUTE);
                }
                setXDim(1);
                setYDim(0);
            } else if (!value.getClass().getComponentType().isArray()) {// SPECTRUM
                // check if this value can be an attribute value
                if (!AttributeTangoType.ATTRIBUTE_CLASSES.contains(value.getClass().getComponentType())) {
                    throw DevFailedUtils.newDevFailed(value.getClass().getCanonicalName() + CANNOT_BE_AN_ATTRIBUTE);
                }
                setXDim(Array.getLength(value));
                setYDim(0);
            } else { // IMAGE
                int y = Array.getLength(value);
                if (y > 0) {
                    final Object xArray = Array.get(value, 0);
                    final int x = Array.getLength(xArray);
                    if (x == 0) {
                        y = 0;
                        // check if this value can be an attribute value
                    } else if (!AttributeTangoType.ATTRIBUTE_CLASSES.contains(xArray.getClass().getComponentType())) {
                        throw DevFailedUtils.newDevFailed(value.getClass().getCanonicalName() + CANNOT_BE_AN_ATTRIBUTE);
                    }
                    setXDim(x);
                } else {
                    setXDim(0);
                }
                setYDim(y);
            }
            this.value = value;
        } else {
            this.value = null;
        }
    }

    /**
     * Set Value and time. cf {@link #setValue(Object)} for details
     *
     * @param value
     * @param time
     * @throws DevFailed
     */
    @Override
    public void setValue(final Object value, final long time) throws DevFailed {
        this.setValue(value);
        this.setTime(time);
    }

    void setValueWithoutDim(final Object value) throws DevFailed {
        this.value = value;
    }

    public AttrQuality getQuality() {
        return AttrQuality.from_int(quality.value());
    }

    /**
     * Set the quality of the attribute {@link AttrQuality}
     *
     * @param quality
     */
    public void setQuality(final AttrQuality quality) {
        // copy value
        this.quality = AttrQuality.from_int(quality.value());
    }

    public long getTime() {
        return time;
    }

    /**
     * Set timestamp. By default, time is set to System.currentTimeMillis() in {@link #setValue(Object)}.
     *
     * @param time
     *            timestamp in milliseconds
     */
    @Override
    public void setTime(final long time) {
        this.time = time;
    }

    public int getXDim() {
        return xDim;
    }

    /**
     * Set x dimension.Must be called after setValue to override default value
     *
     * @param xDim
     */
    public void setXDim(final int xDim) {
        this.xDim = xDim;
    }

    public int getYDim() {
        return yDim;
    }

    /**
     * Set y dimension. Must be called after setValue to override default value
     *
     * @param yDim
     */
    public void setYDim(final int yDim) {
        this.yDim = yDim;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final AttributeValue newValue = (AttributeValue) super.clone();
        newValue.quality = AttrQuality.from_int(quality.value());
        newValue.value = ArrayUtils.deepCopyOf(value);
        return newValue;
    }

    @Override
    public String toString() {
        final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
        return reflectionToStringBuilder.toString();
    }

}
