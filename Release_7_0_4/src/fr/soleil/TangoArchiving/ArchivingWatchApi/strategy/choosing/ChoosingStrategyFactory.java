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
//Revision 1.5  2006/08/24 13:48:49  ounsy
//minor changes
//
//Revision 1.3  2006/06/02 14:26:13  ounsy
//added javadoc
//
//Revision 1.2  2006/03/01 15:43:03  ounsy
//spectrum attributes are now controlled
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
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.choosing;

/**
 * A factory for the IChoosingStrategy singleton.
 * @author CLAISSE 
 */
public class ChoosingStrategyFactory
{
	 /**
	 * Code for a TotalChoosingStrategy implementation
	 */
	 public static final int ALL = 1;
     /**
     * Code for a RandomChoosingStrategy implementation 
     */
     public static final int RANDOM = 2;
	 /**
	 * Code for a Exhaustive implementation 
	 */
	 public static final int EXHAUSTIVE_LIST = 3;
	 /**
	 * Code for a ScalarOnlyChoosingStrategy implementation 
	 */
	 public static final int SCALAR_ONLY = 4;
	 /**
	 * Code for a ScalarAndSpectrumsOnlyChoosingStrategy implementation 
	 */
	 public static final int SCALAR_AND_SPECTRUM_ONLY = 5;
	 
	 private static IChoosingStrategy currentImpl = null;
	
     /**
      * Instantiates and return the IChoosingStrategy singleton
      * @param typeOfImpl The type of implementation
      * @return The required implementation
      */
	 public static IChoosingStrategy getImpl ( int typeOfImpl )
	 {
	     switch ( typeOfImpl )
	     {
	         case ALL:
	             currentImpl = new TotalChoosingStrategy();
	         break;
	
	         case RANDOM:
	             currentImpl = new RandomChoosingStrategy();
	         break;
	         
	         case EXHAUSTIVE_LIST:
	             currentImpl = new ExhaustiveChoosingStrategy();
	         break;
	         
	         case SCALAR_ONLY:
	             currentImpl = new ScalarOnlyChoosingStrategy();
	         break;
	         
	         case SCALAR_AND_SPECTRUM_ONLY:
	             currentImpl = new ScalarsAndSpectrumsOnlyChoosingStrategy();
	         break;
	         
	         default:
	             throw new IllegalArgumentException( "Expected either TOTAL_TYPE (1) or RANDOM_TYPE (2) or EXHAUSTIVE_TYPE (3) or SCALAR_ONLY_TYPE(4) or SCALAR_AND_SPECTRUMS_ONLY_TYPE(5), got " + typeOfImpl + " instead." );
	     }
	
	     return currentImpl;
	 }

     /**
      * Returns the current implementation singleton
      * @return The current implementation singleton
      */
	 public static IChoosingStrategy getCurrentImpl ()
	 {
	     return currentImpl;
	 }

}
