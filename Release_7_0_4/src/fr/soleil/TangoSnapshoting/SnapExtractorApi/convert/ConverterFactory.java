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
package fr.soleil.TangoSnapshoting.SnapExtractorApi.convert;

/**
 * A factory for the IConverter singleton.
 * @author CLAISSE 
 */
public class ConverterFactory 
{
	 public static final int DEFAULT = 0;
	 private static IConverter currentImpl = null;
	 
     /**
      * Instantiates and return the IConverter singleton
      * @param typeOfImpl The type of implementation
      * @return The required implementation
      */
	 public static IConverter getImpl ( int typeOfImpl ) 
	 {
	     switch ( typeOfImpl )
	     {
	     	case DEFAULT:
	     	    currentImpl = new DefaultConverter ();
	     	break;
	     	
	     	default:
	     	    throw new IllegalArgumentException ( "Expected DEFAULT_CONVERTER (0), got " + typeOfImpl + " instead." );
	     }
	     
	     return currentImpl;
	 }
	 
     /**
      * Returns the current implementation singleton
      * @return The current implementation singleton
      */
	 public static IConverter getCurrentImpl() 
	 {
	     return currentImpl;
	 }

}
