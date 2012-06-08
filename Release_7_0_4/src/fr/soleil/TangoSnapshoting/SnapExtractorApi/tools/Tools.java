package fr.soleil.TangoSnapshoting.SnapExtractorApi.tools;

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;

/**
 * A class with a few exception handling methods.
 * @author CLAISSE 
 */
public class Tools 
{
    /**
     * Converts a Throwable to a DevFailed and throws it.
     * @param exception The exception to convert
     * @return The DevFailed representation
     * @throws DevFailed Always thrown by definition of the method
     */
    public static void throwDevFailed(Throwable exception) throws DevFailed 
    {
	    DevFailed devFailed = new DevFailed ();
	    devFailed.initCause ( exception );
	    throw devFailed;
    }

    /**
     * Extracts causes from a Throwable if it is an instance of DevFailed, and prints it.
     * @param t The exception to log
     */
    public static void printIfDevFailed(Throwable t) 
    {
        t.printStackTrace ();    
        
        if (t instanceof DevFailed)
		{
            if ( ( (DevFailed) t ).getCause() != null )
            {
                System.out.println ( "CAUSE---------------START" );
			    ( (DevFailed) t ).getCause().printStackTrace (); 
			    System.out.println ( "CAUSE---------------END" );
            }
            
            DevError [] errors = ( (DevFailed) t ).errors;
		    if ( errors != null && errors.length !=0 )
		    {
		        System.out.println ( "ERRORS---------------START" );
		        for (  int i = 0 ; i < errors.length ; i ++ )
		        {
		            DevError error = errors [ i ];
		            System.out.println ( "desc/"+error.desc+"/origin/"+error.origin+"/reason/"+error.reason );
		        }
		        System.out.println ( "ERRORS---------------END" );
		    }
		}    
    }
}
