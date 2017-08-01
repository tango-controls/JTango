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

/**
 *	This class is used to store all the data needed to build
 *	an attribute value.
 *
 * @author $Author: pascal_verdier $
 * @version $Revision: 25296 $
 */

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevError;

public class AttrData implements TangoConst, java.io.Serializable {
    public boolean[] bool_ptr;
    public short[] sh_ptr;
    public int[] lg_ptr;
    public long[] lg64_ptr;
    public float[] fl_ptr;
    public double[] db_ptr;
    public String[] str_ptr;
    public int data_type;
    public AttrQuality qual;
    public int x;
    public int y;
    public DevError[] err;


    //===============================================================
// For boolean scalar and spectrum
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(boolean[] p) {
        bool_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_BOOLEAN;
    }

    //===============================================================
    //===============================================================
    public AttrData(boolean[] p, AttrQuality q) {
        bool_ptr = p;
        qual = q;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_BOOLEAN;
    }

    //===============================================================
    //===============================================================
    public AttrData(boolean[] p, int nb) {
        bool_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = 0;
        data_type = Tango_DEV_BOOLEAN;
    }

    //===============================================================
    //===============================================================
    public AttrData(boolean[] p, int nb, AttrQuality q) {
        bool_ptr = p;
        qual = q;
        x = nb;
        y = 0;
        data_type = Tango_DEV_BOOLEAN;
    }

//===============================================================
// For boolean image
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(boolean[] p, int nb, int nb2) {
        bool_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_BOOLEAN;
    }

    //===============================================================
    //===============================================================
    public AttrData(boolean[] p, int nb, int nb2, AttrQuality q) {
        bool_ptr = p;
        qual = q;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_BOOLEAN;
    }


    //===============================================================
// For short scalar and spectrum
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(short[] p) {
        sh_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_SHORT;
    }

    //===============================================================
    //===============================================================
    public AttrData(short[] p, AttrQuality q) {
        sh_ptr = p;
        qual = q;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_SHORT;
    }

    //===============================================================
    //===============================================================
    public AttrData(short[] p, int nb) {
        sh_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = 0;
        data_type = Tango_DEV_SHORT;
    }

    //===============================================================
    //===============================================================
    public AttrData(short[] p, int nb, AttrQuality q) {
        sh_ptr = p;
        qual = q;
        x = nb;
        y = 0;
        data_type = Tango_DEV_SHORT;
    }

//===============================================================
// For short image
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(short[] p, int nb, int nb2) {
        sh_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_SHORT;
    }

    //===============================================================
    //===============================================================
    public AttrData(short[] p, int nb, int nb2, AttrQuality q) {
        sh_ptr = p;
        qual = q;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_SHORT;
    }


//===============================================================
// For int scalar and spectrum
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(int[] p) {
        lg_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_LONG;
    }

    //===============================================================
    //===============================================================
    public AttrData(int[] p, AttrQuality q) {
        lg_ptr = p;
        qual = q;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_LONG;
    }

    //===============================================================
    //===============================================================
    public AttrData(int[] p, int nb) {
        lg_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = 0;
        data_type = Tango_DEV_LONG;
    }

    //===============================================================
    //===============================================================
    public AttrData(int[] p, int nb, AttrQuality q) {
        lg_ptr = p;
        qual = q;
        x = nb;
        y = 0;
        data_type = Tango_DEV_LONG;
    }

//===============================================================
// For int image
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(int[] p, int nb, int nb2) {
        lg_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_LONG;
    }

    //===============================================================
    //===============================================================
    public AttrData(int[] p, int nb, int nb2, AttrQuality q) {
        lg_ptr = p;
        qual = q;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_LONG;
    }


//===============================================================
// For long scalar and spectrum
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(long[] p) {
        lg64_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_LONG;
    }

    //===============================================================
    //===============================================================
    public AttrData(long[] p, AttrQuality q) {
        lg64_ptr = p;
        qual = q;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_LONG;
    }

