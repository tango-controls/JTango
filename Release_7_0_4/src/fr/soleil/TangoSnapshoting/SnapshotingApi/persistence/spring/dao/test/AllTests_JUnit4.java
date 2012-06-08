package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
 
@RunWith(Suite.class)
@Suite.SuiteClasses
({
    ScNum1ValDAOTest.class,
    ScNum2ValDAOTest.class,
    ScStr1ValDAOTest.class,
    ScStr2ValDAOTest.class,
    Sp1ValDAOTest.class,
    Sp2ValDAOTest.class
})
public class AllTests_JUnit4 
{
    // the class remains completely empty, 
    // being used only as a holder for the above annotations
    
    /*CLA/Uncomment if needed
     * public static void main(String[] args) 
    {
        JUnitCore.runClasses(new Class[] { AllTests2.class });
    }*/
}