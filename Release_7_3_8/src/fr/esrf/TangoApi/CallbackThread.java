//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the CallbackThread class definition .
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
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
// $Revision$
//
// $Log$
// Revision 1.5  2009/03/25 13:32:08  pascal_verdier
// ...
//
// Revision 1.4  2009/01/16 12:42:58  pascal_verdier
// IntelliJIdea warnings removed.
// Bug in State attribute and Quality extraction fixed.
//
// Revision 1.3  2008/12/03 15:44:26  pascal_verdier
// javadoc warnings removed.
//
// Revision 1.2  2008/10/10 11:28:31  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.1  2007/08/23 08:33:26  ounsy
// updated change from api/java
//
// Revision 3.7  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.0  2003/04/29 08:03:27  pascal_verdier
// Asynchronous calls added.
// Logging related methods.
// little bugs fixed.
//
//-======================================================================

package fr.esrf.TangoApi;

/**
 * This class get the asynchronous call result and send it to a CallBack object.
 */

public class CallbackThread extends Thread implements ApiDefs {
    private final AsyncCallObject aco;

    // ===============================================================
    /**
     * Object constructor
     * 
     * @param aco
     *            Asynchronous call object
     */
    // ===============================================================
    public CallbackThread(final AsyncCallObject aco) {
	super("Async callaback");
	this.aco = aco;
    }

    // ===============================================================
    /**
     * Start thread.
     */
    // ===============================================================
    @Override
    public void run() {
	// System.out.println("I am in thread!");

	// All info are in AsyncCallObjec. It does everything itself
	try {
	    aco.manage_reply(0);
	} catch (final Exception e) {
	    System.err.println(e);
	}
    }
    // ===============================================================
    // ===============================================================
}
