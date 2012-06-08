/*	Synchrotron Soleil 
 *  
 *   File          :  SimpleDelayManager.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  20 oct. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: SimpleDelayManager.java,v 
 *
 */
 /*
 * Created on 20 oct. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.delay;


public class SimpleDelayManager extends DefaultDelayManager implements IDelayManager 
{

    public SimpleDelayManager() 
    {
        super();
    }

    public int getSizeOfSteps(int totalNumberOfAttributes,long macroPeriod) 
    {
        return 1;
    }

    public long getTimeBetweenSteps(int totalNumberOfAttributes,long macroPeriod) 
    {
        if ( totalNumberOfAttributes == 0 )
        {
        	return macroPeriod;
        }
        
    	return macroPeriod/totalNumberOfAttributes;
    }
}
