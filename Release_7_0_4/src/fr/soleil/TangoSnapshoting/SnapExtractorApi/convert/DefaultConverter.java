/*	Synchrotron Soleil 
 *  
 *   File          :  DefaultConverter.java
 *  
 *   Project       :  snapExtractorAPI
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  24 janv. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: DefaultConverter.java,v 
 *
 */
 /*
 * Created on 24 janv. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapExtractorApi.convert;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.DbData;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.NullableTimedData;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeExtract;

/**
 * 
 * @author CLAISSE 
 */
public class DefaultConverter implements IConverter 
{    
    DefaultConverter() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see snapextractor.api.convert.IConverter#convert(fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeExtract)
     */
    public synchronized DbData convert ( SnapAttributeExtract currentExtract ) throws DevFailed 
    {
        DbData dbData = new DbData ( currentExtract.getAttribute_complete_name () );
        dbData.setData_format ( currentExtract.getData_format () );
        dbData.setData_type ( currentExtract.getData_type () );
        dbData.setWritable ( currentExtract.getWritable () );
        
        NullableTimedData [] timedAttrDatas = null;
        try
        {
            timedAttrDatas = this.convertData ( currentExtract );
            
            if ( timedAttrDatas == null )
            {
                return null;
            }
        }
        catch ( IllegalArgumentException iae )
        {
            DevFailed devFailed = new DevFailed ();
            devFailed.initCause ( iae );
            throw devFailed;
        }
        dbData.setData ( timedAttrDatas );
        
        NullableTimedData  theTimedAttrData = timedAttrDatas [ 0 ];
        dbData.setMax_x ( theTimedAttrData.x );
        dbData.setMax_y ( theTimedAttrData.y );
        
        return dbData;
    }

    private NullableTimedData[] convertData ( SnapAttributeExtract currentExtract ) throws IllegalArgumentException 
    {
        Object value = currentExtract.getValue ();
        int dataType = currentExtract.getData_type ();
        int dataFormat = currentExtract.getData_format ();
        boolean isScalar = ( dataFormat == AttrDataFormat._SCALAR );
        boolean hasBothReadAndWriteValues = 
        ( 
                ( currentExtract.getWritable() == AttrWriteType._READ_WITH_WRITE ) 
                ||
                ( currentExtract.getWritable() == AttrWriteType._READ_WRITE )
    	);
        
        long when;
        if ( currentExtract.getSnap_date () == null )
        {
            when = System.currentTimeMillis();
        }
        else
        {
            when = currentExtract.getSnap_date ().getTime ();
        }
        
        NullableTimedData [] ret = new NullableTimedData [ 1 ];
        NullableTimedData theTimedAttrData;
        try
        {
	        if ( isScalar )
	        {
	            if ( hasBothReadAndWriteValues )
	            {
	                theTimedAttrData = buildDataScalarRW ( value , dataType , when );    
	            }
	            else
	            {
	                theTimedAttrData = buildDataScalarR ( value , dataType , when );
	            }   
	        }
	        else
	        {
	            if ( hasBothReadAndWriteValues )
	            {
	                theTimedAttrData = buildDataSpectrumRW ( value , dataType , when );
	                theTimedAttrData.x = currentExtract.getDimX ();
	            }
	            else
	            {
	                theTimedAttrData = buildDataSpectrumR ( value , dataType , when );
	            }  
	        }
        }
        catch ( IllegalArgumentException iae )
        {
            throw iae;
        }
        
        if ( theTimedAttrData == null )
        {
            return null;
        }
        
        ret [ 0 ] = theTimedAttrData;
        
        return ret;
    }

