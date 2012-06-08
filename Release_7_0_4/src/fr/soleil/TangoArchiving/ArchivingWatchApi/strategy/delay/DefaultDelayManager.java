/*	Synchrotron Soleil 
 *  
 *   File          :  DefaultDelayManager.java
 *  
 *   Project       :  archiving_watcher
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  5 janv. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: DefaultDelayManager.java,v 
 *
 */
 /*
 * Created on 5 janv. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.delay;

import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Set;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.ArchivingWatch;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.DBReaderFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.IDBReader;
import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warnable;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ControlResult;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ModeData;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.choosing.ChoosingStrategyFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.choosing.IChoosingStrategy;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * The default implementation.
 * @author CLAISSE 
 */
public class DefaultDelayManager implements IDelayManager 
{
    /**
     * The ControlResult for the latest complete full cycle 
     */
    protected ControlResult latestCompleteResult;
    /**
     * The ControlResult that is currently being built 
     */
    protected ControlResult currentResult;
    /**
     * The latest ControlResult to be added to currentResult
     */
    protected ControlResult latestAddition;
    /**
     * The ControlResult for the latest complete full cycle having KO attributes
     */
    protected ControlResult latestBadCompleteResult;
    /**
     * A ControlResult that is built progressively during the first cycle, then (when it is complete), each newer control result for a given atribute overrides the control result for the same given atribute that was computed in the previous cycle.   
     */
    protected ControlResult rollOverResult;
    
    /**
     * The current position in the list of attributes to control, ranging from 0 to the total number of attributes to control
     */
    protected int currentIndex;
    
    /**
     * The Hashtable that stores the list of attributes to control for the next step
     */
    protected Hashtable archivedAttributes = null;
    /**
     * True if the current step is the first step a cycle 
     */
    protected boolean isNewCycle = true;
    
    /**
     * Used to directly control the number of attributes controlled in a step: minimum value  
     */
    private static final int MIN_PACKET_SIZE = 5;
    /**
     * Used to directly control the number of attributes controlled in a step: maximum value  
     */
    private static final int MAX_PACKET_SIZE = 5;
    
    /**
     * The size of the latest ControlResult to be added to currentResult
     */
    private int sizeOfLatestAddition  = 0;
    
    /**
     * A reference to the device that needs to be warned when the first control step is completed (so that it can go from the INIT state the ON state)  
     */
    protected Warnable watcher;
    private boolean hasWarnedWatcher;
    
    DefaultDelayManager() 
    {
        super();
        
        this.currentResult = new ControlResult ();
        this.rollOverResult = new ControlResult ();
        this.latestCompleteResult = null;
        
        this.currentIndex = 0;
        this.archivedAttributes = null;
        this.isNewCycle = true;
    }

    private boolean isFullCycle ()
    {
        boolean isFullCycle = this.currentResult == null ? false : this.currentResult.isComplete ();
        //System.out.println ( "CLA/isFullCycle/"+isFullCycle );
        return isFullCycle;
    }
    
    /* (non-Javadoc)
     * @see archwatch.strategy.delay.IDelayManager#addControlResult(archwatch.dto.ControlResult)
     */
    public boolean addControlResult(ControlResult partialControlResult, Set namesOfAttributesToControl) 
    {
        this.setSizeOfLatestAddition ( partialControlResult.getCurrentSize () );
        this.rollOverResult.removeOldLines ( namesOfAttributesToControl );//CLA 14/12/06 to avoid keeping attributes that are no longer archived, but were in previous cycles. The rollover system would otherwise prevent them from ever being removed from the list
        this.rollOverResult.addLines (  partialControlResult );
        
        boolean isFullCycle = this.isFullCycle ();
        if ( isFullCycle )
        {
            this.completeCycle ( partialControlResult );
        }
        else
        {
            this.latestAddition = partialControlResult.cloneControlResult ();
            this.currentResult.addLines (  partialControlResult );
            
            isFullCycle = this.currentResult.isComplete ();
            if ( isFullCycle )
            {
                this.completeCycle ( partialControlResult );
            }
        }
        
        this.warnWatcherOn ();
        
        return isFullCycle;
    }

