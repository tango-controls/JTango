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
// $Revision:  $
//
//-======================================================================


package fr.esrf.TangoApi.events;


/**
 * Class Description: Utility methods for event classes
 * 
 * @author verdier
 * @version $Revision:  $
 */

public class EventUtil {

    private static boolean graphicIsAvailable = true;
    private static boolean graphicAvailableChecked = false;
    private static final Object  monitor = new Object();

    //===================================================================
    /**
     * Check if graphical environment is available
     * This test is used to know if method SwingUtilities.invokeLater() can be used.
     *
     * @return true if graphical environment is available
     */
    //===================================================================
    public static boolean graphicAvailable() {
        synchronized (monitor) {
            if (!graphicAvailableChecked) {
                String s = System.getProperty("SERVER");
                if (s!=null && s.equals("true")) {
                    graphicIsAvailable = false;
                }
                else {
                    try {
                        graphicIsAvailable = !java.awt.GraphicsEnvironment.isHeadless();
                    }
                    catch(Error e) {
                        graphicIsAvailable = false;
                        //System.err.println(e + "\n" + "---------------> Graphics Environment not available");
                    }
                    catch(Exception e) {
                        graphicIsAvailable = false;
                        //System.err.println(e + "\n" + "---------------> Graphics Environment not available");
                    }
                }
                graphicAvailableChecked = true;
           }
        }
        return graphicIsAvailable;
    }
    //===================================================================
    //===================================================================
}
