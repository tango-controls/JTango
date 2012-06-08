package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.global;

import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ControlResult;

/**
 * An implementation that calls a "RetryForAttributes" command on the detected KO attributes. 
 * @author CLAISSE 
 */
public class RetryEveryNthStepController extends RetryAtEachStepController 
{
    private int numberOfStepsBetweenTwoRetries;
    private int stepsCounter;
    
    RetryEveryNthStepController () 
    {
        super();
        this.stepsCounter = 0;
    }
    
    RetryEveryNthStepController ( int _numberOfStepsBetweenTwoRetries ) 
    {
        super();
        this.numberOfStepsBetweenTwoRetries = _numberOfStepsBetweenTwoRetries;
        this.stepsCounter = 0;
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.control.global.IController#useControlResult(archwatch.dto.ControlResult)
     */
    public synchronized int useControlResult ( ControlResult control ) 
    {
        int basicActionResult = super.useControlResult ( control );
        if ( basicActionResult == IController.NO_ACTION_NEEDED )
        {
            return IController.NO_ACTION_NEEDED;
        }
        
        this.stepsCounter ++;
        if ( this.stepsCounter == this.numberOfStepsBetweenTwoRetries )
        {
            this.stepsCounter = 0;
            return ControllerAdapter.doRetry ( control );    
        }
        else
        {
            return IController.NO_ACTION_NEEDED;
        }
    }

    /**
     * @return Returns the numberOfStepsBetweenTwoRetries.
     */
    protected int getNumberOfStepsBetweenTwoRetries() {
        return this.numberOfStepsBetweenTwoRetries;
    }

    /**
     * @param numberOfStepsBetweenTwoRetries The numberOfStepsBetweenTwoRetries to set.
     */
    protected void setNumberOfStepsBetweenTwoRetries(int numberOfStepsBetweenTwoRetries) {
        this.numberOfStepsBetweenTwoRetries = numberOfStepsBetweenTwoRetries;
    }
}
