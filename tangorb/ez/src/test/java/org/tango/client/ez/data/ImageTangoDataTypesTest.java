// +======================================================================
//   $Source$
//
//   Project:   ezTangORB
//
//   Description:  java source code for the simplified TangORB API.
//
//   $Author: Igor Khokhriakov <igor.khokhriakov@hzg.de> $
//
//   Copyright (C) :      2014
//                        Helmholtz-Zentrum Geesthacht
//                        Max-Planck-Strasse, 1, Geesthacht 21502
//                        GERMANY
//                        http://hzg.de
//
//   This file is part of Tango.
//
//   Tango is free software: you can redistribute it and/or modify
//   it under the terms of the GNU Lesser General Public License as published by
//   the Free Software Foundation, either version 3 of the License, or
//   (at your option) any later version.
//
//   Tango is distributed in the hope that it will be useful,
//   but WITHOUT ANY WARRANTY; without even the implied warranty of
//   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//   GNU Lesser General Public License for more details.
//
//   You should have received a copy of the GNU Lesser General Public License
//   along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
//  $Revision: 25721 $
//
// -======================================================================

package org.tango.client.ez.data;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.TangoApi.DeviceAttribute;
import org.junit.Test;
import org.tango.client.ez.data.format.TangoDataFormat;
import org.tango.client.ez.data.type.ImageTangoDataTypes;
import org.tango.client.ez.data.type.TangoDataType;
import org.tango.client.ez.data.type.TangoImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 12.06.12
 */
public class ImageTangoDataTypesTest {
    @Test
    public void testImageDataType_extract() throws Exception {
        TangoDataType<TangoImage<double[]>> type = ImageTangoDataTypes.DOUBLE_IMAGE;

        DeviceAttribute attribute = new DeviceAttribute("test", new double[]{1., 3., 2., 4.}, 2, 2);

        TangoDataWrapper attrWrapper = TangoDataWrapper.create(attribute);

        TangoImage<double[]> image = type.extract(attrWrapper);

        double[][] result = image.to2DArray();

        assertArrayEquals(new double[]{1., 3.}, result[0], 0.0);
        assertArrayEquals(new double[]{2., 4.}, result[1], 0.0);
    }

    @Test
    public void testImageDataType_insert() throws Exception {
        TangoDataType<TangoImage<double[]>> type = ImageTangoDataTypes.DOUBLE_IMAGE;

        DeviceAttribute attribute = new DeviceAttribute("test");

        TangoDataWrapper attrWrapper = TangoDataWrapper.create(attribute);

        type.insert(attrWrapper, TangoImage.<double[]>from2DArray(new double[][]{{1., 3.}, {2., 4.}}));

        assertArrayEquals(new double[]{1., 3., 2., 4.}, attribute.extractDoubleArray(), 0.0);
    }

    @Test
    public void testEncodedImage() throws Exception {
        BufferedImage image = ImageIO.read(new File("target/test-classes/1290338792.jpg"));//☭

        BufferedImage gray8 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        gray8.getGraphics().drawImage(image, 0, 0, null);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(gray8, "jpeg", os);
        os.close();

        DevEncoded encoded = new DevEncoded("JPEG_GRAY8", os.toByteArray());

        DeviceAttribute attribute = new DeviceAttribute("encoded", "prevent NPE in the following insert method");

        attribute.insert(encoded);
        attribute.getAttributeValueObject_5().data_format = AttrDataFormat.SCALAR;//insert always set FMT_UNKNOWN

        TangoDataWrapper data = TangoDataWrapper.create(attribute);

        TangoDataFormat<BufferedImage> format = TangoDataFormat.createForAttrDataFormat(attribute.getDataFormat());

        TangoDataType<BufferedImage> type = format.getDataType(data.getType());

        BufferedImage result = type.extract(data);

        ImageIO.write(result, "jpg", new File("target/testEncodedImage_result.jpg"));

        //TODO check image
    }
}
