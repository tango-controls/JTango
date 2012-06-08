/*	Synchrotron Soleil 
 *  
 *   File          :  Test.java
 *  
 *   Project       :  archiving_watcher
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  12 janv. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: Test.java,v 
 *
 */
 /*
 * Created on 12 janv. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi;


/**
 * 
 * @author CLAISSE 
 */
public class Test 
{

    /**
     * 
     */
    public Test() 
    {
        
    }
    
    public static void main ( String[] args )
    {
        InfiniteLoopThread infiniteLoopThread = new Test().getInfiniteLoopThread ();
       
        System.out.println ( "CLA AVANT" );
        infiniteLoopThread.start ();
        System.out.println ( "CLA APRES" );
    }
    
    public InfiniteLoopThread getInfiniteLoopThread() 
    {
        return new InfiniteLoopThread ();
    }
    
    private class InfiniteLoopThread extends Thread
    {
        public void run ()
        {
            while ( true )
            {
    
            }
        }
    }

}
