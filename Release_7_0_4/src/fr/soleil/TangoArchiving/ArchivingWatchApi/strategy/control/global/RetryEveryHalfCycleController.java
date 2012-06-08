package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.global;


/**
 * An implementation that calls a "RetryForAttributes" command on the detected KO attributes. 
 * @author CLAISSE 
 */
public class RetryEveryHalfCycleController extends RetryEveryNthStepController 
{
    RetryEveryHalfCycleController () 
    {
        super ();
        
        int _numberOfStepsBetweenTwoRetries = 4;
        super.setNumberOfStepsBetweenTwoRetries(_numberOfStepsBetweenTwoRetries);
    }
}