    //===============================================================
    //===============================================================
    public AttrData(long[] p, int nb) {
        lg64_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = 0;
        data_type = Tango_DEV_LONG;
    }

    //===============================================================
    //===============================================================
    public AttrData(long[] p, int nb, AttrQuality q) {
        lg64_ptr = p;
        qual = q;
        x = nb;
        y = 0;
        data_type = Tango_DEV_LONG;
    }

//===============================================================
// For long image
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(long[] p, int nb, int nb2) {
        lg64_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_LONG;
    }

    //===============================================================
    //===============================================================
    public AttrData(long[] p, int nb, int nb2, AttrQuality q) {
        lg64_ptr = p;
        qual = q;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_LONG;
    }


//===============================================================
// For float scalar and spectrum
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(float[] p) {
        fl_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_FLOAT;
    }

    //===============================================================
    //===============================================================
    public AttrData(float[] p, AttrQuality q) {
        fl_ptr = p;
        qual = q;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_FLOAT;
    }

    //===============================================================
    //===============================================================
    public AttrData(float[] p, int nb) {
        fl_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = 0;
        data_type = Tango_DEV_FLOAT;
    }

    //===============================================================
    //===============================================================
    public AttrData(float[] p, int nb, AttrQuality q) {
        fl_ptr = p;
        qual = q;
        x = nb;
        y = 0;
        data_type = Tango_DEV_FLOAT;
    }

//===============================================================
// For float image
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(float[] p, int nb, int nb2) {
        fl_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_FLOAT;
    }

    //===============================================================
    //===============================================================
    public AttrData(float[] p, int nb, int nb2, AttrQuality q) {
        fl_ptr = p;
        qual = q;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_FLOAT;
    }


//===============================================================
// For double scalar and spectrum
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(double[] p) {
        db_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_DOUBLE;
    }

    //===============================================================
    //===============================================================
    public AttrData(double[] p, AttrQuality q) {
        db_ptr = p;
        qual = q;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_DOUBLE;
    }

    //===============================================================
    //===============================================================
    public AttrData(double[] p, int nb) {
        db_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = 0;
        data_type = Tango_DEV_DOUBLE;
    }

    //===============================================================
    //===============================================================
    public AttrData(double[] p, int nb, AttrQuality q) {
        db_ptr = p;
        qual = q;
        x = nb;
        y = 0;
        data_type = Tango_DEV_DOUBLE;
    }

//===============================================================
// For double image
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(double[] p, int nb, int nb2) {
        db_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_DOUBLE;
    }

    //===============================================================
    //===============================================================
    public AttrData(double[] p, int nb, int nb2, AttrQuality q) {
        db_ptr = p;
        qual = q;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_DOUBLE;
    }


//===============================================================
// For String scalar and spectrum
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(String[] p) {
        str_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_STRING;
    }

    //===============================================================
    //===============================================================
    public AttrData(String[] p, AttrQuality q) {
        str_ptr = p;
        qual = q;
        x = p.length;
        y = 0;
        data_type = Tango_DEV_STRING;
    }

    //===============================================================
    //===============================================================
    public AttrData(String[] p, int nb) {
        str_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = 0;
        data_type = Tango_DEV_STRING;
    }

    //===============================================================
    //===============================================================
    public AttrData(String[] p, int nb, AttrQuality q) {
        str_ptr = p;
        qual = q;
        x = nb;
        y = 0;
        data_type = Tango_DEV_STRING;
    }

//===============================================================
// For String image
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(String[] p, int nb, int nb2) {
        str_ptr = p;
        qual = AttrQuality.ATTR_VALID;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_STRING;
    }

    //===============================================================
    //===============================================================
    public AttrData(String[] p, int nb, int nb2, AttrQuality q) {
        str_ptr = p;
        qual = q;
        x = nb;
        y = nb2;
        data_type = Tango_DEV_STRING;
    }


//===============================================================
// For error
//===============================================================
    //===============================================================
    //===============================================================
    public AttrData(DevError[] e) {
        err = e;
    }
}
