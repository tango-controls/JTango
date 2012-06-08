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
//Revision 1.7  2007/01/31 12:57:29  ounsy
//added a RETRY_EVERY_LARGE_AMOUNT_OF_STEPS implementaion
//
//Revision 1.6  2006/09/13 08:21:55  ounsy
//added RetryEveryNthStepController implementations
//
//Revision 1.5  2006/09/11 14:49:49  ounsy
//new implementation
//
//Revision 1.4  2006/08/24 13:46:24  ounsy
//minor changes
//
//Revision 1.3  2006/06/02 14:26:13  ounsy
//added javadoc
//
//Revision 1.2  2006/02/06 12:25:29  ounsy
//added the archiving auto-recovery process
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
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.global;

/**
 * A factory for the IController singleton.
 * @author CLAISSE 
 */
public class ControllerFactory
{
	 /**
	 * Code for a DummyController implementation 
	 */
	 public static final int DUMMY = 1;
	 /**
	 * Code for a DoActionAtFullCycleController implementation 
	 */
	 public static final int DO_ACTION_AT_FULL_CYCLE = 2;
	 /**
	 * Code for a RetryAtFullCycleController implementation 
	 */
	 public static final int RETRY_AT_FULL_CYCLE = 3;
     /**
     * Code for a DoActionAtEachStepController implementation 
     */
     public static final int DO_ACTION_AT_EACH_STEP = 4;
     /**
     * Code for a RetryAtEachStepController implementation 
     */
     public static final int RETRY_AT_EACH_STEP = 5;
     /**
     * Code for a RetryEveryThirdStepController implementation 
     */
     public static final int RETRY_EVERY_THIRD_STEP = 6;
     /**
      * Code for a RetryEveryLargeAmountOfStepsController implementation 
      */
     public static final int RETRY_EVERY_LARGE_AMOUNT_OF_STEPS = 7;
	
	 private static IController currentImpl = null;
	
     /**
      * Instantiates and return the IController singleton
      * @param typeOfImpl The type of implementation
      * @return The required implementation
      */
	 public static IController getImpl ( int typeOfImpl )
	 {
	     switch ( typeOfImpl )
	     {
	         case DUMMY:
	           	 currentImpl = new DummyController();
	         break;
	
	         case DO_ACTION_AT_FULL_CYCLE:
	             currentImpl = new DoActionAtFullCycleController();
	         break;
	         
	         case RETRY_AT_FULL_CYCLE:
	             currentImpl = new RetryAtFullCycleController();
	         break;
             
             case DO_ACTION_AT_EACH_STEP:
                 currentImpl = new DoActionAtEachStepController();
             break;
             
             case RETRY_AT_EACH_STEP:
                 currentImpl = new RetryAtEachStepController();
             break;
             
             case RETRY_EVERY_THIRD_STEP:
                 currentImpl = new RetryEveryThirdStepController();
             break;
             
             case RETRY_EVERY_LARGE_AMOUNT_OF_STEPS:
                 currentImpl = new RetryEveryLargeAmountOfStepsController();
             break;
             
             default:
	             throw new IllegalArgumentException( "Expected either DUMMY_TYPE (1) or BASIC_TYPE (2) or SECOND_CHANCE_TYPE(3), got " + typeOfImpl + " instead." );
	     }
	
	     return currentImpl;
	 }

     /**
      * Returns the current implementation singleton
      * @return The current implementation singleton
      */
	 public static IController getCurrentImpl ()
	 {
	     return currentImpl;
	 }
}
