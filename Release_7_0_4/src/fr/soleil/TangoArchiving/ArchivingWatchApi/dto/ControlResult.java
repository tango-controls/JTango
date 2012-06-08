/*	Synchrotron Soleil 
 *  
 *   File          :  ControlResult.java
 *  
 *   Project       :  archiving_watcher
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  28 nov. 2005 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: ControlResult.java,v 
 *
 */
 /*
 * Created on 28 nov. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.dto;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.DBReaderFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.IDBReader;
import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warnable;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.comparators.ArchiversComparator;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.comparators.ArchivingAttributeSubNamesComparator;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.comparators.ControlResultLineComparator;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.comparators.DomainsComparator;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.choosing.ChoosingStrategyFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.choosing.IChoosingStrategy;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.modes.IModeController;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * Models the result of a control step or cycle, and contains all the necessary data
 * to display a report and a status code.
 * @author CLAISSE 
 */
public class ControlResult 
{
    private int code = ControlResult.NOT_READY;
    private Hashtable lines;
    private boolean isBuilt = false;
    
    private Hashtable errorArchivers;
    private Hashtable errorDomains;
    private Hashtable errorAttributes;
    private Hashtable errorSubAttributes;
    
    private Hashtable okAttributes;
    
    private long totalTime;
    private long timeReadingArchivingAttributes;
    private long timeApplyingChoosingStrategy;
    private long timeControlling;
    
    private Timestamp startDate;
    private Timestamp endDate;
    
    private int numberOfArchivingAttributes;
    private int numberOfControlledAttributes;
    private int numberOfKoAttributes;
    private int numberOfUndeterminedAttributes;
    private int numberOfAttributesControlledWithSuccess;
    
    private boolean isFullCycle;
    private int cycles;
    private int steps;
    
    private ControlResultLineComparator linesComparator;
    private ArchiversComparator archiversComparator;
    private DomainsComparator domainsComparator;
    private ArchivingAttributeSubNamesComparator archivingAttributeSubNamesComparator;
    
        
    /**
     * Status code for when the ControlResult is empty or not computed yet. 
     */
    public static final int NOT_READY = -1;
    
    /**
     * Status code for when all attributes are OK
     */
    public static final int ALL_OK = 10;
    /**
     * Status code for when there are no attributes to control
     */
    public static final int NO_ATTRIBUTES_TO_CONTROL = 11;
    
    /**
     * Status code for when all attributes are KO
     */
    public static final int ALL_KO = 20;
    /**
     * Status code for when some, but not all attributes are KO
     */
    public static final int PARTIAL_KO = 21;
    /**
     * Status code for when all attributes are Undetermined (eg. can happen if the cycle or step failed to load the list of archiving attributes)
     */
    public static final int ALL_UNDETERMINED = 22;
    
    /**
     * The label for an empty report
     */
    public static final String EMPTY_REPORT = "NO REPORT AVAILABLE YET.";
    
    /**
     * Default constructor. 
     */
    public ControlResult () 
    {
        super();
        this.lines = new Hashtable ();
        
        this.linesComparator = new ControlResultLineComparator ();
        this.archiversComparator = new ArchiversComparator ();
        this.domainsComparator = new DomainsComparator ();
        this.archivingAttributeSubNamesComparator = new ArchivingAttributeSubNamesComparator ();
        
        this.errorArchivers = new Hashtable ();
        this.errorDomains = new Hashtable ();
        this.errorSubAttributes = new Hashtable ();
        this.errorAttributes = new Hashtable ();
        this.okAttributes = new Hashtable ();
        
        isBuilt = false;
        
        Timestamp _startDate = new Timestamp ( System.currentTimeMillis () );
        this.setStartDate ( _startDate );
    }
    
    public void setAllArchivingAttributes ( Hashtable list ) 
    {
        if ( list == null )
        {
            return;
        }
        Enumeration en = list.keys ();
        while ( en.hasMoreElements () )
        {
            String next = (String) en.nextElement ();
            this.lines.put ( next , new ControlResultLine ( next ) );
        }
        
    }

    /**
     * Sets the CR's OK and KO attributes
     * @param listOfControlledAttributes A Vector [] of size 2, which first element is the list of the KO attributes complete names, and which second element is the list of the OK attributes complete names.
     * @throws DevFailed
     */
    public void setLines ( Vector [] listOfControlledAttributes ) throws DevFailed
    {
        Vector koAttributes = listOfControlledAttributes [ 0 ];
        Vector okAttributes = listOfControlledAttributes [ 1 ];
        
        this.addList ( koAttributes , false );
        this.addList ( okAttributes , true );
        
        this.setNumberOfArchivingAttributes ( koAttributes.size() + okAttributes.size () );
        
        this.build ( false , false );
    }
    
