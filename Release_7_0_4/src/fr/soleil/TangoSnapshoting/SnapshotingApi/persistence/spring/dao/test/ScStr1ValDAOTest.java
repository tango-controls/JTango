package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.test;


import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.ValDAO;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.CompositeId;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScStr1Val;

public class ScStr1ValDAOTest extends AbstractValDAOTest <ScStr1Val>  
{
    protected void compare(ScStr1Val original, ScStr1Val copy) 
    {
        super.compare ( original , copy );
        super.assertEquals ( original.getValue () , copy.getValue () );
    }
    
    protected ScStr1Val buildLine() 
    {
        ScStr1Val line = new ScStr1Val ();
        
        CompositeId compositeId = new CompositeId ();
        int idAtt = 999;
        int idSnap = 888;
        compositeId.setIdAtt(idAtt);
        compositeId.setIdSnap(idSnap);
        line.setCompositeId(compositeId);
        
        String value = "ScStr1Val example value";
        line.setValue(value);
        
        return line;
    }

    /**
     * @param scNum1ValDAO the scNum1ValDAO to set
     */
    public void setScStr1ValDAO(ValDAO<ScStr1Val> scNum1ValDAO) 
    {
        super.dao = scNum1ValDAO;
    }

    @Override
    protected ScStr1Val[] buildEmptyLines(int numberOfLines) 
    {   
        return new ScStr1Val [ numberOfLines ];
    }
}
