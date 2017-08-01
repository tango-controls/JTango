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
// $Revision: 25296 $
//
//-======================================================================


package fr.esrf.TangoDs;

import java.text.DecimalFormat;
import java.util.StringTokenizer;


/**
 * This class is a TANGO core class used to print attribute value following
 * the format defined as one of the attribute property
 * @author $Author: pascal_verdier $
 * @version $Revision: 25296 $
 */

public class AttrManip implements java.io.Serializable {
    static DecimalFormat df = null;
    static boolean pos = false;
    static int prec = 0;
    static boolean point = false;

    /**
     * Format the attribute value (value is an int).
     * <p/>
     * This method returns the input value as a string formatted according to the
     * format parameter. The syntax of this format string is the syntax defined
     * for the attribute format property.
     *
     * @param format The format string (as defined in the attribute property format)
     * @param value  The attribute value
     * @return The input value as a string
     */

    public static String format(String format, int value) {
        prec = 0;
        point = false;
        df = null;

        analyse_format(format);

        StringBuilder out = new StringBuilder();

        if (df != null)
            out.append(df.format(value));
        else
            out.append(value);
        if (pos) {
            if (value > 0)
                out.insert(0, "+");
            pos = false;
        }
        return out.toString();
    }


    /**
     * Format the attribute value (value is a double).
     * <p/>
     * This method returns the input value as a string formatted according to the
     * format parameter. The syntax of this format string is the syntax defined
     * for the attribute format property.
     *
     * @param format The format string (as defined in the attribute property format)
     * @param value  The attribute value
     * @return The input value as a string
     */

    public static String format(String format, double value) {
        prec = 0;
        point = false;
        df = null;

        analyse_format(format);

        StringBuilder out = new StringBuilder();

        if (df != null)
            out.append(df.format(value));
        else
            out.append(value);

        if (pos) {
            if (value > 0)
                out.insert(0, "+");
            pos = false;
        }
        return out.toString();
    }

    /**
     * Format the attribute value (value is a string).
     * <p/>
     * This method does nothing and only returns the input string. It has been
     * written for compatibility with the other attribute data type.
     *
     * @param format The format string (not used in this case)
     * @param value  The attribute value
     * @return The input value
     */
    public static String format(String format, String value) {
        return value;
    }

//+-------------------------------------------------------------------------
//
// method : 		analyse_format
// 
// description : 	This method parse the inout string and for each
//			token inside the input string calls the execute_manip
//			method
//
// argument : in : 	- format : The format property string
//
//--------------------------------------------------------------------------

    private static void analyse_format(String format) {
        StringTokenizer st = new StringTokenizer(format.toLowerCase(), ";\n");

        int token_nb = st.countTokens();
        String[] m_array = new String[token_nb];
        int i = 0;
        while (st.hasMoreTokens()) {
            m_array[i] = st.nextToken();
            i++;
        }

        for (i = 0; i < token_nb; i++)
            execute_manip(m_array[i]);
    }

//+-------------------------------------------------------------------------
//
// method : 		execute_manip
// 
// description : 	This method creates and/or set the DecimalFormat
//			class parameters according to the C++ like manipulator
//			defined for the Tango attribute format property. The
//			supported manipulator are :
//				- showpos
//				- fixed
//				- scientific
//				- setprecision(xx)
//				- showpoint
//
// argument : in : 	- s : The C++ like manipulator
//
//--------------------------------------------------------------------------

    private static void execute_manip(String s) {
        if (s.equals("showpos"))
            pos = true;
        else if (s.equals("fixed")) {
            if (df == null) {
                df = new DecimalFormat("0.######");
                if (prec != 0)
                    df.setMaximumFractionDigits(prec);
                if (!point) {
                    if (prec != 0)
                        df.setMinimumFractionDigits(prec);
                    else
                        df.setMinimumFractionDigits(6);
                }
            }
        } else if (s.equals("scientific")) {
            if (df == null) {
                df = new DecimalFormat("0.######E00");
                if (prec != 0)
                    df.setMaximumFractionDigits(prec);
                if (!point) {
                    if (prec != 0)
                        df.setMinimumFractionDigits(prec);
                    else
                        df.setMinimumFractionDigits(6);
                }
            }
        } else if (s.length() >= 15) {
            if (s.substring(0, 13).equals("setprecision(")) {
                String prec_str = s.substring(13, s.length() - 1);
                int tmp_prec = Integer.parseInt(prec_str);
                if (df != null)
                    df.setMaximumFractionDigits(tmp_prec);
                prec = tmp_prec;
            }
        } else if (s.equals("showpoint")) {
            if (df != null) {
                if (prec != 0)
                    df.setMinimumFractionDigits(prec);
                else
                    df.setMinimumFractionDigits(6);
            } else
                point = true;
        }
    }
}
