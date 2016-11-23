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
// $Revision: 25723 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;

/**
 * Class Description:
 * This class implements utility methods to convert objects
 *
 * @author verdier
 * @version $Revision: 25723 $
 */


class ConversionUtil {

    //==========================================================================
    /**
     * Convert a DevCmdHistory_4 object (since Device_4Impl) to
     * a DeviceDataHistory array.
     *
     * @param commandName Command name.
     * @param cmdHistory4 Command history object read from a Device_4Impl.
     * @return DeviceDataHistory array.
     */
    //==========================================================================
    static DeviceDataHistory[] commandHistoryToDeviceDataHistoryArray(String commandName,
                                                              DevCmdHistory_4 cmdHistory4) throws DevFailed {
        // Check received data validity
        if (cmdHistory4.dims.length!=cmdHistory4.dims_array.length ||
                cmdHistory4.errors.length!=cmdHistory4.errors_array.length) {
            Except.throw_exception("API_WrongHistoryDataBuffer",
                    "Data buffer received from server is not valid !");
        }

        // Get history depth
        int nb_records = cmdHistory4.dates.length;

        // Copy date and name in each History list element
        DeviceDataHistory[] ddh = new DeviceDataHistory[nb_records];

        for (int i = 0 ; i<nb_records ; i++)
            ddh[i] = new DeviceDataHistory(commandName, TangoConst.COMMAND, cmdHistory4.dates[i]);

        // Copy read dimension
        for (int i = 0 ; i<cmdHistory4.dims.length ; i++) {
            int nb_elt = cmdHistory4.dims_array[i].nb_elt;
            int start = cmdHistory4.dims_array[i].start;
            for (int k = 0 ; k<nb_elt ; k++) {
                ddh[start - k].setDimX(cmdHistory4.dims[i].dim_x);
                ddh[start - k].setDimY(cmdHistory4.dims[i].dim_y);
            }
        }

        // Copy errors
        for (int i = 0 ; i<cmdHistory4.errors.length ; i++) {
            int nb_elt = cmdHistory4.errors_array[i].nb_elt;
            int start = cmdHistory4.errors_array[i].start;
            for (int k = 0 ; k<nb_elt ; k++)
                ddh[start - k].setErrStack(cmdHistory4.errors[i]);
        }

        // Get data type and data ptr
        insertValues(ddh, cmdHistory4.value, nb_records, cmdHistory4.cmd_type);

        return ddh;
    }
    //==========================================================================
    /**
     * Convert a DevAttrHistory_4 object (since Device_4Impl) to
     * a DeviceDataHistory array.
     *
     * @param attrHistory4 Attribute history object read from a Device_4Impl.
     * @return DeviceDataHistory array.
     */
    //==========================================================================
    static DeviceDataHistory[] attributeHistoryToDeviceDataHistoryArray(DevAttrHistory_4 attrHistory4)
            throws DevFailed {
        // Check received data validity
        if (attrHistory4.quals.length!=attrHistory4.quals_array.length ||
                attrHistory4.r_dims.length!=attrHistory4.r_dims_array.length ||
                attrHistory4.w_dims.length!=attrHistory4.w_dims_array.length ||
                attrHistory4.errors.length!=attrHistory4.errors_array.length) {
            Except.throw_exception("API_WrongHistoryDataBuffer",
                    "Data buffer received from server is not valid !",
                    "DeviceProxy::from_hist4_2_AttHistory");
        }

        // Get history depth
        int nb_records = attrHistory4.dates.length;

        // Copy date and name in each History list element
        String name = attrHistory4.name;
        DeviceDataHistory[] ddh = new DeviceDataHistory[nb_records];

        for (int i = 0 ; i<nb_records ; i++)
            ddh[i] = new DeviceDataHistory(name, TangoConst.ATTRIBUTE, attrHistory4.dates[i]);

        // Copy the attribute quality factor
        for (int i = 0 ; i<attrHistory4.quals.length ; i++) {
            int nb_elt = attrHistory4.quals_array[i].nb_elt;
            int start = attrHistory4.quals_array[i].start;
            for (int k = 0 ; k<nb_elt ; k++)
                ddh[start - k].setAttrQuality(attrHistory4.quals[i]);
        }

        // Copy read dimension
        for (int i = 0 ; i<attrHistory4.r_dims.length ; i++) {
            int nb_elt = attrHistory4.r_dims_array[i].nb_elt;
            int start = attrHistory4.r_dims_array[i].start;
            for (int k = 0 ; k<nb_elt ; k++) {
                ddh[start - k].setDimX(attrHistory4.r_dims[i].dim_x);
                ddh[start - k].setDimY(attrHistory4.r_dims[i].dim_y);
            }
        }

        // Copy write dimension
        for (int i = 0 ; i<attrHistory4.w_dims.length ; i++) {
            int nb_elt = attrHistory4.w_dims_array[i].nb_elt;
            int start = attrHistory4.w_dims_array[i].start;

            for (int k = 0 ; k<nb_elt ; k++) {
                ddh[start - k].setWrittenDimX(attrHistory4.w_dims[i].dim_x);
                ddh[start - k].setWrittenDimY(attrHistory4.w_dims[i].dim_y);
            }
        }

        // Copy errors
        for (int i = 0 ; i<attrHistory4.errors.length ; i++) {
            int nb_elt = attrHistory4.errors_array[i].nb_elt;
            int start = attrHistory4.errors_array[i].start;
            for (int k = 0 ; k<nb_elt ; k++)
                ddh[start - k].setErrStack(attrHistory4.errors[i]);
        }

        // Get data type and data ptr
        insertValues(ddh, attrHistory4.value, nb_records, getType(attrHistory4.value));
        return ddh;
    }

