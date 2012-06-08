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
//Revision 1.3  2006/12/06 10:14:36  ounsy
//minor changes
//
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
package fr.soleil.TangoSnapshoting.SnapExtractorApi.datasources.db;

/**
 * A factory for the ISnapReader singleton.
 * @author CLAISSE 
 */
public class SnapReaderFactory
{
	 public static final int DUMMY_TYPE = 1;
	 public static final int REAL = 2;
	
	 private static ISnapReader currentImpl = null;
	
     /**
      * Instantiates and return the ISnapReader singleton
      * @param typeOfImpl The type of implementation
      * @return The required implementation
      */
	 public static ISnapReader getImpl ( int typeOfImpl )
	 {
	     switch ( typeOfImpl )
	     {
	         case DUMMY_TYPE:
	             currentImpl = new DummySnapReader();
	         break;
	
	         case REAL:
	             currentImpl = new RealSnapReader();
	         break;
	
	         default:
	             throw new IllegalArgumentException( "Expected either DUMMY_IMPL_TYPE (1) or REAL_IMPL_TYPE (2), got " + typeOfImpl + " instead." );
	     }
	
	     return currentImpl;
	 }

     /**
      * Returns the current implementation singleton
      * @return The current implementation singleton
      */
	 public static ISnapReader getCurrentImpl ()
	 {
	     return currentImpl;
	 }

}
