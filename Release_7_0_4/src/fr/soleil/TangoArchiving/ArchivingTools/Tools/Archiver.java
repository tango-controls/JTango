/*	Synchrotron Soleil 
 *  
 *   File          :  Archiver.java
 *  
 *   Project       :  mambo
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  6 juin 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: Archiver.java,v 
 *
 */
 /*
 * Created on 6 juin 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import java.awt.Color;
import java.util.Hashtable;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DbDatum;
import fr.esrf.TangoApi.DeviceProxy;

public class Archiver 
{
    private String name;
    private boolean isDedicated;
    private String [] reservedAttributes;
    private boolean isLoaded = false;
    private boolean isHistoric = false;
    private boolean isExported;
    
    private static Hashtable attributesToHdbDedicatedArchiver;
    private static Hashtable attributesToTdbDedicatedArchiver;
    private static Hashtable archivers = new Hashtable ();
    
    public static final int NON_DEDICATED_ARCHIVER_RIGHT = 1;
    public static final int DEDICATED_ARCHIVER_RIGHT = 2;
    public static final int DEDICATED_ARCHIVER_WRONG = 3;
    public static final int NON_DEDICATED_ARCHIVER_WRONG = 4;
    
    private static final String m_reservedAttributes = "reservedAttributes";
    private static final String m_isDedicated = "isDedicated";
    
    private static final Color DARKER_GREEN = new Color ( 0 , 170 , 0 );
    private static final Color DARKER_RED = new Color ( 200 , 0 , 0 );
    
    public void load ()
    {
        if ( this.isLoaded )
        {
            return;    
        }
        
        try 
        {
            Archiver loaded = Archiver.getArchiver ( this.name , this.isHistoric );
            this.setDedicated ( loaded.isDedicated () );
            this.setReservedAttributes ( loaded.getReservedAttributes () );
        } 
        catch (ArchivingException e) 
        {
            e.printStackTrace();
        }
        finally
        {
            this.isLoaded  = true;
        }
    }
    
    public Color getAssociationColor ( String attributeName )
    {
        int colorCase = this.hasReservedAttribute ( attributeName );
        Color color;
        switch ( colorCase )
        {
            case Archiver.DEDICATED_ARCHIVER_RIGHT:
                color = DARKER_GREEN;
            break;
            
            case Archiver.DEDICATED_ARCHIVER_WRONG:
                color = DARKER_RED;
            break;
            
            /*case Archiver.NON_DEDICATED_ARCHIVER_RIGHT:
                color = Color.BLACK;
            break;
            
            case Archiver.NON_DEDICATED_ARCHIVER_WRONG:
                color = Color.ORANGE;
            break;
            
            default:
                color = Color.GRAY;*/
            default:
                color = Color.BLACK;
        }
        
        return color;
    }
    
    private int hasReservedAttribute ( String attributeName )
    {
        this.load ();
        
        if ( ! this.isDedicated )
        {
            return nonDedicated ( attributeName ); 
        }
        if ( this.reservedAttributes == null || this.reservedAttributes.length == 0 )
        {
            return nonDedicated ( attributeName );
        }
        if ( attributeName == null || attributeName.equals ( "" ) )
        {
            return NON_DEDICATED_ARCHIVER_RIGHT;
        }
        
        boolean hasAttribute = false;
        for ( int i = 0 ; i < this.reservedAttributes.length ; i++ )
        {
            String nextAttribute = this.reservedAttributes [ i ];
            if ( nextAttribute == null )
            {
                continue;
            }

            if ( nextAttribute.equals ( attributeName ) )
            {
                hasAttribute = true;
                break;
            }
        }
        int ret = hasAttribute ? DEDICATED_ARCHIVER_RIGHT : DEDICATED_ARCHIVER_WRONG;
        return ret;
    }
    
    private int nonDedicated ( String attributeName ) 
    {
        Hashtable attributesToDedicatedArchiver = this.isHistoric ? attributesToHdbDedicatedArchiver : attributesToTdbDedicatedArchiver; 
        
        if ( attributesToDedicatedArchiver == null || attributeName == null )
        {
            return NON_DEDICATED_ARCHIVER_RIGHT;
        }
        
        if ( ! attributesToDedicatedArchiver.containsKey ( attributeName ) )
        {
            return NON_DEDICATED_ARCHIVER_RIGHT;    
        }
        else
        {
            return NON_DEDICATED_ARCHIVER_WRONG;
        }
    }

    public Archiver ( String _name , boolean _isHistoric ) 
    {
        this.name = _name;
        this.isHistoric = _isHistoric;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the isDedicated.
     */
    public boolean isDedicated() {
        return this.isDedicated;
    }

    /**
     * @param isDedicated The isDedicated to set.
     */
    public void setDedicated(boolean isDedicated) {
        this.isDedicated = isDedicated;
    }

    /**
     * @return Returns the reservedAttributes.
     */
    public String[] getReservedAttributes() {
        return this.reservedAttributes;
    }

    /**
     * @param reservedAttributes The reservedAttributes to set.
     */
    public void setReservedAttributes(String[] reservedAttributes) 
    {
        this.reservedAttributes = reservedAttributes;
    }

    /**
     * @param attributesToHdbDedicatedArchiver The deviceDefinedAttributesToDedicatedArchiver to set.
     */
    public static void setAttributesToDedicatedArchiver(boolean historic,Hashtable _deviceDefinedAttributesToDedicatedArchiver) 
    {
        if ( historic )
        {
            //System.out.println ( "CLA/Archiver/setAttributesToDedicatedArchiver/0" );
            attributesToHdbDedicatedArchiver = _deviceDefinedAttributesToDedicatedArchiver;    
        }
        else
        {
            //System.out.println ( "CLA/Archiver/setAttributesToDedicatedArchiver/1" );
            attributesToTdbDedicatedArchiver = _deviceDefinedAttributesToDedicatedArchiver;
        }
    }
    
    private static Archiver getArchiver ( String my_archiver, boolean _historic ) throws ArchivingException
    {
        Archiver ret = new Archiver ( my_archiver , _historic );
        
        DeviceProxy archiverProxy = null;
        try
        {
            archiverProxy = new DeviceProxy(my_archiver);
            
            DbDatum isDedicatedRawValue = archiverProxy.get_property ( m_isDedicated );
            boolean isDedicated = false;
            
            DbDatum reservedAttributesRawValue = archiverProxy.get_property ( m_reservedAttributes );
            String [] reservedAttributes = null;
            
            try
            {
                isDedicated = isDedicatedRawValue.extractBoolean ();
            }
            catch ( Exception e )
            {
                //do nothing, no such attribute or empty value
            }
            try
            {
                reservedAttributes = reservedAttributesRawValue.extractStringArray ();
                /*int l = reservedAttributes == null ? 0 : reservedAttributes.length;
                for ( int i = 0 ; i < l ; i ++ )
                {
                    //System.out.println ( "CLA/Archiver/getArchiver/i|"+i+"|reservedAttributes|"+reservedAttributes[i]+"|" );    
                }*/
            }
            catch ( Exception e )
            {
                //do nothing, no such attribute or empty value
            }
            
            ret.setDedicated ( isDedicated );
            ret.setReservedAttributes ( reservedAttributes );
            
            archivers.put ( ret.getName () , ret );
        }
        catch ( DevFailed devFailed )
        {
            devFailed.printStackTrace ();
        }
        return ret;
    }

    /*public void trace () 
    {
        if ( attributesToHdbDedicatedArchiver == null )
        {
            return;
        }
        Enumeration enumeration = attributesToHdbDedicatedArchiver.keys ();
        while ( enumeration.hasMoreElements () )
        {
            String nextAttribute = (String) enumeration.nextElement ();
            Archiver nextArchiver = (Archiver) attributesToHdbDedicatedArchiver.get ( nextAttribute );
            
            System.out.println ( "Archiver/trace/nextAttribute|"+nextAttribute+"|nextArchiver|"+nextArchiver.getName()+"|" );
        }
            
    }*/

    public static Archiver findArchiver ( String name , boolean _historic ) throws ArchivingException 
    {
        if ( name == null )
        {
            return null;
        }
        
        Archiver ret = (Archiver) archivers.get ( name );
        if ( ret == null )
        {
            return Archiver.getArchiver ( name, _historic );
        }
        else
        {
            return ret;   
        }
    }

    /**
     * @return Returns the attributesToDedicatedArchiver.
     */
    public static Hashtable getAttributesToDedicatedArchiver( boolean historic ) 
    {
        if ( historic )
        {
            return attributesToHdbDedicatedArchiver;    
        }
        else
        {
            return attributesToTdbDedicatedArchiver;    
        }
    }

    public void setExported(boolean _isExported) 
    {
        this.isExported = _isExported;
    }

    /**
     * @return Returns the isExported.
     */
    public boolean isExported() {
        return this.isExported;
    }
}