    /**
     * @param controlledAttributes
     * @param b
     */
    private void addList(Vector controlledAttributes, boolean areOK) 
    {
        //System.out.println ( "CLA/ControlResult/addList/areOK/"+areOK );
        
        if ( controlledAttributes != null && controlledAttributes.size () != 0 )
        {
            int numberOfKOAttributes = controlledAttributes.size ();
            for ( int i = 0 ; i < numberOfKOAttributes ; i ++ )
            {
                String nextCompleteName = (String) controlledAttributes.elementAt ( i );
                //System.out.println ( "CLA/ControlResult/addList/nextCompleteName|"+nextCompleteName );
                
                //CLA 17/05/06--------
                //ModeData nextModeData = null;
                ModeData nextModeData = new ModeData ();
                nextModeData.setIncomplete ( true );
                //--------
                
                ArchivingAttribute attr = new ArchivingAttribute ();
                attr.setCompleteName ( nextCompleteName );
                int [] result = this.getResult ( areOK );
                
                ControlResultLine line = new ControlResultLine ( attr , nextModeData , result );
                this.addLine ( line );
            }
        }        
    }

    /**
     * @return
     */
    private int[] getResult ( boolean isOK ) 
    {
        int [] result = new int [ IModeController.NUMBER_OF_MODES_TO_CONTROL ];
        
        if ( isOK )
        {
            result [ IModeController.MODE_P_POSITION ] = IModeController.CONTROL_OK;    
        }
        else
        {
            result [ IModeController.MODE_P_POSITION ] = IModeController.CONTROL_KO;
        }
        result [ IModeController.MODE_A_POSITION ] = IModeController.CONTROL_NOT_DONE;
        result [ IModeController.MODE_R_POSITION ] = IModeController.CONTROL_NOT_DONE;
        result [ IModeController.MODE_T_POSITION ] = IModeController.CONTROL_NOT_DONE;
        result [ IModeController.MODE_D_POSITION ] = IModeController.CONTROL_NOT_DONE;
        result [ IModeController.MODE_C_POSITION ] = IModeController.CONTROL_NOT_DONE;
        result [ IModeController.MODE_E_POSITION ] = IModeController.CONTROL_NOT_DONE;
        
        return result;
    }

    /**
     * Returns true if and only if the CR is complete (no attribute missing)
     * @return True if and only if the CR is complete (no attribute missing)
     */
    public boolean isComplete ()
    {
        return this.getCurrentSize () >= this.getFinalSize (); 
    }
    
    /**
     * Returns true if and only if the CR is empty (no attribute)
     * @return True if and only if the CR is empty (no attribute)
     */
    public boolean isEmpty ()
    {
        return this.getCurrentSize () == 0; 
    }
    
    /**
     * Returns the CR's current size (number of attributes)
     * @return
     */
    public int getCurrentSize ()
    {
        int size = lines == null ? 0 : lines.size ();
        return size; 
    }
    
    /**
     * Returns the CR's final size (all attributes accounted for)
     * @return
     */
    public int getFinalSize ()
    {
        IChoosingStrategy choosingStrategy = ChoosingStrategyFactory.getCurrentImpl ();
        int size = choosingStrategy.getNumberOfAttributesToControl();
        return size;
    }
    
    
    /**
     * Builds and returns the associated report
     * @return The report
     * @throws DevFailed
     */
    public synchronized String getReport () throws DevFailed
    {
        StringBuffer buff = new StringBuffer ();
     
        buff.append ( this.getReportHeader () );
        buff.append (Tools.CRLF );
     
        buff.append ( this.getAttributesReport () );
        buff.append ( Tools.CRLF );
     
        buff.append ( this.getArchiversReport () );
        buff.append ( Tools.CRLF );
     
        buff.append ( this.getDomainsReport () );
        buff.append ( Tools.CRLF );
     
        //buff.append ( this.getAttributeSubNameReport () );
     
        String ret = buff.toString (); 
     
        return ret;
    }
    
    /**
     * @return
     */
    private String getArchiversReport() 
    {
        StringBuffer buff = new StringBuffer ();
        
        if ( this.errorArchivers != null && this.errorArchivers.size () != 0 )
        {
            buff.append ( "**************************************ARCHIVERS IN CHARGE OF KO ATTRIBUTES*****************************************" );
            buff.append ( Tools.CRLF );
        }
        
        List list = new Vector ();
        list.addAll ( this.errorArchivers.values () );
        Collections.sort(list, this.archiversComparator );
        Iterator it = list.iterator ();
        
        while ( it.hasNext () )
        {
            /*String nextArchiverName = (String) it.next ();
            Archiver nextArchiver = (Archiver) this.errorArchivers.get ( nextArchiverName );*/
            Archiver nextArchiver = (Archiver) it.next ();
            
            buff.append ( nextArchiver.getReport () );
            buff.append ( Tools.CRLF );
        }
        
        if ( this.errorArchivers != null && this.errorArchivers.size () != 0 )
        {
            buff.append ( Tools.CRLF );
            buff.append ( "**************************************************************************************************" );   
        }
        
        return buff.toString ();
    }
    
