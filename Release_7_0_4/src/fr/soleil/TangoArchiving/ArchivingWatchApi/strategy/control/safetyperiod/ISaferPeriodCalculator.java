/*	Synchrotron Soleil 
 *  
 *   File          :  ISaferPeriodCalculator.java
 *  
 *   Project       :  javaapi_project
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  26 janv. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: ISaferPeriodCalculator.java,v 
 *
 */
 /*
 * Created on 26 janv. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.safetyperiod;

/**
 * Given a theoretical archiving period,  
 * computes an effective, "safer" period by which the archiving controls will be performed.
 * This saferPeriod is longer than the theoretical archiving period, to account for the various
 * delays introduced by network, database,... times and delays.
 * This way, one can avoid "false positives" controls, where fine working attributes would be wrongfully marked KO because
 * of those physical delays.
 * @author CLAISSE 
 */
public interface ISaferPeriodCalculator 
{
    /**
     * Computes a safer, effective control period, given the theoretical archiving period.
     * @param theoricPeriod The theoretical archiving period, as found in the table AMT
     * @return The larger, safer control period. 
     */
    public int getSaferPeriod ( int theoricPeriod );
    /**
     * Returns a description of this implementation's calculation of saferPeriod. 
     * @return A description of this implementation's calculation of saferPeriod
     */
    public String getDescription ();
}
