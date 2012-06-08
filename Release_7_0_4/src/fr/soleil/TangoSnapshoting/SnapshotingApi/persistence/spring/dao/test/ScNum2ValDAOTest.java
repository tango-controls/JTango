package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.test;


import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.ValDAO;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.CompositeId;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScNum2Val;

public class ScNum2ValDAOTest extends AbstractValDAOTest <ScNum2Val>  
{
    protected void compare(ScNum2Val original, ScNum2Val copy) 
    {
        super.compare ( original , copy );
        super.assertEquals ( original.getReadValue () , copy.getReadValue () );
        super.assertEquals ( original.getWriteValue () , copy.getWriteValue () );
    }
    
    protected ScNum2Val buildLine() 
    {
        ScNum2Val line = new ScNum2Val ();
        
        CompositeId compositeId = new CompositeId ();
        int idAtt = 999;
        int idSnap = 888;
        compositeId.setIdAtt(idAtt);
        compositeId.setIdSnap(idSnap);
        line.setCompositeId(compositeId);
        
        double readValue = 1.234;
        double writeValue = 5.678;
        line.setReadValue(readValue);
        line.setWriteValue(writeValue);
        
        return line;
    }

    /**
     * @param scNum1ValDAO the scNum1ValDAO to set
     */
    public void setScNum2ValDAO(ValDAO<ScNum2Val> scNum1ValDAO) 
    {
        super.dao = scNum1ValDAO;
    }

    @Override
    protected ScNum2Val[] buildEmptyLines(int numberOfLines) 
    {   
        return new ScNum2Val [ numberOfLines ];
    }
}
