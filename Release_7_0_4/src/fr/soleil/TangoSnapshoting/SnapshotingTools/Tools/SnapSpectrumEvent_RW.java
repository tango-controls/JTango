/*	Synchrotron Soleil 
 *  
 *   File          :  SnapSpectrumEvent_RW.java
 *  
 *   Project       :  apiDev
 *  
 *   Description   :  
 *  
 *   Author        :  SOLEIL
 *  
 *   Original      :  7 févr. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: SnapSpectrumEvent_RW.java,v 
 *
 */
package fr.soleil.TangoSnapshoting.SnapshotingTools.Tools;

import java.sql.Timestamp;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.GlobalConst;

/**
 * @author SOLEIL
 */
public class SnapSpectrumEvent_RW extends SnapAttribute
{
	private int dim_x;
	private int dim_y = 0;

	public SnapSpectrumEvent_RW()
	{
		super();
	}


	public int getDim_x()
	{
		return dim_x;
	}

	public void setDim_x(int dim_x)
	{
		this.dim_x = dim_x;
	}

	public int getDim_y()
	{
		return dim_y;
	}

	/**
	 * Creates a new instance of Spectrum Event
	 */
	public SnapSpectrumEvent_RW(String[] snapSpectrumEvent_RO)
	{
		setAttribute_complete_name(snapSpectrumEvent_RO[ 0 ]);
		setId_att(Integer.parseInt(snapSpectrumEvent_RO[ 1 ]));
		setId_snap(Integer.parseInt(snapSpectrumEvent_RO[ 2 ]));
		setSnap_date(Timestamp.valueOf(snapSpectrumEvent_RO[ 3 ]));
		setDim_x(Integer.parseInt(snapSpectrumEvent_RO[ 4 ]));
		//setDim_y(Integer.parseInt(snapSpectrumEvent_RO[5]));

		Double[] value = new Double[ snapSpectrumEvent_RO.length - 6 ];
		for ( int i = 0 ; i < value.length ; i++ )
		{
			value[ i ] = Double.valueOf(snapSpectrumEvent_RO[ i + 6 ]);
		}
		this.setValue(value);
	}

