//+======================================================================
//$Source$
//
//Project:      Tango Archiving Service
//
//Description:  Java source code for the class  AttrDBManagerFactory.
//						(Claisse Laurent) - 5 juil. 2005
//
//$Author$
//
//$Revision$
//
//$Log$
//Revision 1.7  2006/11/20 09:27:04  ounsy
//removed bad implementations
//
//Revision 1.6  2006/10/20 13:03:32  ounsy
//added a SIMPLE implemnetation
//
//Revision 1.5  2006/08/24 13:44:04  ounsy
//minor changes
//
//Revision 1.4  2006/06/02 14:26:30  ounsy
//added javadoc
//
//Revision 1.3  2006/02/15 11:47:03  ounsy
//small modification
//
//Revision 1.2  2006/01/27 13:04:10  ounsy
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
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.delay;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warnable;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * A factory for the IDelayManager singleton.
 * @author CLAISSE 
 */
public class DelayManagerFactory
{
	 public static final int BASIC = 1;
     public static final int SIMPLE = 2;
	 
	 private static IDelayManager currentImpl = null;
	 private static Warnable watcherToWarn = null;
	
     /**
      * Instantiates and return the IDelayManager singleton
      * @param typeOfImpl The type of implementation
      * @return The required implementation
      */
	 public static IDelayManager getImpl ( int typeOfImpl ) throws DevFailed
	 {
	     switch ( typeOfImpl )
	     {
	         case BASIC:
	           	 currentImpl = new DefaultDelayManager();
	         break;
	         
             case SIMPLE:
                 currentImpl = new SimpleDelayManager();
             break;
	
	         default:
	             Tools.throwDevFailed ( new IllegalArgumentException( "Expected either DEFAULT_TYPE (1) or FAST_REFRESHING_TYPE(2), got " + typeOfImpl + " instead." ) );
	     }
	
	     currentImpl.setWatcherToWarn ( watcherToWarn );
	     return currentImpl;
	 }

     /**
      * Returns the current implementation singleton
      * @return The current implementation singleton
      */
	 public static IDelayManager getCurrentImpl ()
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
    }

}
