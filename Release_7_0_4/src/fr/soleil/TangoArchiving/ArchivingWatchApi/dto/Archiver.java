/*	Synchrotron Soleil 
 *  
 *   File          :  Archiver.java
 *  
 *   Project       :  archiving_watcher
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  15 déc. 2005 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: Archiver.java,v 
 *
 */
 /*
 * Created on 15 déc. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.dto;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import fr.soleil.TangoArchiving.ArchivingTools.Tools.Chaine;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.comparators.ArchivingAttributeComparator;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * Models an archiver.
 * @author CLAISSE 
 */
public class Archiver 
{
    private String name;
    
    private int scalarLoad;
    private int spectrumLoad;
    private int imageLoad;
    private int totalLoad;
    
    private boolean isLiving;
    private boolean doDiagnosis;
    private String status;
    
    private Hashtable KOAttributes;
    private ArchivingAttributeComparator comparator;
    
    /**
     * Default constructor
     */
    public Archiver() 
    {
        this.KOAttributes = new Hashtable ();
        this.comparator = new ArchivingAttributeComparator ();
    }

    /**
     * @return Returns the isLiving.
     */
    public boolean isLiving() {
        return isLiving;
    }
    /**
     * @param isLiving The isLiving to set.
     */
    public void setLiving(boolean isLiving) {
        this.isLiving = isLiving;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
   
    /**
     * @return Returns the imageLoad.
     */
    public int getImageLoad() {
        return imageLoad;
    }
    /**
     * @param imageLoad The imageLoad to set.
     */
    public void setImageLoad(int imageLoad) {
        this.imageLoad = imageLoad;
    }
    /**
     * @return Returns the scalarLoad.
     */
    public int getScalarLoad() {
        return scalarLoad;
    }
    /**
     * @param scalarLoad The scalarLoad to set.
     */
    public void setScalarLoad(int scalarLoad) {
        this.scalarLoad = scalarLoad;
    }
    /**
     * @return Returns the spectrumLoad.
     */
    public int getSpectrumLoad() {
        return spectrumLoad;
    }
    /**
     * @param spectrumLoad The spectrumLoad to set.
     */
    public void setSpectrumLoad(int spectrumLoad) {
        this.spectrumLoad = spectrumLoad;
    }
    /**
     * @return Returns the totalLoad.
     */
    public int getTotalLoad() {
        return totalLoad;
    }
    /**
     * @param totalLoad The totalLoad to set.
     */
    public void setTotalLoad(int totalLoad) {
        this.totalLoad = totalLoad;
    }


    /**
     * Builds and returns the archiving report for this archiver:
     * <UL>
     *  <LI> The archiver's status, loads,..
     *  <LI> The archiver's KO attributes 
     * </UL> 
     * @return The archiving report for this archiver
     */
    public String getReport() 
    {
        StringBuffer buff = new StringBuffer ();
        
        String inLine = this.getName () + " :  VVVVVVVVVVVVVVVVVVVVVVVVVVVV";
        buff.append ( inLine );
        buff.append ( Tools.CRLF );

        String isLiving = this.isDoDiagnosis () ? "" + this.isLiving () : "Not tested";
        buff.append ( "Is device alive? " + isLiving );
        buff.append ( Tools.CRLF );
        buff.append ( "Status: " + this.getStatus () );
        buff.append ( Tools.CRLF );
        buff.append ( "Total load: " + this.getTotalLoad () + ". Scalar load: " + this.getScalarLoad () + ". Spectrum load: " + this.getSpectrumLoad () + ". Image load: " + this.getImageLoad () );
        
        buff.append ( Tools.CRLF );
        buff.append ( "KO attributes : --------------------------" );
        buff.append ( Tools.CRLF );
        
        List list = new Vector ();
        list.addAll ( this.KOAttributes.values () );
        Collections.sort(list, this.comparator );
        Iterator it = list.iterator ();
        
        while ( it.hasNext () )
        {
            ArchivingAttribute attribute = (ArchivingAttribute)  it.next ();
            String attributeCompleteName = attribute.getCompleteName ();
            buff.append ( "    " + attributeCompleteName );
            buff.append ( Tools.CRLF );
        }
        
        int size = inLine.length ();
        String outLine = Chaine.repeter ( "^" , size );
        buff.append ( outLine );
        
        return buff.toString ();
    }
    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return Returns the doDiagnosis.
     */
    public boolean isDoDiagnosis() {
        return doDiagnosis;
    }
    /**
     * @param doDiagnosis The doDiagnosis to set.
     */
    public void setDoDiagnosis(boolean doDiagnosis) {
        this.doDiagnosis = doDiagnosis;
    }


    /**
     * Takes a list of KO attributes and "absorbs" those that have the right archiver name
     * @param hashtable A list of KO attributes, which keys are the attributes complete names and which values are ArchivingAttribute objects 
     */
    public void buildKOAttributes ( Hashtable _errorAttributes ) 
    {
        if ( _errorAttributes == null || _errorAttributes.size () == 0 )
        {
            if ( _errorAttributes == null )
            {
                //Tools.trace ( "Archiver/buildKOAttributes/_errorAttributes == null" , Warnable.LOG_LEVEL_DEBUG );    
            }
            else if ( _errorAttributes.size () == 0 )
            {
                //Tools.trace ( "Archiver/buildKOAttributes/_errorAttributes.size () == 0" , Warnable.LOG_LEVEL_DEBUG );    
            }
            
            return;
        }
        
        Enumeration keys = _errorAttributes.keys ();
        while ( keys.hasMoreElements () )
        {
            String attributeCompleteName = (String) keys.nextElement ();
            ArchivingAttribute attribute = (ArchivingAttribute) _errorAttributes.get ( attributeCompleteName );
            String attributeArchiver = attribute.getArchiver ();
            
            //Tools.trace ( "Archiver/buildKOAttributes/attributeArchiver|"+attributeArchiver+"|" , Warnable.LOG_LEVEL_DEBUG );
            if ( attributeArchiver != null && attributeArchiver.equals ( this.name ) )
            {
                //Tools.trace ( "Archiver/buildKOAttributes/adding attribute|"+attributeCompleteName+"|" , Warnable.LOG_LEVEL_DEBUG );
                this.addKOAttribute ( attribute );    
            }
        }
    }

    /**
     * @param attribute
     */
    private void addKOAttribute ( ArchivingAttribute attribute ) 
    {
        if ( attribute.isDetermined () )
        {
            this.KOAttributes.put ( attribute.getCompleteName () , attribute );
        }
    }
    /**
     * Returns the kOAttributes.
     * @return The list of KO attributes, which keys are the attributes complete names and which values are ArchivingAttribute objects
     */
    public Hashtable getKOAttributes() {
        return KOAttributes;
    }
    /**
     * Sets the kOAttributes.
     * @param attributes The list of KO attributes, which keys are the attributes complete names and which values are ArchivingAttribute objects
     */
    public void setKOAttributes(Hashtable attributes) {
        KOAttributes = attributes;
    }


    /**
     * Returns whether this archiver has at least one KO attribute
     * @return Whether this archiver has at least one KO attribute
     */
    public boolean hasKOAttributes() 
    {
        if ( this.KOAttributes == null )
        {
            return false;    
        }
        if ( this.KOAttributes.size() == 0 )
        {
            return false;    
        }
        
        return true;
    }
    
    
}
