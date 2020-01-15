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
package org.tango.server.dynamic.attribute;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeInfo;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.tango.clientapi.InsertExtractUtils;
import fr.soleil.tango.clientapi.TangoGroupAttribute;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.StatUtils;
import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.IAttributeBehavior;
import org.tango.utils.DevFailedUtils;
import org.tango.utils.TangoUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Attribute to group several attributes. 2 options: <br>
 * <ul>
 * <li>Read all attribute values</li>
 * <li>Write on a group of attributes, read average for scalar attributes.</li>
 * </ul>
 * <br>
 * All attributes must be numbers or boolean (but
 * not string) and of the same format. Write is possible only if at least one attribute is writable.NB1:for spectrum and
 * images, if not all same dimensions, some zeros will be added. NB2: read value for images may be not relevant if not
 * all attributes have the same dimensions.
 *
 * @author ABEILLE
 */
public class GroupAttribute implements IAttributeBehavior {

    private final String name;
    private final TangoGroupAttribute attributeGroup;
    private final String[] attributeNames;
    private final boolean isExternalRead;
    private AttrDataFormat attributeFormat = AttrDataFormat.SCALAR;
    private AttrWriteType attributeWritable = AttrWriteType.READ;
    private DeviceAttribute[] readValues;
    private boolean isMean = true;

    /**
     * @param groupAttributeName the name of this attribute
     * @param isExternalRead     if true, the value has to be set with {@link #setReadValue(DeviceAttribute[])}
     * @param attributeNames     the attributes in the group
     * @throws DevFailed
     */
    public GroupAttribute(final String groupAttributeName, final boolean isExternalRead, final String... attributeNames)
            throws DevFailed {
        name = groupAttributeName;
        this.attributeNames = attributeNames;
        this.isExternalRead = isExternalRead;
        attributeGroup = new TangoGroupAttribute(attributeNames);
        attributeWritable = AttrWriteType.READ;
        init();
    }

    /**
     * @param groupAttributeName
     * @param isExternalRead
     * @param isMean             If true, the attribute value will contain the average. Else, it will contains raw values
     * @param attributeNames
     * @throws DevFailed
     */
    public GroupAttribute(final String groupAttributeName, final boolean isExternalRead, boolean isMean, final String... attributeNames) throws DevFailed {
        this.isMean = isMean;
        name = groupAttributeName;
        this.attributeNames = attributeNames;
        this.isExternalRead = isExternalRead;
        attributeGroup = new TangoGroupAttribute(attributeNames);
        attributeWritable = AttrWriteType.READ;
        init();
    }

    private void init() throws DevFailed {
        AttrDataFormat firstAttributeFormat = AttrDataFormat.SCALAR;
        for (int i = 0; i < attributeNames.length; i++) {
            String attributeName = TangoUtil.getAttributeName(attributeNames[i]);
            if (attributeName == null) {
                throw DevFailedUtils.newDevFailed("cannot retrieve " + attributeNames[i]);
            }
            final AttributeInfo info = attributeGroup.getGroup().getDevice(attributeNames[i])
                    .get_attribute_info(attributeName);
            if (info.data_type == TangoConst.Tango_DEV_STRING) {
                throw DevFailedUtils.newDevFailed(attributeNames[i] + " is a String, not supported");
            }
            if (i == 0) {
                firstAttributeFormat = info.data_format;
            } else {
                if (!info.data_format.equals(firstAttributeFormat)) {
                    throw DevFailedUtils.newDevFailed("All attributes must have the same format");
                }
                if (isMean && !info.writable.equals(AttrWriteType.READ)) {
                    // this attribute will be writable if at least one attribute is writable
                    attributeWritable = AttrWriteType.READ_WRITE;
                }
            }
        }
        if (isMean) {
            attributeFormat = firstAttributeFormat;
        } else {
            // composition of raw values
            if (firstAttributeFormat.equals(AttrDataFormat.SCALAR))
                attributeFormat = AttrDataFormat.SPECTRUM;
            else if (firstAttributeFormat.equals(AttrDataFormat.SPECTRUM)) {
                attributeFormat = AttrDataFormat.IMAGE;
            } else {
                throw DevFailedUtils.newDevFailed("image format not supported");
            }
        }
    }