    private String getAttributeSubNameReport() 
    {
        StringBuffer buff = new StringBuffer ();
        
        if ( this.errorSubAttributes != null && this.errorSubAttributes.size () != 0 )
        {
            buff.append ( "**************************************ATTRIBUTE NAMES WITH AT LEAST 1 KO ATTRIBUTE*****************************************" );
            buff.append ( Tools.CRLF );
        }
        
        List list = new Vector ();
        list.addAll ( this.errorSubAttributes.values () );
        Collections.sort(list, this.archivingAttributeSubNamesComparator );
        Iterator it = list.iterator ();
        
        while ( it.hasNext () )
        {
            ArchivingAttributeSubName nextArchivingAttributeSubName = (ArchivingAttributeSubName) it.next ();
            
            buff.append ( nextArchivingAttributeSubName.getReport () );
            buff.append ( Tools.CRLF );
        }
        
        if ( this.errorSubAttributes != null && this.errorSubAttributes.size () != 0 )
        {
            buff.append ( Tools.CRLF );
            buff.append (  "**************************************************************************************************" );   
        }
        
        return buff.toString ();
    }
    
    private String getDomainsReport() 
    {
        StringBuffer buff = new StringBuffer ();
        
        if ( this.errorDomains != null && this.errorDomains.size () != 0 )
        {
            buff.append ( "**************************************DOMAINS WITH AT LEAST 1 KO ATTRIBUTE*****************************************" );
            buff.append ( Tools.CRLF );
        }
        
        List list = new Vector ();
        list.addAll ( this.errorDomains.values () );
        Collections.sort(list, this.domainsComparator );
        Iterator it = list.iterator ();
        
        while ( it.hasNext () )
        {
            Domain nextDomain = (Domain) it.next ();
            
            buff.append ( nextDomain.getReport () );
            buff.append ( Tools.CRLF );
        }
        
        if ( this.errorDomains != null && this.errorDomains.size () != 0 )
        {
            buff.append ( Tools.CRLF );
            buff.append (  "**************************************************************************************************" );   
        }
        
        return buff.toString ();
    }

    /**
     * @return
     */
    private String getAttributesReport() 
    {
        StringBuffer bufferKO = new StringBuffer ();
        StringBuffer bufferUndetermined = new StringBuffer ();
        StringBuffer bufferValueCertainlyNull = new StringBuffer ();
        StringBuffer bufferValuePossiblyNull = new StringBuffer ();
        
        StringBuffer buff = new StringBuffer ();
        boolean hasCasesOfValueCertainlyNull = false;
        boolean hasCasesOfValuePossiblyNull = false;
        
        List list = new Vector ();
        list.addAll ( this.lines.values () );
        Collections.sort(list, this.linesComparator );
        Iterator it = list.iterator ();
        
        while ( it.hasNext () )
        {
            ControlResultLine nextLine = (ControlResultLine) it.next ();
            
            if ( ! nextLine.isArchivingOK () )
            {
                //System.out.println ( "CLA/getAttributesReport/nextCompleteName|"+nextCompleteName+"|nextLine.getAttribute ().isDetermined ()|"+nextLine.getAttribute ().isDetermined () );
                if ( nextLine.getAttribute ().isDetermined () )
                {
                    bufferKO.append ( nextLine.getReport () );
                    bufferKO.append ( Tools.CRLF );    
                }
                else
                {
                    bufferUndetermined.append ( nextLine.getReport () );
                    bufferUndetermined.append ( Tools.CRLF );    
                }
            }
            if ( nextLine.isValueCertainlyNull () )
            {
                hasCasesOfValueCertainlyNull = true;
                
                bufferValueCertainlyNull.append ( nextLine.getReport () );
                bufferValueCertainlyNull.append ( Tools.CRLF );    
            }
            if ( nextLine.isValuePossiblyNull () )
            {
                hasCasesOfValuePossiblyNull = true;
                
                bufferValuePossiblyNull.append ( nextLine.getReport () );
                bufferValuePossiblyNull.append ( Tools.CRLF );    
            }
        }
        
        if ( hasCasesOfValueCertainlyNull )
        {
            buff.append ( Tools.CRLF );
            buff.append ( "******************* OK Attributes whose last values have been null (archiving works but there might be a problem with the archived device) ***********************" );
            buff.append ( Tools.CRLF );
            buff.append ( bufferValueCertainlyNull );
        }
        if ( hasCasesOfValuePossiblyNull )
        {
            buff.append ( Tools.CRLF );
            buff.append ( "******************* OK Attributes whose last values might have been null, but it couldn't be determined ***********************" );
            buff.append ( Tools.CRLF );
            buff.append ( bufferValuePossiblyNull );
        }
        if ( this.getNumberOfUndeterminedAttributes () > 0 )
        {
            buff.append ( Tools.CRLF );
            buff.append ( "***************************************** Undetermined Attributes **********************************" );
            buff.append ( Tools.CRLF );
            buff.append ( bufferUndetermined );
        }
        if ( this.getNumberOfKoAttributes () > 0  )
        {
            buff.append ( Tools.CRLF );
            buff.append ( "***************************************** KO Attributes **********************************" );
            buff.append ( Tools.CRLF );
            buff.append ( bufferKO );
        }
        
        return buff.toString ();
    }

