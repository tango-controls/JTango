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
// $Revision: 26453 $
//
//-======================================================================


package fr.esrf.TangoDs;


import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;
import fr.esrf.Tango.MultiDevFailed;

import java.util.ArrayList;


/**
 * Class Description:	This class extends fr.esrf.Tango.DevFailed.
 * WARNING :	DO NOT FORGET to remove final on class
 * fr.esrf.Tango.DevFailed.
 *
 * @author verdier
 * @version $Revision: 26453 $
 */


public class NamedDevFailedList extends DevFailed {
    private final ArrayList<NamedDevFailed> err_list = new ArrayList<NamedDevFailed>();

    //==========================================================================
    //==========================================================================
    public NamedDevFailedList(MultiDevFailed corba_ex, String dev_name, String op_name, String reason) {
        int nb_obj_failed = corba_ex.errors.length;

        for (int i=0 ; i<nb_obj_failed ; i++) {
            NamedDevFailed tmp_err =
                    new NamedDevFailed(corba_ex.errors[i].err_list,
                            corba_ex.errors[i].name,
                            corba_ex.errors[i].index_in_call);
            err_list.add(tmp_err);
        }
        //	Build an exception summary (as string) in the DevFailed part
        //	of this exception.
        String desc = "Failed to execute " + op_name + " on device " + dev_name + ", object(s) ";
        for (int i=0 ; i<nb_obj_failed ; i++) {
            desc += corba_ex.errors[i].name;
            if (i != nb_obj_failed - 1)
                desc += ", ";
        }

        errors = new DevError[1];
        errors[0] = new DevError();
        errors[0].severity = ErrSeverity.ERR;
        errors[0].reason = reason;
        errors[0].origin = op_name;
        errors[0].desc = desc;
        System.out.println("NamedDevFailedList\n" + desc);
    }

    //==========================================================================
    //==========================================================================
    public int get_faulty_attr_nb() {
        return err_list.size();
    }

    //==========================================================================
    //==========================================================================
    public NamedDevFailed elementAt(int i) {
        return err_list.get(i);
    }

    //==========================================================================
    //==========================================================================
    @SuppressWarnings("UnusedDeclaration")
    public boolean call_failed() {
        return ((err_list.size() == 0) && (errors.length != 0));
    }
}
