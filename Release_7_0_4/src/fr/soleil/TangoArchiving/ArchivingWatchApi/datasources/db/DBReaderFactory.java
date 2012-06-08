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
//Revision 1.1  2006/08/24 13:41:22  ounsy
//replaces HdbReaderFactory
//
//Revision 1.3  2006/06/02 14:26:13  ounsy
//added javadoc
//
//Revision 1.2  2006/01/27 13:01:36  ounsy
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
package fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * A factory for the IHDBReader singleton.
 * @author CLAISSE 
 */
public class DBReaderFactory
{
	 /**
	 * Code for a dummy implementation
	 */
	 public static final int DUMMY = 1;
     /**
     * Code for an implementation that really loads Database data
     */
	 public static final int HDB = 2;
     /**
     * Code for an implementation that really loads Database data
     */
     public static final int TDB = 3;
	
	 private static IDBReader currentImpl = null;
	
     /**
      * Instantiates and return the IHDBReader singleton
      * @param typeOfImpl The type of implementation
      * @return The required implementation
      */
	 public static IDBReader getImpl ( int typeOfImpl ) throws DevFailed
	 {
	     switch ( typeOfImpl )
	     {
	         case DUMMY:
	             currentImpl = new DummyDBReader();
	         break;
	
	         case HDB:
	             currentImpl = new HDBReader();
	         break;
	
             case TDB:
                 currentImpl = new TDBReader();
             break;
             
	         default:
	             Tools.throwDevFailed ( new IllegalArgumentException( "Expected either DUMMY_IMPL_TYPE (1) or REAL_IMPL_TYPE (2), got " + typeOfImpl + " instead." ) );
	     }
	
	     return currentImpl;
	 }


     /**
      * Returns the current implementation singleton
      * @return The current implementation singleton
      */
	 public static IDBReader getCurrentImpl ()
	 {
	     return currentImpl;
	 }

}
