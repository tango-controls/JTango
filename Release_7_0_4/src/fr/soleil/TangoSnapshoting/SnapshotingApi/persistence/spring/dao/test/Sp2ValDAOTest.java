package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.test;


import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.ValDAO;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.CompositeId;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Sp2Val;

public class Sp2ValDAOTest extends AbstractValDAOTest <Sp2Val>  
{
    protected void compare(Sp2Val original, Sp2Val copy) 
    {
        super.compare ( original , copy );
        super.assertEquals ( original.getReadValue () , copy.getReadValue () );
        super.assertEquals ( original.getWriteValue () , copy.getWriteValue () );
        super.assertEquals ( original.getDimX (), copy.getDimX () );
    }
    
    protected Sp2Val buildLine() 
    {
        Sp2Val line = new Sp2Val ();
        
        CompositeId compositeId = new CompositeId ();
        int idAtt = 999;
        int idSnap = 888;
        compositeId.setIdAtt(idAtt);
        compositeId.setIdSnap(idSnap);
        line.setCompositeId(compositeId);
        
        String readValue = "A,B,C";
        String writeValue = "D";
        line.setReadValue(readValue);
        line.setWriteValue(writeValue);
        
        line.setDimX(3);
        
        return line;
    }

    /**
     * @param scNum1ValDAO the scNum1ValDAO to set
     */
    public void setSp2ValDAO(ValDAO<Sp2Val> scNum1ValDAO) 
    {
        super.dao = scNum1ValDAO;
    }

    @Override
    protected Sp2Val[] buildEmptyLines(int numberOfLines) 
    {   
        return new Sp2Val [ numberOfLines ];
    }
}
