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
//Revision 1.3  2007/03/14 15:45:04  ounsy
//has two new parameters user and password
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
package fr.soleil.TangoSnapshoting.SnapExtractorApi.lifecycle;

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
	 public static final int DEFAULT_LIFE_CYCLE = 0;
	 private static LifeCycleManager currentImpl = null;
	 
     private static String snapUser;
     private static String snapPassword;
     
     /**
      * Instantiates and return the LifeCycleManager singleton
      * @param typeOfImpl The type of implementation
      * @return The required implementation
      */
	 public static LifeCycleManager getImpl ( int typeOfImpl ) 
	 {
	     switch ( typeOfImpl )
	     {
	     	case DEFAULT_LIFE_CYCLE:
	     	    currentImpl = new DefaultLifeCycleManager ();
	     	break;
	     	
	     	default:
	     	    throw new IllegalArgumentException ( "Expected DEFAULT_LIFE_CYCLE (0), got " + typeOfImpl + " instead." );
	     }
	     
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
     
     public static void setUser(String _snapUser) 
        {
            snapUser = _snapUser;
        }

        public static void setPassword(String _snapPassword) 
        {
            snapPassword = _snapPassword;    
        }
}
