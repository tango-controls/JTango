package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.ErrSeverity;
import fr.esrf.Tango.DevFailed;

//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  ArchivingException.
//               This class defines the exceptions used in the tango archiving service.
//						(chinkumo) - 10 mai 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.7  2006/09/26 15:53:12  ounsy
// corrected a bug in addStack methods
//
// Revision 1.6  2006/09/26 15:05:55  ounsy
// added a isDueToATimeOut attribute
//
// Revision 1.5  2006/06/07 14:25:21  ounsy
// corrected a bug in the toString method
//
// Revision 1.4  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.3.12.2  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.3.12.1  2005/09/26 08:36:46  chinkumo
// Method added to check if the object is null.
//
// Revision 1.3  2005/06/14 14:04:02  chinkumo
// no message
//
// Revision 1.1  2005/06/13 09:35:19  chinkumo
// The ArchivingException object defines exceptions for the Archiving Service.
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================

public class ArchivingException extends Exception
{
	private String archExcepMessage;
	private DevError[] devErrorTab;
    private static final String TIMEOUT = "org.omg.CORBA.TIMEOUT";
    private boolean isDueToATimeOut = false;

	public ArchivingException()
	{
		super();
		archExcepMessage = "";
		devErrorTab = null;
	}

	/**
	 * @param message deprecated
	 */
	public ArchivingException(String message)
	{
		super(message);
		archExcepMessage = message;
		String reason = "Unknown reason";
		ErrSeverity archSeverity = ErrSeverity.WARN;
		String desc = "Unknown exception";
		String origin = this.getClass().toString();
		DevError devError = new DevError(reason , archSeverity , desc , origin);
		devErrorTab = new DevError[ 1 ];
		devErrorTab[ 0 ] = devError;
	}

	/**
	 * This class can be instanciated when exceptions in the archiving service.
	 * Exceptions can be :
	 * ConnectionException, ATKException
	 */
	public ArchivingException(String message , String reason , ErrSeverity archSeverity , String desc , String origin)
	{
		super(message);
		archExcepMessage = message;
		String _reason = ( reason.equals(null) || reason.equals("") ) ? "Unknown reason" : reason;
		ErrSeverity _archSeverity = ( archSeverity == null ) ? ErrSeverity.WARN : archSeverity;
		String _desc = ( desc.equals(null) || desc.equals("") ) ? "Unknown exception" : desc;
		String _origin = ( origin.equals(null) || origin.equals("") ) ? "Unknown origin" : origin;
		DevError devError = new DevError(_reason , _archSeverity , _desc , _origin);
		devErrorTab = new DevError[ 1 ];
		devErrorTab[ 0 ] = devError;
	}