    /**
     * Completes the current cycle
     * @param partialControlResult The final step of the cycle
     */
    private void completeCycle(ControlResult partialControlResult) 
    {
        Timestamp now = new Timestamp ( System.currentTimeMillis () );
//        System.out.println ( "---------completeCycle/now/"+now );
        this.currentResult.setEndDate ( now );
        
        this.latestCompleteResult = this.currentResult == null ? null :this.currentResult.cloneControlResult (); 
        
        this.rollOverResult.setStartDate ( now );
        this.rollOverResult.setEndDate ( null );
        
        this.currentResult = new ControlResult ();
        this.setNewCycle ( true );
    }

    public Hashtable chooseAttributesSubsetForCurrentStep ( Hashtable in ) throws DevFailed 
    {
        IDBReader attributesReader = DBReaderFactory.getCurrentImpl ();
        
        if ( in == null )
        {
            Tools.trace ( "DefaultDelayManager/chooseAttributesSubsetForCurrentStep/in == null !!!!" , Warnable.LOG_LEVEL_WARN );
        }
        
        IChoosingStrategy choosingStrategy = ChoosingStrategyFactory.getCurrentImpl ();
        Hashtable filteredAttributes = choosingStrategy.filterAttributesToControl ( in );
        int numberOfAttributesToControl = filteredAttributes.size ();
        int totalNumberOfAttributes = choosingStrategy.getNumberOfAttributesToControl();
        /*if ( numberOfAttributesToControl != totalNumberOfAttributes )
        {
            System.out.println ( "CLA/DefaultDelayManager/numberOfAttributesToControl/"+numberOfAttributesToControl+"/totalNumberOfAttributes/"+totalNumberOfAttributes );
        }*/
        
        long macroPeriod = ArchivingWatch.getMacroPeriod ();
        int numberOfAttributesByStep = this.getSizeOfSteps ( numberOfAttributesToControl , macroPeriod );        
        //System.out.println ( "CLA/DefaultDelayManager/numberOfAttributesByStep/"+numberOfAttributesByStep );
        
        int numberOfAttributesNotControlledYet = numberOfAttributesToControl - this.currentIndex;
        //System.out.println ( "CLA/DefaultDelayManager/numberOfAttributesToControl/"+numberOfAttributesToControl+"/currentIndex/"+currentIndex+"/numberOfAttributesNotControlledYet/"+numberOfAttributesNotControlledYet );
        if ( numberOfAttributesNotControlledYet < numberOfAttributesByStep )
        {
            numberOfAttributesByStep = numberOfAttributesNotControlledYet;
            //System.out.println ( "CLA/DefaultDelayManager/R 1" );
        }
        if ( numberOfAttributesByStep <= 0 )
        {
            this.currentIndex = 0;
            return new Hashtable ();
        }
        
        Hashtable ret = new Hashtable (); 
        Enumeration keys = filteredAttributes.keys ();
        for ( int i = 0 ; i < this.currentIndex ; i ++ )
        {
            try
            {
                keys.nextElement ();
            }
            catch ( NoSuchElementException nsee )
            {
                Tools.trace ( "DefaultDelayManager/chooseAttributesSubsetForCurrentStep/suspect NoSuchElementException." , Warnable.LOG_LEVEL_WARN );    
                int inSize = in == null ? -1 : in.size ();
                //Tools.trace ( "inSize/"+inSize+"/numberOfAttributesToControl/"+numberOfAttributesToControl+"/totalNumberOfAttributes/"+totalNumberOfAttributes+"/numberOfAttributesByStep/"+numberOfAttributesByStep+"/" , Warnable.LOG_LEVEL_WARN );
                Tools.trace ( "inSize/"+inSize+"/numberOfAttributesToControl/"+numberOfAttributesToControl+"/numberOfAttributesByStep/"+numberOfAttributesByStep+"/" , Warnable.LOG_LEVEL_WARN );
                return ret;
            }
        }

        for ( int i = 0 ; i < numberOfAttributesByStep ; i ++ )
        {
            try
            {
                String key = (String) keys.nextElement ();
                //System.out.println ( "----------CLA/DefaultDelayManager/key|"+key );
                ModeData val = attributesReader.getModeDataForAttribute ( key );
                
                if ( val.getMode().getModeP() == null )
                {
                    Tools.trace ( "DefaultDelayManager/chooseAttributesSubsetForCurrentStep/modeP == null!/for attribute|"+key+"|" , Warnable.LOG_LEVEL_ERROR );
                }
                
                ret.put ( key , val );
            }
            catch ( NoSuchElementException nsee )
            {
                this.currentIndex = 0;
                return ret;
            }
        }
        
        this.currentIndex += numberOfAttributesByStep;
        return ret;
    }