    /**
     * @param value
     * @param dataType
     * @param when
     * @return
     */
    private NullableTimedData buildDataScalarRW(Object value, int dataType, long when) throws IllegalArgumentException 
    {
        //String[] valueTab = this.castObjectTableToString ( (Object []) value , true );
        String[] valueTab = this.castObjectTableToString ( (Object []) value , false );
        if ( valueTab == null )
        {
            return null;
        }

        NullableTimedData theTimedAttrData = null;

        switch ( dataType )
		{
			case TangoConst.Tango_DEV_LONG:
			case TangoConst.Tango_DEV_ULONG:
                Integer theDataL_R, theDataL_W;
                if (valueTab[0] == null || "null".equals(valueTab[0]) || "NaN".equalsIgnoreCase(valueTab[0]))
                {
                    theDataL_R = null;
                }
                else
                {
                    double theDataL_R_D = Double.parseDouble ( valueTab [0] );
                    theDataL_R = new Integer((int) Math.round ( theDataL_R_D ));
                }
                if (valueTab[1] == null || "null".equals(valueTab[1]) || "NaN".equalsIgnoreCase(valueTab[1]))
                {
                    theDataL_W = null;
                }
                else
                {
                    double theDataL_W_D = Double.parseDouble ( valueTab [1] );
                    theDataL_W = new Integer((int) Math.round ( theDataL_W_D ));
                }

				Integer [] dataL = new Integer [ 2 ];
		        dataL [ 0 ] = theDataL_R;
		        dataL [ 1 ] = theDataL_W;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataL;
		    break;

			case TangoConst.Tango_DEV_SHORT:
			case TangoConst.Tango_DEV_USHORT:
                Short theDataS_R, theDataS_W;
                if (valueTab[0] == null || "null".equals(valueTab[0]) || "NaN".equalsIgnoreCase(valueTab[0]))
                {
                    theDataS_R = null;
                }
                else
                {
                    double theDataS_R_D = Double.parseDouble ( valueTab [0] );
                    theDataS_R = new Short((short) Math.round ( theDataS_R_D ));
                }
                if (valueTab[1] == null || "null".equals(valueTab[1]) || "NaN".equalsIgnoreCase(valueTab[1]))
                {
                    theDataS_W = null;
                }
                else
                {
                    double theDataS_W_D = Double.parseDouble ( valueTab [1] );
                    theDataS_W = new Short((short) Math.round ( theDataS_W_D ));
                }

				Short [] dataS = new Short [ 2 ];
				dataS [ 0 ] =theDataS_R;
				dataS [ 1 ] = theDataS_W;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataS;
		    break;
		    
			case TangoConst.Tango_DEV_DOUBLE:
                Double theDataD_R, theDataD_W;
                if (valueTab[0] == null || "null".equals(valueTab[0]) || "NaN".equalsIgnoreCase(valueTab[0]))
                {
                    theDataD_R = null;
                }
                else
                {
                    theDataD_R = Double.valueOf ( valueTab [0] );
                }
                if (valueTab[1] == null || "null".equals(valueTab[1]) || "NaN".equalsIgnoreCase(valueTab[1]))
                {
                    theDataD_W = null;
                }
                else
                {
                    theDataD_W = Double.valueOf ( valueTab [1] );
                }

				Double [] dataD = new Double [ 2 ];
				dataD [ 0 ] = theDataD_R;
				dataD [ 1 ] = theDataD_W;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataD;
		    break;
		    
			case TangoConst.Tango_DEV_BOOLEAN:
                Boolean theDataB_R, theDataB_W;
                if (valueTab[0] == null || "null".equals(valueTab[0]) || "NaN".equalsIgnoreCase(valueTab[0]))
                {
                    theDataB_R = null;
                }
                else
                {
                    theDataB_R = Boolean.valueOf ( valueTab [ 0 ] );
                }
                if (valueTab[1] == null || "null".equals(valueTab[1]) || "NaN".equalsIgnoreCase(valueTab[1]))
                {
                    theDataB_W = null;
                }
                else
                {
                    theDataB_W = Boolean.valueOf ( valueTab [ 1 ] );
                }
				
				Boolean [] dataB = new Boolean [ 2 ];
				dataB [ 0 ] = theDataB_R;
				dataB [ 1 ] = theDataB_W;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataB;
		    break;
		    
			case TangoConst.Tango_DEV_FLOAT:
                Float theDataF_R, theDataF_W;
                if (valueTab[0] == null || "null".equals(valueTab[0]) || "NaN".equalsIgnoreCase(valueTab[0]))
                {
                    theDataF_R = null;
                }
                else
                {
                    theDataF_R = Float.valueOf ( valueTab [0] );
                }
                if (valueTab[1] == null || "null".equals(valueTab[1]) || "NaN".equalsIgnoreCase(valueTab[1]))
                {
                    theDataF_W = null;
                }
                else
                {
                    theDataF_W = Float.valueOf ( valueTab [1] );
                }
				
				Float [] dataF = new Float [ 2 ];
				dataF [ 0 ] = theDataF_R;
				dataF [ 1 ] = theDataF_W;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataF;
		    break;
		    
			case TangoConst.Tango_DEV_STRING:
                String theDataStr_R, theDataStr_W;
                if (valueTab[0] == null || "null".equals(valueTab[0]) || "NaN".equalsIgnoreCase(valueTab[0]))
                {
                    theDataStr_R = null;
                }
                else
                {
                    theDataStr_R = valueTab [0];
                }
                if (valueTab[1] == null || "null".equals(valueTab[1]) || "NaN".equalsIgnoreCase(valueTab[1]))
                {
                    theDataStr_W = null;
                }
                else
                {
                    theDataStr_W = valueTab [1];
                }
				
				String [] dataStr = new String [ 2 ];
				dataStr [ 0 ] = theDataStr_R;
				dataStr [ 1 ] = theDataStr_W;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataStr;
		    break;
		    
			case TangoConst.Tango_DEV_STATE:
                Integer theDataI_R, theDataI_W;
                if (valueTab[0] == null || "null".equals(valueTab[0]) || "NaN".equalsIgnoreCase(valueTab[0]))
                {
                    theDataI_R = null;
                }
                else
                {
                    theDataI_R = Integer.valueOf ( valueTab [0] );
                }
                if (valueTab[1] == null || "null".equals(valueTab[1]) || "NaN".equalsIgnoreCase(valueTab[1]))
                {
                    theDataI_W = null;
                }
                else
                {
                    theDataI_W = Integer.valueOf ( valueTab [1] );
                }
				
				Integer [] dataI = new Integer [ 2 ];
				dataI [ 0 ] = theDataI_R;
				dataI [ 1 ] = theDataI_W;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataI;
		    break;
		    
			default:
			    throw new IllegalArgumentException ( "DefaultConverter/Data type not supported: " + dataType );
		}
        
        theTimedAttrData.data_type = dataType;
        theTimedAttrData.time = new Long(when);
        theTimedAttrData.x = 1;
        theTimedAttrData.y = 0;

        return theTimedAttrData;
    }