	public ArchivingException(String message , String reason , ErrSeverity archSeverity , String desc , String origin , Exception e)
	{
		super(e);
		archExcepMessage = message;

		if ( e instanceof DevFailed )
		{
			// The current stack is initialized
			devErrorTab = new DevError[ ( ( DevFailed ) e ).errors.length + 1 ];
			// The stack of the catched error copied
			System.arraycopy(( ( DevFailed ) e ).errors , 0 , devErrorTab , 0 , ( ( DevFailed ) e ).errors.length);
			// A new DevError object is built with the given parameters
			String _reason = ( reason.equals(null) || reason.equals("") ) ? "Unknown reason" : reason;
			ErrSeverity _archSeverity = ( archSeverity == null ) ? ErrSeverity.WARN : archSeverity;
			String _desc = ( desc.equals(null) || desc.equals("") ) ? "DevFailed EXCEPTION" : desc;
			String _origin = ( origin.equals(null) || origin.equals("") ) ? e.getClass().getName() : origin;
			DevError devError = new DevError(_reason , _archSeverity , _desc , _origin);
			// The DevError object is added at the end of the stack
			devErrorTab[ devErrorTab.length - 1 ] = devError;
		}
		if ( e instanceof ArchivingException )
		{
			// The current stack is initialized
			devErrorTab = new DevError[ ( ( ArchivingException ) e ).devErrorTab.length + 1 ];
			// The stack of the catched error copied
			System.arraycopy(( ( ArchivingException ) e ).devErrorTab , 0 , devErrorTab , 0 , ( ( ArchivingException ) e ).devErrorTab.length);
			// A new DevError object is built with the given parameters
			String _reason = ( reason.equals(null) || reason.equals("") ) ? "Unknown reason" : reason;
			ErrSeverity _archSeverity = ( archSeverity == null ) ? ErrSeverity.WARN : archSeverity;
			String _desc = ( desc.equals(null) || desc.equals("") ) ? "Unknown exception" : desc;
			String _origin = ( origin.equals(null) || origin.equals("") ) ? e.getClass().getName() : origin;
			DevError devError = new DevError(_reason , _archSeverity , _desc , _origin);

			// The DevError object is added at the end of the stack
			devErrorTab[ devErrorTab.length - 1 ] = devError;
		}
		else
		{
			// The current stack is initialized
			devErrorTab = new DevError[ 2 ];
			// A new DevError object is built for the catched exception (Original exception)
			String reason_original = e.getMessage();
			ErrSeverity archSeverity_original = ErrSeverity.WARN;
			String desc_original = e.getLocalizedMessage();
			String origin_original = e.getClass().getName();
			DevError devError_original = new DevError(reason_original , archSeverity_original , desc_original , origin_original);
			// A new DevError object is built with the given parameters
			String _reason = ( reason.equals(null) || reason.equals("") ) ? "Unknown reason" : reason;
			ErrSeverity _archSeverity = ( ( archSeverity == null ) ? ErrSeverity.WARN : archSeverity );
			String _desc = ( desc.equals(null) || desc.equals("") ) ? "Unknown exception" : desc;
			String _origin = ( origin.equals(null) || origin.equals("") ) ? e.getClass().getName() : origin;
			DevError devError = new DevError(_reason , _archSeverity , _desc , _origin);

			// The DevError objects are added at the end of the stack
			devErrorTab[ 0 ] = devError_original;
			devErrorTab[ 1 ] = devError;
		}
	}

	public void addStack(String message , ArchivingException e)
	{
        if ( e.isDueToATimeOut () )
        {
            this.setDueToATimeOut ( true );
        }
        
        archExcepMessage = message;

		// The current stack is cloned
		DevError[] devErrorTabClone = devErrorTab;

        if ( e.devErrorTab != null )
        {
            if ( devErrorTabClone != null )
            {
                // The current stack is re-initialized
                devErrorTab = new DevError[ devErrorTabClone.length + e.devErrorTab.length ];
                // The cloned is copied again
                System.arraycopy(devErrorTabClone , 0 , devErrorTab , 0 , devErrorTabClone.length);
                // The stack of the catched error copied
                System.arraycopy(e.devErrorTab , 0 , devErrorTab , devErrorTabClone.length , e.devErrorTab.length);
            }
            else
            {
                // The current stack is re-initialized
                devErrorTab = new DevError[ e.devErrorTab.length ];
                // The stack of the catched error copied
                System.arraycopy(e.devErrorTab , 0 , devErrorTab , 0 , e.devErrorTab.length);
            }    
        }
	}

	public void addStack(String message , String reason , ErrSeverity archSeverity , String desc , String origin , ArchivingException e)
	{
		if ( e.isDueToATimeOut () )
        {
		    this.setDueToATimeOut ( true );
        }
            
        archExcepMessage = message;
		// new ArchivingException
		String _reason = ( reason.equals(null) || reason.equals("") ) ? "Unknown reason" : reason;
		ErrSeverity _archSeverity = ( ( archSeverity == null ) ? ErrSeverity.WARN : archSeverity );
		String _desc = ( desc.equals(null) || desc.equals("") ) ? "Unknown exception" : desc;
		String _origin = ( origin.equals(null) || origin.equals("") ) ? e.getClass().getName() : origin;
		DevError _devError = new DevError(_reason , _archSeverity , _desc , _origin);
		// The current stack is cloned
		DevError[] devErrorTabClone = devErrorTab;

        if ( e.devErrorTab != null )
        {
            if ( devErrorTabClone != null )
            {
                // The current stack is re-initialized
                int devErrorTabLength = e.devErrorTab == null ? 0 : e.devErrorTab.length;
                devErrorTab = new DevError[ devErrorTabClone.length + devErrorTabLength + 1 ];
                //devErrorTab = new DevError[ devErrorTabClone.length + e.devErrorTab.length + 1 ];
                
                // The cloned is copied again
                System.arraycopy(devErrorTabClone , 0 , devErrorTab , 0 , devErrorTabClone.length);
                // The stack of the catched error copied
                System.arraycopy(e.devErrorTab , 0 , devErrorTab , devErrorTabClone.length , e.devErrorTab.length);
                // The DevError builded with the given parameters is added at the end of the stack
                devErrorTab[ devErrorTabClone.length + e.devErrorTab.length ] = _devError;
            }
            else
            {
                // The current stack is re-initialized
                devErrorTab = new DevError[ e.devErrorTab.length + 1 ];
                // The stack of the catched error copied
                System.arraycopy(e.devErrorTab , 0 , devErrorTab , 0 , e.devErrorTab.length);
                // The DevError builded with the given parameters is added at the end of the stack
                devErrorTab[ e.devErrorTab.length ] = _devError;
            }
        }
	}