    /**
     * @return Returns the latestCompleteResult.
     */
    public synchronized ControlResult getLatestCompleteCycleResult() 
    {
        return this.latestCompleteResult;
    }
    
    /**
     * Returns the ControlResult of the latest complete cycle if available, otherwise returns the result of the latest constrol step.
     * @return The ControlResult of the latest complete cycle if available, otherwise returns the result of the latest constrol step.  
     */
    public synchronized ControlResult getLatestCompleteCycleResultOrStepIfCycleUnavailable () 
    {
        if ( this.latestCompleteResult != null )
        {
            return this.latestCompleteResult;    
        }
        else
        {
            return this.getLatestCompleteStepResult ();
        }
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.delay.IDelayManager#getArchivedAttributes()
     */
    public Hashtable reloadArchivingAttributesIfNecessary() throws DevFailed 
    {
        if ( this.isNewCycle )
        {            
            //System.out.println ( "DefaultDelayManager/getArchivingAttributesForCurrentStep/isNewCycle" );
            IDBReader archivedAttributesReader = DBReaderFactory.getCurrentImpl ();
            
            Hashtable _archivedAttributes = new Hashtable ();
            try
            {
                _archivedAttributes = archivedAttributesReader.getArchivedAttributes ();
            }
            catch ( DevFailed e )
            {
                Tools.trace ( "DefaultDelayManager/getArchivingAttributesForCurrentStep/failed calling getArchivedAttributes!" , e , Warnable.LOG_LEVEL_ERROR );
                this.resetControlResults ();
                
                if ( ! archivedAttributesReader.isDBConnectionAlive () )
                {
                    throw e;
                }
            }
            finally
            {
                this.archivedAttributes = _archivedAttributes;
                this.isNewCycle = false;    
            }
        }
        else
        {
            System.out.println ( "DefaultDelayManager/getArchivingAttributesForCurrentStep/!isNewCycle" );
        }
        return this.archivedAttributes;
    }
    
    private void resetControlResults() 
    {
        this.latestCompleteResult = new ControlResult ();
        this.currentResult = new ControlResult ();
        this.latestAddition = new ControlResult ();
        this.rollOverResult = new ControlResult ();
        this.currentIndex = 0;
        
        this.archivedAttributes = new Hashtable ();
        
        this.sizeOfLatestAddition  = 0;
    }

    /**
     * @return Returns the isNewCycle.
     */
    public synchronized boolean isNewCycle() {
        return isNewCycle;
    }
    /**
     * @param isNewCycle The isNewCycle to set.
     */
    public synchronized void setNewCycle(boolean isNewCycle) {
        this.isNewCycle = isNewCycle;
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.delay.IDelayManager#getSizeOfAttributesPackets(int, long)
     */
    public int getSizeOfSteps ( int totalNumberOfAttributes , long macroPeriod ) 
    {        
        System.out.println ("DefaultDelayManager/getSizeOfFullAttributesPackets/0");
        
        if ( totalNumberOfAttributes < DefaultDelayManager.MIN_PACKET_SIZE )
        {
            return totalNumberOfAttributes;
            //MIN_PACKET_SIZE can be overriden if there is only one packet
        }
        else if ( totalNumberOfAttributes < DefaultDelayManager.MAX_PACKET_SIZE )
        {
            return totalNumberOfAttributes;
        } 
        else
        {
            return MAX_PACKET_SIZE;
        }
    }
    
    /**
     * Computes the number of steps needed to complete a cycle
     * @param totalNumberOfAttributes The total number of attributes in a cycle
     * @param macroPeriod The length of a cycle
     * @return The number of steps needed to complete a cycle
     */
    private int getNumberOfPackets ( int totalNumberOfAttributes , long macroPeriod ) 
    {
        int sizeOfAttributesPackets = this.getSizeOfSteps ( totalNumberOfAttributes , macroPeriod );
        int numberOfPackets = (int) Math.ceil ( ( totalNumberOfAttributes * 1.0 ) / ( sizeOfAttributesPackets * 1.0 ) );
        
        return numberOfPackets;
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.delay.IDelayManager#getTimeBetweenAttributesPackets(int, long)
     */
    public long getTimeBetweenSteps ( int totalNumberOfAttributes , long macroPeriod ) 
    {
        int numberOfPackets = this.getNumberOfPackets ( totalNumberOfAttributes , macroPeriod );
        long timeBetweenAttributesPackets = (long) (( macroPeriod * 1.0 ) / ( numberOfPackets * 1.0 ));
        
        return timeBetweenAttributesPackets;
    }

    /**
     * @return Returns the sizeOfLatestAddition.
     */
    public synchronized int getSizeOfLatestAddition() {
        return sizeOfLatestAddition;
    }
    /**
     * @param sizeOfLatestAddition The sizeOfLatestAddition to set.
     */
    private void setSizeOfLatestAddition(int sizeOfLatestAddition) {
        this.sizeOfLatestAddition = sizeOfLatestAddition;
    }
    /**
     * @return Returns the ControlResult of the latest complete cycle.
     */
    public synchronized ControlResult getLatestCompleteStepResult() 
    {
        if ( currentResult != null && currentResult.getCurrentSize () != 0 )
        {
            return this.currentResult;    
        }
        else
        {
            return this.latestCompleteResult;
        }
    }
    /**
     * Returns the ControlResult of the latest step  
     * @return Returns the latestAddition.
     */
    public synchronized ControlResult getLatestAddition() {
        return latestAddition;
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.delay.IDelayManager#getControlResult(int)
     */
    public synchronized ControlResult getControlResult ( int readType ) throws DevFailed 
    {
        ControlResult ret = null;
        
        switch ( readType )
        {
        	case IDelayManager.READ_LATEST_ADDITION :
        	    ret = this.getLatestAddition ();
        	break;
        	
        	case IDelayManager.READ_LATEST_COMPLETE_CYCLE :
        	    ret = this.getLatestCompleteCycleResult ();
        	break;
        	    
        	case IDelayManager.READ_LATEST_COMPLETE_CYCLE_AUTO :
        	    ret = this.getLatestCompleteCycleResultOrStepIfCycleUnavailable ();
        	break;
        	    
        	case IDelayManager.READ_LATEST_COMPLETE_STEP :
        	    ret = this.getLatestCompleteStepResult ();
        	break;
        	
        	case IDelayManager.READ_LATEST_BAD_CYCLE :
        	    ret = this.getLatestBadCompleteResult ();
        	break;
        	
        	case IDelayManager.READ_ROLLOVER :
        	    ret = this.getRollOverResult ();
        	break;
        	
        	default :
        	    Tools.throwDevFailed ( new IllegalArgumentException( "Expected either LATEST_ADDITION (30),LATEST_COMPLETE_CYCLE(10),LATEST_COMPLETE_CYCLE_OR_STEP_IF_CYCLE_NOT_AVAILABLE(11),LATEST_COMPLETE_STEP(20), got " + readType + " instead." ) );
        }
        
        if ( ret != null && ! ret.isBuilt () )
        {
            ret.build ( ArchivingWatch.isDoArchiverDiagnosis () , false );
        }
        
        return ret;
    }
    /**
     * Returns a ControlResult refreshed at every control step, by overriding the previous result of the attributes that were just controlled
     * @return A ControlResult refreshed at every control step, by overriding the previous result of the attributes that were just controlled 
     */
    public ControlResult getRollOverResult() 
    {
        return this.rollOverResult;
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.delay.IDelayManager#setWatcherToWarn(ArchivingWatcher.ArchivingWatcher)
     */
    public synchronized void setWatcherToWarn(Warnable _watcher) 
    {
        this.watcher = _watcher;
    }
    
    /**
     * Warns the device it should go into the ON state, if it hasn't been warned already
     */
    protected synchronized void warnWatcherOn () 
    {
        if ( this.watcher == null )
        {
            //standalone mode, do nothing
            return;
        }
        
        if ( ! this.hasWarnedWatcher )
        {
            this.watcher.warnOn ();
            this.hasWarnedWatcher = true;
        }
    }
    /**
     * Returns the ControlResult for the latest complete full cycle having KO attributes
     * @return Returns the latestBadCompleteResult.
     */
    public synchronized ControlResult getLatestBadCompleteResult() {
        return latestBadCompleteResult;
    }
    /**
     * @param latestBadCompleteResult The latestBadCompleteResult to set.
     */
    public synchronized void setLatestBadCompleteResult(ControlResult latestBadCompleteResult) 
    {
        this.latestBadCompleteResult = latestBadCompleteResult;
        this.latestBadCompleteResult.setEndDate ( new Timestamp ( System.currentTimeMillis () ) );
    }

    /* (non-Javadoc)
     * @see fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warner#getWatcherToWarn()
     */
    public Warnable getWatcherToWarn() 
    {
        return this.watcher;
    }
}
