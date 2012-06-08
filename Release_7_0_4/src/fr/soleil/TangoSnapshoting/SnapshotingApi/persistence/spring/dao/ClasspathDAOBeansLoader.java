/*	Synchrotron Soleil 
 *  
 *   File          :  ClasspathDAOBeansLoader.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  6 avr. 07 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: ClasspathDAOBeansLoader.java,v 
 *
 */
 /*
 * Created on 6 avr. 07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Im1Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Im2Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScNum1Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScNum2Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScStr1Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScStr2Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Sp1Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Sp2Val;

public class ClasspathDAOBeansLoader implements DAOBeansLoader 
{
    private static final String DEFAULT_BEANS_FILE_NAME = "beans.xml";
    private String resourceName;
    
    private ValDAO<ScNum1Val> scNum1ValDAO;
    private ValDAO<ScNum2Val> scNum2ValDAO;
    private ValDAO<ScStr1Val> scStr1ValDAO;
    private ValDAO<ScStr2Val> scStr2ValDAO;
    private ValDAO<Sp1Val> sp1ValDAO;
    private ValDAO<Sp2Val> sp2ValDAO;
    private ValDAO<Im1Val> im1ValDAO;
    private ValDAO<Im2Val> im2ValDAO;
    
    public ClasspathDAOBeansLoader ( String _resourceName )
    {
        boolean defaultResource = _resourceName == null || _resourceName.trim().length() == 0;
        this.resourceName = defaultResource ? DEFAULT_BEANS_FILE_NAME : _resourceName;
        
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext ( this.resourceName );
        ctx.registerShutdownHook();//Shuts down the Spring IoC container gracefully in non-web applications
        this.instantiateBeans ( ctx );
    }
    
    @SuppressWarnings("unchecked")
    private void instantiateBeans(AbstractApplicationContext ctx) 
    {
        scNum1ValDAO = (ValDAO<ScNum1Val>) ctx.getBean("scNum1ValDAO");
        scNum2ValDAO = (ValDAO<ScNum2Val>) ctx.getBean("scNum2ValDAO");
        scStr1ValDAO = (ValDAO<ScStr1Val>) ctx.getBean("scStr1ValDAO");
        scStr2ValDAO = (ValDAO<ScStr2Val>) ctx.getBean("scStr2ValDAO");
        sp1ValDAO = (ValDAO<Sp1Val>) ctx.getBean("sp1ValDAO");
        sp2ValDAO = (ValDAO<Sp2Val>) ctx.getBean("sp2ValDAO");
        im1ValDAO = (ValDAO<Im1Val>) ctx.getBean("im1ValDAO");
        im2ValDAO = (ValDAO<Im2Val>) ctx.getBean("im2ValDAO");    
    }

    /**
     * @return the im1ValDAO
     */
    public ValDAO<Im1Val> getIm1ValDAO() {
        return this.im1ValDAO;
    }

    /**
     * @return the im2ValDAO
     */
    public ValDAO<Im2Val> getIm2ValDAO() {
        return this.im2ValDAO;
    }

    /**
     * @return the scNum1ValDAO
     */
    public ValDAO<ScNum1Val> getScNum1ValDAO() {
        return this.scNum1ValDAO;
    }

    /**
     * @return the scNum2ValDAO
     */
    public ValDAO<ScNum2Val> getScNum2ValDAO() {
        return this.scNum2ValDAO;
    }

    /**
     * @return the scStr1ValDAO
     */
    public ValDAO<ScStr1Val> getScStr1ValDAO() {
        return this.scStr1ValDAO;
    }

    /**
     * @return the scStr2ValDAO
     */
    public ValDAO<ScStr2Val> getScStr2ValDAO() {
        return this.scStr2ValDAO;
    }

    /**
     * @return the sp1ValDAO
     */
    public ValDAO<Sp1Val> getSp1ValDAO() {
        return this.sp1ValDAO;
    }

    /**
     * @return the sp2ValDAO
     */
    public ValDAO<Sp2Val> getSp2ValDAO() {
        return this.sp2ValDAO;
    }

    public String getResourceName() 
    {
        return this.resourceName;
    }
}
