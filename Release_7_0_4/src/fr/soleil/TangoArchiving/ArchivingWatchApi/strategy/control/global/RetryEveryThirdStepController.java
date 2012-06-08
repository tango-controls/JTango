package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.global;


/**
 * An implementation that calls a "RetryForAttributes" command on the detected KO attributes. 
 * @author CLAISSE 
 */
public class RetryEveryThirdStepController extends RetryEveryNthStepController 
{
    RetryEveryThirdStepController () 
    {
        super ( 3 );
    }
}
