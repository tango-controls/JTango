package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.test;


import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.ValDAO;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.CompositeId;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScStr2Val;

public class ScStr2ValDAOTest extends AbstractValDAOTest <ScStr2Val>  
{
    protected void compare(ScStr2Val original, ScStr2Val copy) 
    {
        super.compare ( original , copy );
        super.assertEquals ( original.getReadValue () , copy.getReadValue () );
        super.assertEquals ( original.getWriteValue () , copy.getWriteValue () );
    }
    
    protected ScStr2Val buildLine() 
    {
        ScStr2Val line = new ScStr2Val ();
        
        CompositeId compositeId = new CompositeId ();
        int idAtt = 999;
        int idSnap = 888;
        compositeId.setIdAtt(idAtt);
        compositeId.setIdSnap(idSnap);
        line.setCompositeId(compositeId);
        
        String readValue = "ScStr2Val example readValue";
        String writeValue = "ScStr2Val example writeValue";
        line.setReadValue(readValue);
        line.setWriteValue(writeValue);
        
        return line;
    }

    /**
     * @param scNum1ValDAO the scNum1ValDAO to set
     */
    public void setScStr2ValDAO(ValDAO<ScStr2Val> scNum1ValDAO) 
    {
        super.dao = scNum1ValDAO;
    }

    @Override
    protected ScStr2Val[] buildEmptyLines(int numberOfLines) 
    {   
        return new ScStr2Val [ numberOfLines ];
    }
}