    private NullableTimedData buildDataSpectrumRW(Object value, int dataType, long when) throws IllegalArgumentException 
    {
        if ( "NaN".equalsIgnoreCase ( value + "" ) || "[NaN]".equalsIgnoreCase ( value + "" ) )
        {
            return null;
        }
        Object [] valueTab = (Object []) value;
        if ( valueTab.length != 2 )
        {
            return null;
        }
        if ( valueTab [ 0 ] == null || "NaN".equalsIgnoreCase ( valueTab [ 0 ] + "" ) || "[NaN]".equalsIgnoreCase ( valueTab [ 0 ] + "" ) )
        {
            return null;
        }
        if ( valueTab [ 1 ] == null || "NaN".equalsIgnoreCase ( valueTab [ 1 ] + "" ) || "[NaN]".equalsIgnoreCase ( valueTab [ 1 ] + "" ) )
        {
            return null;
        }

        NullableTimedData theTimedAttrData = null;
        int x;

        switch ( dataType )
		{
			case TangoConst.Tango_DEV_LONG:
			case TangoConst.Tango_DEV_ULONG:
			    Integer [] dataL_Read = (Integer []) valueTab [ 0 ];
                Integer [] dataL_Write = (Integer []) valueTab [ 1 ];
				x = dataL_Read.length + dataL_Write.length;
                Integer [] dataL = new Integer [ x ];

				for ( int i = 0 ; i < dataL_Read.length ; i ++ )
			    {
			        dataL [ i ] = dataL_Read [ i ];
			    }
				for ( int i = 0 ; i < dataL_Write.length ; i ++ )
			    {
			        dataL [ i + dataL_Read.length ] = dataL_Write [ i ];
			    }

                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataL;
		    break;

			case TangoConst.Tango_DEV_SHORT:
			case TangoConst.Tango_DEV_USHORT:
			    Short [] dataS_Read = (Short []) valueTab [ 0 ];
				Short [] dataS_Write = (Short []) valueTab [ 1 ];
				x = dataS_Read.length + dataS_Write.length;
				Short [] dataS = new Short [ x ];

				for ( int i = 0 ; i < dataS_Read.length ; i ++ )
			    {
				    dataS [ i ] = dataS_Read [ i ];
			    }
				for ( int i = 0 ; i < dataS_Write.length ; i ++ )
			    {
				    dataS [ i + dataS_Read.length ] = dataS_Write [ i ];
			    }

                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataS;
		    break;

			case TangoConst.Tango_DEV_DOUBLE:
			    Double [] dataD_Read = (Double []) valueTab [ 0 ];
				Double [] dataD_Write = (Double []) valueTab [ 1 ];
				x = dataD_Read.length + dataD_Write.length;
				Double [] dataD = new Double [ x ];

				for ( int i = 0 ; i < dataD_Read.length ; i ++ )
			    {
				    dataD [ i ] = dataD_Read [ i ];
			    }
				for ( int i = 0 ; i < dataD_Write.length ; i ++ )
			    {
				    dataD [ i + dataD_Read.length ] = dataD_Write [ i ];
			    }

                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataD;
		    break;

			case TangoConst.Tango_DEV_BOOLEAN:
			    Boolean [] dataB_Read = (Boolean []) valueTab [ 0 ];
				Boolean [] dataB_Write = (Boolean []) valueTab [ 1 ];
				x = dataB_Read.length + dataB_Write.length;
				Boolean [] dataB = new Boolean [ x ];

				for ( int i = 0 ; i < dataB_Read.length ; i ++ )
			    {
				    dataB [ i ] = dataB_Read [ i ];
			    }
				for ( int i = 0 ; i < dataB_Write.length ; i ++ )
			    {
				    dataB [ i + dataB_Read.length ] = dataB_Write [ i ];
			    }

                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataB;
		    break;

			case TangoConst.Tango_DEV_FLOAT:
			    Float [] dataF_Read = (Float []) valueTab [ 0 ];
				Float [] dataF_Write = (Float []) valueTab [ 1 ];
				x = dataF_Read.length + dataF_Write.length;
				Float [] dataF = new Float [ x ];

				for ( int i = 0 ; i < dataF_Read.length ; i ++ )
			    {
				    dataF [ i ] = dataF_Read [ i ];
			    }
				for ( int i = 0 ; i < dataF_Write.length ; i ++ )
			    {
				    dataF [ i + dataF_Read.length ] = dataF_Write [ i ];
			    }

                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataF;
		    break;

			case TangoConst.Tango_DEV_STRING:
			    String [] dataStr_Read = (String []) valueTab [ 0 ];
				String [] dataStr_Write = (String []) valueTab [ 1 ];
				x = dataStr_Read.length + dataStr_Write.length;
				String [] dataStr = new String [ x ];
				
				for ( int i = 0 ; i < dataStr_Read.length ; i ++ )
			    {
				    dataStr [ i ] = dataStr_Read [ i ];
			    }
				for ( int i = 0 ; i < dataStr_Write.length ; i ++ )
			    {
				    dataStr [ i + dataStr_Read.length ] = dataStr_Write [ i ];
			    }

                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataStr;
		    break;

			case TangoConst.Tango_DEV_STATE:
                Integer [] dataI_Read = (Integer []) valueTab [ 0 ];
                Integer [] dataI_Write = (Integer []) valueTab [ 1 ];
				x = dataI_Read.length + dataI_Write.length;
                Integer [] dataI = new Integer [ x ];
				
				for ( int i = 0 ; i < dataI_Read.length ; i ++ )
			    {
				    dataI [ i ] = dataI_Read [ i ];
			    }
				for ( int i = 0 ; i < dataI_Write.length ; i ++ )
			    {
				    dataI [ i + dataI_Read.length ] = dataI_Write [ i ];
			    }
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataI;
		    break;

		    default:
		        throw new IllegalArgumentException ( "DefaultConverter/Data type not supported: " + dataType );
		}

        theTimedAttrData.data_type = dataType;
        theTimedAttrData.time = new Long(when);
        theTimedAttrData.x = x;
        return theTimedAttrData;
    }
    
