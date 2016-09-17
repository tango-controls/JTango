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
package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevPipeBlob;
import fr.esrf.Tango.DevPipeDataElt;

import java.util.ArrayList;

/**
 * This class is a list of PipeDataElement objects
 */

public class PipeBlob extends ArrayList<PipeDataElement> {
    private String name;
    // ===================================================================
    // ===================================================================
    public PipeBlob(String name) {
        this.name = name;
    }
    // ===================================================================
    // ===================================================================
    public PipeBlob(DevPipeBlob blob) {
        name = blob.name;
        for (DevPipeDataElt element : blob.blob_data)
            add(new PipeDataElement(element));
    }
    // ===================================================================
    // ===================================================================
    public PipeBlob(String name, DevPipeDataElt[] elements) {
        this.name = name;
        for (DevPipeDataElt element : elements)
            add(new PipeDataElement(element));
    }
    // ===================================================================
    /**
     * @return blob name
     */
    // ===================================================================
    public String getName() {
        return name;
    }
    // ===================================================================
    /**
     * @return the number of data elements in root blob
     */
    // ===================================================================
    public int getDataElementNumber() {
        return size();
    }
    // ===================================================================
    // ===================================================================
    public DevPipeBlob getDevPipeBlobObject() {
        DevPipeDataElt[]    elementList = new DevPipeDataElt[size()];
        for (int i=0 ; i<size() ; i++)
            elementList[i] = get(i).getDevPipeDataEltObject();
        return new DevPipeBlob(name, elementList);
    }
    // ===================================================================
    /**
     * @param index specified data element index.
     * @return the name of data element at index
     * @throws DevFailed if index is negative or higher than data element number.
     *
    // ===================================================================
    public String getDataElementName(int index) throws DevFailed {
        try {
            return get(index).getName();
        }
        catch (Exception e) {
            Except.throw_exception(e.toString(), e.toString());
            return null;    //  cannot occur.
        }
    }
    // ===================================================================
    /**
     * @param index index specified index
     * @return type of data element at specified index.
     * @throws DevFailed if index is out of bounds.
     *
    // ===================================================================
    public int getDataElementType(int index) throws DevFailed {
        int type = TangoConst.Tango_DEV_VOID;
        try {
            type = get(index).getType();
        }
        catch (Exception e) {
            Except.throw_exception(e.toString(), e.toString());
        }
        return type;
    }
    // ===================================================================
    /**
     * @param name specified element name.
     * @return type of data element at specified name.
     * @throws DevFailed if name not found
     *
    // ===================================================================
    public int getDataElementType(String name) throws DevFailed {
        for (PipeDataElement element : this) {
            if (element.getName().equals(name)) {
                return element.getType();
            }
        }
        Except.throw_exception("TangoApi_NOT_FOUND", "Element named "  + name + " not found");
        return 0;    //  cannot occur.
    }
    */
     // ===================================================================
    // ===================================================================
}
