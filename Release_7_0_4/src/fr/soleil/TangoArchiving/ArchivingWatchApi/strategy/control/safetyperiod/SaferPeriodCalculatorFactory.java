package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.safetyperiod;

import java.util.StringTokenizer;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * A factory for the ISaferPeriodCalculator singleton.
 * @author CLAISSE 
 */
public class SaferPeriodCalculatorFactory
{
	 /**
	 * Code for the absolute type, where saferPeriod=period+constant
	 */
	 public static final int ABSOLUTE = 1;
     /**
     * Code for the relative type, where saferPeriod=period*coefficient
     */
	 public static final int RELATIVE = 2;
	 
	 private static ISaferPeriodCalculator currentImpl = null;
	 
     private static final String MODE_DESCRIPTION_SEPARATOR = "/";
     private static String userDefinedDefaultMode = null;
	
	
     /**
      * Instantiates and return the ISaferPeriodCalculator singleton
      * @param typeOfImpl The type of implementation
      * @return The required implementation
      */
	 public static ISaferPeriodCalculator getImpl ( int typeOfImpl ) throws DevFailed
	 {
	     if ( SaferPeriodCalculatorFactory.userDefinedDefaultMode == null )
	     {
	         switch ( typeOfImpl )
		     {
		         case ABSOLUTE:
		           	 currentImpl = new AbsoluteSaferPeriodCalculator ( AbsoluteSaferPeriodCalculator.DEFAULT_AMOUNT , AbsoluteSaferPeriodCalculator.DEFAULT_TYPE );
		         break;
		
		         case RELATIVE:
		             currentImpl = new RelativeSaferPeriodCalculator ( RelativeSaferPeriodCalculator.DEFAULT_MULTIPLIER );
		         break;
		         
		         default:
		             Tools.throwDevFailed ( new IllegalArgumentException( "Expected either DUMMY_TYPE (1) or SLOW_BASIC_TYPE (2) or FAST_BASIC_TYPE (3) or FAST_BASIC_TYPE_2 (4) or DEBUG_TYPE(5) or FAST_BASIC_TYPE_3(6), got " + typeOfImpl + " instead." ) );
		     }
	     }
	     else
	     {
	         currentImpl = SaferPeriodCalculatorFactory.getImpl ( SaferPeriodCalculatorFactory.userDefinedDefaultMode );
	     }
	
	     return currentImpl;
	 }
	 
     
	 /**
	 * Instantiates and return the ISaferPeriodCalculator singleton of the Absolute variety
     * @param amount The integer amount of time units to add to the period
	 * @param type The type of time units to add to the period; must be either ADD_SECONDS, ADD_MINUTES, or ADD_HOURS  
	 * @return The required implementation
	 * @throws DevFailed
	 */
	public static AbsoluteSaferPeriodCalculator getAbsoluteImpl ( int amount , int type ) throws DevFailed
	 {
	     currentImpl = new AbsoluteSaferPeriodCalculator ( amount , type );
	     return (AbsoluteSaferPeriodCalculator) currentImpl;
	 }
	 
	 /**
     * Instantiates and return the ISaferPeriodCalculator singleton of the Relative variety
	 * @param multiplier The multiplier by which to multiply the period
	 * @return The required implementation
	 * @throws DevFailed
	 */
	public static RelativeSaferPeriodCalculator getRelativeImpl ( double multiplier ) throws DevFailed
	 {
	     currentImpl = new RelativeSaferPeriodCalculator ( multiplier );
	     return (RelativeSaferPeriodCalculator) currentImpl;
	 }

     /**
      * Returns the current implementation singleton
      * @return The current implementation singleton
      */
	 public static ISaferPeriodCalculator getCurrentImpl ()
	 {
	     return currentImpl;
	 }

     /**
      * Instantiates and return the ISaferPeriodCalculator singleton
      * @param modeDescription The description of the type of implementation; can be either of two types:
      * <UL>
      *     <LI> absolute/[seconds|minutes|hours]/[integer amount]; ex:absolute/minutes/10 means that saferPeriod=period+10 minutes  
      *     <LI> relative/[float amount] ex: relative/2.5 means that saferPeriod=period*2.5 
      * </UL>
      * @return The required implementation
      */
    public static ISaferPeriodCalculator getImpl ( String modeDescription ) throws DevFailed
    {
        if ( modeDescription == null )
        {
            return SaferPeriodCalculatorFactory.getImpl ( SaferPeriodCalculatorFactory.ABSOLUTE );
        }
        
        StringTokenizer st = new StringTokenizer ( modeDescription , SaferPeriodCalculatorFactory.MODE_DESCRIPTION_SEPARATOR );
        try
        {
            int type = -1;
            String type_s = st.nextToken ();
            
            if ( type_s.equals ( AbsoluteSaferPeriodCalculator.MODE_LABEL ) )
            {
                type = ABSOLUTE;
            }
            else if ( type_s.equals ( RelativeSaferPeriodCalculator.MODE_LABEL ) )
            {
                type = RELATIVE;
            }
         
            switch ( type )
            {
	            case ABSOLUTE:
	                try
	                {
		                String typeOfAddition_s = st.nextToken ();
		                String amount_s = st.nextToken ();
		                int typeOfAddition = AbsoluteSaferPeriodCalculator.getTypeOfAddition ( typeOfAddition_s );
		                int amount = Integer.parseInt ( amount_s );
		                
		                return getAbsoluteImpl ( amount , typeOfAddition );
	                }
	                catch ( Throwable t )
	                { 
	                    throwIllegalArgumentException ( "Illegal mode description: " + modeDescription );
	                }
		        case RELATIVE:
		            try
	                {
			            String multiplier_s = st.nextToken ();
			            double multiplier = Double.parseDouble ( multiplier_s );
			            
			            return getRelativeImpl ( multiplier );
	                }
	                catch ( Throwable t )
	                { 
	                    throwIllegalArgumentException ( "Illegal mode description: " + modeDescription );
	                }
		        default:
		            throwIllegalArgumentException ( "Illegal mode description: " + modeDescription );
            }
        }
        catch ( Exception e )
        {
            throwIllegalArgumentException ( "Illegal mode description: " + modeDescription );
        }
        return null;
    }
    
    static void throwIllegalArgumentException ( String invalidValue ) throws DevFailed
    {
        IllegalArgumentException iae = new IllegalArgumentException ( "Illegal mode description: " + invalidValue );
        Tools.throwDevFailed ( iae );
    }
    /**
     * Returns the default safety period calculation description.
     * @return Returns the userDefinedDefaultMode.
     */
    public static String getUserDefinedDefaultMode() {
        return userDefinedDefaultMode;
    }
    /**
     * Sets the default safety period calculation description.
     * @param userDefinedDefaultMode The userDefinedDefaultMode to set.
     */
    public static void setUserDefinedDefaultMode(String userDefinedDefaultMode) {
        SaferPeriodCalculatorFactory.userDefinedDefaultMode = userDefinedDefaultMode;
    }
}
