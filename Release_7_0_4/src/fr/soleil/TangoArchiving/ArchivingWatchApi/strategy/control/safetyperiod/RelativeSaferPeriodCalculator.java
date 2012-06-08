/*	Synchrotron Soleil 
 *  
 *   File          :  RelativeSaferPeriodCalculator.java
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
 *   Log: RelativeSaferPeriodCalculator.java,v 
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
 * An implementation that computes the safer period by multiplying by a constant amount the theoretical archiving period.
 * @author CLAISSE 
 */
public class RelativeSaferPeriodCalculator implements ISaferPeriodCalculator 
{
    static final int DEFAULT_MULTIPLIER = 5;
    
    private double multiplier;
    private String description;
    
    static final String MODE_LABEL = "relative";
    
    RelativeSaferPeriodCalculator ( double _multiplier ) 
    {
        this.multiplier = _multiplier;
        this.description = this.getCalculationDescription ();
    }

    /* (non-Javadoc)
     * @see fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.safetyperiod.ISaferPeriodCalculator#getSaferPeriod(int)
     */
    public int getSaferPeriod ( int theoricPeriod ) 
    {
        return (int) Math.round ( theoricPeriod * multiplier );
    }

    /* (non-Javadoc)
     * @see fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.safetyperiod.ISaferPeriodCalculator#getCalculationDescription()
     */
    private String getCalculationDescription () 
    {
        String ret = "Safer period = theoric period * " + this.multiplier + ".";  
        return ret;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() 
    {
        return this.description;
    }
    
}
