//+======================================================================
//$Source$
//
//Project:      Tango Archiving Service
//
//Description:  Java source code for the class  LifeCycleManager.
//						(Claisse Laurent) - 5 juil. 2005
//
//$Author$
//
//$Revision$
//
//$Log$
//Revision 1.2  2006/06/02 14:26:54  ounsy
//added javadoc
//
//Revision 1.1  2006/01/27 14:36:48  ounsy
//new APIS for snap extracting
//
//Revision 1.1.2.2  2005/09/14 15:41:32  chinkumo
//Second commit !
//
//
//copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================
package fr.soleil.TangoSnapshoting.SnapExtractorApi.lifecycle;

import java.util.Hashtable;

import fr.soleil.TangoSnapshoting.SnapExtractorApi.devicelink.Warner;

/**
 * Describes the application's lifecycle:
 * <UL>
 *  <LI> its startup and shutdown
 *  <LI> its processing commands 
 * </UL> 
 * Extends Warner so that the APIs can warn the associated device (SnapExtractor) and use its Tango log methods.
 * @author CLAISSE 
 */
public interface LifeCycleManager extends Warner
{
    /**
     * Called when the application will start. Connects to the database, loads resources, .. 
     * @param startParameters Key/Value couples of parameters can be passed to this method. Not used so far.
     */
	public void applicationWillStart ( Hashtable startParameters );
     /**
     * Called when the application will stop. Closes the connection to the database, frees resources, .. 
     * @param endParameters Key/Value couples of parameters can be passed to this method. Not used so far.
     */
    public void applicationWillClose ( Hashtable endParameters );
    
    /**
     * Returns a Runnable representation of this life cycle. Useful in device mode, not in standalone mode. 
     * @return A Runnable representation of this life cycle
     */
	public Thread getAsThread ();
}
