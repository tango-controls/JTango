/**
 * Copyright (C) :     2012
 * <p>
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
 * <p>
 * This file is part of Tango.
 * <p>
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.attribute;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DispLevel;
import fr.esrf.TangoDs.TangoConst;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.tango.attribute.AttributeTangoType;
import org.tango.server.Constants;
import org.tango.server.IConfigurable;
import org.tango.server.PolledObjectConfig;

import java.lang.reflect.Array;

public final class AttributeConfiguration implements PolledObjectConfig, IConfigurable {
    private String name = "";
    private AttrDataFormat format = AttrDataFormat.SCALAR;
    private AttrWriteType writable = AttrWriteType.READ;
    private Class<?> type = String.class;
    private int tangoType = TangoConst.Tango_DEV_STRING;
    private AttributeTangoType enumType = AttributeTangoType.DEVSTRING;
    private DispLevel dispLevel = DispLevel.OPERATOR;
    private int maxX = Integer.MAX_VALUE;
    private int maxY = Integer.MAX_VALUE;
    private boolean isMemorized = false;
    private boolean isMemorizedAtInit = true;
    private int pollingPeriod = 0;
    private boolean isPolled = false;
    private AttributePropertiesImpl attributeProperties = new AttributePropertiesImpl();
    private boolean pushDataReady;
    private boolean pushChangeEvent;
    private boolean checkChangeEvent;
    private boolean pushArchiveEvent;
    private boolean checkArchivingEvent;

    public AttributeConfiguration() {

    }

    public AttributeConfiguration(final AttributeConfiguration config) {
        name = config.name;
        format = config.format;
        writable = config.writable;
        type = config.type;
        dispLevel = config.dispLevel;
        maxX = config.maxX;
        maxY = config.maxY;
        isMemorized = config.isMemorized;
        isMemorizedAtInit = config.isMemorizedAtInit;
        pollingPeriod = config.pollingPeriod;
        isPolled = config.isPolled;
        pushDataReady = config.pushDataReady;
        pushChangeEvent = config.pushChangeEvent;
        checkChangeEvent = config.checkChangeEvent;
        pushArchiveEvent = config.pushArchiveEvent;
        checkArchivingEvent = config.checkArchivingEvent;
        attributeProperties = config.attributeProperties;
    }

    public DispLevel getDispLevel() {
        return dispLevel;
    }

    public void setDispLevel(final DispLevel dispLevel) {
        this.dispLevel = dispLevel;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public AttrDataFormat getFormat() {
        return format;
    }

    public void setFormat(final AttrDataFormat format) {
        this.format = format;
        if (format.equals(AttrDataFormat.SCALAR)) {
            maxX = 1;
            maxY = 0;
        } else if (format.equals(AttrDataFormat.SPECTRUM)) {
            maxY = 0;
        }
    }

    public AttrWriteType getWritable() {
        return writable;
    }

    public void setWritable(final AttrWriteType writable) {
        this.writable = writable;
    }

    public Class<?> getType() {
        return type;
    }

    /**
     * Set the attribute type with Java class. Can be scalar, array or matrix
     *
     * @param type
     * @throws DevFailed
     */
    public void setType(final Class<?> type) throws DevFailed {
        this.type = AttributeTangoType.getTypeFromClass(type).getType();
        enumType = AttributeTangoType.getTypeFromClass(type);
        tangoType = enumType.getTangoIDLType();
        if (type.isArray()) {
            if (type.getComponentType().isArray()) {
                format = AttrDataFormat.IMAGE;
            } else {
                format = AttrDataFormat.SPECTRUM;
                maxY = 0;
            }
        } else {
            format = AttrDataFormat.SCALAR;
            maxX = 1;
            maxY = 0;
        }
    }

    public Class<?> getScalarType() {
        return enumType.getType();
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(final int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(final int maxY) {
        this.maxY = maxY;
    }

    @Override
    public String toString() {
        final ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        sb.append("name", name);
        sb.append("format", format.value());
        final StringBuilder s = new StringBuilder().append(type.getCanonicalName()).append(",").append(enumType)
                .append("=").append(tangoType);
        sb.append("type", s.toString());
        sb.append("writable", writable.value());
        sb.append("dispLevel", dispLevel.value());
        sb.append("isMemorized", isMemorized);
        sb.append("isMemorizedAtInit", isMemorizedAtInit);
        sb.append("isPolled", isPolled);
        if (isPolled) {
            sb.append("pollingPeriod", pollingPeriod);
        }
        sb.appendToString(attributeProperties.toString());
        return sb.toString();
    }

    public boolean isMemorized() {
        return isMemorized;
    }

    public void setMemorized(final boolean isMemorized) {
        this.isMemorized = isMemorized;
    }

    public AttributePropertiesImpl getAttributeProperties() {
        return attributeProperties;
    }

    public void setAttributeProperties(final AttributePropertiesImpl attributeProperties) throws DevFailed {
        this.attributeProperties = new AttributePropertiesImpl(attributeProperties);
        if (this.attributeProperties.getLabel().isEmpty()
                || this.attributeProperties.getLabel().equalsIgnoreCase(Constants.NOT_SPECIFIED)) {
            this.attributeProperties.setLabel(name);
        }
    }

    @Override
    public void persist(final String deviceName) throws DevFailed {
        attributeProperties.persist(deviceName, name);
    }

    @Override
    public void load(final String deviceName) throws DevFailed {
        attributeProperties.load(deviceName, name);
    }

    @Override
    public void clear(final String deviceName) throws DevFailed {
        attributeProperties.clear(deviceName, name);
    }

    public int getTangoType() {
        return tangoType;
    }

    /**
     * Set the attribute type with Tango type.
     *
     * @see TangoConst for possible values
     * @param tangoType
     * @throws DevFailed
     */
    public void setTangoType(final int tangoType, final AttrDataFormat format) throws DevFailed {
        setFormat(format);
        this.tangoType = tangoType;
        enumType = AttributeTangoType.getTypeFromTango(tangoType);
        if (format.equals(AttrDataFormat.SCALAR)) {
            type = enumType.getType();
        } else if (format.equals(AttrDataFormat.SPECTRUM)) {
            type = Array.newInstance(enumType.getType(), 0).getClass();
        } else {
            type = Array.newInstance(enumType.getType(), 0, 0).getClass();
        }
    }

    public boolean isMemorizedAtInit() {
        return isMemorizedAtInit;
    }

    public void setMemorizedAtInit(final boolean isMemorizedAtInit) {
        this.isMemorizedAtInit = isMemorizedAtInit;
    }

    public int getPollingPeriod() {
        return pollingPeriod;
    }

    @Override
    public void setPollingPeriod(final int pollingPeriod) {
        this.pollingPeriod = pollingPeriod;
    }

    public boolean isPolled() {
        return isPolled;
    }

    @Override
    public void setPolled(final boolean isPolled) {
        this.isPolled = isPolled;
    }

    public boolean isPushDataReady() {
        return pushDataReady;
    }

    public void setPushDataReady(final boolean pushDataReady) {
        this.pushDataReady = pushDataReady;
    }

    public boolean isPushChangeEvent() {
        return pushChangeEvent;
    }

    public void setPushChangeEvent(final boolean pushChangeEvent) {
        this.pushChangeEvent = pushChangeEvent;
    }

    public boolean isCheckChangeEvent() {
        return checkChangeEvent;
    }

    public void setCheckChangeEvent(final boolean checkChangeEvent) {
        this.checkChangeEvent = checkChangeEvent;
    }

    public boolean isPushArchiveEvent() {
        return pushArchiveEvent;
    }

    public void setPushArchiveEvent(final boolean pushArchiveEvent) {
        this.pushArchiveEvent = pushArchiveEvent;
    }

    public boolean isCheckArchivingEvent() {
        return checkArchivingEvent;
    }

    public void setCheckArchivingEvent(final boolean checkArchivingEvent) {
        this.checkArchivingEvent = checkArchivingEvent;
    }
}
