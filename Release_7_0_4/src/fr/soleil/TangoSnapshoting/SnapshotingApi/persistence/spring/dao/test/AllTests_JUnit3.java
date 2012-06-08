/*	Synchrotron Soleil 
 *  
 *   File          :  AllTests.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  14 mars 07 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: AllTests.java,v 
 *
 */
 /*
 * Created on 14 mars 07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests_JUnit3 {

    public static Test suite() {
        TestSuite suite = new TestSuite(
                "Test for fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.test.generic");
        //$JUnit-BEGIN$
        suite.addTestSuite(ScNum1ValDAOTest.class);
        suite.addTestSuite(ScStr2ValDAOTest.class);
        suite.addTestSuite(Sp2ValDAOTest.class);
        suite.addTestSuite(ScNum2ValDAOTest.class);
        suite.addTestSuite(ScStr1ValDAOTest.class);
        suite.addTestSuite(Sp1ValDAOTest.class);
        //$JUnit-END$
        return suite;
    }

}
