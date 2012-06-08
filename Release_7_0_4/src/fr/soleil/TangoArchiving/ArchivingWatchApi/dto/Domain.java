/*	Synchrotron Soleil 
 *  
 *   File          :  Domain.java
 *  
 *   Project       :  archiving_watcher
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  13 janv. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: Domain.java,v 
 *
 */
 /*
 * Created on 13 janv. 2006
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
 * An equivalence class for all the archiving attributes that have the same domain name.
 * @author CLAISSE 
 */
public class Domain 
{
    private String name;   
    private Hashtable KOAttributes;
    private ArchivingAttributeComparator comparator;
    
    /**
     * Default constructor
     */
    public Domain() 
    {
        this.KOAttributes = new Hashtable ();
        this.comparator = new ArchivingAttributeComparator ();
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
     * Takes a list of KO attributes and "absorbs" those that have the right domain name
     * @param hashtable A list of KO attributes, which keys are the attributes complete names and which values are ArchivingAttribute objects 
     */
    public void buildKOAttributes ( Hashtable _errorAttributes ) 
    {
        if ( _errorAttributes == null || _errorAttributes.size () == 0 )
        {
            return;
        }
        
        Enumeration keys = _errorAttributes.keys ();
        while ( keys.hasMoreElements () )
        {
            String attributeCompleteName = (String) keys.nextElement ();
            ArchivingAttribute attribute = (ArchivingAttribute) _errorAttributes.get ( attributeCompleteName );
            String attributeDomain = attribute.getDomain ();
            
            if ( attributeDomain != null && attributeDomain.equals ( this.name ) )
            {
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
     * Builds and returns the archiving report for this archiver:
     * <UL>
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
