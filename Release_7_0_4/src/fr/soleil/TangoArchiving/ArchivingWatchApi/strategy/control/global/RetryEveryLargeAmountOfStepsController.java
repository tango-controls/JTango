package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.global;


/**
 * An implementation that calls a "RetryForAttributes" command on the detected KO attributes. 
 * @author CLAISSE 
 */
public class RetryEveryLargeAmountOfStepsController extends RetryEveryNthStepController 
{
    private static final int LARGE_AMOUNT_OF_STEPS = 1000;
    
    RetryEveryLargeAmountOfStepsController () 
    {
        super ( LARGE_AMOUNT_OF_STEPS );
    }
}
