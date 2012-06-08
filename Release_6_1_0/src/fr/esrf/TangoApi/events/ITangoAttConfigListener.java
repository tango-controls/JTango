//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.1  2008/04/11 07:14:09  pascal_verdier
// AttConfig event management added.
//
//
// Copyleftt 2008 by Synchrotron Soleil, France
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

