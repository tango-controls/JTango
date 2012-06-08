/*	Synchrotron Soleil 
 *  
 *   File          :  SamplingType.java
 *  
 *   Project       :  mambo
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  29 août 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: SamplingType.java,v 
 *
 */
 /*
 * Created on 29 août 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import fr.esrf.TangoDs.TangoConst;


public class SamplingType 
{
    private short type;
    private String oracleFormat;
    private String mySqlFormat;
    private String label;
    private boolean hasAdditionalFiltering;
    private short additionalFilteringFactor;
    
    public static final short MONTH = 5;
    public static final short DAY = 4;
    public static final short HOUR = 3;
    public static final short MINUTE = 2;
    public static final short SECOND = 1;
    public static final short ALL = -1;
    
    public static final String EVERY_DAY_LABEL = "DAY";
    public static final String EVERY_HOUR_LABEL = "HOUR";
    public static final String EVERY_MINUTE_LABEL = "MINUTE";
    public static final String EVERY_SECOND_LABEL = "SECOND";
    public static final String NO_SAMPLING_LABEL = "ALL";
    
    public static final String AVERAGING_NORMALISATION = "AVG";
    public static final String MINIMUM_NORMALISATION = "MIN";
    
    public SamplingType( short _type , String _oracleFormat , String _mySqlFormat , String _label ) 
    {
        super();
        
        this.type = _type;
        this.oracleFormat = _oracleFormat;
        this.mySqlFormat = _mySqlFormat;
        this.label = _label;
    }

    /**
     * @return Returns the format.
     */
    public String getOracleFormat() {
        return this.oracleFormat;
    }

    /**
     * @param format The format to set.
     */
    public void setOracleFormat(String format) {
        this.oracleFormat = format;
    }

    /**
     * @return Returns the label.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * @param label The label to set.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return Returns the type.
     */
    public short getType() {
        return this.type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(short type) {
        this.type = type;
    }

    public static SamplingType getSamplingType(short samplingType) 
    {
        String oracleFormat;
        String mySqlFormat;
        String label;
        
        switch ( samplingType )
        {
            case SamplingType.SECOND:
                oracleFormat = "YYYY-MM-DD HH24:MI:SS";
                mySqlFormat = "%Y-%m-%d %H:%i:%s";
                label = "Seconds";
            break;
            
            /*case SamplingType.EVERY_TEN_SECONDS:
                format = "YYYY-MM-DD HH24:MI:S";
                label = "Every ten seconds";
            break;*/
            
            case SamplingType.MINUTE:
                oracleFormat = "YYYY-MM-DD HH24:MI";
                mySqlFormat = "%Y-%m-%d %H:%i";
                label = "Minutes";
            break;
                
            /*case SamplingType.EVERY_TEN_MINUTES:
                format = "YYYY-MM-DD HH24:M";
            break;*/
            
            case SamplingType.HOUR:
                oracleFormat = "YYYY-MM-DD HH24";
                mySqlFormat = "%Y-%m-%d %H";
                label = "Hours";
            break;
                
            case SamplingType.DAY:
                oracleFormat = "YYYY-MM-DD";
                mySqlFormat = "%Y-%m-%d";
                label = "Days";
            break;
            
            default:
                oracleFormat = "YYYY-MM-DD HH24:MI:SS.FF";
                mySqlFormat = "%Y-%m-%d %H:%i:%s.%f";
                label = "None";
            break;
        }
        
        return new SamplingType ( samplingType , oracleFormat , mySqlFormat , label );
    }

    public static SamplingType[] getChoices() 
    {
        // Basic types
        SamplingType defaultSampling = SamplingType.getSamplingType ( SamplingType.ALL );     
        SamplingType everySecondSampling = SamplingType.getSamplingType ( SamplingType.SECOND );
        SamplingType everyMinuteSampling = SamplingType.getSamplingType ( SamplingType.MINUTE );
        SamplingType everyHourSampling = SamplingType.getSamplingType ( SamplingType.HOUR );
        SamplingType everyDaySampling = SamplingType.getSamplingType ( SamplingType.DAY );
        
        //Additionnal types
        /*SamplingType everyFiveMinutesSampling = SamplingType.getSamplingType ( SamplingType.MINUTE );
        everyFiveMinutesSampling.setHasAdditionalFiltering ( true );
        everyFiveMinutesSampling.setAdditionalFilteringFactor ( (short) 5 );
        everyFiveMinutesSampling.setLabel ( "5 Minutes" );*/
        
        SamplingType [] ret = new SamplingType [ 5 ];
        ret [ 0 ] = defaultSampling;
        ret [ 1 ] = everySecondSampling;
        ret [ 2 ] = everyMinuteSampling;
        ret [ 3 ] = everyHourSampling;
        ret [ 4 ] = everyDaySampling;
        
        return ret;
    }

    public String toString ()
    {
        return String.valueOf ( this.getType() );
    }
    
    public String getDescription ()
    {
        String CRLF = System.getProperty( "line.separator" );
        
        String ret = "getType: " + this.getType();
        ret += CRLF;
        ret += "hasSampling: " + this.hasSampling ();
        ret += CRLF;
        ret += "getLabel: " + this.getLabel();
        ret += CRLF;
        ret += "hasAdditionalFiltering: " + this.hasAdditionalFiltering();
        ret += CRLF;
        ret += "getAdditionalFilteringFactor: " + this.getAdditionalFilteringFactor();
        
        return ret;
    } 
    
    public static SamplingType fromString ( String samplingType_s ) 
    {
        short samplingType;
        try
        {
            samplingType = Short.parseShort ( samplingType_s );
        }
        catch ( Exception e )
        {
            samplingType = SamplingType.ALL;
        }
        
        return SamplingType.getSamplingType ( samplingType );
    }
    
    public boolean hasSampling ()
    {
        return this.getType() != SamplingType.ALL;
    }

    /**
     * @return Returns the mySqlFormat.
     */
    public String getMySqlFormat() {
        return this.mySqlFormat;
    }

    /**
     * @param mySqlFormat The mySqlFormat to set.
     */
    public void setMySqlFormat(String mySqlFormat) {
        this.mySqlFormat = mySqlFormat;
    }

    public static String getGroupingNormalisationType(int data_type) 
    {
        switch (data_type )
        {
            case TangoConst.Tango_DEV_DOUBLE:
            case TangoConst.Tango_DEV_FLOAT:
            case TangoConst.Tango_DEV_LONG:
            case TangoConst.Tango_DEV_SHORT:
            case TangoConst.Tango_DEV_ULONG:
            case TangoConst.Tango_DEV_USHORT:
                return AVERAGING_NORMALISATION;
            default:
                return MINIMUM_NORMALISATION;
        }
    }

    public static SamplingType getSamplingTypeByLabel ( String typeName ) throws IllegalArgumentException
    {
        boolean invalidType = false;
        short typeId = ALL;
        
        if ( typeName == null )
        {
            invalidType = true;
        }
        else if ( typeName.equals ( EVERY_DAY_LABEL ) )
        {
            typeId = DAY;
        }
        else if ( typeName.equals ( EVERY_HOUR_LABEL ) )
        {
            typeId = HOUR;
        }
        else if ( typeName.equals ( EVERY_MINUTE_LABEL ) )
        {
            typeId = MINUTE;
        }
        else if ( typeName.equals ( EVERY_SECOND_LABEL ) )
        {
            typeId = SECOND;
        }
        else if ( typeName.equals ( NO_SAMPLING_LABEL ) )
        {
            typeId = ALL;
        }
        else
        {
            invalidType = true;
        }

        if ( invalidType )
        {
            throw new IllegalArgumentException ( "SamplingType.getSamplingTypeByLabel ( " + typeName + " ): Invalid typeName."  );
        }

        return SamplingType.getSamplingType ( typeId );
    }

    public SamplingType cloneSamplingType() 
    {
        return SamplingType.getSamplingType ( this.getType () );
    }
    
    /**
     * @return Returns the additionalFilteringFactor.
     */
    public short getAdditionalFilteringFactor() {
        return this.additionalFilteringFactor;
    }

    /**
     * @param additionalFilteringFactor The additionalFilteringFactor to set.
     */
    public void setAdditionalFilteringFactor(short additionalFilteringFactor) {
        this.additionalFilteringFactor = additionalFilteringFactor;
    }

    /**
     * @return Returns the hasAdditionalFiltering.
     */
    public boolean hasAdditionalFiltering() {
        return this.hasAdditionalFiltering;
    }

    /**
     * @param hasAdditionalFiltering The hasAdditionalFiltering to set.
     */
    public void setHasAdditionalFiltering(boolean hasAdditionalFiltering) {
        this.hasAdditionalFiltering = hasAdditionalFiltering;
    }
    
    public boolean isSameTypeAs ( SamplingType another )
    {
        boolean ret = ( this.getType () == another.getType () );
        /*ret = ret && ( this.hasAdditionalFiltering () == another.hasAdditionalFiltering () );
        ret = ret && ( this.getAdditionalFilteringFactor () == another.getAdditionalFilteringFactor () );*/
        return ret;
    }

    public String getOneLevelHigherFormat(boolean b) 
    {
        short oneLevelHigherType = (short) (this.getType() + 1);
        SamplingType oneLevelHigherSamplingType = SamplingType.getSamplingType ( oneLevelHigherType );
        return oneLevelHigherSamplingType.getFormat (b);
    }
    
    public String getAdditionalFilteringClause ( boolean isMySQL , String field ) 
    {
        if ( ! this.hasAdditionalFiltering  )
        {
            return "";
        }
        if ( ! this.hasSampling ()  )
        {
            return "";
        }
        
        if ( isMySQL )
        {
            String typeOfAdditionalFiltering;
            switch ( this.type )
            {
                case SamplingType.SECOND:
                    typeOfAdditionalFiltering = "'%s'";
                break;
                
                case SamplingType.MINUTE:
                    typeOfAdditionalFiltering = "'%i'";
                break;
                
                case SamplingType.HOUR:
                    typeOfAdditionalFiltering = "'%H'";
                break;
                    
                case SamplingType.DAY:
                    typeOfAdditionalFiltering = "'%d'";
                break;
                
                default:
                    return "";   
            }
            String to_char = "DATE_FORMAT(" + field + "," + typeOfAdditionalFiltering + ")" ;
            String to_number = to_char;
            return " ,FLOOR(" + to_number + "/" + this.additionalFilteringFactor + ")";    
        }
        else
        {
            String typeOfAdditionalFiltering;
            switch ( this.type )
            {
                case SamplingType.SECOND:
                    typeOfAdditionalFiltering = "'SS'";
                break;
                
                case SamplingType.MINUTE:
                    typeOfAdditionalFiltering = "'MI'";
                break;
                
                case SamplingType.HOUR:
                    typeOfAdditionalFiltering = "'HH24'";
                break;
                    
                case SamplingType.DAY:
                    typeOfAdditionalFiltering = "'DD'";
                break;
                
                default:
                    return "";   
            }
            
            String to_char = "to_char ( " + field + " , " + typeOfAdditionalFiltering + " )" ;
            String to_number = "to_number ( " + to_char + " )";
            return " , FLOOR ( " + to_number + "/" + this.additionalFilteringFactor + " )";
        }
    }
    
    public String getFormat ( boolean isMySQL ) 
    {
        if ( isMySQL )
        {
            return this.mySqlFormat;    
        }
        else
        {
            return this.oracleFormat;
        }
    }
}
