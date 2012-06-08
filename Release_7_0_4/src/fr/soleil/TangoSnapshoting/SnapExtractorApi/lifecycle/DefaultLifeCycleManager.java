//+======================================================================
//$Source$
//
//Project:      Tango Archiving Service
//
//Description:  Java source code for the class  DefaultLifeCycleManager.
//						(Claisse Laurent) - 5 juil. 2005
//
//$Author$
//
//$Revision$
//
//$Log$
//Revision 1.5  2007/03/14 15:45:04  ounsy
//has two new parameters user and password
//
//Revision 1.4  2006/12/06 10:14:36  ounsy
//minor changes
//
//Revision 1.3  2006/06/02 14:26:54  ounsy
//added javadoc
//
//Revision 1.2  2006/05/04 14:32:10  ounsy
//minor changes (commented useless methods and variables)
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

import java.util.Hashtable;

import fr.soleil.TangoSnapshoting.SnapExtractorApi.convert.ConverterFactory;
import fr.soleil.TangoSnapshoting.SnapExtractorApi.datasources.db.ISnapReader;
import fr.soleil.TangoSnapshoting.SnapExtractorApi.datasources.db.SnapReaderFactory;
import fr.soleil.TangoSnapshoting.SnapExtractorApi.devicelink.Warnable;
import fr.soleil.TangoSnapshoting.SnapExtractorApi.naming.DynamicAttributeNamerFactory;

/**
 * The default implementation.
 * Extends Thread so that it can run in a separate thread at the request of the device
 * @author CLAISSE 
 */
public class DefaultLifeCycleManager extends Thread implements LifeCycleManager
{	 
  /**
  * A reference to the device that instantiated this, if the application is running in device mode 
  */
  protected Warnable watcherToWarn;
  private ISnapReader snapReader;
  
  private String snapUser;
  private String snapPassword;
  
  DefaultLifeCycleManager ()
  {
      this.setName (  "watcherThread" );
  }
  
  /* (non-Javadoc)snapUser
	  * @see mambo.lifecycle.LifeCycleManager#applicationWillStart(java.util.Hashtable)
	  */
	 public synchronized void applicationWillStart ( Hashtable startParameters )
	 {
	     System.out.println ( ".....INITIALIZING APPLICATION" );
	     
	     try
	     {
		     startFactories();
		     
		     snapReader = SnapReaderFactory.getCurrentImpl ();
             snapReader.openConnection ( this.snapUser , this.snapPassword );
	     }
	     catch ( Throwable t )
	     {
	         t.printStackTrace ();
	         this.warnWatcherFault ();
	     }
	     
	     System.out.println ( ".....APPLICATION INITIALIZED" );
	     System.out.println ();
	 }
	
	 
	 /**
	  * 5 juil. 2005
	  */
	 private void startFactories ()
	 {
	     SnapReaderFactory.getImpl ( SnapReaderFactory.REAL );
	     ConverterFactory.getImpl ( ConverterFactory.DEFAULT );
	     DynamicAttributeNamerFactory.getImpl ( DynamicAttributeNamerFactory.DEFAULT );
	 }
	
	 /* (non-Javadoc)
	  * @see mambo.lifecycle.LifeCycleManager#applicationClosed(java.util.Hashtable)
	  */
	 public synchronized void applicationWillClose ( Hashtable endParameters )
	 {
	     try
	     {
	         //begin do stuff
	         System.out.println( "Application will close !" );
	         
	         snapReader = SnapReaderFactory.getCurrentImpl ();
	         snapReader.closeConnection ();
	
	         System.out.println( "Application closed" );
	         //end do stuff
	         System.exit( 0 );
	     }
	     catch ( Throwable t )
	     {
	         t.printStackTrace();
	         System.exit( 1 );
	     }
	 }
	
  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  public void run()
  {
      try 
      {
          this.applicationWillStart ( null );
      } 
      catch (Throwable t) 
      {        
          t.printStackTrace();
      }
      
  }


  /* (non-Javadoc)
   * @see archwatch.lifecycle.LifeCycleManager#getAsThread()
   */
  public Thread getAsThread () 
  {
      return this;
  }

  /* (non-Javadoc)
   * @see archwatch.strategy.delay.IDelayManager#setWatcherToWarn(ArchivingWatcher.ArchivingWatcher)
   */
  public synchronized void setWatcherToWarn(Warnable _watcher) 
  {
      this.watcherToWarn = _watcher;
  }
  
  /**
  * Warns the device it should go into Fault state 
  */
  protected synchronized void warnWatcherFault () 
  {
      if ( this.watcherToWarn == null )
      {
          //standalone mode, do nothing
          return;
      }
      
      this.watcherToWarn.warnFault ();
  }
  
  /**
   * Warns the device it should go into Alarm state 
   */
  protected synchronized void warnWatcherAlarm () 
  {
      if ( this.watcherToWarn == null )
      {
          //standalone mode, do nothing
          return;
      }
      
      this.watcherToWarn.warnAlarm ();
  }
  
  /**
   * Warns the device it should go into Init state 
   */
  protected synchronized void warnWatcherInit () 
  {
      if ( this.watcherToWarn == null )
      {
          //standalone mode, do nothing
          return;
      }
      
      this.watcherToWarn.warnInit ();
  }
  
  /**
   * Warns the device it should go into Off state 
   */
  protected synchronized void warnWatcherOff () 
  {
      if ( this.watcherToWarn == null )
      {
          //standalone mode, do nothing
          return;
      }
      
      this.watcherToWarn.warnOff ();
  }

    /* (non-Javadoc)
     * @see snapextractor.api.devicelink.Warner#getWatcherToWarn()
     */
    public Warnable getWatcherToWarn() 
    {
        return this.watcherToWarn;
    }
}
