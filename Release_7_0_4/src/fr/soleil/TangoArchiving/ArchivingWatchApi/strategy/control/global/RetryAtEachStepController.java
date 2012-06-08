package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.global;


/**
 * An implementation that calls a "RetryForAttributes" command on the detected KO attributes. 
 * @author CLAISSE 
 */
public class RetryAtEachStepController extends DoActionAtEachStepController 
{
    RetryAtEachStepController() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.control.global.IController#useControlResult(archwatch.dto.ControlResult)
     */
    /*public synchronized int useControlResult ( ControlResult control ) 
    {
        int basicActionResult = super.useControlResult ( control );
        if ( basicActionResult == IController.NO_ACTION_NEEDED )
        {
            return IController.NO_ACTION_NEEDED;
        }
        
        //System.out.println ( "CLA/DoActionAtEachStepController/useControlResult/control.isFullCycle ()/"+control.isFullCycle () );
        return ControllerAdapter.doRetry ( control );
    }*/
}
