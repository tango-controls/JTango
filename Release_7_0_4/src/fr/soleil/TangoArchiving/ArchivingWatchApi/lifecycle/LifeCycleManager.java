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
//Revision 1.6  2006/08/24 13:50:45  ounsy
//processStep is now void
//
//Revision 1.5  2006/06/02 14:26:13  ounsy
//added javadoc
//
//Revision 1.4  2006/05/16 13:16:38  ounsy
//added a isProcessing() method
//
//Revision 1.3  2006/02/15 11:44:19  ounsy
//Added doRetry and doStartOnInitDevice flags
//
//Revision 1.2  2006/01/27 13:01:58  ounsy
//The exceptions thrown are now all DevFailed
//
//Revision 1.1  2006/01/19 16:20:13  ounsy
//New API specialised in watching archiving.
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
package fr.soleil.TangoArchiving.ArchivingWatchApi.lifecycle;

import java.util.Hashtable;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warner;

/**
 * Describes the application's lifecycle:
 * <UL>
 *  <LI> its startup and shutdown
 *  <LI> its processing commands 
 * </UL> 
 * Extends Warner so that the APIs can warn the associated device (ArchivingWatcher) and use its Tango log methods.
 * @author CLAISSE 
 */
public interface LifeCycleManager extends Warner
{
	 /**
     * Called when the application will start. Connects to the database, loads resources, .. 
	 * @param doRetry True if the watcher has to call a retry command on the KO attributes it detects.
	 */
	 public void applicationWillStart ( boolean doRetry );
	 /**
	 * Called when the application will stop. Closes the connection to the database, frees resources, .. 
     * @param endParameters Key/Value couples of parameters can be passed to this method. Not used so far.
	 */
	 public void applicationWillClose ( Hashtable endParameters );
     
     /**
     * Describes the infinite repetition of control cycles through time. A cycle is made of smaller atomic steps.
     * @param macroPeriod The length of a full control cycle.
     * @param doArchiverDiagnosis True if the watcher has to investigate KO archivers
     */
     public void process ( long macroPeriod , boolean doArchiverDiagnosis );
     /**
     * @param macroPeriod The length of a control step.
     * @param doArchiverDiagnosis True if the watcher has to investigate KO archivers
     * @throws DevFailed
     */
     public void processStep ( long macroPeriod , boolean doArchiverDiagnosis ) throws DevFailed;
     
     /**
     * Tells the watcher to start or resume controlling. 
     */
     public void startProcessing ();
     /**
     * Tells the watcher to stop controlling. 
     */
     public void stopProcessing ();
     /**
     * Returns whether the watcher is currently controlling. 
     * @return Whether the watcher is currently controlling.  
     */ 
     public boolean isProcessing();
     
     /**
     * Returns a Runnable representation of this life cycle. Useful in device mode, not in standalone mode. 
     * @return A Runnable representation of this life cycle
     */
     public Thread getAsThread ();
}
