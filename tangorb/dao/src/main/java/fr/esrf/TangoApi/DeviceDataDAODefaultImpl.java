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
// $Revision: 28442 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;

/**
 * Class Description: This class manage data object for Tango device access.
 * <p/>
 * <Br>
 * <Br>
 * <Br>
 * <b> Usage example: </b> <Br>
 * <ul>
 * <i> String status; <Br>
 * DeviceProxy dev = DeviceProxyFactory.get("sys/steppermotor/1"); <Br>
 * try { <Br>
 * <ul>
 * DeviceData data = dev.command_inout("DevStatus"); <Br>
 * status = data.extractString(); <Br>
 * </ul>
 * } <Br>
 * catch (DevFailed e) { <Br>
 * <ul>
 * status = "Unknown status"; <Br>
 * Except.print_exception(e); <Br>
 * </ul>
 * } <Br>
 * </ul></i>
 *
 * @author verdier
 * @version $Revision: 28442 $
 */

public class DeviceDataDAODefaultImpl implements TangoConst, IDeviceDataDAO {

    public DeviceDataDAODefaultImpl() {
    }

    // ===========================================================
    /**
     * Constructor for the TgApi Data Object.
     *
     * @throws DevFailed if TgApi class not instancied.
     */
    // ===========================================================
    public void init(final DeviceData deviceData) throws DevFailed {
        deviceData.setAny(ApiUtil.get_orb().create_any());
    }
    // ===========================================================
    /**
     * Constructor for the TgApi Data Object.
     *
     * @param orb orb connection id.
     * @throws DevFailed if TgApi class not instancied.
     */
    // ===========================================================
    public void init(final DeviceData deviceData, final ORB orb) throws DevFailed {
        deviceData.setAny(orb.create_any());
    }
    // ===========================================================
    /**
     * Constructor for the TgApi Data Object.
     *
     * @param any CORBA Any reference to be used in DeviceData.
     * @throws DevFailed if TgApi class not instancied.
     */
    // ===========================================================
    public void init(final DeviceData deviceData, final Any any) throws DevFailed {
        deviceData.setAny(any);
    }

    // ********** Insert Methods for basic types *********************