    public String getReportHeader() throws DevFailed 
    {
        StringBuffer buff = new StringBuffer ();
        
        buff.append ( "************************************** General *****************************************" );
        buff.append ( Tools.CRLF );
        
        String endDate = this.getEndDate () == null ? "NOT YET" : this.getEndDate () + "";
        buff.append ( "Cycle started at: " + this.getStartDate () + ". Current time: " + new Timestamp ( System.currentTimeMillis () ) + ". Cycle completed at: " + endDate );
        buff.append ( Tools.CRLF );
        
        buff.append ( "Completed cycles: " + this.getCycles () + ". Completed steps: " + this.getSteps () );
        buff.append ( Tools.CRLF );
        
        buff.append ( "Global result: " + ControlResult.getAssociatedLabel ( this.getCode () ) );
        buff.append ( Tools.CRLF );
        
        buff.append ( "Attributes recorded in DB when the current fixed cycle started: " + this.getNumberOfArchivingAttributes () );
        buff.append ( Tools.CRLF );
        
        buff.append ( "Control attempts in the current rollover cycle: " + this.getNumberOfControlledAttributes () );
        buff.append ( Tools.CRLF );
        
        buff.append ( "Successful control attempts in the current rollover cycle: " + this.getNumberOfAttributesControlledWithSuccess () );
        buff.append ( Tools.CRLF );
        
        buff.append ( "Number of KO attributes: " + this.getNumberOfKoAttributes () );
        buff.append ( Tools.CRLF );
        
        buff.append ( "Number of undetermined attributes: " + this.getNumberOfUndeterminedAttributes () );
        
        return buff.toString ();
    }

    /**
     * Tests whether a given attribute is correctly archived
     * @param completeName The attribute's complete name
     * @return Whether the attribute is correctly archived
     */
    public synchronized boolean isAttributeCorrectlyArchived ( String completeName )
    {
        ControlResultLine nextLine = (ControlResultLine) this.lines.get ( completeName );    
        int attributeStatus = nextLine.getAttribute ().getArchivingStatus ();
        
        boolean ret = ( attributeStatus == IModeController.CONTROL_OK );
        ret = ret || ( attributeStatus == IModeController.CONTROL_OK_BUT_VALUE_MIGHT_BE_NULL );
        ret = ret || ( attributeStatus == IModeController.CONTROL_OK_BUT_VALUE_IS_NULL );
        return ret;
    }
    
    private static String getAssociatedLabel ( int _code ) throws DevFailed
    {
        switch ( _code )
        {
        	case ControlResult.NOT_READY:
        	    return "NOT_READY";
        
        	case ControlResult.ALL_OK:
        	    return "ALL_OK";
        	
        	case ControlResult.ALL_KO:
        	    return "ALL_KO";
        	
        	case ControlResult.PARTIAL_KO:
        	    return "PARTIAL_KO";
        	
        	case ControlResult.NO_ATTRIBUTES_TO_CONTROL:
        	    return "NO_ATTRIBUTES_TO_CONTROL";
                
            case ControlResult.ALL_UNDETERMINED:
                return "ALL_UNDETERMINED";
        	
        	default:
        	    Tools.throwDevFailed ( new IllegalArgumentException( "Expected either of " + NOT_READY + "," + ALL_OK + "," + ALL_KO + "," + PARTIAL_KO + "," + NO_ATTRIBUTES_TO_CONTROL + " as a parameter. Received " + _code + " instead." ) );
        }
        return null;
    }
    
    /**
     * @return Returns the code.
     */
    public int getCode() {
        return code;
    }
    /**
     * @param code The code to set.
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Adds a line to the current CR
     * @param line The line to add
     */
    public void addLine ( ControlResultLine line ) 
    {
        ArchivingAttribute attr = line.getAttribute ();
        String completeName = attr.getCompleteName ();
        //System.out.println ( "ControlResult/addLine/completeName/"+completeName );
        this.lines.put ( completeName , line );
    }
    
