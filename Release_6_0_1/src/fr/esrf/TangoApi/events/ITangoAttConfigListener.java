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
 * @author  ounsy
 */
public interface ITangoAttConfigListener extends EventListener {
    public void attConfig( TangoAttConfigEvent e);          
}

