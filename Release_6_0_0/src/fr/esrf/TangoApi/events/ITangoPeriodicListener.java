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
// Revision 1.2  2004/03/19 10:24:34  ounsy
// Modification of the overall Java event client Api for synchronization with tango C++ Release 4
//
// Revision 1.1  2004/03/08 11:43:23  pascal_verdier
// *** empty log message ***
//
//
// Copyleftt 2003 by Synchrotron Soleil, France
//-======================================================================
/*
 * IAttrPeriodicChangeListener.java
 *
 * Created on September 22, 2003, 11:54 AM
 */

package fr.esrf.TangoApi.events;


import java.util.EventListener;

/**
 *
 * @author  ounsy
 */
public interface ITangoPeriodicListener extends EventListener {
    public void periodic( TangoPeriodicEvent e);    
}