    /**
     * Computes the status code and builds the report, from the accumulated individual attributes data.  
     * @param doArchiverDiagnosis True if one desires informations on archiver that have KO attributes. Makes the process a bit more resource-intensive.
     * @throws DevFailed
     */
    public void build ( boolean doArchiverDiagnosis , boolean _isFullCycle ) throws DevFailed
    {
        //Tools.trace ( "ControlResult/build/START" , Warnable.LOG_LEVEL_DEBUG );
        
        //System.out.println ( "CLA/ControlResult/build/isFullCycle/"+_isFullCycle );
        this.isFullCycle = _isFullCycle;
        
        int _numberOfControlledAttributes = this.lines.size ();

        this.buildLines ( this.lines );
        
        this.setNumberOfControlledAttributes ( _numberOfControlledAttributes );
        this.setNumberOfAttributesControlledWithSuccess ( _numberOfControlledAttributes - this.getNumberOfUndeterminedAttributes() );
        
        //int _code = this.buildGlobalCode ( _numberOfControlledAttributes , this.getNumberOfKoAttributes() );
        int _code = this.buildGlobalCode ( _numberOfControlledAttributes , this.getNumberOfAttributesControlledWithSuccess () , this.getNumberOfKoAttributes() );
        this.setCode ( _code );
        
        //Tools.trace ( "ControlResult/build/_numberOfControlledAttributes/"+_numberOfControlledAttributes+"/_numberOfKoAttributes/"+this.getNumberOfKoAttributes()+"/_code/"+_code+"/" , Warnable.LOG_LEVEL_DEBUG );
        
        Hashtable _errorArchivers;
        try
        {
            _errorArchivers = this.buildArchivers ( this.errorArchivers , doArchiverDiagnosis );
        }
        catch ( Exception e )
        {
            //Tools.throwDevFailed ( e );
            Tools.trace ( "ControlResult/failed to build the archivers report!" , e , Warnable.LOG_LEVEL_ERROR );
            _errorArchivers = new Hashtable ();
        }
        this.setErrorArchivers ( _errorArchivers );
        
        Hashtable _errorDomains = this.buildDomains ( this.errorDomains );
        this.setErrorDomains ( _errorDomains );
        
        Hashtable _errorSubAttributes = this.buildSubAttributes ( this.errorSubAttributes );
        this.setErrorSubAttributes ( _errorSubAttributes );
        
        this.isBuilt = true;
    }
    
    private Hashtable buildSubAttributes(Hashtable _errorSubAttributes) 
    {
        Enumeration errorSubAttributesKeys = _errorSubAttributes.keys ();  
        while ( errorSubAttributesKeys.hasMoreElements () )
        {
            String nextKey = (String) errorSubAttributesKeys.nextElement ();
            
            ArchivingAttributeSubName attrSubName = new ArchivingAttributeSubName ();
            attrSubName.setName ( nextKey );
            attrSubName.buildKOAttributes ( this.errorAttributes );
            if ( ! attrSubName.hasKOAttributes () )
            {
                _errorSubAttributes.remove ( nextKey );
                continue;
            }
            
            _errorSubAttributes.put ( nextKey , attrSubName );
        }
        return _errorSubAttributes;
    }
    
    private Hashtable buildDomains(Hashtable _errorDomains) 
    {
        Enumeration errorDomainsKeys = _errorDomains.keys ();  
        while ( errorDomainsKeys.hasMoreElements () )
        {
            String nextKey = (String) errorDomainsKeys.nextElement ();
            Domain domain = new Domain ();
            domain.setName ( nextKey );
            domain.buildKOAttributes ( this.errorAttributes );
            if ( ! domain.hasKOAttributes () )
            {
                _errorDomains.remove ( nextKey );
                continue;
            }
            
            _errorDomains.put ( nextKey , domain );
        }
        return _errorDomains;
    }
   
    private void buildLines ( Hashtable _lines ) throws DevFailed 
    {
        Enumeration _enum = _lines.keys ();
        int _numberOfKoAttributes = 0;
        int _numberOfUndeterminedAttributes = 0;
        
        while ( _enum.hasMoreElements () )
        {
            String nextCompleteName = (String) _enum.nextElement ();
            ControlResultLine nextLine = (ControlResultLine) this.lines.get ( nextCompleteName );
            
            boolean tst = nextLine.build ();
            
            ArchivingAttribute attr = nextLine.getAttribute ();
            boolean isArchivingOK = attr.isArchivingOK ();
            boolean isDetermined = attr.isDetermined ();
            ModeData modeData = nextLine.getModeData ();
            
            if ( !isArchivingOK )
            {
                if ( modeData != null && ! modeData.isIncomplete () )
                {
                    this.errorArchivers.put ( modeData.getArchiver () , "" );
                }
                
                this.errorDomains.put ( attr.getDomain () , "" );
                this.errorSubAttributes.put ( attr.getAttributeSubName () , "" );
                this.errorAttributes.put ( attr.getCompleteName () , attr );
                
                if ( isDetermined )
                {
                    _numberOfKoAttributes ++;    
                }
                else
                {
                    _numberOfUndeterminedAttributes ++; 
                }
            }
            else
            {
                this.okAttributes.put ( attr.getCompleteName () , attr );
                this.errorAttributes.remove ( attr.getCompleteName () );
            }
        }
        
        this.setNumberOfKoAttributes ( _numberOfKoAttributes );
        this.setNumberOfUndeterminedAttributes ( _numberOfUndeterminedAttributes );
    }