    @Override
    public AttributeConfiguration getConfiguration() throws DevFailed {
        final AttributeConfiguration config = new AttributeConfiguration();
        config.setName(name);
        config.setTangoType(TangoConst.Tango_DEV_DOUBLE, attributeFormat);
        config.setWritable(attributeWritable);
        final AttributePropertiesImpl props = new AttributePropertiesImpl();
        if (isMean) {
            props.setDescription("manage attributes: " + Arrays.toString(attributeNames)
                    + "\nread part: average for scalars, write part: write on all writable attributes");
        } else {
            props.setDescription("read attributes: " + Arrays.toString(attributeNames));
        }
        config.setAttributeProperties(props);
        return config;
    }

    public void setReadValue(final DeviceAttribute[] readValues) {
        this.readValues = Arrays.copyOf(readValues, readValues.length);
    }

    @Override
    public AttributeValue getValue() throws DevFailed {
        AttributeValue value = null;
        final DeviceAttribute[] result;
        if (isExternalRead) {
            result = readValues;
        } else {
            result = attributeGroup.read();
        }
        // calculate average
        if (attributeFormat.equals(AttrDataFormat.SCALAR)) {

            final double[] data = new double[result.length];
            for (int i = 0; i < result.length; i++) {
                data[i] = InsertExtractUtils.extractRead(result[i], AttrDataFormat.SCALAR, double.class);
            }
            if (isMean) {
                value = new AttributeValue(StatUtils.mean(data));
            } else {
                value = new AttributeValue(data);
            }
        } else {
            final List<double[]> values = new LinkedList<double[]>();
            int maxLength = 0;
            int maxDimX = 0;
            int maxDimY = 0;
            for (final DeviceAttribute element : result) {
                final double[] data = InsertExtractUtils.extractRead(element, AttrDataFormat.SPECTRUM, double[].class);
                values.add(data);
                // each attribute may have a different size, finding max size
                if (data.length > maxLength) {
                    maxLength = data.length;
                }
                if (element.getDimX() > maxDimX) {
                    maxDimX = element.getDimX();
                }
                if (element.getDimY() > maxDimY) {
                    maxDimY = element.getDimY();
                }
            }
            final double[][] array = new double[maxLength][attributeNames.length];
            for (int i = 0; i < attributeNames.length; i++) {
                final double[] attrValue = values.get(i);
                double[] totalArray = attrValue;
                if (attrValue.length < maxLength) {
                    // put some trailing 0 if not maxLength
                    final double[] fillArray = new double[maxLength - attrValue.length];
                    Arrays.fill(fillArray, 0);
                    totalArray = ArrayUtils.addAll(attrValue, fillArray);
                }

                for (int x = 0; x < maxLength; x++) {
                    array[x][i] = totalArray[x];
                }
            }
            // avg
            if (isMean) {
                final double[] avg = new double[maxLength];
                for (int i = 0; i < array.length; i++) {
                    avg[i] = StatUtils.mean(array[i]);
                }
                if (attributeFormat.equals(AttrDataFormat.SPECTRUM)) {
                    value = new AttributeValue(avg);
                } else {
                    // if attribute is an image, don't know how to manage all dimensions properly
                    value = new AttributeValue(avg);
                    value.setXDim(maxDimX);
                    value.setYDim(maxDimY);
                }
            } else {
                value = new AttributeValue(array);
            }
        }

        return value;
    }

    @Override
    public void setValue(final AttributeValue value) throws DevFailed {
        attributeGroup.write(value.getValue());
    }

    @Override
    public StateMachineBehavior getStateMachine() throws DevFailed {
        return null;
    }

}
