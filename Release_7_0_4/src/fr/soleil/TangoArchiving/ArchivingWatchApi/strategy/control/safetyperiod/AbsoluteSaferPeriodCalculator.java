/*	Synchrotron Soleil 
 *  
 *   File          :  AbsoluteSaferPeriodCalculator.java
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
 *   Log: AbsoluteSaferPeriodCalculator.java,v 
 *
 */
 /*
 * Created on 26 janv. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.safetyperiod;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * An implementation that computes the safer period by adding a constant amount of time to the theoretical archiving period. 
 * @author CLAISSE 
 */
public class AbsoluteSaferPeriodCalculator implements ISaferPeriodCalculator 
{
    private static final int ADD_SECONDS = 1;
    private static final int ADD_MINUTES = 2;
    private static final int ADD_HOURS = 3;
    
    static final int DEFAULT_TYPE = AbsoluteSaferPeriodCalculator.ADD_MINUTES;
    static final int DEFAULT_AMOUNT = 15;
    
    private int amount; 
    private int type;
    private String description;
    
    static final String MODE_LABEL = "absolute";
    private static final String ADD_SECONDS_LABEL = "seconds";
    private static final String ADD_MINUTES_LABEL = "minutes";
    private static final String ADD_HOURS_LABEL = "hours";
    
    AbsoluteSaferPeriodCalculator ( int _amount , int _type ) throws DevFailed 
    {
        switch ( _type )
        {
	        case AbsoluteSaferPeriodCalculator.ADD_HOURS:
	            break;
	    	case AbsoluteSaferPeriodCalculator.ADD_MINUTES:
	    	    break;
	    	case AbsoluteSaferPeriodCalculator.ADD_SECONDS:
	    	    break;
	    	default :
	    	    Tools.throwDevFailed ( new IllegalArgumentException ( "Expected ADD_SECONDS(), ADD_MINUTES(2), ADD_HOURS(3) got " + _type + "instead." ) );
        }
        
        this.amount = _amount;
        this.type = _type;
        this.description = this.getCalculationDescription ();
    }

    /* (non-Javadoc)
     * @see fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.safetyperiod.ISaferPeriodCalculator#getSaferPeriod(int)
     */
    public int getSaferPeriod(int theoricPeriod) 
    {
        int amountInMilliSeconds = this.amount;
        int ret = theoricPeriod;
        
        switch ( this.type )
        {
        	case AbsoluteSaferPeriodCalculator.ADD_HOURS:
        	    amountInMilliSeconds *= 60;
        	case AbsoluteSaferPeriodCalculator.ADD_MINUTES:
        	    amountInMilliSeconds *= 60;
        	case AbsoluteSaferPeriodCalculator.ADD_SECONDS:
        	    amountInMilliSeconds *= 1000;
        }
        ret += amountInMilliSeconds;
        return ret;
    }

    /* (non-Javadoc)
     * @see fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.safetyperiod.ISaferPeriodCalculator#getCalculationDescription()
     */
    private String getCalculationDescription () 
    {
        String label = null;
        switch ( this.type )
        {
	        case AbsoluteSaferPeriodCalculator.ADD_HOURS:
	            label = ADD_HOURS_LABEL;
	        break;
	    	case AbsoluteSaferPeriodCalculator.ADD_MINUTES:
	    	    label = ADD_MINUTES_LABEL;
	    	break;
	    	case AbsoluteSaferPeriodCalculator.ADD_SECONDS:
	    	    label = ADD_SECONDS_LABEL;
	    	break;
        }
        
        String ret = "Safer period = theoric period + " + this.amount + " " + label;
        return ret;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param typeOfAddition_s
     * @return
     */
    static int getTypeOfAddition ( String typeOfAddition_s ) throws DevFailed
    {
        if ( AbsoluteSaferPeriodCalculator.ADD_HOURS_LABEL.equals ( typeOfAddition_s ) )
        {
            return AbsoluteSaferPeriodCalculator.ADD_HOURS;    
        }
        else if ( AbsoluteSaferPeriodCalculator.ADD_MINUTES_LABEL.equals ( typeOfAddition_s ) )
        {
            return AbsoluteSaferPeriodCalculator.ADD_MINUTES;    
        }
        else if ( AbsoluteSaferPeriodCalculator.ADD_SECONDS_LABEL.equals ( typeOfAddition_s ) )
        {
            return AbsoluteSaferPeriodCalculator.ADD_SECONDS;    
        }
        else
        {
            SaferPeriodCalculatorFactory.throwIllegalArgumentException ( typeOfAddition_s );
        }
        return -1;
    }
    
}