    private Hashtable buildArchivers ( Hashtable _errorArchivers , boolean doArchiverDiagnosis ) //throws DevFailed, ArchivingException 
    {
        //Tools.trace ( "ControlResult/buildArchivers/START" , Warnable.LOG_LEVEL_DEBUG );
        
        Enumeration errorArchiversKeys = _errorArchivers.keys ();
        while ( errorArchiversKeys.hasMoreElements () )
        {
            String nextKey = (String) errorArchiversKeys.nextElement ();
            //Tools.trace ( "ControlResult/buildArchivers/nextKey|"+nextKey+"|" , Warnable.LOG_LEVEL_DEBUG );
            
            Archiver archiver = new Archiver ();
            archiver.setName ( nextKey );
            archiver.setDoDiagnosis ( doArchiverDiagnosis );
            archiver.buildKOAttributes ( this.errorAttributes );
            if ( ! archiver.hasKOAttributes () )
            {
                _errorArchivers.remove ( nextKey );
                continue;
            }
            
            if ( doArchiverDiagnosis )
            {
                IDBReader attributesReader = DBReaderFactory.getCurrentImpl ();  
                
                boolean isAlive = false;
                try
                {
                    isAlive = attributesReader.isAlive ( nextKey );
                }
                catch ( DevFailed e )
                {
                    Tools.trace ( "ControlResult/buildArchivers/failed calling isAlive for archiver|"+nextKey+"|" , e , Warnable.LOG_LEVEL_WARN );
                }
                archiver.setLiving ( isAlive );
                
                if ( isAlive )
                {
                    int scalarLoad = -1;
                    int spectrumLoad = -1;
                    int imageLoad = -1;
                    int totalLoad = -1;
                    boolean ok = true;
                    
                    try
                    {
                        scalarLoad = attributesReader.getArchiverLoad ( nextKey , IDBReader.LOAD_SCALAR );
                        ok = false;
                    }
                    catch ( DevFailed e )
                    {
                        Tools.trace ( "ControlResult/buildArchivers/failed calling getArchiverLoad (LOAD_SCALAR) for archiver|"+nextKey+"|" , e , Warnable.LOG_LEVEL_WARN );
                    }
                    
                    try
                    {
                        spectrumLoad = attributesReader.getArchiverLoad ( nextKey , IDBReader.LOAD_SPECTRUM );
                        ok = false;
                    }
                    catch ( DevFailed e )
                    {
                        Tools.trace ( "ControlResult/buildArchivers/failed calling getArchiverLoad (LOAD_SPECTRUM) for archiver|"+nextKey+"|" , e , Warnable.LOG_LEVEL_WARN );
                    }
                    
                    try
                    {
                        imageLoad = attributesReader.getArchiverLoad ( nextKey , IDBReader.LOAD_IMAGE );
                        ok = false;
                    }
                    catch ( DevFailed e )
                    {
                        Tools.trace ( "ControlResult/buildArchivers/failed calling getArchiverLoad (LOAD_IMAGE) for archiver|"+nextKey+"|" , e , Warnable.LOG_LEVEL_WARN );
                    }
    	            
    	            if ( ok )
                    {
                        totalLoad = scalarLoad + spectrumLoad + imageLoad; 
                    }
    	            
                    archiver.setScalarLoad ( scalarLoad );
    	            archiver.setSpectrumLoad ( spectrumLoad );
    	            archiver.setImageLoad ( imageLoad );
    	            archiver.setTotalLoad ( totalLoad );
    	            
                    String status = "";
                    try
                    {
                        status = attributesReader.getDeviceStatus ( nextKey );
                    }
                    catch ( DevFailed e )
                    {
                        Tools.trace ( "ControlResult/buildArchivers/failed calling getDeviceStatus for archiver|"+nextKey+"|" , e , Warnable.LOG_LEVEL_WARN );
                    }
                    
    	            archiver.setStatus ( status );
                }
            }
            
            _errorArchivers.put ( nextKey , archiver );
        }
        return _errorArchivers;    
    }

    /**
     * @param ofControlledAttributes
     * @param ofKoAttributes
     */
    private int buildGlobalCode(int _numberOfControlledAttributes, int _numberOfControlledAttributesWithSuccess, int _numberOfKoAttributes) 
    {
        if ( _numberOfControlledAttributes == 0 )
        {
            return ControlResult.NO_ATTRIBUTES_TO_CONTROL;
        }
        else
        {
            if ( _numberOfControlledAttributesWithSuccess == 0 )
            {
                return ControlResult.ALL_UNDETERMINED;
            }
            if ( _numberOfKoAttributes == 0 )
            {
                return ControlResult.ALL_OK;
            }
            else if ( _numberOfKoAttributes == _numberOfControlledAttributesWithSuccess )
            {
                return ControlResult.ALL_KO;
            }
            else
            {
                return ControlResult.PARTIAL_KO;
            }
        }    
    }
    
