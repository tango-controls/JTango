//+======================================================================
//$Source$
//
//Project:      Tango Archiving Service
//
//Description:  Java source code for the class  LifeCycleManagerFactory.
//						(Claisse Laurent) - 5 juil. 2005
//
//$Author$
//
//$Revision$
//
//$Log$
//Revision 1.4  2006/08/24 13:51:03  ounsy
//added the Tdb  case
//
//Revision 1.3  2006/06/02 14:26:13  ounsy
//added javadoc
//
//Revision 1.2  2006/02/15 11:44:19  ounsy
//Added doRetry and doStartOnInitDevice flags
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

import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warnable;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.delay.DelayManagerFactory;

/**
 * A factory for the LifeCycleManager singleton.
 * Currently knows only one type of implementation.
 * @author CLAISSE 
 */
public class LifeCycleManagerFactory 
{
	 /**
	 * The default type
	 */
	 public static final int HDB_LIFE_CYCLE = 0;
     public static final int TDB_LIFE_CYCLE = 1;
	 private static LifeCycleManager currentImpl = null;
	 private static Warnable watcherToWarn = null;
	 
	 /**
	  * Instantiates and return the LifeCycleManager singleton
      * @param typeOfImpl The type of implementation
	  * @return The required implementation
	  */
	 public static LifeCycleManager getImpl ( int typeOfImpl ) 
	 {
	     switch ( typeOfImpl )
	     {
	     	case HDB_LIFE_CYCLE:
                currentImpl = new HdbLifeCycleManager ();
            break;
            
            case TDB_LIFE_CYCLE:
                currentImpl = new TdbLifeCycleManager ();
            break;
	     	
	     	default:
	     	    throw new IllegalArgumentException ( "Expected DEFAULT_LIFE_CYCLE (0), got " + typeOfImpl + " instead." );
	     }
	     
	     currentImpl.setWatcherToWarn ( watcherToWarn );
	     return currentImpl;
	 }
	 
	 /**
	  * Returns the current implementation singleton
      * @return The current implementation singleton
	  */
	 public static LifeCycleManager getCurrentImpl() 
	 {
	     return currentImpl;
	 }
	 
	 /**
     * Stores a reference to the specified Warnable device implementation for future use:
     * future instances created by this factory will be given this Warnable reference.
     * @param _watcherToWarn The specified Warnable device implementation
     */
    public static void setWatcherToWarn ( Warnable _watcherToWarn )
    {
        watcherToWarn = _watcherToWarn;
        DelayManagerFactory.setWatcherToWarn ( _watcherToWarn );
    }

}
