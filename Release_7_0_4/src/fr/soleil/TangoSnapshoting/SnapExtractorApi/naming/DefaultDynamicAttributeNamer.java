/*	Synchrotron Soleil 
 *  
 *   File          :  DefaultDynamicAttributeNamer.java
 *  
 *   Project       :  snapExtractorAPI
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  25 janv. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: DefaultDynamicAttributeNamer.java,v 
 *
 */
 /*
 * Created on 25 janv. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapExtractorApi.naming;

import java.util.StringTokenizer;

import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeExtract;

/**
 * The default implementation:
 * the dynamic attributes names it generates follow the pattern:
 * [attributeName]_[dynamicAttributesCounter]_[R|W]  
 * @author CLAISSE 
 */
public class DefaultDynamicAttributeNamer implements IDynamicAttributeNamer
{
    private static final String TANGO_SEPARATOR = "/";
    private static final String NAME_SEPARATOR = "_";
    private static final String READ_MARKER = "R";
    private static final String WRITE_MARKER = "W";
    
    DefaultDynamicAttributeNamer() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see snapextractor.api.naming.IDynamicAttributeNamer#getName(fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeExtract, int, boolean)
     */
    public String getName(SnapAttributeExtract realAttribute, int id, boolean isReadValue) 
    {
        String completeName = realAttribute.getAttribute_complete_name ();
        String partialName = this.getPartialName ( completeName );
        String marker = isReadValue ? DefaultDynamicAttributeNamer.READ_MARKER : DefaultDynamicAttributeNamer.WRITE_MARKER; 
        
        StringBuffer buff = new StringBuffer ();
        buff.append ( partialName );
        buff.append ( DefaultDynamicAttributeNamer.NAME_SEPARATOR );
        buff.append ( String.valueOf ( id ) );
        buff.append ( DefaultDynamicAttributeNamer.NAME_SEPARATOR );
        buff.append ( marker );
        
        return buff.toString ().toLowerCase ();
    }

    /**
     * @param completeName
     * @return
     */
    private String getPartialName(String completeName) 
    {
        StringTokenizer st = new StringTokenizer ( completeName , DefaultDynamicAttributeNamer.TANGO_SEPARATOR );
        st.nextToken ();//domain
        st.nextToken ();//family
        st.nextToken ();//member
        return st.nextToken ();//attribute
    }

}