    /**
     * This method returns the value of this spectrum event. The returned value is then formated as a String.
     *
     * @return the value of this spectrum event.
     */
    public String getValue_AsString()
    {
        Object value = getValue();
        StringBuffer valueStr = new StringBuffer();
        if (value instanceof Double[])
        {
            for ( int i = 0 ; i < ((Double[])value).length - 1 ; i++ )
            {
                valueStr.append(((Double[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
            }
            valueStr.append(((Double[])value)[ ((Double[])value).length - 1 ]);
        }
        else if (value instanceof Byte[])
        {
            for ( int i = 0 ; i < ((Byte[])value).length - 1 ; i++ )
            {
                valueStr.append(((Byte[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
            }
            valueStr.append(((Byte[])value)[ ((Byte[])value).length - 1 ]);
        }
        else if (value instanceof Short[])
        {
            for ( int i = 0 ; i < ((Short[])value).length - 1 ; i++ )
            {
                valueStr.append(((Short[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
            }
            valueStr.append(((Short[])value)[ ((Short[])value).length - 1 ]);
        }
        else if (value instanceof Integer[])
        {
            for ( int i = 0 ; i < ((Integer[])value).length - 1 ; i++ )
            {
                valueStr.append(((Integer[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
            }
            valueStr.append(((Integer[])value)[ ((Integer[])value).length - 1 ]);
        }
        else if (value instanceof Float[])
        {
            for ( int i = 0 ; i < ((Float[])value).length - 1 ; i++ )
            {
                valueStr.append(((Float[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
            }
            valueStr.append(((Float[])value)[ ((Float[])value).length - 1 ]);
        }
        else if (value instanceof Boolean[])
        {
            for ( int i = 0 ; i < ((Boolean[])value).length - 1 ; i++ )
            {
                valueStr.append(((Boolean[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
            }
            valueStr.append(((Boolean[])value)[ ((Boolean[])value).length - 1 ]);
        }
        else if (value instanceof String[])
        {
            for ( int i = 0 ; i < ((String[])value).length - 1 ; i++ )
            {
                valueStr.append(((String[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
            }
            valueStr.append(((String[])value)[ ((String[])value).length - 1 ]);
        }
        else
        {
            valueStr.append( value.toString() );
        }
        return valueStr.toString();
    }

    /**
     * Returns an array representation of the object <I>SnapSpectrumEvent_RO</I>.
     *
     * @return an array representation of the object <I>SnapSpectrumEvent_RO</I>.
     */
    public String[] toArray()
    {
        Double[] value = ( Double[] ) getValue();
        String[] snapSpectrumEvent_RO = new String[ 6 + value.length ];
        snapSpectrumEvent_RO[ 0 ] = getAttribute_complete_name(); //    name
        snapSpectrumEvent_RO[ 1 ] = Integer.toString(getId_att()); //   id_context
        snapSpectrumEvent_RO[ 2 ] = Integer.toString(getId_snap()); //  id_snap
        snapSpectrumEvent_RO[ 3 ] = getSnap_date().toString(); //   time
        snapSpectrumEvent_RO[ 4 ] = Integer.toString(dim_x);  // dim_x
        snapSpectrumEvent_RO[ 5 ] = Integer.toString(dim_y);  // dim_y

        for ( int i = 0 ; i < value.length ; i++ )
        {
            snapSpectrumEvent_RO[ i + 6 ] = value[ i ] + "";
        }
        return snapSpectrumEvent_RO;
    }

    public String toString()
    {
        String snapSpectrumEvent_RO = "";
        snapSpectrumEvent_RO =
        "Source : \t" + getAttribute_complete_name() + "\r\n" +
        "Attribute ID : \t" + getId_att() + "\r\n" +
        "Snap ID : \t" + getId_snap() + "\r\n" +
        "Snap Time : \t" + getSnap_date() + "\r\n" +
        "Dim x : \t" + getDim_x() + "\r\n" +
        "Dim y : \t" + getDim_y() + "\r\n" +
        "Value : \t..." + "\r\n";
        return snapSpectrumEvent_RO;
    }

    /**
     * @return
     */
    public Object getSpectrumValueRWRead() 
    {
        Object value = getValue();
        if ( value == null )
        {
            return null;
        }
        int len = this.dim_x;
        if (value instanceof Double[])
        {
            Double[] ret = new Double [ len ];
            for ( int i = 0 ; i < len ; i ++ )
            {
                ret [ i ] = ((Double[])value) [ i ];
            }

            return ret;
        }
        else if (value instanceof Byte[])
        {
            Byte[] ret = new Byte [ len ];
            for ( int i = 0 ; i < len ; i ++ )
            {
                ret [ i ] = ((Byte[])value) [ i ];
            }

            return ret;
        }
        else if (value instanceof Short[])
        {
            Short[] ret = new Short [ len ];
            for ( int i = 0 ; i < len ; i ++ )
            {
                ret [ i ] = ((Short[])value) [ i ];
            }

            return ret;
        }
        else if (value instanceof Integer[])
        {
            Integer[] ret = new Integer [ len ];
            for ( int i = 0 ; i < len ; i ++ )
            {
                ret [ i ] = ((Integer[])value) [ i ];
            }

            return ret;
        }
        else if (value instanceof Float[])
        {
            Float[] ret = new Float [ len ];
            for ( int i = 0 ; i < len ; i ++ )
            {
                ret [ i ] = ((Float[])value) [ i ];
            }

            return ret;
        }
        else if (value instanceof Boolean[])
        {
            Boolean[] ret = new Boolean [ len ];
            for ( int i = 0 ; i < len ; i ++ )
            {
                ret [ i ] = ((Boolean[])value) [ i ];
            }

            return ret;
        }
        else if (value instanceof String[])
        {
            String[] ret = new String [ len ];
            for ( int i = 0 ; i < len ; i ++ )
            {
                ret [ i ] = ((String[])value) [ i ];
            }

            return ret;
        }
        else
        {
            return null;
        }
    }
    
    public Object getSpectrumValueRWWrite() 
    {
        Object value = getValue();
        if ( value == null )
        {
            return null;
        }
        if (value instanceof Double[])
        {
            int len = ((Double[])value).length - this.dim_x;
            if (len < 0) len = 0;
            Double[] ret = new Double [ len ];
            for ( int i = this.dim_x ; i < ((Double[])value).length; i ++ )
            {
                ret [ i-this.dim_x ] = ((Double[])value) [ i ];
            }

            return ret;       
        }
        else if (value instanceof Byte[])
        {
            int len = ((Byte[])value).length - this.dim_x;
            if (len < 0) len = 0;
            Byte[] ret = new Byte [ len ];
            for ( int i = this.dim_x ; i < ((Byte[])value).length; i ++ )
            {
                ret [ i-this.dim_x ] = ((Byte[])value) [ i ];
            }

            return ret;       
        }
        else if (value instanceof Short[])
        {
            int len = ((short[])value).length - this.dim_x;
            if (len < 0) len = 0;
            Short[] ret = new Short [ len ];
            for ( int i = this.dim_x ; i < ((Short[])value).length; i ++ )
            {
                ret [ i-this.dim_x ] = ((Short[])value) [ i ];
            }

            return ret;       
        }
        else if (value instanceof Integer[])
        {
            int len = ((Integer[])value).length - this.dim_x;
            if (len < 0) len = 0;
            Integer[] ret = new Integer [ len ];
            for ( int i = this.dim_x ; i < ((Integer[])value).length; i ++ )
            {
                ret [ i-this.dim_x ] = ((Integer[])value) [ i ];
            }

            return ret;       
        }
        else if (value instanceof Float[])
        {
            int len = ((Float[])value).length - this.dim_x;
            if (len < 0) len = 0;
            Float[] ret = new Float [ len ];
            for ( int i = this.dim_x ; i < ((Float[])value).length; i ++ )
            {
                ret [ i-this.dim_x ] = ((Float[])value) [ i ];
            }

            return ret;       
        }
        else if (value instanceof Boolean[])
        {
            int len = ((Boolean[])value).length - this.dim_x;
            if (len < 0) len = 0;
            Boolean[] ret = new Boolean [ len ];
            for ( int i = this.dim_x ; i < ((Boolean[])value).length; i ++ )
            {
                ret [ i-this.dim_x ] = ((Boolean[])value) [ i ];
            }

            return ret;       
        }
        else if (value instanceof String[])
        {
            int len = ((String[])value).length - this.dim_x;
            if (len < 0) len = 0;
            String[] ret = new String [ len ];
            for ( int i = this.dim_x ; i < ((String[])value).length; i ++ )
            {
                ret [ i-this.dim_x ] = ((String[])value) [ i ];
            }

            return ret;       
        }
        else
        {
            return null;
        }
    }

    /**
     * @return
     */
    public String getSpectrumValueRW_AsString_Read() 
    {
        Object value = getSpectrumValueRWRead();
        if ( value == null )
        {
            return "";
        }
        if (value instanceof Double[])
        {
            return convertDoubleTabToString ( (Double[])value ); 
        }
        else if (value instanceof Byte[])
        {
            return convertByteTabToString ( (Byte[])value ); 
        }
        else if (value instanceof Short[])
        {
            return convertShortTabToString ( (Short[])value ); 
        }
        else if (value instanceof Integer[])
        {
            return convertIntTabToString ( (Integer[])value ); 
        }
        else if (value instanceof Float[])
        {
            return convertFloatTabToString ( (Float[])value ); 
        }
        else if (value instanceof Boolean[])
        {
            return convertBooleanTabToString ( (Boolean[])value ); 
        }
        else if (value instanceof String[])
        {
            return convertStringTabToString ( (String[])value ); 
        }
        else
        {
            return null;
        }
    }
    
    /**
     * @return
     */
    public String getSpectrumValueRW_AsString_Write() 
    {
        Object value = getSpectrumValueRWWrite();
        if ( value == null )
        {
            return "";
        }
        if (value instanceof Double[])
        {
            return convertDoubleTabToString ( (Double[])value ); 
        }
        else if (value instanceof Byte[])
        {
            return convertByteTabToString ( (Byte[])value ); 
        }
        else if (value instanceof Short[])
        {
            return convertShortTabToString ( (Short[])value ); 
        }
        else if (value instanceof Integer[])
        {
            return convertIntTabToString ( (Integer[])value ); 
        }
        else if (value instanceof Float[])
        {
            return convertFloatTabToString ( (Float[])value ); 
        }
        else if (value instanceof Boolean[])
        {
            return convertBooleanTabToString ( (Boolean[])value ); 
        }
        else if (value instanceof String[])
        {
            return convertStringTabToString ( (String[])value ); 
        }
        else
        {
            return null;
        }
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertDoubleTabToString(Double[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertByteTabToString(Byte[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertIntTabToString(Integer[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertShortTabToString(Short[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertFloatTabToString(Float[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertBooleanTabToString(Boolean[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertStringTabToString(String[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

}
