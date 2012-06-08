/*******************************************************************************
 * Copyright (c) 2006 Synchrotron SOLEIL
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.All rights reserved. This program and the accompanying materials
 * 
 * Contributors:
 *    katy.saintin@synchrotron-soleil.fr - initial implementation
 *    gwenaelle.abeille@synchrotron-soleil.fr - initial implementation
 *******************************************************************************/
/*	Synchrotron Soleil 
 *  
 *   File          :  AttributeHelper.java
 *  
 *   Author        :  SAINTIN, ABEILLE
 *  
 *   Original      :  12 mai 2005 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: AttributeHelper.java,v 
 *
 */
package fr.esrf.TangoApi.helpers;

import java.util.Vector;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.DbDatum;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.StateUtilities;
import fr.esrf.TangoDs.Attribute;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import fr.esrf.TangoDs.WAttribute;

/**
 * 
 * This class provide useful static methods to insert or extract data from
 * DeviceAttribute
 * 
 * @author SAINTIN
 * @author ABEILLE
 */
public class AttributeHelper {

	/**
	 * Insert data in DeviceAttribute from an object
	 * 
	 * @param value
	 *            the value to write on DeviceAttribute, possibles Class Short,
	 *            String, Long, Float, Boolean, Integer, Double, DevState,
	 *            WAttribute or Vector.
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @throws DevFailed
	 */
	public static void insert(Object value,DeviceAttribute deviceAttributeWritten) throws DevFailed {

		if (value instanceof Short)
			AttributeHelper.insertFromShort((Short) value,deviceAttributeWritten);
		else if (value instanceof String)
			AttributeHelper.insertFromString((String) value,deviceAttributeWritten);
		else if (value instanceof Integer)
			AttributeHelper.insertFromInteger((Integer) value,deviceAttributeWritten);
		else if (value instanceof Long)
			AttributeHelper.insertFromLong((Long) value, deviceAttributeWritten);
		else if (value instanceof Float)
			AttributeHelper.insertFromFloat((Float) value,deviceAttributeWritten);
		else if (value instanceof Boolean)
			AttributeHelper.insertFromBoolean((Boolean) value,deviceAttributeWritten);
		else if (value instanceof Double)
			AttributeHelper.insertFromDouble((Double) value,deviceAttributeWritten);
		else if (value instanceof DevState)
			AttributeHelper.insertFromDevState((DevState) value,deviceAttributeWritten);
		else if (value instanceof WAttribute)
			AttributeHelper.insertFromWAttribute((WAttribute) value,deviceAttributeWritten);
		else if (value instanceof Vector)
			AttributeHelper.insertFromArray(((Vector) value).toArray(),deviceAttributeWritten, deviceAttributeWritten.getWrittenDimX(), deviceAttributeWritten.getWrittenDimY());
		else {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ value.getClass() + " not supported",
							"AttributeHelper.insert(Object value,deviceAttributeWritten)");
		}
	}

	/**
	 * Set value with an object on Attribute
	 * 
	 * @param value
	 *            the value to set on Attribute of JServer, possibles Class :
	 *            Short, String, Long, Float, Boolean, Integer, Double,
	 *            DevState.
	 * @param attribute
	 *            the attribute to set
	 * @throws DevFailed
	 */
	public static void set_value(Object value, Attribute attribute)
			throws DevFailed {
       try{
           
       
        if (value instanceof Short)
            attribute.set_value(((Short) value).shortValue());
        else if (value instanceof Byte)
            attribute.set_value(((Byte) value).shortValue());
        else if (value instanceof String)
			attribute.set_value((String) value);
		else if (value instanceof Integer)
			attribute.set_value(((Integer) value).intValue());
		else if (value instanceof Long)
			attribute.set_value(((Long) value).longValue());
		else if (value instanceof Float)
			attribute.set_value(((Float) value).doubleValue());
		else if (value instanceof Boolean)
			attribute.set_value(((Boolean) value).booleanValue());
		else if (value instanceof Double)
			attribute.set_value(((Double) value).doubleValue());
		else if (value instanceof DevState)
			attribute.set_value((DevState) value);
		else
        {
            //System.out.println(value.getClass().getName());
            Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ value.getClass() + " not supported",
							"AttributeHelper.insert(Object value,deviceAttributeWritten)");
		}
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
    }

	/**
	 * Set value with an object on Attribute
	 * 
	 * @param valueArray
	 *            the value to set on Attribute of JServer, possibles Class :
	 *            Short, String, Long, Float, Boolean, Integer, Double,
	 *            DevState.
	 * @param attribute
	 *            the attribute to set
	 * @param dimX
	 *            the x dimension of the attribute
	 * @param dimY
	 *            the y dimension of the attribute
	 * @throws DevFailed
	 */
	public static void set_value(Object[] valueArray, Attribute attribute,int dimX, int dimY) throws DevFailed {
	    
        if (valueArray.length > 0 && dimX == 1 && dimY == 0)
        {
            AttributeHelper.set_value(valueArray[0], attribute);
        }
		else if (valueArray.length > 1)
        {
			Object value = valueArray[0];
            if (value instanceof Short) {
				short[] shortValues = new short[valueArray.length];
				for (int i = 0; i < valueArray.length; i++)
					shortValues[i] = ((Short) valueArray[i]).shortValue();

				if (dimY == 0)
					attribute.set_value(shortValues, shortValues.length);
				if (dimY > 0)
					attribute.set_value(shortValues, shortValues.length / dimY,	dimY);
			} else if (value instanceof String) {
				String[] stringValues = new String[valueArray.length];
				for (int i = 0; i < valueArray.length; i++)
					stringValues[i] = (String) valueArray[i];

				if (dimY == 0)
					attribute.set_value(stringValues, stringValues.length);
				if (dimY > 0)
					attribute.set_value(stringValues, stringValues.length
							- dimY, dimY);
			} else if (value instanceof Integer) {
				int[] intValues = new int[valueArray.length];
				for (int i = 0; i < valueArray.length; i++)
					intValues[i] = ((Integer) valueArray[i]).intValue();

				if (dimY == 0)
					attribute.set_value(intValues, intValues.length);
				if (dimY > 0)
					attribute.set_value(intValues, intValues.length / dimY,dimY);
			} else if (value instanceof Long) {
				long[] longValues = new long[valueArray.length];
				for (int i = 0; i < valueArray.length; i++)
					longValues[i] = ((Long) valueArray[i]).longValue();

				if (dimY == 0)
					attribute.set_value(longValues, longValues.length);
				if (dimY > 0)
					attribute.set_value(longValues, longValues.length / dimY,dimY);
			} else if (value instanceof Float) {
                double[] floatValues = new double[valueArray.length];
				for (int i = 0; i < valueArray.length; i++)
					floatValues[i] = ((Float) valueArray[i]).doubleValue();
                if (dimY == 0)
                    attribute.set_value(floatValues, floatValues.length);
                if (dimY > 0)
					attribute.set_value(floatValues, floatValues.length / dimY,dimY);
			} else if (value instanceof Boolean) {
				boolean[] booleanValues = new boolean[valueArray.length];
				for (int i = 0; i < valueArray.length; i++)
					booleanValues[i] = ((Boolean) valueArray[i]).booleanValue();

				if (dimY == 0)
					attribute.set_value(booleanValues, booleanValues.length);
				if (dimY > 0)
					attribute.set_value(booleanValues, booleanValues.length
							- dimY, dimY);
			} else if (value instanceof Double) {
				double[] doubleValues = new double[valueArray.length];
				for (int i = 0; i < valueArray.length; i++)
					doubleValues[i] = ((Double) valueArray[i]).doubleValue();

				if (dimY == 0)
					attribute.set_value(doubleValues, doubleValues.length);
				if (dimY > 0)
					attribute.set_value(doubleValues, doubleValues.length / dimY, dimY);
             }
             else if (value instanceof Byte) {
                     short[] byteValues = new short[valueArray.length];
                    for (int i = 0; i < valueArray.length; i++)
                        byteValues[i] = ((Byte) valueArray[i]).shortValue();
                    if (dimY == 0)
                        attribute.set_value(byteValues, byteValues.length);
                    if (dimY > 0)
                        attribute.set_value(byteValues, byteValues.length / dimY, dimY);
			} else if (value instanceof DevState) {
				DevState[] devStateValues = new DevState[valueArray.length];
				for (int i = 0; i < valueArray.length; i++)
					devStateValues[i] = (DevState) valueArray[i];

				if (dimY == 0)
					attribute.set_value(devStateValues, devStateValues.length);
				if (dimY > 0)
					attribute.set_value(devStateValues, devStateValues.length / dimY, dimY);
			} else {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"input type " + value.getClass()
										+ " not supported",
								"AttributeHelper.insert(Object value,deviceAttributeWritten)");
			}
		}
    }

	/**
	 * Insert data in DeviceAttribute from an Object
	 * 
	 * @param value
	 *            the value to write on DeviceAttribute, possibles Class :
	 *            Short, String, Long, Float, Boolean, Integer, Double, DevState
	 *            or Vector.
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @param dimX
	 *            the custom X dimension of the attribute to write use only if
	 *            it is a Vector
	 * @param dimY
	 *            the custom Y dimension of the attribute to write use only if
	 *            it is a Vector
	 * @throws DevFailed
	 */
	public static void insert(Object value,
			DeviceAttribute deviceAttributeWritten, int dimX, int dimY)
			throws DevFailed {

		if (value instanceof Short)
			AttributeHelper.insertFromShort((Short) value,
					deviceAttributeWritten);
		else if (value instanceof String)
			AttributeHelper.insertFromString((String) value,
					deviceAttributeWritten);
		else if (value instanceof Integer)
			AttributeHelper.insertFromInteger((Integer) value,
					deviceAttributeWritten);
		else if (value instanceof Long)
			AttributeHelper
					.insertFromLong((Long) value, deviceAttributeWritten);
		else if (value instanceof Float)
			AttributeHelper.insertFromFloat((Float) value,
					deviceAttributeWritten);
		else if (value instanceof Boolean)
			AttributeHelper.insertFromBoolean((Boolean) value,
					deviceAttributeWritten);
		else if (value instanceof Double)
			AttributeHelper.insertFromDouble((Double) value,
					deviceAttributeWritten);
		else if (value instanceof DevState)
			AttributeHelper.insertFromDevState((DevState) value,
					deviceAttributeWritten);
		else if (value instanceof WAttribute)
			AttributeHelper.insertFromWAttribute((WAttribute) value,
					deviceAttributeWritten);
		else if (value instanceof Vector)
			AttributeHelper.insertFromArray(((Vector) value).toArray(),
					deviceAttributeWritten, dimX, dimY);
		else {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ value.getClass() + " not supported",
							"AttributeHelper.insert(Object value,deviceAttributeWritten)");
		}
	}

	/**
	 * Extract data from DeviceAttribute to an Object
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute to read
	 * @return Object possibles Class : Short, String, Long, Float, Boolean,
	 *         Integer, Double, DevState or Vector.
	 * @throws DevFailed
	 */
	public static Object extract(DeviceAttribute deviceAttributeRead)
			throws DevFailed {
		Object argout = null;
		if (deviceAttributeRead.getDimX() != 1
				|| deviceAttributeRead.getDimY() != 0){
			argout = extractArray(deviceAttributeRead);
		}else{
			
			switch (deviceAttributeRead.getType()) {
			case TangoConst.Tango_DEV_SHORT:
				argout = Short.valueOf(deviceAttributeRead.extractShort());
				break;
			case TangoConst.Tango_DEV_USHORT:
				argout = Integer.valueOf(deviceAttributeRead.extractUShort())
						.shortValue();
				break;
			case TangoConst.Tango_DEV_CHAR:
				// VH : a char correspond to a Byte in java
				// but deviceAttribute doesn't have a extractchar method !!!
				Except.throw_exception("TANGO_WRONG_DATA_ERROR",
						"out type Tango_DEV_CHAR not supported",
						"AttributeHelper.extract(deviceAttributeWritten)");
				break;
			case TangoConst.Tango_DEV_UCHAR:
				argout = Short.valueOf(deviceAttributeRead.extractUChar()).shortValue();
				break;
			case TangoConst.Tango_DEV_LONG:
				argout = Integer.valueOf(deviceAttributeRead.extractLong());
				break;
			case TangoConst.Tango_DEV_ULONG:
				argout = Long.valueOf(deviceAttributeRead.extractULong());
				break;
			case TangoConst.Tango_DEV_LONG64:
				argout = Long.valueOf(deviceAttributeRead.extractLong64());
				break;
			case TangoConst.Tango_DEV_ULONG64:
				argout = Long.valueOf(deviceAttributeRead.extractULong64());
				break;
			case TangoConst.Tango_DEV_INT:
				argout = Integer.valueOf(deviceAttributeRead.extractLong());
				break;
			case TangoConst.Tango_DEV_FLOAT:
				argout = Float.valueOf(deviceAttributeRead.extractFloat());
				break;
			case TangoConst.Tango_DEV_DOUBLE:
				argout = Double.valueOf(deviceAttributeRead.extractDouble());
				break;
			case TangoConst.Tango_DEV_STRING:
				argout = deviceAttributeRead.extractString();
				break;
			case TangoConst.Tango_DEV_BOOLEAN:
				argout = Boolean.valueOf(deviceAttributeRead.extractBoolean());
				break;
			case TangoConst.Tango_DEV_STATE:
				argout = deviceAttributeRead.extractDevState();
				break;
			default:
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
								+ deviceAttributeRead.getType() + " not supported",
								"AttributeHelper.extract(Short value,deviceAttributeWritten)");
				break;
			}
		}
		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a list of Object
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute to read
	 * @return Object[], an array of the extracted values, possible type :
	 *         Short, String, Long, Float, Boolean, Integer, Double.
	 * @throws DevFailed
	 */
	public static Object[] extractArray(DeviceAttribute deviceAttributeRead)
			throws DevFailed {
		Object[] argout = null;
			switch (deviceAttributeRead.getType()) {
			case TangoConst.Tango_DEV_SHORT:
				short[] simpleShortValues = deviceAttributeRead
						.extractShortArray();
				argout = new Object[simpleShortValues.length];
				for (int i = 0; i < simpleShortValues.length; i++)
					argout[i] = Short.valueOf(simpleShortValues[i]);
				break;
			case TangoConst.Tango_DEV_USHORT:
				int[] simpleUshortValues = deviceAttributeRead
						.extractUShortArray();
				argout = new Object[simpleUshortValues.length];
				for (int i = 0; i < simpleUshortValues.length; i++)
					argout[i] = Integer.valueOf(simpleUshortValues[i]);
				break;
			case TangoConst.Tango_DEV_CHAR:
				Except.throw_exception("TANGO_WRONG_DATA_ERROR",
						"output type Tango_DEV_CHAR not supported",
						"AttributeHelper.extractArray(deviceAttributeWritten)");
				break;
			case TangoConst.Tango_DEV_UCHAR:
				byte[] byteValues = deviceAttributeRead.extractCharArray();
				argout = new Object[byteValues.length];
				for (int i = 0; i < byteValues.length; i++)
					argout[i] = Byte.valueOf(byteValues[i]);
				break;
			case TangoConst.Tango_DEV_LONG:
				int[] simpleLongValues = deviceAttributeRead.extractLongArray();
				argout = new Object[simpleLongValues.length];
				for (int i = 0; i < simpleLongValues.length; i++)
					argout[i] = Integer.valueOf(simpleLongValues[i]);
				break;
			case TangoConst.Tango_DEV_ULONG:
				long[] simpleUlongValues = deviceAttributeRead
						.extractULongArray();
				argout = new Object[simpleUlongValues.length];
				for (int i = 0; i < simpleUlongValues.length; i++)
					argout[i] = Long.valueOf(simpleUlongValues[i]);
				break;
			case TangoConst.Tango_DEV_LONG64:
				long[] simpleLong64Values = deviceAttributeRead
						.extractLong64Array();
				argout = new Object[simpleLong64Values.length];
				for (int i = 0; i < simpleLong64Values.length; i++)
					argout[i] = Long.valueOf(simpleLong64Values[i]);
				break;
			case TangoConst.Tango_DEV_ULONG64:
				long[] simpleUlong64Values = deviceAttributeRead
						.extractULong64Array();
				argout = new Object[simpleUlong64Values.length];
				for (int i = 0; i < simpleUlong64Values.length; i++)
					argout[i] = Long.valueOf(simpleUlong64Values[i]);
				break;
			case TangoConst.Tango_DEV_INT:
				int[] simpleIntValues = deviceAttributeRead.extractLongArray();
				argout = new Object[simpleIntValues.length];
				for (int i = 0; i < simpleIntValues.length; i++)
					argout[i] = Integer.valueOf(simpleIntValues[i]);
				break;
			case TangoConst.Tango_DEV_FLOAT:
				float[] simpleFloatValues = deviceAttributeRead
						.extractFloatArray();
				argout = new Object[simpleFloatValues.length];
				for (int i = 0; i < simpleFloatValues.length; i++)
					argout[i] = Float.valueOf(simpleFloatValues[i]);
				break;
			case TangoConst.Tango_DEV_DOUBLE:
				double[] simpleDoubleValues = deviceAttributeRead
						.extractDoubleArray();
				argout = new Object[simpleDoubleValues.length];
				for (int i = 0; i < simpleDoubleValues.length; i++)
					argout[i] = Double.valueOf(simpleDoubleValues[i]);
				break;
			case TangoConst.Tango_DEV_STRING:
				String[] simpleStringValues = deviceAttributeRead
						.extractStringArray();
				argout = new Object[simpleStringValues.length];
				for (int i = 0; i < simpleStringValues.length; i++)
					argout[i] = simpleStringValues[i];
				break;
			case TangoConst.Tango_DEV_BOOLEAN:
				boolean[] simpleBooleanValues = deviceAttributeRead
						.extractBooleanArray();
				argout = new Object[simpleBooleanValues.length];
				for (int i = 0; i < simpleBooleanValues.length; i++)
					argout[i] = Boolean.valueOf(simpleBooleanValues[i]);
				break;
			case TangoConst.Tango_DEV_STATE:
				argout = new Object[] { deviceAttributeRead.extractDevState() };
				break;

			// Array input type
			case TangoConst.Tango_DEVVAR_SHORTARRAY:
				short[] shortValues = deviceAttributeRead.extractShortArray();
				argout = new Object[shortValues.length];
				for (int i = 0; i < shortValues.length; i++)
					argout[i] = Short.valueOf(shortValues[i]);
				break;
			case TangoConst.Tango_DEVVAR_USHORTARRAY:
				int[] ushortValues = deviceAttributeRead.extractUShortArray();
				argout = new Object[ushortValues.length];
				for (int i = 0; i < ushortValues.length; i++)
					argout[i] = Integer.valueOf(ushortValues[i]);
				break;
			case TangoConst.Tango_DEVVAR_CHARARRAY:
				byte[] charValues = deviceAttributeRead.extractCharArray();
				argout = new Object[charValues.length];
				for (int i = 0; i < charValues.length; i++)
					argout[i] = Byte.valueOf(charValues[i]);
				break;
			case TangoConst.Tango_DEVVAR_LONGARRAY:
				int[] longValues = deviceAttributeRead.extractLongArray();
				argout = new Object[longValues.length];
				for (int i = 0; i < longValues.length; i++)
					argout[i] = Integer.valueOf(longValues[i]);
				break;
			case TangoConst.Tango_DEVVAR_ULONGARRAY:
				long[] ulongValues = deviceAttributeRead.extractULongArray();
				argout = new Object[ulongValues.length];
				for (int i = 0; i < ulongValues.length; i++)
					argout[i] = Long.valueOf(ulongValues[i]);
				break;
			case TangoConst.Tango_DEVVAR_LONG64ARRAY:
				long[] long64Values = deviceAttributeRead.extractLong64Array();
				argout = new Object[long64Values.length];
				for (int i = 0; i < long64Values.length; i++)
					argout[i] = Long.valueOf(long64Values[i]);
				break;
			case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
				long[] ulong64Values = deviceAttributeRead
						.extractULong64Array();
				argout = new Object[ulong64Values.length];
				for (int i = 0; i < ulong64Values.length; i++)
					argout[i] = Long.valueOf(ulong64Values[i]);
				break;
			case TangoConst.Tango_DEVVAR_FLOATARRAY:
				float[] floatValues = deviceAttributeRead.extractFloatArray();
				argout = new Object[floatValues.length];
				for (int i = 0; i < floatValues.length; i++)
					argout[i] = Float.valueOf(floatValues[i]);
				break;
			case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
				double[] doubleValues = deviceAttributeRead
						.extractDoubleArray();
				argout = new Object[doubleValues.length];
				for (int i = 0; i < doubleValues.length; i++)
					argout[i] = Double.valueOf(doubleValues[i]);
				break;
			case TangoConst.Tango_DEVVAR_STRINGARRAY:
				String[] stringValues = deviceAttributeRead
						.extractStringArray();
				argout = new Object[stringValues.length];
				for (int i = 0; i < stringValues.length; i++)
					argout[i] = stringValues[i];
				break;

			case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY:
				Except
						.throw_exception(
								"TANGO_WRONG_DATA_ERROR",
								"output type Tango_DEVVAR_LONGSTRINGARRAY not supported",
								"AttributeHelper.insertFromShortArray(short[] values,deviceAttributeWritten)");
				break;
			case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY:
				Except
						.throw_exception(
								"TANGO_WRONG_DATA_ERROR",
								"output type Tango_DEVVAR_DOUBLESTRINGARRAY not supported",
								"AttributeHelper.insertFromShortArray(short[] values,deviceAttributeWritten)");
				break;

			default:
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + deviceAttributeRead.getType()
										+ " not supported",
								"AttributeHelper.insertFromShort(Short value,deviceAttributeWritten)");
				break;
			}
		return argout;
	}

	/**
	 * Insert data in DeviceAttribute from a Short object.
	 * 
	 * @param shortValue
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @throws DevFailed
	 */
	public static void insertFromShort(Short shortValue,
			DeviceAttribute deviceAttributeWritten) throws DevFailed {

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			deviceAttributeWritten.insert(shortValue.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			deviceAttributeWritten.insert_us(shortValue.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromShort(Short value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			deviceAttributeWritten.insert_uc(shortValue.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			deviceAttributeWritten.insert(shortValue.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(shortValue.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromShort(Short value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			deviceAttributeWritten.insert_u64(shortValue.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			deviceAttributeWritten.insert(shortValue.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			deviceAttributeWritten.insert(shortValue.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttributeWritten.insert(shortValue.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(shortValue.toString());
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			if (shortValue.doubleValue() == 1)
				deviceAttributeWritten.insert(true);
			else
				deviceAttributeWritten.insert(false);
			break;
		case TangoConst.Tango_DEV_STATE:
			try {
				deviceAttributeWritten.insert(DevState.from_int(shortValue
						.intValue()));
			} catch (org.omg.CORBA.BAD_PARAM badParam) {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"Cannot sent" + shortValue.intValue()
										+ "for input Tango_DEV_STATE type",
								"AttributeHelper.insertFromShort(Short value,deviceAttributeWritten)");
			}
			break;
		default:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ deviceAttributeWritten.getType()
							+ " not supported",
							"AttributeHelper.insertFromShort(Short value,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Extract data from DeviceAttribute to a Short
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return Short, the result in Short format
	 * @throws DevFailed
	 */
	public static Short extractToShort(DeviceAttribute deviceAttributeRead)
			throws DevFailed {
		Object value = AttributeHelper.extract(deviceAttributeRead);
		Short argout = null;
		if (value instanceof Short)
			argout = (Short) value;
		else if (value instanceof String) {
			try {
				argout = Short.valueOf((String) value);
			} catch (Exception e) {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + value + " is not a numerical",
								"AttributeHelper.extractToShort(deviceAttributeWritten)");
			}
		} else if (value instanceof Integer)
			argout = Short.valueOf(((Integer) value).shortValue());
		else if (value instanceof Long)
			argout = Short.valueOf(((Long) value).shortValue());
		else if (value instanceof Float)
			argout = Short.valueOf(((Float) value).shortValue());
		else if (value instanceof Boolean) {
			if (((Boolean) value).booleanValue())
				argout = Short.valueOf((short) 1);
			else
				argout = Short.valueOf((short) 0);
		} else if (value instanceof Double)
			argout = Short.valueOf(((Double) value).shortValue());
		else if (value instanceof DevState)
			argout = Short.valueOf(Integer.valueOf(((DevState) value).value())
					.shortValue());
		else {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
							+ value.getClass() + " not supported",
							"AttributeHelper.extractToShort(Object value,deviceAttributeWritten)");
		}

		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a String
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return String, the result in String format
	 * @throws DevFailed
	 */
	public static String extractToString(DeviceAttribute deviceAttributeRead)
			throws DevFailed {
		Object value = AttributeHelper.extract(deviceAttributeRead);
		String argout = null;
		if (value instanceof Short)
			argout = ((Short) value).toString();
		else if (value instanceof String)
			argout = (String) value;
		else if (value instanceof Integer)
			argout = ((Integer) value).toString();
		else if (value instanceof Long)
			argout = ((Long) value).toString();
		else if (value instanceof Float)
			argout = ((Float) value).toString();
		else if (value instanceof Boolean)
			argout = ((Boolean) value).toString();
		else if (value instanceof Double)
			argout = ((Double) value).toString();
		else if (value instanceof DevState)
			argout = StateUtilities.getNameForState((DevState) value);
		else {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
							+ value.getClass() + " not supported",
							"AttributeHelper.extractToString(Object value,deviceAttributeWritten)");
		}

		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to an Integer
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return Integer, the result in Integer format
	 * @throws DevFailed
	 */
	public static Integer extractToInteger(DeviceAttribute deviceAttributeRead)
			throws DevFailed {
		Object value = AttributeHelper.extract(deviceAttributeRead);
		Integer argout = null;
		if (value instanceof Short)
			argout = Integer.valueOf(((Short) value).intValue());
		else if (value instanceof String) {
			try {
				argout = Integer.valueOf((String) value);
			} catch (Exception e) {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + value + " is not a numerical",
								"AttributeHelper.extractToInteger(deviceAttributeWritten)");
			}
		} else if (value instanceof Integer)
			argout = (Integer) value;
		else if (value instanceof Long)
			argout = Integer.valueOf(((Long) value).intValue());
		else if (value instanceof Float)
			argout = Integer.valueOf(((Float) value).intValue());
		else if (value instanceof Boolean) {
			if (((Boolean) value).booleanValue())
				argout = Integer.valueOf(1);
			else
				argout = Integer.valueOf(0);
		} else if (value instanceof Double)
			argout = Integer.valueOf(((Double) value).intValue());
		else if (value instanceof DevState)
			argout = Integer.valueOf(((DevState) value).value());
		else {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
							+ value.getClass() + " not supported",
							"AttributeHelper.extractToInteger(Object value,deviceAttributeWritten)");
		}

		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a Long
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return Long, the result in Long format
	 * @throws DevFailed
	 */
	public static Long extractToLong(DeviceAttribute deviceAttributeRead)
			throws DevFailed {
		Object value = AttributeHelper.extract(deviceAttributeRead);
		Long argout = null;
		if (value instanceof Short)
			argout = Long.valueOf(((Short) value).longValue());
		else if (value instanceof String) {
			try {
				argout = Long.valueOf((String) value);
			} catch (Exception e) {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + value + " is not a numerical",
								"AttributeHelper.extractToLong(deviceAttributeWritten)");
			}
		} else if (value instanceof Integer)
			argout = Long.valueOf(((Integer) value).longValue());
		else if (value instanceof Long)
			argout = Long.valueOf(((Long) value).longValue());
		else if (value instanceof Float)
			argout = Long.valueOf(((Float) value).longValue());
		else if (value instanceof Boolean) {
			if (((Boolean) value).booleanValue())
				argout = Long.valueOf(1);
			else
				argout = Long.valueOf(0);
		} else if (value instanceof Double)
			argout = Long.valueOf(((Double) value).longValue());
		else if (value instanceof DevState)
			argout = Long.valueOf(Integer.valueOf(((DevState) value).value())
					.longValue());
		else {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
							+ value.getClass() + " not supported",
							"AttributeHelper.extractToLong(Object value,deviceAttributeWritten)");
		}

		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a Float
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return Float the result in Float format
	 * @throws DevFailed
	 */
	public static Float extractToFloat(DeviceAttribute deviceAttributeRead)
			throws DevFailed {
		Object value = AttributeHelper.extract(deviceAttributeRead);
		Float argout = null;
		if (value instanceof Short)
			argout = Float.valueOf(((Short) value).floatValue());
		else if (value instanceof String) {
			try {
				argout = Float.valueOf((String) value);
			} catch (Exception e) {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + value + " is not a numerical",
								"AttributeHelper.extractToFloat(deviceAttributeWritten)");
			}
		} else if (value instanceof Integer)
			argout = Float.valueOf(((Integer) value).floatValue());
		else if (value instanceof Long)
			argout = Float.valueOf(((Long) value).floatValue());
		else if (value instanceof Float)
			argout = Float.valueOf(((Float) value).floatValue());
		else if (value instanceof Boolean) {
			if (((Boolean) value).booleanValue())
				argout = Float.valueOf(1);
			else
				argout = Float.valueOf(0);
		} else if (value instanceof Double)
			argout = Float.valueOf(((Double) value).floatValue());
		else if (value instanceof DevState)
			argout = Float.valueOf(Integer.valueOf(((DevState) value).value())
					.floatValue());
		else {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
							+ value.getClass() + " not supported",
							"AttributeHelper.extractToFloat(Object value,deviceAttributeWritten)");
		}

		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a Boolean.
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return Boolean the result in Boolean format
	 * @throws DevFailed
	 */
	public static Boolean extractToBoolean(DeviceAttribute deviceAttributeRead)
			throws DevFailed {
		Object value = AttributeHelper.extract(deviceAttributeRead);
		int boolValue = 0;
		Boolean argout = Boolean.FALSE;
		;

		if (value instanceof Short)
			boolValue = ((Short) value).intValue();
		else if (value instanceof String) {
			try {
				if (Boolean.getBoolean((String) value))
					boolValue = 1;
			} catch (Exception e) {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + value + " is not a boolean",
								"AttributeHelper.extractToBoolean(deviceAttributeWritten)");
			}
		} else if (value instanceof Integer)
			boolValue = ((Integer) value).intValue();
		else if (value instanceof Long)
			boolValue = ((Long) value).intValue();
		else if (value instanceof Float)
			boolValue = ((Float) value).intValue();
		else if (value instanceof Boolean) {
			if (((Boolean) value).booleanValue())
				boolValue = 1;
		} else if (value instanceof Double)
			boolValue = ((Double) value).intValue();
		else if (value instanceof DevState)
			boolValue = ((DevState) value).value();
		else {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
							+ value.getClass() + " not supported",
							"AttributeHelper.extractToBoolean(Object value,deviceAttributeWritten)");
		}

		if (boolValue == 1)
			argout = Boolean.TRUE;

		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a Double.
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return Double the result in Double format
	 * @throws DevFailed
	 */
	public static Double extractToDouble(DeviceAttribute deviceAttributeRead)
			throws DevFailed {
		Object value = AttributeHelper.extract(deviceAttributeRead);
		Double argout = null;
		if (value instanceof Short)
			argout = Double.valueOf(((Short) value).doubleValue());
		else if (value instanceof String) {
			try {
				argout = Double.valueOf((String) value);
			} catch (Exception e) {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + value + " is not a numerical",
								"AttributeHelper.extractToFloat(deviceAttributeWritten)");
			}
		} else if (value instanceof Integer)
			argout = Double.valueOf(((Integer) value).doubleValue());
		else if (value instanceof Long)
			argout = Double.valueOf(((Long) value).doubleValue());
		else if (value instanceof Float)
			argout = Double.valueOf(((Float) value).doubleValue());
		else if (value instanceof Boolean) {
			if (((Boolean) value).booleanValue())
				argout = Double.valueOf(1);
			else
				argout = Double.valueOf(0);
		} else if (value instanceof Double)
			argout = (Double) value;
		else if (value instanceof DevState)
			argout = Double.valueOf(Integer.valueOf(((DevState) value).value())
					.doubleValue());
		else {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
							+ value.getClass() + " not supported",
							"AttributeHelper.extractToFloat(Object value,deviceAttributeWritten)");
		}

		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a DevState.
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return DevState the result in DevState format
	 * @throws DevFailed
	 */
	public static DevState extractToDevState(DeviceAttribute deviceAttributeRead)
			throws DevFailed {
		Object value = AttributeHelper.extract(deviceAttributeRead);
		DevState argout = null;
		if (value instanceof Short) {
			try {
				argout = DevState.from_int(((Short) value).intValue());
			} catch (Exception e) {
				argout = DevState.UNKNOWN;
			}
		} else if (value instanceof String)
			argout = StateUtilities.getStateForName((String) value);
		else if (value instanceof Integer) {
			try {
				argout = DevState.from_int(((Integer) value).intValue());
			} catch (Exception e) {
				argout = DevState.UNKNOWN;
			}
		} else if (value instanceof Long) {
			try {
				argout = DevState.from_int(((Long) value).intValue());
			} catch (Exception e) {
				argout = DevState.UNKNOWN;
			}
		} else if (value instanceof Float) {
			try {
				argout = DevState.from_int(((Float) value).intValue());
			} catch (Exception e) {
				argout = DevState.UNKNOWN;
			}
		} else if (value instanceof Boolean) {
			try {
				if (((Boolean) value).booleanValue())
					argout = DevState.from_int(1);
				else
					argout = DevState.from_int(0);
			} catch (Exception e) {
				argout = DevState.UNKNOWN;
			}
		} else if (value instanceof Double) {
			try {
				argout = DevState.from_int(((Double) value).intValue());
			} catch (Exception e) {
				argout = DevState.UNKNOWN;
			}
		} else if (value instanceof DevState)
			argout = (DevState) value;
		else {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
							+ value.getClass() + " not supported",
							"AttributeHelper.extractToDevState(Object value,deviceAttributeWritten)");
		}

		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a short Array.
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return short[]
	 * @throws DevFailed
	 */
	public static short[] extractToShortArray(
			DeviceAttribute deviceAttributeRead) throws DevFailed {
		Object[] values = AttributeHelper.extractArray(deviceAttributeRead);
		if (values == null) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"output is empty ",
							"AttributeHelper.extractToShortArray(deviceAttributeWritten)");
		}
		short[] argout = new short[values.length];

		if (values.length > 0) {
			if (values[0] instanceof Short) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Short) values[i]).shortValue();
			} else if (values[0] instanceof String) {
				try {
					for (int i = 0; i < values.length; i++)
						argout[i] = Double.valueOf((String) values[i])
								.shortValue();
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"output type not numerical",
									"AttributeHelper.extractToShortArray(deviceAttributeWritten)");
				}
			} else if (values[0] instanceof Integer) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Integer) values[i]).shortValue();
			} else if (values[0] instanceof Long) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Long) values[i]).shortValue();
			} else if (values[0] instanceof Float) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Float) values[i]).shortValue();
			} else if (values[0] instanceof Boolean) {
				for (int i = 0; i < values.length; i++) {
					if (((Boolean) values[i]).booleanValue())
						argout[i] = (short) 1;
					else
						argout[i] = (short) 0;
				}
			} else if (values[0] instanceof Double) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Double) values[i]).shortValue();
			} else if (values[0] instanceof Byte) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Byte) values[i]).shortValue();
			} else if (values[0] instanceof DevState) {
				for (int i = 0; i < values.length; i++)
					argout[i] = Integer.valueOf(((DevState) values[i]).value())
							.shortValue();
			} else {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + values[0].getClass()
										+ " not supported",
								"AttributeHelper.extractToShortArray(Object value,deviceAttributeWritten)");
			}
		}
		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a String Array.
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return String[]
	 * @throws DevFailed
	 */
	public static String[] extractToStringArray(
			DeviceAttribute deviceAttributeRead) throws DevFailed {
		Object[] values = AttributeHelper.extractArray(deviceAttributeRead);
		if (values == null) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"output is empty ",
							"AttributeHelper.extractToStringArray(deviceAttributeWritten)");
		}
		String[] argout = new String[values.length];

		if (values.length > 0) {
			if (values[0] instanceof Short) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Short) values[i]).toString();
			} else if (values[0] instanceof String) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((String) values[i]);

			} else if (values[0] instanceof Integer) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Integer) values[i]).toString();
			} else if (values[0] instanceof Long) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Long) values[i]).toString();
			} else if (values[0] instanceof Float) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Float) values[i]).toString();
			} else if (values[0] instanceof Boolean) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Boolean) values[i]).toString();
			} else if (values[0] instanceof Double) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Double) values[i]).toString();
			} else if (values[0] instanceof Byte) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Byte) values[i]).toString();
			} else if (values[0] instanceof DevState) {
				for (int i = 0; i < values.length; i++)
					argout[i] = StateUtilities
							.getNameForState((DevState) values[0]);
			} else {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + values[0].getClass()
										+ " not supported",
								"AttributeHelper.extractToStringArray(Object value,deviceAttributeWritten)");
			}
		}
		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a int Array.
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return int[]
	 * @throws DevFailed
	 */
	public static int[] extractToIntegerArray(
			DeviceAttribute deviceAttributeRead) throws DevFailed {
		Object[] values = AttributeHelper.extractArray(deviceAttributeRead);
		if (values == null) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"output is empty ",
							"AttributeHelper.extractToIntegerArray(deviceAttributeWritten)");
		}
		int[] argout = new int[values.length];

		if (values.length > 0) {
			if (values[0] instanceof Short) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Short) values[i]).intValue();
			} else if (values[0] instanceof String) {
				try {
					for (int i = 0; i < values.length; i++)
						argout[i] = Integer.valueOf((String) values[i])
								.intValue();
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"output type not numerical",
									"AttributeHelper.extractToIntegerArray(deviceAttributeWritten)");
				}
			} else if (values[0] instanceof Integer) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Integer) values[i]).intValue();
			} else if (values[0] instanceof Long) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Long) values[i]).intValue();
			} else if (values[0] instanceof Float) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Float) values[i]).intValue();
			} else if (values[0] instanceof Boolean) {
				for (int i = 0; i < values.length; i++) {
					if (((Boolean) values[i]).booleanValue())
						argout[i] = 1;
					else
						argout[i] = 0;
				}
			} else if (values[0] instanceof Double) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Double) values[i]).intValue();
			} else if (values[0] instanceof Byte) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Byte) values[i]).intValue();
			} else if (values[0] instanceof DevState) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((DevState) values[i]).value();
			} else {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + values[0].getClass()
										+ " not supported",
								"AttributeHelper.extractToIntegerArray(Object value,deviceAttributeWritten)");
			}
		}
		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a long Array.
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return long[]
	 * @throws DevFailed
	 */
	public static long[] extractToLongArray(DeviceAttribute deviceAttributeRead)
			throws DevFailed {
		Object[] values = AttributeHelper.extractArray(deviceAttributeRead);
		if (values == null) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"output is empty ",
							"AttributeHelper.extractToLongArray(deviceAttributeWritten)");
		}
		long[] argout = new long[values.length];

		if (values.length > 0) {
			if (values[0] instanceof Short) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Short) values[i]).longValue();
			} else if (values[0] instanceof String) {
				try {
					for (int i = 0; i < values.length; i++)
						argout[i] = Long.valueOf((String) values[i])
								.longValue();
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"output type not numerical",
									"AttributeHelper.extractToLongArray(deviceAttributeWritten)");
				}
			} else if (values[0] instanceof Integer) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Integer) values[i]).longValue();
			} else if (values[0] instanceof Long) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Long) values[i]).longValue();
			} else if (values[0] instanceof Float) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Float) values[i]).longValue();
			} else if (values[0] instanceof Boolean) {
				for (int i = 0; i < values.length; i++) {
					if (((Boolean) values[i]).booleanValue())
						argout[i] = 1;
					else
						argout[i] = 0;
				}
			} else if (values[0] instanceof Double) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Double) values[i]).longValue();
			} else if (values[0] instanceof Byte) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Byte) values[i]).longValue();
			} else if (values[0] instanceof DevState) {
				for (int i = 0; i < values.length; i++)
					argout[i] = Integer.valueOf(((DevState) values[i]).value())
							.longValue();
			} else {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + values[0].getClass()
										+ " not supported",
								"AttributeHelper.extractToLongArray(Object value,deviceAttributeWritten)");
			}
		}
		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a float Array.
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return float[]
	 * @throws DevFailed
	 */
	public static float[] extractToFloatArray(
			DeviceAttribute deviceAttributeRead) throws DevFailed {
		Object[] values = AttributeHelper.extractArray(deviceAttributeRead);
		if (values == null) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"output is empty ",
							"AttributeHelper.extractToFloatArray(deviceAttributeWritten)");
		}
		float[] argout = new float[values.length];

		if (values.length > 0) {
			if (values[0] instanceof Short) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Short) values[i]).floatValue();
			} else if (values[0] instanceof String) {
				try {
					for (int i = 0; i < values.length; i++)
						argout[i] = Float.valueOf((String) values[i])
								.floatValue();
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"output type not numerical",
									"AttributeHelper.extractToFloatArray(deviceAttributeWritten)");
				}
			} else if (values[0] instanceof Integer) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Integer) values[i]).floatValue();
			} else if (values[0] instanceof Long) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Long) values[i]).floatValue();
			} else if (values[0] instanceof Float) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Float) values[i]).floatValue();
			} else if (values[0] instanceof Boolean) {
				for (int i = 0; i < values.length; i++) {
					if (((Boolean) values[i]).booleanValue())
						argout[i] = 1;
					else
						argout[i] = 0;
				}
			} else if (values[0] instanceof Double) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Double) values[i]).floatValue();
			} else if (values[0] instanceof Byte) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Byte) values[i]).floatValue();
			} else if (values[0] instanceof DevState) {
				for (int i = 0; i < values.length; i++)
					argout[i] = Integer.valueOf(((DevState) values[i]).value())
							.floatValue();
			} else {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + values[0].getClass()
										+ " not supported",
								"AttributeHelper.extractToFloatArray(Object value,deviceAttributeWritten)");
			}
		}
		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a boolean Array.
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return boolean[]
	 * @throws DevFailed
	 */
	public static boolean[] extractToBooleanArray(
			DeviceAttribute deviceAttributeRead) throws DevFailed {
		Object[] values = AttributeHelper.extractArray(deviceAttributeRead);
		if (values == null) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"output is empty ",
							"AttributeHelper.extractToBooleanArray(deviceAttributeWritten)");
		}
		boolean[] argout = new boolean[values.length];

		if (values.length > 0) {
			if (values[0] instanceof Short) {
				for (int i = 0; i < values.length; i++) {
					if (((Short) values[i]).intValue() == 1)
						argout[i] = true;
					else
						argout[i] = false;
				}
			} else if (values[0] instanceof String) {
				try {
					for (int i = 0; i < values.length; i++)
						argout[i] = Boolean.getBoolean((String) values[i]);
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"output type not numerical",
									"AttributeHelper.extractToBooleanArray(deviceAttributeWritten)");
				}
			} else if (values[0] instanceof Integer) {
				for (int i = 0; i < values.length; i++) {
					if (((Integer) values[i]).intValue() == 1)
						argout[i] = true;
					else
						argout[i] = false;
				}
			} else if (values[0] instanceof Long) {
				for (int i = 0; i < values.length; i++) {
					if (((Long) values[i]).intValue() == 1)
						argout[i] = true;
					else
						argout[i] = false;
				}
			} else if (values[0] instanceof Float) {
				for (int i = 0; i < values.length; i++) {
					if (((Float) values[i]).intValue() == 1)
						argout[i] = true;
					else
						argout[i] = false;
				}
			} else if (values[0] instanceof Boolean) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Boolean) values[i]).booleanValue();
			} else if (values[0] instanceof Double) {
				for (int i = 0; i < values.length; i++) {
					if (((Double) values[i]).intValue() == 1)
						argout[i] = true;
					else
						argout[i] = false;
				}
			} else if (values[0] instanceof Byte) {
				for (int i = 0; i < values.length; i++) {
					if (((Byte) values[i]).intValue() == 1)
						argout[i] = true;
					else
						argout[i] = false;
				}
			} else if (values[0] instanceof DevState) {
				for (int i = 0; i < values.length; i++) {
					if (((DevState) values[i]).value() == 1)
						argout[i] = true;
					else
						argout[i] = false;
				}
			} else {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + values[0].getClass()
										+ " not supported",
								"AttributeHelper.extractToBooleanArray(Object value,deviceAttributeWritten)");
			}
		}
		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a byte Array.
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return byte[]
	 * @throws DevFailed
	 */
	public static byte[] extractToByteArray(DeviceAttribute deviceAttributeRead)
			throws DevFailed {
		Object[] values = AttributeHelper.extractArray(deviceAttributeRead);
		if (values == null) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"output is empty ",
							"AttributeHelper.extractToFloatArray(deviceAttributeWritten)");
		}
		byte[] argout = new byte[values.length];

		if (values.length > 0) {
			if (values[0] instanceof Short) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Short) values[i]).byteValue();
			} else if (values[0] instanceof String) {
				try {
					for (int i = 0; i < values.length; i++)
						argout[i] = Float.valueOf((String) values[i])
								.byteValue();
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"output type not numerical",
									"AttributeHelper.extractToFloatArray(deviceAttributeWritten)");
				}
			} else if (values[0] instanceof Integer) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Integer) values[i]).byteValue();
			} else if (values[0] instanceof Long) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Long) values[i]).byteValue();
			} else if (values[0] instanceof Float) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Float) values[i]).byteValue();
			} else if (values[0] instanceof Boolean) {
				for (int i = 0; i < values.length; i++) {
					if (((Boolean) values[i]).booleanValue())
						argout[i] = 1;
					else
						argout[i] = 0;
				}
			} else if (values[0] instanceof Double) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Double) values[i]).byteValue();
			} else if (values[0] instanceof Byte) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Byte) values[i]).byteValue();
			} else if (values[0] instanceof DevState) {
				for (int i = 0; i < values.length; i++)
					argout[i] = Integer.valueOf(((DevState) values[i]).value())
							.byteValue();
			} else {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + values[0].getClass()
										+ " not supported",
								"AttributeHelper.extractToFloatArray(Object value,deviceAttributeWritten)");
			}
		}
		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a double Array.
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return double[]
	 * @throws DevFailed
	 */
	public static double[] extractToDoubleArray(
			DeviceAttribute deviceAttributeRead) throws DevFailed {
		Object[] values = AttributeHelper.extractArray(deviceAttributeRead);
		if (values == null) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"output is empty ",
							"AttributeHelper.extractToDoubleArray(deviceAttributeWritten)");
		}
		double[] argout = new double[values.length];

		if (values.length > 0) {
			if (values[0] instanceof Short) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Short) values[i]).doubleValue();
			} else if (values[0] instanceof String) {
				try {
					for (int i = 0; i < values.length; i++)
						argout[i] = Double.valueOf((String) values[i])
								.doubleValue();
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"output type not numerical",
									"AttributeHelper.extractToDoubleArray(deviceAttributeWritten)");
				}
			} else if (values[0] instanceof Integer) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Integer) values[i]).doubleValue();
			} else if (values[0] instanceof Long) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Long) values[i]).doubleValue();
			} else if (values[0] instanceof Float) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Float) values[i]).doubleValue();
			} else if (values[0] instanceof Boolean) {
				for (int i = 0; i < values.length; i++) {
					if (((Boolean) values[i]).booleanValue())
						argout[i] = 1;
					else
						argout[i] = 0;
				}
			} else if (values[0] instanceof Double) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Double) values[i]).doubleValue();
			} else if (values[0] instanceof Byte) {
				for (int i = 0; i < values.length; i++)
					argout[i] = ((Byte) values[i]).doubleValue();
			} else if (values[0] instanceof DevState) {
				for (int i = 0; i < values.length; i++)
					argout[i] = Integer.valueOf(((DevState) values[i]).value())
							.doubleValue();
			} else {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + values[0].getClass()
										+ " not supported",
								"AttributeHelper.extractToDoubleArray(Object value,deviceAttributeWritten)");
			}
		}
		return argout;
	}

	/**
	 * Extract data from DeviceAttribute to a DevState Array.
	 * 
	 * @param deviceAttributeRead
	 *            the DeviceAttribute attribute to read
	 * @return DevState[]
	 * @throws DevFailed
	 */
	public static DevState[] extractToDevStateArray(
			DeviceAttribute deviceAttributeRead) throws DevFailed {
		Object[] values = AttributeHelper.extractArray(deviceAttributeRead);
		if (values == null) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"output is empty ",
							"AttributeHelper.extractToFloatArray(deviceAttributeWritten)");
		}
		DevState[] argout = new DevState[values.length];

		if (values.length > 0) {
			if (values[0] instanceof Short) {
				for (int i = 0; i < values.length; i++) {
					try {
						argout[i] = DevState.from_int(((Short) values[i])
								.intValue());
					} catch (Exception e) {
						argout[i] = DevState.UNKNOWN;
					}
				}
			} else if (values[0] instanceof String) {
				for (int i = 0; i < values.length; i++)
					argout[i] = StateUtilities
							.getStateForName((String) values[i]);
			} else if (values[0] instanceof Integer) {
				for (int i = 0; i < values.length; i++) {
					try {
						argout[i] = DevState.from_int(((Integer) values[i])
								.intValue());
					} catch (Exception e) {
						argout[i] = DevState.UNKNOWN;
					}
				}
			} else if (values[0] instanceof Long) {
				for (int i = 0; i < values.length; i++) {
					try {
						argout[i] = DevState.from_int(((Long) values[i])
								.intValue());
					} catch (Exception e) {
						argout[i] = DevState.UNKNOWN;
					}
				}
			} else if (values[0] instanceof Float) {
				for (int i = 0; i < values.length; i++) {
					try {
						argout[i] = DevState.from_int(((Float) values[i])
								.intValue());
					} catch (Exception e) {
						argout[i] = DevState.UNKNOWN;
					}
				}
			} else if (values[0] instanceof Boolean) {
				for (int i = 0; i < values.length; i++) {
					try {
						if (((Boolean) values[i]).booleanValue())
							argout[i] = DevState.from_int(1);
						else
							argout[i] = DevState.from_int(0);
					} catch (Exception e) {
						argout[i] = DevState.UNKNOWN;
					}
				}
			} else if (values[0] instanceof Double) {
				for (int i = 0; i < values.length; i++) {
					try {
						argout[i] = DevState.from_int(((Double) values[i])
								.intValue());
					} catch (Exception e) {
						argout[i] = DevState.UNKNOWN;
					}
				}
			} else if (values[0] instanceof Byte) {
				for (int i = 0; i < values.length; i++) {
					try {
						argout[i] = DevState.from_int(((Byte) values[i])
								.intValue());
					} catch (Exception e) {
						argout[i] = DevState.UNKNOWN;
					}
				}
			} else if (values[0] instanceof DevState) {
				for (int i = 0; i < values.length; i++)
					argout[i] = (DevState) values[i];
			} else {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"output type " + values[0].getClass()
										+ " not supported",
								"AttributeHelper.extractToFloatArray(Object value,deviceAttributeWritten)");
			}
		}
		return argout;
	}

	/**
	 * Insert data in DeviceAttribute from a short Array.
	 * 
	 * @param shortValues
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @param dimX
	 *            the x dimension of the attribute
	 * @param dimY
	 *            the y dimension of the attribute
	 * @throws DevFailed
	 */
	public static void insertFromShortArray(short[] shortValues,
			DeviceAttribute deviceAttributeWritten, int dimX, int dimY)
			throws DevFailed {
		// by default for xdim = 1, send the sum.
		Double doubleSum = new Double(0);
		for (int i = 0; i < shortValues.length; i++)
			doubleSum = doubleSum + shortValues[i];

		if (shortValues.length > dimX * dimY) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "size of array "
							+ shortValues.length + " is too great",
							"AttributeHelper.insertFromShortArray(short[] values,deviceAttributeWritten)");
		}

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			deviceAttributeWritten.insert(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			deviceAttributeWritten.insert_us(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromShortArray(short[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			deviceAttributeWritten.insert_uc(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			deviceAttributeWritten.insert(doubleSum.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(doubleSum.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromShort(short[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			deviceAttributeWritten.insert_u64(doubleSum.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			deviceAttributeWritten.insert(doubleSum.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			deviceAttributeWritten.insert(doubleSum.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttributeWritten.insert(doubleSum.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(doubleSum.toString());
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			if (doubleSum.doubleValue() == 1)
				deviceAttributeWritten.insert(true);
			else
				deviceAttributeWritten.insert(false);
			break;
		case TangoConst.Tango_DEV_STATE:
			DevState[] devStateValues = new DevState[shortValues.length];
			for (int i = 0; i < shortValues.length; i++) {
				try {
					devStateValues[i] = DevState.from_int((Short
							.valueOf(shortValues[i])).intValue());
				} catch (org.omg.CORBA.BAD_PARAM badParam) {
					devStateValues[i] = DevState.UNKNOWN;
				}
			}
			deviceAttributeWritten.insert(devStateValues);
			break;

		// Array input type
		case TangoConst.Tango_DEVVAR_SHORTARRAY:
			if (dimY == 0)
				deviceAttributeWritten.insert(shortValues);
			else
				deviceAttributeWritten.insert(shortValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_USHORTARRAY:
			if (dimY == 0)
				deviceAttributeWritten.insert_uc(shortValues);
			else
				deviceAttributeWritten.insert_uc(shortValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_CHARARRAY:
			byte[] byteValues = new byte[shortValues.length];
			for (int i = 0; i < shortValues.length; i++)
				byteValues[i] = (Short.valueOf(shortValues[i]).byteValue());

			deviceAttributeWritten.insert_uc(byteValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONGARRAY:
            int[] longValues = new int[shortValues.length];
			for (int i = 0; i < shortValues.length; i++)
				longValues[i] = (Short.valueOf(shortValues[i]).intValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(longValues);
			else
				deviceAttributeWritten.insert(longValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_ULONGARRAY:
			long[] ulongValues = new long[shortValues.length];
			for (int i = 0; i < shortValues.length; i++)
				ulongValues[i] = (Short.valueOf(shortValues[i]).longValue());
			if (dimY == 0)
				deviceAttributeWritten.insert_ul(ulongValues);
			else
				deviceAttributeWritten.insert_ul(ulongValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONG64ARRAY not supported",
							"AttributeHelper.insertFromShortArray(short[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_ULONG64ARRAY not supported",
							"AttributeHelper.insertFromShortArray(short[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_FLOATARRAY:
			float[] floatValues = new float[shortValues.length];
			for (int i = 0; i < shortValues.length; i++)
				floatValues[i] = (Short.valueOf(shortValues[i]).floatValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(floatValues);
			else
				deviceAttributeWritten.insert(floatValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
			double[] doubleValues = new double[shortValues.length];
			for (int i = 0; i < shortValues.length; i++)
				doubleValues[i] = (Short.valueOf(shortValues[i]).doubleValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(doubleValues);
			else
				deviceAttributeWritten.insert(doubleValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_STRINGARRAY:
			String[] stringValues = new String[shortValues.length];
			for (int i = 0; i < shortValues.length; i++)
				stringValues[i] = (Short.valueOf(shortValues[i]).toString());
			if (dimY == 0)
				deviceAttributeWritten.insert(stringValues);
			else
				deviceAttributeWritten.insert(stringValues, dimX, dimY);
			break;

		case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONGSTRINGARRAY not supported",
							"AttributeHelper.insertFromShortArray(short[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_DOUBLESTRINGARRAY not supported",
							"AttributeHelper.insertFromShortArray(short[] values,deviceAttributeWritten)");
			break;

		default:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ deviceAttributeWritten.getType()
							+ " not supported",
							"AttributeHelper.insertFromShortArray(short[] values,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from a String Array.
	 * 
	 * @param stringValues
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @param dimX
	 *            the x dimension of the attribute
	 * @param dimY
	 *            the y dimension of the attribute
	 * @throws DevFailed
	 */
	public static void insertFromStringArray(String[] stringValues,
			DeviceAttribute deviceAttributeWritten, int dimX, int dimY)
			throws DevFailed {
		// by default for xdim = 1, send the first value.
		String firsString = "";
		Double numericalValue = Double.NaN;
		try {
			numericalValue = Double.valueOf(firsString);
		} catch (Exception e) {
			numericalValue = Double.NaN;
		}

		if (stringValues.length > 0)
			firsString = stringValues[0];

		if (stringValues.length > dimX * dimY) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "size of array "
							+ stringValues.length + " is too great",
							"AttributeHelper.insertFromShortArray(String[] values,deviceAttributeWritten)");
		}

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			if (!numericalValue.isNaN())
				deviceAttributeWritten.insert(numericalValue.shortValue());
			else
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
								+ " is not a Tango_DEV_SHORT",
								"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_USHORT:
			if (!numericalValue.isNaN())
				deviceAttributeWritten.insert_us(numericalValue.shortValue());
			else
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
								+ " is not a Tango_DEV_USHORT",
								"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			if (!numericalValue.isNaN())
				deviceAttributeWritten.insert_uc(numericalValue.shortValue());
			else
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
								+ " is not a Tango_DEV_UCHAR",
								"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_LONG:
			if (!numericalValue.isNaN())
				deviceAttributeWritten.insert(numericalValue.intValue());
			else
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
								+ " is not a Tango_DEV_LONG",
								"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG:
			if (!numericalValue.isNaN())
				deviceAttributeWritten.insert_ul(numericalValue.longValue());
			else
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
								+ " is not a Tango_DEV_ULONG",
								"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			if (!numericalValue.isNaN())
				deviceAttributeWritten.insert_u64(numericalValue.longValue());
			else
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
								+ " is not a Tango_DEV_ULONG64",
								"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_INT:
			if (!numericalValue.isNaN())
				deviceAttributeWritten.insert(numericalValue.intValue());
			else
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
								+ " is not a Tango_DEV_INT",
								"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_FLOAT:
			if (!numericalValue.isNaN())
				deviceAttributeWritten.insert(numericalValue.floatValue());
			else
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
								+ " is not a Tango_DEV_FLOAT",
								"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			if (!numericalValue.isNaN())
				deviceAttributeWritten.insert(numericalValue.doubleValue());
			else
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
								+ " is not a Tango_DEV_DOUBLE",
								"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(firsString);
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			if (!numericalValue.isNaN()) {
				if (numericalValue.doubleValue() == 1)
					deviceAttributeWritten.insert(true);
				else
					deviceAttributeWritten.insert(false);
			} else
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
								+ " is not a Tango_DEV_BOOLEAN",
								"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_STATE:
			if (!numericalValue.isNaN()) {
				DevState[] devStateValues = new DevState[stringValues.length];
				for (int i = 0; i < stringValues.length; i++) {
					try {
						devStateValues[i] = DevState.from_int((Short
								.valueOf(stringValues[i])).intValue());
					} catch (org.omg.CORBA.BAD_PARAM badParam) {
						devStateValues[i] = DevState.UNKNOWN;
					}
				}
				deviceAttributeWritten.insert(devStateValues);
			} else
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
								+ " is not a Tango_DEV_STATE",
								"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;

		// Array input type
		case TangoConst.Tango_DEVVAR_SHORTARRAY:
			short[] shortValues = new short[stringValues.length];
			for (int i = 0; i < stringValues.length; i++) {
				try {
					shortValues[i] = (Double.valueOf(stringValues[i])
							.shortValue());
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"input is not a Tango_DEVVAR_SHORTARRAY",
									"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
				}
			}
			if (dimY == 0)
				deviceAttributeWritten.insert(shortValues);
			else
				deviceAttributeWritten.insert(shortValues, dimX, dimY);
			break;

		case TangoConst.Tango_DEVVAR_USHORTARRAY:
			short[] ushortValues = new short[stringValues.length];
			for (int i = 0; i < stringValues.length; i++) {
				try {
					ushortValues[i] = (Double.valueOf(stringValues[i]).shortValue());
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"input is not a Tango_DEVVAR_USHORTARRAY",
									"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
				}
			}
			if (dimY == 0)
				deviceAttributeWritten.insert_uc(ushortValues);
			else
				deviceAttributeWritten.insert_uc(ushortValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_CHARARRAY:
			byte[] byteValues = new byte[stringValues.length];
			for (int i = 0; i < stringValues.length; i++) {
				try {
					byteValues[i] = (Double.valueOf(stringValues[i]).byteValue());
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"input is not a Tango_DEVVAR_CHARARRAY",
									"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
				}
			}
			deviceAttributeWritten.insert_uc(byteValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONGARRAY:
			int[] longValues = new int[stringValues.length];
			for (int i = 0; i < stringValues.length; i++) {
				try {
					longValues[i] = (Double.valueOf(stringValues[i]).intValue());
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"input is not a Tango_DEVVAR_LONGARRAY",
									"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
				}
			}
			if (dimY == 0)
				deviceAttributeWritten.insert(longValues);
			else
				deviceAttributeWritten.insert(longValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_ULONGARRAY:
			long[] ulongValues = new long[stringValues.length];
			for (int i = 0; i < stringValues.length; i++) {
				try {
					ulongValues[i] = (Double.valueOf(stringValues[i]).longValue());
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"input is not a Tango_DEVVAR_LONGARRAY",
									"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
				}
			}
			if (dimY == 0)
				deviceAttributeWritten.insert_ul(ulongValues);
			else
				deviceAttributeWritten.insert_ul(ulongValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONG64ARRAY not supported",
							"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_ULONG64ARRAY not supported",
							"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_FLOATARRAY:
			float[] floatValues = new float[stringValues.length];
			for (int i = 0; i < stringValues.length; i++) {
				try {
					floatValues[i] = (Double.valueOf(stringValues[i]).floatValue());
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"input is not a Tango_DEVVAR_FLOATARRAY",
									"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
				}
			}
			if (dimY == 0)
				deviceAttributeWritten.insert(floatValues);
			else
				deviceAttributeWritten.insert(floatValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
			double[] doubleValues = new double[stringValues.length];
			for (int i = 0; i < stringValues.length; i++) {
				try {
					doubleValues[i] = (Double.valueOf(stringValues[i]).doubleValue());
				} catch (Exception e) {
					Except
							.throw_exception("TANGO_WRONG_DATA_ERROR",
									"input is not a Tango_DEVVAR_DOUBLEARRAY",
									"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
				}
			}
			if (dimY == 0)
				deviceAttributeWritten.insert(doubleValues);
			else
				deviceAttributeWritten.insert(doubleValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_STRINGARRAY:
			if (dimY == 0)
				deviceAttributeWritten.insert(stringValues);
			else
				deviceAttributeWritten.insert(stringValues, dimX, dimY);
			break;

		case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONGSTRINGARRAY not supported",
							"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_DOUBLESTRINGARRAY not supported",
							"AttributeHelper.insertFromStringArray(String[] values,deviceAttributeWritten)");
			break;

		default:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ deviceAttributeWritten.getType()
							+ " not supported",
							"AttributeHelper.insertFromStringArray(String[] value,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from a int Array.
	 * 
	 * @param intValues
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @param dimX
	 *            the x dimension of the attribute
	 * @param dimY
	 *            the y dimension of the attribute
	 * @throws DevFailed
	 */
	public static void insertFromIntegerArray(int[] intValues,
			DeviceAttribute deviceAttributeWritten, int dimX, int dimY)
			throws DevFailed {
		// by default for xdim = 1, send the sum.
		Double doubleSum = new Double(0);
		for (int i = 0; i < intValues.length; i++)
			doubleSum = doubleSum + intValues[i];

		if (intValues.length > dimX * dimY) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "size of array "
							+ intValues.length + " is too great",
							"AttributeHelper.insertFromIntegerArray(int[] values,deviceAttributeWritten)");
		}

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			deviceAttributeWritten.insert(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			deviceAttributeWritten.insert_us(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromIntegerArray(int[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			deviceAttributeWritten.insert_uc(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			deviceAttributeWritten.insert(doubleSum.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(doubleSum.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromIntegerArray(int[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			deviceAttributeWritten.insert_u64(doubleSum.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			deviceAttributeWritten.insert(doubleSum.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			deviceAttributeWritten.insert(doubleSum.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttributeWritten.insert(doubleSum.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(doubleSum.toString());
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			if (doubleSum.doubleValue() == 1)
				deviceAttributeWritten.insert(true);
			else
				deviceAttributeWritten.insert(false);
			break;
		case TangoConst.Tango_DEV_STATE:
			DevState[] devStateValues = new DevState[intValues.length];
			for (int i = 0; i < intValues.length; i++) {
				try {
					devStateValues[i] = DevState.from_int((Integer
							.valueOf(intValues[i])).intValue());
				} catch (org.omg.CORBA.BAD_PARAM badParam) {
					devStateValues[i] = DevState.UNKNOWN;
				}
			}
			deviceAttributeWritten.insert(devStateValues);
			break;

		// Array input type
		case TangoConst.Tango_DEVVAR_SHORTARRAY:
			short[] shortValues = new short[intValues.length];
			for (int i = 0; i < intValues.length; i++)
				shortValues[i] = (Integer.valueOf(intValues[i]).shortValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(shortValues);
			else
				deviceAttributeWritten.insert(shortValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_USHORTARRAY:
			short[] ushortValues = new short[intValues.length];
			for (int i = 0; i < intValues.length; i++)
				ushortValues[i] = (Integer.valueOf(intValues[i]).shortValue());
			if (dimY == 0)
				deviceAttributeWritten.insert_uc(ushortValues);
			else
				deviceAttributeWritten.insert_uc(ushortValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_CHARARRAY:
			byte[] byteValues = new byte[intValues.length];
			for (int i = 0; i < intValues.length; i++)
				byteValues[i] = (Integer.valueOf(intValues[i]).byteValue());

			deviceAttributeWritten.insert_uc(byteValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONGARRAY:
			long[] longValues = new long[intValues.length];
			for (int i = 0; i < intValues.length; i++)
				longValues[i] = (Integer.valueOf(intValues[i]).longValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(longValues);
			else
				deviceAttributeWritten.insert(longValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_ULONGARRAY:
            int[] ulongValues = new int[intValues.length];
			for (int i = 0; i < intValues.length; i++)
				ulongValues[i] = (Integer.valueOf(intValues[i]).intValue());
			if (dimY == 0)
				deviceAttributeWritten.insert_ul(ulongValues);
			else
				deviceAttributeWritten.insert_ul(ulongValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONG64ARRAY not supported",
							"AttributeHelper.insertFromShortArray(int[] value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_ULONG64ARRAY not supported",
							"AttributeHelper.insertFromIntegerArray(int[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_FLOATARRAY:
			float[] floatValues = new float[intValues.length];
			for (int i = 0; i < intValues.length; i++)
				floatValues[i] = (Integer.valueOf(intValues[i]).floatValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(floatValues);
			else
				deviceAttributeWritten.insert(floatValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
			double[] doubleValues = new double[intValues.length];
			for (int i = 0; i < intValues.length; i++)
				doubleValues[i] = (Integer.valueOf(intValues[i]).doubleValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(doubleValues);
			else
				deviceAttributeWritten.insert(doubleValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_STRINGARRAY:
			String[] stringValues = new String[intValues.length];
			for (int i = 0; i < intValues.length; i++)
				stringValues[i] = (Integer.valueOf(intValues[i]).toString());
			if (dimY == 0)
				deviceAttributeWritten.insert(stringValues);
			else
				deviceAttributeWritten.insert(stringValues, dimX, dimY);
			break;

		case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONGSTRINGARRAY not supported",
							"AttributeHelper.insertFromIntegerArray(int[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_DOUBLESTRINGARRAY not supported",
							"AttributeHelper.insertFromIntegerArray(int[] values,deviceAttributeWritten)");
			break;

		default:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ deviceAttributeWritten.getType()
							+ " not supported",
							"AttributeHelper.insertFromIntegerArray(int[] values,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from a long Array.
	 * 
	 * @param longValues
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @param dimX
	 *            the x dimension of the attribute
	 * @param dimY
	 *            the y dimension of the attribute
	 * @throws DevFailed
	 */
	public static void insertFromLongArray(long[] longValues,
			DeviceAttribute deviceAttributeWritten, int dimX, int dimY)
			throws DevFailed {
		// by default for xdim = 1, send the sum.
		Double doubleSum = new Double(0);
		for (int i = 0; i < longValues.length; i++)
			doubleSum = doubleSum + longValues[i];

		if (longValues.length > dimX * dimY) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "size of array "
							+ longValues.length + " is too great",
							"AttributeHelper.insertFromLongArray(long[] values,deviceAttributeWritten)");
		}

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			deviceAttributeWritten.insert(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			deviceAttributeWritten.insert_us(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromLongArray(long[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			deviceAttributeWritten.insert_uc(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			deviceAttributeWritten.insert(doubleSum.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(doubleSum.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromIntegerArray(long[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			deviceAttributeWritten.insert_u64(doubleSum.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			deviceAttributeWritten.insert(doubleSum.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			deviceAttributeWritten.insert(doubleSum.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttributeWritten.insert(doubleSum.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(doubleSum.toString());
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			if (doubleSum.doubleValue() == 1)
				deviceAttributeWritten.insert(true);
			else
				deviceAttributeWritten.insert(false);
			break;
		case TangoConst.Tango_DEV_STATE:
			DevState[] devStateValues = new DevState[longValues.length];
			for (int i = 0; i < longValues.length; i++) {
				try {
					devStateValues[i] = DevState.from_int((Long
							.valueOf(longValues[i])).intValue());
				} catch (org.omg.CORBA.BAD_PARAM badParam) {
					devStateValues[i] = DevState.UNKNOWN;
				}
			}
			deviceAttributeWritten.insert(devStateValues);
			break;

		// Array input type
		case TangoConst.Tango_DEVVAR_SHORTARRAY:
			short[] shortValues = new short[longValues.length];
			for (int i = 0; i < longValues.length; i++)
				shortValues[i] = (Long.valueOf(longValues[i]).shortValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(shortValues);
			else
				deviceAttributeWritten.insert(shortValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_USHORTARRAY:
			short[] ushortValues = new short[longValues.length];
			for (int i = 0; i < longValues.length; i++)
				ushortValues[i] = (Long.valueOf(longValues[i]).shortValue());
			if (dimY == 0)
				deviceAttributeWritten.insert_uc(ushortValues);
			else
				deviceAttributeWritten.insert_uc(ushortValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_CHARARRAY:
			byte[] byteValues = new byte[longValues.length];
			for (int i = 0; i < longValues.length; i++)
				byteValues[i] = (Long.valueOf(longValues[i]).byteValue());

			deviceAttributeWritten.insert_uc(byteValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONGARRAY:
            int[] intValues = new int[longValues.length];
			if (dimY == 0)
				deviceAttributeWritten.insert(intValues);
			else
				deviceAttributeWritten.insert(intValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_ULONGARRAY:
			if (dimY == 0)
				deviceAttributeWritten.insert_ul(longValues);
			else
				deviceAttributeWritten.insert_ul(longValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONG64ARRAY not supported",
							"AttributeHelper.insertFromLongArray(long[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_ULONG64ARRAY not supported",
							"AttributeHelper.insertFromLongArray(long[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_FLOATARRAY:
			float[] floatValues = new float[longValues.length];
			for (int i = 0; i < longValues.length; i++)
				floatValues[i] = (Long.valueOf(longValues[i]).floatValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(floatValues);
			else
				deviceAttributeWritten.insert(floatValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
			double[] doubleValues = new double[longValues.length];
			for (int i = 0; i < longValues.length; i++)
				doubleValues[i] = (Long.valueOf(longValues[i]).doubleValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(doubleValues);
			else
				deviceAttributeWritten.insert(doubleValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_STRINGARRAY:
			String[] stringValues = new String[longValues.length];
			for (int i = 0; i < longValues.length; i++)
				stringValues[i] = (Long.valueOf(longValues[i]).toString());
			if (dimY == 0)
				deviceAttributeWritten.insert(stringValues);
			else
				deviceAttributeWritten.insert(stringValues, dimX, dimY);
			break;

		case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONGSTRINGARRAY not supported",
							"AttributeHelper.insertFromLongArray(long[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_DOUBLESTRINGARRAY not supported",
							"AttributeHelper.insertFromLongArray(long[] values,deviceAttributeWritten)");
			break;

		default:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ deviceAttributeWritten.getType()
							+ " not supported",
							"AttributeHelper.insertFromLongArray(long[] values,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from a float Array.
	 * 
	 * @param floatValues
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @param dimX
	 *            the x dimension of the attribute
	 * @param dimY
	 *            the y dimension of the attribute
	 * @throws DevFailed
	 */
	public static void insertFromFloatArray(float[] floatValues,
			DeviceAttribute deviceAttributeWritten, int dimX, int dimY)
			throws DevFailed {
		// by default for xdim = 1, send the sum.
		Double doubleSum = new Double(0);
		for (int i = 0; i < floatValues.length; i++)
			doubleSum = doubleSum + floatValues[i];

		if (floatValues.length > dimX * dimY) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "size of array "
							+ floatValues.length + " is too great",
							"AttributeHelper.insertFromFloatArray(float[] values,deviceAttributeWritten)");
		}

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			deviceAttributeWritten.insert(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			deviceAttributeWritten.insert_us(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromFloatArray(float[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			deviceAttributeWritten.insert_uc(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			deviceAttributeWritten.insert(doubleSum.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(doubleSum.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromFloatArray(float[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			deviceAttributeWritten.insert_u64(doubleSum.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			deviceAttributeWritten.insert(doubleSum.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			deviceAttributeWritten.insert(doubleSum.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttributeWritten.insert(doubleSum.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(doubleSum.toString());
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			if (doubleSum.doubleValue() == 1)
				deviceAttributeWritten.insert(true);
			else
				deviceAttributeWritten.insert(false);
			break;
		case TangoConst.Tango_DEV_STATE:
			DevState[] devStateValues = new DevState[floatValues.length];
			for (int i = 0; i < floatValues.length; i++) {
				try {
					devStateValues[i] = DevState.from_int((Float
							.valueOf(floatValues[i])).intValue());
				} catch (org.omg.CORBA.BAD_PARAM badParam) {
					devStateValues[i] = DevState.UNKNOWN;
				}
			}
			deviceAttributeWritten.insert(devStateValues);
			break;

		// Array input type
		case TangoConst.Tango_DEVVAR_SHORTARRAY:
			short[] shortValues = new short[floatValues.length];
			for (int i = 0; i < floatValues.length; i++)
				shortValues[i] = (Float.valueOf(floatValues[i]).shortValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(shortValues);
			else
				deviceAttributeWritten.insert(shortValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_USHORTARRAY:
			short[] ushortValues = new short[floatValues.length];
			for (int i = 0; i < floatValues.length; i++)
				ushortValues[i] = (Float.valueOf(floatValues[i]).shortValue());
			if (dimY == 0)
				deviceAttributeWritten.insert_uc(ushortValues);
			else
				deviceAttributeWritten.insert_uc(ushortValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_CHARARRAY:
			byte[] byteValues = new byte[floatValues.length];
			for (int i = 0; i < floatValues.length; i++)
				byteValues[i] = (Float.valueOf(floatValues[i]).byteValue());

			deviceAttributeWritten.insert_uc(byteValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONGARRAY:
            int[] longValues = new int[floatValues.length];
			for (int i = 0; i < floatValues.length; i++)
				longValues[i] = (Float.valueOf(floatValues[i]).intValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(longValues);
			else
				deviceAttributeWritten.insert(longValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_ULONGARRAY:
			long[] ulongValues = new long[floatValues.length];
			for (int i = 0; i < floatValues.length; i++)
				ulongValues[i] = (Float.valueOf(floatValues[i]).longValue());
			if (dimY == 0)
				deviceAttributeWritten.insert_ul(ulongValues);
			else
				deviceAttributeWritten.insert_ul(ulongValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONG64ARRAY not supported",
							"AttributeHelper.insertFromFloatArray(float[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_ULONG64ARRAY not supported",
							"AttributeHelper.insertFromFloatArray(long[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_FLOATARRAY:
			if (dimY == 0)
				deviceAttributeWritten.insert(floatValues);
			else
				deviceAttributeWritten.insert(floatValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
			double[] doubleValues = new double[floatValues.length];
			for (int i = 0; i < floatValues.length; i++)
				doubleValues[i] = (Float.valueOf(floatValues[i]).doubleValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(doubleValues);
			else
				deviceAttributeWritten.insert(doubleValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_STRINGARRAY:
			String[] stringValues = new String[floatValues.length];
			for (int i = 0; i < floatValues.length; i++)
				stringValues[i] = (Float.valueOf(floatValues[i]).toString());
			if (dimY == 0)
				deviceAttributeWritten.insert(stringValues);
			else
				deviceAttributeWritten.insert(stringValues, dimX, dimY);
			break;

		case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONGSTRINGARRAY not supported",
							"AttributeHelper.insertFromFloatArray(float[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_DOUBLESTRINGARRAY not supported",
							"AttributeHelper.insertFromFloatArray(float[] values,deviceAttributeWritten)");
			break;

		default:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type " + deviceAttributeWritten.getType()
									+ " not supported",
							"insertFromFloatArray.insertFromLongArray(float[] values,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from a boolean Array.
	 * 
	 * @param booleanValues
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @param dimX
	 *            the x dimension of the attribute
	 * @param dimY
	 *            the y dimension of the attribute
	 * @throws DevFailed
	 */
	public static void insertFromBooleanArray(boolean[] booleanValues,
			DeviceAttribute deviceAttributeWritten, int dimX, int dimY)
			throws DevFailed {
		// by default for xdim = 1, send the sum.
		boolean firstBoolean = false;
		if (booleanValues.length > 0)
			firstBoolean = booleanValues[0];

		Integer intValue = new Integer(0);
		if (firstBoolean)
			intValue = new Integer(1);

		if (booleanValues.length > dimX * dimY) {
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"size of array " + booleanValues.length
									+ " is too great",
							"AttributeHelper.insertFromBooleanArray(boolean[] values,deviceAttributeWritten)");
		}

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			deviceAttributeWritten.insert(intValue.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			deviceAttributeWritten.insert_us(intValue.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromBooleanArray(boolean[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			deviceAttributeWritten.insert_uc(intValue.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			deviceAttributeWritten.insert(intValue.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(intValue.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromBooleanArray(boolean[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			deviceAttributeWritten.insert_u64(intValue.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			deviceAttributeWritten.insert(intValue.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			deviceAttributeWritten.insert(intValue.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttributeWritten.insert(intValue.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(intValue.toString());
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			deviceAttributeWritten.insert(firstBoolean);
			break;
		case TangoConst.Tango_DEV_STATE:
			DevState[] devStateValues = new DevState[booleanValues.length];
			for (int i = 0; i < booleanValues.length; i++) {
				try {
					if (booleanValues[i])
						devStateValues[i] = DevState.from_int(1);
					else
						devStateValues[i] = DevState.from_int(0);
				} catch (org.omg.CORBA.BAD_PARAM badParam) {
					devStateValues[i] = DevState.UNKNOWN;
				}
			}
			deviceAttributeWritten.insert(devStateValues);
			break;

		// Array input type
		case TangoConst.Tango_DEVVAR_SHORTARRAY:
			short[] shortValues = new short[booleanValues.length];
			for (int i = 0; i < booleanValues.length; i++) {
				if (booleanValues[i])
					shortValues[i] = 1;
				else
					shortValues[i] = 0;
			}
			if (dimY == 0)
				deviceAttributeWritten.insert(shortValues);
			else
				deviceAttributeWritten.insert(shortValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_USHORTARRAY:
			short[] ushortValues = new short[booleanValues.length];
			for (int i = 0; i < booleanValues.length; i++) {
				if (booleanValues[i])
					ushortValues[i] = 1;
				else
					ushortValues[i] = 0;
			}
			if (dimY == 0)
				deviceAttributeWritten.insert_uc(ushortValues);
			else
				deviceAttributeWritten.insert_uc(ushortValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_CHARARRAY:
			byte[] byteValues = new byte[booleanValues.length];
			for (int i = 0; i < booleanValues.length; i++) {
				if (booleanValues[i])
					byteValues[i] = (Short.valueOf((short) 1)).byteValue();
				else
					byteValues[i] = (Short.valueOf((short) 0)).byteValue();
			}
			deviceAttributeWritten.insert_uc(byteValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONGARRAY:
            int[] longValues = new int[booleanValues.length];
			for (int i = 0; i < booleanValues.length; i++) {
				if (booleanValues[i])
					longValues[i] = 1;
				else
					longValues[i] = 0;
			}
			if (dimY == 0)
				deviceAttributeWritten.insert(longValues);
			else
				deviceAttributeWritten.insert(longValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_ULONGARRAY:
			long[] ulongValues = new long[booleanValues.length];
			for (int i = 0; i < booleanValues.length; i++) {
				if (booleanValues[i])
					ulongValues[i] = 1;
				else
					ulongValues[i] = 0;
			}
			if (dimY == 0)
				deviceAttributeWritten.insert_ul(ulongValues);
			else
				deviceAttributeWritten.insert_ul(ulongValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONG64ARRAY not supported",
							"AttributeHelper.insertFromBooleanArray(boolean[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_ULONG64ARRAY not supported",
							"AttributeHelper.insertFromBooleanArray(boolean[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_FLOATARRAY:
			float[] floatValues = new float[booleanValues.length];
			for (int i = 0; i < booleanValues.length; i++) {
				if (booleanValues[i])
					floatValues[i] = 1;
				else
					floatValues[i] = 0;
			}
			if (dimY == 0)
				deviceAttributeWritten.insert(floatValues);
			else
				deviceAttributeWritten.insert(floatValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
			double[] doubleValues = new double[booleanValues.length];
			for (int i = 0; i < booleanValues.length; i++) {
				if (booleanValues[i])
					doubleValues[i] = 1;
				else
					doubleValues[i] = 0;
			}
			if (dimY == 0)
				deviceAttributeWritten.insert(doubleValues);
			else
				deviceAttributeWritten.insert(doubleValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_STRINGARRAY:
			String[] stringValues = new String[booleanValues.length];
			for (int i = 0; i < booleanValues.length; i++) {
				stringValues[i] = (Boolean.valueOf(booleanValues[i]))
						.toString();
			}
			if (dimY == 0)
				deviceAttributeWritten.insert(stringValues);
			else
				deviceAttributeWritten.insert(stringValues, dimX, dimY);
			break;

		case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONGSTRINGARRAY not supported",
							"AttributeHelper.insertFromBooleanArray(boolean[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_DOUBLESTRINGARRAY not supported",
							"AttributeHelper.insertFromBooleanArray(boolean[] values,deviceAttributeWritten)");
			break;

		default:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type " + deviceAttributeWritten.getType()
									+ " not supported",
							"insertFromBooleanArray.insertFromLongArray(boolean[] values,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from a double Array.
	 * 
	 * @param doubleValues
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @param dimX
	 *            the x dimension of the attribute
	 * @param dimY
	 *            the y dimension of the attribute
	 * @throws DevFailed
	 */
	public static void insertFromDoubleArray(double[] doubleValues,
			DeviceAttribute deviceAttributeWritten, int dimX, int dimY)
			throws DevFailed {
		// by default for xdim = 1, send the sum.
		Double doubleSum = new Double(0);
		for (int i = 0; i < doubleValues.length; i++)
			doubleSum = doubleSum + doubleValues[i];

		if (doubleValues.length > dimX * dimY) {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "size of array "
							+ doubleValues.length + " is too great",
							"AttributeHelper.insertFromDoubleArray(double[] values,deviceAttributeWritten)");
		}

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			deviceAttributeWritten.insert(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			deviceAttributeWritten.insert_us(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromDoubleArray(double[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			deviceAttributeWritten.insert_uc(doubleSum.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			deviceAttributeWritten.insert(doubleSum.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(doubleSum.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromDoubleArray(double[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			deviceAttributeWritten.insert_u64(doubleSum.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			deviceAttributeWritten.insert(doubleSum.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			deviceAttributeWritten.insert(doubleSum.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttributeWritten.insert(doubleSum.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(doubleSum.toString());
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			if (doubleSum.doubleValue() == 1)
				deviceAttributeWritten.insert(true);
			else
				deviceAttributeWritten.insert(false);
			break;
		case TangoConst.Tango_DEV_STATE:
			DevState[] devStateValues = new DevState[doubleValues.length];
			for (int i = 0; i < doubleValues.length; i++) {
				try {
					devStateValues[i] = DevState.from_int((Double
							.valueOf(doubleValues[i])).intValue());
				} catch (org.omg.CORBA.BAD_PARAM badParam) {
					devStateValues[i] = DevState.UNKNOWN;
				}
			}
			deviceAttributeWritten.insert(devStateValues);
			break;

		// Array input type
		case TangoConst.Tango_DEVVAR_SHORTARRAY:
			short[] shortValues = new short[doubleValues.length];
			for (int i = 0; i < doubleValues.length; i++)
				shortValues[i] = (Double.valueOf(doubleValues[i]).shortValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(shortValues);
			else
				deviceAttributeWritten.insert(shortValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_USHORTARRAY:
			short[] ushortValues = new short[doubleValues.length];
			for (int i = 0; i < doubleValues.length; i++)
				ushortValues[i] = (Double.valueOf(doubleValues[i]).shortValue());
			if (dimY == 0)
				deviceAttributeWritten.insert_uc(ushortValues);
			else
				deviceAttributeWritten.insert_uc(ushortValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_CHARARRAY:
			byte[] byteValues = new byte[doubleValues.length];
			for (int i = 0; i < doubleValues.length; i++)
				byteValues[i] = (Double.valueOf(doubleValues[i]).byteValue());

			deviceAttributeWritten.insert_uc(byteValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONGARRAY:
            int[] longValues = new int[doubleValues.length];
			for (int i = 0; i < doubleValues.length; i++)
				longValues[i] = (Double.valueOf(doubleValues[i]).intValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(longValues);
			else
				deviceAttributeWritten.insert(longValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_ULONGARRAY:
			long[] ulongValues = new long[doubleValues.length];
			for (int i = 0; i < doubleValues.length; i++)
				ulongValues[i] = (Double.valueOf(doubleValues[i]).longValue());
			if (dimY == 0)
				deviceAttributeWritten.insert_ul(ulongValues);
			else
				deviceAttributeWritten.insert_ul(ulongValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_LONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONG64ARRAY not supported",
							"AttributeHelper.insertFromDoubleArray(double[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_ULONG64ARRAY not supported",
							"AttributeHelper.insertFromDoubleArray(double[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_FLOATARRAY:
			float[] floatValues = new float[doubleValues.length];
			for (int i = 0; i < doubleValues.length; i++)
				floatValues[i] = (Double.valueOf(doubleValues[i]).floatValue());
			if (dimY == 0)
				deviceAttributeWritten.insert(floatValues);
			else
				deviceAttributeWritten.insert(floatValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
			if (dimY == 0)
				deviceAttributeWritten.insert(doubleValues);
			else
				deviceAttributeWritten.insert(doubleValues, dimX, dimY);
			break;
		case TangoConst.Tango_DEVVAR_STRINGARRAY:
			String[] stringValues = new String[doubleValues.length];
			for (int i = 0; i < doubleValues.length; i++)
				stringValues[i] = (Double.valueOf(doubleValues[i]).toString());
			if (dimY == 0)
				deviceAttributeWritten.insert(stringValues);
			else
				deviceAttributeWritten.insert(stringValues, dimX, dimY);
			break;

		case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_LONGSTRINGARRAY not supported",
							"AttributeHelper.insertFromDoubleArray(double[] values,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEVVAR_DOUBLESTRINGARRAY not supported",
							"AttributeHelper.insertFromDoubleArray(double[] values,deviceAttributeWritten)");
			break;

		default:
			Except
					.throw_exception(
							"TANGO_WRONG_DATA_ERROR",
							"input type " + deviceAttributeWritten.getType()
									+ " not supported",
							"insertFromFloatArray.insertFromDoubleArray(double[] values,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from a String.
	 * 
	 * @param stringValue
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @throws DevFailed
	 */
	public static void insertFromString(String stringValue,
			DeviceAttribute deviceAttributeWritten) throws DevFailed {

		Double doubleValue = Double.NaN;
		try {
			doubleValue = Double.valueOf(stringValue);
		} catch (NumberFormatException e) {
			doubleValue = Double.NaN;
		}

		if (doubleValue.isNaN() && deviceAttributeWritten.getType() != TangoConst.Tango_DEV_STRING)
        {
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "Cannot insert "
							+ stringValue + " as a numerical value.",
							"AttributeHelper.insertFromString(String value,deviceAttributeWritten)");
		}

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			if (!doubleValue.isNaN())
				deviceAttributeWritten.insert(doubleValue.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			if (!doubleValue.isNaN())
				deviceAttributeWritten.insert_us(doubleValue.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromString(String value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			if (!doubleValue.isNaN())
				deviceAttributeWritten.insert_uc(doubleValue.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			if (!doubleValue.isNaN())
				deviceAttributeWritten.insert(doubleValue.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(doubleValue.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromString(String value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			if (!doubleValue.isNaN())
				deviceAttributeWritten.insert_u64(doubleValue.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			if (!doubleValue.isNaN())
				deviceAttributeWritten.insert(doubleValue.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			if (!doubleValue.isNaN())
				deviceAttributeWritten.insert(doubleValue.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			if (!doubleValue.isNaN())
				deviceAttributeWritten.insert(doubleValue.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(stringValue);
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			if (!doubleValue.isNaN()) {
				if (doubleValue.doubleValue() == 1)
					deviceAttributeWritten.insert(true);
				else
					deviceAttributeWritten.insert(false);
			}
			break;
		case TangoConst.Tango_DEV_STATE:
			if (!doubleValue.isNaN()) {
				DevState tmpDevState = StateUtilities
						.getStateForName(stringValue);
				deviceAttributeWritten.insert(tmpDevState);
			}
			break;
		default:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ deviceAttributeWritten.getType()
							+ " not supported",
							"AttributeHelper.insertFromString(String value,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from a Integer.
	 * 
	 * @param integerValue
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @throws DevFailed
	 */
	public static void insertFromInteger(Integer integerValue,
			DeviceAttribute deviceAttributeWritten) throws DevFailed {

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			deviceAttributeWritten.insert(integerValue.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			deviceAttributeWritten.insert_us(integerValue.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromInteger(Integer value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			deviceAttributeWritten.insert_uc(integerValue.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			deviceAttributeWritten.insert(integerValue.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(integerValue.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromInteger(Integer value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			deviceAttributeWritten.insert_u64(integerValue.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			deviceAttributeWritten.insert(integerValue.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			deviceAttributeWritten.insert(integerValue.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttributeWritten.insert(integerValue.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(integerValue.toString());
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			if (integerValue.doubleValue() == 1)
				deviceAttributeWritten.insert(true);
			else
				deviceAttributeWritten.insert(false);
			break;
		case TangoConst.Tango_DEV_STATE:
			try {
				deviceAttributeWritten.insert(DevState.from_int(integerValue
						.intValue()));
			} catch (org.omg.CORBA.BAD_PARAM badParam) {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"Cannot sent" + integerValue.intValue()
										+ "for input Tango_DEV_STATE type",
								"AttributeHelper.insertFromInteger(Integer value,deviceAttributeWritten)");
			}
			break;
		default:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ deviceAttributeWritten.getType()
							+ " not supported",
							"AttributeHelper.insertFromInteger(Integer value,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from a Long.
	 * 
	 * @param longValue
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @throws DevFailed
	 */
	public static void insertFromLong(Long longValue,
			DeviceAttribute deviceAttributeWritten) throws DevFailed {

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			deviceAttributeWritten.insert(longValue.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			deviceAttributeWritten.insert_us(longValue.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromLong(Long value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			deviceAttributeWritten.insert_uc(longValue.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			deviceAttributeWritten.insert(longValue.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(longValue.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromLong(Long value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			deviceAttributeWritten.insert_u64(longValue.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			deviceAttributeWritten.insert(longValue.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			deviceAttributeWritten.insert(longValue.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttributeWritten.insert(longValue.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(longValue.toString());
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			if (longValue.doubleValue() == 1)
				deviceAttributeWritten.insert(true);
			else
				deviceAttributeWritten.insert(false);
			break;
		case TangoConst.Tango_DEV_STATE:
			try {
				deviceAttributeWritten.insert(DevState.from_int(longValue
						.intValue()));
			} catch (org.omg.CORBA.BAD_PARAM badParam) {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"Cannot sent" + longValue.intValue()
										+ "for input Tango_DEV_STATE type",
								"AttributeHelper.insertFromLong(Long value,deviceAttributeWritten)");
			}
			break;
		default:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ deviceAttributeWritten.getType()
							+ " not supported",
							"AttributeHelper.insertFromLong(Long value,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from a Float.
	 * 
	 * @param floatValue
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @throws DevFailed
	 */
	public static void insertFromFloat(Float floatValue,
			DeviceAttribute deviceAttributeWritten) throws DevFailed {

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			deviceAttributeWritten.insert(floatValue.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			deviceAttributeWritten.insert_us(floatValue.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromFloat(Float value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			deviceAttributeWritten.insert_uc(floatValue.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			deviceAttributeWritten.insert(floatValue.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(floatValue.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromFloat(Float value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			deviceAttributeWritten.insert_u64(floatValue.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			deviceAttributeWritten.insert(floatValue.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			deviceAttributeWritten.insert(floatValue.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttributeWritten.insert(floatValue.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(floatValue.toString());
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			if (floatValue.doubleValue() == 1)
				deviceAttributeWritten.insert(true);
			else
				deviceAttributeWritten.insert(false);
			break;
		case TangoConst.Tango_DEV_STATE:
			try {
				deviceAttributeWritten.insert(DevState.from_int(floatValue
						.intValue()));
			} catch (org.omg.CORBA.BAD_PARAM badParam) {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"Cannot sent" + floatValue.intValue()
										+ "for input Tango_DEV_STATE type",
								"AttributeHelper.insertFromFloat(Float value,deviceAttributeWritten)");
			}
			break;
		default:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ deviceAttributeWritten.getType()
							+ " not supported",
							"AttributeHelper.insertFromFloat(Float value,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from a Boolean.
	 * 
	 * @param booleanValue
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @throws DevFailed
	 */
	public static void insertFromBoolean(Boolean booleanValue,
			DeviceAttribute deviceAttributeWritten) throws DevFailed {

		Integer integerValue = 0;
		if (booleanValue.booleanValue())
			integerValue = 1;

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			deviceAttributeWritten.insert(integerValue.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			deviceAttributeWritten.insert_us(integerValue.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromBoolean(Boolean value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			deviceAttributeWritten.insert_uc(integerValue.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			deviceAttributeWritten.insert(integerValue.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(integerValue.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromBoolean(Boolean value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			deviceAttributeWritten.insert_u64(integerValue.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			deviceAttributeWritten.insert(integerValue.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			deviceAttributeWritten.insert(integerValue.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttributeWritten.insert(integerValue.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(Boolean.toString(booleanValue
					.booleanValue()));
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			deviceAttributeWritten.insert(booleanValue.booleanValue());
			break;
		case TangoConst.Tango_DEV_STATE:
			deviceAttributeWritten.insert(DevState.from_int(integerValue
					.intValue()));
			break;
		default:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ deviceAttributeWritten.getType()
							+ " not supported",
							"AttributeHelper.insertFromBoolean(Boolean value,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from a Double.
	 * 
	 * @param doubleValue
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @throws DevFailed
	 */
	public static void insertFromDouble(Double doubleValue,
			DeviceAttribute deviceAttributeWritten) throws DevFailed {

		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			deviceAttributeWritten.insert(doubleValue.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			deviceAttributeWritten.insert_us(doubleValue.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromDouble(Double value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			deviceAttributeWritten.insert_uc(doubleValue.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			deviceAttributeWritten.insert(doubleValue.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(doubleValue.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			deviceAttributeWritten.insert(doubleValue.longValue());

//			Except
//					.throw_exception("TANGO_WRONG_DATA_ERROR",
//							"input type Tango_DEV_LONG64 not supported",
//							"AttributeHelper.insertFromDouble(Double value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			deviceAttributeWritten.insert_u64(doubleValue.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			deviceAttributeWritten.insert(doubleValue.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			deviceAttributeWritten.insert(doubleValue.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttributeWritten.insert(doubleValue.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(doubleValue.toString());
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			if (doubleValue.doubleValue() == 1)
				deviceAttributeWritten.insert(true);
			else
				deviceAttributeWritten.insert(false);
			break;
		case TangoConst.Tango_DEV_STATE:
			try {
				deviceAttributeWritten.insert(DevState.from_int(doubleValue
						.intValue()));
			} catch (org.omg.CORBA.BAD_PARAM badParam) {
				Except
						.throw_exception("TANGO_WRONG_DATA_ERROR",
								"Cannot sent" + doubleValue.intValue()
										+ "for input Tango_DEV_STATE type",
								"AttributeHelper.insertFromDouble(Double value,deviceAttributeWritten)");
			}
			break;
		default:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ deviceAttributeWritten.getType()
							+ " not supported",
							"AttributeHelper.insertFromDouble(Double value,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from a WAttribute.
	 * 
	 * @param wAttributeValue
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @throws DevFailed
	 */
	public static void insertFromWAttribute(WAttribute wAttributeValue,DeviceAttribute deviceAttributeWritten) throws DevFailed {
		Object value = null;
        
		switch (wAttributeValue.get_data_type())
        {
    		case TangoConst.Tango_DEV_SHORT:
    			value = Short.valueOf(wAttributeValue.getShortWriteValue());
    			break;
    		case TangoConst.Tango_DEV_USHORT:
    			value = Integer.valueOf(wAttributeValue.getUShortWriteValue());
    			break;
    		case TangoConst.Tango_DEV_CHAR:
                Except
    					.throw_exception(
    							"TANGO_WRONG_DATA_ERROR",
    							"input type Tango_DEV_CHAR not supported",
    							"AttributeHelper.insertFromWAttribute(WAttribute wAttributeValue,deviceAttributeWritten)");
    			break;
    		case TangoConst.Tango_DEV_UCHAR:
    			Except
    					.throw_exception(
    							"TANGO_WRONG_DATA_ERROR",
    							"input type Tango_DEV_UCHAR not supported",
    							"AttributeHelper.insertFromWAttribute(WAttribute wAttributeValue,deviceAttributeWritten)");
    			break;
    		case TangoConst.Tango_DEV_LONG:
    			value = Integer.valueOf(wAttributeValue.getLongWriteValue());
    			break;
    		case TangoConst.Tango_DEV_ULONG:
    			value = Long.valueOf(wAttributeValue.getULongWriteValue());
    			break;
    		case TangoConst.Tango_DEV_LONG64:
    			value = Long.valueOf(wAttributeValue.getLong64WriteValue());
    			break;
    		case TangoConst.Tango_DEV_ULONG64:
    			value = Long.valueOf(wAttributeValue.getULong64WriteValue());
    			break;
    		case TangoConst.Tango_DEV_INT:
    			value = Integer.valueOf(wAttributeValue.getLongWriteValue());
    			break;
    		case TangoConst.Tango_DEV_FLOAT:
    			value = Float.valueOf(Double.valueOf(
    					wAttributeValue.getDoubleWriteValue()).floatValue());
    			break;
    		case TangoConst.Tango_DEV_DOUBLE:
    			value = Double.valueOf(wAttributeValue.getDoubleWriteValue());
    			break;
    		case TangoConst.Tango_DEV_STRING:
    			value = wAttributeValue.getStringWriteValue();
    			break;
    		case TangoConst.Tango_DEV_BOOLEAN:
    			value = Boolean.valueOf(wAttributeValue.getBooleanWriteValue());
    			break;
    		case TangoConst.Tango_DEV_STATE:
    			value = wAttributeValue.getStateWriteValue();
    			break;
    		default:
    			Except
    					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
    							+ deviceAttributeWritten.getType()
    							+ " not supported",
    							"AttributeHelper.insertFromDouble(Double value,deviceAttributeWritten)");
    			break;
    	}
        insert(value, deviceAttributeWritten);
	}

	/**
	 * Insert data in DeviceAttribute from a DevState.
	 * 
	 * @param devStateValue
	 *            the value to insert on DeviceAttribute
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @throws DevFailed
	 */
	public static void insertFromDevState(DevState devStateValue,
			DeviceAttribute deviceAttributeWritten) throws DevFailed {

		Integer integerValue = Integer.valueOf(devStateValue.value());
		switch (deviceAttributeWritten.getType()) {
		case TangoConst.Tango_DEV_SHORT:
			deviceAttributeWritten.insert(integerValue.shortValue());
			break;
		case TangoConst.Tango_DEV_USHORT:
			deviceAttributeWritten.insert_us(integerValue.shortValue());
			break;
		case TangoConst.Tango_DEV_CHAR:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_CHAR not supported",
							"AttributeHelper.insertFromDevState(DevState value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_UCHAR:
			deviceAttributeWritten.insert_uc(integerValue.shortValue());
			break;
		case TangoConst.Tango_DEV_LONG:
			deviceAttributeWritten.insert(integerValue.intValue());
			break;
		case TangoConst.Tango_DEV_ULONG:
			deviceAttributeWritten.insert_ul(integerValue.longValue());
			break;
		case TangoConst.Tango_DEV_LONG64:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR",
							"input type Tango_DEV_LONG64 not supported",
							"AttributeHelper.insertFromDevState(DevState value,deviceAttributeWritten)");
			break;
		case TangoConst.Tango_DEV_ULONG64:
			deviceAttributeWritten.insert_u64(integerValue.longValue());
			break;
		case TangoConst.Tango_DEV_INT:
			deviceAttributeWritten.insert(integerValue.intValue());
			break;
		case TangoConst.Tango_DEV_FLOAT:
			deviceAttributeWritten.insert(integerValue.floatValue());
			break;
		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttributeWritten.insert(integerValue.doubleValue());
			break;
		case TangoConst.Tango_DEV_STRING:
			deviceAttributeWritten.insert(integerValue.toString());
			break;
		case TangoConst.Tango_DEV_BOOLEAN:
			if (integerValue.doubleValue() == 1)
				deviceAttributeWritten.insert(true);
			else
				deviceAttributeWritten.insert(false);
			break;
		case TangoConst.Tango_DEV_STATE:
			deviceAttributeWritten.insert(devStateValue);
			break;
		default:
			Except
					.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
							+ deviceAttributeWritten.getType()
							+ " not supported",
							"AttributeHelper.insertFromDevState(DevState value,deviceAttributeWritten)");
			break;
		}
	}

	/**
	 * Insert data in DeviceAttribute from an Object Array
	 * 
	 * @param values
	 *            the value to insert on DeviceAttribute possibles classe Short,
	 *            String, Long, Float, Boolean, Integer, Double.
	 * @param deviceAttributeWritten
	 *            the DeviceAttribute attribute to write
	 * @param dimX
	 *            the x dimension of the attribute
	 * @param dimY
	 *            the y dimension of the attribute
	 * @throws DevFailed
	 */
	public static void insertFromArray(Object[] values,
			DeviceAttribute deviceAttributeWritten, int dimX, int dimY)
			throws DevFailed {
		if (values.length > 0) {
			Object firstValue = values[0];
			if (firstValue instanceof Short) {
				short[] tmpValues = new short[values.length];
				for (int i = 0; i < tmpValues.length; i++)
					tmpValues[i] = ((Short) values[i]).shortValue();
				AttributeHelper.insertFromShortArray(tmpValues,
						deviceAttributeWritten, dimX, dimY);
			} else if (firstValue instanceof String) {
				String[] tmpValues = new String[values.length];
				for (int i = 0; i < tmpValues.length; i++)
					tmpValues[i] = ((String) values[i]);
				AttributeHelper.insertFromStringArray(tmpValues,
						deviceAttributeWritten, dimX, dimY);
			} else if (firstValue instanceof Integer) {
				int[] tmpValues = new int[values.length];
				for (int i = 0; i < tmpValues.length; i++)
					tmpValues[i] = ((Integer) values[i]).intValue();
				AttributeHelper.insertFromIntegerArray(tmpValues,
						deviceAttributeWritten, dimX, dimY);
			} else if (firstValue instanceof Long) {
				long[] tmpValues = new long[values.length];
				for (int i = 0; i < tmpValues.length; i++)
					tmpValues[i] = ((Long) values[i]).intValue();
				AttributeHelper.insertFromLongArray(tmpValues,
						deviceAttributeWritten, dimX, dimY);
			} else if (firstValue instanceof Float) {
				float[] tmpValues = new float[values.length];
				for (int i = 0; i < tmpValues.length; i++)
					tmpValues[i] = ((Float) values[i]).floatValue();
				AttributeHelper.insertFromFloatArray(tmpValues,
						deviceAttributeWritten, dimX, dimY);
			} else if (firstValue instanceof Boolean) {
				boolean[] tmpValues = new boolean[values.length];
				for (int i = 0; i < tmpValues.length; i++)
					tmpValues[i] = ((Boolean) values[i]).booleanValue();

				AttributeHelper.insertFromBooleanArray(tmpValues,
						deviceAttributeWritten, dimX, dimY);
			} else if (firstValue instanceof Double) {
				double[] tmpValues = new double[values.length];
				for (int i = 0; i < tmpValues.length; i++)
					tmpValues[i] = ((Double) values[i]).doubleValue();

				AttributeHelper.insertFromDoubleArray(tmpValues,
						deviceAttributeWritten, dimX, dimY);
			}
		}
	}

	/**
	 * Fill DbDatum from value of deviceAttribute
	 * 
	 * Doesn't work for case TangoConst.Tango_DEVVAR_CHARARRAY: case
	 * TangoConst.Tango_DEVVAR_ULONGARRAY: case TangoConst.Tango_DEV_LONG: case
	 * TangoConst.Tango_DEV_ULONG:
	 * 
	 * @param dbDatum
	 *            the DbDatum to fill
	 * @param deviceAttribute
	 *            the DeviceAttribute that contains value
	 * @throws DevFailed
	 */
	public final static void fillDbDatumFromDeviceAttribute(DbDatum dbDatum,
			DeviceAttribute deviceAttribute) throws DevFailed {

		switch (deviceAttribute.getType()) {
		case TangoConst.Tango_DEV_VOID:
			// nothing to do
			break;

		case TangoConst.Tango_DEV_BOOLEAN:
			dbDatum.insert(deviceAttribute.extractBoolean());
			break;

		case TangoConst.Tango_DEV_SHORT:
			dbDatum.insert(deviceAttribute.extractShort());
			break;

		case TangoConst.Tango_DEV_USHORT:
			dbDatum.insert(deviceAttribute.extractUShort());
			break;

		// Loading isn t supported
		// case TangoConst.Tango_DEV_LONG:
		// datum.insert(dbattr.extractLong());
		// break;

		// Loading isn t supported
		// case TangoConst.Tango_DEV_ULONG:
		// datum.insert(dbattr.extractULong());
		// break;

		case TangoConst.Tango_DEV_FLOAT:
			dbDatum.insert(deviceAttribute.extractFloat());
			break;

		case TangoConst.Tango_DEV_DOUBLE:
			dbDatum.insert(deviceAttribute.extractDouble());
			break;

		case TangoConst.Tango_DEV_STRING:
			dbDatum.insert(deviceAttribute.extractString());
			break;
		// Loading isn t supported
		// case TangoConst.Tango_DEVVAR_CHARARRAY:
		// datum.insert(dbattr.extractCharArray());
		// break;

		case TangoConst.Tango_DEVVAR_SHORTARRAY:
			dbDatum.insert(deviceAttribute.extractShortArray());
			break;

		case TangoConst.Tango_DEVVAR_USHORTARRAY:
			dbDatum.insert(deviceAttribute.extractUShortArray());
			break;

		case TangoConst.Tango_DEVVAR_LONGARRAY:
			dbDatum.insert(deviceAttribute.extractLongArray());
			break;

		case TangoConst.Tango_DEVVAR_FLOATARRAY:
			dbDatum.insert(deviceAttribute.extractFloatArray());
			break;

		case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
			dbDatum.insert(deviceAttribute.extractDoubleArray());
			break;

		case TangoConst.Tango_DEVVAR_STRINGARRAY:
			dbDatum.insert(deviceAttribute.extractStringArray());
			break;

		case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY:
			dbDatum.insert(deviceAttribute.extractLongArray());
			dbDatum.insert(deviceAttribute.extractStringArray());
			break;

		case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY:
			dbDatum.insert(deviceAttribute.extractDoubleArray());
			dbDatum.insert(deviceAttribute.extractStringArray());
			break;

		case TangoConst.Tango_DEVVAR_CHARARRAY:
		case TangoConst.Tango_DEVVAR_ULONGARRAY:
		case TangoConst.Tango_DEV_LONG:
		case TangoConst.Tango_DEV_ULONG:
		default:
			throw new UnsupportedOperationException(
					"Tango_DEVVAR_CHARARRAY, Tango_DEVVAR_ULONGARRAY, Tango_DEV_LONG, Tango_DEV_ULONG  are not supported by DbDatum or DeviceAttribute");

		}
	}

	/**
	 * Fill deviceAttribute from value of DbDatum
	 * 
	 * Doesn't work for case TangoConst.Tango_DEVVAR_CHARARRAY: case
	 * TangoConst.Tango_DEVVAR_ULONGARRAY: case TangoConst.Tango_DEV_LONG: case
	 * TangoConst.Tango_DEV_ULONG:
	 * 
	 * @param deviceAttribute
	 *            the DeviceAttribute to fill
	 * @param dbDatum
	 *            the DbDatum that contains value
	 * @throws DevFailed
	 */
	public final static void fillDeviceAttributeFromDbDatum(
			DeviceAttribute deviceAttribute, DbDatum dbDatum) throws DevFailed {

		switch (deviceAttribute.getType()) {
		case TangoConst.Tango_DEV_VOID:
			// nothing to do
			break;

		case TangoConst.Tango_DEV_BOOLEAN:
			deviceAttribute.insert(dbDatum.extractBoolean());
			break;

		case TangoConst.Tango_DEV_SHORT:
			deviceAttribute.insert(dbDatum.extractShort());
			break;

		case TangoConst.Tango_DEV_USHORT:
			deviceAttribute.insert(dbDatum.extractLong());
			break;

		case TangoConst.Tango_DEV_FLOAT:
			deviceAttribute.insert(dbDatum.extractFloat());
			break;

		case TangoConst.Tango_DEV_DOUBLE:
			deviceAttribute.insert(dbDatum.extractDouble());
			break;

		case TangoConst.Tango_DEV_STRING:
			deviceAttribute.insert(dbDatum.extractString());
			break;

		case TangoConst.Tango_DEVVAR_SHORTARRAY:
			deviceAttribute.insert(dbDatum.extractShortArray());
			break;

		case TangoConst.Tango_DEVVAR_USHORTARRAY:
			deviceAttribute.insert(dbDatum.extractLongArray());
			break;

		case TangoConst.Tango_DEVVAR_LONGARRAY:
			deviceAttribute.insert(dbDatum.extractLongArray());
			break;

		case TangoConst.Tango_DEVVAR_FLOATARRAY:
			deviceAttribute.insert(dbDatum.extractFloatArray());
			break;

		case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
			deviceAttribute.insert(dbDatum.extractDoubleArray());
			break;

		case TangoConst.Tango_DEVVAR_STRINGARRAY:
			deviceAttribute.insert(dbDatum.extractStringArray());
			break;

		case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY:
			deviceAttribute.insert(dbDatum.extractLongArray());
			deviceAttribute.insert(dbDatum.extractStringArray());
			break;

		case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY:
			deviceAttribute.insert(dbDatum.extractDoubleArray());
			deviceAttribute.insert(dbDatum.extractStringArray());
			break;

		case TangoConst.Tango_DEVVAR_CHARARRAY:
		case TangoConst.Tango_DEVVAR_ULONGARRAY:
		case TangoConst.Tango_DEV_LONG:
		case TangoConst.Tango_DEV_ULONG:
		default:
			throw new UnsupportedOperationException(
					"Tango_DEVVAR_CHARARRAY, Tango_DEVVAR_ULONGARRAY, Tango_DEV_LONG, Tango_DEV_ULONG  are not supported by DbDatum or DeviceAttribute");

		}
	}

}
