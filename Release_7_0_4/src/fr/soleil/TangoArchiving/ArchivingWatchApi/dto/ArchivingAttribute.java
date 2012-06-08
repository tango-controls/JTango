/*	Synchrotron Soleil 
 *  
 *   File          :  Attribute.java
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
 *   Log: Attribute.java,v 
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
import java.util.Hashtable;
import java.util.StringTokenizer;

import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * Models an attribute that is supposed to be archiving
 * @author CLAISSE 
 */
public class ArchivingAttribute 
{
    private String completeName;
    private Timestamp lastInsert;
    private Timestamp lastInsertRequest;
    private int archivingStatus = -1;
    private String archiver;
    private int period = -1;
    private boolean isArchivingOK;
    private String deviceStatus;

    private String domain;
    private String attribute;
    private String device;
    private boolean hasBeenParsed = false;
    
    private boolean isDetermined = true;
    
    private static Hashtable lastInserts = new Hashtable ();
    
    /**
     * Default constructor
     */
    public ArchivingAttribute() 
    {
        super();
    }
    
    public ArchivingAttribute(String attributeName) 
    {
        this.setCompleteName ( attributeName );
    }

    private void parseIfNecessary ()
    {
        if ( ! this.hasBeenParsed )
        {
            this.parse (); 
        }
        
        this.hasBeenParsed = true;
    }
    
    /**
     * 
     */
    private void parse() 
    {
        StringTokenizer st = new StringTokenizer ( this.completeName , Tools.TANGO_SEPARATOR );
        
        try
        {
            String dom = st.nextToken ();
            String fam = st.nextToken ();
            String mem = st.nextToken ();
            String att = st.nextToken ();
            String dev = dom + Tools.TANGO_SEPARATOR + fam + Tools.TANGO_SEPARATOR + mem;
            
            this.domain = dom;
            this.attribute = att;
            this.device = dev;
        }
        catch ( Exception e )
        {
            e.printStackTrace ();
            return;
        }
    }

    /**
     * @return Returns the completeName.
     */
    public String getCompleteName() {
        return completeName;
    }
    /**
     * @param completeName The completeName to set.
     */
    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }
    /**
     * @return Returns the lastInsert.
     */
    public Timestamp getLastInsert() {
        return lastInsert;
    }
    /**
     * @param lastInsert The lastInsert to set.
     */
    public void setLastInsert(Timestamp lastInsert) 
    {
        this.lastInsert = lastInsert;
        ArchivingAttribute.putLastInsert ( this.completeName , lastInsert );
    }
    /**
     * @return Returns the attributeStatus.
     */
    public int getArchivingStatus() {
        return archivingStatus;
    }
    /**
     * @param attributeStatus The attributeStatus to set.
     */
    public void setArchivingStatus(int attributeStatus) {
        this.archivingStatus = attributeStatus;
    }

    /**
     * Returns the domain part of the attribute's complete name 
     * @return The domain name
     */
    public String getDomain() 
    {
        if ( this.completeName == null )
        {
            return null;
        }
        this.parseIfNecessary ();
        return this.domain;
    }
    
    /**
     * Returns the device part of the attribute's complete name 
     * @return The device name
     */
    public String getDeviceName() 
    {
        if ( this.completeName == null )
        {
            return null;
        }
        
        this.parseIfNecessary ();
        return this.device;
    }
    
    /**
     * @return Returns the archiver.
     */
    public String getArchiver() {
        return archiver;
    }
    /**
     * @param archiver The archiver to set.
     */
    public void setArchiver(String archiver) {
        this.archiver = archiver;
    }
    /**
     * @return Returns the isArchivingOK.
     */
    public boolean isArchivingOK() {
        return isArchivingOK;
    }
    /**
     * @param isArchivingOK The isArchivingOK to set.
     */
    public void setArchivingOK(boolean isArchivingOK) {
        this.isArchivingOK = isArchivingOK;
    }
    /**
     * @return Returns the period.
     */
    public int getPeriod() {
        return period;
    }
    /**
     * @param period The period to set.
     */
    public void setPeriod(int period) {
        this.period = period;
    }
    /**
     * @return Returns the deviceStatus.
     */
    public String getDeviceStatus() {
        return deviceStatus;
    }
    /**
     * @param deviceStatus The deviceStatus to set.
     */
    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    /**
     * Returns the attribute part of the attribute's complete name 
     * @return The attribute name
     */
    public String getAttributeSubName() 
    {
        if ( this.completeName == null )
        {
            return null;
        }
        
        this.parseIfNecessary ();
        return this.attribute;
    }

    /**
     * @param lastInsertRequest
     */
    public void setLastInsertRequest(Timestamp _lastInsertRequest) 
    {
        this.lastInsertRequest = _lastInsertRequest;
    }
    /**
     * @return Returns the lastInsertRequest.
     */
    public Timestamp getLastInsertRequest() {
        return lastInsertRequest;
    }

    /**
     * An attribute is called "determined" if its OK/KO status is known with certainty.
     * Conversely, it is called "undetermined" if an error (SQL problem or other) has prevented determining this status with certainty. 
     * @return Returns the isDetermined.
     */
    public boolean isDetermined() {
        return isDetermined;
    }

    /**
     * An attribute is called "determined" if its OK/KO status is known with certainty.
     * Conversely, it is called "undetermined" if an error (SQL problem or other) has prevented determining this status with certainty.
     * @param isDetermined The isDetermined to set.
     */
    public void setDetermined(boolean isDetermined) {
        this.isDetermined = isDetermined;
    }
    
    private static void putLastInsert ( String completeName , Timestamp lastInsert )
    {
        System.out.println ();
        
        if ( lastInsert != null && completeName != null )
        {
            lastInserts.put ( completeName , lastInsert );    
        }
        
    }
    
    public static Timestamp getLastInsert ( ArchivingAttribute attribute )
    {
        if ( attribute == null )
        {
            return null;
        }
        
        String completeName = attribute.getCompleteName ();
        if ( completeName == null )
        {
            return null;
        }
        
        Timestamp _lastInsert = (Timestamp) lastInserts.get ( completeName );
        return _lastInsert;
    }
}