    private NullableTimedData buildDataScalarR(Object value, int dataType, long when) throws IllegalArgumentException 
    {
        NullableTimedData theTimedAttrData = null;

        switch ( dataType )
		{
			case TangoConst.Tango_DEV_LONG:
			case TangoConst.Tango_DEV_ULONG:
			    String valS = value + "";
                Integer theDataL;
                if (valS == null || "null".equals(valS) || "NaN".equalsIgnoreCase(valS))
                {
                    theDataL = null;
                }
                else
                {
                    double valL_D = Double.parseDouble ( valS );
                    theDataL = new Integer((int) Math.round ( valL_D ));
                }

                Integer [] dataL = new Integer [ 1 ];
				dataL [ 0 ] = theDataL;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataL;
		    break;

			case TangoConst.Tango_DEV_SHORT:
			case TangoConst.Tango_DEV_USHORT:
			    valS = value + "";
                Short theDataS;
                if (valS == null || "null".equals(valS) || "NaN".equalsIgnoreCase(valS))
                {
                    theDataS = null;
                }
                else
                {
                    double valS_D = Double.parseDouble ( valS );
                    theDataS = new Short((short) Math.round ( valS_D ));
                }

                Short [] dataS = new Short [ 1 ];
				dataS [ 0 ] = theDataS;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataS;
		    break;

			case TangoConst.Tango_DEV_DOUBLE:
				Double valD = ( Double ) ( value );

				Double [] dataD = new Double [ 1 ];
				dataD [ 0 ] = valD;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataD;
		    break;

			case TangoConst.Tango_DEV_BOOLEAN:
				Boolean valB = ( Boolean ) ( value );

				Boolean [] dataB = new Boolean [ 1 ];
				dataB [ 0 ] = valB;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataB;
		    break;

			case TangoConst.Tango_DEV_FLOAT:
				Float valF = ( Float ) ( value );

				Float [] dataF = new Float [ 1 ];
				dataF [ 0 ] = valF;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataF;
		    break;

			case TangoConst.Tango_DEV_STRING:
				String valStr = ( String ) ( value );

				String [] dataStr = new String [ 1 ];
				dataStr [ 0 ] = valStr;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataStr;
		    break;

			case TangoConst.Tango_DEV_STATE:
				Integer valI = ( Integer ) ( value );

				Integer [] dataI = new Integer [ 1 ];
				dataI [ 0 ] = valI;
                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataI;
		    break;

		    default:
		        throw new IllegalArgumentException ( "DefaultConverter/Data type not supported: " + dataType );
		}

        theTimedAttrData.data_type = dataType;
        theTimedAttrData.time = new Long(when);
        theTimedAttrData.x = 1;
        theTimedAttrData.y = 0;
        return theTimedAttrData;
    }
    