    //==========================================================================
    /**
     * Convert a DevAttrHistory_5 object (since Device_5Impl) to
     * a DeviceDataHistory array.
     *
     * @param attrHistory5 Attribute history object read from a Device_5Impl.
     * @return DeviceDataHistory array.
     */
    //==========================================================================
    static DeviceDataHistory[] attributeHistoryToDeviceDataHistoryArray(DevAttrHistory_5 attrHistory5)
            throws DevFailed {
        // Check received data validity
        if (attrHistory5.quals.length!=attrHistory5.quals_array.length ||
                attrHistory5.r_dims.length!=attrHistory5.r_dims_array.length ||
                attrHistory5.w_dims.length!=attrHistory5.w_dims_array.length ||
                attrHistory5.errors.length!=attrHistory5.errors_array.length) {
            Except.throw_exception("API_WrongHistoryDataBuffer",
                    "Data buffer received from server is not valid !",
                    "DeviceProxy::from_hist4_2_AttHistory");
        }

        // Get history depth
        int nb_records = attrHistory5.dates.length;

        // Copy date and name in each History list element
        String name = attrHistory5.name;
        DeviceDataHistory[] dataHistories = new DeviceDataHistory[nb_records];

        for (int i = 0 ; i<nb_records ; i++) {
            dataHistories[i] = new DeviceDataHistory(name,
                    TangoConst.ATTRIBUTE, attrHistory5.dates[i]);
            dataHistories[i].dataFormat = attrHistory5.data_format;
            dataHistories[i].dataType = attrHistory5.data_type;
        }
        // Copy the attribute quality factor
        for (int i = 0 ; i<attrHistory5.quals.length ; i++) {
            int nb_elt = attrHistory5.quals_array[i].nb_elt;
            int start = attrHistory5.quals_array[i].start;
            for (int k = 0 ; k<nb_elt ; k++)
                dataHistories[start - k].setAttrQuality(attrHistory5.quals[i]);
        }

        // Copy read dimension
        for (int i = 0 ; i<attrHistory5.r_dims.length ; i++) {
            int nb_elt = attrHistory5.r_dims_array[i].nb_elt;
            int start = attrHistory5.r_dims_array[i].start;
            for (int k = 0 ; k<nb_elt ; k++) {
                dataHistories[start - k].setDimX(attrHistory5.r_dims[i].dim_x);
                dataHistories[start - k].setDimY(attrHistory5.r_dims[i].dim_y);
            }
        }

        // Copy write dimension
        for (int i = 0 ; i<attrHistory5.w_dims.length ; i++) {
            int nb_elt = attrHistory5.w_dims_array[i].nb_elt;
            int start = attrHistory5.w_dims_array[i].start;

            for (int k = 0 ; k<nb_elt ; k++) {
                dataHistories[start - k].setWrittenDimX(attrHistory5.w_dims[i].dim_x);
                dataHistories[start - k].setWrittenDimY(attrHistory5.w_dims[i].dim_y);
            }
        }

        // Copy errors
        for (int i = 0 ; i<attrHistory5.errors.length ; i++) {
            int nb_elt = attrHistory5.errors_array[i].nb_elt;
            int start = attrHistory5.errors_array[i].start;
            for (int k = 0 ; k<nb_elt ; k++)
                dataHistories[start - k].setErrStack(attrHistory5.errors[i]);
        }


        // Get data type and data ptr
        insertValues(dataHistories, attrHistory5.value, nb_records, attrHistory5.data_type);
        return dataHistories;
    }