    // ===========================================
    /**
     * Insert method for argin is void.
     */
    // ===========================================
    public void insert(final DeviceData deviceData) {
    }
    // ===========================================
    /**
     * Insert method for argin is Any (CORBA).
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final Any any) {
        deviceData.setAny(any);
    }
    // ===========================================
    /**
     * Insert method for argin is boolean.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final boolean argin) {
        DevBooleanHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is short.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final short argin) {
        DevShortHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is long (64 bits)
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final long argin) {
        DevLong64Helper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is int.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final int argin) {
        DevLongHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is float.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final float argin) {
        DevFloatHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is double.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final double argin) {
        DevDoubleHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is String.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final String argin) {
        DevStringHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is DevState.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final DevState argin) {
        DevStateHelper.insert(deviceData.getAny(), argin);
    }

    // ********** Insert Methods for sequence types *********************

    // ===========================================
    /**
     * Insert method for argin is DevVarCharArray.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final byte[] argin) {
        DevVarCharArrayHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is DevVarShortArray.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final short[] argin) {
        DevVarShortArrayHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is DevVarLongArray.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final int[] argin) {
        DevVarLongArrayHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is long array (64 bits).
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final long[] argin) {
        DevVarLong64ArrayHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is DevVarFloatArray.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final float[] argin) {
        DevVarFloatArrayHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is DevVarDoubleArray.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final double[] argin) {
        DevVarDoubleArrayHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is DevVarStringArray.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final String[] argin) {
        DevVarStringArrayHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is DevVarLongStringArray.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final DevVarLongStringArray argin) {
        DevVarLongStringArrayHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is DevVarDoubleStringArray.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert(final DeviceData deviceData, final DevVarDoubleStringArray argin) {
        DevVarDoubleStringArrayHelper.insert(deviceData.getAny(), argin);
    }

    // ********** Insert Methods for unsigned types *********************

    // ===========================================
    /**
     * Insert method for argin is unsigned long 64.array
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert_u64(final DeviceData deviceData, final long[] argin) {
        DevVarULong64ArrayHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is unsigned long 64.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert_u64(final DeviceData deviceData, final long argin) {
        DevULong64Helper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin int as unsigned char.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert_uc(final DeviceData deviceData, final short argin) {
        final byte val = (byte) (argin & 0xFF);
        DevUCharHelper.insert(deviceData.getAny(), val);
    }
    // ===========================================
    /**
     * Insert method for argin is unsigned char.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert_uc(final DeviceData deviceData, final byte argin) {
        DevUCharHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin int as unsigned short.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert_us(final DeviceData deviceData, final int argin) {
        final short val = (short) (argin & 0xFFFF);
        DevUShortHelper.insert(deviceData.getAny(), val);
    }
    // ===========================================
    /**
     * Insert method for argin is unsigned short.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert_us(final DeviceData deviceData, final short argin) {
        DevUShortHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is unsigned short.
     *
     * @param argin argin value for next command.
     * @deprecated use insert_us(short/int argin)
     */
    // ===========================================
    @Deprecated
    public void insert_u(final DeviceData deviceData, final short argin) {
        DevUShortHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin long ass unsigned int.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert_ul(final DeviceData deviceData, final long argin) {
        final int val = (int) (argin & 0xFFFFFFFF);
        DevULongHelper.insert(deviceData.getAny(), val);
    }
    // ===========================================
    /**
     * Insert method for argin is unsigned int.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert_ul(final DeviceData deviceData, final int argin) {
        DevULongHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is unsigned int.
     *
     * @param argin argin value for next command.
     * @deprecated use insert_ul(int/long argin)
     */
    // ===========================================
    @Deprecated
    public void insert_u(final DeviceData deviceData, final int argin) {
        DevULongHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin int as unsigned short array.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert_us(final DeviceData deviceData, final int[] argin) {
        final short[] val = new short[argin.length];
        for (int i = 0 ; i<argin.length ; i++) {
            val[i] = (short) (argin[i] & 0xFFFF);
        }

        DevVarUShortArrayHelper.insert(deviceData.getAny(), val);
    }
    // ===========================================
    /**
     * Insert method for argin is unsigned short array.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert_us(final DeviceData deviceData, final short[] argin) {
        DevVarUShortArrayHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is unsigned short array.
     *
     * @param argin argin value for next command.
     * @deprecated use insert_us(short[]/int[] argin)
     */
    // ===========================================
    @Deprecated
    public void insert_u(final DeviceData deviceData, final short[] argin) {
        DevVarUShortArrayHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin long array as unsigned int array.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert_ul(final DeviceData deviceData, final long[] argin) {
        final int[] val = new int[argin.length];
        for (int i = 0 ; i<argin.length ; i++) {
            val[i] = (int) (argin[i] & 0xFFFFFFFF);
        }

        DevVarULongArrayHelper.insert(deviceData.getAny(), val);
    }
    // ===========================================
    /**
     * Insert method for argin is unsigned int array.
     *
     * @param argin argin value for next command.
     */
    // ===========================================
    public void insert_ul(final DeviceData deviceData, final int[] argin) {
        DevVarULongArrayHelper.insert(deviceData.getAny(), argin);
    }
    // ===========================================
    /**
     * Insert method for argin is unsigned int array.
     *
     * @param argin argin value for next command.
     * @deprecated use insert_ul(int[]/long[] argin)
     */
    // ===========================================
    @Deprecated
    public void insert_u(final DeviceData deviceData, final int[] argin) {
        DevVarULongArrayHelper.insert(deviceData.getAny(), argin);
    }

    // ********** Extract Methods for basic types *********************

    // ===========================================
    /**
     * extract method for a CORBA Any.
     */
    // ===========================================
    public Any extractAny(final DeviceData deviceData) {
        return deviceData.getAny();
    }

    // ===========================================
    /**
     * extract method for a boolean.
     */
    // ===========================================
    public boolean extractBoolean(final DeviceData deviceData) {
        return DevBooleanHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a short.
     */
    // ===========================================
    public short extractShort(final DeviceData deviceData) {
        return DevShortHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for an unsigned char.
     */
    // ===========================================
    public short extractUChar(final DeviceData deviceData) {
        final byte tmp = DevUCharHelper.extract(deviceData.getAny());
        return (short) (0xFF & tmp);
    }

    // ===========================================
    /**
     * extract method for an unsigned short.
     */
    // ===========================================
    public int extractUShort(final DeviceData deviceData) {
        final short tmp = DevUShortHelper.extract(deviceData.getAny());
        return  0xFFFF & tmp;
    }

    // ===========================================
    /**
     * extract method for a long.
     */
    // ===========================================
    public int extractLong(final DeviceData deviceData) {
        return DevLongHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a long.
     */
    // ===========================================
    public long extractLong64(final DeviceData deviceData) {
        return DevLong64Helper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for an unsigned long.
     */
    // ===========================================
    public long extractULong(final DeviceData deviceData) {
        final int tmp = DevULongHelper.extract(deviceData.getAny());
        long mask = 0x7fffffff;
        mask += (long) 1 << 31;
        return tmp & mask;
    }

    // ===========================================
    /**
     * extract method for an unsigned long.
     */
    // ===========================================
    public long extractULong64(final DeviceData deviceData) {
        return DevULong64Helper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a float.
     */
    // ===========================================
    public float extractFloat(final DeviceData deviceData) {
        return DevFloatHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a double.
     */
    // ===========================================
    public double extractDouble(final DeviceData deviceData) {
        return DevDoubleHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a String.
     */
    // ===========================================
    public String extractString(final DeviceData deviceData) {
        return DevStringHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a DevState.
     */
    // ===========================================
    public DevState extractDevState(final DeviceData deviceData) {
        return DevStateHelper.extract(deviceData.getAny());
    }

    // ********** Extract Methods for sequence types *********************

    // ===========================================
    /**
     * extract method for a byte Array.
     */
    // ===========================================
    public byte[] extractByteArray(final DeviceData deviceData) {
        return DevVarCharArrayHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a boolean Array.
     */
    // ===========================================
    public boolean[] extractBooleanArray(final DeviceData deviceData) {
        return DevVarBooleanArrayHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a short Array.
     */
    // ===========================================
    public short[] extractShortArray(final DeviceData deviceData) {
        return DevVarShortArrayHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for an unsigned short Array.
     *
     * @return extract value as int array
     */
    // ===========================================
    public int[] extractUShortArray(final DeviceData deviceData) {
        final short[] argout = DevVarUShortArrayHelper.extract(deviceData.getAny());
        final int[] val = new int[argout.length];
        for (int i = 0 ; i<argout.length ; i++) {
            val[i] = 0xFFFF & argout[i];
        }
        return val;
    }

    // ===========================================
    /**
     * extract method for a long Array.
     */
    // ===========================================
    public int[] extractLongArray(final DeviceData deviceData) {
        return DevVarLongArrayHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a long64 Array.
     */
    // ===========================================
    public long[] extractLong64Array(final DeviceData deviceData) {
        return DevVarLong64ArrayHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for an unsigned long Array.
     *
     * @return extract value as long array
     */
    // ===========================================
    public long[] extractULongArray(final DeviceData deviceData) {
        final int[] argout = DevVarULongArrayHelper.extract(deviceData.getAny());
        final long[] val = new long[argout.length];
        long mask = 0x7fffffff;
        mask += (long) 1 << 31;

        for (int i = 0 ; i<argout.length ; i++) {
            val[i] = argout[i] & mask;
        }

        return val;
    }

    // ===========================================
    /**
     * extract method for a long64 Array.
     */
    // ===========================================
    public long[] extractULong64Array(final DeviceData deviceData) {
        return DevVarULong64ArrayHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a float Array.
     */
    // ===========================================
    public float[] extractFloatArray(final DeviceData deviceData) {
        return DevVarFloatArrayHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a double Array.
     */
    // ===========================================
    public double[] extractDoubleArray(final DeviceData deviceData) {
        return DevVarDoubleArrayHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a String Array.
     */
    // ===========================================
    public String[] extractStringArray(final DeviceData deviceData) {
        if (deviceData.getAny()==null) {
            System.out.println("any = null !!");
        }
        return DevVarStringArrayHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a DevVarLongStringArray.
     */
    // ===========================================
    public DevVarLongStringArray extractLongStringArray(final DeviceData deviceData) {
        if (deviceData.getAny()==null) {
            System.out.println("any = null !!");
        }
        return DevVarLongStringArrayHelper.extract(deviceData.getAny());
    }

    // ===========================================
    /**
     * extract method for a DevVarDoubleStringArray.
     */
    // ===========================================
    public DevVarDoubleStringArray extractDoubleStringArray(final DeviceData deviceData) {
        return DevVarDoubleStringArrayHelper.extract(deviceData.getAny());
    }

    public TypeCode type(final DeviceData deviceData) {
        return deviceData.getAny().type();
    }

    // ===========================================
    // ===========================================
    public int getType(final DeviceData deviceData) throws DevFailed {
        int type = Tango_DEV_VOID;
        boolean is_array = false;
        boolean is_struct = false;
        TypeCode tc = deviceData.getAny().type();
        TCKind kind;
        try {

            // Special case for state
            if (tc.kind().value()==TCKind._tk_enum) {
                return Tango_DEV_STATE;
            }

            // Check if struct
            if (tc.kind().value()==TCKind._tk_struct) {
                is_struct = true;
                // Get first element of struct
                tc = tc.member_type(0);
            }

            // Check if array
            if (tc.kind().value()==TCKind._tk_alias) {
                final TypeCode tc_alias = tc.content_type();
                if (tc_alias.kind().value()==TCKind._tk_sequence) {
                    final TypeCode tc_seq = tc_alias.content_type();
                    kind = tc_seq.kind();
                    is_array = true;
                } else {
                    kind = tc_alias.kind();
                }
            } else {
                kind = tc.kind();
            }

            // Set Tango type from TypeCode
            switch (kind.value()) {
                case TCKind._tk_void:
                    type = Tango_DEV_VOID;
                    break;

                case TCKind._tk_boolean:
                    if (is_array) {
                        type = Tango_DEVVAR_BOOLEANARRAY;
                    }
                    else {
                        type = Tango_DEV_BOOLEAN;
                    }
                    break;

                case TCKind._tk_char:
                case TCKind._tk_octet:
                    if (is_array) {
                        type = Tango_DEVVAR_CHARARRAY;
                    } else {
                        type = Tango_DEV_CHAR;
                    }
                    break;

                case TCKind._tk_short:
                    if (is_array) {
                        type = Tango_DEVVAR_SHORTARRAY;
                    } else {
                        type = Tango_DEV_SHORT;
                    }
                    break;

                case TCKind._tk_ushort:
                    if (is_array) {
                        type = Tango_DEVVAR_USHORTARRAY;
                    } else {
                        type = Tango_DEV_USHORT;
                    }
                    break;

                case TCKind._tk_long:
                    if (is_struct) {
                        type = Tango_DEVVAR_LONGSTRINGARRAY;
                    } else if (is_array) {
                        type = Tango_DEVVAR_LONGARRAY;
                    } else {
                        type = Tango_DEV_LONG;
                    }
                    break;

                case TCKind._tk_ulong:
                    if (is_array) {
                        type = Tango_DEVVAR_ULONGARRAY;
                    } else {
                        type = Tango_DEV_ULONG;
                    }
                    break;

                case TCKind._tk_longlong:
                    if (is_array) {
                        type = Tango_DEVVAR_LONG64ARRAY;
                    } else {
                        type = Tango_DEV_LONG64;
                    }
                    break;

                case TCKind._tk_ulonglong:
                    if (is_array) {
                        type = Tango_DEVVAR_ULONG64ARRAY;
                    } else {
                        type = Tango_DEV_ULONG64;
                    }
                    break;

                case TCKind._tk_float:
                    if (is_array) {
                        type = Tango_DEVVAR_FLOATARRAY;
                    } else {
                        type = Tango_DEV_FLOAT;
                    }
                    break;

                case TCKind._tk_double:
                    if (is_struct) {
                        type = Tango_DEVVAR_DOUBLESTRINGARRAY;
                    } else if (is_array) {
                        type = Tango_DEVVAR_DOUBLEARRAY;
                    } else {
                        type = Tango_DEV_DOUBLE;
                    }
                    break;

                case TCKind._tk_string:
                    if (is_array) {
                        type = Tango_DEVVAR_STRINGARRAY;
                    } else {
                        type = Tango_DEV_STRING;
                    }
                    break;
            }
        } catch (final org.omg.CORBA.TypeCodePackage.BadKind e) {
            // e.printStackTrace();
            Except.throw_exception("Api_TypeCodePackage.BadKind", "Bad or unknown type ",
                    "DeviceAttribute.getType()");
        } catch (final org.omg.CORBA.TypeCodePackage.Bounds e) {
            Except.throw_exception("Api_TypeCodePackage.BadKind", "Bad or unknown type ",
                    "DeviceAttribute.getType()");
        }
        return type;
    }
}
