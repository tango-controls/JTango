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
//Revision 1.4  2006/09/11 14:49:49  ounsy
//new implementation
//
//Revision 1.3  2006/08/24 13:45:23  ounsy
//minor changes
//
//Revision 1.2  2006/06/02 14:26:30  ounsy
//added javadoc
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
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.modes;


/**
 * A factory for the IModeController singleton.
 * @author CLAISSE 
 */
public class ModeControllerFactory
{
     /**
     * Code for a ModeControllerAdapter implementation 
     */
	 public static final int DUMMY = 1;
	 
	 /**
	 * Code for a DebugModeController implementation
	 */
	 public static final int DEBUG = 2;
	 /**
	 * Code for a HdbModeControllerByRecordCount implementation
	 */
	 public static final int HDB_BY_RECORD_COUNT = 3;
     /**
     * Code for a HdbModeControllerByLastInsertComparison implementation
     */
     public static final int HDB_BY_LAST_INSERT_COMPARISON = 5;
     /**
     * Code for a TdbModeControllerByRecordCount implementation
     */
     public static final int TDB_BY_RECORD_COUNT = 4;
     /**
      * Code for a TdbModeControllerByLastInsertComparison implementation
      */
      public static final int TDB_BY_LAST_INSERT_COMPARISON = 6;
     
	
	 private static IModeController currentImpl = null;
	
     /**
      * Instantiates and return the IController singleton
      * @param typeOfImpl The type of implementation
      * @return The required implementation
      */
	 public static IModeController getImpl ( int typeOfImpl )
	 {
         switch ( typeOfImpl )
	     {
	         case DUMMY:
                 currentImpl = new HdbModeControllerAdapter();
	         break;
	
	         case DEBUG:
                 currentImpl = new DebugModeController();
	         break;
	         
	         case HDB_BY_RECORD_COUNT:
	             currentImpl = new HdbModeControllerByRecordCount();
	         break;
             
             case HDB_BY_LAST_INSERT_COMPARISON:
                 currentImpl = new HdbModeControllerByLastInsertComparison();
             break;
             
             case TDB_BY_RECORD_COUNT:
                 currentImpl = new TdbModeControllerByRecordCount();
             break;
             
             case TDB_BY_LAST_INSERT_COMPARISON:
                 currentImpl = new TdbModeControllerByLastInsertComparison();
             break;
             
	         default:
	             throw new IllegalArgumentException( "Expected either DUMMY (1) or DEBUG (2) or HDB_BY_RECORD_COUNT (3) or TDB_BY_RECORD_COUNT (4) , got " + typeOfImpl + " instead." );
	     }
	
	     return currentImpl;
	 }

     /**
      * Returns the current implementation singleton
      * @return The current implementation singleton
      */
	 public static IModeController getCurrentImpl ()
	 {
	     return currentImpl;
	 }

}
