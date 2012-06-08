package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.test;


import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.ValDAO;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.CompositeId;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScNum1Val;

public class ScNum1ValDAOTest extends AbstractValDAOTest <ScNum1Val>  
{
    protected void compare(ScNum1Val original, ScNum1Val copy) 
    {
        super.compare ( original , copy );
        super.assertEquals ( original.getValue () , copy.getValue () );
    }
    
    protected ScNum1Val buildLine() 
    {
        ScNum1Val line = new ScNum1Val ();
        
        CompositeId compositeId = new CompositeId ();
        int idAtt = 999;
        int idSnap = 888;
        compositeId.setIdAtt(idAtt);
        compositeId.setIdSnap(idSnap);
        line.setCompositeId(compositeId);
        
        double value = 1.234;
        line.setValue(value);
        
        return line;
    }

    /**
     * @param scNum1ValDAO the scNum1ValDAO to set
     */
    public void setScNum1ValDAO(ValDAO<ScNum1Val> scNum1ValDAO) 
    {
        super.dao = scNum1ValDAO;
    }

    @Override
    protected ScNum1Val[] buildEmptyLines(int numberOfLines) 
    {   
        return new ScNum1Val [ numberOfLines ];
    }
}
