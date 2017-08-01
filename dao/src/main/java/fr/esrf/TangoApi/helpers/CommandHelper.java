//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: pascal_verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision: 25297 $
//
//-======================================================================


package fr.esrf.TangoApi.helpers;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.StateUtilities;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

import java.util.Vector;

/**
 * @author SAINTIN
 * 
 *         This class provide useful static methods to insert or extract data
 *         from DeviceData
 */
@Deprecated
public class CommandHelper {

    /**
     * Insert data in DeviceData from an Object possible classe :
     * 
     * @param value
     *            the value to write on DeviceAttribute, possibles Classe :
     *            Short, String, Long, Float, Boolean, Integer, Double, DevState
     *            or Vector, DevVarDoubleStringArray or DevVarLongStringArray
     * @param deviceDataArgin
     *            the DeviceAttribute attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insert(final Object value, final DeviceData deviceDataArgin,
	    final int dataType) throws DevFailed {

	if (value instanceof Short) {
	    CommandHelper.insertFromShort((Short) value, deviceDataArgin, dataType);
	} else if (value instanceof String) {
	    CommandHelper.insertFromString((String) value, deviceDataArgin, dataType);
	} else if (value instanceof Integer) {
	    CommandHelper.insertFromInteger((Integer) value, deviceDataArgin, dataType);
	} else if (value instanceof Long) {
	    CommandHelper.insertFromLong((Long) value, deviceDataArgin, dataType);
	} else if (value instanceof Float) {
	    CommandHelper.insertFromFloat((Float) value, deviceDataArgin, dataType);
	} else if (value instanceof Boolean) {
	    CommandHelper.insertFromBoolean((Boolean) value, deviceDataArgin, dataType);
	} else if (value instanceof Double) {
	    CommandHelper.insertFromDouble((Double) value, deviceDataArgin, dataType);
	} else if (value instanceof DevState) {
	    CommandHelper.insertFromDevState((DevState) value, deviceDataArgin, dataType);
	} else if (value instanceof DevVarDoubleStringArray) {
	    CommandHelper.insertFromDevVarDoubleStringArray((DevVarDoubleStringArray) value,
		    deviceDataArgin, dataType);
	} else if (value instanceof DevVarLongStringArray) {
	    CommandHelper.insertFromDevVarLongStringArray((DevVarLongStringArray) value,
		    deviceDataArgin, dataType);
	} else if (value instanceof Vector) {
	    CommandHelper.insertFromArray(((Vector) value).toArray(), deviceDataArgin, dataType);
	} else {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type " + value.getClass()
		    + " not supported", "CommandHelper.insert(Object value,deviceDataArgin)");
	}
    }

    /**
     * Extract data to DeviceData to an Object
     * 
     * @param deviceDataArgout
     *            the DeviceData to read
     * @return Object, possibles Classe : Short, String, Long, Float, Boolean,
     *         Integer, Double, DevState, DevVarDoubleStringArray or
     *         DevVarLongStringArray.
     * @throws DevFailed
     */
    public static Object extract(final DeviceData deviceDataArgout) throws DevFailed {
	Object argout = null;
	switch (deviceDataArgout.getType()) {
	case TangoConst.Tango_DEV_SHORT:
	    argout = Short.valueOf(deviceDataArgout.extractShort());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    argout = Integer.valueOf(deviceDataArgout.extractUShort()).shortValue();
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "out type Tango_DEV_CHAR not supported",
		    "CommandHelper.extract(deviceDataArgout)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "out type Tango_DEV_UCHAR not supported",
		    "CommandHelper.extract(deviceDataArgout)");
	    break;
	case TangoConst.Tango_DEV_LONG:
	    argout = Integer.valueOf(deviceDataArgout.extractLong());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    argout = Long.valueOf(deviceDataArgout.extractULong());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    argout = Long.valueOf(deviceDataArgout.extractLong64());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    argout = Long.valueOf(deviceDataArgout.extractULong64());
	    break;
	case TangoConst.Tango_DEV_INT:
	    argout = Integer.valueOf(deviceDataArgout.extractLong());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    argout = Float.valueOf(deviceDataArgout.extractFloat());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    argout = Double.valueOf(deviceDataArgout.extractDouble());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    argout = deviceDataArgout.extractString();
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    argout = Boolean.valueOf(deviceDataArgout.extractBoolean());
	    break;
	case TangoConst.Tango_DEV_STATE:
	    argout = deviceDataArgout.extractDevState();
	    break;
	case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY:
	    argout = deviceDataArgout.extractDoubleStringArray();
	    break;
	case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY:
	    argout = deviceDataArgout.extractLongStringArray();
	    break;
	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
		    + deviceDataArgout.getType() + " not supported",
		    "CommandHelper.extract(Short value,deviceDataArgout)");
	    break;
	}

	return argout;
    }

    /**
     * Extract data to DeviceData to an Object Array
     * 
     * @param deviceDataArgout
     *            the DeviceData to read
     * @return Object[], an array of the extracted values possibles classe :
     *         Short, String, Long, Float, Boolean, Integer, Double, DevState.
     * @throws DevFailed
     */
    public static Object[] extractArray(final DeviceData deviceDataArgout) throws DevFailed {
	Object[] argout = null;
	switch (deviceDataArgout.getType()) {
	case TangoConst.Tango_DEV_SHORT:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	case TangoConst.Tango_DEV_LONG:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	case TangoConst.Tango_DEV_INT:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    argout = new Object[] { Double.valueOf(deviceDataArgout.extractDouble()) };
	    break;
	case TangoConst.Tango_DEV_STRING:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	case TangoConst.Tango_DEV_STATE:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;

	// Array input type
	case TangoConst.Tango_DEVVAR_SHORTARRAY:
	    final short[] shortValues = deviceDataArgout.extractShortArray();
	    argout = new Object[shortValues.length];
	    for (int i = 0; i < shortValues.length; i++) {
		argout[i] = Short.valueOf(shortValues[i]);
	    }
	    break;
	case TangoConst.Tango_DEVVAR_USHORTARRAY:
	    final int[] ushortValues = deviceDataArgout.extractUShortArray();
	    argout = new Object[ushortValues.length];
	    for (int i = 0; i < ushortValues.length; i++) {
		argout[i] = Integer.valueOf(ushortValues[i]);
	    }
	    break;
	case TangoConst.Tango_DEVVAR_CHARARRAY:
	    final byte[] charValues = deviceDataArgout.extractByteArray();
	    argout = new Object[charValues.length];
	    for (int i = 0; i < charValues.length; i++) {
		argout[i] = Byte.valueOf(charValues[i]);
	    }
	    break;
	case TangoConst.Tango_DEVVAR_LONGARRAY:
	    final int[] longValues = deviceDataArgout.extractLongArray();
	    argout = new Object[longValues.length];
	    for (int i = 0; i < longValues.length; i++) {
		argout[i] = Integer.valueOf(longValues[i]);
	    }
	    break;
	case TangoConst.Tango_DEVVAR_ULONGARRAY:
	    final long[] ulongValues = deviceDataArgout.extractULongArray();
	    argout = new Object[ulongValues.length];
	    for (int i = 0; i < ulongValues.length; i++) {
		argout[i] = Long.valueOf(ulongValues[i]);
	    }
	    break;
	case TangoConst.Tango_DEVVAR_LONG64ARRAY:
	    final long[] long64Values = deviceDataArgout.extractLong64Array();
	    argout = new Object[long64Values.length];
	    for (int i = 0; i < long64Values.length; i++) {
		argout[i] = Long.valueOf(long64Values[i]);
	    }
	    break;
	case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
	    final long[] ulong64Values = deviceDataArgout.extractULong64Array();
	    argout = new Object[ulong64Values.length];
	    for (int i = 0; i < ulong64Values.length; i++) {
		argout[i] = Long.valueOf(ulong64Values[i]);
	    }
	    break;
	case TangoConst.Tango_DEVVAR_FLOATARRAY:
	    final float[] floatValues = deviceDataArgout.extractFloatArray();
	    argout = new Object[floatValues.length];
	    for (int i = 0; i < floatValues.length; i++) {
		argout[i] = Float.valueOf(floatValues[i]);
	    }
	    break;
	case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
	    final double[] doubleValues = deviceDataArgout.extractDoubleArray();
	    argout = new Object[doubleValues.length];
	    for (int i = 0; i < doubleValues.length; i++) {
		argout[i] = Double.valueOf(doubleValues[i]);
	    }
	    break;
	case TangoConst.Tango_DEVVAR_STRINGARRAY:
	    final String[] stringValues = deviceDataArgout.extractStringArray();
	    argout = new Object[stringValues.length];
	    for (int i = 0; i < stringValues.length; i++) {
		argout[i] = stringValues[i];
	    }
	    break;

	case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY:
	    argout = new Object[] { CommandHelper.extract(deviceDataArgout) };
	    break;
	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
		    + deviceDataArgout.getType() + " not supported",
		    "CommandHelper.insertFromShort(Short value,deviceDataArgin)");
	    break;
	}

	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param shortValue
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromShort(final Short shortValue, final DeviceData deviceDataArgin,
	    final int dataType) throws DevFailed {

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(shortValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(shortValue.intValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromShort(Short value,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(shortValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(shortValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(shortValue.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert(shortValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(shortValue.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(shortValue.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(shortValue.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(shortValue.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(shortValue.toString());
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (shortValue.doubleValue() == 1) {
		deviceDataArgin.insert(true);
	    } else {
		deviceDataArgin.insert(false);
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    try {
		deviceDataArgin.insert(DevState.from_int(shortValue.intValue()));
	    } catch (final org.omg.CORBA.BAD_PARAM badParam) {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "Cannot sent"
			+ shortValue.intValue() + "for input Tango_DEV_STATE type",
			"CommandHelper.insertFromShort(Short value,deviceDataArgin)");
	    }
	    break;
	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "CommandHelper.insertFromShort(Short value,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return Short, the result in Short format
     * @throws DevFailed
     */
    public static Short extractToShort(final DeviceData deviceDataArgout) throws DevFailed {
	final Object value = CommandHelper.extract(deviceDataArgout);
	Short argout = null;
	if (value instanceof Short) {
	    argout = (Short) value;
	} else if (value instanceof String) {
	    try {
		argout = Short.valueOf((String) value);
	    } catch (final Exception e) {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value
			+ " is not a numerical", "CommandHelper.extractToShort(deviceDataArgin)");
	    }
	} else if (value instanceof Integer) {
	    argout = Short.valueOf(((Integer) value).shortValue());
	} else if (value instanceof Long) {
	    argout = Short.valueOf(((Long) value).shortValue());
	} else if (value instanceof Float) {
	    argout = Short.valueOf(((Float) value).shortValue());
	} else if (value instanceof Boolean) {
	    if (((Boolean) value).booleanValue()) {
		argout = Short.valueOf((short) 1);
	    } else {
		argout = Short.valueOf((short) 0);
	    }
	} else if (value instanceof Double) {
	    argout = Short.valueOf(((Double) value).shortValue());
	} else if (value instanceof DevState) {
	    argout = Short.valueOf(Integer.valueOf(((DevState) value).value()).shortValue());
	} else {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value.getClass()
		    + " not supported",
		    "CommandHelper.extractToShort(Object value,deviceDataArgin)");
	}

	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return String, the result in String format
     * @throws DevFailed
     */
    public static String extractToString(final DeviceData deviceDataArgout) throws DevFailed {
	final Object[] values = CommandHelper.extractArray(deviceDataArgout);
	Object value = null;
	String argout = "";
	if (values.length == 1) {
	    value = values[0];
	    if (value instanceof DevState) {
		argout = StateUtilities.getNameForState((DevState) value);
	    } else if (value instanceof DevVarLongStringArray) {
		final int[] intValues = ((DevVarLongStringArray) value).lvalue;
		final String[] stringValues = ((DevVarLongStringArray) value).svalue;
		for (int i = 0; i < stringValues.length; i++) {
		    argout = argout + "DevVarLongStringArray[" + i + "]=(" + intValues[i] + ","
			    + stringValues[i] + ")\n";
		}
	    } else if (value instanceof DevVarDoubleStringArray) {
		final double[] doubleValues = ((DevVarDoubleStringArray) value).dvalue;
		final String[] stringValues = ((DevVarDoubleStringArray) value).svalue;
		for (int i = 0; i < stringValues.length; i++) {
		    argout = argout + "DevVarDoubleStringArray[" + i + "]=(" + doubleValues[i]
			    + "," + stringValues[i] + ")\n";
		}
	    } else {
		argout = value.toString();
	    }
	} else if (values.length > 1) {
	    for (int i = 0; i < values.length; i++) {
		if (values[i] instanceof DevState) {
		    argout = argout + values[i].getClass().getSimpleName() + "[" + i + "]="
			    + StateUtilities.getNameForState((DevState) values[i]) + "\n";
		} else {
		    argout = argout + values[i].getClass().getSimpleName() + "[" + i + "]="
			    + values[i].toString() + "\n";
		}
	    }
	} else {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value.getClass()
		    + " not supported",
		    "CommandHelper.extractToString(Object value,deviceDataArgin)");
	}

	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return Integer, the result in Integer format
     * @throws DevFailed
     */
    public static Integer extractToInteger(final DeviceData deviceDataArgout) throws DevFailed {
	final Object value = CommandHelper.extract(deviceDataArgout);
	Integer argout = null;
	if (value instanceof Short) {
	    argout = Integer.valueOf(((Short) value).intValue());
	} else if (value instanceof String) {
	    try {
		argout = Integer.valueOf((String) value);
	    } catch (final Exception e) {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value
			+ " is not a numerical", "CommandHelper.extractToInteger(deviceDataArgin)");
	    }
	} else if (value instanceof Integer) {
	    argout = (Integer) value;
	} else if (value instanceof Long) {
	    argout = Integer.valueOf(((Long) value).intValue());
	} else if (value instanceof Float) {
	    argout = Integer.valueOf(((Float) value).intValue());
	} else if (value instanceof Boolean) {
	    if (((Boolean) value).booleanValue()) {
		argout = Integer.valueOf(1);
	    } else {
		argout = Integer.valueOf(0);
	    }
	} else if (value instanceof Double) {
	    argout = Integer.valueOf(((Double) value).intValue());
	} else if (value instanceof DevState) {
	    argout = Integer.valueOf(((DevState) value).value());
	} else {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value.getClass()
		    + " not supported",
		    "CommandHelper.extractToInteger(Object value,deviceDataArgin)");
	}

	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return Long, the result in Long format
     * @throws DevFailed
     */
    public static Long extractToLong(final DeviceData deviceDataArgout) throws DevFailed {
	final Object value = CommandHelper.extract(deviceDataArgout);
	Long argout = null;
	if (value instanceof Short) {
	    argout = Long.valueOf(((Short) value).longValue());
	} else if (value instanceof String) {
	    try {
		argout = Long.valueOf((String) value);
	    } catch (final Exception e) {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value
			+ " is not a numerical", "CommandHelper.extractToLong(deviceDataArgin)");
	    }
	} else if (value instanceof Integer) {
	    argout = Long.valueOf(((Integer) value).longValue());
	} else if (value instanceof Long) {
	    argout = Long.valueOf(((Long) value).longValue());
	} else if (value instanceof Float) {
	    argout = Long.valueOf(((Float) value).longValue());
	} else if (value instanceof Boolean) {
	    if (((Boolean) value).booleanValue()) {
		argout = Long.valueOf(1);
	    } else {
		argout = Long.valueOf(0);
	    }
	} else if (value instanceof Double) {
	    argout = Long.valueOf(((Double) value).longValue());
	} else if (value instanceof DevState) {
	    argout = Long.valueOf(Integer.valueOf(((DevState) value).value()).longValue());
	} else {
	    Except
		    .throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value.getClass()
			    + " not supported",
			    "CommandHelper.extractToLong(Object value,deviceDataArgin)");
	}

	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return Float, the result in Float format
     * @throws DevFailed
     */
    public static Float extractToFloat(final DeviceData deviceDataArgout) throws DevFailed {
	final Object value = CommandHelper.extract(deviceDataArgout);
	Float argout = null;
	if (value instanceof Short) {
	    argout = Float.valueOf(((Short) value).floatValue());
	} else if (value instanceof String) {
	    try {
		argout = Float.valueOf((String) value);
	    } catch (final Exception e) {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value
			+ " is not a numerical", "CommandHelper.extractToFloat(deviceDataArgin)");
	    }
	} else if (value instanceof Integer) {
	    argout = Float.valueOf(((Integer) value).floatValue());
	} else if (value instanceof Long) {
	    argout = Float.valueOf(((Long) value).floatValue());
	} else if (value instanceof Float) {
	    argout = Float.valueOf(((Float) value).floatValue());
	} else if (value instanceof Boolean) {
	    if (((Boolean) value).booleanValue()) {
		argout = Float.valueOf(1);
	    } else {
		argout = Float.valueOf(0);
	    }
	} else if (value instanceof Double) {
	    argout = Float.valueOf(((Double) value).floatValue());
	} else if (value instanceof DevState) {
	    argout = Float.valueOf(Integer.valueOf(((DevState) value).value()).floatValue());
	} else {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value.getClass()
		    + " not supported",
		    "CommandHelper.extractToFloat(Object value,deviceDataArgin)");
	}

	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return Boolean, the result in Boolean format
     * @throws DevFailed
     */
    public static Boolean extractToBoolean(final DeviceData deviceDataArgout) throws DevFailed {
	final Object value = CommandHelper.extract(deviceDataArgout);
	int boolValue = 0;
	Boolean argout = Boolean.FALSE;
	;

	if (value instanceof Short) {
	    boolValue = ((Short) value).intValue();
	} else if (value instanceof String) {
	    try {
		if (Boolean.getBoolean((String) value)) {
		    boolValue = 1;
		}
	    } catch (final Exception e) {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value
			+ " is not a boolean", "CommandHelper.extractToBoolean(deviceDataArgin)");
	    }
	} else if (value instanceof Integer) {
	    boolValue = ((Integer) value).intValue();
	} else if (value instanceof Long) {
	    boolValue = ((Long) value).intValue();
	} else if (value instanceof Float) {
	    boolValue = ((Float) value).intValue();
	} else if (value instanceof Boolean) {
	    if (((Boolean) value).booleanValue()) {
		boolValue = 1;
	    }
	} else if (value instanceof Double) {
	    boolValue = ((Double) value).intValue();
	} else if (value instanceof DevState) {
	    boolValue = ((DevState) value).value();
	} else {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value.getClass()
		    + " not supported",
		    "CommandHelper.extractToBoolean(Object value,deviceDataArgin)");
	}

	if (boolValue == 1) {
	    argout = Boolean.TRUE;
	}

	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return Double, the result in Double format
     * @throws DevFailed
     */
    public static Double extractToDouble(final DeviceData deviceDataArgout) throws DevFailed {
	final Object value = CommandHelper.extract(deviceDataArgout);
	Double argout = null;
	if (value instanceof Short) {
	    argout = Double.valueOf(((Short) value).doubleValue());
	} else if (value instanceof String) {
	    try {
		argout = Double.valueOf((String) value);
	    } catch (final Exception e) {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value
			+ " is not a numerical", "CommandHelper.extractToFloat(deviceDataArgin)");
	    }
	} else if (value instanceof Integer) {
	    argout = Double.valueOf(((Integer) value).doubleValue());
	} else if (value instanceof Long) {
	    argout = Double.valueOf(((Long) value).doubleValue());
	} else if (value instanceof Float) {
	    argout = Double.valueOf(((Float) value).doubleValue());
	} else if (value instanceof Boolean) {
	    if (((Boolean) value).booleanValue()) {
		argout = Double.valueOf(1);
	    } else {
		argout = Double.valueOf(0);
	    }
	} else if (value instanceof Double) {
	    argout = (Double) value;
	} else if (value instanceof DevState) {
	    argout = Double.valueOf(Integer.valueOf(((DevState) value).value()).doubleValue());
	} else {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value.getClass()
		    + " not supported",
		    "CommandHelper.extractToFloat(Object value,deviceDataArgin)");
	}

	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return DevState, the result in Short format
     * @throws DevFailed
     */
    public static DevState extractToDevState(final DeviceData deviceDataArgout) throws DevFailed {
	final Object value = CommandHelper.extract(deviceDataArgout);
	DevState argout = null;
	if (value instanceof Short) {
	    try {
		argout = DevState.from_int(((Short) value).intValue());
	    } catch (final Exception e) {
		argout = DevState.UNKNOWN;
	    }
	} else if (value instanceof String) {
	    argout = StateUtilities.getStateForName((String) value);
	} else if (value instanceof Integer) {
	    try {
		argout = DevState.from_int(((Integer) value).intValue());
	    } catch (final Exception e) {
		argout = DevState.UNKNOWN;
	    }
	} else if (value instanceof Long) {
	    try {
		argout = DevState.from_int(((Long) value).intValue());
	    } catch (final Exception e) {
		argout = DevState.UNKNOWN;
	    }
	} else if (value instanceof Float) {
	    try {
		argout = DevState.from_int(((Float) value).intValue());
	    } catch (final Exception e) {
		argout = DevState.UNKNOWN;
	    }
	} else if (value instanceof Boolean) {
	    try {
		if (((Boolean) value).booleanValue()) {
		    argout = DevState.from_int(1);
		} else {
		    argout = DevState.from_int(0);
		}
	    } catch (final Exception e) {
		argout = DevState.UNKNOWN;
	    }
	} else if (value instanceof Double) {
	    try {
		argout = DevState.from_int(((Double) value).intValue());
	    } catch (final Exception e) {
		argout = DevState.UNKNOWN;
	    }
	} else if (value instanceof DevState) {
	    argout = (DevState) value;
	} else {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type " + value.getClass()
		    + " not supported",
		    "CommandHelper.extractToDevState(Object value,deviceDataArgin)");
	}

	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return short[]
     * @throws DevFailed
     */
    public static short[] extractToShortArray(final DeviceData deviceDataArgout) throws DevFailed {
	final Object[] values = CommandHelper.extractArray(deviceDataArgout);
	if (values == null) {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output is empty ",
		    "CommandHelper.extractToShortArray(deviceDataArgin)");
	}
	final short[] argout = new short[values.length];

	if (values.length > 0) {
	    if (values[0] instanceof Short) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Short) values[i]).shortValue();
		}
	    } else if (values[0] instanceof String) {
		try {
		    for (int i = 0; i < values.length; i++) {
			argout[i] = Double.valueOf((String) values[i]).shortValue();
		    }
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type not numerical",
			    "CommandHelper.extractToShortArray(deviceDataArgin)");
		}
	    } else if (values[0] instanceof Integer) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Integer) values[i]).shortValue();
		}
	    } else if (values[0] instanceof Long) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Long) values[i]).shortValue();
		}
	    } else if (values[0] instanceof Float) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Float) values[i]).shortValue();
		}
	    } else if (values[0] instanceof Boolean) {
		for (int i = 0; i < values.length; i++) {
		    if (((Boolean) values[i]).booleanValue()) {
			argout[i] = (short) 1;
		    } else {
			argout[i] = (short) 0;
		    }
		}
	    } else if (values[0] instanceof Double) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Double) values[i]).shortValue();
		}
	    } else if (values[0] instanceof Byte) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Byte) values[i]).shortValue();
		}
	    } else if (values[0] instanceof DevState) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = Integer.valueOf(((DevState) values[i]).value()).shortValue();
		}
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
			+ values[0].getClass() + " not supported",
			"CommandHelper.extractToShortArray(Object value,deviceDataArgin)");
	    }
	}
	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return String[]
     * @throws DevFailed
     */
    public static String[] extractToStringArray(final DeviceData deviceDataArgout) throws DevFailed {
	final Object[] values = CommandHelper.extractArray(deviceDataArgout);
	if (values == null) {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output is empty ",
		    "CommandHelper.extractToStringArray(deviceDataArgin)");
	}
	final String[] argout = new String[values.length];

	if (values.length > 0) {
	    if (values[0] instanceof Short) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Short) values[i]).toString();
		}
	    } else if (values[0] instanceof String) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = (String) values[i];
		}

	    } else if (values[0] instanceof Integer) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Integer) values[i]).toString();
		}
	    } else if (values[0] instanceof Long) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Long) values[i]).toString();
		}
	    } else if (values[0] instanceof Float) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Float) values[i]).toString();
		}
	    } else if (values[0] instanceof Boolean) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Boolean) values[i]).toString();
		}
	    } else if (values[0] instanceof Double) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Double) values[i]).toString();
		}
	    } else if (values[0] instanceof Byte) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Byte) values[i]).toString();
		}
	    } else if (values[0] instanceof DevState) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = StateUtilities.getNameForState((DevState) values[0]);
		}
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
			+ values[0].getClass() + " not supported",
			"CommandHelper.extractToStringArray(Object value,deviceDataArgin)");
	    }
	}
	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return int[]
     * @throws DevFailed
     */
    public static int[] extractToIntegerArray(final DeviceData deviceDataArgout) throws DevFailed {
	final Object[] values = CommandHelper.extractArray(deviceDataArgout);
	if (values == null) {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output is empty ",
		    "CommandHelper.extractToIntegerArray(deviceDataArgin)");
	}
	final int[] argout = new int[values.length];

	if (values.length > 0) {
	    if (values[0] instanceof Short) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Short) values[i]).intValue();
		}
	    } else if (values[0] instanceof String) {
		try {
		    for (int i = 0; i < values.length; i++) {
			argout[i] = Integer.valueOf((String) values[i]).intValue();
		    }
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type not numerical",
			    "CommandHelper.extractToIntegerArray(deviceDataArgin)");
		}
	    } else if (values[0] instanceof Integer) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Integer) values[i]).intValue();
		}
	    } else if (values[0] instanceof Long) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Long) values[i]).intValue();
		}
	    } else if (values[0] instanceof Float) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Float) values[i]).intValue();
		}
	    } else if (values[0] instanceof Boolean) {
		for (int i = 0; i < values.length; i++) {
		    if (((Boolean) values[i]).booleanValue()) {
			argout[i] = 1;
		    } else {
			argout[i] = 0;
		    }
		}
	    } else if (values[0] instanceof Double) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Double) values[i]).intValue();
		}
	    } else if (values[0] instanceof Byte) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Byte) values[i]).intValue();
		}
	    } else if (values[0] instanceof DevState) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((DevState) values[i]).value();
		}
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
			+ values[0].getClass() + " not supported",
			"CommandHelper.extractToIntegerArray(Object value,deviceDataArgin)");
	    }
	}
	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return long[]
     * @throws DevFailed
     */
    public static long[] extractToLongArray(final DeviceData deviceDataArgout) throws DevFailed {
	final Object[] values = CommandHelper.extractArray(deviceDataArgout);
	if (values == null) {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output is empty ",
		    "CommandHelper.extractToLongArray(deviceDataArgin)");
	}
	final long[] argout = new long[values.length];

	if (values.length > 0) {
	    if (values[0] instanceof Short) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Short) values[i]).longValue();
		}
	    } else if (values[0] instanceof String) {
		try {
		    for (int i = 0; i < values.length; i++) {
			argout[i] = Long.valueOf((String) values[i]).longValue();
		    }
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type not numerical",
			    "CommandHelper.extractToLongArray(deviceDataArgin)");
		}
	    } else if (values[0] instanceof Integer) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Integer) values[i]).longValue();
		}
	    } else if (values[0] instanceof Long) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Long) values[i]).longValue();
		}
	    } else if (values[0] instanceof Float) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Float) values[i]).longValue();
		}
	    } else if (values[0] instanceof Boolean) {
		for (int i = 0; i < values.length; i++) {
		    if (((Boolean) values[i]).booleanValue()) {
			argout[i] = 1;
		    } else {
			argout[i] = 0;
		    }
		}
	    } else if (values[0] instanceof Double) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Double) values[i]).longValue();
		}
	    } else if (values[0] instanceof Byte) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Byte) values[i]).longValue();
		}
	    } else if (values[0] instanceof DevState) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = Integer.valueOf(((DevState) values[i]).value()).longValue();
		}
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
			+ values[0].getClass() + " not supported",
			"CommandHelper.extractToLongArray(Object value,deviceDataArgin)");
	    }
	}
	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return float[]
     * @throws DevFailed
     */
    public static float[] extractToFloatArray(final DeviceData deviceDataArgout) throws DevFailed {
	final Object[] values = CommandHelper.extractArray(deviceDataArgout);
	if (values == null) {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output is empty ",
		    "CommandHelper.extractToFloatArray(deviceDataArgin)");
	}
	final float[] argout = new float[values.length];

	if (values.length > 0) {
	    if (values[0] instanceof Short) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Short) values[i]).floatValue();
		}
	    } else if (values[0] instanceof String) {
		try {
		    for (int i = 0; i < values.length; i++) {
			argout[i] = Float.valueOf((String) values[i]).floatValue();
		    }
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type not numerical",
			    "CommandHelper.extractToFloatArray(deviceDataArgin)");
		}
	    } else if (values[0] instanceof Integer) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Integer) values[i]).floatValue();
		}
	    } else if (values[0] instanceof Long) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Long) values[i]).floatValue();
		}
	    } else if (values[0] instanceof Float) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Float) values[i]).floatValue();
		}
	    } else if (values[0] instanceof Boolean) {
		for (int i = 0; i < values.length; i++) {
		    if (((Boolean) values[i]).booleanValue()) {
			argout[i] = 1;
		    } else {
			argout[i] = 0;
		    }
		}
	    } else if (values[0] instanceof Double) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Double) values[i]).floatValue();
		}
	    } else if (values[0] instanceof Byte) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Byte) values[i]).floatValue();
		}
	    } else if (values[0] instanceof DevState) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = Integer.valueOf(((DevState) values[i]).value()).floatValue();
		}
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
			+ values[0].getClass() + " not supported",
			"CommandHelper.extractToFloatArray(Object value,deviceDataArgin)");
	    }
	}
	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return boolean[]
     * @throws DevFailed
     */
    public static boolean[] extractToBooleanArray(final DeviceData deviceDataArgout)
	    throws DevFailed {
	final Object[] values = CommandHelper.extractArray(deviceDataArgout);
	if (values == null) {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output is empty ",
		    "CommandHelper.extractToBooleanArray(deviceDataArgin)");
	}
	final boolean[] argout = new boolean[values.length];

	if (values.length > 0) {
	    if (values[0] instanceof Short) {
		for (int i = 0; i < values.length; i++) {
		    if (((Short) values[i]).intValue() == 1) {
			argout[i] = true;
		    } else {
			argout[i] = false;
		    }
		}
	    } else if (values[0] instanceof String) {
		try {
		    for (int i = 0; i < values.length; i++) {
			argout[i] = Boolean.getBoolean((String) values[i]);
		    }
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type not numerical",
			    "CommandHelper.extractToBooleanArray(deviceDataArgin)");
		}
	    } else if (values[0] instanceof Integer) {
		for (int i = 0; i < values.length; i++) {
		    if (((Integer) values[i]).intValue() == 1) {
			argout[i] = true;
		    } else {
			argout[i] = false;
		    }
		}
	    } else if (values[0] instanceof Long) {
		for (int i = 0; i < values.length; i++) {
		    if (((Long) values[i]).intValue() == 1) {
			argout[i] = true;
		    } else {
			argout[i] = false;
		    }
		}
	    } else if (values[0] instanceof Float) {
		for (int i = 0; i < values.length; i++) {
		    if (((Float) values[i]).intValue() == 1) {
			argout[i] = true;
		    } else {
			argout[i] = false;
		    }
		}
	    } else if (values[0] instanceof Boolean) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Boolean) values[i]).booleanValue();
		}
	    } else if (values[0] instanceof Double) {
		for (int i = 0; i < values.length; i++) {
		    if (((Double) values[i]).intValue() == 1) {
			argout[i] = true;
		    } else {
			argout[i] = false;
		    }
		}
	    } else if (values[0] instanceof Byte) {
		for (int i = 0; i < values.length; i++) {
		    if (((Byte) values[i]).intValue() == 1) {
			argout[i] = true;
		    } else {
			argout[i] = false;
		    }
		}
	    } else if (values[0] instanceof DevState) {
		for (int i = 0; i < values.length; i++) {
		    if (((DevState) values[i]).value() == 1) {
			argout[i] = true;
		    } else {
			argout[i] = false;
		    }
		}
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
			+ values[0].getClass() + " not supported",
			"CommandHelper.extractToBooleanArray(Object value,deviceDataArgin)");
	    }
	}
	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return byte[]
     * @throws DevFailed
     */
    public static byte[] extractToByteArray(final DeviceData deviceDataArgout) throws DevFailed {
	final Object[] values = CommandHelper.extractArray(deviceDataArgout);
	if (values == null) {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output is empty ",
		    "CommandHelper.extractToFloatArray(deviceDataArgin)");
	}
	final byte[] argout = new byte[values.length];

	if (values.length > 0) {
	    if (values[0] instanceof Short) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Short) values[i]).byteValue();
		}
	    } else if (values[0] instanceof String) {
		try {
		    for (int i = 0; i < values.length; i++) {
			argout[i] = Float.valueOf((String) values[i]).byteValue();
		    }
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type not numerical",
			    "CommandHelper.extractToFloatArray(deviceDataArgin)");
		}
	    } else if (values[0] instanceof Integer) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Integer) values[i]).byteValue();
		}
	    } else if (values[0] instanceof Long) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Long) values[i]).byteValue();
		}
	    } else if (values[0] instanceof Float) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Float) values[i]).byteValue();
		}
	    } else if (values[0] instanceof Boolean) {
		for (int i = 0; i < values.length; i++) {
		    if (((Boolean) values[i]).booleanValue()) {
			argout[i] = 1;
		    } else {
			argout[i] = 0;
		    }
		}
	    } else if (values[0] instanceof Double) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Double) values[i]).byteValue();
		}
	    } else if (values[0] instanceof Byte) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Byte) values[i]).byteValue();
		}
	    } else if (values[0] instanceof DevState) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = Integer.valueOf(((DevState) values[i]).value()).byteValue();
		}
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
			+ values[0].getClass() + " not supported",
			"CommandHelper.extractToFloatArray(Object value,deviceDataArgin)");
	    }
	}
	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return double[]
     * @throws DevFailed
     */
    public static double[] extractToDoubleArray(final DeviceData deviceDataArgout) throws DevFailed {
	final Object[] values = CommandHelper.extractArray(deviceDataArgout);
	if (values == null) {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output is empty ",
		    "CommandHelper.extractToDoubleArray(deviceDataArgin)");
	}
	final double[] argout = new double[values.length];

	if (values.length > 0) {
	    if (values[0] instanceof Short) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Short) values[i]).doubleValue();
		}
	    } else if (values[0] instanceof String) {
		try {
		    for (int i = 0; i < values.length; i++) {
			argout[i] = Double.valueOf((String) values[i]).doubleValue();
		    }
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type not numerical",
			    "CommandHelper.extractToDoubleArray(deviceDataArgin)");
		}
	    } else if (values[0] instanceof Integer) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Integer) values[i]).doubleValue();
		}
	    } else if (values[0] instanceof Long) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Long) values[i]).doubleValue();
		}
	    } else if (values[0] instanceof Float) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Float) values[i]).doubleValue();
		}
	    } else if (values[0] instanceof Boolean) {
		for (int i = 0; i < values.length; i++) {
		    if (((Boolean) values[i]).booleanValue()) {
			argout[i] = 1;
		    } else {
			argout[i] = 0;
		    }
		}
	    } else if (values[0] instanceof Double) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Double) values[i]).doubleValue();
		}
	    } else if (values[0] instanceof Byte) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = ((Byte) values[i]).doubleValue();
		}
	    } else if (values[0] instanceof DevState) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = Integer.valueOf(((DevState) values[i]).value()).doubleValue();
		}
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
			+ values[0].getClass() + " not supported",
			"CommandHelper.extractToDoubleArray(Object value,deviceDataArgin)");
	    }
	}
	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param deviceDataArgout
     *            the DeviceData attribute to read
     * @return DevState[]
     * @throws DevFailed
     */
    public static DevState[] extractToDevStateArray(final DeviceData deviceDataArgout)
	    throws DevFailed {
	final Object[] values = CommandHelper.extractArray(deviceDataArgout);
	if (values == null) {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output is empty ",
		    "CommandHelper.extractToFloatArray(deviceDataArgin)");
	}
	final DevState[] argout = new DevState[values.length];

	if (values.length > 0) {
	    if (values[0] instanceof Short) {
		for (int i = 0; i < values.length; i++) {
		    try {
			argout[i] = DevState.from_int(((Short) values[i]).intValue());
		    } catch (final Exception e) {
			argout[i] = DevState.UNKNOWN;
		    }
		}
	    } else if (values[0] instanceof String) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = StateUtilities.getStateForName((String) values[i]);
		}
	    } else if (values[0] instanceof Integer) {
		for (int i = 0; i < values.length; i++) {
		    try {
			argout[i] = DevState.from_int(((Integer) values[i]).intValue());
		    } catch (final Exception e) {
			argout[i] = DevState.UNKNOWN;
		    }
		}
	    } else if (values[0] instanceof Long) {
		for (int i = 0; i < values.length; i++) {
		    try {
			argout[i] = DevState.from_int(((Long) values[i]).intValue());
		    } catch (final Exception e) {
			argout[i] = DevState.UNKNOWN;
		    }
		}
	    } else if (values[0] instanceof Float) {
		for (int i = 0; i < values.length; i++) {
		    try {
			argout[i] = DevState.from_int(((Float) values[i]).intValue());
		    } catch (final Exception e) {
			argout[i] = DevState.UNKNOWN;
		    }
		}
	    } else if (values[0] instanceof Boolean) {
		for (int i = 0; i < values.length; i++) {
		    try {
			if (((Boolean) values[i]).booleanValue()) {
			    argout[i] = DevState.from_int(1);
			} else {
			    argout[i] = DevState.from_int(0);
			}
		    } catch (final Exception e) {
			argout[i] = DevState.UNKNOWN;
		    }
		}
	    } else if (values[0] instanceof Double) {
		for (int i = 0; i < values.length; i++) {
		    try {
			argout[i] = DevState.from_int(((Double) values[i]).intValue());
		    } catch (final Exception e) {
			argout[i] = DevState.UNKNOWN;
		    }
		}
	    } else if (values[0] instanceof Byte) {
		for (int i = 0; i < values.length; i++) {
		    try {
			argout[i] = DevState.from_int(((Byte) values[i]).intValue());
		    } catch (final Exception e) {
			argout[i] = DevState.UNKNOWN;
		    }
		}
	    } else if (values[0] instanceof DevState) {
		for (int i = 0; i < values.length; i++) {
		    argout[i] = (DevState) values[i];
		}
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "output type "
			+ values[0].getClass() + " not supported",
			"CommandHelper.extractToFloatArray(Object value,deviceDataArgin)");
	    }
	}
	return argout;
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param shortValues
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromShortArray(final short[] shortValues,
	    final DeviceData deviceDataArgin, final int dataType) throws DevFailed {
	// by default for xdim = 1, send the sum.
	Double doubleSum = new Double(0);
	for (final short shortValue : shortValues) {
	    doubleSum = doubleSum + shortValue;
	}

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromShortArray(short[] values,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(doubleSum.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(doubleSum.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(doubleSum.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(doubleSum.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(doubleSum.toString());
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (doubleSum.doubleValue() == 1) {
		deviceDataArgin.insert(true);
	    } else {
		deviceDataArgin.insert(false);
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    DevState devStateValue = DevState.UNKNOWN;
	    if (shortValues.length > 0) {
		try {
		    devStateValue = DevState.from_int(Short.valueOf(shortValues[0]).intValue());
		} catch (final org.omg.CORBA.BAD_PARAM badParam) {
		    devStateValue = DevState.UNKNOWN;
		}
	    }
	    deviceDataArgin.insert(devStateValue);
	    break;

	// Array input type
	case TangoConst.Tango_DEVVAR_SHORTARRAY:
	    deviceDataArgin.insert(shortValues);
	    break;
	case TangoConst.Tango_DEVVAR_USHORTARRAY:
	    deviceDataArgin.insert_us(shortValues);
	    break;
	case TangoConst.Tango_DEVVAR_CHARARRAY:
	    final byte[] byteValues = new byte[shortValues.length];
	    for (int i = 0; i < shortValues.length; i++) {
		byteValues[i] = Short.valueOf(shortValues[i]).byteValue();
	    }
	    deviceDataArgin.insert(byteValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONGARRAY:
	    final int[] longValues = new int[shortValues.length];
	    for (int i = 0; i < shortValues.length; i++) {
		longValues[i] = Short.valueOf(shortValues[i]).intValue();
	    }
	    deviceDataArgin.insert(longValues);
	    break;
	case TangoConst.Tango_DEVVAR_ULONGARRAY:
	    final long[] ulongValues = new long[shortValues.length];
	    for (int i = 0; i < shortValues.length; i++) {
		ulongValues[i] = Short.valueOf(shortValues[i]).longValue();
	    }
	    deviceDataArgin.insert_ul(ulongValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONG64ARRAY:
	    final int[] long64Values = new int[shortValues.length];
	    for (int i = 0; i < shortValues.length; i++) {
		long64Values[i] = Short.valueOf(shortValues[i]).intValue();
	    }
	    deviceDataArgin.insert(long64Values);
	    break;
	case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
	    final long[] uLong64Values = new long[shortValues.length];
	    for (int i = 0; i < shortValues.length; i++) {
		uLong64Values[i] = Short.valueOf(shortValues[i]).longValue();
	    }
	    deviceDataArgin.insert_u64(uLong64Values);
	    break;
	case TangoConst.Tango_DEVVAR_FLOATARRAY:
	    final float[] floatValues = new float[shortValues.length];
	    for (int i = 0; i < shortValues.length; i++) {
		floatValues[i] = Short.valueOf(shortValues[i]).floatValue();
	    }
	    deviceDataArgin.insert(floatValues);
	    break;
	case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
	    final double[] doubleValues = new double[shortValues.length];
	    for (int i = 0; i < shortValues.length; i++) {
		doubleValues[i] = Short.valueOf(shortValues[i]).doubleValue();
	    }
	    deviceDataArgin.insert(doubleValues);
	    break;
	case TangoConst.Tango_DEVVAR_STRINGARRAY:
	    final String[] stringValues = new String[shortValues.length];
	    for (int i = 0; i < shortValues.length; i++) {
		stringValues[i] = Short.valueOf(shortValues[i]).toString();
	    }
	    deviceDataArgin.insert(stringValues);
	    break;

	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "CommandHelper.insertFromShortArray(short[] values,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param stringValues
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromStringArray(final String[] stringValues,
	    final DeviceData deviceDataArgin, final int dataType) throws DevFailed {
	// by default for xdim = 1, send the first value.
	String firsString = "";
	Double numericalValue = Double.NaN;

	if (stringValues.length > 0) {
	    firsString = stringValues[0];
	}

	try {
	    numericalValue = Double.valueOf(firsString);
	} catch (final Exception e) {
	    numericalValue = Double.NaN;
	}

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    if (!numericalValue.isNaN()) {
		deviceDataArgin.insert(numericalValue.shortValue());
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
			+ " is not a Tango_DEV_SHORT",
			"CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
	    }
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    if (!numericalValue.isNaN()) {
		deviceDataArgin.insert_us(numericalValue.shortValue());
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
			+ " is not a Tango_DEV_USHORT",
			"CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
	    }
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    deviceDataArgin.insert(numericalValue.byteValue());
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    if (!numericalValue.isNaN()) {
		deviceDataArgin.insert(numericalValue.shortValue());
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
			+ " is not a Tango_DEV_UCHAR",
			"CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
	    }
	    break;
	case TangoConst.Tango_DEV_LONG:
	    if (!numericalValue.isNaN()) {
		deviceDataArgin.insert(numericalValue.intValue());
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
			+ " is not a Tango_DEV_LONG",
			"CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
	    }
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    if (!numericalValue.isNaN()) {
		deviceDataArgin.insert_ul(numericalValue.longValue());
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
			+ " is not a Tango_DEV_ULONG",
			"CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
	    }
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_LONG64 not supported",
		    "CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    if (!numericalValue.isNaN()) {
		deviceDataArgin.insert_u64(numericalValue.longValue());
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
			+ " is not a Tango_DEV_ULONG64",
			"CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
	    }
	    break;
	case TangoConst.Tango_DEV_INT:
	    if (!numericalValue.isNaN()) {
		deviceDataArgin.insert(numericalValue.intValue());
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
			+ " is not a Tango_DEV_INT",
			"CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
	    }
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    if (!numericalValue.isNaN()) {
		deviceDataArgin.insert(numericalValue.floatValue());
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
			+ " is not a Tango_DEV_FLOAT",
			"CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
	    }
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    if (!numericalValue.isNaN()) {
		deviceDataArgin.insert(numericalValue.doubleValue());
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
			+ " is not a Tango_DEV_DOUBLE",
			"CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
	    }
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(firsString);
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (!numericalValue.isNaN()) {
		if (numericalValue.doubleValue() == 1) {
		    deviceDataArgin.insert(true);
		} else {
		    deviceDataArgin.insert(false);
		}
	    } else {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", firsString
			+ " is not a Tango_DEV_BOOLEAN",
			"CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "Tango_DEV_STATE is not supported",
		    "CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
	    break;

	// Array input type
	case TangoConst.Tango_DEVVAR_SHORTARRAY:
	    final short[] shortValues = new short[stringValues.length];
	    for (int i = 0; i < stringValues.length; i++) {
		try {
		    shortValues[i] = Double.valueOf(stringValues[i]).shortValue();
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
			    "input is not a Tango_DEVVAR_SHORTARRAY",
			    "CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
		}
	    }
	    deviceDataArgin.insert(shortValues);
	    break;

	case TangoConst.Tango_DEVVAR_USHORTARRAY:
	    final short[] ushortValues = new short[stringValues.length];
	    for (int i = 0; i < stringValues.length; i++) {
		try {
		    ushortValues[i] = Double.valueOf(stringValues[i]).shortValue();
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
			    "input is not a Tango_DEVVAR_USHORTARRAY",
			    "CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
		}
	    }
	    deviceDataArgin.insert_us(ushortValues);
	    break;
	case TangoConst.Tango_DEVVAR_CHARARRAY:
	    final byte[] byteValues = new byte[stringValues.length];
	    for (int i = 0; i < stringValues.length; i++) {
		try {
		    byteValues[i] = Double.valueOf(stringValues[i]).byteValue();
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
			    "input is not a Tango_DEVVAR_CHARARRAY",
			    "CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
		}
	    }
	    deviceDataArgin.insert(byteValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONGARRAY:
	    final int[] longValues = new int[stringValues.length];
	    for (int i = 0; i < stringValues.length; i++) {
		try {
		    longValues[i] = Double.valueOf(stringValues[i]).intValue();
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
			    "input is not a Tango_DEVVAR_LONGARRAY",
			    "CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
		}
	    }
	    deviceDataArgin.insert(longValues);
	    break;
	case TangoConst.Tango_DEVVAR_ULONGARRAY:
	    final long[] ulongValues = new long[stringValues.length];
	    for (int i = 0; i < stringValues.length; i++) {
		try {
		    ulongValues[i] = Double.valueOf(stringValues[i]).longValue();
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
			    "input is not a Tango_DEVVAR_LONGARRAY",
			    "CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
		}
	    }
	    deviceDataArgin.insert_ul(ulongValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONG64ARRAY:
	    final int[] long64Values = new int[stringValues.length];
	    for (int i = 0; i < stringValues.length; i++) {
		try {
		    long64Values[i] = Double.valueOf(stringValues[i]).intValue();
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
			    "input is not a Tango_DEVVAR_LONGARRAY",
			    "CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
		}
	    }
	    deviceDataArgin.insert(long64Values);
	    break;
	case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
	    final long[] ulong64Values = new long[stringValues.length];
	    for (int i = 0; i < stringValues.length; i++) {
		try {
		    ulong64Values[i] = Double.valueOf(stringValues[i]).longValue();
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
			    "input is not a Tango_DEVVAR_LONGARRAY",
			    "CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
		}
	    }
	    deviceDataArgin.insert_u64(ulong64Values);
	    break;
	case TangoConst.Tango_DEVVAR_FLOATARRAY:
	    final float[] floatValues = new float[stringValues.length];
	    for (int i = 0; i < stringValues.length; i++) {
		try {
		    floatValues[i] = Double.valueOf(stringValues[i]).floatValue();
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
			    "input is not a Tango_DEVVAR_FLOATARRAY",
			    "CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
		}
	    }
	    deviceDataArgin.insert(floatValues);
	    break;
	case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
	    final double[] doubleValues = new double[stringValues.length];
	    for (int i = 0; i < stringValues.length; i++) {
		try {
		    doubleValues[i] = Double.valueOf(stringValues[i]).doubleValue();
		} catch (final Exception e) {
		    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
			    "input is not a Tango_DEVVAR_DOUBLEARRAY",
			    "CommandHelper.insertFromStringArray(String[] values,deviceDataArgin)");
		}
	    }
	    deviceDataArgin.insert(doubleValues);
	    break;
	case TangoConst.Tango_DEVVAR_STRINGARRAY:
	    deviceDataArgin.insert(stringValues);
	    break;

	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "CommandHelper.insertFromStringArray(String[] value,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param intValues
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromIntegerArray(final int[] intValues,
	    final DeviceData deviceDataArgin, final int dataType) throws DevFailed {
	// by default for xdim = 1, send the sum.
	Double doubleSum = new Double(0);
	for (final int intValue : intValues) {
	    doubleSum = doubleSum + intValue;
	}

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromIntegerArray(int[] values,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(doubleSum.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(doubleSum.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(doubleSum.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(doubleSum.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(doubleSum.toString());
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (doubleSum.doubleValue() == 1) {
		deviceDataArgin.insert(true);
	    } else {
		deviceDataArgin.insert(false);
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    DevState devStateValue = DevState.UNKNOWN;
	    if (intValues.length > 0) {
		try {
		    devStateValue = DevState.from_int(intValues[0]);
		} catch (final org.omg.CORBA.BAD_PARAM badParam) {
		    devStateValue = DevState.UNKNOWN;
		}
	    }
	    deviceDataArgin.insert(devStateValue);
	    break;

	// Array input type
	case TangoConst.Tango_DEVVAR_SHORTARRAY:
	    final short[] shortValues = new short[intValues.length];
	    for (int i = 0; i < intValues.length; i++) {
		shortValues[i] = Integer.valueOf(intValues[i]).shortValue();
	    }
	    deviceDataArgin.insert(shortValues);
	    break;
	case TangoConst.Tango_DEVVAR_USHORTARRAY:
	    final short[] ushortValues = new short[intValues.length];
	    for (int i = 0; i < intValues.length; i++) {
		ushortValues[i] = Integer.valueOf(intValues[i]).shortValue();
	    }
	    deviceDataArgin.insert_us(ushortValues);
	    break;
	case TangoConst.Tango_DEVVAR_CHARARRAY:
	    final byte[] byteValues = new byte[intValues.length];
	    for (int i = 0; i < intValues.length; i++) {
		byteValues[i] = Integer.valueOf(intValues[i]).byteValue();
	    }
	    deviceDataArgin.insert(byteValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONGARRAY:
	    deviceDataArgin.insert(intValues);
	    break;
	case TangoConst.Tango_DEVVAR_ULONGARRAY:
	    final long[] ulongValues = new long[intValues.length];
	    for (int i = 0; i < intValues.length; i++) {
		ulongValues[i] = Integer.valueOf(intValues[i]).longValue();
	    }
	    deviceDataArgin.insert_ul(ulongValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONG64ARRAY:
	    final int[] long64Values = new int[intValues.length];
	    for (int i = 0; i < intValues.length; i++) {
		long64Values[i] = Integer.valueOf(intValues[i]).intValue();
	    }
	    deviceDataArgin.insert_ul(long64Values);
	    break;
	case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
	    final long[] ulong64Values = new long[intValues.length];
	    for (int i = 0; i < intValues.length; i++) {
		ulong64Values[i] = Integer.valueOf(intValues[i]).longValue();
	    }
	    deviceDataArgin.insert_u64(ulong64Values);
	    break;
	case TangoConst.Tango_DEVVAR_FLOATARRAY:
	    final float[] floatValues = new float[intValues.length];
	    for (int i = 0; i < intValues.length; i++) {
		floatValues[i] = Integer.valueOf(intValues[i]).floatValue();
	    }

	    deviceDataArgin.insert(floatValues);
	    break;
	case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
	    final double[] doubleValues = new double[intValues.length];
	    for (int i = 0; i < intValues.length; i++) {
		doubleValues[i] = Integer.valueOf(intValues[i]).doubleValue();
	    }
	    deviceDataArgin.insert(doubleValues);
	    break;
	case TangoConst.Tango_DEVVAR_STRINGARRAY:
	    final String[] stringValues = new String[intValues.length];
	    for (int i = 0; i < intValues.length; i++) {
		stringValues[i] = Integer.valueOf(intValues[i]).toString();
	    }
	    deviceDataArgin.insert(stringValues);
	    break;

	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "CommandHelper.insertFromIntegerArray(int[] values,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param longValues
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromLongArray(final long[] longValues,
	    final DeviceData deviceDataArgin, final int dataType) throws DevFailed {
	// by default for xdim = 1, send the sum.
	Double doubleSum = new Double(0);
	for (final long longValue : longValues) {
	    doubleSum = doubleSum + longValue;
	}

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromLongArray(long[] values,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(doubleSum.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(doubleSum.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(doubleSum.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(doubleSum.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(doubleSum.toString());
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (doubleSum.doubleValue() == 1) {
		deviceDataArgin.insert(true);
	    } else {
		deviceDataArgin.insert(false);
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    DevState devStateValue = DevState.UNKNOWN;
	    if (longValues.length > 0) {
		try {
		    devStateValue = DevState.from_int(Long.valueOf(longValues[0]).intValue());
		} catch (final org.omg.CORBA.BAD_PARAM badParam) {
		    devStateValue = DevState.UNKNOWN;
		}
	    }
	    deviceDataArgin.insert(devStateValue);
	    break;
	// Array input type
	case TangoConst.Tango_DEVVAR_SHORTARRAY:
	    final short[] shortValues = new short[longValues.length];
	    for (int i = 0; i < longValues.length; i++) {
		shortValues[i] = Long.valueOf(longValues[i]).shortValue();
	    }
	    deviceDataArgin.insert(shortValues);
	    break;
	case TangoConst.Tango_DEVVAR_USHORTARRAY:
	    final short[] ushortValues = new short[longValues.length];
	    for (int i = 0; i < longValues.length; i++) {
		ushortValues[i] = Long.valueOf(longValues[i]).shortValue();
	    }
	    deviceDataArgin.insert_us(ushortValues);
	    break;
	case TangoConst.Tango_DEVVAR_CHARARRAY:
	    final byte[] byteValues = new byte[longValues.length];
	    for (int i = 0; i < longValues.length; i++) {
		byteValues[i] = Long.valueOf(longValues[i]).byteValue();
	    }
	    deviceDataArgin.insert(byteValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONGARRAY:
	    final int[] intValues = new int[longValues.length];
	    for (int i = 0; i < longValues.length; i++) {
		intValues[i] = Long.valueOf(longValues[i]).intValue();
	    }
	    deviceDataArgin.insert(intValues);
	    break;
	case TangoConst.Tango_DEVVAR_ULONGARRAY:
	    deviceDataArgin.insert_ul(longValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONG64ARRAY:
	    final int[] long64Values = new int[longValues.length];
	    for (int i = 0; i < longValues.length; i++) {
		long64Values[i] = Long.valueOf(longValues[i]).intValue();
	    }
	    deviceDataArgin.insert(long64Values);
	    break;
	case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
	    deviceDataArgin.insert_u64(longValues);
	    break;
	case TangoConst.Tango_DEVVAR_FLOATARRAY:
	    final float[] floatValues = new float[longValues.length];
	    for (int i = 0; i < longValues.length; i++) {
		floatValues[i] = Long.valueOf(longValues[i]).floatValue();
	    }
	    deviceDataArgin.insert(floatValues);
	    break;
	case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
	    final double[] doubleValues = new double[longValues.length];
	    for (int i = 0; i < longValues.length; i++) {
		doubleValues[i] = Long.valueOf(longValues[i]).doubleValue();
	    }
	    deviceDataArgin.insert(doubleValues);
	    break;
	case TangoConst.Tango_DEVVAR_STRINGARRAY:
	    final String[] stringValues = new String[longValues.length];
	    for (int i = 0; i < longValues.length; i++) {
		stringValues[i] = Long.valueOf(longValues[i]).toString();
	    }
	    deviceDataArgin.insert(stringValues);
	    break;

	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "CommandHelper.insertFromLongArray(long[] values,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param floatValues
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromFloatArray(final float[] floatValues,
	    final DeviceData deviceDataArgin, final int dataType) throws DevFailed {
	// by default for xdim = 1, send the sum.
	Double doubleSum = new Double(0);
	for (final float floatValue : floatValues) {
	    doubleSum = doubleSum + floatValue;
	}

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromFloatArray(float[] values,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(doubleSum.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert_ul(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(doubleSum.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(doubleSum.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(doubleSum.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(doubleSum.toString());
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (doubleSum.doubleValue() == 1) {
		deviceDataArgin.insert(true);
	    } else {
		deviceDataArgin.insert(false);
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    DevState devStateValue = DevState.UNKNOWN;
	    if (floatValues.length > 0) {
		try {
		    devStateValue = DevState.from_int(Float.valueOf(floatValues[0]).intValue());
		} catch (final org.omg.CORBA.BAD_PARAM badParam) {
		    devStateValue = DevState.UNKNOWN;
		}
	    }
	    deviceDataArgin.insert(devStateValue);
	    break;

	// Array input type
	case TangoConst.Tango_DEVVAR_SHORTARRAY:
	    final short[] shortValues = new short[floatValues.length];
	    for (int i = 0; i < floatValues.length; i++) {
		shortValues[i] = Float.valueOf(floatValues[i]).shortValue();
	    }
	    deviceDataArgin.insert(shortValues);
	    break;
	case TangoConst.Tango_DEVVAR_USHORTARRAY:
	    final short[] ushortValues = new short[floatValues.length];
	    for (int i = 0; i < floatValues.length; i++) {
		ushortValues[i] = Float.valueOf(floatValues[i]).shortValue();
	    }
	    deviceDataArgin.insert(ushortValues);
	    break;
	case TangoConst.Tango_DEVVAR_CHARARRAY:
	    final byte[] byteValues = new byte[floatValues.length];
	    for (int i = 0; i < floatValues.length; i++) {
		byteValues[i] = Float.valueOf(floatValues[i]).byteValue();
	    }
	    deviceDataArgin.insert(byteValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONGARRAY:
	    final int[] longValues = new int[floatValues.length];
	    for (int i = 0; i < floatValues.length; i++) {
		longValues[i] = Float.valueOf(floatValues[i]).intValue();
	    }
	    deviceDataArgin.insert(longValues);
	    break;
	case TangoConst.Tango_DEVVAR_ULONGARRAY:
	    final long[] ulongValues = new long[floatValues.length];
	    for (int i = 0; i < floatValues.length; i++) {
		ulongValues[i] = Float.valueOf(floatValues[i]).longValue();
	    }
	    deviceDataArgin.insert_ul(ulongValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONG64ARRAY:
	    final int[] long64Values = new int[floatValues.length];
	    for (int i = 0; i < floatValues.length; i++) {
		long64Values[i] = Float.valueOf(floatValues[i]).intValue();
	    }
	    deviceDataArgin.insert(long64Values);
	    break;
	case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
	    final long[] ulong64Values = new long[floatValues.length];
	    for (int i = 0; i < floatValues.length; i++) {
		ulong64Values[i] = Float.valueOf(floatValues[i]).longValue();
	    }
	    deviceDataArgin.insert_u64(ulong64Values);
	    break;
	case TangoConst.Tango_DEVVAR_FLOATARRAY:
	    deviceDataArgin.insert(floatValues);
	    break;
	case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
	    final double[] doubleValues = new double[floatValues.length];
	    for (int i = 0; i < floatValues.length; i++) {
		doubleValues[i] = Float.valueOf(floatValues[i]).doubleValue();
	    }
	    deviceDataArgin.insert(doubleValues);
	    break;
	case TangoConst.Tango_DEVVAR_STRINGARRAY:
	    final String[] stringValues = new String[floatValues.length];
	    for (int i = 0; i < floatValues.length; i++) {
		stringValues[i] = Float.valueOf(floatValues[i]).toString();
	    }
	    deviceDataArgin.insert(stringValues);
	    break;

	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "insertFromFloatArray.insertFromLongArray(float[] values,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param booleanValues
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromBooleanArray(final boolean[] booleanValues,
	    final DeviceData deviceDataArgin, final int dataType) throws DevFailed {
	// by default for xdim = 1, send the sum.
	boolean firstBoolean = false;
	if (booleanValues.length > 0) {
	    firstBoolean = booleanValues[0];
	}

	Integer intValue = new Integer(0);
	if (firstBoolean) {
	    intValue = new Integer(1);
	}

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(intValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(intValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromBooleanArray(boolean[] values,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(intValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(intValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(intValue.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert(intValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(intValue.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(intValue.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(intValue.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(intValue.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(intValue.toString());
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    deviceDataArgin.insert(firstBoolean);
	    break;
	case TangoConst.Tango_DEV_STATE:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_STATE not supported",
		    "CommandHelper.insertFromBooleanArray(boolean[] values,deviceDataArgin)");
	    break;

	// Array input type
	case TangoConst.Tango_DEVVAR_SHORTARRAY:
	    final short[] shortValues = new short[booleanValues.length];
	    for (int i = 0; i < booleanValues.length; i++) {
		if (booleanValues[i]) {
		    shortValues[i] = 1;
		} else {
		    shortValues[i] = 0;
		}
	    }
	    deviceDataArgin.insert(shortValues);
	    break;
	case TangoConst.Tango_DEVVAR_USHORTARRAY:
	    final short[] ushortValues = new short[booleanValues.length];
	    for (int i = 0; i < booleanValues.length; i++) {
		if (booleanValues[i]) {
		    ushortValues[i] = 1;
		} else {
		    ushortValues[i] = 0;
		}
	    }
	    deviceDataArgin.insert(ushortValues);
	    break;
	case TangoConst.Tango_DEVVAR_CHARARRAY:
	    final byte[] byteValues = new byte[booleanValues.length];
	    for (int i = 0; i < booleanValues.length; i++) {
		if (booleanValues[i]) {
		    byteValues[i] = Short.valueOf((short) 1).byteValue();
		} else {
		    byteValues[i] = Short.valueOf((short) 0).byteValue();
		}
	    }
	    deviceDataArgin.insert(byteValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONGARRAY:
	    final int[] longValues = new int[booleanValues.length];
	    for (int i = 0; i < booleanValues.length; i++) {
		if (booleanValues[i]) {
		    longValues[i] = 1;
		} else {
		    longValues[i] = 0;
		}
	    }
	    deviceDataArgin.insert(longValues);
	    break;
	case TangoConst.Tango_DEVVAR_ULONGARRAY:
	    final long[] ulongValues = new long[booleanValues.length];
	    for (int i = 0; i < booleanValues.length; i++) {
		if (booleanValues[i]) {
		    ulongValues[i] = 1;
		} else {
		    ulongValues[i] = 0;
		}
	    }
	    deviceDataArgin.insert_ul(ulongValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONG64ARRAY:
	    final int[] long64Values = new int[booleanValues.length];
	    for (int i = 0; i < booleanValues.length; i++) {
		if (booleanValues[i]) {
		    long64Values[i] = 1;
		} else {
		    long64Values[i] = 0;
		}
	    }
	    deviceDataArgin.insert(long64Values);
	    break;
	case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
	    final long[] ulong64Values = new long[booleanValues.length];
	    for (int i = 0; i < booleanValues.length; i++) {
		if (booleanValues[i]) {
		    ulong64Values[i] = 1;
		} else {
		    ulong64Values[i] = 0;
		}
	    }
	    deviceDataArgin.insert_u64(ulong64Values);
	    break;
	case TangoConst.Tango_DEVVAR_FLOATARRAY:
	    final float[] floatValues = new float[booleanValues.length];
	    for (int i = 0; i < booleanValues.length; i++) {
		if (booleanValues[i]) {
		    floatValues[i] = 1;
		} else {
		    floatValues[i] = 0;
		}
	    }
	    deviceDataArgin.insert(floatValues);
	    break;
	case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
	    final double[] doubleValues = new double[booleanValues.length];
	    for (int i = 0; i < booleanValues.length; i++) {
		if (booleanValues[i]) {
		    doubleValues[i] = 1;
		} else {
		    doubleValues[i] = 0;
		}
	    }
	    deviceDataArgin.insert(doubleValues);
	    break;
	case TangoConst.Tango_DEVVAR_STRINGARRAY:
	    final String[] stringValues = new String[booleanValues.length];
	    for (int i = 0; i < booleanValues.length; i++) {
		stringValues[i] = Boolean.valueOf(booleanValues[i]).toString();
	    }
	    deviceDataArgin.insert(stringValues);
	    break;

	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "insertFromBooleanArray.insertFromLongArray(boolean[] values,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param doubleValues
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromDoubleArray(final double[] doubleValues,
	    final DeviceData deviceDataArgin, final int dataType) throws DevFailed {
	// by default for xdim = 1, send the sum.
	Double doubleSum = new Double(0);
	for (final double doubleValue : doubleValues) {
	    doubleSum = doubleSum + doubleValue;
	}

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromDoubleArray(double[] values,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(doubleSum.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(doubleSum.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(doubleSum.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(doubleSum.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(doubleSum.toString());
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (doubleSum.doubleValue() == 1) {
		deviceDataArgin.insert(true);
	    } else {
		deviceDataArgin.insert(false);
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    DevState devStateValue = DevState.UNKNOWN;
	    if (doubleValues.length > 0) {
		try {
		    devStateValue = DevState.from_int(Double.valueOf(doubleValues[0]).intValue());
		} catch (final org.omg.CORBA.BAD_PARAM badParam) {
		    devStateValue = DevState.UNKNOWN;
		}
	    }
	    deviceDataArgin.insert(devStateValue);
	    break;

	// Array input type
	case TangoConst.Tango_DEVVAR_SHORTARRAY:
	    final short[] shortValues = new short[doubleValues.length];
	    for (int i = 0; i < doubleValues.length; i++) {
		shortValues[i] = Double.valueOf(doubleValues[i]).shortValue();
	    }
	    deviceDataArgin.insert(shortValues);
	    break;
	case TangoConst.Tango_DEVVAR_USHORTARRAY:
	    final short[] ushortValues = new short[doubleValues.length];
	    for (int i = 0; i < doubleValues.length; i++) {
		ushortValues[i] = Double.valueOf(doubleValues[i]).shortValue();
	    }
	    deviceDataArgin.insert_us(ushortValues);
	    break;
	case TangoConst.Tango_DEVVAR_CHARARRAY:
	    final byte[] byteValues = new byte[doubleValues.length];
	    for (int i = 0; i < doubleValues.length; i++) {
		byteValues[i] = Double.valueOf(doubleValues[i]).byteValue();
	    }

	    deviceDataArgin.insert(byteValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONGARRAY:
	    final int[] intValues = new int[doubleValues.length];
	    for (int i = 0; i < doubleValues.length; i++) {
		intValues[i] = Double.valueOf(doubleValues[i]).intValue();
	    }
	    deviceDataArgin.insert(intValues);
	    break;
	case TangoConst.Tango_DEVVAR_ULONGARRAY:
	    final long[] ulongValues = new long[doubleValues.length];
	    for (int i = 0; i < doubleValues.length; i++) {
		ulongValues[i] = Double.valueOf(doubleValues[i]).longValue();
	    }
	    deviceDataArgin.insert_ul(ulongValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONG64ARRAY:
	    final int[] long64Values = new int[doubleValues.length];
	    for (int i = 0; i < doubleValues.length; i++) {
		long64Values[i] = Double.valueOf(doubleValues[i]).intValue();
	    }
	    deviceDataArgin.insert(long64Values);
	    break;
	case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
	    final long[] ulong64Values = new long[doubleValues.length];
	    for (int i = 0; i < doubleValues.length; i++) {
		ulong64Values[i] = Double.valueOf(doubleValues[i]).longValue();
	    }
	    deviceDataArgin.insert_u64(ulong64Values);
	    break;
	case TangoConst.Tango_DEVVAR_FLOATARRAY:
	    final float[] floatValues = new float[doubleValues.length];
	    for (int i = 0; i < doubleValues.length; i++) {
		floatValues[i] = Double.valueOf(doubleValues[i]).floatValue();
	    }
	    deviceDataArgin.insert(floatValues);
	    break;
	case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
	    deviceDataArgin.insert(doubleValues);
	    break;
	case TangoConst.Tango_DEVVAR_STRINGARRAY:
	    final String[] stringValues = new String[doubleValues.length];
	    for (int i = 0; i < doubleValues.length; i++) {
		stringValues[i] = Double.valueOf(doubleValues[i]).toString();
	    }
	    deviceDataArgin.insert(stringValues);
	    break;

	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "insertFromFloatArray.insertFromDoubleArray(double[] values,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param stringValue
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromString(final String stringValue, final DeviceData deviceDataArgin,
	    final int dataType) throws DevFailed {

	Double doubleValue = Double.NaN;
	try {
	    doubleValue = Double.valueOf(stringValue);
	} catch (final NumberFormatException e) {
	    doubleValue = Double.NaN;
	}

	if (doubleValue.isNaN() && dataType != TangoConst.Tango_DEV_STRING) {
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "Cannot insert " + stringValue
		    + " as a numerical value.",
		    "CommandHelper.insertFromString(String value,deviceDataArgin)");
	}

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    if (!doubleValue.isNaN()) {
		deviceDataArgin.insert(doubleValue.shortValue());
	    }
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    if (!doubleValue.isNaN()) {
		deviceDataArgin.insert_us(doubleValue.shortValue());
	    }
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromString(String value,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    if (!doubleValue.isNaN()) {
		deviceDataArgin.insert(doubleValue.shortValue());
	    }
	    break;
	case TangoConst.Tango_DEV_LONG:
	    if (!doubleValue.isNaN()) {
		deviceDataArgin.insert(doubleValue.intValue());
	    }
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(doubleValue.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert(doubleValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    if (!doubleValue.isNaN()) {
		deviceDataArgin.insert_u64(doubleValue.longValue());
	    }
	    break;
	case TangoConst.Tango_DEV_INT:
	    if (!doubleValue.isNaN()) {
		deviceDataArgin.insert(doubleValue.intValue());
	    }
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    if (!doubleValue.isNaN()) {
		deviceDataArgin.insert(doubleValue.floatValue());
	    }
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    if (!doubleValue.isNaN()) {
		deviceDataArgin.insert(doubleValue.doubleValue());
	    }
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(stringValue);
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (!doubleValue.isNaN()) {
		if (doubleValue.doubleValue() == 1) {
		    deviceDataArgin.insert(true);
		} else {
		    deviceDataArgin.insert(false);
		}
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    if (!doubleValue.isNaN()) {
		final DevState tmpDevState = StateUtilities.getStateForName(stringValue);
		deviceDataArgin.insert(tmpDevState);
	    }
	    break;
	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "CommandHelper.insertFromString(String value,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param integerValue
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromInteger(final Integer integerValue,
	    final DeviceData deviceDataArgin, final int dataType) throws DevFailed {

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(integerValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(integerValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromInteger(Integer value,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(integerValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(integerValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(integerValue.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert(integerValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(integerValue.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(integerValue.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(integerValue.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(integerValue.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(integerValue.toString());
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (integerValue.doubleValue() == 1) {
		deviceDataArgin.insert(true);
	    } else {
		deviceDataArgin.insert(false);
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    try {
		deviceDataArgin.insert(DevState.from_int(integerValue.intValue()));
	    } catch (final org.omg.CORBA.BAD_PARAM badParam) {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "Cannot sent"
			+ integerValue.intValue() + "for input Tango_DEV_STATE type",
			"CommandHelper.insertFromInteger(Integer value,deviceDataArgin)");
	    }
	    break;
	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "CommandHelper.insertFromInteger(Integer value,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param longValue
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromLong(final Long longValue, final DeviceData deviceDataArgin,
	    final int dataType) throws DevFailed {

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(longValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(longValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromLong(Long value,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(longValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(longValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(longValue.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert(longValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(longValue.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(longValue.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(longValue.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(longValue.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(longValue.toString());
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (longValue.doubleValue() == 1) {
		deviceDataArgin.insert(true);
	    } else {
		deviceDataArgin.insert(false);
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    try {
		deviceDataArgin.insert(DevState.from_int(longValue.intValue()));
	    } catch (final org.omg.CORBA.BAD_PARAM badParam) {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "Cannot sent"
			+ longValue.intValue() + "for input Tango_DEV_STATE type",
			"CommandHelper.insertFromLong(Long value,deviceDataArgin)");
	    }
	    break;
	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "CommandHelper.insertFromLong(Long value,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param floatValue
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromFloat(final Float floatValue, final DeviceData deviceDataArgin,
	    final int dataType) throws DevFailed {

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(floatValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(floatValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromFloat(Float value,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(floatValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(floatValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(floatValue.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert_ul(floatValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(floatValue.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(floatValue.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(floatValue.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(floatValue.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(floatValue.toString());
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (floatValue.doubleValue() == 1) {
		deviceDataArgin.insert(true);
	    } else {
		deviceDataArgin.insert(false);
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    try {
		deviceDataArgin.insert(DevState.from_int(floatValue.intValue()));
	    } catch (final org.omg.CORBA.BAD_PARAM badParam) {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "Cannot sent"
			+ floatValue.intValue() + "for input Tango_DEV_STATE type",
			"CommandHelper.insertFromFloat(Float value,deviceDataArgin)");
	    }
	    break;
	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "CommandHelper.insertFromFloat(Float value,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param byteValues
     *            the values to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromByteArray(final byte[] byteValues,
	    final DeviceData deviceDataArgin, final int dataType) throws DevFailed {

	// by default for xdim = 1, send the sum.
	Double doubleSum = new Double(0);
	for (final byte byteValue : byteValues) {
	    doubleSum = doubleSum + Byte.valueOf(byteValue).doubleValue();
	}

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromByteArray(float[] values,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(doubleSum.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(doubleSum.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(doubleSum.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(doubleSum.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(doubleSum.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(doubleSum.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(doubleSum.toString());
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (doubleSum.doubleValue() == 1) {
		deviceDataArgin.insert(true);
	    } else {
		deviceDataArgin.insert(false);
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    DevState devStateValue = DevState.UNKNOWN;
	    if (byteValues.length > 0) {
		try {
		    devStateValue = DevState.from_int(Byte.valueOf(byteValues[0]).intValue());
		} catch (final org.omg.CORBA.BAD_PARAM badParam) {
		    devStateValue = DevState.UNKNOWN;
		}
	    }
	    deviceDataArgin.insert(devStateValue);
	    break;

	// Array input type
	case TangoConst.Tango_DEVVAR_SHORTARRAY:
	    final short[] shortValues = new short[byteValues.length];
	    for (int i = 0; i < byteValues.length; i++) {
		shortValues[i] = Byte.valueOf(byteValues[i]).shortValue();
	    }
	    deviceDataArgin.insert(shortValues);
	    break;
	case TangoConst.Tango_DEVVAR_USHORTARRAY:
	    final short[] ushortValues = new short[byteValues.length];
	    for (int i = 0; i < byteValues.length; i++) {
		ushortValues[i] = Byte.valueOf(byteValues[i]).shortValue();
	    }
	    deviceDataArgin.insert(ushortValues);
	    break;
	case TangoConst.Tango_DEVVAR_CHARARRAY:
	    deviceDataArgin.insert(byteValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONGARRAY:
	    final int[] longValues = new int[byteValues.length];
	    for (int i = 0; i < byteValues.length; i++) {
		longValues[i] = Byte.valueOf(byteValues[i]).intValue();
	    }
	    deviceDataArgin.insert(longValues);
	    break;
	case TangoConst.Tango_DEVVAR_ULONGARRAY:
	    final long[] ulongValues = new long[byteValues.length];
	    for (int i = 0; i < byteValues.length; i++) {
		ulongValues[i] = Byte.valueOf(byteValues[i]).longValue();
	    }
	    deviceDataArgin.insert_ul(ulongValues);
	    break;
	case TangoConst.Tango_DEVVAR_LONG64ARRAY:
	    final int[] long64Values = new int[byteValues.length];
	    for (int i = 0; i < byteValues.length; i++) {
		long64Values[i] = Byte.valueOf(byteValues[i]).intValue();
	    }
	    deviceDataArgin.insert(long64Values);
	    break;
	case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
	    final long[] ulong64Values = new long[byteValues.length];
	    for (int i = 0; i < byteValues.length; i++) {
		ulong64Values[i] = Byte.valueOf(byteValues[i]).longValue();
	    }
	    deviceDataArgin.insert_u64(ulong64Values);
	    break;
	case TangoConst.Tango_DEVVAR_FLOATARRAY:
	    final float[] floatValues = new float[byteValues.length];
	    for (int i = 0; i < byteValues.length; i++) {
		floatValues[i] = Byte.valueOf(byteValues[i]).longValue();
	    }
	    deviceDataArgin.insert(floatValues);
	    break;
	case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
	    final double[] doubleValues = new double[byteValues.length];
	    for (int i = 0; i < byteValues.length; i++) {
		doubleValues[i] = Byte.valueOf(byteValues[i]).doubleValue();
	    }
	    deviceDataArgin.insert(doubleValues);
	    break;
	case TangoConst.Tango_DEVVAR_STRINGARRAY:
	    final String[] stringValues = new String[byteValues.length];
	    for (int i = 0; i < byteValues.length; i++) {
		stringValues[i] = Byte.valueOf(byteValues[i]).toString();
	    }
	    deviceDataArgin.insert(stringValues);
	    break;

	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "insertFromFloatArray.insertFromLongArray(float[] values,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param booleanValue
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromBoolean(final Boolean booleanValue,
	    final DeviceData deviceDataArgin, final int dataType) throws DevFailed {

	Integer integerValue = 0;
	if (booleanValue.booleanValue()) {
	    integerValue = 1;
	}

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(integerValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(integerValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromBoolean(Boolean value,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(integerValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(integerValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(integerValue.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert_ul(integerValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(integerValue.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(integerValue.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(integerValue.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(integerValue.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(Boolean.toString(booleanValue.booleanValue()));
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    deviceDataArgin.insert(booleanValue.booleanValue());
	    break;
	case TangoConst.Tango_DEV_STATE:
	    deviceDataArgin.insert(DevState.from_int(integerValue.intValue()));
	    break;
	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "CommandHelper.insertFromBoolean(Boolean value,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param doubleValue
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromDouble(final Double doubleValue, final DeviceData deviceDataArgin,
	    final int dataType) throws DevFailed {

	switch (dataType) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(doubleValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(doubleValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromDouble(Double value,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(doubleValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(doubleValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(doubleValue.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert(doubleValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(doubleValue.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(doubleValue.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(doubleValue.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(doubleValue.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(doubleValue.toString());
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (doubleValue.doubleValue() == 1) {
		deviceDataArgin.insert(true);
	    } else {
		deviceDataArgin.insert(false);
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    try {
		deviceDataArgin.insert(DevState.from_int(doubleValue.intValue()));
	    } catch (final org.omg.CORBA.BAD_PARAM badParam) {
		Except.throw_exception("TANGO_WRONG_DATA_ERROR", "Cannot sent"
			+ doubleValue.intValue() + "for input Tango_DEV_STATE type",
			"CommandHelper.insertFromDouble(Double value,deviceDataArgin)");
	    }
	    break;
	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "CommandHelper.insertFromDouble(Double value,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param devStateValue
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromDevState(final DevState devStateValue,
	    final DeviceData deviceDataArgin, final int dataType) throws DevFailed {

	final Integer integerValue = Integer.valueOf(devStateValue.value());
	switch (deviceDataArgin.getType()) {
	case TangoConst.Tango_DEV_SHORT:
	    deviceDataArgin.insert(integerValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_USHORT:
	    deviceDataArgin.insert_us(integerValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_CHAR:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR",
		    "input type Tango_DEV_CHAR not supported",
		    "CommandHelper.insertFromDevState(DevState value,deviceDataArgin)");
	    break;
	case TangoConst.Tango_DEV_UCHAR:
	    deviceDataArgin.insert(integerValue.shortValue());
	    break;
	case TangoConst.Tango_DEV_LONG:
	    deviceDataArgin.insert(integerValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG:
	    deviceDataArgin.insert_ul(integerValue.longValue());
	    break;
	case TangoConst.Tango_DEV_LONG64:
	    deviceDataArgin.insert(integerValue.intValue());
	    break;
	case TangoConst.Tango_DEV_ULONG64:
	    deviceDataArgin.insert_u64(integerValue.longValue());
	    break;
	case TangoConst.Tango_DEV_INT:
	    deviceDataArgin.insert(integerValue.intValue());
	    break;
	case TangoConst.Tango_DEV_FLOAT:
	    deviceDataArgin.insert(integerValue.floatValue());
	    break;
	case TangoConst.Tango_DEV_DOUBLE:
	    deviceDataArgin.insert(integerValue.doubleValue());
	    break;
	case TangoConst.Tango_DEV_STRING:
	    deviceDataArgin.insert(integerValue.toString());
	    break;
	case TangoConst.Tango_DEV_BOOLEAN:
	    if (integerValue.doubleValue() == 1) {
		deviceDataArgin.insert(true);
	    } else {
		deviceDataArgin.insert(false);
	    }
	    break;
	case TangoConst.Tango_DEV_STATE:
	    deviceDataArgin.insert(devStateValue);
	    break;
	default:
	    Except.throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
		    + deviceDataArgin.getType() + " not supported",
		    "CommandHelper.insertFromDevState(DevState value,deviceDataArgin)");
	    break;
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param value
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromDevVarDoubleStringArray(final DevVarDoubleStringArray value,
	    final DeviceData deviceDataArgin, final int dataType) throws DevFailed {
	if (dataType == TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY) {
	    deviceDataArgin.insert(value);
	} else {
	    Except
		    .throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
			    + deviceDataArgin.getType() + " not supported",
			    "CommandHelper.insertFromDevVarDoubleStringArray(DevVarDoubleStringArray value,deviceDataArgin)");
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param value
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromDevVarLongStringArray(final DevVarLongStringArray value,
	    final DeviceData deviceDataArgin, final int dataType) throws DevFailed {
	if (dataType == TangoConst.Tango_DEVVAR_LONGSTRINGARRAY) {
	    deviceDataArgin.insert(value);
	} else {
	    Except
		    .throw_exception("TANGO_WRONG_DATA_ERROR", "input type "
			    + deviceDataArgin.getType() + " not supported",
			    "CommandHelper.insertFromDevVarLongStringArray(DevVarLongStringArray value,deviceDataArgin)");
	}
    }

    /**
     * Convert the value according the type of DeviceData.
     * 
     * @param values
     *            the value to insert on DeviceData
     * @param deviceDataArgin
     *            the DeviceData attribute to write
     * @param dataType
     *            the type of inserted data
     * @throws DevFailed
     */
    public static void insertFromArray(final Object[] values, final DeviceData deviceDataArgin,
	    final int dataType) throws DevFailed {
	if (values.length > 0) {
	    final Object firstValue = values[0];
	    if (firstValue instanceof Short) {
		final short[] tmpValues = new short[values.length];
		for (int i = 0; i < tmpValues.length; i++) {
		    tmpValues[i] = ((Short) values[i]).shortValue();
		}
		CommandHelper.insertFromShortArray(tmpValues, deviceDataArgin, dataType);
	    } else if (firstValue instanceof String) {
		final String[] tmpValues = new String[values.length];
		for (int i = 0; i < tmpValues.length; i++) {
		    tmpValues[i] = (String) values[i];
		}
		CommandHelper.insertFromStringArray(tmpValues, deviceDataArgin, dataType);
	    } else if (firstValue instanceof Integer) {
		final int[] tmpValues = new int[values.length];
		for (int i = 0; i < tmpValues.length; i++) {
		    tmpValues[i] = ((Integer) values[i]).intValue();
		}
		CommandHelper.insertFromIntegerArray(tmpValues, deviceDataArgin, dataType);
	    } else if (firstValue instanceof Long) {
		final long[] tmpValues = new long[values.length];
		for (int i = 0; i < tmpValues.length; i++) {
		    tmpValues[i] = ((Long) values[i]).intValue();
		}
		CommandHelper.insertFromLongArray(tmpValues, deviceDataArgin, dataType);
	    } else if (firstValue instanceof Float) {
		final float[] tmpValues = new float[values.length];
		for (int i = 0; i < tmpValues.length; i++) {
		    tmpValues[i] = ((Float) values[i]).floatValue();
		}
		CommandHelper.insertFromFloatArray(tmpValues, deviceDataArgin, dataType);
	    } else if (firstValue instanceof Byte) {
		final byte[] tmpValues = new byte[values.length];
		for (int i = 0; i < tmpValues.length; i++) {
		    tmpValues[i] = ((Byte) values[i]).byteValue();
		}
		CommandHelper.insertFromByteArray(tmpValues, deviceDataArgin, dataType);
	    } else if (firstValue instanceof Boolean) {
		final boolean[] tmpValues = new boolean[values.length];
		for (int i = 0; i < tmpValues.length; i++) {
		    tmpValues[i] = ((Boolean) values[i]).booleanValue();
		}

		CommandHelper.insertFromBooleanArray(tmpValues, deviceDataArgin, dataType);
	    } else if (firstValue instanceof Double) {
		final double[] tmpValues = new double[values.length];
		for (int i = 0; i < tmpValues.length; i++) {
		    tmpValues[i] = ((Double) values[i]).doubleValue();
		}

		CommandHelper.insertFromDoubleArray(tmpValues, deviceDataArgin, dataType);
	    }
	}
    }

}