    /**
     * Tests whether the CR contains KO attributes. The CR must have been built first.
     * @return Whether the CR contains KO attributes.
     */
    public boolean hasErrors ()
    {
        if ( ! this.isBuilt )
        {
            throw new IllegalStateException ( "Build the ControlResult first." );
        }
        
        switch ( this.getCode() )
        {
        	case ControlResult.ALL_OK:
        		return false;
        	
        	case ControlResult.NO_ATTRIBUTES_TO_CONTROL:
        		return false;
        	
        	default:
        	    return true;
        }
    }

    /**
     * Returns the list of all archivers that have KO attributes
     * @return A Hashtable which keys are the KO archivers names and which values are the corresponding Archiver objects.
     */
    public synchronized Hashtable getErrorArchivers() {
        return errorArchivers;
    }
    /**
     * @param errorArchivers The errorArchivers to set.
     */
    private synchronized void setErrorArchivers(Hashtable errorArchivers) {
        this.errorArchivers = errorArchivers;
    }
    
    /**
     * Returns the list of all domains that have KO attributes
     * @return A Hashtable which keys are the KO domains names and which values are the corresponding Domain objects.
     */
    public synchronized Hashtable getErrorDomains() {
        return errorDomains;
    }
    /**
     * @param errorDomains The errorDomains to set.
     */
    private synchronized void setErrorDomains(Hashtable errorDomains) {
        this.errorDomains = errorDomains;
    }
    
    /**
     * @return Returns the numberOfArchivingAttributes.
     */
    public int getNumberOfArchivingAttributes() {
        return numberOfArchivingAttributes;
    }
    /**
     * @param numberOfArchivingAttributes The numberOfArchivingAttributes to set.
     */
    public void setNumberOfArchivingAttributes(int _numberOfArchivingAttributes) {
        this.numberOfArchivingAttributes = _numberOfArchivingAttributes;
    }
    /**
     * @return Returns the numberOfControlledAttributes.
     */
    public int getNumberOfControlledAttributes() {
        return numberOfControlledAttributes;
    }
    /**
     * @param numberOfControlledAttributes The numberOfControlledAttributes to set.
     */
    public void setNumberOfControlledAttributes(int numberOfControlledAttributes) {
        this.numberOfControlledAttributes = numberOfControlledAttributes;
    }
    /**
     * @return Returns the numberOfKoAttributes.
     */
    public int getNumberOfKoAttributes() {
        return numberOfKoAttributes;
    }
    /**
     * @param numberOfKoAttributes The numberOfKoAttributes to set.
     */
    public void setNumberOfKoAttributes(int numberOfKoAttributes) {
        this.numberOfKoAttributes = numberOfKoAttributes;
    }
    /**
     * Returns the date when the CR was completed 
     * @return Returns the endDate.
     */
    public Timestamp getEndDate() 
    {
        return endDate;
    }
    
    /**
     * Returns the date when the CR was started 
     * @return Returns the startDate.
     */
    public Timestamp getStartDate() 
    {
        return startDate;
    }
    
    /**
     * Adds the lines of another CR to this one
     * @param step 
     * @param controlResult the other CR
     */
    public void addLines ( ControlResult controlResult2 ) 
    {
        Hashtable lines2 = controlResult2.lines;
        Enumeration keys = lines2.keys ();
        while ( keys.hasMoreElements () )
        {
            String nextKey = (String) keys.nextElement ();
            ControlResultLine nextLine = (ControlResultLine) lines2.get ( nextKey );
            this.addLine ( nextLine );    
        }
    }