    //==========================================================================
    //==========================================================================
    static private void insertValues(DeviceDataHistory[] dataHistories, org.omg.CORBA.Any value, int nb_records, int type)
            throws DevFailed {
        switch (type) {
            case TangoConst.Tango_DEV_BOOLEAN: {
                boolean[] values =
                        DevVarBooleanArrayHelper.extract(value);
                int base = values.length;
                for (int i = 0 ; i<nb_records ; i++)
                    base = dataHistories[i].insert(values, base);
            }
            break;
            case TangoConst.Tango_DEV_UCHAR: {
                byte[] argout =
                        DevVarCharArrayHelper.extract(value);
                short[] values = new short[argout.length];
                short mask = 0xFF;
                for (int i = 0 ; i<argout.length ; i++)
                    values[i] = (short) (mask & argout[i]);
                int base = values.length;
                for (int i = 0 ; i<nb_records ; i++)
                    base = dataHistories[i].insert(values, base);
            }
            break;
            case TangoConst.Tango_DEV_SHORT:
            case TangoConst.Tango_DEVVAR_SHORTARRAY: {
                short[] values =
                        DevVarShortArrayHelper.extract(value);
                int base = values.length;
                for (int i = 0 ; i<nb_records ; i++)
                    base = dataHistories[i].insert(values, base);
            }
            break;
            case TangoConst.Tango_DEV_USHORT:
            case TangoConst.Tango_DEVVAR_USHORTARRAY: {
                short[] values =
                        DevVarUShortArrayHelper.extract(value);
                int base = values.length;
                for (int i = 0 ; i<nb_records ; i++)
                    base = dataHistories[i].insert(values, base);
            }
            break;
            case TangoConst.Tango_DEV_LONG:
            case TangoConst.Tango_DEVVAR_LONGARRAY: {
                int[] values =
                        DevVarLongArrayHelper.extract(value);
                int base = values.length;
                for (int i = 0 ; i<nb_records ; i++)
                    base = dataHistories[i].insert(values, base);
            }
            break;
            case TangoConst.Tango_DEV_ULONG:
            case TangoConst.Tango_DEVVAR_ULONGARRAY: {
                int[] values =
                        DevVarULongArrayHelper.extract(value);
                int base = values.length;
                for (int i = 0 ; i<nb_records ; i++)
                    base = dataHistories[i].insert(values, base);
            }
            break;
            case TangoConst.Tango_DEV_LONG64:
            case TangoConst.Tango_DEVVAR_LONG64ARRAY: {
                long[] values =
                        DevVarLong64ArrayHelper.extract(value);
                int base = values.length;
                for (int i = 0 ; i<nb_records ; i++)
                    base = dataHistories[i].insert(values, base);
            }
            break;
            case TangoConst.Tango_DEV_ULONG64:
            case TangoConst.Tango_DEVVAR_ULONG64ARRAY: {
                long[] values =
                        DevVarULong64ArrayHelper.extract(value);
                int base = values.length;
                for (int i = 0 ; i<nb_records ; i++)
                    base = dataHistories[i].insert(values, base);
            }
            break;
            case TangoConst.Tango_DEV_FLOAT:
            case TangoConst.Tango_DEVVAR_FLOATARRAY: {
                float[] values =
                        DevVarFloatArrayHelper.extract(value);
                int base = values.length;
                for (int i = 0 ; i<nb_records ; i++)
                    base = dataHistories[i].insert(values, base);
            }
            break;
            case TangoConst.Tango_DEV_DOUBLE:
            case TangoConst.Tango_DEVVAR_DOUBLEARRAY: {
                double[] values =
                        DevVarDoubleArrayHelper.extract(value);
                int base = values.length;
                for (int i = 0 ; i<nb_records ; i++)
                    base = dataHistories[i].insert(values, base);
            }
            break;
            case TangoConst.Tango_DEV_STRING:
            case TangoConst.Tango_DEVVAR_STRINGARRAY: {
                String[] values =
                        DevVarStringArrayHelper.extract(value);
                int base = values.length;
                for (int i = 0 ; i<nb_records ; i++)
                    base = dataHistories[i].insert(values, base);
            }
            break;
            case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY: {
                DevVarLongStringArray values =
                        DevVarLongStringArrayHelper.extract(value);

                int s_base = values.svalue.length;
                int l_base = values.lvalue.length;
                int[] bases = {s_base, l_base};
                for (int i = 0 ; i<nb_records ; i++)
                    bases = dataHistories[i].insert(values, bases);
            }
            break;
            case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY: {
                DevVarDoubleStringArray values =
                        DevVarDoubleStringArrayHelper.extract(value);

                int s_base = values.svalue.length;
                int d_base = values.dvalue.length;
                int[] bases = {s_base, d_base};
                for (int i = 0 ; i<nb_records ; i++)
                    bases = dataHistories[i].insert(values, bases);
            }
            break;
            case TangoConst.Tango_DEV_STATE: {
                DevState[] values;
                if (isArray(value))
                    values = DevVarStateArrayHelper.extract(value);
                else
                    values = new DevState[]{DevStateHelper.extract(value)};
                int base = values.length;
                for (int i = 0 ; i<nb_records ; i++)
                    base = dataHistories[i].insert(values, base);
            }
            break;
            case TangoConst.Tango_DEV_ENCODED: {
                DevEncoded[] values =
                        DevVarEncodedArrayHelper.extract(value);
                int base = values.length;
                for (int i = 0 ; i<nb_records ; i++)
                    base = dataHistories[i].insert(values, base);
            }
            break;
            default:
                Except.throw_wrong_data_exception("Api_TypeCodePackage.BadKind",
                        "Bad or unknown type (" + type + ")",
                        "ConversionUtil.insertValue()");
                break;
        }
    }
    //===========================================
    /**
     * return true if any is an array
     */
    //===========================================
    private static boolean isArray(org.omg.CORBA.Any any) {
        boolean retval = true;

        try {
            TypeCode tc = any.type();
            TypeCode tc_alias = tc.content_type();
            //TypeCode        tc_seq   =
            tc_alias.content_type();
        } catch (org.omg.CORBA.TypeCodePackage.BadKind e) {
            //System.out.println(e);
            retval = false;
        }
        return retval;
    }
    //==========================================================================
    /**
     * Returns attribute Tango type.
     */
    //==========================================================================
    static public int getType(org.omg.CORBA.Any any) throws DevFailed {
        int type = -1;
        try {
            TypeCode tc = any.type();

            //	Check if sequence is empty
            if (tc.kind().value()==TCKind._tk_null)
                return TangoConst.Tango_DEV_VOID;

            //	Special case for state
            if (tc.kind().value()==TCKind._tk_enum)
                return TangoConst.Tango_DEV_STATE;
            TypeCode tc_alias = tc.content_type();
            TypeCode tc_seq = tc_alias.content_type();
            TCKind kind = tc_seq.kind();
            switch (kind.value()) {
                case TCKind._tk_void:
                    type = TangoConst.Tango_DEV_VOID;
                    break;
                case TCKind._tk_boolean:
                    type = TangoConst.Tango_DEV_BOOLEAN;
                    break;
                case TCKind._tk_char:
                    type = TangoConst.Tango_DEV_CHAR;
                    break;
                case TCKind._tk_octet:
                    type = TangoConst.Tango_DEV_UCHAR;
                    break;
                case TCKind._tk_short:
                    type = TangoConst.Tango_DEV_SHORT;
                    break;
                case TCKind._tk_ushort:
                    type = TangoConst.Tango_DEV_USHORT;
                    break;
                case TCKind._tk_long:
                    type = TangoConst.Tango_DEV_LONG;
                    break;
                case TCKind._tk_ulong:
                    type = TangoConst.Tango_DEV_ULONG;
                    break;
                case TCKind._tk_longlong:
                    type = TangoConst.Tango_DEV_LONG64;
                    break;
                case TCKind._tk_ulonglong:
                    type = TangoConst.Tango_DEV_ULONG64;
                    break;
                case TCKind._tk_float:
                    type = TangoConst.Tango_DEV_FLOAT;
                    break;
                case TCKind._tk_double:
                    type = TangoConst.Tango_DEV_DOUBLE;
                    break;
                case TCKind._tk_string:
                    type = TangoConst.Tango_DEV_STRING;
                    break;
                case TCKind._tk_enum:
                    type = TangoConst.Tango_DEV_STATE;
                    break;
                case TCKind._tk_struct:
                    type = TangoConst.Tango_DEV_ENCODED;
                    break;
            }
        } catch (org.omg.CORBA.TypeCodePackage.BadKind e) {
            e.printStackTrace();
            Except.throw_exception("Api_TypeCodePackage.BadKind",
                    "Bad or unknown type ",
                    "ConversionUtil.getType()");
        }
        return type;
    }
}
