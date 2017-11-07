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
// $Revision: 26328 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.TangoDs.Except;


/**
 * Class Description: This class is an extension of AttributeInfo class.
 * <Br><Br>
 * <Br><b> Usage example: </b> <Br>
 * <ul><i>
 * AttributeInfoEx[]	attributeInfoList = dev.get_attribute_info_ex();	<Br>
 * for (AttributeInfoEx attributeInfo : attributeInfoList) {
 * <ul>
 *      System.out.println(attributeInfo.name +
 *      " generate a change event for an absolute change of " +
 *      attributeInfo.events.ch_event.abs_change);	<Br>
 * </ul>
 * } <Br>
 * </ul></i>
 *
 * @author verdier
 * @version $Revision: 26328 $
 */


public class AttributeInfoEx extends AttributeInfo implements ApiDefs, java.io.Serializable {
    public AttributeAlarmInfo alarms = null;
    public AttributeEventInfo events = null;
    public String[] extensions = null;
    public String[] sys_extensions = null;
    public boolean isMemorized;
    public boolean isSetAtInit;
    public Memorized memorized = Memorized.UNKNOWN;
    public String   root_attr_name = "Not specified";
    public String[] enum_label = null;

    /**
     * Define  possible memorized information
     */
    public enum Memorized {
        /**
         * Not available (IDL too old)
         */
        UNKNOWN,
        /**
         * Not memorized
         */
        NOT_MEMORIZED,
        /**
         * Memorized
         */
        MEMORIZED,
        /**
         * Memorized and set at init
         */
        MEMORIZED_SET_AT_INIT;

        public static String toString(Memorized index) {
            switch (index) {
                case NOT_MEMORIZED:
                    return "Write value is NOT memorized";
                case MEMORIZED:
                    return "Write value is Memorized in database";
                case MEMORIZED_SET_AT_INIT:
                    return "Write value is Memorized and set at init";
                default:
                    return "Not available (IDL too old)";
             }
        }
    }

    //==========================================================================
    //==========================================================================
    public AttributeInfoEx(AttributeConfig_5 attributeConfig5) {
        super(attributeConfig5);
        alarms = new AttributeAlarmInfo(attributeConfig5.att_alarm);
        events = new AttributeEventInfo(attributeConfig5.event_prop);
        extensions = attributeConfig5.extensions;
        sys_extensions = attributeConfig5.sys_extensions;

        //  Memorized attribute management
        isMemorized = attributeConfig5.memorized;
        isSetAtInit = attributeConfig5.mem_init;
        if (isMemorized) {
            if (isSetAtInit)
                this.memorized = Memorized.MEMORIZED_SET_AT_INIT;
            else
                this.memorized = Memorized.MEMORIZED;
        }
        else {
            this.memorized = Memorized.NOT_MEMORIZED;
        }

        root_attr_name = attributeConfig5.root_attr_name;
        enum_label = attributeConfig5.enum_labels;
    }
    //==========================================================================
    //==========================================================================
    public AttributeInfoEx(AttributeConfig_3 attributeConfig3) {
        super(attributeConfig3);
        alarms = new AttributeAlarmInfo(attributeConfig3.att_alarm);
        events = new AttributeEventInfo(attributeConfig3.event_prop);
        extensions = attributeConfig3.extensions;
        sys_extensions = attributeConfig3.sys_extensions;
    }

    //==========================================================================
    //==========================================================================
    public AttributeInfoEx(AttributeConfig_2 attributeConfig2) {
        super(attributeConfig2);
        extensions = new String[0];
        sys_extensions = new String[0];
    }

    //==========================================================================
    //==========================================================================
    public AttributeInfoEx(AttributeConfig attributeConfig) {
        super(attributeConfig);
        extensions = new String[0];
        sys_extensions = new String[0];
    }

    //==========================================================================
    //==========================================================================
    public AttributeConfig_3 get_attribute_config_obj_3() {
        return new AttributeConfig_3(
                name,
                writable,
                data_format,
                data_type,
                max_dim_x,
                max_dim_y,
                description,
                label,
                unit,
                standard_unit,
                display_unit,
                format, min_value,
                max_value,
                writable_attr_name,
                level,
                alarms.getTangoObj(),
                events.getTangoObj(),
                extensions,
                sys_extensions);
    }
    //==========================================================================
    //==========================================================================
    public AttributeConfig_5 get_attribute_config_obj_5() {
        return new AttributeConfig_5(
                name,
                writable,
                data_format,
                data_type,
                isMemorized,
                isSetAtInit,
                max_dim_x,
                max_dim_y,
                description,
                label,
                unit,
                standard_unit,
                display_unit,
                format,
                min_value,
                max_value,
                writable_attr_name,
                level,
                root_attr_name,
                enum_label,
                alarms.getTangoObj(),
                events.getTangoObj(),
                extensions,
                sys_extensions);
    }

    //==========================================================================
    /**
     * Returns the label for specified index from enum
     * @param index short value from enum
     * @return  the label for specified index from enum
     * @throws DevFailed if Labels not defined or index out of bounds.
     */
    //==========================================================================
    public String getEnumLabel(short index) throws DevFailed  {
        if (enum_label==null)
            Except.throw_exception("NoLabels", "Attribute " +
                    name + " has no labels defined");
        String  label = "";
        try {
            label = enum_label[index];
        }
        catch (Exception e) {
            Except.throw_exception(e.toString(), e.toString());
        }
        return label;
    }
}
