//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
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
// Revision 1.3  2008/10/10 11:34:14  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.2  2008/04/11 08:08:44  pascal_verdier
// *** empty log message ***
//
// Revision 1.1  2008/04/11 07:14:09  pascal_verdier
// AttConfig event management added.
//-======================================================================
/*
 * ITangoAttConfigListener.java
 *
 * Created on April 10, 2008 By Pascal Verdier
 */

package fr.esrf.TangoApi.events;

import java.util.EventListener;

/**
 *
 * @author  pascal_verdier
 */
public interface ITangoAttConfigListener extends EventListener {
    public void attConfig( TangoAttConfigEvent e);          
}