    private NullableTimedData buildDataSpectrumR(Object value, int dataType, long when) throws IllegalArgumentException 
    {
        if ( "NaN".equalsIgnoreCase ( value + "" ) || "[NaN]".equalsIgnoreCase ( value + "" ) )
        {
            return null;
        }

        int x;
        NullableTimedData theTimedAttrData = null;

        switch ( dataType )
		{
			case TangoConst.Tango_DEV_LONG:
			case TangoConst.Tango_DEV_ULONG:
			    Integer [] dataL = (Integer []) value;
				x = dataL.length;

                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataL;
		    break;
		    
			case TangoConst.Tango_DEV_SHORT:
			case TangoConst.Tango_DEV_USHORT:
			    Short [] dataS = (Short []) value;
				x = dataS.length;

                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataS;
		    break;

			case TangoConst.Tango_DEV_DOUBLE:
			    Double [] dataD = (Double []) value;
				x = dataD.length;

                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataD;
		    break;

			case TangoConst.Tango_DEV_BOOLEAN:
			    Boolean [] dataB = (Boolean []) value;
				x = dataB.length;

                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataB;
		    break;

			case TangoConst.Tango_DEV_FLOAT:
			    Float [] dataF = (Float []) value;
				x = dataF.length;

                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataF;
		    break;

			case TangoConst.Tango_DEV_STRING:
			    String [] dataStr = (String []) value;
				x = dataStr.length;

                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataStr;
		    break;

			case TangoConst.Tango_DEV_STATE:
                Integer [] dataI = (Integer []) value;
				x = dataI.length;

                theTimedAttrData = new NullableTimedData();
                theTimedAttrData.value = dataI;
		    break;

		    default:
		        throw new IllegalArgumentException ( "DefaultConverter/Data type not supported: " + dataType );
		}

        theTimedAttrData.data_type = dataType;
        theTimedAttrData.time = new Long(when);
        theTimedAttrData.x = x;
        return theTimedAttrData;
    }

    /* (non-Javadoc)
     * @see fr.soleil.TangoSnapshoting.SnapExtractorApi.convert.IConverter#castObjectTableToString(java.lang.Object[], boolean)
     */
    public String[] castObjectTableToString ( Object[] objects , boolean returnNullIfOneElementIsNull ) 
    {
        if ( objects == null || objects.length == 0 )
        {
            return null;
        }
        
        String[] ret = new String [ objects.length ];
        for ( int i = 0 ; i < objects.length ; i ++ )
        {
            if ( objects [i] == null && returnNullIfOneElementIsNull )
            {
                return null;
            }  
            
            ret [ i ] = "" + objects [i];
        }
        
        return ret;
    }
}