    /**
     * Clones a ControlResult object
     * @return A clone 
     */
    public synchronized ControlResult cloneControlResult() 
    {
        ControlResult ret = new ControlResult ();
        
        ret.lines = (Hashtable) this.lines.clone ();
        ret.code = this.code;
        
        Timestamp newDate;
        if ( this.startDate == null )
        {
            newDate = null;
        }
        else
        {
            newDate = new Timestamp ( this.startDate.getTime () );
        }
        ret.startDate = newDate;
        
        if ( this.endDate == null )
        {
            newDate = null;
        }
        else
        {
            newDate = new Timestamp ( this.endDate.getTime () );
        }
        ret.endDate = newDate;
        
        ret.errorArchivers = (Hashtable) this.errorArchivers.clone();
        ret.errorAttributes = (Hashtable) this.errorAttributes.clone();
        ret.errorDomains = (Hashtable) this.errorDomains.clone();
        ret.errorSubAttributes = (Hashtable) this.errorSubAttributes.clone();
        ret.okAttributes = (Hashtable) this.okAttributes.clone();
        
        ret.isBuilt = this.isBuilt;
        
        ret.numberOfArchivingAttributes = this.numberOfArchivingAttributes;
        ret.numberOfControlledAttributes = this.numberOfControlledAttributes;
        ret.numberOfKoAttributes = this.numberOfKoAttributes;
        ret.numberOfAttributesControlledWithSuccess = this.numberOfAttributesControlledWithSuccess;
        
        ret.timeApplyingChoosingStrategy = this.timeApplyingChoosingStrategy;
        ret.timeControlling = this.timeControlling;
        ret.timeReadingArchivingAttributes = this.timeReadingArchivingAttributes;
        ret.totalTime = this.totalTime;
           
        ret.cycles = this.cycles;
        ret.steps = this.steps;
        
        return ret;
    }
    /**
     * Returns the list of all attribute names that have KO attributes
     * @return A Hashtable which keys are the attribute names and which values are empty.
     */
    public Hashtable getErrorSubAttributes() {
        return errorSubAttributes;
    }
    /**
     * @param errorSubAttributes The errorSubAttributes to set.
     */
    private void setErrorSubAttributes(Hashtable errorSubAttributes) {
        this.errorSubAttributes = errorSubAttributes;
    }
    
    /**
     * Returns whether this CR has been built yet 
     * @return Returns the isBuilt.
     */
    public boolean isBuilt() {
        return isBuilt;
    }

    /**
     * @return Returns the numberOfUndeterminedAttributes.
     */
    public int getNumberOfUndeterminedAttributes() {
        return numberOfUndeterminedAttributes;
    }

    /**
     * @param numberOfUndeterminedAttributes The numberOfUndeterminedAttributes to set.
     */
    private void setNumberOfUndeterminedAttributes(int numberOfUndeterminedAttributes) {
        this.numberOfUndeterminedAttributes = numberOfUndeterminedAttributes;
    }

    /**
     * @return Returns the numberOfAttributesControlledWithSuccess.
     */
    public int getNumberOfAttributesControlledWithSuccess() {
        return numberOfAttributesControlledWithSuccess;
    }

    /**
     * @param numberOfAttributesControlledWithSuccess The numberOfAttributesControlledWithSuccess to set.
     */
    private void setNumberOfAttributesControlledWithSuccess(
            int numberOfAttributesControlledWithSuccess) {
        this.numberOfAttributesControlledWithSuccess = numberOfAttributesControlledWithSuccess;
    }
    
    /**
     * @return Returns the isFullCycle.
     */
    public boolean isFullCycle() {
        return this.isFullCycle;
    }

    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public void setCompletedCycles(int _cycles) 
    {
        this.cycles = _cycles;
    }

    public void setCompletedSteps(int _steps) 
    {
        this.steps = _steps;        
    }

    /**
     * @return Returns the cycles.
     */
    public int getCycles() {
        return this.cycles;
    }

    /**
     * @param cycles The cycles to set.
     */
    public void setCycles(int _cycles) {
        this.cycles = _cycles;
    }

    /**
     * @return Returns the steps.
     */
    public int getSteps() {
        return this.steps;
    }

    /**
     * @param steps The steps to set.
     */
    public void setSteps(int _steps) 
    {
        System.out.println ( "CLA/ControlResult/"+this.hashCode()+"/setSteps/_steps/"+_steps );
        this.steps = _steps;
    }
    
    public ControlResultLine [] sort ()
    {
        ControlResultLine [] ret = new ControlResultLine [ this.lines.size () ];
        Iterator it = this.lines.values ().iterator ();
        int i = 0;
        
        while ( it.hasNext () )
        {
            ControlResultLine next = (ControlResultLine) it.next ();
            ret [ i ] = next;
            i ++;
        }
        
        List list = Arrays.asList ( ret );
        Collections.sort ( list , this.linesComparator );
        ret = (ControlResultLine[]) list.toArray ( ret );
        
        return ret;
    }

    public void removeOldLines(Set namesOfAttributesToControl)//CLA 14/12/06 
    {
        //System.out.println ( "CLA/removeOldLines/namesOfAttributesToControl|"+namesOfAttributesToControl.size() );
        Enumeration it = this.lines.keys();
        while ( it.hasMoreElements () )
        {
            String nextName = (String) it.nextElement ();
            ControlResultLine next = (ControlResultLine) this.lines.get(nextName);
            
            //if ( next.isOlderThan ( step , cycle ) )
            if ( ! namesOfAttributesToControl.contains ( nextName ) )
            {
                //System.out.println ( "CLA/removeOldLines/removing|"+nextName );
                this.lines.remove ( nextName );
                this.errorAttributes.remove ( nextName );//CLA 22/01/07
            }   
        }
    }
}