	public String getMessage()
	{
		return archExcepMessage;
	}

	public String getLastExceptionMessage()
	{
		return devErrorTab[ 0 ].desc;
	}

	public String toString()
	{
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Message : ").append(archExcepMessage).append("\r\n");
		if ( devErrorTab != null )
        {
            for ( int i = 0 ; i < devErrorTab.length ; i++ )
    		{
    			DevError devError = devErrorTab[ i ];
    			stringBuffer.append("\t [").append(i + 1).append("] : ").append("\r\n");
    			stringBuffer.append("\t").append("\t").append("Reason : ").append(devError.reason).append("\r\n");
    			stringBuffer.append("\t").append("\t").append("Severity : ").append(errorSeverityToString(devError.severity)).append("\r\n");
    			stringBuffer.append("\t").append("\t").append("Description : ").append(devError.desc).append("\r\n");
    			stringBuffer.append("\t").append("\t").append("Origin : ").append(devError.origin).append("\r\n");
    		}
        }
		return stringBuffer.toString();
	}

	private String errorSeverityToString(ErrSeverity errSeverity)
	{
		if ( errSeverity == null )
			return "WARNING";
		switch ( errSeverity.value() )
		{
			case ErrSeverity._ERR:
				return "ERROR";
			case ErrSeverity._PANIC:
				return "PANIC";
			default :
				return "WARNING";
		}

	}

	public boolean isNull()
	{
		return ( devErrorTab == null || devErrorTab.length == 0 );
	}

	public DevFailed toTangoException()
	{
		DevFailed devFailed = new DevFailed(archExcepMessage , devErrorTab);
		return devFailed;
	}
    
    public boolean computeIsDueToATimeOut ()
    {
        Throwable cause = this.getCause ();
        if ( cause instanceof DevFailed )
        {
            DevFailed devFailedCause = (DevFailed) cause;
            DevError[] errs = devFailedCause.errors;
            if ( errs != null )
            {
                for ( int i = 0 ; i < errs.length ; i++ )
                {
                    DevError nextErr = errs [ i ];
                    /*String desc = nextErr.desc;
                    String origin = nextErr.origin;*/
                    String reason = nextErr.reason;
                    
                    /*System.out.println("CLA/DevFailed/desc/"+desc+"/");
                    System.out.println("CLA/DevFailed/origin/"+origin+"/");
                    System.out.println("CLA/DevFailed/reason/"+reason+"/");*/
                    
                    if ( reason.indexOf ( TIMEOUT ) != -1 )
                    {
                        this.setDueToATimeOut ( true );
                        return true; 
                    }
                }
            }
        }
        
        this.setDueToATimeOut ( false );
        return false;    
    }

    /**
     * @return Returns the isDueToATimeOut.
     */
    public boolean isDueToATimeOut() {
        return this.isDueToATimeOut;
    }

    /**
     * @param isDueToATimeOut The isDueToATimeOut to set.
     */
    public void setDueToATimeOut(boolean isDueToATimeOut) {
        this.isDueToATimeOut = isDueToATimeOut;
    }
    
}
